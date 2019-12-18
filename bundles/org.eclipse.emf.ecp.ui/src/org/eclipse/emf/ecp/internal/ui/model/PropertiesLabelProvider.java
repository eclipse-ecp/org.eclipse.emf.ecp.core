/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import java.util.Map;

import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A {@link LabelProvider} for properties.
 * 
 * @author Eike Stepper
 */
public class PropertiesLabelProvider extends LabelProvider implements ITableLabelProvider {
	private static final Image PROPERTY = Activator.getImage("icons/property_obj.gif"); //$NON-NLS-1$

	/**
	 * Default constructor.
	 */
	public PropertiesLabelProvider() {
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Map.Entry) {
			@SuppressWarnings("unchecked")
			final Map.Entry<String, String> entry = (Map.Entry<String, String>) element;
			switch (columnIndex) {
			case 0:
				return entry.getKey();

			case 1:
				return entry.getValue();
			default:
				return null;
			}
		}

		return null;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Map.Entry) {
			switch (columnIndex) {
			case 0:
				return PROPERTY;
			default:
				return null;
			}
		}

		return null;
	}
}
