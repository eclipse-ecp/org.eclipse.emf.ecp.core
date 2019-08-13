/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jonas - initial API and implementation
 * Christian W. Damus - bug 547271
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.swt.table.action.KeyBindingManager;
import org.eclipse.emfforms.spi.swt.table.action.ActionBar;
import org.eclipse.emfforms.spi.swt.table.action.ActionBarProvider;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
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
 * @param <V> the TableViewer implementation to use
 */
public abstract class AbstractTableViewerComposite<V extends AbstractTableViewer> extends Composite {

	private final EMFDataBindingContext emfDatabindingContext;
	private Optional<List<Control>> validationControls;
	private final Set<Feature> enabledFeatures;
	private final Optional<ActionBar<V>> actionBar;

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
		TableViewerSWTCustomization<V> customization,
		IObservableValue<String> title,
		IObservableValue<String> tooltip) {
		super(parent, style);

		emfDatabindingContext = new EMFDataBindingContext();
		enabledFeatures = determineEnabledFeatures(customization);
		actionBar = customization.getActionBar();

		renderControl(this, customization, inputObject, emfDatabindingContext, title, tooltip);
	}

	/**
	 * Determine the list of enabled features (both for the table as well as the columns).
	 *
	 * @param customization the viewer customization
	 * @return a set of enabled features
	 */
	private Set<Feature> determineEnabledFeatures(TableViewerSWTCustomization<V> customization) {

		final Set<Feature> enabled = new LinkedHashSet<Feature>();
		enabled.addAll(customization.getTableConfiguration().getEnabledFeatures());

		for (final ColumnConfiguration columnConfig : customization.getColumnConfigurations()) {
			enabled.addAll(columnConfig.getEnabledFeatures());
		}

		return enabled;
	}

	/**
	 * @return the enabledFeatures
	 */
	public Set<Feature> getEnabledFeatures() {
		return enabledFeatures;
	}

	/**
	 * If present, returns the {@link ActionBar} configuring actions and corresponding control for this composite's
	 * viewer.
	 *
	 * @return The {@link ActionBar}, if any
	 */
	public Optional<ActionBar<V>> getActionBar() {
		return actionBar;
	}

	/**
	 * @return the {@link AbstractTableViewer}
	 */
	public abstract V getTableViewer();

	/**
	 *
	 * @return the validation controls, if present
	 */
	public Optional<List<Control>> getValidationControls() {
		return validationControls;
	}

	/**
	 * Configures the context menu for the given TableViewer instance.
	 *
	 * @param tableViewer the table viewer to configure
	 */
	protected void configureContextMenu(V tableViewer) {

	}

	/**
	 * Configures viewer filters for the given TableViewer instance.
	 *
	 * @param tableViewer the table viewer to configure
	 */
	protected void configureViewerFilters(V tableViewer) {

	}

	private void renderControl(Composite parent, TableViewerSWTCustomization<V> customization,
		Object inputObject, EMFDataBindingContext emfDataBindingContext, IObservableValue<String> title,
		IObservableValue<String> tooltip) {
		customization.createCompositeLayout(parent);

		final Optional<Label> titleLabel = customization.getTitleLabel();
		if (titleLabel.isPresent()) {
			initTitleLabel(titleLabel.get(), title, tooltip, emfDatabindingContext);
		}

		validationControls = customization.getValidationControls();

		final Composite viewerComposite = customization.getViewerComposite();

		final V tableViewer = createTableViewer(customization, viewerComposite);

		final TableConfiguration configuration = customization.getTableConfiguration();
		if (configuration != null) {
			// Pump in the configuration data
			configuration.getData().forEach(tableViewer::setData);
		}

		// If an action configuration was configured, bind key bindings to the viewer
		final Optional<ActionConfiguration> actionConfiguration = customization.getActionConfiguration();
		if (actionConfiguration.isPresent()) {
			final KeyBindingManager keyBindingManager = new KeyBindingManager();
			keyBindingManager.applyActionConfiguration(actionConfiguration.get());
			keyBindingManager.bindToViewer(tableViewer);
		}

		final Optional<Composite> buttonComposite = customization.getButtonComposite();
		if (buttonComposite.isPresent()) {
			initActionBar(buttonComposite.get(), customization, tableViewer);
		}

		enableTooltipSupport(tableViewer);

		final Optional<ViewerComparator> comparator = customization.getComparator();
		if (comparator.isPresent()) {
			tableViewer.setComparator(comparator.get());
		}

		tableViewer.setContentProvider(customization.createContentProvider());

		addColumns(customization, tableViewer, emfDataBindingContext);

		setupDragAndDrop(customization, tableViewer);
		configureContextMenu(tableViewer);
		configureViewerFilters(tableViewer);

		tableViewer.setInput(inputObject);

		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				viewerSelectionChanged(event);
			}
		});

		final AbstractColumnLayout layout = createLayout(viewerComposite);
		final Widget[] columns = getColumns();
		for (int i = 0; i < columns.length; i++) {
			final Widget tableColumn = columns[i];
			final boolean storedIsResizable = (Boolean) tableColumn.getData(ColumnConfiguration.RESIZABLE);
			final Integer storedWeight = (Integer) tableColumn.getData(ColumnConfiguration.WEIGHT);
			final Integer storedMinWidth = (Integer) tableColumn.getData(ColumnConfiguration.MIN_WIDTH);
			if (storedWeight == ColumnConfiguration.NO_WEIGHT) {
				layout.setColumnData(tableColumn, new ColumnPixelData(storedMinWidth, storedIsResizable));
			} else if (storedMinWidth > 0) {
				layout.setColumnData(tableColumn,
					new ColumnWeightData(storedWeight, storedMinWidth, storedIsResizable));
			} else {
				layout.setColumnData(tableColumn,
					new ColumnWeightData(storedWeight, storedIsResizable));
			}
		}
	}

	private void setupDragAndDrop(TableViewerSWTCustomization<V> customization, final V tableViewer) {
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
	protected abstract AbstractColumnLayout createLayout(Composite viewerComposite);

	/**
	 * Creates the table viewer.
	 *
	 * @param customization the {@link TableViewerSWTCustomization} to use
	 * @param viewerComposite the parent composite
	 *
	 * @return the table viewer
	 */
	protected abstract V createTableViewer(TableViewerSWTCustomization<V> customization,
		Composite viewerComposite);

	private void addColumns(TableViewerSWTCustomization<V> customization, V tableViewer,
		EMFDataBindingContext emfDataBindingContext) {
		for (final ColumnConfiguration columnConfiguration : customization.getColumnConfigurations()) {
			createColumn(columnConfiguration, emfDataBindingContext, tableViewer);
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
	protected abstract ViewerColumn createColumn(ColumnConfiguration columnDescription,
		EMFDataBindingContext emfDataBindingContext, V tableViewer);

	/**
	 * Returns the {@link ColumnConfiguration} of the given widget instance.
	 *
	 * @param columnWidget the widget to fetch the column configuration for
	 * @return the {@link ColumnConfigurationImpl}
	 */
	public ColumnConfiguration getColumnConfiguration(Widget columnWidget) {
		return (ColumnConfiguration) columnWidget.getData(ColumnConfiguration.ID);
	}

	/**
	 * This method gets called when the selection on the {@link TableViewer} (see {@link #getTableViewer()}) has
	 * changed.
	 * <p>
	 * If you override this method make sure to call super.
	 * </p>
	 *
	 * @param event the {@link SelectionChangedEvent}
	 */
	protected void viewerSelectionChanged(SelectionChangedEvent event) {
		if (actionBar.isPresent()) {
			actionBar.get().updateActionBar();
		}
	}

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

	private static void enableTooltipSupport(AbstractTableViewer tableViewer) {
		ColumnViewerToolTipSupport.enableFor(tableViewer);
	}

	private static <T extends AbstractTableViewer> void initActionBar(
		Composite composite, ActionBarProvider<T> actionBarProvider, T tableViewer) {
		final Optional<ActionBar<T>> actionBar = actionBarProvider.getActionBar();
		if (actionBar.isPresent()) {
			actionBar.get().fillComposite(composite, tableViewer);
		}
	}

	private static void initTitleLabel(Label label, IObservableValue<String> title, IObservableValue<String> tooltip,
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
	public abstract void setComparator(TableViewerComparator comparator, List<Integer> sortableColumns);

	/**
	 * Allow to set an initial sort.
	 *
	 * @param column The column to sort
	 * @since 1.22
	 */
	public abstract void setCompareColumn(int column);
}
