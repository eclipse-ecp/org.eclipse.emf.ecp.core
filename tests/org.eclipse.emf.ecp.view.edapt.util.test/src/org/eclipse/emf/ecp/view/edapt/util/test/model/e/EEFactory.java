/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.e;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.EEPackage
 * @generated
 */
public interface EEFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	EEFactory eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EEFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>E</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>E</em>'.
	 * @generated
	 */
	E createE();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	EEPackage getEEPackage();

} // EEFactory
