/**
 */
package test.localmodel.localmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import test.localmodel.localmodel.LocalmodelFactory;
import test.localmodel.localmodel.LocalmodelPackage;
import test.localmodel.localmodel.TestClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class LocalmodelFactoryImpl extends EFactoryImpl implements LocalmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static LocalmodelFactory init() {
		try {
			final LocalmodelFactory theLocalmodelFactory = (LocalmodelFactory) EPackage.Registry.INSTANCE
				.getEFactory(LocalmodelPackage.eNS_URI);
			if (theLocalmodelFactory != null) {
				return theLocalmodelFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LocalmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LocalmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case LocalmodelPackage.TEST_CLASS:
			return createTestClass();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TestClass createTestClass() {
		final TestClassImpl testClass = new TestClassImpl();
		return testClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LocalmodelPackage getLocalmodelPackage() {
		return (LocalmodelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LocalmodelPackage getPackage() {
		return LocalmodelPackage.eINSTANCE;
	}

} // LocalmodelFactoryImpl
