/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core.di.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererFactory;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.framework.Bundle;

/**
 * The {@link EMFFormsDIExtensionRendererFactory} reads the extension point
 * "org.eclipse.emfforms.swt.core.di.extension.renderers" and provides renderer instances for the registered renderers.
 * The extension point requires the class of the renderer and the {@link ECPRendererTester} for the renderer.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDIExtensionRendererFactory extends EMFFormsDIRendererFactory {

	private static final String EXTENSION_POINT_ID = "org.eclipse.emfforms.swt.core.di.extension.renderers"; //$NON-NLS-1$

	/**
	 * Called by the initializer. Reads in the extension point.
	 */
	protected void activate() {
		final IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(EXTENSION_POINT_ID);

		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				final ECPRendererTester rendererTester = (ECPRendererTester) configurationElement
					.createExecutableExtension("tester"); //$NON-NLS-1$

				final Class<AbstractSWTRenderer<VElement>> rendererClass = loadClass(configurationElement
					.getContributor().getName(), configurationElement.getAttribute("class")); //$NON-NLS-1$

				final EMFFormsDIRendererService<VElement> rendererService = new EMFFormsDIWrapperRendererService(
					rendererClass, rendererTester);
				addEMFFormsDIRendererService(rendererService);
			} catch (final ClassNotFoundException ex) {
				getReportService().report(new AbstractReport(ex));
			} catch (final InvalidRegistryObjectException ex) {
				getReportService().report(new AbstractReport(ex));
			} catch (final CoreException ex) {
				getReportService().report(new AbstractReport(ex));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> loadClass(String bundleName, String clazz)
		throws ClassNotFoundException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			throw new ClassNotFoundException(clazz + bundleName);
		}
		return (Class<T>) bundle.loadClass(clazz);

	}
}
