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
 * Mat Hansen - builder refactoring
 * Christian W. Damus - bugs 534829, 530314
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.internal.swt.table.DefaultTableControlSWTCustomization;
import org.eclipse.emfforms.spi.swt.table.action.ActionBar;
import org.eclipse.emfforms.spi.swt.table.action.ActionConfiguration;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Composite;

/**
 * Builder class for creating a {@link TableViewerComposite}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 * @author Mat Hansen
 *
 */
@SuppressWarnings("deprecation")
public class TableViewerSWTBuilder extends AbstractFeatureAwareBuilder<TableViewerSWTBuilder> {

	private boolean tableConfigured;

	/** The parent composite. */
	private final Composite composite;
	/** The style bits. */
	private final int swtStyleBits;
	/** The input object. */
	private final Object input;
	/** The table control customization. */
	private final DefaultTableControlSWTCustomization customization;
	/** The title. */
	private final IObservableValue title;
	/** The tooltip. */
	private final IObservableValue tooltip;

	private final Set<Feature> features = new LinkedHashSet<Feature>();

	/**
	 * @param composite the parent {@link Composite}
	 * @param swtStyleBits the swt style bits
	 * @param input the input object
	 * @param title the title
	 * @param tooltip the tooltip
	 */
	@SuppressWarnings("rawtypes")
	protected TableViewerSWTBuilder(Composite composite, int swtStyleBits, Object input, IObservableValue title,
		IObservableValue tooltip) {

		this.composite = composite;
		this.swtStyleBits = swtStyleBits;
		this.input = input;
		this.title = title;
		this.tooltip = tooltip;
		customization = new DefaultTableControlSWTCustomization() {

			@Override
			public TableConfiguration getTableConfiguration() {
				// if the table hasn't been configured, do it now (using defaults)
				if (!tableConfigured) {
					configureTable(TableConfigurationBuilder.withFeatures(features).build());
				}
				return super.getTableConfiguration();
			}

		};
	}

	/**
	 * @return the composite
	 */
	protected Composite getComposite() {
		return composite;
	}

	/**
	 * @return the swtStyleBits
	 */
	protected int getSwtStyleBits() {
		return swtStyleBits;
	}

	/**
	 * @return the input
	 */
	protected Object getInput() {
		return input;
	}

	/**
	 * @return the customization
	 */
	public TableViewerSWTCustomization<?> getCustomization() {
		return customization;
	}

	/**
	 * @return the title
	 */
	protected IObservableValue getTitle() {
		return title;
	}

	/**
	 * @return the tooltip
	 */
	protected IObservableValue getTooltip() {
		return tooltip;
	}

	/**
	 * <p>
	 * Use this method to customize the way title, validation, buttons and the tableviewer are arranged.
	 * </p>
	 * <p>
	 * The default implementation will create a title bar with title to left, a validation label in the middle and a
	 * button bar on the right. Below the title bar the viewer will be created
	 * </p>
	 *
	 * @param builder the {@link TableViewerCompositeBuilder}
	 * @return self
	 */
	public TableViewerSWTBuilder customizeCompositeStructure(TableViewerCompositeBuilder builder) {
		customization.setTableViewerCompositeBuilder(builder);
		return this;
	}

	/**
	 * <p>
	 * Use this method to create the actual TableViewer.
	 * </p>
	 * <p>
	 * The default implementation will create a viewer with the SWT#MULTI, SWT#V_SCROLL,
	 * FULL_SELECTION and SWT#BORDER style bits. The table will show the
	 * {@link org.eclipse.swt.widgets.Table#setHeaderVisible(boolean) header} and will show
	 * {@link org.eclipse.swt.widgets.Table#setLinesVisible(boolean) lines}.
	 * </p>
	 *
	 * @param creator the {@link TableViewerCreator}
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public TableViewerSWTBuilder customizeTableViewerCreation(
		TableViewerCreator<? extends AbstractTableViewer> creator) {
		customization.setTableViewerCreator(creator);
		return this;
	}

	/**
	 * <p>
	 * Use this method to set a {@link ViewerComparator} on the table.
	 * </p>
	 * <p>
	 * The default implementation does not add a comparator.
	 * </p>
	 *
	 * @param comparator the {@link ViewerComparator}
	 * @return self
	 */
	public TableViewerSWTBuilder customizeComparator(ViewerComparator comparator) {
		customization.setViewerComparator(comparator);
		return this;
	}

