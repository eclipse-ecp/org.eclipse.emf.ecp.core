/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.test.common.spi.EMFMockingRunner;
import org.eclipse.emf.ecp.view.test.common.spi.EMock;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * Unit tests for the {@link GotoMarkerAdapter} class.
 */
@SuppressWarnings("nls")
@RunWith(EMFMockingRunner.class)
public class GotoMarkerAdapter_Test {
	private final EStructuralFeature feature = EcorePackage.Literals.ECLASS__ABSTRACT;

	@Mock
	private ViewModelContext context;

	@EMock
	private VView view;

	@Mock(extraInterfaces = Resource.Internal.class)
	private Resource resource;

	@EMock
	private EObject object;

	@Mock
	private IMarker marker;

	@Mock
	private EMFFormsRevealService revealService;

	private EditingDomain domain;
	private GotoMarkerAdapter adapter;

	/**
	 * Initializes me.
	 */
	public GotoMarkerAdapter_Test() {
		super();
	}

	@Test
	public void gotoMarker() {
		adapter.gotoMarker(marker);

		verify(revealService).reveal(object);
	}

	@Test
	public void gotoMarker_withFeature() {
		mockFeature();

		adapter.gotoMarker(marker);

		verify(revealService).reveal(object, feature);
	}

	@Test
	public void getEObject() {
		final List<?> targets = new EditUIMarkerHelper().getTargetObjects(domain, marker);
		assertThat("Wrong EObject found", adapter.getEObject(targets), sameInstance(object));
	}

	@Test
	public void getEStructuralFeature() {
		final List<?> targets = new EditUIMarkerHelper().getTargetObjects(domain, marker);
		assertThat("Wrong EStructuralFeature found", adapter.getEObject(targets), sameInstance(object));
	}

	//
	// Test framework
	//

	@Before
	public void mockup() throws CoreException {
		final URI uri = URI.createURI("fake://bogus.xmi");
		final String fragment = "e0b1ec7";
		when(resource.getURI()).thenReturn(uri);
		when(resource.getEObject(fragment)).thenReturn(object);
		when(resource.getURIFragment(object)).thenReturn(fragment);

		final String uriValue = uri.appendFragment(fragment).toString();
		when(marker.getAttribute(EValidator.URI_ATTRIBUTE)).thenReturn(uriValue);
		when(marker.getAttribute(argThat(is(EValidator.URI_ATTRIBUTE)), anyString())).thenReturn(uriValue);
		when(context.getService(EMFFormsRevealService.class)).thenReturn(revealService);
	}

	@Before
	public void createFixture() {
		domain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), new BasicCommandStack());
		domain.getResourceSet().getResources().add(resource);
		adapter = new GotoMarkerAdapter(context, domain);
	}

	void mockFeature() {
		try {
			final String featureURI = EcoreUtil.getURI(feature).toString();
			when(marker.getAttribute(EValidator.RELATED_URIS_ATTRIBUTE)).thenReturn(featureURI);
			when(marker.getAttribute(argThat(is(EValidator.RELATED_URIS_ATTRIBUTE)), anyString()))
				.thenReturn(featureURI);
		} catch (final CoreException e) {
			e.printStackTrace();
			fail("Mock threw exception in stubbing: " + e.getMessage());
		}
	}

}
