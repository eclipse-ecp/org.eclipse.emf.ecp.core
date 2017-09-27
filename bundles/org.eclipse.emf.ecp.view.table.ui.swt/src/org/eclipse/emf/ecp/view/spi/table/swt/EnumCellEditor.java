/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPEnumCellEditor;
import org.eclipse.emf.ecp.view.internal.core.swt.ComboUtil;
import org.eclipse.emf.ecp.view.internal.core.swt.MatchItemComboViewer;
import org.eclipse.emf.ecp.view.internal.table.swt.FigureUtilities;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Generic {@link org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor ECPCellEditor} which is
 * applicable for all {@link EAttribute EAttributes} with a Single {@link EEnum} data type.
 *
 * @since 1.13
 */
public class EnumCellEditor extends ECPEnumCellEditor {

	private static final String EMPTY = ""; //$NON-NLS-1$
	private EEnum eEnum;
	private MatchItemComboViewer viewer;
	private int minWidth;
	private String text;
	private EnumCellEditorLabelProvider labelProvider;

	/**
	 * Default constructor.
	 *
	 * @param parent the parent
	 */
	public EnumCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Constructor allowing to specify a SWT style.
	 *
	 * @param parent the parent
	 * @param style the SWT style
	 */
	public EnumCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getValueProperty()
	 */
	@Override
	public IValueProperty getValueProperty() {
		return new ComboValueProperty();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#instantiate(org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {

		final EAttribute attribute = EAttribute.class.cast(feature);
		eEnum = EEnum.class.cast(attribute.getEAttributeType());
		final int literalsSize = getELiterals().size();
		viewer.getCCombo().setVisibleItemCount(literalsSize <= 25 ? literalsSize : 25);
		final Point emptyViewerSize = viewer.getCCombo().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		minWidth = 50;
		for (final EEnumLiteral literal : getELiterals()) {
			final String string = literal.getInstance().getLiteral();
			final int newWidth = FigureUtilities.getTextWidth(string, viewer.getCCombo().getFont());
			if (newWidth > minWidth) {
				minWidth = newWidth;
			}
		}
		minWidth += emptyViewerSize.x;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getFormatedString(java.lang.Object)
	 */
	@Override
	public String getFormatedString(Object value) {
		if (value != null) {
			final Enumerator enumerator = (Enumerator) value;
			return enumerator.getLiteral();
		}
		return EMPTY;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getColumnWidthWeight()
	 */
	@Override
	public int getColumnWidthWeight() {
		return 100;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getMinWidth()
	 */
	@Override
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object value) {
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getTargetToModelStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return new TargetToModelStrategy();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#getModelToTargetStrategy(org.eclipse.core.databinding.DataBindingContext)
	 */
	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return new ModelToTargetStrategy();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#getLayoutData()
	 */
	@Override
	public LayoutData getLayoutData() {
		final LayoutData data = new LayoutData();
		data.minimumWidth = 0;
		return data;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean editable) {
		viewer.getCCombo().setEnabled(editable);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(Composite parent) {
		viewer = new MatchItemComboViewer(new CCombo(parent, SWT.NONE)) {
			@Override
			public void onEnter() {
				final int selectedIndex = ComboUtil.getClosestMatchIndex(getCCombo().getItems(),
					getBuffer().asString());
				if (!getBuffer().isEmpty() && selectedIndex > -1) {
					final String closestMatch = getCCombo().getItems()[selectedIndex];
					final Optional<SelectedEnumeratorMapping> selectedMappping = SelectedEnumeratorMapping
						.findLiteral((SelectedEnumeratorMapping[]) getInput(), closestMatch);
					if (selectedMappping.isPresent()) {
						setSelection(new StructuredSelection(selectedMappping.get()));
					}

				} else {
					setClosestMatch(getCCombo().getText());
				}

				focusLost();
			}

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.emf.ecp.view.internal.core.swt.MatchItemComboViewer#onEscape()
			 */
			@Override
			protected void onEscape() {
				EnumCellEditor.this.fireCancelEditor();
			}
		};

		final CCombo combo = viewer.getCCombo();

		GridDataFactory.fillDefaults().grab(true, false).applyTo(combo);
		labelProvider = new EnumCellEditorLabelProvider();
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(labelProvider);
		return combo;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return viewer.getInput();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doSetFocus()
	 */
	@Override
	protected void doSetFocus() {
		final CCombo combo = viewer.getCCombo();
		if (combo == null || combo.isDisposed()) {
			return;
		}
		combo.setFocus();
		// Remove text selection and move the cursor to the end.
		final String text = combo.getText();
		if (text != null) {
			combo.setSelection(new Point(text.length(), text.length()));
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#activate(org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent)
	 */
	@Override
	public void activate(ColumnViewerEditorActivationEvent actEvent) {
		super.activate(actEvent);
		if (actEvent.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED) {
			final CCombo control = (CCombo) getControl();
			if (control != null && Character.isLetterOrDigit(actEvent.character)) {
				viewer.getBuffer().reset();
				// key pressed is not fired during activation
				viewer.getBuffer().addLast(actEvent.character);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#deactivate()
	 */
	@Override
	public void deactivate() {
		final CCombo control = (CCombo) getControl();

		if (control != null && !control.isDisposed()) {
			text = control.getText();
			text = text == null ? EMPTY : text;
		}

		final SelectedEnumeratorMapping[] selectedMapping = (SelectedEnumeratorMapping[]) viewer.getInput();
		if (selectedMapping != null) {
			final Optional<SelectedEnumeratorMapping> sel = SelectedEnumeratorMapping.findLiteral(selectedMapping,
				text);
			if (sel.isPresent()) {
				sel.get().setSelected(true);
				viewer.setSelection(new StructuredSelection(sel.get()));
			}
		}

		text = EMPTY;
		viewer.getBuffer().reset();

		// As the same cell editor is used for all the rows.
		// We need to reset the value to avoid the values are cached.
		if (control != null && !control.isDisposed()) {
			control.setText(""); //$NON-NLS-1$
		}
		super.deactivate();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(Object value) {
		viewer.setInput(value);
	}

	private String getComboText(SelectedEnumeratorMapping[] input) {
		final Optional<Enumerator> selected = SelectedEnumeratorMapping.findSelected(input);
		if (selected.isPresent()) {
			return selected.get().getLiteral();
		}
		return EMPTY;
	}

	/**
	 * Returns the {@link EEnum} is cell editor responsible for.
	 *
	 * @return the enum
	 */
	@Override
	public EEnum getEEnum() {
		return eEnum;
	}

	/**
	 * Label provider for the drop down.
	 *
	 *
	 */
	private class EnumCellEditorLabelProvider extends LabelProvider {

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			final SelectedEnumeratorMapping mapping = (SelectedEnumeratorMapping) element;
			return mapping.getEnumerator().getLiteral();
		}
	}

	/**
	 * Target to model update strategy.
	 *
	 *
	 */
	private class TargetToModelStrategy extends EMFUpdateValueStrategy {
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
		 */
		@Override
		public Object convert(Object value) {
			final SelectedEnumeratorMapping[] mapping = (SelectedEnumeratorMapping[]) value;
			final Optional<Enumerator> selected = SelectedEnumeratorMapping.findSelected(mapping);
			return selected.orNull();
		}
	}

	/**
	 * Model to target update strategy.
	 *
	 *
	 */
	private class ModelToTargetStrategy extends EMFUpdateValueStrategy {

		@Override
		public Object convert(Object value) {
			final Enumerator enumerator = (Enumerator) value;
			final SelectedEnumeratorMapping[] mapping = SelectedEnumeratorMapping.createFromList(getELiterals());
			SelectedEnumeratorMapping.select(mapping, enumerator);
			return mapping;
		}
	}

	/**
	 * Value property working on the combo.
	 *
	 */
	private class ComboValueProperty extends WidgetValueProperty {

		@Override
		public Object getValueType() {
			return CCombo.class;
		}

		@Override
		protected Object doGetValue(Object source) {
			return EnumCellEditor.this.getValue();
		}

		@Override
		protected void doSetValue(Object source, Object value) {

			EnumCellEditor.this.doSetValue(value);
		}

		@Override
		public IObservableValue observe(Object source) {
			return new ComboObservableValue();
		}
	}

	/**
	 * Observable value for the combo.
	 *
	 */
	private class ComboObservableValue extends AbstractObservableValue {

		private final EnumSelectionAdapter selectionAdapter;

		/**
		 * Default Constructor.
		 */
		ComboObservableValue() {
			selectionAdapter = new EnumSelectionAdapter();
			viewer.addSelectionChangedListener(selectionAdapter);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
		 */
		@Override
		public Object getValueType() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
		 */
		@Override
		protected Object doGetValue() {
			return viewer.getInput();
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
		 */
		@Override
		protected void doSetValue(Object value) {
			viewer.setInput(value);
			viewer.getCCombo().setText(getComboText((SelectedEnumeratorMapping[]) value));
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.databinding.observable.AbstractObservable#dispose()
		 */
		@Override
		public synchronized void dispose() {
			viewer.removeSelectionChangedListener(selectionAdapter);
			super.dispose();
		}

		/**
		 * Selection adapter for the combo.
		 *
		 *
		 */
		private class EnumSelectionAdapter implements ISelectionChangedListener {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				final SelectedEnumeratorMapping mapping = (SelectedEnumeratorMapping) ((IStructuredSelection) event
					.getSelection())
						.getFirstElement();
				if (mapping == null) {
					return;
				}
				final SelectedEnumeratorMapping[] allMapping = (SelectedEnumeratorMapping[]) viewer.getInput();
				SelectedEnumeratorMapping.deselectAll(allMapping);
				mapping.setSelected(!mapping.isSelected());
				viewer.getCCombo().setText(getComboText((SelectedEnumeratorMapping[]) viewer.getInput()));
				viewer.update(mapping, null);
				fireValueChange(Diffs.createValueDiff(!mapping.isSelected(), mapping.isSelected()));
			}
		}
	}
}
