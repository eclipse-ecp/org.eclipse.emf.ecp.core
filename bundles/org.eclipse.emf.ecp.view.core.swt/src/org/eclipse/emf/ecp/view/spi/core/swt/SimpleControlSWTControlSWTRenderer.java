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
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
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
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SimpleControlSWTControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * Creates the control itself.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the rendered control
	 */
	@Override
	protected final Control createControl(Composite parent) {
		if (!getVElement().getDomainModelReference().getIterator().hasNext()) {
			return null;
		}
		final Setting setting = getVElement().getDomainModelReference().getIterator().next();

		final Control control = createSWTControl(parent, setting);
		if (control == null) {
			return null;
		}
		final Binding[] bindings = createBindings(control, setting);

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
	 * @param setting the current {@link Setting}
	 * @return all the bindings created by this renderer
	 */
	protected abstract Binding[] createBindings(Control control, Setting setting);

	/**
	 * Creates the Control.
	 *
	 * @param parent the {@link Composite} to use as a parent
	 * @param setting the {@link Setting}
	 * @return the created control
	 */
	protected abstract Control createSWTControl(Composite parent, Setting setting);

}
