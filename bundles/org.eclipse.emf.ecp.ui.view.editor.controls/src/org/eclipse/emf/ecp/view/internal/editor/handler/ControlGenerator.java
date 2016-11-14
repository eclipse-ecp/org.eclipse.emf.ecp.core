/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * Lucas Koehler - adapted to DMR segments
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/** Generates and sets a list of controls to a {@link VView}. */
public final class ControlGenerator {

	private ControlGenerator() {

	}

	/**
	 * Create controls and set them to the view.
	 *
	 * @param project the {@link ECPProject} from which the rootClass is retrieved. It is assumed that the project
	 *            contains only one {@link VView}.
	 * @param compositeToFill the {@link VElement} to fill , must be of type {@link VView} or {@link VContainer}
	 * @param datasegment the class to parse
	 * @param features the list of features to create
	 */
	public static void addControls(ECPProject project, VContainer compositeToFill,
		EClass datasegment, Set<EStructuralFeature> features) {

		final EClass rootClass = Helper.getRootEClass(project);
		addControls(rootClass, compositeToFill, features);
	}

	/**
	 * Create controls and set them to the view.
	 *
	 * @param rootClass the rootClass for identifying the path
	 * @param compositeToFill the {@link VElement} to fill , must be of type {@link VView} or {@link VContainer}
	 * @param features the list of features to create
	 */
	public static void addControls(EClass rootClass, VElement compositeToFill, Set<EStructuralFeature> features) {
		if (!VContainer.class.isInstance(compositeToFill) && !VView.class.isInstance(compositeToFill)) {
			return;
		}
		final Map<EClass, EReference> childParentReferenceMap = new HashMap<EClass, EReference>();
		Helper.getReferenceMap(rootClass, childParentReferenceMap);

		for (final EStructuralFeature feature : features) {
			final VControl control = VViewFactory.eINSTANCE.createControl();
			control.setName("Control " + feature.getName()); //$NON-NLS-1$
			control.setReadonly(false);

			final VDomainModelReference modelReference = VViewFactory.eINSTANCE.createDomainModelReference();

			final List<EStructuralFeature> featurePath = new LinkedList<EStructuralFeature>();
			featurePath
				.addAll(Helper.getReferencePath(rootClass, feature.getEContainingClass(), childParentReferenceMap));
			featurePath.add(feature);

			// Create segments and add them to the model reference
			for (final EStructuralFeature structuralFeature : featurePath) {
				final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
					.createFeatureDomainModelReferenceSegment();
				segment.setDomainModelFeature(structuralFeature.getName());
				modelReference.getSegments().add(segment);
			}

			control.setDomainModelReference(modelReference);

			// add to the composite
			if (VContainer.class.isInstance(compositeToFill)) {
				((VContainer) compositeToFill).getChildren().add(control);
			} else if (VView.class.isInstance(compositeToFill)) {
				((VView) compositeToFill).getChildren().add(control);
			}
		}
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
			resource.save(null);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}
}
