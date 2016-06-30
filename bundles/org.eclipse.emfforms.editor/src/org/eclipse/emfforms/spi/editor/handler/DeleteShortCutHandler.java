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
package org.eclipse.emfforms.spi.editor.handler;

import java.util.List;

/**
 * This service will be invoked when the delete shortcut was pressed in the
 * {@link org.eclipse.emfforms.spi.editor.GenericEditor GenericEditor}.
 *
 * @author Johannes Faltermeier
 */
public interface DeleteShortCutHandler {

	/**
	 * Performs the deletion.
	 *
	 * @param toDelete the objects to delete
	 */
	void handleDeleteShortcut(List<Object> toDelete);
}
