/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.data;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * This service is used by renderers to set data on SWT controls.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public interface EMFFormsSWTDataService {

	/**
	 * Return an ID for the given element.
	 *
	 * @param element the {@link VElement}
	 * @param viewModelContext the {@link ViewModelContext}
	 * @return the id
	 */
	String getId(VElement element, ViewModelContext viewModelContext);

}
