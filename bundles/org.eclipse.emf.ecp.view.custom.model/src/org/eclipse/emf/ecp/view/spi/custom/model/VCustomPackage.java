/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model;

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
 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory
 * @model kind="package"
 * @generated
 */
public interface VCustomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "custom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/custom/model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.custom.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	VCustomPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VHardcodedDomainModelReferenceImpl
	 * <em>Hardcoded Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VHardcodedDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getHardcodedDomainModelReference()
	 * @generated
	 */
	int HARDCODED_DOMAIN_MODEL_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Control Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HARDCODED_DOMAIN_MODEL_REFERENCE__CONTROL_ID = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HARDCODED_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Control Checked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HARDCODED_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Hardcoded Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int HARDCODED_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference
	 * <em>Hardcoded Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Hardcoded Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference
	 * @generated
	 */
	EClass getHardcodedDomainModelReference();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#getControlId <em>Control Id</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Control Id</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#getControlId()
	 * @see #getHardcodedDomainModelReference()
	 * @generated
	 */
	EAttribute getHardcodedDomainModelReference_ControlId();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#getDomainModelReferences
	 * <em>Domain Model References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Domain Model References</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#getDomainModelReferences()
	 * @see #getHardcodedDomainModelReference()
	 * @generated
	 */
	EReference getHardcodedDomainModelReference_DomainModelReferences();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#isControlChecked
	 * <em>Control Checked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Control Checked</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference#isControlChecked()
	 * @see #getHardcodedDomainModelReference()
	 * @generated
	 */
	EAttribute getHardcodedDomainModelReference_ControlChecked();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VCustomFactory getCustomFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VHardcodedDomainModelReferenceImpl
		 * <em>Hardcoded Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VHardcodedDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getHardcodedDomainModelReference()
		 * @generated
		 */
		EClass HARDCODED_DOMAIN_MODEL_REFERENCE = eINSTANCE.getHardcodedDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Control Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HARDCODED_DOMAIN_MODEL_REFERENCE__CONTROL_ID = eINSTANCE
			.getHardcodedDomainModelReference_ControlId();

		/**
		 * The meta object literal for the '<em><b>Domain Model References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference HARDCODED_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES = eINSTANCE
			.getHardcodedDomainModelReference_DomainModelReferences();

		/**
		 * The meta object literal for the '<em><b>Control Checked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute HARDCODED_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED = eINSTANCE
			.getHardcodedDomainModelReference_ControlChecked();

	}

} // VCustomPackage
