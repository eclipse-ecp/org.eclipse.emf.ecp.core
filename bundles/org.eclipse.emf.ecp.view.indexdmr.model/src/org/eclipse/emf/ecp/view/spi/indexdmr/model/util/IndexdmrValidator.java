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
package org.eclipse.emf.ecp.view.spi.indexdmr.model.util;

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
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage
 * @generated
 */
public class IndexdmrValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final IndexdmrValidator INSTANCE = new IndexdmrValidator();

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
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.ecp.view.spi.indexdmr.model"; //$NON-NLS-1$

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
	public IndexdmrValidator()
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
		return VIndexdmrPackage.eINSTANCE;
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
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE:
			return validateIndexDomainModelReference((VIndexDomainModelReference) value, diagnostics, context);
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
	public boolean validateIndexDomainModelReference(VIndexDomainModelReference indexDomainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		if (!validate_NoCircularContainment(indexDomainModelReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(indexDomainModelReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(indexDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateIndexDomainModelReference_resolveable(indexDomainModelReference, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Index Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	public boolean validateIndexDomainModelReference_resolveable(VIndexDomainModelReference indexDomainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context) {

		// validate path to index
		if (!ViewValidator.INSTANCE.validateFeaturePathDomainModelReference_resolveable(indexDomainModelReference,
			diagnostics, context)) {
			return false;
		}
		final EStructuralFeature feature = indexDomainModelReference.getDomainModelEFeature();
		if (!EReference.class.isInstance(feature) || !feature.isMany()) {
			if (diagnostics != null) {
				final String message = "Domain model reference does not end at a multi reference."; //$NON-NLS-1$
				if (indexDomainModelReference.eContainer() != null) {
					diagnostics
						.add(createDiagnostic(Diagnostic.ERROR, 0,
							message, indexDomainModelReference.eContainer(),
							indexDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, indexDomainModelReference,
					VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature()));
			}
			return false;
		}

		if (indexDomainModelReference.getIndex() < 0) {
			if (diagnostics != null) {
				final String message = "Index has invalid value: " + indexDomainModelReference.getIndex(); //$NON-NLS-1$
				if (indexDomainModelReference.eContainer() != null) {
					diagnostics
						.add(createDiagnostic(Diagnostic.ERROR, 0,
							message, indexDomainModelReference.eContainer(),
							indexDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, indexDomainModelReference,
					VIndexdmrPackage.eINSTANCE.getIndexDomainModelReference_Index()));
			}
			return false;
		}

		if (indexDomainModelReference.getTargetDMR() == null) {
			if (indexDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0,
					"No target DMR set.", indexDomainModelReference.eContainer(), //$NON-NLS-1$
					indexDomainModelReference.eContainingFeature()));
			}
			return false;
		}
		final EClass rootEClass = EReference.class.cast(feature).getEReferenceType();
		final VDomainModelReference targetDMR = indexDomainModelReference.getTargetDMR();
		final EValidator validator = EValidator.Registry.INSTANCE.getEValidator(targetDMR.eClass().getEPackage());
		final Map<Object, Object> newContext = new LinkedHashMap<Object, Object>(context);
		newContext.put(ViewValidator.ECLASS_KEY, rootEClass);
		if (!validator.validate(targetDMR, diagnostics, newContext)) {
			final String message = "Target DMR not resolveable."; //$NON-NLS-1$
			if (indexDomainModelReference.eContainer() != null && diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0,
					message, indexDomainModelReference.eContainer(),
					indexDomainModelReference.eContainingFeature()));
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

} // IndexdmrValidator
