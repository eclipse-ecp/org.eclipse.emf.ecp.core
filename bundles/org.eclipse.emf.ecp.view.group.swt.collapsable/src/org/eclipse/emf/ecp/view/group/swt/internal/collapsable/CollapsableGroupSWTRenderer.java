/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.group.swt.internal.collapsable;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

/**
 * A Group renderer, which is collapsable.
 *
 * @author Eugen Neufeld
 * @author jfaltermeier
 *
 */
public class CollapsableGroupSWTRenderer extends ContainerSWTRenderer<VGroup> {

	private static final int MARGIN = 5;

	private SWTGridDescription rendererGridDescription;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 */
	@Inject
	public CollapsableGroupSWTRenderer(VGroup vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsDatabinding emfFormsDatabinding) {
		super(vElement, viewContext, reportService, factory, emfFormsDatabinding);
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			final SWTGridCell swtGridCell = rendererGridDescription.getGrid().get(0);
			swtGridCell.setVerticalFill(false);
			swtGridCell.setVerticalGrab(false);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell gridCell, final Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final ExpandBar bar = new ExpandBar(parent, SWT.NONE);
		bar.setBackground(parent.getBackground());

		// First item
		final Composite composite = new Composite(bar, SWT.NONE);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = MARGIN;
		fillLayout.marginWidth = MARGIN;
		composite.setLayout(fillLayout);
		final ExpandItem item0 = new ExpandItem(bar, SWT.NONE, 0);
		String text = getVElement().getName();
		if (text == null) {
			text = ""; //$NON-NLS-1$
		}
		item0.setText(text);
		super.renderControl(gridCell, composite);
		final int height = computeHeight(composite);
		item0.setHeight(height);
		item0.setControl(composite);
		bar.addExpandListener(new ExpandListener() {

			@Override
			public void itemCollapsed(ExpandEvent e) {
				final int headerHeight = item0.getHeaderHeight();
				item0.setHeight(headerHeight);
				final Object layoutData = bar.getLayoutData();
				updateLayoutData(layoutData, headerHeight + 2 * MARGIN);
				EMFFormsSWTLayoutUtil.adjustParentSize(bar);
				getVElement().setCollapsed(true);
				postCollapsed();
			}

			@Override
			public void itemExpanded(ExpandEvent e) {
				item0.setHeight(computeHeight(composite));
				final Object layoutData = bar.getLayoutData();
				updateLayoutData(layoutData, computeHeight(composite) + item0.getHeaderHeight()
					+ 2 * MARGIN);
				EMFFormsSWTLayoutUtil.adjustParentSize(bar);
				getVElement().setCollapsed(false);
				postExpanded();
			}

			// XXX relayout upon expand/collapse will only work properly when the grid data is adjusted
			// this might not be the case for non GridData layout datas
			private void updateLayoutData(final Object layoutData, int height) {
				if (layoutData instanceof GridData) {
					final GridData gridData = (GridData) layoutData;
					gridData.heightHint = height;
				}
			}

		});
		item0.setExpanded(!getVElement().isCollapsed());
		return bar;
	}

	/**
	 * This method gets called after the group has been expanded. Default implementation does nothing.
	 */
	protected void postExpanded() {
		// no op
	}

	/**
	 * This method gets called after the group has been collapsed. Default implementation does nothing.
	 */
	protected void postCollapsed() {
		// no op
	}

	private int computeHeight(Composite composite) {
		// XXX +1 because last pixel gets cut off on windows 7 64
		return composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 1;
	}
}
