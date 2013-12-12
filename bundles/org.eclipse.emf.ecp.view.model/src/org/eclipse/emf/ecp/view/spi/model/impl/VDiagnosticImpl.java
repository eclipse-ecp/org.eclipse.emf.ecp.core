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
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VDiagnostic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl#getDiagnostics <em>Diagnostics</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VDiagnosticImpl extends EObjectImpl implements VDiagnostic
{
	/**
	 * The cached value of the '{@link #getDiagnostics() <em>Diagnostics</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDiagnostics()
	 * @generated
	 * @ordered
	 */
	protected EList<Object> diagnostics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VDiagnosticImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VViewPackage.Literals.DIAGNOSTIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Object> getDiagnostics()
	{
		if (diagnostics == null)
		{
			diagnostics = new EDataTypeUniqueEList<Object>(Object.class, this, VViewPackage.DIAGNOSTIC__DIAGNOSTICS);
		}
		return diagnostics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			return getDiagnostics();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			getDiagnostics().clear();
			getDiagnostics().addAll((Collection<? extends Object>) newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			getDiagnostics().clear();
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VViewPackage.DIAGNOSTIC__DIAGNOSTICS:
			return diagnostics != null && !diagnostics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (diagnostics: "); //$NON-NLS-1$
		result.append(diagnostics);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getHighestSeverity()
	 */
	public int getHighestSeverity() {
		int highestSeverity = Diagnostic.OK;
		if (getDiagnostics().size() > 0) {
			for (final Object o : getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) o;
				highestSeverity = highestSeverity >= diagnostic.getSeverity() ? highestSeverity : diagnostic
					.getSeverity();
			}
		}
		return highestSeverity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getMessage()
	 */
	public String getMessage() {
		String message = "";
		if (getDiagnostics().size() > 0) {
			for (final Object o : getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) o;
				final String diagnosticMessage = diagnostic.getMessage();
				message = message.concat(diagnosticMessage + "\n");
			}
		}
		return message;
	}

} // VDiagnosticImpl
