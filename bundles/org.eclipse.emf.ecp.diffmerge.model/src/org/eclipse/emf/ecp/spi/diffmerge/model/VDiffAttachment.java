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

import org.eclipse.emf.ecp.view.spi.model.VAttachment;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Diff Attachment</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getTotalNumberOfDiffs <em>Total Number Of Diffs
 * </em>}</li>
 * <li>{@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getMergedDiffs <em>Merged Diffs</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage#getDiffAttachment()
 * @model
 * @generated
 */
public interface VDiffAttachment extends VAttachment {

	/**
	 * Returns the value of the '<em><b>Total Number Of Diffs</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Number Of Diffs</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Total Number Of Diffs</em>' attribute.
	 * @see #setTotalNumberOfDiffs(int)
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage#getDiffAttachment_TotalNumberOfDiffs()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getTotalNumberOfDiffs();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getTotalNumberOfDiffs
	 * <em>Total Number Of Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Total Number Of Diffs</em>' attribute.
	 * @see #getTotalNumberOfDiffs()
	 * @generated
	 */
	void setTotalNumberOfDiffs(int value);

	/**
	 * Returns the value of the '<em><b>Merged Diffs</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merged Diffs</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Merged Diffs</em>' attribute.
	 * @see #setMergedDiffs(int)
	 * @see org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage#getDiffAttachment_MergedDiffs()
	 * @model default="0"
	 * @generated
	 */
	int getMergedDiffs();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment#getMergedDiffs
	 * <em>Merged Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Merged Diffs</em>' attribute.
	 * @see #getMergedDiffs()
	 * @generated
	 */
	void setMergedDiffs(int value);
} // VDiffAttachment
