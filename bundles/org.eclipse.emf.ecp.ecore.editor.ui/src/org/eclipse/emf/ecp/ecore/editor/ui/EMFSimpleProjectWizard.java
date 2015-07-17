/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation, implementation based
 * org.eclipse.emf.ecore.presentation.EcoreModelWizard and
 * org.eclipse.emf.importer.ui.EMFProjectWizard
 ******************************************************************************/
/**
 *
 * Copyright (c) 2005-2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *
 */
package org.eclipse.emf.ecp.ecore.editor.ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelEditPlugin;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.presentation.EcoreEditorPlugin;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecp.ecore.editor.ui.operations.CreateModelsWorkspaceModifyOperation;
import org.eclipse.emf.ecp.ecore.editor.util.ProjectHelper;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 *
 * A wizard for creating a simple EMF project with an ecore and a genmodel file.
 *
 */
public class EMFSimpleProjectWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;
	private IPath genModelProjectLocation;
	private IPath genModelContainerPath;
	private IProject project;
	private String initialProjectName;
	private IStructuredSelection selection;
	private WizardNewProjectCreationPage newProjectCreationPage;

	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		super.setContainer(wizardContainer);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
			.getImageDescriptor(GenModelEditPlugin.INSTANCE
				.getImage("full/wizban/NewEmptyEMFProject")));
		setWindowTitle(GenModelEditPlugin.INSTANCE
			.getString("_UI_NewEmptyProject_title"));

	}

	@Override
	public void addPages() {
		newProjectCreationPage = new WizardNewProjectCreationPage(
			"NewProjectCreationPage") {
			@Override
			protected boolean validatePage() {
				if (super.validatePage()) {
					final IPath locationPath = getLocationPath();
					genModelProjectLocation = Platform.getLocation().equals(
						locationPath) ? null : locationPath;
					final IPath projectPath = getProjectHandle().getFullPath();
					genModelContainerPath = projectPath.append("src");
					return true;
				}
				return false;
			}

			@Override
			public void createControl(Composite parent) {
				super.createControl(parent);
				createWorkingSetGroup((Composite) getControl(), selection,
					new String[] { "org.eclipse.jdt.ui.JavaWorkingSetPage",
						"org.eclipse.pde.ui.pluginWorkingSet",
						"org.eclipse.ui.resourceWorkingSetPage" });
			}
		};
		newProjectCreationPage.setInitialProjectName(initialProjectName);
		newProjectCreationPage.setTitle(GenModelEditPlugin.INSTANCE
			.getString("_UI_EmptyProject_title"));
		newProjectCreationPage.setDescription(GenModelEditPlugin.INSTANCE
			.getString("_UI_EmptyProject_description"));

		addPage(newProjectCreationPage);
	}

	@Override
	public boolean performFinish() {
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				try {
					modifyWorkspace(progressMonitor);
				} catch (final UnsupportedEncodingException ex) {
					GenModelEditPlugin.INSTANCE.log(ex);
				} catch (final CoreException ex) {
					GenModelEditPlugin.INSTANCE.log(ex);
				} catch (final IOException ex) {
					GenModelEditPlugin.INSTANCE.log(ex);
				} finally {
					progressMonitor.done();
				}
			}
		};

		try {
			getContainer().run(false, false, operation);
		} catch (final InvocationTargetException ex) {
			GenModelEditPlugin.INSTANCE.log(ex);
			return false;
		} catch (final InterruptedException ex) {
			GenModelEditPlugin.INSTANCE.log(ex);
			return false;
		}

		if (project != null) {
			final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
			final IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				final ISelection targetSelection = new StructuredSelection(
					project);
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						((ISetSelectionTarget) activePart)
							.selectReveal(targetSelection);
					}
				});
			}
		}
		final ProjectHelper helper = new ProjectHelper(newProjectCreationPage.getProjectName());
		final String filePrefix = helper.getProjectName().length() == 0 ? "model"
			: helper.getProjectName();
		final IFile modelFile = getModelFile(newProjectCreationPage.getProjectName(),
			filePrefix + ".ecore");

		// Do the work within an operation.
		final WorkspaceModifyOperation createModelsOperation = new CreateModelsWorkspaceModifyOperation(
			modelFile, "EPackage", "UTF-8",
			newProjectCreationPage.getProjectName());

		try {
			getContainer().run(false, false, createModelsOperation);
		} catch (final InvocationTargetException ex) {
			EcoreEditorPlugin.INSTANCE.log(ex);
			return false;
		} catch (final InterruptedException ex) {
			EcoreEditorPlugin.INSTANCE.log(ex);
			return false;
		}

		// Select the new file resource in the current view.
		final IWorkbenchWindow workbenchWindow = workbench
			.getActiveWorkbenchWindow();
		final IWorkbenchPage page = workbenchWindow.getActivePage();
		final IWorkbenchPart activePart = page.getActivePart();
		if (activePart instanceof ISetSelectionTarget) {
			final ISelection targetSelection = new StructuredSelection(
				modelFile);
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					((ISetSelectionTarget) activePart)
						.selectReveal(targetSelection);
				}
			});
		}

		return openFile(modelFile, workbenchWindow, page);
	}

	private boolean openFile(final IFile modelFile, final IWorkbenchWindow workbenchWindow, final IWorkbenchPage page) {
		// Open an editor on the new file.
		try {
			final IEditorDescriptor defaultEditor = workbench.getEditorRegistry()
				.getDefaultEditor(modelFile.getFullPath().toString());
			page.openEditor(
				new FileEditorInput(modelFile),
				defaultEditor == null ? "org.eclipse.emf.ecore.presentation.EcoreEditorID"
					: defaultEditor.getId());
		} catch (final PartInitException exception) {
			MessageDialog.openError(workbenchWindow.getShell(),
				EcoreEditorPlugin.INSTANCE.getString("_UI_OpenEditorError_label"),
				exception.getMessage());
			return false;
		}

		return true;
	}

	private void modifyWorkspace(IProgressMonitor progressMonitor)
		throws CoreException, UnsupportedEncodingException, IOException {
		project = Generator.createEMFProject(
			new Path(genModelContainerPath.toString()),
			genModelProjectLocation, Collections.<IProject> emptyList(),
			progressMonitor, Generator.EMF_MODEL_PROJECT_STYLE
				| Generator.EMF_PLUGIN_PROJECT_STYLE);

		final IWorkingSet[] workingSets = newProjectCreationPage
			.getSelectedWorkingSets();
		if (workingSets != null) {
			workbench.getWorkingSetManager().addToWorkingSets(project,
				workingSets);
		}

		CodeGenUtil.EclipseUtil.findOrCreateContainer(new Path("/"
			+ genModelContainerPath.segment(0) + "/model"), true,
			genModelProjectLocation, progressMonitor);

		final PrintStream manifest = new PrintStream(
			URIConverter.INSTANCE.createOutputStream(
				URI.createPlatformResourceURI("/"
					+ genModelContainerPath.segment(0)
					+ "/META-INF/MANIFEST.MF", true), null), false,
			"UTF-8");
		manifest.println("Manifest-Version: 1.0");
		manifest.println("Bundle-ManifestVersion: 2");
		manifest.print("Bundle-Name: ");
		manifest.println(genModelContainerPath.segment(0));
		manifest.print("Bundle-SymbolicName: ");
		manifest.print(CodeGenUtil.validPluginID(genModelContainerPath
			.segment(0)));
		manifest.println("; singleton:=true");
		manifest.println("Bundle-Version: 0.1.0.qualifier");
		manifest.print("Require-Bundle: ");
		final String[] requiredBundles = getRequiredBundles();
		for (int i = 0, size = requiredBundles.length; i < size;) {
			manifest.print(requiredBundles[i]);
			if (++i == size) {
				manifest.println();
				break;
			}
			manifest.println(",");
			manifest.print(" ");
		}
		manifest.close();
	}

	private String[] getRequiredBundles() {
		return new String[] { "org.eclipse.emf.ecore" };
	}

	private IFile getModelFile(String projectName, String fileName) {
		return ResourcesPlugin
			.getWorkspace()
			.getRoot()
			.getFile(
				new Path(new File(projectName + "/model/" + fileName)
					.getPath()));
	}
}
