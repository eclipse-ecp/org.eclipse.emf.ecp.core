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
package org.eclipse.emf.ecp.view.spi.model;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VDiagnostic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics <em>Diagnostics</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDiagnostic()
 * @model
 * @generated
 * @since 1.2
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface VDiagnostic extends EObject
{
	/**
	 * Returns the value of the '<em><b>Diagnostics</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Object}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagnostics</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Diagnostics</em>' attribute list.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getDiagnostic_Diagnostics()
	 * @model transient="true"
	 * @generated
	 */
	EList<Object> getDiagnostics();

	/**
	 * Returns the highest severity found in the diagnostics.
	 *
	 * @return the highest severity
	 */
	int getHighestSeverity();

	/**
	 * Returns the message associated with this validation.
	 * The message contains only the highest known severity for each EObject.
	 *
	 * @return the message
	 */
	String getMessage();

	/**
	 * Returns all diagnostics for the provided {@link EObject}. The result is sorted by severity and message.
	 *
	 * @param eObject the {@link EObject} to search diagnostics for
	 * @return the list of sorted diagnostics
	 * @since 1.3
	 */
	List<Diagnostic> getDiagnostics(EObject eObject);

	/**
	 * Returns all diagnostics for the provided {@link EObject} and {@link EStructuralFeature}. The result is sorted by
	 * severity and message.
	 *
	 * @param eObject the {@link EObject} to search diagnostics for
	 * @param eStructuralFeature the {@link EStructuralFeature} to search diagnostics for
	 * @return the list of sorted diagnostics
	 * @since 1.3
	 */
	List<Diagnostic> getDiagnostic(EObject eObject, EStructuralFeature eStructuralFeature);

} // VDiagnostic
