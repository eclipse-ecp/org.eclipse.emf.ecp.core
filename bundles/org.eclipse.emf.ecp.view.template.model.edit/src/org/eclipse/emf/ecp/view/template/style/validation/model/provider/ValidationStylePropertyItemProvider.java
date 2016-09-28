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
package org.eclipse.emf.ecp.view.template.style.validation.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;
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
 * {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ValidationStylePropertyItemProvider
	extends ItemProviderAdapter
	implements
	IEditingDomainItemProvider,
	IStructuredItemContentProvider,
	ITreeItemContentProvider,
	IItemLabelProvider,
	IItemPropertySource
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ValidationStylePropertyItemProvider(AdapterFactory adapterFactory)
	{
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
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
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
	protected void addOkColorHEXPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_okColorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_okColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX,
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
				getString("_UI_ValidationStyleProperty_okForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_okForegroundColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX,
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
	protected void addOkImageURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_okImageURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_okImageURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL,
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
	protected void addOkOverlayURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_okOverlayURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_okOverlayURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL,
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
	protected void addInfoColorHEXPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_infoColorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_infoColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX,
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
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_infoForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_infoForegroundColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX,
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
	protected void addInfoImageURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_infoImageURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_infoImageURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL,
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
	protected void addInfoOverlayURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_infoOverlayURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_infoOverlayURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL,
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
	protected void addWarningColorHEXPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_warningColorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_warningColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX,
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
				getString("_UI_ValidationStyleProperty_warningForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_warningForegroundColorHEX_feature", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_type"), //$NON-NLS-1$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX,
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
	protected void addWarningImageURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_warningImageURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_warningImageURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL,
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
	protected void addWarningOverlayURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_warningOverlayURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_warningOverlayURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL,
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
	protected void addErrorColorHEXPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_errorColorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_errorColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX,
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
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_errorForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_errorForegroundColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX,
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
	protected void addErrorImageURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_errorImageURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_errorImageURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL,
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
	protected void addErrorOverlayURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_errorOverlayURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_errorOverlayURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL,
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
	protected void addCancelColorHEXPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_cancelColorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_cancelColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX,
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
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_cancelForegroundColorHEX_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_ValidationStyleProperty_cancelForegroundColorHEX_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX,
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
	protected void addCancelImageURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_cancelImageURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_cancelImageURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL,
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
	protected void addCancelOverlayURLPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ValidationStyleProperty_cancelOverlayURL_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ValidationStyleProperty_cancelOverlayURL_feature", "_UI_ValidationStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTValidationPackage.Literals.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns ValidationStyleProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ValidationStyleProperty")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object)
	{
		final String label = ((VTValidationStyleProperty) object).getOkColorHEX();
		return label == null || label.length() == 0 ?
			getString("_UI_ValidationStyleProperty_type") : //$NON-NLS-1$
			getString("_UI_ValidationStyleProperty_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(VTValidationStyleProperty.class))
		{
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_FOREGROUND_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_IMAGE_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__OK_OVERLAY_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_FOREGROUND_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_IMAGE_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__INFO_OVERLAY_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_FOREGROUND_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_IMAGE_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__WARNING_OVERLAY_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_FOREGROUND_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_IMAGE_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__ERROR_OVERLAY_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_FOREGROUND_COLOR_HEX:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_IMAGE_URL:
		case VTValidationPackage.VALIDATION_STYLE_PROPERTY__CANCEL_OVERLAY_URL:
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
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
	public ResourceLocator getResourceLocator()
	{
		return ((IChildCreationExtender) adapterFactory).getResourceLocator();
	}

}
