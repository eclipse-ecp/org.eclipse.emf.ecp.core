/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.table.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VElementUtil;
import org.eclipse.emf.ecp.view.spi.model.provider.ControlItemProvider;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TableControlItemProvider
	extends ControlItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TableControlItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addAddRemoveDisabledPropertyDescriptor(object);
			addMoveUpDownDisabledPropertyDescriptor(object);
			addDetailEditingPropertyDescriptor(object);
			addDetailViewPropertyDescriptor(object);
			addEnableDetailEditingDialogPropertyDescriptor(object);
			addDuplicateDisabledPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Add Remove Disabled feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addAddRemoveDisabledPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_addRemoveDisabled_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_addRemoveDisabled_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__ADD_REMOVE_DISABLED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Move Up Down Disabled feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addMoveUpDownDisabledPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_moveUpDownDisabled_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_moveUpDownDisabled_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__MOVE_UP_DOWN_DISABLED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Detail Editing feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addDetailEditingPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_detailEditing_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_detailEditing_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__DETAIL_EDITING,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Detail View feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addDetailViewPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_detailView_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_detailView_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__DETAIL_VIEW,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Enable Detail Editing Dialog feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addEnableDetailEditingDialogPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_enableDetailEditingDialog_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_enableDetailEditingDialog_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__ENABLE_DETAIL_EDITING_DIALOG,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Duplicate Disabled feature.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.18
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDuplicateDisabledPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableControl_duplicateDisabled_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableControl_duplicateDisabled_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableControl_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_CONTROL__DUPLICATE_DISABLED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(VTablePackage.Literals.TABLE_CONTROL__COLUMN_CONFIGURATIONS);
			childrenFeatures.add(VTablePackage.Literals.TABLE_CONTROL__DETAIL_VIEW);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns TableControl.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TableControl")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String label = ((VTableControl) object).getName();
		if (label == null) {
			label = VElementUtil.getCleanName(VElement.class.cast(object));
		}
		return label == null || label.length() == 0 ? getString("_UI_TableControl_type") //$NON-NLS-1$
			: getString("_UI_TableControl_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(VTableControl.class)) {
		case VTablePackage.TABLE_CONTROL__ADD_REMOVE_DISABLED:
		case VTablePackage.TABLE_CONTROL__MOVE_UP_DOWN_DISABLED:
		case VTablePackage.TABLE_CONTROL__DETAIL_EDITING:
		case VTablePackage.TABLE_CONTROL__ENABLE_DETAIL_EDITING_DIALOG:
		case VTablePackage.TABLE_CONTROL__DUPLICATE_DISABLED:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case VTablePackage.TABLE_CONTROL__COLUMN_CONFIGURATIONS:
		case VTablePackage.TABLE_CONTROL__DETAIL_VIEW:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(VTablePackage.Literals.TABLE_CONTROL__COLUMN_CONFIGURATIONS,
			VTableFactory.eINSTANCE.createReadOnlyColumnConfiguration()));

		newChildDescriptors.add(createChildParameter(VTablePackage.Literals.TABLE_CONTROL__COLUMN_CONFIGURATIONS,
			VTableFactory.eINSTANCE.createWidthConfiguration()));

		newChildDescriptors.add(createChildParameter(VTablePackage.Literals.TABLE_CONTROL__COLUMN_CONFIGURATIONS,
			VTableFactory.eINSTANCE.createEnablementConfiguration()));
	}

}
