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
package org.eclipse.emf.ecp.view.spi.indexdmr.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory
 * @model kind="package"
 * @generated
 */
public interface VIndexdmrPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "indexdmr"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/indexdmr/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.indexdmr.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VIndexdmrPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexdmrPackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexDomainModelReferenceImpl
	 * <em>Index Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexdmrPackageImpl#getIndexDomainModelReference()
	 * @generated
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE;

	/**
	 * The feature id for the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH;

	/**
	 * The feature id for the '<em><b>Target DMR</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE__INDEX = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Index Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int INDEX_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference
	 * <em>Index Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Index Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference
	 * @generated
	 */
	EClass getIndexDomainModelReference();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getTargetDMR <em>Target DMR</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Target DMR</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getTargetDMR()
	 * @see #getIndexDomainModelReference()
	 * @generated
	 */
	EReference getIndexDomainModelReference_TargetDMR();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference#getIndex()
	 * @see #getIndexDomainModelReference()
	 * @generated
	 */
	EAttribute getIndexDomainModelReference_Index();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VIndexdmrFactory getIndexdmrFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
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
	interface Literals
	{
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexDomainModelReferenceImpl
		 * <em>Index Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.indexdmr.model.impl.VIndexdmrPackageImpl#getIndexDomainModelReference()
		 * @generated
		 */
		EClass INDEX_DOMAIN_MODEL_REFERENCE = eINSTANCE.getIndexDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Target DMR</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR = eINSTANCE.getIndexDomainModelReference_TargetDMR();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute INDEX_DOMAIN_MODEL_REFERENCE__INDEX = eINSTANCE.getIndexDomainModelReference_Index();

	}

} // VIndexdmrPackage
