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
package org.eclipse.emf.ecp.spi.diffmerge.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergeFactory
 * @model kind="package"
 * @generated
 */
public interface VDiffmergePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "diffmerge"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/ecp/view/diffmerge/model"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.ecp.view.diffmerge.model"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	VDiffmergePackage eINSTANCE = org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffmergePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl
	 * <em>Diff Attachment</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffmergePackageImpl#getDiffAttachment()
	 * @generated
	 */
	int DIFF_ATTACHMENT = 0;

	/**
	 * The feature id for the '<em><b>Total Number Of Diffs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS = VViewPackage.ATTACHMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Merged Diffs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DIFF_ATTACHMENT__MERGED_DIFFS = VViewPackage.ATTACHMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Diff Attachment</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int DIFF_ATTACHMENT_FEATURE_COUNT = VViewPackage.ATTACHMENT_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment
	 * <em>Diff Attachment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Diff Attachment</em>'.
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment
	 * @generated
	 */
	EClass getDiffAttachment();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getTotalNumberOfDiffs
	 * <em>Total Number Of Diffs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Total Number Of Diffs</em>'.
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getTotalNumberOfDiffs()
	 * @see #getDiffAttachment()
	 * @generated
	 */
	EAttribute getDiffAttachment_TotalNumberOfDiffs();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getMergedDiffs <em>Merged Diffs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Merged Diffs</em>'.
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getMergedDiffs()
	 * @see #getDiffAttachment()
	 * @generated
	 */
	EAttribute getDiffAttachment_MergedDiffs();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VDiffmergeFactory getDiffmergeFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
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
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl
		 * <em>Diff Attachment</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @see org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl
		 * @see org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffmergePackageImpl#getDiffAttachment()
		 * @generated
		 */
		EClass DIFF_ATTACHMENT = eINSTANCE.getDiffAttachment();
		/**
		 * The meta object literal for the '<em><b>Total Number Of Diffs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS = eINSTANCE.getDiffAttachment_TotalNumberOfDiffs();
		/**
		 * The meta object literal for the '<em><b>Merged Diffs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute DIFF_ATTACHMENT__MERGED_DIFFS = eINSTANCE.getDiffAttachment_MergedDiffs();

	}

} // VDiffmergePackage
