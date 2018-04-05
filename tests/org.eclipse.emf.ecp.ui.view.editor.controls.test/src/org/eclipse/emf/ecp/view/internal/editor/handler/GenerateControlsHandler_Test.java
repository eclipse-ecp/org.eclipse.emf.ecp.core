/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecp.view.internal.editor.handler.GenerateControlsHandler.CallbackFeatureSupplier;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * JUnit tests for {@link GenerateControlsHandler}.
 *
 * @author Lucas Koehler
 *
 */
public class GenerateControlsHandler_Test {
	private GenerateControlsHandler controlsHandler;

	@Before
	public void setUp() {
		controlsHandler = new GenerateControlsHandler();
	}

	@Test
	public void testShouldShow() {
		final VElement element = mock(VElement.class);
		final VControl control = mock(VControl.class);
		final VContainer container = mock(VContainer.class);
		final VContainedContainer containedContainer = mock(VContainedContainer.class);
		final VView view = mock(VView.class);

		assertFalse(controlsHandler.shouldShow(null));
		assertFalse(controlsHandler.shouldShow(element));
		assertFalse(controlsHandler.shouldShow(control));
		assertTrue(controlsHandler.shouldShow(container));
		assertTrue(controlsHandler.shouldShow(containedContainer));
		assertTrue(controlsHandler.shouldShow(view));
	}

	@Test
	public void testInnerExecuteOnView() {
		final EStructuralFeature suppliedFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		final CallbackFeatureSupplier featureSupplier = new CallbackFeatureSupplier() {

			@Override
			public Set<EStructuralFeature> get(VView view) {
				return Collections.singleton(suppliedFeature);
			}
		};

		final Resource resource = getResource();
		final VView vView = mock(VView.class);
		final EList<Adapter> adapters = new BasicEList<Adapter>();
		when(vView.eAdapters()).thenReturn(adapters);
		when(vView.eResource()).thenReturn(resource);
		when(vView.getRootEClass()).thenReturn(EcorePackage.eINSTANCE.getEClass());
		final EList<VContainedElement> children = new BasicEList<VContainedElement>();
		when(vView.getChildren()).thenReturn(children);

		controlsHandler.execute(vView, featureSupplier);

		Mockito.inOrder(vView).verify(vView, Mockito.times(1)).getChildren();
		assertEquals(1, children.size());
		assertTrue(VControl.class.isInstance(children.get(0)));
		final VControl control = VControl.class.cast(children.get(0));
		assertTrue(VFeaturePathDomainModelReference.class.isInstance(control.getDomainModelReference()));
		final VFeaturePathDomainModelReference domainModelReference = VFeaturePathDomainModelReference.class
			.cast(control.getDomainModelReference());
		assertSame(suppliedFeature, domainModelReference.getDomainModelEFeature());
		assertTrue(domainModelReference.getDomainModelEReferencePath().isEmpty());
		cleanup(resource);
	}

	@Test
	public void testInnerExecuteOnViewNoFeatures() {
		final CallbackFeatureSupplier featureSupplier = new CallbackFeatureSupplier() {

			@Override
			public Set<EStructuralFeature> get(VView view) {
				return Collections.emptySet();
			}
		};

		final VView vView = mock(VView.class);

		controlsHandler.execute(vView, featureSupplier);

		Mockito.verify(vView, Mockito.never()).getChildren();
	}

	@Test
	public void testInnerExecuteOnElement() {

		final VContainedElement vContainedElement = mock(VContainedElement.class);

		controlsHandler.execute(vContainedElement, null);

		Mockito.verifyZeroInteractions(vContainedElement);

	}

	@Test
	public void testInnerExecuteOnContainerNoParent() {

		final VContainer vContainedElement = mock(VContainer.class);

		controlsHandler.execute(vContainedElement, null);

		Mockito.verify(vContainedElement, Mockito.never()).getChildren();

	}

	@Test
	public void testInnerExecuteOnContainer() {

		final EStructuralFeature suppliedFeature = EcorePackage.eINSTANCE.getENamedElement_Name();
		final CallbackFeatureSupplier featureSupplier = new CallbackFeatureSupplier() {

			@Override
			public Set<EStructuralFeature> get(VView view) {
				return Collections.singleton(suppliedFeature);
			}
		};

		final Resource resource = getResource();
		final VView vView = mock(VView.class);
		final EList<Adapter> adapters = new BasicEList<Adapter>();
		when(vView.eAdapters()).thenReturn(adapters);
		when(vView.eResource()).thenReturn(resource);
		when(vView.getRootEClass()).thenReturn(EcorePackage.eINSTANCE.getEClass());
		final EList<VContainedElement> viewChildren = new BasicEList<VContainedElement>();
		when(vView.getChildren()).thenReturn(viewChildren);

		final VContainer vContainer = mock(VContainer.class);
		final EList<VContainedElement> children = new BasicEList<VContainedElement>();
		when(vContainer.getChildren()).thenReturn(children);
		when(vContainer.eContainer()).thenReturn(vView);

		controlsHandler.execute(vContainer, featureSupplier);

		Mockito.inOrder(vContainer).verify(vContainer, Mockito.times(1)).getChildren();
		assertEquals(1, children.size());
		assertTrue(VControl.class.isInstance(children.get(0)));
		final VControl control = VControl.class.cast(children.get(0));
		assertTrue(VFeaturePathDomainModelReference.class.isInstance(control.getDomainModelReference()));
		final VFeaturePathDomainModelReference domainModelReference = VFeaturePathDomainModelReference.class
			.cast(control.getDomainModelReference());
		assertSame(suppliedFeature, domainModelReference.getDomainModelEFeature());
		assertTrue(domainModelReference.getDomainModelEReferencePath().isEmpty());
		cleanup(resource);
	}

	private Resource getResource() {
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		return resource;
	}

	private void cleanup(Resource resource) {
		final AdapterFactoryEditingDomain editingDomain = (AdapterFactoryEditingDomain) ((AdapterFactoryEditingDomain.EditingDomainProvider) resource
			.getResourceSet()
			.eAdapters().get(0)).getEditingDomain();
		((ComposedAdapterFactory) editingDomain.getAdapterFactory()).dispose();
	}
}
