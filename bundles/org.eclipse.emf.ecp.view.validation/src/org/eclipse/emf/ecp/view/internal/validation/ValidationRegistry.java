/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Edgar Mueller - added remove methods to avoid leaks, removed subprocessors
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/**
 * This registry maps eObjects in a domain model to their corresponding renderables.
 * 
 * @author jfaltermeier
 * @author Eugen Neufeld
 * @author emueller
 */
public class ValidationRegistry {

	/**
	 * The list is ordered so that if two renderables
	 * are part of the same hierarchy the child will have a lower index than the parent.
	 */
	private final Map<EObject, Set<VControl>> domainObjectToAffectedControls;
	private final Set<VElement> processedRenderables = new LinkedHashSet<VElement>();
	private Map<VElement, Set<EObject>> controlToDomainMapping;

	/**
	 * Default constructor.
	 */
	public ValidationRegistry() {
		controlToDomainMapping = new LinkedHashMap<VElement, Set<EObject>>();
		domainObjectToAffectedControls = new LinkedHashMap<EObject, Set<VControl>>();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
	}

	/**
	 * Maps eObjects from the model to their corresponding renderables.
	 * 
	 * @param domainModel the domain model
	 * @param renderable the view model
	 */
	public void register(EObject domainModel, VElement renderable) {
		final Map<EObject, Set<VControl>> domainToControlMapping = getDomainToControlMapping(domainModel,
			renderable);
		fillMap(domainToControlMapping, domainObjectToAffectedControls);
	}

	/**
	 * This method walks down the containment tree of the renderable and collects the EObject to Control mapping.
	 * All renderables are added to the {@link #processedRenderables} set, which contains all {@link VElement
	 * Renderables} which were parsed.
	 * Furthermore this method adds a default {@link org.eclipse.emf.ecp.view.spi.model.VDiagnostic VDiagnostic} to each
	 * {@link VElement} if it was not set before.
	 * 3 cases are differentiated:
	 * <ul>
	 * <li>the {@link VElement} is a {@link VControl}</li>
	 * <li>the renderable is searched for containment {@link VElement VElement}s, which are then passed recursively</li>
	 * </ul>
	 * 
	 * @param domainModel the {@link EObject} representing the root Domain Element
	 * @param renderable the {@link VElement} to check
	 * @return a Map of EObject to {@link VControl VControls}
	 */
	public Map<EObject, Set<VControl>> getDomainToControlMapping(EObject domainModel, VElement renderable) {
		processedRenderables.add(renderable);
		if (renderable.getDiagnostic() == null) {
			renderable.setDiagnostic(VViewFactory.eINSTANCE.createDiagnostic());
		}
		final Map<EObject, Set<VControl>> result = new LinkedHashMap<EObject, Set<VControl>>();
		if (VControl.class.isInstance(renderable)) {
			final VControl control = (VControl) renderable;
			final VDomainModelReference domainModelReference = control.getDomainModelReference();
			final Iterator<Setting> iterator = domainModelReference.getIterator();
			while (iterator.hasNext()) {
				final Setting setting = iterator.next();
				final EObject referencedDomainModel = setting.getEObject();
				if (!result.containsKey(referencedDomainModel)) {
					result.put(referencedDomainModel, new LinkedHashSet<VControl>());
				}
				result.get(referencedDomainModel).add(control);
			}
		} else {
			for (final EObject eObject : renderable.eContents()) {
				if (!VElement.class.isInstance(eObject)) {
					continue;
				}
				fillMap(getDomainToControlMapping(domainModel, (VElement) eObject), result);
			}
		}

		return result;
	}

	/**
	 * Resolved an {@link EObject} based on the list of {@link EReference EReferences}.
	 * 
	 * @param domainModel the root domain Model
	 * @param references the list of {@link EReference} to use for navigation
	 * @return the resolved {@link EObject}
	 */
	public EObject resolveDomainModel(EObject domainModel, List<EReference> references) {
		EObject referencedDomainModel = domainModel;
		for (final EReference eReference : references) {
			if (!eReference.getEContainingClass().isInstance(referencedDomainModel)) {
				continue;
			}
			EObject child = (EObject) referencedDomainModel.eGet(eReference);
			if (child == null) {
				child = EcoreUtil.create(eReference.getEReferenceType());
				referencedDomainModel.eSet(eReference, child);
			}
			referencedDomainModel = child;
		}
		return referencedDomainModel;
	}

