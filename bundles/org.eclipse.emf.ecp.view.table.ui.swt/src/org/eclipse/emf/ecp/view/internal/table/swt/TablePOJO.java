/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen
 * @author Johannes
 */
public class TablePOJO {

	final LabelService labelService;
	final VTableControl tableControl;
	final DatabindingService databindingService;

	public TablePOJO(VTableControl tableControl, LabelService labelService, DatabindingService databindingService) {
		if (tableControl == null) {
			throw new IllegalArgumentException("TableControl may not be null");
		}
		this.tableControl = tableControl;
		if (labelService == null) {
			throw new IllegalArgumentException("LabelTextService may not be null");
		}
		this.labelService = labelService;
		if (databindingService == null) {
			throw new IllegalArgumentException("DatabindingService must not be null!");
		}
		this.databindingService = databindingService;
	}

	public SWTGridDescription getGridDescription(AbstractSWTRenderer<VTableControl> renderer) {
		if (renderer == null)
		{
			throw new IllegalArgumentException("Renderer must not be null!"); //$NON-NLS-1$
		}
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, renderer);
	}

	public Composite render(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTitle(composite);
		createTableComposite(composite);

		return composite;
	}

	void createTitle(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTitleLabel(composite);
		createValidationLabel(composite);
		createButtons(composite);
	}

	void createTitleLabel(Composite composite) {
		final Label label = new Label(composite, SWT.NONE);
		final String labelText = labelService.getLabelText(tableControl);
		label.setText(labelText);
	}

	void createValidationLabel(Composite composite) {
		final Label validation = new Label(composite, SWT.NONE);
	}

	void createButtons(Composite parent) {
		final Composite buttonComposite = new Composite(parent, SWT.NONE);
		createAddButton(buttonComposite);
		createRemoveButton(buttonComposite);
		addCustomButtons(buttonComposite);
	}

	void createAddButton(Composite buttonComposite) {
		final Button addButton = new Button(buttonComposite, SWT.PUSH);
	}

	void createRemoveButton(Composite buttonComposite) {
		final Button removeButton = new Button(buttonComposite, SWT.PUSH);
	}

	/**
	 * Here you can add your own additional buttons to the buttons bar.
	 *
	 * @param parent The Composite to add your stuff to
	 */
	protected void addCustomButtons(Composite parent) {
		// empty implementation intentionally
	}

	void createTableComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		createTable(composite);
	}

	void createTable(Composite parent) {
		final TableViewer tableViewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION
			| SWT.BORDER);
		final ObservableListContentProvider cp = new ObservableListContentProvider();
		tableViewer.setContentProvider(cp);
		final IObservableList list = databindingService.getObservableList(tableControl.getDomainModelReference());
		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) tableControl.getDomainModelReference();
		for (final VDomainModelReference dmr : tableDMR.getColumnDomainModelReferences()) {
			final IValueProperty valueProperty = databindingService.getValueProperty(
				EReference.class.cast(list.getElementType()), dmr);

			final TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
			// column.getColumn().setText(labelService.getLabelText(tableControl));
			// column.getColumn().setToolTipText(labelService.getDescriptionText(dmr));
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(false);

			column.setLabelProvider(new ObservableMapCellLabelProvider(
				valueProperty.observeDetail(cp.getKnownElements())));

			// final EditingSupport editingSupport = new InlineEditingSupport(tableViewer,
			// databinding.getDataBindingContext(), valueProperty);
			// column.setEditingSupport(editingSupport);
		}
		tableViewer.setInput(list);
	}

}
