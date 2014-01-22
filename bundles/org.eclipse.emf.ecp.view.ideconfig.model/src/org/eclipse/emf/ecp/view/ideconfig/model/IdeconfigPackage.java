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
package org.eclipse.emf.ecp.view.ideconfig.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see org.eclipse.emf.ecp.view.ideconfig.model.IdeconfigFactory
 * @model kind="package"
 * @generated
 */
public interface IdeconfigPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "ideconfig"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/ideconfig/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.ideconfig.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	IdeconfigPackage eINSTANCE = org.eclipse.emf.ecp.view.ideconfig.model.impl.IdeconfigPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.view.ideconfig.model.impl.IDEConfigImpl
	 * <em>IDE Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecp.view.ideconfig.model.impl.IDEConfigImpl
	 * @see org.eclipse.emf.ecp.view.ideconfig.model.impl.IdeconfigPackageImpl#getIDEConfig()
	 * @generated
	 */
	int IDE_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Ecore Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDE_CONFIG__ECORE_PATH = 0;

	/**
	 * The number of structural features of the '<em>IDE Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDE_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IDE Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDE_CONFIG_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig <em>IDE Config</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>IDE Config</em>'.
	 * @see org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig
	 * @generated
	 */
	EClass getIDEConfig();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig#getEcorePath <em>Ecore Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ecore Path</em>'.
	 * @see org.eclipse.emf.ecp.view.ideconfig.model.IDEConfig#getEcorePath()
	 * @see #getIDEConfig()
	 * @generated
	 */
	EAttribute getIDEConfig_EcorePath();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IdeconfigFactory getIdeconfigFactory();

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
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.view.ideconfig.model.impl.IDEConfigImpl
		 * <em>IDE Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.ecp.view.ideconfig.model.impl.IDEConfigImpl
		 * @see org.eclipse.emf.ecp.view.ideconfig.model.impl.IdeconfigPackageImpl#getIDEConfig()
		 * @generated
		 */
		EClass IDE_CONFIG = eINSTANCE.getIDEConfig();

		/**
		 * The meta object literal for the '<em><b>Ecore Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute IDE_CONFIG__ECORE_PATH = eINSTANCE.getIDEConfig_EcorePath();

	}

} // IdeconfigPackage
