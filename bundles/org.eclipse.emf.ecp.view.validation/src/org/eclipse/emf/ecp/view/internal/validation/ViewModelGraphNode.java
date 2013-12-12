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
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * <p>
 * A node within an {@link ViewModelGraph}.
 * </p>
 * <p>
 * A node has a value and a source as well as an optional parent nodes. A source is an object that produces a value of
 * type {@code T}
 * </p>
 * 
 * @param <T> the type of the value stored by this node
 * 
 * @author emueller
 */
public class ViewModelGraphNode<T> {

	private T initValue;
	private Set<ViewModelGraphNode<T>> parents;
	private final UniqueSetting setting;
	private final PriorityQueue<ViewModelGraphNode<T>> children;
	private final boolean isDomainObject;
	private T value;

	/**
	 * Constructor.
	 * 
	 * @param setting
	 *            the {@link UniqueSetting} this node is referring to
	 * @param value
	 *            the initial value of the node
	 * @param isDomainObject
	 *            whether the {@link UniqueSetting#getEObject()} is a domain object
	 * @param comparator
	 *            the {@link Comparator} that is used to maintain the aggregated values
	 */
	public ViewModelGraphNode(UniqueSetting setting, T value, boolean isDomainObject,
		final Comparator<T> comparator) {
		this.setting = setting;
		this.value = value;
		this.initValue = value;
		this.isDomainObject = isDomainObject;
		this.parents = new LinkedHashSet<ViewModelGraphNode<T>>();
		this.children = new PriorityQueue<ViewModelGraphNode<T>>(10, new Comparator<ViewModelGraphNode<T>>() {
			public int compare(ViewModelGraphNode<T> node1, ViewModelGraphNode<T> node2) {
				final T node1Value = node1.getValue();
				final T node2Value = node2.getValue();
				// inverse result: head of priority queue is the least element
				return -comparator.compare(node1Value, node2Value);
			}
		});
	}

	/**
	 * Returns the aggregated value of this node, i.e. if the node
	 * has any children, the values of the children are considered.
	 * 
	 * @return the aggregated value for this node
	 */
	public T getValue() {

		if (isLocked()) {
			return initValue;
		}

		if (hasChildren()) {
			return children.element().getValue();
		}
		// TODO: why can we not return initValue?
		// because some caller may have called setValue
		// alternative: set initValue in setValue and return initValue here;
		// same as setting value to initValue if there are no more children
		return value;
	}

	/**
	 * Whether this node is locked. If a node is locked, this means that value of children
	 * will not be considered when calling {@link #getValue()}.
	 * 
	 * @return {@code true}, if this node is locked, {@code false otherwise}
	 */
	public boolean isLocked() {
		if (isRenderable()) {
			final VElement renderable = (VElement) getSetting().getEObject();

			if (!renderable.isEnabled() || !renderable.isVisible() || renderable.isReadonly()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Whether this node has any children.
	 * 
	 * @return {@code true}, if the node has children, {@code false} otherwise
	 */
	private boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * Adds a child node to this node.
	 * This node will be set as a parent of the child node.
	 * 
	 * @param childNode
	 *            the child node to be added
	 */
	public void addChild(ViewModelGraphNode<T> childNode) {
		childNode.addParent(this);
		addToChildrenQueue(childNode);
	}

	/**
	 * Removes the given child node.
	 * This node will be removed as a parent of the child node.
	 * 
	 * @param childNode
	 *            the child node to be removed
	 */
	public void removeChild(ViewModelGraphNode<T> childNode) {
		children.remove(childNode);
		childNode.getParents().remove(this);
		if (!hasChildren()) {
			value = initValue;
		}
	}

	/**
	 * Sets the value of this node and propagates it upwards.
	 * 
	 * @param value
	 *            the value to be set
	 */
	public void setValue(T value) {
		this.value = value;

		// TODO: add doc why we need to update all parents
		// (corner case: children of a parents node may all validate ok, even
		// if one child has validation errors)
		for (final ViewModelGraphNode<T> parent : getParents()) {
			parent.addToChildrenQueue(this);
			parent.setValue(parent.getValue());
		}
	}

	/**
	 * Returns all parent nodes.
	 * 
	 * @return the parent nodes
	 */
	public Set<ViewModelGraphNode<T>> getParents() {
		return parents;
	}

	/**
	 * Adds a parent node.
	 * 
	 * @param parent
	 *            the parent node
	 */
	private void addParent(ViewModelGraphNode<T> parent) {
		parents.add(parent);
	}

	private void addToChildrenQueue(ViewModelGraphNode<T> childNode) {
		// remove and add to keep queue sorted
		children.remove(childNode);
		children.add(childNode);
	}

	/**
	 * Whether the source object is a domain object.
	 * 
	 * @return {@code true}, if the object is a domain object, {@code false} otherwise
	 */
	// this is useful for remove
	public boolean isDomainObject() {
		return isDomainObject;
	}

	/**
	 * Whether the source object is a {@link VElement}.
	 * 
	 * @return {@code true}, if the object stored by this node is a {@link VElement}
	 */
	public boolean isRenderable() {
		return !isDomainObject && VElement.class.isInstance(getSetting().getEObject());
	}

	/**
	 * Whether the mapping contains the given node.
	 * 
	 * @param node
	 *            the node to be checked whether it is contained in the mapping
	 * @return {@code true}, if the node is contained, {@code false} otherwise
	 */
	public boolean containsChild(ViewModelGraphNode<T> node) {
		return children.contains(node);
	}

	/**
	 * Returns the {@link UniqueSetting} this node is referring to.
	 * 
	 * @return the {@link UniqueSetting} this node is referring to
	 */
	public UniqueSetting getSetting() {
		return setting;
	}

	/**
	 * Returns the children of this node.
	 * 
	 * @return an iterator over the children of this node.
	 */
	public Iterator<ViewModelGraphNode<T>> getChildren() {
		return children.iterator();
	}

}
