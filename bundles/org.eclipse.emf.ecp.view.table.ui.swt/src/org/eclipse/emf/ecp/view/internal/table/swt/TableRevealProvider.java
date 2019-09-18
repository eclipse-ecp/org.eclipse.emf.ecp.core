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

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.Reveal;
import org.eclipse.emfforms.spi.core.services.reveal.RevealHelper;
import org.eclipse.emfforms.spi.core.services.reveal.RevealStep;
import org.osgi.service.component.annotations.Component;

/**
 * A reveal provider for {@link VTableControl}s that reveals objects contained (as rows)
 * in the table.
 *
 * @since 1.22
 */
@Component(name = "tableRevealProvider")
public class TableRevealProvider implements EMFFormsRevealProvider {

	private final Double tableBid = 5.0;

	/**
	 * I bid on the {@code element} if it is a {@link VTableControl} that has a row
	 * representing the {@code object} to be revealed.
	 *
	 * @param tableControl the table control to bid on
	 * @param object the object to be revealed
	 * @param context the context in which the table is rendered
	 *
	 * @return my bid
	 */
	@Bid
	public Double canReveal(VTableControl tableControl, EObject object, ViewModelContext context) {
		return contains(context, tableControl, object) ? tableBid : null;
	}

	private boolean contains(ViewModelContext context, VTableControl tableControl, EObject object) {
		boolean result = false;

		try {
			final IObservableList<?> list = context.getService(EMFFormsDatabinding.class)
				.getObservableList(tableControl.getDomainModelReference(), context.getDomainModel());
			result = list.contains(object);
		} catch (final DatabindingFailedException e) {
			// The object cannot be in this table, then
		}

		return result;
	}

	/**
	 * Create a terminal reveal step to drill down into a table control.
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

		if (contains(context, tableControl, object)) {
			// It's in this table. Defer the access to the renderer because
			// in a categorization it may not yet exist
			result = helper.defer(this);
		}

		return result;
	}

	@Reveal
	private RevealStep doReveal(@Renderer TableControlSWTRenderer renderer, EObject object) {
		final VTableControl tableControl = renderer.getVElement();
		return RevealStep.reveal(tableControl, object, () -> renderer.reveal(object));
	}

}
