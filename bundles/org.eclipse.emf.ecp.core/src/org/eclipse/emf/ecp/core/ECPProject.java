/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPRepositoryAware;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

/**
 * This class describes a Project. A project has a name, a label. It has zero or one {@link ECPRepository} and one
 * {@link ECPProvider}.
 * 
 * @author Eike Stepper
 * @author Eugen Neufeld
 * @author Jonas Helming
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */

public interface ECPProject extends ECPContainer, ECPRepositoryAware, IEditingDomainProvider {
	/**
	 * The type of the ECPElement.
	 */
	String TYPE = "Project"; //$NON-NLS-1$

	/**
	 * Returns the list of the direct content objects; each is of type Object.
	 * The contents may be directly modified.
	 * Adding an object will remove it from the previous container;
	 * 
	 * @return A list of {@link Object}
	 */
	EList<Object> getContents();

	/**
	 * Saves the currently pending changes of the model. This method delegates to the provider.
	 */
	void saveContents();

	/**
	 * Checks whether the model, associated with this project is dirty.
	 * 
	 * @return true if model is dirty, false otherwise
	 */
	boolean hasDirtyContents();

	/**
	 * Deletes a collection of {@link Object}s by delegating the task to the provider.
	 * 
	 * @param objects the collection of {@link Object}s to delete
	 */
	void deleteElements(Collection<Object> objects);

	/**
	 * Whether an object is open or not.
	 * 
	 * @return true if it is open, false otherwise
	 */
	boolean isOpen();

	/**
	 * This opens a closed object. If the object was already opened, nothing happens.
	 */
	void open();

	/**
	 * This closes an opened object. If the object was already closed, nothing happens.
	 */
	void close();

}
