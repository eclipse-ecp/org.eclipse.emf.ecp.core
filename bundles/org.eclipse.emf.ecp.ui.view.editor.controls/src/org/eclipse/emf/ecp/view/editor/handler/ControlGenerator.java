/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.view.editor.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.view.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

public final class ControlGenerator {

	private ControlGenerator() {

	}

	public static void addControls(ECPProject project, VContainer compositeToFill,
		EClass datasegment, Set<EStructuralFeature> features) {

		final EClass rootClass = Helper.getRootEClass(project);
		addControls(rootClass, compositeToFill, datasegment, features);

	}

	/**
	 * Create controls and set them to the view.
	 * 
	 * @param rootClass the rootClass for identifying the path
	 * @param compositeToFill the {@link VElement} to fill , must be of type {@link VView} or {@link VContainer}
	 * @param datasegment the class to parse
	 * @param features the list of features to create
	 */
	public static void addControls(EClass rootClass, VElement compositeToFill, EClass datasegment,
		Set<EStructuralFeature> features) {
		if (!VContainer.class.isInstance(compositeToFill) && !VView.class.isInstance(compositeToFill)) {
			return;
		}
		final Map<EClass, EReference> childParentReferenceMap = new HashMap<EClass, EReference>();
		Helper.getReferenceMap(rootClass, childParentReferenceMap);
		final List<EReference> bottomUpPath = Helper.getReferencePath(datasegment,
			childParentReferenceMap);

		for (final EStructuralFeature feature : features) {
			final VControl control = VViewFactory.eINSTANCE.createControl();
			control.setName("Control " + feature.getName());
			control.setReadonly(false);

			final VFeaturePathDomainModelReference modelReference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			modelReference.setDomainModelEFeature(feature);
			modelReference.getDomainModelEReferencePath().addAll(bottomUpPath);
			// control.setTargetFeature(feature);
			// control.getPathToFeature().addAll(bottomUpPath);
			control.setDomainModelReference(modelReference);

			// add to the composite
			if (VContainer.class.isInstance(compositeToFill)) {
				((VContainer) compositeToFill).getChildren().add(control);
			} else if (VView.class.isInstance(compositeToFill)) {
				((VView) compositeToFill).getChildren().add(control);
			}
		}
	}

}
