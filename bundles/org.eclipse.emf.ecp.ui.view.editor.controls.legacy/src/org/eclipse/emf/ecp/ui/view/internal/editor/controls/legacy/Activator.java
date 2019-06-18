/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.view.internal.editor.controls.legacy;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectContentChangedObserver;
import org.eclipse.emf.ecp.view.model.provider.xmi.ViewModelFileExtensionsManager;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
@SuppressWarnings("restriction")
public class Activator extends AbstractUIPlugin {

	// The shared instance
	private static Activator plugin;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		/**
		 * Listen to changes in file projects. If a view is affected, reload the view models provided over file
		 * extension point.
		 */
		ECPUtil.getECPObserverBus().register(new ECPProjectContentChangedObserver() {

			@Override
			public Collection<Object> objectsChanged(ECPProject project, Collection<Object> objects) {
				if (project.getProvider().getName().equals(WorkspaceProvider.NAME)) {
					final EList<Object> contents = project.getContents();
					for (final Object object : contents) {
						if (object instanceof VView) {
							ViewModelFileExtensionsManager.dispose();
						}
					}
				}
				return new ArrayList<Object>();

			}

		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
