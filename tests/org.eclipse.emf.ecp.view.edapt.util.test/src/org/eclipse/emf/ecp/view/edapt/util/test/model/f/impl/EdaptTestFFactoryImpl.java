/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestF;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestFFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.EdaptTestFPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class EdaptTestFFactoryImpl extends EFactoryImpl implements EdaptTestFFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EdaptTestFFactory init() {
		try {
			final EdaptTestFFactory theFFactory = (EdaptTestFFactory) EPackage.Registry.INSTANCE
				.getEFactory(EdaptTestFPackage.eNS_URI);
			if (theFFactory != null) {
				return theFFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EdaptTestFFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EdaptTestFFactoryImpl() {
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
		case EdaptTestFPackage.F:
			return createF();
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
	public EdaptTestF createF() {
		final EdaptTestFImpl f = new EdaptTestFImpl();
		return f;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EdaptTestFPackage getFPackage() {
		return (EdaptTestFPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EdaptTestFPackage getPackage() {
		return EdaptTestFPackage.eINSTANCE;
	}

} // EdaptTestFFactoryImpl
