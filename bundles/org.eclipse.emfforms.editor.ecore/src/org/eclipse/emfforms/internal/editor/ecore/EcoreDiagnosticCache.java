/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 533522
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;

/**
 * {@link DiagnosticCache} for ecore.
 */
public class EcoreDiagnosticCache extends DiagnosticCache {

	/**
	 * @param input the input
	 */
	public EcoreDiagnosticCache(Notifier input) {
		super(input);

		final Set<EObject> rootObjects = new LinkedHashSet<>();
		if (ResourceSet.class.isInstance(input)) {
			final EList<Resource> resources = ResourceSet.class.cast(input).getResources();
			resources.stream().map(Resource::getContents).forEach(rootObjects::addAll);
		} else if (Resource.class.isInstance(input)) {
			rootObjects.addAll(Resource.class.cast(input).getContents());
		} else if (EObject.class.isInstance(input)) {
			rootObjects.add(EObject.class.cast(input));
		} else {
			return;
		}

		// The super implementation builds the cache tree on initialization, but does not propagate validation results
		// of children to their parents. This means, the own diagnostic for every EObject in the containment history is
		// known but nodes do not know about the diagnostics of their children, yet. As a consequence, validation errors
		// can only be shown where they occur.
		// In order to show the errors at the parents, we walk down the containment hierarchy in a depth-first search
		// approach and propagate the validation results upwards. On the way up, each node propagates its aggregated
		// validation result which is the most severe diagnostic of itself and its children. With this we only need to
		// propagate the results one time from bottom to the top.
		rootObjects.forEach(this::depthFirstCacheUpdate);
	}

	/**
	 * Recursively walks down the containment hierarchy of the given root object and puts the children's diagnostics
	 * into the cache of root's cached tree node. Afterwards, the most severe diagnostic of root's subtree (including
	 * root) is returned which allows caching it in root's parent.
	 *
	 * @param root The root object of the current depth first search
	 * @return The most severe Diagnostic in the subtree of the root object
	 */
	private Diagnostic depthFirstCacheUpdate(EObject root) {
		final CachedTreeNode<Diagnostic> treeNode = getNodes().get(root);
		root.eContents().stream().forEach(c -> treeNode.putIntoCache(c, depthFirstCacheUpdate(c)));
		return treeNode.getDisplayValue();
	}

	@Override
	protected void updateCache(EObject element, DiagnosticCache cache) {
		super.updateCache(element, cache);

		// In the initial walk over the contents, we will already have processed
		// the containment chain
		if (!isInitializing()) {
			final EObject parent = element.eContainer();
			if (parent != null) {
				updateCache(parent, cache);
			}
		}
	}

	@Override
	protected void updateCacheWithoutRefresh(EObject element, DiagnosticCache cache) {
		super.updateCacheWithoutRefresh(element, cache);

		// In the initial walk over the contents, we will already have processed
		// the containment chain
		if (!isInitializing()) {
			final EObject parent = element.eContainer();
			if (parent != null) {
				updateCacheWithoutRefresh(parent, cache);
			}
		}
	}

}
