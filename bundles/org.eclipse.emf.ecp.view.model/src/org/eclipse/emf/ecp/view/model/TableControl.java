/**
 */
package org.eclipse.emf.ecp.view.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Control</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.model.TableControl#getColumns <em>Columns</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.model.TableControl#isAddRemoveDisabled <em>Add Remove Disabled</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.ecp.view.model.ViewPackage#getTableControl()
 * @model
 * @generated
 */
public interface TableControl extends Control {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecp.view.model.TableColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' containment reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see org.eclipse.emf.ecp.view.model.ViewPackage#getTableControl_Columns()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TableColumn> getColumns();

	/**
	 * Returns the value of the '<em><b>Add Remove Disabled</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Add Remove Disabled</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Add Remove Disabled</em>' attribute.
	 * @see #setAddRemoveDisabled(boolean)
	 * @see org.eclipse.emf.ecp.view.model.ViewPackage#getTableControl_AddRemoveDisabled()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isAddRemoveDisabled();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.view.model.TableControl#isAddRemoveDisabled
	 * <em>Add Remove Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Add Remove Disabled</em>' attribute.
	 * @see #isAddRemoveDisabled()
	 * @generated
	 */
	void setAddRemoveDisabled(boolean value);

} // TableControl
