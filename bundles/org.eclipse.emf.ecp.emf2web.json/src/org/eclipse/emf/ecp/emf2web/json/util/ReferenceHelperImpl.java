/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.util;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.emf2web.util.AbstractReferenceHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;

/**
 * @author Stefan Dirix
 *
 */
public class ReferenceHelperImpl extends AbstractReferenceHelper {

	private static final String ROOT = "#"; //$NON-NLS-1$
	private static final String SEPARATOR = "/"; //$NON-NLS-1$
	private static final String PROPERTIES = "properties"; //$NON-NLS-1$
	private static final String ITEMS = "items"; //$NON-NLS-1$

	@Override
	public String getStringRepresentation(VDomainModelReference reference) {
		return buildSchemaPath((VFeaturePathDomainModelReference) reference);
	}

	private String buildSchemaPath(VFeaturePathDomainModelReference reference) {
		final StringBuilder fragments = new StringBuilder(ROOT);
		for (final EReference ref : reference.getDomainModelEReferencePath()) {
			fragments.append(SEPARATOR);
			if (isArray(ref)) {
				fragments.append(ITEMS);
			} else {
				fragments.append(PROPERTIES);
			}
			fragments.append(SEPARATOR);
			fragments.append(ref.getName());
		}

		fragments.append(SEPARATOR);
		fragments.append(PROPERTIES);
		fragments.append(SEPARATOR);
		fragments.append(reference.getDomainModelEFeature().getName());
		return fragments.toString();
	}

	private static boolean isArray(EReference ref) {
		return ref.getUpperBound() > 1;
	}
}
