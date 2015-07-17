/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getObjectLabel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String getObjectLabel(EObject eObject)
	{
		final IItemLabelProvider provider =
			(IItemLabelProvider) factory.adapt(
				eObject,
				IItemLabelProvider.class);
		if (provider == null) {
			return EcoreUtil.getIdentification(eObject);
		}
		return provider.getText(eObject);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider#getFeatureLabel(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public String getFeatureLabel(EStructuralFeature eStructuralFeature)
	{
		final EClass eClass = eStructuralFeature.getEContainingClass();
		if (eClass.isInterface() || eClass.isAbstract()) {
			return eStructuralFeature.getName();
		}
		final EObject tempInstance = EcoreUtil.create(eClass);
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
	public String getValueLabel(EDataType eDataType, Object value)
	{
		return EcoreUtil.convertToString(eDataType, value);
	}
}