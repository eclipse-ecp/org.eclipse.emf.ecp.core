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
package org.eclipse.emf.ecp.ide.view.service;

/**
 * A Callback used to triger a reload of the model.
 * @author Eugen Neufeld
 *
 */
public interface ViewModelEditorCallback {

	/**
	 * Called by the framework to trigger a view model reload.
	 */
	void reloadViewModel();
}
