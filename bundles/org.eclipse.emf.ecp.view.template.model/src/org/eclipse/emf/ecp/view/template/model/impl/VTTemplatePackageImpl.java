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
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
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
public class VTTemplatePackageImpl extends EPackageImpl implements VTTemplatePackage {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass styleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass stylePropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass styleSelectorEClass = null;

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
	private VTTemplatePackageImpl() {
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
	 * This method is used to initialize {@link VTTemplatePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTTemplatePackage init() {
		if (isInited) {
			return (VTTemplatePackage) EPackage.Registry.INSTANCE.getEPackage(VTTemplatePackage.eNS_URI);
		}

		// Obtain or create and register package
		final VTTemplatePackageImpl theTemplatePackage = (VTTemplatePackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTTemplatePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VTTemplatePackageImpl());

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
	@Override
	public EClass getViewTemplate() {
		return viewTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getViewTemplate_ControlValidationConfiguration() {
		return (EReference) viewTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getViewTemplate_Styles() {
		return (EReference) viewTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getViewTemplate_ReferencedEcores() {
		return (EAttribute) viewTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getControlValidationTemplate() {
		return controlValidationTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_OkColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_OkForegroundColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_OkImageURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_OkOverlayURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_InfoColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_InfoForegroundColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_InfoImageURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_InfoOverlayURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_WarningColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_WarningForegroundColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_WarningImageURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_WarningOverlayURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_ErrorColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_ErrorForegroundColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_ErrorImageURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_ErrorOverlayURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_CancelColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_CancelForegroundColorHEX() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_CancelImageURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getControlValidationTemplate_CancelOverlayURL() {
		return (EAttribute) controlValidationTemplateEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStyle() {
		return styleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStyle_Selector() {
		return (EReference) styleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStyle_Properties()
	{
		return (EReference) styleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStyleProperty()
	{
		return stylePropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStyleSelector()
	{
		return styleSelectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
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
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		viewTemplateEClass = createEClass(VIEW_TEMPLATE);
		createEReference(viewTemplateEClass, VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION);
		createEReference(viewTemplateEClass, VIEW_TEMPLATE__STYLES);
		createEAttribute(viewTemplateEClass, VIEW_TEMPLATE__REFERENCED_ECORES);

		controlValidationTemplateEClass = createEClass(CONTROL_VALIDATION_TEMPLATE);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_FOREGROUND_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_FOREGROUND_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_FOREGROUND_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_FOREGROUND_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_FOREGROUND_COLOR_HEX);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL);
		createEAttribute(controlValidationTemplateEClass, CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL);

		styleEClass = createEClass(STYLE);
		createEReference(styleEClass, STYLE__SELECTOR);
		createEReference(styleEClass, STYLE__PROPERTIES);

		stylePropertyEClass = createEClass(STYLE_PROPERTY);

		styleSelectorEClass = createEClass(STYLE_SELECTOR);
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
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(viewTemplateEClass, VTViewTemplate.class, "ViewTemplate", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getViewTemplate_ControlValidationConfiguration(), getControlValidationTemplate(), null,
			"controlValidationConfiguration", null, 1, 1, VTViewTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getViewTemplate_Styles(), getStyle(), null, "styles", null, 0, -1, VTViewTemplate.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getViewTemplate_ReferencedEcores(), ecorePackage.getEString(), "referencedEcores", null, 0, -1, //$NON-NLS-1$
			VTViewTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(controlValidationTemplateEClass, VTControlValidationTemplate.class, "ControlValidationTemplate", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getControlValidationTemplate_OkColorHEX(), ecorePackage.getEString(), "okColorHEX", null, 0, 1, //$NON-NLS-1$
			VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_OkForegroundColorHEX(), ecorePackage.getEString(),
			"okForegroundColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_OkImageURL(), ecorePackage.getEString(), "okImageURL", null, 0, 1, //$NON-NLS-1$
			VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_OkOverlayURL(), ecorePackage.getEString(), "okOverlayURL", null, 0, //$NON-NLS-1$
			1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_InfoColorHEX(), ecorePackage.getEString(), "infoColorHEX", null, 0, //$NON-NLS-1$
			1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_InfoForegroundColorHEX(), ecorePackage.getEString(),
			"infoForegroundColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_InfoImageURL(), ecorePackage.getEString(), "infoImageURL", null, 0, //$NON-NLS-1$
			1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_InfoOverlayURL(), ecorePackage.getEString(), "infoOverlayURL", null, //$NON-NLS-1$
			0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_WarningColorHEX(), ecorePackage.getEString(), "warningColorHEX", //$NON-NLS-1$
			null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_WarningForegroundColorHEX(), ecorePackage.getEString(),
			"warningForegroundColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_WarningImageURL(), ecorePackage.getEString(), "warningImageURL", //$NON-NLS-1$
			null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_WarningOverlayURL(), ecorePackage.getEString(), "warningOverlayURL", //$NON-NLS-1$
			null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_ErrorColorHEX(), ecorePackage.getEString(), "errorColorHEX", null, //$NON-NLS-1$
			0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_ErrorForegroundColorHEX(), ecorePackage.getEString(),
			"errorForegroundColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_ErrorImageURL(), ecorePackage.getEString(), "errorImageURL", null, //$NON-NLS-1$
			0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_ErrorOverlayURL(), ecorePackage.getEString(), "errorOverlayURL", //$NON-NLS-1$
			null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_CancelColorHEX(), ecorePackage.getEString(), "cancelColorHEX", null, //$NON-NLS-1$
			0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_CancelForegroundColorHEX(), ecorePackage.getEString(),
			"cancelForegroundColorHEX", null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_CancelImageURL(), ecorePackage.getEString(), "cancelImageURL", null, //$NON-NLS-1$
			0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControlValidationTemplate_CancelOverlayURL(), ecorePackage.getEString(), "cancelOverlayURL", //$NON-NLS-1$
			null, 0, 1, VTControlValidationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(styleEClass, VTStyle.class, "Style", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStyle_Selector(), getStyleSelector(), null, "selector", null, 1, 1, VTStyle.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getStyle_Properties(), getStyleProperty(), null, "properties", null, 0, -1, VTStyle.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(stylePropertyEClass, VTStyleProperty.class, "StyleProperty", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(styleSelectorEClass, VTStyleSelector.class, "StyleSelector", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // VTTemplatePackageImpl
