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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.provider.FeaturePathDomainModelReferenceItemProvider;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference}
 * object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class TableDomainModelReferenceItemProvider
	extends FeaturePathDomainModelReferenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TableDomainModelReferenceItemProvider(AdapterFactory adapterFactory) {
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

			addColumnDomainModelReferencesPropertyDescriptor(object);
			addDomainModelReferencePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Column Domain Model References feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addColumnDomainModelReferencesPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableDomainModelReference_columnDomainModelReferences_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_TableDomainModelReference_columnDomainModelReferences_feature", //$NON-NLS-1$
					"_UI_TableDomainModelReference_type"), //$NON-NLS-1$
				VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Domain Model Reference feature.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDomainModelReferencePropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_TableDomainModelReference_domainModelReference_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
					"_UI_TableDomainModelReference_domainModelReference_feature", "_UI_TableDomainModelReference_type"), //$NON-NLS-1$ //$NON-NLS-2$
				VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
				true,
				false,
				false,
				null,
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
			childrenFeatures.add(VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES);
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
	 * This returns TableDomainModelReference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/TableDomainModelReference")); //$NON-NLS-1$
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
		return getString("_UI_TableDomainModelReference_type"); //$NON-NLS-1$
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

		switch (notification.getFeatureID(VTableDomainModelReference.class)) {
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add(
			createChildParameter(VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES,
				VTableFactory.eINSTANCE.createTableDomainModelReference()));

		newChildDescriptors.add(
			createChildParameter(VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES,
				VViewFactory.eINSTANCE.createDomainModelReference()));

		newChildDescriptors.add(
			createChildParameter(VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES,
				VViewFactory.eINSTANCE.createFeaturePathDomainModelReference()));
	}

}
