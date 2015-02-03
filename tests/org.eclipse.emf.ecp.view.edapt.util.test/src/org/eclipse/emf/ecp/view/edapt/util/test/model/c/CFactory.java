/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.c;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage
 * @generated
 */
public interface CFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	CFactory eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.c.impl.CFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>C</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>C</em>'.
	 * @generated
	 */
	C createC();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	CPackage getCPackage();

} // CFactory
