/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.editor.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.viewers.TreePath;

/**
 * Helper class for editor controls.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public final class Helper {

	private Helper() {

	}

	/**
	 * Retrieves the root ECLass form a project, with the assumption that the project consists only of a {@link VView}.
	 *
	 * @param project the project to check
	 * @return the root {@link EClass}
	 */
	public static EClass getRootEClass(ECPProject project) {
		if (VView.class.isInstance(project.getContents().get(0))) {
			return VView.class.cast(project.getContents().get(0)).getRootEClass();
		}
		return null;
	}

	/**
	 * Retrieves the root ECLass form an EObject. The hierarchy of the provided {@link EObject} is checked for
	 * {@link VView}.
	 *
	 * @param eObject the {@link EObject} to check
	 * @return the root {@link EClass}
	 */
	public static EClass getRootEClass(EObject eObject) {
		EObject testObject = eObject;
		while (!VView.class.isInstance(testObject)
			&& testObject != null) {
			testObject = testObject.eContainer();
		}
		if (VView.class.isInstance(testObject)) {
			return ((VView) testObject).getRootEClass();
		}
		return getRootEClass(ECPUtil.getECPProjectManager().getProject(eObject));
	}

	/**
	 * Fills a map based on all containment features found from the provided EClass onward.
	 *
	 * @param parent the {@link EClass} to use as root
	 * @param childParentReferenceMap the map to fill
	 */
	public static void getReferenceMap(EClass parent,
		Map<EClass, EReference> childParentReferenceMap) {
		for (final EReference eReference : parent.getEAllContainments()) {
			if (!eReference.getEReferenceType().isSuperTypeOf(parent)
				&& childParentReferenceMap.get(eReference.getEReferenceType()) == null) {
				childParentReferenceMap.put(eReference.getEReferenceType(), eReference);
				getReferenceMap(eReference.getEReferenceType(), childParentReferenceMap);
			}
		}
	}

	/**
	 * Retrieves the reference path for a selected EClass from the provided map.
	 *
	 * @param rootEClass the root EClass of the view model
	 * @param selectedClass the {@link EClass} to get the reference path for
	 * @param childParentReferenceMap the map to use
	 * @return the reference path
	 */
	public static List<EReference> getReferencePath(EClass rootEClass, EClass selectedClass,
		Map<EClass, EReference> childParentReferenceMap) {

		final List<EReference> bottomUpPath = new ArrayList<EReference>();

		if (rootEClass == selectedClass) {
			return bottomUpPath;
		}

		EReference parentReference = childParentReferenceMap.get(selectedClass);
		while (parentReference != null && !bottomUpPath.contains(parentReference)) {
			bottomUpPath.add(parentReference);
			selectedClass = parentReference.getEContainingClass();
			if (selectedClass == rootEClass) {
				break;
			}
			parentReference = childParentReferenceMap.get(selectedClass);
		}
		Collections.reverse(bottomUpPath);
		return bottomUpPath;
	}

	/**
	 * Determines all EClasses that can be reached from this EClass.
	 *
	 * @param root the EClass to analyze
	 * @return the Set of ECLasses which can be references from this control
	 */
	public static Set<EClass> getDatasegmentSubclasses(EClass root) {
		final Set<EClass> possibleSegments = new LinkedHashSet<EClass>();
		getDatasegmentSubclasses(root, possibleSegments);
		return possibleSegments;

	}

	private static void getDatasegmentSubclasses(EClass root, Set<EClass> possibleSegments) {
		if (possibleSegments.contains(root)) {
			return;
		}
		possibleSegments.add(root);
		for (final EReference eReference : root.getEAllContainments()) {
			getDatasegmentSubclasses(eReference.getEReferenceType(), possibleSegments);
		}
	}

	/**
	 * Checks whether a {@link EStructuralFeature} has an {@link IItemPropertyDescriptor}.
	 *
	 * @param featureToCheck the {@link EStructuralFeature} to check
	 * @return true if a IItemPropertyDescriptor could be found, false otherwise
	 */
	public static boolean hasFeaturePropertyDescriptor(EStructuralFeature featureToCheck) {

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		final IItemPropertyDescriptor propertyDescriptor = adapterFactoryItemDelegator
			.getPropertyDescriptor(EcoreUtil.create(featureToCheck.getEContainingClass()), featureToCheck);

		composedAdapterFactory.dispose();
		return propertyDescriptor != null;
	}

	/**
	 * Checks whether a {@link EStructuralFeature} has an {@link IItemPropertyDescriptor}.
	 *
	 * @param eClass the root {@link EClass}
	 * @param treePath the {@link TreePath} to check
	 * @return true if a IItemPropertyDescriptor could be found, false otherwise
	 */
	public static boolean hasFeaturePropertyDescriptor(EClass eClass, TreePath treePath) {

		EClass eClassToCheck = eClass;
		final EStructuralFeature featureToCheck = (EStructuralFeature) treePath.getLastSegment();
		final int segments = treePath.getSegmentCount();
		if (segments > 1 && EReference.class.isInstance(treePath.getSegment(segments - 1))) {
			eClassToCheck = EReference.class.cast(treePath.getSegment(segments - 1)).getEReferenceType();
		}
		if (eClassToCheck.isAbstract() || eClass.isInterface()) {
			return false;
		}

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);

		final IItemPropertyDescriptor propertyDescriptor = adapterFactoryItemDelegator
			.getPropertyDescriptor(EcoreUtil.create(eClassToCheck), featureToCheck);

		composedAdapterFactory.dispose();
		return propertyDescriptor != null;
	}

	/**
	 * Returns the last segment of a given {@link VDomainModelReference}.
	 *
	 * @param domainModelReference The {@link VDomainModelReference}
	 * @return the last {@link VDomainModelReferenceSegment} of the given DMR, <code>null</code> if the DMR is null or
	 *         does not contain any segments.
	 * @since 2.0
	 */
	public static VDomainModelReferenceSegment getLastSegment(VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			return null;
		}
		final EList<VDomainModelReferenceSegment> segments = domainModelReference.getSegments();
		if (segments.isEmpty()) {
			return null;
		}
		return segments.get(segments.size() - 1);
	}
}
