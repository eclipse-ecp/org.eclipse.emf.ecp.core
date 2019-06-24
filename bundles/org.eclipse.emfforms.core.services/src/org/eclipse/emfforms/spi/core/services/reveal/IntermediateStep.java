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
package org.eclipse.emfforms.spi.core.services.reveal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * An intermediate step in a successful reveal.
 *
 * @since 1.22
 */
final class IntermediateStep implements RevealStep {

	private final VElement viewModel;
	private final EObject domainModel;
	private final RevealStep nextStep;
	private final Runnable revealAction;

	/**
	 * Initializes me.
	 *
	 * @param viewModel the view model in which I reveal the object
	 * @param domainModel the object that I reveal
	 * @param nextStep the next step in the drill-down sequence
	 * @param revealAction the action that performs the reveal in the UI
	 */
	IntermediateStep(VElement viewModel, EObject domainModel, RevealStep nextStep, Runnable revealAction) {
		super();

		this.viewModel = viewModel;
		this.domainModel = domainModel;
		this.nextStep = nextStep;
		this.revealAction = revealAction;
	}

	@Override
	public RevealStepKind getType() {
		return RevealStepKind.INTERMEDIATE;
	}

	@Override
	public VElement getViewModel() {
		return viewModel;
	}

	@Override
	public EObject getDomainModel() {
		return domainModel;
	}

	@Override
	public EStructuralFeature getFeature() {
		return null;
	}

	@Override
	public RevealStep drillDown() {
		reveal();

		return nextStep;
	}

	@Override
	public void reveal() {
		if (revealAction != null) {
			revealAction.run();
		}
	}

}
