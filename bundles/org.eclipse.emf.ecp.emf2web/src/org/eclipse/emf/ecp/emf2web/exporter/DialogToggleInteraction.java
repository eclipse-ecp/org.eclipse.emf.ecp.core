/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.exporter;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Stefan Dirix
 *
 */
public class DialogToggleInteraction implements UserInteraction {

	private boolean askAgain;
	private int lastAnswer;

	/**
	 * Constructor.
	 */
	public DialogToggleInteraction() {
		askAgain = true;
		lastAnswer = SWT.NO;
	}

	@Override
	public int askQuestion(String title, String question, String toggleQuestion) {
		if (!askAgain) {
			return lastAnswer;
		}

		final Shell shell = Display.getDefault().getActiveShell();
		final MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoCancelQuestion(shell, title,
			question,
			toggleQuestion, false, null, null);
		if (dialog.getToggleState()) {
			askAgain = false;
		}
		lastAnswer = getAnswerCode(dialog.getReturnCode());
		return lastAnswer;
	}

	private int getAnswerCode(int dialogCode) {
		switch (dialogCode) {
		case IDialogConstants.YES_ID:
			return YES;
		case IDialogConstants.NO_ID:
			return NO;
		case IDialogConstants.CANCEL_ID:
			return CANCEL;
		default:
			return NO;
		}
	}

}
