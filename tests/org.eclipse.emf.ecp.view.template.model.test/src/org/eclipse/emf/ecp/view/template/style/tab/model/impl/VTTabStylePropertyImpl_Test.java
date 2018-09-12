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
package org.eclipse.emf.ecp.view.template.style.tab.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tab.model.TabType;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabFactory;
import org.eclipse.emf.ecp.view.template.style.tab.model.VTTabStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTTabStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTTabStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTTabStyleProperty tabStyleProperty = VTTabFactory.eINSTANCE.createTabStyleProperty();
		final VTTabStyleProperty tabStyleProperty2 = VTTabFactory.eINSTANCE.createTabStyleProperty();

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		assertTrue(tabStyleProperty.equalStyles(tabStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTTabStyleProperty tabStyleProperty = VTTabFactory.eINSTANCE.createTabStyleProperty();
		final VTTabStyleProperty tabStyleProperty2 = VTTabFactory.eINSTANCE.createTabStyleProperty();

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		tabStyleProperty2.setCancelImageURL("different.png"); //$NON-NLS-1$
		assertFalse(tabStyleProperty.equalStyles(tabStyleProperty2));

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		tabStyleProperty2.setErrorImageURL("different.png"); //$NON-NLS-1$
		assertFalse(tabStyleProperty.equalStyles(tabStyleProperty2));

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		tabStyleProperty2.setOkImageURL("different.png"); //$NON-NLS-1$
		assertFalse(tabStyleProperty.equalStyles(tabStyleProperty2));

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		tabStyleProperty2.setType(TabType.TOP);
		assertFalse(tabStyleProperty.equalStyles(tabStyleProperty2));

		setEqualFields(tabStyleProperty, tabStyleProperty2);
		tabStyleProperty2.setWarningImageURL("different.png"); //$NON-NLS-1$
		assertFalse(tabStyleProperty.equalStyles(tabStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTabStyleProperty tabStyleProperty = VTTabFactory.eINSTANCE.createTabStyleProperty();

		assertFalse(tabStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTTabStyleProperty prop1, VTTabStyleProperty prop2) {
		prop1.setCancelImageURL("cancel.png"); //$NON-NLS-1$
		prop2.setCancelImageURL("cancel.png"); //$NON-NLS-1$

		prop1.setErrorImageURL("error.png"); //$NON-NLS-1$
		prop2.setErrorImageURL("error.png"); //$NON-NLS-1$

		prop1.setInfoImageURL("error.png"); //$NON-NLS-1$
		prop2.setInfoImageURL("error.png"); //$NON-NLS-1$

		prop1.setOkImageURL("ok.png"); //$NON-NLS-1$
		prop2.setOkImageURL("ok.png"); //$NON-NLS-1$

		prop1.setType(TabType.BOTTOM);
		prop2.setType(TabType.BOTTOM);

		prop1.setWarningImageURL("warning.png"); //$NON-NLS-1$
		prop2.setWarningImageURL("warning.png"); //$NON-NLS-1$
	}

}
