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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
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
	private final ViewModelListener viewModelListener;

	private final Map<EObject, Set<UniqueSetting>> eObjectToMappedSettings = new LinkedHashMap<EObject, Set<UniqueSetting>>();

	/**
	 * A mapping between settings and controls.
	 */
	private final Map<UniqueSetting, Set<VControl>> settingToControlMap = new LinkedHashMap<UniqueSetting, Set<VControl>>();
	private final EMFFormsMappingProviderManager mappingManager;
	private final Map<VElement, Set<EMFFormsSettingToControlMapper>> childMappers = new LinkedHashMap<VElement, Set<EMFFormsSettingToControlMapper>>();

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
		viewModelContext.registerViewChangeListener(new ModelChangeAddRemoveListenerImplementation());
		viewModelListener = new ViewModelListener(viewModelContext, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		return settingToControlMap.get(UniqueSetting.createSetting(setting));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		final Set<VElement> elements = new LinkedHashSet<VElement>();
		final Set<VControl> currentControls = settingToControlMap.get(setting);
		if (currentControls != null) {
			for (final VElement control : currentControls) {
				if (!control.isEnabled() || !control.isVisible() || control.isReadonly()) {
					continue;
				}
				elements.add(control);
			}
		}
		for (final VElement parentElement : childMappers.keySet()) {
			for (final EMFFormsSettingToControlMapper childMapper : childMappers.get(parentElement)) {
				final Set<VElement> controlsFor = childMapper.getControlsFor(setting);
				elements.addAll(controlsFor);
				if (!controlsFor.isEmpty()) {
					elements.add(parentElement);
				}
			}
		}
		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateControlMapping(VControl vControl) {
		if (vControl == null) {
			return;
		}
		// delete old mapping
		final Set<UniqueSetting> keysWithEmptySets = new LinkedHashSet<UniqueSetting>();
		for (final UniqueSetting setting : settingToControlMap.keySet()) {
			final Set<VControl> controlSet = settingToControlMap.get(setting);
			controlSet.remove(vControl);
			if (controlSet.isEmpty()) {
				keysWithEmptySets.add(setting);
			}
		}
		for (final UniqueSetting setting : keysWithEmptySets) {
			settingToControlMap.remove(setting);
			handleRemoveForEObjectMapping(setting);
		}
		// update mapping
		final Set<UniqueSetting> map = mappingManager.getAllSettingsFor(vControl.getDomainModelReference(),
			viewModelContext.getDomainModel());
		for (final UniqueSetting setting : map) {
			if (!settingToControlMap.containsKey(setting)) {
				settingToControlMap.put(setting, new LinkedHashSet<VControl>());
				handleAddForEObjectMapping(setting);
			}
			settingToControlMap.get(setting).add(vControl);
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
		if (vControl.getDomainModelReference() == null) {
			return;
		}

		final Set<UniqueSetting> map = mappingManager.getAllSettingsFor(vControl.getDomainModelReference(),
			viewModelContext.getDomainModel());
		for (final UniqueSetting setting : map) {
			if (settingToControlMap.containsKey(setting)) {
				settingToControlMap.get(setting).remove(vControl);
				if (settingToControlMap.get(setting).size() == 0) {
					settingToControlMap.remove(setting);
					handleRemoveForEObjectMapping(setting);
				}
			}
		}

		viewModelListener.removeVControl(vControl);
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

		viewModelListener.addVControl(vControl);
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
		final EMFFormsSettingToControlMapper childSettingToControlMapper = childContext
			.getService(EMFFormsSettingToControlMapper.class);
		if (!childMappers.containsKey(parentElement)) {
			childMappers.put(parentElement, new LinkedHashSet<EMFFormsSettingToControlMapper>());
		}
		childMappers.get(parentElement).add(childSettingToControlMapper);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		final EMFFormsSettingToControlMapper childSettingToControlMapper = childContext
			.getService(EMFFormsSettingToControlMapper.class);
		for (final VElement vElement : childMappers.keySet()) {
			childMappers.get(vElement).remove(childSettingToControlMapper);
		}
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
		viewModelListener.dispose();
		settingToControlMap.clear();
		childMappers.clear();
	}

	@Override
	public boolean hasControlsFor(EObject eObject) {
		if (eObjectToMappedSettings.containsKey(eObject)) {
			return true;
		}

		for (final VElement parentElement : childMappers.keySet()) {
			for (final EMFFormsSettingToControlMapper childMapper : childMappers.get(parentElement)) {
				if (childMapper.hasControlsFor(eObject)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Collection<EObject> getEObjectsWithSettings() {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		result.addAll(eObjectToMappedSettings.keySet());
		for (final VElement parentElement : childMappers.keySet()) {
			for (final EMFFormsSettingToControlMapper childMapper : childMappers.get(parentElement)) {
				result.addAll(childMapper.getEObjectsWithSettings());
			}
		}
		return result;
	}
}
