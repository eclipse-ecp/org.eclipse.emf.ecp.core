/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EEnumImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.provider.ECPTooltipModifierHelper;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * A control for defining an value in a leaf condition.
 *
 * @author Eugen Neufeld
 *
 */
// APITODO no api yet
public abstract class ExpectedValueControlRenderer extends SimpleControlSWTControlSWTRenderer {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param databindingService The {@link EMFFormsDatabinding}
	 * @param labelProvider The {@link EMFFormsLabelProvider}
	 * @param viewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	public ExpectedValueControlRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding databindingService, EMFFormsLabelProvider labelProvider,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService, databindingService, labelProvider, viewTemplateProvider);
	}

	private Label text;
	private Shell shell;

	private String getTextVariantID() {
		return "org_eclipse_emf_ecp_view_editor_controls_ruleattribute"; //$NON-NLS-1$
	}

	@Override
	public void finalizeRendering(Composite parent) {
		super.finalizeRendering(parent);
		shell = parent.getShell();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(composite);
		final Button bSelectObject = new Button(composite, SWT.PUSH);
		bSelectObject.setText("Select Object"); //$NON-NLS-1$

		text = new Label(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text.setData(CUSTOM_VARIANT, getTextVariantID());

		bSelectObject.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				onSelectButton(text);
			}
		});
		return text;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {
		final Label text = (Label) control;
		final TargetToModelUpdateStrategy targetToModelUpdateStrategy = new TargetToModelUpdateStrategy();
		final ModelToTargetUpdateStrategy modelToTargetUpdateStrategy = new ModelToTargetUpdateStrategy();

		final IObservableValue value = org.eclipse.jface.databinding.swt.WidgetProperties.text().observe(text);

		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			withPreSetValidation(targetToModelUpdateStrategy), modelToTargetUpdateStrategy);
		return new Binding[] { binding };
	}

	/**
	 * Creates a tooltip binding for this control.
	 *
	 * @param text the {@link Text} to bind
	 * @param modelValue the {@link IObservableValue} to bind
	 * @param dataBindingContext the {@link DataBindingContext} to use
	 * @param targetToModel the {@link UpdateValueStrategy} from target to Model
	 * @param modelToTarget the {@link UpdateValueStrategy} from model to target
	 * @return the created {@link Binding}
	 */
	protected Binding createTooltipBinding(Control text, IObservableValue modelValue,
		DataBindingContext dataBindingContext, UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		final IObservableValue toolTip = org.eclipse.jface.databinding.swt.WidgetProperties.tooltipText().observe(text);
		return dataBindingContext.bindValue(toolTip, modelValue, targetToModel, modelToTarget);
	}

	/**
	 * Lets the user select an object and returns the selection.
	 *
	 * @param attribute the attribute for which an object is needed
	 * @return the object
	 */
	protected Object getSelectedObject(EAttribute attribute) {
		Object object = null;
		// final EAttribute attribute = (EAttribute) structuralFeature;
		if (attribute == null) {
			showError(shell, "Missing Attribute", //$NON-NLS-1$
				"Please select an attribute before attempting to select its value."); //$NON-NLS-1$
			return object;
		}
		Class<?> attribuetClazz = attribute.getEAttributeType().getInstanceClass();

		if (attribuetClazz == null) {
			if (EcorePackage.eINSTANCE.getEEnum().isInstance(attribute.getEType())) {
				attribuetClazz = Enum.class;
				final EList<EEnumLiteral> eLiterals = EEnumImpl.class.cast(attribute.getEType()).getELiterals();
				final Object[] enumValues = eLiterals.toArray();
				final ListDialog ld = new ListDialog(shell);
				ld.setLabelProvider(new LabelProvider());
				ld.setContentProvider(ArrayContentProvider.getInstance());
				ld.setInput(enumValues);
				ld.setInitialSelections(new Object[] { enumValues[0] });
				ld.setMessage("Please select the enum value to set."); //$NON-NLS-1$
				ld.setTitle("Select a value"); //$NON-NLS-1$
				final int enumSelectionResult = ld.open();
				if (Window.OK == enumSelectionResult) {
					object = EEnumLiteral.class.cast(ld.getResult()[0]).getLiteral();
				}
			} else {
				return null;
			}
		} else {
			if (attribuetClazz.isPrimitive()) {
				attribuetClazz = getAttributeClass(attribuetClazz);
			}
			if (Enum.class.isAssignableFrom(attribuetClazz)) {
				final Object[] enumValues = attribuetClazz.getEnumConstants();
				final ListDialog ld = new ListDialog(shell);
				ld.setLabelProvider(new LabelProvider());
				ld.setContentProvider(ArrayContentProvider.getInstance());
				ld.setInput(enumValues);
				ld.setInitialSelections(new Object[] { enumValues[0] });
				ld.setMessage("Please select the enum value to set."); //$NON-NLS-1$
				ld.setTitle("Select a value"); //$NON-NLS-1$
				final int enumSelectionResult = ld.open();
				if (Window.OK == enumSelectionResult) {
					object = Enumerator.class.cast(ld.getResult()[0]).getLiteral();
				}
			} else if (String.class.isAssignableFrom(attribuetClazz)
				|| Number.class.isAssignableFrom(attribuetClazz)
				|| Boolean.class.isAssignableFrom(attribuetClazz)) {
				object = promptForValue(shell, object, attribuetClazz);
			} else {
				showError(shell, "Not primitive Attribute selected", //$NON-NLS-1$
					"The selected attribute has a not primitive type. We can't provide you support for it!"); //$NON-NLS-1$
			}
		}
		return object;
	}

	/**
	 * Shows an error message to the user.
	 *
	 * @param shell The Shell to show the error on
	 * @param title The title of the error message
	 * @param description The error description
	 */
	protected void showError(Shell shell, String title, String description) {
		MessageDialog.openError(shell, title, description);
	}

	/**
	 * Prompts the user to input a value. Return the original object if the input is cancelled.
	 *
	 * @param shell The Shell on which an input dialog will be opened
	 * @param object The current value
	 * @param attributeClazz The Class for which we want a value
	 * @return the prompted value, or the input object if the prompt was cancelled
	 */
	Object promptForValue(Shell shell, Object object, Class<?> attributeClazz) {
		try {
			final Constructor<?> constructor = attributeClazz.getConstructor(String.class);
			final InputDialog id = new InputDialog(
				shell,
				"Insert the value", //$NON-NLS-1$
				"The value must be parseable by the " //$NON-NLS-1$
					+ attributeClazz.getSimpleName()
					+ " class. For a double value please use the #.# format. For boolean values 'true' or 'false'.", //$NON-NLS-1$
				null, null);
			final int inputResult = id.open();
			if (Window.OK == inputResult) {
				object = constructor.newInstance(id.getValue());
			}
			if (Boolean.class.isAssignableFrom(attributeClazz) && !Boolean.class.cast(object)
				&& !"false".equalsIgnoreCase(id.getValue())) { //$NON-NLS-1$
				showError(shell, "Invalid boolean value", //$NON-NLS-1$
					"You have entered an invalid value. False has been chosen instead."); //$NON-NLS-1$
			}
		} catch (final IllegalArgumentException ex) {
			openInvalidValueMessage();
		} catch (final SecurityException ex) {
			openInvalidValueMessage();
		} catch (final NoSuchMethodException ex) {
			openInvalidValueMessage();
		} catch (final InstantiationException ex) {
			openInvalidValueMessage();
		} catch (final IllegalAccessException ex) {
			openInvalidValueMessage();
		} catch (final InvocationTargetException ex) {
			openInvalidValueMessage();
		}
		return object;
	}

	private Class<?> getAttributeClass(Class<?> attributeClazz) {
		if (int.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Integer.class;
		} else if (long.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Long.class;
		} else if (float.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Float.class;
		} else if (double.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Double.class;
		} else if (boolean.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Boolean.class;
		} else if (char.class.isAssignableFrom(attributeClazz)) {
			attributeClazz = Character.class;
		}
		return attributeClazz;
	}

	private void openInvalidValueMessage() {
		MessageDialog.openError(shell, "Invalid value", //$NON-NLS-1$
			"You have entered an invalid value. The previsous value will be kept."); //$NON-NLS-1$
	}

	/**
	 * Called when the select value button is pressed.
	 *
	 * @param text the label which should be used to set the value
	 */
	protected abstract void onSelectButton(Label text);

	/**
	 * Returns the model object representing the value for this renderer's domain model reference.
	 *
	 * @return the EObject
	 * @throws DatabindingFailedException if the databinding fails
	 */
	protected EObject getObservedEObject() throws DatabindingFailedException {
		final IObservableValue observableValue = getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EObject result = (EObject) ((IObserving) observableValue).getObserved();
		observableValue.dispose();
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The strategy to convert from model to target.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	protected class ModelToTargetUpdateStrategy extends EMFUpdateValueStrategy {

		@Override
		public Object convert(Object value) {
			final Object converted = value.toString();
			if (String.class.isInstance(converted)) {
				IObservableValue observableValue;
				try {
					observableValue = getEMFFormsDatabinding()
						.getObservableValue(getVElement().getDomainModelReference(),
							getViewModelContext().getDomainModel());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
					return converted;
				}
				final InternalEObject internalEObject = (InternalEObject) ((IObserving) observableValue).getObserved();
				final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
				observableValue.dispose();
				return ECPTooltipModifierHelper.modifyString(String.class.cast(converted),
					internalEObject.eSetting(structuralFeature));
			}
			return converted;
		}

	}

	/**
	 * The strategy to convert from target to model.
	 *
	 * @author Eugen
	 *
	 */
	protected class TargetToModelUpdateStrategy extends EMFUpdateValueStrategy {

		/**
		 * Constructor for indicating whether a value is unsettable.
		 */
		public TargetToModelUpdateStrategy() {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object convert(Object value) {
			return value;
		}
	}

}
