/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;

/**
 * Delegates to {@link PasteInParentAction}.
 *
 * @author Jonas Helming
 * @since 1.13
 *
 */
public class PasteInParentMasterDetailAction extends PasteMasterDetailAction {

	/**
	 * Constructor.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} for the {@link PasteInParentAction}.
	 */
	public PasteInParentMasterDetailAction(EditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	protected CommandActionHandler createDelegatedAction(EditingDomain editingDomain) {
		return new PasteInParentAction(editingDomain);
	}

}
