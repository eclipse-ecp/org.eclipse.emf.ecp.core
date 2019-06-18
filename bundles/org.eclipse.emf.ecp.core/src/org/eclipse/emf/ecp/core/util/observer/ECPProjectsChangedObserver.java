/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Edgar Mueller - change to ECPObserver
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core.util.observer;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 * This Observer is called to notify listeners about changes of projects.
 *
 * @author Eike Stepper
 * @author Edgar Mueller
 * @author Eugen Neufeld
 *
 */
public interface ECPProjectsChangedObserver extends ECPObserver {

	/**
	 * This is called when a project changes, so if it is either added or removed.
	 *
	 * @param oldProjects the collection of projects before the change
	 * @param newProjects the collection of projects after the change
	 */
	void projectsChanged(Collection<ECPProject> oldProjects, Collection<ECPProject> newProjects);

}
