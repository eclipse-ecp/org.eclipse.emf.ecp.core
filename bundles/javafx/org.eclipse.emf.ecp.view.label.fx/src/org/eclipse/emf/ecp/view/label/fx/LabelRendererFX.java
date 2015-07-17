/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.label.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;

import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFXFactory;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFX;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

/**
 * @author Lucas Koehler
 *
 */
public class LabelRendererFX extends RendererFX<VLabel> {

	private GridDescriptionFX gridDescription;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.internal.fx.RendererFX#getGridDescription()
	 */
	@Override
	public GridDescriptionFX getGridDescription() {
		if (gridDescription == null) {
			gridDescription = GridDescriptionFXFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return gridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.internal.fx.RendererFX#renderNode(org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX)
	 */
	@Override
	protected Node renderNode(GridCellFX cell) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Label label = new Label();
		if (getVElement().getName() != null) {
			label.setText(getVElement().getName());
		}
		return label;
	}
}
