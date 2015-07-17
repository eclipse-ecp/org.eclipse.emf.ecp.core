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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.dynamictree.model.DomainIntermediate;
import org.eclipse.emf.ecp.view.dynamictree.model.DomainRoot;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentItem;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTree;
import org.eclipse.emf.ecp.view.dynamictree.model.DynamicContainmentTreeDomainModelReference;
import org.eclipse.emf.ecp.view.dynamictree.model.ModelFactory;
import org.eclipse.emf.ecp.view.dynamictree.model.ModelPackage;
import org.eclipse.emf.ecp.view.dynamictree.model.TestElement;
import org.eclipse.emf.ecp.view.dynamictree.model.TestElementContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static ModelFactory init()
	{
		try {
			final ModelFactory theModelFactory = (ModelFactory) EPackage.Registry.INSTANCE
				.getEFactory(ModelPackage.eNS_URI);
			if (theModelFactory != null) {
				return theModelFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ModelFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID()) {
		case ModelPackage.DYNAMIC_CONTAINMENT_TREE:
			return createDynamicContainmentTree();
		case ModelPackage.DYNAMIC_CONTAINMENT_ITEM:
			return createDynamicContainmentItem();
		case ModelPackage.TEST_ELEMENT:
			return createTestElement();
		case ModelPackage.DOMAIN_ROOT:
			return createDomainRoot();
		case ModelPackage.DOMAIN_INTERMEDIATE:
			return createDomainIntermediate();
		case ModelPackage.TEST_ELEMENT_CONTAINER:
			return createTestElementContainer();
		case ModelPackage.DYNAMIC_CONTAINMENT_TREE_DOMAIN_MODEL_REFERENCE:
			return createDynamicContainmentTreeDomainModelReference();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DynamicContainmentTree createDynamicContainmentTree()
	{
		final DynamicContainmentTreeImpl dynamicContainmentTree = new DynamicContainmentTreeImpl();
		return dynamicContainmentTree;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DynamicContainmentItem createDynamicContainmentItem()
	{
		final DynamicContainmentItemImpl dynamicContainmentItem = new DynamicContainmentItemImpl();
		return dynamicContainmentItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TestElement createTestElement()
	{
		final TestElementImpl testElement = new TestElementImpl();
		return testElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DomainRoot createDomainRoot()
	{
		final DomainRootImpl domainRoot = new DomainRootImpl();
		return domainRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DomainIntermediate createDomainIntermediate()
	{
		final DomainIntermediateImpl domainIntermediate = new DomainIntermediateImpl();
		return domainIntermediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TestElementContainer createTestElementContainer()
	{
		final TestElementContainerImpl testElementContainer = new TestElementContainerImpl();
		return testElementContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DynamicContainmentTreeDomainModelReference createDynamicContainmentTreeDomainModelReference() {
		final DynamicContainmentTreeDomainModelReferenceImpl dynamicContainmentTreeDomainModelReference = new DynamicContainmentTreeDomainModelReferenceImpl();
		return dynamicContainmentTreeDomainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelPackage getModelPackage()
	{
		return (ModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage()
	{
		return ModelPackage.eINSTANCE;
	}

} // ModelFactoryImpl
