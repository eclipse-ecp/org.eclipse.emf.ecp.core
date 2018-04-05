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
 * Lucas Koehler - refactored common bazaar functionality out
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategyUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.bazaar.BazaarUtil;
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

	private final Bazaar<EObjectSelectionStrategy> eobjectSelectionStrategyBazaar = BazaarUtil.createBazaar(
		EObjectSelectionStrategy.NULL);
	private final Bazaar<CreateNewModelElementStrategy> createNewModelElementStrategyBazaar = BazaarUtil.createBazaar(
		CreateNewModelElementStrategy.DEFAULT);
	private final Bazaar<AttachmentStrategy> attachmentStrategyBazaar = BazaarUtil
		.createBazaar(AttachmentStrategy.DEFAULT);
	private final Bazaar<ReferenceStrategy> referenceStrategyBazaar = BazaarUtil
		.createBazaar(ReferenceStrategy.DEFAULT);
	private final Bazaar<OpenInNewContextStrategy> openInNewContextStrategyBazaar = BazaarUtil.createBazaar(
		OpenInNewContextStrategy.DEFAULT);

	/**
	 * Initializes me.
	 */
	public DefaultReferenceServiceFactory() {
		super();
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
	 * Add a create new model element strategy provider.
	 *
	 * @param provider the provider to add
	 */
	@Reference(cardinality = MULTIPLE, policy = DYNAMIC)
	void addCreateNewModelElementStrategyProvider(CreateNewModelElementStrategy.Provider provider) {
		createNewModelElementStrategyBazaar.addVendor(provider);
	}

	/**
	 * Remove a create new model element strategy provider.
	 *
	 * @param provider the provider to remove
	 */
	void removeCreateNewModelElementStrategyProvider(CreateNewModelElementStrategy.Provider provider) {
		createNewModelElementStrategyBazaar.removeVendor(provider);
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
			drs.setCreateNewModelElementStrategy(createDynamicCreateNewModelElementStrategy());
			drs.setEObjectSelectionStrategy(createDynamicEObjectSelectionStrategy());
			drs.setOpenStrategy(createDynamicOpenInNewContextStrategy());
			drs.setReferenceStrategy(createDynamicReferenceStrategy());

			drs.instantiate((ViewModelContext) emfFormsViewContext);

			return drs;
		}
		throw new IllegalStateException("The provided context is not a ViewModelContext."); //$NON-NLS-1$
	}

	private AttachmentStrategy createDynamicAttachmentStrategy() {
		return new AttachmentStrategy() {

			@Override
			public boolean addElementToModel(EObject owner, EReference reference, EObject object) {
				final AttachmentStrategy delegate = attachmentStrategyBazaar.createProduct(
					ReferenceStrategyUtil.createBazaarContext(context, owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.addElementToModel(owner, reference, object);
			}
		};
	}

	private CreateNewModelElementStrategy createDynamicCreateNewModelElementStrategy() {
		return new CreateNewModelElementStrategy() {

			@Override
			public Optional<EObject> createNewModelElement(EObject owner, EReference reference) {
				final CreateNewModelElementStrategy delegate = createNewModelElementStrategyBazaar
					.createProduct(ReferenceStrategyUtil.createBazaarContext(context, owner, reference));
				if (delegate == null) {
					return Optional.empty();
				}
				return delegate.createNewModelElement(owner, reference);
			}
		};
	}

	private ReferenceStrategy createDynamicReferenceStrategy() {
		return new ReferenceStrategy() {

			@Override
			public boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects) {
				final ReferenceStrategy delegate = referenceStrategyBazaar.createProduct(
					ReferenceStrategyUtil.createBazaarContext(context, owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.addElementsToReference(owner, reference, objects);
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
					ReferenceStrategyUtil.createBazaarContext(context, owner, reference));
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
					ReferenceStrategyUtil.createBazaarContext(context, owner, reference));
				if (delegate == null) {
					return false;
				}
				return delegate.openInNewContext(owner, reference, object);
			}
		};
	}

}
