/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * Renderer for {@link VSection} with child items.
 *
 * @author jfaltermeier
 *
 */
public class SectionNodeSWTRenderer extends AbstractSectionSWTRenderer {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.18
	 */
	@Inject
	public SectionNodeSWTRenderer(VSection vElement, ViewModelContext viewContext, ReportService reportService,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, viewTemplateProvider);
	}

	private Set<AbstractSectionSWTRenderer> childRenderers;
	private SWTGridDescription rendererGridDescription;
	private ModelChangeListener listener;
	private ExpandableComposite expandableComposite;

	@Override
	protected void preInit() {
		super.preInit();
		listener = new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().isTouch()) {
					return;
				}
				if (notification.getNotifier() != getVElement()) {
					return;
				}
				if (notification.getStructuralFeature() == VSectionPackage.eINSTANCE
					.getSection_Collapsed()) {
					handleCollapseState();
				}
			}
		};
		getViewModelContext().registerViewChangeListener(listener);
	}

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {

		rendererGridDescription = new SWTGridDescription();
		childRenderers = new LinkedHashSet<AbstractSectionSWTRenderer>();

		/* get griddescriptions from child sections */
		final List<SWTGridDescription> childGridDescriptions = new ArrayList<SWTGridDescription>();
		for (final VSection item : getVElement().getChildItems()) {
			AbstractSWTRenderer<?> itemRenderer;
			try {
				itemRenderer = getEMFFormsRendererFactory()
					.getRendererInstance(item, getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				continue;
			}
			final SWTGridDescription itemGridDescription = itemRenderer
				.getGridDescription(GridDescriptionFactory.INSTANCE
					.createEmptyGridDescription());
			childRenderers.add((AbstractSectionSWTRenderer) itemRenderer);
			childGridDescriptions.add(itemGridDescription);
		}

		/* compute required column count based on self and children */
		final int selfColumns = 1 + getVElement().getChildren().size();
		int columns = selfColumns;
		for (final SWTGridDescription childGridDescription : childGridDescriptions) {
			columns = childGridDescription.getColumns() > columns ? childGridDescription.getColumns() : columns;
		}

		/* create grid description for this renderer */
		rendererGridDescription.setColumns(columns);
		final List<SWTGridCell> gridCells = new ArrayList<SWTGridCell>();
		int emptyCellColumnIndicator = -1;

		/* add self */
		int row = 0;

		/* label */
		final Optional<Integer> labelWidth = getLabelWidth();
		Point prefSize;
		if (labelWidth.isPresent()) {
			prefSize = new Point(labelWidth.get(), SWT.DEFAULT);
		} else {
			prefSize = new Point(SWT.DEFAULT, SWT.DEFAULT);
		}
		int curSelfColumn = 0;
		gridCells.add(createGridCell(row, curSelfColumn++, this, prefSize));
		/* empty columns */
		final int emptyColumns = columns - selfColumns;
		for (int i = 0; i < emptyColumns; i++) {
			gridCells.add(createGridCell(row, emptyCellColumnIndicator--, this));
		}
		/* regular columns */
		for (int columnToAdd = 0; columnToAdd < getVElement().getChildren().size(); columnToAdd++) {
			gridCells.add(createGridCell(row, curSelfColumn++, this));
		}
		row += 1;

		/* add children */
		for (final SWTGridDescription childGridDescription : childGridDescriptions) {
			final SWTGridCell[][] sortedChildGridCells = getArrangedChildGridCells(childGridDescription, columns);
			for (final SWTGridCell[] rowGridCells : sortedChildGridCells) {
				/* There is always at least one column (index) */
				final int currentRow = rowGridCells[0].getRow() + row;
				final AbstractSWTRenderer<?> renderer = rowGridCells[0].getRenderer();
				for (int i = 0; i < rowGridCells.length; i++) {
					final SWTGridCell swtGridCell = rowGridCells[i];
					if (swtGridCell != null) {
						gridCells.add(
							createGridCell(
								currentRow,
								swtGridCell.getColumn(),
								swtGridCell.getRenderer(),
								swtGridCell.getPreferredSize()));
					} else {
						/* create empty column */
						gridCells.add(createGridCell(currentRow, emptyCellColumnIndicator--, renderer));
					}
				}
			}
			row += childGridDescription.getRows();
		}

		rendererGridDescription.setRows(row);
		rendererGridDescription.setGrid(gridCells);
		return rendererGridDescription;
	}

	private SWTGridCell[][] getArrangedChildGridCells(SWTGridDescription childGridDescription, int columns) {
		final SWTGridCell[][] result = new SWTGridCell[childGridDescription.getRows()][columns];
		for (final SWTGridCell swtGridCell : childGridDescription.getGrid()) {
			if (swtGridCell.getColumn() < 0) {
				continue;
			}
			result[swtGridCell.getRow()][swtGridCell.getColumn()] = swtGridCell;
		}
		for (final SWTGridCell[] columnArray : result) {
			shiftElementsToEndOfArrayButFirstElement(columnArray);
		}
		return result;
	}

	private static void shiftElementsToEndOfArrayButFirstElement(SWTGridCell[] columnArray) {
		final int length = columnArray.length;
		for (int i = length - 1; i >= 0; i--) {
			if (columnArray[i] == null) {
				final int index = getIndexToMove(columnArray, i);
				if (index == -1) {
					return;
				}
				columnArray[i] = columnArray[index];
				columnArray[index] = null;
			} else {
				return;
			}
		}
	}

	private static int getIndexToMove(SWTGridCell[] columnArray, int index) {
		for (int i = index - 1; i > 0; i--) {
			if (columnArray[i] != null) {
				return i;
			}
		}
		return -1;
	}

	private SWTGridCell createGridCell(int row, int column,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell gridCell = new SWTGridCell(row, column, renderer);
		gridCell.setVerticalFill(false);
		gridCell.setVerticalGrab(false);
		if (column == 3) {
			gridCell.setHorizontalGrab(true);
		} else {
			gridCell.setHorizontalGrab(false);
		}
		return gridCell;

	}

	private SWTGridCell createGridCell(int row, int column,
		AbstractSWTRenderer<? extends VElement> renderer, Point prefSize) {
		final SWTGridCell gridCell = createGridCell(row, column, renderer);
		gridCell.setPreferredSize(prefSize);
		return gridCell;
	}

	@Override
	protected Control createFirstColumn(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1)
			.extendedMargins(computeLeftMargin(), 0, 0, 0)
			.applyTo(composite);

		setExpandableComposite(new ExpandableComposite(
			composite, SWT.NONE, ExpandableComposite.TWISTIE));
		getExpandableComposite().setExpanded(!getVElement().isCollapsed());
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(getVElement());

		final IObservableValue modelLabelValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		final String text = "text"; //$NON-NLS-1$
		final WritableValue value = new WritableValue(text, String.class);
		final IObservableValue textObservable = PojoProperties.value(ExpandableComposite.class, text, String.class)
			.observe(expandableComposite);

		getDataBindingContext().bindValue(textObservable, modelLabelValue);

		initExpandableComposite(getExpandableComposite());

		final IObservableValue modelTooltipValue = EMFEditObservables.observeValue(
			editingDomain,
			getVElement(),
			VViewPackage.eINSTANCE.getHasTooltip_Tooltip());
		final IObservableValue targetTooltipValue = new ExpandableCompositeTooltipProperty()
			.observe(getExpandableComposite());
		getDataBindingContext().bindValue(targetTooltipValue, modelTooltipValue);

		return composite;
	}

	private void initExpandableComposite(ExpandableComposite expandableComposite) {
		expandableComposite.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				getVElement().setCollapsed(!e.getState());
			}
		});
	}

	@Override
	protected void adjustLayoutData(boolean vis) {
		super.adjustLayoutData(vis);
		for (final AbstractSectionSWTRenderer childRenderer : childRenderers) {
			boolean visible = vis;
			if (getVElement().isCollapsed()) {
				visible = false;
			}
			childRenderer.adjustLayoutData(visible);
		}
	}

	@Override
	protected void applyEnable() {
		getExpandableComposite().setEnabled(getVElement().isEffectivelyEnabled());
	}

	@Override
	protected void dispose() {
		getViewModelContext().unregisterViewChangeListener(listener);
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.section.swt.AbstractSectionSWTRenderer#initCollapseState()
	 * @since 1.6
	 */
	@Override
	protected void initCollapseState() {
		/* top root gets current width as width hint so that further resizes will keep the column width intact */
		final Iterator<Control> iterator = getControls().values().iterator();
		while (iterator.hasNext()) {
			final Control control = iterator.next();
			final int width = control.getSize().x;
			final Object layoutData = control.getLayoutData();
			if (GridData.class.isInstance(layoutData)) {
				final GridData gridData = (GridData) layoutData;
				if (gridData == null) {
					continue;
				}
				gridData.widthHint = width;
				if (iterator.hasNext()) {
					continue;
				}
				gridData.grabExcessHorizontalSpace = true;
			}

		}

		handleCollapseState();
	}

	private void handleCollapseState() {
		for (final AbstractSectionSWTRenderer childRenderer : childRenderers) {
			childRenderer.adjustLayoutData(!getVElement()
				.isCollapsed());
		}
		getControls().values().iterator().next().getParent()
			.layout(false);
		getExpandableComposite().setExpanded(!getVElement()
			.isCollapsed());
	}

	/**
	 * @return the expandableComposite
	 * @since 1.13
	 */
	protected ExpandableComposite getExpandableComposite() {
		return expandableComposite;
	}

	/**
	 * @param expandableComposite the expandableComposite to set
	 * @since 1.13
	 */
	protected void setExpandableComposite(ExpandableComposite expandableComposite) {
		this.expandableComposite = expandableComposite;
	}

}
