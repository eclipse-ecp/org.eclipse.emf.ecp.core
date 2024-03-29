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
package org.eclipse.emf.ecp.view.spi.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
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
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.model.VElement} object.
 * <!-- begin-user-doc -->
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 * @generated
 */
public class ElementItemProvider
	extends ItemProviderAdapter
	implements
	IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider,
	IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ElementItemProvider(AdapterFactory adapterFactory) {
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

			addNamePropertyDescriptor(object);
			addLabelPropertyDescriptor(object);
			addVisiblePropertyDescriptor(object);
			addEnabledPropertyDescriptor(object);
			addReadonlyPropertyDescriptor(object);
			addUuidPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_name_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_name_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__NAME,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Label feature.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.6
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_label_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_label_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__LABEL,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Visible feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addVisiblePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_visible_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_visible_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__VISIBLE,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Enabled feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addEnabledPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_enabled_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_enabled_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__ENABLED,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Readonly feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addReadonlyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_readonly_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_readonly_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__READONLY,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Uuid feature.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUuidPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Element_uuid_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Element_uuid_feature", "_UI_Element_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VViewPackage.Literals.ELEMENT__UUID,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
			childrenFeatures.add(VViewPackage.Literals.ELEMENT__ATTACHMENTS);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		final String label = ((VElement) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Element_type") : //$NON-NLS-1$
			getString("_UI_Element_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 *
	 * @param notification a description of the change
	 *            <!-- begin-user-doc -->
	 *            <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(VElement.class)) {
		case VViewPackage.ELEMENT__NAME:
		case VViewPackage.ELEMENT__ENABLED:
		case VViewPackage.ELEMENT__READONLY:
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;

		case VViewPackage.ELEMENT__VISIBLE:
			// notify parent as else no update on tree will happen
			fireNotifyChanged(new ViewerNotification(notification, ((EObject) notification.getNotifier()).eContainer(),
				true, false));
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		case VViewPackage.ELEMENT__ATTACHMENTS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		default:
			break;
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

		newChildDescriptors.add(createChildParameter(VViewPackage.Literals.ELEMENT__ATTACHMENTS,
			VViewFactory.eINSTANCE.createDateTimeDisplayAttachment()));
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
