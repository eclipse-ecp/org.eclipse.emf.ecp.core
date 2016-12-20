/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Jonas Helming
 *
 */
public abstract class AbstractTableViewerComposite extends Composite {

	// TODO: refactor these constants into ColumnDescription interface
	/** Data key for resizable columns. */
	public static final String RESIZABLE = "resizable"; //$NON-NLS-1$
	/** Data key for column weight. */
	public static final String WEIGHT = "weight"; //$NON-NLS-1$
	/** Data key for the minimum width of the column. */
	public static final String MIN_WIDTH = "min_width"; //$NON-NLS-1$
	/** Data key for column id. */
	public static final String COLUMN_ID = "column_id"; //$NON-NLS-1$
	/** Data key for a domain model reference. */
	public static final String DMR = "domain_model_reference"; //$NON-NLS-1$

	private final EMFDataBindingContext emfDatabindingContext;
	private Optional<List<Control>> validationControls;

	/**
	 * Default constructor.
	 *
	 * @param parent the parent {@link Composite}
	 * @param style the style bits
	 * @param inputObject the input object
	 * @param customization the {@link TableViewerSWTCustomization}
	 * @param title the title
	 * @param tooltip the tooltip
	 */
	protected AbstractTableViewerComposite(
		Composite parent,
		int style,
		Object inputObject,
		TableViewerSWTCustomization customization,
		IObservableValue title,
		IObservableValue tooltip) {
		super(parent, style);
		emfDatabindingContext = new EMFDataBindingContext();
		renderControl(this, customization, inputObject, emfDatabindingContext, title, tooltip);
	}

	/**
	 * @return the {@link AbstractTableViewer}
	 */
	public abstract AbstractTableViewer getTableViewer();

	/**
	 *
	 * @return the validation controls, if present
	 */
	public Optional<List<Control>> getValidationControls() {
		return validationControls;
	}

	private void renderControl(Composite parent, TableViewerSWTCustomization customization,
		Object inputObject, EMFDataBindingContext emfDataBindingContext, IObservableValue title,
		IObservableValue tooltip) {
		customization.createCompositeLayout(parent);

		final Optional<Label> titleLabel = customization.getTitleLabel();
		if (titleLabel.isPresent()) {
			initTitleLabel(titleLabel.get(), title, tooltip, emfDatabindingContext);
		}

		validationControls = customization.getValidationControls();

		final Composite viewerComposite = customization.getViewerComposite();

		final AbstractTableViewer tableViewer = createTableViewer(customization, viewerComposite);

		final Optional<Composite> buttonComposite = customization.getButtonComposite();
		if (buttonComposite.isPresent()) {
			initButtonComposite(buttonComposite.get(), customization, tableViewer);
		}

		enableTooltipSupport(tableViewer);

		final Optional<ViewerComparator> comparator = customization.getComparator();
		if (comparator.isPresent()) {
			tableViewer.setComparator(comparator.get());
		}

		tableViewer.setContentProvider(customization.createContentProvider());

		addColumns(customization, tableViewer, emfDataBindingContext);

		setupDragAndDrop(customization, tableViewer);

		tableViewer.setInput(inputObject);

		final AbstractColumnLayout layout = createLayout(viewerComposite);
		final Widget[] columns = getColumns();
		for (int i = 0; i < columns.length; i++) {
			final Widget tableColumn = columns[i];
			final boolean storedIsResizable = (Boolean) tableColumn.getData(RESIZABLE);
			final Integer storedWeight = (Integer) tableColumn.getData(WEIGHT);
			final Integer storedMinWidth = (Integer) tableColumn.getData(MIN_WIDTH);
			if (storedWeight == ColumnDescription.NO_WEIGHT) {
				layout.setColumnData(tableColumn, new ColumnPixelData(storedMinWidth, storedIsResizable));
			} else {
				layout.setColumnData(tableColumn,
					new ColumnWeightData(storedWeight, storedMinWidth, storedIsResizable));
			}
		}
	}

