/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugen Neufeld
 * 
 */
public class TableControlConfiguration {

	private boolean addRemoveDisabled;
	private final List<TableColumnConfiguration> columns = new ArrayList<TableColumnConfiguration>();

	public List<TableColumnConfiguration> getColumns() {
		return columns;
	}

	public boolean isAddRemoveDisabled() {
		return addRemoveDisabled;
	}

	public void setAddRemoveDisabled(boolean addRemoveDisabled) {
		this.addRemoveDisabled = addRemoveDisabled;
	}

}
