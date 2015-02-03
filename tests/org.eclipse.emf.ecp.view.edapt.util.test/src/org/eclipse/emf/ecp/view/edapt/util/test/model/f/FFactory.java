/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.f;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.f.FPackage
 * @generated
 */
public interface FFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	FFactory eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl.FFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>F</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>F</em>'.
	 * @generated
	 */
	F createF();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	FPackage getFPackage();

} // FFactory
