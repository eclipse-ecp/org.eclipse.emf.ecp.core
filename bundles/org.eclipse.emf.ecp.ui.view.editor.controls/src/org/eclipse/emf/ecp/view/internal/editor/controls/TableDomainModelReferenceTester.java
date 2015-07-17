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
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * @author Eugen
 *
 */
public class TableDomainModelReferenceTester implements org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.edit.provider.IItemPropertyDescriptor,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public int isApplicable(IItemPropertyDescriptor itemPropertyDescriptor, EObject eObject) {
		return isApplicable(eObject, (EStructuralFeature) itemPropertyDescriptor.getFeature(eObject));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public int isApplicable(VDomainModelReference domainModelReference) {
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.util.ECPApplicableTester#isApplicable(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature) {
		if (VTableDomainModelReference.class.isInstance(eObject.eContainer())
			&& VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature() == eStructuralFeature) {
			return 4;
		}
		return NOT_APPLICABLE;
	}
}
