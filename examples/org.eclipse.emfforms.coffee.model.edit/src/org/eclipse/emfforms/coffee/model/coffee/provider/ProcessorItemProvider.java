/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.coffee.model.coffee.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emfforms.coffee.model.coffee.CoffeePackage;
import org.eclipse.emfforms.coffee.model.coffee.Processor;

/**
 * This is the item provider adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Processor} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ProcessorItemProvider
	extends ItemProviderAdapter
	implements
	IEditingDomainItemProvider,
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
	public ProcessorItemProvider(AdapterFactory adapterFactory) {
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

			addVendorPropertyDescriptor(object);
			addClockSpeedPropertyDescriptor(object);
			addNumberOfCoresPropertyDescriptor(object);
			addSocketconnectorTypePropertyDescriptor(object);
			addThermalDesignPowerPropertyDescriptor(object);
			addManufactoringProcessPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Vendor feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addVendorPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_vendor_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_vendor_feature", "_UI_Processor_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				CoffeePackage.Literals.PROCESSOR__VENDOR,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Clock Speed feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addClockSpeedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_clockSpeed_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_clockSpeed_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_Processor_type"), //$NON-NLS-1$
				CoffeePackage.Literals.PROCESSOR__CLOCK_SPEED,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Number Of Cores feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addNumberOfCoresPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_numberOfCores_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_numberOfCores_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_Processor_type"), //$NON-NLS-1$
				CoffeePackage.Literals.PROCESSOR__NUMBER_OF_CORES,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Socketconnector Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addSocketconnectorTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_socketconnectorType_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_socketconnectorType_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_Processor_type"), //$NON-NLS-1$
				CoffeePackage.Literals.PROCESSOR__SOCKETCONNECTOR_TYPE,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Thermal Design Power feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addThermalDesignPowerPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_thermalDesignPower_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_thermalDesignPower_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_Processor_type"), //$NON-NLS-1$
				CoffeePackage.Literals.PROCESSOR__THERMAL_DESIGN_POWER,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Manufactoring Process feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addManufactoringProcessPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_Processor_manufactoringProcess_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_Processor_manufactoringProcess_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_Processor_type"), //$NON-NLS-1$
				CoffeePackage.Literals.PROCESSOR__MANUFACTORING_PROCESS,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This returns Processor.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Processor")); //$NON-NLS-1$
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
		final String label = ((Processor) object).getVendor();
		return label == null || label.length() == 0 ? getString("_UI_Processor_type") : //$NON-NLS-1$
			getString("_UI_Processor_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(Processor.class)) {
		case CoffeePackage.PROCESSOR__VENDOR:
		case CoffeePackage.PROCESSOR__CLOCK_SPEED:
		case CoffeePackage.PROCESSOR__NUMBER_OF_CORES:
		case CoffeePackage.PROCESSOR__SOCKETCONNECTOR_TYPE:
		case CoffeePackage.PROCESSOR__THERMAL_DESIGN_POWER:
		case CoffeePackage.PROCESSOR__MANUFACTORING_PROCESS:
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
		return CoffeeEditPlugin.INSTANCE;
	}

}
