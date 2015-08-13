/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Johannes Faltermeier - moved to dedicated class
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.swt.treemasterdetail.helpers.EcoreHelpers;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateChildAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.MasterDetailAction;
import org.eclipse.jface.action.Action;
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
	public TreeMasterDetailMenuListener(
		ChildrenDescriptorCollector childrenDescriptorCollector,
		MenuManager menuMgr,
		TreeViewer treeViewer,
		EditingDomain editingDomain,
		Collection<MasterDetailAction> rightClickActions,
		CreateElementCallback createElementCallback) {

		this.childrenDescriptorCollector = childrenDescriptorCollector;
		this.menuMgr = menuMgr;
		this.treeViewer = treeViewer;
		this.editingDomain = editingDomain;
		this.rightClickActions = rightClickActions;
		this.createElementCallback = createElementCallback;
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

			if (selection.getFirstElement() instanceof EObject && rightClickActions != null) {
				final EObject eSelectedObject = (EObject) selection.getFirstElement();

				for (final MasterDetailAction menuAction : rightClickActions) {
					if (menuAction.shouldShow(eSelectedObject)) {
						final Action newAction = new Action() {
							@Override
							public void run() {
								super.run();
								menuAction.execute(eSelectedObject);
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

			final CommandParameter cp = (CommandParameter) descriptor;
			if (!CommandParameter.class.isInstance(descriptor)) {
				continue;
			}
			if (cp.getEReference() == null) {
				continue;
			}
			if (EcoreHelpers.isGenericFeature(cp.getFeature())) {
				// This ensures, that we won't show any generic features anymore
				continue;
			}
			if (!cp.getEReference().isMany() && eObject.eIsSet(cp.getEStructuralFeature())) {
				continue;
			} else if (cp.getEReference().isMany() && cp.getEReference().getUpperBound() != -1
				&& cp.getEReference().getUpperBound() <= ((List<?>) eObject.eGet(cp.getEReference())).size()) {
				continue;
			}

			manager.add(new CreateChildAction(eObject, domain, treeViewer, cp, createElementCallback));
		}

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

		// Create the RemovEommand and check, if it can be executed.
		// If it can't, don't create a menu item
		final Command removeCommand = RemoveCommand.create(editingDomain, selection.toList());

		if (!removeCommand.canExecute()) {
			return;
		}

		final Action deleteAction = new Action() {
			@Override
			public void run() {
				super.run();
				editingDomain.getCommandStack().execute(removeCommand);
				// TODO
				// treeViewer.setSelection(new StructuredSelection(input));
			}
		};

		final String deleteImagePath = "icons/delete.png";//$NON-NLS-1$
		deleteAction.setImageDescriptor(ImageDescriptor
			.createFromURL(FrameworkUtil.getBundle(TreeMasterDetailComposite.class).getResource(deleteImagePath)));
		deleteAction.setText("Delete"); //$NON-NLS-1$
		manager.add(deleteAction);
	}
}