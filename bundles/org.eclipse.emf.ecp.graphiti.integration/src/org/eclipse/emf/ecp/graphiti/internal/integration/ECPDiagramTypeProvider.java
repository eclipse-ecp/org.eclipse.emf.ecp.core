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

import org.eclipse.emf.ecp.internal.graphiti.feature.ECPFeatureProvider;
import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class ECPDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public ECPDiagramTypeProvider() {
		super();
		setFeatureProvider(new ECPFeatureProvider(this));
	}

	@Override
	public boolean isAutoUpdateAtRuntimeWhenEditorIsSaved() {
		return true;
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders =
				new IToolBehaviorProvider[] { new ECPTutorialToolBehaviorProvider(
					this) };
		}
		return toolBehaviorProviders;
	}
}
