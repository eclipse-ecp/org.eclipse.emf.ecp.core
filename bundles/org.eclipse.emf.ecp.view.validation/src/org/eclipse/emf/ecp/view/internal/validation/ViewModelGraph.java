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
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
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
 */
public abstract class ViewModelGraph {

	private final SettingsNodeMapping viewModelSettings;
	private final SettingsNodeMapping domainModelSettings;
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
	public ViewModelGraph(VElement viewModel, EObject domainModel, Comparator<VDiagnostic> comparator) {
		this.domainModel = domainModel;
		viewModelSettings = new SettingsNodeMapping(comparator);
		domainModelSettings = new SettingsNodeMapping(comparator);
		buildRenderableNodes(viewModel);
	}

	private void buildRenderableNodes(EObject viewModel) {
		final EList<EObject> eContents = viewModel.eContents();
		final ViewModelGraphNode parentNode = viewModelSettings.createNode(viewModel,
			SettingsNodeMapping.allFeatures(),
			getDefaultValue(),
			false);
		for (final EObject content : eContents) {
			if (!VElement.class.isInstance(content)) {
				continue;
			}
			final ViewModelGraphNode childNode = viewModelSettings.createNode(content,
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
	public abstract VDiagnostic getDefaultValue();

	/**
	 * Returns the computed value for the given {@link VElement}.
	 * 
	 * @param renderable
	 *            the {@link VElement} to fetch the value for
	 * @return the value for the given {@link VElement}
	 */
	public VDiagnostic getValue(VElement renderable) {
		return viewModelSettings.getNode(renderable, SettingsNodeMapping.allFeatures()).getValue();
	}

	/**
	 * Returns all values set for the provided domainObject.
	 * 
	 * @param domainObject the {@link EObject} to search the values for
	 * @return the set of all values currently associated with the provided {@link EObject}
	 */
	public Set<VDiagnostic> getAllValues(EObject domainObject) {
		final Set<VDiagnostic> result = new LinkedHashSet<VDiagnostic>();
		final Set<ViewModelGraphNode> allNodes = domainModelSettings.getAllNodes(domainObject);
		for (final ViewModelGraphNode viewModelGraphNode : allNodes) {
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
	public Map<EStructuralFeature, VDiagnostic> getValuePerFeature(EObject domainObject) {
		final Map<EStructuralFeature, VDiagnostic> result = new LinkedHashMap<EStructuralFeature, VDiagnostic>();
		final Set<ViewModelGraphNode> allNodes = domainModelSettings.getAllNodes(domainObject);
		for (final ViewModelGraphNode viewModelGraphNode : allNodes) {
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
	public void update(VElement renderable, EObject domainObject, EStructuralFeature feature, VDiagnostic value) {

		final Set<ViewModelGraphNode> parentNodes = updateNodes(renderable, domainObject, feature, value);

		for (final ViewModelGraphNode cachedNode : parentNodes) {
			updateParentNodes(cachedNode);
		}
	}

	private Set<EObject> updateParentNodes(ViewModelGraphNode node) {

		final Set<EObject> updated = new LinkedHashSet<EObject>();

		final Set<ViewModelGraphNode> nodes = node.getParents();
		updateRenderable((VElement) node.getSetting().getEObject());

		for (final ViewModelGraphNode cachedNode : nodes) {
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

			final ViewModelGraphNode node = domainModelSettings.getNode(domainObject, eStructuralFeature);
			if (node == null) {
				continue;
			}
			// domain nodes are always leaf, no need to take care of their children
			final Set<ViewModelGraphNode> parents = node.getParents();
			for (final ViewModelGraphNode parentNode : new LinkedHashSet<ViewModelGraphNode>(
				parents)) {
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
		final ViewModelGraphNode node = viewModelSettings.getNode(renderable,
			SettingsNodeMapping.allFeatures());

		if (node != null) {
			final Set<ViewModelGraphNode> parents = node.getParents();
			for (final ViewModelGraphNode parentNode : new LinkedHashSet<ViewModelGraphNode>(
				parents)) {
				parentNode.removeChild(node);
				updateParentNodes(parentNode);
			}
			// renderable nodes are not leafs
			final Iterator<ViewModelGraphNode> children = node.getChildren();
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

	private Set<ViewModelGraphNode> updateNodes(VElement renderable, EObject domainObject,
		EStructuralFeature feature, VDiagnostic value) {

		final Set<ViewModelGraphNode> affectedParentNodes = new LinkedHashSet<ViewModelGraphNode>();

		final ViewModelGraphNode renderableNode = getNodeForRenderable(renderable);
		final ViewModelGraphNode domainNode = getNodeForDomainObject(domainObject, feature, value);

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

	private ViewModelGraphNode getNodeForDomainObject(EObject domainObject, EStructuralFeature feature,
		VDiagnostic value) {
		ViewModelGraphNode node = domainModelSettings.getNode(domainObject, feature);

		if (node == null) {
			node = domainModelSettings.createNode(domainObject, feature, value, true);
		}

		return node;
	}

	private void removeChildFromParents(final ViewModelGraphNode childNode) {
		final Set<ViewModelGraphNode> parentNodes = new LinkedHashSet<ViewModelGraphNode>(
			childNode.getParents());
		for (final ViewModelGraphNode oldParent : parentNodes) {
			oldParent.removeChild(childNode);
		}
	}

	private boolean domainObjectHasBeenRemoved(EObject domainObject, EStructuralFeature feature) {
		final ViewModelGraphNode node = domainModelSettings.getNode(domainObject, feature);
		if (node.isDomainObject() && !isDomainModelElement(domainObject)) {
			return true;
		}
		return false;
	}

	private ViewModelGraphNode getNodeForRenderable(VElement renderable) {
		ViewModelGraphNode node = viewModelSettings.getNode(renderable, SettingsNodeMapping.allFeatures());

		// dynamic registration
		if (node == null) {
			node = viewModelSettings
				.createNode(renderable, SettingsNodeMapping.allFeatures(), getDefaultValue(), false);
			final EObject eContainer = renderable.eContainer();
			// must have parent
			ViewModelGraphNode possibleParent = viewModelSettings
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
