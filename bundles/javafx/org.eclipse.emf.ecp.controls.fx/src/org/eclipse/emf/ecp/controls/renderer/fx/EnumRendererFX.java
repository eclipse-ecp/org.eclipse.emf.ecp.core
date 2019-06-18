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

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.internal.fx.SimpleControlRendererFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

public class EnumRendererFX extends SimpleControlRendererFX {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public EnumRendererFX(VControl vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Node createControl() {
		final VControl control = getVElement();
		final ChoiceBox choiceBox = new ChoiceBox();
		choiceBox.setItems(FXCollections.observableArrayList(EStructuralFeature.class
			.cast(getModelObservable().getValueType()).getEType().getInstanceClass().getEnumConstants()));
		choiceBox.setMaxWidth(Double.MAX_VALUE);

		final IObservableValue targetValue = getTargetObservable(choiceBox, "value");
		final IObservableValue modelValue = getModelObservable();
		bindModelToTarget(targetValue, modelValue, null,
			null);

		control.eAdapters().add(new AdapterImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VViewPackage.eINSTANCE
					.getElement_Diagnostic()) {
					applyValidation(control, choiceBox);
				}
			}

		});

		applyValidation(control, choiceBox);

		return choiceBox;
	}

}
