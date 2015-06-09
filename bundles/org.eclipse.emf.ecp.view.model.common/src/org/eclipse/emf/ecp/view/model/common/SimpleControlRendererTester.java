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
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.model.common.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * Tester for Control Renderer.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class SimpleControlRendererTester implements ECPRendererTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(control.getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) observableValue.getValueType();
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		observableValue.dispose();
		// if the feature is a multiValue and the description is a singlevalue continue
		if (isSingleValue() == feature.isMany()) {
			return NOT_APPLICABLE;
		}
		// if we have an attribute
		if (EAttribute.class.isInstance(feature)) {
			if (checkAttributeInvalid((EAttribute) feature)) {
				return NOT_APPLICABLE;
			}
		}
		// if we have an reference the the classes
		else if (EReference.class.isInstance(feature)) {
			Class<?> instanceClass = feature.getEType().getInstanceClass();
			if (instanceClass == null) {
				instanceClass = EObject.class;
			}
			if (!getSupportedClassType().isAssignableFrom(instanceClass)) {
				return NOT_APPLICABLE;
			}
		}
		if (!checkFeatureETypeAnnotations(feature.getEType().getEAnnotations())) {
			return NOT_APPLICABLE;
		}
		// if the supported eobject is assignable from the current eobject and the supported feature is eitehr null or
		// equals the current one
		if (getSupportedEObject().isInstance(eObject)
			&& (getSupportedFeature() == null || feature.equals(getSupportedFeature()))) {
			return getPriority();
		}
		return NOT_APPLICABLE;
	}

	/**
	 * Allows to check the {@link EAnnotation EAnnotations} of the {@link EStructuralFeature#getEType() feature's type}.
	 *
	 * @param eAnnotations the annotations
	 * @return <code>true</code> if applicable, <code>false</code> otherwise
	 * @since 1.5
	 */
	protected boolean checkFeatureETypeAnnotations(EList<EAnnotation> eAnnotations) {
		return true;
	}

	private boolean checkAttributeInvalid(EAttribute attribute) {
		final Class<?> instanceClass = attribute.getEAttributeType().getInstanceClass();
		if (instanceClass == null) {
			return true;
		}
		if (Object.class.equals(getSupportedClassType())) {
			return false;
		}
		// if the attribute class is an primitive test the primitive types
		if (instanceClass.isPrimitive()) {
			try {
				final Class<?> primitive = (Class<?>) getSupportedClassType().getField("TYPE").get(null); //$NON-NLS-1$
				if (!primitive.equals(instanceClass)) {
					return true;
				}

			} catch (final IllegalArgumentException e) {
				return true;
			} catch (final SecurityException e) {
				return true;
			} catch (final IllegalAccessException e) {
				return true;
			} catch (final NoSuchFieldException e) {
				return true;
			}
		}
		// otherwise test the classes itself
		else if (!getSupportedClassType().isAssignableFrom(instanceClass)) {
			return true;
		}

		return false;
	}

	/**
	 * Whether the corresponding control is allowed only for single values.
	 *
	 * @return true if only a single value is allows
	 */
	protected abstract boolean isSingleValue();

	/**
	 * The static priority of the corresponding control.
	 *
	 * @return the priority
	 */
	protected abstract int getPriority();

	/**
	 * The eobejct which is supported by the corresponding control. Default is the {@link EObject}.
	 *
	 * @return the class of the supported eobejct
	 */
	protected Class<? extends EObject> getSupportedEObject() {
		return EObject.class;
	}

	/**
	 * The feature the corresponding control supports. Default is null to support all features.
	 *
	 * @return the supported feature
	 */
	protected EStructuralFeature getSupportedFeature() {
		return null;
	}

	/**
	 * The class of the type the corresponding control supports.
	 *
	 * @return the class of the supported type
	 */
	protected abstract Class<?> getSupportedClassType();
}
