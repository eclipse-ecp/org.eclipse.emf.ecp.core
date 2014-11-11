/**
 */
package org.eclipse.emf.ecp.view.validation.test.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Without Multiplicity Concrete</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.ecp.view.validation.test.model.TableWithoutMultiplicityConcrete#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getTableWithoutMultiplicityConcrete()
 * @model
 * @generated
 */
public interface TableWithoutMultiplicityConcrete extends EObject {
	/**
	 * Returns the value of the '<em><b>Content</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.validation.test.model.TableContentWithInnerChild}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.validation.test.model.TestPackage#getTableWithoutMultiplicityConcrete_Content()
	 * @model containment="true"
	 * @generated
	 */
	EList<TableContentWithInnerChild> getContent();

} // TableWithoutMultiplicityConcrete
