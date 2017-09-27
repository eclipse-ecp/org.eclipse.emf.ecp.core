/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.controlmapper;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * Implementation of {@link EMFFormsSettingToControlMapper}.
 *
 * @author Lucas Koehler
 *
 */
public class SettingToControlMapperImpl implements EMFFormsSettingToControlMapper, EMFFormsContextListener {
	/**
	 * Used to get View model changes.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private final class ModelChangeAddRemoveListenerImplementation implements ModelChangeAddRemoveListener {
		@Override
		public void notifyChange(ModelChangeNotification notification) {
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener#notifyAdd(org.eclipse.emf.common.notify.Notifier)
		 */
		@Override
		public void notifyAdd(Notifier notifier) {
			if (VDomainModelReference.class.isInstance(notifier)
				&& !VDomainModelReference.class.isInstance(EObject.class.cast(notifier).eContainer())) {
				final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(notifier);

				// FIXME remove
				SettingToControlExpandHelper.resolveDomainReferences(domainModelReference,
					viewModelContext.getDomainModel(),
					viewModelContext);

				final VControl control = findControl(domainModelReference);
				if (control != null) {
					vControlAdded(control);
				}
			}
		}

		private VControl findControl(VDomainModelReference dmr) {
			EObject parent = dmr.eContainer();
			while (!VControl.class.isInstance(parent) && parent != null) {
				parent = parent.eContainer();
			}
			return (VControl) parent;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener#notifyRemove(org.eclipse.emf.common.notify.Notifier)
		 */
		@Override
		public void notifyRemove(Notifier notifier) {
			if (VControl.class.isInstance(notifier)) {
				vControlRemoved((VControl) notifier);
			}
		}
	}

	private final EMFFormsViewContext viewModelContext;
	private final ViewModelListener dataModelListener;

	private final Map<EObject, Set<UniqueSetting>> eObjectToMappedSettings = new LinkedHashMap<EObject, Set<UniqueSetting>>();

	/**
	 * A mapping between settings and controls.
	 */
	private final Map<UniqueSetting, Set<VElement>> settingToControlMap = new LinkedHashMap<UniqueSetting, Set<VElement>>();
	private final Map<VElement, Set<UniqueSetting>> controlToSettingMap = new LinkedHashMap<VElement, Set<UniqueSetting>>();
	private final EMFFormsMappingProviderManager mappingManager;
	private final Map<VControl, EMFFormsViewContext> controlContextMap = new LinkedHashMap<VControl, EMFFormsViewContext>();
	private final Map<EMFFormsViewContext, VElement> contextParentMap = new LinkedHashMap<EMFFormsViewContext, VElement>();
	private final Map<EMFFormsViewContext, ViewModelListener> contextListenerMap = new LinkedHashMap<EMFFormsViewContext, ViewModelListener>();
	private final ModelChangeAddRemoveListenerImplementation viewModelChangeListener;
	private boolean disposed;

