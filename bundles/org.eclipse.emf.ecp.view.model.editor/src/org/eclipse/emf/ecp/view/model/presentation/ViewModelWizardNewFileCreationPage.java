/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.presentation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * @author Alexandra Buzila
 *
 */
/**
 * This is the first page of the wizard. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 *
 * @generated
 */
public class ViewModelWizardNewFileCreationPage extends
	WizardNewFileCreationPage {

	/**
	 * The supported extensions for created files. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<String> FILE_EXTENSIONS = Collections
		.unmodifiableList(Arrays.asList(ViewEditorPlugin.INSTANCE
			.getString("_UI_ViewEditorFilenameExtensions").split( //$NON-NLS-1$
				"\\s*,\\s*"))); //$NON-NLS-1$

	/**
	 * A formatted list of supported file extensions, suitable for display. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final String FORMATTED_FILE_EXTENSIONS = ViewEditorPlugin.INSTANCE
		.getString("_UI_ViewEditorFilenameExtensions").replaceAll( //$NON-NLS-1$
			"\\s*,\\s*", ", "); //$NON-NLS-1$ //$NON-NLS-2$

	private EClass eClass;
	private final IStructuredSelection selection;

	/**
	 * @return the eClass
	 */
	public EClass getEClass() {
		return eClass;
	}

	/**
	 * @param eClass the eClass to set
	 */
	public void setEClass(EClass eClass) {
		this.eClass = eClass;
	}

	/**
	 * Pass in the selection. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ViewModelWizardNewFileCreationPage(String pageId,
		IStructuredSelection selection) {
		super(pageId, selection);
		this.selection = selection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			// Try and get the resource selection to determine a current directory
			// for the file dialog.
			if (selection != null && !selection.isEmpty()) {
				// Get the resource...
				final Object selectedElement = selection.iterator().next();
				if (selectedElement instanceof IResource) {
					// Get the resource parent, if its a file.
					IResource selectedResource = (IResource) selectedElement;
					if (selectedResource.getType() == IResource.FILE) {
						selectedResource = selectedResource.getParent();
					}

					// This gives us a directory...
					if (selectedResource instanceof IFolder
						|| selectedResource instanceof IProject) {
						// Set this for the container.
						setContainerFullPath(selectedResource
							.getFullPath());
					}
					// Make up a unique new name here.
					final String defaultModelBaseFilename = eClass.getName();
					final String defaultModelFilenameExtension = FILE_EXTENSIONS
						.get(0);
					String modelFilename = defaultModelBaseFilename + "." //$NON-NLS-1$
						+ defaultModelFilenameExtension;
					for (int i = 1; ((IContainer) selectedResource)
						.findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." //$NON-NLS-1$
							+ defaultModelFilenameExtension;
					}
					setFileName(modelFilename);
					setPageComplete(true);
				}
			}
		} else {
			setPageComplete(false);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}

	/**
	 * The framework calls this to see if the file is correct. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validatePage() {
		if (super.validatePage()) {
			// validate extension
			final String extension = new Path(getFileName()).getFileExtension();
			if (extension == null || !FILE_EXTENSIONS.contains(extension)) {
				final String key = FILE_EXTENSIONS.size() > 1 ? "_WARN_FilenameExtensions" //$NON-NLS-1$
					: "_WARN_FilenameExtension"; //$NON-NLS-1$
				setErrorMessage(ViewEditorPlugin.INSTANCE.getString(key,
					new Object[] { FORMATTED_FILE_EXTENSIONS }));
				return false;
			}
			// validate selected project
			checkProjectNature();

			return true;
		}
		return false;
	}

	private void checkProjectNature() {
		if (getModelFile() == null) {
			return;
		}
		final IProject project = getModelFile().getProject();
		try {
			if (!project.hasNature("org.eclipse.pde.PluginNature")) { //$NON-NLS-1$
				setMessage(ViewEditorPlugin.INSTANCE.getString("_WARN_PluginProject"), IMessageProvider.WARNING); //$NON-NLS-1$
			}
		} catch (final CoreException ex) {
			// do nothing
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IFile getModelFile() {
		if (getControl() == null || getContainerFullPath() == null || getFileName() == null) {
			return null;
		}
		return ResourcesPlugin.getWorkspace().getRoot()
			.getFile(getContainerFullPath().append(getFileName()));
	}

}
