/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 527740
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.viewproxy.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelServiceProvider;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewProxy;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewproxyFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;
import org.junit.Before;
import org.junit.Test;

public class ResolverViewService_PTest {

	private static final String GENDER = "gender"; //$NON-NLS-1$
	private static final String ID = "player2"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private EObject domain;
	private ProxyResolverViewService service;
	private VView view;
	private VViewProxy viewProxy;

	@Before
	public void before() {
		domain = BowlingFactory.eINSTANCE.createPlayer();
		service = new ProxyResolverViewService();
		view = VViewFactory.eINSTANCE.createView();
		viewProxy = VViewproxyFactory.eINSTANCE.createViewProxy();
		view.getChildren().add(viewProxy);
	}

	@Test
	public void testNoProxyId() {
		service.instantiate(new DummyContext());
		assertEquals(1, view.getChildren().size());
		assertTrue(VVerticalLayout.class.isInstance(view.getChildren().get(0)));
		final VVerticalLayout layout = VVerticalLayout.class.cast(view.getChildren().get(0));
		assertEquals(1, layout.getChildren().size());
		assertEquals(NAME, layout.getChildren().get(0).getName());
	}

	@Test
	public void testWithProxyId() {
		viewProxy.setId(ID);
		service.instantiate(new DummyContext());
		assertEquals(1, view.getChildren().size());
		assertTrue(VVerticalLayout.class.isInstance(view.getChildren().get(0)));
		final VVerticalLayout layout = VVerticalLayout.class.cast(view.getChildren().get(0));
		assertEquals(1, layout.getChildren().size());
		assertEquals(GENDER, layout.getChildren().get(0).getName());
	}

	@Test
	public void testWithGeneratedView() {
		domain = EcoreFactory.eINSTANCE.createEClass();
		service.instantiate(new DummyContext());
		assertEquals(1, view.getChildren().size());
		assertTrue(VVerticalLayout.class.isInstance(view.getChildren().get(0)));
		final VVerticalLayout layout = VVerticalLayout.class.cast(view.getChildren().get(0));
		assertEquals(4, layout.getChildren().size());
	}

	private class DummyContext implements ViewModelContext {

		private final Map<String, Object> context = new LinkedHashMap<String, Object>();

		@Override
		public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
		}

		@Override
		public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
		}

		@Override
		public VElement getViewModel() {
			return view;
		}

		@Override
		public EObject getDomainModel() {
			return domain;
		}

		@Override
		public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
		}

		@Override
		public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public <T> boolean hasService(Class<T> serviceType) {
			return false;
		}

		@Override
		public <T> T getService(Class<T> serviceType) {
			return null;
		}

		@Deprecated
		@Override
		public Set<VControl> getControlsFor(Setting setting) {
			return null;
		}

		@Deprecated
		@Override
		public Set<VElement> getControlsFor(org.eclipse.emf.ecp.common.spi.UniqueSetting setting) {
			return null;
		}

		@Override
		public Object getContextValue(String key) {
			return context.get(key);
		}

		@Override
		public void putContextValue(String key, Object value) {
			context.put(key, value);
		}

		@Deprecated
		@Override
		public ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
			ViewModelService... viewModelServices) {
			return null;
		}

		@Override
		public ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
			ViewModelServiceProvider viewModelServiceProvider) {
			return null;
		}

		@Override
		public void registerDisposeListener(ViewModelContextDisposeListener listener) {
			// do nothing
		}

		@Override
		public void addContextUser(Object user) {
			// do nothing
		}

		@Override
		public void removeContextUser(Object user) {
			// do nothing
		}

		@Override
		public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {
			// do nothing
		}

		@Override
		public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {
			// do nothing
		}

		@Override
		public ViewModelContext getParentContext() {
			return null;
		}

		@Override
		public void changeDomainModel(EObject newDomainModel) {
			// do nothing
		}

		@Override
		public VElement getParentVElement() {
			return null;
		}

		@Override
		public void registerRootDomainModelChangeListener(RootDomainModelChangeListener rootDomainModelChangeListener) {
			// do nothing
		}

		@Override
		public void unregisterRootDomainModelChangeListener(
			RootDomainModelChangeListener rootDomainModelChangeListener) {
			// do nothing
		}

		@Override
		public void pause() {
			// do nothing
		}

		@Override
		public void reactivate() {
			// do nothing
		}
	}
}
