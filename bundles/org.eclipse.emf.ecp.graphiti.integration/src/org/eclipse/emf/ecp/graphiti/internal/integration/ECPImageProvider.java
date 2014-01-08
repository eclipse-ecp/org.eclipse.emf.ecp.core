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
