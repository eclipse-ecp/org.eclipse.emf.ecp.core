/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
