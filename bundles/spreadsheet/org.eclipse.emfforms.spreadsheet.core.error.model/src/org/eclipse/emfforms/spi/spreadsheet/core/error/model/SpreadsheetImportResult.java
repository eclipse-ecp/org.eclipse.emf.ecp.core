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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reports</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getErrorReports
 * <em>Error Reports</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getImportedEObjects
 * <em>Imported EObjects</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getSettingToSheetMap
 * <em>Setting To Sheet Map</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSpreadsheetImportResult()
 * @model
 * @generated
 */
public interface SpreadsheetImportResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Error Reports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Reports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Error Reports</em>' containment reference list.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSpreadsheetImportResult_ErrorReports()
	 * @model containment="true"
	 * @generated
	 */
	EList<ErrorReport> getErrorReports();

	/**
	 * Returns the value of the '<em><b>Imported EObjects</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported EObjects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Imported EObjects</em>' reference list.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSpreadsheetImportResult_ImportedEObjects()
	 * @model
	 * @generated
	 */
	EList<EObject> getImportedEObjects();

	/**
	 * Returns the value of the '<em><b>Setting To Sheet Map</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping}
	 * .
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Setting To Sheet Map</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Setting To Sheet Map</em>' reference list.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage#getSpreadsheetImportResult_SettingToSheetMap()
	 * @model
	 * @generated
	 */
	EList<SettingToSheetMapping> getSettingToSheetMap();

	/**
	 * Reports a new {@link ErrorReport error}.
	 *
	 * @param severity the {@link Severity severity}
	 * @param message the description message
	 */
	void reportError(Severity severity, String message);

	/**
	 * Reports a new {@link ErrorReport error}.
	 *
	 * @param severity the {@link Severity severity}
	 * @param message the description message
	 * @param emfLocation the affected objects in the EMF model
	 */
	void reportError(Severity severity, String message, EMFLocation emfLocation);

	/**
	 * Reports a new {@link ErrorReport error}.
	 *
	 * @param severity the {@link Severity severity}
	 * @param message the description message
	 * @param sheetLocation the information to identify the affected cell in the spreadsheet
	 */
	void reportError(Severity severity, String message, SheetLocation sheetLocation);

	/**
	 * Reports a new {@link ErrorReport error}.
	 *
	 * @param severity the {@link Severity severity}
	 * @param message the description message
	 * @param emfLocation the affected objects in the EMF model
	 * @param sheetLocation the information to identify the affected cell in the spreadsheet
	 */
	void reportError(Severity severity, String message, EMFLocation emfLocation, SheetLocation sheetLocation);

	/**
	 * Retrieves the SheetLocations which corresponds to the provided EStructuralFeature. If such
	 * a feature cannot be found at all, then the information in the sheetlocation shows this erroneous location.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to return the {@link SheetLocation SheetLocations} for
	 * @return The {@link SheetLocation SheetLocations}
	 */
	Collection<SheetLocation> getSheetLocations(EStructuralFeature structuralFeature);

	/**
	 * Retrieves the SheetLocation which corresponds to the provided Setting. If such
	 * a feature cannot be found at all, then the information in the sheetlocation shows this erroneous location.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to return the {@link SheetLocation} for
	 * @return The {@link SheetLocation}
	 */
	SheetLocation getSheetLocation(EObject eObject, EStructuralFeature structuralFeature);

} // ErrorReports
