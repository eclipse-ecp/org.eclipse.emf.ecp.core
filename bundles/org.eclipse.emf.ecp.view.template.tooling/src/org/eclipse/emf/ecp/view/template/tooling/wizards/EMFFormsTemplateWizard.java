/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "template". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class EMFFormsTemplateWizard extends Wizard implements INewWizard {
	private EMFFormsTemplateWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for EMFFormsTemplateWizard.
	 */
	public EMFFormsTemplateWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new EMFFormsTemplateWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
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
			MessageDialog.openError(getShell(), Messages.EMFFormsTemplateWizard_errorTitle, realException.getMessage());
			return false;
		}
		return true;
	}

	private void addToPluginXML(IFile modelFile, IProgressMonitor monitor) {
		final IProject project = modelFile.getProject();
		final IFile pluginFile = project.getFile("plugin.xml"); //$NON-NLS-1$

		try {
			if (!pluginFile.exists()) {
				final String pluginXmlContents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<?eclipse version=\"3.4\"?>\n<plugin>\n</plugin>"; //$NON-NLS-1$
				pluginFile.create(new ByteArrayInputStream(pluginXmlContents.getBytes()), true, null);
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.parse(pluginFile.getLocationURI().toString());
			final Element element = doc.createElement("extension"); //$NON-NLS-1$
			element.setAttribute("point", "org.eclipse.emf.ecp.view.template"); //$NON-NLS-1$ //$NON-NLS-2$
			final Element templateElement = doc.createElement("viewTemplate"); //$NON-NLS-1$
			templateElement.setAttribute("xmi", modelFile.getProjectRelativePath().toString()); //$NON-NLS-1$
			element.appendChild(templateElement);
			doc.getDocumentElement().appendChild(element);

			final Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			tf.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			final StreamResult file = new StreamResult(pluginFile.getLocation().toFile());
			tf.transform(new DOMSource(doc), file);
		} catch (final SAXException ex) {
			Activator.log(ex);
		} catch (final IOException ex) {
			Activator.log(ex);
		} catch (final ParserConfigurationException ex) {
			Activator.log(ex);
		} catch (final TransformerException ex) {
			Activator.log(ex);
		} catch (final CoreException ex) {
			Activator.log(ex);
		}
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
		String containerName,
		String fileName,
		IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask(Messages.EMFFormsTemplateWizard_creatingTask + fileName, 2);
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException(String.format("Container \"%s\" does not exist.", containerName)); //$NON-NLS-1$
		}
		final IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			final VTViewTemplate template = VTTemplateFactory.eINSTANCE.createViewTemplate();

			final ResourceSet rs = new ResourceSetImpl();
			final Resource templateResource = rs.createResource(URI.createURI(file.getLocationURI().toString()));
			templateResource.getContents().add(template);
			templateResource.save(null);
			container.refreshLocal(IResource.DEPTH_ONE, monitor);
		} catch (final IOException e) {

		}
		monitor.worked(1);
		monitor.setTaskName(Messages.EMFFormsTemplateWizard_editingTask);
		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (final PartInitException e) {
				}
			}
		});
		monitor.worked(1);
		addToPluginXML(file, monitor);
	}

	private void throwCoreException(String message) throws CoreException {
		final IStatus status =
			new Status(IStatus.ERROR, "org.eclipse.emf.ecp.view.template.tooling", IStatus.OK, message, null); //$NON-NLS-1$
		throw new CoreException(status);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}
