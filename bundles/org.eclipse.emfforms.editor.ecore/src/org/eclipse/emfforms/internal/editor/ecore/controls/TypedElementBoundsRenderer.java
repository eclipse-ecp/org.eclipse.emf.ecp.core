/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Alexandra Buzila - refactoring
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore.controls;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 *
 * The Renderer allowing the user to test the bounds with two Combos.
 *
 * @author Clemens Elflein
 */
public class TypedElementBoundsRenderer extends AbstractControlSWTRenderer<VControl> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	@Inject
	public TypedElementBoundsRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	private SWTGridDescription rendererGridDescription;

	@Override
	public final SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			final boolean showLabel = getVElement().getLabelAlignment() != LabelAlignment.NONE;
			final int columns = showLabel ? 3 : 2;

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

	private SWTGridCell createLabelCell(int column) {
		final SWTGridCell labelCell = new SWTGridCell(0, column, this);
		labelCell.setHorizontalGrab(false);
		labelCell.setVerticalGrab(false);
		labelCell.setHorizontalFill(false);
		labelCell.setHorizontalAlignment(SWTGridCell.Alignment.BEGINNING);
		labelCell.setVerticalFill(false);
		labelCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		labelCell.setRenderer(this);
		return labelCell;
	}

	private SWTGridCell createValidationCell(int column) {
		final SWTGridCell validationCell = new SWTGridCell(0, column, this);
		validationCell.setHorizontalGrab(false);
		validationCell.setVerticalGrab(false);
		validationCell.setHorizontalFill(false);
		validationCell.setHorizontalAlignment(SWTGridCell.Alignment.CENTER);
		validationCell.setVerticalFill(false);
		validationCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		validationCell.setRenderer(this);
		validationCell.setPreferredSize(16, 17);
		return validationCell;
	}

	private SWTGridCell createControlCell(int column) {
		final SWTGridCell controlCell = new SWTGridCell(0, column, this);
		controlCell.setHorizontalGrab(true);
		controlCell.setVerticalGrab(false);
		controlCell.setHorizontalFill(true);
		controlCell.setVerticalFill(false);
		controlCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		controlCell.setRenderer(this);
		return controlCell;
	}

	@Override
	protected final Control renderControl(SWTGridCell gridCell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int controlIndex = gridCell.getColumn();
		if (getVElement().getLabelAlignment() == LabelAlignment.NONE) {
			controlIndex++;
		}
		switch (controlIndex) {
		case 0:
			return createBoundsLabel(parent);
		case 1:
			return createValidationIcon(parent);
		case 2:
			return createControl(parent);
		default:
			throw new IllegalArgumentException(
				String
					.format(
						"The provided SWTGridCell (%1$s) cannot be used by this (%2$s) renderer.", gridCell.toString(), //$NON-NLS-1$
						toString()));
		}
	}

	private Control createBoundsLabel(Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
		label.setText(getLocalizedString(Messages.TypedElementBoundsRenderer_Bounds));
		return label;
	}

	private Control createControl(Composite parent) {
		final Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false)
			.align(SWT.FILL, SWT.BEGINNING).applyTo(main);

		final Spinner lowerBound = new Spinner(main, SWT.BORDER);
		lowerBound.setMaximum(Integer.MAX_VALUE);
		lowerBound.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, true));

		final Spinner upperBound = new Spinner(main, SWT.BORDER);
		upperBound.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, true));
		upperBound.setMinimum(-1);
		upperBound.setMaximum(Integer.MAX_VALUE);

		final Button unbounded = new Button(main, SWT.CHECK);
		unbounded.setText(getLocalizedString(Messages.TypedElementBoundsRenderer_Unbounded));

		createDataBindings(lowerBound, upperBound, unbounded);

		applyValidation();
		return main;
	}

	private String getLocalizedString(String string) {
		return LocalizationServiceHelper.getString(getClass(), string);
	}

	private void createDataBindings(final Spinner lowerBound, final Spinner upperBound, Button unbounded) {
		final EObject domainObject = getViewModelContext().getDomainModel();
		final DataBindingContext dbc = getDataBindingContext();

		final ISWTObservableValue lowerBoundSelectionTargetValue = WidgetProperties.selection().observe(lowerBound);
		final IObservableValue lowerBoundModelValue = EMFEditObservables.observeValue(getEditingDomain(domainObject),
			domainObject, EcorePackage.eINSTANCE.getETypedElement_LowerBound());
		dbc.bindValue(lowerBoundSelectionTargetValue, lowerBoundModelValue);

		final IObservableValue upperBoundModelValue = EMFEditObservables.observeValue(getEditingDomain(domainObject),
			domainObject, EcorePackage.eINSTANCE.getETypedElement_UpperBound());
		final ISWTObservableValue upperBoundSelectionTargetValue = WidgetProperties.selection().observe(upperBound);
		dbc.bindValue(upperBoundSelectionTargetValue, upperBoundModelValue);

		bindUpperAndLowerBounds(dbc, lowerBoundModelValue, upperBoundModelValue);

		/* Disable the upperBound spinner when it's value is set to -1 */
		final ISWTObservableValue upperBoundEnabledTargetValue = WidgetProperties.enabled().observe(upperBound);
		dbc.bindValue(upperBoundEnabledTargetValue, upperBoundModelValue, null,
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					// model to target
					if (!Integer.class.isInstance(value)) {
						return null;
					}
					return (Integer) value != -1;
				}
			});

		/* The unbounded checkbox is selected, when the upperBound value is -1 (and vice versa) */
		final ISWTObservableValue unboundedSelectionTargetValue = WidgetProperties.selection().observe(unbounded);
		dbc.bindValue(unboundedSelectionTargetValue, upperBoundModelValue,
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					// target to model
					final Boolean unbounded = (Boolean) value;
					if (!unbounded) {
						return Math.max(1, lowerBound.getSelection());
					}
					return -1;
				}
			}, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					// model to target
					if (!Integer.class.isInstance(value)) {
						return null;
					}
					return (Integer) value == -1;
				}
			});
	}

	/**
	 * Make sure that the upperBound cannot be lower than the lowerBound and that the lowerBound cannot be higher
	 * than the upperBound.
	 */
	private void bindUpperAndLowerBounds(final DataBindingContext dbc, final IObservableValue lowerBoundModelValue,
		final IObservableValue upperBoundModelValue) {
		dbc.bindValue(upperBoundModelValue, lowerBoundModelValue,
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					// upper value to lower value
					if (!Integer.class.isInstance(value)) {
						return null;
					}
					final Integer upperValue = (Integer) value;
					final Integer lowerValue = (Integer) lowerBoundModelValue.getValue();
					if (upperValue < lowerValue && upperValue != -1) {
						return upperValue;
					}
					return lowerValue;
				}

				@Override
				protected IStatus doSet(IObservableValue observableValue, Object value) {
					if (observableValue.getValue().equals(value)) {
						return Status.OK_STATUS;
					}
					return super.doSet(observableValue, value);
				}
			}, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					// lower value to upper value
					if (!Integer.class.isInstance(value)) {
						return null;
					}
					final Integer lowerValue = (Integer) value;
					final Integer upperValue = (Integer) upperBoundModelValue.getValue();
					if (upperValue >= 0 && upperValue < lowerValue) {
						return lowerValue;
					}
					return upperValue;
				}

				@Override
				protected IStatus doSet(IObservableValue observableValue, Object value) {
					if (observableValue.getValue().equals(value)) {
						return Status.OK_STATUS;
					}
					return super.doSet(observableValue, value);
				}
			});
	}

	private void setValidationColor(Control control, Color validationColor) {
		control.setBackground(validationColor);
	}

	@Override
	protected final void applyValidation() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
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
				new SWTGridCell(0, 0, TypedElementBoundsRenderer.this)));
			editControl = getControls().get(new SWTGridCell(0, 1, TypedElementBoundsRenderer.this));
			break;
		case 3:
			validationIcon = Label.class.cast(getControls().get(
				new SWTGridCell(0, 1, TypedElementBoundsRenderer.this)));
			editControl = getControls().get(new SWTGridCell(0, 2, TypedElementBoundsRenderer.this));
			break;
		default: // TODO log error ;
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
		// no diagnostic set
		if (getVElement().getDiagnostic() == null) {
			return;
		}

		validationIcon.setImage(getValidationIcon(getVElement().getDiagnostic().getHighestSeverity()));
		validationIcon.setToolTipText(getVElement().getDiagnostic().getMessage());

		setValidationColor(editControl, getValidationBackgroundColor(getVElement().getDiagnostic()
			.getHighestSeverity()));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer#rootDomainModelChanged()
	 */
	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		// TODO change implementation to use databinding
	}
}
