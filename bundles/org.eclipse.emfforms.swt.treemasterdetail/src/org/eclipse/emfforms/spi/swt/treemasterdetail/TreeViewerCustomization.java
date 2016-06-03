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

/**
 * The TreeViewerCustomization is used to influence the way a {@link org.eclipse.jface.viewers.TreeViewer TreeViewer} is
 * rendered and how it behaves.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public interface TreeViewerCustomization extends TreeViewerBuilder, ContentProviderProvider, DNDProvider,
	InitialSelectionProvider, LabelProviderProvider, ViewerFilterProvider, MenuProvider, LabelDecoratorProvider {
}
