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
package org.eclipse.emf.ecp.internal.graphiti.feature;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

public class ECPFeatureProvider extends DefaultFeatureProviderWithPatterns {

	public ECPFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new EObjectPattern());
		addConnectionPattern(new ContainmentPattern());
		addConnectionPattern(new ReferencePattern());
	}

}
