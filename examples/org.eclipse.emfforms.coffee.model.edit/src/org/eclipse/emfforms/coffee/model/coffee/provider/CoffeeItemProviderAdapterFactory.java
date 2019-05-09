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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emfforms.coffee.model.coffee.util.CoffeeAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged
 * fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CoffeeItemProviderAdapterFactory extends CoffeeAdapterFactory
	implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CoffeeItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.Machine}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected MachineItemProvider machineItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Machine}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createMachineAdapter() {
		if (machineItemProvider == null) {
			machineItemProvider = new MachineItemProvider(this);
		}

		return machineItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.ControlUnit}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ControlUnitItemProvider controlUnitItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.ControlUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createControlUnitAdapter() {
		if (controlUnitItemProvider == null) {
			controlUnitItemProvider = new ControlUnitItemProvider(this);
		}

		return controlUnitItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.BrewingUnit}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected BrewingUnitItemProvider brewingUnitItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.BrewingUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createBrewingUnitAdapter() {
		if (brewingUnitItemProvider == null) {
			brewingUnitItemProvider = new BrewingUnitItemProvider(this);
		}

		return brewingUnitItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.DipTray}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected DipTrayItemProvider dipTrayItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.DipTray}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createDipTrayAdapter() {
		if (dipTrayItemProvider == null) {
			dipTrayItemProvider = new DipTrayItemProvider(this);
		}

		return dipTrayItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.WaterTank}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WaterTankItemProvider waterTankItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.WaterTank}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createWaterTankAdapter() {
		if (waterTankItemProvider == null) {
			waterTankItemProvider = new WaterTankItemProvider(this);
		}

		return waterTankItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.Processor}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ProcessorItemProvider processorItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Processor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createProcessorAdapter() {
		if (processorItemProvider == null) {
			processorItemProvider = new ProcessorItemProvider(this);
		}

		return processorItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.RAM} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RAMItemProvider ramItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.RAM}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createRAMAdapter() {
		if (ramItemProvider == null) {
			ramItemProvider = new RAMItemProvider(this);
		}

		return ramItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.Activity}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ActivityItemProvider activityItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Activity}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createActivityAdapter() {
		if (activityItemProvider == null) {
			activityItemProvider = new ActivityItemProvider(this);
		}

		return activityItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.Dimension}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected DimensionItemProvider dimensionItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Dimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createDimensionAdapter() {
		if (dimensionItemProvider == null) {
			dimensionItemProvider = new DimensionItemProvider(this);
		}

		return dimensionItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emfforms.coffee.model.coffee.Display}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected DisplayItemProvider displayItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.coffee.model.coffee.Display}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createDisplayAdapter() {
		if (displayItemProvider == null) {
			displayItemProvider = new DisplayItemProvider(this);
		}

		return displayItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			final Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || ((Class<?>) type).isInstance(adapter)) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void dispose() {
		if (machineItemProvider != null) {
			machineItemProvider.dispose();
		}
		if (controlUnitItemProvider != null) {
			controlUnitItemProvider.dispose();
		}
		if (brewingUnitItemProvider != null) {
			brewingUnitItemProvider.dispose();
		}
		if (dipTrayItemProvider != null) {
			dipTrayItemProvider.dispose();
		}
		if (waterTankItemProvider != null) {
			waterTankItemProvider.dispose();
		}
		if (processorItemProvider != null) {
			processorItemProvider.dispose();
		}
		if (ramItemProvider != null) {
			ramItemProvider.dispose();
		}
		if (activityItemProvider != null) {
			activityItemProvider.dispose();
		}
		if (dimensionItemProvider != null) {
			dimensionItemProvider.dispose();
		}
		if (displayItemProvider != null) {
			displayItemProvider.dispose();
		}
	}

}
