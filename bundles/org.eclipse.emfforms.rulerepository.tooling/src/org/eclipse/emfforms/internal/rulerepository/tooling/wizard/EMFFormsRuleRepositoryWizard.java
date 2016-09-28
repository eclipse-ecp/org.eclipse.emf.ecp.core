/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.rulerepository.tooling.wizard;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "rulerepository". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class EMFFormsRuleRepositoryWizard extends Wizard implements INewWizard {

	private ISelection selection;

	private Optional<EMFFormsNewRuleRepositoryWizardPage> newPage = Optional.empty();

	/**
	 * Constructor for EMFFormsRuleRepositoryWizard. This wizard allows you to create a new rulerepository model.
	 */
	public EMFFormsRuleRepositoryWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {

		newPage = Optional.of(new EMFFormsNewRuleRepositoryWizardPage(selection));
		addPage(newPage.get());

	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		/* if we allow to create a style (selector page is present) show the selector page after the new/select page */
		if (newPage.isPresent() && page == newPage.get()) {
			return null;
		}

		return super.getNextPage(page);
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}

	@Override
	public boolean performFinish() {
		return performFinishNewPage();
	}

	private boolean performFinishNewPage() {
		final String containerName = newPage.get().getContainerName();
		final String fileName = newPage.get().getFileName();
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (final CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Messages.EMFFormsRuleRepositoryWizard_errorTitle,
				realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(String containerName, String fileName, final IProgressMonitor monitor) throws CoreException {
		// create a sample file
		monitor.beginTask(Messages.EMFFormsRuleRepositoryWizard_creatingTask + fileName, 2);
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException(String.format("Container \"%s\" does not exist.", containerName)); //$NON-NLS-1$
		}
		final IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			final VRuleRepository ruleRepository = VRulerepositoryFactory.eINSTANCE.createRuleRepository();

			final ResourceSet rs = new ResourceSetImpl();
			final Resource ruleRepositoryResource = rs.createResource(URI.createURI(file.getLocationURI().toString()));
			ruleRepositoryResource.getContents().add(ruleRepository);
			ruleRepositoryResource.save(null);
			container.refreshLocal(IResource.DEPTH_ONE, monitor);
		} catch (final IOException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage()); //$NON-NLS-1$
			monitor.done();
			return;
		}
		monitor.worked(1);
		monitor.setTaskName(Messages.EMFFormsRuleRepositoryWizard_editingTask);
		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (final PartInitException e) {
					MessageDialog.openError(getShell(), "Error", e.getMessage()); //$NON-NLS-1$
					monitor.done();
					return;
				}
			}
		});
		monitor.worked(1);
	}

	private void throwCoreException(String message) throws CoreException {
		final IStatus status = new Status(IStatus.ERROR, "org.eclipse.emfforms.rulerepository.tooling", IStatus.OK, //$NON-NLS-1$
			message, null);
		throw new CoreException(status);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
