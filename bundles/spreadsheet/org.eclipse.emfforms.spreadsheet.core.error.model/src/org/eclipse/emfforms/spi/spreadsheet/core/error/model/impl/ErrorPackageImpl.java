/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ErrorPackageImpl extends EPackageImpl implements ErrorPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass spreadsheetImportResultEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass errorReportEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass emfLocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass settingLocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass dmrLocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass settingToSheetMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass sheetLocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum severityEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ErrorPackageImpl() {
		super(eNS_URI, ErrorFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link ErrorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ErrorPackage init() {
		if (isInited) {
			return (ErrorPackage) EPackage.Registry.INSTANCE.getEPackage(ErrorPackage.eNS_URI);
		}

		// Obtain or create and register package
		final ErrorPackageImpl theErrorPackage = (ErrorPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof ErrorPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new ErrorPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theErrorPackage.createPackageContents();

		// Initialize created meta-data
		theErrorPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theErrorPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ErrorPackage.eNS_URI, theErrorPackage);
		return theErrorPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSpreadsheetImportResult() {
		return spreadsheetImportResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSpreadsheetImportResult_ErrorReports() {
		return (EReference) spreadsheetImportResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSpreadsheetImportResult_ImportedEObjects() {
		return (EReference) spreadsheetImportResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSpreadsheetImportResult_SettingToSheetMap() {
		return (EReference) spreadsheetImportResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getErrorReport() {
		return errorReportEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getErrorReport_Severity() {
		return (EAttribute) errorReportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getErrorReport_Message() {
		return (EAttribute) errorReportEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getErrorReport_EmfLocation() {
		return (EReference) errorReportEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getErrorReport_SheetLocation() {
		return (EReference) errorReportEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getEMFLocation() {
		return emfLocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEMFLocation_Root() {
		return (EReference) emfLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEMFLocation_SettingLocation() {
		return (EReference) emfLocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEMFLocation_DmrLocation() {
		return (EReference) emfLocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSettingLocation() {
		return settingLocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSettingLocation_EObject() {
		return (EReference) settingLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSettingLocation_Feature() {
		return (EReference) settingLocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDMRLocation() {
		return dmrLocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDMRLocation_DomainModelReference() {
		return (EReference) dmrLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSettingToSheetMapping() {
		return settingToSheetMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSettingToSheetMapping_SheetLocation() {
		return (EReference) settingToSheetMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSettingToSheetMapping_SettingLocation() {
		return (EReference) settingToSheetMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSheetLocation() {
		return sheetLocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSheetLocation_Sheet() {
		return (EAttribute) sheetLocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSheetLocation_Column() {
		return (EAttribute) sheetLocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSheetLocation_Row() {
		return (EAttribute) sheetLocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSheetLocation_ColumnName() {
		return (EAttribute) sheetLocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSheetLocation_Valid() {
		return (EAttribute) sheetLocationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getSeverity() {
		return severityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ErrorFactory getErrorFactory() {
		return (ErrorFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		spreadsheetImportResultEClass = createEClass(SPREADSHEET_IMPORT_RESULT);
		createEReference(spreadsheetImportResultEClass, SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS);
		createEReference(spreadsheetImportResultEClass, SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS);
		createEReference(spreadsheetImportResultEClass, SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP);

		errorReportEClass = createEClass(ERROR_REPORT);
		createEAttribute(errorReportEClass, ERROR_REPORT__SEVERITY);
		createEAttribute(errorReportEClass, ERROR_REPORT__MESSAGE);
		createEReference(errorReportEClass, ERROR_REPORT__EMF_LOCATION);
		createEReference(errorReportEClass, ERROR_REPORT__SHEET_LOCATION);

		sheetLocationEClass = createEClass(SHEET_LOCATION);
		createEAttribute(sheetLocationEClass, SHEET_LOCATION__SHEET);
		createEAttribute(sheetLocationEClass, SHEET_LOCATION__COLUMN);
		createEAttribute(sheetLocationEClass, SHEET_LOCATION__ROW);
		createEAttribute(sheetLocationEClass, SHEET_LOCATION__COLUMN_NAME);
		createEAttribute(sheetLocationEClass, SHEET_LOCATION__VALID);

		emfLocationEClass = createEClass(EMF_LOCATION);
		createEReference(emfLocationEClass, EMF_LOCATION__ROOT);
		createEReference(emfLocationEClass, EMF_LOCATION__SETTING_LOCATION);
		createEReference(emfLocationEClass, EMF_LOCATION__DMR_LOCATION);

		settingLocationEClass = createEClass(SETTING_LOCATION);
		createEReference(settingLocationEClass, SETTING_LOCATION__EOBJECT);
		createEReference(settingLocationEClass, SETTING_LOCATION__FEATURE);

		dmrLocationEClass = createEClass(DMR_LOCATION);
		createEReference(dmrLocationEClass, DMR_LOCATION__DOMAIN_MODEL_REFERENCE);

		settingToSheetMappingEClass = createEClass(SETTING_TO_SHEET_MAPPING);
		createEReference(settingToSheetMappingEClass, SETTING_TO_SHEET_MAPPING__SHEET_LOCATION);
		createEReference(settingToSheetMappingEClass, SETTING_TO_SHEET_MAPPING__SETTING_LOCATION);

		// Create enums
		severityEEnum = createEEnum(SEVERITY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(spreadsheetImportResultEClass, SpreadsheetImportResult.class, "SpreadsheetImportResult", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSpreadsheetImportResult_ErrorReports(), getErrorReport(), null, "errorReports", null, 0, //$NON-NLS-1$
			-1, SpreadsheetImportResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSpreadsheetImportResult_ImportedEObjects(), theEcorePackage.getEObject(), null,
			"importedEObjects", null, 0, -1, SpreadsheetImportResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSpreadsheetImportResult_SettingToSheetMap(), getSettingToSheetMapping(), null,
			"settingToSheetMap", null, 0, -1, SpreadsheetImportResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorReportEClass, ErrorReport.class, "ErrorReport", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorReport_Severity(), getSeverity(), "severity", null, 1, 1, ErrorReport.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorReport_Message(), ecorePackage.getEString(), "message", null, 1, 1, ErrorReport.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getErrorReport_EmfLocation(), getEMFLocation(), null, "emfLocation", null, 0, 1, //$NON-NLS-1$
			ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getErrorReport_SheetLocation(), getSheetLocation(), null, "sheetLocation", null, 0, 1, //$NON-NLS-1$
			ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sheetLocationEClass, SheetLocation.class, "SheetLocation", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSheetLocation_Sheet(), ecorePackage.getEString(), "sheet", null, 1, 1, SheetLocation.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSheetLocation_Column(), ecorePackage.getEInt(), "column", null, 1, 1, SheetLocation.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSheetLocation_Row(), ecorePackage.getEInt(), "row", null, 1, 1, SheetLocation.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSheetLocation_ColumnName(), ecorePackage.getEString(), "columnName", null, 1, 1, //$NON-NLS-1$
			SheetLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSheetLocation_Valid(), ecorePackage.getEBoolean(), "valid", "true", 1, 1, SheetLocation.class, //$NON-NLS-1$ //$NON-NLS-2$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(emfLocationEClass, EMFLocation.class, "EMFLocation", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEMFLocation_Root(), theEcorePackage.getEObject(), null, "root", null, 1, 1, EMFLocation.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getEMFLocation_SettingLocation(), getSettingLocation(), null, "settingLocation", null, 0, 1, //$NON-NLS-1$
			EMFLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEMFLocation_DmrLocation(), getDMRLocation(), null, "dmrLocation", null, 0, 1, //$NON-NLS-1$
			EMFLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(settingLocationEClass, SettingLocation.class, "SettingLocation", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSettingLocation_EObject(), theEcorePackage.getEObject(), null, "eObject", null, 1, 1, //$NON-NLS-1$
			SettingLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSettingLocation_Feature(), theEcorePackage.getEStructuralFeature(), null, "feature", null, 1, //$NON-NLS-1$
			1, SettingLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dmrLocationEClass, DMRLocation.class, "DMRLocation", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDMRLocation_DomainModelReference(), theViewPackage.getDomainModelReference(), null,
			"domainModelReference", null, 1, 1, DMRLocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(settingToSheetMappingEClass, SettingToSheetMapping.class, "SettingToSheetMapping", !IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSettingToSheetMapping_SheetLocation(), getSheetLocation(), null, "sheetLocation", null, //$NON-NLS-1$
			1, 1, SettingToSheetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSettingToSheetMapping_SettingLocation(), getSettingLocation(), null, "settingLocation", //$NON-NLS-1$
			null, 1, 1, SettingToSheetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(severityEEnum, Severity.class, "Severity"); //$NON-NLS-1$
		addEEnumLiteral(severityEEnum, Severity.OK);
		addEEnumLiteral(severityEEnum, Severity.INFO);
		addEEnumLiteral(severityEEnum, Severity.WARNING);
		addEEnumLiteral(severityEEnum, Severity.ERROR);
		addEEnumLiteral(severityEEnum, Severity.CANCEL);

		// Create resource
		createResource(eNS_URI);
	}

} // ErrorPackageImpl
