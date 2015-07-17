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
package org.eclipse.emf.ecp.spi.diffmerge.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment;
import org.eclipse.emf.ecp.spi.diffmerge.model.VDiffmergePackage;
import org.eclipse.emf.ecp.view.spi.model.provider.AttachmentItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.spi.diffmerge.model.VDiffAttachment} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DiffAttachmentItemProvider extends AttachmentItemProvider
	implements IEditingDomainItemProvider, IStructuredItemContentProvider,
	ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public DiffAttachmentItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addTotalNumberOfDiffsPropertyDescriptor(object);
			addMergedDiffsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Total Number Of Diffs feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTotalNumberOfDiffsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_DiffAttachment_totalNumberOfDiffs_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_DiffAttachment_totalNumberOfDiffs_feature", "_UI_DiffAttachment_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VDiffmergePackage.Literals.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS,
				true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Merged Diffs feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addMergedDiffsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_DiffAttachment_mergedDiffs_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_DiffAttachment_mergedDiffs_feature", "_UI_DiffAttachment_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VDiffmergePackage.Literals.DIFF_ATTACHMENT__MERGED_DIFFS,
				true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns DiffAttachment.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object,
			getResourceLocator().getImage("full/obj16/DiffAttachment")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		final VDiffAttachment diffAttachment = (VDiffAttachment) object;
		//		return getString("_UI_DiffAttachment_type") + " " + diffAttachment.getTotalNumberOfDiffs(); //$NON-NLS-1$ //$NON-NLS-2$
		return getString(
			"_UI_DiffAttachment_text", new Object[] { diffAttachment.getMergedDiffs(), diffAttachment.getTotalNumberOfDiffs() });//$NON-NLS-1$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated NOT
	 * @param notification the {@link Notification} to propagate
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(VDiffAttachment.class)) {
		case VDiffmergePackage.DIFF_ATTACHMENT__TOTAL_NUMBER_OF_DIFFS:
		case VDiffmergePackage.DIFF_ATTACHMENT__MERGED_DIFFS:
			fireNotifyChanged(new ViewerNotification(notification,
				notification.getNotifier(), false, true));
			fireNotifyChanged(new ViewerNotification(notification,
				((EObject) notification.getNotifier()).eContainer(), false, true));
			return;
		default:
			super.notifyChanged(notification);
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
		Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
