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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.util.ViewSwitch;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.reveal.DrillDown;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.osgi.service.component.annotations.Component;

/**
 * A reveal provider for {@link VContainer}s and {@link VView}s.
 *
 * @since 1.22
 */
@Component(name = "containerRevealProvider")
public class ContainerRevealProvider implements EMFFormsRevealProvider {

	private final Double containerBid = 5.0;
	private final Double viewBid = 1.0;

	private final Switch<EList<? extends VElement>> childrenSwitch = new ViewSwitch<EList<? extends VElement>>() {
		@Override
		public EList<? extends VElement> caseView(VView object) {
			return object.getChildren();
		}

		@Override
		public EList<? extends VElement> caseContainer(VContainer object) {
			return object.getChildren();
		}

		@Override
		public EList<? extends VElement> defaultCase(EObject object) {
			return ECollections.emptyEList();
		}

		@DrillDown
		private Iterable<? extends VElement> drillDown(VElement element) {
			return childrenSwitch.doSwitch(element);
		}
	};

	/**
	 * I bid on the {@code element} if it is a {@link VContainer}
	 * or a {@link VView}.
	 *
	 * @param element the element to bid on
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VElement element) {
		return element instanceof VContainer ? containerBid
			: element instanceof VView ? viewBid
				: null;
	}

	/**
	 * Create an intermediate reveal step to drill down into a container {@code element}.
	 *
	 * @param element the container in which to drill down
	 * @param object the object to reveal
	 * @param helper the reveal helper for drill-down
	 * @return the drill-down reveal step
	 */
	@Create
	public RevealStep reveal(VElement element, EObject object, RevealHelper helper) {
		return helper.drillDown(this, childrenSwitch);
	}

	@Reveal
	private RevealStep drillDown(EObject object, @Renderer AbstractSWTRenderer<?> renderer,
		RevealStep nextStep) {

		return RevealStep.reveal(nextStep.getViewModel(), object, renderer::scrollToReveal);
	}

}
