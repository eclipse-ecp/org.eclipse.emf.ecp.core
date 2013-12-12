/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt;

import java.util.Map;

import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Interface for contributing custom SWT renderers.
 * 
 * @author emuller
 * 
 */
public interface CustomSWTRenderer {

	/**
	 * Returns a map containing a mapping that specifies which {@link AbstractSWTRenderer} should
	 * be used for a certain {@link VElement}.
	 * 
	 * @return a map associating {@link VElement}s with {@link AbstractSWTRenderer AbstractSWTRenderers}
	 */
	Map<Class<? extends VElement>, AbstractSWTRenderer<? extends VElement>> getCustomRenderers();
}
