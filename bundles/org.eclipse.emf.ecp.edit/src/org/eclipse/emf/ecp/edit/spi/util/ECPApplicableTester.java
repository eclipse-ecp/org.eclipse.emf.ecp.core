/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * This interface defines a {@link #isApplicable(IItemPropertyDescriptor, EObject)} method which is used to determine
 * the priority of a control. The control with the highest priority for a combination of an {@link EObject} and a
 * feature will be used.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public interface ECPApplicableTester {
	/**
	 * Return this whenever the control should not be drawn for the tested feature.
	 */
	int NOT_APPLICABLE = -1;

	/**
	 * Returns the priority of the corresponding control for the combination of the {@link EObject} and the
	 * {@link IItemPropertyDescriptor}.
	 *
	 * @param itemPropertyDescriptor the {@link IItemPropertyDescriptor} to test
	 * @param eObject the {@link EObject} to test
	 * @return {@link #NOT_APPLICABLE} if the corresponding control should not be used, a positivie integer value
	 *         otherwise. The control with the highest priority will be taken.
	 */
	@Deprecated
	int isApplicable(IItemPropertyDescriptor itemPropertyDescriptor, EObject eObject);

	/**
	 * Returns the priority of the corresponding control for the provided {@link VDomainModelReference}.
	 *
	 * @param domainModelReference the {@link VDomainModelReference} to test
	 * @return {@link #NOT_APPLICABLE} if the corresponding control should not be used, a positivie integer value
	 *         otherwise. The control with the highest priority will be taken.
	 * @since 1.2
	 * @deprecated Do not use. Use {@link #isApplicable(EObject, EStructuralFeature)} instead.
	 */
	@Deprecated
	int isApplicable(VDomainModelReference domainModelReference);

	/**
	 * Returns the priority of the corresponding control for the combination of the {@link EObject} and the
	 * {@link EStructuralFeature}.
	 *
	 * @param eStructuralFeature the {@link EStructuralFeature} to test
	 * @param eObject the {@link EObject} to test
	 * @return {@link #NOT_APPLICABLE} if the corresponding control should not be used, a positivie integer value
	 *         otherwise. The control with the highest priority will be taken.
	 * @since 1.2
	 */
	int isApplicable(EObject eObject, EStructuralFeature eStructuralFeature);
}
