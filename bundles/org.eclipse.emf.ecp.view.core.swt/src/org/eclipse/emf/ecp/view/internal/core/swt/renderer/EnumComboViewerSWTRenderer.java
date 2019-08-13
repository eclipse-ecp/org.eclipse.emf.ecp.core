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
 * Eugen - initial API and implementation
 * Christian Damus - enum choice filtering based on ItemPropertyDescriptor
 * Lucas Koehler - enum choice filtering based on ItemPropertyDescriptor
 * Christian W. Damus - bug 547422
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.sideeffect.ISideEffect;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.IEMFObservable;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlJFaceViewerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Renderer for Enums.
 *
 * @author Eugen Neufeld
 *
 */
public class EnumComboViewerSWTRenderer extends SimpleControlJFaceViewerSWTRenderer {

	private final EMFFormsEditSupport emfFormsEditSupport;
	private IObservableValue<Collection<?>> availableChoicesValue;
	private ISideEffect pushValue;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public EnumComboViewerSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.emfFormsEditSupport = emfFormsEditSupport;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Binding[] createBindings(Viewer viewer) throws DatabindingFailedException {
		// This binding needs to fire before the value binding so that the value
		// to be selected exists in the combo's items
		final IObservableValue<Object> viewerInput = org.eclipse.jface.databinding.viewers.ViewerProperties.input()
			.observe(viewer);
		final Binding inputBinding = getDataBindingContext().bindValue(
			viewerInput,
			getAvailableChoicesValue());

		final IObservableValue<?> modelValue = getModelValue();

		final Binding binding = getDataBindingContext().bindValue(ViewerProperties.singleSelection().observe(viewer),
			modelValue);

		pushValue = ISideEffect.create(viewerInput::getValue, input -> {
			if (input != null) {
				binding.updateModelToTarget();
			}
		});

		final Binding tooltipBinding = getDataBindingContext().bindValue(
			WidgetProperties.tooltipText().observe(viewer.getControl()),
			getModelValue());
		return new Binding[] { inputBinding, binding, tooltipBinding };
	}

	/**
	 * Create a new {@link ComboViewer} instance. Overwrite this method in case you need a custom CCombo instance.
	 *
	 * @param parent the parent container
	 * @param eEnum the enum being rendered
	 * @return a {@link ComboViewer}
	 */
	protected ComboViewer createComboViewer(Composite parent, EEnum eEnum) {
		return new ComboViewer(parent);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Viewer createJFaceViewer(Composite parent) throws DatabindingFailedException {
		final IValueProperty valueProperty = getEMFFormsDatabinding()
			.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		final EEnum eEnum = EEnum.class.cast(structuralFeature.getEType());

		final ComboViewer combo = createComboViewer(parent, eEnum);
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				return getEMFFormsEditSupport()
					.getText(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel(), element);
			}

		});
		combo.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_enum"); //$NON-NLS-1$

		return combo;
	}

	/**
	 * Returns the list of literals of the enum.
	 *
	 * @param eEnum the enum to get the literals for
	 * @return a list of literals
	 *
	 */
	public List<EEnumLiteral> getELiterals(EEnum eEnum) {
		final List<EEnumLiteral> filtered = new ArrayList<EEnumLiteral>();
		final EList<EEnumLiteral> eLiterals = eEnum.getELiterals();
		for (final EEnumLiteral literal : eLiterals) {

			final String isInputtable = EcoreUtil.getAnnotation(literal,
				VViewPackage.NS_URI_170,
				"isInputtable"); //$NON-NLS-1$

			if (isInputtable == null || Boolean.getBoolean(isInputtable)) {
				filtered.add(literal);
			}
		}
		return filtered;
	}

	/**
	 * Obtains the combo viewer input as an observable value.
	 * This is an observable value, not an observable collection, because
	 * <ul>
	 * <li>it is not to be treated as a mutable collection, and</li>
	 * <li>it is used as a viewer input, which is an opaque object</li>
	 * </ul>
	 *
	 * @return the available-choices value
	 *
	 * @throws DatabindingFailedException on failure to get the {@linkplain #getModelValue() model value}
	 */
	protected IObservableValue<Collection<?>> getAvailableChoicesValue() throws DatabindingFailedException {
		if (availableChoicesValue == null) {
			// It makes no sense to use this renderer with a different kind of property than this
			final IEMFObservable emfObservable = (IEMFObservable) getModelValue();

			// the domain object must be the same as the object of the feature
			final EObject domainObject = (EObject) emfObservable.getObserved();

			final EStructuralFeature feature = emfObservable.getStructuralFeature();

			final Optional<IItemPropertySource> propertySource = EMFUtils.adapt(domainObject,
				IItemPropertySource.class);
			final Optional<IItemPropertyDescriptor> propertyDescriptor = propertySource
				.map(source -> source.getPropertyDescriptor(domainObject, feature));

			availableChoicesValue = new ComputedValue<Collection<?>>(Collection.class) {
				// maybe better subscribe to all changes in the current context and update?
				private final Optional<IChangeNotifier> changeNotifier = propertySource
					.filter(IChangeNotifier.class::isInstance).map(IChangeNotifier.class::cast);
				private final INotifyChangedListener listener = __ -> getRealm().exec(this::makeDirty);
				{
					changeNotifier.ifPresent(cn -> cn.addListener(listener));
				}

				@Override
				public synchronized void dispose() {
					changeNotifier.ifPresent(cn -> cn.removeListener(listener));
					super.dispose();
				}

				@Override
				protected Collection<?> calculate() {
					final List<EEnumLiteral> allLiterals = getELiterals((EEnum) feature.getEType());
					// We have two filter mechanisms: a) a custom annotation and b) filters defined in the property
					// descriptor. The latter is only used if a property descriptor is available. In this case, we use
					// the intersection of both enumerator sets
					final List<Enumerator> filteredByAnnotation = allLiterals.stream()
						.map(EEnumLiteral::getInstance)
						.collect(Collectors.toList());
					if (propertyDescriptor.isPresent()) {
						filteredByAnnotation.retainAll(propertyDescriptor.get().getChoiceOfValues(domainObject));
					}
					return filteredByAnnotation;
				}
			};
		}

		return availableChoicesValue;
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		availableChoicesValue.dispose();
		availableChoicesValue = null;
		super.rootDomainModelChanged();
	}

	@Override
	protected void dispose() {
		super.dispose();
		pushValue.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper
			.getString(getClass(), MessageKeys.EEnumControl_NoValueSetClickToSetValue);
	}

	/**
	 * Return the {@link EMFFormsEditSupport}.
	 *
	 * @return the {@link EMFFormsEditSupport}
	 */
	protected EMFFormsEditSupport getEMFFormsEditSupport() {
		return emfFormsEditSupport;
	}

}
