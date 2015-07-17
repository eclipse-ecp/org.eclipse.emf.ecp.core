/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.spi.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.internal.ui.dialogs.DeleteDialog;
import org.eclipse.emf.ecp.internal.ui.dialogs.ProjectPropertiesDialog;
import org.eclipse.emf.ecp.internal.ui.dialogs.RepositoryPropertiesDialog;
import org.eclipse.emf.ecp.internal.ui.util.HandlerHelperUtil;
import org.eclipse.emf.ecp.internal.wizards.AddRepositoryWizard;
import org.eclipse.emf.ecp.internal.wizards.CheckoutProjectWizard;
import org.eclipse.emf.ecp.internal.wizards.CreateProjectWizard;
import org.eclipse.emf.ecp.internal.wizards.FilterModelElementWizard;
import org.eclipse.emf.ecp.spi.common.ui.CheckedModelClassComposite;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizard;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.ui.common.AddRepositoryComposite;
import org.eclipse.emf.ecp.ui.common.CheckoutProjectComposite;
import org.eclipse.emf.ecp.ui.common.CreateProjectComposite;
import org.eclipse.emf.ecp.ui.common.ECPCompositeFactory;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpenTester;
import org.eclipse.emf.ecp.ui.util.ECPModelElementOpener;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * This is a utility class providing commonly necessary methods.
 *
 * @author Eugen Neufeld
 * @since 1.4
 */
public final class ECPHandlerHelper {
	private ECPHandlerHelper() {
	}

	private static void showError(Shell shell, String title, String message) {
		MessageDialog.openError(shell, title, message);
	}

