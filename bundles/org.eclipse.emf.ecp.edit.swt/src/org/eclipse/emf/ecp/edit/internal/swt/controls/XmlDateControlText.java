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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.util.DateUtil;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This is a XMLDateControl. It is used to display values of type {@link XMLGregorianCalendar}. This control only
 * displays a date widget.
 *
 * @author Eugen Neufeld
 *
 *         private Button bDate;
 */
@Deprecated
public class XmlDateControlText extends AbstractTextControl {
	private static final DateFormat CHECK_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); //$NON-NLS-1$
	private static final Pattern CHECK_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); //$NON-NLS-1$
	private Button bDate;

	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_xmldate"; //$NON-NLS-1$
	}

	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.XmlDateControlText_NoDateSetClickToSetDate);
	}

	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.XmlDateControlText_UnsetDate);
	}

	@Override
	protected void customizeText(Text text) {
		super.customizeText(text);
		text.setMessage(((SimpleDateFormat) setupFormat()).toPattern());
	}

	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		bDate.setVisible(editable);
	}

	@Override
	protected void fillControlComposite(Composite composite) {
		final Composite main = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(main);
		super.fillControlComposite(main);
		bDate = new Button(main, SWT.PUSH);
		bDate.setImage(Activator.getImage("icons/date.png")); //$NON-NLS-1$
		bDate.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_xmldate"); //$NON-NLS-1$
		bDate.addSelectionListener(new SelectionAdapterExtension());
	}

	@Override
	public Binding bindValue() {
		final IObservableValue value = SWTObservables.observeText(getText(), SWT.FocusOut);
		final DateTargetToModelUpdateStrategy targetToModelUpdateStrategy = new DateTargetToModelUpdateStrategy();
		final DateModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new DateModelToTargetUpdateStrategy();
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			targetToModelUpdateStrategy, modelToTargetUpdateStrategy);
		createTooltipBinding(targetToModelUpdateStrategy, modelToTargetUpdateStrategy);
		return binding;
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class SelectionAdapterExtension extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			final Shell dialog = new Shell(getText().getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));

			final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) getModelValue().getValue();
			final Calendar cal = Calendar.getInstance(getLocale());
			if (gregorianCalendar != null) {
				cal.setTime(gregorianCalendar.toGregorianCalendar().getTime());
			}
			calendar.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			final IObservableValue dateObserver = SWTObservables.observeSelection(calendar);
			final Binding binding = getDataBindingContext().bindValue(dateObserver, getModelValue(),
				new DateTargetToModelUpdateStrategy(), new DateModelToTargetUpdateStrategy());
			binding.updateModelToTarget();

			final Button okButton = new Button(dialog, SWT.PUSH);
			okButton.setText(JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY));
			GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).grab(false, false).applyTo(okButton);
			okButton.addSelectionListener(new SelectionAdapter() {
				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
				 */
				@Override
				public void widgetSelected(SelectionEvent e) {
					binding.updateTargetToModel();
					binding.dispose();
					dialog.close();
				}
			});

			dialog.pack();
			dialog.layout();
			final Point dialogSize = dialog.getSize();
			final Rectangle displayBounds = dialog.getDisplay().getBounds();
			final Point buttonLocation = okButton.toDisplay(okButton.getSize().x, okButton.getSize().y);

			// TODO what if dialogsize > displaybounds? + some other cases

			int dialogX = buttonLocation.x - dialogSize.x;
			int dialogY = buttonLocation.y;
			if (dialogY + dialogSize.y > displayBounds.height) {
				dialogY = dialogY - okButton.getSize().y - dialogSize.y;
			}
			if (dialogX + dialogSize.x > displayBounds.width) {
				dialogX = dialogX - dialogSize.x;
			}
			else if (dialogX - dialogSize.x < displayBounds.x) {
				dialogX = buttonLocation.x - okButton.getSize().x;
			}
			dialog.setLocation(dialogX, dialogY);
			dialog.open();
		}
	}

	private class DateModelToTargetUpdateStrategy extends ModelToTargetUpdateStrategy {

		@Override
		public Object convertValue(Object value) {
			// final DecimalFormat format = (DecimalFormat) DecimalFormat
			// .getInstance(getModelElementContext().getLocale());
			// format.setGroupingUsed(false);
			// format.setParseIntegerOnly(isInteger());
			final DateFormat format = setupFormat();
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) value;
			if (gregorianCalendar == null) {
				return null;
			}
			final Date date = gregorianCalendar.toGregorianCalendar().getTime();
			return format.format(date);
		}
	}

	private class DateTargetToModelUpdateStrategy extends TargetToModelUpdateStrategy {

		private final DateFormat format;

		DateTargetToModelUpdateStrategy() {
			super();
			format = setupFormat();

		}

		@Override
		protected Object convertValue(final Object value) {
			try {
				Date date = null;
				if (String.class.isInstance(value)) {
					date = format.parse((String) value);
				} else if (Date.class.isInstance(value)) {
					date = (Date) value;
				} else if (value == null) {
					return value;
				}
				final String xmlFormat = CHECK_FORMAT.format(date);
				if (!CHECK_PATTERN.matcher(xmlFormat).matches()) {
					return revertToOldValue(value);
				}
				final String formatedDate = format.format(date);
				getText().setText(formatedDate);

				final Calendar targetCal = Calendar.getInstance();
				targetCal.setTime(date);
				return DateUtil.convertOnlyDateToXMLGregorianCalendar(targetCal);
			} catch (final ParseException ex) {
				return revertToOldValue(value);
			}
			// return null;
		}

		private Object revertToOldValue(final Object value) {

			if (getFirstStructuralFeature().getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}

			final Object result = getModelValue().getValue();

			final MessageDialog messageDialog = new MessageDialog(getText().getShell(),
				LocalizationServiceHelper.getString(getClass(),
					DepricatedControlMessageKeys.XmlDateControlText_InvalidNumber), null,
				LocalizationServiceHelper.getString(getClass(),
					DepricatedControlMessageKeys.XmlDateControlText_NumberInvalidValueWillBeUnset),
				MessageDialog.ERROR,
				new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			new ECPDialogExecutor(messageDialog) {
				@Override
				public void handleResult(int codeResult) {

				}
			}.execute();

			if (result == null) {
				getText().setText(""); //$NON-NLS-1$
			} else {
				final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) result;
				final Date date = gregorianCalendar.toGregorianCalendar().getTime();
				getText().setText(format.format(date));
			}

			if (getFirstStructuralFeature().isUnsettable() && result == null) {
				showUnsetLabel();
				return SetCommand.UNSET_VALUE;
			}
			return result;
		}
	}

	/**
	 * Sets up a {@link DateFormat} for the current {@link java.util.Locale}.
	 *
	 * @return the date format
	 */
	protected DateFormat setupFormat() {
		return DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale());
	}
}
