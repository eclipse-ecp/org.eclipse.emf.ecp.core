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
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

/**
 * This class defines a SingleControl which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature
 * EStructuralFeature}s which have maximum 1
 * value.
 * 
 * @author Eugen Neufeld
 * 
 */
public abstract class SingleControl extends SWTControl {

	//	private static final String VALIDATION_ERROR_ICON = "icons/validation_error.png";//$NON-NLS-1$
	private ControlDecoration controlDecoration;

	// private static final Color VALIDATION_ERROR_BACKGROUND_COLOR=new Color(Display.getDefault(), 255, 140, 0);

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void handleValidation(Diagnostic diagnostic) {
		if (diagnostic.getData().size() < 2) {
			return;
		}
		if (!diagnostic.getData().get(0).equals(getFirstSetting().getEObject())
			|| !diagnostic.getData().get(1).equals(getFirstStructuralFeature())) {
			return;
		}

		final Image image = getValidationIcon(diagnostic.getSeverity());
		Diagnostic reason = diagnostic;
		if (diagnostic.getChildren() != null && diagnostic.getChildren().size() != 0) {
			reason = diagnostic.getChildren().get(0);
		}
		if (validationLabel != null) {
			validationLabel.setImage(image);
			validationLabel.setToolTipText(reason.getMessage());

		}
		if (controlDecoration != null) {
			controlDecoration.setDescriptionText(reason.getMessage());
			controlDecoration.show();
		}
		updateValidationColor(getValidationBackgroundColor(diagnostic.getSeverity()));
	}

	/**
	 * Allows controls to supply a second visual effect for controls on validation. The color to set is provided as the
	 * parameter.
	 * 
	 * @param color the color to set, null if the default background color should be set
	 */
	protected void updateValidationColor(Color color) {

	}

	protected void addControlDecoration(Control control) {
		// controlDecoration = new ControlDecoration(control, SWT.TOP | SWT.LEFT);
		// final FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(
		// FieldDecorationRegistry.DEC_ERROR);
		// controlDecoration.setImage(fieldDecoration.getImage());
		// controlDecoration.hide();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void resetValidation() {
		if (validationLabel == null || validationLabel.isDisposed()) {
			return;
		}
		updateValidationColor(null);
		validationLabel.setImage(null);
		validationLabel.setToolTipText(""); //$NON-NLS-1$
		if (controlDecoration != null) {
			controlDecoration.hide();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (validationLabel != null) {
			validationLabel.dispose();
		}
		if (controlDecoration != null) {
			controlDecoration.dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	@Override
	@Deprecated
	public boolean showLabel() {
		return true;
	}
}
