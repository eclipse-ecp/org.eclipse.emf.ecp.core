/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Neil Mackenzie - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.workspace.internal.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;

/**
 * Test weather an undo is possible on a project.
 *
 * @author Neil Mackenzie
 */
public class WorkspaceCanUndoTester extends PropertyTester {

	/** {@inheritDoc} **/
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final InternalProject project = (InternalProject) receiver;
		if (!WorkspaceProvider.class.isInstance(project.getProvider())) {
			return false;
		}
		return Boolean.valueOf(project.getEditingDomain().getCommandStack().canUndo()).equals(expectedValue);

	}

}
