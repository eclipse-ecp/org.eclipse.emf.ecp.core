/*******************************************************************************
 * Copyright (c) 2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.ide.internal.migration;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Opens the {@link ViewMigrationDialog}.
 *
 */
public class OpenViewMigrationDialog extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final Shell shell = Display.getCurrent().getActiveShell();
		final ViewMigrationDialog dialog = new ViewMigrationDialog(shell);

		if (dialog.open() == Window.OK) {
			final ISelection sel = HandlerUtil
				.getActiveWorkbenchWindowChecked(event)
				.getSelectionService()
				.getSelection();

			if (sel instanceof IStructuredSelection) {
				final Job job = new Job(Messages.OpenViewMigrationDialog_JobTitle) {

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						final SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
						subMonitor.setTaskName(Messages.OpenViewMigrationDialog_Find);
						final Set<IFile> viewModelFiles = Resources.findAllViewFilesInWorkspace(subMonitor.split(20));
						subMonitor.setWorkRemaining(80);
						subMonitor.setTaskName(Messages.OpenViewMigrationDialog_Migrate);
						try {
							final Map<String, Optional<Diagnostic>> diagnostics = new ViewMigrationHandler(
								dialog.getOldNamespaceFragment(),
								dialog.getNewNamespaceFragment()).execute(viewModelFiles, subMonitor.split(80));
							showResultDialog(diagnostics, dialog.shouldShowWarning());

						} catch (final ViewMigrationException ex) {
							return new Status(
								IStatus.ERROR,
								"org.eclipse.emf.ecp.ide.migration", //$NON-NLS-1$
								IStatus.ERROR,
								Messages.OpenUpdateNSDialog_ErrorTitle,
								ex);
						}

						return Status.OK_STATUS;
					}

				};
				job.schedule();
			}
		}

		return null;
	}

	private void showResultDialog(final Map<String, Optional<Diagnostic>> diagnostics, final boolean showWarnings) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				final ViewMigrationResultDialog dialog = new ViewMigrationResultDialog(
					Display.getCurrent().getActiveShell(),
					diagnostics,
					showWarnings);
				dialog.open();
			}
		});
	}
}
