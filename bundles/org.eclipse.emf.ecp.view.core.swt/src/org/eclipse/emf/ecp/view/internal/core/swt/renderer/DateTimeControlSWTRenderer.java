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

import java.util.Calendar;
import java.util.Locale;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.DateAndTimeObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.ViewLocaleService;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
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

/**
 * A control which can handle {@link java.util.Date Date}.
 *
 * @author Eugen Neufeld
 *
 */
public class DateTimeControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private Label unsetLabel;

	private StackLayout stackLayout;

	private Composite stackComposite, dateTimeComposite;

	private Composite composite;

	private Shell dialog;

	private Setting setting;

	private ModelChangeListener domainModelChangeListener;

	/**
	 * Default constructor.
	 */
	public DateTimeControlSWTRenderer() {
		super();
	}

	/**
	 * Test constructor.
	 *
	 * @param factory the {@link SWTRendererFactory} to use.
	 */
	DateTimeControlSWTRenderer(SWTRendererFactory factory) {
		super(factory);
	}

	@Override
	protected Binding[] createBindings(Control control, final Setting setting) {
		this.setting = setting;

		final DateTime date = (DateTime) ((Composite) ((Composite) ((Composite) control).getChildren()[0])
			.getChildren()[0]).getChildren()[0];
		final DateTime time = (DateTime) ((Composite) ((Composite) ((Composite) control).getChildren()[0])
			.getChildren()[0]).getChildren()[1];
		final Button unsetBtn = (Button) ((Composite) ((Composite) ((Composite) control).getChildren()[0])
			.getChildren()[0])
			.getChildren()[2];
		final Button setBtn = (Button) ((Composite) control).getChildren()[1];

		final IObservableValue dateObserver = SWTObservables.observeSelection(date);
		final IObservableValue timeObserver = SWTObservables.observeSelection(time);
		final IObservableValue target = new DateAndTimeObservableValue(dateObserver, timeObserver);
		final Binding binding = getDataBindingContext().bindValue(target, getModelValue(setting));

		setBtn.addSelectionListener(new SetBtnSelectionAdapterExtension(setBtn, getModelValue(setting),
			getViewModelContext()));

		unsetBtn.addSelectionListener(new UnsetBtnSelectionAdapterExtension());

		domainModelChangeListener = new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (setting.getEStructuralFeature().equals(notification.getStructuralFeature())) {
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
	protected Control createSWTControl(Composite parent, Setting setting) {
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

		final DateTime dateWidget = new DateTime(dateTimeComposite, SWT.DATE | SWT.BORDER);
		dateWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dateWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_date"); //$NON-NLS-1$

		final DateTime timeWidget = new DateTime(dateTimeComposite, SWT.TIME | SWT.SHORT | SWT.BORDER);
		timeWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		timeWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_time"); //$NON-NLS-1$

		final Button bUnset = new Button(dateTimeComposite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(bUnset);
		bUnset.setImage(Activator.getImage("icons/delete.png")); //$NON-NLS-1$
		bUnset.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_buttonUnset"); //$NON-NLS-1$
		bUnset.setToolTipText(LocalizationServiceHelper
			.getString(getClass(), MessageKeys.DateTimeControlSWTRenderer_CleanDate));

		final Button bDate = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(bDate);
		bDate.setImage(Activator.getImage("icons/date.png")); //$NON-NLS-1$
		bDate.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_buttonSet"); //$NON-NLS-1$
		bDate.setToolTipText(LocalizationServiceHelper
			.getString(getClass(), MessageKeys.DateTimeControlSWTRenderer_SelectData));

		if (setting.isSet()) {
			stackLayout.topControl = dateTimeComposite;
		} else {
			stackLayout.topControl = unsetLabel;
		}
		return composite;
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
		return LocalizationServiceHelper
			.getString(getClass(), MessageKeys.DateTimeControl_NoDateSetClickToSetDate);
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
		public SetBtnSelectionAdapterExtension(Button btn,
			IObservableValue modelValue, ViewModelContext viewModelContext) {
			this.btn = btn;
			this.modelValue = modelValue;
			this.viewModelContext = viewModelContext;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (dialog != null && !dialog.isDisposed()) {
				dialog.dispose();
				return;
			}
			dialog = new Shell(btn.getShell(), SWT.NONE);
			dialog.setLayout(new GridLayout(1, false));

			final DateTime calendar = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);

			final Calendar defaultCalendar = Calendar.getInstance(getLocale(viewModelContext));
			calendar.setDate(defaultCalendar.get(Calendar.YEAR), defaultCalendar.get(Calendar.MONTH),
				defaultCalendar.get(Calendar.DAY_OF_MONTH));

			final IObservableValue calendarObserver = SWTObservables.observeSelection(calendar);
			final UpdateValueStrategy modelToTarget = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
			final UpdateValueStrategy targetToModel = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);

			final Binding binding = getDataBindingContext().bindValue(calendarObserver, modelValue, modelToTarget,
				targetToModel);

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

	}

	/**
	 * Unset button adapter.
	 */
	private class UnsetBtnSelectionAdapterExtension extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			final Command removeCommand = SetCommand.create(getEditingDomain(setting), setting.getEObject(),
				setting.getEStructuralFeature(), null);
			getEditingDomain(setting).getCommandStack().execute(removeCommand);
			updateChangeListener(getModelValue(setting).getValue());
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
	}
}
