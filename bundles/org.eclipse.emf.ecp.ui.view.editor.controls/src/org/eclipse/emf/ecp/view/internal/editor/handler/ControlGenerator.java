/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * Lucas Koehler - Extend for segment tooling
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/** Generates and sets a list of controls to a {@link VView}. */
public final class ControlGenerator {

	private ControlGenerator() {

	}

	/**
	 * Create controls and set them to the view.
	 *
	 * @param rootClass the rootClass for identifying the path
	 * @param compositeToFill the {@link VElement} to fill , must be of type {@link VView} or {@link VContainer}
	 * @param features the list of features to create
	 */
	public static void addControls(EClass rootClass, VElement compositeToFill, Set<EStructuralFeature> features) {
		addControls(rootClass, compositeToFill, features, ToolingModeUtil.isSegmentToolingEnabled());
	}

	/**
	 * Create controls and set them to the view.
	 *
	 * @param rootClass the rootClass for identifying the path
	 * @param compositeToFill the {@link VElement} to fill , must be of type {@link VView} or {@link VContainer}
	 * @param features the list of features to create
	 * @param segmentTooling <code>true</code> if segment based DMRs should be generated
	 */
	static void addControls(EClass rootClass, VElement compositeToFill, Set<EStructuralFeature> features,
		boolean segmentTooling) {
		if (!VContainer.class.isInstance(compositeToFill) && !VView.class.isInstance(compositeToFill)) {
			return;
		}
		final Map<EClass, EReference> childParentReferenceMap = new HashMap<EClass, EReference>();
		Helper.getReferenceMap(rootClass, childParentReferenceMap);

		for (final EStructuralFeature feature : features) {
			final VControl control = VViewFactory.eINSTANCE.createControl();
			control.setName("Control " + feature.getName()); //$NON-NLS-1$
			control.setReadonly(false);

			final List<EReference> bottomUpPath = Helper.getReferencePath(rootClass,
				feature.getEContainingClass(), childParentReferenceMap);
			// How to create the dmr depends on the tooling mode (legacy or segments)
			final VDomainModelReference modelReference = segmentTooling
				? createSegmentDmr(feature, bottomUpPath)
				: createLegacyDmr(feature, bottomUpPath);

			control.setDomainModelReference(modelReference);

			// add to the composite
			if (VContainer.class.isInstance(compositeToFill)) {
				((VContainer) compositeToFill).getChildren().add(control);
			} else if (VView.class.isInstance(compositeToFill)) {
				((VView) compositeToFill).getChildren().add(control);
			}
		}
	}

	private static VDomainModelReference createLegacyDmr(EStructuralFeature domainFeature,
		List<EReference> bottomUpPath) {
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		dmr.getDomainModelEReferencePath().addAll(bottomUpPath);
		dmr.setDomainModelEFeature(domainFeature);
		return dmr;
	}

	private static VDomainModelReference createSegmentDmr(EStructuralFeature domainFeature,
		List<EReference> bottomUpPath) {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		bottomUpPath.stream()
			.map(EReference::getName)
			.map(ControlGenerator::createFeatureSegment)
			.forEach(dmr.getSegments()::add);
		dmr.getSegments().add(createFeatureSegment(domainFeature.getName()));
		return dmr;
	}

	private static VDomainModelReferenceSegment createFeatureSegment(String featureName) {
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(featureName);
		return segment;
	}

	/**
	 * Create all the controls and set them to the given view.
	 *
	 * @param view the view for which the controls are created for
	 */
	public static void generateAllControls(final VView view) {
		// load resource
		final URI uri = EcoreUtil.getURI(view);
		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$

		final ResourceSet resSet = new ResourceSetImpl();
		final Resource resource = resSet.getResource(uri, true);
		try {
			resource.load(null);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
		// resolve the proxies
		int rsSize = resSet.getResources().size();
		EcoreUtil.resolveAll(resSet);
		while (rsSize != resSet.getResources().size()) {
			EcoreUtil.resolveAll(resSet);
			rsSize = resSet.getResources().size();
		}
		final VView vview = (VView) resource.getContents().get(0);

		final EClass rootEClass = vview.getRootEClass();
		final Set<EStructuralFeature> mySet = new LinkedHashSet<EStructuralFeature>(
			rootEClass.getEAllStructuralFeatures());
		addControls(rootEClass, (VView) resource.getContents().get(0), mySet);
		try {
			resource.save(Collections.singletonMap(XMLResource.OPTION_ENCODING, "UTF-8")); //$NON-NLS-1$
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}
}
