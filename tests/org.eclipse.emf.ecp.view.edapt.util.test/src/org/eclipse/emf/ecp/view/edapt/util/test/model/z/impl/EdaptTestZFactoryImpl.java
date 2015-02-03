/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.z.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZ;
import org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.z.EdaptTestZPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class EdaptTestZFactoryImpl extends EFactoryImpl implements EdaptTestZFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EdaptTestZFactory init() {
		try {
			final EdaptTestZFactory theZFactory = (EdaptTestZFactory) EPackage.Registry.INSTANCE
				.getEFactory(EdaptTestZPackage.eNS_URI);
			if (theZFactory != null) {
				return theZFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EdaptTestZFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EdaptTestZFactoryImpl() {
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
		case EdaptTestZPackage.Z:
			return createZ();
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
	public EdaptTestZ createZ() {
		final EdaptTestZImpl z = new EdaptTestZImpl();
		return z;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EdaptTestZPackage getZPackage() {
		return (EdaptTestZPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EdaptTestZPackage getPackage() {
		return EdaptTestZPackage.eINSTANCE;
	}

} // EdaptTestZFactoryImpl
