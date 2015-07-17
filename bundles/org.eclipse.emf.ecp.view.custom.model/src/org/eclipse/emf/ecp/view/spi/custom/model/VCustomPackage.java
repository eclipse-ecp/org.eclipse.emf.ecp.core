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
 *
 * @noimplement This interface is not intended to be implemented by clients.
 *              <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory
 * @model kind="package"
 * @generated
 *
 */
public interface VCustomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "custom"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/custom/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.custom.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VCustomPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl
	 * <em>Control</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getCustomControl()
	 * @generated
	 */
	int CUSTOM_CONTROL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__NAME = VViewPackage.CONTROL__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__VISIBLE = VViewPackage.CONTROL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__ENABLED = VViewPackage.CONTROL__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__READONLY = VViewPackage.CONTROL__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__DIAGNOSTIC = VViewPackage.CONTROL__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__ATTACHMENTS = VViewPackage.CONTROL__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__LABEL_ALIGNMENT = VViewPackage.CONTROL__LABEL_ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__DOMAIN_MODEL_REFERENCE = VViewPackage.CONTROL__DOMAIN_MODEL_REFERENCE;

	/**
	 * The feature id for the '<em><b>Bundle Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__BUNDLE_NAME = VViewPackage.CONTROL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL__CLASS_NAME = VViewPackage.CONTROL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Control</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_CONTROL_FEATURE_COUNT = VViewPackage.CONTROL_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl
	 * <em>Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getCustomDomainModelReference()
	 * @generated
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Domain Model References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Bundle Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Control Checked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CUSTOM_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 4;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl
	 * <em>Control</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Control</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl
	 * @generated
	 */
	EClass getCustomControl();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getBundleName <em>Bundle Name</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bundle Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getBundleName()
	 * @see #getCustomControl()
	 * @generated
	 */
	EAttribute getCustomControl_BundleName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl#getClassName()
	 * @see #getCustomControl()
	 * @generated
	 */
	EAttribute getCustomControl_ClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference
	 * @generated
	 */
	EClass getCustomDomainModelReference();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getDomainModelReferences
	 * <em>Domain Model References</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Domain Model References</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getDomainModelReferences()
	 * @see #getCustomDomainModelReference()
	 * @generated
	 */
	EReference getCustomDomainModelReference_DomainModelReferences();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getBundleName <em>Bundle Name</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Bundle Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getBundleName()
	 * @see #getCustomDomainModelReference()
	 * @generated
	 */
	EAttribute getCustomDomainModelReference_BundleName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#getClassName()
	 * @see #getCustomDomainModelReference()
	 * @generated
	 */
	EAttribute getCustomDomainModelReference_ClassName();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#isControlChecked
	 * <em>Control Checked</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Control Checked</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference#isControlChecked()
	 * @see #getCustomDomainModelReference()
	 * @generated
	 */
	EAttribute getCustomDomainModelReference_ControlChecked();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl
		 * <em>Control</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getCustomControl()
		 * @generated
		 */
		EClass CUSTOM_CONTROL = eINSTANCE.getCustomControl();

		/**
		 * The meta object literal for the '<em><b>Bundle Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOM_CONTROL__BUNDLE_NAME = eINSTANCE.getCustomControl_BundleName();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOM_CONTROL__CLASS_NAME = eINSTANCE.getCustomControl_ClassName();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl
		 * <em>Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomPackageImpl#getCustomDomainModelReference()
		 * @generated
		 */
		EClass CUSTOM_DOMAIN_MODEL_REFERENCE = eINSTANCE.getCustomDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Domain Model References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES = eINSTANCE
			.getCustomDomainModelReference_DomainModelReferences();

		/**
		 * The meta object literal for the '<em><b>Bundle Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME = eINSTANCE.getCustomDomainModelReference_BundleName();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME = eINSTANCE.getCustomDomainModelReference_ClassName();

		/**
		 * The meta object literal for the '<em><b>Control Checked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED = eINSTANCE
			.getCustomDomainModelReference_ControlChecked();

	}

} // VCustomPackage
