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

import java.util.Collection;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Control</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *
 *        <p>
 *        The following features are supported:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VControl#getLabelAlignment <em>Label Alignment</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.VControl#getDomainModelReference <em>Domain Model Reference</em>
 *        }</li>
 *        </ul>
 *
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getControl()
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
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getControl_DomainModelReference()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VDomainModelReference getDomainModelReference();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VControl#getDomainModelReference
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
	 * The default value is <code>"Default"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Alignment</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * @see #setLabelAlignment(LabelAlignment)
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#getControl_LabelAlignment()
	 * @model default="Default" required="true"
	 * @generated
	 */
	LabelAlignment getLabelAlignment();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.spi.model.VControl#getLabelAlignment
	 * <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Label Alignment</em>' attribute.
	 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * @see #getLabelAlignment()
	 * @generated
	 */
	void setLabelAlignment(LabelAlignment value);

	/**
	 * Sets the {@link VDomainModelReference} based on the provided {@link EStructuralFeature}. Thus a
	 * {@link VFeaturePathDomainModelReference} is created.
	 *
	 * @param feature the {@link EStructuralFeature} to create the {@link VDomainModelReference} for
	 */
	void setDomainModelReference(EStructuralFeature feature);

	/**
	 * Sets the {@link VDomainModelReference} based on the provided {@link EStructuralFeature} and the collection of
	 * {@link EReference EReferences}. Thus a {@link VFeaturePathDomainModelReference} is created.
	 *
	 * @param feature the {@link EStructuralFeature} to create the {@link VDomainModelReference} for
	 * @param referencePath the {@link Collection} of {@link EReference EReferences} being the reference path
	 */
	void setDomainModelReference(EStructuralFeature feature,
		Collection<EReference> referencePath);

} // Control
