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
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;
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
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewModelElementSelectorItemProvider
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
	public ViewModelElementSelectorItemProvider(AdapterFactory adapterFactory) {
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
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addSelectSubclassesPropertyDescriptor(object);
			addClassTypePropertyDescriptor(object);
			addAttributePropertyDescriptor(object);
			addAttributeValuePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Class Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addClassTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ViewModelElementSelector_classType_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ViewModelElementSelector_classType_feature", "_UI_ViewModelElementSelector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTViewModelElementPackage.Literals.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Attribute feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addAttributePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ViewModelElementSelector_attribute_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ViewModelElementSelector_attribute_feature", "_UI_ViewModelElementSelector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTViewModelElementPackage.Literals.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Attribute Value feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addAttributeValuePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ViewModelElementSelector_attributeValue_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ViewModelElementSelector_attributeValue_feature", "_UI_ViewModelElementSelector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTViewModelElementPackage.Literals.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Select Subclasses feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSelectSubclassesPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_ViewModelElementSelector_selectSubclasses_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_ViewModelElementSelector_selectSubclasses_feature", "_UI_ViewModelElementSelector_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VTViewModelElementPackage.Literals.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns ViewModelElementSelector.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ViewModelElementSelector")); //$NON-NLS-1$
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
		final VTViewModelElementSelector viewModelElementSelector = (VTViewModelElementSelector) object;
		return getString("_UI_ViewModelElementSelector_type") + " " + viewModelElementSelector.isSelectSubclasses(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VTViewModelElementSelector.class))
		{
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES:
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE:
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE:
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
