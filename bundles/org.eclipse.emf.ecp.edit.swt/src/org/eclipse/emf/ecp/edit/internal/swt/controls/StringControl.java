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

import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

/**
 * The class describing a String control.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class StringControl extends AbstractTextControl {

	/** {@inheritDoc} */
	@Override
	protected GridData getTextWidgetLayoutData() {
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);

		if (getItemPropertyDescriptor(getFirstSetting()).isMultiLine(null)) {
			gridData.heightHint = 200;
		}
		return gridData;
	}

	/** {@inheritDoc} */
	@Override
	protected int getTextWidgetStyle() {
		int textStyle = SWT.BORDER;
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		if (getItemPropertyDescriptor(getFirstSetting()).isMultiLine(null)) {
			textStyle = textStyle | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL;
			gridData.heightHint = 200;
		} else {
			textStyle = textStyle | SWT.SINGLE;
		}
		return textStyle;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.AbstractTextControl#getTextVariantID()
	 */
	@Override
	protected String getTextVariantID() {
		return "org_eclipse_emf_ecp_control_string"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.StringControl_NoTextSetClickToSetText);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), DepricatedControlMessageKeys.StringControl_UnsetText);
	}

	@Override
	protected void customizeText(Text text) {
		super.customizeText(text);
		text.setMessage(getItemPropertyDescriptor(getFirstSetting()).getDisplayName(null));
	}
}
