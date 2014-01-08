/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.cdo.internal.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.cdo.server.CDOServerBrowser;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.workspace.InternalCDOWorkspace;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author Eike Stepper
 */
public class Activator extends Plugin {
	/**
	 * The PlugIn ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.cdo.core"; //$NON-NLS-1$

	private static Activator instance;

	private CDOServerBrowser serverBrowser;

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		instance = this;

		serverBrowser = new CDOServerBrowser(null) {
			@Override
			protected Set<String> getRepositoryNames() {
				Set<String> names = new HashSet<String>();
				for (ECPProject project : ECPUtil.getECPProjectManager().getProjects()) {
					if (project.getProvider().getName().equals(CDOProvider.NAME)) {
						CDOProjectData projectData = CDOProvider.getProjectData((InternalProject) project);
						InternalCDOWorkspace workspace = (InternalCDOWorkspace) projectData.getWorkspace();
						if (workspace != null) {
							InternalRepository localRepository = workspace.getLocalRepository();
							names.add(localRepository.getName());
						}
					}
				}

				return names;
			}

			@Override
			protected InternalRepository getRepository(String name) {
				for (ECPProject project : ECPUtil.getECPProjectManager().getProjects()) {
					if (project.getProvider().getName().equals(CDOProvider.NAME)) {
						CDOProjectData projectData = CDOProvider.getProjectData((InternalProject) project);
						InternalCDOWorkspace workspace = (InternalCDOWorkspace) projectData.getWorkspace();
						if (workspace != null) {
							InternalRepository localRepository = workspace.getLocalRepository();
							if (localRepository.getName().equals(name)) {
								return localRepository;
							}
						}
					}
				}

				return null;
			}
		};
		// TODO: Please check this. Added. because EMFCP fails on start up if this port is in use
		try {
			serverBrowser.setPort(7778);
			serverBrowser.activate();
		} catch (Exception e) {
			log(e.getMessage());
			// TODO: Please check this. Added. because EMFCP fails on start up if this port is in use
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		serverBrowser.deactivate();
		serverBrowser = null;

		if (CDOProvider.getInstance() != null) {
			CDOProvider.getInstance().dispose();
		}

		instance = null;
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION
	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static Activator getInstance() {
		return instance;
	}

	/**
	 * Logs messages.
	 * 
	 * @param message the message
	 */
	public static void log(String message) {
		instance.getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
	}

	/**
	 * Logs {@link IStatus}.
	 * 
	 * @param status the {@link IStatus}
	 */
	public static void log(IStatus status) {
		instance.getLog().log(status);
	}

	/**
	 * Logs {@link Throwable}.
	 * 
	 * @param t the {@link Throwable}
	 * @return the message of the created status
	 */
	public static String log(Throwable t) {
		IStatus status = getStatus(t);
		log(status);
		return status.getMessage();
	}

	/**
	 * Gets a {@link IStatus} for a throwable.
	 * 
	 * @param t the {@link Throwable}
	 * @return the created {@link IStatus}
	 */
	public static IStatus getStatus(Throwable t) {
		if (t instanceof CoreException) {
			CoreException coreException = (CoreException) t;
			return coreException.getStatus();
		}

		String msg = t.getLocalizedMessage();
		if (msg == null || msg.length() == 0) {
			msg = t.getClass().getName();
		}

		return new Status(IStatus.ERROR, PLUGIN_ID, msg, t);
	}
}
