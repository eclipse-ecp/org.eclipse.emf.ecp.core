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
 * Description for table configurations.
 *
 * @author Eugen Neufeld
 *
 */
public class TableControlConfiguration {

	private boolean addRemoveDisabled;
	private final List<TableColumnConfiguration> columns = new ArrayList<TableColumnConfiguration>();

	/**
	 * The list of column configurations to use for showing columns in the table.
	 *
	 * @return the List of {@link TableColumnConfiguration}
	 */
	public List<TableColumnConfiguration> getColumns() {
		return columns;
	}

	/**
	 * Whether the add and remove buttons should be active.
	 *
	 * @return true if the buttons should be disabled, false other wise
	 */
	public boolean isAddRemoveDisabled() {
		return addRemoveDisabled;
	}

	/**
	 * Sets whether the add and remove buttons should be active.
	 *
	 * @param addRemoveDisabled true if the buttons should be disabled, false otherwise
	 */
	public void setAddRemoveDisabled(boolean addRemoveDisabled) {
		this.addRemoveDisabled = addRemoveDisabled;
	}

}
