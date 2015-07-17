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
package org.eclipse.emf.ecp.emfstore.internal.ui.observer;

import java.text.DateFormat;
import java.util.Date;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * Creates a new {@link ECPProject} on checkout.
 *
 * @author Jonas
 *
 */
public class CheckoutObserver implements ESCheckoutObserver {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver#checkoutDone(org.eclipse.emf.emfstore.client.ESLocalProject)
	 */
	@Override
	public void checkoutDone(ESLocalProject project) {

		// The project with this name is just temporary used to debug EMFStore. Therefore it should be ignored.
		if ("log_error_checksum_debug_checkout".equals(project.getProjectName())) { //$NON-NLS-1$
			return;
		}

		final ECPRepository repository = EMFStoreProvider.INSTANCE.getRepository(
			project.getUsersession().getServer());
		boolean ecpProjectExists = false;
		boolean validProjectName = false;

		for (final ECPProject ecpProject : ECPUtil.getECPProjectManager().getProjects()) {
			final InternalProject internalProject = (InternalProject) ecpProject;
			final Object localProject = internalProject.getProviderSpecificData();
			if (localProject instanceof ESLocalProject) {
				if (localProject == project) {
					ecpProjectExists = true;
					break;
				}
			}
		}

		String projectName = project.getProjectName();

		if (!ecpProjectExists) {
			while (!validProjectName) {
				try {
					ECPUtil.getECPProjectManager().createProject(repository, projectName,
						createECPProperties(project));
					validProjectName = true;
				} catch (final ECPProjectWithNameExistsException ex) {
					final InputDialog id = new InputDialog(Display.getCurrent().getActiveShell(),
						Messages.CheckoutObserver_CreateProject,
						Messages.CheckoutObserver_EnterNameForProject, project.getProjectName()
							+ "@" + createDateString(), //$NON-NLS-1$
						new IInputValidator() {

							@Override
							public String isValid(String newText) {
								if (ECPUtil.getECPProjectManager().getProject(newText) == null) {
									return null;
								}
								return Messages.CheckoutObserver_ProjectWithNameExists;

							}
						});
					final int inputResult = id.open();
					if (Window.OK != inputResult) {
						// cancel, provide default name
						projectName = project.getProjectName() + "@" + createDateString(); //$NON-NLS-1$
					} else {
						projectName = id.getValue();
					}
				}
			}
		}
	}

	private ECPProperties createECPProperties(ESLocalProject project) {
		final ECPProperties projectProperties = ECPUtil.createProperties();
		projectProperties.addProperty(EMFStoreProvider.PROP_PROJECTSPACEID, project.getLocalProjectId().getId());
		return projectProperties;
	}

	private String createDateString() {
		final DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
		return format.format(new Date());
	}

}
