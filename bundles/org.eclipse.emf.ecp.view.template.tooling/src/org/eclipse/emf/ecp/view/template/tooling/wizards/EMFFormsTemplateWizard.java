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
import java.util.Arrays;

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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceImpl;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTTemplateFactory;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;
import org.eclipse.emf.ecp.view.template.tooling.editor.TemplateModelEditorPart;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
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

	private ISelection selection;

	private final Optional<VElement> vElement;

	private Optional<EMFFormsChooseTemplateWizardPage> choosePage = Optional.empty();
	private Optional<EMFFormsNewTemplateWizardPage> newPage = Optional.empty();
	private Optional<EMFFormsSelectTemplateWizardPage> selectPage = Optional.empty();
	private Optional<EMFFormsStyleSelectorWizardPage> selectorPage = Optional.empty();

	/**
	 * Constructor for EMFFormsTemplateWizard. This wizard allows you to create a new template model.
	 */
	public EMFFormsTemplateWizard() {
		this(null);
	}

	/**
	 * Constructs a wizard which will help you to create styles for the {@link VElement}. The style may be added to a
	 * new template model or to an existing one.
	 *
	 * @param vElement the element
	 */
	public EMFFormsTemplateWizard(VElement vElement) {
		super();
		setNeedsProgressMonitor(true);
		this.vElement = Optional.ofNullable(vElement);

	}

	/**
	 * Adding the paged to the wizard. If a {@link VElement} was passed to the constructor creating a style is enabled.
	 */
	@Override
	public void addPages() {
		if (vElement.isPresent()) {
			choosePage = Optional.of(new EMFFormsChooseTemplateWizardPage());
			addPage(choosePage.get());
		}

		newPage = Optional.of(new EMFFormsNewTemplateWizardPage(selection));
		addPage(newPage.get());

		if (vElement.isPresent()) {
			selectPage = Optional.of(new EMFFormsSelectTemplateWizardPage());
			addPage(selectPage.get());
		}

		if (vElement.isPresent()) {
			selectorPage = Optional.of(new EMFFormsStyleSelectorWizardPage(VControl.class.isInstance(vElement.get())));
			addPage(selectorPage.get());
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		/*
		 * if we may choose between creating a new template model or an existing one show the new page or the select
		 * page accordingly
		 */
		if (choosePage.isPresent() && page == choosePage.get()) {
			if (choosePage.get().createNewTemplate()) {
				return newPage.get();
			}
			return selectPage.get();
		}

		/* if we allow to create a style (selector page is present) show the selector page after the new/select page */
		if (newPage.isPresent() && page == newPage.get() ||
			selectPage.isPresent() && page == selectPage.get()) {
			if (selectorPage.isPresent()) {
				return selectorPage.get();
			}
			return null;
		}

		return super.getNextPage(page);
	}

	@Override
	public boolean canFinish() {
		if (!vElement.isPresent()) {
			/* we only have one page (new) -> super call is sufficient */
			return super.canFinish();
		}
		/* otherwise check if all pages are filled as expected */
		for (int i = 0; i < getPages().length; i++) {
			final IWizardPage page = getPages()[i];
			if (choosePage.get().createNewTemplate() && selectPage.get() == page) {
				continue;
			}
			if (!choosePage.get().createNewTemplate() && newPage.get() == page) {
				continue;
			}
			if (!page.isPageComplete()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean performFinish() {
		if (!vElement.isPresent()) {
			/* we dont allow to create a style -> simply create a new template */
			return performFinishNewPage();
		}

		/*
		 * register a listener on the active page. once the template editor shows, we will access the template and add
		 * the style we created. The editor will become dirty because of this, so the user may still discard/undo the
		 * change.
		 */
		final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		activePage.addPartListener(new AddToTemplatePartListener(activePage));

		/* (create template and) open editor */
		if (choosePage.get().createNewTemplate()) {
			if (!performFinishNewPage()) {
				return false;
			}
		} else {
			/* open the editor for the existing template model */
			final IFile resource = (IFile) ResourcesPlugin.getWorkspace().getRoot()
				.findMember(selectPage.get().getTemplateName());
			getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(page, resource, true);
					} catch (final PartInitException e) {
						ErrorDialog.openError(
							getShell(),
							Messages.EMFFormsTemplateWizard_OpenEditorFailTitle,
							Messages.EMFFormsTemplateWizard_OpenEditorFailMessage,
							new Status(
								IStatus.ERROR,
								Activator.PLUGIN_ID,
								e.getMessage(),
								e));
					}
				}
			});
		}
		return true;
	}

	private boolean addStyleToTemplate(
		final VTStyleSelector styleSelector,
		final TemplateModelEditorPart templateEditor) {

		final VTViewTemplate template = templateEditor.getTemplate();
		if (template == null) {
			return false;
		}

		/* check if there is a style with the same selector already present */
		final TreeIterator<EObject> templateContents = EcoreUtil.getAllContents(template, true);
		while (templateContents.hasNext()) {
			final EObject next = templateContents.next();
			if (!VTStyleSelector.class.isInstance(next)) {
				continue;
			}
			if (EcoreUtil.equals(next, styleSelector)) {
				/* we have found a style. instead of creating a second style, reveal the existing one. */
				templateEditor.reveal(next);
				return true;
			}
		}

		/* we have to create a new style */
		final VTStyle style = VTTemplateFactory.eINSTANCE.createStyle();
		style.setSelector(styleSelector);

		final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(template);
		Command command = AddCommand.create(domain, template,
			VTTemplatePackage.eINSTANCE.getViewTemplate_Styles(), style);

		if (VTDomainModelReferenceSelector.class.isInstance(styleSelector)) {
			EObject view = vElement.get();
			while (!VView.class.isInstance(view) && view != null) {
				view = view.eContainer();
			}
			command = addEcorePathIfRequired(template, domain, command, view);
		}

		if (!command.canExecute()) {
			return false;
		}
		domain.getCommandStack().execute(command);
		templateEditor.reveal(styleSelector);

		return true;
	}

	private Command addEcorePathIfRequired(
		final VTViewTemplate template,
		final EditingDomain domain,
		Command command,
		EObject view) {
		if (view == null) {
			return command;
		}
		final String ecorePath = VView.class.cast(view).getEcorePath();
		if (template.getReferencedEcores().contains(ecorePath)) {
			return command;
		}
		final Command ecorePathCommand = AddCommand.create(domain, template,
			VTTemplatePackage.eINSTANCE.getViewTemplate_ReferencedEcores(), ecorePath);
		return new CompoundCommand(Arrays.asList(command, ecorePathCommand));
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
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
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
		final IStatus status = new Status(IStatus.ERROR, "org.eclipse.emf.ecp.view.template.tooling", IStatus.OK, //$NON-NLS-1$
			message, null);
		throw new CoreException(status);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	private static Optional<String> getUUID(VElement vElement) {
		final Resource eResource = vElement.eResource();
		if (!VViewResourceImpl.class.isInstance(eResource)) {
			return Optional.empty();
		}
		return Optional.ofNullable(VViewResourceImpl.class.cast(eResource).getID(vElement));
	}

	/**
	 * Listener which waits for the template editor to open and add a style to it.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class AddToTemplatePartListener implements IPartListener2 {
		private final IWorkbenchPage activePage;

		/**
		 * @param activePage
		 */
		private AddToTemplatePartListener(IWorkbenchPage activePage) {
			this.activePage = activePage;
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			/* no op */
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (!"org.eclipse.emf.ecp.view.template.tooling.editor".equals(partRef.getId())) { //$NON-NLS-1$
				return;
			}

			activePage.removePartListener(this);

			EClass selectorEClass = VTViewModelElementPackage.eINSTANCE.getViewModelElementSelector();
			Optional<Boolean> useUUID = Optional.empty();
			if (selectorPage.isPresent()) {
				selectorEClass = selectorPage.get().getSelectorEClass();
				useUUID = selectorPage.get().getUseUUID();
			}

			final VTStyleSelector styleSelector = (VTStyleSelector) EcoreUtil.create(selectorEClass);
			if (selectorEClass == VTViewModelElementPackage.eINSTANCE.getViewModelElementSelector()) {
				final VTViewModelElementSelector selector = VTViewModelElementSelector.class.cast(styleSelector);
				selector.setClassType(vElement.get().eClass());
				if (useUUID.isPresent() && useUUID.get()) {
					final Optional<String> uuid = getUUID(vElement.get());
					if (uuid.isPresent()) {
						selector.setAttribute(VViewPackage.eINSTANCE.getElement_Uuid());
						selector.setAttributeValue(uuid.get());
					}
				}
			} else if (selectorEClass == VTDomainmodelreferencePackage.eINSTANCE
				.getDomainModelReferenceSelector()) {
				final VTDomainModelReferenceSelector selector = VTDomainModelReferenceSelector.class
					.cast(styleSelector);
				final VDomainModelReference dmr = VControl.class.cast(vElement.get()).getDomainModelReference();
				selector.setDomainModelReference(EcoreUtil.copy(dmr));
			}

			final IEditorPart editor = activePage.getActiveEditor();
			final TemplateModelEditorPart templateEditor = TemplateModelEditorPart.class.cast(editor);
			addStyleToTemplate(styleSelector, templateEditor);
		}

	}
}
