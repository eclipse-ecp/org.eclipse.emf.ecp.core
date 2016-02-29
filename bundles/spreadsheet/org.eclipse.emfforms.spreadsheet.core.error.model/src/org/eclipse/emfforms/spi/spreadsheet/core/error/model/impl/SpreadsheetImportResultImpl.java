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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderImpl;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.Severity;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reports</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl#getErrorReports
 * <em>Error Reports</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl#getImportedEObjects
 * <em>Imported EObjects</em>}</li>
 * <li>
 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.impl.SpreadsheetImportResultImpl#getSettingToSheetMap
 * <em>Setting To Sheet Map</em>}</li>
 * </ul>
 *
 * @generated
 */
@SuppressWarnings("restriction")
public class SpreadsheetImportResultImpl extends MinimalEObjectImpl.Container implements SpreadsheetImportResult {
	/**
	 * The cached value of the '{@link #getErrorReports() <em>Error Reports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getErrorReports()
	 * @generated
	 * @ordered
	 */
	protected EList<ErrorReport> errorReports;
	/**
	 * The cached value of the '{@link #getImportedEObjects() <em>Imported EObjects</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getImportedEObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> importedEObjects;

	/**
	 * The cached value of the '{@link #getSettingToSheetMap() <em>Setting To Sheet Map</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSettingToSheetMap()
	 * @generated
	 * @ordered
	 */
	protected EList<SettingToSheetMapping> settingToSheetMap;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SpreadsheetImportResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ErrorPackage.Literals.SPREADSHEET_IMPORT_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ErrorReport> getErrorReports() {
		if (errorReports == null) {
			errorReports = new EObjectContainmentEList<ErrorReport>(ErrorReport.class, this,
				ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS);
		}
		return errorReports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<EObject> getImportedEObjects() {
		if (importedEObjects == null) {
			importedEObjects = new EObjectResolvingEList<EObject>(EObject.class, this,
				ErrorPackage.SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS);
		}
		return importedEObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @return The List of SettingToSheetMapping.
	 *         <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public EList<SettingToSheetMapping> getSettingToSheetMap() {
		if (settingToSheetMap == null) {
			settingToSheetMap = new EObjectResolvingEList<SettingToSheetMapping>(SettingToSheetMapping.class, this,
				ErrorPackage.SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP) {

				private static final long serialVersionUID = 8911738816135102554L;

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.emf.ecore.util.EObjectEList#isUnique()
				 */
				@Override
				protected boolean isUnique() {
					return false;
				}

			};
		}
		return settingToSheetMap;
	}

	/**
	 * <!-- begin-user-doc -->.
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS:
			return ((InternalEList<?>) getErrorReports()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS:
			return getErrorReports();
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS:
			return getImportedEObjects();
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP:
			return getSettingToSheetMap();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS:
			getErrorReports().clear();
			getErrorReports().addAll((Collection<? extends ErrorReport>) newValue);
			return;
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS:
			getImportedEObjects().clear();
			getImportedEObjects().addAll((Collection<? extends EObject>) newValue);
			return;
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP:
			getSettingToSheetMap().clear();
			getSettingToSheetMap().addAll((Collection<? extends SettingToSheetMapping>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS:
			getErrorReports().clear();
			return;
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS:
			getImportedEObjects().clear();
			return;
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP:
			getSettingToSheetMap().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__ERROR_REPORTS:
			return errorReports != null && !errorReports.isEmpty();
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__IMPORTED_EOBJECTS:
			return importedEObjects != null && !importedEObjects.isEmpty();
		case ErrorPackage.SPREADSHEET_IMPORT_RESULT__SETTING_TO_SHEET_MAP:
			return settingToSheetMap != null && !settingToSheetMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public void reportError(Severity severity, String message) {
		reportError(severity, message, null, null);
	}

	@Override
	public void reportError(Severity severity, String message, EMFLocation emfLocation) {
		reportError(severity, message, emfLocation, null);
	}

	@Override
	public void reportError(Severity severity, String message, SheetLocation sheetLocation) {
		reportError(severity, message, null, sheetLocation);
	}

	@Override
	public void reportError(Severity severity, String message, EMFLocation emfLocation, SheetLocation sheetLocation) {
		final ErrorReport report = ErrorFactory.eINSTANCE.createErrorReport();
		report.setSeverity(severity);
		report.setMessage(message);
		report.setEmfLocation(emfLocation);
		report.setSheetLocation(sheetLocation);
		getErrorReports().add(report);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult#getSheetLocation(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public SheetLocation getSheetLocation(EObject eObject, EStructuralFeature structuralFeature) {
		SheetLocation possibleResult = null;
		for (final SettingToSheetMapping settingToSheetMapping : getSettingToSheetMap()) {
			if (structuralFeature != settingToSheetMapping.getSettingLocation().getFeature()) {
				continue;
			}
			if (possibleResult == null) {
				possibleResult = settingToSheetMapping.getSheetLocation();
			}
			if (eObject != settingToSheetMapping.getSettingLocation().getEObject()) {
				continue;
			}
			return EcoreUtil.copy(settingToSheetMapping.getSheetLocation());
		}
		if (possibleResult != null) {
			final SheetLocation result = EcoreUtil.copy(possibleResult);
			result.setRow(SheetLocation.INVALID_ROW);
			result.setValid(false);
			return result;
		}

		return ErrorFactory.eINSTANCE.createInvalidSheetLocation(getFeatureName(structuralFeature));
	}

	private String getFeatureName(EStructuralFeature structuralFeature) {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle == null) {
			return structuralFeature.getName();
		}
		final BundleContext bundleContext = bundle.getBundleContext();
		if (bundleContext == null) {
			return structuralFeature.getName();
		}

		final ServiceReference<EMFFormsLabelProviderImpl> serviceReference = bundleContext
			.getServiceReference(EMFFormsLabelProviderImpl.class);
		if (serviceReference == null) {
			return structuralFeature.getName();
		}
		final EMFFormsLabelProviderImpl labelProvider = bundleContext.getService(serviceReference);
		if (labelProvider == null) {
			return structuralFeature.getName();
		}

		return labelProvider.getDisplayName(structuralFeature);
	}

} // ErrorReportsImpl