	private void fillMap(Map<EObject, Set<VControl>> source, Map<EObject, Set<VControl>> target) {
		for (final EObject domainSource : source.keySet()) {
			final Set<VControl> controlSet = source.get(domainSource);
			fillMapEntry(target, domainSource, controlSet);
		}
	}

	private void fillMapEntry(Map<EObject, Set<VControl>> target, final EObject domainSource,
		Set<VControl> controlSet) {
		for (final VControl abstractControl : controlSet) {
			final Set<EObject> set = controlToDomainMapping.get(abstractControl);
			if (set == null) {
				controlToDomainMapping.put(abstractControl, new LinkedHashSet<EObject>());
			}
			controlToDomainMapping.get(abstractControl).add(domainSource);
		}
		if (!target.containsKey(domainSource)) {
			target.put(domainSource, controlSet);
		} else {
			target.get(domainSource).addAll(controlSet);
		}
	}

	/**
	 * Adds a single control for a domainObject to the registry.
	 * 
	 * @param domainObject the domain object
	 * @param control the control to be registered for the domain object
	 */
	public void addEObjectControlMapping(EObject domainObject, VControl control) {
		final LinkedHashSet<VControl> controlSet = new LinkedHashSet<VControl>();
		controlSet.add(control);
		fillMapEntry(domainObjectToAffectedControls, domainObject, controlSet);
	}

	/**
	 * Removes the given {@link VElement} from the mappings of the registry.
	 * 
	 * @param renderable
	 *            the {@link VElement} to be removed from the registry
	 */
	public void removeRenderable(VElement renderable) {
		final Set<EObject> eObjects = controlToDomainMapping.get(renderable);
		if (eObjects != null) {
			for (final EObject eObject : eObjects) {
				final Set<VControl> set = domainObjectToAffectedControls.get(eObject);
				if (set != null && set.contains(renderable)) {
					domainObjectToAffectedControls.get(eObject).remove(renderable);
				}
			}
		}
		controlToDomainMapping.remove(renderable);
		processedRenderables.remove(renderable);
	}

	/**
	 * Removes the given domain object from the mappings of the registry.
	 * 
	 * @param domainObject
	 *            the domain object to be removed from the registry
	 */
	public void removeDomainObject(EObject domainObject) {
		final Set<VControl> set = domainObjectToAffectedControls.get(domainObject);
		if (set != null) {
			for (final VControl abstractControl : set) {
				controlToDomainMapping.remove(abstractControl);
			}
		}
		domainObjectToAffectedControls.remove(domainObject);
	}

	/**
	 * Returns all associated {@link VElement}s for the given model. The list is ordered so that if two renderables
	 * are part of the same hierachy the child will have a lower index than the parent.
	 * 
	 * @param model the model
	 * @return list of all renderables
	 */
	public Set<VControl> getRenderablesForEObject(EObject model) {
		if (!domainObjectToAffectedControls.containsKey(model)) {

			final EObject parent = model.eContainer();

			if (parent != null && domainObjectToAffectedControls.containsKey(parent)) {
				final Set<VControl> set = domainObjectToAffectedControls.get(parent);
				for (final VControl vControl : set) {
					register(model, vControl);
				}
				return set;
			}

			return Collections.emptySet();
		}
		return domainObjectToAffectedControls.get(model);
	}

	/**
	 * Checks whether a {@link VElement} was already checked for controls a thus added to the
	 * {@link #domainObjectToAffectedControls}.
	 * 
	 * @param renderable the {@link VElement} to check
	 * @return true if {@link VElement} was already checked
	 */
	public boolean containsRenderable(VElement renderable) {
		return processedRenderables.contains(renderable);
	}

}
