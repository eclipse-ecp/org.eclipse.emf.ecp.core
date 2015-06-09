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
package org.eclipse.emf.ecp.diffmerge.swt;

import org.eclipse.emf.ecp.diffmerge.spi.context.ControlPair;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * The Diff Dialog helper provides methods to open a Diff Dialog.
 *
 * @author Eugen Neufeld
 *
 */
public final class DiffDialogHelper {
	private DiffDialogHelper() {
	}

	/**
	 * This opens a dialog displaying the differences of the elements of the {@link DiffMergeModelContext} based on the
	 * index of the difference. If the index is not valid, less then 0 or greater equals the number of differences, then
	 * an {@link IllegalArgumentException} is thrown.
	 *
	 * @param diffModelContext the {@link DiffMergeModelContext} containing the data
	 * @param diffIndex the index of the difference
	 * @throws IllegalArgumentException if the index is invalid
	 */
	public static void showDialog(DiffMergeModelContext diffModelContext, int diffIndex)
		throws IllegalArgumentException {
		final VControl control = diffModelContext.getControl(diffIndex);
		final String label = getControlLabel(control, diffModelContext);
		showDialog(diffModelContext, control, label);
	}

	/**
	 * Opens a dialog displaying the differences of the elements of the {@link DiffMergeModelContext} based on the
	 * provided {@link VControl}.
	 *
	 * @param diffModelContext the {@link DiffMergeModelContext} containing the data
	 * @param vControl the {@link VControl} having the differences
	 * @param featureLabel the feature text to use in the title of the dialog
	 * @throws IllegalArgumentException if the controls doesn't have a difference
	 */
	public static void showDialog(DiffMergeModelContext diffModelContext, VControl vControl, String featureLabel)
		throws IllegalArgumentException {
		final ControlPair pairWithDiff = diffModelContext.getPairWithDiff(vControl);
		if (pairWithDiff == null) {
			return;
		}
		final DiffDialog dialog = new DiffDialog(diffModelContext, featureLabel, pairWithDiff.getLeftControl(),
			pairWithDiff.getRightControl(), vControl);

		final Shell shell = new Shell(SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE);
		final int index = diffModelContext.getIndexOf(vControl);
		if (index == -1) {
			throw new IllegalArgumentException("This VControl doesn't has a difference."); //$NON-NLS-1$
		}
		shell
			.setText(String.format(
				LocalizationServiceHelper.getString(DiffDialogHelper.class, MessageKeys.DiffDialog_title),
				featureLabel, index + 1, diffModelContext.getTotalNumberOfDiffs()));
		shell.setLayout(new FillLayout());
		final Rectangle clientArea = shell.getDisplay().getBounds();
		shell.setSize(clientArea.width / 2, 500);
		dialog.create(shell);
		shell.setLocation(clientArea.width / 4, clientArea.height / 4);
		shell.open();
	}

	private static String getControlLabel(VControl control, DiffMergeModelContext diffModelContext) {
		try {
			return (String) Activator.getInstance().getEMFFormsLabelProvider()
				.getDisplayName(control.getDomainModelReference(), diffModelContext.getDomainModel()).getValue();
		} catch (final NoLabelFoundException e) {
			return e.getMessage();
		}
	}
}
