/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable.DisposeListener;
import org.eclipse.net4j.util.lifecycle.Lifecycle;

/**
 * @author Eike Stepper
 * @param <ELEMENT>
 * @param <OBSERVER>
 */
public abstract class Registry<ELEMENT, OBSERVER extends ECPObserver> extends Lifecycle implements DisposeListener {
	private static final ThreadLocal<Boolean> DISPOSING_ELEMENT = new InheritableThreadLocal<Boolean>();

	private final Map<String, ELEMENT> elements = new HashMap<String, ELEMENT>();

	/**
	 * Default constructor.
	 */
	public Registry() {
	}

	/**
	 * Retrieves an element with a certain name from the registry.
	 *
	 * @param key the name of the element to retrieve
	 * @return the element or null if not contained in the registry
	 */
	public final ELEMENT getElement(String key) {
		checkActive();
		synchronized (this) {
			return elements.get(key);
		}
	}

	/**
	 * Returns the names of the elements managed by the registry.
	 *
	 * @return a set of strings representing the names of elements
	 */
	public final Set<String> getElementNames() {
		checkActive();
		synchronized (this) {
			// return elements.keySet().toArray(new String[elements.size()]);
			return Collections.unmodifiableSet(new HashSet<String>(elements.keySet()));
		}
	}

	/**
	 * Returns the number of elements managed by the registry.
	 *
	 * @return an integer
	 */
	public final int getElementCount() {
		checkActive();
		synchronized (this) {
			return elements.size();
		}
	}

	/**
	 * Returns the list of elements managed by the registry.
	 *
	 * @return a collection of elements
	 */
	public final Collection<ELEMENT> getElements() {
		checkActive();
		synchronized (this) {
			return Collections.unmodifiableCollection(new ArrayList<ELEMENT>(elements.values()));
			// ELEMENT[] result = createElementArray(elements.size());
			// return elements.values().toArray(result);
		}
	}

	/**
	 * Returns if the registry has elements.
	 *
	 * @return true if the registry has elements
	 */
	public final boolean hasElements() {
		checkActive();
		synchronized (this) {
			return !elements.isEmpty();
		}
	}

	/**
	 * Checks if an element with a certain name is managed by the registry.
	 *
	 * @param name the name of the element to check
	 * @return true if the element is managed by the registry
	 */
	public final boolean hasElement(String name) {
		checkActive();
		synchronized (this) {
			return elements.containsKey(name);
		}
	}

	/**
	 * Adds or remove elements contained in the registry.
	 *
	 * @param remove a set of elements to be removed
	 * @param add a set of elements to be added
	 */
	public final void changeElements(Set<String> remove, Set<ELEMENT> add) {
		checkActive();
		doChangeElements(remove, add);
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public final void disposed(ECPDisposable disposable) {
		if (isRemoveDisposedElements()) {
			final String name = getElementName((ELEMENT) disposable);
			if (name != null) {
				try {
					DISPOSING_ELEMENT.set(true);
					doChangeElements(Collections.singleton(name), null);
				} finally {
					DISPOSING_ELEMENT.remove();
				}
			}
		}
	}

	/**
	 *
	 * @return Whether elements should be removed from the registry if they are disposed.
	 */
	protected boolean isRemoveDisposedElements() {
		return true;
	}

	/**
	 * Executes adding or removing elements contained in the registry.
	 * As a caller, use changeElements().
	 *
	 * @param remove a set of elements to be removed
	 * @param add a set of elements to be added
	 * @return a set of elements, which have been removed
	 */
	protected final Set<ELEMENT> doChangeElements(Set<String> remove, Set<ELEMENT> add) {
		Set<ELEMENT> result = null;
		final Set<ELEMENT> oldElements = new HashSet<ELEMENT>();
		Set<ELEMENT> newElements = null;

		synchronized (this) {
			oldElements.addAll(elements.values());

			if (remove != null) {
				for (final String name : remove) {
					final ELEMENT element = elements.remove(name);
					if (element != null) {
						if (element instanceof ECPDisposable) {
							final ECPDisposable disposable = (ECPDisposable) element;
							disposable.removeDisposeListener(this);
						}

						if (result == null) {
							result = new HashSet<ELEMENT>();
						}

						result.add(element);
					}
				}
			}

			if (add != null) {
				for (final ELEMENT element : add) {
					final String name = getElementName(element);
					elements.put(name, element);

					if (element instanceof ECPDisposable) {
						final ECPDisposable disposable = (ECPDisposable) element;
						disposable.addDisposeListener(this);
					}
				}
			}

			if (!oldElements.equals(elements)) {
				newElements = new HashSet<ELEMENT>(elements.values());
			}
		}

		if (newElements != null) {
			// ELEMENT[] oldArray = oldElements.toArray(createElementArray(oldElements.size()));
			// ELEMENT[] newArray = newElements.toArray(createElementArray(newElements.size()));
			final Collection<ELEMENT> unmodifiableOld = Collections.unmodifiableCollection(oldElements);
			final Collection<ELEMENT> unmodifiableNew = Collections.unmodifiableCollection(newElements);
			elementsChanged(unmodifiableOld, unmodifiableNew);

			try {
				notifyObservers(unmodifiableOld, unmodifiableNew);
			} catch (final Exception ex) {
				Activator.log(ex);
			}
		}

		return result;
	}

	/**
	 * Adds an {@link ECPObserver} to the {@link org.eclipse.emf.ecp.core.util.observer.ECPObserverBus}.
	 *
	 * @param observer the observer to be added
	 */
	public void addObserver(OBSERVER observer) {
		ECPUtil.getECPObserverBus().register(observer);
	}

	/**
	 * Removes an {@link ECPObserver} to the {@link org.eclipse.emf.ecp.core.util.observer.ECPObserverBus}.
	 *
	 * @param observer the observer to be removed
	 */
	public void removeObserver(OBSERVER observer) {
		ECPUtil.getECPObserverBus().unregister(observer);
	}

	/**
	 * Notifies observers that the elements in the registry have changed.
	 *
	 * @param oldArray The old collection of elements
	 * @param newArray The new collection of elements
	 * @throws Exception
	 */
	protected abstract void notifyObservers(Collection<ELEMENT> oldArray, Collection<ELEMENT> newArray)
		throws Exception;

	protected void elementsChanged(Collection<ELEMENT> oldElements, Collection<ELEMENT> newElements) {
		// Can be overridden in subclasses
	}

	/**
	 * @param element the element to retrieve a name for.
	 * @return the name of an element
	 */
	protected abstract String getElementName(ELEMENT element);

	protected static boolean isDisposingElement() {
		final Boolean disposingElement = DISPOSING_ELEMENT.get();
		return disposingElement != null && disposingElement;
	}
}
