/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.model;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This interface defines a service cappable of providing an instance of the {@link VTViewTemplate}.
 *
 * @author Eugen Neufeld
 *
 */
public interface VTViewTemplateProvider {

	/**
	 * Returns the view template.
	 *
	 * @return the {@link VTViewTemplate}
	 */
	VTViewTemplate getViewTemplate();

	/**
	 * Return all {@link VTStyleProperty StyleProperties} which are applicable to the provided {@link VElement}.
	 *
	 * @param vElement the {@link VElement} to get the {@link VTStyleProperty StyleProperties} for
	 * @param viewModelContext the {@link ViewModelContext} currently in use
	 * @return the collection of all {@link VTStyleProperty StyleProperties} which are applicable to the provided
	 *         {@link VElement} or an empty set
	 */
	Set<VTStyleProperty> getStyleProperties(VElement vElement, ViewModelContext viewModelContext);
}
