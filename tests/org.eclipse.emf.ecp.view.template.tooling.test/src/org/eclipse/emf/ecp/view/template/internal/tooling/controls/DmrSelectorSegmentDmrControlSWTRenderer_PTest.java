/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.test.common.MultiTryTestRule;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferenceFactory;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link DmrSelectorSegmentDmrControlSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(DatabindingClassRunner.class)
public class DmrSelectorSegmentDmrControlSWTRenderer_PTest {

	// Allow two tries because Jenkins CI might fail on the first run of the first test case
	@Rule
	public final MultiTryTestRule multiTryRule = new MultiTryTestRule(2);

	private DmrSelectorSegmentDmrControlSWTRenderer renderer;
	private VTDomainModelReferenceSelector domainObject;

	private EMFFormsDatabindingEMF databinding;

	@Before
	public void setUp() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final VControl vControl = mock(VControl.class);

		domainObject = VTDomainmodelreferenceFactory.eINSTANCE.createDomainModelReferenceSelector();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(
			VTDomainmodelreferencePackage.Literals.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE.getName());
		dmr.getSegments().add(segment);

		when(vControl.getDomainModelReference()).thenReturn(dmr);
		final ViewModelContext viewContext = mock(ViewModelContext.class);
		when(viewContext.getDomainModel()).thenReturn(domainObject);
		final ReportService reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabindingEMF.class);

		// Add domain object to resource with editing domain
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(domainObject);

		renderer = new DmrSelectorSegmentDmrControlSWTRenderer(vControl, viewContext, reportService, databinding);

		// Render
		final Shell shell = new Shell(Display.getDefault());
		renderer.init();
		renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);
	}

	/** Test that the dmr and root EClass are set with a proper compound command. */
	@Test
	public void setDmrAndRootEClass() {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(domainObject);
		editingDomain.getCommandStack().flush();

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final EClass rootEClass = EcoreFactory.eINSTANCE.createEClass();

		renderer.setDmrAndRootEClass(domainObject, dmr, rootEClass);

		assertSame(dmr, domainObject.getDomainModelReference());
		assertSame(rootEClass, domainObject.getRootEClass());

		assertTrue(editingDomain.getCommandStack().canUndo());
		editingDomain.getCommandStack().undo();

		assertNull(domainObject.getDomainModelReference());
		assertNull(domainObject.getRootEClass());

		assertFalse(editingDomain.getCommandStack().canUndo());
		assertTrue(editingDomain.getCommandStack().canRedo());
	}
}
