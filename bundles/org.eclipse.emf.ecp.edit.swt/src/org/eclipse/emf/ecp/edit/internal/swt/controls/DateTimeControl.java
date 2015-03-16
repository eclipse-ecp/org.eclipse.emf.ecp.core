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
import org.eclipse.core.databinding.observable.value.DateAndTimeObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;

/**
 * This class defines a DateTimeControl which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature
 * EStructuralFeature}s which have a date
 * value.
 *
 * @author Eugen Neufeld
 *
 */
public class DateTimeControl extends SingleControl {

	private DateTime dateWidget;

	private DateTime timeWidget;

	@Override
	protected void fillControlComposite(Composite composite) {
		final Composite dateTimeComposite = new Composite(composite, SWT.NONE);
		dateTimeComposite.setBackground(composite.getBackground());
		int numColumns = 3;
		if (isEmbedded()) {
			numColumns = 2;
		}
		GridLayoutFactory.fillDefaults().numColumns(numColumns).spacing(2, 0).equalWidth(false)
			.applyTo(dateTimeComposite);

		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(dateTimeComposite);
		createDateAndTimeWidget(dateTimeComposite);

	}

	/**
	 * This method creates the date widget, the time widget and the delete button.
	 *
	 * @param composite the parent {@link Composite}
	 */
	private void createDateAndTimeWidget(Composite composite) {

		dateWidget = new DateTime(composite, SWT.DATE | SWT.BORDER);
		dateWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dateWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_date"); //$NON-NLS-1$

		timeWidget = new DateTime(composite, SWT.TIME | SWT.SHORT | SWT.BORDER);
		timeWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		timeWidget.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_dateTime_time"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditable(boolean isEditable) {
		dateWidget.setEnabled(isEditable);
		timeWidget.setEnabled(isEditable);
	}

	@Override
	public Binding bindValue() {
		final IObservableValue dateObserver = SWTObservables.observeSelection(dateWidget);
		final IObservableValue timeObserver = SWTObservables.observeSelection(timeWidget);
		final IObservableValue target = new DateAndTimeObservableValue(dateObserver, timeObserver);
		final Binding binding = getDataBindingContext().bindValue(target, getModelValue());
		return binding;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.DateTimeControl_NoDateSetClickToSetDate);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.DateTimeControl_UnsetDate);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getControlForTooltip()
	 */
	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { dateWidget, timeWidget };
		return new Control[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#updateValidationColor(org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void updateValidationColor(Color color) {
		dateWidget.setBackground(color);
		timeWidget.setBackground(color);
	}
}
