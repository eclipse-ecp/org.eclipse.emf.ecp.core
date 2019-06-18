/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.e4.emfstore;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.ShowHistoryViewHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to open the EMFStore History View.
 *
 * @see ShowHistoryViewHelper#showHistoryView(Object, Shell)
 * @author Eugen Neufeld
 */
public class ShowHistoryViewHandler {
	/**
	 * Called by the framework when handler is triggered.
	 *
	 * @param shell The current {@link Shell}
	 * @param object The currently selected {@link Object}
	 */
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Object object) {
		if (object != null) {
			ShowHistoryViewHelper.showHistoryView(object, shell);
		}
	}

}