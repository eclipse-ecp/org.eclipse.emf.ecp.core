/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.jface.viewers.IContentProvider;

/**
 * Interface to influence the {@link IContentProvider content provider} which is added to the tree master detail.
 *
 * @author Johannes Faltermeier
 *
 */
public interface ContentProviderProvider extends Disposeable {

	/**
	 * Creates the content provider added to the treeviewer.
	 *
	 * @return the content provider
	 */
	IContentProvider getContentProvider();

}