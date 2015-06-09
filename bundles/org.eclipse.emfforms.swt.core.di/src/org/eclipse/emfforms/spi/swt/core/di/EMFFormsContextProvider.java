/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.di;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * A {@link EMFFormsContextProvider} is a {@link ViewModelService} that provides and {@link IEclipseContext}.
 *
 * @author Lucas Koehler
 *
 */
public interface EMFFormsContextProvider extends ViewModelService {

	/**
	 * Returns the {@link IEclipseContext}.
	 *
	 * @return The {@link IEclipseContext}
	 */
	IEclipseContext getContext();

	/**
	 * Sets the {@link IEclipseContext}.
	 *
	 * @param eclipseContext The {@link IEclipseContext}
	 */
	void setContext(IEclipseContext eclipseContext);
}
