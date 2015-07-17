/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.edit.provider;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;

/**
 * @author Alexandra Buzila
 *
 */
public class CustomReflectiveItemProvider extends ReflectiveItemProvider

{
	/**
	 * A custom reflective item provider.
	 *
	 * @param adapterFactory the parent {@link AdapterFactory}
	 */
	public CustomReflectiveItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		super.getPropertyDescriptors(object);

		for (final EStructuralFeature eFeature : ((EObject) object).eClass().getEAllStructuralFeatures()) {
			if (eFeature instanceof EReference && ((EReference) eFeature).isContainment()) {
				itemPropertyDescriptors.add
					(new ItemPropertyDescriptor
					(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getFeatureText(eFeature),
						getResourceLocator().getString
							("_UI_Property_description", //$NON-NLS-1$
								new Object[] { getFeatureText(eFeature), eFeature.getEType().getName() }),
						eFeature,
						eFeature.isChangeable(),
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE));
			}
		}
		return itemPropertyDescriptors;
	}
}
