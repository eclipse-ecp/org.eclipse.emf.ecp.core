/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 */
package org.eclipse.emf.ecp.view.dynamictree.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.dynamictree.model.DomainIntermediate;
import org.eclipse.emf.ecp.view.dynamictree.model.DomainRoot;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentItem;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTree;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTreeDomainModelReference;
import org.eclipse.emf.ecp.view.dynamictree.model.ModelFactory;
import org.eclipse.emf.ecp.view.dynamictree.model.ModelPackage;
import org.eclipse.emf.ecp.view.dynamictree.model.TestElement;
import org.eclipse.emf.ecp.view.dynamictree.model.TestElementContainer;
import org.eclipse.emf.ecp.view.dynamictree.model.util.ModelValidator;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass dynamicContainmentTreeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass dynamicContainmentItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass testElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass domainRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass domainIntermediateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass testElementContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass dynamicContainmentTreeDomainModelReferenceEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.dynamictree.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl()
	{
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init()
	{
		if (isInited) {
			return (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		}

		// Obtain or create and register package
		final ModelPackageImpl theModelPackage = (ModelPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new ModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VCategorizationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theModelPackage,
				new EValidator.Descriptor() {
					@Override
					public EValidator getEValidator() {
						return ModelValidator.INSTANCE;
					}
				});

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDynamicContainmentTree()
	{
		return dynamicContainmentTreeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTree_DomainModel()
	{
		return (EReference) dynamicContainmentTreeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTree_ChildReference()
	{
		return (EReference) dynamicContainmentTreeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTree_PathToRoot()
	{
		return (EReference) dynamicContainmentTreeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTree_ChildComposite()
	{
		return (EReference) dynamicContainmentTreeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTree_Items()
	{
		return (EReference) dynamicContainmentTreeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDynamicContainmentItem()
	{
		return dynamicContainmentItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentItem_DomainModel()
	{
		return (EReference) dynamicContainmentItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentItem_Items()
	{
		return (EReference) dynamicContainmentItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentItem_Composite()
	{
		return (EReference) dynamicContainmentItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getDynamicContainmentItem_BaseItemIndex() {
		return (EAttribute) dynamicContainmentItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTestElement()
	{
		return testElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTestElement_Id()
	{
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTestElement_Elements()
	{
		return (EReference) testElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTestElement_ParentId()
	{
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTestElement_Name() {
		return (EAttribute) testElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDomainRoot()
	{
		return domainRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDomainRoot_Intermediate()
	{
		return (EReference) domainRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDomainIntermediate()
	{
		return domainIntermediateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDomainIntermediate_TestElementContainer()
	{
		return (EReference) domainIntermediateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTestElementContainer()
	{
		return testElementContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTestElementContainer_TestElements()
	{
		return (EReference) testElementContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getTestElementContainer_Id()
	{
		return (EAttribute) testElementContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDynamicContainmentTreeDomainModelReference() {
		return dynamicContainmentTreeDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTreeDomainModelReference_PathFromRoot() {
		return (EReference) dynamicContainmentTreeDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getDynamicContainmentTreeDomainModelReference_PathFromBase() {
		return (EReference) dynamicContainmentTreeDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelFactory getModelFactory()
	{
		return (ModelFactory) getEFactoryInstance();
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
		dynamicContainmentTreeEClass = createEClass(DYNAMIC_CONTAINMENT_TREE);
		createEReference(dynamicContainmentTreeEClass, DYNAMIC_CONTAINMENT_TREE__DOMAIN_MODEL);
		createEReference(dynamicContainmentTreeEClass, DYNAMIC_CONTAINMENT_TREE__CHILD_REFERENCE);
		createEReference(dynamicContainmentTreeEClass, DYNAMIC_CONTAINMENT_TREE__PATH_TO_ROOT);
		createEReference(dynamicContainmentTreeEClass, DYNAMIC_CONTAINMENT_TREE__CHILD_COMPOSITE);
		createEReference(dynamicContainmentTreeEClass, DYNAMIC_CONTAINMENT_TREE__ITEMS);

		dynamicContainmentItemEClass = createEClass(DYNAMIC_CONTAINMENT_ITEM);
		createEReference(dynamicContainmentItemEClass, DYNAMIC_CONTAINMENT_ITEM__DOMAIN_MODEL);
		createEReference(dynamicContainmentItemEClass, DYNAMIC_CONTAINMENT_ITEM__ITEMS);
		createEReference(dynamicContainmentItemEClass, DYNAMIC_CONTAINMENT_ITEM__COMPOSITE);
		createEAttribute(dynamicContainmentItemEClass, DYNAMIC_CONTAINMENT_ITEM__BASE_ITEM_INDEX);

		testElementEClass = createEClass(TEST_ELEMENT);
		createEAttribute(testElementEClass, TEST_ELEMENT__ID);
		createEReference(testElementEClass, TEST_ELEMENT__ELEMENTS);
		createEAttribute(testElementEClass, TEST_ELEMENT__PARENT_ID);
		createEAttribute(testElementEClass, TEST_ELEMENT__NAME);

		domainRootEClass = createEClass(DOMAIN_ROOT);
		createEReference(domainRootEClass, DOMAIN_ROOT__INTERMEDIATE);

		domainIntermediateEClass = createEClass(DOMAIN_INTERMEDIATE);
		createEReference(domainIntermediateEClass, DOMAIN_INTERMEDIATE__TEST_ELEMENT_CONTAINER);

		testElementContainerEClass = createEClass(TEST_ELEMENT_CONTAINER);
		createEReference(testElementContainerEClass, TEST_ELEMENT_CONTAINER__TEST_ELEMENTS);
		createEAttribute(testElementContainerEClass, TEST_ELEMENT_CONTAINER__ID);

		dynamicContainmentTreeDomainModelReferenceEClass = createEClass(DYNAMIC_CONTAINMENT_TREE_DOMAIN_MODEL_REFERENCE);
		createEReference(dynamicContainmentTreeDomainModelReferenceEClass,
			DYNAMIC_CONTAINMENT_TREE_DOMAIN_MODEL_REFERENCE__PATH_FROM_ROOT);
		createEReference(dynamicContainmentTreeDomainModelReferenceEClass,
			DYNAMIC_CONTAINMENT_TREE_DOMAIN_MODEL_REFERENCE__PATH_FROM_BASE);
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

		// Obtain other dependent packages
		final VCategorizationPackage theCategorizationPackage = (VCategorizationPackage) EPackage.Registry.INSTANCE
			.getEPackage(VCategorizationPackage.eNS_URI);
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dynamicContainmentTreeEClass.getESuperTypes().add(theCategorizationPackage.getCategory());
		dynamicContainmentItemEClass.getESuperTypes().add(theCategorizationPackage.getCategorizableElement());
		testElementEClass.getESuperTypes().add(theEcorePackage.getEObject());
		dynamicContainmentTreeDomainModelReferenceEClass.getESuperTypes().add(theViewPackage.getDomainModelReference());

		// Initialize classes and features; add operations and parameters
		initEClass(dynamicContainmentTreeEClass, DynamicContainmentTree.class, "DynamicContainmentTree", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicContainmentTree_DomainModel(), ecorePackage.getEObject(), null, "domainModel", null,
			0, 1, DynamicContainmentTree.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentTree_ChildReference(), theEcorePackage.getEReference(), null,
			"childReference", null, 1, 1, DynamicContainmentTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentTree_PathToRoot(), theEcorePackage.getEReference(), null, "pathToRoot",
			null, 0, -1, DynamicContainmentTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentTree_ChildComposite(), theViewPackage.getContainedElement(), null,
			"childComposite", null, 0, 1, DynamicContainmentTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentTree_Items(), getDynamicContainmentItem(), null, "items", null, 0, -1,
			DynamicContainmentTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicContainmentItemEClass, DynamicContainmentItem.class, "DynamicContainmentItem", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicContainmentItem_DomainModel(), ecorePackage.getEObject(), null, "domainModel", null,
			0, 1, DynamicContainmentItem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentItem_Items(), getDynamicContainmentItem(), null, "items", null, 0, -1,
			DynamicContainmentItem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentItem_Composite(), theViewPackage.getContainedElement(), null, "composite",
			null, 0, 1, DynamicContainmentItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicContainmentItem_BaseItemIndex(), theEcorePackage.getEIntegerObject(), "baseItemIndex",
			null, 0, 1, DynamicContainmentItem.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
			!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testElementEClass, TestElement.class, "TestElement", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestElement_Id(), theEcorePackage.getEString(), "id", null, 0, 1, TestElement.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestElement_Elements(), getTestElement(), null, "elements", null, 0, -1,
			TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestElement_ParentId(), theEcorePackage.getEString(), "parentId", null, 0, 1,
			TestElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestElement_Name(), theEcorePackage.getEString(), "name", null, 0, 1, TestElement.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		final EOperation op = addEOperation(testElementEClass, theEcorePackage.getEBoolean(), "validate", 0, 1,
			IS_UNIQUE,
			IS_ORDERED);
		addEParameter(op, theEcorePackage.getEDiagnosticChain(), "diagnostic", 0, 1, IS_UNIQUE, IS_ORDERED);
		final EGenericType g1 = createEGenericType(theEcorePackage.getEMap());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(domainRootEClass, DomainRoot.class, "DomainRoot", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainRoot_Intermediate(), getDomainIntermediate(), null, "intermediate", null, 0, 1,
			DomainRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(domainIntermediateEClass, DomainIntermediate.class, "DomainIntermediate", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainIntermediate_TestElementContainer(), getTestElementContainer(), null,
			"testElementContainer", null, 0, 1, DomainIntermediate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testElementContainerEClass, TestElementContainer.class, "TestElementContainer", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTestElementContainer_TestElements(), getTestElement(), null, "testElements", null, 0,
			-1, TestElementContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestElementContainer_Id(), theEcorePackage.getEString(), "id", null, 0, 1,
			TestElementContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(dynamicContainmentTreeDomainModelReferenceEClass, DynamicContainmentTreeDomainModelReference.class,
			"DynamicContainmentTreeDomainModelReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicContainmentTreeDomainModelReference_PathFromRoot(),
			theViewPackage.getDomainModelReference(), null, "pathFromRoot", null, 0, 1,
			DynamicContainmentTreeDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDynamicContainmentTreeDomainModelReference_PathFromBase(),
			theViewPackage.getDomainModelReference(), null, "pathFromBase", null, 1, 1,
			DynamicContainmentTreeDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // ModelPackageImpl
