/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Johannes Faltermeier - moved to dedicated class
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateChildAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.osgi.framework.FrameworkUtil;

/**
 * {@link IMenuListener Menu listener} added on the tree master detail.
 *
 * @author Clemens Elflein
 *
 */
public class TreeMasterDetailMenuListener implements IMenuListener {

	private final Collection<MasterDetailAction> rightClickActions;
	private final ChildrenDescriptorCollector childrenDescriptorCollector;
	private final MenuManager menuMgr;
	private final TreeViewer treeViewer;
	private final EditingDomain editingDomain;
	private final CreateElementCallback createElementCallback;
	private final DeleteActionBuilder deleteActionBuilder;

	/**
	 * Default constructor.
	 *
	 * @param childrenDescriptorCollector the child description collector
	 * @param menuMgr the menu manager
	 * @param treeViewer the treeviewer
	 * @param editingDomain the editing domain
	 * @param rightClickActions the right click actions to show
	 * @param createElementCallback the create element callback
	 * @param deleteActionBuilder the delete action builder
	 * @since 1.8
	 */
	public TreeMasterDetailMenuListener(
		ChildrenDescriptorCollector childrenDescriptorCollector,
		MenuManager menuMgr,
		TreeViewer treeViewer,
		EditingDomain editingDomain,
		Collection<MasterDetailAction> rightClickActions,
		CreateElementCallback createElementCallback,
		DeleteActionBuilder deleteActionBuilder) {

		this.childrenDescriptorCollector = childrenDescriptorCollector;
		this.menuMgr = menuMgr;
		this.treeViewer = treeViewer;
		this.editingDomain = editingDomain;
		this.rightClickActions = rightClickActions;
		this.createElementCallback = createElementCallback;
		this.deleteActionBuilder = deleteActionBuilder;
		initializeRightClickActions();
	}

	/**
	 * The rightClickActions need to be initialized in order for the key bindings to work, even if the context menu was
	 * not yet shown.
	 */
	private void initializeRightClickActions() {
		if (rightClickActions == null) {
			return;
		}
		for (final MasterDetailAction action : rightClickActions) {
			action.setTreeViewer(treeViewer);
		}
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {
		if (treeViewer.getSelection().isEmpty()) {
			return;
		}
		if (treeViewer.getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

			if (selection.size() == 1 && selection.getFirstElement() instanceof EObject) {
				final EObject eObject = (EObject) selection.getFirstElement();
				final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
				if (domain == null) {
					return;
				}
				final Collection<?> descriptors = childrenDescriptorCollector.getDescriptors(eObject);
				fillContextMenu(manager, descriptors, editingDomain, eObject);
			}
			manager.add(new Separator());
			addDeleteActionToContextMenu(editingDomain, menuMgr, selection);

			if (rightClickActions != null) {
				// JFACE API
				@SuppressWarnings("unchecked")
				final List<Object> list = new ArrayList<Object>(selection.toList());

				for (final MasterDetailAction menuAction : rightClickActions) {
					if (menuAction.shouldShow(list)) {
						menuAction.setTreeViewer(treeViewer);
						final Action newAction = new Action() {
							@Override
							public void run() {
								super.run();
								menuAction.execute(list);
							}
						};

						newAction.setImageDescriptor(ImageDescriptor.createFromURL(FrameworkUtil.getBundle(
							menuAction.getClass())
							.getResource(menuAction.getImagePath())));
						newAction.setText(menuAction.getLabel());

						manager.add(newAction);
					}
				}

			}
		}
	}

	/**
	 * Fill context menu.
	 *
	 * @param manager The menu manager responsible for the context menu
	 * @param descriptors The menu items to be added
	 * @param domain The editing domain of the current EObject
	 * @param eObject The model element
	 */
	private void fillContextMenu(IMenuManager manager, Collection<?> descriptors, final EditingDomain domain,
		final EObject eObject) {
		for (final Object descriptor : descriptors) {

			if (!CommandParameter.class.isInstance(descriptor)) {
				continue;
			}
			final CommandParameter cp = (CommandParameter) descriptor;
			if (cp.getEReference() == null) {
				continue;
			}
			if (filterDescriptor(cp)) {
				continue;
			}
			if (!cp.getEReference().isMany() && eObject.eIsSet(cp.getEStructuralFeature())) {
				continue;
			} else if (cp.getEReference().isMany() && cp.getEReference().getUpperBound() != -1
				&& cp.getEReference().getUpperBound() <= ((List<?>) eObject.eGet(cp.getEReference())).size()) {
				continue;
			}

			manager.add(createCreateChildAction(domain, eObject, cp));
		}

	}

	/**
	 * Creates a new child creation action based on the given {@link CommandParameter}. Subclasses might override this
	 * to customize the action creation (e.g. an action's label).
	 *
	 * @param domain The {@link EditingDomain} the element is be created with
	 * @param eObject The parent object the element is added to
	 * @param cp The {@link CommandParameter} creating the element when executed
	 * @return The action creating a new child when ran
	 * @since 1.20
	 */
	protected IAction createCreateChildAction(final EditingDomain domain, final EObject eObject,
		final CommandParameter cp) {
		return new CreateChildAction(eObject, domain, treeViewer, cp, createElementCallback);
	}

	/**
	 * Allows to prevent adding a create child action for the given {@link CommandParameter}.
	 *
	 * @param cp the descriptor
	 * @return <code>true</code> if action should be filtered (=not created), <code>false</code> otherwise
	 * @since 1.8
	 */
	protected boolean filterDescriptor(CommandParameter cp) {
		return false;
	}

	/**
	 * Adds the delete action to context menu.
	 *
	 * @param editingDomain the editing domain
	 * @param manager the manager
	 * @param selection the selection
	 */
	private void addDeleteActionToContextMenu(final EditingDomain editingDomain, final IMenuManager manager,
		final IStructuredSelection selection) {
		final Action deleteAction = deleteActionBuilder.createDeleteAction(selection, editingDomain);
		manager.add(deleteAction);
	}
}