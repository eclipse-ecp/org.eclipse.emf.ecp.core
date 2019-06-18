/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.vertical.fx;

import org.eclipse.emf.ecp.view.model.internal.fx.ContainerRendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class VerticalLayoutRendererFX extends ContainerRendererFX<VVerticalLayout> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public VerticalLayoutRendererFX(VVerticalLayout vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected Node renderNode(GridCellFX cell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getColumn() != 0) {
			return null;
		}

		return renderGrid();
	}

	@Override
	protected GridPane getGridPane() {
		final GridPane grid = new GridPane();
		grid.getStyleClass().add("vertical");
		return grid;
	}

}
