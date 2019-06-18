/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * alex - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.preview.common.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecp.view.model.preview.common.Preview;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.junit.Test;

/**
 * Unit tests for the {@link Preview}.
 */
public class Preview_Test {

	@Test(expected = Test.None.class /* no exception expected */)
	public void testRenderNullView() {
		final Composite parent = mock(Composite.class);
		when(parent.getChildren()).thenReturn(new Control[0]);
		final Preview preview = new Preview(parent);
		preview.render(null, null);
	}

}
