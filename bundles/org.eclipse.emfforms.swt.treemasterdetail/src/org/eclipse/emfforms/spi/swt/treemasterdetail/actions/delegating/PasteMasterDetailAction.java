/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

/**
 * Delegates to {@link PasteAction}.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public class PasteMasterDetailAction extends DelegatingMasterDetailAction {

	private static final String ICON_PATH = "icons/paste.gif"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} for the {@link PasteAction}.
	 */
	public PasteMasterDetailAction(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	protected String getEMFImagePath() {
		return ICON_PATH;
	}

	@Override
	protected CommandActionHandler createDelegatedAction(EditingDomain editingDomain) {
		return new PasteAction(editingDomain);
	}

	@Override
	protected boolean isExecuteOnKeyRelease(KeyEvent event) {
		return isActivated(event, SWT.CTRL, 'v');
	}
}
