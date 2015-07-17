/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.internal.categorization.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * SWT categorization renderer.
 *
 * @author emueller
 * @author Eugen Neufeld
 *
 */
public class SWTCategorizationRenderer extends AbstractSWTRenderer<VCategorization> {
	private final EMFDataBindingContext dataBindingContext;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @since 1.6
	 */
	@Inject
	public SWTCategorizationRenderer(VCategorization vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
		dataBindingContext = new EMFDataBindingContext();
	}

	private SWTGridDescription rendererGridDescription;

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		dataBindingContext.dispose();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final Composite categoryComposite = new Composite(parent, SWT.NONE);
		categoryComposite.setBackground(parent.getBackground());
		categoryComposite.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_categorization"); //$NON-NLS-1$

		categoryComposite.setLayout(LayoutProviderHelper.getColumnLayout(1, false));

		final Label headingLbl = new Label(categoryComposite, SWT.NONE);
		headingLbl.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_categorization_title"); //$NON-NLS-1$
		final Label whatToDoLbl = new Label(categoryComposite, SWT.NONE);
		whatToDoLbl.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_categorization_message"); //$NON-NLS-1$

		final IObservableValue modelValue = EMFEditObservables.observeValue(
			AdapterFactoryEditingDomain.getEditingDomainFor(getVElement()), getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		final IObservableValue targetValue = SWTObservables.observeText(headingLbl);
		dataBindingContext.bindValue(targetValue, modelValue);

		whatToDoLbl.setText(LocalizationServiceHelper.getString(getClass(), MessageKeys.Categorization_Selection));
		return categoryComposite;
	}

}
