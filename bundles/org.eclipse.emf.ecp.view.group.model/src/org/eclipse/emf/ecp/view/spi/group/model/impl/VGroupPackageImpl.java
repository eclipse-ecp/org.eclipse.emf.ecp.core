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
package org.eclipse.emf.ecp.view.spi.group.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.group.model.GroupLabelAlignment;
import org.eclipse.emf.ecp.view.spi.group.model.GroupType;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VGroupPackageImpl extends EPackageImpl implements VGroupPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass groupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum groupTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum groupLabelAlignmentEEnum = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VGroupPackageImpl()
	{
		super(eNS_URI, VGroupFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VGroupPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VGroupPackage init()
	{
		if (isInited) {
			return (VGroupPackage) EPackage.Registry.INSTANCE.getEPackage(VGroupPackage.eNS_URI);
		}

		// Obtain or create and register package
		final VGroupPackageImpl theGroupPackage = (VGroupPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VGroupPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new VGroupPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGroupPackage.createPackageContents();

		// Initialize created meta-data
		theGroupPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGroupPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VGroupPackage.eNS_URI, theGroupPackage);
		return theGroupPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getGroup()
	{
		return groupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGroup_GroupType()
	{
		return (EAttribute) groupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGroup_LabelAlignment()
	{
		return (EAttribute) groupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGroup_Collapsed()
	{
		return (EAttribute) groupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.4
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getGroupType()
	{
		return groupTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getGroupLabelAlignment()
	{
		return groupLabelAlignmentEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VGroupFactory getGroupFactory()
	{
		return (VGroupFactory) getEFactoryInstance();
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
		groupEClass = createEClass(GROUP);
		createEAttribute(groupEClass, GROUP__GROUP_TYPE);
		createEAttribute(groupEClass, GROUP__LABEL_ALIGNMENT);
		createEAttribute(groupEClass, GROUP__COLLAPSED);

		// Create enums
		groupTypeEEnum = createEEnum(GROUP_TYPE);
		groupLabelAlignmentEEnum = createEEnum(GROUP_LABEL_ALIGNMENT);
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
		final VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);
		final EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
			.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		groupEClass.getESuperTypes().add(theViewPackage.getContainedContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(groupEClass, VGroup.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
			getGroup_GroupType(),
			getGroupType(),
			"groupType", null, 1, 1, VGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
			getGroup_LabelAlignment(),
			getGroupLabelAlignment(),
			"labelAlignment", "LabelAligned", 0, 1, VGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(
			getGroup_Collapsed(),
			theEcorePackage.getEBoolean(),
			"collapsed", null, 0, 1, VGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(groupTypeEEnum, GroupType.class, "GroupType"); //$NON-NLS-1$
		addEEnumLiteral(groupTypeEEnum, GroupType.NORMAL);
		addEEnumLiteral(groupTypeEEnum, GroupType.EMBEDDED);
		addEEnumLiteral(groupTypeEEnum, GroupType.COLLAPSIBLE);

		initEEnum(groupLabelAlignmentEEnum, GroupLabelAlignment.class, "GroupLabelAlignment"); //$NON-NLS-1$
		addEEnumLiteral(groupLabelAlignmentEEnum, GroupLabelAlignment.LABEL_ALIGNED);
		addEEnumLiteral(groupLabelAlignmentEEnum, GroupLabelAlignment.INPUT_ALIGNED);

		// Create resource
		createResource(eNS_URI);
	}

} // VGroupPackageImpl