	/**
	 * This method allows to checkout a list of {@link ECPCheckoutSource} into the workspace.
	 *
	 * @param checkoutObjects the List of {@link ECPCheckoutSource} to checkout
	 * @param shell the {@link Shell} to use for diplaying UI
	 */
	public static void checkout(final List<ECPCheckoutSource> checkoutObjects, final Shell shell) {
		for (final ECPCheckoutSource checkoutSource : checkoutObjects) {
			final CheckoutProjectComposite checkoutCompposite = ECPCompositeFactory
				.getCheckoutProjectComposite(checkoutSource);
			final CheckoutProjectWizard wizard = new CheckoutProjectWizard();
			wizard.setCompositeProvider(checkoutCompposite);

			final WizardDialog wd = new WizardDialog(shell, wizard);

			final int result = wd.open();
			if (result == Window.OK) {

				final String projectName = checkoutCompposite.getProjectName();
				final ECPProperties projectProperties = checkoutCompposite.getProjectProperties();
				try {
					checkoutSource.checkout(projectName, projectProperties);
				} catch (final ECPProjectWithNameExistsException ex) {
					showError(shell, "Cannot checkout project", "A project with name " + projectName //$NON-NLS-1$ //$NON-NLS-2$
						+ " already exists in the workspace."); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * This helper method is used to delete model elements from a project.
	 *
	 * @param project the project to delete from
	 * @param objects the model elements to delete
	 */
	public static void deleteModelElement(final ECPProject project, final Collection<Object> objects) {
		if (project != null) {
			project.deleteElements(objects);
		}
	}

	/**
	 * This method creates a new project.
	 *
	 * @param shell the shell for displaying the wizard
	 * @return the created {@link ECPProject}
	 */
	public static ECPProject createProject(final Shell shell) {
		final List<ECPProvider> providers = new ArrayList<ECPProvider>();
		for (final ECPProvider provider : ECPUtil.getECPProviderRegistry().getProviders()) {
			if (provider.hasCreateProjectWithoutRepositorySupport()) {
				providers.add(provider);
			}
		}
		if (providers.size() == 0) {
			// TODO language
			showError(shell, "No Provider", "Please check if a suitable provider is installed."); //$NON-NLS-1$//$NON-NLS-2$
			return null;
		}
		final CreateProjectComposite createProjectComposite = ECPCompositeFactory.getCreateProjectComposite(providers);
		final CreateProjectWizard wizard = new CreateProjectWizard();
		wizard.setCompositeProvider(createProjectComposite);

		final WizardDialog wd = new WizardDialog(shell, wizard);

		final int result = wd.open();
		if (result == Window.OK) {
			final ECPProvider selectedProvider = createProjectComposite.getProvider();
			if (selectedProvider == null) {
				showError(shell, "No project created", "Please check if a suitable provider is installed."); //$NON-NLS-1$//$NON-NLS-2$
				return null;
			}

			final ECPProperties projectProperties = createProjectComposite.getProperties();
			final String projectName = createProjectComposite.getProjectName();

			ECPProject project = null;
			try {
				project = ECPUtil.getECPProjectManager()
					.createProject(selectedProvider, projectName, projectProperties);
			} catch (final ECPProjectWithNameExistsException ex) {
				showError(shell, "No project created", "A project with name " + projectName //$NON-NLS-1$ //$NON-NLS-2$
					+ " already exists in the workspace."); //$NON-NLS-1$
				return null;
			}
			if (project == null) {
				showError(shell, "No project created", "Please check the log."); //$NON-NLS-1$//$NON-NLS-2$
				return null;
			}
			((InternalProvider) selectedProvider).handleLifecycle(project, LifecycleEvent.CREATE);
			project.open();
			return project;
		}
		return null;
	}

	/**
	 * Add a new {@link EObject} to the root of an {@link ECPProject}.
	 *
	 * @param ecpProject the {@link ECPProject} to add the {@link EObject} to
	 * @param shell the {@link Shell} used to display the UI
	 * @param open whether to open the corresponding editor or not
	 * @return the created {@link EObject}
	 */
	public static EObject addModelElement(final ECPProject ecpProject, final Shell shell, boolean open) {
		final EClass newMEType = openSelectModelElementWizard(ecpProject, shell, open);
		if (ecpProject != null && newMEType != null) {
			// create ME
			final EObject newMEInstance = createModelElementInstance(newMEType);
			ecpProject.getEditingDomain().getCommandStack().execute(new ChangeCommand(newMEInstance) {

				@Override
				protected void doExecute() {
					ecpProject.getContents().add(newMEInstance);
				}
			});
			if (open) {
				openModelElement(newMEInstance, ecpProject);
			}
			return newMEInstance;
		}
		return null;
	}

	/**
	 * @param resource the resource
	 * @param activeShell current active shell
	 * @param open if model element should be directly opened in an editor after it is added to project.
	 *
	 * @return the created model element.
	 */
	public static EObject addModelElement(final Resource resource, Shell activeShell, boolean open) {

		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(resource);
		if (ecpProject == null) {
			return null;
		}
		final EClass newMEType = openSelectModelElementWizard(ecpProject, activeShell, open);
		if (resource != null && newMEType != null) {
			// create ME
			final EObject newMEInstance = createModelElementInstance(newMEType);
			ecpProject.getEditingDomain().getCommandStack().execute(new ChangeCommand(newMEInstance) {

				@Override
				protected void doExecute() {
					resource.getContents().add(newMEInstance);
				}
			});
			if (open) {
				openModelElement(newMEInstance, ecpProject);
			}
			return newMEInstance;
		}
		return null;
	}

	/**
	 * @param newMEType
	 * @return
	 */
	private static EObject createModelElementInstance(final EClass newMEType) {
		final EPackage ePackage = newMEType.getEPackage();
		final EObject newMEInstance = ePackage.getEFactoryInstance().create(newMEType);
		return newMEInstance;
	}

	/**
	 * @param ecpProject
	 * @param shell
	 * @param open
	 * @return
	 */
	private static EClass openSelectModelElementWizard(final ECPProject ecpProject, final Shell shell, boolean open) {
		final SelectionComposite<TreeViewer> helper = ECPCompositeFactory.getSelectModelClassComposite(ecpProject);
		final SelectModelElementWizard wizard = new SelectModelElementWizard(
			Messages.NewModelElementWizardHandler_Title,
			Messages.NewModelElementWizard_WizardTitle_AddModelElement,
			Messages.NewModelElementWizard_PageTitle_AddModelElement,
			Messages.NewModelElementWizard_PageDescription_AddModelElement);
		wizard.setCompositeProvider(helper);
		final WizardDialog wd = new WizardDialog(shell, wizard);

		final int wizardResult = wd.open();
		if (wizardResult == Window.OK) {
			final Object[] selection = helper.getSelection();
			if (selection != null && selection.length > 0) {
				return (EClass) selection[0];
			}
		}
		return null;
	}

	/**
	 * This method allows the user to filter the visible packages and classes.
	 *
	 * @param ecpProject the project to filter
	 * @param shell the {@link Shell} to use for UI
	 */
	public static void filterProjectPackages(final ECPProject ecpProject, final Shell shell) {
		final Set<EPackage> ePackages = ECPUtil.getAllRegisteredEPackages();

		final CheckedModelClassComposite checkedModelComposite = ECPCompositeFactory
			.getCheckedModelClassComposite(ePackages);
		final Set<Object> initialSelectionSet = new HashSet<Object>();
		initialSelectionSet.addAll(((InternalProject) ecpProject).getVisiblePackages());
		initialSelectionSet.addAll(((InternalProject) ecpProject).getVisibleEClasses());
		checkedModelComposite.setInitialSelection(initialSelectionSet.toArray());

		final FilterModelElementWizard wizard = new FilterModelElementWizard();
		wizard.setCompositeProvider(checkedModelComposite);
		final WizardDialog wd = new WizardDialog(shell, wizard);

		final int wizardResult = wd.open();
		if (wizardResult == Window.OK) {
			final Object[] dialogSelection = checkedModelComposite.getChecked();
			final Set<EPackage> filtererdPackages = new HashSet<EPackage>();
			final Set<EClass> filtererdEClasses = new HashSet<EClass>();
			for (final Object object : dialogSelection) {
				if (object instanceof EPackage) {
					filtererdPackages.add((EPackage) object);
				} else if (object instanceof EClass) {
					final EClass eClass = (EClass) object;
					if (!filtererdPackages.contains(eClass.getEPackage())) {
						filtererdEClasses.add(eClass);
					}
				}
			}
			((InternalProject) ecpProject).setVisiblePackages(filtererdPackages);
			((InternalProject) ecpProject).setVisibleEClasses(filtererdEClasses);
		}
	}

	/**
	 * This method created a new Repository.
	 *
	 * @param shell the shell for the Wizard
	 * @return the created {@link ECPRepository}
	 */
	public static ECPRepository createRepository(final Shell shell) {
		final AddRepositoryComposite addRepositoryComposite = ECPCompositeFactory.getAddRepositoryComposite();
		final AddRepositoryWizard wizard = new AddRepositoryWizard();
		wizard.setCompositeProvider(addRepositoryComposite);
		final WizardDialog wd = new WizardDialog(shell, wizard);

		final int wizardResult = wd.open();
		if (wizardResult == Window.OK) {
			final ECPRepository ecpRepository = ECPUtil.getECPRepositoryManager().addRepository(
				addRepositoryComposite.getProvider(), addRepositoryComposite.getRepositoryName(),
				addRepositoryComposite.getRepositoryLabel() == null ? "" : addRepositoryComposite.getRepositoryLabel(), //$NON-NLS-1$
				addRepositoryComposite.getRepositoryDescription() == null ? "" : addRepositoryComposite //$NON-NLS-1$
					.getRepositoryDescription(), addRepositoryComposite.getProperties());
			return ecpRepository;
		}
		return null;
	}

	/**
	 * This method closes/opens an array of ECPProject.
	 *
	 * @param closeables the {@link ECPProject}s to change the state for
	 * @param currentType the action to do
	 */
	public static void changeCloseState(ECPProject[] closeables, String currentType) {
		for (final ECPProject closeable : closeables) {
			if ("open".equalsIgnoreCase(currentType)) { //$NON-NLS-1$
				closeable.open();
			} else if ("close".equalsIgnoreCase(currentType)) { //$NON-NLS-1$
				closeable.close();
			}
		}
	}

	/**
	 * Deletes the provided {@link ECPContainer} elements.
	 *
	 * @param deletables the List of {@link ECPContainer}s to delete
	 * @param shell the shell to use for UI
	 */
	public static void deleteHandlerHelper(List<ECPContainer> deletables, Shell shell) {

		if (!deletables.isEmpty()) {
			final DeleteDialog dialog = new DeleteDialog(shell, deletables);
			if (dialog.open() == Window.OK) {
				for (final ECPContainer deletable : deletables) {
					deletable.delete();
				}
			}
		}
	}

	/**
	 * Triggers the save on an {@link ECPProject}.
	 *
	 * @param project the project to save the changes on
	 */
	public static void saveProject(ECPProject project) {
		project.saveContents();
	}

	/**
	 * This opens the model element.
	 *
	 * @param me
	 *            ModelElement to open
	 *            the view that requested the open model element
	 * @param ecpProject the {@link ECPProject} of the model element
	 */
	public static void openModelElement(final Object me, ECPProject ecpProject) {
		if (me == null) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
				Messages.ActionHelper_ErrorTitle_ElementDeleted, Messages.ActionHelper_ErrorMessage_ElementDeleted);
			return;
		}
		IConfigurationElement[] modelelementopener = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.ui.modelElementOpener"); //$NON-NLS-1$
		ECPModelElementOpener bestCandidate = null;
		int bestValue = -1;
		for (final IConfigurationElement element : modelelementopener) {
			modelelementopener = null;
			try {
				final ECPModelElementOpener modelelementOpener = (ECPModelElementOpener) element
					.createExecutableExtension("class"); //$NON-NLS-1$
				for (final IConfigurationElement testerElement : element.getChildren()) {
					if ("staticTester".equals(testerElement.getName())) {//$NON-NLS-1$
						final int priority = Integer.parseInt(testerElement.getAttribute("priority"));//$NON-NLS-1$
						final String type = testerElement.getAttribute("modelElement"); //$NON-NLS-1$
						try {
							final Class<?> supportedClassType = HandlerHelperUtil.loadClass(testerElement
								.getContributor().getName(),
								type);
							if (supportedClassType.isInstance(me)) {
								if (priority > bestValue) {
									bestCandidate = modelelementOpener;
									bestValue = priority;
								}
							}

						} catch (final ClassNotFoundException ex) {
							Activator.log(ex);
						}
					} else if ("dynamicTester".equals(testerElement.getName())) {//$NON-NLS-1$
						final ECPModelElementOpenTester tester = (ECPModelElementOpenTester) testerElement
							.createExecutableExtension("tester"); //$NON-NLS-1$
						final int value = tester.isApplicable(me);
						if (value > bestValue) {
							bestCandidate = modelelementOpener;
							bestValue = value;
						}
					}
				}

			} catch (final CoreException e) {

				Activator.log(e);
			}
		}
		// TODO: find solution
		// ECPWorkspaceManager.getObserverBus().notify(ModelElementOpenObserver.class).onOpen(me, sourceView, name);
		// BEGIN SUPRESS CATCH EXCEPTION
		if (bestCandidate == null) {
			return;
		}
		try {
			bestCandidate.openModelElement(me, ecpProject);
		} catch (final RuntimeException e) {
			Activator.log(e);
		}
		// END SUPRESS CATCH EXCEPTION

	}

	/**
	 * Opens a Dialog showing the properties of the provided {@link ECPProject}.
	 *
	 * @param project the project whose properties should be shown
	 * @param editable whether the properties should be editable
	 * @param shell the {@link Shell} to use for the dialog
	 */
	public static void openProjectProperties(ECPProject project, boolean editable, Shell shell) {
		new ProjectPropertiesDialog(shell, editable, project).open();
	}

	/**
	 * Opens a Dialog showing the properties of the provided {@link ECPRepository}.
	 *
	 * @param repository the repository whose properties should be shown
	 * @param editable whether the properties should be editable
	 * @param shell the {@link Shell} to use for the dialog
	 */
	public static void openRepositoryProperties(ECPRepository repository, boolean editable, Shell shell) {
		new RepositoryPropertiesDialog(shell, editable, repository).open();
	}

	/**
	 * Opens a dialog to save dirty projects.
	 *
	 * @param shell to open the dialog in
	 * @return if the save was triggered
	 */
	public static boolean showDirtyProjectsDialog(Shell shell) {
		final ECPProjectManager manager = ECPUtil.getECPProjectManager();

		final List<ECPProject> dirtyProjects = new ArrayList<ECPProject>();
		for (final ECPProject project : manager.getProjects()) {
			if (project.isOpen() && project.hasDirtyContents()) {
				dirtyProjects.add(project);
			}
		}
		if (dirtyProjects.isEmpty()) {
			return true;
		}
		final ListSelectionDialog lsd = new ListSelectionDialog(shell, dirtyProjects,
			ArrayContentProvider.getInstance(),
			new LabelProvider() {

				@Override
				public Image getImage(Object element) {
					if (ECPProject.class.isInstance(element)) {
						return Activator.getImage("icons/project_open.gif"); //$NON-NLS-1$
					}
					return super.getImage(element);
				}

				@Override
				public String getText(Object element) {
					if (ECPProject.class.isInstance(element)) {
						return ((ECPProject) element).getName();
					}
					return super.getText(element);
				}

			}, "Select the projects, which should be saved."); //$NON-NLS-1$
		lsd.setInitialSelections(manager.getProjects().toArray());
		lsd.setTitle("Unsaved Projects"); //$NON-NLS-1$
		final int result = lsd.open();
		if (Window.OK == result) {
			for (final Object o : lsd.getResult()) {
				ECPHandlerHelper.saveProject((ECPProject) o);
			}
			return true;
		}
		return false;
	}

}
