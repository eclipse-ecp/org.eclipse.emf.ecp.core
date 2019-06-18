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
 * Alexandra Buzila - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bugs 534829, 530314, 547271
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPFilterableCell;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridControlSWTRenderer.CustomGridTableViewer;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.menu.GridColumnAction;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.messages.Messages;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.common.Property;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableControl;
import org.eclipse.emfforms.spi.swt.table.TableViewerComparator;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuListener2;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.nebula.jface.gridviewer.GridColumnLayout;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.jface.gridviewer.GridViewerRow;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;

/**
 * A {@link Composite} containing a {@link GridTableViewer}.
 *
 * @author Jonas Helming
 *
 */
public class GridTableViewerComposite extends AbstractTableViewerComposite<GridTableViewer> {

	private static final Map<Feature, Function<GridTableViewerComposite, ? extends IMenuListener>> FEATURE_MENU_LISTENERS = new HashMap<>();

	private static final Map<Feature, Function<GridTableViewerComposite, ? extends ViewerFilter>> FEATURE_VIEWER_FILTERS = new HashMap<>();

	private GridTableViewer gridTableViewer;
	private Point lastKnownPointer;

	private final IObservableValue<Feature> activeFilteringMode = new WritableValue<>();

	static {
		FEATURE_MENU_LISTENERS.put(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW,
			comp -> comp.new ColumnHideShowMenuListener());
		FEATURE_MENU_LISTENERS.put(TableConfiguration.FEATURE_COLUMN_FILTER,
			comp -> comp.new ColumnFilterMenuListener(ColumnConfiguration.FEATURE_COLUMN_FILTER,
				Messages.GridTableViewerComposite_toggleFilterControlsAction));
		FEATURE_MENU_LISTENERS.put(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER,
			comp -> comp.new ColumnFilterMenuListener(ColumnConfiguration.FEATURE_COLUMN_REGEX_FILTER,
				Messages.GridTableViewerComposite_toggleRegexFilterControlsAction));

		FEATURE_VIEWER_FILTERS.put(TableConfiguration.FEATURE_COLUMN_FILTER,
			comp -> comp.new GridColumnFilterViewerFilter());
		FEATURE_VIEWER_FILTERS.put(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER,
			comp -> comp.new GridColumnRegexFilterViewerFilter());
	}

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
	public GridTableViewerComposite(Composite parent, int style, Object inputObject,
		TableViewerSWTCustomization customization,
		IObservableValue title, IObservableValue tooltip) {

		super(parent, style, inputObject, customization, title, tooltip);

		activeFilteringMode.addValueChangeListener(this::handleFilteringMode);
	}

	@Override
	public void dispose() {
		activeFilteringMode.dispose();

		super.dispose();
	}

	@Override
	public GridTableViewer getTableViewer() {
		return gridTableViewer;
	}

