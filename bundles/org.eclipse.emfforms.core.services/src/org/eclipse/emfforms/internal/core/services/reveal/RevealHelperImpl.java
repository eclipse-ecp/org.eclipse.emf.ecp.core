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

import static java.util.stream.StreamSupport.stream;

import java.util.Collections;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.reveal.DrillDown;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;

/**
 * Implementation of the reveal helper protocol.
 */
final class RevealHelperImpl implements RevealHelper {

	private final EMFFormsRevealServiceImpl owner;
	private final VElement viewModel;
	private final EObject domainModel;
	private final EStructuralFeature feature;

	/**
	 * Initialize me without a feature to reveal.
	 *
	 * @param owner the reveal service that owns me
	 * @param viewModel the view model in which to reveal an object
	 * @param domainModel the object to reveal
	 */
	@Inject
	RevealHelperImpl(EMFFormsRevealServiceImpl owner, VElement viewModel, EObject domainModel) {
		this(owner, viewModel, domainModel, null);
	}

	/**
	 * Initialize me with a feature to reveal.
	 *
	 * @param owner the reveal service that owns me
	 * @param viewModel the view model in which to reveal an object
	 * @param domainModel the object to reveal
	 * @param feature the specific feature of the object to reveal in the form
	 */
	@Inject
	RevealHelperImpl(EMFFormsRevealServiceImpl owner, VElement viewModel, EObject domainModel,
		EStructuralFeature feature) {
		super();

		this.owner = owner;
		this.viewModel = viewModel;
		this.domainModel = domainModel;
		this.feature = feature;
	}

	@Override
	public RevealStep drillDown(Object drillDownStep, Object childrenFunction) {
		Stream<? extends VElement> children;
		if (childrenFunction == null) {
			children = viewModel.eContents().stream()
				.filter(VElement.class::isInstance).map(VElement.class::cast);
		} else {
			final Iterable<?> evaluation = owner.evaluate(DrillDown.class, Iterable.class,
				viewModel, domainModel, feature, childrenFunction).orElse(Collections.emptyList());
			children = stream(evaluation.spliterator(), false)
				.filter(VElement.class::isInstance).map(VElement.class::cast);
		}

		return children
			.map(contained -> owner.reveal(domainModel, feature, contained))
			.sorted(RevealStep.preferredOrdering())
			.findFirst().map(nextStep -> RevealStep.drillDown(
				viewModel, owner.getViewContext(viewModel).getDomainModel(),
				nextStep, () -> owner.perform(new DrillDownRevealStep(
					owner, viewModel, domainModel, feature, nextStep, drillDownStep))))
			.orElse(RevealStep.FAILED);
	}

	@Override
	public RevealStep defer(Object deferredStep) {
		return new DeferredRevealStep(owner, viewModel, domainModel,
			feature, deferredStep);
	}

	@Override
	public RevealStep masterDetail(Object masterStep, Object masterFunction) {
		return new MasterDetailRevealStep(owner, viewModel, domainModel,
			feature, masterFunction, masterStep);
	}

}
