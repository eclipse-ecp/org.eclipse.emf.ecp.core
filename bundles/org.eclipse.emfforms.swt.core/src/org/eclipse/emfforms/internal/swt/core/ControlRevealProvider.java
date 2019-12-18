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
import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.model.common.di.annotations.ViewService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
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
	 * Create a terminal reveal step to focus into the specific setting {@code control}
	 * in a that presents a {@code feature} or an {@code object}.
	 *
	 * @param control the control to reveal
	 * @param object the object to reveal
	 * @param feature the feature of the domain {@code object} to reveal
	 * @param context the view-model context in which the {@code control} is rendered
	 * @return the specific control reveal step
	 */
	@Create
	public RevealStep reveal(VControl control, EObject object,
		EStructuralFeature feature, ViewModelContext context) {

		return RevealStep.reveal(control, object, feature,
			// Scroll to reveal the control in the future, when it has been
			// rendered by the previous reveal step (bug 551066)
			() -> Display.getDefault().asyncExec(() -> scrollToReveal(control, context)));
	}

	/**
	 * Scroll to reveal the rendered {@code control}.
	 *
	 * @param control a control rendered in some {@code context}
	 * @param context the context in which the {@code control} is rendered
	 */
	private void scrollToReveal(VControl control, ViewModelContext context) {
		final AbstractRenderer<?> renderer = AbstractRenderer.getRenderer(control, context);
		if (renderer instanceof AbstractSWTRenderer<?>) {
			((AbstractSWTRenderer<?>) renderer).scrollToReveal();
		}
	}

}
