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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Before;
import org.junit.Test;

public class MoveRowUpAction_Test {

	private MoveRowUpAction action;
	private AbstractTableViewer viewer;
	private EPackage ePackage;
	private EClass eClass1;
	private EClass eClass2;

	@Before
	public void setUp() throws Exception {
		ePackage = EcoreFactory.eINSTANCE.createEPackage();
		eClass1 = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(eClass1);
		eClass2 = EcoreFactory.eINSTANCE.createEClass();
		ePackage.getEClassifiers().add(eClass2);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(ePackage);

		final TableRendererViewerActionContext actionContext = mock(TableRendererViewerActionContext.class);
		when(actionContext.getEditingDomain()).thenReturn(domain);
		when(actionContext.getSetting())
			.thenReturn(((InternalEObject) ePackage).eSetting(EcorePackage.eINSTANCE.getEPackage_EClassifiers()));
		action = spy(new MoveRowUpAction(actionContext));
		viewer = mock(AbstractTableViewer.class);
		when(actionContext.getViewer()).thenReturn(viewer);
		final IObservableList<?> input = mock(IObservableList.class);
		when(input.size()).thenReturn(2);
		when(viewer.getInput()).thenReturn(input);

		final VTableControl tableControl = mock(VTableControl.class);
		when(actionContext.getVElement()).thenReturn(tableControl);
		when(tableControl.isEffectivelyReadonly()).thenReturn(false);
		when(tableControl.isEffectivelyEnabled()).thenReturn(true);
	}

	@Test
	public void testCanExecute() {
		// test that the top most element cannot be moved up
		when(viewer.getSelection()).thenReturn(new StructuredSelection(eClass1));
		assertFalse(action.canExecute());
		// test that the last element can be moved up
		when(viewer.getSelection()).thenReturn(new StructuredSelection(eClass2));
		assertTrue(action.canExecute());
	}

	@Test
	public void testExecute() {

		// test that the top most element cannot be moved up
		when(viewer.getSelection()).thenReturn(new StructuredSelection(eClass2));
		action.execute();

		assertEquals(eClass2, ePackage.getEClassifiers().get(0));
		verify(viewer).refresh();
	}

}
