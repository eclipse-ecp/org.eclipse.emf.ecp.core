/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.property;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;

/**
 * Tests weather a project is dirty.
 *
 * @author Tobias Verhoeven
 */
public class EMFStoreProjectIsDirtyTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final InternalProject ip = (InternalProject) receiver;
		if (!ip.getProvider().getName().equals(EMFStoreProvider.NAME)) {
			return false;
		}
		final ESLocalProject ps = EMFStoreProvider.INSTANCE.getProjectSpace(ip);
		if (ps != null) {
			return Boolean.valueOf(ps.hasUncommitedChanges()).equals(expectedValue);
		}
		return false;
	}
}
