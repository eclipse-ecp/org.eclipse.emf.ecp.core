/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.celleditor.rcp;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.table.celleditor.rcp.NativeWidgetHelper;
import org.eclipse.emf.ecp.view.spi.table.celleditor.rcp.NativeWidgetHelper.CheckBoxState;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Cell editor for boolean values.
 *
 * @author jfaltermeier
 *
 */
public class BooleanCellEditor extends CellEditor implements ECPCellEditor {

	private boolean editable;
	private final Composite parent;
	private boolean value;

	/**
	 * A constructor which takes only a parent.
	 *
	 * @param parent the {@link Composite} to use as a parent.
	 */
	public BooleanCellEditor(Composite parent) {
		super(parent);
		this.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getValueProperty()
	 */
	@Override
	public IValueProperty getValueProperty() {
		return new WidgetValueProperty() {

			@Override
			public Object getValueType() {
				return Boolean.class;
			}

			@Override
			protected Object doGetValue(Object source) {
				return BooleanCellEditor.this.doGetValue();
			}

			@Override
			protected void doSetValue(Object source, Object value) {
				BooleanCellEditor.this.doSetValue(value);
			}

			@Override
			public IObservableValue observe(Object source) {
				return new BooleanCellEditorObservableValue();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#instantiate(org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {
		// no op
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getFormatedString(java.lang.Object)
	 */
	@Override
	public String getFormatedString(Object value) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getColumnWidthWeight()
	 */
	@Override
	public int getColumnWidthWeight() {
		return 25;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getTargetToModelStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return new EMFUpdateValueStrategy();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getModelToTargetStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return new EMFUpdateValueStrategy();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object value) {
		final CheckBoxState state = (Boolean) value ? CheckBoxState.checked : CheckBoxState.unchecked;
		return NativeWidgetHelper.getCheckBoxImage(parent, state);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CheckboxCellEditor#activate()
	 */
	@Override
	public void activate() {
		if (editable) {
			value = !value;
			fireApplyEditorValue();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(Composite parent) {
		// intended null since there is no actual cell editor. Activate is used as trigger.
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return value ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doSetFocus()
	 */
	@Override
	protected void doSetFocus() {
		// ignored
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(Object value) {
		Assert.isTrue(value instanceof Boolean);
		this.value = ((Boolean) value).booleanValue();
	}

	@Override
	public void activate(ColumnViewerEditorActivationEvent activationEvent) {
		if (activationEvent.eventType != ColumnViewerEditorActivationEvent.TRAVERSAL) {
			super.activate(activationEvent);
		}
	}

	/**
	 * {@link IObservableValue} for the boolean cell editor.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class BooleanCellEditorObservableValue extends AbstractObservableValue {

		private final ICellEditorListener listener;

		BooleanCellEditorObservableValue() {
			listener = new ICellEditorListener() {

				@Override
				public void editorValueChanged(boolean oldValidState, boolean newValidState) {
					// no op
				}

				@Override
				public void cancelEditor() {
					// no op
				}

				@Override
				public void applyEditorValue() {
					fireValueChange(Diffs.createValueDiff(!Boolean.class.cast(BooleanCellEditor.this.getValue()),
						BooleanCellEditor.this.getValue()));
				}
			};
			BooleanCellEditor.this.addListener(listener);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
		 */
		@Override
		public Object getValueType() {
			return Boolean.class;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
		 */
		@Override
		protected Object doGetValue() {
			return BooleanCellEditor.this.getValue();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
		 */
		@Override
		protected void doSetValue(Object value) {
			BooleanCellEditor.this.setValue(value);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.AbstractObservable#dispose()
		 */
		@Override
		public synchronized void dispose() {
			BooleanCellEditor.this.removeListener(listener);
			super.dispose();
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getMinWidth()
	 */
	@Override
	public int getMinWidth() {
		return 0;
	}
}
