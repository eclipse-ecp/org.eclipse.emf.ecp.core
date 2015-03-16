/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.util.DateUtil;
import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Eugen
 *
 */
@SuppressWarnings("restriction")
public class XMLDateControlSWTRenderer extends TextControlSWTRenderer {

	private static final DateFormat CHECK_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); //$NON-NLS-1$
	private static final Pattern CHECK_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); //$NON-NLS-1$

	/**
	 * Selection adapter for the set date button.
	 */
	private final class SelectionAdapterExtension extends SelectionAdapter {
		private final Control button;
		private final IObservableValue modelValue;
		private final ViewModelContext viewModelContext;
		private final DataBindingContext dataBindingContext;
		private final Text text;
		private final EStructuralFeature eStructuralFeature;

		private SelectionAdapterExtension(Text text, Button button, IObservableValue modelValue,
			ViewModelContext viewModelContext,
			DataBindingContext dataBindingContext, EStructuralFeature eStructuralFeature) {
			this.text = text;
			this.button = button;
			this.modelValue = modelValue;
			this.viewModelContext = viewModelContext;
			this.dataBindingContext = dataBindingContext;
			this.eStructuralFeature = eStructuralFeature;

		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (dialog != null && !dialog.isDisposed()) {
				dialog.dispose();
				return;
			}
			dialog = new Shell(button.getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));

			final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) modelValue.getValue();
			final Calendar cal = Calendar.getInstance(getLocale(viewModelContext));
			if (gregorianCalendar != null) {
				cal.setTime(gregorianCalendar.toGregorianCalendar().getTime());
			}
			calendar.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			final IObservableValue dateObserver = SWTObservables.observeSelection(calendar);
			final Binding binding = dataBindingContext.bindValue(dateObserver, modelValue,
				new DateTargetToModelUpdateStrategy(eStructuralFeature, modelValue,
					dataBindingContext, text), new DateModelToTargetUpdateStrategy(false));
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
			final Point buttonLocation = button.toDisplay(button.getSize().x, button.getSize().y);

			// TODO what if dialogsize > displaybounds? + some other cases

			int dialogX = buttonLocation.x - dialogSize.x;
			int dialogY = buttonLocation.y;
			if (dialogY + dialogSize.y > displayBounds.height) {
				dialogY = dialogY - button.getSize().y - dialogSize.y;
			}
			if (dialogX + dialogSize.x > displayBounds.width) {
				dialogX = dialogX - dialogSize.x;
			}
			else if (dialogX - dialogSize.x < displayBounds.x) {
				dialogX = buttonLocation.x - button.getSize().x;
			}
			dialog.setLocation(dialogX, dialogY);

			dialog.open();
		}
	}

	/**
	 * Model to target strategy.
	 */
	private class DateModelToTargetUpdateStrategy extends ModelToTargetUpdateStrategy {

		public DateModelToTargetUpdateStrategy(boolean tooltip) {
			super(tooltip);
		}

		@Override
		public Object convertValue(Object value) {
			final DateFormat format = setupFormat();
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) value;
			if (gregorianCalendar == null) {
				return null;
			}
			final Date date = gregorianCalendar.toGregorianCalendar().getTime();
			return format.format(date);
		}
	}

	/**
	 * Target to model strategy.
	 */
	private class DateTargetToModelUpdateStrategy extends TargetToModelUpdateStrategy {

		private final EStructuralFeature eStructuralFeature;
		private final Text text;
		private final IObservableValue modelValue;

		DateTargetToModelUpdateStrategy(EStructuralFeature eStructuralFeature, IObservableValue modelValue,
			DataBindingContext dataBindingContext,
			Text text) {
			super(eStructuralFeature.isUnsettable());
			this.eStructuralFeature = eStructuralFeature;
			this.modelValue = modelValue;
			this.text = text;

		}

		@Override
		protected Object convertValue(final Object value) {
			try {
				Date date = null;
				if (String.class.isInstance(value)) {
					date = setupFormat().parse((String) value);
				} else if (Date.class.isInstance(value)) {
					date = (Date) value;
				} else if (value == null) {
					return value;
				}
				final String xmlFormat = CHECK_FORMAT.format(date);
				if (!CHECK_PATTERN.matcher(xmlFormat).matches()) {
					return revertToOldValue(value);
				}
				final String formatedDate = setupFormat().format(date);
				text.setText(formatedDate);

				final Calendar targetCal = Calendar.getInstance();
				targetCal.setTime(date);
				return DateUtil.convertOnlyDateToXMLGregorianCalendar(targetCal);
			} catch (final ParseException ex) {
				return revertToOldValue(value);
			}
		}

		private Object revertToOldValue(final Object value) {

			if (eStructuralFeature.getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}

			final Object result = modelValue.getValue();

			final MessageDialog messageDialog = new MessageDialog(text.getShell(),
				LocalizationServiceHelper.getString(getClass(), MessageKeys.XmlDateControlText_InvalidNumber), null,
				LocalizationServiceHelper.getString(getClass(),
					MessageKeys.XmlDateControlText_NumberInvalidValueWillBeUnset), MessageDialog.ERROR,
				new String[] { JFaceResources.getString(IDialogLabelKeys.OK_LABEL_KEY) }, 0);

			new ECPDialogExecutor(messageDialog) {
				@Override
				public void handleResult(int codeResult) {

				}
			}.execute();

			getDataBindingContext().updateTargets();

			if (eStructuralFeature.isUnsettable() && result == null) {
				return SetCommand.UNSET_VALUE;
			}
			return result;
		}
	}

	private Shell dialog;

	/**
	 * Default constructor.
	 */
	public XMLDateControlSWTRenderer() {
		super();
	}

	/**
	 * Test constructor.
	 *
	 * @param factory the renderer factory
	 */
	/* package */XMLDateControlSWTRenderer(SWTRendererFactory factory) {
		super(factory);
	}

	@Override
	protected Control createSWTControl(Composite parent, Setting setting) {
		final Composite main = new Composite(parent, SWT.NONE);
		main.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(main);
		final Control text = super.createSWTControl(main, setting);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(text);
		final Button bDate = new Button(main, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(bDate);
		bDate.setImage(Activator.getImage("icons/date.png")); //$NON-NLS-1$
		bDate.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_xmldate"); //$NON-NLS-1$

		return main;
	}

	@Override
	protected String getTextMessage(Setting setting) {
		return ((SimpleDateFormat) setupFormat()).toPattern();
	}

	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_xmldate"; //$NON-NLS-1$
	}

	@Override
	protected Binding[] createBindings(Control control, Setting setting) {
		final Text text = (Text) ((Composite) control).getChildren()[0];
		final Button button = (Button) ((Composite) control).getChildren()[1];
		button.addSelectionListener(new SelectionAdapterExtension(text, button, getModelValue(setting),
			getViewModelContext(),
			getDataBindingContext(), setting.getEStructuralFeature()));

		final IObservableValue value = SWTObservables.observeText(text, SWT.FocusOut);

		final DateTargetToModelUpdateStrategy targetToModelUpdateStrategy = new DateTargetToModelUpdateStrategy(
			setting.getEStructuralFeature(), getModelValue(setting), getDataBindingContext(),
			text);

		final DateModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new DateModelToTargetUpdateStrategy(false);

		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(setting),
			targetToModelUpdateStrategy, modelToTargetUpdateStrategy);

		final Binding tooltipBinding = createTooltipBinding(control, getModelValue(setting), getDataBindingContext(),
			targetToModelUpdateStrategy, new DateModelToTargetUpdateStrategy(true));
		return new Binding[] { binding, tooltipBinding };
	}

	/**
	 * Setups the {@link DateFormat}.
	 *
	 * @return the {@link DateFormat}
	 */
	protected DateFormat setupFormat() {
		final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale(getViewModelContext()));
		df.setLenient(false);
		return df;
	}

	private Locale getLocale(ViewModelContext viewModelContext) {
		final ViewLocaleService service = viewModelContext.getService(ViewLocaleService.class);
		if (service == null) {
			return Locale.getDefault();
		}
		return service.getLocale();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#setValidationColor(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void setValidationColor(Control control, Color validationColor) {
		((Composite) control).getChildren()[0].setBackground(validationColor);
	}

	@Override
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE && gridCell.getColumn() == 1
			|| getVElement().getLabelAlignment() == LabelAlignment.LEFT && gridCell.getColumn() == 2) {
			((Text) ((Composite) control).getChildren()[0]).setEditable(enabled);
			((Button) ((Composite) control).getChildren()[1]).setVisible(enabled);
		} else {
			super.setControlEnabled(gridCell, control, enabled);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper
			.getString(getClass(), MessageKeys.XmlDateControlText_NoDateSetClickToSetDate);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		if (dialog != null && !dialog.isDisposed()) {
			dialog.dispose();
		}
		super.dispose();
	}

}
