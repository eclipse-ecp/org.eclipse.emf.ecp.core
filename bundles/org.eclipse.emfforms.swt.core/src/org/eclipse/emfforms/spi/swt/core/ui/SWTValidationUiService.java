/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.swt.core.ui;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * Service implementations provide visualizations (icons and colors) for validation results.
 *
 * @author Lucas Koehler
 * @since 1.23
 *
 */
public interface SWTValidationUiService {

	/**
	 * Returns the validation icon matching the given Diagnostic, VElement
	 * and view model context, if applicable.
	 *
	 * @param diagnostic the {@link Diagnostic} defining the validation visualization
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	Image getValidationIcon(Diagnostic diagnostic, VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the validation icon matching the highest severity of the given VElement's diagnostic.
	 *
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	Image getValidationIcon(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the foreground color for a control with the given Diagnostic, VElement
	 * and view model context, if applicable.
	 *
	 * @param diagnostic the {@link Diagnostic} defining the validation visualization
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a foreground color
	 */
	Color getValidationForegroundColor(Diagnostic diagnostic, VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the foreground color for a control matching the highest severity of the given VElement's diagnostic.
	 *
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a foreground color
	 */
	Color getValidationForegroundColor(VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the background color for a control with the given Diagnostic, VElement
	 * and view model context, if applicable.
	 *
	 * @param diagnostic the {@link Diagnostic} defining the validation visualization
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a background color
	 */
	Color getValidationBackgroundColor(Diagnostic diagnostic, VElement vElement, ViewModelContext viewModelContext);

	/**
	 * Returns the background color for a control matching the highest severity of the given VElement's diagnostic.
	 *
	 * @param vElement The {@link VElement} that is being rendered
	 * @param viewModelContext The corresponding {@link ViewModelContext}
	 * @return the color to be used as a background color
	 */
	Color getValidationBackgroundColor(VElement vElement, ViewModelContext viewModelContext);
	// TODO more methods?
}
