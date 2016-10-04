/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * @author Jonas
 *
 */
public class TableControlHandle {

	public TableControlHandle(VTableControl tableControl) {
		setTableControl(tableControl);
	}

	private VTableControl tableControl;
	private VDomainModelReference tableColumn1;
	private VDomainModelReference tableColumn2;

	/**
	 * @param tableColumn1
	 */
	public void addFirstTableColumn(VDomainModelReference tableColumn1) {
		setTableColumn1(tableColumn1);
		getMultiSegmentFromDMR(getTableControl().getDomainModelReference())
			.getChildDomainModelReferences().add(tableColumn1);

	}

	/**
	 * @param tableColumn2
	 */
	public void addSecondTableColumn(VDomainModelReference tableColumn2) {
		setTableColumn2(tableColumn2);
		getMultiSegmentFromDMR(getTableControl().getDomainModelReference())
			.getChildDomainModelReferences().add(tableColumn2);

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
	 *
	 * @param dmr
	 * @return The multi segment of the DMR, throws an {@link IllegalArgumentException} if it is not present
	 */
	private VMultiDomainModelReferenceSegment getMultiSegmentFromDMR(VDomainModelReference dmr) {
		if (dmr.getSegments().isEmpty()) {
			throw new IllegalStateException("The dmr does not contain any segments");
		}
		final VDomainModelReferenceSegment segment = dmr.getSegments().get(dmr.getSegments().size() - 1);
		if (VMultiDomainModelReferenceSegment.class.isInstance(segment)) {
			return (VMultiDomainModelReferenceSegment) segment;
		}
		throw new IllegalStateException("The DMR's last segment was not a multi segment.");
	}
}
