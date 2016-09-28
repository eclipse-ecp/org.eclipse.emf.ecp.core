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
package org.eclipse.emf.ecp.ui.linkedView;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;

/**
 * A View, which can be linked to an editor.
 * 
 * @author jonas
 *
 */
public interface ILinkedWithEditorView {

	/**
	 * Called to tell the view, that a certain {@link IEditorPart} has been activated.
	 *
	 * @param activatedEditor The activated {@link IEditorPart}
	 */
	void editorActivated(IEditorPart activatedEditor);

	/**
	 * @deprecated Use ViewPart#getViewSite instead
	 * @return the {@link IViewSite} of the linked part
	 */
	@Deprecated
	IViewSite getViewSite();
}
