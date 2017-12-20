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
 * Mat Hansen - builder refactoring
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.emfforms.internal.swt.table.DefaultTableControlSWTCustomization;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Builder class for creating a {@link TableViewerComposite}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 * @author Mat Hansen
 *
 */
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
					configureTable(TableConfigurationBuilder.usingDefaults()
						.inheritFeatures(features)
						.build());
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
	 * The {@link DefaultButtonBarBuilder default implementation} will add an add and a remove button.
	 * </p>
	 *
	 * @param builder the {@link ButtonBarBuilder}
	 * @return self
	 */
	public TableViewerSWTBuilder customizeButtons(ButtonBarBuilder builder) {
		customization.setButtonBarBuilder(builder);
		return this;
	}

	/**
	 * <p>
	 * Use this method to customize the way new elements are created.
	 * </p>
	 * <p>
	 * This may only be used in conjunction with the {@link DefaultButtonBarBuilder}
	 * </p>
	 *
	 * @param creator the creator
	 * @return self
	 */
	public TableViewerSWTBuilder customizeElementCreation(NewElementCreator<Object, Button> creator) {
		customization.setNewElementCreator(creator);
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

	@Override
	public Set<Feature> getSupportedFeatures() {
		return new LinkedHashSet<Feature>(Arrays.asList(TableConfiguration.FEATURES));
	}

	@Override
	public Set<Feature> getEnabledFeatures() {
		return features;
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link TableViewerComposite} with the desired customizations.
	 *
	 * @return the {@link TableViewerComposite}
	 */
	public AbstractTableViewerComposite build() {

		final AbstractTableViewerComposite viewerComposite = //
			new TableViewerComposite(composite, swtStyleBits, input, customization, title, tooltip);

		viewerComposite.setData(TableConfiguration.ID, customization.getTableConfiguration());

		return viewerComposite;
	}

}
