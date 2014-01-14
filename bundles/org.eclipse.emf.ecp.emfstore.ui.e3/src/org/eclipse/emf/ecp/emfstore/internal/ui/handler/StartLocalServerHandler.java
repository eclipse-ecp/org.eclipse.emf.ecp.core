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
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * A handler allowing to start a local EMFStore server. The call is delegated to {@link StartLocalServerHelper}.
 * 
 * @author Eugen Neufeld
 * 
 */
public class StartLocalServerHandler extends AbstractHandler {

	/** {@inheritDoc} **/
	public Object execute(ExecutionEvent event) throws ExecutionException {
		StartLocalServerHelper.startLocalServer();
		return null;
	}

}
