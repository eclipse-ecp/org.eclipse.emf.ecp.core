/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPViewerAwareCellEditor;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A cell editor, which displays the multi references feature as a readonly text.
 *
 * @since 1.18
 *
 */
public class MultiReferenceCellEditor extends CellEditor implements ECPCellEditor, ECPViewerAwareCellEditor {

	private ComposedAdapterFactory caf;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private Composite parent;
	private AbstractTableViewer tableViewer;
	private EReference tableRefrence;
	private ModelChangeListener modelChangeListener;
	private ViewModelContext viewModelContext;

	/**
	 * Default constructor.
	 *
	 * @param parent the parent
	 */
	public MultiReferenceCellEditor(Composite parent) {
		super(parent);
		this.parent = parent;
	}

	/**
	 * Constructor allowing to specify a SWT style.
	 *
	 * @param parent the parent
	 * @param style the SWT style
	 */
	public MultiReferenceCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public IValueProperty getValueProperty() {
		return new WidgetValueProperty() {

			@Override
			public Object getValueType() {
				return String.class;
			}

			@Override
			protected Object doGetValue(Object source) {
				return getFormatedString(source);
			}

			@Override
			protected void doSetValue(Object source, Object value) {
			}

			@Override
			public IObservableValue observe(Object source) {
				return super.observe(parent);
			}
		};
	}

	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {
		caf = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(caf);
		this.viewModelContext = viewModelContext;
		modelChangeListener = new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (tableViewer == null || tableRefrence == null) {
					return;
				}
				if (notification.getNotifier().eContainingFeature() == feature) {
					EObject tableRow = notification.getNotifier();
					while (tableRow != null && tableRow.eContainingFeature() != tableRefrence) {
						tableRow = tableRow.eContainer();
					}
					if (tableRow != null) {
						tableViewer.update(tableRow, null);
					}
				}
			}
		};
		viewModelContext.registerDomainChangeListener(modelChangeListener);
	}

	@Override
	public String getFormatedString(Object value) {
		// no need to handle null and not lists as this editor is only used for multi references
		return adapterFactoryItemDelegator.getText(value);
	}

	@Override
	public Image getImage(Object value) {
		return null;
	}

	@Override
	public int getColumnWidthWeight() {
		return 100;
	}

	@Override
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return null;
	}

	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return null;
	}

	@Override
	public void setEditable(boolean editable) {
		// intentionally left empty
	}

	@Override
	public int getMinWidth() {
		return 50;
	}

	@Override
	protected Control createControl(Composite parent) {
		return null;
	}

	@Override
	protected Object doGetValue() {
		return null;
	}

	@Override
	protected void doSetFocus() {
		// intentionally left empty
	}

	@Override
	protected void doSetValue(Object value) {
		// intentionally left empty
	}

	@Override
	public void dispose() {
		if (caf != null) {
			caf.dispose();
		}
		if (viewModelContext != null) {
			viewModelContext.unregisterDomainChangeListener(modelChangeListener);
		}
		super.dispose();
	}

	@Override
	public void setTableViewer(AbstractTableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	@Override
	public void setTableFeature(EReference tableRefrence) {
		this.tableRefrence = tableRefrence;
	}

}
