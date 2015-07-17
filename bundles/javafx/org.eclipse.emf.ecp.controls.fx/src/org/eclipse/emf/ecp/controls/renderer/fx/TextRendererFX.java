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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecp.controls.fx.util.ECPTextFieldToModelUpdateValueStrategy;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

public class TextRendererFX extends SimpleControlRendererFX {

	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final TextField text = new TextField();
		text.setEditable(!control.isReadonly());
		final IObservableValue targetValue = getTargetObservable(text, "text"); //$NON-NLS-1$
		final IObservableValue modelValue = getModelObservable(control
			.getDomainModelReference().getIterator().next());
		final Binding binding = bindModelToTarget(targetValue, modelValue, getTargetToModelStrategy(control),
			getModelToTargetStrategy(control));

		text.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					binding.updateTargetToModel();
				}
			}
		});

		control.eAdapters().add(new AdapterImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VViewPackage.eINSTANCE
					.getElement_Diagnostic()) {
					applyValidation(control, text);
				}
			}

		});

		applyValidation(control, text);
		text.setMaxWidth(Double.MAX_VALUE);

		return text;
	}

	protected UpdateValueStrategy getModelToTargetStrategy(VControl control) {
		return new EMFUpdateValueStrategy();
	}

	protected UpdateValueStrategy getTargetToModelStrategy(VControl control) {
		return new ECPTextFieldToModelUpdateValueStrategy();
	}
}
