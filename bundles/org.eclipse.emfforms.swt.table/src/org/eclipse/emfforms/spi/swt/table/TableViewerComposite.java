/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

/**
 * A {@link Composite} containing a {@link TableViewer}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 */
public class TableViewerComposite extends Composite {

	private static final String RESIZABLE = "resizable"; //$NON-NLS-1$
	private static final String WEIGHT = "weight"; //$NON-NLS-1$
	private static final String MIN_WIDTH = "min_width"; //$NON-NLS-1$
	private final EMFDataBindingContext emfDatabindingContext;
	private TableViewer tableViewer;
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
	TableViewerComposite(
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
	 * @return the {@link TableViewer}
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

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

		tableViewer = customization.createTableViewer(viewerComposite);

		final Optional<Composite> buttonComposite = customization.getButtonComposite();
		if (buttonComposite.isPresent()) {
			initButtonComposite(buttonComposite.get(), customization, tableViewer);
		}

		enableTooltipSupport(tableViewer);

		enableEditingSupport(tableViewer);

		final Optional<ViewerComparator> comparator = customization.getComparator();
		if (comparator.isPresent()) {
			tableViewer.setComparator(comparator.get());
		}

		tableViewer.setContentProvider(customization.createContentProvider());

		addColumns(customization, tableViewer, emfDataBindingContext);

		tableViewer.setInput(inputObject);

		final TableColumnLayout layout = new TableColumnLayout();
		viewerComposite.setLayout(layout);
		for (int i = 0; i < tableViewer.getTable().getColumns().length; i++) {
			final TableColumn tableColumn = tableViewer.getTable().getColumns()[i];
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

	private static void addColumns(TableViewerSWTCustomization customization, TableViewer tableViewer,
		EMFDataBindingContext emfDataBindingContext) {
		for (final ColumnDescription columnDescription : customization.getColumns()) {
			/* create column */
			// TODO move TableViewerColumnBuilder?
			@SuppressWarnings("restriction")
			final TableViewerColumn column = org.eclipse.emf.ecp.edit.internal.swt.controls.TableViewerColumnBuilder
				.create()
				.setData(RESIZABLE, columnDescription.isResizeable())
				.setMoveable(columnDescription.isMoveable())
				.setStyle(columnDescription.getStyleBits())
				.setData(WEIGHT, columnDescription.getWeight())
				.setData(MIN_WIDTH, columnDescription.getMinWidth())
				.build(tableViewer);

			/* bind text and tooltip */
			final IObservableValue text = columnDescription.getColumnText();
			emfDataBindingContext.bindValue(WidgetProperties.text().observe(column.getColumn()), text);
			final IObservableValue tooltipText = columnDescription.getColumnTooltip();
			emfDataBindingContext.bindValue(WidgetProperties.tooltipText().observe(column.getColumn()), tooltipText);

			/* set label provider */
			column.setLabelProvider(columnDescription.createLabelProvider(tableViewer));

			/* set editing support */
			final Optional<EditingSupport> editingSupport = columnDescription.createEditingSupport(tableViewer);
			if (editingSupport.isPresent()) {
				column.setEditingSupport(editingSupport.get());
			}

			if (columnDescription.getColumnImage().isPresent()) {
				column.getColumn().setImage(columnDescription.getColumnImage().get());
			}

			/* setup drag&drop */
			if (customization.hasDND()) {
				tableViewer.addDragSupport(customization.getDragOperations(), customization.getDragTransferTypes(),
					customization.getDragListener(tableViewer));
				tableViewer.addDropSupport(customization.getDropOperations(), customization.getDropTransferTypes(),
					customization.getDropListener(tableViewer));
			}

		}
	}

	private static void enableEditingSupport(final TableViewer tableViewer) {
		@SuppressWarnings("restriction")
		final TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tableViewer,
			new org.eclipse.emf.ecp.edit.internal.swt.controls.ECPFocusCellDrawHighlighter(tableViewer));
		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(tableViewer) {
			@Override
			protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
					|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
					|| event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR
					|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		TableViewerEditor.create(
			tableViewer,
			focusCellManager,
			actSupport,
			ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
	}

	private static void enableTooltipSupport(final TableViewer tableViewer) {
		ColumnViewerToolTipSupport.enableFor(tableViewer);
	}

	private static void initButtonComposite(Composite composite, ButtonBarBuilder customization,
		TableViewer viewer) {
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

}
