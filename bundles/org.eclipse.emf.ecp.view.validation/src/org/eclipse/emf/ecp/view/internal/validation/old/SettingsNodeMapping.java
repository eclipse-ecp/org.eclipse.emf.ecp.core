/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation.old;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;

/**
 * Maps a {@link UniqueSetting} to a {@link ViewModelGraphNode}.
 * 
 * @author emueller
 * 
 */
public class SettingsNodeMapping {

	private static final EStructuralFeature ALL_FEATURES = null;

	private final Map<UniqueSetting, ViewModelGraphNode> settings;
	private final Comparator<VDiagnostic> comparator;

	/**
	 * Constructor.
	 * 
	 * @param comparator
	 *            the comparator that will be used by the constructed nodes when calling
	 *            {@link #createNode(EObject, EStructuralFeature, VDiagnostic, boolean)}
	 */
	public SettingsNodeMapping(Comparator<VDiagnostic> comparator) {
		settings = new LinkedHashMap<UniqueSetting, ViewModelGraphNode>();
		this.comparator = comparator;
	}

	/**
	 * Constructor.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @param feature
	 *            a {@link EStructuralFeature} of the {@link EObject}
	 * @return the node for the given setting
	 */
	public ViewModelGraphNode getNode(EObject eObject, EStructuralFeature feature) {
		final UniqueSetting setting = UniqueSetting.createSetting(eObject, feature);
		final ViewModelGraphNode node = settings.get(setting);

		if (node == null) {
			return null;
		}

		return node;
	}

	/**
	 * Returns a set of all {@link ViewModelGraphNode ViewModelGraphNodes} which are registered with one of the EObject
	 * features.
	 * 
	 * @param eObject the {@link EObject} to find all GraphNodes for
	 * @return the set of all {@link ViewModelGraphNode ViewModelGraphNodes} for the provided {@link EObject}
	 */
	public Set<ViewModelGraphNode> getAllNodes(EObject eObject) {

		final Set<ViewModelGraphNode> result = new LinkedHashSet<ViewModelGraphNode>();
		final EList<EStructuralFeature> eAllStructuralFeatures = eObject.eClass().getEAllStructuralFeatures();
		for (final EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
			final UniqueSetting setting = UniqueSetting.createSetting(eObject, eStructuralFeature);
			final ViewModelGraphNode viewModelGraphNode = settings.get(setting);
			if (viewModelGraphNode != null) {
				result.add(viewModelGraphNode);
			}
		}

		return result;
	}

	/**
	 * Creates a node for the given {@link EObject} and {@link EStructuralFeature} together
	 * with an initial value.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @param feature
	 *            a {@link EStructuralFeature} of the {@link EObject}
	 * @param value
	 *            the initial value
	 * @param isDomainObject
	 *            whether the object contained by the node is a domain object
	 * @return the constructed {@link ViewModelGraphNode} for the given setting
	 */
	public ViewModelGraphNode createNode(EObject eObject, EStructuralFeature feature,
		VDiagnostic value, boolean isDomainObject) {
		final UniqueSetting setting = UniqueSetting.createSetting(eObject, feature);
		ViewModelGraphNode node = settings.get(setting);

		if (node == null) {
			node = createNode(setting, value, isDomainObject);
			settings.put(setting, node);
		} else {
			node.setValue(value);
		}

		return node;
	}

	private ViewModelGraphNode createNode(UniqueSetting setting, VDiagnostic initValue,
		boolean isDomainObject) {
		return new ViewModelGraphNode(setting, initValue, isDomainObject, comparator);
	}

	/**
	 * Removes the given {@link EObject} and all its {@link UniqueSetting}s from the map.
	 * 
	 * @param eObject
	 *            the {@link EObject} whose mappings should be removed
	 */
	public void removeAll(EObject eObject) {
		final EList<EStructuralFeature> eAllStructuralFeatures = eObject.eClass().getEAllStructuralFeatures();
		for (final EStructuralFeature eStructuralFeature : eAllStructuralFeatures) {
			final UniqueSetting setting = UniqueSetting.createSetting(eObject, eStructuralFeature);
			settings.remove(setting);
		}
		final UniqueSetting setting = UniqueSetting.createSetting(eObject, ALL_FEATURES);
		settings.remove(setting);
	}

	/**
	 * Returns a {@link EStructuralFeature} that is used in case
	 * a node does not correlate to a any feature of an {@link EObject}.
	 * 
	 * @return a unique {@link EStructuralFeature} that is used in case
	 *         a node does not correlate to a any feature of an {@link EObject}.
	 */
	public static EStructuralFeature allFeatures() {
		return ALL_FEATURES;
	}
}
