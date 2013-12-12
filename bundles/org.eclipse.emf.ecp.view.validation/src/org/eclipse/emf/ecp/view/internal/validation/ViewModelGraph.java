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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * The validation graph is a graph like structure where each {@link VElement} is a represented as
 * {@link ViewModelGraphNode} with a value of type {@code T}. The graph structure follows the {@link EObject} hierarchy
 * of
 * the view model (and in that sense conforms to the containment tree of the Renderable).
 * The actually computed values of the nodes are based on domain objects, which are also represented as
 * {@link ViewModelGraphNode}s, together with a feature, but in contrast to the nodes containing the {@link VElement} s,
 * never
 * have any children.
 * Nodes containing domain objects also can have multiple parent.
 * For instance this is the case if a domain object's feature is visualized by multiple controls.
 * 
 * @author emueller
 * 
 * @param <T> the type stored by this tree
 */
public abstract class ViewModelGraph<T> {

	private final SettingsNodeMapping<T> viewModelSettings;
	private final SettingsNodeMapping<T> domainModelSettings;
	private final EObject domainModel;

	/**
	 * Constructor.
	 * 
	 * @param viewModel
	 *            the root of the view model
	 * @param domainModel
	 *            the root of the domain model
	 * @param comparator
	 *            the comparator that is used to compute the most distinctive value
	 */
	public ViewModelGraph(VElement viewModel, EObject domainModel, Comparator<T> comparator) {
		this.domainModel = domainModel;
		viewModelSettings = new SettingsNodeMapping<T>(comparator);
		domainModelSettings = new SettingsNodeMapping<T>(comparator);
		buildRenderableNodes(viewModel);
	}

	private void buildRenderableNodes(EObject viewModel) {
		final EList<EObject> eContents = viewModel.eContents();
		final ViewModelGraphNode<T> parentNode = viewModelSettings.createNode(viewModel,
			SettingsNodeMapping.allFeatures(),
			getDefaultValue(),
			false);
		for (final EObject content : eContents) {
			if (!VElement.class.isInstance(content)) {
				continue;
			}
			final ViewModelGraphNode<T> childNode = viewModelSettings.createNode(content,
				SettingsNodeMapping.allFeatures(),
				getDefaultValue(),
				false);
			parentNode.addChild(childNode);
			buildRenderableNodes(content);
		}
	}

	/**
	 * Returns the default value for a cached node.
	 * 
	 * @return the default value for a cached tree node
	 */
	public abstract T getDefaultValue();

	/**
	 * Returns the computed value for the given {@link VElement}.
	 * 
	 * @param renderable
	 *            the {@link VElement} to fetch the value for
	 * @return the value for the given {@link VElement}
	 */
	public T getValue(VElement renderable) {
		return viewModelSettings.getNode(renderable, SettingsNodeMapping.allFeatures()).getValue();
	}

	/**
	 * Returns all values set for the provided domainObject.
	 * 
	 * @param domainObject the {@link EObject} to search the values for
	 * @return the set of all values currently associated with the provided {@link EObject}
	 */
	public Set<T> getAllValues(EObject domainObject) {
		final Set<T> result = new LinkedHashSet<T>();
		final Set<ViewModelGraphNode<T>> allNodes = domainModelSettings.getAllNodes(domainObject);
		for (final ViewModelGraphNode<T> viewModelGraphNode : allNodes) {
			result.add(viewModelGraphNode.getValue());
		}
		return result;
	}

	/**
	 * Returns a Map containing all {@link EStructuralFeature EStructuralFeatures} and the corresponding value for the
	 * provided domainObject.
	 * 
	 * @param domainObject the {@link EObject} to search the map for
	 * @return a mapping between all {@link EStructuralFeature EStructuralFeatures} and its associated value, currently
	 *         associated with the provided {@link EObject}
	 */
	public Map<EStructuralFeature, T> getValuePerFeature(EObject domainObject) {
		final Map<EStructuralFeature, T> result = new LinkedHashMap<EStructuralFeature, T>();
		final Set<ViewModelGraphNode<T>> allNodes = domainModelSettings.getAllNodes(domainObject);
		for (final ViewModelGraphNode<T> viewModelGraphNode : allNodes) {
			result.put(viewModelGraphNode.getSetting().getEStructuralFeature(), viewModelGraphNode.getValue());
		}
		return result;
	}

	/**
	 * Updates the cached entry for the given {@link EObject} with the given value.<br/>
	 * If the cached entry does not yet exist, it will be created.
	 * 
	 * @param renderable
	 *            the {@link VElement} that is referencing the {@code feature} of the {@code domainObject}
	 * @param domainObject
	 *            the domain object that caused the {@code value} to be computed
	 * @param feature
	 *            the {@link EStructuralFeature} referenced by the Renderable
	 * @param value
	 *            the actual value
	 */
	public void update(VElement renderable, EObject domainObject, EStructuralFeature feature, T value) {

		final Set<ViewModelGraphNode<T>> parentNodes = updateNodes(renderable, domainObject, feature, value);

		for (final ViewModelGraphNode<T> cachedNode : parentNodes) {
			updateParentNodes(cachedNode);
		}
	}

