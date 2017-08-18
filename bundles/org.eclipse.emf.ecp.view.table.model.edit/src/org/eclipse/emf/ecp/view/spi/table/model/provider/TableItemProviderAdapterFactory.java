/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.table.model.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.ViewSwitch;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.table.model.util.TableAdapterFactory;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

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
public class TableItemProviderAdapterFactory extends TableAdapterFactory implements ComposeableAdapterFactory,
	IChangeNotifier, IDisposable, IChildCreationExtender {
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
	 * This helps manage the child creation extenders.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(
		TableEditPlugin.INSTANCE, VTablePackage.eNS_URI);

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
	public TableItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl}
	 * instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TableControlItemProvider tableControlItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableControl}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createTableControlAdapter() {
		if (tableControlItemProvider == null) {
			tableControlItemProvider = new TableControlItemProvider(this);
		}

		return tableControlItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TableDomainModelReferenceItemProvider tableDomainModelReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createTableDomainModelReferenceAdapter() {
		if (tableDomainModelReferenceItemProvider == null) {
			tableDomainModelReferenceItemProvider = new TableDomainModelReferenceItemProvider(this);
		}

		return tableDomainModelReferenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ReadOnlyColumnConfigurationItemProvider readOnlyColumnConfigurationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createReadOnlyColumnConfigurationAdapter() {
		if (readOnlyColumnConfigurationItemProvider == null) {
			readOnlyColumnConfigurationItemProvider = new ReadOnlyColumnConfigurationItemProvider(this);
		}

		return readOnlyColumnConfigurationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration} instances.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.9
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected WidthConfigurationItemProvider widthConfigurationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createWidthConfigurationAdapter() {
		if (widthConfigurationItemProvider == null) {
			widthConfigurationItemProvider = new WidthConfigurationItemProvider(this);
		}

		return widthConfigurationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emf.ecp.view.spi.table.model.VEnablementConfiguration} instances.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected EnablementConfigurationItemProvider enablementConfigurationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VEnablementConfiguration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createEnablementConfigurationAdapter() {
		if (enablementConfigurationItemProvider == null) {
			enablementConfigurationItemProvider = new EnablementConfigurationItemProvider(this);
		}

		return enablementConfigurationItemProvider;
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
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public List<IChildCreationExtender> getChildCreationExtenders() {
		return childCreationExtenderManager.getChildCreationExtenders();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
		return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return childCreationExtenderManager;
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
		if (tableControlItemProvider != null)
			tableControlItemProvider.dispose();
		if (tableDomainModelReferenceItemProvider != null)
			tableDomainModelReferenceItemProvider.dispose();
		if (readOnlyColumnConfigurationItemProvider != null)
			readOnlyColumnConfigurationItemProvider.dispose();
		if (widthConfigurationItemProvider != null)
			widthConfigurationItemProvider.dispose();
		}
		if (enablementConfigurationItemProvider != null) {
			enablementConfigurationItemProvider.dispose();
	}

	/**
	 * A child creation extender for the {@link VViewPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static class ViewChildCreationExtender implements IChildCreationExtender {
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected static class CreationSwitch extends ViewSwitch<Object> {
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			@Override
			public Object caseView(VView object) {
				newChildDescriptors.add(createChildParameter(VViewPackage.Literals.VIEW__CHILDREN,
					VTableFactory.eINSTANCE.createTableControl()));

				return null;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			@Override
			public Object caseContainer(VContainer object) {
				newChildDescriptors.add(createChildParameter(VViewPackage.Literals.CONTAINER__CHILDREN,
					VTableFactory.eINSTANCE.createTableControl()));

				return null;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 *
			 * @generated NOT
			 */
			@Override
			public Object caseControl(VControl object) {
				// Do not add table dmr to control
				return null;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * 
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child) {
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		@Override
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject) object);
			return result;
		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		@Override
		public ResourceLocator getResourceLocator() {
			return TableEditPlugin.INSTANCE;
		}
	}

}
