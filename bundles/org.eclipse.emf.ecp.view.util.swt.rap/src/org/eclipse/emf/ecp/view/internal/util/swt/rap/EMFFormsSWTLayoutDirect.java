/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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
