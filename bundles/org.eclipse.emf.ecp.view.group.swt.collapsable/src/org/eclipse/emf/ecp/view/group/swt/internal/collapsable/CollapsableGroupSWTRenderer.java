/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.group.swt.internal.collapsable;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutUtil;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
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

	private final EMFDataBindingContext dbc;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 */
	@Inject
	public CollapsableGroupSWTRenderer(VGroup vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory) {
		super(vElement, viewContext, reportService, factory);
		dbc = new EMFDataBindingContext();
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
		final CollapsableGroupExpandBar bar = new CollapsableGroupExpandBar(parent, SWT.NONE);
		bar.setBackground(parent.getBackground());

		// First item
		final Composite composite = new Composite(bar, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(MARGIN, MARGIN).applyTo(composite);
		final ExpandItem item0 = new ExpandItem(bar, SWT.NONE, 0);
		bar.setInitialHeaderHeight(item0.getHeaderHeight());

		final FontDescriptor boldDescriptor = FontDescriptor.createFrom(bar.getFont()).setStyle(SWT.BOLD);
		final Font boldFont = boldDescriptor.createFont(bar.getDisplay());
		bar.setFont(boldFont);

		final ISWTObservableValue target = WidgetProperties.text().observe(item0);
		final IObservableValue modelValue = EMFEditObservables.observeValue(
			AdapterFactoryEditingDomain.getEditingDomainFor(getVElement()), getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		dbc.bindValue(target, modelValue);

		final Control containerControl = super.renderControl(gridCell, composite);
		GridDataFactory.fillDefaults().grab(true, false)
			.minSize(containerControl.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, SWT.DEFAULT).applyTo(containerControl);
		final int height = computeHeight(composite);
		item0.setHeight(height);
		item0.setControl(composite);
		bar.setItemComposite(composite);
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

	/**
	 * {@link ExpandBar} which takes its item content size into account when computing the size.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private static final class CollapsableGroupExpandBar extends ExpandBar {

		private Composite itemComposite;
		private int headerHeight;

		CollapsableGroupExpandBar(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * @param headerHeight the header height
		 */
		void setInitialHeaderHeight(int headerHeight) {
			this.headerHeight = headerHeight;
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			return computeSizeForBar(wHint, hHint, changed);
		}

		@Override
		protected void checkSubclass() {
			/*
			 * we have to override expandbar because the size computation does not take the items control size into
			 * account. For our use case we need this to enable parent scrolled composites to set the min size
			 * correctely.
			 */
		}

		void setItemComposite(Composite itemComposite) {
			this.itemComposite = itemComposite;
		}

		private Point computeSizeForBar(int wHint, int hHint, boolean changed) {
			final Point sizeComputedByBar = super.computeSize(wHint, hHint, changed);
			if (itemComposite != null) {
				final Point itemSize = itemComposite.computeSize(wHint, hHint, changed);
				if (itemSize.x > sizeComputedByBar.x) {
					/* else might be true if the expandbar has a really long group text */
					sizeComputedByBar.x = itemSize.x;
				}
				/* workaround for SWT-Bug 430600 */
				if (getItemCount() == 1 && getItem(0).getHeaderHeight() < 0) {
					if (itemSize.y + headerHeight > sizeComputedByBar.y && getItem(0).getExpanded()) {
						sizeComputedByBar.y = itemSize.y + headerHeight;
					}
				}
			}
			sizeComputedByBar.x = sizeComputedByBar.x + 2 * MARGIN;
			return sizeComputedByBar;
		}
	}

	@Override
	protected void dispose() {
		if (dbc != null) {
			dbc.dispose();
		}
		super.dispose();
	}
}
