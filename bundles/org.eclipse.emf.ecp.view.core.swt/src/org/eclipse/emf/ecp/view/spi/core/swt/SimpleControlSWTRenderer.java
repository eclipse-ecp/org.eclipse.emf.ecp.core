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
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.text.MessageFormat;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author Eugen
 *
 */
public abstract class SimpleControlSWTRenderer extends AbstractControlSWTRenderer<VControl> {

	/**
	 * {@link ModelChangeListener} which will sync the top-control to the unset state.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private static final class UnsetModelChangeListener implements ModelChangeListener {
		private final EObject eObject;
		private final Button unsetButton;
		private final EStructuralFeature structuralFeature;
		private final Control createUnsetLabel;
		private final Composite controlComposite;
		private final StackLayout sl;
		private final Control baseControl;

		private UnsetModelChangeListener(EObject eObject, Button unsetButton, EStructuralFeature structuralFeature,
			Control createUnsetLabel, Composite controlComposite, StackLayout sl, Control baseControl) {
			this.eObject = eObject;
			this.unsetButton = unsetButton;
			this.structuralFeature = structuralFeature;
			this.createUnsetLabel = createUnsetLabel;
			this.controlComposite = controlComposite;
			this.sl = sl;
			this.baseControl = baseControl;
		}

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (eObject.eIsSet(structuralFeature)) {
				if (sl.topControl == baseControl) {
					return;
				}
				sl.topControl = baseControl;
				unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
				controlComposite.layout(true);
			} else {
				if (sl.topControl == createUnsetLabel) {
					return;
				}
				sl.topControl = createUnsetLabel;
				unsetButton.setImage(Activator.getImage(ICONS_SET_FEATURE));
				controlComposite.layout(true);
			}
		}
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class UnsetSelectionAdapter extends SelectionAdapter {
		private final StackLayout sl;
		private final Button unsetButton;
		private final Control createUnsetLabel;
		private final Control baseControl;
		private final Composite controlComposite;

		/**
		 * @param sl
		 * @param unsetButton
		 * @param createUnsetLabel
		 * @param baseControl
		 * @param controlComposite
		 */
		private UnsetSelectionAdapter(StackLayout sl, Button unsetButton, Control createUnsetLabel, Control baseControl,
			Composite controlComposite) {
			this.sl = sl;
			this.unsetButton = unsetButton;
			this.createUnsetLabel = createUnsetLabel;
			this.baseControl = baseControl;
			this.controlComposite = controlComposite;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			IObservableValue observableValue;
			try {
				observableValue = getEMFFormsDatabinding()
					.getObservableValue(getVElement().getDomainModelReference(),
						getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
				return;
			}
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
			final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
			observableValue.dispose();
			Object value = null;
			if (!eObject.eIsSet(structuralFeature)) {
				sl.topControl = baseControl;
				unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
				value = structuralFeature.getDefaultValue();
			} else {
				sl.topControl = createUnsetLabel;
				unsetButton.setImage(Activator.getImage(ICONS_SET_FEATURE));
				value = SetCommand.UNSET_VALUE;
			}
			final EditingDomain editingDomain = getEditingDomain(eObject);
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, eObject, structuralFeature, value));
			controlComposite.layout();
		}
	}

	private static final String ICONS_UNSET_REFERENCE = "icons/unset_reference.png"; //$NON-NLS-1$
	private static final String ICONS_UNSET_FEATURE = "icons/unset_feature.png"; //$NON-NLS-1$
	private static final String ICONS_SET_REFERENCE = "icons/set_reference.png"; //$NON-NLS-1$
	private static final String ICONS_SET_FEATURE = "icons/set_feature.png"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @since 1.6
	 */
	public SimpleControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	private SWTGridDescription rendererGridDescription;
	private UnsetModelChangeListener unsetModelChangeListener;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			int columns;
			switch (getVElement().getLabelAlignment()) {
			case DEFAULT:
			case LEFT:
				columns = 3;
				break;
			case NONE:
				columns = 2;
				break;
			default:
				getReportService().report(new AbstractReport(MessageFormat.format(
					"Label alignment {0} is not supported by renderer {1}. Label alignment set to default.", //$NON-NLS-1$
					getVElement().getLabelAlignment().getLiteral(), getClass().getName()), IStatus.INFO));
				getVElement().setLabelAlignment(LabelAlignment.DEFAULT);
				columns = 3;
			}
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, columns, this);
			for (int i = 0; i < rendererGridDescription.getGrid().size() - 1; i++) {
				final SWTGridCell swtGridCell = rendererGridDescription.getGrid().get(i);
				swtGridCell.setHorizontalGrab(false);
			}
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected final Control renderControl(SWTGridCell gridCell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int controlIndex = gridCell.getColumn();
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE) {
			controlIndex++;
		}
		switch (controlIndex) {
		case 0:
			return createLabel(parent);
		case 1:
			return createValidationIcon(parent);
		case 2:
			try {
				if (isUnsettable()) {
					return createUnsettableControl(parent);
				}
				return createControl(parent);
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				final Label errorLabel = new Label(parent, SWT.NONE);
				errorLabel.setText(ex.getMessage());
				return errorLabel;
			}
		default:
			throw new IllegalArgumentException(
				String
					.format(
						"The provided SWTGridCell (%1$s) cannot be used by this (%2$s) renderer.", gridCell.toString(), //$NON-NLS-1$
						toString()));
		}
	}

	/**
	 * Returns true if the control is unsettable.
	 *
	 * @return true if unsettable, false otherwise
	 * @throws DatabindingFailedException if the databinding fails
	 */
	protected boolean isUnsettable() throws DatabindingFailedException {
		final IValueProperty valueProperty = getEMFFormsDatabinding()
			.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		return feature.isUnsettable();
	}

	private Control createUnsettableControl(Composite parent) throws DatabindingFailedException {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		controlComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(controlComposite);
		final StackLayout sl = new StackLayout();
		controlComposite.setLayout(sl);
		final Control baseControl = createControl(controlComposite);
		final Control createUnsetLabel = createUnsetLabel(controlComposite);
		final Button unsetButton = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(false, false).applyTo(unsetButton);
		unsetButton.addSelectionListener(
			new UnsetSelectionAdapter(sl, unsetButton, createUnsetLabel, baseControl, controlComposite));

		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
		if (eObject.eIsSet(structuralFeature)) {
			sl.topControl = baseControl;
			unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
		} else {
			sl.topControl = createUnsetLabel;
			unsetButton.setImage(Activator.getImage(ICONS_SET_FEATURE));
		}
		/* There is no UNSET databinding trigger available */
		unsetModelChangeListener = new UnsetModelChangeListener(eObject, unsetButton,
			structuralFeature, createUnsetLabel, controlComposite, sl, baseControl);
		getViewModelContext().registerDomainChangeListener(unsetModelChangeListener);
		return composite;
	}

	private Control createUnsetLabel(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(composite);
		final Label unsetLabel = new Label(composite, SWT.NONE);
		unsetLabel.setBackground(parent.getBackground());
		unsetLabel.setText(getUnsetText());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, true).applyTo(unsetLabel);
		return composite;
	}

	/**
	 * Provide the unset text to show on the label when value is unset.
	 *
	 * @return the text to show on the unset label
	 */
	protected abstract String getUnsetText();

	/**
	 * Set the provided validation color as the background for the provided control.
	 *
	 * @param control the control to set the color on
	 * @param validationColor the validation color to set
	 */
	protected void setValidationColor(Control control, Color validationColor) {
		control.setBackground(validationColor);
	}

	@Override
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		int controlIndex = gridCell.getColumn();
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE) {
			controlIndex++;
		}
		switch (controlIndex) {
		case 0:
		case 1:
			break;
		default:
			control.setEnabled(enabled);
		}
	}

	@Override
	protected final void applyValidation() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (getControls().size() == 0 || getControls().values().iterator().next().isDisposed()) {
					return;
				}
				applyInnerValidation();
			}
		});
	}

	private void applyInnerValidation() {
		Label validationIcon;
		Control editControl;
		switch (getControls().size()) {
		case 2:
			validationIcon = Label.class.cast(getControls().get(
				new SWTGridCell(0, 0, SimpleControlSWTRenderer.this)));
			editControl = getControls().get(new SWTGridCell(0, 1, SimpleControlSWTRenderer.this));
			break;
		case 3:
			validationIcon = Label.class.cast(getControls().get(
				new SWTGridCell(0, 1, SimpleControlSWTRenderer.this)));
			editControl = getControls().get(new SWTGridCell(0, 2, SimpleControlSWTRenderer.this));
			break;
		default:
			getReportService().report(new AbstractReport("Wrong number of controls!")); //$NON-NLS-1$
			return;
		}
		// triggered due to another validation rule before this control is rendered
		if (validationIcon == null || editControl == null) {
			return;
		}
		// validation rule triggered after the control was disposed
		if (validationIcon.isDisposed()) {
			return;
		}
		int highestSeverity = Diagnostic.OK;
		// no diagnostic set
		if (getVElement().getDiagnostic() != null) {
			highestSeverity = getVElement().getDiagnostic().getHighestSeverity();
		}

		validationIcon.setImage(getValidationIcon(highestSeverity));
		setValidationColor(editControl, getValidationBackgroundColor(highestSeverity));
		if (getVElement().getDiagnostic() == null) {
			validationIcon.setToolTipText(null);
		} else {
			validationIcon.setToolTipText(ECPTooltipModifierHelper.modifyString(getVElement().getDiagnostic()
				.getMessage(),
				null));
		}
	}

	/**
	 * Creates the control itself.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the rendered control
	 * @throws DatabindingFailedException if the databinding of the control fails
	 */
	protected abstract Control createControl(Composite parent) throws DatabindingFailedException;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		if (unsetModelChangeListener != null) {
			getViewModelContext().unregisterDomainChangeListener(unsetModelChangeListener);
			unsetModelChangeListener = null;
		}
		super.dispose();
	}
}
