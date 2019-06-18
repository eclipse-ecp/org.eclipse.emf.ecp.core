/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.spi;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Jonas
 * @since 1.7
 *
 *        The Observer gets called before a delete operation, after all {@link VetoableDeleteObserver}s have allowed the
 *        delete operation.
 */
public interface PreDeleteObserver extends EMFObserver {

	/**
	 * Called before an EObject gets deleted.
	 *
	 * @param objectToBeDeleted The {@link EObject} to be deleted
	 */
	void preDelete(EObject objectToBeDeleted);

}
