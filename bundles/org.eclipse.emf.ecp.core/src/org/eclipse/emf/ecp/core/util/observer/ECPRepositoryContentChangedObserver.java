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
 * This Observer is called to notify listeners about changes of the objects in a repository.
 *
 * @author Eike Stepper
 * @author Edgar Mueller
 * @author Eugen Neufeld
 *
 */
public interface ECPRepositoryContentChangedObserver extends ECPObserver {

	/**
	 * This is called to indicate, that objects inside the repository changed.
	 *
	 * @param repository the repository the changes occurred
	 * @param objects the objects that changed
	 */
	void contentChanged(ECPRepository repository, Collection<Object> objects);
}
