/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.di.renderer;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.model.common.ECPStaticRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.osgi.framework.Bundle;

/**
 * Factory for creating POJO renderers.
 *
 * @author jfaltermeier
 *
 */
public final class POJORendererFactory {

	private static final String TEST_CLASS = "testClass"; //$NON-NLS-1$
	private static final String PRIORITY = "priority"; //$NON-NLS-1$
	private static final String ELEMENT = "element"; //$NON-NLS-1$
	private static final String DYNAMIC_TEST = "dynamicTest"; //$NON-NLS-1$
	private static final String STATIC_TEST = "staticTest"; //$NON-NLS-1$
	private static final String RENDERER = "renderer"; //$NON-NLS-1$

	private static final String POINT_ID = "org.eclipse.emf.ecp.view.model.common.di.renderers"; //$NON-NLS-1$

	private static POJORendererFactory instance;

	private final Map<Class<Object>, Set<ECPRendererTester>> rendererPojos;

	/**
	 * Returns the instance of the {@link POJORendererFactory}.
	 *
	 * @return the factory
	 */
	public static POJORendererFactory getInstance() {
		if (instance == null) {
			instance = new POJORendererFactory();
		}
		return instance;
	}

	private POJORendererFactory() {
		rendererPojos = new LinkedHashMap<Class<Object>, Set<ECPRendererTester>>();
		readExtensionPoint();
	}

	private void readExtensionPoint() {
		try {
			final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
				POINT_ID);
			for (final IConfigurationElement configurationElement : controls) {
				// get pojo class
				final String plugin = configurationElement.getContributor().getName();
				final String pojoClassName = configurationElement.getAttribute(RENDERER);
				final Class<Object> pojoClass = loadClass(plugin, pojoClassName);

				// get tester
				final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
				for (final IConfigurationElement childElement : configurationElement.getChildren()) {
					// static tests
					if (STATIC_TEST.equals(childElement.getName())) {
						final String childPlugin = childElement.getContributor().getName();
						final String testerElementClassName = childElement.getAttribute(ELEMENT);
						final Class<? extends VElement> staticTestElement = loadClass(childPlugin,
							testerElementClassName);

						final int priority = Integer.parseInt(childElement.getAttribute(PRIORITY));

						tester.add(new ECPStaticRendererTester(priority, staticTestElement));
					}

					// dynamic tests
					else if (DYNAMIC_TEST.equals(childElement.getName())) {
						final ECPRendererTester dynamicTester = (ECPRendererTester) childElement
							.createExecutableExtension(TEST_CLASS);
						tester.add(dynamicTester);
					}
				}

				// register
				rendererPojos.put(pojoClass, tester);

			}
		} catch (final InvalidRegistryObjectException ex) {
			// TODO Auto-generated catch block
		} catch (final ClassNotFoundException ex) {
			// TODO Auto-generated catch block
		} catch (final CoreException ex) {
			// TODO Auto-generated catch block
		}

	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> loadClass(String bundleName, String clazz) throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	/**
	 * Returns the renderer POJO for the given element/context.
	 *
	 * @param vElement the {@link VElement} to render
	 * @param viewContext the current {@link ViewModelContext}
	 * @return the renderer
	 */
	public Object getRenderer(VElement vElement, ViewModelContext viewContext) {
		int highestPriority = -1;
		Class<Object> bestCandidate = null;
		for (final Entry<Class<Object>, Set<ECPRendererTester>> rendererCandidate : rendererPojos.entrySet()) {
			int currentPriority = -1;

			for (final ECPRendererTester tester : rendererCandidate.getValue()) {
				final int testerPriority = tester.isApplicable(vElement, viewContext);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
				}

			}

			if (currentPriority > highestPriority) {
				highestPriority = currentPriority;
				bestCandidate = rendererCandidate.getKey();
			}
		}

		if (bestCandidate == null) {
			throw new NoSuchElementException();
		}
		try {
			return bestCandidate.newInstance();
		} catch (final InstantiationException ex) {
			// TODO Auto-generated catch block
			throw new NoSuchElementException();
		} catch (final IllegalAccessException ex) {
			// TODO Auto-generated catch block
			throw new NoSuchElementException();
		}
	}

}
