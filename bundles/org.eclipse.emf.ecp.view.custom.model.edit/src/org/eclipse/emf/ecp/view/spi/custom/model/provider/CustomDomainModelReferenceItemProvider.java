/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.model.provider.DomainModelReferenceItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference}
 * object.
 * <!-- begin-user-doc -->
 *
 * @since 1.3
 *        <!-- end-user-doc -->
 * @generated
 */
public class CustomDomainModelReferenceItemProvider
	extends DomainModelReferenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CustomDomainModelReferenceItemProvider(AdapterFactory adapterFactory) {
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

			addBundleNamePropertyDescriptor(object);
			addClassNamePropertyDescriptor(object);
			addControlCheckedPropertyDescriptor(object);
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
	protected void addBundleNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_CustomDomainModelReference_bundleName_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_CustomDomainModelReference_bundleName_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_CustomDomainModelReference_type"), //$NON-NLS-1$
				VCustomPackage.Literals.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME,
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
	protected void addClassNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_CustomDomainModelReference_className_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_CustomDomainModelReference_className_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_CustomDomainModelReference_type"), //$NON-NLS-1$
				VCustomPackage.Literals.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Control Checked feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addControlCheckedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_CustomDomainModelReference_controlChecked_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_CustomDomainModelReference_controlChecked_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_CustomDomainModelReference_type"), //$NON-NLS-1$
				VCustomPackage.Literals.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns CustomDomainModelReference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CustomDomainModelReference")); //$NON-NLS-1$
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
		final String label = ((VCustomDomainModelReference) object).getBundleName();
		return label == null || label.length() == 0 ? getString("_UI_CustomDomainModelReference_type") : //$NON-NLS-1$
			getString("_UI_CustomDomainModelReference_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VCustomDomainModelReference.class)) {
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME:
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME:
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED:
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

}
