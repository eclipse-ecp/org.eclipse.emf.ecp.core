/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * @author Eike Stepper
 */
public class PropertiesLabelProvider extends LabelProvider implements ITableLabelProvider {
	private static final Image PROPERTY = Activator.getImage("icons/property_obj.gif"); //$NON-NLS-1$

	public PropertiesLabelProvider() {
	}

	/** {@inheritDoc} */
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
			}
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Map.Entry) {
			switch (columnIndex) {
			case 0:
				return PROPERTY;
			}
		}

		return null;
	}
}
