/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.legacy;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

public class TestLocalViewModelService2 implements ITestViewModelService {

	public TestLocalViewModelService2() {
		// intentionally left empty
	}

	@Override
	public void instantiate(ViewModelContext context) {
		context.putContextValue(getClass().getSimpleName(), getClass());
	}

	@Override
	public void dispose() {
		// intentionally left empty
	}

	@Override
	public int getPriority() {
		return 0;
	}

}
