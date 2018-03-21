/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.StaticBid;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The Factory for creating the {@link DefaultReferenceService}.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
@Component
public class DefaultReferenceServiceFactory implements EMFFormsViewServiceFactory<ReferenceService> {

	private ComponentContext context;

	private final Bazaar<EObjectSelectionStrategy> eobjectSelectionStrategyBazaar = createBazaar(
		EObjectSelectionStrategy.NULL);
	private final Bazaar<EClassSelectionStrategy> eclassSelectionStrategyBazaar = createBazaar(
		EClassSelectionStrategy.NULL);
	private final Bazaar<AttachmentStrategy> attachmentStrategyBazaar = createBazaar(AttachmentStrategy.DEFAULT);
	private final Bazaar<ReferenceStrategy> referenceStrategyBazaar = createBazaar(ReferenceStrategy.DEFAULT);
	private final Bazaar<OpenInNewContextStrategy> openInNewContextStrategyBazaar = createBazaar(
		OpenInNewContextStrategy.DEFAULT);

	/**
	 * Initializes me.
	 */
	public DefaultReferenceServiceFactory() {
		super();
	}

	private static <T> Bazaar<T> createBazaar(T defaultStrategy) {
		final Bazaar.Builder<T> builder = builder(defaultStrategy);
		return builder.build();
	}

