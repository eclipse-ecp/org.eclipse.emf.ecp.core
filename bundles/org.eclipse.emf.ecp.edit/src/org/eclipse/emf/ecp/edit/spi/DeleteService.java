/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * The DeleteService is used by renderers in order to delete objects from the containment tree.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public interface DeleteService extends ViewModelService {

	/**
	 * Deletes the given objects from the containment tree. This will cut all references to the deleted objects.
	 *
	 * @param toDelete the objects to delete
	 */
	void deleteElements(Collection<Object> toDelete);

	/**
	 * Deletes the given object from the containment tree. This will cut all references to the deleted object.
	 *
	 * @param toDelete the object to delete
	 */
	void deleteElement(Object toDelete);

}
