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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;

/**
 * This abstract class is used as a common superclass for all widgets that use a {@link Text} widget.
 * 
 * @author Eugen Neufeld
 */
public abstract class AbstractTextControl extends SingleControl {

	/**
	 * The {@link Text} holding the value.
	 */
	private Text text;
	private boolean doVerify;

	@Override
	protected void updateValidationColor(Color color) {
		text.setBackground(color);
	}

	@Override
	protected void fillControlComposite(Composite composite) {
		doVerify = false;
		createTextWidget(composite);
		addControlDecoration(text);
	}

	private void createTextWidget(Composite composite) {
		text = new Text(composite, getTextWidgetStyle());
		text.setLayoutData(getTextWidgetLayoutData());
		if (getFirstStructuralFeature().isUnsettable()) {
			text.setMessage(ControlMessages.AbstractTextControl_Unset);
		}
		text.setData(CUSTOM_VARIANT, getTextVariantID());
		customizeText(text);

	}

	/**
	 * Creates a {@link ToolTip}.
	 * 
	 * @param style the SWT style
	 * @param text the text
	 * @param message the message
	 * @return a {@link ToolTip}
	 */
	protected ToolTip createToolTip(int style, String text, String message) {
		final ToolTip toolTip = new ToolTip(this.text.getShell(), style);
		toolTip.setText(text);
		toolTip.setMessage(message);
		return toolTip;

	}

	/**
	 * This method allows to set custom values to the text field, e.g. a tooltip or a validation.
	 * 
	 * @param text the text widget to customize
	 */
	protected void customizeText(Text text) {

	}

	/**
	 * The VariantId to use e.g. for RAP
	 * 
	 * @return the String identifying this control
	 */
	protected abstract String getTextVariantID();

	/**
	 * The LayoutData for the created {@link Text} widget. Can be changed by the concrete classes.
	 * 
	 * @return the {@link GridData} to apply
	 */
	protected GridData getTextWidgetLayoutData() {
		return new GridData(SWT.FILL, SWT.FILL, true, true);
	}

	/**
	 * The style to apply to the text widget. This can be changed by the concrete classes.
	 * 
	 * @return the style to apply
	 */
	protected int getTextWidgetStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}

	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { text };
		return new Control[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditable(boolean isEditable) {
		text.setEditable(isEditable);
	}

	@Override
	public Binding bindValue() {
		final IObservableValue value = SWTObservables.observeText(text, SWT.FocusOut);
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			new TargetToModelUpdateStrategy(),
			new ModelToTargetUpdateStrategy());
		return binding;
	}

	/**
	 * Sets the content of the SWT text control to the given string without calling {@link #validateString(String)}.
	 * 
	 * @param string
	 *            the content of the SWT Text control
	 */
	protected void setUnvalidatedString(String string) {
		final boolean oldDoVerify = doVerify;
		doVerify = false;
		text.setText(string);
		doVerify = oldDoVerify;
	}

	@Override
	public void dispose() {
		text.dispose();
		super.dispose();
	}

	/**
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/**
	 * An {@link EMFUpdateConvertValueStrategy} that encapsulates the converting
	 * of the actual value. Use this class to provide a specific context
	 * for the conversion of the value, but likewise enable it clients to modify
	 * the conversion behavior.
	 * 
	 * @author emueller
	 * 
	 */
	class EMFUpdateConvertValueStrategy extends EMFUpdateValueStrategy {

		/**
		 * Constructor.
		 */
		public EMFUpdateConvertValueStrategy() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
		 */
		@Override
		public Object convert(Object value) {
			return convertValue(value);
		}

		protected Object convertValue(Object value) {
			return super.convert(value);
		}
	}

	protected class ModelToTargetUpdateStrategy extends EMFUpdateConvertValueStrategy {

		@Override
		public Object convert(Object value) {
			// controlDecoration.hide();
			// updateValidationColor(null);
			return convertValue(value);
		}

	}

	protected class TargetToModelUpdateStrategy extends EMFUpdateConvertValueStrategy {

		@Override
		public IStatus validateAfterGet(Object value) {
			final IStatus status = super.validateAfterGet(value);
			// if (status.getSeverity() == IStatus.ERROR) {
			// controlDecoration.show();
			// controlDecoration.setDescriptionText(status.getMessage());
			// } else {
			// controlDecoration.hide();
			// controlDecoration.setDescriptionText(null);
			// }
			return status;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object convert(Object value) {
			try {

				// controlDecoration.hide();
				// updateValidationColor(null);
				if ("".equals(value)) { //$NON-NLS-1$
					value = null;
				}
				if (value == null && getFirstStructuralFeature().isUnsettable()) {
					return SetCommand.UNSET_VALUE;
				}

				final Object convertedValue = convertValue(value);

				return convertedValue;

			} catch (final IllegalArgumentException e) {
				// controlDecoration.show();
				// updateValidationColor(getText().getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
				// controlDecoration.setDescriptionText(ControlMessages.AbstractTextControl_InvalidInputSpace
				// + e.getLocalizedMessage());
				throw e;
			}
		}
	}
}
