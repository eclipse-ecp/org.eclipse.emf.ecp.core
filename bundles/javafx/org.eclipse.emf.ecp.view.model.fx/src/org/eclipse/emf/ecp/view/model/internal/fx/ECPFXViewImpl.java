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
package org.eclipse.emf.ecp.view.model.internal.fx;

import javafx.scene.Node;

import org.eclipse.emf.ecp.view.model.fx.ECPFXView;

/**
 * @author Jonas
 *
 */
public class ECPFXViewImpl implements ECPFXView {

	private final Node node;

	/**
	 * @param vBox
	 */
	public ECPFXViewImpl(Node node) {
		this.node = node;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.view.fx.ECPFXView#getFXScene()
	 */
	@Override
	public Node getFXNode() {
		return node;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.view.fx.ECPFXView#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