	private Set<EObject> updateParentNodes(ViewModelGraphNode<T> node) {

		final Set<EObject> updated = new LinkedHashSet<EObject>();

		final Set<ViewModelGraphNode<T>> nodes = node.getParents();
		updateRenderable((VElement) node.getSetting().getEObject());

		for (final ViewModelGraphNode<T> cachedNode : nodes) {
			updated.addAll(updateParentNodes(cachedNode));
		}

		return updated;
	}

	/**
	 * Removes the given domain object from the graph.
	 * 
	 * @param domainObject
	 *            the domain object to be removed
	 */
	public void removeDomainObject(EObject domainObject) {
		final EList<EStructuralFeature> eAllStructuralFeatures = domainObject.eClass().getEAllStructuralFeatures();
		for (final EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {

			final ViewModelGraphNode<T> node = domainModelSettings.getNode(domainObject, eStructuralFeature);
			if (node == null) {
				continue;
			}
			// domain nodes are always leaf, no need to take care of their children
			final Set<ViewModelGraphNode<T>> parents = node.getParents();
			for (final ViewModelGraphNode<T> parentNode : new LinkedHashSet<ViewModelGraphNode<T>>(parents)) {
				parentNode.removeChild(node);
			}
		}
		domainModelSettings.removeAll(domainObject);
	}

	/**
	 * Removes the given {@link VElement} from the graph.
	 * 
	 * @param renderable
	 *            the {@link VElement} be removed
	 */
	public void removeRenderable(VElement renderable) {
		final ViewModelGraphNode<T> node = viewModelSettings.getNode(renderable, SettingsNodeMapping.allFeatures());

		if (node != null) {
			final Set<ViewModelGraphNode<T>> parents = node.getParents();
			for (final ViewModelGraphNode<T> parentNode : new LinkedHashSet<ViewModelGraphNode<T>>(parents)) {
				parentNode.removeChild(node);
				updateParentNodes(parentNode);
			}
			// renderable nodes are not leafs
			final Iterator<ViewModelGraphNode<T>> children = node.getChildren();
			while (children.hasNext()) {
				node.removeChild(children.next());
			}
		}

		viewModelSettings.removeAll(renderable);
	}

	/**
	 * Allows clients to hook into the update call.
	 * 
	 * @param renderable
	 *            the {@link VElement} being updated
	 */
	protected void updateRenderable(VElement renderable) {
	}

	private Set<ViewModelGraphNode<T>> updateNodes(VElement renderable, EObject domainObject,
		EStructuralFeature feature, T value) {

		final Set<ViewModelGraphNode<T>> affectedParentNodes = new LinkedHashSet<ViewModelGraphNode<T>>();

		final ViewModelGraphNode<T> renderableNode = getNodeForRenderable(renderable);
		final ViewModelGraphNode<T> domainNode = getNodeForDomainObject(domainObject, feature, value);

		affectedParentNodes.add(renderableNode);

		if (domainObjectHasBeenRemoved(domainObject, feature)) {
			removeChildFromParents(domainNode);
			return affectedParentNodes;
		}

		if (!renderableNode.containsChild(domainNode)) {
			renderableNode.addChild(domainNode);
		}

		// we only change a node's value if it contains a domain object
		domainNode.setValue(value);

		return affectedParentNodes;
	}

	private ViewModelGraphNode<T> getNodeForDomainObject(EObject domainObject, EStructuralFeature feature, T value) {
		ViewModelGraphNode<T> node = domainModelSettings.getNode(domainObject, feature);

		if (node == null) {
			node = domainModelSettings.createNode(domainObject, feature, value, true);
		}

		return node;
	}

	private void removeChildFromParents(final ViewModelGraphNode<T> childNode) {
		final Set<ViewModelGraphNode<T>> parentNodes = new LinkedHashSet<ViewModelGraphNode<T>>(childNode.getParents());
		for (final ViewModelGraphNode<T> oldParent : parentNodes) {
			oldParent.removeChild(childNode);
		}
	}

	private boolean domainObjectHasBeenRemoved(EObject domainObject, EStructuralFeature feature) {
		final ViewModelGraphNode<T> node = domainModelSettings.getNode(domainObject, feature);
		if (node.isDomainObject() && !isDomainModelElement(domainObject)) {
			return true;
		}
		return false;
	}

	private ViewModelGraphNode<T> getNodeForRenderable(VElement renderable) {
		ViewModelGraphNode<T> node = viewModelSettings.getNode(renderable, SettingsNodeMapping.allFeatures());

		// dynamic registration
		if (node == null) {
			node = viewModelSettings
				.createNode(renderable, SettingsNodeMapping.allFeatures(), getDefaultValue(), false);
			final EObject eContainer = renderable.eContainer();
			// must have parent
			ViewModelGraphNode<T> possibleParent = viewModelSettings
				.getNode(eContainer, SettingsNodeMapping.allFeatures());
			if (possibleParent == null) {
				possibleParent = getNodeForRenderable((VElement) eContainer);
			}
			possibleParent.addChild(node);
		}

		return node;
	}

	private boolean isDomainModelElement(EObject possibleChild) {
		return isContainedInModel(domainModel, possibleChild);
	}

	private boolean isContainedInModel(EObject root, EObject possibleChild) {

		if (root == possibleChild) {
			return true;
		}

		EObject container = possibleChild;
		while (container != null) {
			if (container == root) {
				return true;
			}
			container = container.eContainer();
		}

		return false;
	}

}
