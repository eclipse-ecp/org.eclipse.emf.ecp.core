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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.osgi.service.component.annotations.Component;

/**
 * {@link DmrToRootEClassConverter} for {@link VFeaturePathDomainModelReference}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "FeatureDmrToRootEClassConverter")
public class FeatureDmrToRootEClassConverter implements DmrToRootEClassConverter {

	@Override
	public double isApplicable(VDomainModelReference dmr) {
		if (dmr instanceof VFeaturePathDomainModelReference) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public EClass getRootEClass(VDomainModelReference dmr) {
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) dmr;
		if (featureDmr.getDomainModelEFeature() == null) {
			throw new IllegalArgumentException(
				String.format("The DMR %s has no domain model feature.", featureDmr)); //$NON-NLS-1$
		}
		return featureDmr.getDomainModelEReferencePath().isEmpty()
			? featureDmr.getDomainModelEFeature().getEContainingClass()
			: featureDmr.getDomainModelEReferencePath().get(0).getEContainingClass();
	}

}
