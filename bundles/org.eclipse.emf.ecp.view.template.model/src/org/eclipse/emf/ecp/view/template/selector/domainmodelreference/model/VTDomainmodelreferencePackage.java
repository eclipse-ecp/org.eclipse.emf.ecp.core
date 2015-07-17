/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferenceFactory
 * @model kind="package"
 * @generated
 */
public interface VTDomainmodelreferencePackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "domainmodelreference"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/ecp/view/template/selector/domainmodelreference/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	VTDomainmodelreferencePackage eINSTANCE = org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainmodelreferencePackageImpl
		.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainModelReferenceSelectorImpl
	 * <em>Domain Model Reference Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainModelReferenceSelectorImpl
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainmodelreferencePackageImpl#getDomainModelReferenceSelector()
	 * @generated
	 */
	int DOMAIN_MODEL_REFERENCE_SELECTOR = 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Domain Model Reference Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_SELECTOR_FEATURE_COUNT = VTTemplatePackage.STYLE_SELECTOR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Domain Model Reference Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_REFERENCE_SELECTOR_OPERATION_COUNT = VTTemplatePackage.STYLE_SELECTOR_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector
	 * <em>Domain Model Reference Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Domain Model Reference Selector</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector
	 * @generated
	 */
	EClass getDomainModelReferenceSelector();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector#getDomainModelReference()
	 * @see #getDomainModelReferenceSelector()
	 * @generated
	 */
	EReference getDomainModelReferenceSelector_DomainModelReference();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTDomainmodelreferenceFactory getDomainmodelreferenceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
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
		 * {@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainModelReferenceSelectorImpl
		 * <em>Domain Model Reference Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainModelReferenceSelectorImpl
		 * @see org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainmodelreferencePackageImpl#getDomainModelReferenceSelector()
		 * @generated
		 */
		EClass DOMAIN_MODEL_REFERENCE_SELECTOR = eINSTANCE.getDomainModelReferenceSelector();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE = eINSTANCE
			.getDomainModelReferenceSelector_DomainModelReference();

	}

} // VTDomainmodelreferencePackage
