/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.cdo.internal.core;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.cdo.workspace.CDOWorkspace;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.spi.core.InternalProject;

/**
 * @author Eike Stepper
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ECPProjectAdapterFactory implements IAdapterFactory {
	private static final Class[] CLASSES = { CDOWorkspace.class };

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	@Override
	public Class[] getAdapterList() {
		return CLASSES;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object getAdapter(Object adaptable, Class adapterType) {
		return adapt(adaptable, adapterType);
	}

	/**
	 * Adapt the given adaptable to the given type.
	 *
	 * @param adaptable an adaptable
	 * @param adapterType the target type
	 * @param <T> the type of the adapter
	 * @return an object of type <T> if supported or null
	 */
	public static <T> T adapt(Object adaptable, Class<T> adapterType) {
		if (adapterType == CLASSES[0]) {
			if (adaptable instanceof ECPProject) {
				final ECPProject project = (ECPProject) adaptable;
				if (project.isOpen() && project.getProvider().getName().equals(CDOProvider.NAME)) {
					final CDOProjectData data = CDOProvider.getProjectData((InternalProject) project);
					return (T) data.getWorkspace();
				}
			}
		}

		return null;
	}
}
