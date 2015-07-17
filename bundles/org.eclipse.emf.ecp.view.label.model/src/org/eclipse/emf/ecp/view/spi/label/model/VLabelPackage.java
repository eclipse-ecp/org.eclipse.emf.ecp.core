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
package org.eclipse.emf.ecp.view.spi.label.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelFactory
 * @model kind="package"
 * @generated
 */
public interface VLabelPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "label"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/label/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.label.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VLabelPackage eINSTANCE = org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelImpl <em>Label</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelImpl
	 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__NAME = VViewPackage.CONTAINED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__VISIBLE = VViewPackage.CONTAINED_ELEMENT__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__ENABLED = VViewPackage.CONTAINED_ELEMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__READONLY = VViewPackage.CONTAINED_ELEMENT__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__DIAGNOSTIC = VViewPackage.CONTAINED_ELEMENT__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__ATTACHMENTS = VViewPackage.CONTAINED_ELEMENT__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__STYLE = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL__DOMAIN_MODEL_REFERENCE = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = VViewPackage.CONTAINED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle <em>VLabel Style</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
	 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelPackageImpl#getVLabelStyle()
	 * @generated
	 */
	int VLABEL_STYLE = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabel
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getStyle
	 * <em>Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Style</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabel#getStyle()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Style();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.label.model.VLabel#getDomainModelReference <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabel#getDomainModelReference()
	 * @see #getLabel()
	 * @generated
	 */
	EReference getLabel_DomainModelReference();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
	 * <em>VLabel Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for enum '<em>VLabel Style</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
	 * @generated
	 */
	EEnum getVLabelStyle();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VLabelFactory getLabelFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelImpl
		 * <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelImpl
		 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute LABEL__STYLE = eINSTANCE.getLabel_Style();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 *
		 * @since 1.4
		 *        <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference LABEL__DOMAIN_MODEL_REFERENCE = eINSTANCE.getLabel_DomainModelReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
		 * <em>VLabel Style</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.spi.label.model.VLabelStyle
		 * @see org.eclipse.emf.ecp.view.spi.label.model.impl.VLabelPackageImpl#getVLabelStyle()
		 * @generated
		 */
		EEnum VLABEL_STYLE = eINSTANCE.getVLabelStyle();

	}

} // VLabelPackage
