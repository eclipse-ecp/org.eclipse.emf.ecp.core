/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.categorization.model.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VElementUtil;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.ecp.view.spi.categorization.model.VCategory} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CategoryItemProvider
	extends AbstractCategorizationItemProvider {
	/** Do not access directly! Use {@link #getChildrenDescriptorCollector()}. */
	private ChildrenDescriptorCollector childrenDescriptorCollector;

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CategoryItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(VCategorizationPackage.Literals.CATEGORY__COMPOSITE);
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
	 * This returns Category.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Category")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String label = ((VCategory) object).getLabel();
		if (label == null) {
			label = VElementUtil.getCleanName(VElement.class.cast(object));
		}
		return label == null || label.length() == 0 ? getString("_UI_Category_type") : label; //$NON-NLS-1$
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

		switch (notification.getFeatureID(VCategory.class)) {
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * Returns the cached {@link ChildrenDescriptorCollector}.
	 *
	 * @return The {@link ChildrenDescriptorCollector}.
	 * @since 1.17
	 */
	protected ChildrenDescriptorCollector getChildrenDescriptorCollector() {
		if (childrenDescriptorCollector == null) {
			childrenDescriptorCollector = new ChildrenDescriptorCollector();
		}
		return childrenDescriptorCollector;
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		collectContainerChildDecriptors(newChildDescriptors, object);
	}

	/**
	 * Adds child descriptors for the {@link VContainer}'s children reference. This is necessary as long as
	 * {@link VCategory} does not extend {@link VCategory}.
	 *
	 * @param newChildDescriptors The collection of child descriptors that the collected descriptors will be added to.
	 * @param object The object for which the child descriptors are collected
	 * @since 1.17
	 */
	protected void collectContainerChildDecriptors(Collection<Object> newChildDescriptors, Object object) {
		// Get children descriptors for a vertical layout
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(object);
		final VVerticalLayout verticalLayout = VVerticalFactory.eINSTANCE.createVerticalLayout();
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = resourceSet.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		if (resource != null) {
			resource.getContents().add(verticalLayout);
		}
		final Collection<?> verticalDescriptors = getChildrenDescriptorCollector()
			.getDescriptors(verticalLayout);

		// TODO needed?
		resourceSet.eAdapters().clear();

		// Iterate over the gathered descriptors and transform relevant ones to fit for the VCategory
		for (final Object descriptor : verticalDescriptors) {
			final CommandParameter parameter = (CommandParameter) descriptor;
			// We only need the descriptors for the VContainer's children reference
			if (parameter.getEStructuralFeature() == VViewPackage.Literals.CONTAINER__CHILDREN) {
				newChildDescriptors
					.add(createChildParameter(VCategorizationPackage.Literals.CATEGORY__COMPOSITE,
						parameter.getValue()));
			}
		}
	}
}
