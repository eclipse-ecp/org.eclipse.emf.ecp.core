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
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

/**
 * Delegates to {@link CopyAction}.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public class CopyMasterDetailAction extends DelegatingMasterDetailAction {

	private static final String ICON_PATH = "icons/copy.gif"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} for the {@link CopyAction}.
	 */
	public CopyMasterDetailAction(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	protected String getEMFImagePath() {
		return ICON_PATH;
	}

	@Override
	protected CommandActionHandler createDelegatedAction(EditingDomain editingDomain) {
		return new CopyAction(editingDomain);
	}

	@Override
	protected boolean isExecuteOnKeyRelease(KeyEvent event) {
		return isActivated(event, SWT.CTRL, 'c');
	}
}
