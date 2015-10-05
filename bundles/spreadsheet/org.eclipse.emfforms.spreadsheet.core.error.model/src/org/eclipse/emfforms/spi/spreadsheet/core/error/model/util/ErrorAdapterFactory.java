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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage
 * @generated
 */
public class ErrorAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static ErrorPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ErrorAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ErrorPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model.
	 * <!-- end-user-doc -->
	 *
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ErrorSwitch<Adapter> modelSwitch = new ErrorSwitch<Adapter>() {
		@Override
		public Adapter caseSpreadsheetImportResult(SpreadsheetImportResult object) {
			return createSpreadsheetImportResultAdapter();
		}

		@Override
		public Adapter caseErrorReport(ErrorReport object) {
			return createErrorReportAdapter();
		}

		@Override
		public Adapter caseSheetLocation(SheetLocation object) {
			return createSheetLocationAdapter();
		}

		@Override
		public Adapter caseEMFLocation(EMFLocation object) {
			return createEMFLocationAdapter();
		}

		@Override
		public Adapter caseSettingLocation(SettingLocation object) {
			return createSettingLocationAdapter();
		}

		@Override
		public Adapter caseDMRLocation(DMRLocation object) {
			return createDMRLocationAdapter();
		}

		@Override
		public Adapter caseSettingToSheetMapping(SettingToSheetMapping object) {
			return createSettingToSheetMappingAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult
	 * <em>Spreadsheet Import Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult
	 * @generated
	 */
	public Adapter createSpreadsheetImportResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport <em>Report</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport
	 * @generated
	 */
	public Adapter createErrorReportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation <em>EMF Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation
	 * @generated
	 */
	public Adapter createEMFLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation <em>Setting Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation
	 * @generated
	 */
	public Adapter createSettingLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation <em>DMR Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation
	 * @generated
	 */
	public Adapter createDMRLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping
	 * <em>Setting To Sheet Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingToSheetMapping
	 * @generated
	 */
	public Adapter createSettingToSheetMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation <em>Sheet Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation
	 * @generated
	 */
	public Adapter createSheetLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 *
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // ErrorAdapterFactory
