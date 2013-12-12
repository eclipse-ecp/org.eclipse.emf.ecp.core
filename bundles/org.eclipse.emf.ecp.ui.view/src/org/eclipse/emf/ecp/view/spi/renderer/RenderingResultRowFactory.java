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
package org.eclipse.emf.ecp.view.spi.renderer;

/**
 * A Factory for creating RenderingResultRow. By providing an own implementation the layout can be extended.
 * 
 * @param <CONTROL> the type of the control this {@link RenderingResultRowFactory} works on
 * @author Eugen Neufeld
 * 
 */
public interface RenderingResultRowFactory<CONTROL> {
	/**
	 * Creates a {@link RenderingResultRow} from a list of controls. If the Factory can't handle the provided amount of
	 * controls it must throw an {@link IllegalArgumentException}.
	 * 
	 * @param controls a list of Controls to put into one row
	 * @return a {@link RenderingResultRow} or throws a {@link IllegalArgumentException}
	 */
	RenderingResultRow<CONTROL> createRenderingResultRow(CONTROL... controls);
}
