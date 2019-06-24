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

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.reveal.DrillUp;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStepKind;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * A master/detail reveal step.
 *
 * @since 1.22
 */
final class MasterDetailRevealStep implements RevealStep {

	private final EMFFormsRevealServiceImpl owner;
	private final VElement element;
	private final EObject object;
	private final EStructuralFeature feature;
	private final Object masterFunction;
	private final Object masterStep;
	private RevealStep resolvedMasterStep;
	private RevealStep resolvedDetailStep;

	/**
	 * Initializes me.
	 *
	 * @param owner the reveal service that owns me
	 * @param element the master view model element
	 * @param object the object to reveal in the detail
	 * @param feature the structural feature that I reveal
	 * @param masterFunction the to compute the master selection object in the master view
	 *            that needs to be revealed to present the detail for reveal of the {@code object}
	 * @param masterStep how to reveal the selection in the master view
	 */
	MasterDetailRevealStep(EMFFormsRevealServiceImpl owner, VElement element, EObject object,
		EStructuralFeature feature, Object masterFunction, Object masterStep) {

		super();

		this.owner = owner;
		this.element = element;
		this.object = object;
		this.feature = feature;
		this.masterFunction = masterFunction;
		this.masterStep = masterStep;
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
		if (resolvedMasterStep == null) {
			final EObject master = computeMaster(object);

			if (master != null) {
				// The master is revealed as itself in some control, so no feature applies
				resolvedMasterStep = owner.evaluate(Reveal.class, RevealStep.class,
					element, master, null, masterStep).orElse(FAILED);

				// Perform the master step now so that it can set up the
				// detail context that we need next
				reveal();

				if (master != object || feature != null) {
					// There's also a detail to reveal, possibly a specific feature.
					// Find the view model context for the master object
					final EMFFormsViewContext detailContext = owner.getDetailContext(master);
					resolvedDetailStep = Optional.ofNullable(detailContext)
						.map(ctx -> owner.reveal(object, feature, ctx.getViewModel()))
						.orElse(FAILED);
				} else {
					resolvedDetailStep = FAILED;
				}
			} else {
				resolvedMasterStep = FAILED;
				resolvedDetailStep = FAILED;
			}
		}

		return resolvedDetailStep;
	}

	private EObject computeMaster(EObject object) {
		// The master is revealed as itself in some control, so no feature applies
		return owner.evaluate(DrillUp.class, EObject.class,
			element, object, null, masterFunction).orElse(null);
	}

	@Override
	public void reveal() {
		resolvedMasterStep.ifPresent(owner::perform);
	}

}
