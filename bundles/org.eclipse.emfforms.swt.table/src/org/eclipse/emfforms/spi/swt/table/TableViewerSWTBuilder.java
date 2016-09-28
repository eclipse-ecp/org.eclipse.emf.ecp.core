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

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.internal.swt.table.DefaultTableControlSWTCustomization;
import org.eclipse.emfforms.internal.swt.table.util.StaticCellLabelProviderFactory;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Builder class for creating a {@link TableViewerComposite}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 *
 */
public class TableViewerSWTBuilder {
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
		customization = new DefaultTableControlSWTCustomization();
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
	protected DefaultTableControlSWTCustomization getCustomization() {
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
	 * The default implementation will create a viewer with the {@link SWT#MULTI}, {@link SWT#V_SCROLL},
	 * {@link SWT#FULL_SELECTION} and {@link SWT#BORDER} style bits. The table will show the
	 * {@link org.eclipse.swt.widgets.Table#setHeaderVisible(boolean) header} and will show
	 * {@link org.eclipse.swt.widgets.Table#setLinesVisible(boolean) lines}.
	 * </p>
	 *
	 * @param creator the {@link TableViewerCreator}
	 * @return self
	 */
	public TableViewerSWTBuilder customizeTableViewerCreation(TableViewerCreator creator) {
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

	// BEGIN COMPLEX CODE
	/**
	 * Adds a column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @param image the column image
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProviderFactory labelProvider,
		EditingSupportCreator editingSupport,
		Image image) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				columnText,
				tooltipText,
				labelProvider,
				editingSupport,
				image));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @param image the column image
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport,
		Image image) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				columnText,
				tooltipText,
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				image));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @param image the column image
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		String columnText,
		String tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport,
		Image image) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				Observables.constantObservableValue(columnText, String.class),
				Observables.constantObservableValue(tooltipText, String.class),
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				image));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				columnText,
				tooltipText,
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				null));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		String columnText,
		String tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				Observables.constantObservableValue(columnText, String.class),
				Observables.constantObservableValue(tooltipText, String.class),
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				null));

		return this;
	}

	/**
	 * Adds a column. The column is resizeable, not moveable, uses the {@link SWT#NONE} style bit, has a weight of 100
	 * and a min width of 0 pixel.
	 *
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport) {

		customization.addColumn(
			new ColumnDescriptionImpl(
				true,
				false,
				SWT.NONE,
				100,
				0,
				columnText,
				tooltipText,
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				null));

		return this;
	}

	/**
	 * Adds a column. The column is resizeable, not moveable, uses the {@link SWT#NONE} style bit, has a weight of 100
	 * and a min width of 0 pixel.
	 *
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @param editingSupport the editing support
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		String columnText,
		String tooltipText,
		CellLabelProvider labelProvider,
		EditingSupportCreator editingSupport) {

		customization.addColumn(
			new ColumnDescriptionImpl(
				true,
				false,
				SWT.NONE,
				100,
				0,
				Observables.constantObservableValue(columnText, String.class),
				Observables.constantObservableValue(tooltipText, String.class),
				new StaticCellLabelProviderFactory(labelProvider),
				editingSupport,
				null));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a read-only column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProvider labelProvider) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				columnText,
				tooltipText,
				new StaticCellLabelProviderFactory(labelProvider),
				null,
				null));

		return this;
	}

	// BEGIN COMPLEX CODE
	/**
	 * Adds a read-only column.
	 *
	 * @param resizeable whether the column is resizeable or not
	 * @param moveable whether the column is moveable or not
	 * @param styleBits the style bits for the column
	 * @param weight the weight of the column
	 * @param minWidth the min width in pixels
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		boolean resizeable,
		boolean moveable,
		int styleBits,
		int weight,
		int minWidth,
		String columnText,
		String tooltipText,
		CellLabelProvider labelProvider) {
		// END COMPLEX CODE

		customization.addColumn(
			new ColumnDescriptionImpl(
				resizeable,
				moveable,
				styleBits,
				weight,
				minWidth,
				Observables.constantObservableValue(columnText, String.class),
				Observables.constantObservableValue(tooltipText, String.class),
				new StaticCellLabelProviderFactory(labelProvider),
				null,
				null));

		return this;
	}

	/**
	 * Adds a read-only column. The column is resizeable, not moveable, uses the {@link SWT#NONE} style bit, has a
	 * weight of 100 and a min width of 0 pixel.
	 *
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		IObservableValue columnText,
		IObservableValue tooltipText,
		CellLabelProvider labelProvider) {

		customization.addColumn(
			new ColumnDescriptionImpl(
				true,
				false,
				SWT.NONE,
				100,
				0,
				columnText,
				tooltipText,
				new StaticCellLabelProviderFactory(labelProvider),
				null,
				null));

		return this;
	}

	/**
	 * Adds a read-only column. The column is resizeable, not moveable, uses the {@link SWT#NONE} style bit, has a
	 * weight of 100 and a min width of 0 pixel.
	 *
	 * @param columnText the column text
	 * @param tooltipText the tooltip text
	 * @param labelProvider the label provider
	 * @return self
	 */
	public TableViewerSWTBuilder addColumn(
		String columnText,
		String tooltipText,
		CellLabelProvider labelProvider) {

		customization.addColumn(
			new ColumnDescriptionImpl(
				true,
				false,
				SWT.NONE,
				100,
				0,
				Observables.constantObservableValue(columnText, String.class),
				Observables.constantObservableValue(tooltipText, String.class),
				new StaticCellLabelProviderFactory(labelProvider),
				null,
				null));

		return this;
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link TableViewerComposite} with the desired customizations.
	 *
	 * @return the {@link TableViewerComposite}
	 */
	public AbstractTableViewerComposite create() {
		return new TableViewerComposite(composite, swtStyleBits, input, customization, title, tooltip);
	}
}
