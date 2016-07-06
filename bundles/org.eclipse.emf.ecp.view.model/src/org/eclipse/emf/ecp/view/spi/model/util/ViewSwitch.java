/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method
 * for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch.
 *
 * @since 1.2 <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage
 * @generated
 */
public class ViewSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static VViewPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ViewSwitch() {
		if (modelPackage == null) {
			modelPackage = VViewPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
	 * result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case VViewPackage.DIAGNOSTIC: {
			VDiagnostic diagnostic = (VDiagnostic) theEObject;
			T result = caseDiagnostic(diagnostic);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.ATTACHMENT: {
			VAttachment attachment = (VAttachment) theEObject;
			T result = caseAttachment(attachment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.DOMAIN_MODEL_REFERENCE: {
			VDomainModelReference domainModelReference = (VDomainModelReference) theEObject;
			T result = caseDomainModelReference(domainModelReference);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.ELEMENT: {
			VElement element = (VElement) theEObject;
			T result = caseElement(element);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.VIEW: {
			VView view = (VView) theEObject;
			T result = caseView(view);
			if (result == null)
				result = caseElement(view);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.CONTAINED_ELEMENT: {
			VContainedElement containedElement = (VContainedElement) theEObject;
			T result = caseContainedElement(containedElement);
			if (result == null)
				result = caseElement(containedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.CONTAINER: {
			VContainer container = (VContainer) theEObject;
			T result = caseContainer(container);
			if (result == null)
				result = caseElement(container);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.CONTAINED_CONTAINER: {
			VContainedContainer containedContainer = (VContainedContainer) theEObject;
			T result = caseContainedContainer(containedContainer);
			if (result == null)
				result = caseContainedElement(containedContainer);
			if (result == null)
				result = caseContainer(containedContainer);
			if (result == null)
				result = caseElement(containedContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.CONTROL: {
			VControl control = (VControl) theEObject;
			T result = caseControl(control);
			if (result == null)
				result = caseContainedElement(control);
			if (result == null)
				result = caseElement(control);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES: {
			VViewModelLoadingProperties viewModelLoadingProperties = (VViewModelLoadingProperties) theEObject;
			T result = caseViewModelLoadingProperties(viewModelLoadingProperties);
			if (result == null)
				result = caseViewModelProperties(viewModelLoadingProperties);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.STRING_TO_OBJECT_MAP_ENTRY: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Object> stringToObjectMapEntry = (Map.Entry<String, Object>) theEObject;
			T result = caseStringToObjectMapEntry(stringToObjectMapEntry);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.VIEW_MODEL_PROPERTIES: {
			VViewModelProperties viewModelProperties = (VViewModelProperties) theEObject;
			T result = caseViewModelProperties(viewModelProperties);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT: {
			VDateTimeDisplayAttachment dateTimeDisplayAttachment = (VDateTimeDisplayAttachment) theEObject;
			T result = caseDateTimeDisplayAttachment(dateTimeDisplayAttachment);
			if (result == null)
				result = caseAttachment(dateTimeDisplayAttachment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.DOMAIN_MODEL_REFERENCE_SEGMENT: {
			VDomainModelReferenceSegment domainModelReferenceSegment = (VDomainModelReferenceSegment) theEObject;
			T result = caseDomainModelReferenceSegment(domainModelReferenceSegment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT: {
			VFeatureDomainModelReferenceSegment featureDomainModelReferenceSegment = (VFeatureDomainModelReferenceSegment) theEObject;
			T result = caseFeatureDomainModelReferenceSegment(featureDomainModelReferenceSegment);
			if (result == null)
				result = caseDomainModelReferenceSegment(featureDomainModelReferenceSegment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElement(VElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Diagnostic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagnostic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiagnostic(VDiagnostic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>View</em>'. <!-- begin-user-doc --> This
	 * implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseView(VView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contained Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contained Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainedElement(VContainedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainer(VContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contained Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contained Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainedContainer(VContainedContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Control</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControl(VControl object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Loading Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Loading Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewModelLoadingProperties(VViewModelLoadingProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String To Object Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String To Object Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringToObjectMapEntry(Map.Entry<String, Object> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Properties</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Properties</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseViewModelProperties(VViewModelProperties object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Display Attachment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Display Attachment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDateTimeDisplayAttachment(VDateTimeDisplayAttachment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Model Reference Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Model Reference Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainModelReferenceSegment(VDomainModelReferenceSegment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Domain Model Reference
	 * Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * 
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Domain Model Reference
	 *         Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureDomainModelReferenceSegment(VFeatureDomainModelReferenceSegment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Model Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainModelReference(VDomainModelReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attachment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attachment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttachment(VAttachment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // ViewSwitch
