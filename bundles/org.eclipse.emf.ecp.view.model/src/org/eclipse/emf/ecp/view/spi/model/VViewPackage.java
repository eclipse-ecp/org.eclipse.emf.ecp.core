/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
	String eNS_URI = "http://org/eclipse/emf/ecp/view/model/200"; //$NON-NLS-1$

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
	int ELEMENT = 3;

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
	int VIEW = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl <em>Control</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getControl()
	 * @generated
	 */
	int CONTROL = 8;

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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl
	 * <em>Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReference()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE__SEGMENTS = 0;

	/**
	 * The number of structural features of the '<em>Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = 1;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__VISIBLE = 2;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ENABLED = 3;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__READONLY = 4;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__DIAGNOSTIC = 5;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ATTACHMENTS = 6;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW__LABEL = ELEMENT__LABEL;

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
	 * The feature id for the '<em><b>Root EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW__ROOT_ECLASS = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int VIEW__CHILDREN = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ecore Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ECORE_PATH = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl
	 * <em>Contained Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedElement()
	 * @generated
	 */
	int CONTAINED_ELEMENT = 5;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTAINED_ELEMENT__LABEL = ELEMENT__LABEL;

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
	int CONTAINER = 6;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTAINER__LABEL = ELEMENT__LABEL;

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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl
	 * <em>Contained Container</em>}' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VContainedContainerImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getContainedContainer()
	 * @generated
	 */
	int CONTAINED_CONTAINER = 7;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINED_CONTAINER__LABEL = CONTAINED_ELEMENT__LABEL;

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
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL__LABEL = CONTAINED_ELEMENT__LABEL;

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
	 * The feature id for the '<em><b>Label Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL__LABEL_ALIGNMENT = CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONTROL__DOMAIN_MODEL_REFERENCE = CONTAINED_ELEMENT_FEATURE_COUNT + 1;

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
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDMRSegmentImpl <em>DMR Segment</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDMRSegmentImpl
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDMRSegment()
	 * @generated
	 */
	int DMR_SEGMENT = 9;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_SEGMENT__PROPERTY_NAME = 0;

	/**
	 * The number of structural features of the '<em>DMR Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DMR_SEGMENT_FEATURE_COUNT = 1;

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
	int LABEL_ALIGNMENT = 10;

	/**
	 * The meta object id for the '<em>Domain Model Reference Change Listener</em>' data type.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDomainModelReferenceChangeListener()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE_CHANGE_LISTENER = 11;

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VElement#getLabel
	 * <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VElement#getLabel()
	 * @see #getElement()
	 * @generated
	 */
	EAttribute getElement_Label();

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
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VElement#getDiagnostic <em>Diagnostic</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VElement#getAttachments <em>Attachments</em>}'.
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
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic#getDiagnostics <em>Diagnostics</em>}'.
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
	 * Returns the meta object for the reference '{@link org.eclipse.emf.ecp.view.spi.model.VView#getRootEClass
	 * <em>Root EClass</em>}'.
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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VView#getChildren <em>Children</em>}'.
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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VView#getEcorePath
	 * <em>Ecore Path</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedElement
	 * <em>Contained Element</em>}'.
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
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VControl#getDomainModelReference <em>Domain Model Reference</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDMRSegment <em>DMR Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>DMR Segment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDMRSegment
	 * @generated
	 */
	EClass getDMRSegment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.model.VDMRSegment#getPropertyName
	 * <em>Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Property Name</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDMRSegment#getPropertyName()
	 * @see #getDMRSegment()
	 * @generated
	 */
	EAttribute getDMRSegment_PropertyName();

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
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VContainer#getChildren <em>Children</em>}'.
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
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VContainedContainer
	 * <em>Contained Container</em>}'.
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
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * <em>Label Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Label Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
	 * @generated
	 */
	EEnum getLabelAlignment();

	/**
	 * Returns the meta object for data type '
	 * {@link org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
	 * <em>Domain Model Reference Change Listener</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Domain Model Reference Change Listener</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener
	 * @model instanceClass="org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener"
	 * @generated
	 */
	EDataType getDomainModelReferenceChangeListener();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
	 * @generated
	 */
	EClass getDomainModelReference();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getSegments()
	 * @see #getDomainModelReference()
	 * @generated
	 */
	EReference getDomainModelReference_Segments();

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
	 *              <!-- end-user-doc -->
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
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ELEMENT__LABEL = eINSTANCE.getElement_Label();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.impl.VDMRSegmentImpl
		 * <em>DMR Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VDMRSegmentImpl
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getDMRSegment()
		 * @generated
		 */
		EClass DMR_SEGMENT = eINSTANCE.getDMRSegment();

		/**
		 * The meta object literal for the '<em><b>Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute DMR_SEGMENT__PROPERTY_NAME = eINSTANCE.getDMRSegment_PropertyName();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.model.LabelAlignment
		 * <em>Label Alignment</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.LabelAlignment
		 * @see org.eclipse.emf.ecp.view.spi.model.impl.VViewPackageImpl#getLabelAlignment()
		 * @generated
		 */
		EEnum LABEL_ALIGNMENT = eINSTANCE.getLabelAlignment();

		/**
		 * The meta object literal for the '<em>Domain Model Reference Change Listener</em>' data type.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
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
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DOMAIN_MODEL_REFERENCE__SEGMENTS = eINSTANCE.getDomainModelReference_Segments();

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
