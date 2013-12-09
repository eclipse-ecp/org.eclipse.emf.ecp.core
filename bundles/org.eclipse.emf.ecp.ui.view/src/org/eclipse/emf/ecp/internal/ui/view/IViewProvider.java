/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;

public interface IViewProvider {

	int NOT_APPLICABLE = -1;

	int canRender(EObject eObject);

	VView generate(EObject eObject);
}
