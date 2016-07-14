// CHECKSTYLE.OFF: FileLength|RegexpHeader
/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @noextend This interface is not intended to be extended by clients.
 * @since 1.2
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.model.VViewFactory
 * @model kind="package"
 * @generated
 */
public interface VViewPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "model"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/model/170"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	VViewPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl <em>Diagnostic</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDiagnostic()
	 * @generated
	 */
	int DIAGNOSTIC = 0;

	/**
	 * The feature id for the '<em><b>Diagnostics</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGNOSTIC__DIAGNOSTICS = 0;

	/**
	 * The number of structural features of the '<em>Diagnostic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGNOSTIC_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl <em>Control</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getControl()
	 * @generated
	 */
	int CONTROL = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VAttachmentImpl <em>Attachment</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VAttachmentImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getAttachment()
	 * @generated
	 */
	int ATTACHMENT = 1;

	/**
	 * The number of structural features of the '<em>Attachment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTACHMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl <em>Domain
	 * Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReference()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE__SEGMENTS = 1;

	/**
	 * The number of structural features of the '<em>Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl
	 * <em>Feature Path Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getFeaturePathDomainModelReference()
	 * @generated
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE__SEGMENTS = DOMAIN_MODEL_REFERENCE__SEGMENTS;

	/**
	 * The feature id for the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Feature Path Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__VISIBLE = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ENABLED = 2;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__READONLY = 3;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__DIAGNOSTIC = 4;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ATTACHMENTS = 5;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__UUID = 6;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__VISIBLE = ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__ENABLED = ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__READONLY = ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__DIAGNOSTIC = ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__ATTACHMENTS = ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__UUID = ELEMENT__UUID;

	/**
	 * The feature id for the '<em><b>Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__ROOT_ECLASS = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ecore Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ECORE_PATH = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VIEW__CHILDREN = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Loading Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__LOADING_PROPERTIES = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl <em>Contained
	 * Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedElement()
	 * @generated
	 */
	int CONTAINED_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__VISIBLE = ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__ENABLED = ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__READONLY = ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__DIAGNOSTIC = ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__ATTACHMENTS = ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__UUID = ELEMENT__UUID;

	/**
	 * The number of structural features of the '<em>Contained Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainerImpl <em>Container</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainerImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__NAME = ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__VISIBLE = ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__ENABLED = ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__READONLY = ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__DIAGNOSTIC = ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__ATTACHMENTS = ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__UUID = ELEMENT__UUID;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER__CHILDREN = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl <em>Contained
	 * Container</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedContainer()
	 * @generated
	 */
	int CONTAINED_CONTAINER = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__NAME = CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__VISIBLE = CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__ENABLED = CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__READONLY = CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__DIAGNOSTIC = CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__ATTACHMENTS = CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__UUID = CONTAINED_ELEMENT__UUID;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__CHILDREN = CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Contained Container</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER_FEATURE_COUNT = CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__NAME = CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__VISIBLE = CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__ENABLED = CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__READONLY = CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__DIAGNOSTIC = CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__ATTACHMENTS = CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL__UUID = CONTAINED_ELEMENT__UUID;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__DOMAIN_MODEL_REFERENCE = CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL__LABEL_ALIGNMENT = CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Control</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTROL_FEATURE_COUNT = CONTAINED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelProperties <em>Model
	 * Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelProperties
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getViewModelProperties()
	 * @generated
	 */
	int VIEW_MODEL_PROPERTIES = 10;

	/**
	 * The number of structural features of the '<em>Model Properties</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_PROPERTIES_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl
	 * <em>Model Loading Properties</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getViewModelLoadingProperties()
	 * @generated
	 */
	int VIEW_MODEL_LOADING_PROPERTIES = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VStringToObjectMapEntryImpl <em>String
	 * To Object Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VStringToObjectMapEntryImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getStringToObjectMapEntry()
	 * @generated
	 */
	int STRING_TO_OBJECT_MAP_ENTRY = 11;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP_ENTRY__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP_ENTRY__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To Object Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_OBJECT_MAP_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Inheritable Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES = VIEW_MODEL_PROPERTIES_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Non Inheritable Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES = VIEW_MODEL_PROPERTIES_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Model Loading Properties</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_MODEL_LOADING_PROPERTIES_FEATURE_COUNT = VIEW_MODEL_PROPERTIES_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDateTimeDisplayAttachmentImpl
	 * <em>Date Time Display Attachment</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDateTimeDisplayAttachmentImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDateTimeDisplayAttachment()
	 * @generated
	 */
	int DATE_TIME_DISPLAY_ATTACHMENT = 13;

	/**
	 * The feature id for the '<em><b>Display Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE = ATTACHMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Date Time Display Attachment</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_DISPLAY_ATTACHMENT_FEATURE_COUNT = ATTACHMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment <em>Domain
	 * Model Reference Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReferenceSegment()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE_SEGMENT = 14;

	/**
	 * The number of structural features of the '<em>Domain Model Reference Segment</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl <em>Feature Domain Model
	 * Reference Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getFeatureDomainModelReferenceSegment()
	 * @generated
	 */
	int FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT = 15;

	/**
	 * The feature id for the '<em><b>Domain Model Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE = DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Feature Domain Model Reference Segment</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT = DOMAIN_MODEL_REFERENCE_SEGMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment <em>Label Alignment</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getLabelAlignment()
	 * @generated
	 */
	int LABEL_ALIGNMENT = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType <em>Date Time Display
	 * Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDateTimeDisplayType()
	 * @generated
	 */
	int DATE_TIME_DISPLAY_TYPE = 17;

	/**
	 * The meta object id for the '<em>Domain Model Reference Change Listener</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReferenceChangeListener()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE_CHANGE_LISTENER = 18;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#getName()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isVisible
	 * <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#isVisible()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Visible();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isEnabled
	 * <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#isEnabled()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Enabled();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#isReadonly
	 * <em>Readonly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Readonly</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#isReadonly()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Readonly();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getDiagnostic <em>Diagnostic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Diagnostic</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#getDiagnostic()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Diagnostic();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getAttachments <em>Attachments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Attachments</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#getAttachments()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Attachments();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getUuid
	 * <em>Uuid</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uuid</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#getUuid()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Uuid();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic <em>Diagnostic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Diagnostic</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic
	 * @generated
	 */
	EClass getDiagnostic();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics <em>Diagnostics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Diagnostics</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics()
	 * @see #getDiagnostic()
	 * @generated
	 */
	EAttribute getDiagnostic_Diagnostics();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VView <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>View</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass <em>Root
	 * EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Root EClass</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_RootEClass();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VView#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView#getChildren()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Children();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VView#getEcorePath <em>Ecore
	 * Path</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ecore Path</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView#getEcorePath()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_EcorePath();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VView#getLoadingProperties <em>Loading Properties</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Loading Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VView#getLoadingProperties()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_LoadingProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedElement <em>Contained
	 * Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Contained Element</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainedElement
	 * @generated
	 */
	EClass getContainedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VControl <em>Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Control</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VControl
	 * @generated
	 */
	EClass getControl();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VControl#getDomainModelReference <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VControl#getDomainModelReference()
	 * @see #getControl()
	 * @generated
	 */
	EReference getControl_DomainModelReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties
	 * <em>Model Loading Properties</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Loading Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties
	 * @generated
	 */
	EClass getViewModelLoadingProperties();

	/**
	 * Returns the meta object for the map
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getInheritableProperties <em>Inheritable
	 * Properties</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Inheritable Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getInheritableProperties()
	 * @see #getViewModelLoadingProperties()
	 * @generated
	 */
	EReference getViewModelLoadingProperties_InheritableProperties();

	/**
	 * Returns the meta object for the map
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getNonInheritableProperties <em>Non
	 * Inheritable Properties</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Non Inheritable Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties#getNonInheritableProperties()
	 * @see #getViewModelLoadingProperties()
	 * @generated
	 */
	EReference getViewModelLoadingProperties_NonInheritableProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelProperties <em>Model
	 * Properties</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Properties</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelProperties
	 * @generated
	 */
	EClass getViewModelProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment <em>Date
	 * Time Display Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Display Attachment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment
	 * @generated
	 */
	EClass getDateTimeDisplayAttachment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment#getDisplayType <em>Display Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment#getDisplayType()
	 * @see #getDateTimeDisplayAttachment()
	 * @generated
	 */
	EAttribute getDateTimeDisplayAttachment_DisplayType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment
	 * <em>Domain Model Reference Segment</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Model Reference Segment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment
	 * @generated
	 */
	EClass getDomainModelReferenceSegment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment
	 * <em>Feature Domain Model Reference Segment</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Domain Model Reference Segment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment
	 * @generated
	 */
	EClass getFeatureDomainModelReferenceSegment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment#getDomainModelFeature <em>Domain
	 * Model Feature</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Model Feature</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment#getDomainModelFeature()
	 * @see #getFeatureDomainModelReferenceSegment()
	 * @generated
	 */
	EAttribute getFeatureDomainModelReferenceSegment_DomainModelFeature();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To Object Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To Object Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
	 *        valueDataType="org.eclipse.emf.ecore.EJavaObject" valueRequired="true"
	 * @generated
	 */
	EClass getStringToObjectMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToObjectMapEntry()
	 * @generated
	 */
	EAttribute getStringToObjectMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToObjectMapEntry()
	 * @generated
	 */
	EAttribute getStringToObjectMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainer
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VContainer#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainer#getChildren()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Children();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedContainer <em>Contained
	 * Container</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contained Container</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VContainedContainer
	 * @generated
	 */
	EClass getContainedContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VControl#getLabelAlignment
	 * <em>Label Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VControl#getLabelAlignment()
	 * @see #getControl()
	 * @generated
	 */
	EAttribute getControl_LabelAlignment();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment <em>Label
	 * Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Label Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * @generated
	 */
	EEnum getLabelAlignment();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType <em>Date Time
	 * Display Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Date Time Display Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType
	 * @generated
	 */
	EEnum getDateTimeDisplayType();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener <em>Domain Model Reference Change
	 * Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Domain Model Reference Change Listener</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
	 * @model instanceClass="org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener"
	 * @generated
	 */
	EDataType getDomainModelReferenceChangeListener();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference <em>Domain
	 * Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * @generated
	 */
	EClass getDomainModelReference();

	/**
	 * Returns the meta object for the attribute list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getChangeListener <em>Change Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Change Listener</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getChangeListener()
	 * @see #getDomainModelReference()
	 * @generated
	 */
	EAttribute getDomainModelReference_ChangeListener();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getSegments()
	 * @see #getDomainModelReference()
	 * @generated
	 */
	EReference getDomainModelReference_Segments();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * <em>Feature Path Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Feature Path Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference
	 * @generated
	 */
	EClass getFeaturePathDomainModelReference();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference#getDomainModelEFeature <em>Domain
	 * Model EFeature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Domain Model EFeature</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference#getDomainModelEFeature()
	 * @see #getFeaturePathDomainModelReference()
	 * @generated
	 */
	EReference getFeaturePathDomainModelReference_DomainModelEFeature();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference#getDomainModelEReferencePath
	 * <em>Domain Model EReference Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Domain Model EReference Path</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference#getDomainModelEReferencePath()
	 * @see #getFeaturePathDomainModelReference()
	 * @generated
	 */
	EReference getFeaturePathDomainModelReference_DomainModelEReferencePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VAttachment <em>Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Attachment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VAttachment
	 * @generated
	 */
	EClass getAttachment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VViewFactory getViewFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 *
	 * @noimplement This interface is not intended to be implemented by clients.
	 * @noextend This interface is not intended to be extended by clients.
	 *           <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl
		 * <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ELEMENT__NAME = eINSTANCE.getElement_Name();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ELEMENT__VISIBLE = eINSTANCE.getElement_Visible();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ELEMENT__ENABLED = eINSTANCE.getElement_Enabled();

		/**
		 * The meta object literal for the '<em><b>Readonly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ELEMENT__READONLY = eINSTANCE.getElement_Readonly();

		/**
		 * The meta object literal for the '<em><b>Diagnostic</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ELEMENT__DIAGNOSTIC = eINSTANCE.getElement_Diagnostic();

		/**
		 * The meta object literal for the '<em><b>Attachments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ELEMENT__ATTACHMENTS = eINSTANCE.getElement_Attachments();

		/**
		 * The meta object literal for the '<em><b>Uuid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.9
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT__UUID = eINSTANCE.getElement_Uuid();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl
		 * <em>Diagnostic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDiagnosticImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDiagnostic()
		 * @generated
		 */
		EClass DIAGNOSTIC = eINSTANCE.getDiagnostic();

		/**
		 * The meta object literal for the '<em><b>Diagnostics</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DIAGNOSTIC__DIAGNOSTICS = eINSTANCE.getDiagnostic_Diagnostics();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl <em>View</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Root EClass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VIEW__ROOT_ECLASS = eINSTANCE.getView_RootEClass();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VIEW__CHILDREN = eINSTANCE.getView_Children();

		/**
		 * The meta object literal for the '<em><b>Ecore Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW__ECORE_PATH = eINSTANCE.getView_EcorePath();

		/**
		 * The meta object literal for the '<em><b>Loading Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__LOADING_PROPERTIES = eINSTANCE.getView_LoadingProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl
		 * <em>Contained Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedElement()
		 * @generated
		 */
		EClass CONTAINED_ELEMENT = eINSTANCE.getContainedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl
		 * <em>Control</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getControl()
		 * @generated
		 */
		EClass CONTROL = eINSTANCE.getControl();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTROL__DOMAIN_MODEL_REFERENCE = eINSTANCE.getControl_DomainModelReference();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl <em>Model Loading
		 * Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getViewModelLoadingProperties()
		 * @generated
		 */
		EClass VIEW_MODEL_LOADING_PROPERTIES = eINSTANCE.getViewModelLoadingProperties();

		/**
		 * The meta object literal for the '<em><b>Inheritable Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES = eINSTANCE
			.getViewModelLoadingProperties_InheritableProperties();

		/**
		 * The meta object literal for the '<em><b>Non Inheritable Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES = eINSTANCE
			.getViewModelLoadingProperties_NonInheritableProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.VViewModelProperties <em>Model
		 * Properties</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelProperties
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getViewModelProperties()
		 * @generated
		 */
		EClass VIEW_MODEL_PROPERTIES = eINSTANCE.getViewModelProperties();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDateTimeDisplayAttachmentImpl <em>Date Time Display
		 * Attachment</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.8
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDateTimeDisplayAttachmentImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDateTimeDisplayAttachment()
		 * @generated
		 */
		EClass DATE_TIME_DISPLAY_ATTACHMENT = eINSTANCE.getDateTimeDisplayAttachment();

		/**
		 * The meta object literal for the '<em><b>Display Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.8
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE = eINSTANCE.getDateTimeDisplayAttachment_DisplayType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment
		 * <em>Domain Model Reference Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 2.0
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReferenceSegment()
		 * @generated
		 */
		EClass DOMAIN_MODEL_REFERENCE_SEGMENT = eINSTANCE.getDomainModelReferenceSegment();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl <em>Feature Domain
		 * Model Reference Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 2.0
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getFeatureDomainModelReferenceSegment()
		 * @generated
		 */
		EClass FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT = eINSTANCE.getFeatureDomainModelReferenceSegment();

		/**
		 * The meta object literal for the '<em><b>Domain Model Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 2.0
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE = eINSTANCE
			.getFeatureDomainModelReferenceSegment_DomainModelFeature();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VStringToObjectMapEntryImpl
		 * <em>String To Object Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VStringToObjectMapEntryImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getStringToObjectMapEntry()
		 * @generated
		 */
		EClass STRING_TO_OBJECT_MAP_ENTRY = eINSTANCE.getStringToObjectMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_OBJECT_MAP_ENTRY__KEY = eINSTANCE.getStringToObjectMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.7
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_OBJECT_MAP_ENTRY__VALUE = eINSTANCE.getStringToObjectMapEntry_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainerImpl
		 * <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainerImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTAINER__CHILDREN = eINSTANCE.getContainer_Children();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl
		 * <em>Contained Container</em>}' class.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.4
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedContainer()
		 * @generated
		 */
		EClass CONTAINED_CONTAINER = eINSTANCE.getContainedContainer();

		/**
		 * The meta object literal for the '<em><b>Label Alignment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONTROL__LABEL_ALIGNMENT = eINSTANCE.getControl_LabelAlignment();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment <em>Label
		 * Alignment</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getLabelAlignment()
		 * @generated
		 */
		EEnum LABEL_ALIGNMENT = eINSTANCE.getLabelAlignment();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType <em>Date Time
		 * Display Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.8
		 *        <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDateTimeDisplayType()
		 * @generated
		 */
		EEnum DATE_TIME_DISPLAY_TYPE = eINSTANCE.getDateTimeDisplayType();

		/**
		 * The meta object literal for the '<em>Domain Model Reference Change Listener</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReferenceChangeListener()
		 * @generated
		 */
		EDataType DOMAIN_MODEL_REFERENCE_CHANGE_LISTENER = eINSTANCE.getDomainModelReferenceChangeListener();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl
		 * <em>Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReference()
		 * @generated
		 */
		EClass DOMAIN_MODEL_REFERENCE = eINSTANCE.getDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Change Listener</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = eINSTANCE.getDomainModelReference_ChangeListener();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * 
		 * @since 2.0
		 *        <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_MODEL_REFERENCE__SEGMENTS = eINSTANCE.getDomainModelReference_Segments();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl <em>Feature Path Domain
		 * Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getFeaturePathDomainModelReference()
		 * @generated
		 */
		EClass FEATURE_PATH_DOMAIN_MODEL_REFERENCE = eINSTANCE.getFeaturePathDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Domain Model EFeature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = eINSTANCE
			.getFeaturePathDomainModelReference_DomainModelEFeature();

		/**
		 * The meta object literal for the '<em><b>Domain Model EReference Path</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = eINSTANCE
			.getFeaturePathDomainModelReference_DomainModelEReferencePath();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VAttachmentImpl
		 * <em>Attachment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VAttachmentImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getAttachment()
		 * @generated
		 */
		EClass ATTACHMENT = eINSTANCE.getAttachment();

	}

} // ViewPackage
