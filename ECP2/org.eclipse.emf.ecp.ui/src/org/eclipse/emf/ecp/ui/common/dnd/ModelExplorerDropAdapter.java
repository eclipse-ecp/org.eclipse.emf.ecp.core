/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH.
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
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

/**
 * @author Eugen Neufeld
 */
public class ModelExplorerDropAdapter extends EditingDomainViewerDropAdapter {

	private ECPModelContextProvider contextProvider;

	/*
	 * @param viewer
	 */
	public ModelExplorerDropAdapter(ECPModelContextProvider contextProvider, Viewer viewer) {
		super(null, viewer);
		this.contextProvider = contextProvider;
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

	/**
	 * @param object
	 * @return
	 */
	private EditingDomain getProjectDomain(Object object) {
		ECPProject project = (ECPProject) ECPUtil.getModelContext(contextProvider, object);
		if (project != null) {
			return project.getEditingDomain();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragOver(DropTargetEvent event) {
		Object target = extractDropTarget(event.item);
		if (target == null) {
			return;
		}
		source = getDragSource(event);
		Object sourceObject = source.iterator().next();

		EditingDomain sourceProjectDomain = getProjectDomain(sourceObject);
		EditingDomain targetProjectDomain = getProjectDomain(target);
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

			ECPProject project = (ECPProject) target;
			if (sourceObject instanceof ECPProject) {
				event.detail = DND.DROP_COPY;
				event.feedback = DND.FEEDBACK_INSERT_AFTER | DND.FEEDBACK_INSERT_BEFORE | DND.FEEDBACK_SCROLL;
			}
			// TODO delegate to provider?
			else if (project.getElements().contains(sourceObject)) {
				event.detail = DND.DROP_NONE;
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
	public void drop(DropTargetEvent event) {
		Object target = extractDropTarget(event.item);
		source = getDragSource(event);
		Object sourceObject = source.iterator().next();

		if (target instanceof ECPProject) {
			ECPProject project = (ECPProject) target;

			if (sourceObject instanceof EObject) {
				if (event.detail == DND.DROP_MOVE) {
					project.addModelElement((EObject) sourceObject);
				} else if (event.detail == DND.DROP_COPY) {
					project.addModelElement(EcoreUtil.copy((EObject) sourceObject));
				}
			} else if (sourceObject instanceof ECPProject) {

				// TODO copy of project by projectmanager
				ECPProject oldProject = (ECPProject) sourceObject;
				ECPProjectManager.INSTANCE.cloneProject(oldProject);
				//
				// ECPProvider offlineProvider = null;
				// for (ECPProvider provider : ECPProviderRegistry.INSTANCE.getProviders()) {
				// if (provider.hasUnsharedProjectSupport()) {
				// offlineProvider = provider;
				// break;
				// }
				// }
				// if (offlineProvider == null) {
				// return;
				// }
				//
				// ECPProject newProject = ECPProjectManager.INSTANCE.createProject(offlineProvider,
				// oldProject.getName()
				// + " (Copy)", ECPUtil.createProperties());
				//
				// newProject.setFilteredEClasses(oldProject.getFilteredEClasses());
				// newProject.setFilteredPackages(oldProject.getFilteredPackages());
				//
				// for (EObject eObject : oldProject.getElements()) {
				// newProject.addModelElement(EcoreUtil.copy(eObject));
				// }
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
		Object target = extractDropTarget(event.item);
		if (target instanceof ECPProject) {
			event.feedback = DND.FEEDBACK_SELECT | getAutoFeedback();
		} else {
			super.dropAccept(event);
		}
	}

	public void setEditingDomain(EditingDomain editingDomain) {
		domain = editingDomain;
	}

}