/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.reporting.test;

import java.util.Iterator;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewSWTRenderer;
import org.eclipse.emf.ecp.view.internal.swt.ECPRendererDescription;
import org.eclipse.emf.ecp.view.internal.swt.SWTRendererFactoryImpl;

class SWTTestRendererFactoryImpl extends SWTRendererFactoryImpl {

	public void clearRenderers() {
		getRendererDescriptors().clear();
	}

	public void registerRenderer(ECPRendererDescription descriptor) {
		getRendererDescriptors().add(descriptor);
	}

	// TODO: refactor
	public void replaceViewRenderer(ECPRendererDescription newDescription) {

		final Iterator<ECPRendererDescription> iterator = getRendererDescriptors().iterator();

		ECPRendererDescription oldRenderer = null;
		while (iterator.hasNext()) {
			final ECPRendererDescription next = iterator.next();
			if (next.getRenderer().equals(ViewSWTRenderer.class)) {
				oldRenderer = next;
				break;
			}
		}

		getRendererDescriptors().remove(oldRenderer);
		getRendererDescriptors().add(newDescription);
	}
}