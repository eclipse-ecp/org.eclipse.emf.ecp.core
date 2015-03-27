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
package org.eclipse.emf.ecp.view.spi.core.swt;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.reporting.AbstractReport;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
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
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	public SimpleControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	private SWTGridDescription rendererGridDescription;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public final SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1,
				getVElement().getLabelAlignment() == LabelAlignment.NONE ? 2 : 3, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
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
						"The provided SWTGridCell (%1$s) cannot be used by this (%2$s) renderer.", gridCell.toString(), toString())); //$NON-NLS-1$
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
			.getValueProperty(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel().eClass());
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		return feature.isUnsettable();
	}

	private Control createUnsettableControl(Composite parent) throws DatabindingFailedException {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		final Composite controlComposite = new Composite(composite, SWT.NONE);
		controlComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(controlComposite);
		final StackLayout sl = new StackLayout();
		controlComposite.setLayout(sl);
		final Control baseControl = createControl(controlComposite);
		final Control createUnsetLabel = createUnsetLabel(controlComposite);
		final Button unsetButton = new Button(composite, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(false, false).applyTo(unsetButton);
		unsetButton.addSelectionListener(new SelectionAdapter() {
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
					unsetButton.setText(LocalizationServiceHelper.getString(getClass(),
						MessageKeys.SimpleControlSWTRenderer_Unset));
					value = structuralFeature.getDefaultValue();
				}
				else {
					sl.topControl = createUnsetLabel;
					unsetButton.setText(LocalizationServiceHelper
						.getString(getClass(), MessageKeys.SimpleControlSWTRenderer_Set));
					value = SetCommand.UNSET_VALUE;
				}
				final EditingDomain editingDomain = getEditingDomain(eObject);
				editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain, eObject, structuralFeature, value));
				composite.layout();
			}
		});

		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
		if (eObject.eIsSet(structuralFeature)) {
			sl.topControl = baseControl;
			unsetButton.setText(LocalizationServiceHelper
				.getString(getClass(), MessageKeys.SimpleControlSWTRenderer_Unset));
		}
		else {
			sl.topControl = createUnsetLabel;
			unsetButton.setText(LocalizationServiceHelper
				.getString(getClass(), MessageKeys.SimpleControlSWTRenderer_Set));
		}

		return composite;
	}

	private Control createUnsetLabel(Composite parent) {
		final Label unsetLabel = new Label(parent, SWT.NONE);
		unsetLabel.setBackground(parent.getBackground());
		unsetLabel.setText(getUnsetText());
		return unsetLabel;
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
			if (ViewModelUtil.isDebugMode()) {
				getReportService().report(new AbstractReport("Wrong number of controls!")); //$NON-NLS-1$
			}
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
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		super.dispose();
	}
}
