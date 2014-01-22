/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIUndoLastOperationController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore UndoLastOperation Handler delegating to the EMFStore {@link UIUndoLastOperationController}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class UndoLastOperationHelper {
	private UndoLastOperationHelper() {

	}

	/**
	 * Undo the last change on an {@link InternalProject}. Delegates to {@link UIUndoLastOperationController}.
	 * 
	 * @param project the {@link InternalProject}
	 * @param shell the {@link Shell}
	 */
	public static void undo(InternalProject project, Shell shell) {
		if (project == null) {
			return;
		}
		final ESLocalProject projectSpace = EMFStoreProvider.INSTANCE.getProjectSpace(project);
		if (projectSpace != null) {
			// TODO EMFStore Constructor is missing
			new UIUndoLastOperationController(shell, projectSpace).execute();
		}
	}
}
