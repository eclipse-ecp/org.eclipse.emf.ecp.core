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
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

/**
 * An minimal interface for viewer actions.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public interface Action {

	/**
	 * The default prefix to be used for all actions implementing this interface.
	 */
	String PREFIX = "org.eclipse.emfforms.action."; //$NON-NLS-1$

	/**
	 * Return the ID for this action. Must be unique.
	 *
	 * @return the action ID
	 */
	String getId();

	/**
	 * Execute the action.
	 */
	void execute();

	/**
	 * Verify that this action can be executed given the current action context.
	 *
	 * @return true in case execute() can be called, false otherwise
	 */
	boolean canExecute();

}
