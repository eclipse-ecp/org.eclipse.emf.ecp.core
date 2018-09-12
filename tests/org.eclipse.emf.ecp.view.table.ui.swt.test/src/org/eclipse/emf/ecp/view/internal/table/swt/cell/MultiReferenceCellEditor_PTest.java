/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MultiReferenceCellEditor_PTest {

	private Shell shell;
	private MultiReferenceCellEditor cellEditor;

	@Before
	public void setUp() throws Exception {
		shell = new Shell(Display.getCurrent());
		cellEditor = new MultiReferenceCellEditor(shell);
	}

	@After
	public void after() {
		cellEditor.dispose();
		shell.dispose();
	}

	@Test
	public void testGetFormatedString() {
		cellEditor.instantiate(null, mock(ViewModelContext.class));

		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		final EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.setName("1");
		ePackage.getEClassifiers().add(eClass1);
		final EClass eClass2 = EcoreFactory.eINSTANCE.createEClass();
		eClass2.setName("2");
		ePackage.getEClassifiers().add(eClass2);

		resource.getContents().add(ePackage);

		assertEquals("1, 2", cellEditor.getFormatedString(ePackage.getEClassifiers()));
	}

	@Test
	public void testGetImage() {
		assertNull(cellEditor.getImage(null));
	}

	@Test
	public void testGetColumnWidthWeight() {
		assertEquals(100, cellEditor.getColumnWidthWeight());
	}

	@Test
	public void testGetTargetToModelStrategy() {
		assertNull(cellEditor.getTargetToModelStrategy(null));
	}

	@Test
	public void testGetModelToTargetStrategy() {
		assertNull(cellEditor.getModelToTargetStrategy(null));
	}

	@Test
	public void testGetMinWidth() {
		assertEquals(50, cellEditor.getMinWidth());
	}

	@Test
	public void testCreateControlComposite() {
		assertNull(cellEditor.createControl(null));
	}

	@Test
	public void testDoGetValue() {
		assertNull(cellEditor.doGetValue());
	}

	@Test
	public void testGetValueProperty() {
		assertNotNull(cellEditor.getValueProperty());
	}

	@Test
	public void testChangeTracking() {

		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$

		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		final EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.setName("1");
		ePackage.getEClassifiers().add(eClass1);
		final EAttribute eAttribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute1.setName("Att_1");
		final EAttribute eAttribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute2.setName("Att_2");
		eClass1.getEStructuralFeatures().add(eAttribute1);
		eClass1.getEStructuralFeatures().add(eAttribute2);

		resource.getContents().add(ePackage);

		final ViewModelContext vmc = mock(ViewModelContext.class);

		final List<ModelChangeListener> listeners = new ArrayList<ModelChangeListener>();
		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				listeners.add((ModelChangeListener) invocation.getArguments()[0]);
				return null;
			}
		}).when(vmc).registerDomainChangeListener(any(ModelChangeListener.class));
		Mockito.inOrder(vmc).verify(vmc, Mockito.times(0)).registerDomainChangeListener(any(ModelChangeListener.class));
		cellEditor.instantiate(EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), vmc);
		final ModelChangeNotification notification = mock(ModelChangeNotification.class);
		when(notification.getNotifier()).thenReturn(eAttribute1);
		listeners.get(0).notifyChange(notification);
		Mockito.inOrder(vmc).verify(vmc, Mockito.times(1)).registerDomainChangeListener(any(ModelChangeListener.class));
		// add table viewer
		final TableViewer tableViewer = mock(TableViewer.class);
		cellEditor.setTableViewer(tableViewer);
		listeners.get(0).notifyChange(notification);
		Mockito.inOrder(tableViewer).verify(tableViewer, Mockito.times(0)).update(any(Object.class),
			any(String[].class));
		// add table feature
		cellEditor.setTableFeature(EcorePackage.eINSTANCE.getEPackage_EClassifiers());
		listeners.get(0).notifyChange(notification);
		Mockito.inOrder(tableViewer).verify(tableViewer, Mockito.times(1)).update(same(eClass1),
			any(String[].class));
	}
}
