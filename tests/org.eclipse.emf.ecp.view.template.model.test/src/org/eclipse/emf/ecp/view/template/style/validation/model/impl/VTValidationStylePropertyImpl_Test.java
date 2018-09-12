/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.style.validation.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationFactory;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationStyleProperty;
import org.junit.Test;

/**
 * JUnit test cases for {@link VTValidationStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTValidationStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTValidationStyleProperty validationStyleProperty = VTValidationFactory.eINSTANCE
			.createValidationStyleProperty();
		final VTValidationStyleProperty validationStyleProperty2 = VTValidationFactory.eINSTANCE
			.createValidationStyleProperty();

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		assertTrue(validationStyleProperty.equalStyles(validationStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTValidationStyleProperty validationStyleProperty = VTValidationFactory.eINSTANCE
			.createValidationStyleProperty();
		final VTValidationStyleProperty validationStyleProperty2 = VTValidationFactory.eINSTANCE
			.createValidationStyleProperty();

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setCancelColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setCancelForegroundColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setCancelImageURL("different.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setCancelImageURL("different-overlay.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setErrorColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setErrorForegroundColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setErrorImageURL("different.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setErrorImageURL("different-overlay.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setInfoColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setInfoForegroundColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setInfoImageURL("different.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setInfoImageURL("different-overlay.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setOkColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setOkForegroundColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setOkImageURL("different.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setOkImageURL("different-overlay.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setWarningColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setWarningForegroundColorHEX("#bbbbbb"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setWarningImageURL("different.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));

		setEqualFields(validationStyleProperty, validationStyleProperty2);
		validationStyleProperty.setWarningImageURL("different-overlay.png"); //$NON-NLS-1$
		assertFalse(validationStyleProperty.equalStyles(validationStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		assertFalse(tableValidationStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTValidationStyleProperty prop1, VTValidationStyleProperty prop2) {
		prop1.setCancelColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setCancelColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setCancelForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setCancelForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setCancelImageURL("cancel.png"); //$NON-NLS-1$
		prop2.setCancelImageURL("cancel.png"); //$NON-NLS-1$

		prop1.setCancelOverlayURL("cancel-overlay.png"); //$NON-NLS-1$
		prop2.setCancelOverlayURL("cancel-overlay.png"); //$NON-NLS-1$

		prop1.setErrorColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setErrorColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setErrorForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setErrorForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setErrorImageURL("error.png"); //$NON-NLS-1$
		prop2.setErrorImageURL("error.png"); //$NON-NLS-1$

		prop1.setErrorOverlayURL("error-overlay.png"); //$NON-NLS-1$
		prop2.setErrorOverlayURL("error-overlay.png"); //$NON-NLS-1$

		prop1.setInfoColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setInfoColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setInfoForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setInfoForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setInfoImageURL("info.png"); //$NON-NLS-1$
		prop2.setInfoImageURL("info.png"); //$NON-NLS-1$

		prop1.setInfoOverlayURL("info-overlay.png"); //$NON-NLS-1$
		prop2.setInfoOverlayURL("info-overlay.png"); //$NON-NLS-1$

		prop1.setOkColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setOkColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setOkForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setOkForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setOkImageURL("ok.png"); //$NON-NLS-1$
		prop2.setOkImageURL("ok.png"); //$NON-NLS-1$

		prop1.setOkOverlayURL("ok-overlay.png"); //$NON-NLS-1$
		prop2.setOkOverlayURL("ok-overlay.png"); //$NON-NLS-1$

		prop1.setWarningColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setWarningColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setWarningForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$
		prop2.setWarningForegroundColorHEX("#aaaaaa"); //$NON-NLS-1$

		prop1.setWarningImageURL("warning.png"); //$NON-NLS-1$
		prop2.setWarningImageURL("warning.png"); //$NON-NLS-1$

		prop1.setWarningOverlayURL("warning-overlay.png"); //$NON-NLS-1$
		prop2.setWarningOverlayURL("warning-overlay.png"); //$NON-NLS-1$
	}

}
