/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.ui.common.dnd;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

/**
 * @author Eugen Neufeld
 */
public class ModelExplorerDropAdapter extends ECPDropAdapter {

	/**
	 * Default constructor.
	 *
	 * @param viewer the viewer, the drop adapter is responsible for
	 */
	public ModelExplorerDropAdapter(Viewer viewer) {
		super(null, viewer);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragEnter(DropTargetEvent event) {
		if (domain == null) {
			return;
		}
		super.dragEnter(event);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragOver(DropTargetEvent event) {
		final Object target = extractDropTarget(event.item);
		if (target == null) {
			return;
		}
		source = getDragSource(event);

		Object sourceObject = null;

		if (source == null) {
			final ISelection selection = viewer.getSelection();
			if (selection instanceof IStructuredSelection) {
				sourceObject = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			}

		} else {
			sourceObject = source.iterator().next();
		}

		final EditingDomain sourceProjectDomain = getProjectDomain(sourceObject);
		final EditingDomain targetProjectDomain = getProjectDomain(target);
		EditingDomain newDomain = null;

		if (target instanceof ECPProject) {
			newDomain = ((ECPProject) target).getEditingDomain();
		} else {
			newDomain = AdapterFactoryEditingDomain.getEditingDomainFor(target);
		}

		if (domain == null || newDomain != null && sourceProjectDomain == targetProjectDomain) {
			domain = newDomain;
		}

		if (target instanceof ECPProject) {

			final ECPProject project = (ECPProject) target;
			if (sourceObject instanceof ECPProject) {
				event.detail = DND.DROP_COPY;
				event.feedback = DND.FEEDBACK_INSERT_AFTER | DND.FEEDBACK_INSERT_BEFORE | DND.FEEDBACK_SCROLL;
			}
			// TODO delegate to provider?
			else if (project.getContents().contains(sourceObject)) {
				if (originalOperation != DND.DROP_COPY) {
					event.detail = DND.DROP_NONE;
				} else {
					event.detail = DND.DROP_COPY;
				}
			} else {
				event.feedback = DND.FEEDBACK_SELECT | getAutoFeedback();
				if (sourceProjectDomain == targetProjectDomain) {
					event.detail = DND.DROP_MOVE;
				} else {
					event.detail = DND.DROP_COPY;
				}

			}
		} else {
			if (sourceProjectDomain != targetProjectDomain) {
				event.detail = DND.DROP_NONE;

			} else {
				super.dragOver(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void drop(final DropTargetEvent event) {
		final Object target = extractDropTarget(event.item);
		source = getDragSource(event);
		final Object sourceObject = source.iterator().next();

		if (target instanceof ECPProject) {
			final ECPProject project = (ECPProject) target;

			if (sourceObject instanceof EObject) {
				project.getEditingDomain().getCommandStack().execute(new ChangeCommand((EObject) sourceObject) {

					@Override
					protected void doExecute() {
						if (event.detail == DND.DROP_MOVE) {
							project.getContents().add(sourceObject);
						} else if (event.detail == DND.DROP_COPY) {
							project.getContents().add(EcoreUtil.copy((EObject) sourceObject));
						}
					}
				});

			} else if (sourceObject instanceof ECPProject) {
				final ECPProject oldProject = (ECPProject) sourceObject;
				ECPUtil.getECPProjectManager().createProject(oldProject, oldProject.getName() + "(Copy)");
			}
		} else if (event.detail != DND.DROP_NONE) {
			super.drop(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dropAccept(DropTargetEvent event) {
		final Object target = extractDropTarget(event.item);
		if (target instanceof ECPProject) {
			event.feedback = DND.FEEDBACK_SELECT | getAutoFeedback();
		} else {
			super.dropAccept(event);
		}
	}

}