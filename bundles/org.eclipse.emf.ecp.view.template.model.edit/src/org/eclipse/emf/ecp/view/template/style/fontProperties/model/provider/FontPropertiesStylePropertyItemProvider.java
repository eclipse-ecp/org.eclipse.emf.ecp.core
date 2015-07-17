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
package org.eclipse.emf.ecp.view.template.style.fontProperties.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
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
 * {@link org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FontPropertiesStylePropertyItemProvider
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
	public FontPropertiesStylePropertyItemProvider(AdapterFactory adapterFactory) {
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

			addItalicPropertyDescriptor(object);
			addBoldPropertyDescriptor(object);
			addColorHEXPropertyDescriptor(object);
			addHeightPropertyDescriptor(object);
			addFontNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Italic feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addItalicPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FontPropertiesStyleProperty_italic_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_FontPropertiesStyleProperty_italic_feature", "_UI_FontPropertiesStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Bold feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addBoldPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FontPropertiesStyleProperty_bold_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_FontPropertiesStyleProperty_bold_feature", "_UI_FontPropertiesStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY__BOLD,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Color HEX feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addColorHEXPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FontPropertiesStyleProperty_colorHEX_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_FontPropertiesStyleProperty_colorHEX_feature", "_UI_FontPropertiesStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Height feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addHeightPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FontPropertiesStyleProperty_height_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_FontPropertiesStyleProperty_height_feature", "_UI_FontPropertiesStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Font Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addFontNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_FontPropertiesStyleProperty_fontName_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_FontPropertiesStyleProperty_fontName_feature", "_UI_FontPropertiesStyleProperty_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTFontPropertiesPackage.Literals.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns FontPropertiesStyleProperty.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/FontPropertiesStyleProperty")); //$NON-NLS-1$
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
		final String label = ((VTFontPropertiesStyleProperty) object).getFontName();
		return label == null || label.length() == 0 ?
			getString("_UI_FontPropertiesStyleProperty_type") : //$NON-NLS-1$
			getString("_UI_FontPropertiesStyleProperty_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VTFontPropertiesStyleProperty.class)) {
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__ITALIC:
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__BOLD:
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__COLOR_HEX:
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__HEIGHT:
		case VTFontPropertiesPackage.FONT_PROPERTIES_STYLE_PROPERTY__FONT_NAME:
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
