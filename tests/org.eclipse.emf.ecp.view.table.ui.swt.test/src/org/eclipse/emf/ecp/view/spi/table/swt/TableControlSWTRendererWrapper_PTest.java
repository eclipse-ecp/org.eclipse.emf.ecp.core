/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecp.view.internal.table.swt.DatabindingService;
import org.eclipse.emf.ecp.view.internal.table.swt.LabelService;
import org.eclipse.emf.ecp.view.internal.table.swt.TablePOJO;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonas
 *
 */
@RunWith(DatabindingClassRunner.class)
public class TableControlSWTRendererWrapper_PTest {

	private TableControlSWTRendererWrapper rendererWrapper;
	private SWTGridCell swtGridCell;
	private Composite composite;
	private ViewModelContext viewModelContext;
	private TablePOJO tablePOJOSpy;
	private VTableControl vTableControl;

	@Before
	public void init() {
		swtGridCell = mock(SWTGridCell.class);
		composite = new Shell();
		vTableControl = VTableFactory.eINSTANCE.createTableControl();
		final LabelService labelService = mock(LabelService.class);
		when(labelService.getLabelText(any(VTableControl.class))).thenReturn("");
		final DatabindingService databindingService = mock(DatabindingService.class);
		tablePOJOSpy = spy(new TablePOJO(vTableControl, labelService, databindingService));
		rendererWrapper = new TableControlSWTRendererWrapper(tablePOJOSpy);
		final VFeaturePathDomainModelReference domainModelReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		vTableControl.setDomainModelReference(domainModelReference);
		viewModelContext = mock(ViewModelContext.class);
	}

	@SuppressWarnings({})
	@Test
	public void testGetGridDescription() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		rendererWrapper.init(vTableControl, viewModelContext);
		final SWTGridDescription swtGridDescription = mock(SWTGridDescription.class);
		rendererWrapper.getGridDescription(swtGridDescription);
		verify(tablePOJOSpy, times(1)).getGridDescription(rendererWrapper);
	}

	@Test
	public void testRender() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		rendererWrapper.init(vTableControl, viewModelContext);
		rendererWrapper.renderControl(swtGridCell, composite);
		verify(tablePOJOSpy, times(1)).render(composite);
	}
}
