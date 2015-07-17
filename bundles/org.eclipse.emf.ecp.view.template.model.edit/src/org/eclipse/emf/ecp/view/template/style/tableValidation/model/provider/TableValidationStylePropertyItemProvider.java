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
package org.eclipse.emf.ecp.view.template.style.tableValidation.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationPackage;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TableValidationStylePropertyItemProvider
	extends ItemProviderAdapter
	implements
	IEditingDomainItemProvider,
	IStructuredItemContentProvider,
	ITreeItemContentProvider,
	IItemLabelProvider,
	IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TableValidationStylePropertyItemProvider(AdapterFactory adapterFactory) {
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

			addColumnWidthPropertyDescriptor(object);
			addColumnNamePropertyDescriptor(object);
			addImagePathPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Column Width feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addColumnWidthPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableValidationStyleProperty_columnWidth_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TableValidationStyleProperty_columnWidth_feature", "_UI_TableValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTTableValidationPackage.Literals.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Column Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addColumnNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableValidationStyleProperty_columnName_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TableValidationStyleProperty_columnName_feature", "_UI_TableValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTTableValidationPackage.Literals.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Image Path feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addImagePathPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableValidationStyleProperty_imagePath_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_TableValidationStyleProperty_imagePath_feature", "_UI_TableValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTTableValidationPackage.Literals.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns TableValidationStyleProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TableValidationStyleProperty")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		final String label = ((VTTableValidationStyleProperty) object).getColumnName();
		return label == null || label.length() == 0 ?
			getString("_UI_TableValidationStyleProperty_type") : //$NON-NLS-1$
			getString("_UI_TableValidationStyleProperty_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VTTableValidationStyleProperty.class)) {
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_WIDTH:
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__COLUMN_NAME:
		case VTTableValidationPackage.TABLE_VALIDATION_STYLE_PROPERTY__IMAGE_PATH:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ((IChildCreationExtender) adapterFactory).getResourceLocator();
	}

}
