/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.spi;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Jonas
 * @since 1.7
 *        The Observer gets called before a delete operation and before all {@link PreDeleteObserver}. A
 *        {@link VetoableDeleteObserver} can decide whether a delete operation on a specific object is allowed ot not,
 *        e.g. by asking the user. It is not allowed to change anything.
 *        delete operation.
 */
public interface VetoableDeleteObserver extends EMFObserver {

	/**
	 * @param objectToBeDeleted The {@link EObject} to be deleted
	 * @return if the object can be deleted
	 */
	boolean canDelete(EObject objectToBeDeleted);

}
