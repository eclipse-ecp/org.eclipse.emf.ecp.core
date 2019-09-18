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
package org.eclipse.emfforms.internal.core.services.reveal;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStepKind;

/**
 * A deferred drill-down step.
 *
 * @since 1.22
 */
final class DrillDownRevealStep implements RevealStep {

	private final EMFFormsRevealServiceImpl owner;
	private final VElement element;
	private final EObject object;
	private final EStructuralFeature feature;
	private final RevealStep nextStep;
	private final Object computation;

	private RevealStep resolved;

	/**
	 * Initializes me.
	 *
	 * @param owner my owning reveal service
	 * @param element the view model element in which I drill down to the {@code object}
	 * @param object the object that I reveal
	 * @param feature the structural feature that I reveal
	 * @param nextStep the actual next step in the drill-down sequence
	 * @param computation the computation of the drill-down step to which I delegate
	 */
	DrillDownRevealStep(EMFFormsRevealServiceImpl owner, VElement element, EObject object,
		EStructuralFeature feature, RevealStep nextStep, Object computation) {

		super();

		this.owner = owner;
		this.element = element;
		this.object = object;
		this.feature = feature;
		this.nextStep = nextStep;
		this.computation = computation;
	}

	@Override
	public RevealStepKind getType() {
		return RevealStepKind.INTERMEDIATE;
	}

	@Override
	public VElement getViewModel() {
		return element;
	}

	@Override
	public EObject getDomainModel() {
		return object;
	}

	@Override
	public EStructuralFeature getFeature() {
		return null; // I am an intermediate step
	}

	@Override
	public RevealStep drillDown() {
		if (resolved == null) {
			final Map<Class<?>, Object> additionalParameters = Collections.singletonMap(RevealStep.class, nextStep);

			resolved = owner.evaluate(Reveal.class, RevealStep.class,
				element, object, feature, additionalParameters, computation).orElse(FAILED);
		}

		return resolved;
	}

	@Override
	public void reveal() {
		// I have nothing of my own to reveal; only the next step to drill down into
	}

}
