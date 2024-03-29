/**
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
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.model.presentation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.eclipse.emf.ecp.view.internal.editor.handler.ControlGenerator;
import org.eclipse.emf.ecp.view.internal.editor.handler.SelectEcorePage;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.ServiceReference;

/**
 * This is a simple wizard for creating a new model file. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewModelWizard extends Wizard implements INewWizard {

	private static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.model.presentation"; //$NON-NLS-1$
	private Object selectedContainer;
	private List<EClass> selectedEClasses;
	private List<ViewModelWizardNewFileCreationPage> fileCreationPages;

	/**
	 * @param selectedContainer the selectedContainer to set
	 */
	public void setSelectedContainer(Object selectedContainer) {
		this.selectedContainer = selectedContainer;
	}

	public void clearSelectedContainer() {
		selectedContainer = null;
		if (selectEcorePage != null) {
			selectEcorePage.setSelectedContainer(null);
		}
	}

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

	/**
	 * This caches an instance of the model package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewPackage viewPackage = VViewPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewFactory viewFactory = viewPackage.getViewFactory();

	/**
	 * This is the file creation page. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */

	private IncludeViewModelProviderXmiFileExtensionPage contributeToFileExtensionPage;

	/**
	 * Remember the selection during initialization for populating the default
	 * container. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * Remember the workbench during initialization. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IWorkbench workbench;

	/**
	 * Caches the names of the types that can be created as the root object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected List<String> initialObjectNames;

	private SelectEcorePage selectEcorePage;
	private SelectEClassForViewWizardPage selectEClassPage;

	/**
	 * This just records the information. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(ViewEditorPlugin.INSTANCE.getString("_UI_Wizard_label")); //$NON-NLS-1$
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE
			.getImageDescriptor(ViewEditorPlugin.INSTANCE
				.getImage("full/wizban/NewView"))); //$NON-NLS-1$
	}

	/**
	 * Returns the names of the types that can be created as the root object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected Collection<String> getInitialObjectNames() {
		if (initialObjectNames == null) {
			initialObjectNames = new ArrayList<String>();
			for (final EClassifier eClassifier : viewPackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					final EClass eClass = (EClass) eClassifier;
					if (!eClass.isAbstract()) {
						initialObjectNames.add(eClass.getName());
					}
				}
			}
			Collections.sort(initialObjectNames,
				CommonPlugin.INSTANCE.getComparator());
		}
		return initialObjectNames;
	}

	/**
	 * The framework calls this to create the contents of the wizard. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void addPages() {
		selectEcorePage = new SelectEcorePage(PLUGIN_ID);
		addPage(selectEcorePage);

		contributeToFileExtensionPage = new IncludeViewModelProviderXmiFileExtensionPage(PLUGIN_ID);
		addPage(contributeToFileExtensionPage);
	}

	@Override
	public IWizardPage getStartingPage() {
		if (selectedContainer == null) {
			return selectEcorePage;
		}

		selectEClassPage = new SelectEClassForViewWizardPage();
		selectEClassPage.setSelectedEPackage(getEPackage());
		selectEClassPage.setPageComplete(true);
		addPage(selectEClassPage);
		return selectEClassPage;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == selectEcorePage) {
			selectedEClasses = null;
			selectedContainer = selectEcorePage.getSelectedContainer();
			if (selectedContainer != null) {
				if (selectEClassPage == null) {
					selectEClassPage = new SelectEClassForViewWizardPage();
					selectEClassPage.setPageComplete(true);
					addPage(selectEClassPage);
				}
				selectEClassPage.setSelectedEPackage(getEPackage());
				return selectEClassPage;
			}
			return null;
		} else if (page == selectEClassPage) {
			selectedEClasses = selectEClassPage.getSelectedEClasses();
			fileCreationPages = new ArrayList<ViewModelWizardNewFileCreationPage>();
			if (selectedEClasses != null && !selectedEClasses.isEmpty()) {
				for (final EClass eclass : selectedEClasses) {
					final ViewModelWizardNewFileCreationPage fileCreationPage = getNewFileCreationPage();
					fileCreationPage.setEClass(eclass);
					fileCreationPage.setWizard(this);
					fileCreationPages.add(fileCreationPage);
				}
				if (fileCreationPages.isEmpty()) {
					return null;
				}
				return fileCreationPages.get(0);
			}
		} else if (ViewModelWizardNewFileCreationPage.class.isInstance(page)) {
			final int i = fileCreationPages.indexOf(page);
			if (i < fileCreationPages.size() - 1) {
				return fileCreationPages.get(i + 1);
			}
			if (shouldShowFileExtensionPage()) {
				return contributeToFileExtensionPage;
			}
		}
		return null;
	}

	/**
	 * @return <code>true</code> if at least one of the locations in which the viewmodels are created is a Plugin
	 *         Project
	 */
	private boolean shouldShowFileExtensionPage() {
		for (final ViewModelWizardNewFileCreationPage page : fileCreationPages) {
			if (viewModelContainerIsPluginProject(page)) {
				return true;
			}
		}
		return false;
	}

	private EPackage getEPackage() {
		EPackage ePackage = null;
		if (EPackage.class.isInstance(selectedContainer)) {
			ePackage = EPackage.class.cast(selectedContainer);
		} else if (IFile.class.isInstance(selectedContainer)) {
			final ResourceSetImpl resourceSet = new ResourceSetImpl();
			// needed to be able to resolve resource paths to plugin paths and thus load referenced ecores
			resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));
			resourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);

			final String path = ((IFile) selectedContainer).getFullPath().toString();
			final URI uri = URI.createPlatformResourceURI(path, true);

			final Resource resource = resourceSet.getResource(uri, true);
			if (resource != null) {
				final EList<EObject> contents = resource.getContents();
				if (contents.size() != 1) {
					return null;
				}
				final EObject object = contents.get(0);
				if (!(object instanceof EPackage)) {
					return null;
				}
				ePackage = (EPackage) object;
			}
		}
		return ePackage;
	}

	private ViewModelWizardNewFileCreationPage getNewFileCreationPage() {
		// Create page, set title and the initial model file name.
		final ViewModelWizardNewFileCreationPage newFileCreationPage = new ViewModelWizardNewFileCreationPage(
			"Whatever", selection); //$NON-NLS-1$
		newFileCreationPage.setTitle(ViewEditorPlugin.INSTANCE
			.getString("_UI_ViewModelWizard_label")); //$NON-NLS-1$
		newFileCreationPage.setDescription(ViewEditorPlugin.INSTANCE
			.getString("_UI_ViewModelWizard_description")); //$NON-NLS-1$
		newFileCreationPage.setFileName(ViewEditorPlugin.INSTANCE
			.getString("_UI_ViewEditorFilenameDefaultBase") //$NON-NLS-1$
			+ "." //$NON-NLS-1$
			+ FILE_EXTENSIONS.get(0));

		return newFileCreationPage;
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page == selectEClassPage) {
			return selectEcorePage;
		} else if (ViewModelWizardNewFileCreationPage.class.isInstance(page)) {
			return selectEClassPage;
		}
		return null;
	}

	@Override
	public boolean canFinish() {
		if (selectEClassPage != null) {
			final List<EClass> selectedClasses = selectEClassPage.getSelectedEClasses();
			if (selectedClasses == null || selectedClasses.isEmpty()) {
				return false;
			}
			if (fileCreationPages == null || fileCreationPages.isEmpty()) {
				return false;
			}
			for (final ViewModelWizardNewFileCreationPage page : fileCreationPages) {
				if (page.getModelFile() == null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the project where the view model will be created is a plugin project.
	 *
	 * @return true if the container project is a plugin project
	 */
	private boolean viewModelContainerIsPluginProject(ViewModelWizardNewFileCreationPage page) {
		if (fileCreationPages == null || fileCreationPages.isEmpty()) {
			return false;
		}
		if (page.getModelFile() == null || page.getModelFile().getProject() == null) {
			return false;
		}
		final IProject project = page.getModelFile().getProject();
		return ContributionUtil.isPluginProject(project);
	}

	/**
	 * Do the work after everything is specified. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean performFinish() {
		try {
			// Do the work within an operation.
			final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						final boolean generateViewModelControls = selectEClassPage
							.isGenerateViewModelOptionSelected();

						final IDEViewModelRegistry registry = getViewModelRegistry();
						if (registry == null) {
							ViewEditorPlugin.INSTANCE.log(new Status(IStatus.ERROR, PLUGIN_ID,
								"No View Model Registry", null)); //$NON-NLS-1$
							return;
						}
						for (final ViewModelWizardNewFileCreationPage filePage : fileCreationPages) {
							// Remember the file.
							final IFile modelFile = filePage.getModelFile();
							// create view
							final VView view = ViewModelHelper.createViewModel(modelFile, filePage.getEClass(),
								getSelectedEcore());
							// generate controls
							if (generateViewModelControls) {
								ControlGenerator.generateAllControls(view);
							}
							// contribute to ..provider.xmi.file extension point
							if (contributeToFileExtensionPage.isContributeToExtensionOptionSelected()) {
								addContribution(modelFile);
							}
							// add containing folder to the build.properties file
							addToBuildProperties(modelFile);

							// Open the view
							final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
							page.openEditor(new FileEditorInput(modelFile),
								workbench.getEditorRegistry().getDefaultEditor(modelFile.getFullPath().toString())
									.getId());
							modelFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
						}

					} catch (final Exception exception) {
						ViewEditorPlugin.INSTANCE.log(exception);
					} finally {
						progressMonitor.done();
					}
				}
			};

			getContainer().run(false, false, operation);
			return true;
		} catch (final Exception exception) {
			ViewEditorPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	/**
	 * @return the Ecore container
	 */
	protected IFile getSelectedEcore() {
		if (IFile.class.isInstance(selectedContainer)) {
			return (IFile) selectedContainer;
		}
		return null;
	}

	/**
	 * Adds the folder where the view model is created to the project's build.properties file.
	 *
	 * @param modelFile the view model IFile
	 */
	protected void addToBuildProperties(IFile modelFile) {
		final IProject project = modelFile.getProject();
		final String projectRelPath = modelFile.getProjectRelativePath().toString();
		final int lastPathDelimiter = projectRelPath.lastIndexOf("/"); //$NON-NLS-1$
		final String pluginXml = "plugin.xml"; //$NON-NLS-1$
		final String path;
		if (lastPathDelimiter == -1) {
			path = projectRelPath;
		} else {
			path = projectRelPath.substring(0, projectRelPath.lastIndexOf("/") + 1); //$NON-NLS-1$
		}
		final String includes = "bin.includes"; //$NON-NLS-1$
		final IFile buildFile = project.getFile("build.properties"); //$NON-NLS-1$
		try {
			if (!buildFile.exists()) {
				return;
			}
			final BufferedReader in = new BufferedReader(new InputStreamReader(buildFile.getContents()));
			String line = null;
			final StringBuffer contents = new StringBuffer();
			while ((line = in.readLine()) != null) {
				if (line.contains(includes)) {
					boolean found = false;
					boolean foundPlugin = false;
					while (line.contains(",\\")) //$NON-NLS-1$
					{
						// entry start
						final int start = line.indexOf("="); //$NON-NLS-1$

						final String entry = line.substring(start + 1, line.indexOf(",\\")).trim(); //$NON-NLS-1$

						if (entry.equals(path)) {
							found = true;
						}
						if (entry.equals(pluginXml)) {
							foundPlugin = true;
						}
						if (found && foundPlugin) {
							break;
						}
						contents.append(line + "\n"); //$NON-NLS-1$
						line = in.readLine();

					}
					if (!found) {
						// check last line
						int start = line.indexOf("="); //$NON-NLS-1$
						if (start == -1) {
							start = 0;
						}
						final String entry = line.substring(start).trim();
						if (!entry.equals(path)) {
							contents.append("               " + path + ",\\\n"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
					if (!foundPlugin) {
						// check last line
						int start = line.indexOf("="); //$NON-NLS-1$
						if (start == -1) {
							start = 0;
						}
						final String entry = line.substring(start).trim();
						if (!entry.equals(pluginXml)) {
							contents.append("               " + pluginXml + ",\\\n"); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
				}
				contents.append(line + "\n"); //$NON-NLS-1$
			}
			in.close();

			final FileWriter out = new FileWriter(buildFile.getRawLocation().makeAbsolute().toFile());
			out.write(String.valueOf(contents));
			out.flush();
			out.close();

			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (final CoreException e) {
			ViewEditorPlugin.INSTANCE.log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} catch (final IOException e) {
			ViewEditorPlugin.INSTANCE.log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Contributes the view model via the "..provider.xmi" extension point. If the project doesn't have a plugin.xml
	 * file,
	 * one will be created.
	 *
	 * @param modelFile the view model IFile
	 */
	protected void addContribution(IFile modelFile) {
		final IProject project = modelFile.getProject();
		if (!ContributionUtil.isPluginProject(project)) {
			return;
		}
		final boolean isFragmentProject = ContributionUtil.isFragmentProject(project);
		final String contributionFileName = isFragmentProject ? "fragment.xml" : "plugin.xml"; //$NON-NLS-1$ //$NON-NLS-2$
		final IFile pluginFile = project.getFile(contributionFileName);
		try {
			if (!pluginFile.exists()) {
				final String xmlContents = MessageFormat.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<?eclipse version=\"3.4\"?>\n<{0}>\n</{0}>", //$NON-NLS-1$
					isFragmentProject ? "fragment" : "plugin"); //$NON-NLS-1$//$NON-NLS-2$
				pluginFile.create(new ByteArrayInputStream(xmlContents.getBytes()), true, null);
			}
			final BufferedReader in = new BufferedReader(new InputStreamReader(pluginFile.getContents()));
			final String extension = "org.eclipse.emf.ecp.view.model.provider.xmi.file"; //$NON-NLS-1$
			String line = null;
			final StringBuffer contents = new StringBuffer();
			boolean extensionAdded = false;
			while ((line = in.readLine()) != null) {
				if (line.contains(extension)) {
					final int end = line.indexOf("/>"); //$NON-NLS-1$
					if (end != -1) {
						final String filePathAttribute = "<file filePath=\"" //$NON-NLS-1$
							+ modelFile.getProjectRelativePath().toString() + "\"/>"; //$NON-NLS-1$
						line = line.substring(0, end)
							+ ">\n" + filePathAttribute + "\n</extension>\n" + line.substring(end + 2, line.length()); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						final String filePathAttribute = "<file filePath=\"" //$NON-NLS-1$
							+ modelFile.getProjectRelativePath().toString() + "\"/>"; //$NON-NLS-1$
						line = line.concat("\n" + filePathAttribute + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					extensionAdded = true;
				}
				final String eof = isFragmentProject ? "</fragment>" : "</plugin>"; //$NON-NLS-1$ //$NON-NLS-2$
				if (line.contains(eof) && !extensionAdded) {
					final int end = line.indexOf(eof);
					line = line.substring(0, end)
						+ "\n<extension  point=\"org.eclipse.emf.ecp.view.model.provider.xmi.file\">\n<file filePath=\"" //$NON-NLS-1$
						+ modelFile.getProjectRelativePath().toString()
						+ "\"/>\n</extension>\n" + line.substring(end); //$NON-NLS-1$
				}
				contents.append(line + "\n"); //$NON-NLS-1$
			}
			in.close();

			final FileWriter out = new FileWriter(pluginFile.getRawLocation().makeAbsolute().toFile());
			out.write(String.valueOf(contents));
			out.flush();
			out.close();

		} catch (final CoreException e) {
			ViewEditorPlugin.INSTANCE.log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		} catch (final IOException e) {
			ViewEditorPlugin.INSTANCE.log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Return the {@link IDEViewModelRegistry}.
	 *
	 * @return the {@link IDEViewModelRegistry}
	 */
	public static IDEViewModelRegistry getViewModelRegistry() {
		final ServiceReference<IDEViewModelRegistry> serviceReference = ViewEditorPlugin.getPlugin().getBundle()
			.getBundleContext()
			.getServiceReference(IDEViewModelRegistry.class);
		if (serviceReference == null) {
			return null;
		}
		return ViewEditorPlugin.getPlugin().getBundle().getBundleContext().getService(serviceReference);
	}

	/**
	 * @param workbench the workbench to set
	 */
	public void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}
}
