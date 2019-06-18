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
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.view.service;

/**
 * A Callback used to triger a reload of the model.
 *
 * @author Eugen Neufeld
 *
 */
public interface ViewModelEditorCallback {

	/**
	 * Called by the framework to trigger a view model reload.
	 */
	void reloadViewModel();

	/**
	 * Called by the framework to signal that the view model's resource has changed since the view model was last
	 * loaded.
	 */
	void signalEcoreOutOfSync();
}
