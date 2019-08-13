/*******************************************************************************
 * Copyright (c) 2017-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Christian W. Damus - rework for i18n support and code cleanup
 * Lucas Koehler - rework integration
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static java.lang.Math.min;
import static org.eclipse.emf.ecp.view.internal.table.swt.FigureUtilities.getTextWidth;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.IEMFObservable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPEnumCellEditor;
import org.eclipse.emf.ecp.view.internal.core.swt.MatchItemComboViewer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emfforms.spi.common.BundleResolver;
import org.eclipse.emfforms.spi.common.BundleResolver.NoBundleFoundException;
import org.eclipse.emfforms.spi.common.BundleResolverFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.Bundle;

/**
 * Generic {@link org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditor ECPCellEditor} which is
 * applicable for all {@link EAttribute EAttributes} with a Single {@link EEnum} data type.
 * This cell editor uses the EMF.Edit item provider to determine
 * the model's proper choice of values for an {@link EEnum} attribute. Additionally, it filters out enum literals with
 * are marked as <code>isInputtable=false</code> with a custom annotation.
 *
 * @author Christian W. Damus
 * @author Lucas Koehler
 * @since 1.22
 */
public class ItemProviderEnumCellEditor extends ECPEnumCellEditor {

	/**
	 * Template to generate the localization key for an enum value. First parameter is the EEnum's type name, second
	 * parameter is the value's name.
	 */
	private static final String LOCALIZATION_KEY_TEMPLATE = "_UI_%s_%s_literal"; //$NON-NLS-1$

	private EMFFormsLocalizationService l10n;
	private MatchItemComboViewer viewer;
	private int minWidth;

	private EAttribute attribute;
	private BundleResolver bundleResolver = BundleResolverFactory.createBundleResolver();
	/** The edit bundle for the EEnum renderered by this cell editor. */
	private Optional<Bundle> editBundle;
	private Optional<EObject> source = Optional.empty();

