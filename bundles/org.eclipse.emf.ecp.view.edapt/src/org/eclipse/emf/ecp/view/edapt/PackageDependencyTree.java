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
	 * For testing purposes.
	 * 
	 * @return a map of all registered nodes
	 */
	Map<String, PackageTreeNode> getNsURIToNodeMap() {
		return nsURIToNodeMap;
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

	/**
	 * Adds the node and all dependencies (if not already there) to the tree.
	 *
	 * @param node the node to add
	 */
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
	 * Simple tree data structure to order the changes of all required epackages during the migration.
	 *
	 * @author jfaltermeier
	 *
	 */
	static class PackageTreeNode {

		private final String nsURI;
		private final Set<PackageTreeNode> parents;
		private final Set<PackageTreeNode> children;

		/**
		 * Constructs a new treenode for the given ns uri.
		 *
		 * @param nsURI the uri
		 */
		public PackageTreeNode(String nsURI) {
			this.nsURI = nsURI;
			parents = new LinkedHashSet<PackageTreeNode>();
			children = new LinkedHashSet<PackageTreeNode>();
		}

		/**
		 * Returns the namesapce uri.
		 *
		 * @return the node's uri
		 */
		public String getNSURI() {
			return nsURI;
		}

		/**
		 * Adds a parent to this node.
		 *
		 * @param node the parent node
		 */
		public void addParent(PackageTreeNode node) {
			parents.add(node);
		}

		/**
		 * Adds a child to this node.
		 *
		 * @param node the child
		 */
		public void addChild(PackageTreeNode node) {
			children.add(node);
		}

		/**
		 * Returns all parents of this node.
		 *
		 * @return parents
		 */
		public Set<PackageTreeNode> getParents() {
			return parents;
		}

		/**
		 * Returns all children of this node.
		 *
		 * @return the children.
		 */
		public Set<PackageTreeNode> getChildren() {
			return children;
		}

	}

}
