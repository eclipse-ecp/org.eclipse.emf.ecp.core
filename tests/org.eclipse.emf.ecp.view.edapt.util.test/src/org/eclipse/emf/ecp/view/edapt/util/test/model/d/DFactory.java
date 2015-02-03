/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.d;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage
 * @generated
 */
public interface DFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DFactory eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl.DFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>D</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>D</em>'.
	 * @generated
	 */
	D createD();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DPackage getDPackage();

} // DFactory
