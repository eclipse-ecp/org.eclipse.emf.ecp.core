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
package org.eclipse.emfforms.spi.view.indexsegment.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentFactory;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VIndexsegmentFactoryImpl extends EFactoryImpl implements VIndexsegmentFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static VIndexsegmentFactory init() {
		try {
			final VIndexsegmentFactory theIndexsegmentFactory = (VIndexsegmentFactory) EPackage.Registry.INSTANCE
				.getEFactory(VIndexsegmentPackage.eNS_URI);
			if (theIndexsegmentFactory != null) {
				return theIndexsegmentFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VIndexsegmentFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public VIndexsegmentFactoryImpl() {
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
		case VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return createIndexDomainModelReferenceSegment();
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
	public VIndexDomainModelReferenceSegment createIndexDomainModelReferenceSegment() {
		final VIndexDomainModelReferenceSegmentImpl indexDomainModelReferenceSegment = new VIndexDomainModelReferenceSegmentImpl();
		return indexDomainModelReferenceSegment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VIndexsegmentPackage getIndexsegmentPackage() {
		return (VIndexsegmentPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VIndexsegmentPackage getPackage() {
		return VIndexsegmentPackage.eINSTANCE;
	}

} // VIndexsegmentFactoryImpl
