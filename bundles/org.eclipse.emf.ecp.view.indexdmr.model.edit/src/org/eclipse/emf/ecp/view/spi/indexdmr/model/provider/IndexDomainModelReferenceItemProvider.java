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
package org.eclipse.emf.ecp.view.spi.indexdmr.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.provider.FeaturePathDomainModelReferenceItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference} object.
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated
 */
public class IndexDomainModelReferenceItemProvider extends
	FeaturePathDomainModelReferenceItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IndexDomainModelReferenceItemProvider(AdapterFactory adapterFactory) {
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

			addPrefixDMRPropertyDescriptor(object);
			addIndexPropertyDescriptor(object);
			addTargetDMRPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Prefix DMR feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addPrefixDMRPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_IndexDomainModelReference_prefixDMR_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_IndexDomainModelReference_prefixDMR_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_IndexDomainModelReference_type"), //$NON-NLS-1$
				VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Target DMR feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addTargetDMRPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_IndexDomainModelReference_targetDMR_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_IndexDomainModelReference_targetDMR_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_IndexDomainModelReference_type"), //$NON-NLS-1$
				VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR,
				true,
				false,
				false,
				null,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the Index feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void addIndexPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_IndexDomainModelReference_index_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_IndexDomainModelReference_index_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_IndexDomainModelReference_type"), //$NON-NLS-1$
				VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__INDEX,
				true,
				false,
				false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
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
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR);
			childrenFeatures.add(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR);
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
	 * This returns IndexDomainModelReference.gif.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IndexDomainModelReference")); //$NON-NLS-1$
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
		final VIndexDomainModelReference indexDomainModelReference = (VIndexDomainModelReference) object;
		return getString("_UI_IndexDomainModelReference_type") + " " + indexDomainModelReference.getIndex(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(VIndexDomainModelReference.class)) {
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE__INDEX:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR:
		case VIndexdmrPackage.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR:
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

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR,
			VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference()));

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR,
			VViewFactory.eINSTANCE.createDomainModelReference()));

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR,
			VViewFactory.eINSTANCE.createFeaturePathDomainModelReference()));

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR,
			VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference()));

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR,
			VViewFactory.eINSTANCE.createDomainModelReference()));

		newChildDescriptors.add(createChildParameter(VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR,
			VViewFactory.eINSTANCE.createFeaturePathDomainModelReference()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		final Object childFeature = feature;
		final Object childObject = child;

		final boolean qualify = childFeature == VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__PREFIX_DMR ||
			childFeature == VIndexdmrPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE__TARGET_DMR;

		if (qualify) {
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
				new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
