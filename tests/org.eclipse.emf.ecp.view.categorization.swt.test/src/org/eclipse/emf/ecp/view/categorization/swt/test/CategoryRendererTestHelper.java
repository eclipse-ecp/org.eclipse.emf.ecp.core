/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.categorization.swt.test;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

/**
 * @author Jonas Helming
 *
 */
public abstract class CategoryRendererTestHelper {
	/**
	 * @param swtControl
	 * @return the {@link Tree} rendered by the Categorization Renderer
	 */
	public static Tree getTree(Control swtControl) {
		final Composite composite = (Composite) swtControl;
		return (Tree) ((Composite) composite.getChildren()[0]).getChildren()[0];
	}

}
