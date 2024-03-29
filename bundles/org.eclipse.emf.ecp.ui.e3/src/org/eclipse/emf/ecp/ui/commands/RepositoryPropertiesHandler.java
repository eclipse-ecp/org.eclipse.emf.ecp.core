/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Then handler for opening a non editable properties dialog on an {@link org.eclipse.emf.ecp.core.ECPProject}.
 *
 * @author Eugen Neufeld
 *
 */
public class RepositoryPropertiesHandler extends AbstractHandler {

	/** {@inheritDoc} **/
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (IStructuredSelection.class.isInstance(selection)) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ECPRepository.class.isInstance(ssel.getFirstElement())) {
				ECPHandlerHelper.openRepositoryProperties((ECPRepository) ssel.getFirstElement(), false,
					HandlerUtil.getActiveShell(event));
			}
		}
		return null;
	}

}
