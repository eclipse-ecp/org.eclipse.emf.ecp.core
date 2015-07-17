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
package org.eclipse.emf.ecp.view.spi.group.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
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
 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory
 * @model kind="package"
 * @generated
 */
public interface VGroupPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "group"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/group/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.group.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VGroupPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupImpl <em>Group</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupImpl
	 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__NAME = VViewPackage.CONTAINED_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__VISIBLE = VViewPackage.CONTAINED_CONTAINER__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__ENABLED = VViewPackage.CONTAINED_CONTAINER__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__READONLY = VViewPackage.CONTAINED_CONTAINER__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__DIAGNOSTIC = VViewPackage.CONTAINED_CONTAINER__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__ATTACHMENTS = VViewPackage.CONTAINED_CONTAINER__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__CHILDREN = VViewPackage.CONTAINED_CONTAINER__CHILDREN;

	/**
	 * The feature id for the '<em><b>Group Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP_TYPE = VViewPackage.CONTAINED_CONTAINER_FEATURE_COUNT + 0;

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
	int GROUP__LABEL_ALIGNMENT = VViewPackage.CONTAINED_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Collapsed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP__COLLAPSED = VViewPackage.CONTAINED_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = VViewPackage.CONTAINED_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupType <em>Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupType
	 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroupType()
	 * @generated
	 */
	int GROUP_TYPE = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * <em>Label Alignment</em>}' enum.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroupLabelAlignment()
	 * @generated
	 */
	int GROUP_LABEL_ALIGNMENT = 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Group</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroup
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getGroupType
	 * <em>Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Group Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroup#getGroupType()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_GroupType();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#getLabelAlignment <em>Label Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Label Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroup#getLabelAlignment()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_LabelAlignment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.group.model.VGroup#isCollapsed
	 * <em>Collapsed</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Collapsed</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroup#isCollapsed()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Collapsed();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Type</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupType
	 * @generated
	 */
	EEnum getGroupType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * <em>Label Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>Label Alignment</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
	 * @generated
	 */
	EEnum getGroupLabelAlignment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VGroupFactory getGroupFactory();

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
	 *
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupImpl
		 * <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupImpl
		 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();
		/**
		 * The meta object literal for the '<em><b>Group Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.4
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute GROUP__GROUP_TYPE = eINSTANCE.getGroup_GroupType();
		/**
		 * The meta object literal for the '<em><b>Label Alignment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute GROUP__LABEL_ALIGNMENT = eINSTANCE.getGroup_LabelAlignment();
		/**
		 * The meta object literal for the '<em><b>Collapsed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.4
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute GROUP__COLLAPSED = eINSTANCE.getGroup_Collapsed();
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupType <em>Type</em>}'
		 * enum.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.4
		 *        <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupType
		 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroupType()
		 * @generated
		 */
		EEnum GROUP_TYPE = eINSTANCE.getGroupType();
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
		 * <em>Label Alignment</em>}' enum.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.3
		 *        <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment
		 * @see org.eclipse.emf.ecp.view.spi.group.model.impl.VGroupPackageImpl#getGroupLabelAlignment()
		 * @generated
		 */
		EEnum GROUP_LABEL_ALIGNMENT = eINSTANCE.getGroupLabelAlignment();

	}

} // VGroupPackage
