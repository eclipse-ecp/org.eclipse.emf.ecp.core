/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;

/**
 * A delegator for testing default methods of the {@link EMFFormsSettingToControlMapper} interface.
 */
class InterfaceDelegator implements EMFFormsSettingToControlMapper {

	private final EMFFormsSettingToControlMapper delegate;

	/**
	 * Initializes me with my delegate.
	 */
	InterfaceDelegator(EMFFormsSettingToControlMapper delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		return delegate.getControlsFor(setting);
	}

	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		return delegate.getControlsFor(setting);
	}

	@Override
	public void updateControlMapping(VControl vControl) {
		delegate.updateControlMapping(vControl);
	}

	@Override
	public void vControlAdded(VControl vControl) {
		delegate.vControlAdded(vControl);
	}

	@Override
	public void vControlRemoved(VControl vControl) {
		delegate.vControlRemoved(vControl);
	}

	@Override
	public void checkAndUpdateSettingToControlMapping(EObject eObject) {
		delegate.checkAndUpdateSettingToControlMapping(eObject);
	}

	@Override
	public boolean hasControlsFor(EObject eObject) {
		return delegate.hasControlsFor(eObject);
	}

	@Override
	public Collection<EObject> getEObjectsWithSettings() {
		return delegate.getEObjectsWithSettings();
	}

	@Override
	public Set<UniqueSetting> getSettingsForControl(VControl control) {
		return delegate.getSettingsForControl(control);
	}

}
