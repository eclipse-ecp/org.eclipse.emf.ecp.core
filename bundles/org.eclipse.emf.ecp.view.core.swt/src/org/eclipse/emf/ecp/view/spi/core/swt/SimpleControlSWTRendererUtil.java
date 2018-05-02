/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

/**
 * Holds some Util methods for the {@link SimpleControlSWTRenderer} which may be reused by renderers which cannot
 * inherit from {@link SimpleControlSWTRenderer} but want to reuse functionality.
 *
 * @author Johannes Faltermeier
 * @since 1.17
 *
 */
public final class SimpleControlSWTRendererUtil {

	private static final Point VALIDATION_PREFERRED_SIZE = new Point(16, 17);

	private SimpleControlSWTRendererUtil() {
		/* util */
	}

	/**
	 * Whether to create a label cell on the left in the same row.
	 *
	 * @param control the rendered {@link VControl}
	 * @param reportService the {@link ReportService}
	 * @param rendererName the name of the current renderer for logging
	 * @return <code>true</code> if label grid cell should be created, <code>false</code> otherwise
	 */
	public static boolean showLabel(VControl control, ReportService reportService, String rendererName) {
		switch (control.getLabelAlignment()) {
		case DEFAULT:
		case LEFT:
			return true;
		case NONE:
			return false;
		default:
			reportService.report(new AbstractReport(MessageFormat.format(
				"Label alignment {0} is not supported by renderer {1}. Label alignment set to default.", //$NON-NLS-1$
				control.getLabelAlignment().getLiteral(), rendererName), IStatus.INFO));
			control.setLabelAlignment(LabelAlignment.DEFAULT);
			return true;
		}
	}

	/**
	 * Creates the cell for the label.
	 *
	 * @param column the column index
	 * @param renderer the renderer
	 * @param labelWidth the width for the label
	 * @return the grid cell
	 */
	public static SWTGridCell createLabelCell(
		int column,
		AbstractSWTRenderer<? extends VElement> renderer,
		Optional<Integer> labelWidth) {

		final SWTGridCell labelCell = new SWTGridCell(0, column, renderer);
		labelCell.setHorizontalGrab(false);
		labelCell.setVerticalGrab(false);
		labelCell.setHorizontalFill(false);
		labelCell.setHorizontalAlignment(SWTGridCell.Alignment.BEGINNING);
		labelCell.setVerticalFill(false);
		labelCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		labelCell.setRenderer(renderer);
		if (labelWidth.isPresent()) {
			labelCell.setPreferredSize(labelWidth.get(), SWT.DEFAULT);
		}
		return labelCell;
	}

	/**
	 * Creates the validation cell.
	 *
	 * @param column the column index.
	 * @param renderer the renderer
	 * @return the grid cell
	 */
	public static SWTGridCell createValidationCell(int column, AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell validationCell = new SWTGridCell(0, column, renderer);
		validationCell.setHorizontalGrab(false);
		validationCell.setVerticalGrab(false);
		validationCell.setHorizontalFill(false);
		validationCell.setHorizontalAlignment(SWTGridCell.Alignment.CENTER);
		validationCell.setVerticalFill(false);
		validationCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		validationCell.setRenderer(renderer);
		validationCell.setPreferredSize(VALIDATION_PREFERRED_SIZE);
		return validationCell;
	}

	/**
	 * Creates the control cell.
	 * 
	 * @param column the column index
	 * @param renderer the renderer
	 * @return the grid cell
	 */
	public static SWTGridCell createControlCell(int column, AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell controlCell = new SWTGridCell(0, column, renderer);
		controlCell.setHorizontalGrab(true);
		controlCell.setVerticalGrab(false);
		controlCell.setHorizontalFill(true);
		controlCell.setVerticalFill(true);
		controlCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		controlCell.setRenderer(renderer);
		return controlCell;
	}
}