	/**
	 * Creates a new instance of {@link SettingToControlMapperImpl}.
	 *
	 * @param mappingManager The {@link EMFFormsMappingProviderManager}
	 * @param viewModelContext The {@link EMFFormsViewContext} that created this instance
	 */
	public SettingToControlMapperImpl(EMFFormsMappingProviderManager mappingManager,
		EMFFormsViewContext viewModelContext) {
		this.mappingManager = mappingManager;
		this.viewModelContext = viewModelContext;
		viewModelContext.registerEMFFormsContextListener(this);
		viewModelChangeListener = new ModelChangeAddRemoveListenerImplementation();
		viewModelContext.registerViewChangeListener(viewModelChangeListener);
		dataModelListener = new ViewModelListener(viewModelContext, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		final Set<VControl> result = new LinkedHashSet<VControl>();
		final Set<VElement> allElements = getControlsFor(UniqueSetting.createSetting(setting));
		for (final VElement element : allElements) {
			if (VControl.class.isInstance(element)) {
				result.add((VControl) element);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		final Set<VElement> elements = new LinkedHashSet<VElement>();
		final Set<VElement> currentControls = settingToControlMap.get(setting);
		final Set<VControl> controls = new LinkedHashSet<VControl>();
		final Set<VElement> validParents = new LinkedHashSet<VElement>();
		if (currentControls != null) {
			for (final VElement control : currentControls) {
				if (!control.isEffectivelyEnabled() || !control.isEffectivelyVisible()
					|| control.isEffectivelyReadonly()) {
					continue;
				}
				if (VControl.class.isInstance(control)) {
					controls.add((VControl) control);
					if (controlContextMap.containsKey(control)) {
						final VElement parent = contextParentMap.get(controlContextMap.get(control));
						validParents.add(parent);
					}
				} else {
					elements.add(control);
				}
			}
		}
		if (controls.isEmpty()) {
			return Collections.emptySet();
		}
		final Set<VElement> result = new LinkedHashSet<VElement>(controls);
		elements.retainAll(validParents);
		result.addAll(elements);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void updateControlMapping(VControl vControl) {
		if (vControl == null) {
			return;
		}
		// delete old mapping
		deleteOldMapping(vControl);
		// update mapping
		final EMFFormsViewContext controlContext = controlContextMap.get(vControl);
		final Set<UniqueSetting> map = mappingManager.getAllSettingsFor(vControl.getDomainModelReference(),
			controlContext == null ? viewModelContext.getDomainModel() : controlContext.getDomainModel());
		if (!controlToSettingMap.containsKey(vControl)) {
			controlToSettingMap.put(vControl, new LinkedHashSet<UniqueSetting>());
		}
		controlToSettingMap.get(vControl).addAll(map);
		for (final UniqueSetting setting : map) {
			if (!settingToControlMap.containsKey(setting)) {
				settingToControlMap.put(setting, new LinkedHashSet<VElement>());
				handleAddForEObjectMapping(setting);
			}
			settingToControlMap.get(setting).add(vControl);
			if (controlContext != null) {
				VElement parentElement = contextParentMap.get(controlContext);
				while (parentElement != null) {
					settingToControlMap.get(setting).add(parentElement);
					final EMFFormsViewContext context = controlContextMap.get(parentElement);
					if (context == null) {
						break;
					}
					parentElement = contextParentMap.get(context);
				}
			}
		}
	}

	private void deleteOldMapping(VControl vControl) {
		if (controlToSettingMap.containsKey(vControl)) {
			final Set<UniqueSetting> keysWithEmptySets = new LinkedHashSet<UniqueSetting>();
			for (final UniqueSetting setting : controlToSettingMap.get(vControl)) {
				final Set<VElement> controlSet = settingToControlMap.get(setting);
				controlSet.remove(vControl);
				if (controlSet.isEmpty()) {
					keysWithEmptySets.add(setting);
				}
			}
			for (final UniqueSetting setting : keysWithEmptySets) {
				settingToControlMap.remove(setting);
				handleRemoveForEObjectMapping(setting);
			}
			controlToSettingMap.remove(vControl);
		}
	}

	private void handleAddForEObjectMapping(UniqueSetting setting) {
		if (!eObjectToMappedSettings.containsKey(setting.getEObject())) {
			eObjectToMappedSettings.put(setting.getEObject(), new LinkedHashSet<UniqueSetting>());
		}
		eObjectToMappedSettings.get(setting.getEObject()).add(setting);
	}

	private void handleRemoveForEObjectMapping(UniqueSetting setting) {
		final Set<UniqueSetting> settings = eObjectToMappedSettings.get(setting.getEObject());
		settings.remove(setting);
		if (settings.isEmpty()) {
			eObjectToMappedSettings.remove(setting.getEObject());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vControlRemoved(VControl vControl) {
		deleteOldMapping(vControl);

		final EMFFormsViewContext viewContext = controlContextMap.get(vControl);
		if (viewContext != null && contextListenerMap.containsKey(viewContext)) {
			contextListenerMap.get(viewContext).removeVControl(vControl);
		} else {
			dataModelListener.removeVControl(vControl);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void vControlAdded(VControl vControl) {
		if (vControl.getDomainModelReference() == null) {
			return;
		}

		checkAndUpdateSettingToControlMapping(vControl);
		final EMFFormsViewContext viewContext = controlContextMap.get(vControl);
		if (viewContext != null) {
			contextListenerMap.get(viewContext).addVControl(vControl);
		} else {
			dataModelListener.addVControl(vControl);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkAndUpdateSettingToControlMapping(EObject eObject) {
		if (VControl.class.isInstance(eObject)) {
			final VControl vControl = (VControl) eObject;
			if (vControl.getDomainModelReference() == null) {
				return;
			}

			updateControlMapping(vControl);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextAdded(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		childContext.registerViewChangeListener(viewModelChangeListener);
		contextParentMap.put(childContext, parentElement);
		contextListenerMap.put(childContext, new ViewModelListener(childContext, this));
		final TreeIterator<EObject> eAllContents = childContext.getViewModel().eAllContents();
		while (eAllContents.hasNext()) {
			final EObject next = eAllContents.next();
			if (VControl.class.isInstance(next)) {
				controlContextMap.put((VControl) next, childContext);
				vControlAdded((VControl) next);
			}
		}
		childContext.registerEMFFormsContextListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		if (disposed) {
			return;
		}
		childContext.unregisterViewChangeListener(viewModelChangeListener);
		contextParentMap.remove(childContext);
		final ViewModelListener listener = contextListenerMap.remove(childContext);
		listener.dispose();
		final TreeIterator<EObject> eAllContents = childContext.getViewModel().eAllContents();
		while (eAllContents.hasNext()) {
			final EObject next = eAllContents.next();
			if (VControl.class.isInstance(next)) {
				vControlRemoved((VControl) next);
				controlContextMap.remove(next);
			}
		}
		childContext.unregisterEMFFormsContextListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextInitialised()
	 */
	@Override
	public void contextInitialised() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextDispose()
	 */
	@Override
	public void contextDispose() {
		viewModelContext.unregisterEMFFormsContextListener(this);
		dataModelListener.dispose();
		settingToControlMap.clear();
		controlContextMap.clear();
		contextParentMap.clear();
		contextListenerMap.clear();
		disposed = true;
	}

	@Override
	public boolean hasControlsFor(EObject eObject) {
		if (eObjectToMappedSettings.containsKey(eObject)) {
			return true;
		}

		return false;
	}

	@Override
	public Collection<EObject> getEObjectsWithSettings() {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		result.addAll(eObjectToMappedSettings.keySet());
		return result;
	}
}
