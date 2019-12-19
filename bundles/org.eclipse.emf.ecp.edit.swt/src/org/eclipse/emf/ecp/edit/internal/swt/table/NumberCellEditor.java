/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.controls.NumericalHelper;
import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.edit.spi.swt.table.StringBasedCellEditor;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A number cell Editor which displays numbers.
 *
 * @author Eugen Neufeld
 *
 */
public class NumberCellEditor extends StringBasedCellEditor {

	/**
	 * @author Jonas
	 *
	 */
	private final class TargetToModelStrategy extends EMFUpdateValueStrategy {
		private final DataBindingContext databindingContext;

		/**
		 * @param databindingContext
		 */
		private TargetToModelStrategy(DataBindingContext databindingContext) {
			this.databindingContext = databindingContext;
		}

		@Override
		public Object convert(final Object value) {
			final DecimalFormat format = NumericalHelper.setupFormat(getLocale(),
				getInstanceClass());
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
						if (isOfClass(Integer.class, instanceClass) && Integer.MAX_VALUE == number.intValue()) {
							maxValue = true;
							formatedValue = format.format(Integer.MAX_VALUE);
						} else if (isOfClass(Long.class, instanceClass) && Long.MAX_VALUE == number.longValue()) {
							maxValue = true;
							formatedValue = format.format(Long.MAX_VALUE);
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
				// if (number.toString().contains("E")
				// || ((String) value).matches("0*" + formatedNumber + "\\"
				// + format.getDecimalFormatSymbols().getDecimalSeparator() + "?0*")) {
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

		private <T extends Number> boolean isOfClass(Class<T> clazz, Class<?> toCheck) {
			try {
				return clazz.isAssignableFrom(toCheck)
					|| clazz.getField("TYPE").get(null).equals(toCheck); //$NON-NLS-1$
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
				Activator.logException(ex);
				return false;
			}
		}

		private Object revertToOldValue(final Object value) {

			if (eStructuralFeature.getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}

			final Object result = null;

			final MessageDialog messageDialog = new MessageDialog(text.getShell(),
				LocalizationServiceHelper.getString(getClass(), TableMessageKeys.NumberCellEditor_InvalidNumber),
				null, LocalizationServiceHelper.getString(getClass(),
					TableMessageKeys.NumberCellEditor_NumberYouEnteredIsInvalid),
				MessageDialog.ERROR,
				new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			new ECPDialogExecutor(messageDialog) {
				@Override
				public void handleResult(int codeResult) {

				}
			}.execute();

			databindingContext.updateTargets();

			if (eStructuralFeature.isUnsettable()) {
				return SetCommand.UNSET_VALUE;
			}
			return result;
		}
	}

	private EStructuralFeature eStructuralFeature;
	@Deprecated
	private ViewLocaleService localeService;
	private EMFFormsLocaleProvider localeProvider;

	/**
	 * The constructor which only takes a parent composite.
	 *
	 * @param parent the {@link Composite} to use as a parent.
	 */
	public NumberCellEditor(Composite parent) {
		super(parent, SWT.RIGHT);
	}

	/**
	 * A constructor which takes a parent and the style to use, the style is ignored by this cell editor.
	 *
	 * @param parent the {@link Composite} to use as a parent
	 * @param style the SWT style to set
	 */
	public NumberCellEditor(Composite parent, int style) {
		super(parent, style | SWT.RIGHT);
	}

	@Override
	public IValueProperty getValueProperty() {
		return CellEditorProperties.control().value(WidgetProperties.text(SWT.FocusOut));
	}

	@Override
	public void instantiate(EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		super.instantiate(eStructuralFeature, viewModelContext);
		this.eStructuralFeature = eStructuralFeature;
		getControl().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_edit_cellEditor_numberical"); //$NON-NLS-1$
		localeService = viewModelContext.getService(ViewLocaleService.class);
		localeProvider = viewModelContext.getService(EMFFormsLocaleProvider.class);
	}

	@Override
	public String getFormatedString(Object value) {
		if (value == null) {
			setErrorMessage(LocalizationServiceHelper.getString(NumberCellEditor.class,
				TableMessageKeys.NumberCellEditor_ValueIsNull));
			return ""; //$NON-NLS-1$
		}

		final DecimalFormat format = NumericalHelper.setupFormat(getLocale(), getInstanceClass());
		return format.format(value);

		// if (BigDecimal.class.isInstance(value)) {
		// BigDecimal bigDecimal = (BigDecimal) value;
		// bigDecimal.toPlainString();
		// }
		// return ((Number) value).toString();
	}

	private Locale getLocale() {
		if (localeService != null) {
			return localeService.getLocale();
		}
		if (localeProvider != null) {
			return localeProvider.getLocale();
		}
		return Locale.getDefault();
	}

	@Override
	public int getColumnWidthWeight() {
		return 50;
	}

	@Override
	public UpdateValueStrategy getTargetToModelStrategy(final DataBindingContext databindingContext) {
		return withPreSetValidation(eStructuralFeature, new TargetToModelStrategy(databindingContext));
	}

	@Override
	public UpdateValueStrategy getModelToTargetStrategy(DataBindingContext databindingContext) {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null) {
					return ""; //$NON-NLS-1$
				}
				final DecimalFormat format = NumericalHelper.setupFormat(getLocale(),
					getInstanceClass());
				return format.format(value);
			}
		};
	}

	private Class<?> getInstanceClass() {
		return eStructuralFeature.getEType().getInstanceClass();
	}

	private Text getText() {
		return text;
	}

	@Override
	public void setEditable(boolean editable) {
		if (getText() != null) {
			getText().setEditable(editable);
		}
	}

	@Override
	public Image getImage(Object value) {
		return null;
	}

	@Override
	public int getMinWidth() {
		return 0;
	}
}
