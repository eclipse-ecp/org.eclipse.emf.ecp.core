/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.renderer;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.view.internal.ui.Activator;

/**
 * This class allows to get a fitting {@link LayoutHelper} based on the provided {@link Class} as well as to get a
 * fitting {@link RenderingResultRowFactory} based on a {@link Class}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@Deprecated
public final class RenderingConfiguration {

	private static final String RENDERING_LAYOUT_HELPER = "renderingLayoutHelper"; //$NON-NLS-1$

	private static final String RENDERING_RESULT_ROW_FACTORY = "renderingResultRowFactory"; //$NON-NLS-1$

	private static final String RENDERING_CONFIGURATION_EXTENSION_POINT = "org.eclipse.emf.ecp.ui.view.renderingConfiguration"; //$NON-NLS-1$

	private static final String CLASS_ATTRIBUTE = "class";//$NON-NLS-1$

	private static final RenderingConfiguration INSTANCE = new RenderingConfiguration();
	private final Map<Class<?>, RenderingResultRowFactory<?>> rowFactories = new LinkedHashMap<Class<?>, RenderingResultRowFactory<?>>();
	private final Map<Class<?>, LayoutHelper<?>> layoutHelpers = new LinkedHashMap<Class<?>, LayoutHelper<?>>();

	/**
	 * Returns the singleton instance.
	 *
	 * @return the singleton
	 */
	public static RenderingConfiguration getCurrent() {
		return INSTANCE;
	}

	private RenderingConfiguration() {
		readExtensionPoint();
	}

	private void readExtensionPoint() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry.getConfigurationElementsFor(
			RENDERING_CONFIGURATION_EXTENSION_POINT);
		for (final IConfigurationElement e : controls) {
			for (final IConfigurationElement e2 : e.getChildren()) {
				try {
					if (RENDERING_RESULT_ROW_FACTORY.equalsIgnoreCase(e2.getName())) {
						final RenderingResultRowFactory<?> rrrf = (RenderingResultRowFactory<?>) e2
							.createExecutableExtension(CLASS_ATTRIBUTE);
						final Class<?> clazz = (Class<?>) ((ParameterizedType) rrrf.getClass().getGenericInterfaces()[0])
							.getActualTypeArguments()[0];
						rowFactories.put(clazz, rrrf);
					}
					else if (RENDERING_LAYOUT_HELPER.equalsIgnoreCase(e2.getName())) {
						final LayoutHelper<?> layoutHelper = (LayoutHelper<?>) e2
							.createExecutableExtension(CLASS_ATTRIBUTE);
						final Class<?> clazz = (Class<?>) ((ParameterizedType) layoutHelper.getClass()
							.getGenericInterfaces()[0]).getActualTypeArguments()[0];
						layoutHelpers.put(clazz, layoutHelper);
					}
				} catch (final CoreException ex) {
					Activator.log(ex);
				}
			}
		}

	}

	/**
	 * Returns the {@link RenderingResultRowFactory} registered for a specific {@link Class}.
	 *
	 * @param controlClazz the class to get the {@link RenderingResultRowFactory} for
	 * @param <CONTROL> the type of the control, the factory is working on
	 * @return the registered {@link RenderingResultRowFactory} or null if none is registered for the provided
	 *         {@link Class}
	 */
	@SuppressWarnings("unchecked")
	public <CONTROL> RenderingResultRowFactory<CONTROL> getRenderingRowFactory(Class<CONTROL> controlClazz) {
		return (RenderingResultRowFactory<CONTROL>) rowFactories.get(controlClazz);
	}

	/**
	 * Returns the layout helper registered for a specific {@link Class}.
	 *
	 * @param layoutClazz the class to get the {@link LayoutHelper} for
	 * @param <LAYOUT> the type of the layout, the layout helper should create
	 * @return the registered {@link LayoutHelper} or null if none is registered for the provided {@link Class}
	 */
	@SuppressWarnings("unchecked")
	public <LAYOUT> LayoutHelper<LAYOUT> getLayoutHelper(Class<LAYOUT> layoutClazz) {
		return (LayoutHelper<LAYOUT>) layoutHelpers.get(layoutClazz);
	}
}
