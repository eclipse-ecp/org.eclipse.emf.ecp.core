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
package org.eclipse.emf.ecp.view.spi.indexdmr.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VIndexdmrFactoryImpl extends EFactoryImpl implements VIndexdmrFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static VIndexdmrFactory init() {
		try {
			final VIndexdmrFactory theIndexdmrFactory = (VIndexdmrFactory) EPackage.Registry.INSTANCE
				.getEFactory(VIndexdmrPackage.eNS_URI);
			if (theIndexdmrFactory != null) {
				return theIndexdmrFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VIndexdmrFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VIndexdmrFactoryImpl() {
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
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE:
			return createIndexDomainModelReference();
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return createIndexDomainModelReferenceSegment();
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
	public VIndexDomainModelReference createIndexDomainModelReference() {
		final VIndexDomainModelReferenceImpl indexDomainModelReference = new VIndexDomainModelReferenceImpl();
		return indexDomainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VIndexDomainModelReferenceSegment createIndexDomainModelReferenceSegment() {
		final VIndexDomainModelReferenceSegmentImpl indexDomainModelReferenceSegment = new VIndexDomainModelReferenceSegmentImpl();
		return indexDomainModelReferenceSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VIndexdmrPackage getIndexdmrPackage() {
		return (VIndexdmrPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VIndexdmrPackage getPackage() {
		return VIndexdmrPackage.eINSTANCE;
	}

} // VIndexdmrFactoryImpl
