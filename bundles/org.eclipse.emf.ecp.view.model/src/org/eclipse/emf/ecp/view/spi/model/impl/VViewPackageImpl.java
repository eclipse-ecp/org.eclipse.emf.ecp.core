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
package org.eclipse.emf.ecp.view.spi.model.impl;

import static org.eclipse.emf.ecp.view.spi.model.VViewPackage.CONTAINER;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>.
 *
 * @since 1.2
 *        <!--
 *        end-user-doc -->
 * @generated
 */
public class VViewPackageImpl extends EPackageImpl implements VViewPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass diagnosticEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass viewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass containedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass controlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass viewModelLoadingPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass viewModelPropertiesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dateTimeDisplayAttachmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass domainModelReferenceSegmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass featureDomainModelReferenceSegmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass featurePathDomainModelReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringToObjectMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass containerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass containedContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum labelAlignmentEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum dateTimeDisplayTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass domainModelReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass attachmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VViewPackageImpl() {
		super(eNS_URI, VViewFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link VViewPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VViewPackage init() {
		if (isInited)
			return (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Obtain or create and register package
		VViewPackageImpl theViewPackage = (VViewPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VViewPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VViewPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theViewPackage.createPackageContents();

		// Initialize created meta-data
		theViewPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theViewPackage,
			new EValidator.Descriptor() {
				public EValidator getEValidator() {
					return ViewValidator.INSTANCE;
				}
			});

		// Mark meta-data to indicate it can't be changed
		theViewPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VViewPackage.eNS_URI, theViewPackage);
		return theViewPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getElement_Name() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getElement_Visible() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getElement_Enabled() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getElement_Readonly() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getElement_Diagnostic() {
		return (EReference) elementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getElement_Attachments() {
		return (EReference) elementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getElement_Uuid() {
		return (EAttribute) elementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDiagnostic() {
		return diagnosticEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDiagnostic_Diagnostics() {
		return (EAttribute) diagnosticEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getView() {
		return viewEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getView_RootEClass() {
		return (EReference) viewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getView_Children() {
		return (EReference) viewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getView_EcorePath() {
		return (EAttribute) viewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getView_LoadingProperties() {
		return (EReference) viewEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getContainedElement() {
		return containedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getControl() {
		return controlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getControl_DomainModelReference() {
		return (EReference) controlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getViewModelLoadingProperties() {
		return viewModelLoadingPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getViewModelLoadingProperties_InheritableProperties() {
		return (EReference) viewModelLoadingPropertiesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getViewModelLoadingProperties_NonInheritableProperties() {
		return (EReference) viewModelLoadingPropertiesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getViewModelProperties() {
		return viewModelPropertiesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateTimeDisplayAttachment() {
		return dateTimeDisplayAttachmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTimeDisplayAttachment_DisplayType() {
		return (EAttribute) dateTimeDisplayAttachmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDomainModelReferenceSegment() {
		return domainModelReferenceSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureDomainModelReferenceSegment() {
		return featureDomainModelReferenceSegmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureDomainModelReferenceSegment_DomainModelFeature() {
		return (EAttribute) featureDomainModelReferenceSegmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFeaturePathDomainModelReference() {
		return featurePathDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeaturePathDomainModelReference_DomainModelEFeature() {
		return (EReference) featurePathDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeaturePathDomainModelReference_DomainModelEReferencePath() {
		return (EReference) featurePathDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringToObjectMapEntry() {
		return stringToObjectMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToObjectMapEntry_Key() {
		return (EAttribute) stringToObjectMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStringToObjectMapEntry_Value() {
		return (EAttribute) stringToObjectMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getContainer() {
		return containerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getContainer_Children() {
		return (EReference) containerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContainedContainer() {
		return containedContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getControl_LabelAlignment() {
		return (EAttribute) controlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLabelAlignment() {
		return labelAlignmentEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDateTimeDisplayType() {
		return dateTimeDisplayTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDomainModelReference() {
		return domainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainModelReference_Segments() {
		return (EReference) domainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAttachment() {
		return attachmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VViewFactory getViewFactory() {
		return (VViewFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		diagnosticEClass = createEClass(DIAGNOSTIC);
		createEAttribute(diagnosticEClass, DIAGNOSTIC__DIAGNOSTICS);

		attachmentEClass = createEClass(ATTACHMENT);

		domainModelReferenceEClass = createEClass(DOMAIN_MODEL_REFERENCE);
		createEReference(domainModelReferenceEClass, DOMAIN_MODEL_REFERENCE__SEGMENTS);

		elementEClass = createEClass(ELEMENT);
		createEAttribute(elementEClass, ELEMENT__NAME);
		createEAttribute(elementEClass, ELEMENT__VISIBLE);
		createEAttribute(elementEClass, ELEMENT__ENABLED);
		createEAttribute(elementEClass, ELEMENT__READONLY);
		createEReference(elementEClass, ELEMENT__DIAGNOSTIC);
		createEReference(elementEClass, ELEMENT__ATTACHMENTS);
		createEAttribute(elementEClass, ELEMENT__UUID);

		viewEClass = createEClass(VIEW);
		createEReference(viewEClass, VIEW__ROOT_ECLASS);
		createEAttribute(viewEClass, VIEW__ECORE_PATH);
		createEReference(viewEClass, VIEW__CHILDREN);
		createEReference(viewEClass, VIEW__LOADING_PROPERTIES);

		containedElementEClass = createEClass(CONTAINED_ELEMENT);

		containerEClass = createEClass(CONTAINER);
		createEReference(containerEClass, CONTAINER__CHILDREN);

		containedContainerEClass = createEClass(CONTAINED_CONTAINER);

		controlEClass = createEClass(CONTROL);
		createEReference(controlEClass, CONTROL__DOMAIN_MODEL_REFERENCE);
		createEAttribute(controlEClass, CONTROL__LABEL_ALIGNMENT);

		viewModelPropertiesEClass = createEClass(VIEW_MODEL_PROPERTIES);

		stringToObjectMapEntryEClass = createEClass(STRING_TO_OBJECT_MAP_ENTRY);
		createEAttribute(stringToObjectMapEntryEClass, STRING_TO_OBJECT_MAP_ENTRY__KEY);
		createEAttribute(stringToObjectMapEntryEClass, STRING_TO_OBJECT_MAP_ENTRY__VALUE);

		viewModelLoadingPropertiesEClass = createEClass(VIEW_MODEL_LOADING_PROPERTIES);
		createEReference(viewModelLoadingPropertiesEClass, VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES);
		createEReference(viewModelLoadingPropertiesEClass, VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES);

		dateTimeDisplayAttachmentEClass = createEClass(DATE_TIME_DISPLAY_ATTACHMENT);
		createEAttribute(dateTimeDisplayAttachmentEClass, DATE_TIME_DISPLAY_ATTACHMENT__DISPLAY_TYPE);

		domainModelReferenceSegmentEClass = createEClass(DOMAIN_MODEL_REFERENCE_SEGMENT);

		featureDomainModelReferenceSegmentEClass = createEClass(FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT);
		createEAttribute(featureDomainModelReferenceSegmentEClass,
			FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE);

		featurePathDomainModelReferenceEClass = createEClass(FEATURE_PATH_DOMAIN_MODEL_REFERENCE);
		createEReference(featurePathDomainModelReferenceEClass,
			FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE);
		createEReference(featurePathDomainModelReferenceEClass,
			FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH);

		// Create enums
		labelAlignmentEEnum = createEEnum(LABEL_ALIGNMENT);
		dateTimeDisplayTypeEEnum = createEEnum(DATE_TIME_DISPLAY_TYPE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		viewEClass.getESuperTypes().add(this.getElement());
		containedElementEClass.getESuperTypes().add(this.getElement());
		containerEClass.getESuperTypes().add(this.getElement());
		containedContainerEClass.getESuperTypes().add(this.getContainedElement());
		containedContainerEClass.getESuperTypes().add(this.getContainer());
		controlEClass.getESuperTypes().add(this.getContainedElement());
		viewModelLoadingPropertiesEClass.getESuperTypes().add(this.getViewModelProperties());
		dateTimeDisplayAttachmentEClass.getESuperTypes().add(this.getAttachment());
		featureDomainModelReferenceSegmentEClass.getESuperTypes().add(this.getDomainModelReferenceSegment());
		featurePathDomainModelReferenceEClass.getESuperTypes().add(this.getDomainModelReference());

		// Initialize classes and features; add operations and parameters
		initEClass(diagnosticEClass, VDiagnostic.class, "Diagnostic", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiagnostic_Diagnostics(), theEcorePackage.getEJavaObject(), "diagnostics", null, 0, -1, //$NON-NLS-1$
			VDiagnostic.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(attachmentEClass, VAttachment.class, "Attachment", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(domainModelReferenceEClass, VDomainModelReference.class, "DomainModelReference", !IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainModelReference_Segments(), this.getDomainModelReferenceSegment(), null, "segments", //$NON-NLS-1$
			null, 1, -1, VDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementEClass, VElement.class, "Element", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getElement_Name(), theEcorePackage.getEString(), "name", null, 0, 1, VElement.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Visible(), ecorePackage.getEBoolean(), "visible", "true", 0, 1, VElement.class, //$NON-NLS-1$ //$NON-NLS-2$
			IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Enabled(), ecorePackage.getEBoolean(), "enabled", "true", 0, 1, VElement.class, //$NON-NLS-1$ //$NON-NLS-2$
			IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Readonly(), theEcorePackage.getEBoolean(), "readonly", "false", 0, 1, VElement.class, //$NON-NLS-1$ //$NON-NLS-2$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElement_Diagnostic(), this.getDiagnostic(), null, "diagnostic", null, 0, 1, VElement.class, //$NON-NLS-1$
			IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getElement_Attachments(), this.getAttachment(), null, "attachments", null, 0, -1, VElement.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getElement_Uuid(), theEcorePackage.getEString(), "uuid", null, 0, 1, VElement.class, //$NON-NLS-1$
			IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(viewEClass, VView.class, "View", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getView_RootEClass(), theEcorePackage.getEClass(), null, "rootEClass", null, 1, 1, VView.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getView_EcorePath(), theEcorePackage.getEString(), "ecorePath", null, 1, 1, VView.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getView_Children(), this.getContainedElement(), null, "children", null, 0, -1, VView.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getView_LoadingProperties(), this.getViewModelProperties(), null, "loadingProperties", null, 1, //$NON-NLS-1$
			1, VView.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containedElementEClass, VContainedElement.class, "ContainedElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(containerEClass, VContainer.class, "Container", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainer_Children(), this.getContainedElement(), null, "children", null, 0, -1, //$NON-NLS-1$
			VContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containedContainerEClass, VContainedContainer.class, "ContainedContainer", IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(controlEClass, VControl.class, "Control", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getControl_DomainModelReference(), this.getDomainModelReference(), null, "domainModelReference", //$NON-NLS-1$
			null, 1, 1, VControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getControl_LabelAlignment(), this.getLabelAlignment(), "labelAlignment", "Default", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
			VControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(viewModelPropertiesEClass, VViewModelProperties.class, "ViewModelProperties", IS_ABSTRACT, //$NON-NLS-1$
			IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringToObjectMapEntryEClass, Map.Entry.class, "StringToObjectMapEntry", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToObjectMapEntry_Key(), ecorePackage.getEString(), "key", null, 1, 1, Map.Entry.class, //$NON-NLS-1$
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToObjectMapEntry_Value(), ecorePackage.getEJavaObject(), "value", null, 1, 1, //$NON-NLS-1$
			Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(viewModelLoadingPropertiesEClass, VViewModelLoadingProperties.class, "ViewModelLoadingProperties", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getViewModelLoadingProperties_InheritableProperties(), this.getStringToObjectMapEntry(), null,
			"inheritableProperties", null, 0, -1, VViewModelLoadingProperties.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getViewModelLoadingProperties_NonInheritableProperties(), this.getStringToObjectMapEntry(), null,
			"nonInheritableProperties", null, 0, -1, VViewModelLoadingProperties.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateTimeDisplayAttachmentEClass, VDateTimeDisplayAttachment.class, "DateTimeDisplayAttachment", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDateTimeDisplayAttachment_DisplayType(), this.getDateTimeDisplayType(), "displayType", null, //$NON-NLS-1$
			0, 1, VDateTimeDisplayAttachment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(domainModelReferenceSegmentEClass, VDomainModelReferenceSegment.class, "DomainModelReferenceSegment", //$NON-NLS-1$
			IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(featureDomainModelReferenceSegmentEClass, VFeatureDomainModelReferenceSegment.class,
			"FeatureDomainModelReferenceSegment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getFeatureDomainModelReferenceSegment_DomainModelFeature(), ecorePackage.getEString(),
			"domainModelFeature", null, 1, 1, VFeatureDomainModelReferenceSegment.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featurePathDomainModelReferenceEClass, VFeaturePathDomainModelReference.class,
			"FeaturePathDomainModelReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFeaturePathDomainModelReference_DomainModelEFeature(),
			theEcorePackage.getEStructuralFeature(), null, "domainModelEFeature", null, 1, 1, //$NON-NLS-1$
			VFeaturePathDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeaturePathDomainModelReference_DomainModelEReferencePath(), theEcorePackage.getEReference(),
			null, "domainModelEReferencePath", null, 0, -1, VFeaturePathDomainModelReference.class, !IS_TRANSIENT, //$NON-NLS-1$
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(labelAlignmentEEnum, LabelAlignment.class, "LabelAlignment"); //$NON-NLS-1$
		addEEnumLiteral(labelAlignmentEEnum, LabelAlignment.LEFT);
		addEEnumLiteral(labelAlignmentEEnum, LabelAlignment.NONE);
		addEEnumLiteral(labelAlignmentEEnum, LabelAlignment.TOP);
		addEEnumLiteral(labelAlignmentEEnum, LabelAlignment.DEFAULT);

		initEEnum(dateTimeDisplayTypeEEnum, DateTimeDisplayType.class, "DateTimeDisplayType"); //$NON-NLS-1$
		addEEnumLiteral(dateTimeDisplayTypeEEnum, DateTimeDisplayType.TIME_AND_DATE);
		addEEnumLiteral(dateTimeDisplayTypeEEnum, DateTimeDisplayType.TIME_ONLY);
		addEEnumLiteral(dateTimeDisplayTypeEEnum, DateTimeDisplayType.DATE_ONLY);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/edapt
		createEdaptAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/edapt</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createEdaptAnnotations() {
		String source = "http://www.eclipse.org/edapt"; //$NON-NLS-1$
		addAnnotation(this,
			source,
			new String[] {
				"historyURI", "../../org.eclipse.emf.ecp.view.indexdmr.model/model/indexdmr.history" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation(featurePathDomainModelReferenceEClass,
			source,
			new String[] {
				"constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // ViewPackageImpl
