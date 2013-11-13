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
package org.eclipse.emf.ecp.view.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.model.VControl#getLabelAlignment <em>Label Alignment</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.model.VControl#getDomainModelReference <em>Domain Model Reference</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getControl()
 * @model
 * @generated
 */
public interface VControl extends VContainedElement {
	/**
	 * Returns the value of the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Reference</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #setDomainModelReference(VDomainModelReference)
	 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getControl_DomainModelReference()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.model.VControl#getDomainModelReference
	 * <em>Domain Model Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Domain Model Reference</em>' containment reference.
	 * @see #getDomainModelReference()
	 * @generated
	 */
	void setDomainModelReference(VDomainModelReference value);

	/**
	 * Returns the value of the '<em><b>Label Alignment</b></em>' attribute.
	 * The default value is <code>"Left"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.model.LabelAlignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Alignment</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.model.LabelAlignment
	 * @see #setLabelAlignment(LabelAlignment)
	 * @see org.eclipse.emf.ecp.view.model.VViewPackage#getControl_LabelAlignment()
	 * @model default="Left" required="true"
	 * @generated
	 */
	LabelAlignment getLabelAlignment();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.model.VControl#getLabelAlignment <em>Label Alignment</em>}
	 * ' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.model.LabelAlignment
	 * @see #getLabelAlignment()
	 * @generated
	 */
	void setLabelAlignment(LabelAlignment value);

} // Control