	private void setupDragAndDrop(TableViewerSWTCustomization customization, final AbstractTableViewer tableViewer) {
		if (customization.hasDND()) {
			tableViewer.addDragSupport(customization.getDragOperations(), customization.getDragTransferTypes(),
				customization.getDragListener(tableViewer));
			tableViewer.addDropSupport(customization.getDropOperations(), customization.getDropTransferTypes(),
				customization.getDropListener(tableViewer));
		}
	}

	/**
	 * Returns the list of columns of the table viewer.
	 *
	 * @return the list of columns
	 */
	public abstract Widget[] getColumns();

	/**
	 * Sets the layout of the given {@link Composite}.
	 *
	 * @param viewerComposite the target composite
	 *
	 * @return the applied layout
	 *
	 */
	protected abstract AbstractColumnLayout createLayout(final Composite viewerComposite);

	/**
	 * Creates the table viewer.
	 *
	 * @param customization the {@link TableViewerSWTCustomization} to use
	 * @param viewerComposite the parent composite
	 *
	 * @return the table viewer
	 */
	protected abstract AbstractTableViewer createTableViewer(TableViewerSWTCustomization customization,
		final Composite viewerComposite);

	private void addColumns(TableViewerSWTCustomization customization, AbstractTableViewer tableViewer,
		EMFDataBindingContext emfDataBindingContext) {
		for (final ColumnDescription columnDescription : customization.getColumns()) {
			/* create column */
			// TODO move TableViewerColumnBuilder?
			createColumn(columnDescription, emfDataBindingContext, tableViewer);

		}
	}

	/**
	 * Creates a new {@link ViewerColumn}.
	 *
	 * @param columnDescription the column description to use
	 * @param tableViewer the parent {@link AbstractTableViewer}
	 * @param emfDataBindingContext the data binding context to use
	 * @return the viewer column
	 */
	protected abstract ViewerColumn createColumn(ColumnDescription columnDescription,
		EMFDataBindingContext emfDataBindingContext, AbstractTableViewer tableViewer);

	/**
	 * Creates a new {@link ColumnViewerEditorActivationStrategy} for the given table viewer.
	 *
	 * @param tableViewer the target table viewer.
	 *
	 * @return the ColumnViewerEditorActivationStrategy
	 *
	 */
	protected static ColumnViewerEditorActivationStrategy createColumnViewerActivationStrategy(
		final AbstractTableViewer tableViewer) {
		return new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
					|| event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR
					|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
	}

	private static void enableTooltipSupport(final AbstractTableViewer tableViewer) {
		ColumnViewerToolTipSupport.enableFor(tableViewer);
	}

	private static void initButtonComposite(Composite composite, ButtonBarBuilder customization,
		AbstractTableViewer viewer) {
		customization.fillButtonComposite(composite, viewer);

	}

	private static void initTitleLabel(Label label, IObservableValue title, IObservableValue tooltip,
		EMFDataBindingContext emfDatabindingContext) {
		emfDatabindingContext.bindValue(
			WidgetProperties.text().observe(label),
			title);
		emfDatabindingContext.bindValue(
			WidgetProperties.tooltipText().observe(label),
			tooltip);
	}

	@Override
	public void dispose() {
		emfDatabindingContext.dispose();
		super.dispose();
	}

	/**
	 * Adds a new {@link ControlListener column listener}.
	 *
	 * @param columnlistener the listener to add
	 */
	public abstract void addColumnListener(ControlListener columnlistener);

	/**
	 * Returns the {@link TableControl}.
	 *
	 * @return the table control
	 */
	public abstract TableControl getTableControl();

	/**
	 * Sets the comparator of the table viewer.
	 *
	 * @param comparator the {@link TableViewerComparator} to set
	 * @param sortableColumns the list of columns that can be sorted
	 */
	public abstract void setComparator(final TableViewerComparator comparator, List<Integer> sortableColumns);

}
