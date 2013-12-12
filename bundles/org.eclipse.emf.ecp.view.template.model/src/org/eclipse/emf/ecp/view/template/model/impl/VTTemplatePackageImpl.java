/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTTemplatePackageImpl extends EPackageImpl implements VTTemplatePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass viewTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass controlValidationTemplateEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.template.model.VTTemplatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTTemplatePackageImpl()
	{
		super(eNS_URI, VTTemplateFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link VTTemplatePackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTTemplatePackage init()
	{
		if (isInited)
			return (VTTemplatePackage) EPackage.Registry.INSTANCE.getEPackage(VTTemplatePackage.eNS_URI);

		// Obtain or create and register package
		VTTemplatePackageImpl theTemplatePackage = (VTTemplatePackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VTTemplatePackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new VTTemplatePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theTemplatePackage.createPackageContents();

		// Initialize created meta-data
		theTemplatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTemplatePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTTemplatePackage.eNS_URI, theTemplatePackage);
		return theTemplatePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getViewTemplate()
	{
		return viewTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getViewTemplate_ControlValidationConfiguration()
	{
		return (EReference) viewTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getControlValidationTemplate()
	{
		return controlValidationTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_OkColorHEX()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_OkImageURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_OkOverlayURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_InfoColorHEX()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_InfoImageURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_InfoOverlayURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_WarningColorHEX()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_WarningImageURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_WarningOverlayURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_ErrorColorHEX()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_ErrorImageURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_ErrorOverlayURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_CancelColorHEX()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_CancelImageURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getControlValidationTemplate_CancelOverlayURL()
	{
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VTTemplateFactory getTemplateFactory()
	{
		return (VTTemplateFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		viewTemplateEClass = createEClass(VIEW_TEMPLATE);
		createEReference(viewTemplateEClass, VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION);

		controlValidationTemplateEClass = createEClass(CONTROL_VALIDATION_TEMPLATE);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(viewTemplateEClass, VTViewTemplate.class,
			"ViewTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
			getViewTemplate_ControlValidationConfiguration(),
			this.getControlValidationTemplate(),
			null,
			"controlValidationConfiguration", null, 1, 1, VTViewTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(controlValidationTemplateEClass, VTControlValidationTemplate.class,
			"ControlValidationTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_OkColorHEX(),
			ecorePackage.getEString(),
			"okColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_OkImageURL(),
			ecorePackage.getEString(),
			"okImageURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_OkOverlayURL(),
			ecorePackage.getEString(),
			"okOverlayURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_InfoColorHEX(),
			ecorePackage.getEString(),
			"infoColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_InfoImageURL(),
			ecorePackage.getEString(),
			"infoImageURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_InfoOverlayURL(),
			ecorePackage.getEString(),
			"infoOverlayURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_WarningColorHEX(),
			ecorePackage.getEString(),
			"warningColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_WarningImageURL(),
			ecorePackage.getEString(),
			"warningImageURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_WarningOverlayURL(),
			ecorePackage.getEString(),
			"warningOverlayURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_ErrorColorHEX(),
			ecorePackage.getEString(),
			"errorColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_ErrorImageURL(),
			ecorePackage.getEString(),
			"errorImageURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_ErrorOverlayURL(),
			ecorePackage.getEString(),
			"errorOverlayURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_CancelColorHEX(),
			ecorePackage.getEString(),
			"cancelColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_CancelImageURL(),
			ecorePackage.getEString(),
			"cancelImageURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getControlValidationTemplate_CancelOverlayURL(),
			ecorePackage.getEString(),
			"cancelOverlayURL", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // VTTemplatePackageImpl
