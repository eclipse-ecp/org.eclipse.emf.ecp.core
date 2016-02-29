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
package org.eclipse.emfforms.internal.swt.table.util;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationListener;
import org.eclipse.jface.viewers.ColumnViewerEditorDeactivationEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * {@link EditingSupport} for a value described by a {@link org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
 * VDomainModelReference}.
 *
 * @author Johannes Faltermeier
 *
 */
public class DMREditingSupport extends EditingSupport {

	private final CellEditor cellEditor;

	private final IValueProperty valueProperty;

	private EditingState editingState;

	private final ColumnViewerEditorActivationListenerHelper activationListener = new ColumnViewerEditorActivationListenerHelper();

	/**
	 * Constructs a new {@link DMREditingSupport}.
	 *
	 * @param viewer the {@link ColumnViewer}
	 * @param cellEditor the {@link CellEditor}
	 * @param valueProperty the {@link IValueProperty} for the value of the cell
	 */
	public DMREditingSupport(ColumnViewer viewer, CellEditor cellEditor, IValueProperty valueProperty) {
		super(viewer);
		this.cellEditor = cellEditor;
		this.valueProperty = valueProperty;
		if (valueProperty == null) {
			throw new IllegalArgumentException("ValueProperty may not be null"); //$NON-NLS-1$
		}
	}

	/**
	 * Constructs a new {@link DMREditingSupport}.
	 *
	 * @param columnViewer the {@link ColumnViewer}
	 * @param cellEditor the {@link CellEditor}
	 * @param domainModelReference the domain model reference of the column value
	 * @param parent the domain object of the column value
	 */
	public DMREditingSupport(ColumnViewer columnViewer, CellEditor cellEditor,
		VDomainModelReference domainModelReference, EObject parent) {
		this(columnViewer, cellEditor,
			getValueProperty(domainModelReference, parent));
	}

	private static IValueProperty getValueProperty(VDomainModelReference domainModelReference, EObject parent) {
		try {
			return getService(EMFFormsDatabinding.class).getValueProperty(domainModelReference, parent);
		} catch (final DatabindingFailedException ex) {
			return null;
		}
	}

	@Override
	protected Object getValue(Object element) {
		// no op
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		// no op
	}

	@Override
	protected void initializeCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		final EMFDataBindingContext bindingContext = new EMFDataBindingContext();
		final IObservableValue target = doCreateCellEditorObservable(cellEditor);
		final IObservableValue model = valueProperty.observe(cell.getElement());
		final Binding binding = bindingContext.bindValue(target, model);
		editingState = new EditingState(bindingContext, binding, target, model);
		getViewer().getColumnViewerEditor().addEditorActivationListener(activationListener);
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	private IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
		return WidgetProperties.text(SWT.FocusOut).observe(cellEditor.getControl());
	}

	@Override
	protected final void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
		editingState.binding.updateTargetToModel();
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	private static <T> T getService(Class<T> clazz) {
		final Bundle bundle = FrameworkUtil.getBundle(DMRCellLabelProvider.class);
		final BundleContext bundleContext = bundle.getBundleContext();
		final ServiceReference<T> serviceReference = bundleContext.getServiceReference(clazz);
		final T service = bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);
		return service;
	}

	/**
	 * A ColumnViewerEditorActivationListener to reset the cells after focus lost.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private class ColumnViewerEditorActivationListenerHelper extends ColumnViewerEditorActivationListener {

		@Override
		public void afterEditorActivated(ColumnViewerEditorActivationEvent event) {
			// no op
		}

		@Override
		public void afterEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
			editingState.dispose();
			editingState = null;

			getViewer().getColumnViewerEditor().removeEditorActivationListener(this);
			final ViewerCell focusCell = getViewer().getColumnViewerEditor().getFocusCell();
			if (focusCell != null) {
				getViewer().update(focusCell.getElement(), null);
			}
		}

		@Override
		public void beforeEditorActivated(ColumnViewerEditorActivationEvent event) {
			// do nothing
		}

		@Override
		public void beforeEditorDeactivated(ColumnViewerEditorDeactivationEvent event) {
			// do nothing
		}
	}

	/** @author Johannes Faltermeier */
	private class EditingState {
		private final EMFDataBindingContext bindingContext;

		private final IObservableValue target;

		private final IObservableValue model;

		private final Binding binding;

		EditingState(EMFDataBindingContext bindingContext, Binding binding, IObservableValue target,
			IObservableValue model) {
			this.binding = binding;
			this.target = target;
			this.model = model;
			this.bindingContext = bindingContext;
		}

		void dispose() {
			binding.dispose();
			target.dispose();
			model.dispose();
			bindingContext.dispose();
		}
	}
}