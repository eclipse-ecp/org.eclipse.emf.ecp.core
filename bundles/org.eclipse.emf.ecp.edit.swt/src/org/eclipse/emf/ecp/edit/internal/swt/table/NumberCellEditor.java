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
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.controls.NumericalHelper;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor;
import org.eclipse.emf.ecp.edit.internal.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class NumberCellEditor extends TextCellEditor implements ECPCellEditor {

	private EStructuralFeature eStructuralFeature;
	private ViewModelContext viewModelContext;
	private ViewLocaleService localeService;

	public NumberCellEditor(Composite parent) {
		super(parent, SWT.RIGHT);
	}

	public NumberCellEditor(Composite parent, int style) {
		super(parent, style | SWT.RIGHT);
	}

	public IValueProperty getValueProperty() {
		return CellEditorProperties.control().value(WidgetProperties.text(SWT.FocusOut));
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#instantiate(org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext) {
		this.eStructuralFeature = eStructuralFeature;
		this.viewModelContext = viewModelContext;
		getControl().setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_edit_cellEditor_numberical"); //$NON-NLS-1$
		localeService = viewModelContext.getService(ViewLocaleService.class);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getFormatedString(java.lang.Object)
	 */
	public String getFormatedString(Object value) {
		if (value == null) {
			setErrorMessage(TableMessages.NumberCellEditor_ValueIsNull);
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
		return Locale.getDefault();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getColumnWidthWeight()
	 */
	public int getColumnWidthWeight() {
		return 50;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getTargetToModelStrategy()
	 */
	public UpdateValueStrategy getTargetToModelStrategy() {
		return new EMFUpdateValueStrategy() {

			@Override
			public Object convert(final Object value) {
				DecimalFormat format = format = NumericalHelper.setupFormat(getLocale(),
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

			private Object revertToOldValue(final Object value) {

				if (eStructuralFeature.getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
					return null;
				}

				// Object result = getModelValue().getValue();
				final Object result = null;

				final MessageDialog messageDialog = new MessageDialog(getText().getShell(),
					TableMessages.NumberCellEditor_InvalidNumber, null,
					TableMessages.NumberCellEditor_NumberYouEnteredIsInvalid, MessageDialog.ERROR,
					new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

				new ECPDialogExecutor(messageDialog) {
					@Override
					public void handleResult(int codeResult) {

					}
				}.execute();

				if (result == null) {
					getText().setText(""); //$NON-NLS-1$
				} else {
					DecimalFormat format = format = NumericalHelper.setupFormat(getLocale(),
						getInstanceClass());
					getText().setText(format.format(result));
				}

				// if (getStructuralFeature().isUnsettable() && result == null) {
				// showUnsetLabel();
				// return SetCommand.UNSET_VALUE;
				// }
				return result;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.ECPCellEditor#getModelToTargetStrategy()
	 */
	public UpdateValueStrategy getModelToTargetStrategy() {
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
}
