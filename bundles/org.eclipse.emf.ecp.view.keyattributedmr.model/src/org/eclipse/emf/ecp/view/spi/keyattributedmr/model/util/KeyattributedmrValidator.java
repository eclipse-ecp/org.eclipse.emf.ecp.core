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
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model.util;

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
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage
 * @generated
 */
public class KeyattributedmrValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final KeyattributedmrValidator INSTANCE = new KeyattributedmrValidator();

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
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.ecp.view.spi.keyattributedmr.model"; //$NON-NLS-1$

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
	public KeyattributedmrValidator()
	{
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
	protected EPackage getEPackage()
	{
		return VKeyattributedmrPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		switch (classifierID)
		{
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE:
			return validateKeyAttributeDomainModelReference((VKeyAttributeDomainModelReference) value, diagnostics,
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
	public boolean validateKeyAttributeDomainModelReference(
		VKeyAttributeDomainModelReference keyAttributeDomainModelReference, DiagnosticChain diagnostics,
		Map<Object, Object> context)
	{
		if (!validate_NoCircularContainment(keyAttributeDomainModelReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(keyAttributeDomainModelReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(keyAttributeDomainModelReference, diagnostics,
				context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(keyAttributeDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateKeyAttributeDomainModelReference_resolveable(keyAttributeDomainModelReference,
				diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Key Attribute Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	public boolean validateKeyAttributeDomainModelReference_resolveable(
		VKeyAttributeDomainModelReference keyAttributeDomainModelReference, DiagnosticChain diagnostics,
		Map<Object, Object> context) {

		if (keyAttributeDomainModelReference.getKeyDMR() == null) {
			if (keyAttributeDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics
					.add(createDiagnostic(Diagnostic.ERROR, 0, "Missing key DMR.", //$NON-NLS-1$
						keyAttributeDomainModelReference.eContainer(),
						keyAttributeDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		if (keyAttributeDomainModelReference.getKeyValue() == null) {
			if (keyAttributeDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics
					.add(createDiagnostic(Diagnostic.ERROR, 0, "No key Value.", //$NON-NLS-1$
						keyAttributeDomainModelReference.eContainer(),
						keyAttributeDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		// validate path to reference
		if (!ViewValidator.INSTANCE.validateFeaturePathDomainModelReference_resolveable(
			keyAttributeDomainModelReference, diagnostics, context)) {
			return false;
		}

		final EStructuralFeature feature = keyAttributeDomainModelReference.getDomainModelEFeature();
		if (!EReference.class.isInstance(feature) || !feature.isMany()) {
			if (diagnostics != null) {
				final String message = "Domain model reference does not end at a multi reference."; //$NON-NLS-1$
				if (keyAttributeDomainModelReference.eContainer() != null) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message,
						keyAttributeDomainModelReference.eContainer(),
						keyAttributeDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, keyAttributeDomainModelReference,
					VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature()));
			}
			return false;
		}

		final EClass rootEClass = EReference.class.cast(feature).getEReferenceType();
		final VDomainModelReference targetDMR = keyAttributeDomainModelReference.getKeyDMR();
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(targetDMR.eClass().getEPackage());
		Map<Object, Object> newContext = new LinkedHashMap<Object, Object>(context);
		newContext.put(ViewValidator.ECLASS_KEY, rootEClass);
		if (!validator.validate(targetDMR, diagnostics, newContext)) {
			final String message = "Key DMR cannot be resolved"; //$NON-NLS-1$
			if (keyAttributeDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message,
					keyAttributeDomainModelReference.eContainer(),
					keyAttributeDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		if (keyAttributeDomainModelReference.getValueDMR() == null) {
			if (keyAttributeDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, "Missing value DMR", //$NON-NLS-1$
					keyAttributeDomainModelReference.eContainer(),
					keyAttributeDomainModelReference.eContainingFeature()));
			}
			return false;
		}
		final VDomainModelReference valueDMR = keyAttributeDomainModelReference.getValueDMR();
		validator = EValidator.Registry.INSTANCE.getEValidator(valueDMR.eClass().getEPackage());
		newContext = new LinkedHashMap<Object, Object>(context);
		newContext.put(ViewValidator.ECLASS_KEY, rootEClass);
		if (!validator.validate(valueDMR, diagnostics, newContext)) {
			final String message = "Value DMR cannot be resolved"; //$NON-NLS-1$
			if (keyAttributeDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message,
					keyAttributeDomainModelReference.eContainer(),
					keyAttributeDomainModelReference.eContainingFeature()));
			}
			return false;
		}
		return true;
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
	public ResourceLocator getResourceLocator()
	{
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} // KeyattributedmrValidator
