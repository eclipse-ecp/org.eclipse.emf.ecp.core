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
package org.eclipse.emf.ecp.cdo.internal.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.cdo.workspace.CDOWorkspace;
import org.eclipse.emf.ecp.cdo.internal.ui.Activator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.net4j.util.AdapterUtil;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.IEvaluationService;

/**
 * Abstract Handler for executing commands.
 *
 * @author Eike Stepper
 */
public abstract class AbstractWorkspaceHandler extends AbstractHandler {
	private final String jobName;

	/**
	 * Constructor.
	 *
	 * @param jobName display job name
	 */
	public AbstractWorkspaceHandler(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Get the display name of the current job.
	 *
	 * @return the name
	 */
	public final String getJobName() {
		return jobName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();

			final CDOWorkspace workspace = AdapterUtil.adapt(element, CDOWorkspace.class);
			if (workspace != null) {
				try {
					if (jobName == null) {
						PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
							@Override
							public void run(IProgressMonitor monitor) throws InvocationTargetException,
								InterruptedException {
								try {
									execute(event, workspace, monitor);
								} catch (final ExecutionException ex) {
									Activator.log(ex);
								}
							}
						});
					} else {
						new Job(jobName) {
							@Override
							protected IStatus run(IProgressMonitor monitor) {
								try {
									execute(event, workspace, monitor);
									return Status.OK_STATUS;
								} catch (final ExecutionException ex) {
									return new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex);
								}
							}
						}.schedule();
					}
				} catch (final InvocationTargetException ex) {
					throw new ExecutionException("Problem while handling " + element, ex); //$NON-NLS-1$
				} catch (final InterruptedException ex) {
					throw new ExecutionException("Problem while handling " + element, ex); //$NON-NLS-1$
				}
			}
		}

		return null;
	}

	/**
	 * Execute the given event.
	 *
	 * @param event the event
	 * @param workspace the {@link CDOWorkspace}
	 * @param monitor a progress monitor
	 * @throws ExecutionException if execution fails
	 */
	protected abstract void execute(ExecutionEvent event, CDOWorkspace workspace, IProgressMonitor monitor)
		throws ExecutionException;

	/**
	 * Refresh the dirty state of the {@link CDOWorkspace}.
	 *
	 * @param event the event
	 * @throws ExecutionException if refresh fails
	 */
	protected static void refreshDirtyState(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow ww = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// We keep the cast, otherwise we loose SRC compatibility with Luna
		@SuppressWarnings("cast")
		final IEvaluationService service = (IEvaluationService) ww.getService(IEvaluationService.class);
		if (service != null) {
			service.requestEvaluation("org.eclipse.emf.cdo.workspace.dirty"); //$NON-NLS-1$
		}
	}
}
