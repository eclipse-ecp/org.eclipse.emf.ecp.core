/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.common.spi.cachetree;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A node within an {@link AbstractCachedTree}.
 *
 * @param <T> the type of the value stored by this node
 *
 * @author emueller
 * @author Tobias Verhoeven
 * @since 1.5
 */
public abstract class CachedTreeNode<T> {

	private T ownValue;
	private T cachedChildrenValue;
	private final Map<Object, T> cache;
	private Object parent;

	/**
	 * Constructor.
	 *
	 * @param initialValue the initial value
	 */
	public CachedTreeNode(T initialValue) {
		this.ownValue = initialValue;
		cache = new LinkedHashMap<Object, T>();
	}

	/**
	 * Recomputes the cached value of this node.
	 */
	protected abstract void update();

	/**
	 * Puts a value into the cache and updates its value.
	 *
	 * @param key
	 *            the (child) object that contains the given value
	 * @param value
	 *            an additional value that will be considered for the computation of the
	 *            actual value that results to a {@link #update()} call
	 */
	// TODO rename method
	public void putIntoCache(Object key, T value) {
		cache.put(key, value);
		update();
	}

	/**
	 * Removes a (child) object from the cache and updates its value.
	 *
	 * @param key
	 *            the object to be removed
	 */
	// TODO rename method
	public void removeFromCache(Object key) {
		cache.remove(key);
		update();
	}

	/**
	 * Returns the value of this node.
	 *
	 * @return the value stored within this node
	 */
	public T getOwnValue() {
		return ownValue;
	}

	/**
	 * Sets the value of this node.
	 *
	 * @param newValue
	 *            the new value to be associated with this node
	 */
	public void setOwnValue(T newValue) {
		this.ownValue = newValue;
	}

	/**
	 * Returns the cached values that are stored in the children nodes.
	 *
	 * @return a set of values stored in the children nodes of this node
	 */
	public Collection<T> values() {
		return cache.values();
	}

	/**
	 * Returns the most severe cached value of all children.
	 *
	 * @return the childValue
	 */
	public T getChildValue() {
		return cachedChildrenValue;
	}

	/**
	 * Sets the the most severe cached value of all children.
	 *
	 * @param childValue the childValue to set
	 */
	protected void setChildValue(T childValue) {
		this.cachedChildrenValue = childValue;
	}

	/**
	 * Returns the value that this node should represent.
	 * This value is also passed to parents in case of changes to the tree.
	 *
	 * @return the display value
	 */
	public abstract T getDisplayValue();

	/**
	 * @return the parent object, this is not the parent tree node.
	 */
	public Object getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set, this is not the parent tree node.
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}

	/**
	 * Returns the internal representation of the child value cache.
	 *
	 * @return the cache
	 * @since 1.9
	 */
	protected final Map<Object, T> getCache() {
		return cache;
	}
}
