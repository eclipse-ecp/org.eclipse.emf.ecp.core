/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.InjectionException;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.BazaarContextFunction;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Exchange;
import org.eclipse.emfforms.bazaar.Precondition;
import org.eclipse.emfforms.bazaar.Preconditions;
import org.eclipse.emfforms.bazaar.StaticBid;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Implementation of a {@link Bazaar} using the {@link IEclipseContext} for dependency injection.
 *
 * @param <T> the type of product produced by this {@link Bazaar}
 * @author jonas
 *
 */
// Suppress due to https://bugs.eclipse.org/bugs/show_bug.cgi?id=530023
@SuppressWarnings("restriction")
public class BazaarImpl<T> implements Bazaar<T> {

	private final Set<Vendor<? extends T>> vendors = new LinkedHashSet<Vendor<? extends T>>();

	private PriorityOverlapCallBack<? super T> priorityOverlapCallBack;

	private final Map<String, BazaarContextFunction> contextFunctionMap = new HashMap<String, BazaarContextFunction>();

	@Override
	public void addVendor(Vendor<? extends T> vendor) {
		vendors.add(vendor);
	}

	@Override
	public void removeVendor(Vendor<? extends T> vendor) {
		vendors.remove(vendor);
	}

	/**
	 * Returns the product which is provided with the highest priority by any {@link Vendor}.
	 *
	 * @param vendor The {@link Vendor} creating the product
	 * @param context The {@link IEclipseContext} to inject parameters for creating a product (see {@link Create})
	 * @return T the created product
	 */
	@SuppressWarnings("unchecked")
	public T createProduct(Vendor<? extends T> vendor, IEclipseContext context) {
		try {
			return (T) ContextInjectionFactory.invoke(vendor, Create.class, context);
		} catch (final InjectionException e) {
			return null;
		}
	}

