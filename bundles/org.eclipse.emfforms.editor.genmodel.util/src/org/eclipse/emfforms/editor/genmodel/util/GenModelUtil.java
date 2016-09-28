/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.editor.genmodel.util;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

/**
 * Util class for manipulating {@link GenModel GenModels}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class GenModelUtil {

	// TODO API: package is set to internal. Adjust this when API is in final state.

	private GenModelUtil() {
		/* util */
	}

	/**
	 * Add the given tag as a property description to all GenFeatures, which do not yet have a description.
	 *
	 * @param genModel the {@link GenModel}
	 * @param tag the tag
	 */
	public static void addDescriptionTags(GenModel genModel, String tag) {
		genModel.reconcile();
		for (final GenPackage genPackage : genModel.getGenPackages()) {
			for (final GenFeature genFeature : genPackage.getAllGenFeatures()) {
				final String propertyDescription = genFeature.getPropertyDescription();
				if (propertyDescription != null && !propertyDescription.isEmpty()
					&& !tag.equalsIgnoreCase(propertyDescription)) {
					/*
					 * non-null, non-empty description which is not equal to the tag
					 * -> don't override
					 */
					continue;
				}
				genFeature.setPropertyDescription(tag);
			}
		}
	}

}