	@Override
	protected GridTableViewer createTableViewer(TableViewerSWTCustomization<GridTableViewer> customization,
		Composite viewerComposite) {
		gridTableViewer = customization.createTableViewer(viewerComposite);
		gridTableViewer.getControl().addMouseMoveListener(event -> lastKnownPointer = new Point(event.x, event.y));
		gridTableViewer.getControl().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent event) {
				lastKnownPointer = null;
			}
		});
		return gridTableViewer;
	}

	@Override
	protected void configureContextMenu(GridTableViewer tableViewer) {
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);

		mapFeatures(FEATURE_MENU_LISTENERS::get, menuMgr::addMenuListener);

		final Menu menu = menuMgr.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
	}

	private <T> void mapFeatures(Function<Feature, Function<? super GridTableViewerComposite, T>> mapper,
		Consumer<? super T> action) {

		getEnabledFeatures().stream().map(mapper).filter(Objects::nonNull)
			.map(f -> f.apply(this)).forEach(action);
	}

	@Override
	protected void configureViewerFilters(GridTableViewer tableViewer) {
		mapFeatures(FEATURE_VIEWER_FILTERS::get, tableViewer::addFilter);
	}

	@Override
	protected AbstractColumnLayout createLayout(Composite viewerComposite) {
		final GridColumnLayout layout = new GridColumnLayout();
		viewerComposite.setLayout(layout);
		return layout;
	}

	@Override
	public Widget[] getColumns() {
		return gridTableViewer.getGrid().getColumns();
	}

	@Override
	public void addColumnListener(ControlListener columnlistener) {
		for (int i = 0; i < gridTableViewer.getGrid().getColumns().length; i++) {
			final GridColumn gridColumn = gridTableViewer.getGrid().getColumns()[i];
			gridColumn.addControlListener(columnlistener);
		}
	}

	@Override
	public TableControl getTableControl() {
		return new TableControl() {

			@Override
			public boolean isDisposed() {
				return getTableViewer().getGrid().isDisposed();
			}

			@Override
			public int getItemHeight() {
				return getTableViewer().getGrid().getItemHeight();
			}

			@Override
			public boolean getHeaderVisible() {
				return getTableViewer().getGrid().getHeaderVisible();
			}

			@Override
			public int getHeaderHeight() {
				return getTableViewer().getGrid().getHeaderHeight();
			}

			@Override
			public int getItemCount() {
				return getTableViewer().getGrid().getItemCount();
			}
		};
	}

	@Override
	protected ViewerColumn createColumn(final ColumnConfiguration config,
		EMFDataBindingContext emfDataBindingContext, final GridTableViewer tableViewer) {

		final GridViewerColumn column = new GridViewerColumnBuilder(config)
			.withDatabinding(emfDataBindingContext)
			.build(tableViewer);

		return column;
	}

	@Override
	public void setComparator(final TableViewerComparator comparator, List<Integer> sortableColumns) {
		for (int i = 0; i < getTableViewer().getGrid().getColumns().length; i++) {
			if (!sortableColumns.contains(i)) {
				continue;
			}
			final int j = i;
			final GridColumn tableColumn = getTableViewer().getGrid().getColumns()[i];
			final SelectionAdapter selectionAdapter = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// Reset other columns to avoid left over sort indicators
					for (final int index : sortableColumns) {
						final GridColumn column = getTableViewer().getGrid().getColumns()[index];
						if (index != j && column.getSort() != SWT.NONE) {
							column.setSort(SWT.NONE);
						}
					}
					comparator.setColumn(j);
					tableColumn.setSort(comparator.getDirection());
					gridTableViewer.refresh();
				}
			};
			tableColumn.addSelectionListener(selectionAdapter);
		}

	}

	private GridColumn getCurrentColumn() {
		GridColumn result = null;
		final Grid grid = getTableViewer().getGrid();

		if (lastKnownPointer == null) {
			// For testability, especially in RCPTT, we may not have any real
			// tracking of the mouse pointer. So, hope there's a selection
			final Point[] selectedCells = grid.getCellSelection();
			if (selectedCells != null && selectedCells.length > 0) {
				result = grid.getColumn(selectedCells[0].x);
			}
		} else {
			result = grid.getColumn(lastKnownPointer);
		}

		return result;
	}

	private ColumnConfiguration getCurrentColumnConfig() {
		final GridColumn column = getCurrentColumn();
		if (column == null) {
			return null;
		}
		return getColumnConfiguration(column);
	}

	/**
	 * Query the currently active filtering mode, if filtering is engaged.
	 *
	 * @return one the {@linkplain ColumnConfiguration#FEATURE_COLUMN_FILTER filtering features}
	 *         indicating the filtering mode that is active, or {@code null} if the grid is not filtered
	 *
	 * @since 1.21
	 * @see #setFilteringMode(Feature)
	 * @see ColumnConfiguration#FEATURE_COLUMN_FILTER
	 * @see ColumnConfiguration#FEATURE_COLUMN_REGEX_FILTER
	 */
	public Feature getFilteringMode() {
		return activeFilteringMode == null ? null : activeFilteringMode.getValue();
	}

	/**
	 * Set the currently active filtering mode.
	 *
	 * @param filteringFeature one the {@linkplain ColumnConfiguration#FEATURE_COLUMN_FILTER filtering features}
	 *            indicating the filtering mode that is active, or {@code null} if the grid is not to be filtered
	 *
	 * @throws IllegalStateException if the composite is not yet initialized
	 * @throws IllegalArgumentException if the {@code filteringFeature} is not supported by my
	 *             table configuration ({@code null}, excepted, of course)
	 *
	 * @since 1.21
	 * @see #getFilteringMode()
	 * @see ColumnConfiguration#FEATURE_COLUMN_FILTER
	 * @see ColumnConfiguration#FEATURE_COLUMN_REGEX_FILTER
	 */
	public void setFilteringMode(Feature filteringFeature) {
		if (activeFilteringMode == null) {
			// This can happen while superclass constructor is running, which
			// calls into polymorphic methods overridden in this class
			throw new IllegalStateException();
		}
		if (filteringFeature != null && !getEnabledFeatures().contains(filteringFeature)) {
			throw new IllegalArgumentException(filteringFeature.toString());
		}

		activeFilteringMode.setValue(filteringFeature);
	}

	/**
	 * Respond to a change of filtering mode by showing/hiding filter controls
	 * in the columns as appropriate.
	 *
	 * @param event the change in the filtering mode
	 */
	private void handleFilteringMode(ValueChangeEvent<? extends Feature> event) {
		final Boolean showFilterControl = getFilteringMode() != null;

		boolean recalculateHeader = false;
		for (final Widget widget : getColumns()) {
			final Property<Boolean> showProperty = getColumnConfiguration(widget).showFilterControl();
			if (!showFilterControl.equals(showProperty.getValue())) {
				recalculateHeader = true;
				showProperty.setValue(showFilterControl);
			}
		}

		if (recalculateHeader) {
			getTableViewer().getGrid().recalculateHeader();
		}
	}

	//
	// Nested types
	//

	/**
	 * A grid column action whose enablement depends on a enablement of a
	 * {@linkplain ColumnConfiguration#getEnabledFeatures() configuration feature}.
	 */
	private abstract class FeatureBasedColumnAction extends GridColumnAction {

		private final Feature feature;

		{
			setCurrentColumnProvider(GridTableViewerComposite.this::getCurrentColumn);
		}

		FeatureBasedColumnAction(GridTableViewerComposite gridTableViewerComposite, String actionLabel,
			Feature feature) {

			super(gridTableViewerComposite, actionLabel);

			this.feature = feature;
		}

		FeatureBasedColumnAction(GridTableViewerComposite gridTableViewerComposite, String actionLabel, int style,
			Feature feature) {

			super(gridTableViewerComposite, actionLabel, style);

			this.feature = feature;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && getEnabledFeatures().contains(feature);
		}

	}

	/**
	 * Column hide/show menu listener.
	 *
	 * @author Mat Hansen
	 *
	 */
	private class ColumnHideShowMenuListener implements IMenuListener {

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			final ColumnConfiguration columnConfiguration = getCurrentColumnConfig();
			if (columnConfiguration == null) {
				return;
			}
			manager.add(new FeatureBasedColumnAction(GridTableViewerComposite.this,
				Messages.GridTableViewerComposite_hideColumnAction, ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW) {
				@Override
				public void run() {
					columnConfiguration.visible().setValue(Boolean.FALSE);
				}
			});
			manager.add(new FeatureBasedColumnAction(GridTableViewerComposite.this,
				Messages.GridTableViewerComposite_showAllColumnsAction, ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW) {
				@Override
				public void run() {
					for (final Widget widget : getColumns()) {
						getGridTableViewer().getColumnConfiguration(widget).visible().setValue(Boolean.TRUE);
					}
				}

				@Override
				public boolean isEnabled() {
					return super.isEnabled() && hasHiddenColumns();
				}

				boolean hasHiddenColumns() {
					for (final Widget widget : getColumns()) {
						if (!getGridTableViewer().getColumnConfiguration(widget).visible().getValue()) {
							return true;
						}
					}
					return false;
				}
			});
		}

	}

	/**
	 * Common definition of a filtering menu listener.
	 *
	 * @author Mat Hansen
	 *
	 */
	private class ColumnFilterMenuListener implements IMenuListener2 {
		private final IValueChangeListener<Feature> radioListener = this::handleRadio;
		private final Feature feature;
		private final String label;

		private GridColumnAction action;

		ColumnFilterMenuListener(Feature feature, String label) {
			super();

			this.feature = feature;
			this.label = label;
		}

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			final ColumnConfiguration columnConfiguration = getCurrentColumnConfig();
			if (columnConfiguration == null) {
				return;
			}

			action = createAction(feature, label, columnConfiguration);
			action.setChecked(getFilteringMode() == feature);

			manager.add(action);
			activeFilteringMode.addValueChangeListener(radioListener);
		}

		@Override
		public void menuAboutToHide(IMenuManager manager) {
			activeFilteringMode.removeValueChangeListener(radioListener);
			action = null;
		}

		GridColumnAction createAction(Feature feature, String label, ColumnConfiguration columnConfiguration) {
			return new FeatureBasedColumnAction(GridTableViewerComposite.this, label, IAction.AS_RADIO_BUTTON,
				feature) {

				@Override
				public void run() {
					if (getFilteringMode() == feature) {
						// We're toggling filtering off
						setFilteringMode(null);
					} else {
						// We're setting filtering to my mode
						setFilteringMode(feature);
					}
				}
			};
		}

		void handleRadio(ValueChangeEvent<? extends Feature> event) {
			action.setChecked(event.getObservableValue().getValue() == feature);
		}

	}

	/**
	 * Common viewer filter implementation for column filters.
	 *
	 * @author Mat Hansen
	 *
	 */
	private abstract class AbstractGridColumnFilterViewerFilter extends ViewerFilter {

		private final Feature feature;

		private final GridTableViewer tableViewer;
		private final Grid grid;

		/**
		 * Initializes me with the filtering feature that I implement.
		 *
		 * @param feature my defining feature
		 */
		AbstractGridColumnFilterViewerFilter(Feature feature) {
			super();

			this.feature = feature;

			tableViewer = getTableViewer();
			grid = tableViewer.getGrid();
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (!isApplicable(viewer)) {
				return true;
			}

			GridItem dummyItem = null;
			GridViewerRow viewerRow = null;

			try {
				for (final Widget widget : getColumns()) {
					final ColumnConfiguration config = getColumnConfiguration(widget);

					// Do we even have a filter to apply?
					final Object filter = config.matchFilter().getValue();
					if (filter == null || String.valueOf(filter).isEmpty()) {
						continue;
					}

					final GridColumn column = (GridColumn) widget;
					final int columnIndex = tableViewer.getGrid().indexOf(column);
					final CellLabelProvider labelProvider = tableViewer.getLabelProvider(columnIndex);

					// Optimize for the standard case
					final ECPFilterableCell filterable = Adapters.adapt(labelProvider, ECPFilterableCell.class);
					if (filterable != null) {
						// Just get the text and filter it
						final String text = filterable.getFilterableText(element);
						if (!matchesColumnFilter(text, filter)) {
							return false;
						}
					} else {
						// We have a filter, but we need something to filter on
						if (dummyItem == null) {
							grid.setRedraw(false);
							dummyItem = new GridItem(grid, SWT.NONE);

							dummyItem.setData(element);
							viewerRow = (GridViewerRow) ((CustomGridTableViewer) tableViewer)
								.getViewerRowFromItem(dummyItem);
						}

						// Update the cell, the slow way
						final ViewerCell cell = viewerRow.getCell(columnIndex);
						labelProvider.update(cell);

						if (!matchesColumnFilter(cell.getText(), filter)) {
							return false;
						}
					}
				}
			} finally {
				if (dummyItem != null) {
					dummyItem.dispose();
					grid.setRedraw(true);
				}
			}

			return true;
		}

		protected boolean isApplicable(Viewer viewer) {
			return getFilteringMode() == feature;
		}

		/**
		 * Test whether the given value/filter combination matches.
		 *
		 * @param value the value to test
		 * @param filterValue the filter value
		 * @return true if the value matches the filter value
		 */
		protected abstract boolean matchesColumnFilter(Object value, Object filterValue);

	}

	/**
	 * Viewer filter for column simple filter support.
	 *
	 * @author Mat Hansen
	 *
	 */
	private class GridColumnFilterViewerFilter extends AbstractGridColumnFilterViewerFilter {

		/**
		 * The Constructor.
		 */
		GridColumnFilterViewerFilter() {
			super(ColumnConfiguration.FEATURE_COLUMN_FILTER);
		}

		@Override
		protected boolean matchesColumnFilter(Object value, Object filterValue) {

			if (filterValue == null) {
				return false;
			}

			return String.valueOf(value).toLowerCase()
				.contains(String.valueOf(filterValue).toLowerCase());
		}

	}

	/**
	 * Viewer filter for column regular expression filter support.
	 */
	private class GridColumnRegexFilterViewerFilter extends AbstractGridColumnFilterViewerFilter {

		private Object rawFilter;
		private Pattern pattern;

		/**
		 * Initializes me.
		 */
		GridColumnRegexFilterViewerFilter() {
			super(ColumnConfiguration.FEATURE_COLUMN_REGEX_FILTER);
		}

		@Override
		protected boolean matchesColumnFilter(Object value, Object filterValue) {

			if (!Objects.equals(filterValue, rawFilter)) {
				// Cache a new pattern, if possible
				pattern = parse(String.valueOf(filterValue));
			}

			if (pattern == null) {
				// Couldn't parse it. Everything will match (user should be able to see examples
				// in the data to formulate a pattern)
				return true;
			}

			return pattern.matcher(String.valueOf(value)).find();
		}

		protected Pattern parse(String regex) {
			Pattern result = null;

			try {
				result = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			} catch (final PatternSyntaxException e) {
				// This is normal while the user is formulating the pattern
			}

			return result;
		}
	}

}
