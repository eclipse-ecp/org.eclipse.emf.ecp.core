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
package org.eclipse.emf.ecp.view.spi.groupedgrid.swt;

import java.util.List;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen Neufeld
 * 
 */
public class GroupedGridSWTRenderer extends AbstractSWTRenderer<VGroupedGrid> {

	/**
	 * The instance of the GroupedGridSWTRenderer.
	 */
	public static final GroupedGridSWTRenderer INSTANCE = new GroupedGridSWTRenderer();

	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VGroupedGrid groupedGrid,
		ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		final int maxNumColumns = calculateColumns(groupedGrid);

		GridLayoutFactory.fillDefaults().numColumns(maxNumColumns).equalWidth(true)
			.applyTo(columnComposite);

		final int currentControl = 0;

		for (final VGroup group : groupedGrid.getGroups()) {
			// Label
			final Composite labelComposite = new Composite(columnComposite, SWT.NONE);
			labelComposite.setBackground(parent.getBackground());
			GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(labelComposite);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).span(maxNumColumns, 1)
				.applyTo(labelComposite);
			final Label l = new Label(labelComposite, SWT.NONE);
			l.setText(group.getName());
			l.setBackground(parent.getBackground());
			GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.BEGINNING)
				.applyTo(l);
			final Label seperator = new Label(labelComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER)
				.applyTo(seperator);
			// Content
			for (final VRow row : group.getRows()) {
				int spanned = 0;
				for (final org.eclipse.emf.ecp.view.spi.model.VContainedElement child : row.getChildren()) {

					final int hSpan = getHSpanOfComposite(child);
					final List<RenderingResultRow<Control>> resultRows = SWTRendererFactory.INSTANCE.render(
						columnComposite,
						child, viewContext);

					// TOOD; when does this case apply?
					if (resultRows == null) {
						continue;
					}
					// TODO refactor
					final Control childRender = resultRows.get(0).getMainControl();
					childRender.setBackground(parent.getBackground());
					GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).indent(0, 0)
						.span(hSpan, 1).applyTo(childRender);
					GridDataFactory.createFrom((GridData) childRender.getLayoutData()).indent(10, 0)
						.applyTo(childRender);
					spanned += hSpan;
					if (org.eclipse.emf.ecp.view.spi.model.VControl.class.isInstance(child)) {
						final org.eclipse.emf.ecp.view.spi.model.VControl control = (org.eclipse.emf.ecp.view.spi.model.VControl) child;
						if (control.getLabelAlignment() == LabelAlignment.LEFT) {
							spanned++;
						}
					}

				}
				final int spanDif = maxNumColumns - spanned;
				if (spanDif != 0) {
					final Label filler = new Label(columnComposite, SWT.NONE);
					filler.setBackground(parent.getBackground());
					GridDataFactory.fillDefaults()
						.span(spanDif, 1).applyTo(filler);
				}
			}
		}
		return createResult(columnComposite);
	}

	/**
	 * @param renderable
	 * @return
	 */
	private int calculateColumns(VGroupedGrid renderable) {
		int maxColumns = 0;
		for (final VGroup group : renderable.getGroups()) {
			for (final VRow row : group.getRows()) {
				int columns = 0;
				for (final org.eclipse.emf.ecp.view.spi.model.VContainedElement composite : row.getChildren()) {
					columns += getHSpanOfComposite(composite) + getExtraColumnForLabel(composite);
				}
				if (columns > maxColumns) {
					maxColumns = columns;
				}
			}
		}
		return maxColumns;
	}

	/**
	 * @param composite
	 * @return
	 */
	private int getHSpanOfComposite(org.eclipse.emf.ecp.view.spi.model.VContainedElement composite) {
		for (final VAttachment attachment : composite.getAttachments()) {
			if (VSpan.class.isInstance(attachment)) {
				final VSpan span = (VSpan) attachment;
				return span.getHorizontalSpan();
			}
		}
		return 1;
	}

	private int getExtraColumnForLabel(org.eclipse.emf.ecp.view.spi.model.VContainedElement child) {
		if (org.eclipse.emf.ecp.view.spi.model.VControl.class.isInstance(child)) {
			final org.eclipse.emf.ecp.view.spi.model.VControl control = (org.eclipse.emf.ecp.view.spi.model.VControl) child;
			return control.getLabelAlignment() == LabelAlignment.LEFT ? 1 : 0;
		}
		return 0;

	}
}
