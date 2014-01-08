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

import org.eclipse.emf.ecore.EAttribute;

public class TableColumnConfiguration {
	private final boolean readOnly;
	private final EAttribute columnAttribute;

	/**
	 * @param readOnly whether the column is read-only
	 * @param columnAttribute the {@link EAttribute} to be displayed in this column
	 */
	public TableColumnConfiguration(boolean readOnly, EAttribute columnAttribute) {
		super();
		this.readOnly = readOnly;
		this.columnAttribute = columnAttribute;
	}

	/**
	 * 
	 * @return if the column is read-only
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	public EAttribute getColumnAttribute() {
		return columnAttribute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (columnAttribute == null ? 0 : columnAttribute.hashCode());
		result = prime * result + (readOnly ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TableColumnConfiguration other = (TableColumnConfiguration) obj;
		if (columnAttribute == null) {
			if (other.columnAttribute != null) {
				return false;
			}
		} else if (!columnAttribute.equals(other.columnAttribute)) {
			return false;
		}
		if (readOnly != other.readOnly) {
			return false;
		}
		return true;
	}

}