	/**
	 * Initializes me with my parent.
	 *
	 * @param parent my parent composite
	 */
	public ItemProviderEnumCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Initializes me with my parent and style.
	 *
	 * @param parent my parent composite
	 * @param style my style bits
	 */
	public ItemProviderEnumCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Initializes me with my parent, style, and custom {@link BundleResolver} and {@link EMFFormsLocalizationService}.
	 *
	 * @param parent my parent composite
	 * @param style my style bits
	 * @param bundleResolver custom {@link BundleResolver}
	 * @param l10n custom {@link EMFFormsLocalizationService}
	 */
	public ItemProviderEnumCellEditor(Composite parent, int style, BundleResolver bundleResolver,
		EMFFormsLocalizationService l10n) {
		this(parent, style);
		this.bundleResolver = bundleResolver;
		this.l10n = l10n;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (!source.isPresent()) {
					// Extract the source model element from the data binding
					source = inferSource(databindingContext);
				}

				return value;
			}
		};
	}

	@Override
	@SuppressWarnings("rawtypes")
	public UpdateValueStrategy getTargetToModelStrategy(DataBindingContext databindingContext) {
		return new UpdateValueStrategy();
	}

	@Override
	protected Control createControl(Composite parent) {
		viewer = new MatchItemComboViewer(new CCombo(parent, SWT.NONE)) {
			@Override
			public void onEnter() {
				super.onEnter();
				applySelection();
				focusLost();
			}

			@Override
			protected void onEscape() {
				fireCancelEditor();
			}
		};

		final CCombo combo = viewer.getCCombo();
		GridDataFactory.fillDefaults().grab(true, false).applyTo(combo);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new EnumLabelProvider());
		combo.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				applySelection();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// nothing to do here
			}
		});
		return combo;
	}

	private void applySelection() {
		final CCombo combo = viewer.getCCombo();
		final int selection = combo.getSelectionIndex();
		if (selection >= 0) {
			final List<?> input = (List<?>) viewer.getInput();
			viewer.setSelection(new StructuredSelection(input.get(selection)));
		}
	}

	/**
	 * Gets the proper choice of values provided by the item-provider
	 * of the source object of our data binding for the attribute that
	 * is bound. In addition, we remove all choices annotated by our custom annotation.
	 * <br/>
	 * If the property descriptor is not available or does not return any choice, the enum's literals minus the
	 * annotated ones are returned.
	 *
	 * @return The available enum values, might be empty but never <code>null</code>
	 */
	protected List<?> getChoiceOfValues() {
		final Collection<?> providerChoices = getPropertyDescriptor()
			// if the propertyDescriptor is present, we have a source
			.map(descriptor -> descriptor.getChoiceOfValues(getSource().get()))
			.orElse(Collections.emptySet());

		final List<Enumerator> result = getELiterals().stream().map(EEnumLiteral::getInstance)
			.collect(Collectors.toList());
		if (!providerChoices.isEmpty()) {
			result.retainAll(providerChoices);
		}

		return result;
	}

	@Override
	public String getFormatedString(Object value) {
		// If the propertyDescriptor is present, then we have a source
		return getPropertyDescriptor().map(desc -> desc.getLabelProvider(getSource().get()))
			.map(lp -> lp.getText(value))
			.orElseGet(() -> getLabel((Enumerator) value));
	}

	private String getLabel(Enumerator enumValue) {
		final String typeName = attribute.getEType().getName();

		return editBundle
			.map(eB -> l10n.getString(eB, String.format(LOCALIZATION_KEY_TEMPLATE, typeName, enumValue.getName())))
			.orElse(enumValue.getLiteral());
	}

	@Override
	public void instantiate(EStructuralFeature feature, ViewModelContext viewModelContext) {
		if (l10n == null) {
			l10n = viewModelContext.getService(EMFFormsLocalizationService.class);
		}
		attribute = (EAttribute) feature;

		try {
			editBundle = Optional.of(bundleResolver.getEditBundle(feature.getEType()));
		} catch (final NoBundleFoundException ex) {
			viewModelContext.getService(ReportService.class)
				.report(new AbstractReport(
					MessageFormat.format(
						"No edit bundle was found for EEnum ''{0}''. Hence, its literals cannot be internationalized for feature ''{1}''.", //$NON-NLS-1$
						feature.getEType().getName(), feature.getName()),
					IStatus.WARNING));
			editBundle = Optional.empty();
		}

		final List<?> choices = getChoiceOfValues();
		viewer.getCCombo().setVisibleItemCount(min(choices.size(), 8));
		final Point emptyViewerSize = viewer.getCCombo().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		minWidth = choices.stream()
			.mapToInt(value -> getTextWidth(getFormatedString(value), viewer.getCCombo().getFont()))
			.reduce(50, Math::max);
		minWidth += emptyViewerSize.x;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IValueProperty getValueProperty() {
		return new ComboValueProperty();
	}

	@Override
	public void activate(ColumnViewerEditorActivationEvent actEvent) {
		viewer.setInput(getChoiceOfValues());
		source.ifPresent(obj -> {
			viewer.getCCombo().setText(getFormatedString(obj.eGet(attribute)));
		});

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

	@Override
	public void deactivate() {
		super.deactivate();

		// Forget the source previously inferred
		source = Optional.empty();
	}

	@Override
	public int getColumnWidthWeight() {
		return 100;
	}

	@Override
	public int getMinWidth() {
		return minWidth;
	}

	@Override
	public EEnum getEEnum() {
		return (EEnum) attribute.getEType();
	}

	@Override
	public Image getImage(Object value) {
		return null;
	}

	@Override
	public void setEditable(boolean editable) {
		viewer.getCCombo().setEnabled(editable);
	}

	// Infer the source model element from the EMF binding in the
	// context that is for our attribute
	private Optional<EObject> inferSource(DataBindingContext context) {
		return ((List<?>) context.getBindings()).stream()
			.map(Binding.class::cast)
			.map(Binding::getModel)
			.filter(IEMFObservable.class::isInstance).map(IEMFObservable.class::cast)
			.filter(obs -> obs.getStructuralFeature() == attribute)
			.map(IEMFObservable::getObserved)
			.map(EObject.class::cast) // Can't observe a feature of a non-EObject
			.findAny();
	}

	/**
	 * @return The current source EObject
	 */
	protected Optional<EObject> getSource() {
		return source;
	}

	/**
	 * @return The {@link IItemPropertyDescriptor} descriptor of the current source if it is available
	 */
	protected Optional<IItemPropertyDescriptor> getPropertyDescriptor() {
		return getSource().flatMap(source -> getPropertyDescriptor(source, attribute.getName()));
	}

	@Override
	protected Object doGetValue() {
		return viewer.getStructuredSelection().getFirstElement();
	}

	@Override
	protected void doSetValue(Object value) {
		viewer.setSelection(value == null ? StructuredSelection.EMPTY : new StructuredSelection(value));
	}

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
	 * Obtains the EMF.Edit property descriptor for the named property of the {@code object}.
	 *
	 * @param object an object
	 * @param propertyName a property to access
	 * @return its descriptor
	 */
	static Optional<IItemPropertyDescriptor> getPropertyDescriptor(EObject object, String propertyName) {
		return EMFUtils.adapt(object, IItemPropertySource.class)
			.map(propertySource -> propertySource.getPropertyDescriptor(object, propertyName));
	}

	//
	// Nested types
	//

	/**
	 * Label provider for enumeration values.
	 */
	private class EnumLabelProvider extends LabelProvider {
		EnumLabelProvider() {
			super();
		}

		@Override
		public String getText(Object element) {
			return getFormatedString(element);
		}

	}

	/**
	 * Observable value of the combo.
	 */
	private class ComboValueProperty extends SimpleValueProperty<Object, Object> {

		@Override
		public Object getValueType() {
			return CCombo.class;
		}

		@Override
		protected Object doGetValue(Object source) {
			return ItemProviderEnumCellEditor.this.getValue();
		}

		@Override
		protected void doSetValue(Object source, Object value) {
			ItemProviderEnumCellEditor.this.doSetValue(value);
		}

		@Override
		public IObservableValue<Object> observe(Object source) {
			if (source != ItemProviderEnumCellEditor.this) {
				return Observables.constantObservableValue(null);
			}

			return ViewerProperties.singleSelection().observe(viewer);
		}

		@Override
		public INativePropertyListener<Object> adaptListener(
			ISimplePropertyListener<Object, ValueDiff<? extends Object>> listener) {
			return null;
		}
	}

}
