/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentTouchedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectsChangedObserver;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;

import java.util.Collection;

/**
 * @author Eike Stepper
 */
public class ModelContentProvider extends ECPContentProvider<ECPProjectManager> implements ECPProjectsChangedObserver,
	ECPProjectOpenClosedObserver, ECPProjectContentTouchedObserver {
	public ModelContentProvider() {
	}

	/** {@inheritDoc} */
	public void projectsChanged(Collection<ECPProject> oldProjects, Collection<ECPProject> newProjects) {
		refreshViewer();
	}

	/** {@inheritDoc} */
	public void projectChanged(ECPProject project, boolean opened) {
		refreshViewer(true, project);
	}

	/** {@inheritDoc} */
	public void contentTouched(ECPProject project, Collection<Object> objects, boolean structural) {
		refreshViewer(structural, objects.toArray());
		if (!objects.contains(project)) {
			refreshViewer(false, project);
		}
	}

	@Override
	protected void connectInput(ECPProjectManager input) {
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	protected void disconnectInput(ECPProjectManager input) {
		ECPUtil.getECPObserverBus().unregister(this);
	}

	@Override
	protected void fillChildren(Object parent, InternalChildrenList childrenList) {
		if (parent == ECPUtil.getECPProjectManager()) {
			childrenList.addChildren(ECPUtil.getECPProjectManager().getProjects());
		} else {
			super.fillChildren(parent, childrenList);
		}
	}
}
