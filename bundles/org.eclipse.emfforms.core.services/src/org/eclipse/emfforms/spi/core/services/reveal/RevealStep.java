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

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A step in the progressive reveal of an object.
 *
 * @since 1.22
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface RevealStep {

	/**
	 * The step indicating failure to reveal the object.
	 *
	 * @see RevealStepKind#FAILED
	 *
	 * @see #fail()
	 */
	RevealStep FAILED = new FailedStep();

	/**
	 * Query the type of reveal step that I am.
	 *
	 * @return my type
	 */
	RevealStepKind getType();

	/**
	 * Query whether I am a {@linkplain RevealStepKind#FAILED failed} reveal step.
	 *
	 * @return whether I represent failure to reveal the requested object
	 */
	default boolean isFailed() {
		return getType() == RevealStepKind.FAILED;
	}

	/**
	 * Query whether I am a {@linkplain RevealStepKind#INTERMEDIATE non-intermediate} reveal step.
	 *
	 * @return {@code true} if there is no further progress to be made in revealing;
	 *         {@code false}, otherwise
	 */
	default boolean isDone() {
		return getType() != RevealStepKind.INTERMEDIATE;
	}

	/**
	 * Query the view model element in which I reveal my {@linkplain #getDomainModel() domain model}.
	 *
	 * @return my view model scope, or {@code null} if {@linkplain #isFailed() I am failed}
	 */
	VElement getViewModel();

	/**
	 * Query the domain object that I reveal in my {@linkplain #getViewModel() view model element}.
	 *
	 * @return my domain model scope, or {@code null} if {@linkplain #isFailed() I am failed}
	 */
	EObject getDomainModel();

	/**
	 * Query the feature that I reveal, if any. If I am not a {@link RevealStepKind#TERMINAL terminal step},
	 * then I should not reveal any feature.
	 *
	 * @return the feature that I reveal, or {@code null} if none or if {@linkplain #isFailed() I am failed}
	 */
	EStructuralFeature getFeature();

	/**
	 * Drills down into me to reveal the next step in the nested reveal.
	 *
	 * @return the next step in the progressive reveal, or {@code null} if I am the
	 *         {@linkplain RevealStepKind#TERMINAL terminal step}
	 *
	 * @throws IllegalStateException if {@linkplain #isFailed() I am failed}
	 */
	RevealStep drillDown();

	/**
	 * Reveal my {@linkplain #getDomainModel() domain object} in my
	 * {@linkplain #getViewModel() view model element}.
	 *
	 * @throws IllegalStateException if {@linkplain #isFailed() I am failed}
	 */
	void reveal();

	/**
	 * Invoke an {@code action} on me if I am {@linkplain #isFailed() not failed}.
	 *
	 * @param action an action to invoke
	 */
	default void ifPresent(Consumer<? super RevealStep> action) {
		if (!isFailed()) {
			action.accept(this);
		}
	}

	/**
	 * Create an intermediate step that drills down into another step.
	 *
	 * @param viewModel the view model element that is rendered and revealed
	 * @param domainModel the domain model object that is revealed in that view
	 * @param nextStep the next step to drill down into in the reveal operation
	 * @return the intermediate reveal step
	 */
	static RevealStep drillDown(VElement viewModel, EObject domainModel, RevealStep nextStep) {
		return drillDown(viewModel, domainModel, nextStep, null);
	}

	/**
	 * Create an intermediate step that reveals some kind of container and drills
	 * down into another step.
	 *
	 * @param viewModel the view model element that is rendered and revealed
	 * @param domainModel the domain model object that is revealed in that view
	 * @param nextStep the next step to drill down into in the reveal operation
	 * @param revealAction a rendering-specific action that actually reveals the rendered
	 *            container element in the UI
	 * @return the intermediate reveal step
	 */
	static RevealStep drillDown(VElement viewModel, EObject domainModel, RevealStep nextStep, Runnable revealAction) {
		// Short-circuit to failure
		return nextStep.isFailed() ? nextStep : new IntermediateStep(viewModel, domainModel, nextStep, revealAction);
	}

	/**
	 * Create a terminal step that reveals the object in the editor UI.
	 *
	 * @param viewModel the view model element that is rendered and revealed
	 * @param domainModel the domain model object that is revealed in that view
	 * @param revealAction a rendering-specific action that actually reveals the rendered
	 *            element that presents the object in the UI
	 * @return the terminal reveal step
	 */
	static RevealStep reveal(VElement viewModel, EObject domainModel, Runnable revealAction) {
		return reveal(viewModel, domainModel, null, revealAction);
	}

	/**
	 * Create a terminal step that reveals a {@code feature} of the object in the editor UI.
	 *
	 * @param viewModel the view model element that is rendered and revealed
	 * @param domainModel the domain model object that is revealed in that view
	 * @param feature the feature of the domain model object that I specfically revealed
	 * @param revealAction a rendering-specific action that actually reveals the rendered
	 *            element that presents the object in the UI
	 * @return the terminal reveal step
	 */
	static RevealStep reveal(VElement viewModel, EObject domainModel, EStructuralFeature feature,
		Runnable revealAction) {

		return new TerminalStep(viewModel, domainModel, feature, revealAction);
	}

	/**
	 * Obtain a reveal failure step that signals absence of any UI element that can be
	 * activated to reveal the object.
	 *
	 * @return the failed reveal step
	 *
	 * @see #FAILED
	 */
	static RevealStep fail() {
		return FAILED;
	}

	/**
	 * Obtain a predicate that matches reveal steps of a particular {@code type}.
	 *
	 * @param type the type of step to match
	 * @return the predicate
	 *
	 * @see #getType()
	 */
	static Predicate<RevealStep> is(RevealStepKind type) {
		return step -> step.getType() == type;
	}

	/**
	 * Obtain a comparator that sorts reveal steps in preferential order by
	 * {@link #getType type}, with the {@link RevealStepKind#TERMINAL} the most
	 * preferred and {@link RevealStepKind#FAILED} the least preferred.
	 *
	 * @return the ordering
	 *
	 * @see #getType()
	 */
	static Comparator<RevealStep> preferredOrdering() {
		return Comparator.comparing(RevealStep::getType);
	}

}
