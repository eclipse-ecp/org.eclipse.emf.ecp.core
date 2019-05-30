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
package org.eclipse.emf.ecp.ui.view.swt.reference;

import java.util.Collection;

import javax.inject.Named;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.jface.viewers.StructuredViewer;

/**
 * Strategy for provision of the selection composite to show in the object selection dialog.
 *
 * @since 1.22
 */
public interface SelectionCompositeStrategy {

	/**
	 * The {@linkplain Named name} of a {@linkplain BazaarContext bazaar context} variable
	 * providing the extent of objects from which to select, as a {@link Collection}.
	 */
	String EXTENT = "extent"; //$NON-NLS-1$

	/**
	 * The default strategy. Provides a selection table with just the label column.
	 */
	SelectionCompositeStrategy DEFAULT = (owner, reference, extent) -> CompositeFactory
		.getTableSelectionComposite(extent, reference.isMany());

	/**
	 * Query the viewer to present from which the user will select object(s) to reference.
	 *
	 * @param owner an existing object in which a reference feature is to have references added
	 * @param reference the reference of the {@code owner} to which objects are to be added
	 * @param extent the collection of objects from which the user may make a selection
	 *
	 * @return the selection viewer composite
	 */
	SelectionComposite<? extends StructuredViewer> getSelectionViewer(EObject owner, EReference reference,
		Collection<? extends EObject> extent);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for reference selection composite strategies. The
	 * {@link BazaarContext} includes a value {@link Named named} {@code "extent"}.
	 *
	 * @since 1.22
	 *
	 * @see SelectionCompositeStrategy#EXTENT
	 */
	public interface Provider extends Vendor<SelectionCompositeStrategy> {
		// Nothing to add to the superinterface
	}

}
