/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPElement;

/**
 * @author Eugen Neufeld
 *
 */
public final class InternalUtil {

	private InternalUtil() {
	}

	/**
	 * Returns the Set of names of a set of {@link ECPElement ECPElements}.
	 *
	 * @param elements the set of elements to get the names for
	 * @return the set of names of this elements
	 */
	public static Set<String> getElementNames(Set<? extends ECPElement> elements) {
		final Set<String> names = new HashSet<String>();
		for (final ECPElement element : elements) {
			names.add(element.getName());
		}

		return names;
	}

	/**
	 * Finds the set of all Elements that are in the new collection but not in the old.
	 *
	 * @param oldElements the collection containing the old elements
	 * @param newElements the collection containing the new elements
	 * @param <E> the type of the elements
	 * @return the Set<E> of elements which are only in the newElements collection
	 */
	public static <E> Set<E> getAddedElements(Collection<E> oldElements, Collection<E> newElements) {
		final Set<E> result = new HashSet<E>(newElements);
		result.removeAll(oldElements);
		return result;
	}

	/**
	 * Finds the set of all Elements that are in the old collection but not in the new.
	 *
	 * @param oldElements the collection containing the old elements
	 * @param newElements the collection containing the new elements
	 * @param <E> the type of the elements
	 * @return the Set<E> of elements which are only in the oldElements collection
	 */
	public static <E> Set<E> getRemovedElements(Collection<E> oldElements, Collection<E> newElements) {
		final Set<E> result = new HashSet<E>(oldElements);
		result.removeAll(newElements);
		return result;
	}
}
