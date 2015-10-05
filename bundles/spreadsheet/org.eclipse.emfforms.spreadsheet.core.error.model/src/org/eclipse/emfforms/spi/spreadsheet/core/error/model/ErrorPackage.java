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
package org.eclipse.emfforms.spi.spreadsheet.core.error.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorFactory
 * @model kind="package"
 * @generated
 */
public interface ErrorPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "error"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/emfforms/spreadsheet/core/error/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emfforms.spreadsheet.core.error.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	ErrorPackage eINSTANCE = org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl
	 * <em>Spreadsheet Import Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSpreadsheetImportResult()
	 * @generated
	 */
	int SPREADSHEET_IMPORT_RESULT = 0;

	/**
	 * The feature id for the '<em><b>Error Reports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS = 0;

	/**
	 * The feature id for the '<em><b>Imported EObjects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS = 1;

	/**
	 * The feature id for the '<em><b>Setting To Sheet Map</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP = 2;

	/**
	 * The number of structural features of the '<em>Spreadsheet Import Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SPREADSHEET_IMPORT_RESULT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl
	 * <em>Report</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getErrorReport()
	 * @generated
	 */
	int ERROR_REPORT = 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ERROR_REPORT__SEVERITY = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ERROR_REPORT__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Emf Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ERROR_REPORT__EMF_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Sheet Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ERROR_REPORT__SHEET_LOCATION = 3;

	/**
	 * The number of structural features of the '<em>Report</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ERROR_REPORT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl
	 * <em>EMF Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getEMFLocation()
	 * @generated
	 */
	int EMF_LOCATION = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl
	 * <em>Setting Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSettingLocation()
	 * @generated
	 */
	int SETTING_LOCATION = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.DMRLocationImpl
	 * <em>DMR Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.DMRLocationImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getDMRLocation()
	 * @generated
	 */
	int DMR_LOCATION = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl
	 * <em>Sheet Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSheetLocation()
	 * @generated
	 */
	int SHEET_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Sheet</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION__SHEET = 0;

	/**
	 * The feature id for the '<em><b>Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION__COLUMN = 1;

	/**
	 * The feature id for the '<em><b>Row</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION__ROW = 2;

	/**
	 * The feature id for the '<em><b>Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION__COLUMN_NAME = 3;

	/**
	 * The feature id for the '<em><b>Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION__VALID = 4;

	/**
	 * The number of structural features of the '<em>Sheet Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SHEET_LOCATION_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Root</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_LOCATION__ROOT = 0;

	/**
	 * The feature id for the '<em><b>Setting Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_LOCATION__SETTING_LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Dmr Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_LOCATION__DMR_LOCATION = 2;

	/**
	 * The number of structural features of the '<em>EMF Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int EMF_LOCATION_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_LOCATION__EOBJECT = 0;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_LOCATION__FEATURE = 1;

	/**
	 * The number of structural features of the '<em>Setting Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_LOCATION_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_LOCATION__DOMAIN_MODEL_REFERENCE = 0;

	/**
	 * The number of structural features of the '<em>DMR Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_LOCATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl
	 * <em>Setting To Sheet Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSettingToSheetMapping()
	 * @generated
	 */
	int SETTING_TO_SHEET_MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Sheet Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_TO_SHEET_MAPPING__SHEET_LOCATION = 0;

	/**
	 * The feature id for the '<em><b>Setting Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_TO_SHEET_MAPPING__SETTING_LOCATION = 1;

	/**
	 * The number of structural features of the '<em>Setting To Sheet Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int SETTING_TO_SHEET_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 7;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult
	 * <em>Spreadsheet Import Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Spreadsheet Import Result</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult
	 * @generated
	 */
	EClass getSpreadsheetImportResult();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getErrorReports
	 * <em>Error Reports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Error Reports</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getErrorReports()
	 * @see #getSpreadsheetImportResult()
	 * @generated
	 */
	EReference getSpreadsheetImportResult_ErrorReports();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getImportedEObjects
	 * <em>Imported EObjects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Imported EObjects</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getImportedEObjects()
	 * @see #getSpreadsheetImportResult()
	 * @generated
	 */
	EReference getSpreadsheetImportResult_ImportedEObjects();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getSettingToSheetMap
	 * <em>Setting To Sheet Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Setting To Sheet Map</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getSettingToSheetMap()
	 * @see #getSpreadsheetImportResult()
	 * @generated
	 */
	EReference getSpreadsheetImportResult_SettingToSheetMap();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport
	 * <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Report</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport
	 * @generated
	 */
	EClass getErrorReport();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSeverity()
	 * @see #getErrorReport()
	 * @generated
	 */
	EAttribute getErrorReport_Severity();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getMessage()
	 * @see #getErrorReport()
	 * @generated
	 */
	EAttribute getErrorReport_Message();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getEmfLocation <em>Emf Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Emf Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getEmfLocation()
	 * @see #getErrorReport()
	 * @generated
	 */
	EReference getErrorReport_EmfLocation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSheetLocation <em>Sheet Location</em>
	 * }'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Sheet Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport#getSheetLocation()
	 * @see #getErrorReport()
	 * @generated
	 */
	EReference getErrorReport_SheetLocation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation
	 * <em>EMF Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>EMF Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation
	 * @generated
	 */
	EClass getEMFLocation();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Root</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getRoot()
	 * @see #getEMFLocation()
	 * @generated
	 */
	EReference getEMFLocation_Root();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getSettingLocation
	 * <em>Setting Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Setting Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getSettingLocation()
	 * @see #getEMFLocation()
	 * @generated
	 */
	EReference getEMFLocation_SettingLocation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getDmrLocation <em>Dmr Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Dmr Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation#getDmrLocation()
	 * @see #getEMFLocation()
	 * @generated
	 */
	EReference getEMFLocation_DmrLocation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation
	 * <em>Setting Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Setting Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation
	 * @generated
	 */
	EClass getSettingLocation();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation#getEObject <em>EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>EObject</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation#getEObject()
	 * @see #getSettingLocation()
	 * @generated
	 */
	EReference getSettingLocation_EObject();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation#getFeature()
	 * @see #getSettingLocation()
	 * @generated
	 */
	EReference getSettingLocation_Feature();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation
	 * <em>DMR Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>DMR Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation
	 * @generated
	 */
	EClass getDMRLocation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation#getDomainModelReference()
	 * @see #getDMRLocation()
	 * @generated
	 */
	EReference getDMRLocation_DomainModelReference();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping
	 * <em>Setting To Sheet Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Setting To Sheet Mapping</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping
	 * @generated
	 */
	EClass getSettingToSheetMapping();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSheetLocation
	 * <em>Sheet Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Sheet Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSheetLocation()
	 * @see #getSettingToSheetMapping()
	 * @generated
	 */
	EReference getSettingToSheetMapping_SheetLocation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSettingLocation
	 * <em>Setting Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Setting Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping#getSettingLocation()
	 * @see #getSettingToSheetMapping()
	 * @generated
	 */
	EReference getSettingToSheetMapping_SettingLocation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation
	 * <em>Sheet Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Sheet Location</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation
	 * @generated
	 */
	EClass getSheetLocation();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getSheet <em>Sheet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Sheet</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getSheet()
	 * @see #getSheetLocation()
	 * @generated
	 */
	EAttribute getSheetLocation_Sheet();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Column</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getColumn()
	 * @see #getSheetLocation()
	 * @generated
	 */
	EAttribute getSheetLocation_Column();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getRow <em>Row</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Row</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getRow()
	 * @see #getSheetLocation()
	 * @generated
	 */
	EAttribute getSheetLocation_Row();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getColumnName <em>Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Column Name</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#getColumnName()
	 * @see #getSheetLocation()
	 * @generated
	 */
	EAttribute getSheetLocation_ColumnName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#isValid <em>Valid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Valid</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation#isValid()
	 * @see #getSheetLocation()
	 * @generated
	 */
	EAttribute getSheetLocation_Valid();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
	 * @generated
	 */
	EEnum getSeverity();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ErrorFactory getErrorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl
		 * <em>Spreadsheet Import Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSpreadsheetImportResult()
		 * @generated
		 */
		EClass SPREADSHEET_IMPORT_RESULT = eINSTANCE.getSpreadsheetImportResult();

		/**
		 * The meta object literal for the '<em><b>Error Reports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS = eINSTANCE.getSpreadsheetImportResult_ErrorReports();

		/**
		 * The meta object literal for the '<em><b>Imported EObjects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS = eINSTANCE
			.getSpreadsheetImportResult_ImportedEObjects();

		/**
		 * The meta object literal for the '<em><b>Setting To Sheet Map</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP = eINSTANCE
			.getSpreadsheetImportResult_SettingToSheetMap();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl <em>Report</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorReportImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getErrorReport()
		 * @generated
		 */
		EClass ERROR_REPORT = eINSTANCE.getErrorReport();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ERROR_REPORT__SEVERITY = eINSTANCE.getErrorReport_Severity();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ERROR_REPORT__MESSAGE = eINSTANCE.getErrorReport_Message();

		/**
		 * The meta object literal for the '<em><b>Emf Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ERROR_REPORT__EMF_LOCATION = eINSTANCE.getErrorReport_EmfLocation();

		/**
		 * The meta object literal for the '<em><b>Sheet Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ERROR_REPORT__SHEET_LOCATION = eINSTANCE.getErrorReport_SheetLocation();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl <em>EMF Location</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.EMFLocationImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getEMFLocation()
		 * @generated
		 */
		EClass EMF_LOCATION = eINSTANCE.getEMFLocation();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EMF_LOCATION__ROOT = eINSTANCE.getEMFLocation_Root();

		/**
		 * The meta object literal for the '<em><b>Setting Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EMF_LOCATION__SETTING_LOCATION = eINSTANCE.getEMFLocation_SettingLocation();

		/**
		 * The meta object literal for the '<em><b>Dmr Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference EMF_LOCATION__DMR_LOCATION = eINSTANCE.getEMFLocation_DmrLocation();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl
		 * <em>Setting Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingLocationImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSettingLocation()
		 * @generated
		 */
		EClass SETTING_LOCATION = eINSTANCE.getSettingLocation();

		/**
		 * The meta object literal for the '<em><b>EObject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SETTING_LOCATION__EOBJECT = eINSTANCE.getSettingLocation_EObject();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SETTING_LOCATION__FEATURE = eINSTANCE.getSettingLocation_Feature();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.DMRLocationImpl <em>DMR Location</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.DMRLocationImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getDMRLocation()
		 * @generated
		 */
		EClass DMR_LOCATION = eINSTANCE.getDMRLocation();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DMR_LOCATION__DOMAIN_MODEL_REFERENCE = eINSTANCE.getDMRLocation_DomainModelReference();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl
		 * <em>Setting To Sheet Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SettingToSheetMappingImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSettingToSheetMapping()
		 * @generated
		 */
		EClass SETTING_TO_SHEET_MAPPING = eINSTANCE.getSettingToSheetMapping();

		/**
		 * The meta object literal for the '<em><b>Sheet Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SETTING_TO_SHEET_MAPPING__SHEET_LOCATION = eINSTANCE.getSettingToSheetMapping_SheetLocation();

		/**
		 * The meta object literal for the '<em><b>Setting Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference SETTING_TO_SHEET_MAPPING__SETTING_LOCATION = eINSTANCE.getSettingToSheetMapping_SettingLocation();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl <em>Sheet Location</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SheetLocationImpl
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSheetLocation()
		 * @generated
		 */
		EClass SHEET_LOCATION = eINSTANCE.getSheetLocation();

		/**
		 * The meta object literal for the '<em><b>Sheet</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHEET_LOCATION__SHEET = eINSTANCE.getSheetLocation_Sheet();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHEET_LOCATION__COLUMN = eINSTANCE.getSheetLocation_Column();

		/**
		 * The meta object literal for the '<em><b>Row</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHEET_LOCATION__ROW = eINSTANCE.getSheetLocation_Row();

		/**
		 * The meta object literal for the '<em><b>Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHEET_LOCATION__COLUMN_NAME = eINSTANCE.getSheetLocation_ColumnName();

		/**
		 * The meta object literal for the '<em><b>Valid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute SHEET_LOCATION__VALID = eINSTANCE.getSheetLocation_Valid();

		/**
		 * The meta object literal for the '{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
		 * <em>Severity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity
		 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.ErrorPackageImpl#getSeverity()
		 * @generated
		 */
		EEnum SEVERITY = eINSTANCE.getSeverity();

	}

} // ErrorPackage
