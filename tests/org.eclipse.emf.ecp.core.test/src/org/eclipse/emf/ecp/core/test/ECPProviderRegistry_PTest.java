/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.core.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.junit.Test;
import org.mockito.Mockito;

public class ECPProviderRegistry_PTest extends AbstractTest {

	// @Test
	// public void getProviderByItselfTest(){
	// ECPProvider ecpProvider=ECPUtil.getECPProviderRegistry().getProvider(getProvider());
	// assertEquals(ecpProvider,getProvider());
	// }
	//
	@Test
	public void removeAddProviderTest() {
		assertEquals(1, ECPUtil.getECPProviderRegistry().getProviders().size());
		final InternalProvider mocked = Mockito.mock(InternalProvider.class);
		Mockito.when(mocked.getName()).thenReturn("Mocked");
		ECPUtil.getECPProviderRegistry().addProvider(mocked);
		assertEquals(2, ECPUtil.getECPProviderRegistry().getProviders().size());
		ECPUtil.getECPProviderRegistry().removeProvider("Mocked");
		assertEquals(1, ECPUtil.getECPProviderRegistry().getProviders().size());
	}
}
