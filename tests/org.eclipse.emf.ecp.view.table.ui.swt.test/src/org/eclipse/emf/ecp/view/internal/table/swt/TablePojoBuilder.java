/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;

/**
 * @author jfaltermeier
 *
 */
public class TablePojoBuilder {

	private VTableControl tableControl;
	private LabelService labelService;

	private TooltipModifier tooltipModifier;
	private DatabindingService databindingService;

	private TablePojoBuilder() {
		tableControl = mock(VTableControl.class);
		labelService = mock(LabelService.class);
		databindingService = mock(DatabindingService.class);
		tooltipModifier = mock(TooltipModifier.class);
	}

	public static TablePojoBuilder init() {
		return new TablePojoBuilder();
	}

	public TablePojoBuilder withTableControl(VTableControl tableControl) {
		this.tableControl = tableControl;
		return this;
	}

	public TablePojoBuilder withLabelService(LabelService labelService) {
		this.labelService = labelService;
		return this;
	}

	public TablePojoBuilder withDatabindingService(DatabindingService databindingService) {
		this.databindingService = databindingService;
		return this;
	}

	public TablePojoBuilder withTooltipModifier(TooltipModifier tooltipModifier) {
		this.tooltipModifier = tooltipModifier;
		return this;
	}

	public TablePOJO build() {
		return new TablePOJO(tableControl, labelService, tooltipModifier, databindingService);
	}

}
