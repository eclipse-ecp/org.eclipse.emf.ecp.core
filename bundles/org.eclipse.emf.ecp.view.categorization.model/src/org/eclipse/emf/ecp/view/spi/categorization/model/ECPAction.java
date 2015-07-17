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
package org.eclipse.emf.ecp.view.spi.categorization.model;

/**
 * This interface defines an ECPAction. Concrete implementations of this class can be added as actions to the view model
 * items.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPAction {

	/**
	 * Method which is called when the action is started.
	 */
	void execute();
}