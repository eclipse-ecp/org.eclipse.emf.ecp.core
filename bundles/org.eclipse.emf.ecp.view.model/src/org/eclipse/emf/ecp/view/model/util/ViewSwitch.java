/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.model.VAction;
import org.eclipse.emf.ecp.view.model.VAttachment;
import org.eclipse.emf.ecp.view.model.VCategorization;
import org.eclipse.emf.ecp.view.model.VCategory;
import org.eclipse.emf.ecp.view.model.VContainedElement;
import org.eclipse.emf.ecp.view.model.VContainer;
import org.eclipse.emf.ecp.view.model.VControl;
import org.eclipse.emf.ecp.view.model.VDiagnostic;
import org.eclipse.emf.ecp.view.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.model.VElement;
import org.eclipse.emf.ecp.view.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.model.VView;
import org.eclipse.emf.ecp.view.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method
 * for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.model.VViewPackage
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
		if (modelPackage == null)
		{
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
		switch (classifierID)
		{
		case VViewPackage.DIAGNOSTIC: {
			final VDiagnostic diagnostic = (VDiagnostic) theEObject;
			T result = caseDiagnostic(diagnostic);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.ATTACHMENT: {
			final VAttachment attachment = (VAttachment) theEObject;
			T result = caseAttachment(attachment);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.DOMAIN_MODEL_REFERENCE: {
			final VDomainModelReference domainModelReference = (VDomainModelReference) theEObject;
			T result = caseDomainModelReference(domainModelReference);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE: {
			final VFeaturePathDomainModelReference featurePathDomainModelReference = (VFeaturePathDomainModelReference) theEObject;
			T result = caseFeaturePathDomainModelReference(featurePathDomainModelReference);
			if (result == null) {
				result = caseDomainModelReference(featurePathDomainModelReference);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.ELEMENT: {
			final VElement element = (VElement) theEObject;
			T result = caseElement(element);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.VIEW: {
			final VView view = (VView) theEObject;
			T result = caseView(view);
			if (result == null) {
				result = caseElement(view);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.CONTAINED_ELEMENT: {
			final VContainedElement containedElement = (VContainedElement) theEObject;
			T result = caseContainedElement(containedElement);
			if (result == null) {
				result = caseElement(containedElement);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.CONTAINER: {
			final VContainer container = (VContainer) theEObject;
			T result = caseContainer(container);
			if (result == null) {
				result = caseContainedElement(container);
			}
			if (result == null) {
				result = caseElement(container);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.CONTROL: {
			final VControl control = (VControl) theEObject;
			T result = caseControl(control);
			if (result == null) {
				result = caseContainedElement(control);
			}
			if (result == null) {
				result = caseElement(control);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.ABSTRACT_CATEGORIZATION: {
			final VAbstractCategorization abstractCategorization = (VAbstractCategorization) theEObject;
			T result = caseAbstractCategorization(abstractCategorization);
			if (result == null) {
				result = caseElement(abstractCategorization);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.CATEGORIZATION: {
			final VCategorization categorization = (VCategorization) theEObject;
			T result = caseCategorization(categorization);
			if (result == null) {
				result = caseAbstractCategorization(categorization);
			}
			if (result == null) {
				result = caseElement(categorization);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.CATEGORY: {
			final VCategory category = (VCategory) theEObject;
			T result = caseCategory(category);
			if (result == null) {
				result = caseAbstractCategorization(category);
			}
			if (result == null) {
				result = caseElement(category);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VViewPackage.ACTION: {
			final VAction action = (VAction) theEObject;
			T result = caseAction(action);
			if (result == null) {
				result = defaultCase(theEObject);
			}
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
	public T caseElement(VElement object)
	{
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
	public T caseDiagnostic(VDiagnostic object)
	{
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
	public T caseContainedElement(VContainedElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Abstract Categorization</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Abstract Categorization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractCategorization(VAbstractCategorization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Categorization</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Categorization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategorization(VCategorization object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategory(VCategory object) {
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
	public T caseContainer(VContainer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(VAction object) {
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
	public T caseDomainModelReference(VDomainModelReference object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Path Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Path Domain Model Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeaturePathDomainModelReference(VFeaturePathDomainModelReference object)
	{
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
	public T caseAttachment(VAttachment object)
	{
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
