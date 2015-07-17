/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.quickfix.ui.e4.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.quickfix.ModelQuickFix;
import org.eclipse.emf.ecp.quickfix.ModelQuickFixException;
import org.eclipse.jface.dialogs.ErrorDialog;

/**
 * @author Alexandra Buzila
 *
 */
public class ModelQuickFixContextMenuItemHandler {
	private static final String PLUGIN_ID = "org.eclipse.emf.ecp.quickfix.ui.e4"; //$NON-NLS-1$
	private final ModelQuickFix modelQuickFix;
	private final EObject target;

	/**
	 * Handler for executing ModelQuickFixes.
	 *
	 * @param modelQuickFix - the ModelQuickFix to execute
	 * @param target - the target EObject to which the quick fix should be applied
	 */
	ModelQuickFixContextMenuItemHandler(ModelQuickFix modelQuickFix, EObject target) {
		this.modelQuickFix = modelQuickFix;
		this.target = target;
	}

	/** @see org.eclipse.e4.core.di.annotations.Execute */
	@Execute
	public void execute() {
		if (modelQuickFix == null || target == null) {
			return;
		}
		try {
			modelQuickFix.applyFix(target);
		} catch (final ModelQuickFixException e) {

			final StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			final String stackTrace = sw.toString();

			/*
			 * splitting stack trace into children statuses, such that each line in the message will show up on a new
			 * line in the details section of the error dialog
			 */
			final List<Status> childStatuses = new ArrayList<Status>();
			for (final String line : stackTrace.split(System.getProperty("line.separator"))) { //$NON-NLS-1$
				childStatuses.add(new Status(IStatus.ERROR, PLUGIN_ID, line));
			}
			final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.ERROR,
				childStatuses.toArray(new Status[] {}),
				e.getLocalizedMessage(), e);

			ErrorDialog.openError(null, Messages._UI_ModelQuickFixExceptionDialog_title, Messages._UI_ModelQuickFixExceptionDialog_message,
				status);
		}
		return;
	}
}
