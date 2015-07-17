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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VAttachmentImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Diff Attachment</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl#getTotalNumberOfDiffs <em>Total Number Of
 * Diffs</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.spi.diffmerge.model.impl.VDiffAttachmentImpl#getMergedDiffs <em>Merged Diffs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VDiffAttachmentImpl extends VAttachmentImpl implements
	VDiffAttachment {
	/**
	 * The default value of the '{@link #getTotalNumberOfDiffs() <em>Total Number Of Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTotalNumberOfDiffs()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_NUMBER_OF_DIFFS_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getTotalNumberOfDiffs() <em>Total Number Of Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTotalNumberOfDiffs()
	 * @generated
	 * @ordered
	 */
	protected int totalNumberOfDiffs = TOTAL_NUMBER_OF_DIFFS_EDEFAULT;
	/**
	 * The default value of the '{@link #getMergedDiffs() <em>Merged Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMergedDiffs()
	 * @generated
	 * @ordered
	 */
	protected static final int MERGED_DIFFS_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getMergedDiffs() <em>Merged Diffs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMergedDiffs()
	 * @generated
	 * @ordered
	 */
	protected int mergedDiffs = MERGED_DIFFS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VDiffAttachmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VDiffmergePackage.Literals.DIFF_ATTACHMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getTotalNumberOfDiffs()
	{
		return totalNumberOfDiffs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setTotalNumberOfDiffs(int newTotalNumberOfDiffs)
	{
		final int oldTotalNumberOfDiffs = totalNumberOfDiffs;
		totalNumberOfDiffs = newTotalNumberOfDiffs;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS, oldTotalNumberOfDiffs, totalNumberOfDiffs));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMergedDiffs()
	{
		return mergedDiffs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMergedDiffs(int newMergedDiffs)
	{
		final int oldMergedDiffs = mergedDiffs;
		mergedDiffs = newMergedDiffs;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS,
				oldMergedDiffs, mergedDiffs));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS:
			return getTotalNumberOfDiffs();
		case VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS:
			return getMergedDiffs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS:
			setTotalNumberOfDiffs((Integer) newValue);
			return;
		case VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS:
			setMergedDiffs((Integer) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS:
			setTotalNumberOfDiffs(TOTAL_NUMBER_OF_DIFFS_EDEFAULT);
			return;
		case VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS:
			setMergedDiffs(MERGED_DIFFS_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS:
			return totalNumberOfDiffs != TOTAL_NUMBER_OF_DIFFS_EDEFAULT;
		case VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS:
			return mergedDiffs != MERGED_DIFFS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (totalNumberOfDiffs: "); //$NON-NLS-1$
		result.append(totalNumberOfDiffs);
		result.append(", mergedDiffs: "); //$NON-NLS-1$
		result.append(mergedDiffs);
		result.append(')');
		return result.toString();
	}

} // VDiffAttachmentImpl
