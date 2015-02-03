/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tree like datastructure representing EPackages and their depdendencies to each other. Offers an Iterator to
 * navigate over the dependencies.
 *
 * @author jfaltermeier
 *
 */
public class PackageDependencyTree {

	private final Map<String, PackageTreeNode> nsURIToNodeMap;
	private final Set<PackageTreeNode> roots;

	/**
	 * Constructs a new empty {@link PackageDependencyTree}.
	 */
	public PackageDependencyTree() {
		roots = new LinkedHashSet<PackageTreeNode>();
		nsURIToNodeMap = new LinkedHashMap<String, PackageTreeNode>();
	}

	/**
	 * Adds a new {@link EPackage} with the given namespace URI to the tree. All required dependencies of the EPackage
	 * will be registered as well.
	 *
	 * @param nsURI the namespace uri of the package to add
	 */
	public void addPackage(String nsURI) {
		if (nsURIToNodeMap.containsKey(nsURI)) {
			return;
		}
		final PackageTreeNode node = createNode(nsURI);
		resolveNode(node);
	}

	private void resolveNode(PackageTreeNode node) {
		final Set<String> nsURIs = new LinkedHashSet<String>();

		/* resolve direct nsURIs */
		final EPackage rootPackage = Registry.INSTANCE.getEPackage(node.getNSURI());
		final TreeIterator<EObject> iterator = rootPackage.eAllContents();
		while (iterator.hasNext()) {
			final EObject next = iterator.next();

			/* check super types of contained classes */
			if (EClass.class.isInstance(next)) {
				final EClass eClass = (EClass) next;
				for (final EClass superType : eClass.getESuperTypes()) {
					nsURIs.add(superType.getEPackage().getNsURI());
				}
			}

			/* check types of features */
			else if (EStructuralFeature.class.isInstance(next)) {
				final EStructuralFeature feature = (EStructuralFeature) next;
				final EClassifier eType = feature.getEType();
				nsURIs.add(eType.getEPackage().getNsURI());
			}
		}

		/* remove self */
		nsURIs.remove(node.getNSURI());

		/* root? */
		if (nsURIs.isEmpty()) {
			roots.add(node);
		}

		/* insert dependencies in tree */
		for (final String nsURI : nsURIs) {
			/* existing */
			if (nsURIToNodeMap.containsKey(nsURI)) {
				final PackageTreeNode parent = nsURIToNodeMap.get(nsURI);
				node.addParent(parent);
				parent.addChild(node);
			}

			/* non existing */
			else {
				final PackageTreeNode parent = createNode(nsURI);
				resolveNode(parent);
				parent.addChild(node);
				node.addParent(parent);
			}
		}
	}

	private PackageTreeNode createNode(String nsURI) {
		final PackageTreeNode node = new PackageTreeNode(nsURI);
		nsURIToNodeMap.put(nsURI, node);
		return node;
	}

	/**
	 * Returns an iterator for the tree. It will return sets of namespace uris. The set that is returned by next will
	 * always have no unvisited parents, meaning that all parents have been returned by next before.
	 *
	 * @return the iterator
	 */
	public Iterator<Set<String>> getIerator() {
		return new PackageDependencyIterator(roots, nsURIToNodeMap.values());
	}

	/**
	 * Iterator for nsURIs based on the dependencies beginning from the roots.
	 *
	 * @author jfaltermeier
	 *
	 */
	private static class PackageDependencyIterator implements Iterator<Set<String>> {

		private final Set<PackageTreeNode> nodesToVisit;
		private final Set<PackageTreeNode> visitedNodes;
		private final Set<PackageTreeNode> unvisitedNodes;
		private Set<PackageTreeNode> next;

		public PackageDependencyIterator(Collection<PackageTreeNode> roots, Collection<PackageTreeNode> allNodes) {
			visitedNodes = new LinkedHashSet<PackageTreeNode>();
			unvisitedNodes = new LinkedHashSet<PackageTreeNode>(allNodes);
			nodesToVisit = new LinkedHashSet<PackageTreeNode>(roots);
			next = findNext();
		}

		@Override
		public boolean hasNext() {
			return !next.isEmpty();
		}

		@Override
		public Set<String> next() {
			visitedNodes.addAll(next);
			unvisitedNodes.removeAll(next);
			final Set<String> nsuri = new LinkedHashSet<String>();
			for (final PackageTreeNode nextNode : next) {
				nsuri.add(nextNode.getNSURI());
			}
			next = findNext();
			return nsuri;
		}

