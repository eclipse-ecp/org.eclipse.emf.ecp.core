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
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.EdaptTestEPackage
 * @generated
 */
public interface EdaptTestEFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	EdaptTestEFactory eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EdaptTestEFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>E</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>E</em>'.
	 * @generated
	 */
	EdaptTestE createE();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	@Override
	EdaptTestEPackage getEPackage();

} // EdaptTestEFactory
