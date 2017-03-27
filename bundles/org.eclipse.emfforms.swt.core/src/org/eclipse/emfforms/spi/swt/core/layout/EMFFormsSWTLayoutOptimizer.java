/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
