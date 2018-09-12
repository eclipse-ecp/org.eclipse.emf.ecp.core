/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.AbstractTableViewer;

/**
 * An action bar provider interface.
 *
 * @param <T> the concrete table viewer to use
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public interface ActionBarProvider<T extends AbstractTableViewer> {

	/**
	 * Returns the action bar instance (if any).
	 *
	 * @return the action bar
	 */
	Optional<ActionBar<T>> getActionBar();
}
