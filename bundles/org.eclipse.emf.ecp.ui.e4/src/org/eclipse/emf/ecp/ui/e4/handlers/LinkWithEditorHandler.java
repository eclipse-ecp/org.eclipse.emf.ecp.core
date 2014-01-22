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
package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.Map;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.emf.ecp.ui.e4.view.ECPModelView;

/**
 * A Handler for linking the navigator with the part service.
 * 
 * @author Eugen Neufeld
 * 
 */
public class LinkWithEditorHandler {

	/**
	 * Execute Method.
	 * 
	 * @param part the {@link MPart}
	 * @param toolBarElement the selected {@link org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement}
	 */
	@Execute
	public void execute(MPart part, MToolItem toolBarElement) {
		final Map<String, String> state = part.getPersistedState();
		state.put(ECPModelView.P_LINK_WITH_EDITOR, String.valueOf(toolBarElement.isSelected()));
	}

}
