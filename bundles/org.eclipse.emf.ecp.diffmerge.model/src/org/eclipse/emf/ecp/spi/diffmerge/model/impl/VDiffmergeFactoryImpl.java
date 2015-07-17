/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.spi.diffmerge.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergeFactory;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class VDiffmergeFactoryImpl extends EFactoryImpl implements
	VDiffmergeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static VDiffmergeFactory init() {
		try
		{
			final VDiffmergeFactory theDiffmergeFactory = (VDiffmergeFactory) EPackage.Registry.INSTANCE
				.getEFactory(VDiffmergePackage.eNS_URI);
			if (theDiffmergeFactory != null)
			{
				return theDiffmergeFactory;
			}
		} catch (final Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VDiffmergeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public VDiffmergeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
		case VDiffmergePackage.DIFF_ATTACHMENT:
			return createDiffAttachment();
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
	public VDiffAttachment createDiffAttachment() {
		final VDiffAttachmentImpl diffAttachment = new VDiffAttachmentImpl();
		return diffAttachment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDiffmergePackage getDiffmergePackage() {
		return (VDiffmergePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VDiffmergePackage getPackage() {
		return VDiffmergePackage.eINSTANCE;
	}

} // VDiffmergeFactoryImpl
