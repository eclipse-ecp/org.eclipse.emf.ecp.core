/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryFactory;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VRulerepositoryFactoryImpl extends EFactoryImpl implements VRulerepositoryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static VRulerepositoryFactory init() {
		try {
			final VRulerepositoryFactory theRulerepositoryFactory = (VRulerepositoryFactory) EPackage.Registry.INSTANCE
				.getEFactory(VRulerepositoryPackage.eNS_URI);
			if (theRulerepositoryFactory != null) {
				return theRulerepositoryFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VRulerepositoryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public VRulerepositoryFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case VRulerepositoryPackage.RULE_REPOSITORY:
			return createRuleRepository();
		case VRulerepositoryPackage.RULE_ENTRY:
			return createRuleEntry();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VRuleRepository createRuleRepository() {
		final VRuleRepositoryImpl ruleRepository = new VRuleRepositoryImpl();
		return ruleRepository;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VRuleEntry createRuleEntry() {
		final VRuleEntryImpl ruleEntry = new VRuleEntryImpl();
		return ruleEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VRulerepositoryPackage getRulerepositoryPackage() {
		return (VRulerepositoryPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VRulerepositoryPackage getPackage() {
		return VRulerepositoryPackage.eINSTANCE;
	}

} // VRulerepositoryFactoryImpl
