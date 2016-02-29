/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTCustomization.ColumnDescription;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of the {@link ColumnDescription}.
 *
 * @author Johannes Faltermeier
 *
 */
public class ColumnDescriptionImpl implements ColumnDescription {

	private final boolean resizeable;
	private final boolean moveable;
	private final int styleBits;
	private final int weight;
	private final int minWidth;
	private final IObservableValue columnText;
	private final IObservableValue tooltipText;
	private final CellLabelProviderFactory labelProvider;
	private final Optional<EditingSupportCreator> editingSupport;
	private final Optional<Image> image;

	// BEGIN COMPLEX CODE
	/**
	 * Constructs a new {@link ColumnDescription}.
	 *
	 * @param resizeable resizeable
	 * @param moveable moveable
	 * @param styleBits styleBits
	 * @param weight weight
	 * @param minWidth minWidth
	 * @param columnText columnText
	 * @param tooltipText tooltipText
	 * @param labelProvider labelProvider
	 * @param editingSupport editingSupport. May be <code>null</code> to indicate that there is no editing support
	 */
	public ColumnDescriptionImpl(
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
		this.resizeable = resizeable;
		this.moveable = moveable;
		this.styleBits = styleBits;
		this.weight = weight;
		this.minWidth = minWidth;
		this.columnText = columnText;
		this.tooltipText = tooltipText;
		this.labelProvider = labelProvider;
		this.editingSupport = Optional.ofNullable(editingSupport);
		this.image = Optional.ofNullable(image);
	}

	@Override
	public boolean isResizeable() {
		return resizeable;
	}

	@Override
	public boolean isMoveable() {
		return moveable;
	}

	@Override
	public int getStyleBits() {
		return styleBits;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public int getMinWidth() {
		return minWidth;
	}

	@Override
	public IObservableValue getColumnText() {
		return columnText;
	}

	@Override
	public IObservableValue getColumnTooltip() {
		return tooltipText;
	}

	@Override
	public CellLabelProvider createLabelProvider(TableViewer columnViewer) {
		return labelProvider.createCellLabelProvider(columnViewer);
	}

	@Override
	public Optional<EditingSupport> createEditingSupport(TableViewer columnViewer) {
		if (editingSupport.isPresent()) {
			return Optional.of(editingSupport.get().createEditingSupport(columnViewer));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Image> getColumnImage() {
		return image;
	}

}
