/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
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
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
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
import org.eclipse.swt.layout.GridData;
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
			updateTopControl();
		}

		void updateTopControl() {
			if (eObject.eIsSet(structuralFeature)) {
				if (getStack().topControl == getBaseControl()) {
					return;
				}
				getStack().topControl = getBaseControl();
				getUnsetButton().setImage(Activator.getImage(ICONS_UNSET_FEATURE));
				getControlComposite().layout(true);
			} else {
				if (getStack().topControl == getUnsetLabel()) {
					return;
				}
				getStack().topControl = getUnsetLabel();
				getUnsetButton().setImage(Activator.getImage(ICONS_SET_FEATURE));
				getControlComposite().layout(true);
			}
		}

		Composite getControlComposite() {
			return controlComposite;
		}

		StackLayout getStack() {
			return sl;
		}

		Control getBaseControl() {
			return baseControl;
		}

		Control getUnsetLabel() {
			return createUnsetLabel;
		}

		Button getUnsetButton() {
			return unsetButton;
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
			final ButtonAlignmentType buttonAlignment = getUnsettableStyleProperty().getButtonAlignment();
			final ButtonPlacementType buttonPlacement = getUnsettableStyleProperty().getButtonPlacement();
			if (!eObject.eIsSet(structuralFeature)) {
				sl.topControl = baseControl;
				unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
				value = structuralFeature.getDefaultValue();
				// adjust space grabbing if the unset button is alignend directly on the right side of the unset label.
				if (buttonAlignment == ButtonAlignmentType.LEFT
					&& buttonPlacement == ButtonPlacementType.RIGHT_OF_LABEL) {
					GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false)
						.applyTo(controlComposite);
				}
			} else {
				sl.topControl = createUnsetLabel;
				unsetButton.setImage(Activator.getImage(ICONS_SET_FEATURE));
				value = SetCommand.UNSET_VALUE;
				// re-set standard space grabbing if the unset button was alignend directly on the right side of the
				// unset label.
				if (buttonAlignment == ButtonAlignmentType.LEFT
					&& buttonPlacement == ButtonPlacementType.RIGHT_OF_LABEL) {
					GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false)
						.applyTo(controlComposite);
				}
			}
			final EditingDomain editingDomain = getEditingDomain(eObject);
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, eObject, structuralFeature, value));
			if (buttonAlignment == ButtonAlignmentType.LEFT) {
				controlComposite.getParent().layout();
			}
			controlComposite.layout();
		}
	}

	private static final String UNSET = "unset"; //$NON-NLS-1$
	private static final String ICONS_UNSET_FEATURE = "icons/unset_feature.png"; //$NON-NLS-1$
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

	private Label validationIcon;
	private Control editControl;
	private Button unsetButton;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			final int columns = SimpleControlSWTRendererUtil
				.showLabel(getVElement(), getReportService(), getClass().getName()) ? 3 : 2;

			rendererGridDescription = GridDescriptionFactory.INSTANCE.createEmptyGridDescription();
			rendererGridDescription.setRows(1);
			rendererGridDescription.setColumns(columns);

			final List<SWTGridCell> grid = new ArrayList<SWTGridCell>();

			if (columns == 3) {
				final SWTGridCell labelCell = createLabelCell(grid.size());
				grid.add(labelCell);
			}

			final SWTGridCell validationCell = createValidationCell(grid.size());
			grid.add(validationCell);

			final SWTGridCell controlCel = createControlCell(grid.size());
			grid.add(controlCel);

			rendererGridDescription.setGrid(grid);
		}
		return rendererGridDescription;
	}

	/**
	 * Creates the label cell if necessary.
	 *
	 * @param column column number within the grid row
	 * @return created and configured label cell
	 * @since 1.9
	 */
	protected SWTGridCell createLabelCell(int column) {
		return SimpleControlSWTRendererUtil.createLabelCell(column, this, getLabelWidth());
	}

	/**
	 * @return an optional width for the control's label
	 * @since 1.16
	 */
	protected Optional<Integer> getLabelWidth() {
		final VTLabelWidthStyleProperty styleProperty = RendererUtil.getStyleProperty(
			getVTViewTemplateProvider(),
			getVElement(),
			getViewModelContext(),
			VTLabelWidthStyleProperty.class);
		if (styleProperty == null || !styleProperty.isSetWidth()) {
			return Optional.empty();
		}
		return Optional.of(styleProperty.getWidth());
	}

	/**
	 * Creates the validation cell.
	 *
	 * @param column column number within the grid row
	 * @return created and configured label cell
	 * @since 1.9
	 */
	protected SWTGridCell createValidationCell(int column) {
		return SimpleControlSWTRendererUtil.createValidationCell(column, this);
	}

	/**
	 * Creates the control cell.
	 *
	 * @param column column number within the grid row
	 * @return created and configured label cell
	 * @since 1.9
	 */
	protected SWTGridCell createControlCell(int column) {
		return SimpleControlSWTRendererUtil.createControlCell(column, this);
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
			validationIcon = createValidationIcon(parent);
			return validationIcon;
		case 2:
			editControl = createEditControl(parent);
			return editControl;
		default:
			throw new IllegalArgumentException(
				String
					.format(
						"The provided SWTGridCell (%1$s) cannot be used by this (%2$s) renderer.", gridCell.toString(), //$NON-NLS-1$
						toString()));
		}
	}

	private Control createEditControl(Composite parent) {
		try {
			if (isUnsettable()) {
				return createUnsettableControl(parent);
			}
			final Control control = createControl(parent);
			setControlIdData(control);
			return control;
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			final Label errorLabel = new Label(parent, SWT.NONE);
			errorLabel.setText(ex.getMessage());
			return errorLabel;
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

		final ButtonPlacementType buttonPlacement = getUnsettableStyleProperty().getButtonPlacement();
		final Composite controlComposite;
		if (buttonPlacement == ButtonPlacementType.RIGHT_OF_LABEL) {
			controlComposite = new Composite(composite, SWT.NONE);
			unsetButton = new Button(composite, SWT.PUSH);
		} else {
			unsetButton = new Button(composite, SWT.PUSH);
			controlComposite = new Composite(composite, SWT.NONE);
		}
		controlComposite.setBackground(parent.getBackground());
		final ButtonAlignmentType buttonAlignment = getUnsettableStyleProperty().getButtonAlignment();
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(controlComposite);

		if (buttonAlignment == ButtonAlignmentType.LEFT && buttonPlacement == ButtonPlacementType.RIGHT_OF_LABEL) {
			// If the (un)set button is configured to be left aligned and no value is set,
			// align the button directly next to the unset label.
			try {
				IObservableValue observableValue;
				observableValue = getEMFFormsDatabinding().getObservableValue(getVElement().getDomainModelReference(),
					getViewModelContext().getDomainModel());
				final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
				final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
				if (!eObject.eIsSet(structuralFeature)) {
					GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(false, false)
						.applyTo(controlComposite);
				}
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
			}
		}

		final StackLayout sl = new StackLayout();
		controlComposite.setLayout(sl);
		final Control baseControl = createControl(controlComposite);
		setControlIdData(baseControl);
		final Control createUnsetLabel = createUnsetLabel(controlComposite);
		SWTDataElementIdHelper.setElementIdDataWithSubId(createUnsetLabel, getVElement(), UNSET, getViewModelContext());
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(false, false).applyTo(unsetButton);
		unsetButton.addSelectionListener(
			new UnsetSelectionAdapter(sl, unsetButton, createUnsetLabel, baseControl, controlComposite));

		unsetModelChangeListener = registerUnsetStateListener(controlComposite, sl, baseControl, createUnsetLabel,
			unsetButton);
		return composite;
	}

	private UnsetModelChangeListener registerUnsetStateListener(final Composite controlComposite, final StackLayout sl,
		final Control baseControl, final Control createUnsetLabel, final Button unsetButton)
		throws DatabindingFailedException {
		final EStructuralFeature structuralFeature = (EStructuralFeature) getModelValue().getValueType();
		final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
		if (eObject.eIsSet(structuralFeature)) {
			sl.topControl = baseControl;
			unsetButton.setImage(Activator.getImage(ICONS_UNSET_FEATURE));
		} else {
			sl.topControl = createUnsetLabel;
			unsetButton.setImage(Activator.getImage(ICONS_SET_FEATURE));
		}
		controlComposite.layout(true);
		/* There is no UNSET databinding trigger available */
		final UnsetModelChangeListener unsetModelChangeListener = new UnsetModelChangeListener(eObject, unsetButton,
			structuralFeature, createUnsetLabel, controlComposite, sl, baseControl);
		getViewModelContext().registerDomainChangeListener(unsetModelChangeListener);
		return unsetModelChangeListener;
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

	/**
	 * Set the provided validation color as the foreground for the provided control.
	 *
	 * @param control the control to set the color on
	 * @param validationColor the validation color to set
	 * @since 1.10
	 */
	protected void setValidationForegroundColor(Control control, Color validationColor) {
		control.setForeground(validationColor);
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
		setValidationForegroundColor(editControl, getValidationForegroundColor(highestSeverity));
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
	 * This method will set the element id data on the control created by {@link #createControl(Composite)}.
	 *
	 * @param control the control created by {@link #createControl(Composite)}
	 * @since 1.9
	 */
	protected void setControlIdData(final Control control) {
		if (control != null) {
			SWTDataElementIdHelper.setElementIdDataForVControl(control, getVElement(), getViewModelContext());
		}
	}

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
		validationIcon = null;
		editControl = null;
		super.dispose();
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		if (unsetModelChangeListener == null) {
			super.rootDomainModelChanged();
			return;
		}
		getViewModelContext().unregisterDomainChangeListener(unsetModelChangeListener);
		unsetModelChangeListener = registerUnsetStateListener(
			unsetModelChangeListener.getControlComposite(),
			unsetModelChangeListener.getStack(),
			unsetModelChangeListener.getBaseControl(),
			unsetModelChangeListener.getUnsetLabel(),
			unsetModelChangeListener.getUnsetButton());
		super.rootDomainModelChanged();
	}

	/**
	 * Creates the default {@link VTUnsettableStyleProperty}.
	 *
	 * @return the default {@link VTUnsettableStyleProperty}
	 * @since 1.14
	 */
	protected VTUnsettableStyleProperty createDefaultUnsettableStyleProperty() {
		return VTUnsettableFactory.eINSTANCE.createUnsettableStyleProperty();
	}

	/**
	 * Returns the {@link VTUnsettableStyleProperty}.
	 *
	 * @return the {@link VTUnsettableStyleProperty}
	 * @since 1.14
	 */
	protected VTUnsettableStyleProperty getUnsettableStyleProperty() {
		VTUnsettableStyleProperty styleProperty = RendererUtil.getStyleProperty(getVTViewTemplateProvider(),
			getVElement(), getViewModelContext(), VTUnsettableStyleProperty.class);
		if (styleProperty == null) {
			styleProperty = createDefaultUnsettableStyleProperty();
		}
		return styleProperty;
	}

	@Override
	protected void applyReadOnly() {
		super.applyReadOnly();
		if (unsetButton != null) {
			final boolean readonly = getVElement().isEffectivelyReadonly();
			unsetButton.setVisible(!readonly);
			final GridData data = (GridData) unsetButton.getLayoutData();
			data.exclude = readonly;
			unsetButton.getParent().layout();
		}
	}
}
