/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.common.cachetree;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * A cached tree resembles a tree structure where each node is associated with a specific value.
 * The value stored by parent nodes within the tree hereby are influenced by the values stored within their children.
 * Thus, if the value of a child changes, it needs to be propagated upwards.
 * Analogously, if the child is deleted the value associated with the deleted child must not influence the value of
 * the parent anymore. This class is responsible for adding and removing nodes and for correctly maintaining such
 * parent/child relationships.
 * 
 * @param <T> the actual value type that is stored by the tree
 * 
 * @author emueller
 * @author Tobias Verhoeven
 */
public abstract class AbstractCachedTree<T> {

	private final Map<Object, CachedTreeNode<T>> nodes;
	private CachedTreeNode<T> rootValue;
	private final IExcludedObjectsCallback excludedCallback;

	/**
	 * Private constructor.
	 * 
	 * @param callback the {@link IExcludedObjectsCallback} to use when checking when to stop
	 */
	public AbstractCachedTree(IExcludedObjectsCallback callback) {
		nodes = new LinkedHashMap<Object, CachedTreeNode<T>>();
		rootValue = createdCachedTreeNode(getDefaultValue());
		this.excludedCallback = callback;
	}

	/**
	 * Gets the Nodes of the cached tree containing necessary information.
	 * 
	 * @return the nodes
	 */
	protected Map<Object, CachedTreeNode<T>> getNodes() {
		return nodes;
	}

	/**
	 * Returns the default value for a cached node.<br/>
	 * The root value will be initialized with this value, too
	 * 
	 * @return the default value for a cached tree node
	 */
	public abstract T getDefaultValue();

	/**
	 * Creates a cached tree node.
	 * 
	 * @param value
	 *            the value stored by the cached tree node
	 * @return the created node
	 */
	protected abstract CachedTreeNode<T> createdCachedTreeNode(T value);

	/**
	 * Updates the cached entry for the given {@link EObject} with the given value.<br/>
	 * If the cached entry does not yet exist, it will be created.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @param value
	 *            the value associated with the {@link EObject}
	 * @return set of affected eobjects
	 */
	public Set<EObject> update(EObject eObject, T value) {

		if (excludedCallback.isExcluded(eObject)) {
			return Collections.emptySet();
		}

		final CachedTreeNode<T> node = createNodeIfNecessary(eObject, value);
		node.setOwnValue(value);

		updateNodeObject(eObject);

		rootValue.putIntoCache(eObject, node.getOwnValue());

		final Set<EObject> affectedElements = removeOutdatedParentCacheIfNeeded(eObject);
		// propagate upwards
		EObject parent = eObject.eContainer();

		while (parent != null && !excludedCallback.isExcluded(parent)) {// !isExcludedType(excludedTypes,
																		// parent.getClass())
			updateParentNode(parent, eObject, nodes.get(eObject).getDisplayValue());
			eObject = parent;
			parent = parent.eContainer();
			affectedElements.add(eObject);
		}
		return affectedElements;
	}

	/**
	 * Helper method which gets called when an update on the specified object is performed. Implementors can use this
	 * method when they need to perform special behaviour one a node changes.
	 * 
	 * @param object The upject for which an update on the tree is performed
	 */
	protected void updateNodeObject(Object object) {
		// TODO better way?
	}

	// If an object has been moved the cached entries must be removed from old parents.
	private Set<EObject> removeOutdatedParentCacheIfNeeded(EObject eObject) {

		final Set<EObject> affectedElements = new HashSet<EObject>();
		CachedTreeNode<T> node = nodes.get(eObject);

		if (node.getParent() != null && node.getParent() != eObject.eContainer()) {
			EObject oldParent = (EObject) node.getParent();
			while (oldParent != null && !excludedCallback.isExcluded(oldParent)) {
				affectedElements.add(oldParent);
				node = nodes.get(oldParent);
				node.removeFromCache(eObject);
				oldParent = oldParent.eContainer();
				eObject = oldParent;
			}
		}
		return affectedElements;
	}

	/**
	 * Returns the root value of this tree.<br/>
	 * Note that the root is purely notional.
	 * 
	 * @return the node
	 */
	public T getRootValue() {
		return rootValue.getDisplayValue();
	}

	/**
	 * Returns the cached value stored for the given {@link EObject}.
	 * 
	 * @param eObject
	 *            the {@link EObject} whose cached value should be found
	 * 
	 * @return the cached value associated with the given {@link EObject}, if found, otherwise
	 *         the default value which is returned via {@link #getDefaultValue()}
	 */
	public T getCachedValue(Object eObject) {
		final CachedTreeNode<T> nodeEntry = nodes.get(eObject);

		if (nodeEntry != null) {
			return nodes.get(eObject).getDisplayValue();
		}

		return getDefaultValue();
	}

	/**
	 * Removes the cache entry that contains the given {@link EObject}.
	 * 
	 * @param eObject
	 *            the {@link EObject} that needs to be removed from the cached tree
	 */
	public void remove(EObject eObject) {

		CachedTreeNode<T> node = nodes.get(eObject);
		final CachedTreeNode<T> parentNode = nodes.get(node.getParent());

		nodes.remove(eObject);
		rootValue.removeFromCache(eObject);

		if (parentNode == null) {
			return;
		}

		// propagate removed value upwards
		parentNode.removeFromCache(eObject);
		EObject parent = eObject.eContainer();

		while (parent != null && !excludedCallback.isExcluded(parent)) {

			node = nodes.get(parent);
			node.removeFromCache(eObject);

			parent = parent.eContainer();
			eObject = parent;
		}
	}

	private CachedTreeNode<T> createNodeIfNecessary(Object object, T t) {
		CachedTreeNode<T> node = nodes.get(object);

		if (node == null) {
			node = createNodeEntry(object, t);
		}

		return node;
	}

	private CachedTreeNode<T> createNodeEntry(Object object, T t) {
		final CachedTreeNode<T> node = createdCachedTreeNode(t);
		nodes.put(object, node);
		return node;
	}

	/**
	 * Updates the passed parent nodes cached value.
	 * 
	 * @param parent the parent object to be updated
	 * @param object the object for which the cached value should be changed.
	 * @param value the the cached value for the object
	 */
	protected void updateParentNode(Object parent, Object object, T value) {
		final CachedTreeNode<T> node = nodes.get(object);
		node.setParent(parent);

		final CachedTreeNode<T> parentNode = createNodeIfNecessary(parent, getDefaultValue());
		parentNode.putIntoCache(object, value);
		updateNodeObject(parent);
		rootValue.putIntoCache(parent, parentNode.getDisplayValue());
	}

	/**
	 * Clears the cache.
	 */
	public void clear() {
		nodes.clear();
		rootValue = createdCachedTreeNode(getDefaultValue());
	}
}
