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

import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.DateUtil;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.jface.databinding.swt.WidgetProperties;
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
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eugen
 *
 */
public class XMLDateControlSWTRenderer extends TextControlSWTRenderer {

	private final EMFFormsLocaleProvider localeProvider;
	private final EMFFormsLocalizationService localizationService;
	private final ImageRegistryService imageRegistryService;

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
	 * @param imageRegistryService The {@link ImageRegistryService}
	 */
	@Inject
	public XMLDateControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport,
		EMFFormsLocalizationService localizationService, EMFFormsLocaleProvider localeProvider,
		ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			emfFormsEditSupport);
		this.localizationService = localizationService;
		this.localeProvider = localeProvider;
		this.imageRegistryService = imageRegistryService;
	}

	private static final DateFormat CHECK_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); //$NON-NLS-1$
	private static final Pattern CHECK_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"); //$NON-NLS-1$

	/**
	 * Selection adapter for the set date button.
	 */
	private final class SelectionAdapterExtension extends SelectionAdapter {
		private final Control button;
		private final Text text;

		private SelectionAdapterExtension(Text text, Button button) {
			this.text = text;
			this.button = button;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (dialog != null && !dialog.isDisposed()) {
				dialog.dispose();
				return;
			}
			IObservableValue modelValue;
			EStructuralFeature eStructuralFeature;
			try {
				modelValue = getModelValue();
				eStructuralFeature = (EStructuralFeature) modelValue.getValueType();
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new AbstractReport(ex));
				return;
			}
			dialog = new Shell(button.getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));

			final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) modelValue.getValue();
			final Calendar cal = Calendar.getInstance(localeProvider.getLocale());
			if (gregorianCalendar != null) {
				cal.setTime(gregorianCalendar.toGregorianCalendar().getTime());
			}
			calendar.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			final IObservableValue dateObserver = WidgetProperties.selection().observe(calendar);
			final Binding binding = getDataBindingContext().bindValue(dateObserver, modelValue,
				new DateTargetToModelUpdateStrategy(eStructuralFeature, text),
				new DateModelToTargetUpdateStrategy(false, true));
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
			} else if (dialogX - dialogSize.x < displayBounds.x) {
				dialogX = buttonLocation.x - button.getSize().x;
			}
			dialog.setLocation(dialogX, dialogY);

			dialog.open();
		}
	}

	@Override
	protected Object convert(Text text, EDataType attributeType, String value) throws DatabindingFailedException {
		final EStructuralFeature eStructuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final DateTargetToModelUpdateStrategy converter = new DateTargetToModelUpdateStrategy(eStructuralFeature, text);
		return converter.convert(value);
	}

	/**
	 * Model to target strategy.
	 */
	private class DateModelToTargetUpdateStrategy extends ModelToTargetUpdateStrategy {

		private final boolean toDate;

		DateModelToTargetUpdateStrategy(boolean tooltip, boolean toDate) {
			super(tooltip);
			this.toDate = toDate;
		}

		DateModelToTargetUpdateStrategy(boolean tooltip) {
			this(tooltip, false);
		}

		@Override
		public Object convertValue(Object value) {
			final DateFormat format = setupFormat();
			final XMLGregorianCalendar gregorianCalendar = (XMLGregorianCalendar) value;
			if (gregorianCalendar == null) {
				return null;
			}
			final Date date = gregorianCalendar.toGregorianCalendar().getTime();
			if (toDate) {
				return date;
			}
			return format.format(date);
		}

		@Override
		protected IStatus doSet(IObservableValue observableValue, Object value) {
			if (value == null && toDate) {
				return Status.OK_STATUS;
			}
			if (value == null && !toDate) {
				value = ""; //$NON-NLS-1$
			}
			return super.doSet(observableValue, value);
		}

	}

	/**
	 * Target to model strategy.
	 */
	private class DateTargetToModelUpdateStrategy extends TargetToModelUpdateStrategy {

		private final EStructuralFeature eStructuralFeature;
		private final Text text;
		private final boolean isDate;

		DateTargetToModelUpdateStrategy(EStructuralFeature eStructuralFeature, Text text) {
			super(eStructuralFeature.isUnsettable());
			this.eStructuralFeature = eStructuralFeature;
			this.text = text;
			final EClassifier eType = eStructuralFeature.getEType();
			if (eType == null) {
				isDate = true;
				return;
			}
			final EAnnotation eAnnotation = eType.getEAnnotation("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");//$NON-NLS-1$
			if (eAnnotation == null) {
				isDate = true;
				return;
			}
			final EMap<String, String> typeDetails = eAnnotation.getDetails();
			if (typeDetails.containsKey("name")) {//$NON-NLS-1$
				isDate = "date".equals(typeDetails.get("name"));//$NON-NLS-1$//$NON-NLS-2$
			} else if (typeDetails.containsKey("baseType")) {//$NON-NLS-1$
				isDate = typeDetails.get("baseType").endsWith("date");//$NON-NLS-1$//$NON-NLS-2$
			} else {
				isDate = true;
			}

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
				if (isDate) {
					return DateUtil.convertOnlyDateToXMLGregorianCalendar(targetCal);
				}
				return DateUtil.convertCalendarToXMLGregorianCalendar(targetCal);
			} catch (final ParseException ex) {
				return revertToOldValue(value);
			}
		}

		private Object revertToOldValue(final Object value) {

			if (eStructuralFeature.getDefaultValue() == null && (value == null || value.equals(""))) { //$NON-NLS-1$
				return null;
			}

			Object result;
			try {
				result = getModelValue().getValue();
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new AbstractReport(ex));
				return null;
			}

			final MessageDialog messageDialog = new MessageDialog(text.getShell(),
				LocalizationServiceHelper.getString(XMLDateControlSWTRenderer.class,
					MessageKeys.XmlDateControlText_InvalidNumber),
				null,
				LocalizationServiceHelper.getString(XMLDateControlSWTRenderer.class,
					MessageKeys.XmlDateControlText_NumberInvalidValueWillBeUnset),
				MessageDialog.ERROR,
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
	private EMFFormsLocaleChangeListener emfFormsLocaleChangeListener;
	private Text text;

	@Override
	protected Control createSWTControl(Composite parent) {
		final Composite main = new Composite(parent, SWT.NONE);
		main.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(main);
		final Control control = super.createSWTControl(main);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(control);
		final Button bDate = new Button(main, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(bDate);
		bDate.setImage(
			imageRegistryService.getImage(FrameworkUtil.getBundle(XMLDateControlSWTRenderer.class), "icons/date.png")); //$NON-NLS-1$
		bDate.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_xmldate"); //$NON-NLS-1$
		text = (Text) Composite.class.cast(control).getChildren()[0];
		bDate.addSelectionListener(new SelectionAdapterExtension(text, bDate));
		return main;
	}

	@Override
	protected String getTextMessage() {
		return ((SimpleDateFormat) setupFormat()).toPattern();
	}

	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_xmldate"; //$NON-NLS-1$
	}

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {
		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final Text text = (Text) Composite.class.cast(Composite.class.cast(control).getChildren()[0]).getChildren()[0];

		final IObservableValue value = WidgetProperties.text(SWT.FocusOut).observe(text);

		final UpdateValueStrategy targetToModelUpdateStrategy = withPreSetValidation(
			new DateTargetToModelUpdateStrategy(structuralFeature, text));

		final DateModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new DateModelToTargetUpdateStrategy(false);

		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			targetToModelUpdateStrategy, modelToTargetUpdateStrategy);

		final Binding tooltipBinding = createTooltipBinding(control, getModelValue(), getDataBindingContext(),
			targetToModelUpdateStrategy, new DateModelToTargetUpdateStrategy(true));

		emfFormsLocaleChangeListener = new EMFFormsLocaleChangeListener() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleChangeListener#notifyLocaleChange()
			 */
			@Override
			public void notifyLocaleChange() {
				text.setMessage(getTextMessage());
				binding.updateModelToTarget();
			}
		};
		localeProvider.addEMFFormsLocaleChangeListener(emfFormsLocaleChangeListener);

		return new Binding[] { binding, tooltipBinding };
	}

	/**
	 * Setups the {@link DateFormat}.
	 *
	 * @return the {@link DateFormat}
	 */
	protected DateFormat setupFormat() {
		final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, localeProvider.getLocale());
		df.setLenient(false);
		return df;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#setValidationColor(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void setValidationColor(Control control, Color validationColor) {
		super.setValidationColor(Composite.class.cast(control).getChildren()[0], validationColor);
	}

	@Override
	protected void setValidationForegroundColor(Control control, Color validationColor) {
		super.setValidationForegroundColor(Composite.class.cast(control).getChildren()[0], validationColor);
	}

	@Override
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE && gridCell.getColumn() == 1
			|| hasLeftLabelAlignment() && gridCell.getColumn() == 2) {
			super.setControlEnabled(gridCell, Composite.class.cast(control).getChildren()[0], enabled);
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
		return localizationService
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
		localeProvider.removeEMFFormsLocaleChangeListener(emfFormsLocaleChangeListener);
		super.dispose();
	}

}
