/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyPackage;
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
 * {@link org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class TableStylePropertyItemProvider
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
	public TableStylePropertyItemProvider(AdapterFactory adapterFactory) {
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

			addMinimumHeightPropertyDescriptor(object);
			addMaximumHeightPropertyDescriptor(object);
			addShowValidationSummaryTooltipPropertyDescriptor(object);
			addEnableSortingPropertyDescriptor(object);
			addVisibleLinesPropertyDescriptor(object);
			addRenderModePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Minimum Height feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMinimumHeightPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_minimumHeight_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableStyleProperty_minimumHeight_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableStyleProperty_type"), //$NON-NLS-1$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Maximum Height feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMaximumHeightPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_maximumHeight_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableStyleProperty_maximumHeight_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableStyleProperty_type"), //$NON-NLS-1$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Show Validation Summary Tooltip feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addShowValidationSummaryTooltipPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_showValidationSummaryTooltip_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_TableStyleProperty_showValidationSummaryTooltip_feature", "_UI_TableStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Enable Sorting feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEnableSortingPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_enableSorting_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableStyleProperty_enableSorting_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableStyleProperty_type"), //$NON-NLS-1$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__ENABLE_SORTING,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Visible Lines feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addVisibleLinesPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_visibleLines_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableStyleProperty_visibleLines_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableStyleProperty_type"), //$NON-NLS-1$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__VISIBLE_LINES,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Render Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRenderModePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableStyleProperty_renderMode_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_TableStyleProperty_renderMode_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_TableStyleProperty_type"), //$NON-NLS-1$
				VTTableStylePropertyPackage.Literals.TABLE_STYLE_PROPERTY__RENDER_MODE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns TableStyleProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TableStyleProperty")); //$NON-NLS-1$
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
		final VTTableStyleProperty tableStyleProperty = (VTTableStyleProperty) object;
		return getString("_UI_TableStyleProperty_type") + " " + tableStyleProperty.getMinimumHeight(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VTTableStyleProperty.class)) {
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MINIMUM_HEIGHT:
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__MAXIMUM_HEIGHT:
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__SHOW_VALIDATION_SUMMARY_TOOLTIP:
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__ENABLE_SORTING:
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__VISIBLE_LINES:
		case VTTableStylePropertyPackage.TABLE_STYLE_PROPERTY__RENDER_MODE:
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
