/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Edgar Mueller - change to ECPObserver
 * Eugen Neufeld - JavaDoc
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPRepository;

/**
 * This Observer is called to notify listeners about changes of repositories.
 *
 * @author Eike Stepper
 * @author Edgar Mueller
 * @author Eugen Neufeld
 *
 */
public interface ECPRepositoriesChangedObserver extends ECPObserver {

	/**
	 * This is called to indicate, that repositories changed.
	 *
	 * @param oldRepositories repositories before change
	 * @param newRepositories repositories after change
	 */
	void repositoriesChanged(Collection<ECPRepository> oldRepositories, Collection<ECPRepository> newRepositories);
}
