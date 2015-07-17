/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.provider.FeaturePathDomainModelReferenceItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class MappingDomainModelReferenceItemProvider extends
	FeaturePathDomainModelReferenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public MappingDomainModelReferenceItemProvider(AdapterFactory adapterFactory) {
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
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addMappedClassPropertyDescriptor(object);
			addDomainModelReferencePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Mapped Class feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addMappedClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_MappingDomainModelReference_mappedClass_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_MappingDomainModelReference_mappedClass_feature", "_UI_MappingDomainModelReference_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS,
				true,
				false,
				true,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Domain Model Reference feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addDomainModelReferencePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
			.add
			(createItemPropertyDescriptor
			(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_MappingDomainModelReference_domainModelReference_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_MappingDomainModelReference_domainModelReference_feature", "_UI_MappingDomainModelReference_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
		Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * This returns MappingDomainModelReference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MappingDomainModelReference")); //$NON-NLS-1$
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
		return getString("_UI_MappingDomainModelReference_type"); //$NON-NLS-1$
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

		switch (notification.getFeatureID(VMappingDomainModelReference.class))
		{
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add
			(createChildParameter
			(VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
				VMappingdmrFactory.eINSTANCE.createMappingDomainModelReference()));

		newChildDescriptors.add
			(createChildParameter
			(VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
				VViewFactory.eINSTANCE.createFeaturePathDomainModelReference()));
	}

}
