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
package org.eclipse.emf.ecp.view.internal.validation.old;

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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.UniqueSetting;
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
	private final Map<UniqueSetting, Set<VControl>> domainObjectToAffectedControls;
	private final Set<VElement> processedRenderables = new LinkedHashSet<VElement>();
	private Map<VElement, Set<UniqueSetting>> controlToDomainMapping;

	/**
	 * Default constructor.
	 */
	public ValidationRegistry() {
		controlToDomainMapping = new LinkedHashMap<VElement, Set<UniqueSetting>>();
		domainObjectToAffectedControls = new LinkedHashMap<UniqueSetting, Set<VControl>>();
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
		final Map<UniqueSetting, Set<VControl>> domainToControlMapping = getDomainToControlMapping(domainModel,
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
	public Map<UniqueSetting, Set<VControl>> getDomainToControlMapping(EObject domainModel, VElement renderable) {
		processedRenderables.add(renderable);
		if (renderable.getDiagnostic() == null) {
			renderable.setDiagnostic(VViewFactory.eINSTANCE.createDiagnostic());
		}
		final Map<UniqueSetting, Set<VControl>> result = new LinkedHashMap<UniqueSetting, Set<VControl>>();
		if (VControl.class.isInstance(renderable)) {
			final VControl control = (VControl) renderable;
			final VDomainModelReference domainModelReference = control.getDomainModelReference();
			if (domainModelReference == null) {
				return result;
			}
			final Iterator<Setting> iterator = domainModelReference.getIterator();
			while (iterator.hasNext()) {
				final UniqueSetting uniqueSetting = UniqueSetting.createSetting(iterator.next());
				// final EObject referencedDomainModel = setting.getEObject();
				if (!result.containsKey(uniqueSetting)) {
					result.put(uniqueSetting, new LinkedHashSet<VControl>());
				}
				result.get(uniqueSetting).add(control);
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

	private void fillMap(Map<UniqueSetting, Set<VControl>> source, Map<UniqueSetting, Set<VControl>> target) {
		for (final UniqueSetting domainSource : source.keySet()) {
			final Set<VControl> controlSet = source.get(domainSource);
			fillMapEntry(target, domainSource, controlSet);
		}
	}

	private void fillMapEntry(Map<UniqueSetting, Set<VControl>> target, final UniqueSetting domainSource,
		Set<VControl> controlSet) {
		for (final VControl abstractControl : controlSet) {
			final Set<UniqueSetting> set = controlToDomainMapping.get(abstractControl);
			if (set == null) {
				controlToDomainMapping.put(abstractControl, new LinkedHashSet<UniqueSetting>());
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
	public void addEObjectControlMapping(Setting domainObject, VControl control) {
		final LinkedHashSet<VControl> controlSet = new LinkedHashSet<VControl>();
		controlSet.add(control);
		fillMapEntry(domainObjectToAffectedControls, UniqueSetting.createSetting(domainObject), controlSet);
	}

	/**
	 * Removes the given {@link VElement} from the mappings of the registry.
	 * 
	 * @param renderable
	 *            the {@link VElement} to be removed from the registry
	 */
	public void removeRenderable(VElement renderable) {
		final Set<UniqueSetting> eObjects = controlToDomainMapping.get(renderable);
		if (eObjects != null) {
			for (final UniqueSetting eObject : eObjects) {
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
	 *            the {@link EObject} to be removed from the registry
	 * @return the Set of {@link VControl VControls} which were conntected to the correspoding object
	 */
	public Set<VControl> removeDomainObject(EObject domainObject) {
		final Set<VControl> allRemovedControls = new LinkedHashSet<VControl>();
		for (final EStructuralFeature feature : domainObject.eClass().getEAllStructuralFeatures()) {
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(domainObject,
				feature);
			final Set<VControl> set = domainObjectToAffectedControls.remove(uniqueSetting);
			if (set == null) {
				continue;
			}
			for (final VControl control : set) {
				if (controlToDomainMapping.get(control).size() == 1) {
					allRemovedControls.add(control);
				}
			}
		}
		// final Set<VControl> set = domainObjectToAffectedControls.get(domainObject);
		// if (allRemovedControls != null) {
		for (final VControl abstractControl : allRemovedControls) {
			controlToDomainMapping.remove(abstractControl);
		}
		// }
		return allRemovedControls;
	}

	/**
	 * Returns all associated {@link VElement}s for the given model. The list is ordered so that if two renderables
	 * are part of the same hierachy the child will have a lower index than the parent.
	 * 
	 * @param model the {@link UniqueSetting} to get the controls for
	 * @return list of all renderables
	 */
	public Set<VControl> getRenderablesForEObject(UniqueSetting model) {
		if (!domainObjectToAffectedControls.containsKey(model)) {
			// TODO refresh only with control existing objects
			return Collections.emptySet();
			// final EObject parent = model.getEObject().eContainer();
			// if (parent == null) {
			// return Collections.emptySet();
			// }
			// final Setting parentSetting = ((InternalEObject) parent).eSetting(model
			// .getEObject().eContainmentFeature());
			// final UniqueSetting parentUniqueSetting = UniqueSetting.createSetting(parentSetting);
			// if (!domainObjectToAffectedControls.containsKey(parentUniqueSetting)) {
			// return Collections.emptySet();
			// }
			// final Set<VControl> set = domainObjectToAffectedControls.get(parentUniqueSetting);
			//
			// for (final VControl vControl : set) {
			// final Iterator<Setting> iterator = vControl.getDomainModelReference().getIterator();
			// while (iterator.hasNext()) {
			// final Setting controlSetting = iterator.next();
			// if (UniqueSetting.createSetting(controlSetting).equals(UniqueSetting.createSetting(model))) {
			// register(model.getEObject(), vControl);
			// }
			// }
			// }
			// return set;
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

	/**
	 * Update current Mappings, because a new EObject was added, this updates only for VControls referencing the parent
	 * of the provided EObject.
	 * 
	 * @param newEObject the added EObject
	 */
	public void updateMappings(EObject newEObject) {

		final EObject parent = newEObject.eContainer();
		if (parent == null) {
			return;
		}
		final Setting parentSetting = ((InternalEObject) parent).eSetting(newEObject.eContainmentFeature());
		final UniqueSetting parentUniqueSetting = UniqueSetting.createSetting(parentSetting);
		if (!domainObjectToAffectedControls.containsKey(parentUniqueSetting)) {
			return;
		}
		final Set<VControl> set = domainObjectToAffectedControls.get(parentUniqueSetting);

		for (final VControl vControl : set) {
			final Iterator<Setting> iterator = vControl.getDomainModelReference().getIterator();
			while (iterator.hasNext()) {
				final Setting controlSetting = iterator.next();
				if (controlSetting.getEObject().equals(newEObject)) {
					register(newEObject, vControl);
				}
			}
		}
	}

	/**
	 * Checks whether there is a {@link VElement} for an {@link EObject}.
	 * 
	 * @param eObject the {@link EObject} to search the VElement for
	 * @return true if there is a control, false otherwise
	 */
	public boolean containsVElementForEObject(EObject eObject) {
		for (final EStructuralFeature esf : eObject.eClass().getEAllStructuralFeatures()) {
			if (domainObjectToAffectedControls.containsKey(UniqueSetting.createSetting(eObject, esf))) {
				return true;
			}
		}
		return false;
	}
}
