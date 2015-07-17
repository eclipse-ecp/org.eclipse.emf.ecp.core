/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrFactory
 * @model kind="package"
 * @generated
 */
public interface VKeyattributedmrPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "keyattributedmr"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/keyattributedmr/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.keyattributedmr.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VKeyattributedmrPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyattributedmrPackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl
	 * <em>Key Attribute Domain Model Reference</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyattributedmrPackageImpl#getKeyAttributeDomainModelReference()
	 * @generated
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE;

	/**
	 * The feature id for the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH;

	/**
	 * The feature id for the '<em><b>Key DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Key Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the ' <em>Key Attribute Domain Model Reference</em>' class. <!--
	 * begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference
	 * <em>Key Attribute Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Key Attribute Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference
	 * @generated
	 */
	EClass getKeyAttributeDomainModelReference();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyDMR
	 * <em>Key DMR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Key DMR</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyDMR()
	 * @see #getKeyAttributeDomainModelReference()
	 * @generated
	 */
	EReference getKeyAttributeDomainModelReference_KeyDMR();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyValue
	 * <em>Key Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Key Value</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getKeyValue()
	 * @see #getKeyAttributeDomainModelReference()
	 * @generated
	 */
	EAttribute getKeyAttributeDomainModelReference_KeyValue();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getValueDMR
	 * <em>Value DMR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Value DMR</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference#getValueDMR()
	 * @see #getKeyAttributeDomainModelReference()
	 * @generated
	 */
	EReference getKeyAttributeDomainModelReference_ValueDMR();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VKeyattributedmrFactory getKeyattributedmrFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl
		 * <em>Key Attribute Domain Model Reference</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyattributedmrPackageImpl#getKeyAttributeDomainModelReference()
		 * @generated
		 */
		EClass KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE = eINSTANCE.getKeyAttributeDomainModelReference();
		/**
		 * The meta object literal for the '<em><b>Key DMR</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR = eINSTANCE
			.getKeyAttributeDomainModelReference_KeyDMR();
		/**
		 * The meta object literal for the '<em><b>Key Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE = eINSTANCE
			.getKeyAttributeDomainModelReference_KeyValue();
		/**
		 * The meta object literal for the '<em><b>Value DMR</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR = eINSTANCE
			.getKeyAttributeDomainModelReference_ValueDMR();

	}

} // VKeyattributedmrPackage
