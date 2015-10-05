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
package org.eclipse.emfforms.spi.spreadsheet.core.error.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage
 * @generated
 */
public class ErrorSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static ErrorPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ErrorSwitch() {
		if (modelPackage == null) {
			modelPackage = ErrorPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param ePackage the package in question.
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT: {
			final SpreadsheetImportResult spreadsheetImportResult = (SpreadsheetImportResult) theEObject;
			T result = caseSpreadsheetImportResult(spreadsheetImportResult);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.ERROR_REPORT: {
			final ErrorReport errorReport = (ErrorReport) theEObject;
			T result = caseErrorReport(errorReport);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.SHEET_LOCATION: {
			final SheetLocation sheetLocation = (SheetLocation) theEObject;
			T result = caseSheetLocation(sheetLocation);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.EMF_LOCATION: {
			final EMFLocation emfLocation = (EMFLocation) theEObject;
			T result = caseEMFLocation(emfLocation);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.SETTING_LOCATION: {
			final SettingLocation settingLocation = (SettingLocation) theEObject;
			T result = caseSettingLocation(settingLocation);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.DMR_LOCATION: {
			final DMRLocation dmrLocation = (DMRLocation) theEObject;
			T result = caseDMRLocation(dmrLocation);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case ErrorPackage.SETTING_TO_SHEET_MAPPING: {
			final SettingToSheetMapping settingToSheetMapping = (SettingToSheetMapping) theEObject;
			T result = caseSettingToSheetMapping(settingToSheetMapping);
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
	 * Returns the result of interpreting the object as an instance of '<em>Spreadsheet Import Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Spreadsheet Import Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSpreadsheetImportResult(SpreadsheetImportResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Report</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Report</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorReport(ErrorReport object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EMF Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EMF Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEMFLocation(EMFLocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Setting Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Setting Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSettingLocation(SettingLocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DMR Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DMR Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDMRLocation(DMRLocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Setting To Sheet Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Setting To Sheet Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSettingToSheetMapping(SettingToSheetMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sheet Location</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sheet Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSheetLocation(SheetLocation object) {
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

} // ErrorSwitch
