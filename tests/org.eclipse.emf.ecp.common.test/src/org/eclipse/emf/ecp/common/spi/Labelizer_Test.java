/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EAttributeItemProvider;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecp.common.test.model.TestFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

/**
 * Unit tests for the {@link Labelizer} API.
 */
public class Labelizer_Test {

	@Rule
	public final TestRule timeout = new Timeout(5, TimeUnit.SECONDS);

	static {
		// Ensure that the item-provider adapter factory registry has what our test needs
		final Collection<?> types = Arrays.asList(EcorePackage.eINSTANCE, IItemLabelProvider.class);
		ComposedAdapterFactory.Descriptor desc = ComposedAdapterFactory.Descriptor.Registry.INSTANCE
			.getDescriptor(types);
		if (desc == null) {
			desc = new ComposedAdapterFactory.Descriptor() {

				@Override
				public AdapterFactory createAdapterFactory() {
					return new EcoreItemProviderAdapterFactory();
				}
			};

			@SuppressWarnings("unchecked")
			final Map<Collection<?>, ComposedAdapterFactory.Descriptor> registryMap = (Map<Collection<?>, ComposedAdapterFactory.Descriptor>) ComposedAdapterFactory.Descriptor.Registry.INSTANCE;
			registryMap.put(types, desc);
		}
	}

	/**
	 * Initializes me.
	 */
	public Labelizer_Test() {
		super();
	}

	@Test
	public void intern() {
		final Labelizer instance1 = Labelizer.get(EcorePackage.eINSTANCE);
		final Labelizer instance2 = Labelizer.get(EcorePackage.eINSTANCE);

		assertThat("Non-unique instances", instance2, sameInstance(instance1));
	}

	@Test
	public void getLabel_registeredAdapterFactory() {
		final EObject object = EcorePackage.Literals.EATTRIBUTE__ID;
		final Labelizer labelizer = Labelizer.get(object);
		final String actualLabel = labelizer.getLabel(object);
		final String expectedLabel = new EAttributeItemProvider(null).getText(object);

		assertThat(actualLabel, is(expectedLabel));
	}

	@Test
	public void getLabel_defaultAdapterFactory() {
		// We don't have an EMF.Edit implementation for these
		final EObject object = TestFactory.eINSTANCE.createTest1();
		final Labelizer labelizer = Labelizer.get(object);
		final String actualLabel = labelizer.getLabel(object);
		final String expectedLabel = new ReflectiveItemProvider(null).getText(object);

		assertThat(actualLabel, is(expectedLabel));
	}

	@Test
	public void asyncCleanUp() throws Exception {
		final EPackage ePackage = EcorePackage.eINSTANCE;
		Labelizer.get(ePackage).getLabel(ePackage);

		final Adapter itemProvider = ePackage.eAdapters().stream().filter(IItemLabelProvider.class::isInstance)
			.findFirst().orElse(null);
		assumeThat("No item-provider adapter attached", itemProvider, notNullValue());

		for (int i = 0; i < 15; i++) {
			if (!ePackage.eAdapters().contains(itemProvider)) {
				break;
			}

			System.gc();
			Thread.sleep(500L);
		}

		assertThat("Item providers not disposed", ePackage.eAdapters(), not(hasItem(itemProvider)));
	}

}
