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
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;

/**
 * This tests whether an element is shared.
 *
 * @author Tobias Verhoeven
 */
public class EMFStoreElementIsShared extends PropertyTester {

	/** {@inheritDoc} */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final InternalProject project = (InternalProject) ECPUtil.getECPProjectManager().getProject(receiver);
		if (project != null && project.getProvider().getName().equals(EMFStoreProvider.NAME)) {
			final ESLocalProject ps = EMFStoreProvider.INSTANCE.getProjectSpace(project);
			if (ps != null) {
				return Boolean.valueOf(ps.isShared()).equals(expectedValue);
			}
		}
		return false;
	}
}
