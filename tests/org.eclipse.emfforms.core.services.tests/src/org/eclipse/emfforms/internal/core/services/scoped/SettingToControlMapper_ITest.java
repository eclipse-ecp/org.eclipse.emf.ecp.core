/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.internal.core.services.controlmapper.SettingToControlMapperImpl;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;
import org.junit.Test;

public class SettingToControlMapper_ITest {

	private static final int NR_UPDATES = 100;
	private static final int NR_RUNNABLES = 100;

	@Test
	public void updateMappingsConcurrently() throws InterruptedException {
		// setup
		final CountDownLatch latch = new CountDownLatch(NR_RUNNABLES);
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final EObject domainObject = EcoreUtil.create(EcorePackage.eINSTANCE.getEClass());
		final SettingToControlMapperImpl settingToControlMapper = new SettingToControlMapperImpl(
			new EMFFormsMappingProviderManager() {
				@Override
				public Set<UniqueSetting> getAllSettingsFor(VDomainModelReference domainModelReference,
					EObject domainObject) {
					final Set<UniqueSetting> settings = new LinkedHashSet<UniqueSetting>();
					settings.add(
						UniqueSetting.createSetting(domainObject, EcorePackage.eINSTANCE.getEClass_EAttributes()));
					return settings;
				}
			},
			new FakeViewContext(domainObject, control));

		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ArrayList<Runnable> runnables = new ArrayList<Runnable>();
		for (int i = 0; i < NR_RUNNABLES; i++) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					try {
						int j = 0;
						while (j < NR_UPDATES) {
							settingToControlMapper.updateControlMapping(control);
							Thread.sleep(1);
							j += 1;
						}
						// BEGIN SUPRESS CATCH EXCEPTION
					} catch (final Exception exception) {
						exceptions.add(exception);
						// END SUPRESS CATCH EXCEPTION
					} finally {
						latch.countDown();
					}

				}
			});
		}
		final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(runnables.size());

		// act
		for (final Runnable runnable : runnables) {
			newFixedThreadPool.submit(runnable);
		}

		latch.await();

		// assert
		assertTrue(exceptions.isEmpty());
	}

	class FakeViewContext implements EMFFormsViewContext {

		private final EObject domainObject;
		private final VElement viewModel;

		FakeViewContext(EObject domainObject, VElement viewModel) {
			this.domainObject = domainObject;
			this.viewModel = viewModel;
		}

		@Override
		public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		}

		@Override
		public void unregisterRootDomainModelChangeListener(
			RootDomainModelChangeListener rootDomainModelChangeListener) {

		}

		@Override
		public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {

		}

		@Override
		public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {

		}

		@Override
		public void registerViewChangeListener(ModelChangeListener modelChangeListener) {

		}

		@Override
		public void registerRootDomainModelChangeListener(
			RootDomainModelChangeListener rootDomainModelChangeListener) {

		}

		@Override
		public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {

		}

		@Override
		public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {

		}

		@Override
		public VElement getViewModel() {
			return viewModel;
		}

		@Override
		public <T> T getService(Class<T> serviceType) {
			return null;
		}

		@Override
		public EObject getDomainModel() {
			return domainObject;
		}

		@Override
		public void changeDomainModel(EObject newDomainModel) {

		}
	}
}
