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
package org.eclipse.emfforms.internal.editor.ecore;

import java.util.Collection;

import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.editor.ecore.helpers.EcoreHelpers;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultDeleteActionBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailMenuListener;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * {@link TreeMasterDetailMenuListener} for the Ecore Editor. Filters generic features.
 *
 * @author Johannes Faltermeier
 *
 */
public class EcoreEditorMenuListener extends TreeMasterDetailMenuListener {

	/**
	 * Default constructor.
	 *
	 * @param childrenDescriptorCollector the child description collector
	 * @param menuMgr the menu manager
	 * @param treeViewer the treeviewer
	 * @param editingDomain the editing domain
	 * @param rightClickActions the right click actions to show
	 * @param createElementCallback the create element callback
	 */
	public EcoreEditorMenuListener(ChildrenDescriptorCollector childrenDescriptorCollector, MenuManager menuMgr,
		TreeViewer treeViewer, EditingDomain editingDomain, Collection<MasterDetailAction> rightClickActions,
		CreateElementCallback createElementCallback) {
		super(childrenDescriptorCollector, menuMgr, treeViewer, editingDomain, rightClickActions,
			createElementCallback, new DefaultDeleteActionBuilder());
	}

	@Override
	protected boolean filterDescriptor(CommandParameter cp) {
		return EcoreHelpers.isGenericFeature(cp.getFeature());
	}

}
