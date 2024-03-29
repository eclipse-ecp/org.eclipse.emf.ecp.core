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
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
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
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate}
 * object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ControlValidationTemplateItemProvider
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
	public ControlValidationTemplateItemProvider(AdapterFactory adapterFactory) {
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

			addOkColorHEXPropertyDescriptor(object);
			addOkForegroundColorHEXPropertyDescriptor(object);
			addOkImageURLPropertyDescriptor(object);
			addOkOverlayURLPropertyDescriptor(object);
			addInfoColorHEXPropertyDescriptor(object);
			addInfoForegroundColorHEXPropertyDescriptor(object);
			addInfoImageURLPropertyDescriptor(object);
			addInfoOverlayURLPropertyDescriptor(object);
			addWarningColorHEXPropertyDescriptor(object);
			addWarningForegroundColorHEXPropertyDescriptor(object);
			addWarningImageURLPropertyDescriptor(object);
			addWarningOverlayURLPropertyDescriptor(object);
			addErrorColorHEXPropertyDescriptor(object);
			addErrorForegroundColorHEXPropertyDescriptor(object);
			addErrorImageURLPropertyDescriptor(object);
			addErrorOverlayURLPropertyDescriptor(object);
			addCancelColorHEXPropertyDescriptor(object);
			addCancelForegroundColorHEXPropertyDescriptor(object);
			addCancelImageURLPropertyDescriptor(object);
			addCancelOverlayURLPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Ok Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addOkColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_okColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_okColorHEX_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Ok Foreground Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addOkForegroundColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_okForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ControlValidationTemplate_okForegroundColorHEX_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__OK_FOREGROUND_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Ok Image URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addOkImageURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_okImageURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_okImageURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Ok Overlay URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addOkOverlayURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_okOverlayURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_okOverlayURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Info Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInfoColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_infoColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_infoColorHEX_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Info Foreground Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInfoForegroundColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_ControlValidationTemplate_infoForegroundColorHEX_feature"), //$NON-NLS-1$
			getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
				"_UI_ControlValidationTemplate_infoForegroundColorHEX_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
			VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__INFO_FOREGROUND_COLOR_HEX,
			true,
			false,
			false,
			ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
			null,
			null));
	}

	/**
	 * This adds a property descriptor for the Info Image URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInfoImageURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_infoImageURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_infoImageURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Info Overlay URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addInfoOverlayURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_infoOverlayURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_infoOverlayURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Warning Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addWarningColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_warningColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_warningColorHEX_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Warning Foreground Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addWarningForegroundColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_warningForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ControlValidationTemplate_warningForegroundColorHEX_feature", //$NON-NLS-1$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__WARNING_FOREGROUND_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Warning Image URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addWarningImageURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_warningImageURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_warningImageURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Warning Overlay URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addWarningOverlayURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_warningOverlayURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ControlValidationTemplate_warningOverlayURL_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Error Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addErrorColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_errorColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_errorColorHEX_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Error Foreground Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addErrorForegroundColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_ControlValidationTemplate_errorForegroundColorHEX_feature"), //$NON-NLS-1$
			getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
				"_UI_ControlValidationTemplate_errorForegroundColorHEX_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
			VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__ERROR_FOREGROUND_COLOR_HEX,
			true,
			false,
			false,
			ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
			null,
			null));
	}

	/**
	 * This adds a property descriptor for the Error Image URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addErrorImageURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_errorImageURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_errorImageURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Error Overlay URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addErrorOverlayURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_errorOverlayURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_errorOverlayURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Cancel Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCancelColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_cancelColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_cancelColorHEX_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Cancel Foreground Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCancelForegroundColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
			((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
			getResourceLocator(),
			getString("_UI_ControlValidationTemplate_cancelForegroundColorHEX_feature"), //$NON-NLS-1$
			getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
				"_UI_ControlValidationTemplate_cancelForegroundColorHEX_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
			VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__CANCEL_FOREGROUND_COLOR_HEX,
			true,
			false,
			false,
			ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
			null,
			null));
	}

	/**
	 * This adds a property descriptor for the Cancel Image URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCancelImageURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_cancelImageURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ControlValidationTemplate_cancelImageURL_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_ControlValidationTemplate_type"), //$NON-NLS-1$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Cancel Overlay URL feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addCancelOverlayURLPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ControlValidationTemplate_cancelOverlayURL_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ControlValidationTemplate_cancelOverlayURL_feature", "_UI_ControlValidationTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTTemplatePackage.Literals.CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns ControlValidationTemplate.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ControlValidationTemplate")); //$NON-NLS-1$
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
		final String label = ((VTControlValidationTemplate) object).getOkColorHEX();
		return label == null || label.length() == 0 ? getString("_UI_ControlValidationTemplate_type") : //$NON-NLS-1$
			getString("_UI_ControlValidationTemplate_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VTControlValidationTemplate.class)) {
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__OK_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__OK_FOREGROUND_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__OK_IMAGE_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__OK_OVERLAY_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__INFO_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__INFO_FOREGROUND_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__INFO_IMAGE_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__INFO_OVERLAY_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__WARNING_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__WARNING_FOREGROUND_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__WARNING_IMAGE_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__WARNING_OVERLAY_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__ERROR_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__ERROR_FOREGROUND_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__ERROR_IMAGE_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__ERROR_OVERLAY_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__CANCEL_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__CANCEL_FOREGROUND_COLOR_HEX:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__CANCEL_IMAGE_URL:
		case VTTemplatePackage.CONTROL_VALIDATION_TEMPLATE__CANCEL_OVERLAY_URL:
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
