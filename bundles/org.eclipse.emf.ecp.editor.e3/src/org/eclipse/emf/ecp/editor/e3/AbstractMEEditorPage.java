/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.editor.e3;

import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

/**
 * An abstract class for the MEEditorPages.
 *
 * @author Eugen Neufeld
 */
public abstract class AbstractMEEditorPage {

	/**
	 * Create a page for the editor.
	 *
	 * @param editor the editor to add the page to
	 * @param modelElementContext the {@link ECPEditorContext}
	 * @return the {@link FormPage}
	 */
	public abstract FormPage createPage(FormEditor editor, ECPEditorContext modelElementContext);

}
