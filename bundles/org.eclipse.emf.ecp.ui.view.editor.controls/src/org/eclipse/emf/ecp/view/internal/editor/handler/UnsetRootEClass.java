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
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Eugen
 *
 */
public class UnsetRootEClass extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof VView)) {
			return null;
		}

		final VView view = (VView) selection;
		view.setRootEClass(null);

		ECPUtil.getECPProjectManager().getProject(view).saveContents();

		return null;
	}

}
