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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage
 * @generated
 */
public class MappingdmrValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final MappingdmrValidator INSTANCE = new MappingdmrValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic
	 * {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.ecp.view.spi.mappingdmr.model"; //$NON-NLS-1$

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a
	 * derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ViewValidator viewValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public MappingdmrValidator() {
		super();
		viewValidator = ViewValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return VMappingdmrPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		switch (classifierID) {
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE:
			return validateMappingDomainModelReference((VMappingDomainModelReference) value, diagnostics, context);
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return validateMappingDomainModelReferenceSegment((VMappingDomainModelReferenceSegment) value, diagnostics,
				context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateMappingDomainModelReference(VMappingDomainModelReference mappingDomainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(mappingDomainModelReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(mappingDomainModelReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(mappingDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateMappingDomainModelReference_resolveable(mappingDomainModelReference, diagnostics,
				context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Mapping Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	public boolean validateMappingDomainModelReference_resolveable(
		VMappingDomainModelReference mappingDomainModelReference, DiagnosticChain diagnostics,
		Map<Object, Object> context) {

		if (mappingDomainModelReference.getDomainModelReference() == null
			|| mappingDomainModelReference.getMappedClass() == null) {
			if (diagnostics != null && mappingDomainModelReference.eContainer() != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, "Missing required feature", //$NON-NLS-1$
					mappingDomainModelReference.eContainer(),
					mappingDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		// check path to map
		if (!ViewValidator.INSTANCE.validateFeaturePathDomainModelReference_resolveable(
			mappingDomainModelReference,
			diagnostics, context)) {
			return false;
		}

		// check map
		final EStructuralFeature feature = mappingDomainModelReference.getDomainModelEFeature();
		if (!EReference.class.isInstance(feature) || !feature.isMany()
			|| !"java.util.Map$Entry".equals(feature.getEType().getInstanceClassName())) { //$NON-NLS-1$
			if (diagnostics != null) {
				final String message = "Domain model reference does not end at a map."; //$NON-NLS-1$
				if (mappingDomainModelReference.eContainer() != null) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0,
						message,
						mappingDomainModelReference.eContainer(),
						mappingDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, mappingDomainModelReference,
					VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature()));
			}
			return false;
		}

		final EClass rootEClass = mappingDomainModelReference.getMappedClass();
		final VDomainModelReference domainModelReference = mappingDomainModelReference.getDomainModelReference();
		final EValidator validator = EValidator.Registry.INSTANCE.getEValidator(domainModelReference.eClass()
			.getEPackage());
		final Map<Object, Object> newContext = new LinkedHashMap<Object, Object>(context);
		newContext.put(ViewValidator.ECLASS_KEY, rootEClass);
		if (!validator.validate(domainModelReference, diagnostics, newContext)) {
			final String message = "Cannot resolve mapped DMR"; //$NON-NLS-1$
			if (mappingDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0,
					message,
					mappingDomainModelReference.eContainer(),
					mappingDomainModelReference.eContainingFeature()));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateMappingDomainModelReferenceSegment(
		VMappingDomainModelReferenceSegment mappingDomainModelReferenceSegment, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(mappingDomainModelReferenceSegment, diagnostics, context);
	}

	// END COMPLEX CODE

	private Diagnostic createDiagnostic(int severity, int code, String message, EObject object,
		EStructuralFeature feature) {
		return new BasicDiagnostic(
			severity,
			DIAGNOSTIC_SOURCE,
			code,
			message,
			new Object[] { object, feature });
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} // MappingdmrValidator
