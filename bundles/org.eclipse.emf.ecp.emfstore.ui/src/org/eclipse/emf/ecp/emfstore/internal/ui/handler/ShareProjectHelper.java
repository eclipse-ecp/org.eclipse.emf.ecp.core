/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.internal.wizards.ShareWizard;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.util.RunESCommand;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.client.ui.controller.UIShareProjectController;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the EMFStore ShareProject Handler delegating to the EMFStore {@link UIShareProjectController}.
 *
 * @author Eugen Neufeld
 *
 */
public final class ShareProjectHelper {

	private ShareProjectHelper() {
	}

	/**
	 * Shares an {@link InternalProject}. Delegates to {@link UIShareProjectController}.
	 *
	 * @param project the {@link InternalProject}
	 * @param shell the {@link Shell}
	 */
	public static void share(InternalProject project, Shell shell) {
		final ShareWizard rw = new ShareWizard();
		rw.init(project.getProvider());

		final WizardDialog wd = new WizardDialog(shell, rw);
		final int result = wd.open();
		if (result == Window.OK) {
			// TODO internal cast again
			final InternalRepository repository = (InternalRepository) rw.getSelectedRepository();
			project.undispose(repository);
			final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(project);

			// TODO Ugly
			if (localProject.getUsersession() == null) {
				final ESServerImpl server = (ESServerImpl) EMFStoreProvider.INSTANCE.getServerInfo(project
					.getRepository());
				final ServerInfo serverInfo = server.toInternalAPI();
				RunESCommand.run(new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						((ESLocalProjectImpl) localProject).toInternalAPI().setUsersession(
							serverInfo.getLastUsersession());
						return null;
					}
				});
			}
			// TODO EMFStore Constructor is missing
			new UIShareProjectController(shell, localProject).execute();

			project.notifyObjectsChanged(Collections.singleton((Object) project), false);
			repository.notifyObjectsChanged(Collections.singleton((Object) repository));
		}
	}
}
