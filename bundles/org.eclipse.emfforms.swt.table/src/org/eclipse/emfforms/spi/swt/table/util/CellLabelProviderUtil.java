/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.util;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.ModelReferenceHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.internal.swt.table.util.DMRCellLabelProvider;
import org.eclipse.jface.viewers.CellLabelProvider;

/**
 * Util class for creating {@link CellLabelProvider}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class CellLabelProviderUtil {

	private CellLabelProviderUtil() {
		// util
	}

	/**
	 * Creates a {@link CellLabelProvider}.
	 *
	 * @param domainModelReference the domain model reference specifying the value
	 * @param parent the domain object
	 * @return the provider
	 */
	public static CellLabelProvider createCellLabelProvider(VDomainModelReference domainModelReference,
		EObject parent) {
		return new DMRCellLabelProvider(domainModelReference);

	}

	/**
	 * Creates a {@link CellLabelProvider}.
	 *
	 * @param parent the domain object
	 * @param feature the feature of the value
	 * @param path the path of the value
	 * @return the provider
	 */
	public static CellLabelProvider createCellLabelProvider(EObject parent,
		EStructuralFeature feature, EReference... path) {
		return createCellLabelProvider(
			ModelReferenceHelper.createDomainModelReference(feature, Arrays.asList(path)), parent);
	}

}
