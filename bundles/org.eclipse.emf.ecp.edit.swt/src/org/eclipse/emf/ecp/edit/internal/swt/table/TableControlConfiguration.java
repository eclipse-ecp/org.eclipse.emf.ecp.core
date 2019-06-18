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
 * Eugen Neufeld - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Description for table configurations.
 *
 * @deprecated Used in the deprecated TableControl
 * @author Eugen Neufeld
 *
 */
@Deprecated
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
