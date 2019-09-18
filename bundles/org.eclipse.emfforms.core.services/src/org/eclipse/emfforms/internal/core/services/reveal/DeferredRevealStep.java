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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStepKind;

/**
 * A deferred reveal step.
 *
 * @since 1.22
 */
final class DeferredRevealStep implements RevealStep {

	private final EMFFormsRevealServiceImpl owner;
	private final VElement element;
	private final EObject object;
	private final EStructuralFeature feature;
	private final Object computation;

	private RevealStep resolved;

	/**
	 * Initializes me.
	 *
	 * @param owner my owning reveal service
	 * @param element the view model element in which I reveal the {@code object}
	 * @param object the object that I reveal
	 * @param feature the structural feature that I reveal
	 * @param computation the computation of the reveal step to which I delegate
	 */
	DeferredRevealStep(EMFFormsRevealServiceImpl owner, VElement element, EObject object,
		EStructuralFeature feature, Object computation) {

		super();

		this.owner = owner;
		this.element = element;
		this.object = object;
		this.feature = feature;
		this.computation = computation;
	}

	@Override
	public RevealStepKind getType() {
		return RevealStepKind.TERMINAL;
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
		return feature;
	}

	@Override
	public RevealStep drillDown() {
		// I'm a terminal step
		return null;
	}

	@Override
	public void reveal() {
		if (resolved == null) {
			resolved = owner.evaluate(Reveal.class, RevealStep.class,
				element, object, feature, computation).orElse(FAILED);
		}

		resolved.ifPresent(RevealStep::reveal);
	}

}
