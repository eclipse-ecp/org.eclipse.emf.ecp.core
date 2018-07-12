/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.net.URL;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @author Alexandra Buzila
 *
 */
public class ControlRootEClassControl2SWTRenderer extends SimpleControlSWTControlSWTRenderer {

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
	@Inject
	public ControlRootEClassControl2SWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	private Composite labelComposite;
	private Label label;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private Label imageLabel;
	private ECPModelElementChangeListener modelElementChangeListener;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {

		final Binding[] bindings = new Binding[3];
		final IObservableValue value = WidgetProperties.text().observe(label);

		bindings[0] = getDataBindingContext().bindValue(value, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy() {

				@Override
				public Object convert(Object value) {
					try {
						return getModelValue().getValue();
					} catch (final DatabindingFailedException ex) {
						getReportService().report(new DatabindingFailedReport(ex));
						return null;
					}
				}
			}), new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					updateChangeListener((EObject) value);
					return getText(value);
				}
			});
		final IObservableValue tooltipValue = WidgetProperties.tooltipText().observe(label);
		bindings[1] = getDataBindingContext().bindValue(tooltipValue, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy() {

				@Override
				public Object convert(Object value) {
					try {
						return getModelValue().getValue();
					} catch (final DatabindingFailedException ex) {
						getReportService().report(new DatabindingFailedReport(ex));
						return null;
					}
				}
			}), new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getText(value);
				}
			});

		final IObservableValue imageValue = WidgetProperties.image().observe(imageLabel);
		bindings[2] = getDataBindingContext().bindValue(imageValue, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy() {

				@Override
				public Object convert(Object value) {
					try {
						return getModelValue().getValue();
					} catch (final DatabindingFailedException ex) {
						getReportService().report(new DatabindingFailedReport(ex));
						return null;
					}
				}
			}), new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getImage(value);
				}
			});

		return bindings;
	}

	private Object getImage(Object value) {
		return Activator.getImage((URL) adapterFactoryItemDelegator.getImage(value));
	}

	private Object getText(Object value) {
		final String textName = adapterFactoryItemDelegator.getText(value);
		return textName == null ? "" : textName; //$NON-NLS-1$
	}

	private void updateChangeListener(final EObject value) {
		if (modelElementChangeListener != null) {
			if (modelElementChangeListener.getTarget().equals(value)) {
				return;
			}
			modelElementChangeListener.remove();
			modelElementChangeListener = null;
		}

		modelElementChangeListener = new ECPModelElementChangeListener(value) {

			@Override
			public void onChange(Notification notification) {
				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						getDataBindingContext().updateTargets();
						labelComposite.layout();

					}

				});

			}
		};

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(Composite parent2) {
		final Composite composite2 = new Composite(parent2, SWT.NONE);
		composite2.setBackground(parent2.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(0, 0).equalWidth(false).applyTo(composite2);

		labelComposite = new Composite(composite2, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(labelComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(labelComposite);
		labelComposite.setBackground(composite2.getBackground());

		// create labels
		imageLabel = new Label(labelComposite, SWT.NONE);
		imageLabel.setBackground(labelComposite.getBackground());
		label = new Label(labelComposite, SWT.NONE);
		label.setBackground(labelComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(label);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL, SWT.BEGINNING).hint(20, 20)
			.applyTo(imageLabel);

		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return composite2;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return "Not set"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		composedAdapterFactory.dispose();
		if (modelElementChangeListener != null) {
			modelElementChangeListener.remove();
		}
		label.dispose();
		super.dispose();
	}

}
