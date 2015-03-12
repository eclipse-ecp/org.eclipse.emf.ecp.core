/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renderer for {@link org.eclipse.swt.widgets.Control Controls}.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class SimpleControlSWTControlSWTRenderer extends SimpleControlSWTRenderer {

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
	public SimpleControlSWTControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
	}

	/**
	 * Creates the control itself.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the rendered control
	 * @throws DatabindingFailedException if the databinding of the control fails
	 */
	@Override
	protected final Control createControl(Composite parent) throws DatabindingFailedException {
		final Control control = createSWTControl(parent);
		if (control == null) {
			return null;
		}
		final Binding[] bindings = createBindings(control);

		control.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (bindings != null) {
					for (final Binding binding : bindings) {
						binding.dispose();
					}
				}

			}
		});

		return control;
	}

	/**
	 * Create the {@link Binding Bindings} for this controls.
	 *
	 * @param control the {@link Control} to create the binding for
	 * @return all the bindings created by this renderer
	 * @throws DatabindingFailedException if the creation of the bindings fails.
	 */
	protected abstract Binding[] createBindings(Control control) throws DatabindingFailedException;

	/**
	 * Creates the Control.
	 *
	 * @param parent the {@link Composite} to use as a parent
	 * @return the created control
	 * @throws DatabindingFailedException if the creation of the control fails due to databinding problems
	 */
	protected abstract Control createSWTControl(Composite parent) throws DatabindingFailedException;

}
