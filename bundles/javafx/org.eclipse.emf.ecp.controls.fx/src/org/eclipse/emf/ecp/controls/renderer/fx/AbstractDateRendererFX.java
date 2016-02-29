/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.renderer.fx;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;

/**
 * @author Lucas
 *
 */
public abstract class AbstractDateRendererFX extends SimpleControlRendererFX {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public AbstractDateRendererFX(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX#createControl()
	 */
	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final DatePicker picker = new DatePicker();
		picker.setEditable(!control.isReadonly());
		final IObservableValue targetValue = getTargetObservable(picker, "value"); //$NON-NLS-1$
		final IObservableValue modelValue = getModelObservable();
		bindModelToTarget(targetValue, modelValue, getTargetToModelStrategy(),
			getModelToTargetStrategy());

		control.eAdapters().add(new AdapterImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VViewPackage.eINSTANCE
					.getElement_Diagnostic()) {
					applyValidation(control, picker);
				}
			}

		});

		applyValidation(control, picker);
		picker.setMaxWidth(Double.MAX_VALUE);
		return picker;
	}

	protected abstract UpdateValueStrategy getModelToTargetStrategy();

	protected abstract UpdateValueStrategy getTargetToModelStrategy();
}
