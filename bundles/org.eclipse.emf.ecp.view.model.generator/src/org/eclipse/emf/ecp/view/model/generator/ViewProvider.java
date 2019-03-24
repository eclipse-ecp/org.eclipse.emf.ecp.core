/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.generator;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * View Provider.
 */
public class ViewProvider implements IViewProvider {

	@Override
	public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setUuid(generateId(eObject.eClass(), null));
		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(
			new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator delegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		for (final EStructuralFeature feature : getValidFeatures(delegator, eObject)) {
			final VControl control;
			if (isTableFeature(feature)) {
				control = VTableFactory.eINSTANCE.createTableControl();
				final VTableDomainModelReference tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
				tableDmr.setDomainModelReference(createModelReference(feature));
				control.setDomainModelReference(tableDmr);
			} else {
				control = VViewFactory.eINSTANCE.createControl();
				control.setDomainModelReference(createModelReference(feature));
			}
			control.setReadonly(isReadOnly(delegator, eObject, feature));
			control.setUuid(generateId(eObject.eClass(), feature));
			view.getChildren().add(control);
		}
		composedAdapterFactory.dispose();
		view.setRootEClass(eObject.eClass());
		view.setLoadingProperties(EcoreUtil.copy(properties));
		return view;
	}

	private VDomainModelReference createModelReference(final EStructuralFeature feature) {
		final VFeaturePathDomainModelReference modelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		modelReference.setDomainModelEFeature(feature);
		return modelReference;
	}

	private boolean isTableFeature(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			final EReference ref = (EReference) feature;
			return ref.isMany() && ref.isContainment();
		}
		return false;
	}

	private boolean isReadOnly(AdapterFactoryItemDelegator delegator,
		EObject owner, EStructuralFeature feature) {
		if (!feature.isChangeable()) {
			return true;
		}
		final IItemPropertyDescriptor descriptor = delegator.getPropertyDescriptor(owner,
			feature);
		return !descriptor.canSetProperty(feature);
	}

	private boolean isInvalidFeature(EStructuralFeature feature) {
		return isContainerReference(feature) || isTransient(feature) || isVolatile(feature);
	}

	private boolean isContainerReference(EStructuralFeature feature) {
		if (feature instanceof EReference) {
			final EReference reference = (EReference) feature;
			if (reference.isContainer()) {
				return true;
			}
		}

		return false;
	}

	private boolean isTransient(EStructuralFeature feature) {
		return feature.isTransient();
	}

	private boolean isVolatile(EStructuralFeature feature) {
		return feature.isVolatile();
	}

	private Set<EStructuralFeature> getValidFeatures(
		AdapterFactoryItemDelegator itemDelegator, EObject eObject) {
		final Collection<EStructuralFeature> features = eObject.eClass()
			.getEAllStructuralFeatures();
		final Set<EStructuralFeature> featuresToAdd = new LinkedHashSet<EStructuralFeature>();
		IItemPropertyDescriptor propertyDescriptor = null;
		for (final EStructuralFeature feature : features) {
			propertyDescriptor = itemDelegator
				.getPropertyDescriptor(eObject, feature);
			if (propertyDescriptor == null || isInvalidFeature(feature)) {
				continue;
			}

			featuresToAdd.add(feature);

		}
		return featuresToAdd;
	}

	// TODO this is not unique, because of the use of hashCode, but maybe good enough?
	private static String generateId(EClass eClass, EStructuralFeature feature) {
		final StringBuilder stringBuilder = new StringBuilder();
		final EPackage ePackage = eClass.getEPackage();
		if (ePackage != null) {
			/* might be null with dynamic emf */
			stringBuilder.append(ePackage.getNsURI());
		}
		stringBuilder.append("#"); //$NON-NLS-1$
		stringBuilder.append(eClass.getName());
		if (feature != null) {
			stringBuilder.append("#"); //$NON-NLS-1$
			stringBuilder.append(feature.getName());
		}
		return String.valueOf(stringBuilder.toString().hashCode());
	}

	@Override
	public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		return 1d;
	}
}
