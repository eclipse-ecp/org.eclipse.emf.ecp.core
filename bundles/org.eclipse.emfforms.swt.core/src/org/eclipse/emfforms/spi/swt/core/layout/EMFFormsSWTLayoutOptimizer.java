/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.spi.swt.core.layout;

import org.eclipse.swt.widgets.Composite;

/**
 * EMFFormsSWTLayoutOptimizer allows to optimize the layout process. This is used by the EMFFormsSWTLayoutUtil.
 *
 * @author Eugen Neufeld
 * @since 1.12
 *
 */
public interface EMFFormsSWTLayoutOptimizer {

	/**
	 * Layout the provided parent composite.
	 *
	 * @param parent The Composite to layout
	 */
	void layout(Composite parent);
}
