/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.controls.NumericalHelper;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * Renders numbers.
 *
 * @author Eugen Neufeld
 *
 */
public class NumberControlSWTRenderer extends TextControlSWTRenderer {

	private final EMFFormsLocalizationService localizationService;
	private final EMFFormsLocaleProvider localeProvider;
	private EMFFormsLocaleChangeListener emfFormsLocaleChangeListener;

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
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 * @param localeProvider The {@link EMFFormsLocaleProvider}
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public NumberControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport,
		EMFFormsLocalizationService localizationService, EMFFormsLocaleProvider localeProvider) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			emfFormsEditSupport);
		this.localizationService = localizationService;
		this.localeProvider = localeProvider;
	}

	@Override
	protected int getDefaultAlignment() {
		return SWT.RIGHT;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.AbstractTextControl#getTextVariantID()
	 */
	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_numerical"; //$NON-NLS-1$
	}

	@Override
	protected String getTextMessage() {
		try {
			final IValueProperty valueProperty = getEMFFormsDatabinding()
				.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
			final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
			// if (NumericalHelper.isInteger(getInstanceClass(structuralFeature))) {
			// return localizationService.getString(getClass(), MessageKeys.NumericalControl_FormatNumerical);
			// } else if (NumericalHelper.isDouble(getInstanceClass(structuralFeature))) {
			// return localizationService.getString(getClass(),
			// MessageKeys.NumericalControl_FormatNumericalDecimal);
			// }

			final DecimalFormat format = NumericalHelper.setupFormat(localeProvider.getLocale(),
				getInstanceClass(structuralFeature));
			if (format.getMaximumFractionDigits() > 1) {
				format.setMaximumFractionDigits(1);
			}
			return format.toLocalizedPattern();
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new DatabindingFailedReport(ex));
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected Object convert(Text text, EDataType attributeType, String value) throws DatabindingFailedException {
		final EStructuralFeature eStructuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final NumericalTargetToModelUpdateStrategy converter = new NumericalTargetToModelUpdateStrategy(
			eStructuralFeature, getModelValue(), getDataBindingContext(), text);
		return converter.convert(value);
	}

	@Override
	protected Binding[] createBindings(final Control control) throws DatabindingFailedException {
		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();

		final UpdateValueStrategy targetToModelStrategy = withPreSetValidation(new NumericalTargetToModelUpdateStrategy(
			structuralFeature, getModelValue(), getDataBindingContext(),
			(Text) Composite.class.cast(control).getChildren()[0]));
		final NumericalModelToTargetUpdateStrategy modelToTargetStrategy = new NumericalModelToTargetUpdateStrategy(
			getInstanceClass(structuralFeature), false);
		final Binding binding = bindValue(control, getModelValue(), getDataBindingContext(),
			targetToModelStrategy, modelToTargetStrategy);
		final Binding tooltipBinding = createTooltipBinding(control, getModelValue(), getDataBindingContext(),
			targetToModelStrategy,
			new NumericalModelToTargetUpdateStrategy(getInstanceClass(structuralFeature), true));

		emfFormsLocaleChangeListener = new EMFFormsLocaleChangeListener() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener#notifyLocaleChange()
			 */
			@Override
			public void notifyLocaleChange() {
				((Text) control).setMessage(getTextMessage());
				binding.updateModelToTarget();
			}
		};
		localeProvider.addEMFFormsLocaleChangeListener(emfFormsLocaleChangeListener);

		return new Binding[] { binding, tooltipBinding };
	}

	private Class<?> getInstanceClass(EStructuralFeature feature) {
		return feature.getEType().getInstanceClass();
	}

	@Override
	protected String getTextFromTextField(Text text, EDataType attributeType) {
		if (!Object.class.isAssignableFrom(attributeType.getInstanceClass())) {
			/* primitive types */
			return super.getTextFromTextField(text, attributeType);
		}
		if (text.getText() != null && text.getText().isEmpty()) {
			/* string is empty, but since we are a non primitive type, return null instead */
			return null;
		}
		return super.getTextFromTextField(text, attributeType);
	}

	/**
	 * Converts the numerical value from the model to the target. Locale settings are respected,
	 * i.e. formatting is performed according to the current locale.
	 */
	private class NumericalModelToTargetUpdateStrategy extends ModelToTargetUpdateStrategy {

		private final Class<?> instanceClass;

		NumericalModelToTargetUpdateStrategy(Class<?> instanceClass, boolean tooltip) {
			super(tooltip);
			this.instanceClass = instanceClass;
		}

		@Override
		public Object convertValue(Object value) {
			if (value == null) {
				return ""; //$NON-NLS-1$
			}
			final DecimalFormat format = NumericalHelper.setupFormat(localeProvider.getLocale(),
				instanceClass);
			return format.format(value);
		}
	}

	/**
	 * More specific target to model update strategy that convert the string
	 * in the text field to a number. If the string is a invalid number,
	 * for instance because of the current locale, the value is reset to
	 * the last valid value found in the mode.
	 */
	private class NumericalTargetToModelUpdateStrategy extends TargetToModelUpdateStrategy {

		private final Text text;
		private final IObservableValue modelValue;
		private final EStructuralFeature eStructuralFeature;
		private final DataBindingContext dataBindingContext;

		NumericalTargetToModelUpdateStrategy(EStructuralFeature eStructuralFeature,
			IObservableValue modelValue, DataBindingContext dataBindingContext, Text text) {
			super(eStructuralFeature.isUnsettable());
			this.eStructuralFeature = eStructuralFeature;
			this.modelValue = modelValue;
			this.dataBindingContext = dataBindingContext;
			this.text = text;
		}

		private DecimalFormat getFormat() {
			return NumericalHelper.setupFormat(localeProvider.getLocale(), getInstanceClass(eStructuralFeature));
		}

		@Override
		protected Object convertValue(final Object value) {
			final DecimalFormat format = getFormat();
			try {
				Number number = null;
				if (value == null) {
					number = NumericalHelper.getDefaultValue(getInstanceClass(eStructuralFeature));
				} else {
					final ParsePosition pp = new ParsePosition(0);
					number = format.parse((String) value, pp);
					if (pp.getErrorIndex() != -1 || pp.getIndex() != ((String) value).length()) {
						return getOldValue(value);
					}
					if (NumericalHelper.isInteger(getInstanceClass(eStructuralFeature))) {
						boolean maxValue = false;
						boolean minValue = false;
						final Class<?> instanceClass = getInstanceClass(eStructuralFeature);
						try {
							if (number.doubleValue() >= getInstanceMaxValue(instanceClass)) {
								maxValue = true;
							} else if (number.doubleValue() <= getInstanceMinValue(instanceClass)) {
								minValue = true;
							}
						} catch (final IllegalArgumentException ex) {
							Activator.logException(ex);
						} catch (final SecurityException ex) {
							Activator.logException(ex);
						} catch (final IllegalAccessException ex) {
							Activator.logException(ex);
						} catch (final NoSuchFieldException ex) {
							Activator.logException(ex);
						}

						if (maxValue || minValue) {
							return NumericalHelper.numberToInstanceClass(number, getInstanceClass(eStructuralFeature));
						}
					}
				}
				String formatedNumber = ""; //$NON-NLS-1$
				if (number != null) {
					formatedNumber = format.format(number);
				}
				if (formatedNumber.length() == 0) {
					return null;
				}
				return NumericalHelper.numberToInstanceClass(format.parse(formatedNumber),
					getInstanceClass(eStructuralFeature));
			} catch (final ParseException ex) {
				return getOldValue(value);
			}
		}

		private double getInstanceMinValue(Class<?> instanceClass)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
			if (Integer.class.isAssignableFrom(instanceClass)
				|| Integer.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Integer.MIN_VALUE;
			}
			if (Long.class.isAssignableFrom(instanceClass)
				|| Long.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Long.MIN_VALUE;
			}
			if (Short.class.isAssignableFrom(instanceClass)
				|| Short.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Short.MIN_VALUE;
			}

			return Double.NaN;
		}

		private double getInstanceMaxValue(Class<?> instanceClass)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
			if (Integer.class.isAssignableFrom(instanceClass)
				|| Integer.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Integer.MAX_VALUE;
			}
			if (Long.class.isAssignableFrom(instanceClass)
				|| Long.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Long.MAX_VALUE;
			}
			if (Short.class.isAssignableFrom(instanceClass)
				|| Short.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
				return Short.MAX_VALUE;
			}

			return Double.NaN;
		}

		@Override
		protected IStatus doSet(IObservableValue observableValue, Object value) {
			final IStatus status = super.doSet(observableValue, value);
			// update targets after a model change triggered by the target to model databinding
			dataBindingContext.updateTargets();
			return status;
		}

		private Object getOldValue(final Object value) {
			if (eStructuralFeature.getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}
			final Object result = modelValue.getValue();

			final MessageDialog messageDialog = new MessageDialog(text.getShell(),
				localizationService.getString(getClass(), MessageKeys.NumericalControl_InvalidNumber), null,
				localizationService.getString(getClass(), MessageKeys.NumericalControl_InvalidNumberWillBeUnset),
				MessageDialog.ERROR,
				new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			new ECPDialogExecutor(messageDialog) {
				@Override
				public void handleResult(int codeResult) {

				}
			}.execute();

			dataBindingContext.updateTargets();
			if (eStructuralFeature.isUnsettable() && result == null) {
				// showUnsetLabel();
				return SetCommand.UNSET_VALUE;
			}
			return result;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return localizationService.getString(getClass(), MessageKeys.NumericalControl_NoNumberClickToSetNumber);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		super.dispose();
		localeProvider.removeEMFFormsLocaleChangeListener(emfFormsLocaleChangeListener);
	}

}
