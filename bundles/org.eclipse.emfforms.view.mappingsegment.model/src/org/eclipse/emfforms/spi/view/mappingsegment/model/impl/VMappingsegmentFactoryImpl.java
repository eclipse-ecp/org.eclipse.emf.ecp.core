/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.mappingsegment.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VMappingsegmentFactoryImpl extends EFactoryImpl implements VMappingsegmentFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VMappingsegmentFactory init() {
		try {
			final VMappingsegmentFactory theMappingsegmentFactory = (VMappingsegmentFactory) EPackage.Registry.INSTANCE
				.getEFactory(VMappingsegmentPackage.eNS_URI);
			if (theMappingsegmentFactory != null) {
				return theMappingsegmentFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VMappingsegmentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VMappingsegmentFactoryImpl() {
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
		case VMappingsegmentPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return createMappingDomainModelReferenceSegment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VMappingDomainModelReferenceSegment createMappingDomainModelReferenceSegment() {
		final VMappingDomainModelReferenceSegmentImpl mappingDomainModelReferenceSegment = new VMappingDomainModelReferenceSegmentImpl();
		return mappingDomainModelReferenceSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VMappingsegmentPackage getMappingsegmentPackage() {
		return (VMappingsegmentPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VMappingsegmentPackage getPackage() {
		return VMappingsegmentPackage.eINSTANCE;
	}

} // VMappingsegmentFactoryImpl
