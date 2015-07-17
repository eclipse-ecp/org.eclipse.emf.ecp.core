/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class defines a BooleanControl which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature
 * EStructuralFeature}s which have a Boolean
 * value.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class BooleanControl extends SingleControl {

	private Button check;

	@Override
	protected void fillControlComposite(Composite composite) {
		check = new Button(composite, SWT.CHECK);
		check.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_boolean"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditable(boolean isEditable) {
		check.setEnabled(isEditable);
	}

	@Override
	public void dispose() {
		check.dispose();
		super.dispose();
	}

	@Override
	public Binding bindValue() {
		final IObservableValue targetValue = SWTObservables.observeSelection(check);
		return getDataBindingContext().bindValue(targetValue, getModelValue());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.BooleanControl_NoBooleanSetClickToSetBoolean);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper
			.getString(getClass(), DepricatedControlMessageKeys.BooleanControl_UnsetBoolean);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getControlForTooltip()
	 */
	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { check };
		return new Control[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#updateValidationColor(org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void updateValidationColor(Color color) {
		check.setBackground(color);
	}
}
