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
package org.eclipse.emf.ecp.explorereditorbridge.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentTouchedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectsChangedObserver;
import org.eclipse.emf.ecp.edit.spi.ECPContextDisposedListener;
import org.eclipse.emf.ecp.editor.e3.ECPEditorContext;
import org.eclipse.emf.ecp.spi.core.InternalProject;

/**
 * An EditorContext depending on an {@link ECPProject}.
 *
 * @author Eugen Neufeld
 *
 */
public class EditorContext implements ECPEditorContext {

	/**
	 * @author Jonas
	 *
	 */
	private final class IECPProjectsChangedUIObserverImplementation implements ECPProjectsChangedObserver,
		ECPProjectOpenClosedObserver, ECPProjectContentTouchedObserver {
		/** {@inheritDoc} */
		@Override
		public void projectsChanged(Collection<ECPProject> oldProjects, Collection<ECPProject> newProjects) {
			// TODO Auto-generated method stub
			if (!newProjects.contains(ecpProject)) {
				for (final ECPContextDisposedListener contextListener : contextListeners) {
					contextListener.contextDisposed();
				}
				dispose();
			}
		}

		/** {@inheritDoc} */
		@Override
		public void projectChanged(ECPProject project, boolean opened) {
			if (!opened) {
				for (final ECPContextDisposedListener contextListener : contextListeners) {
					contextListener.contextDisposed();
				}
				dispose();
			}
		}

		/** {@inheritDoc} */
		@Override
		public void contentTouched(ECPProject project, Collection<Object> objects, boolean structural) {
			// if we have a structural change (otherwise nothing should be closed), and the change is in our project
			// and our model element is no longer contained
			// then we notify about deletion and dispose ourself
			if (structural && ecpProject.equals(project) && !((InternalProject) project).contains(getDomainObject())) {
				for (final ECPContextDisposedListener contextListener : contextListeners) {
					contextListener.contextDisposed();
				}
				dispose();
			}
		}
	}

	private final List<ECPContextDisposedListener> contextListeners = new ArrayList<ECPContextDisposedListener>();

	private final ECPProjectsChangedObserver projectObserver;

	private final ECPProject ecpProject;

	private final EObject modelElement;

	/**
	 * Default constructor.
	 *
	 * @param modelElement the model element to be displayed in the editor
	 * @param ecpProject the {@link ECPProject} containing the model element
	 */
	public EditorContext(EObject modelElement, ECPProject ecpProject) {
		this.modelElement = modelElement;
		this.ecpProject = ecpProject;
		projectObserver = new IECPProjectsChangedUIObserverImplementation();
		ECPUtil.getECPObserverBus().register(projectObserver);
	}

	/** {@inheritDoc} */
	@Override
	public void addECPContextDisposeListener(ECPContextDisposedListener modelElementContextListener) {
		contextListeners.add(modelElementContextListener);
	}

	/**
	 * Dispose the context.
	 */
	@Override
	public void dispose() {
		ECPUtil.getECPObserverBus().unregister(projectObserver);
		contextListeners.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.editor.e3.ECPEditorContext#getDomainObject()
	 */
	@Override
	public EObject getDomainObject() {
		return modelElement;
	}

}
