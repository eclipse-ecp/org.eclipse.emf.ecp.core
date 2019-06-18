/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * An ECP ECPSubstitutionLabelProvider used in the validation context.
 *
 * @author Eugen Neufeld
 *
 */
public final class ECPSubstitutionLabelProvider implements EValidator.SubstitutionLabelProvider {

	private final AdapterFactory factory;
	private final AdapterFactoryItemDelegator adapterFactoryItemDelegator;

	/**
	 * Creates an {@link ECPSubstitutionLabelProvider}.
	 *
	 * @param factory the {@link AdapterFactory} to use
	 */
	public ECPSubstitutionLabelProvider(AdapterFactory factory) {
		this.factory = factory;
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(factory);
	}

	@Override
	public String getObjectLabel(EObject eObject) {
		final Object provider = factory.adapt(
			eObject,
			IItemLabelProvider.class);
		if (provider == null || !IItemLabelProvider.class.isInstance(provider)) {
			return EcoreUtil.getIdentification(eObject);
		}
		return IItemLabelProvider.class.cast(provider).getText(eObject);
	}

	@Override
	public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
		final EClass eClass = eStructuralFeature.getEContainingClass();
		if (eClass.isInterface() || eClass.isAbstract()) {
			return eStructuralFeature.getName();
		}
		EObject tempInstance;
		if (eClass.getInstanceClass() != null) {
			tempInstance = EcoreUtil.create(eClass);
		} else {
			tempInstance = new DynamicEObjectImpl(eClass);
		}
		final IItemPropertyDescriptor itemPropertyDescriptor = adapterFactoryItemDelegator.getPropertyDescriptor(
			tempInstance, eStructuralFeature);

		if (itemPropertyDescriptor == null) {
			return eStructuralFeature.getName();
		}
		return itemPropertyDescriptor.getDisplayName(eStructuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getValueLabel(org.eclipse.emf.ecore.EDataType,
	 *      java.lang.Object)
	 */
	@Override
	public String getValueLabel(EDataType eDataType, Object value) {
		return EcoreUtil.convertToString(eDataType, value);
	}
}