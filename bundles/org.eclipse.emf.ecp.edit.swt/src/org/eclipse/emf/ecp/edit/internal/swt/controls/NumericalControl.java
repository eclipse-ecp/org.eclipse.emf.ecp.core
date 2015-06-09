/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Edgar Mueller - refactored control to respect locale settings
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * This class is used as a common class for all number controls.
 *
 * @author Eugen Neufeld
 * @author emueller
 */
@Deprecated
public class NumericalControl extends AbstractTextControl {

	@Override
	protected int getTextWidgetStyle() {
		return super.getTextWidgetStyle() | SWT.RIGHT;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.AbstractTextControl#getTextVariantID()
	 */
	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_numerical"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.NumericalControl_NoNumberClickToSetNumber);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.NumericalControl_UnsetNumber);
	}

	@Override
	protected void customizeText(Text text) {
		super.customizeText(text);
		text.setMessage(getFormatText());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.AbstractTextControl#bindValue()
	 */
	@Override
	public Binding bindValue() {
		// TODO: FocusOut doesn't seem to fire in case the same invalid text is entered twice
		final IObservableValue value = SWTObservables.observeText(getText(), SWT.FocusOut);
		final NumericalTargetToModelUpdateStrategy targetToModelStrategy = new NumericalTargetToModelUpdateStrategy();
		final NumericalModelToTargetUpdateStrategy modelToTargetStrategy = new NumericalModelToTargetUpdateStrategy();
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(), targetToModelStrategy,
			modelToTargetStrategy);

		createTooltipBinding(targetToModelStrategy, modelToTargetStrategy);

		if (isEmbedded()) {
			// focus out is not fired if control is embedded;
			// use value change listener to get same behavior for control
			value.addValueChangeListener(new IValueChangeListener() {
				@Override
				public void handleValueChange(ValueChangeEvent event) {
					final Object newValue = event.diff.getNewValue();
					final DecimalFormat format = NumericalHelper.setupFormat(getLocale(),
						getInstanceClass());
					try {
						final Number number = format.parse((String) newValue);
						value.setValue(format.format(number));
						binding.updateTargetToModel();
					} catch (final ParseException ex) {
						targetToModelStrategy.revertToOldValue(value);
					}
				}
			});
		}

		return binding;
	}

	private Class<?> getInstanceClass() {
		return getFirstStructuralFeature().getEType().getInstanceClass();
	}

	private String getFormatText() {

		if (NumericalHelper.isInteger(getInstanceClass())) {
			return LocalizationServiceHelper.getString(getClass(),
				DepricatedControlMessageKeys.NumericalControl_FormatNumerical);
		} else if (NumericalHelper.isDouble(getInstanceClass())) {
			return LocalizationServiceHelper.getString(getClass(),
				DepricatedControlMessageKeys.NumericalControl_FormatNumericalDecimal);
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * Converts the numerical value from the model to the target. Locale settings are respected,
	 * i.e. formatting is performed according to the current locale.
	 */
	private class NumericalModelToTargetUpdateStrategy extends ModelToTargetUpdateStrategy {

		@Override
		public Object convertValue(Object value) {
			if (value == null) {
				return ""; //$NON-NLS-1$
			}
			final DecimalFormat format = NumericalHelper.setupFormat(getLocale(),
				getInstanceClass());
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

		private final DecimalFormat format;

		NumericalTargetToModelUpdateStrategy() {
			super();
			format = NumericalHelper.setupFormat(getLocale(), getInstanceClass());

		}

		@Override
		protected Object convertValue(final Object value) {

			try {
				Number number = null;
				if (value == null) {
					number = NumericalHelper.getDefaultValue(getInstanceClass());
				} else {
					final ParsePosition pp = new ParsePosition(0);
					number = format.parse((String) value, pp);
					if (pp.getErrorIndex() != -1 || pp.getIndex() != ((String) value).length()) {
						return revertToOldValue(value);
					}
					if (NumericalHelper.isInteger(getInstanceClass())) {
						boolean maxValue = false;
						final Class<?> instanceClass = getInstanceClass();
						String formatedValue = ""; //$NON-NLS-1$
						try {
							if (Integer.class.isAssignableFrom(instanceClass)
								|| Integer.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
								if (Integer.MAX_VALUE == number.intValue()) {
									maxValue = true;
									formatedValue = format.format(Integer.MAX_VALUE);
								}
							} else if (Long.class.isAssignableFrom(instanceClass)
								|| Long.class.getField("TYPE").get(null).equals(instanceClass)) { //$NON-NLS-1$
								if (Long.MAX_VALUE == number.longValue()) {
									maxValue = true;
									formatedValue = format.format(Long.MAX_VALUE);
								}
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

						if (maxValue) {
							getText().setText(formatedValue);
							return NumericalHelper.numberToInstanceClass(number, getInstanceClass());
						}
					}
				}
				String formatedNumber = ""; //$NON-NLS-1$
				if (number != null) {
					formatedNumber = format.format(number);
				}
				// if (number.toString().contains("E") //$NON-NLS-1$
				// || ((String) value).matches("0*" + formatedNumber + "\\"  //$NON-NLS-1$  //$NON-NLS-2$
				// + format.getDecimalFormatSymbols().getDecimalSeparator() + "?0*")) {  //$NON-NLS-1$
				//
				// }
				// return revertToOldValue(value);
				getText().setText(formatedNumber);
				if (formatedNumber.length() == 0) {
					return null;
				}
				return NumericalHelper.numberToInstanceClass(format.parse(formatedNumber), getInstanceClass());
			} catch (final ParseException ex) {
				return revertToOldValue(value);
			}
		}

		private Object revertToOldValue(final Object value) {

			if (getFirstStructuralFeature().getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}

			final Object result = getModelValue().getValue();

			final MessageDialog messageDialog = new MessageDialog(getText().getShell(),
				LocalizationServiceHelper.getString(getClass(),
					DepricatedControlMessageKeys.NumericalControl_InvalidNumber), null,
				LocalizationServiceHelper.getString(getClass(),
					DepricatedControlMessageKeys.NumericalControl_InvalidNumberWillBeUnset), MessageDialog.ERROR,
				new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			new ECPDialogExecutor(messageDialog) {
				@Override
				public void handleResult(int codeResult) {

				}
			}.execute();

			if (result == null) {
				getText().setText(""); //$NON-NLS-1$
			} else {
				getDataBindingContext().updateTargets();
			}

			if (getFirstStructuralFeature().isUnsettable() && result == null) {
				showUnsetLabel();
				return SetCommand.UNSET_VALUE;
			}
			return result;
		}
	}

}
