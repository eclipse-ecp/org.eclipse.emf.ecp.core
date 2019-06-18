/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import java.util.Arrays;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/**
 * Common functionality for validation tests.
 *
 * @author emueller
 *
 */
public class CommonValidationTest {

	protected VFeaturePathDomainModelReference getVFeaturePathDomainModelReference(EStructuralFeature feature,
		EReference... eReferences) {
		final VFeaturePathDomainModelReference result = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		result.setDomainModelEFeature(feature);
		result.getDomainModelEReferencePath().addAll(Arrays.asList(eReferences));
		return result;
	}
}
