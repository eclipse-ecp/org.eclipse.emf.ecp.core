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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DeleteActionBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailMenuListener;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
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

	private ChildrenDescriptorCollector childrenDescriptorCollector;
	private Collection<MasterDetailAction> rightClickActions;
	private CreateElementCallback createElementCallback;
	private DeleteActionBuilder deleteActionBuilder;

	/**
	 * Default constructor.
	 */
	public DefaultMenuProvider() {
		childrenDescriptorCollector = new ChildrenDescriptorCollector();
		rightClickActions = Collections.<MasterDetailAction> emptySet();
		createElementCallback = new CreateElementCallback() {
			@Override
			public boolean beforeCreateElement(Object newElement) {
				return true;
			}

			@Override
			public void initElement(EObject parent, EReference reference, EObject newObject) {
				/* no op */
			}

			@Override
			public void afterCreateElement(Object newElement) {
				/* no op */
			}
		};
		deleteActionBuilder = new DefaultDeleteActionBuilder();
	}

	/**
	 * Constructor.
	 *
	 * @param childrenDescriptorCollector the child description collector to use
	 * @param rightClickActions the right click actions to use
	 * @param createElementCallback the create element callback
	 * @param deleteActionBuilder the delete action which will be added to the context menu
	 */
	public DefaultMenuProvider(ChildrenDescriptorCollector childrenDescriptorCollector,
		Collection<MasterDetailAction> rightClickActions,
		CreateElementCallback createElementCallback,
		DeleteActionBuilder deleteActionBuilder) {
		this.childrenDescriptorCollector = childrenDescriptorCollector;
		this.rightClickActions = rightClickActions;
		this.createElementCallback = createElementCallback;
		this.deleteActionBuilder = deleteActionBuilder;
	}

	@Override
	public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr
			.addMenuListener(new TreeMasterDetailMenuListener(childrenDescriptorCollector, menuMgr, treeViewer,
				editingDomain, rightClickActions, createElementCallback, deleteActionBuilder));
		final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		return menu;
	}

	/**
	 *
	 * @param deleteActionBuilder the {@link DeleteActionBuilder}
	 */
	public void setDeleteAction(DeleteActionBuilder deleteActionBuilder) {
		this.deleteActionBuilder = deleteActionBuilder;
	}

	/**
	 *
	 * @param createElementCallback the {@link CreateElementCallback}
	 */
	public void setCreateElementCallback(CreateElementCallback createElementCallback) {
		this.createElementCallback = createElementCallback;
	}

	/**
	 *
	 * @param rightClickActions the {@link MasterDetailAction actions}
	 */
	public void setRightClickAction(Collection<MasterDetailAction> rightClickActions) {
		this.rightClickActions = rightClickActions;
	}
}