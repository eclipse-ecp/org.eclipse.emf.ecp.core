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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.workspace.CDOWorkspace;
import org.eclipse.emf.spi.cdo.DefaultCDOMerger;

/**
 * Handles Updates.
 *
 * @author Eike Stepper
 */
public class UpdateHandler extends AbstractWorkspaceHandler {

	/**
	 * Default Constructor.
	 */
	public UpdateHandler() {
		super("Checking in..."); //$NON-NLS-1$
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void execute(ExecutionEvent event, CDOWorkspace workspace, IProgressMonitor monitor)
		throws ExecutionException {
		CDOUtil.setLegacyModeDefault(true);
		final CDOTransaction transaction = workspace.update(new DefaultCDOMerger.PerFeature.ManyValued());
		transaction.setCommitComment("Updated from remote"); //$NON-NLS-1$
		try {
			transaction.commit();
		} catch (final ConcurrentAccessException ex) {
			throw new ExecutionException("Commit failed!", ex); //$NON-NLS-1$
		} catch (final CommitException ex) {
			throw new ExecutionException("Commit failed!", ex); //$NON-NLS-1$
		}
		refreshDirtyState(event);
	}
}
