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
 * The terminal step of a successful reveal.
 *
 * @since 1.22
 */
final class TerminalStep implements RevealStep {

	private final VElement viewModel;
	private final EObject domainModel;
	private final EStructuralFeature feature;
	private final Runnable revealAction;

	/**
	 * Initializes me.
	 *
	 * @param viewModel the view model in which I reveal the object
	 * @param domainModel the object that I reveal
	 * @param feature the feature of the domain model object that I specfically revealed
	 * @param revealAction an action that reveals the object in the UI
	 */
	TerminalStep(VElement viewModel, EObject domainModel, EStructuralFeature feature, Runnable revealAction) {
		super();

		this.viewModel = viewModel;
		this.domainModel = domainModel;
		this.feature = feature;
		this.revealAction = revealAction;
	}

	@Override
	public RevealStepKind getType() {
		return RevealStepKind.TERMINAL;
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
		return feature;
	}

	/**
	 * There is no further step in the reveal process.
	 *
	 * @return {@code null}
	 */
	@Override
	public RevealStep drillDown() {
		return null;
	}

	@Override
	public void reveal() {
		if (revealAction != null) {
			revealAction.run();
		}
	}

}
