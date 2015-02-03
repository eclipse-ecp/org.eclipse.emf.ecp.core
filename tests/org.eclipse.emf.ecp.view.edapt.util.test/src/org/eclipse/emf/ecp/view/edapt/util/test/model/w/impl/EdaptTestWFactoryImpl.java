/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.w.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestW;
import org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestWFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.w.EdaptTestWPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class EdaptTestWFactoryImpl extends EFactoryImpl implements EdaptTestWFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EdaptTestWFactory init() {
		try {
			final EdaptTestWFactory theWFactory = (EdaptTestWFactory) EPackage.Registry.INSTANCE
				.getEFactory(EdaptTestWPackage.eNS_URI);
			if (theWFactory != null) {
				return theWFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EdaptTestWFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EdaptTestWFactoryImpl() {
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
		case EdaptTestWPackage.W:
			return createW();
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
	public EdaptTestW createW() {
		final EdaptTestWImpl w = new EdaptTestWImpl();
		return w;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EdaptTestWPackage getWPackage() {
		return (EdaptTestWPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EdaptTestWPackage getPackage() {
		return EdaptTestWPackage.eINSTANCE;
	}

} // EdaptTestWFactoryImpl
