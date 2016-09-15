/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method
 * for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage
 * @generated
 */
public class MappingdmrSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static VMappingdmrPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public MappingdmrSwitch() {
		if (modelPackage == null) {
			modelPackage = VMappingdmrPackage.eINSTANCE;
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE: {
			final VMappingDomainModelReference mappingDomainModelReference = (VMappingDomainModelReference) theEObject;
			T result = caseMappingDomainModelReference(mappingDomainModelReference);
			if (result == null) {
				result = caseFeaturePathDomainModelReference(mappingDomainModelReference);
			}
			if (result == null) {
				result = caseDomainModelReference(mappingDomainModelReference);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT: {
			final VMappingDomainModelReferenceSegment mappingDomainModelReferenceSegment = (VMappingDomainModelReferenceSegment) theEObject;
			T result = caseMappingDomainModelReferenceSegment(mappingDomainModelReferenceSegment);
			if (result == null) {
				result = caseFeatureDomainModelReferenceSegment(mappingDomainModelReferenceSegment);
			}
			if (result == null) {
				result = caseDomainModelReferenceSegment(mappingDomainModelReferenceSegment);
			}
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
	 * Returns the result of interpreting the object as an instance of ' <em>Mapping Domain Model Reference</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Mapping Domain Model Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingDomainModelReference(VMappingDomainModelReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping Domain Model Reference
	 * Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping Domain Model Reference
	 *         Segment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingDomainModelReferenceSegment(VMappingDomainModelReferenceSegment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Domain Model Reference</em>'. <!--
	 * begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Domain Model Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainModelReference(VDomainModelReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Path Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will
	 * terminate the switch. <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Path Domain Model Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeaturePathDomainModelReference(
		VFeaturePathDomainModelReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Model Reference Segment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
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
	 * <!-- end-user-doc -->
	 *
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
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns
	 * null; returning a non-null result will terminate the switch, but this is
	 * the last case anyway. <!-- end-user-doc -->
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

} // MappingdmrSwitch
