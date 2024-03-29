/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.util.observer;

import org.eclipse.emf.ecp.core.ECPProject;

/**
 * This Observer is notified when a project is closed or opened.
 *
 * @author Eugen Neufeld
 *
 */
public interface ECPProjectOpenClosedObserver extends ECPObserver {
	/**
	 * This is called when a project is opened or closed.
	 *
	 * @param project the {@link ECPProject} that changed
	 * @param opened whether it was opened or closed
	 */
	void projectChanged(ECPProject project, boolean opened);
}
