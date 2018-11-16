/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.editor.view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.ide.editor.view.messages.Messages;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelMigrationException;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelMigratorUtil;
import org.eclipse.emf.ecp.spi.view.migrator.TemplateModelWorkspaceMigrator;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.migrator.ViewModelWorkspaceMigrator;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The IDE ViewModel EditorPart.
 *
 * @author Eugen Neufeld
 *
 */
public class ViewEditorPart extends EditorPart implements
	ViewModelEditorCallback {

	private Resource resource;
	private BasicCommandStack basicCommandStack;
	private Composite parent;
	private ECPSWTView render;

	private boolean ecoreOutOfSync;
	private IPartListener2 partListener;
	private final ViewEditorPart instance;
	private AdapterFactoryEditingDomain editingDomain;

	/** Default constructor for {@link ViewEditorPart}. */
	public ViewEditorPart() {
		super();
		instance = this;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the work within an operation because this is a long running activity that modifies the workbench.
		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			public void execute(IProgressMonitor monitor) {
				try {
					resource.save(null);
				} catch (final IOException e) {
					Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
				}
			}
		};
		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			basicCommandStack.saveIsDone();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (final InvocationTargetException e) {
			Activator.getDefault().getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		} catch (final InterruptedException e) {
			Activator.getDefault().getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

	}

	@Override
	public void doSaveAs() {
		// unsupported
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		super.setSite(site);
		super.setInput(input);
		super.setPartName(input.getName());

		try {
			basicCommandStack = new BasicCommandStack();
			editingDomain = new AdapterFactoryEditingDomain(
				new ComposedAdapterFactory(new AdapterFactory[] {
					new CustomReflectiveItemProviderAdapterFactory(),
					new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
				basicCommandStack);
			editingDomain.getResourceSet().getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
				Boolean.TRUE);
			loadView(false, false);
		} // BEGIN SUPRESS CATCH EXCEPTION
		catch (final Exception e) {// END SUPRESS CATCH EXCEPTION
			/*
			 * ignore all exceptions during first loading of view. The view might actually be an outdated view, so the
			 * second call will migrate the view. if the migration step fails or is not possible at all, we will fail in
			 * the later call.
			 */
		}

		try {
			registerEcore(resource);
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			throw new PartInitException(
				MessageFormat.format(Messages.ViewEditorPart_ViewCannotBeDisplayed, e.getLocalizedMessage()), e);
		} // END SUPRESS CATCH EXCEPTION

		try {
			// reload view resource after EClass' package resource was loaded into the package registry
			loadView(true, true);
			checkLoadedView();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final Exception e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			throw new PartInitException(
				MessageFormat.format(Messages.ViewEditorPart_ViewCannotBeDisplayed, e.getLocalizedMessage()), e);
		} // END SUPRESS CATCH EXCEPTION

		basicCommandStack.addCommandStackListener(new CommandStackListener() {
			@Override
			public void commandStackChanged(final EventObject event) {
				parent.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						firePropertyChange(IEditorPart.PROP_DIRTY);
					}
				});
			}
		});
		partListener = new ViewPartListener();
		getSite().getPage().addPartListener(partListener);

		final IResourceChangeListener listener = new EditorResourceChangedListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
	}

	/**
	 * Checks whether the loaded VView is valid:
	 * <ul>
	 * <li>not null</li>
	 * <li>the root EClass is set</li>
	 * <li>the root EClass is resolved (no proxy)</li>
	 * </ul>
	 * If the VView is invalid, an Exception with a proper error description is thrown.
	 *
	 * @throws IllegalArgumentException If the VView is null or no root EClass was set.
	 * @throws IllegalStateException If the VView's root EClass cannot be resolved.
	 */
	private void checkLoadedView() throws IllegalArgumentException, IllegalStateException {
		if (getView() == null) {
			throw new IllegalArgumentException(Messages.ViewEditorPart_InvalidVView);
		}
		final EClass rootEClass = getView().getRootEClass();
		if (rootEClass == null) {
			throw new IllegalArgumentException(
				Messages.ViewEditorPart_invalidVView_noRootEClass);
		}
		if (rootEClass.eIsProxy()) {
			final String proxyUri = EcoreUtil.getURI(rootEClass).toString();
			final String packageNsUri = proxyUri.split("#")[0]; //$NON-NLS-1$
			final String rootEClassName = proxyUri.split("#")[1].substring(2); //$NON-NLS-1$
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageNsUri);
			if (ePackage == null || ePackage.eIsProxy()) {
				// the whole ecore is not present in the registry
				throw new IllegalStateException(MessageFormat.format(
					Messages.ViewEditorPart_invalidVView_rootEClassPackageNotResolved,
					rootEClassName, packageNsUri));
			}
			// The package is resolved but the Root EClass is not => Ecore was registered but misses the needed
			// class.
			throw new IllegalStateException(MessageFormat.format(
				Messages.ViewEditorPart_invalidVView_rootEClassNotInPackage,
				rootEClassName, ePackage.getName(), ePackage.getNsURI()));
		}
	}

	@Override
	public boolean isDirty() {
		return basicCommandStack.isSaveNeeded();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Loads the view model.
	 *
	 * @param migrate whether the view model should be migrated (if actually needed) <b>before</b> attempting to load it
	 * @throws IOException if the view model resource failed to load
	 * @throws PartInitException
	 */
	private void loadView(boolean migrate, boolean resolve) throws IOException, PartInitException {

		// resourceURI must be a platform resource URI
		final URI resourceURI = EditUIUtil.getURI(getEditorInput(), editingDomain.getResourceSet().getURIConverter());
		if (migrate) {
			checkMigration(resourceURI);
		}
		editingDomain.getResourceSet().getResources().clear();
		resource = editingDomain.getResourceSet().getResource(resourceURI, true);
		if (resource.getContents().size() == 0 || !VView.class.isInstance(resource.getContents().get(0))) {
			throw new PartInitException(Messages.ViewEditorPart_InvalidVView);
		}
		if (resolve) {
			// resolve all proxies
			final ResourceSet resourceSet = editingDomain.getResourceSet();
			int rsSize = resourceSet.getResources().size();
			EcoreUtil.resolveAll(resourceSet);
			while (rsSize != resourceSet.getResources().size()) {
				EcoreUtil.resolveAll(resourceSet);
				rsSize = resourceSet.getResources().size();
			}
		}
	}

	private void checkMigration(final URI resourceURI) {
		final ViewModelMigrator migrator = ViewModelMigratorUtil.getViewModelMigrator();
		if (migrator == null) {
			return;
		}
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		final boolean needsMigration = checkIfMigrationIsNeeded(shell, resourceURI, migrator);
		if (needsMigration) {
			final boolean migrate = MessageDialog.openQuestion(shell, Messages.ViewEditorPart_MigrationTitle,
				Messages.ViewEditorPart_MigrationQuestion);
			if (migrate) {
				final boolean migrateWorkspace = MessageDialog.openQuestion(shell,
					Messages.WorkspaceMigrationDialog_Title,
					Messages.WorkspaceMigrationDialog_Description);
				final List<URI> toMigrate = new ArrayList<URI>();
				if (migrateWorkspace) {
					for (final URI uri : getWorkspaceURIsToMigrate(resourceURI)) {
						try {
							final Resource workspaceResource = editingDomain.getResourceSet().getResource(uri, true);
							registerEcore(workspaceResource);
							toMigrate.add(uri);
						}
						// BEGIN SUPRESS CATCH EXCEPTION
						catch (final Exception ex) {// END SUPRESS CATCH EXCEPTION
							/*
							 * catch any exception because loading the workspace resource may also lead to exception
							 * like NoClassDefFound, IAE, etc. We want to continue in any case, in order to migrate as
							 * much as possible
							 */
							Activator.getDefault().getLog()
								.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, MessageFormat.format(
									Messages.ViewEditorPart_WorkspaceMigrationError,
									uri.toString()), ex));
						}
					}
				}
				toMigrate.add(resourceURI);
				final IRunnableWithProgress runnable = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
						try {
							for (final URI uri : toMigrate) {
								migrator.performMigration(uri);
							}
						} catch (final ViewModelMigrationException ex) {
							throw new InvocationTargetException(ex);
						}
					}
				};
				try {
					new ProgressMonitorDialog(shell).run(true, false, runnable);
				} catch (final InvocationTargetException e) {
					MessageDialog.openError(
						Display.getDefault().getActiveShell(), Messages.ViewEditorPart_MigrationErrorTitle,
						Messages.ViewEditorPart_MigrationErrorText1 +
							Messages.ViewEditorPart_MigrationErrorText2);
					Activator
						.getDefault()
						.getLog()
						.log(
							new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ViewEditorPart_MigrationErrorTitle,
								e));
				} catch (final InterruptedException e) {
					MessageDialog.openError(
						Display.getDefault().getActiveShell(), Messages.ViewEditorPart_MigrationErrorTitle,
						Messages.ViewEditorPart_MigrationErrorText1 +
							Messages.ViewEditorPart_MigrationErrorText2);
					Activator
						.getDefault()
						.getLog()
						.log(
							new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ViewEditorPart_MigrationErrorTitle,
								e));
				}

			}

			migrateTemplateModels(shell);
		}
	}

	/**
	 * If there is a template migrator, prompt the user if (s)he wants to search the workspace for template models that
	 * need migration. Afterwards, let the user chose which models to migrate and execute the migration.
	 *
	 * @param shell The {@link Shell} to create the dialogs for prompting the user on.
	 */
	private void migrateTemplateModels(final Shell shell) {
		final TemplateModelWorkspaceMigrator templateMigrator = TemplateModelMigratorUtil
			.getTemplateModelWorkspaceMigrator();
		if (templateMigrator == null) {
			return;
		}
		// Prompt user to migrate template models in the workspace
		final boolean migrateTemplates = MessageDialog.openQuestion(shell,
			Messages.ViewEditorPart_TemplateMigrationTitle,
			Messages.ViewEditorPart_TemplateMigrationDescription);
		if (migrateTemplates) {
			final List<URI> templateModelsToMigrate = getTemplateModelWorkspaceURIsToMigrate();

			final IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
					try {
						for (final URI uri : templateModelsToMigrate) {
							templateMigrator.performMigration(uri);
						}
					} catch (final TemplateModelMigrationException ex) {
						throw new InvocationTargetException(ex);
					}
				}
			};

			try {
				new ProgressMonitorDialog(shell).run(true, false, runnable);
			} catch (final InvocationTargetException e) {
				MessageDialog.openError(
					Display.getDefault().getActiveShell(), Messages.ViewEditorPart_TemplateMigrationErrorTitle,
					Messages.ViewEditorPart_TemplateMigrationErrorMessage);
				Activator
					.getDefault().getLog().log(
						new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							Messages.ViewEditorPart_TemplateMigrationErrorTitle, e));
			} catch (final InterruptedException e) {
				MessageDialog.openError(
					Display.getDefault().getActiveShell(), Messages.ViewEditorPart_TemplateMigrationErrorTitle,
					Messages.ViewEditorPart_TemplateMigrationErrorMessage);
				Activator.getDefault().getLog().log(
					new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						Messages.ViewEditorPart_TemplateMigrationErrorTitle, e));
			}

		}
	}

	/**
	 * @param resourceURI the resource URI for which the migration dialog was opened. This URI will be removed from the
	 *            list of workspace URI in need of migration, which will be presented to the user.
	 */
	private List<URI> getWorkspaceURIsToMigrate(URI resourceURI) {
		final List<URI> uris = new ArrayList<URI>();

		final ViewModelWorkspaceMigrator workspaceMigrator = ViewModelMigratorUtil
			.getViewModelWorkspaceMigrator();
		if (workspaceMigrator == null) {
			return uris;
		}
		try {
			final ArrayList<URI> urIsToMigrate = workspaceMigrator.getURIsToMigrate();

			// Get the editors's resource uri as a uri with an absolute file path and remove it from the workspace URIs
			// to migrate in order to avoid migrating it two times (this would throw an exception).
			final IResource findMember = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(resourceURI.toPlatformString(true));
			final String osString = findMember.getLocation().toOSString();
			urIsToMigrate.remove(URI.createFileURI(osString));

			if (urIsToMigrate.size() > 0) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final ListSelectionDialog migrationDialog = MigrationDialogHelper
					.getViewModelListMigrationDialog(shell,
						urIsToMigrate);

				if (migrationDialog.open() == Window.OK) {
					final Object[] selectedURIs = migrationDialog.getResult();
					if (selectedURIs != null) {
						for (final Object selectedUri : selectedURIs) {
							uris.add((URI) selectedUri);
						}
					}
				}
			}
		} catch (final CoreException ex) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
		}
		return uris;
	}

	private List<URI> getTemplateModelWorkspaceURIsToMigrate() {
		final List<URI> uris = new LinkedList<URI>();

		final TemplateModelWorkspaceMigrator workspaceMigrator = TemplateModelMigratorUtil
			.getTemplateModelWorkspaceMigrator();
		if (workspaceMigrator == null) {
			return uris;
		}
		try {
			final List<URI> urIsToMigrate = workspaceMigrator.getURIsToMigrate();

			if (urIsToMigrate.size() > 0) {
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				final ListSelectionDialog migrationDialog = MigrationDialogHelper
					.getTemplateModelListMigrationDialog(shell, urIsToMigrate);

				if (migrationDialog.open() == Window.OK) {
					final Object[] selectedURIs = migrationDialog.getResult();
					if (selectedURIs != null) {
						for (final Object selectedUri : selectedURIs) {
							uris.add((URI) selectedUri);
						}
					}
				}
			}
		} catch (final CoreException ex) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
		}
		return uris;
	}

	private boolean checkIfMigrationIsNeeded(Shell shell, final URI resourceURI, final ViewModelMigrator migrator) {
		final CheckMigrationRunnable runnable = new CheckMigrationRunnable(migrator, resourceURI);
		try {
			new ProgressMonitorDialog(shell).run(true, false, runnable);
		} catch (final InvocationTargetException ex) {
			Activator.getDefault().getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ViewEditorPart_MigrationErrorTitle, ex));
		} catch (final InterruptedException ex) {
			Activator.getDefault().getLog()
				.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ViewEditorPart_MigrationErrorTitle, ex));
		}
		return runnable.getResult();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		final VView view = getView();

		Activator.getViewModelRegistry().registerViewModel(view, resource.getURI().toString());
		try {
			Activator.getViewModelRegistry().registerViewModelEditor(view, this);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
		if (view.getRootEClass() != null) {
			if (view.getRootEClass().eResource() != null) {
				Activator.getViewModelRegistry().register(view.getRootEClass().eResource().getURI().toString(),
					view);
			} else {
				Activator
					.getDefault()
					.getLog()
					.log(
						new Status(IStatus.WARNING, Activator.PLUGIN_ID,
							"The Root EClass of the view cannot be resolved." + view.getRootEClass())); //$NON-NLS-1$
			}
		}
		showView();
	}

	private void registerEcore(Resource viewResource) throws IOException {
		for (final String ecorePath : ViewModelHelper.getEcorePaths(viewResource)) {
			if (ecorePath == null) {
				return;
			}
			EcoreHelper.registerEcore(ecorePath);
		}
	}

	private void saveChangedView(VView view) {
		try {
			view.eResource().save(null);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	private String selectEcoreFromWorkspace() {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getDefault()
			.getActiveShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.setAllowMultiple(false);
		dialog.setValidator(new ISelectionStatusValidator() {

			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1) {
					if (selection[0] instanceof IFile) {
						final IFile file = (IFile) selection[0];
						if (file.getType() == IResource.FILE) {
							return new Status(IStatus.OK, Activator.PLUGIN_ID, IStatus.OK, null, null);
						}
					}
				}
				return new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.ERROR,
					Messages.ViewEditorPart_EcoreSelectionValidation,
					null);
			}
		});
		dialog.setTitle(Messages.ViewEditorPart_EcoreSelectionTitle);

		if (dialog.open() == Window.OK) {

			final IFile file = (IFile) dialog.getFirstResult();
			return file.getFullPath()
				.toString();
		}
		return null;
	}

	private void showView() {
		final VView view = getView();

		if (XMLResource.class.isInstance(view.eResource())
			&& !XMLResource.class.cast(view.eResource()).getEObjectToExtensionMap().isEmpty()) {
			// we are showing a view which wasn't fully loaded
			MessageDialog
				.openWarning(
					parent.getShell(),
					Messages.ViewEditorPart_LoadedPartlyTitle,
					Messages.ViewEditorPart_LoadedPartlyDescription);
		}

		try {
			final ViewModelContext viewModelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(ViewProviderHelper.getView(view, null), view, new DefaultReferenceService(),
					new EMFDeleteServiceImpl());
			viewModelContext.putContextValue("enableMultiEdit", Boolean.TRUE); //$NON-NLS-1$
			render = ECPSWTViewRenderer.INSTANCE.render(parent, viewModelContext);
		} catch (final ECPRendererException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
		}
	}

	@Override
	public void setFocus() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback#reloadViewModel()
	 */
	@Override
	public void reloadViewModel() {
		Display.getDefault().asyncExec(new ReloadViewModelRunnable());
	}

	@Override
	public void dispose() {
		final VView view = getView();
		if (view != null) {
			Activator.getViewModelRegistry().unregisterViewModelEditor(view, this);
		}
		if (partListener != null) {
			getSite().getPage().removePartListener(partListener);
		}
		super.dispose();
	}

	/**
	 * @return the VView object
	 */
	public VView getView() {
		if (resource == null || resource.getContents().isEmpty()) {
			return null;
		}
		final EObject eObject = resource.getContents().get(0);
		if (!VView.class.isInstance(eObject)) {
			return null;
		}
		return (VView) eObject;
	}

	/**
	 * Runnable to check if a migration is needed.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private static final class CheckMigrationRunnable implements IRunnableWithProgress {
		private final ViewModelMigrator migrator;
		private final URI resourceURI;
		private boolean needsMigration;

		/**
		 * Default constructor.
		 *
		 * @param migrator the migrator
		 * @param resourceURI the resource uri to check
		 */
		CheckMigrationRunnable(ViewModelMigrator migrator, URI resourceURI) {
			this.migrator = migrator;
			this.resourceURI = resourceURI;
		}

		@Override
		public void run(IProgressMonitor monitor)
			throws InvocationTargetException {
			needsMigration = !migrator.checkMigration(resourceURI);
		}

		/**
		 * Returns the result of the migration check.
		 *
		 * @return <code>true</code> if migration is needed, <code>false</code> otherwise
		 */
		public boolean getResult() {
			return needsMigration;
		}
	}

	/**
	 * @author Jonas
	 *
	 */
	private final class ReloadViewModelRunnable implements Runnable {
		@Override
		public void run() {
			if (parent == null || parent.isDisposed()) {
				final IWorkbenchPage page = instance.getSite().getPage();
				page.closeEditor(instance, true);
				return;
			}
			if (render != null) {
				render.dispose();
				render.getSWTControl().dispose();
			}

			for (final String ecorePath : getView().getEcorePaths()) {
				if (ecorePath != null) {
					try {
						EcoreHelper.registerEcore(ecorePath);
					} catch (final IOException e) {
						Activator.getDefault().getLog()
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
					}
				}
			}

			// reload view resource after EClass' package resource was loaded into the package registry
			try {
				loadView(true, true);
			} catch (final IOException e) {
				Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			} catch (final PartInitException e) {
				Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
				return;
			}
			final VView view = getView();

			try {
				Activator.getViewModelRegistry().registerViewModelEditor(view, instance);
			} catch (final IOException e) {
				Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			}

			if (view.getRootEClass() != null) {
				if (view.getRootEClass().eResource() != null) {
					Activator.getViewModelRegistry().register(
						view.getRootEClass().eResource().getURI().toString(),
						view);
				} else {
					Activator
						.getDefault()
						.getLog()
						.log(
							new Status(IStatus.WARNING, Activator.PLUGIN_ID,
								"The Root EClass of the view cannot be resolved." + view.getRootEClass())); //$NON-NLS-1$
				}
			}
			showView();
			parent.layout(true);
		}
	}

	/**
	 *
	 * */
	private class ViewPartListener implements IPartListener2 {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (instance.equals(partRef.getPart(true))) {

				final VView view = getView();

				// TODO: remove? Should probably handled manually by the user instead.
				if ((view.getEcorePaths().isEmpty()
					|| ResourcesPlugin.getWorkspace().getRoot().findMember(view.getEcorePaths().get(0)) == null)
					&& view.getRootEClass() != null && view.getRootEClass().eIsProxy()) {

					final String selectedECorePath = selectEcoreFromWorkspace();
					if (selectedECorePath != null) {
						view.getEcorePaths().add(selectedECorePath);
						saveChangedView(view);
						reloadViewModel();
					}
				}
				if (ecoreOutOfSync) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							final Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getShell();
							final MessageDialog dialog = new MessageDialog(
								activeShell,
								Messages.ViewEditorPart_Warning,
								null,
								Messages.ViewEditorPart_EditorViewChanged,
								MessageDialog.WARNING,
								new String[] { Messages.ViewEditorPart_Yes, Messages.ViewEditorPart_No },
								0);
							final int result = dialog.open();
							if (result == 0) {
								Activator.getViewModelRegistry().unregisterViewModelEditor(getView(), instance);
								Activator.getViewModelRegistry().unregister(
									getView().getRootEClass().eResource().getURI().toString(),
									getView());
								reloadViewModel();
							}
							ecoreOutOfSync = false;
						}
					});
				}
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback#signalEcoreOutOfSync()
	 */
	@Override
	public void signalEcoreOutOfSync() {
		ecoreOutOfSync = true;

	}

	/** Listens for changes in the editor's resource. */
	private class EditorResourceChangedListener implements IResourceChangeListener {

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			final IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				@Override
				public boolean visit(IResourceDelta delta) {
					if (delta.getKind() == IResourceDelta.REMOVED) {
						final FileEditorInput fei = (FileEditorInput) instance.getEditorInput();
						if (delta.getFullPath().equals(fei.getFile().getFullPath())) {
							final IWorkbenchPage page = instance.getSite().getPage();
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									page.closeEditor(instance, false);
								}
							});
							return false;
						}
					}
					return true;
				}
			};
			try {
				if (delta == null) {
					return;
				}
				delta.accept(visitor);
			} catch (final CoreException ex) {
			}
		}
	}
}
