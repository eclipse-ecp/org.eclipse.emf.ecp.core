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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.data.EMFFormsSWTControlDataService;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

public class SWTDataElementIdHelper_ITest {

	private static final String FOO_BAR = "FooBar"; //$NON-NLS-1$
	private static final String CONTROL = "control"; //$NON-NLS-1$
	private static final String SUB_ID = "subId"; //$NON-NLS-1$
	private static final String UUID = "123456789"; //$NON-NLS-1$

	@Test
	public void setElementIdDataForVControl_noService() {
		/* setup */
		final Shell shell = new Shell();

		final VControl element = Mockito.mock(VControl.class);
		Mockito.doReturn(UUID).when(element).getUuid();

		final ViewModelContext context = Mockito.mock(ViewModelContext.class);

		/* act */
		SWTDataElementIdHelper.setElementIdDataForVControl(shell, element, context);

		/* assert */
		assertEquals("123456789#control", shell.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)); //$NON-NLS-1$
	}

	@Test
	public void setElementIdDataWithSubId_noService() {
		/* setup */
		final Shell shell = new Shell();

		final VControl element = Mockito.mock(VControl.class);
		Mockito.doReturn(UUID).when(element).getUuid();

		final ViewModelContext context = Mockito.mock(ViewModelContext.class);

		/* act */
		SWTDataElementIdHelper.setElementIdDataWithSubId(shell, element, SUB_ID, context);

		/* assert */
		assertEquals("123456789#subId", shell.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)); //$NON-NLS-1$
	}

	@Test
	public void setElementIdDataForVControl_service() {

		/* setup */
		final Shell shell = new Shell();

		final VControl element = Mockito.mock(VControl.class);
		Mockito.doReturn(UUID).when(element).getUuid();

		final ViewModelContext context = Mockito.mock(ViewModelContext.class);

		/* service */
		final EMFFormsSWTControlDataService dataService = Mockito.mock(EMFFormsSWTControlDataService.class);
		Mockito.doReturn(FOO_BAR).when(dataService).getData(element, shell, UUID, CONTROL);

		final Bundle bundle = FrameworkUtil.getBundle(SWTDataElementIdHelper_ITest.class);
		final ServiceRegistration<EMFFormsSWTControlDataService> service = bundle.getBundleContext()
			.registerService(EMFFormsSWTControlDataService.class, dataService, null);

		try {
			/* act */
			SWTDataElementIdHelper.setElementIdDataForVControl(shell, element, context);

			/* assert */
			assertEquals(FOO_BAR, shell.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		} finally {
			service.unregister();
		}
	}

	@Test
	public void setElementIdDataWithSubId_service() {

		/* setup */
		final Shell shell = new Shell();

		final VControl element = Mockito.mock(VControl.class);
		Mockito.doReturn(UUID).when(element).getUuid();

		final ViewModelContext context = Mockito.mock(ViewModelContext.class);

		/* service */
		final EMFFormsSWTControlDataService dataService = Mockito.mock(EMFFormsSWTControlDataService.class);
		Mockito.doReturn(FOO_BAR).when(dataService).getData(element, shell, UUID, SUB_ID);

		final Bundle bundle = FrameworkUtil.getBundle(SWTDataElementIdHelper_ITest.class);
		final ServiceRegistration<EMFFormsSWTControlDataService> service = bundle.getBundleContext()
			.registerService(EMFFormsSWTControlDataService.class, dataService, null);

		try {
			/* act */
			SWTDataElementIdHelper.setElementIdDataWithSubId(shell, element, SUB_ID, context);

			/* assert */
			assertEquals(FOO_BAR, shell.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		} finally {
			service.unregister();
		}
	}

}
