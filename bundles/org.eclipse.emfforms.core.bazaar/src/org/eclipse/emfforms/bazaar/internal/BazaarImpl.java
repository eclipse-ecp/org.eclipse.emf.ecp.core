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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

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
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * Implementation of a {@link Bazaar} using the {@link IEclipseContext} for dependency injection.
 *
 * @param <T> the type of ware produced by this {@link Bazaar}
 * @author jonas
 *
 */
public class BazaarImpl<T> implements Bazaar<T> {

	private final Set<Vendor<T>> mountebanks = new LinkedHashSet<Vendor<T>>();

	private PriorityOverlapCallBack<T> priorityOverlapCallBack;

	private final Map<String, BazaarContextFunction> contextFunctionMap = new HashMap<String, BazaarContextFunction>();

	@Override
	public void addVendor(Vendor<T> mountebank) {
		mountebanks.add(mountebank);
	}

	/**
	 * Returns the ware which is provided with the highest priority by any {@link Vendor}.
	 *
	 * @return T
	 */
	public T createWare(Vendor<T> mountebank, IEclipseContext context) {
		return (T) ContextInjectionFactory.invoke(mountebank, Create.class, context);
	}

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

	public IEclipseContext createEclipseContext(BazaarContext bazaarContext) {
		final IEclipseContext context = EclipseContextFactory.create();
		final Set<String> keySet = bazaarContext.getContextMap().keySet();
		for (final String string : keySet) {
			context.set(string, bazaarContext.getContextMap().get(string));
		}
		return context;
	}

	/**
	 * @param context
	 * @return
	 */
	public Vendor<T> getBestMountebank(IEclipseContext context) {
		final SortedMap<Double, Vendor<T>> priorityMap = createVendorPriorityMap(context);

		if (priorityMap.isEmpty()) {
			return null;
		}
		return priorityMap.get(priorityMap.lastKey());
	}

	private SortedMap<Double, Vendor<T>> createVendorPriorityMap(IEclipseContext context) {
		final SortedMap<Double, Vendor<T>> priorityMap = new TreeMap<Double, Vendor<T>>();
		for (final Vendor<T> mountebank : mountebanks) {
			try {
				final double bid = (Double) ContextInjectionFactory.invoke(mountebank, Bid.class, context);
				final Vendor<T> existingMounteBankWithSamePriority = priorityMap.get(bid);
				if (existingMounteBankWithSamePriority != null) {
					reportPriorityOverlap(existingMounteBankWithSamePriority, mountebank);
				}
				priorityMap.put(bid, mountebank);
			} catch (final InjectionException e) {
				// Do Nothing
			}
		}
		return priorityMap;
	}

	/**
	 * @param existingMounteBankWithSamePriority
	 * @param mountebank
	 */
	private void reportPriorityOverlap(Vendor<T> existingMounteBankWithSamePriority, Vendor<T> mountebank) {
		if (priorityOverlapCallBack == null) {
			return;
		}
		priorityOverlapCallBack.priorityOverlap(existingMounteBankWithSamePriority, mountebank);

	}

	@Override
	public void setPriorityOverlapCallBack(PriorityOverlapCallBack<T> priorityOverlapCallBack) {
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
	 * @see org.eclipse.emfforms.bazaar.Bazaar#createWare(org.eclipse.emfforms.bazaar.BazaarContext)
	 */
	@Override
	public T createWare(BazaarContext bazaarContext) {
		final IEclipseContext eclipseContext = createEclipseContextWithFunctions(bazaarContext);
		final Vendor<T> bestMountebank = getBestMountebank(eclipseContext);
		if (bestMountebank == null) {
			return null;
		}
		final T createWare = createWare(bestMountebank, eclipseContext);
		return createWare;
	}

	private IEclipseContext createEclipseContextWithFunctions(BazaarContext bazaarContext) {
		IEclipseContext eclipseContext = createEclipseContext(bazaarContext);
		eclipseContext = addContextFunctions(eclipseContext);
		return eclipseContext;
	}

	/**
	 *
	 * @see org.eclipse.emfforms.bazaar.Bazaar#createWares(org.eclipse.emfforms.bazaar.BazaarContext)
	 */
	@Override
	public List<T> createWares(BazaarContext bazaarContext) {
		final List<T> ret = new ArrayList<T>();
		final IEclipseContext eclipseContext = createEclipseContextWithFunctions(bazaarContext);
		final SortedMap<Double, Vendor<T>> vendorPriorityMap = createVendorPriorityMap(eclipseContext);
		for (final Entry<Double, Vendor<T>> entry : vendorPriorityMap.entrySet()) {
			final Vendor<T> vendor = entry.getValue();
			final T createWare = createWare(vendor, eclipseContext);
			if (createWare != null) {
				ret.add(createWare);
			}
		}
		return ret;
	}

}
