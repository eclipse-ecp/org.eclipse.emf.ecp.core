/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.text.MessageFormat;
import java.util.TimerTask;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * TimerTask that reports that the validation is taking longer than expected. This task should be cancelled when
 * the validation is done.
 */
public class ValidationTimerTask extends TimerTask {

	private boolean cancelled;
	private EObject validatedEObject;

	/**
	 * Constructor.
	 *
	 * @param validatedEObject the EObject being validated
	 */
	public ValidationTimerTask(EObject validatedEObject) {
		super();
		this.validatedEObject = validatedEObject;
	}

	@Override
	public void run() {
		if (!cancelled) {
			Activator.getDefault().getReportService()
				.report(new AbstractReport(MessageFormat.format(
					"Validation took longer than expected for EObject {0}", validatedEObject, //$NON-NLS-1$
					IStatus.INFO)));
		}
		validatedEObject = null;
	}

	@Override
	public boolean cancel() {
		cancelled = true;
		validatedEObject = null;
		return super.cancel();
	}
}