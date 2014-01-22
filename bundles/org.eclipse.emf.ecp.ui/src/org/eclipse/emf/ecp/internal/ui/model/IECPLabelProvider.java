/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * A label provider with additional methods for providing
 * a {@link ECPModelContextProvider}.
 * 
 * @author emueller
 * 
 */
public interface IECPLabelProvider extends ILabelProvider {

	/**
	 * Returns the {@link ECPModelContextProvider}.
	 * 
	 * @return the model context provider
	 */
	ECPModelContextProvider getModelContextProvider();

	/**
	 * Sets the {@link ECPModelContextProvider} to be used by the label provider.
	 * 
	 * @param modelContextProvider
	 *            the model context provider to be set
	 */
	void setModelContextProvider(ECPModelContextProvider modelContextProvider);
}
