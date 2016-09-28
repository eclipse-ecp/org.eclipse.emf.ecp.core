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
package org.eclipse.emf.ecp.view.spi.table.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecp.view.internal.table.model.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 *
 * @since 1.5
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage
 * @generated
 */
public class TableValidator extends EObjectValidator {
	private static final String VALIDATING_TABLE_CONTROL_KEY = "ValidatingTableControl";

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final TableValidator INSTANCE = new TableValidator();

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
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.ecp.view.spi.table.model"; //$NON-NLS-1$

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
	 * The test EMFFormsDatabinding service.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	private EMFFormsDatabinding emfFormsDatabinding;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * This is a constructor for test cases.
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 * @param emfFormsDatabinding An {@link EMFFormsDatabinding}
	 */
	TableValidator(EMFFormsDatabinding emfFormsDatabinding) {
		this();
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TableValidator() {
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
		return VTablePackage.eINSTANCE;
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
		case VTablePackage.TABLE_CONTROL:
			return validateTableControl((VTableControl) value, diagnostics, context);
		case VTablePackage.TABLE_COLUMN_CONFIGURATION:
			return validateTableColumnConfiguration((VTableColumnConfiguration) value, diagnostics, context);
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE:
			return validateTableDomainModelReference((VTableDomainModelReference) value, diagnostics, context);
		case VTablePackage.READ_ONLY_COLUMN_CONFIGURATION:
			return validateReadOnlyColumnConfiguration((VReadOnlyColumnConfiguration) value, diagnostics, context);
		case VTablePackage.WIDTH_CONFIGURATION:
			return validateWidthConfiguration((VWidthConfiguration) value, diagnostics, context);
		case VTablePackage.DETAIL_EDITING:
			return validateDetailEditing((DetailEditing) value, diagnostics, context);
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
	public boolean validateTableControl(VTableControl tableControl, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		if (!validate_NoCircularContainment(tableControl, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(tableControl, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(tableControl, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateTableControl_resolveable(tableControl, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Control</em>'.
	 * <!-- begin-user-doc -->
	 *
	 * @param tableControl the {@link VTableControl} to check
	 * @param diagnostics the diagnostics
	 * @param context the validation context
	 *            <!-- end-user-doc -->
	 * @return the validation result
	 *
	 * @generated NOT
	 * @since 1.10
	 */
	// CHECKSTYLE.OFF: MethodName
	public boolean validateTableControl_resolveable(VTableControl tableControl, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		// CHECKSTYLE.ON: MethodName
		final VDomainModelReference domainModelReference = tableControl.getDomainModelReference();
		if (domainModelReference == null) {
			diagnostics
				.add(createDiagnostic(Diagnostic.ERROR, 0, "No Domain Model Reference set.", //$NON-NLS-1$
					tableControl, VViewPackage.eINSTANCE.getControl_DomainModelReference()));
			return false;
		}
		if (domainModelReference instanceof VTableDomainModelReference) {
			context.put(VALIDATING_TABLE_CONTROL_KEY, true);
			return validateTableDomainModelReference((VTableDomainModelReference) domainModelReference, diagnostics,
				context);
		}
		return viewValidator.validateDomainModelReference(domainModelReference, diagnostics, context);
	}

	/**
	 * @generated
	 * 			<!-- begin-user-doc -->
	 *            <!-- end-user-doc -->
	 *
	 */
	public boolean validateTableColumnConfiguration(VTableColumnConfiguration tableColumnConfiguration,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(tableColumnConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateTableDomainModelReference(VTableDomainModelReference tableDomainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(tableDomainModelReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(tableDomainModelReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(tableDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateTableDomainModelReference_resolveable(tableDomainModelReference, diagnostics, context);
		}
		return result;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.EObjectValidator#validate_EveryMultiplicityConforms(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @generated NOT
	 *
	 */
	// BEGIN COMPLEX CODE
	@Override
	public boolean validate_EveryMultiplicityConforms(EObject eObject, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		boolean result = true;
		final EClass eClass = eObject.eClass();
		for (int i = 0, size = eClass.getFeatureCount(); i < size; ++i) {
			// begin custom
			if (VTableDomainModelReference.class.isInstance(eObject)
				&& eClass.getEStructuralFeature(i) == VViewPackage.eINSTANCE
					.getFeaturePathDomainModelReference_DomainModelEFeature()) {
				continue;
			}
			// end custom
			result &= validate_MultiplicityConforms(eObject, eClass.getEStructuralFeature(i), diagnostics, context);
			if (!result && diagnostics == null) {
				return false;
			}
		}
		return result;
	}

	// END COMPLEX CODE

	/**
	 * Validates the resolveable constraint of '<em>Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	public boolean validateTableDomainModelReference_resolveable(VTableDomainModelReference tableDomainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		// validate path to table
		VDomainModelReference pathToMultiRef = tableDomainModelReference.getDomainModelReference();
		final EValidator validator;
		if (pathToMultiRef != null) {
			context.put(ViewValidator.ECLASS_KEY, null);
			validator = EValidator.Registry.INSTANCE.getEValidator(pathToMultiRef.eClass().getEPackage());
		} else {
			pathToMultiRef = tableDomainModelReference;
			validator = viewValidator;
		}
		if (!validator.validate(pathToMultiRef, diagnostics, context)) {
			if (pathToMultiRef != tableDomainModelReference && tableDomainModelReference.eContainer() != null
				&& diagnostics != null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, "Error on path to multi reference", //$NON-NLS-1$
					tableDomainModelReference.eContainer(),
					tableDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		// test if table ends a multi reference
		IValueProperty valueProperty;
		try {
			valueProperty = getEMFFormsDatabinding()
				.getValueProperty(pathToMultiRef, null);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return true;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (!EReference.class.isInstance(feature) || !feature.isMany()) {
			if (diagnostics != null) {
				final String message = "Domain model reference does not end at a multi reference."; //$NON-NLS-1$
				if (tableDomainModelReference.eContainer() != null) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message,
						tableDomainModelReference.eContainer(), tableDomainModelReference.eContainingFeature()));
				}
				if (pathToMultiRef == tableDomainModelReference) {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, pathToMultiRef,
						VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature()));
				} else {
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, tableDomainModelReference,
						VTablePackage.eINSTANCE.getTableDomainModelReference_DomainModelReference()));
				}
			}
			return false;
		}
		if (context.containsKey(VALIDATING_TABLE_CONTROL_KEY)) {
			return true;
		}
		// test if columns are resolveable
		final EReference reference = EReference.class.cast(feature);
		final EClass referenceType = reference.getEReferenceType();
		boolean success = true;
		for (final VDomainModelReference columnDMR : tableDomainModelReference.getColumnDomainModelReferences()) {
			success &= validateColumnForRootEClass(columnDMR, referenceType);
			if (!success) {
				break;
			}
		}
		if (success) {
			return true;
		}

		// test if columns are resolvable against a subclass
		final Collection<EClass> subClasses = getSubClasses(referenceType);
		final Set<EClass> usableSubclasses = new LinkedHashSet<EClass>();
		for (final EClass subClass : subClasses) {
			success = true;
			for (final VDomainModelReference columnDMR : tableDomainModelReference.getColumnDomainModelReferences()) {
				success &= validateColumnForRootEClass(columnDMR, subClass);
				if (!success) {
					break;
				}
			}
			if (success) {
				usableSubclasses.add(subClass);
			}
		}

		if (usableSubclasses.isEmpty()) {
			if (diagnostics != null) {
				final String message = "Some columns may not be resolvable"; //$NON-NLS-1$
				diagnostics.add(createDiagnostic(Diagnostic.WARNING, 0, message, tableDomainModelReference,
					VTablePackage.eINSTANCE.getTableDomainModelReference_ColumnDomainModelReferences()));
			}
			return false;
		}

		if (diagnostics != null) {
			final String message = "Columns are resovable against a subclass of " + referenceType.getName(); //$NON-NLS-1$
			diagnostics.add(createDiagnostic(Diagnostic.INFO, 0, message, tableDomainModelReference,
				VTablePackage.eINSTANCE.getTableDomainModelReference_ColumnDomainModelReferences()));
		}
		return true;

	}

	private EMFFormsDatabinding getEMFFormsDatabinding() {
		if (emfFormsDatabinding != null) {
			return emfFormsDatabinding;
		}
		return Activator.getDefault().getEMFFormsDatabinding();
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

	private boolean validateColumnForRootEClass(VDomainModelReference columnDMR, EClass rootEClass) {
		final EValidator validator = EValidator.Registry.INSTANCE.getEValidator(columnDMR.eClass().getEPackage());
		final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
		context.put(ViewValidator.ECLASS_KEY, rootEClass);
		return validator.validate(columnDMR, null, context);
	}

	/**
	 * <!-- begin-user-doc -->.
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateReadOnlyColumnConfiguration(VReadOnlyColumnConfiguration readOnlyColumnConfiguration,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(readOnlyColumnConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWidthConfiguration(VWidthConfiguration widthConfiguration, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(widthConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDetailEditing(DetailEditing detailEditing, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return true;
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

	private static Collection<EClass> getSubClasses(EClass superClass) {
		final Collection<EClass> classes = new HashSet<EClass>();

		// avoid ConcurrentModificationException while iterating over the registry's key set
		final List<String> keySet = new ArrayList<String>(org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.keySet());
		for (final String nsURI : keySet) {
			final EPackage ePackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage(nsURI);
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					final EClass eClass = (EClass) eClassifier;
					if (superClass.isSuperTypeOf(eClass) && !eClass.isAbstract() && !eClass.isInterface()) {
						classes.add(eClass);
					}
				}
			}
		}
		return classes;
	}

} // TableValidator
