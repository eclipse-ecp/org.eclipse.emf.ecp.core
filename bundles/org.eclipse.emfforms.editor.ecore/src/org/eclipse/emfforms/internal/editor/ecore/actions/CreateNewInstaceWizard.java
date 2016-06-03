/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * A wizard to create a new instance of an {@link EClass} and save it into a file.
 *
 * @author Lucas Koehler
 *
 */
public class CreateNewInstaceWizard extends Wizard {

	/**
	 * The supported extensions for created files.
	 */
	public static final List<String> FILE_EXTENSIONS = Collections.singletonList("xmi");

	/**
	 * A formatted list of supported file extensions, suitable for display.
	 */
	public static final String FORMATTED_FILE_EXTENSIONS = ".xmi";

	private CreateNewInstanceFileCreationPage newFileCreationPage;

	/**
	 * The EClass for which a new instance is created and saved.
	 */
	private final EClass eClass;

	/**
	 * Default constructor.
	 *
	 * @param eClass The {@link EClass} for which a new instance is created and saved in a file
	 */
	public CreateNewInstaceWizard(EClass eClass) {
		this.eClass = eClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {

		// Remember the file.
		final IFile modelFile = getModelFile();

		// Do the work within an operation.
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				// Create a resource set
				final ResourceSet resourceSet = new ResourceSetImpl();

				// Get the URI of the model file.
				final URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);

				// Create a resource for this file.
				final Resource resource = resourceSet.createResource(fileURI);

				final EObject instance = EcoreUtil.create(eClass);
				resource.getContents().add(instance);

				// Save the contents of the resource to the file system.
				final Map<Object, Object> options = new HashMap<Object, Object>();
				options.put(XMLResource.OPTION_ENCODING, "UTF-8");
				options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
				options.put(XMLResource.OPTION_LINE_WIDTH, 80);
				try {
					resource.save(options);
				} catch (final IOException exception) {
					openErrorDialog(exception);
					return;
				} finally {
					progressMonitor.done();
				}
			}
		};

		try {
			getContainer().run(false, false, operation);
			return true;
		} catch (final InvocationTargetException exception) {
			openErrorDialog(exception);
			return false;
		} catch (final InterruptedException exception) {
			openErrorDialog(exception);
			return false;
		}
	}

	/**
	 * @param exception The {@link Exception} that caused the error.
	 */
	private void openErrorDialog(final Exception exception) {
		final Status status = new Status(IStatus.ERROR, "org.eclipse.emfforms.editor.ecore",
			"Could not create instance file.", exception);
		ErrorDialog.openError(getShell(), "Error", null, status);
	}

	/**
	 * The wizard page to select in which file the new instance will be saved.
	 */
	public class CreateNewInstanceFileCreationPage extends WizardNewFileCreationPage {
		/**
		 * Default constructor.
		 *
		 * @param pageId This page's id
		 * @param selection The current selection
		 */
		public CreateNewInstanceFileCreationPage(String pageId, IStructuredSelection selection) {
			super(pageId, selection);
		}

		/**
		 * The framework calls this to see if the file is correct.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		@Override
		protected boolean validatePage() {
			if (super.validatePage()) {
				final String extension = new Path(getFileName()).getFileExtension();
				if (extension == null || !FILE_EXTENSIONS.contains(extension)) {
					setErrorMessage("Wrong file extension: Use " + FORMATTED_FILE_EXTENSIONS);
					return false;
				}
				return true;
			}
			return false;
		}

		public IFile getModelFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		// Create a page, set the title, and the initial model file name.
		newFileCreationPage = new CreateNewInstanceFileCreationPage("CreatePage", new StructuredSelection(eClass));
		final String className = eClass.getName();
		newFileCreationPage.setTitle("Create New Instance In File");
		newFileCreationPage.setDescription("Create a new file to save a new instance of EClass " + className + ".");
		newFileCreationPage.setFileName(className + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);
	}

	/**
	 * @return the file from the file creation page.
	 */
	public IFile getModelFile() {
		return newFileCreationPage.getModelFile();
	}

}