	private static <T> Bazaar.Builder<T> builder(final T defaultStrategy) {
		/**
		 * A vendor of the default, that tries to lose every bid.
		 */
		@StaticBid(bid = Double.NEGATIVE_INFINITY)
		class DefaultVendor implements Vendor<T> {
			/**
			 * Return the default strategy.
			 *
			 * @return the default strategy
			 */
			@Create
			public T createDefault() {
				return defaultStrategy;
			}
		}

		final Bazaar.Builder<T> result = Bazaar.Builder.empty();
		return result.threadSafe().add(new DefaultVendor());
	}

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		return EMFFormsViewServicePolicy.LAZY;
	}

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.LOCAL;
	}

	@Override
	public double getPriority() {
		return 1;
	}

	@Override
	public Class<ReferenceService> getType() {
		return ReferenceService.class;
	}

	/**
	 * Add an attachment strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addAttachmentStrategyProvider(AttachmentStrategy.Provider provider) {
		attachmentStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove an attachment strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeAttachmentStrategyProvider(AttachmentStrategy.Provider provider) {
		attachmentStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Add an open strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addOpenInNewContextStrategyProvider(OpenInNewContextStrategy.Provider provider) {
		openInNewContextStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove an open strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeOpenInNewContextStrategyProvider(OpenInNewContextStrategy.Provider provider) {
		openInNewContextStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Add an {@code EClass} selection strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addEClassSelectionStrategyProvider(EClassSelectionStrategy.Provider provider) {
		eclassSelectionStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove an {@code EClass} selection strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeEClassSelectionStrategyProvider(EClassSelectionStrategy.Provider provider) {
		eclassSelectionStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Add an {@code EObject} selection strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addEObjectSelectionStrategyProvider(EObjectSelectionStrategy.Provider provider) {
		eobjectSelectionStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove an {@code EObject} selection strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeEObjectSelectionStrategyProvider(EObjectSelectionStrategy.Provider provider) {
		eobjectSelectionStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Add a reference strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addReferenceStrategyProvider(ReferenceStrategy.Provider provider) {
		referenceStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove a reference strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeReferenceStrategyProvider(ReferenceStrategy.Provider provider) {
		referenceStrategyBazaar.removeVendor(provider);
	}

	/**
	 * Activates me.
	 *
	 * @param context my component context
	 *
	 * @since 1.16
	 */
	@Activate
	void activate(ComponentContext context) {
		this.context = context;
	}

	/**
	 * Deactivates me.
	 *
	 * @since 1.16
	 */
	@Deactivate
	void deactivate() {
		context = null;
	}

	@Override
	public ReferenceService createService(EMFFormsViewContext emfFormsViewContext) {
		if (emfFormsViewContext instanceof ViewModelContext) {
			final DefaultReferenceService drs = new DefaultReferenceService();

			// Inject customizations
			drs.setAttachmentStrategy(createDynamicAttachmentStrategy());
			drs.setEClassSelectionStrategy(createDynamicEClassSelectionStrategy());
			drs.setEObjectSelectionStrategy(createDynamicEObjectSelectionStrategy());
			drs.setOpenStrategy(createDynamicOpenInNewContextStrategy());
			drs.setReferenceStrategy(createDynamicReferenceStrategy());

			drs.instantiate((ViewModelContext) emfFormsViewContext);

			return drs;
		}
		throw new IllegalStateException("The provided context is not a ViewModelContext."); //$NON-NLS-1$
	}

	private BazaarContext getBazaarContext(EObject owner, EReference reference) {
		return baseContext(owner, reference).build();
	}

	private BazaarContext.Builder baseContext(EObject owner, EReference reference) {
		return BazaarContext.Builder.with(adapt(context.getProperties()))
			.put(EObject.class, owner)
			.put(EReference.class, reference);
	}

	/**
	 * Adapt a {@code dictionary} as a map.
	 *
	 * @param dictionary a dictionary
	 * @return the {@code dictionary}, as a map
	 */
	static Map<String, ?> adapt(Dictionary<String, ?> dictionary) {
		// The OSGi implementation of the read-only properties for all sorts of
		// things is a map
		if (dictionary instanceof Map<?, ?>) {
			@SuppressWarnings("unchecked")
			final Map<String, ?> result = (Map<String, ?>) dictionary;
			return result;
		}

		final Map<String, Object> result = new HashMap<String, Object>();
		for (final Enumeration<String> keys = dictionary.keys(); keys.hasMoreElements();) {
			final String next = keys.nextElement();
			result.put(next, dictionary.get(next));
		}
		return result;
	}

	private AttachmentStrategy createDynamicAttachmentStrategy() {
		return new AttachmentStrategy() {

			@Override
			public boolean addElementToModel(EObject owner, EReference reference, EObject object) {
				final AttachmentStrategy delegate = attachmentStrategyBazaar.createProduct(
					getBazaarContext(owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.addElementToModel(owner, reference, object);
			}
		};
	}

	private ReferenceStrategy createDynamicReferenceStrategy() {
		return new ReferenceStrategy() {

			@Override
			public boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects) {
				final ReferenceStrategy delegate = referenceStrategyBazaar.createProduct(
					getBazaarContext(owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.addElementsToReference(owner, reference, objects);
			}
		};
	}

	private EClassSelectionStrategy createDynamicEClassSelectionStrategy() {
		return new EClassSelectionStrategy() {

			@Override
			public Collection<EClass> collectEClasses(EObject owner, EReference reference,
				Collection<EClass> eclasses) {

				Collection<EClass> result = eclasses;

				final List<EClassSelectionStrategy> delegates = eclassSelectionStrategyBazaar.createProducts(
					getBazaarContext(owner, reference));
				for (final EClassSelectionStrategy next : delegates) {
					result = next.collectEClasses(owner, reference, result);
				}

				return result;
			}
		};
	}

	private EObjectSelectionStrategy createDynamicEObjectSelectionStrategy() {
		return new EObjectSelectionStrategy() {

			@Override
			public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
				Collection<EObject> existingObjects) {

				Collection<EObject> result = existingObjects;

				final List<EObjectSelectionStrategy> delegates = eobjectSelectionStrategyBazaar.createProducts(
					getBazaarContext(owner, reference));
				for (final EObjectSelectionStrategy next : delegates) {
					result = next.collectExistingObjects(owner, reference, result);
				}

				return result;
			}
		};
	}

	private OpenInNewContextStrategy createDynamicOpenInNewContextStrategy() {
		return new OpenInNewContextStrategy() {

			@Override
			public boolean openInNewContext(EObject owner, EReference reference, EObject object) {
				final OpenInNewContextStrategy delegate = openInNewContextStrategyBazaar.createProduct(
					getBazaarContext(owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.openInNewContext(owner, reference, object);
			}
		};
	}

}
