/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.rule.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Quantifier;
import org.eclipse.emf.ecp.view.spi.rule.model.RuleFactory;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class IterateConditionItemProvider extends ConditionItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IterateConditionItemProvider(AdapterFactory adapterFactory) {
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

			addQuantifierPropertyDescriptor(object);
			addIfEmptyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Quantifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addQuantifierPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_IterateCondition_quantifier_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_IterateCondition_quantifier_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_IterateCondition_type"), //$NON-NLS-1$
				RulePackage.Literals.ITERATE_CONDITION__QUANTIFIER,
				true,
				false,
				false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				null,
				null));
	}

	/**
	 * This adds a property descriptor for the If Empty feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addIfEmptyPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_IterateCondition_ifEmpty_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_IterateCondition_ifEmpty_feature", //$NON-NLS-1$ //$NON-NLS-2$
					"_UI_IterateCondition_type"), //$NON-NLS-1$
				RulePackage.Literals.ITERATE_CONDITION__IF_EMPTY,
				true,
				false,
				false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
			childrenFeatures.add(RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE);
			childrenFeatures.add(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION);
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
	 * This returns IterateCondition.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IterateCondition")); //$NON-NLS-1$
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
		Quantifier labelValue = ((IterateCondition) object).getQuantifier();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_IterateCondition_type") : //$NON-NLS-1$
			getString("_UI_IterateCondition_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(IterateCondition.class)) {
		case RulePackage.ITERATE_CONDITION__QUANTIFIER:
		case RulePackage.ITERATE_CONDITION__IF_EMPTY:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
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

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_REFERENCE,
			VViewFactory.eINSTANCE.createFeaturePathDomainModelReference()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createLeafCondition()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createOrCondition()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createAndCondition()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createIterateCondition()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createTrue()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createFalse()));

		newChildDescriptors.add(createChildParameter(RulePackage.Literals.ITERATE_CONDITION__ITEM_CONDITION,
			RuleFactory.eINSTANCE.createNotCondition()));
	}

}
