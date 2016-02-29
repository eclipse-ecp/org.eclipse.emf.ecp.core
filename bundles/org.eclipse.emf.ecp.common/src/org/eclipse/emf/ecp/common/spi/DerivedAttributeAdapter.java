/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * The DerivedAttributeAdapter can be added on EObjects which include derived {@link EStructuralFeature
 * EStructuralFeatures}. This Adapter will make sure that correct SET/UNSET notifications are issued when a feature from
 * which the derived feature is derived from changes.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public class DerivedAttributeAdapter extends AdapterImpl {

	private final Set<EStructuralFeature> localFeatures = new LinkedHashSet<EStructuralFeature>();

	private final InternalEObject source;
	private final EStructuralFeature derivedFeature;

	private Object oldValue;

	/**
	 * @param source the owner of the derived feature
	 * @param derivedFeature the derived feature
	 */
	public DerivedAttributeAdapter(EObject source, EStructuralFeature derivedFeature) {
		super();
		this.source = (InternalEObject) source;
		this.derivedFeature = derivedFeature;
		oldValue = derivedFeature.getDefaultValue();
		source.eAdapters().add(this);
	}

	/**
	 * Use this method to indicate that the given feature influences the derived value and is owned by the same
	 * {@link EObject} as the derived feature.
	 *
	 * @param localFeature the local feature.
	 */
	public void addLocalDependency(EStructuralFeature localFeature) {
		localFeatures.add(localFeature);
	}

	/**
	 * Use this method to indicate the given given feature path influences the derived value.
	 *
	 * @param dependentFeature the last feature on the path
	 * @param navigationFeatures the reference path. This must <b>NOT</b> be empty
	 */
	public void addNavigatedDependency(EStructuralFeature dependentFeature, EReference... navigationFeatures) {
		if (navigationFeatures.length < 1) {
			throw new IllegalArgumentException("Use addLocalDependency instead."); //$NON-NLS-1$
		}
		new NavigationAdapter(source, navigationFeatures, dependentFeature);
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final Object feature = notification.getFeature();

		if (feature == null) {
			return;
		}

		if (localFeatures.contains(feature)) {
			notifyDerivedAttributeChange();
		}
	}

	private void notifyDerivedAttributeChange() {
		if (!source.eNotificationRequired()) {
			return;
		}
		final boolean eIsSet = source.eIsSet(derivedFeature);
		final Object newValue = source.eGet(derivedFeature, true, true);
		final int eventType = eIsSet ? Notification.SET : Notification.UNSET;
		source.eNotify(new ENotificationImpl(source, eventType, derivedFeature, oldValue, newValue));
		oldValue = newValue;
	}

	/**
	 * Adapter which gets added on navigated features.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	class NavigationAdapter extends AdapterImpl {

		private boolean hasChildren;

		private EStructuralFeature ownFeature;

		private final Map<EObject, NavigationAdapter> children = new LinkedHashMap<EObject, DerivedAttributeAdapter.NavigationAdapter>();

		private EReference[] remainingPath;

		private EStructuralFeature dependentFeature;

		private final InternalEObject source;

		/**
		 * @param source the Object which will be the parent for the adapter
		 * @param navigationFeatures the remaining navigation path
		 * @param dependentFeature the final feature
		 */
		NavigationAdapter(InternalEObject source, EReference[] navigationFeatures,
			EStructuralFeature dependentFeature) {
			this.source = source;
			if (navigationFeatures.length != 0) {
				hasChildren = true;
				ownFeature = navigationFeatures[0];
				remainingPath = Arrays.copyOfRange(navigationFeatures, 1, navigationFeatures.length);
				this.dependentFeature = dependentFeature;
				if (ownFeature.isMany()) {
					@SuppressWarnings("unchecked")
					final List<InternalEObject> list = (List<InternalEObject>) source.eGet(ownFeature);
					for (final InternalEObject child : list) {
						if (child != null) {
							addNavigationAdapter(dependentFeature, remainingPath, child);
						}
					}
				} else {
					final InternalEObject child = (InternalEObject) source.eGet(ownFeature);
					if (child != null) {
						addNavigationAdapter(dependentFeature, remainingPath, child);
					}
				}
			} else {
				hasChildren = false;
				ownFeature = dependentFeature;
			}
			source.eAdapters().add(this);
		}

		private void addNavigationAdapter(EStructuralFeature dependentFeature, EReference[] remainingPath,
			InternalEObject child) {
			children.put(child, new NavigationAdapter(child, remainingPath, dependentFeature));
		}

		/**
		 * @return the children map (for testing purposes)
		 */
		Map<EObject, NavigationAdapter> children() {
			return children;
		}

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			final Object feature = notification.getFeature();

			if (feature == null) {
				return;
			}

			if (ownFeature != feature) {
				return;
			}

			if (hasChildren) {
				switch (notification.getEventType()) {
				case Notification.ADD:
					final InternalEObject added = (InternalEObject) notification.getNewValue();
					addNavigationAdapter(dependentFeature, remainingPath, added);
					break;
				case Notification.ADD_MANY:
					@SuppressWarnings("unchecked")
					final List<InternalEObject> allAdded = (List<InternalEObject>) notification.getNewValue();
					for (final InternalEObject addedObject : allAdded) {
						addNavigationAdapter(dependentFeature, remainingPath, addedObject);
					}
					break;
				case Notification.SET:
				case Notification.UNSET:
					final InternalEObject newValue = (InternalEObject) notification.getNewValue();
					final InternalEObject oldValue = (InternalEObject) notification.getOldValue();
					if (oldValue != null) {
						children.remove(oldValue).dispose();
					}
					if (newValue != null) {
						addNavigationAdapter(dependentFeature, remainingPath, newValue);
					}
					break;
				case Notification.REMOVE:
					final InternalEObject removed = (InternalEObject) notification.getOldValue();
					children.remove(removed).dispose();
					break;
				case Notification.REMOVE_MANY:
					@SuppressWarnings("unchecked")
					final List<InternalEObject> listRemoved = (List<InternalEObject>) notification.getOldValue();
					for (final InternalEObject removedObj : listRemoved) {
						children.remove(removedObj).dispose();
					}
					break;
				default:
					return; // No notification
				}
			}

			notifyDerivedAttributeChange();
		}

		private void dispose() {
			for (final NavigationAdapter navigationAdapter : children.values()) {
				navigationAdapter.dispose();
			}
			source.eAdapters().remove(this);
		}

	}
}
