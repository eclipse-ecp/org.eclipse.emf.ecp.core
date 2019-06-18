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
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.graphiti.internal.integration;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.graphiti.ECPGraphitiDiagramEditor;

public class GenericECPGraphitiDiagramEditor extends ECPGraphitiDiagramEditor {
	public static final String EDITOR_ID = "org.eclipse.emf.ecp.graphiti.editor";

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void dispose() {
		doSave(new NullProgressMonitor());
		super.dispose();
	}

}
