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
package org.eclipse.emfforms.internal.editor.ecore.helpers;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.internal.editor.ecore.controls.TypedElementBoundsRenderer;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.osgi.service.component.annotations.Component;

/**
 * Specific reveal provider for the bounds control of typed elements, which accounts
 * for the fact that this control edits two features, one of which (the lower bound)
 * is not mapped in the settings mapper service.
 *
 * @since 1.22
 */
@Component(name = "typedElementBoundsRevealProvider")
public class TypedElementBoundsRevealProvider implements EMFFormsRevealProvider {

	private final Set<EStructuralFeature> features = new HashSet<>(asList(
		EcorePackage.Literals.ETYPED_ELEMENT__LOWER_BOUND,
		EcorePackage.Literals.ETYPED_ELEMENT__UPPER_BOUND));

	private final Double bid = 20.0;

	/**
	 * Initializes me.
	 */
	public TypedElementBoundsRevealProvider() {
		super();
	}

	/**
	 * I can reveal a control that renders the upper or lower bound of an Ecore typed element.
	 *
	 * @param control the control
	 * @param feature the feature to be revealed
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VControl control, EStructuralFeature feature) {
		return control.eClass() == VViewPackage.Literals.CONTROL && features.contains(feature)
			? bid
			: null;
	}

	/**
	 * Reveal the given {@code feature} of an {@code owner} in the bounds {@code renderer}.
	 *
	 * @param renderer the bounds renderer
	 * @param owner the typed element
	 * @param feature the bounds feature to reveal
	 * @return the reveal step
	 */
	@Create
	public RevealStep reveal(@Renderer TypedElementBoundsRenderer renderer, EObject owner,
		EStructuralFeature feature) {

		return RevealStep.reveal(renderer.getVElement(), owner, feature, () -> renderer.reveal(feature));
	}

}
