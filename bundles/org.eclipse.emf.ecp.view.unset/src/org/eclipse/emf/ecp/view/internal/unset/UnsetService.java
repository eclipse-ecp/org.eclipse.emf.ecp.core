/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.unset;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * Unset service that, once instantiated, synchronizes the visible state of a
 * view and its children with the affected EStructuralFeature(s), i.e.
 * setting/unsetting the value(s).
 * 
 * @author jfaltermeier
 * 
 */
public class UnsetService implements ViewModelService {

	private static final String DOMAIN_MODEL_NULL_EXCEPTION = "Domain model must not be null."; //$NON-NLS-1$
	private static final String VIEW_MODEL_NULL_EXCEPTION = "View model must not be null."; //$NON-NLS-1$

	private ViewModelContext context;
	private ModelChangeListener viewChangeListener;

	private final Map<EObject, Set<FeatureWrapper>> objectToFeatureMap;
	private final Map<FeatureWrapper, Set<VControl>> settingToControlMap;

	/**
	 * During init all controls that are hidden, either direct or by being
	 * contained by a hidden parent, are collected here.
	 */
	private final Set<VControl> hiddenControlsDuringInit;

	/**
	 * Default constructor for the unset service.
	 */
	public UnsetService() {
		objectToFeatureMap = new LinkedHashMap<EObject, Set<FeatureWrapper>>();
		settingToControlMap = new LinkedHashMap<FeatureWrapper, Set<VControl>>();
		hiddenControlsDuringInit = new LinkedHashSet<VControl>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(ViewModelContext context) {
		this.context = context;

		viewChangeListener = new ModelChangeListener() {
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Visible()) {
					final EObject notifier = notification.getNotifier();
					final Notification rawNotification = notification.getRawNotification();
					if (rawNotification.getNewBooleanValue()) {
						// isVisible set to true
						show((VElement) notifier);
						return;
					}
					// isVisible set to false
					hide((VElement) notifier);

				}
			}

			public void notifyAdd(Notifier notifier) {
			}

			public void notifyRemove(Notifier notifier) {
			}
		};
		context.registerViewChangeListener(viewChangeListener);

		final VElement view = context.getViewModel();
		if (view == null) {
			throw new IllegalStateException(VIEW_MODEL_NULL_EXCEPTION);
		}

		final EObject domainModel = context.getDomainModel();
		if (domainModel == null) {
			throw new IllegalStateException(DOMAIN_MODEL_NULL_EXCEPTION);
		}

		initMaps(view, false);
		unsetInitialHiddenControls();
	}

	private void initMaps(VElement view, boolean parentInvisible) {
		if (!view.isVisible() || parentInvisible) {
			if (view instanceof VControl) {
				hiddenControlsDuringInit.add((VControl) view);
			}
			parentInvisible = true;
		}

		if (view instanceof VControl) {
			addControlToMap((VControl) view);
		} else {
			final EList<EObject> children = view.eContents();
			for (final EObject child : children) {
				if (child == null) {
					continue;
				}

				if (child instanceof VElement) {
					initMaps((VElement) child, parentInvisible);
				}
			}
		}
	}

	private void unsetInitialHiddenControls() {
		for (final VControl control : hiddenControlsDuringInit) {
			removeControlFromMapAndUnsetIfNeeded(control);
		}
	}

	private void addControlToMap(VControl control) {
		final Setting lastSetting = getSetting(control);
		if (lastSetting == null) {
			return;
		}
		final EObject eObject = lastSetting.getEObject();
		final EStructuralFeature eStructuralFeature = lastSetting
			.getEStructuralFeature();

		if (!objectToFeatureMap.containsKey(eObject)) {
			objectToFeatureMap
				.put(eObject, new LinkedHashSet<FeatureWrapper>());
		}
		final Set<FeatureWrapper> features = objectToFeatureMap.get(eObject);
		FeatureWrapper wrapper = null;
		for (final FeatureWrapper w : features) {
			if (w.isWrapperFor(eStructuralFeature)) {
				wrapper = w;
				break;
			}
		}
		if (wrapper == null) {
			wrapper = new FeatureWrapper(eStructuralFeature);
			features.add(wrapper);
		}

		if (!settingToControlMap.containsKey(wrapper)) {
			settingToControlMap.put(wrapper, new LinkedHashSet<VControl>());
		}
		settingToControlMap.get(wrapper).add(control);
	}

	private void removeControlFromMapAndUnsetIfNeeded(VControl control) {
		final Setting lastSetting = getSetting(control);
		if (lastSetting == null) {
			return;
		}
		final EObject eObject = lastSetting.getEObject();
		final EStructuralFeature eStructuralFeature = lastSetting
			.getEStructuralFeature();
		final Set<FeatureWrapper> wrappers = objectToFeatureMap
			.get(eObject);
		FeatureWrapper wrapper = null;
		for (final FeatureWrapper w : wrappers) {
			if (w.isWrapperFor(eStructuralFeature)) {
				wrapper = w;
			}
		}

		final Set<VControl> visibleControls = settingToControlMap
			.get(wrapper);
		visibleControls.remove(control);
		if (visibleControls.isEmpty()) {
			eObject.eUnset(eStructuralFeature);
		}
	}

	/**
	 * The given element just became visible.
	 * If it is a control add it to the map.
	 * If it is a container check if children became visible
	 * 
	 * @param element
	 */
	private void show(VElement element) {
		if (element instanceof VControl) {
			addControlToMap((VControl) element);
			return;
		}

		final EList<EObject> children = element.eContents();
		for (final EObject child : children) {
			if (child == null) {
				continue;
			}
			if (child instanceof VElement) {
				final VElement childElement = (VElement) child;
				if (childElement.isVisible()) {
					show(childElement);
				}
			}
		}
	}

	/**
	 * The given element just became invisible.
	 * If it is a control remove it from map and unset if needed.
	 * If it is a container hide all child controls.
	 * 
	 * 
	 * @param element
	 */
	private void hide(VElement element) {
		if (element instanceof VControl) {
			removeControlFromMapAndUnsetIfNeeded((VControl) element);
			return;
		}

		final TreeIterator<EObject> iterator = element.eAllContents();
		while (iterator.hasNext()) {
			final EObject object = iterator.next();
			if (object == null) {
				continue;
			}
			if (object instanceof VControl) {
				removeControlFromMapAndUnsetIfNeeded((VControl) object);
			}
		}
	}

	private Setting getSetting(VControl control) {
		final VDomainModelReference domainModelReference = control
			.getDomainModelReference();
		final Iterator<Setting> settings = domainModelReference.getIterator();
		Setting firstSetting = null;
		while (settings.hasNext()) {
			final Setting setting = settings.next();
			if (firstSetting == null) {
				firstSetting = setting;
			}
		}
		return firstSetting;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	public void dispose() {
		context.unregisterViewChangeListener(viewChangeListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	public int getPriority() {
		return 5;
	}

	/**
	 * Class wrapping an {@link EStructuralFeature} as a dedicated object that
	 * can be used as key.
	 * 
	 * @author jfaltermeier
	 * 
	 */
	private class FeatureWrapper {

		private final EStructuralFeature feature;

		/**
		 * Default constructor.
		 * 
		 * @param feature
		 *            the feature to wrap
		 */
		public FeatureWrapper(EStructuralFeature feature) {
			this.feature = feature;
		}

		/**
		 * Whether this wrapper is mapped to the given feature.
		 * 
		 * @param featureToCompare
		 * @return <code>true</code> if equals
		 */
		public boolean isWrapperFor(EStructuralFeature featureToCompare) {
			return feature.equals(featureToCompare);
		}

	}

}
