/*******************************************************************************
 * Copyright (c) 2002-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.makeithappen.internal.wizards;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;

/**
 * The <b>Plugin</b> for the model ecp..makeithappen.internal.wizards library.
 * EMF must run
 * within an Eclipse workbench,
 * within a headless Eclipse workspace,
 * or just stand-alone as part of some other application.
 * To support this, all resource access should be directed to the resource locator,
 * which can redirect the service as appropriate to the runtime.
 * During stand-alone invocation no plugin initialization takes place.
 * In this case, common.resources.jar must be on the CLASSPATH.
 *
 * @see #INSTANCE
 */
public final class MakeItHappenWizardsPlugin extends EMFPlugin
{
	/**
	 * The singleton instance of the plugin.
	 */
	public static final MakeItHappenWizardsPlugin INSTANCE = new MakeItHappenWizardsPlugin();

	/**
	 * The one instance of this class.
	 */
	private static Implementation plugin;

	/**
	 * Creates the singleton instance.
	 */
	private MakeItHappenWizardsPlugin()
	{
		super(new ResourceLocator[] {});
	}

	/*
	 * Javadoc copied from base class.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator()
	{
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 *
	 * @return the singleton instance.
	 */
	public static Implementation getPlugin()
	{
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipseUIPlugin
	{
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			plugin = this;
		}

		@Override
		public void stop(BundleContext context) throws Exception
		{

			super.stop(context);
		}
	}
}
