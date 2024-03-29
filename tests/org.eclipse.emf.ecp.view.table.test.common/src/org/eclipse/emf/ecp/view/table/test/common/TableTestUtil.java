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
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.test.common;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;

/**
 * Utility methods for table related test cases.
 *
 * @author Lucas Koehler
 *
 */
public final class TableTestUtil {

	/**
	 * Hide constructor for utility class.
	 */
	private TableTestUtil() {
		// do nothing
	}

	/**
	 * Creates a table column DMR for the given {@link EStructuralFeature}.
	 *
	 * @param feature The target feature that the column DMR references
	 * @return The created DMR
	 */
	public static VDomainModelReference createTableColumn(EStructuralFeature feature) {
		final VFeaturePathDomainModelReference reference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		reference.setDomainModelEFeature(feature);
		return reference;
	}

	/**
	 * Create a {@link TableControlHandle} which contains an initialized table control.
	 *
	 * @return The initialized {@link TableControlHandle}
	 */
	public static TableControlHandle createInitializedTableWithoutTableColumns() {
		return createInitializedTableWithoutTableColumns(EcorePackage.Literals.ECLASS__ESUPER_TYPES);
	}

	/**
	 * Create a {@link TableControlHandle} which contains an initialized table control.
	 *
	 * @param reference the reference from which to get the rows of the table
	 * @return The initialized {@link TableControlHandle}
	 */
	public static TableControlHandle createInitializedTableWithoutTableColumns(EReference reference) {
		final TableControlHandle tableControlHandle = createUninitializedTableWithoutColumns();
		final VFeaturePathDomainModelReference domainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		domainModelReference.setDomainModelEFeature(reference);
		tableControlHandle.getTableControl().setDomainModelReference(domainModelReference);

		return tableControlHandle;
	}

	/**
	 * Create a {@link TableControlHandle} whose table control is not initialized
	 *
	 * @return The uninitialized {@link TableControlHandle}
	 */
	public static TableControlHandle createUninitializedTableWithoutColumns() {
		final VTableControl tableControl = createTableControl();
		return new TableControlHandle(tableControl);
	}

	/**
	 * Create a {@link VTableControl} with an empty table DMR.
	 *
	 * @return The {@link VTableControl}
	 */
	public static VTableControl createTableControl() {
		final VTableControl tc = VTableFactory.eINSTANCE.createTableControl();
		tc.setDomainModelReference(VTableFactory.eINSTANCE.createTableDomainModelReference());
		tc.setUuid("UUID"); //$NON-NLS-1$
		return tc;
	}
}