	/**
	 * Transforms all {@link BazaarContextFunction}s to {@link IContextFunction}s and attaches those to an
	 * {@link IEclipseContext}.
	 *
	 * @param context the {@link IEclipseContext} to attache {@link IContextFunction}s to
	 * @return the {@link IEclipseContext} with attached {@link IContextFunction}
	 */
	public IEclipseContext addContextFunctions(IEclipseContext context) {
		final Set<Entry<String, BazaarContextFunction>> entrySet = contextFunctionMap.entrySet();
		for (final Entry<String, BazaarContextFunction> entry : entrySet) {
			final IContextFunction iContextFunction = new IContextFunction() {

				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					try {
						final Object transformed = ContextInjectionFactory.invoke(entry.getValue(), Exchange.class,
							context);
						if (transformed != null) {
							context.set(entry.getKey(), transformed);
						}
						return transformed;
					} catch (final InjectionException e) {
						return null;
					}
				}
			};
			context.set(entry.getKey(), iContextFunction);
		}
		return context;
	}

	/**
	 * Creates an {@link IEclipseContext} from a {@link BazaarContext}.
	 *
	 * @param bazaarContext the {@link BazaarContext} to get key / values from
	 * @return a {@link IEclipseContext} containign all key / values provided by the {@link BazaarContext}
	 */
	public IEclipseContext createEclipseContext(BazaarContext bazaarContext) {
		final IEclipseContext context = EclipseContextFactory.create();
		final Set<String> keySet = bazaarContext.getContextMap().keySet();
		for (final String string : keySet) {
			context.set(string, bazaarContext.getContextMap().get(string));
		}
		return context;
	}

	/**
	 * @param context the Eclipse injection context
	 * @return obtains the best available vendor for the give {@code context}
	 */
	public Vendor<? extends T> getBestVendor(IEclipseContext context) {
		Double winningBid = null;
		Vendor<? extends T> winner = null;

		for (final Vendor<? extends T> vendor : vendors) {
			final Double bid = bid(vendor, context);

			if (bid == null) {
				// This vendor refuses to bid
				continue;
			}

			if (winningBid == null || bid > winningBid) {
				// We have a new winner
				winningBid = bid;
				winner = vendor;
			} else if (bid.equals(winningBid)) {
				// Report the tie and keep the current winner
				reportPriorityOverlap(winner, vendor);
			}
		}

		return winner;
	}

	private Double bid(Vendor<? extends T> vendor, IEclipseContext context) {
		if (!checkPreConditions(vendor, context)) {
			return null;
		}

		Double result = null; // Assume no bid

		result = staticBid(vendor);
		if (result != null) {
			return result;
		}

		try {
			result = (Double) ContextInjectionFactory.invoke(vendor, Bid.class, context);
		} catch (final InjectionException e) {
			// Do nothing. The vendor refuses the bid
		} catch (final ClassCastException e) {
			// Bid method did not return a double. Reject it
		}

		return result;
	}

	private Double staticBid(Vendor<? extends T> vendor) {
		final StaticBid staticBid = vendor.getClass().getAnnotation(StaticBid.class);
		if (staticBid != null) {
			return staticBid.bid();
		}
		return null;
	}

	private Queue<Vendor<? extends T>> createVendorPriorityQueue(IEclipseContext context) {
		final Map<Vendor<? extends T>, Double> priorities = new HashMap<Vendor<? extends T>, Double>();
		for (final Vendor<? extends T> vendor : vendors) {
			final Double bid = bid(vendor, context);

			if (bid != null) {
				priorities.put(vendor, bid);
			} // else the vendor refuses to bid
		}

		if (priorities.isEmpty()) {
			return new PriorityQueue<Vendor<? extends T>>();
		}

		final Comparator<Vendor<? extends T>> ordering = new Comparator<Vendor<? extends T>>() {
			@Override
			public int compare(Vendor<? extends T> o1, Vendor<? extends T> o2) {
				// Sort highest bid to lowest
				return priorities.get(o2).compareTo(priorities.get(o1));
			}
		};

		final PriorityQueue<Vendor<? extends T>> result = new PriorityQueue<Vendor<? extends T>>(priorities.size(),
			ordering);
		result.addAll(priorities.keySet());

		return result;
	}

	/**
	 * @param existingVendorWithSamePriority
	 * @param vendor
	 */
	private void reportPriorityOverlap(Vendor<? extends T> existingVendorWithSamePriority, Vendor<? extends T> vendor) {
		if (priorityOverlapCallBack == null) {
			return;
		}
		priorityOverlapCallBack.priorityOverlap(existingVendorWithSamePriority, vendor);

	}

	@Override
	public void setPriorityOverlapCallBack(PriorityOverlapCallBack<? super T> priorityOverlapCallBack) {
		this.priorityOverlapCallBack = priorityOverlapCallBack;
	}

	/**
	 * @param key
	 * @param contextFunction
	 */
	@Override
	public void addContextFunction(String key, BazaarContextFunction contextFunction) {
		contextFunctionMap.put(key, contextFunction);
	}

	/**
	 *
	 * @see org.eclipse.emfforms.bazaar.Bazaar#createProduct(org.eclipse.emfforms.bazaar.BazaarContext)
	 */
	@Override
	public T createProduct(BazaarContext bazaarContext) {
		final IEclipseContext eclipseContext = createEclipseContextWithFunctions(bazaarContext);
		final Vendor<? extends T> bestVendor = getBestVendor(eclipseContext);
		if (bestVendor == null) {
			return null;
		}
		final T createProduct = createProduct(bestVendor, eclipseContext);
		return createProduct;
	}

	private IEclipseContext createEclipseContextWithFunctions(BazaarContext bazaarContext) {
		IEclipseContext eclipseContext = createEclipseContext(bazaarContext);
		eclipseContext = addContextFunctions(eclipseContext);
		return eclipseContext;
	}

	/**
	 *
	 * @see org.eclipse.emfforms.bazaar.Bazaar#createProducts(org.eclipse.emfforms.bazaar.BazaarContext)
	 */
	@Override
	public List<T> createProducts(BazaarContext bazaarContext) {
		final IEclipseContext eclipseContext = createEclipseContextWithFunctions(bazaarContext);
		final Queue<Vendor<? extends T>> vendorQueue = createVendorPriorityQueue(eclipseContext);
		if (vendorQueue.isEmpty()) {
			return Collections.emptyList();
		}

		final List<T> ret = new ArrayList<T>();
		for (Vendor<? extends T> vendor = vendorQueue.poll(); vendor != null; vendor = vendorQueue.poll()) {
			final T createProduct = createProduct(vendor, eclipseContext);
			if (createProduct != null) {
				ret.add(createProduct);
			}
		}

		return ret;
	}

	/**
	 * Checks the {@link Precondition}s for a given vendor.
	 *
	 * @param vendor The {@link Vendor} to check the {@link Precondition} for.
	 * @param context The {@link IEclipseContext} to provide the {@link Precondition}
	 * @return whether the {@link Precondition}s are fulfilled
	 */
	public boolean checkPreConditions(Vendor<? extends T> vendor, IEclipseContext context) {
		final Set<Precondition> preConditionList = new LinkedHashSet<Precondition>();
		final Precondition preConditionAnnotation = vendor.getClass().getAnnotation(Precondition.class);
		if (preConditionAnnotation != null) {
			preConditionList.add(preConditionAnnotation);
		}
		final Preconditions conditions = vendor.getClass().getAnnotation(Preconditions.class);
		if (conditions != null) {
			preConditionList.addAll(Arrays.asList(conditions.preconditions()));
		}
		for (final Precondition preCondition : preConditionList) {
			if (!checkPreCondition(preCondition, vendor, context)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkPreCondition(Precondition preCondition, Vendor<? extends T> vendor, IEclipseContext context) {
		if (preCondition.value().equals(Precondition.UNASSIGNED)) {
			return context.containsKey(preCondition.key());
		}
		final Object object = context.get(preCondition.key());
		if (object == null) {
			return false;
		}
		return object.equals(preCondition.value());
	}

}
