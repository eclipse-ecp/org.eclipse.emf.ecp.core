/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling.util;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * Helper class that provides a ListSelectionDialog for selecting template models that should be migrated.
 *
 * @author Lucas Koehler
 *
 */
// TODO this is in parts duplicated from the org.eclipse.emf.ecp.ide.editor.view bundle and should be refactored in the
// future
public final class MigrationDialogHelper {

	private MigrationDialogHelper() {
	}

	/**
	 * Returns a {@link ListSelectionDialog} for selecting template models that should be migrated.
	 *
	 * @param parentShell the parent shell of the dialog
	 * @param input the list of template model URIs to be presented to the user
	 * @return the dialog
	 */
	public static ListSelectionDialog getTemplateModelListMigrationDialog(Shell parentShell, List<URI> input) {
		final IStructuredContentProvider contentProvider = new ArrayContentProvider();
		final ILabelProvider labelProvider = new ListMigrationDialogLabelProvider();
		final ListSelectionDialog dialog = new ListSelectionDialog(parentShell, input, contentProvider, labelProvider,
			Messages.MigrationDialogHelper_TemplatesTitle);
		dialog.setTitle(Messages.MigrationDialogHelper_TemplatesDescription);
		dialog.setHelpAvailable(false);
		dialog.setInitialElementSelections(input);
		return dialog;
	}
}

/** Label provider for the Migration ListSelectionDialog. */
class ListMigrationDialogLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (!URI.class.isInstance(element)) {
			return super.getText(element);
		}
		final URI uri = (URI) element;
		final String filePath = uri.devicePath();
		final String platformPath = Platform.getLocation().toString();
		String text = filePath;
		if (filePath.contains(platformPath)) {
			final int startIndex = filePath.indexOf(platformPath);
			text = filePath.substring(startIndex + 1 + platformPath.length());
		}
		return String.format("%s [%s]", uri.lastSegment(), text); //$NON-NLS-1$
	}

}