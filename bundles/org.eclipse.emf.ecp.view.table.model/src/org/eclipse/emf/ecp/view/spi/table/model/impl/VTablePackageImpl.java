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
package org.eclipse.emf.ecp.view.spi.table.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.util.TableValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class VTablePackageImpl extends EPackageImpl implements VTablePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableControlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableColumnConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tableDomainModelReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass readOnlyColumnConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass widthConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum detailEditingEEnum = null;

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
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTablePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VTablePackageImpl() {
		super(eNS_URI, VTableFactory.eINSTANCE);
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
	 * This method is used to initialize {@link VTablePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VTablePackage init() {
		if (isInited)
			return (VTablePackage) EPackage.Registry.INSTANCE.getEPackage(VTablePackage.eNS_URI);

		// Obtain or create and register package
		VTablePackageImpl theTablePackage = (VTablePackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof VTablePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new VTablePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		VViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTablePackage.createPackageContents();

		// Initialize created meta-data
		theTablePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theTablePackage,
			new EValidator.Descriptor() {
				public EValidator getEValidator() {
					return TableValidator.INSTANCE;
				}
			});

		// Mark meta-data to indicate it can't be changed
		theTablePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VTablePackage.eNS_URI, theTablePackage);
		return theTablePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableControl() {
		return tableControlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTableControl_AddRemoveDisabled() {
		return (EAttribute) tableControlEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableControl_ColumnConfigurations() {
		return (EReference) tableControlEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTableControl_DetailEditing() {
		return (EAttribute) tableControlEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableControl_DetailView() {
		return (EReference) tableControlEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTableControl_EnableDetailEditingDialog() {
		return (EAttribute) tableControlEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableColumnConfiguration() {
		return tableColumnConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTableDomainModelReference() {
		return tableDomainModelReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTableDomainModelReference_ColumnDomainModelReferences() {
		return (EReference) tableDomainModelReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTableDomainModelReference_DomainModelReference() {
		return (EReference) tableDomainModelReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getReadOnlyColumnConfiguration() {
		return readOnlyColumnConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getReadOnlyColumnConfiguration_ColumnDomainReferences() {
		return (EReference) readOnlyColumnConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getWidthConfiguration() {
		return widthConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getWidthConfiguration_ColumnDomainReference() {
		return (EReference) widthConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWidthConfiguration_Weight() {
		return (EAttribute) widthConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getWidthConfiguration_MinWidth() {
		return (EAttribute) widthConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getDetailEditing() {
		return detailEditingEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VTableFactory getTableFactory() {
		return (VTableFactory) getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		tableControlEClass = createEClass(TABLE_CONTROL);
		createEAttribute(tableControlEClass, TABLE_CONTROL__ADD_REMOVE_DISABLED);
		createEReference(tableControlEClass, TABLE_CONTROL__COLUMN_CONFIGURATIONS);
		createEAttribute(tableControlEClass, TABLE_CONTROL__DETAIL_EDITING);
		createEReference(tableControlEClass, TABLE_CONTROL__DETAIL_VIEW);
		createEAttribute(tableControlEClass, TABLE_CONTROL__ENABLE_DETAIL_EDITING_DIALOG);

		tableColumnConfigurationEClass = createEClass(TABLE_COLUMN_CONFIGURATION);

		tableDomainModelReferenceEClass = createEClass(TABLE_DOMAIN_MODEL_REFERENCE);
		createEReference(tableDomainModelReferenceEClass, TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES);
		createEReference(tableDomainModelReferenceEClass, TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE);

		readOnlyColumnConfigurationEClass = createEClass(READ_ONLY_COLUMN_CONFIGURATION);
		createEReference(readOnlyColumnConfigurationEClass, READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES);

		widthConfigurationEClass = createEClass(WIDTH_CONFIGURATION);
		createEReference(widthConfigurationEClass, WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE);
		createEAttribute(widthConfigurationEClass, WIDTH_CONFIGURATION__WEIGHT);
		createEAttribute(widthConfigurationEClass, WIDTH_CONFIGURATION__MIN_WIDTH);

		// Create enums
		detailEditingEEnum = createEEnum(DETAIL_EDITING);
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
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		VViewPackage theViewPackage = (VViewPackage) EPackage.Registry.INSTANCE.getEPackage(VViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tableControlEClass.getESuperTypes().add(theViewPackage.getControl());
		tableDomainModelReferenceEClass.getESuperTypes().add(theViewPackage.getFeaturePathDomainModelReference());
		readOnlyColumnConfigurationEClass.getESuperTypes().add(this.getTableColumnConfiguration());
		widthConfigurationEClass.getESuperTypes().add(this.getTableColumnConfiguration());

		// Initialize classes and features; add operations and parameters
		initEClass(tableControlEClass, VTableControl.class, "TableControl", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableControl_AddRemoveDisabled(), ecorePackage.getEBoolean(), "addRemoveDisabled", "false", 1, //$NON-NLS-1$ //$NON-NLS-2$
			1, VTableControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getTableControl_ColumnConfigurations(), this.getTableColumnConfiguration(), null,
			"columnConfigurations", null, 0, -1, VTableControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableControl_DetailEditing(), this.getDetailEditing(), "detailEditing", "None", 1, 1, //$NON-NLS-1$ //$NON-NLS-2$
			VTableControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getTableControl_DetailView(), theViewPackage.getView(), null, "detailView", null, 0, 1, //$NON-NLS-1$
			VTableControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableControl_EnableDetailEditingDialog(), ecorePackage.getEBoolean(),
			"enableDetailEditingDialog", "false", 0, 1, VTableControl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$ //$NON-NLS-2$
			!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableColumnConfigurationEClass, VTableColumnConfiguration.class, "TableColumnConfiguration", //$NON-NLS-1$
			IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tableDomainModelReferenceEClass, VTableDomainModelReference.class, "TableDomainModelReference", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTableDomainModelReference_ColumnDomainModelReferences(),
			theViewPackage.getDomainModelReference(), null, "columnDomainModelReferences", null, 0, -1, //$NON-NLS-1$
			VTableDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableDomainModelReference_DomainModelReference(), theViewPackage.getDomainModelReference(),
			null, "domainModelReference", null, 0, 1, VTableDomainModelReference.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
			IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(readOnlyColumnConfigurationEClass, VReadOnlyColumnConfiguration.class, "ReadOnlyColumnConfiguration", //$NON-NLS-1$
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReadOnlyColumnConfiguration_ColumnDomainReferences(),
			theViewPackage.getDomainModelReference(), null, "columnDomainReferences", null, 0, -1, //$NON-NLS-1$
			VReadOnlyColumnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(widthConfigurationEClass, VWidthConfiguration.class, "WidthConfiguration", !IS_ABSTRACT, //$NON-NLS-1$
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWidthConfiguration_ColumnDomainReference(), theViewPackage.getDomainModelReference(), null,
			"columnDomainReference", null, 1, 1, VWidthConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidthConfiguration_Weight(), ecorePackage.getEInt(), "weight", null, 1, 1, //$NON-NLS-1$
			VWidthConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getWidthConfiguration_MinWidth(), ecorePackage.getEInt(), "minWidth", null, 1, 1, //$NON-NLS-1$
			VWidthConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(detailEditingEEnum, DetailEditing.class, "DetailEditing"); //$NON-NLS-1$
		addEEnumLiteral(detailEditingEEnum, DetailEditing.NONE);
		addEEnumLiteral(detailEditingEEnum, DetailEditing.WITH_DIALOG);
		addEEnumLiteral(detailEditingEEnum, DetailEditing.WITH_PANEL);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation(tableControlEClass,
			source,
			new String[] {
				"constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
		addAnnotation(tableDomainModelReferenceEClass,
			source,
			new String[] {
				"constraints", "resolveable" //$NON-NLS-1$ //$NON-NLS-2$
			});
	}

} // VTablePackageImpl
