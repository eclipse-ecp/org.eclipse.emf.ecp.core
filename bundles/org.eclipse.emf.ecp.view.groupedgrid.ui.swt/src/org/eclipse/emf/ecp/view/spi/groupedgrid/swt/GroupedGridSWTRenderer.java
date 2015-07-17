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

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VRow;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VSpan;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public class GroupedGridSWTRenderer extends AbstractSWTRenderer<VGroupedGrid> {
	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 */
	public GroupedGridSWTRenderer(final VGroupedGrid vElement, final ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	private SWTGridDescription rendererGridDescription;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		final int maxNumColumns = calculateColumns(getVElement());

		GridLayoutFactory.fillDefaults().numColumns(maxNumColumns).equalWidth(true)
			.applyTo(columnComposite);

		for (final VGroup group : getVElement().getGroups()) {
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
					AbstractSWTRenderer<VElement> renderer;
					try {
						renderer = getEMFFormsRendererFactory().getRendererInstance(child,
							getViewModelContext());
					} catch (final EMFFormsNoRendererException ex) {
						getReportService().report(new RenderingFailedReport(ex));
						continue;
					}
					final Control childRender = renderer.render(new SWTGridCell(0, 0, this),
						columnComposite);

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
		return columnComposite;
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

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		final EMFFormsRendererFactory rendererFactory = bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);
		return rendererFactory;
	}
}
