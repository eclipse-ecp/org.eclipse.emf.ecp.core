/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.internal.categorization.swt.Activator;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Abstract class for a tab renderer.
 *
 * @author Eugen Neufeld
 * @param <VELEMENT> the {@link VElement}
 */
public abstract class AbstractSWTTabRenderer<VELEMENT extends VElement> extends AbstractSWTRenderer<VELEMENT> {
	private final EMFFormsRendererFactory emfFormsRendererFactory;
	private final EMFDataBindingContext dataBindingContext;

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return emfFormsRendererFactory;
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @since 1.6
	 */
	public AbstractSWTTabRenderer(VELEMENT vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory) {
		super(vElement, viewContext, reportService);
		this.emfFormsRendererFactory = emfFormsRendererFactory;
		dataBindingContext = new EMFDataBindingContext();
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final CTabFolder folder = new CTabFolder(parent, SWT.BOTTOM);
		folder.setBackground(parent.getBackground());
		final EList<VAbstractCategorization> categorizations = getCategorizations();
		for (final VAbstractCategorization categorization : categorizations) {
			final CTabItem item = new CTabItem(folder, SWT.NULL);
			final ScrolledComposite scrolledComposite = new ScrolledComposite(folder, SWT.V_SCROLL | SWT.H_SCROLL);

			final IObservableValue modelValue = EMFEditObservables.observeValue(
				AdapterFactoryEditingDomain.getEditingDomainFor(categorization), categorization,
				VViewPackage.eINSTANCE.getElement_Label());
			final IObservableValue targetValue = SWTObservables.observeText(item);
			dataBindingContext.bindValue(targetValue, modelValue);

			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getEMFFormsRendererFactory().getRendererInstance(categorization,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(
					new StatusReport(
						new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
							"No Renderer for %s found.", categorization.eClass().getName(), ex)))); //$NON-NLS-1$
				return null;
			}
			final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
			for (final SWTGridCell gridCell : gridDescription.getGrid()) {
				final Control render = renderer.render(gridCell, scrolledComposite);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
					.applyTo(render);
				scrolledComposite.setExpandHorizontal(true);
				scrolledComposite.setExpandVertical(true);
				scrolledComposite.setContent(render);
				scrolledComposite.setMinSize(render.computeSize(SWT.DEFAULT, SWT.DEFAULT));

				item.setControl(scrolledComposite);
			}

		}
		if (folder.getItemCount() > 0) {
			folder.setSelection(0);
		}
		return folder;
	}

	/**
	 * The list of categorizations to display in the tree.
	 *
	 * @return the list of {@link VAbstractCategorization}
	 */
	protected abstract EList<VAbstractCategorization> getCategorizations();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		dataBindingContext.dispose();
		super.dispose();
	}

}
