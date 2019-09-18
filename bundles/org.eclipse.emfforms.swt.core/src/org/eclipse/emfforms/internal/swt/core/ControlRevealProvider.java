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
package org.eclipse.emfforms.internal.swt.core;

import static org.eclipse.emf.ecp.common.spi.UniqueSetting.createSetting;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.model.common.di.annotations.ViewService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;

/**
 * A reveal provider for {@link VControl}s that reveals a specific {@link EStructuralFeature} setting.
 *
 * @since 1.22
 */
@Component(name = "controlRevealProvider")
public class ControlRevealProvider implements EMFFormsRevealProvider {

	private final Double controlBid = 10.0;

	/**
	 * I bid on the {@code control} if it is presents the given {@code feature} of the domain
	 * model {@code object}.
	 *
	 * @param control the element to bid on
	 * @param object the domain object to bid on
	 * @param feature the feature of the domain {@code object} to bid on
	 * @param settingMapper the setting-to-control mapper service
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VControl control, EObject object, EStructuralFeature feature,
		@ViewService EMFFormsSettingToControlMapper settingMapper) {

		// Not a complex control such as table that is handled separately
		return control.eClass() == VViewPackage.Literals.CONTROL
			&& settingMapper.hasMapping(createSetting(object, feature), control)
				? controlBid
				: null;
	}

	/**
	 * Create a terminal reveal step to focus into the specific setting control in a
	 * container {@code element}.
	 *
	 * @param renderer the renderer of the control to reveal
	 * @param object the object to reveal
	 * @param feature the feature of the domain {@code object} to reveal
	 * @return the specific control reveal step
	 */
	@Create
	public RevealStep reveal(@Renderer AbstractSWTRenderer<?> renderer, EObject object,
		EStructuralFeature feature) {

		return RevealStep.reveal(renderer.getVElement(), object, feature,
			() -> Display.getDefault().asyncExec(renderer::scrollToReveal));
	}

}
