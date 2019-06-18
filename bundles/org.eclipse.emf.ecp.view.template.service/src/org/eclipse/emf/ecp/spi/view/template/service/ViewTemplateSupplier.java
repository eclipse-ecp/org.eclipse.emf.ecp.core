/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.template.service;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;

/**
 * A {@link ViewTemplateSupplier} collects and provides any number of {@link VTViewTemplate VTViewTemplates}.
 * <p>
 * This interface may be implemented by clients to implement custom logic to provide VTViewTemplates and/or
 * {@link VTStyleProperty VTStyleProperties} for given {@link VElement VElements}.
 *
 * @author Lucas Koehler
 * @since 1.18
 */
public interface ViewTemplateSupplier {

	/**
	 * Return all {@link VTStyleProperty StyleProperties} which are applicable to the provided {@link VElement}.
	 * The keys of the map are the {@link VTStyleProperty StyleProperties} and the values the corresponding
	 * specificities.
	 * <p>
	 * The returned values might be cached by the caller. Therefore, for the same arguments, the
	 * {@link ViewTemplateSupplier} is expected to return the same map of {@link VTStyleProperty StyleProperties} and
	 * specificities.
	 *
	 * @param vElement the {@link VElement} to get the {@link VTStyleProperty StyleProperties} for
	 * @param viewModelContext the {@link ViewModelContext} currently in use
	 * @return the map of all {@link VTStyleProperty StyleProperties} and their specificities for the given
	 *         {@link VElement} or an empty map
	 */
	Map<VTStyleProperty, Double> getStyleProperties(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns a copy of the view templates known to this supplier.
	 * Modifications on the copies will not influence any future calls on the {@link ViewTemplateSupplier}
	 *
	 * @return a copy of the {@link VTViewTemplate VTViewTemplates} or an empty set
	 */
	Set<VTViewTemplate> getViewTemplates();
}
