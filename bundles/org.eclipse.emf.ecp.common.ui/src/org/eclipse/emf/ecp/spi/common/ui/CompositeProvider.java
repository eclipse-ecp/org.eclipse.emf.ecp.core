/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.spi.common.ui;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public interface CompositeProvider {
	/**
	 * This method creates a UI bundled into a {@link Composite} that can be used anywhere.
	 *
	 * @param parent the parent {@link Composite}
	 * @return the created {@link Composite}
	 */
	Composite createUI(Composite parent);

	/**
	 * Disposes this composite provider.
	 */
	void dispose();
}
