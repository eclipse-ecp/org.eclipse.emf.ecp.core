/*******************************************************************************
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.ui;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPPropertiesAware;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IActionFilter;

/**
 * @author Eike Stepper
 */
public final class PropertiesActionFilter implements IActionFilter {

	public boolean testAttribute(Object target, String name, String value) {
		ECPProperties properties = ((ECPPropertiesAware) target).getProperties();
		String propertyValue = properties.getValue(name);
		return value.equals(propertyValue);
	}

	/**
	 * @author Eike Stepper
	 */
	public static final class AdapterFactory implements IAdapterFactory {

		@SuppressWarnings("rawtypes")
		private static final Class[] ADAPTER_LIST = { IActionFilter.class };

		@SuppressWarnings("rawtypes")
		public Class[] getAdapterList() {
			return ADAPTER_LIST;
		}

		public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
			if (adaptableObject instanceof ECPPropertiesAware) {
				return new PropertiesActionFilter();
			}

			return null;
		}
	}
}
