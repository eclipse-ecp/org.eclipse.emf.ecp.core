/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.emf2web.util.AbstractReferenceHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;

/**
 * @author Stefan Dirix
 *
 */
public class ReferenceHelperImpl extends AbstractReferenceHelper {

	private static final String ROOT = "#"; //$NON-NLS-1$
	private static final String SEPARATOR = "/"; //$NON-NLS-1$
	private static final String PROPERTIES = "properties"; //$NON-NLS-1$
	private static final String ITEMS = "items"; //$NON-NLS-1$
	private final Set<String> ecorePaths = new LinkedHashSet<String>();

	@Override
	public String getStringRepresentation(VDomainModelReference reference) {
		if (VTableDomainModelReference.class.isInstance(reference)) {
			return buildSchemaPath((VTableDomainModelReference) reference);
		}
		return buildSchemaPath((VFeaturePathDomainModelReference) reference);
	}

	private String buildSchemaPath(VTableDomainModelReference reference) {
		if (reference.getDomainModelReference() == null) {
			return buildSchemaPath((VFeaturePathDomainModelReference) reference);
		}
		return getStringRepresentation(reference.getDomainModelReference());
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

	/**
	 * Allows to add an ecore path to the current view model.
	 *
	 * @param ecorePath The path to the ecore of the current view model
	 */
	public void addEcorePath(String ecorePath) {
		ecorePaths.add(ecorePath);
	}

	@Override
	protected Set<String> getEcorePaths() {
		return ecorePaths;
	}
}
