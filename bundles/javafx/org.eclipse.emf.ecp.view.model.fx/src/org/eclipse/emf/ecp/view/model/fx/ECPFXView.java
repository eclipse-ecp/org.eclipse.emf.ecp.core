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
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.fx;

import javafx.scene.Node;

/**
 * @author Jonas
 *
 */
public interface ECPFXView {

	/**
	 * @return the root rendering result of the view
	 */
	Node getFXNode();

	/**
	 * Disposes the view.
	 */
	void dispose();

}
