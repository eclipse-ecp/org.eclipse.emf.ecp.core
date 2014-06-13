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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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
 * @since 1.2
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
	@Override
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
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
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
	@Override
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
	@Override
	public String getMessage() {
		String message = ""; //$NON-NLS-1$
		if (getDiagnostics().size() == 1) {
			final Diagnostic diagnostic = Diagnostic.class.cast(getDiagnostics().get(0));
			if (Diagnostic.OK == diagnostic.getSeverity()) {
				return message;
			}
			if (diagnostic.getChildren() != null && diagnostic.getChildren().size() == 0) {
				return diagnostic.getMessage();
			}
			final StringBuilder sb = new StringBuilder();
			for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
				if (sb.length() > 0)
				{
					sb.append("\n"); //$NON-NLS-1$
				}
				sb.append(childDiagnostic.getMessage());
			}
			message = sb.toString();
		}
		else if (getDiagnostics().size() > 0) {
			// for (final Object o : getDiagnostics()) {
			// final Diagnostic diagnostic = (Diagnostic) o;
			// if (Diagnostic.OK == diagnostic.getSeverity()) {
			// continue;
			// }
			// final String diagnosticMessage = diagnostic.getMessage();
			// message = message.concat(diagnosticMessage + "\n");
			// }
			final Map<EObject, Diagnostic> diagnosticMap = new LinkedHashMap<EObject, Diagnostic>();
			final List<Diagnostic> diagnostics = new ArrayList<Diagnostic>(getDiagnostics().size());
			for (final Object o : getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) o;
				diagnostics.add(diagnostic);
			}
			sortDiagnostics(diagnostics);
			for (final Object o : diagnostics) {
				final Diagnostic diagnostic = (Diagnostic) o;
				if (Diagnostic.OK == diagnostic.getSeverity()) {
					continue;
				}
				if (diagnostic.getData() != null && diagnostic.getData().size() != 0
					&& EObject.class.isInstance(diagnostic.getData().get(0))) {
					if (!diagnosticMap.containsKey(diagnostic.getData().get(0))) {
						diagnosticMap.put((EObject) diagnostic.getData().get(0), diagnostic);
					}
				}
			}
			final StringBuilder sb = new StringBuilder();
			for (final Diagnostic diagnostic : diagnosticMap.values()) {
				if (sb.length() > 0)
				{
					sb.append("\n"); //$NON-NLS-1$
				}
				sb.append(diagnostic.getMessage());
			}
			message = sb.toString();
		}
		return message;
	}

	private void sortDiagnostics(final List<Diagnostic> diagnostics) {
		Collections.sort(diagnostics, new Comparator<Diagnostic>() {

			@Override
			public int compare(Diagnostic o1, Diagnostic o2) {
				if (o1.getSeverity() != o2.getSeverity()) {
					// highest first
					return o2.getSeverity() - o1.getSeverity();
				}
				return o1.getMessage().compareTo(o2.getMessage());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics(org.eclipse.emf.ecore.EObject)
	 * @since 1.3
	 */
	@Override
	public List<Diagnostic> getDiagnostics(EObject eObject) {
		final EList<Diagnostic> result = new BasicEList<Diagnostic>();
		for (final Object objectDiagnostic : getDiagnostics()) {
			final Diagnostic diagnostic = (Diagnostic) objectDiagnostic;
			if (diagnostic.getSeverity() == Diagnostic.OK) {
				continue;
			}
			result.addAll(getDiagnostics(diagnostic, eObject));
		}
		sortDiagnostics(result);
		return result;
	}

	private List<Diagnostic> getDiagnostics(Diagnostic diagnostic, EObject eObject) {
		final List<Diagnostic> result = new ArrayList<Diagnostic>();
		if (diagnostic.getData() != null && diagnostic.getData().size() != 0
			&& eObject.equals(diagnostic.getData().get(0))) {
			result.add(diagnostic);
		}
		// for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
		// if (childDiagnostic.getSeverity() == Diagnostic.OK) {
		// continue;
		// }
		// result.addAll(getDiagnostics(childDiagnostic, eObject));
		// }
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostic(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 * @since 1.3
	 */
	@Override
	public List<Diagnostic> getDiagnostic(EObject eObject, EStructuralFeature eStructuralFeature) {
		final EList<Diagnostic> result = new BasicEList<Diagnostic>();
		for (final Object objectDiagnostic : getDiagnostics()) {
			final Diagnostic diagnostic = (Diagnostic) objectDiagnostic;
			if (diagnostic.getSeverity() == Diagnostic.OK) {
				continue;
			}
			result.addAll(getDiagnostics(diagnostic, eObject, eStructuralFeature));
		}
		sortDiagnostics(result);
		return result;
	}

	private List<Diagnostic> getDiagnostics(Diagnostic diagnostic, EObject eObject,
		EStructuralFeature eStructuralFeature) {
		final List<Diagnostic> result = new ArrayList<Diagnostic>();
		if (diagnostic.getData() != null && diagnostic.getData().size() > 1
			&& eObject.equals(diagnostic.getData().get(0)) && eStructuralFeature.equals(diagnostic.getData().get(1))) {
			if (diagnostic.getChildren() == null || diagnostic.getChildren().size() == 0) {
				result.add(diagnostic);
			} else {
				for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
					if (childDiagnostic.getSeverity() == Diagnostic.OK) {
						continue;
					}
					// result.addAll(getDiagnostics(childDiagnostic, eObject, eStructuralFeature));
					result.add(childDiagnostic);
				}
			}
		}
		return result;
	}

} // VDiagnosticImpl
