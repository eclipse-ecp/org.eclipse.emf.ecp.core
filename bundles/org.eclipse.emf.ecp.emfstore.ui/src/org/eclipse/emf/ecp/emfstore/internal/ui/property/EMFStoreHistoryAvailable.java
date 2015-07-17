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
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;

/**
 * This tests whether a historyview is available either for a project or for an EObject.
 *
 * @author Tobias Verhoeven
 */
public class EMFStoreHistoryAvailable extends PropertyTester {

	/** {@inheritDoc} */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(receiver);
		if (project == null) {
			return false;
		}
		final ECPProvider provider = project.getProvider();
		if (provider != null) {
			return Boolean.valueOf(provider.getName().equals(EMFStoreProvider.NAME)).equals(expectedValue);
		}
		return false;
	}
}
