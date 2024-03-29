/**
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
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.model.provider.FeaturePathDomainModelReferenceItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class KeyAttributeDomainModelReferenceItemProvider extends
	FeaturePathDomainModelReferenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public KeyAttributeDomainModelReferenceItemProvider(
		AdapterFactory adapterFactory) {
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

			addKeyDMRPropertyDescriptor(object);
			addKeyValuePropertyDescriptor(object);
			addValueDMRPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Key DMR feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addKeyDMRPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_KeyAttributeDomainModelReference_keyDMR_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_KeyAttributeDomainModelReference_keyDMR_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_KeyAttributeDomainModelReference_type"), //$NON-NLS-1$
				VKeyattributedmrPackage.Literals.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Key Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addKeyValuePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_KeyAttributeDomainModelReference_keyValue_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_KeyAttributeDomainModelReference_keyValue_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_KeyAttributeDomainModelReference_type"), //$NON-NLS-1$
				VKeyattributedmrPackage.Literals.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Value DMR feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addValueDMRPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_KeyAttributeDomainModelReference_valueDMR_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_KeyAttributeDomainModelReference_valueDMR_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_KeyAttributeDomainModelReference_type"), //$NON-NLS-1$
				VKeyattributedmrPackage.Literals.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This returns KeyAttributeDomainModelReference.gif.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/KeyAttributeDomainModelReference")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		final Object labelValue = ((VKeyAttributeDomainModelReference) object).getKeyValue();
		final String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_KeyAttributeDomainModelReference_type") : //$NON-NLS-1$
			getString("_UI_KeyAttributeDomainModelReference_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(VKeyAttributeDomainModelReference.class)) {
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE:
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
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
