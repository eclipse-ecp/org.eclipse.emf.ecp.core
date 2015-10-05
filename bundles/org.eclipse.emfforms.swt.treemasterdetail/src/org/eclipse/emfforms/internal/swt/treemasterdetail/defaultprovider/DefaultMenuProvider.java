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
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import java.util.Collections;

import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailMenuListener;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.MasterDetailAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

/**
 * The default menu provider.
 * 
 * @author Johannes Faltermeier
 *
 */
public final class DefaultMenuProvider implements MenuProvider {

	@Override
	public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
		final ChildrenDescriptorCollector childrenDescriptorCollector = new ChildrenDescriptorCollector();
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr
			.addMenuListener(new TreeMasterDetailMenuListener(childrenDescriptorCollector, menuMgr, treeViewer,
				editingDomain, Collections.<MasterDetailAction> emptySet(), new CreateElementCallback() {
					@Override
					public boolean beforeCreateElement(Object newElement) {
						return true;
					}
				}));
		final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		return menu;
	}
}