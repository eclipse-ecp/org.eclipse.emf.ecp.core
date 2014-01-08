/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.ui.e4.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author krusche
 * 
 */
@SuppressWarnings("restriction")
public class DynamicCreateChildrenElementsMenuContribution {
	@Inject
	private ESelectionService selectionService;

	public List<?> getSelectedObjects() {
		final Object sel = selectionService.getSelection();
		if (sel != null) {
			if (sel instanceof Collection) {
				final Collection<?> col = (Collection<?>) sel;
				return new ArrayList<Object>(col);
			} else if (sel instanceof Object[]) {
				return Arrays.asList((Object[]) sel);
			} else if (sel instanceof IStructuredSelection) {
				final IStructuredSelection ssel = (IStructuredSelection) sel;
				return ssel.toList();
			} else {
				return Arrays.asList(sel);
			}
		}

		return null;
	}

	@AboutToShow
	public void aboutToShow(List<MMenuElement> items) {
		final List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return;
		}
		final Object[] elements = selectedObjects.toArray();

		if (elements.length == 1 && elements[0] instanceof EObject) {
			final EObject eObject = (EObject) elements[0];
			final ECPProject project = ECPUtil.getECPProjectManager()
				.getProject(eObject);
			final EditingDomain domain = project.getEditingDomain();
			final Collection<?> childDescriptors = new ChildrenDescriptorCollector()
				.getDescriptors(eObject);

			for (final Object childDescriptor : childDescriptors) {
				final CommandParameter cp = (CommandParameter) childDescriptor;
				if (cp.getEReference() == null) {
					continue;
				}
				if (!cp.getEReference().isMany()
					&& eObject.eIsSet(cp.getEStructuralFeature())) {
					continue;
				} else if (cp.getEReference().isMany()
					&& cp.getEReference().getUpperBound() != -1
					&& cp.getEReference().getUpperBound() <= ((List<?>) eObject
						.eGet(cp.getEReference())).size()) {
					continue;
				}

				final CustomCreateChildAction createChildAction = new CustomCreateChildAction(
					domain, new StructuredSelection(eObject),
					childDescriptor, cp, project);

				final MDirectMenuItem dynamicItem = MMenuFactory.INSTANCE
					.createDirectMenuItem();

				dynamicItem.setLabel(createChildAction.getText());
				dynamicItem.setTooltip(createChildAction.getToolTipText());
				dynamicItem.setObject(new Object() {
					@Execute
					public void execute(MDirectMenuItem me) {
						createChildAction.run();
					}
				});

				dynamicItem.setIconURI(createChildAction.getImageURIString());
				items.add(dynamicItem);
			}
		}
	}

	class CustomCreateChildAction extends CreateChildAction {

		public CustomCreateChildAction(EditingDomain editingDomain,
			ISelection selection, Object descriptor, CommandParameter cp,
			ECPProject project) {
			super(editingDomain, selection, descriptor);
			this.cp = cp;
			this.project = project;
		}

		private final CommandParameter cp;

		private final ECPProject project;

		@Override
		public void run() {
			super.run();
			ECPHandlerHelper.openModelElement(cp.getEValue(), project);
		}

		public String getImageURIString() {
			final CommandActionDelegate commandActionDelegate = (CommandActionDelegate) command;

			return commandActionDelegate.getImage().toString();
		}

	}
}
