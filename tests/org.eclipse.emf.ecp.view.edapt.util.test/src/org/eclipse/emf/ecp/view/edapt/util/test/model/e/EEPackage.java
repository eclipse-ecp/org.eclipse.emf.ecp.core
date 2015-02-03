/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.e;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.EEFactory
 * @model kind="package"
 * @generated
 */
public interface EEPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "e";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://example.org/e";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "e";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	EEPackage eINSTANCE = org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EEPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl <em>E</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EEPackageImpl#getE()
	 * @generated
	 */
	int E = 0;

	/**
	 * The feature id for the '<em><b>D</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int E__D = 0;

	/**
	 * The feature id for the '<em><b>F</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int E__F = 1;

	/**
	 * The number of structural features of the '<em>E</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int E_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>E</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int E_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.E <em>E</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>E</em>'.
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.E
	 * @generated
	 */
	EClass getE();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.E#getD
	 * <em>D</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>D</em>'.
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.E#getD()
	 * @see #getE()
	 * @generated
	 */
	EReference getE_D();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.E#getF
	 * <em>F</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>F</em>'.
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.E#getF()
	 * @see #getE()
	 * @generated
	 */
	EReference getE_F();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EEFactory getEEFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl
		 * <em>E</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl
		 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EEPackageImpl#getE()
		 * @generated
		 */
		EClass E = eINSTANCE.getE();

		/**
		 * The meta object literal for the '<em><b>D</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference E__D = eINSTANCE.getE_D();

		/**
		 * The meta object literal for the '<em><b>F</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference E__F = eINSTANCE.getE_F();

	}

} // EEPackage
