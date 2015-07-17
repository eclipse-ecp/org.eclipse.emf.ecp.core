/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomFactory;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.provider.ControlItemProvider;
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
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl} object.
 * <!-- begin-user-doc -->
 *
 * @since 1.3
 *        <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomControlItemProvider
	extends ControlItemProvider
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
	public CustomControlItemProvider(AdapterFactory adapterFactory)
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

			addBundleNamePropertyDescriptor(object);
			addClassNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Bundle Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addBundleNamePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_CustomControl_bundleName_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_CustomControl_bundleName_feature", "_UI_CustomControl_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VCustomPackage.Literals.CUSTOM_CONTROL__BUNDLE_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Class Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addClassNamePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_CustomControl_className_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_CustomControl_className_feature", "_UI_CustomControl_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VCustomPackage.Literals.CUSTOM_CONTROL__CLASS_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns CustomControl.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CustomControl")); //$NON-NLS-1$
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
		final String label = ((VCustomControl) object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_CustomControl_type") : //$NON-NLS-1$
			getString("_UI_CustomControl_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VCustomControl.class))
		{
		case VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME:
		case VCustomPackage.CUSTOM_CONTROL__CLASS_NAME:
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

		newChildDescriptors.add
			(createChildParameter
			(VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE,
				VCustomFactory.eINSTANCE.createCustomDomainModelReference()));
	}

}
