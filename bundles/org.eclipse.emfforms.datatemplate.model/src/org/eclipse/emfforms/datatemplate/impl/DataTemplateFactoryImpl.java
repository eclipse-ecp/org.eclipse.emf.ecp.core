/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen GmbH - initial API and implementation
 */
package org.eclipse.emfforms.datatemplate.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class DataTemplateFactoryImpl extends EFactoryImpl implements DataTemplateFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DataTemplateFactory init() {
		try {
			final DataTemplateFactory theDataTemplateFactory = (DataTemplateFactory) EPackage.Registry.INSTANCE
				.getEFactory(DataTemplatePackage.eNS_URI);
			if (theDataTemplateFactory != null) {
				return theDataTemplateFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DataTemplateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DataTemplateFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case DataTemplatePackage.TEMPLATE:
			return createTemplate();
		case DataTemplatePackage.TEMPLATE_COLLECTION:
			return createTemplateCollection();
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
	public Template createTemplate() {
		final TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TemplateCollection createTemplateCollection() {
		final TemplateCollectionImpl templateCollection = new TemplateCollectionImpl();
		return templateCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DataTemplatePackage getDataTemplatePackage() {
		return (DataTemplatePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DataTemplatePackage getPackage() {
		return DataTemplatePackage.eINSTANCE;
	}

} // DataTemplateFactoryImpl