	/**
	 * <p>
	 * Use this method to set a different content provider on the viewer.
	 * </p>
	 * <p>
	 * The default implementation uses a {@link org.eclipse.jface.databinding.viewers.ObservableListContentProvider
	 * ObservableListContentProvider}.
	 * </p>
	 *
	 * @param provider the {@link IContentProvider} to use
	 * @return self
	 */
	public TableViewerSWTBuilder customizeContentProvider(IContentProvider provider) {
		customization.setContentProvider(provider);
		return this;
	}

	/**
	 * <p>
	 * Use this method to customize the way the button bar is filled.
	 * </p>
	 * <p>
	 * The default behavior will not add any buttons.
	 * </p>
	 *
	 * @param actionBar the {@link ActionBar}
	 * @return self
	 */
	@SuppressWarnings("unchecked")
	public TableViewerSWTBuilder customizeActionBar(ActionBar<? extends Viewer> actionBar) {
		customization.setActionBar(actionBar);
		return this;
	}

	/**
	 * <p>
	 * Use this method to customize the key bindings for the table viewer.
	 * </p>
	 * <p>
	 * The default behavior will not register any bindings.
	 * </p>
	 *
	 * @param actionConfiguration the {@link ActionConfiguration}
	 * @return self
	 */
	public TableViewerSWTBuilder customizeActionConfiguration(ActionConfiguration actionConfiguration) {
		customization.setActionConfiguration(actionConfiguration);
		return this;
	}

	/**
	 * <p>
	 * Use this method to customize drag&drop.
	 * </p>
	 * <p>
	 * The default behaviour disables drag&drop.
	 * </p>
	 *
	 * @param provider the provider
	 * @return self
	 */
	public TableViewerSWTBuilder customizeDragAndDrop(DNDProvider provider) {
		customization.setDND(provider);
		return this;
	}

	/**
	 * Configures the current table instance using the given configuration.
	 *
	 * @param tableConfiguration the {@link TableConfiguration} to add
	 * @return self
	 */
	public TableViewerSWTBuilder configureTable(TableConfiguration tableConfiguration) {
		customization.configureTable(tableConfiguration);
		tableConfigured = true;
		return this;
	}

	/**
	 * Adds a new column.
	 *
	 * @param columnConfiguration the {@link ColumnConfiguration} to add
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(ColumnConfiguration columnConfiguration) {
		customization.addColumn(columnConfiguration);
		return this;
	}

	/**
	 * @deprecated Since 1.21, use the {@link #showHideColumns(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHideColumns(boolean)
	 * @see #columnSubstringFilter(boolean)
	 * @see #columnRegexFilter(boolean)
	 */
	@Override
	@Deprecated
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(TableConfiguration.ALL_FEATURES);
	}

	/**
	 * @deprecated Since 1.21, use the {@link #showHideColumns(boolean)} and similar
	 *             builder methods, instead
	 * @see #showHideColumns(boolean)
	 * @see #columnSubstringFilter(boolean)
	 * @see #columnRegexFilter(boolean)
	 */
	@Override
	@Deprecated
	public Set<Feature> getEnabledFeatures() {
		return features;
	}

	/**
	 * Set whether support for users to show and hide columns is installed.
	 *
	 * @param showHideColumns {@code true} to enable showing and hiding of columns; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableViewerSWTBuilder showHideColumns(boolean showHideColumns) {
		return showHideColumns ? enableFeature(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW);
	}

	/**
	 * Set whether support for users to show a simple substring-matching filter on columns
	 * is installed.
	 *
	 * @param columnSubstringFilter {@code true} to enable the substring filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableViewerSWTBuilder columnSubstringFilter(boolean columnSubstringFilter) {
		return columnSubstringFilter ? enableFeature(TableConfiguration.FEATURE_COLUMN_FILTER)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_FILTER);
	}

	/**
	 * Set whether support for users to show a regular expression filter on columns
	 * is installed.
	 *
	 * @param columnRegexFilter {@code true} to enable the regex filter; {@code false} to disable it
	 * @return this builder, for fluent chaining
	 *
	 * @since 1.21
	 */
	public TableViewerSWTBuilder columnRegexFilter(boolean columnRegexFilter) {
		return columnRegexFilter ? enableFeature(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER)
			: disableFeature(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER);
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link TableViewerComposite} with the desired customizations.
	 *
	 * @return the {@link TableViewerComposite}
	 */
	public AbstractTableViewerComposite<? extends AbstractTableViewer> build() {

		final AbstractTableViewerComposite<? extends AbstractTableViewer> viewerComposite = //
			new TableViewerComposite(composite, swtStyleBits, input, customization, title, tooltip);

		viewerComposite.setData(TableConfiguration.ID, customization.getTableConfiguration());

		return viewerComposite;
	}

}
