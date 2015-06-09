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
import org.eclipse.jface.databinding.swt.SWTObservables;
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
import org.eclipse.ui.dialogs.ListDialog;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A control for defining an value in a leaf condition.
 *
 * @author Eugen Neufeld
 *
 */
// APITODO no api yet
public abstract class ExpectedValueControlRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final EMFFormsDatabinding emfFormsDatabinding;
	private static final EMFFormsLabelProvider emfFormsLabelProvider;
	private static final VTViewTemplateProvider vtViewTemplateProvider;

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ExpectedValueControlRenderer.class)
			.getBundleContext();
		final ServiceReference<EMFFormsDatabinding> emfFormsDatabindingServiceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		emfFormsDatabinding = bundleContext.getService(emfFormsDatabindingServiceReference);
		final ServiceReference<EMFFormsLabelProvider> emfFormsLabelProviderServiceReference = bundleContext
			.getServiceReference(EMFFormsLabelProvider.class);
		emfFormsLabelProvider = bundleContext.getService(emfFormsLabelProviderServiceReference);
		final ServiceReference<VTViewTemplateProvider> vtViewTemplateProviderServiceReference = bundleContext
			.getServiceReference(VTViewTemplateProvider.class);
		vtViewTemplateProvider = bundleContext.getService(vtViewTemplateProviderServiceReference);
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 */
	public ExpectedValueControlRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	private Label text;

	private String getTextVariantID() {
		return "org_eclipse_emf_ecp_view_editor_controls_ruleattribute"; //$NON-NLS-1$
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

		final IObservableValue value = SWTObservables.observeText(text);

		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			targetToModelUpdateStrategy, modelToTargetUpdateStrategy);
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
		final IObservableValue toolTip = SWTObservables.observeTooltipText(text);
		return dataBindingContext.bindValue(toolTip, modelValue, targetToModel, modelToTarget);
	}

	protected Object getSelectedObject(EAttribute attribute) {
		Object object = null;
		// final EAttribute attribute = (EAttribute) structuralFeature;
		Class<?> attribuetClazz = attribute.getEAttributeType().getInstanceClass();

		if (attribuetClazz == null) {
			if (EcorePackage.eINSTANCE.getEEnum().isInstance(attribute.getEType())) {
				attribuetClazz = Enum.class;
				final EList<EEnumLiteral> eLiterals = EEnumImpl.class.cast(attribute.getEType()).getELiterals();
				final Object[] enumValues = eLiterals.toArray();
				final ListDialog ld = new ListDialog(text.getShell());
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
				if (int.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Integer.class;
				} else if (long.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Long.class;
				} else if (float.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Float.class;
				} else if (double.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Double.class;
				} else if (boolean.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Boolean.class;
				} else if (char.class.isAssignableFrom(attribuetClazz)) {
					attribuetClazz = Character.class;
				}
			}
			if (Enum.class.isAssignableFrom(attribuetClazz)) {
				final Object[] enumValues = attribuetClazz.getEnumConstants();
				final ListDialog ld = new ListDialog(text.getShell());
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
			}
			else if (String.class.isAssignableFrom(attribuetClazz)
				|| Number.class.isAssignableFrom(attribuetClazz)
				|| Boolean.class.isAssignableFrom(attribuetClazz)) {
				try {
					final Constructor<?> constructor = attribuetClazz.getConstructor(String.class);
					final InputDialog id = new InputDialog(
						text.getShell(),
						"Insert the value", //$NON-NLS-1$
						"The value must be parseable by the " //$NON-NLS-1$
							+ attribuetClazz.getSimpleName()
							+ " class. For a double value please use the #.# format. For boolean values 'true' or 'false'.", //$NON-NLS-1$
						null, null);
					final int inputResult = id.open();
					if (Window.OK == inputResult) {
						object = constructor.newInstance(id.getValue());
					}
				} catch (final IllegalArgumentException ex) {
				} catch (final SecurityException ex) {
				} catch (final NoSuchMethodException ex) {
				} catch (final InstantiationException ex) {
				} catch (final IllegalAccessException ex) {
				} catch (final InvocationTargetException ex) {
				}
			} else {
				MessageDialog.openError(text.getShell(), "Not primitive Attribute selected", //$NON-NLS-1$
					"The selected attribute has a not primitive type. We can't provide you support for it!"); //$NON-NLS-1$
			}
		}
		return object;
	}

	/**
	 * @param shell
	 *
	 */
	protected abstract void onSelectButton(Label text);

	/**
	 * Returns the model object representing the value for this renderer's domain model reference.
	 *
	 * @return the EObject
	 * @throws DatabindingFailedException if the databinding fails
	 */
	protected EObject getObservedEObject() throws DatabindingFailedException {
		final IObservableValue observableValue = Activator.getDefault().getEMFFormsDatabinding()
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

		public ModelToTargetUpdateStrategy() {
		}

		@Override
		public Object convert(Object value) {
			final Object converted = value.toString();
			if (String.class.isInstance(converted)) {
				IObservableValue observableValue;
				try {
					observableValue = emfFormsDatabinding
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
