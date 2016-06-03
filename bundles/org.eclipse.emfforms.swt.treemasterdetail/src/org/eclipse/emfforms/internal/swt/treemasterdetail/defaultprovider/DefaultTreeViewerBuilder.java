/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerBuilder;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author jfaltermeier
 *
 */
public final class DefaultTreeViewerBuilder implements TreeViewerBuilder {
	@Override
	public TreeViewer createTree(Composite parent) {
		final TreeViewer treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.BORDER);
		treeViewer.setAutoExpandLevel(3);
		return treeViewer;
		// TODO JF compatible with RAP??
		// new ColumnViewerInformationControlToolTipSupport(treeViewer,
		// new DiagnosticDecorator.EditingDomainLocationListener(editingDomain, treeViewer));
	}
}