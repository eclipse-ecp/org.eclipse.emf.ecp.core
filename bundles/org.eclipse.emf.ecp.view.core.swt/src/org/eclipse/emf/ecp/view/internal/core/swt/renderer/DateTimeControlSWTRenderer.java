/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.DateAndTimeObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;

/**
 * A control which can handle {@link java.util.Date Date}.
 *
 * @author Eugen Neufeld
 *
 */
public class DateTimeControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

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
	 * @param localizationService The {@link EMFFormsLocalizationService}
	 * @param imageRegistryService The {@link ImageRegistryService}
	 */
	@Inject
	public DateTimeControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.localizationService = localizationService;
		this.imageRegistryService = imageRegistryService;
	}

	private Label unsetLabel;

	private StackLayout stackLayout;

	private Composite stackComposite, dateTimeComposite;

	private Composite composite;

	private Shell dialog;

	private ModelChangeListener domainModelChangeListener;

	private DateTime dateWidget;
	private DateTime timeWidget;

	private Button bUnset;

	private Button setBtn;

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {
		ISWTObservableValue dateObserver = WidgetProperties.selection().observe(dateWidget);
		ISWTObservableValue timeObserver = WidgetProperties.selection().observe(timeWidget);
		final IObservableValue target = new DateAndTimeObservableValue(dateObserver, timeObserver);
		final Binding binding = getDataBindingContext().bindValue(target, getModelValue());

		setBtn.addSelectionListener(new SetBtnSelectionAdapterExtension(setBtn, getModelValue(),
			getViewModelContext()));

		domainModelChangeListener = new ModelChangeListener() {
			@Override
			public void notifyChange(ModelChangeNotification notification) {
				EStructuralFeature structuralFeature;
				try {
					structuralFeature = (EStructuralFeature) getModelValue().getValueType();
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
					return;
				}
				if (structuralFeature.equals(notification.getStructuralFeature())) {
					updateChangeListener(notification.getRawNotification().getNewValue());
				}
			}
		};
		getViewModelContext().registerDomainChangeListener(domainModelChangeListener);

		return new Binding[] { binding };
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
		getViewModelContext().unregisterDomainChangeListener(domainModelChangeListener);
		super.dispose();
	}

	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(2, 0).equalWidth(false)
			.applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(composite);

		stackComposite = new Composite(composite, SWT.NONE);
		stackComposite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(2, 0).equalWidth(false)
			.applyTo(stackComposite);

		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(stackComposite);

		dateTimeComposite = new Composite(stackComposite, SWT.NONE);
		dateTimeComposite.setBackground(composite.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(3).spacing(2, 0).equalWidth(false)
			.applyTo(dateTimeComposite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(dateTimeComposite);

		stackLayout = new StackLayout();
		stackComposite.setLayout(stackLayout);
		unsetLabel = new Label(stackComposite, SWT.CENTER);
		unsetLabel.setText(getUnsetText());
		unsetLabel.setBackground(stackComposite.getBackground());
		unsetLabel.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		unsetLabel.setAlignment(SWT.CENTER);

		final DateTimeDisplayType dateTimeDisplayType = getDateTimeDisplayType();
		createDateTimeWidgets(dateTimeDisplayType);

		bUnset = new Button(dateTimeComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(bUnset);
		bUnset
			.setImage(imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), "icons/unset_feature.png")); //$NON-NLS-1$
		bUnset.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_buttonUnset"); //$NON-NLS-1$
		final String tooltip = getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY
			? MessageKeys.DateTimeControlSWTRenderer_CleanTime : MessageKeys.DateTimeControlSWTRenderer_CleanDate;
		bUnset.setToolTipText(getLocalizedString(tooltip));
		bUnset.addSelectionListener(new UnsetBtnSelectionAdapterExtension());

		createSetButton();

		final IObservableValue observableValue = getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		observableValue.dispose();
		if (eObject.eIsSet(structuralFeature)) {
			stackLayout.topControl = dateTimeComposite;
		} else {
			stackLayout.topControl = unsetLabel;
		}
		return composite;
	}

	private void createSetButton() {
		final String imagePath = getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY ? "icons/set_feature.png" //$NON-NLS-1$
			: "icons/date.png"; //$NON-NLS-1$
		setBtn = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(setBtn);
		setBtn.setImage(imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), imagePath));
		setBtn.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_buttonSet"); //$NON-NLS-1$
		final String tooltip = getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY
			? MessageKeys.DateTimeControlSWTRenderer_SelectTime : MessageKeys.DateTimeControlSWTRenderer_SelectData;
		setBtn.setToolTipText(getLocalizedString(tooltip));

	}

	private void createDateTimeWidgets(DateTimeDisplayType dateTimeDisplayType) {
		dateWidget = new DateTime(dateTimeComposite, SWT.DATE | SWT.BORDER);
		dateWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dateWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_date"); //$NON-NLS-1$
		timeWidget = new DateTime(dateTimeComposite, SWT.TIME | SWT.SHORT | SWT.BORDER);
		timeWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		timeWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_time"); //$NON-NLS-1$
		if (dateTimeDisplayType == DateTimeDisplayType.TIME_ONLY) {
			dateWidget.setVisible(false);
			final GridData gridData = (GridData) dateWidget.getLayoutData();
			gridData.exclude = true;

		}
		if (dateTimeDisplayType == DateTimeDisplayType.DATE_ONLY) {
			timeWidget.setVisible(false);
			final GridData gridData = (GridData) timeWidget.getLayoutData();
			gridData.exclude = true;
		}
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
		((Composite) control).getChildren()[1].setBackground(validationColor);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		final String text = getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY
			? MessageKeys.DateTimeControl_NoTimeSetClickToSetTime : MessageKeys.DateTimeControl_NoDateSetClickToSetDate;
		return getLocalizedString(text);
	}

	private String getLocalizedString(String key) {
		return localizationService.getString(getClass(), key);
	}

	/**
	 * Set button adapter.
	 */
	private class SetBtnSelectionAdapterExtension extends SelectionAdapter {

		private final Button btn;
		private final IObservableValue modelValue;
		private final ViewModelContext viewModelContext;

		/**
		 * @param btn
		 * @param modelValue
		 * @param viewModelContext
		 */
		SetBtnSelectionAdapterExtension(Button btn,
			IObservableValue modelValue, ViewModelContext viewModelContext) {
			this.btn = btn;
			this.modelValue = modelValue;
			this.viewModelContext = viewModelContext;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY) {
				setTime();
			} else {
				setDate();
			}
		}

		private void setDate() {
			if (dialog != null && !dialog.isDisposed()) {
				dialog.dispose();
				return;
			}
			dialog = new Shell(btn.getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));
			final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
			final IObservableValue calendarObserver = WidgetProperties.selection().observe(calendar);
			final UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
			final UpdateValueStrategy targetToModel = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);

			final Binding binding = getDataBindingContext().bindValue(calendarObserver, modelValue, modelToTarget,
				targetToModel);
			final Calendar defaultCalendar = Calendar.getInstance(getLocale(viewModelContext));
			calendar.setDate(defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH),
				defaultCalendar.get(Calendar.DAY_OF_MONTH));

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
					updateChangeListener(modelValue.getValue());
				}
			});

			dialog.pack();
			dialog.layout();
			dialog.setLocation(btn.getParent().toDisplay(
				btn.getLocation().x + btn.getSize().x - dialog.getSize().x,
				btn.getLocation().y + btn.getSize().y));
			dialog.open();

		}

		private void setTime() {
			try {
				final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
				final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
				final Command setCommand = SetCommand.create(getEditingDomain(eObject), eObject, structuralFeature,
					new Date());
				getEditingDomain(eObject).getCommandStack().execute(setCommand);
				updateChangeListener(getModelValue().getValue());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
			}
		}

	}

	/**
	 * Unset button adapter.
	 */
	private class UnsetBtnSelectionAdapterExtension extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
				final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
				final Command removeCommand = SetCommand.create(getEditingDomain(eObject), eObject, structuralFeature,
					null);
				getEditingDomain(eObject).getCommandStack().execute(removeCommand);
				updateChangeListener(getModelValue().getValue());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				// Do nothing. This should not happen because if getModelValue() fails, the control will never be
				// rendered and consequently this code will never be executed.
			}
		}

	}

	private Locale getLocale(ViewModelContext viewModelContext) {
		final ViewLocaleService service = viewModelContext.getService(ViewLocaleService.class);
		if (service == null) {
			return Locale.getDefault();
		}
		return service.getLocale();
	}

	private void updateChangeListener(Object value) {
		if (value == null) {
			if (stackLayout.topControl != unsetLabel) {
				stackLayout.topControl = unsetLabel;
				stackComposite.layout();
			}

		} else {
			if (stackLayout.topControl != dateTimeComposite) {
				stackLayout.topControl = dateTimeComposite;
				stackComposite.layout();
			}
		}
		if (getDateTimeDisplayType() == DateTimeDisplayType.TIME_ONLY) {
			setBtn.setVisible(value == null);
		}
	}

	private DateTimeDisplayType getDateTimeDisplayType() {
		if (getVElement() != null && getVElement().getAttachments() != null) {
			for (final VAttachment attachment : getVElement().getAttachments()) {
				if (VDateTimeDisplayAttachment.class.isInstance(attachment)) {
					final VDateTimeDisplayAttachment dateTimeAttachment = (VDateTimeDisplayAttachment) attachment;
					return dateTimeAttachment.getDisplayType();
				}
			}
		}
		return DateTimeDisplayType.TIME_AND_DATE;
	}
}
