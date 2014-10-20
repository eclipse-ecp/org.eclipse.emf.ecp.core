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
package org.eclipse.emf.ecp.view.spi.table.model;

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
 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableFactory
 * @model kind="package"
 * @generated
 */
public interface VTablePackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "table"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/table/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.table.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	VTablePackage eINSTANCE = org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableControlImpl
	 * <em>Control</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTableControlImpl
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableControl()
	 * @generated
	 */
	int TABLE_CONTROL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__NAME = VViewPackage.CONTROL__NAME;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__VISIBLE = VViewPackage.CONTROL__VISIBLE;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__ENABLED = VViewPackage.CONTROL__ENABLED;

	/**
	 * The feature id for the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__READONLY = VViewPackage.CONTROL__READONLY;

	/**
	 * The feature id for the '<em><b>Diagnostic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__DIAGNOSTIC = VViewPackage.CONTROL__DIAGNOSTIC;

	/**
	 * The feature id for the '<em><b>Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__ATTACHMENTS = VViewPackage.CONTROL__ATTACHMENTS;

	/**
	 * The feature id for the '<em><b>Label Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__LABEL_ALIGNMENT = VViewPackage.CONTROL__LABEL_ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__DOMAIN_MODEL_REFERENCE = VViewPackage.CONTROL__DOMAIN_MODEL_REFERENCE;

	/**
	 * The feature id for the '<em><b>Add Remove Disabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__ADD_REMOVE_DISABLED = VViewPackage.CONTROL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Column Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__COLUMN_CONFIGURATIONS = VViewPackage.CONTROL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Detail Editing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__DETAIL_EDITING = VViewPackage.CONTROL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Detail View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__DETAIL_VIEW = VViewPackage.CONTROL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Enable Detail Editing Dialog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL__ENABLE_DETAIL_EDITING_DIALOG = VViewPackage.CONTROL_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Control</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_CONTROL_FEATURE_COUNT = VViewPackage.CONTROL_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
	 * <em>Column Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableColumnConfiguration()
	 * @generated
	 */
	int TABLE_COLUMN_CONFIGURATION = 1;

	/**
	 * The number of structural features of the '<em>Column Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_COLUMN_CONFIGURATION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl
	 * <em>Domain Model Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableDomainModelReference()
	 * @generated
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Change Listener</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER;

	/**
	 * The feature id for the '<em><b>Domain Model EFeature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE;

	/**
	 * The feature id for the '<em><b>Domain Model EReference Path</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH;

	/**
	 * The feature id for the '<em><b>Column Domain Model References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain Model Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Domain Model Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TABLE_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT = VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.impl.VReadOnlyColumnConfigurationImpl
	 * <em>Read Only Column Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VReadOnlyColumnConfigurationImpl
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getReadOnlyColumnConfiguration()
	 * @generated
	 */
	int READ_ONLY_COLUMN_CONFIGURATION = 3;

	/**
	 * The feature id for the '<em><b>Column Domain References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES = TABLE_COLUMN_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Read Only Column Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int READ_ONLY_COLUMN_CONFIGURATION_FEATURE_COUNT = TABLE_COLUMN_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * <em>Detail Editing</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getDetailEditing()
	 * @generated
	 */
	int DETAIL_EDITING = 4;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl
	 * <em>Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Control</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl
	 * @generated
	 */
	EClass getTableControl();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled <em>Add Remove Disabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Add Remove Disabled</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isAddRemoveDisabled()
	 * @see #getTableControl()
	 * @generated
	 */
	EAttribute getTableControl_AddRemoveDisabled();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getColumnConfigurations
	 * <em>Column Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Column Configurations</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getColumnConfigurations()
	 * @see #getTableControl()
	 * @generated
	 */
	EReference getTableControl_ColumnConfigurations();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailEditing <em>Detail Editing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Detail Editing</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailEditing()
	 * @see #getTableControl()
	 * @generated
	 */
	EAttribute getTableControl_DetailEditing();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailView <em>Detail View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Detail View</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl#getDetailView()
	 * @see #getTableControl()
	 * @generated
	 */
	EReference getTableControl_DetailView();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isEnableDetailEditingDialog
	 * <em>Enable Detail Editing Dialog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Detail Editing Dialog</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableControl#isEnableDetailEditingDialog()
	 * @see #getTableControl()
	 * @generated
	 */
	EAttribute getTableControl_EnableDetailEditingDialog();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
	 * <em>Column Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Column Configuration</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
	 * @generated
	 */
	EClass getTableColumnConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference
	 * @generated
	 */
	EClass getTableDomainModelReference();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getColumnDomainModelReferences
	 * <em>Column Domain Model References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Column Domain Model References</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getColumnDomainModelReferences()
	 * @see #getTableDomainModelReference()
	 * @generated
	 */
	EReference getTableDomainModelReference_ColumnDomainModelReferences();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getDomainModelReference
	 * <em>Domain Model Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Domain Model Reference</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference#getDomainModelReference()
	 * @see #getTableDomainModelReference()
	 * @generated
	 */
	EReference getTableDomainModelReference_DomainModelReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration
	 * <em>Read Only Column Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Read Only Column Configuration</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration
	 * @generated
	 */
	EClass getReadOnlyColumnConfiguration();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration#getColumnDomainReferences
	 * <em>Column Domain References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Column Domain References</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration#getColumnDomainReferences()
	 * @see #getReadOnlyColumnConfiguration()
	 * @generated
	 */
	EReference getReadOnlyColumnConfiguration_ColumnDomainReferences();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * <em>Detail Editing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Detail Editing</em>'.
	 * @see org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
	 * @generated
	 */
	EEnum getDetailEditing();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VTableFactory getTableFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableControlImpl
		 * <em>Control</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTableControlImpl
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableControl()
		 * @generated
		 */
		EClass TABLE_CONTROL = eINSTANCE.getTableControl();

		/**
		 * The meta object literal for the '<em><b>Add Remove Disabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTROL__ADD_REMOVE_DISABLED = eINSTANCE.getTableControl_AddRemoveDisabled();

		/**
		 * The meta object literal for the '<em><b>Column Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CONTROL__COLUMN_CONFIGURATIONS = eINSTANCE.getTableControl_ColumnConfigurations();

		/**
		 * The meta object literal for the '<em><b>Detail Editing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTROL__DETAIL_EDITING = eINSTANCE.getTableControl_DetailEditing();

		/**
		 * The meta object literal for the '<em><b>Detail View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_CONTROL__DETAIL_VIEW = eINSTANCE.getTableControl_DetailView();

		/**
		 * The meta object literal for the '<em><b>Enable Detail Editing Dialog</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TABLE_CONTROL__ENABLE_DETAIL_EDITING_DIALOG = eINSTANCE.getTableControl_EnableDetailEditingDialog();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
		 * <em>Column Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableColumnConfiguration()
		 * @generated
		 */
		EClass TABLE_COLUMN_CONFIGURATION = eINSTANCE.getTableColumnConfiguration();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl
		 * <em>Domain Model Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getTableDomainModelReference()
		 * @generated
		 */
		EClass TABLE_DOMAIN_MODEL_REFERENCE = eINSTANCE.getTableDomainModelReference();

		/**
		 * The meta object literal for the '<em><b>Column Domain Model References</b></em>' containment reference list
		 * feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES = eINSTANCE
			.getTableDomainModelReference_ColumnDomainModelReferences();

		/**
		 * The meta object literal for the '<em><b>Domain Model Reference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE = eINSTANCE
			.getTableDomainModelReference_DomainModelReference();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.emf.ecp.view.spi.table.model.impl.VReadOnlyColumnConfigurationImpl
		 * <em>Read Only Column Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VReadOnlyColumnConfigurationImpl
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getReadOnlyColumnConfiguration()
		 * @generated
		 */
		EClass READ_ONLY_COLUMN_CONFIGURATION = eINSTANCE.getReadOnlyColumnConfiguration();

		/**
		 * The meta object literal for the '<em><b>Column Domain References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES = eINSTANCE
			.getReadOnlyColumnConfiguration_ColumnDomainReferences();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
		 * <em>Detail Editing</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.table.model.DetailEditing
		 * @see org.eclipse.emf.ecp.view.spi.table.model.impl.VTablePackageImpl#getDetailEditing()
		 * @generated
		 */
		EEnum DETAIL_EDITING = eINSTANCE.getDetailEditing();

	}

} // VTablePackage
