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
package org.eclipse.emf.ecp.view.spi.provider;

import static java.util.Collections.emptySet;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * A specialization of the {@link EMFFormsViewService} protocol that makes explicit
 * the support for filtering views by matching {@linkplain VViewModelProperties properties}
 * requested by the client. Thus it understands how to interact with {@link IFilteredViewProvider}s
 * that are {@linkplain EMFFormsViewService #addProvider(IViewProvider) added to it}.
 *
 * @since 1.22
 *
 * @see IFilteredViewProvider
 */
public interface EMFFormsFilteredViewService extends EMFFormsViewService {

	/**
	 * Retrieve a {@link VView} for a domain model {@code object} without any required
	 * filter keys.
	 *
	 * @param object the domain model object for which a view is to be requested
	 * @param properties the {@link VViewModelProperties properties} for providing the view, that
	 *            may or may not include matching filters
	 *
	 * @return a view model for the given domain model {@code object} or {@code null} if no
	 *         suitable provider could be found to provide one
	 */
	@Override
	default VView getView(EObject object, VViewModelProperties properties) {
		return getView(object, properties, emptySet());
	}

	/**
	 * Retrieve a {@link VView} for a domain model {@code object} from the most confident of my
	 * {@linkplain EMFFormsViewService#addProvider(IViewProvider) registered view providers}.
	 *
	 * @param object the domain model object for which a view is to be requested
	 * @param properties the {@link VViewModelProperties properties} for providing the view, that
	 *            may or may not include matching filters
	 * @param requiredKeys a subset (possibly empty) of the keys in the {@code properties} that
	 *            must be matched by any view model that I would provide. If any of these keys does not match
	 *            a view model, then that view model must not be provided. Otherwise, it may just be less
	 *            preferred than some other view model that does match
	 *
	 * @return a view model for the given domain model {@code object} or {@code null} if no
	 *         suitable provider could be found to provide one
	 */
	VView getView(EObject object, VViewModelProperties properties, Collection<String> requiredKeys);

}
