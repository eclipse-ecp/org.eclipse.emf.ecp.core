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

import javafx.scene.Node;
import javafx.scene.control.DatePicker;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * @author Lucas
 *
 */
public abstract class AbstractDateRendererFX extends SimpleControlRendererFX {

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
		final IObservableValue modelValue = getModelObservable(control
			.getDomainModelReference().getIterator().next());
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
