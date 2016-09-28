/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;

/**
 * {@link DiagnosticCache} for ecore.
 */
public class EcoreDiagnosticCache extends DiagnosticCache {

	/**
	 * @param input the input
	 */
	public EcoreDiagnosticCache(Notifier input) {
		super(input);
	}

	@Override
	protected void updateCache(EObject element, DiagnosticCache cache) {
		super.updateCache(element, cache);
		final EObject parent = element.eContainer();
		if (parent != null) {
			updateCache(parent, cache);
		}
	}

	@Override
	protected void updateCacheWithoutRefresh(EObject element, DiagnosticCache cache) {
		super.updateCacheWithoutRefresh(element, cache);
		final EObject parent = element.eContainer();
		if (parent != null) {
			updateCacheWithoutRefresh(parent, cache);
		}
	}

}