		private Set<PackageTreeNode> findNext() {
			/* we are looking for a node with no parents */
			final Set<PackageTreeNode> result = new LinkedHashSet<PackageTreeNode>();
			for (final PackageTreeNode node : nodesToVisit) {
				boolean hasUnvisitedParent = false;
				for (final PackageTreeNode parent : node.getParents()) {
					if (!visitedNodes.contains(parent)) {
						hasUnvisitedParent = true;
						break;
					}
				}
				if (!hasUnvisitedParent) {
					for (final PackageTreeNode child : node.getChildren()) {
						if (!visitedNodes.contains(child)) {
							nodesToVisit.add(child);
						}
					}
					result.add(node);
					break;
				}
			}
			if (result.isEmpty() && !unvisitedNodes.isEmpty()) {
				// circle detected
				result.addAll(getCircleSet());
			}
			for (final PackageTreeNode packageTreeNode : result) {
				nodesToVisit.remove(packageTreeNode);
			}
			return result;
		}

		private Collection<? extends PackageTreeNode> getCircleSet() {
			/* 1. circle detection: put all nodes which contain to the same circle in a set */
			final Map<PackageTreeNode, Set<PackageTreeNode>> nodeToCircleMap = new LinkedHashMap<PackageTreeNode, Set<PackageTreeNode>>();
			final Set<Set<PackageTreeNode>> allCircles = new LinkedHashSet<Set<PackageTreeNode>>();
			for (final PackageTreeNode nodeToAllocate : unvisitedNodes) {
				// get existing circle set from map or create new set
				final Set<PackageTreeNode> circle = nodeToCircleMap.containsKey(nodeToAllocate) ?
					nodeToCircleMap.get(nodeToAllocate)
					: new LinkedHashSet<PackageTreeNode>();

				// if new set, fill map
				if (!nodeToCircleMap.containsKey(nodeToAllocate)) {
					circle.add(nodeToAllocate);
					nodeToCircleMap.put(nodeToAllocate, circle);
					allCircles.add(circle);
				}

				// nodes contain to same set if outgoing edge leads back to self
				final Set<PackageTreeNode> outgoingEdges = nodeToAllocate.getChildren();
				for (final PackageTreeNode outgoingEdge : outgoingEdges) {
					final boolean hasPathToOtherNode = hasPathToOtherNode(outgoingEdge, nodeToAllocate,
						new LinkedHashSet<PackageTreeNode>());
					if (hasPathToOtherNode) {
						circle.add(outgoingEdge);
						nodeToCircleMap.put(outgoingEdge, circle);
					}
				}
			}

			/* 2. find root circle */
			return findRootCircle(allCircles);
		}

		private Collection<? extends PackageTreeNode> findRootCircle(final Set<Set<PackageTreeNode>> allCircles) {
			for (final Set<PackageTreeNode> circle : allCircles) {
				// root circle is the set where all unvisited parents are from the same set
				boolean isRoot = true;
				for (final PackageTreeNode node : circle) {
					for (final PackageTreeNode mustBeInCircle : node.getParents()) {
						if (visitedNodes.contains(mustBeInCircle)) {
							// the parent was already returned by the iterator, so we can skip it
							continue;
						}
						if (!circle.contains(mustBeInCircle)) {
							isRoot = false;
							break;
						}
					}
					if (!isRoot) {
						break;
					}
				}
				if (isRoot) {
					return circle;
				}
			}

			// this state is unexpected. if this is reached we could have returned a valid set of nsuri beforehand
			// (either no circle at all, or the circle detection went wrong)
			throw new IllegalStateException("No root circle found"); //$NON-NLS-1$
		}

		private boolean hasPathToOtherNode(PackageTreeNode start, PackageTreeNode target,
			Set<PackageTreeNode> visitedNodes) {
			visitedNodes.add(start);
			final Set<PackageTreeNode> outgoingNodes = start.getChildren();
			if (outgoingNodes.contains(target)) {
				return true;
			}
			boolean result = false;
			for (final PackageTreeNode outgoingNode : outgoingNodes) {
				if (visitedNodes.contains(outgoingNode)) {
					// we already visited/are visiting all children of this node -> skip
					continue;
				}
				result |= hasPathToOtherNode(outgoingNode, target, visitedNodes);
			}
			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Simple tree data structure to order the changes of all required epackages during the migration.
	 *
	 * @author jfaltermeier
	 *
	 */
	private static class PackageTreeNode {

		private final String nsURI;
		private final Set<PackageTreeNode> parents;
		private final Set<PackageTreeNode> children;

		public PackageTreeNode(String nsURI) {
			this.nsURI = nsURI;
			parents = new LinkedHashSet<PackageTreeNode>();
			children = new LinkedHashSet<PackageTreeNode>();
		}

		public String getNSURI() {
			return nsURI;
		}

		public void addParent(PackageTreeNode node) {
			parents.add(node);
		}

		public void addChild(PackageTreeNode node) {
			children.add(node);
		}

		public Set<PackageTreeNode> getParents() {
			return parents;
		}

		public Set<PackageTreeNode> getChildren() {
			return children;
		}

	}

}
