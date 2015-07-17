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
 * Eugen Neufeld - javaDoc
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.spi.core.util;

import java.util.Collection;

/**
 * This class defines a List.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.1
 */
public interface InternalChildrenList {

	/**
	 * The size of the list.
	 *
	 * @return number of elements in the list
	 */
	int size();

	/**
	 * Whether this list has children.
	 *
	 * @return true if this list has children, false otherwise.
	 */
	boolean hasChildren();

	/**
	 * Returns the children of this list.
	 *
	 * @return an array containing all children
	 */

	Object[] getChildren();

	/**
	 * Returns the object with this index from the list. Throws an IndexOutOfBoundException if index is invalid.
	 *
	 * @param index the index of the element to get
	 * @return the element
	 */

	Object getChild(int index);

	/**
	 * Return the parent element of this list.
	 *
	 * @return the parent of the list
	 */
	Object getParent();

	// TODO describe what slow means
	/**
	 * Whether this list is slow or not.
	 *
	 * @return true if it is slow, false otherwise
	 */

	boolean isSlow();

	// TODO describe what complete means
	/**
	 * Whether this list is complete or not.
	 *
	 * @return true if it is complete, false otherwise
	 */
	boolean isComplete();

	void addChildWithoutRefresh(Object child);

	/**
	 * Adds a child to the list.
	 *
	 * @param child the child
	 */
	void addChild(Object child);

	<T> void addChildren(T... children);

	/**
	 * Adds a list of children to the list.
	 *
	 * @param children a collection of new children
	 */
	<T> void addChildren(Collection<T> children);

	void setComplete();
}
