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

import java.util.Set;

/**
 * This class is used to mark an result row of an renderer. Thus all controls should be displayed in one row.
 *
 * @param <T> the type this {@link RenderingResultRow} works on
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@Deprecated
public interface RenderingResultRow<T> {

	/**
	 * The main control of the result row. This method is here for legacy.
	 *
	 * @return the Control that is most important in this row
	 *
	 */
	@Deprecated
	T getMainControl();

	/**
	 * Return all Controls which are held by this RenderingResultRow.
	 *
	 * @return the set of controls
	 */
	Set<T> getControls();
}
