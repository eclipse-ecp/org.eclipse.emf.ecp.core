/**
 */
package test.localmodel.localmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import test.localmodel.localmodel.LocalmodelPackage;
import test.localmodel.localmodel.TestClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link test.localmodel.localmodel.impl.TestClassImpl#getTestAttribute <em>Test Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestClassImpl extends MinimalEObjectImpl.Container implements TestClass {
	/**
	 * The default value of the '{@link #getTestAttribute() <em>Test Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTestAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String TEST_ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTestAttribute() <em>Test Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTestAttribute()
	 * @generated
	 * @ordered
	 */
	protected String testAttribute = TEST_ATTRIBUTE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LocalmodelPackage.Literals.TEST_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTestAttribute() {
		return testAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTestAttribute(String newTestAttribute) {
		final String oldTestAttribute = testAttribute;
		testAttribute = newTestAttribute;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LocalmodelPackage.TEST_CLASS__TEST_ATTRIBUTE,
				oldTestAttribute, testAttribute));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LocalmodelPackage.TEST_CLASS__TEST_ATTRIBUTE:
			return getTestAttribute();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LocalmodelPackage.TEST_CLASS__TEST_ATTRIBUTE:
			setTestAttribute((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LocalmodelPackage.TEST_CLASS__TEST_ATTRIBUTE:
			setTestAttribute(TEST_ATTRIBUTE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LocalmodelPackage.TEST_CLASS__TEST_ATTRIBUTE:
			return TEST_ATTRIBUTE_EDEFAULT == null ? testAttribute != null
				: !TEST_ATTRIBUTE_EDEFAULT.equals(testAttribute);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (testAttribute: ");
		result.append(testAttribute);
		result.append(')');
		return result.toString();
	}

} // TestClassImpl
