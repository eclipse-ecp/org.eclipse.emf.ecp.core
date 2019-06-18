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

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class ECPImageProvider extends AbstractImageProvider {

	@Override
	protected void addAvailableImages() {
		addImageFilePath("addEObject", "icons/add.png");
		addImageFilePath("containment", "icons/connect.png");
		addImageFilePath("reference", "icons/link.png");
	}

}
