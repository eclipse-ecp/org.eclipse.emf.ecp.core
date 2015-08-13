/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emfforms.spi.spreadsheet.core.error.model.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorPackage;
import org.eclipse.emfforms.spi.spreadsheet.core.error.model.util.ErrorAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged
 * fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated
 */
public class ErrorItemProviderAdapterFactory extends ErrorAdapterFactory
	implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement
	 * {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
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
		ErrorEditPlugin.INSTANCE, ErrorPackage.eNS_URI);

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public ErrorItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SpreadsheetImportResultItemProvider spreadsheetImportResultItemProvider;

	/**
	 * This creates an adapter for a
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SpreadsheetImportResult}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createSpreadsheetImportResultAdapter() {
		if (spreadsheetImportResultItemProvider == null) {
			spreadsheetImportResultItemProvider = new SpreadsheetImportResultItemProvider(this);
		}

		return spreadsheetImportResultItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport} instances.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ErrorReportItemProvider errorReportItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.ErrorReport}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createErrorReportAdapter() {
		if (errorReportItemProvider == null) {
			errorReportItemProvider = new ErrorReportItemProvider(this);
		}

		return errorReportItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation} instances.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EMFLocationItemProvider emfLocationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.EMFLocation}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createEMFLocationAdapter() {
		if (emfLocationItemProvider == null) {
			emfLocationItemProvider = new EMFLocationItemProvider(this);
		}

		return emfLocationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SettingLocationItemProvider settingLocationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SettingLocation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createSettingLocationAdapter() {
		if (settingLocationItemProvider == null) {
			settingLocationItemProvider = new SettingLocationItemProvider(this);
		}

		return settingLocationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected DMRLocationItemProvider dmrLocationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.DMRLocation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createDMRLocationAdapter() {
		if (dmrLocationItemProvider == null) {
			dmrLocationItemProvider = new DMRLocationItemProvider(this);
		}

		return dmrLocationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SheetLocationItemProvider sheetLocationItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emfforms.spi.spreadsheet.core.error.model.SheetLocation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter createSheetLocationAdapter() {
		if (sheetLocationItemProvider == null) {
			sheetLocationItemProvider = new SheetLocationItemProvider(this);
		}

		return sheetLocationItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public List<IChildCreationExtender> getChildCreationExtenders() {
		return childCreationExtenderManager.getChildCreationExtenders();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
		return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return childCreationExtenderManager;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to
	 * {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
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
	 * This disposes all of the item providers created by this factory. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void dispose() {
		if (spreadsheetImportResultItemProvider != null) {
			spreadsheetImportResultItemProvider.dispose();
		}
		if (errorReportItemProvider != null) {
			errorReportItemProvider.dispose();
		}
		if (sheetLocationItemProvider != null) {
			sheetLocationItemProvider.dispose();
		}
		if (emfLocationItemProvider != null) {
			emfLocationItemProvider.dispose();
		}
		if (settingLocationItemProvider != null) {
			settingLocationItemProvider.dispose();
		}
		if (dmrLocationItemProvider != null) {
			dmrLocationItemProvider.dispose();
		}
	}

}