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
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper;
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
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Alexandra Buzila
 *
 */
public class EReferenceLabelControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private static final EMFFormsDatabinding EMFFORMS_DATABINDING;
	private static final EMFFormsLabelProvider EMFFORMS_LABELPROVIDER;
	private static final VTViewTemplateProvider VT_VIEW_TEMPLATEPROVIDER;

	static {
		final BundleContext bundleContext = FrameworkUtil.getBundle(EReferenceLabelControlSWTRenderer.class)
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
	public EReferenceLabelControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService, EMFFORMS_DATABINDING, EMFFORMS_LABELPROVIDER,
			VT_VIEW_TEMPLATEPROVIDER);
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
		return SWTImageHelper.getImage(adapterFactoryItemDelegator.getImage(value));
	}

	/**
	 * Computes the label text for the referenced object.
	 * 
	 * @param value The referenced object
	 * @return the label text to display
	 */
	protected Object getText(Object value) {
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
		if (value == null) {
			return;
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
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	protected Control createSWTControl(Composite parent2) throws DatabindingFailedException {
		final Composite composite2 = new Composite(parent2, SWT.NONE);
		composite2.setBackground(parent2.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(0, 0).equalWidth(false).applyTo(composite2);

		labelComposite = new Composite(composite2, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(labelComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(labelComposite);
		labelComposite.setBackground(composite2.getBackground());

		// create labels
		imageLabel = new Label(labelComposite, SWT.NONE);
		imageLabel.setBackground(labelComposite.getBackground());
		label = new Label(labelComposite, SWT.NONE);
		label.setBackground(labelComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.CENTER).applyTo(label);
		GridDataFactory.fillDefaults().grab(false, true).align(SWT.FILL, SWT.CENTER).hint(20, 20)
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
