/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

/**
 * Delegates to {@link CutAction}.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public class CutMasterDetailAction extends DelegatingMasterDetailAction {

	private static final String ICON_PATH = "icons/cut.gif"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} for the {@link CutAction}.
	 */
	public CutMasterDetailAction(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	protected String getEMFImagePath() {
		return ICON_PATH;
	}

	@Override
	protected CommandActionHandler createDelegatedAction(EditingDomain editingDomain) {
		return new CutAction(editingDomain);
	}

	@Override
	protected boolean isExecuteOnKeyRelease(KeyEvent event) {
		return isActivated(event, SWT.CTRL, 'x');
	}

}
