/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.internal.swt.ECPRendererDescription;
import org.eclipse.emf.ecp.view.internal.swt.ECPStaticRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.framework.Bundle;

/**
 * A RendererFactory for SWT controls.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class SWTRendererFactory {
	private static final String TEST_DYNAMIC = "dynamicTest";//$NON-NLS-1$
	private static final String TEST_STATIC = "staticTest";//$NON-NLS-1$
	private static final String TESTER_PRIORITY = "priority";//$NON-NLS-1$
	private static final String TESTER_VELEMENT = "element"; //$NON-NLS-1$
	private static final String RENDERER_TESTER = "testClass"; //$NON-NLS-1$
	/**
	 * The singleton instance of this factory.
	 */
	public static final SWTRendererFactory INSTANCE = new SWTRendererFactory();
	private static final String RENDER_EXTENSION = "org.eclipse.emf.ecp.ui.view.swt.renderers"; //$NON-NLS-1$
	// private final Map<Class<? extends VElement>, AbstractSWTRenderer<VElement>> rendererMapping = new
	// LinkedHashMap<Class<? extends VElement>, AbstractSWTRenderer<VElement>>();

	private final Set<ECPRendererDescription> rendererDescriptors = new LinkedHashSet<ECPRendererDescription>();

	private SWTRendererFactory() {
		readRenderer();
		// readCustomRenderers();
	}

	private void readRenderer() {
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
			.getExtensionPoint(RENDER_EXTENSION);
		for (final IExtension extension : extensionPoint.getExtensions()) {

			for (final IConfigurationElement configurationElement : extension
				.getConfigurationElements()) {
				try {
					@SuppressWarnings("unchecked")
					final AbstractSWTRenderer<VElement> renderer = (AbstractSWTRenderer<VElement>) configurationElement
						.createExecutableExtension("renderer"); //$NON-NLS-1$

					final Set<ECPRendererTester> tester = new LinkedHashSet<ECPRendererTester>();
					for (final IConfigurationElement testerExtension : configurationElement.getChildren()) {
						if (TEST_DYNAMIC.equals(testerExtension.getName())) {
							tester.add((ECPRendererTester) testerExtension.createExecutableExtension(RENDERER_TESTER));
						}
						else if (TEST_STATIC.equals(testerExtension.getName())) {

							final int priority = Integer.parseInt(testerExtension.getAttribute(TESTER_PRIORITY));

							final String vElement = testerExtension.getAttribute(TESTER_VELEMENT);
							final Class<? extends VElement> supportedEObject = loadClass(testerExtension
								.getContributor()
								.getName(), vElement);

							tester.add(new ECPStaticRendererTester(priority,
								supportedEObject));
						}
					}

					rendererDescriptors.add(new ECPRendererDescription(renderer, tester));
				} catch (final CoreException ex) {
					ex.printStackTrace();
				} catch (final ClassNotFoundException e) {
					e.printStackTrace();
				} catch (final InvalidRegistryObjectException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);

	}

	// @SuppressWarnings("unchecked")
	// private void readCustomRenderers() {
	// final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
	//			"org.eclipse.emf.ecp.ui.view.swt.customSWTRenderers"); //$NON-NLS-1$
	// for (final IExtension extension : extensionPoint.getExtensions()) {
	// final IConfigurationElement configurationElement = extension.getConfigurationElements()[0];
	// try {
	// final CustomSWTRenderer renderer = (CustomSWTRenderer) configurationElement
	//					.createExecutableExtension("class"); //$NON-NLS-1$
	// for (final Entry<Class<? extends VElement>, AbstractSWTRenderer<? extends VElement>> entry : renderer
	// .getCustomRenderers()
	// .entrySet()) {
	// rendererMapping.put(entry.getKey(), (AbstractSWTRenderer<VElement>) entry.getValue());
	// }
	// } catch (final CoreException ex) {
	// Activator.log(ex);
	// }
	// }
	// }

	/**
	 * Searches for a fitting renderer and then renders the passed {@link VElement}.
	 * 
	 * @param parent the {@link Composite} to render on
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param vElement the {@link VElement} to render
	 * @return the list for {@link RenderingResultRow} which can be empty if no fitting render could be found
	 * @throws NoPropertyDescriptorFoundExeption is thrown when the binded domain object does not have a property
	 *             descriptor
	 * @throws NoRendererFoundException is thrown when no renderer could be found
	 */
	public List<RenderingResultRow<Control>> render(Composite parent,
		VElement vElement, ViewModelContext viewContext) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {

		final AbstractSWTRenderer<VElement> renderer = getRenderer(vElement, viewContext);
		if (renderer == null) {
			throw new NoRendererFoundException(vElement);
		}
		return renderer.render(parent, vElement, viewContext);
	}

	private AbstractSWTRenderer<VElement> getRenderer(VElement vElement, ViewModelContext viewContext) {
		int highestPriority = -1;
		AbstractSWTRenderer<VElement> bestCandidate = null;
		for (final ECPRendererDescription description : rendererDescriptors) {

			int currentPriority = -1;

			for (final ECPRendererTester tester : description.getTester()) {
				final int testerPriority = tester.isApplicable(vElement, viewContext);
				if (testerPriority > currentPriority) {
					currentPriority = testerPriority;
				}

			}

			if (currentPriority > highestPriority) {
				highestPriority = currentPriority;
				bestCandidate = description.getRenderer();
			}
		}
		return bestCandidate;
	}
}
