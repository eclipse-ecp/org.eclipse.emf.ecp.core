/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.rap;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		final String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		layout.setFixed(true);

		layout.addView("org.eclipse.emf.ecp.ui.ModelExplorerView", IPageLayout.LEFT, 0.3f, //$NON-NLS-1$
			editorArea);
		layout.addView("org.eclipse.emf.ecp.ui.ModelRepositoriesView", IPageLayout.BOTTOM, 0.7f, //$NON-NLS-1$
			editorArea);
	}
}
