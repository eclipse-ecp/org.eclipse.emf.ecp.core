/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.common.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author Eugen
 * @generated
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.common.ui.messages"; //$NON-NLS-1$

	public static String CheckedModelElementHelper_DeselectAllLabel;
	public static String CheckedModelElementHelper_SelectAllLabel;
	public static String AbstractModelElementHelper_FilterLabel;
	public static String AbstractModelElementHelper_FilterText;

	public static String NewModelElementWizard_WizardTitle_AddModelElement;
	public static String NewModelElementWizard_PageTitle_AddModelElement;
	public static String NewModelElementWizard_PageDescription_AddModelElement;

	public static String ModelelementSelectionDialog_DialogMessage_SearchPattern;
	public static String ModelelementSelectionDialog_DialogTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
