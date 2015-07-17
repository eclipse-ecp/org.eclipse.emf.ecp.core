/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.compoundcontrol.model;

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
 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundcontrolFactory
 * @model kind="package"
 * @generated
 */
public interface VCompoundcontrolPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "compoundcontrol"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/compoundcontrol/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.compoundcontrol.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VCompoundcontrolPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundcontrolPackageImpl
		.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundControlImpl
	 * <em>Compound Control</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundControlImpl
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundcontrolPackageImpl#getCompoundControl()
	 * @generated
	 */
	int COMPOUND_CONTROL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL__CONTROLS = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Compound Control</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int COMPOUND_CONTROL_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl
	 * <em>Compound Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Compound Control</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl
	 * @generated
	 */
	EClass getCompoundControl();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl#getControls <em>Controls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Controls</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.VCompoundControl#getControls()
	 * @see #getCompoundControl()
	 * @generated
	 */
	EReference getCompoundControl_Controls();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VCompoundcontrolFactory getCompoundcontrolFactory();

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
		 * {@link org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundControlImpl
		 * <em>Compound Control</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundControlImpl
		 * @see org.eclipse.emf.ecp.view.spi.compoundcontrol.model.impl.VCompoundcontrolPackageImpl#getCompoundControl()
		 * @generated
		 */
		EClass COMPOUND_CONTROL = eINSTANCE.getCompoundControl();

		/**
		 * The meta object literal for the '<em><b>Controls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference COMPOUND_CONTROL__CONTROLS = eINSTANCE.getCompoundControl_Controls();

	}

} // VCompoundcontrolPackage
