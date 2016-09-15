/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VMappingdmrFactoryImpl extends EFactoryImpl implements
	VMappingdmrFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static VMappingdmrFactory init() {
		try {
			final VMappingdmrFactory theMappingdmrFactory = (VMappingdmrFactory) EPackage.Registry.INSTANCE
				.getEFactory(VMappingdmrPackage.eNS_URI);
			if (theMappingdmrFactory != null) {
				return theMappingdmrFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VMappingdmrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public VMappingdmrFactoryImpl() {
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE:
			return createMappingDomainModelReference();
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return createMappingDomainModelReferenceSegment();
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
	public VMappingDomainModelReference createMappingDomainModelReference() {
		final VMappingDomainModelReferenceImpl mappingDomainModelReference = new VMappingDomainModelReferenceImpl();
		return mappingDomainModelReference;
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VMappingdmrPackage getMappingdmrPackage() {
		return (VMappingdmrPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VMappingdmrPackage getPackage() {
		return VMappingdmrPackage.eINSTANCE;
	}

} // VMappingdmrFactoryImpl
