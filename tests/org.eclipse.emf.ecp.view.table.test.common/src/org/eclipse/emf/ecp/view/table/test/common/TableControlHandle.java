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
 * Jonas - initial API and implementation
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.test.common;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;

/**
 * @author Jonas
 *
 */
public class TableControlHandle {

	public TableControlHandle(VTableControl tableControl) {
		setTableControl(tableControl);
	}

	/**
	 * Some services expect everything to be in a view, such as the
	 * {@code EMFFormsMappingProviderTable}.
	 */
	private VView view;
	private VTableControl tableControl;
	private VDomainModelReference tableColumn1;
	private VDomainModelReference tableColumn2;

	/**
	 * @param tableColumn1
	 */
	public void addFirstTableColumn(VDomainModelReference tableColumn1) {
		setTableColumn1(tableColumn1);
		VTableDomainModelReference.class.cast(getTableControl().getDomainModelReference())
			.getColumnDomainModelReferences().add(tableColumn1);

	}

	/**
	 * @param tableColumn2
	 */
	public void addSecondTableColumn(VDomainModelReference tableColumn2) {
		setTableColumn2(tableColumn2);
		VTableDomainModelReference.class.cast(getTableControl().getDomainModelReference())
			.getColumnDomainModelReferences().add(tableColumn2);

	}

	/**
	 * @return the tableControl
	 */
	public VTableControl getTableControl() {
		return tableControl;
	}

	/**
	 * @param tableControl the tableControl to set
	 */
	public void setTableControl(VTableControl tableControl) {
		this.tableControl = tableControl;
	}

	/**
	 * @return the tableColumn1
	 */
	public VDomainModelReference getTableColumn1() {
		return tableColumn1;
	}

	/**
	 * @param tableColumn1 the tableColumn1 to set
	 */
	public void setTableColumn1(VDomainModelReference tableColumn1) {
		this.tableColumn1 = tableColumn1;
	}

	/**
	 * @return the tableColumn2
	 */
	public VDomainModelReference getTableColumn2() {
		return tableColumn2;
	}

	/**
	 * @param tableColumn2 the tableColumn2 to set
	 */
	public void setTableColumn2(VDomainModelReference tableColumn2) {
		this.tableColumn2 = tableColumn2;
	}

	/**
	 * Obtain a view containing the table, for tests that need it.
	 *
	 * @return a view containing the table
	 *
	 * @since 1.22
	 */
	public VView getView() {
		if (view == null && getTableControl() != null) {
			view = VViewFactory.eINSTANCE.createView();

			// Infer the root EClass from the table's DMR
			final VTableDomainModelReference tdmr = (VTableDomainModelReference) getTableControl()
				.getDomainModelReference();
			if (tdmr.getDomainModelEFeature() != null) {
				view.setRootEClass(tdmr.getDomainModelEFeature().getEContainingClass());
			} else if (tdmr.getDomainModelReference() instanceof VFeaturePathDomainModelReference) {
				final VFeaturePathDomainModelReference fpdmr = (VFeaturePathDomainModelReference) tdmr
					.getDomainModelReference();
				if (!fpdmr.getDomainModelEReferencePath().isEmpty()) {
					view.setRootEClass(fpdmr.getDomainModelEReferencePath().get(0).getEContainingClass());
				} else {
					view.setRootEClass(fpdmr.getDomainModelEFeature().getEContainingClass());
				}
			}

			view.getChildren().add(getTableControl());
		}

		return view;
	}
}
