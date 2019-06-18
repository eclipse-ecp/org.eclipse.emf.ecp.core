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
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Add/Remove handler for the View model projects nature.
 */
public class AddRemoveProjectNatureHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		String natureID = event.getParameter("natureID");
		if (natureID == null) {
			// Default to view model nature
			natureID = ViewModelNature.NATURE_ID;
		}

		//
		if (selection instanceof IStructuredSelection) {
			for (final Iterator<?> it = ((IStructuredSelection) selection).iterator(); it
				.hasNext();) {
				final Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null) {
					try {
						ProjectNature.toggleNature(project, natureID);
					} catch (final CoreException e) {
						Activator.log("Failed to toggle nature", e);
						throw new ExecutionException("Failed to toggle nature",
							e);
					}
				}
			}
		}

		return null;
	}

}