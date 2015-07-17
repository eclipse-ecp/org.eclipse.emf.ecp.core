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
package org.eclipse.emf.ecp.internal.core.util;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;

/**
 * Tests weather a project is dirty.
 *
 * @author Tobias Verhoeven
 */
public class ECPDirtyTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final ECPProject project = ECPUtil.getECPProjectManager().getProject(receiver);

		if (project == null) {
			return false;
		}

		return Boolean.valueOf(project.hasDirtyContents()).equals(expectedValue);
	}
}
