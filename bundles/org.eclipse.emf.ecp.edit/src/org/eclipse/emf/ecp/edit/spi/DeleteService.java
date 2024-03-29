/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 552385
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * <p>
 * The DeleteService is used by renderers in order to delete objects from the containment tree.
 * </p>
 * <p>
 * <strong>Note</strong> that since the 1.23 release, it is recommended to implement the
 * {@link ConditionalDeleteService} interface to support conditional deletion, honouring the model's
 * edit providers when they veto deletion by providing unexecutable commands.
 * </p>
 *
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
