/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.renderer.fx;

import java.text.DecimalFormat;
import java.util.Locale;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.controls.fx.util.ECPNumericalFieldToModelUpdateValueStrategy;
import org.eclipse.emf.ecp.controls.fx.util.NumericalHelper;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class NumericalRendererFX extends TextRendererFX {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public NumericalRendererFX(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@Override
	protected Node createControl() {
		final Node textField = super.createControl();
		final Binding binding = (Binding) getDataBindingContext().getBindings().get(0);

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					binding.updateTargetToModel();
					binding.updateModelToTarget();
				}
			}
		});

		return textField;
	}

	@Override
	protected UpdateValueStrategy getModelToTargetStrategy(
		final VControl control) {
		return new EMFUpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				if (value == null || "".equals(value)) { //$NON-NLS-1$
					return null;
				}
				final DecimalFormat format = NumericalHelper.setupFormat(
					Locale.getDefault(), getInstanceClass(control));
				return format.format(value);
			}
		};
	}

	@Override
	protected UpdateValueStrategy getTargetToModelStrategy(VControl control) {
		return new ECPNumericalFieldToModelUpdateValueStrategy(
			getInstanceClass(control));
	}

	private Class<?> getInstanceClass(VControl control) {
		return ((EStructuralFeature) getModelObservable().getValueType()).getEType()
			.getInstanceClass();
	}
}
