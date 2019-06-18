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
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.context;

import java.util.List;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

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
	 * {@link #areEqual(VDomainModelReference,EObject, VDomainModelReference,EObject)}.
	 *
	 * @param left the first VControl to compare
	 * @param leftDomainModel The domain model of the left VControl
	 * @param right the second VControl to compare
	 * @param rightDomainModel The domain model of the right VControl
	 * @return true if all values of the {@link VDomainModelReference VDomainModelReferences} are equal
	 */
	public static boolean areEqual(VControl left, EObject leftDomainModel, VControl right, EObject rightDomainModel) {
		return areEqual(left.getDomainModelReference(), leftDomainModel, right.getDomainModelReference(),
			rightDomainModel);
	}

	/**
	 * Compares to {@link VDomainModelReference VDomainModelReferences} by comparing all values.
	 *
	 * @param left the first {@link VDomainModelReference}
	 * @param leftDomainModel The domain model of the left VControl
	 * @param right the second {@link VDomainModelReference}
	 * @param rightDomainModel The domain model of the right VControl
	 * @return true if all values of the {@link VDomainModelReference VDomainModelReferences} are equal
	 */
	public static boolean areEqual(VDomainModelReference left, EObject leftDomainModel, VDomainModelReference right,
		EObject rightDomainModel) {
		@SuppressWarnings("rawtypes")
		IObservableValue leftObservableValue;
		@SuppressWarnings("rawtypes")
		IObservableValue rightObservableValue;

		try {
			leftObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(left, leftDomainModel);
			rightObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(right, rightDomainModel);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return false;
		}
		final EObject leftEObject = (EObject) ((IObserving) leftObservableValue).getObserved();
		final EStructuralFeature leftStructuralFeature = (EStructuralFeature) leftObservableValue.getValueType();
		final EObject rightEObject = (EObject) ((IObserving) rightObservableValue).getObserved();
		final EStructuralFeature rightStructuralFeature = (EStructuralFeature) rightObservableValue.getValueType();

		leftObservableValue.dispose();
		rightObservableValue.dispose();

		final Object leftValue = leftEObject.eGet(leftStructuralFeature, true);
		final Object rightValue = rightEObject.eGet(rightStructuralFeature, true);

		return areValuesEqual(leftValue, leftStructuralFeature, rightValue, rightStructuralFeature);
	}

	/**
	 * Compares two {@link EStructuralFeature EStructuralFeatures} and their values.
	 * If both are null, they are equal. For a single reference, the referenced EObjects are compare (equals). For a
	 * "many" references, a list entries are compared.
	 *
	 * @param leftValue the value of the left {@link EStructuralFeature}
	 * @param leftStructuralFeature the left EStructuralFeature
	 * @param rightValue the value of the right EStructuralFeature
	 * @param rightStructuralFeature the right EStructuralFeature
	 * @return whether the values are equal
	 */
	@SuppressWarnings("unchecked")
	public static boolean areValuesEqual(final Object leftValue, final EStructuralFeature leftStructuralFeature,
		final Object rightValue,
		final EStructuralFeature rightStructuralFeature) {
		if (leftValue == null && rightValue == null) {
			return true;
		}
		if (leftValue == null || rightValue == null) {
			return false;
		}
		if (leftValue.equals(rightValue)) {
			return true;
		}

		if (isSingleReferences(leftStructuralFeature)
			&& isSingleReferences(rightStructuralFeature)) {
			return EcoreUtil.equals(EObject.class.cast(leftValue), EObject.class.cast(rightValue));
		}
		if (isMultiReference(leftStructuralFeature) && isMultiReference(rightStructuralFeature)) {
			return EcoreUtil.equals((List<EObject>) leftValue, (List<EObject>) rightValue);
		}
		return false;
	}

	/**
	 * @param leftStructuralFeature
	 * @return
	 */
	private static boolean isMultiReference(EStructuralFeature leftStructuralFeature) {
		return EcorePackage.eINSTANCE.getEReference().isInstance(leftStructuralFeature)
			&& leftStructuralFeature.isMany();
	}

	private static boolean isSingleReferences(final EStructuralFeature leftStructuralFeature) {
		return EcorePackage.eINSTANCE.getEReference().isInstance(leftStructuralFeature)
			&& !leftStructuralFeature.isMany();
	}

}
