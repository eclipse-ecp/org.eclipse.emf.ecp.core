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
 ******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 *
 * Observer that gets notified before a project is deleted.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPProjectPreDeleteObserver extends ECPObserver {

	/**
	 * Method that gets notified before a project is deleted.
	 *
	 * @param project the {@link ECPProject} to delete
	 */
	void projectDelete(ECPProject project);
}
