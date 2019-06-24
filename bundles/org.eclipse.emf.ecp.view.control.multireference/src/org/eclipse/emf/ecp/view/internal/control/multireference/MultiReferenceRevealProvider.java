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
package org.eclipse.emf.ecp.view.internal.control.multireference;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.model.common.di.annotations.Renderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
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
 * A reveal provider for multi-reference controls that show contained objects
 * (not just cross-referenced).
 */
@Component(name = "multiReferenceRevealProvider")
public class MultiReferenceRevealProvider implements EMFFormsRevealProvider {

	private final Double basicBid = 1.0;

	/**
	 * I bid on the {@code element} if it is a simple {@link VControl} specified by
	 * a DMR that resolves to a multi-valued containment reference.
	 *
	 * @param context the view model context in which to resolve the DMR
	 * @param control the control to bid on
	 * @param databinding the databinding service in which to resolve the DMR
	 * @return my bid
	 * @throws DatabindingFailedException on failure to resolve the DMR
	 */
	@Bid
	public Double canReveal(ViewModelContext context, VControl control, EMFFormsDatabinding databinding)
		throws DatabindingFailedException {

		Double result = null;

		EReference resolvedReference = null;

		// Not a specialization like TableControl
		if (control.eClass() == VViewPackage.Literals.CONTROL) {
			final IValueProperty<?, ?> property = databinding.getValueProperty(control.getDomainModelReference(),
				context.getDomainModel());
			final Object propertyType = property.getValueType();
			if (propertyType instanceof EReference) {
				resolvedReference = (EReference) propertyType;
			}
		}

		if (resolvedReference != null && resolvedReference.isMany() && resolvedReference.isContainment()) {
			result = basicBid;
		}

		return result;
	}

	/**
	 * Create a terminal reveal step to select and reveal the {@code object} in the
	 * rendered {@code element}.
	 *
	 * @param context the view model context in which to find a renderer for the tree
	 * @param helper a helper for deferred revealing
	 * @param control the multi-reference control view model
	 * @param object the object to reveal
	 * @param databinding the databinding service in which to resolve the DMR
	 * @return the drill-down reveal step
	 * @throws DatabindingFailedException on failure to resolve the DMR
	 */
	@Create
	public RevealStep reveal(ViewModelContext context, RevealHelper helper, VControl control, EObject object,
		EMFFormsDatabinding databinding) throws DatabindingFailedException {

		RevealStep result = RevealStep.FAILED;

		final IObservableList<?> list = databinding.getObservableList(control.getDomainModelReference(),
			context.getDomainModel());
		if (list != null && list.contains(object)) {
			result = helper.defer(this);
		}

		return result;
	}

	@Reveal
	private RevealStep doReveal(@Renderer MultiReferenceSWTRenderer renderer, EObject object) {
		final VControl control = renderer.getVElement();
		return RevealStep.reveal(control, object, () -> renderer.reveal(object));
	}

}
