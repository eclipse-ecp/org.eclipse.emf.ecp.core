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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIShowHistoryController;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore ShowHistory Helper delegating to the EMFStore {@link UIShowHistoryController}.
 *
 * @author Eugen Neufeld
 *
 */
public final class ShowHistoryViewHelper {
	private ShowHistoryViewHelper() {
	}

	/**
	 * Shows an HistoryView based on an {@link Object}. Delegates to {@link UIShowHistoryController}.
	 *
	 * @param object the {@link Object}
	 * @param shell the {@link Shell}
	 */
	public static void showHistoryView(Object object, Shell shell) {
		if (object instanceof EObject) {
			showHistoryView((EObject) object, shell);
		} else if (object instanceof InternalProject) {
			showHistoryView((InternalProject) object, shell);
		}
	}

	private static void showHistoryView(InternalProject internalProject, Shell shell) {
		final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(internalProject);
		new UIShowHistoryController(shell, localProject).execute();
	}

	private static void showHistoryView(EObject object, Shell shell) {
		new UIShowHistoryController(shell, object).execute();
	}
}
