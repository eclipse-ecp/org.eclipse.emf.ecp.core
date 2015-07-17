/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.stack.ui.swt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.internal.stack.ui.swt.Activator;
import org.eclipse.emf.ecp.view.internal.stack.ui.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * The SWT {@link VStackLayout} renderer.
 *
 * @author jfaltermeier
 *
 */
public class SWTStackLayoutRenderer extends AbstractSWTRenderer<VStackLayout> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @since 1.6
	 */
	public SWTStackLayoutRenderer(VStackLayout vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	private static final String CONTROL_STACK_COMPOSITE = "org_eclipse_emf_ecp_ui_layout_stack"; //$NON-NLS-1$

	private SWTGridDescription gridDescription;
	private ModelChangeListener listener;
	private Map<VStackItem, Composite> itemToCompositeMap;
	private StackLayout stackLayout;
	private Composite stackComposite;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (this.gridDescription == null) {
			this.gridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return this.gridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell,
	 *      org.eclipse.emf.ecp.view.spi.swt.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {

		if (cell.getColumn() != 0) {
			return null;
		}

		stackComposite = new Composite(parent, SWT.NONE);
		stackComposite.setBackground(parent.getBackground());
		stackComposite.setData(CUSTOM_VARIANT, CONTROL_STACK_COMPOSITE);

		stackLayout = new StackLayout();
		stackComposite.setLayout(stackLayout);

		final Map<VStackItem, AbstractSWTRenderer<VElement>> elementRendererMap = new LinkedHashMap<VStackItem, AbstractSWTRenderer<VElement>>();
		for (final VStackItem item : getVElement().getStackItems()) {
			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getEMFFormsRendererFactory().getRendererInstance(item,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(new StatusReport(
					new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
						LocalizationServiceHelper.getString(getClass(),
							MessageKeys.SWTStackLayoutRenderer_NoRendererForItemCompositeFound), item.eClass()
							.getName(), ex))));
				continue;
			}
			elementRendererMap.put(item, renderer);
		}

		for (final Entry<VStackItem, AbstractSWTRenderer<VElement>> entry : elementRendererMap.entrySet()) {
			final VStackItem stackItem = entry.getKey();
			final AbstractSWTRenderer<VElement> renderer = entry.getValue();

			final Composite itemComposite = new Composite(stackComposite, SWT.NONE);
			itemComposite.setBackground(parent.getBackground());
			final SWTGridDescription elementGridDescription = renderer
				.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
			itemComposite.setLayoutData(LayoutProviderHelper.getSpanningLayoutData(1, 1));
			itemComposite.setLayout(LayoutProviderHelper.getColumnLayout(elementGridDescription.getColumns(), false));

			for (final SWTGridCell currentCell : elementGridDescription.getGrid()) {
				final Control control = currentCell.getRenderer().render(currentCell, itemComposite);
				if (control == null) {
					continue;
				}
				control.setLayoutData(LayoutProviderHelper.getLayoutData(currentCell, elementGridDescription,
					elementGridDescription, elementGridDescription, currentCell.getRenderer().getVElement(),
					getViewModelContext().getDomainModel(), control));
			}

			for (final SWTGridCell currentCell : elementGridDescription.getGrid()) {
				currentCell.getRenderer().finalizeRendering(itemComposite);
			}

			itemToCompositeMap.put(stackItem, itemComposite);
		}

		/* Add empty composite if value with no matching item is set */
		final Composite nullComposite = new Composite(stackComposite, SWT.NONE);
		itemToCompositeMap.put(null, nullComposite);

		setTopElement();
		return stackComposite;
	}

	private void setTopElement() {
		final VStackItem topElement = getVElement().getTopElement();
		final Composite composite = itemToCompositeMap.get(topElement);
		if (composite == null) {
			return;
		}
		stackLayout.topControl = composite;
		stackComposite.layout();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#preInit()
	 */
	@Override
	protected void preInit() {
		super.preInit();

		listener = new ModelChangeListener() {
			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().isTouch()) {
					return;
				}
				if (notification.getNotifier() != getVElement()) {
					return;
				}
				if (notification.getStructuralFeature() == VStackPackage.eINSTANCE.getStackLayout_TopElement()) {
					setTopElement();
				}
			}
		};

		itemToCompositeMap = new LinkedHashMap<VStackItem, Composite>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#postInit()
	 */
	@Override
	protected void postInit() {
		super.postInit();
		setTopElement();
		getViewModelContext().registerViewChangeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		getViewModelContext().unregisterViewChangeListener(listener);
		gridDescription = null;
		listener = null;
		itemToCompositeMap.clear();
		itemToCompositeMap = null;
		stackLayout = null;
		stackComposite = null;
		super.dispose();
	}

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		final ServiceReference<EMFFormsRendererFactory> serviceReference = bundleContext
			.getServiceReference(EMFFormsRendererFactory.class);
		final EMFFormsRendererFactory rendererFactory = bundleContext.getService(serviceReference);
		bundleContext.ungetService(serviceReference);
		return rendererFactory;
	}
}
