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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.generator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * Helper class to generate {@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration
 * VTableColumnConfiguration}s for a {@link VTableControl}.
 *
 * @author jfaltermeier
 *
 */
public final class TableColumnGenerator {

	private TableColumnGenerator() {
	}

	/**
	 * Generates columns for every {@link EAttribute} of the given {@link EClass} and adds them to the
	 * {@link VTableControl}.
	 *
	 * @param clazz the EClass to use
	 * @param vTableControl the table control to use
	 */
	public static void generateColumns(EClass clazz, VTableControl vTableControl) {
		final EList<EAttribute> attributes = clazz.getEAllAttributes();
		for (final EAttribute a : attributes) {
			addColumn(a, vTableControl);
		}
	}

	/**
	 * Generates a column for the given {@link EAttribute} and adds it to the {@link VTableControl}.
	 *
	 * @param attribute the attribute to use
	 * @param vTableControl the table control to use
	 */
	public static void addColumn(EAttribute attribute, VTableControl vTableControl) {
		final VFeaturePathDomainModelReference column = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		column.setDomainModelEFeature(attribute);

		final VDomainModelReference dmr = vTableControl.getDomainModelReference();
		if (dmr.getSegments().size() > 0) {
			final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) dmr
				.getSegments().get(dmr.getSegments().size() - 1);
			multiSegment.getChildDomainModelReferences().add(column);
		} else {
			VTableDomainModelReference.class.cast(dmr).getColumnDomainModelReferences().add(column);
		}
	}

}
