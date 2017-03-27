/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.util.swt.rap;

import org.eclipse.emfforms.spi.swt.core.layout.EMFFormsSWTLayoutOptimizer;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.annotations.Component;

/**
 * EMFFormsSWTLayoutDirect does not cache anything and triggers the layout directly.
 *
 * @author Eugen Neufeld
 * @since 1.12
 *
 */
@Component
public class EMFFormsSWTLayoutDirect implements EMFFormsSWTLayoutOptimizer {
	@Override
	public synchronized void layout(Composite parent) {
		if (parent.isDisposed()) {
			return;
		}
		parent.layout(true, true);
	}
}
