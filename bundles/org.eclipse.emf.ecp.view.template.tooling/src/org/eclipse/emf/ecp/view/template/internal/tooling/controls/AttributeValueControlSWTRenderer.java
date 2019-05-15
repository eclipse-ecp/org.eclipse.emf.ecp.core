/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.internal.tooling.util.ValueSelectionHelper;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Renderer for the
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector#setAttributeValue(Object)
 * VTViewModelElementSelector#setAttributeValue(Object)}.
 *
 * @author Eugen Neufeld
 *
 */
public class AttributeValueControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final EMFFormsDatabinding EMFFORMS_DATABINDING;
	private static final EMFFormsLabelProvider EMFFORMS_LABELPROVIDER;
	private static final VTViewTemplateProvider VT_VIEW_TEMPLATEPROVIDER;

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(AttributeValueControlSWTRenderer.class)
			.getBundleContext();
		final ServiceReference<EMFFormsDatabinding> emfFormsDatabindingServiceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		EMFFORMS_DATABINDING = bundleContext.getService(emfFormsDatabindingServiceReference);
		final ServiceReference<EMFFormsLabelProvider> emfFormsLabelProviderServiceReference = bundleContext
			.getServiceReference(EMFFormsLabelProvider.class);
		EMFFORMS_LABELPROVIDER = bundleContext.getService(emfFormsLabelProviderServiceReference);
		final ServiceReference<VTViewTemplateProvider> vtViewTemplateProviderServiceReference = bundleContext
			.getServiceReference(VTViewTemplateProvider.class);
		VT_VIEW_TEMPLATEPROVIDER = bundleContext.getService(vtViewTemplateProviderServiceReference);
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 */
	public AttributeValueControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService, EMFFORMS_DATABINDING, EMFFORMS_LABELPROVIDER,
			VT_VIEW_TEMPLATEPROVIDER);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {
		final Label label = (Label) Composite.class.cast(control).getChildren()[0];
		final Binding binding = getDataBindingContext().bindValue(WidgetProperties.text().observe(label),
			getModelValue(), withPreSetValidation(new UpdateValueStrategy()), new UpdateValueStrategy() {

				/**
				 * {@inheritDoc}
				 *
				 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
				 */
				@Override
				public Object convert(Object value) {
					return super.convert(value).toString();
				}

			});
		return new Binding[] { binding };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		final Label label = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(label);

		final Button button = new Button(composite, SWT.PUSH);
		button.setText(Messages.AttributeValueControlSWTRenderer_SelectAttributeValue);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).grab(false, false).applyTo(button);

		button.addSelectionListener(new SelectionAdapter() {
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
				final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
				final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
				observableValue.dispose();

				final Object result = ValueSelectionHelper.openValueSelectionDialog(parent.getShell(),
					VTViewModelElementSelector.class.cast(eObject).getAttribute());
				final EditingDomain editingDomain = getEditingDomain(eObject);
				final Command command = SetCommand.create(editingDomain, eObject, structuralFeature, result);
				editingDomain.getCommandStack().execute(command);
			}
		});

		return composite;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return Messages.AttributeValueControlSWTRenderer_UnsetAttributeMessage;
	}

}
