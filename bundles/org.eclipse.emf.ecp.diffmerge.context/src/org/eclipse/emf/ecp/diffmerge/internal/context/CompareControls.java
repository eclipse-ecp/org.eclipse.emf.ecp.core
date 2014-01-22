/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.context;

import java.util.Iterator;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Class to compare Controls for equality.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class CompareControls {

	private CompareControls() {
	}

	/**
	 * Compares to {@link VControl VControls}. This is just a convenience method which delegates to
	 * {@link #areEqual(VDomainModelReference, VDomainModelReference)}.
	 * 
	 * @param left the first VControl to compare
	 * @param right the second VControl to compare
	 * @return true if all values of the {@link VDomainModelReference VDomainModelReferences} are equal
	 */
	public static boolean areEqual(VControl left, VControl right) {
		return areEqual(left.getDomainModelReference(), right.getDomainModelReference());
	}

	/**
	 * Compares to {@link VDomainModelReference VDomainModelReferences} by comparing all values.
	 * 
	 * @param left the first {@link VDomainModelReference}
	 * @param right the second {@link VDomainModelReference}
	 * @return true if all values of the {@link VDomainModelReference VDomainModelReferences} are equal
	 */
	public static boolean areEqual(VDomainModelReference left, VDomainModelReference right) {
		final Iterator<Setting> leftSettings = left.getIterator();
		final Iterator<Setting> rightSettings = right.getIterator();
		boolean leftHasNext = leftSettings.hasNext();
		boolean rightHasNext = rightSettings.hasNext();
		while (leftHasNext && rightHasNext) {
			final Setting leftSetting = leftSettings.next();
			final Setting rightSetting = rightSettings.next();
			final Object leftValue = leftSetting.get(true);
			final Object rightValue = rightSetting.get(true);
			leftHasNext = leftSettings.hasNext();
			rightHasNext = rightSettings.hasNext();

			if (leftValue == null && rightValue == null) {
				continue;
			}
			// TODO handle EReference (single and many)
			if (EcorePackage.eINSTANCE.getEReference().isInstance(leftSetting.getEStructuralFeature())
				|| EcorePackage.eINSTANCE.getEReference().isInstance(rightSetting.getEStructuralFeature())) {
				continue;
			}
			if (leftValue == null && rightValue != null) {
				return false;
			}
			if (leftValue != null && rightValue == null) {
				return false;
			}
			if (!leftValue.equals(rightValue)) {
				return false;
			}

		}
		// will be false if one iterator still has elements
		return !leftHasNext && !rightHasNext;
	}
}
