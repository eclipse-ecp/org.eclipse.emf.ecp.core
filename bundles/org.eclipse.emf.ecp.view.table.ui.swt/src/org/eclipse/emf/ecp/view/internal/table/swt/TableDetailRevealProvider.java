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
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.reveal.DrillUp;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.osgi.service.component.annotations.Component;

/**
 * A reveal provider for {@link VTableControl}s that reveals objects presented in the details
 * of some object in the table. This is a heuristic guess based on containment (recursively)
 * of the object to be revealed within some object in the table. Applications are advised
 * to provide higher bids to more reliably/appropriately reveal objects in these master-detail
 * configurations.
 *
 * @since 1.22
 */
@Component(name = "tableDetailRevealProvider")
public class TableDetailRevealProvider implements EMFFormsRevealProvider {

	private final Double featureBid = 11.0;
	private final Double tableBid = 1.0;

	/**
	 * I bid on the {@code element} if it is a {@link VTableControl} that has a row
	 * representing an {@link EObject} containing (recursively) the {@code object}
	 * to be revealed and the table control has a detail panel.
	 *
	 * @param tableControl the table control to bid on
	 * @param object the object to be revealed
	 * @param feature provider of the optional feature being revealed
	 * @param context the context in which the table is rendered
	 *
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VTableControl tableControl, EObject object,
		@Optional EStructuralFeature feature, ViewModelContext context) {

		return tableControl.getDetailEditing() == DetailEditing.WITH_PANEL
			&& containsRecursively(context, tableControl, object)
				? feature != null
					? featureBid // Expect that we can reveal this property of the object
					: tableBid
				: null;
	}

	private boolean containsRecursively(ViewModelContext context, VTableControl tableControl, EObject object) {
		boolean result;

		final IObservableList<?> list = getTableInput(context, tableControl);
		result = list.stream().filter(EObject.class::isInstance).map(EObject.class::cast)
			.anyMatch(ancestor -> EcoreUtil.isAncestor(ancestor, object));

		return result;
	}

	private IObservableList<?> getTableInput(ViewModelContext context, VTableControl tableControl) {
		IObservableList<?> result = Observables.emptyObservableList();

		try {
			result = context.getService(EMFFormsDatabinding.class)
				.getObservableList(tableControl.getDomainModelReference(), context.getDomainModel());
		} catch (final DatabindingFailedException e) {
			// The object cannot be in this table, then
		}

		return result;
	}

	/**
	 * Create a reveal step to drill down into the table's details.
	 *
	 * @param context the view model context in which to find a renderer for the table
	 * @param helper a helper for deferred revealing
	 * @param tableControl the table in which to drill down
	 * @param object the object to reveal
	 * @return the drill-down reveal step
	 */
	@Create
	public RevealStep reveal(ViewModelContext context, RevealHelper helper, VTableControl tableControl,
		EObject object) {

		RevealStep result = RevealStep.fail();

		if (containsRecursively(context, tableControl, object)) {
			// It's in this table. Defer the access to the renderer because
			// in a categorization it may not yet exist
			result = helper.masterDetail(this, this);
		}

		return result;
	}

	@DrillUp
	private EObject getParent(ViewModelContext context, VTableControl tableControl, EObject object) {
		EObject result = null;

		final IObservableList<?> list = getTableInput(context, tableControl);
		result = list.stream().filter(EObject.class::isInstance).map(EObject.class::cast)
			.filter(ancestor -> EcoreUtil.isAncestor(ancestor, object))
			.findAny().orElse(null);

		return result;
	}

	@Reveal
	private RevealStep revealMaster(@Renderer TableControlSWTRenderer renderer, EObject masterSelection) {
		final VTableControl tableControl = renderer.getVElement();
		return RevealStep.reveal(tableControl, masterSelection, () -> renderer.reveal(masterSelection));
	}

}
