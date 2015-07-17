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
package org.eclipse.emf.ecp.application3x;

import org.eclipse.emf.ecp.ui.views.ModelExplorerView;
import org.eclipse.emf.ecp.ui.views.ModelRepositoriesView;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * The Perspective definition.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class Perspective implements IPerspectiveFactory {

	/** {@inheritDoc} */
	@Override
	public void createInitialLayout(IPageLayout layout)
	{
		final String editorArea = layout.getEditorArea();
		layout.addView(ModelExplorerView.ID, IPageLayout.LEFT, 0.3f, editorArea);
		layout.addView(ModelRepositoriesView.ID, IPageLayout.BOTTOM, 0.75f, editorArea);
	}
}
