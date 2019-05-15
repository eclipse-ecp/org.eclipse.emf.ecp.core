/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.model;

import java.util.Set;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This interface defines a service capable of providing an instance of the {@link VTViewTemplate}.
 *
 * @author Eugen Neufeld
 *
 */
public interface VTViewTemplateProvider {

	/**
	 * Return all {@link VTStyleProperty StyleProperties} which are applicable to the provided {@link VElement}.
	 * The returned values might be cached by the caller. Therefore on the same arguments, the
	 * {@link VTViewTemplateProvider} is expected to return the same set of VTStyleProperty
	 *
	 * @param vElement the {@link VElement} to get the {@link VTStyleProperty StyleProperties} for
	 * @param viewModelContext the {@link ViewModelContext} currently in use
	 * @return the collection of all {@link VTStyleProperty StyleProperties} which are applicable to the provided
	 *         {@link VElement} or an empty set
	 */
	Set<VTStyleProperty> getStyleProperties(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns a copy of the view template.
	 * Modifications on this copy will not influence any future calls on the {@link VTViewTemplateProvider}
	 *
	 * @return a copy of the {@link VTViewTemplate}
	 * @deprecated Use {@link #getStyleProperties(VElement, ViewModelContext)} instead of using the
	 *             {@link VTViewTemplate} directly
	 */
	@Deprecated
	VTViewTemplate getViewTemplate();

	/**
	 * @return whether the {@link VTViewTemplateProvider} provides a {@link VTControlValidationTemplate}
	 * @deprecated The usage of {@link VTControlValidationTemplate VTControlValidationTemplates} is deprecated. Use
	 *             {@link org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty
	 *             VTValidationStyleProperties} instead.
	 */
	@Deprecated
	boolean hasControlValidationTemplate();
}
