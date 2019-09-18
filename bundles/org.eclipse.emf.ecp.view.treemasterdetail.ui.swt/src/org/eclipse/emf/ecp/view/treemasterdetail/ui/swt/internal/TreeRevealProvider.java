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
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.reveal.DrillUp;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.eclipse.jface.viewers.TreePath;
import org.osgi.service.component.annotations.Component;

/**
 * A reveal provider for {@link VTreeMasterDetail} controls.
 *
 * @since 1.22
 */
@Component(name = "treeRevealProvider")
public class TreeRevealProvider implements EMFFormsRevealProvider {

	private final Double treeBid = 5.0;

	/**
	 * I bid on the {@code element} if it is a {@link VTreeMasterDetail}.
	 *
	 * @param treeControl the element to bid on
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VTreeMasterDetail treeControl) {
		return treeBid;
	}

	/**
	 * Create a terminal reveal step to drill down into a tree control.
	 *
	 * @param context the view model context in which to find a renderer for the tree
	 * @param helper a helper for master/detail reveal delegation
	 * @param treeControl the tree in which to drill down
	 * @param object the object to reveal
	 * @return the drill-down reveal step
	 */
	@Create
	public RevealStep reveal(ViewModelContext context, RevealHelper helper, VTreeMasterDetail treeControl,
		EObject object) {

		RevealStep result = RevealStep.fail();

		final EObject rootObject = context.getDomainModel();
		if (rootObject != null && EcoreUtil.isAncestor(rootObject, object)) {
			// It's in this tree. Defer the access to the renderer because
			// in a categorization it may not yet exist
			result = helper.masterDetail(this, this);
		}

		return result;
	}

	@DrillUp
	private EObject getParent(@Renderer TreeMasterDetailSWTRenderer renderer, EObject object) {
		TreePath path = renderer.getTreePathFor(object);
		while (!renderer.hasPath(path)) {
			path = path.getParentPath();
			if (path.equals(TreePath.EMPTY)) {
				// Give up
				return null;
			}
		}

		return (EObject) path.getLastSegment();
	}

	@Reveal
	private RevealStep revealMaster(@Renderer TreeMasterDetailSWTRenderer renderer, VElement masterView,
		EObject masterSelection) {

		return RevealStep.reveal(masterView, masterSelection, () -> renderer.reveal(masterSelection));
	}

}
