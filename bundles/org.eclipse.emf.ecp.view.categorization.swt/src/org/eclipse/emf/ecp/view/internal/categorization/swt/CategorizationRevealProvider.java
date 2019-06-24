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
package org.eclipse.emf.ecp.view.internal.categorization.swt;

import static org.eclipse.emf.common.util.ECollections.singletonEList;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.categorization.model.util.CategorizationSwitch;
import org.eclipse.emf.ecp.view.spi.categorization.swt.AbstractJFaceTreeRenderer;
import org.eclipse.emf.ecp.view.spi.categorization.swt.AbstractSWTTabRenderer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
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
 * A reveal provider for {@link VCategorizationElement}s and
 * {@link VCategorizableElement}s.
 *
 * @since 1.22
 */
@Component(name = "categorizationRevealProvider")
public class CategorizationRevealProvider implements EMFFormsRevealProvider {

	private final Double containerBid = 5.0;
	private final Double leafBid = 1.0;

	private final Switch<Double> bidSwitch = new CategorizationSwitch<Double>() {
		@Override
		public Double caseCategorizationElement(VCategorizationElement object) {
			return containerBid;
		}

		@Override
		public Double caseCategorizableElement(VCategorizableElement object) {
			return containerBid;
		}

		@Override
		public Double caseCategory(VCategory object) {
			return leafBid;
		}
	};

	private final Switch<EList<? extends VElement>> childrenSwitch = new CategorizationSwitch<EList<? extends VElement>>() {
		@Override
		public EList<? extends VElement> caseCategory(VCategory object) {
			return object.getComposite() != null ? singletonEList(object.getComposite()) : null;
		}

		@Override
		public EList<? extends VElement> caseCategorization(VCategorization object) {
			return object.getCategorizations();
		}

		@Override
		public EList<? extends VElement> caseCategorizationElement(VCategorizationElement object) {
			return object.getCategorizations();
		}

		@Override
		public EList<? extends VElement> defaultCase(EObject object) {
			return ECollections.emptyEList();
		}

		@DrillDown
		Iterable<? extends VElement> drillDown(VElement element) {
			return doSwitch(element);
		}
	};

	/**
	 * I bid on the {@code element} if it is a {@link VCategorizationElement} or some kind
	 * of {@link VCategorizableElement}.
	 *
	 * @param element the element to bid on
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VElement element) {
		return bidSwitch.doSwitch(element);
	}

	/**
	 * Create an intermediate reveal step to drill down into a categorization {@code element}.
	 *
	 * @param element the categorization element in which to drill down
	 * @param object the object to reveal
	 * @param helper the reveal helper for drill-down
	 * @return the drill-down reveal step
	 */
	@Create
	public RevealStep reveal(VElement element, EObject object, RevealHelper helper) {
		return helper.drillDown(this, childrenSwitch);
	}

	@Reveal
	private RevealStep drillDown(VElement element, EObject object,
		@Renderer AbstractSWTRenderer<? extends VAbstractCategorization> renderer,
		RevealStep nextStep) {

		return RevealStep.reveal(element, object, () -> reveal(renderer, nextStep.getViewModel()));
	}

	/**
	 * Reveal the rendering of a {@code categorization} in its parent {@code element}
	 * rendering in the given editor {@code context}.
	 *
	 * @param renderer the parent categorization renderer
	 * @param categorization the categorization to reveal
	 */
	void reveal(AbstractSWTRenderer<? extends VAbstractCategorization> renderer, VElement categorization) {
		if (!(categorization instanceof VAbstractCategorization)) {
			return;
		}

		final VAbstractCategorization abstractCategorization = (VAbstractCategorization) categorization;

		if (renderer instanceof AbstractSWTTabRenderer) {
			final AbstractSWTTabRenderer<?> tabRenderer = (AbstractSWTTabRenderer<?>) renderer;
			tabRenderer.showCategorization(abstractCategorization);
		} else if (renderer instanceof AbstractJFaceTreeRenderer<?>) {
			final AbstractJFaceTreeRenderer<?> treeRenderer = (AbstractJFaceTreeRenderer<?>) renderer;
			treeRenderer.showCategorization(abstractCategorization);
		}
	}

}
