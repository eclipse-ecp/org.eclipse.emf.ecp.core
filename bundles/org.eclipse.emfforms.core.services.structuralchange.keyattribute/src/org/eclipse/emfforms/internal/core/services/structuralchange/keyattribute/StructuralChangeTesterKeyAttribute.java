/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange.keyattribute;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link StructuralChangeTesterInternal internal structural change tester} for
 * {@link VKeyAttributeDomainModelReference VKeyAttributeDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "StructuralChangeTesterKeyAttribute")
public class StructuralChangeTesterKeyAttribute implements StructuralChangeTesterInternal {

	private ServiceReference<EMFFormsStructuralChangeTester> emfFormsStructuralChangeTesterServiceReference;
	private BundleContext bundleContext;
	private EMFFormsStructuralChangeTester emfFormsStructuralChangeTester;
	private EMFFormsDatabindingEMF emfFormsDatabindingEMF;

	/**
	 * Called by the OSGI framework to set the {@link EMFFormsDatabindingEMF}.
	 *
	 * @param emfFormsDatabindingEMF The {@link EMFFormsDatabindingEMF} to set
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabindingEMF(EMFFormsDatabindingEMF emfFormsDatabindingEMF) {
		this.emfFormsDatabindingEMF = emfFormsDatabindingEMF;
	}

	/**
	 * Only needed for testing. The {@link EMFFormsStructuralChangeTester} is not set by the OSGI framework.
	 *
	 * @param emfFormsStructuralChangeTester The {@link EMFFormsStructuralChangeTester} to set.
	 */
	void setEMFFormsStructuralChangeTester(EMFFormsStructuralChangeTester emfFormsStructuralChangeTester) {
		this.emfFormsStructuralChangeTester = emfFormsStructuralChangeTester;
	}

	/**
	 * Called by the framework when the component gets activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by the framework when the component gets deactivated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (emfFormsStructuralChangeTesterServiceReference != null) {
			bundleContext.ungetService(emfFormsStructuralChangeTesterServiceReference);
			emfFormsStructuralChangeTester = null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (reference instanceof VKeyAttributeDomainModelReference) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification) {
		Assert.create(reference).notNull();
		Assert.create(domainRootObject).notNull();
		Assert.create(notification).notNull();

		if (notification.getRawNotification().isTouch()) {
			return false;
		}

		final VKeyAttributeDomainModelReference keyAttributeDMR = VKeyAttributeDomainModelReference.class
			.cast(reference);

		boolean relevantChange = false;
		EObject lastResolvedEObject = domainRootObject;
		for (final EReference eReference : keyAttributeDMR.getDomainModelEReferencePath()) {
			if (lastResolvedEObject == null) {
				return false;
			}
			relevantChange |= eReference.equals(notification.getStructuralFeature())
				&& lastResolvedEObject == notification.getNotifier();
			if (relevantChange) {
				return true;
			}
			lastResolvedEObject = (EObject) lastResolvedEObject.eGet(eReference);
		}

		@SuppressWarnings("unchecked")
		final EList<EObject> list = (EList<EObject>) lastResolvedEObject.eGet(keyAttributeDMR.getDomainModelEFeature());

		EObject keyEObject = null;
		// If it exists, find the EObject that contains the key.
		for (int i = 0; i < list.size(); i++) {
			final EObject eObject = list.get(i);
			Setting setting;
			try {
				setting = emfFormsDatabindingEMF.getSetting(keyAttributeDMR.getKeyDMR(), eObject);
			} catch (final DatabindingFailedException ex) {
				// TODO report needed?
				continue;
			}

			if (isRelevantKeyChange(keyAttributeDMR, setting, notification)) {
				return true;
			}
			if (keyAttributeDMR.getKeyValue().equals(setting.get(true))) {
				// key was present in the current EObject
				keyEObject = eObject;
			}
		}

		if (keyEObject != null) {
			relevantChange = getEMFFormsStructuralChangeTester().isStructureChanged(keyAttributeDMR.getValueDMR(),
				keyEObject, notification);

		}
		return relevantChange;
	}

	/**
	 * Checks whether the given key setting was changed in a relevant way.
	 *
	 * @param keyAttributeDMR The {@link VKeyAttributeDomainModelReference} that defines the key
	 * @param setting The key {@link Setting}
	 * @param notification The {@link ModelChangeNotification} indicating the change
	 * @return whether the given key setting was changed in a relevant way
	 */
	private boolean isRelevantKeyChange(final VKeyAttributeDomainModelReference keyAttributeDMR, Setting setting,
		ModelChangeNotification notification) {
		final Object keyValue = keyAttributeDMR.getKeyValue();
		if (notification.getStructuralFeature().equals(setting.getEStructuralFeature())
			&& notification.getNotifier().equals(setting.getEObject())) {
			// a key was changed
			if (keyValue.equals(notification.getRawNotification().getOldValue())
				|| keyValue.equals(notification.getRawNotification().getNewValue())) {
				// The key change was significant.
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the {@link EMFFormsStructuralChangeTester}. It is not set by the OSGI framework to avoid circular
	 * dependencies.
	 *
	 * @return The {@link EMFFormsStructuralChangeTester}
	 */
	private EMFFormsStructuralChangeTester getEMFFormsStructuralChangeTester() {
		if (emfFormsStructuralChangeTester == null) {
			emfFormsStructuralChangeTesterServiceReference = bundleContext
				.getServiceReference(EMFFormsStructuralChangeTester.class);
			if (emfFormsStructuralChangeTesterServiceReference == null) {
				throw new IllegalStateException("No EMFFormsStructuralChangeTester available!"); //$NON-NLS-1$
			}
			emfFormsStructuralChangeTester = bundleContext.getService(emfFormsStructuralChangeTesterServiceReference);
		}
		return emfFormsStructuralChangeTester;
	}
}
