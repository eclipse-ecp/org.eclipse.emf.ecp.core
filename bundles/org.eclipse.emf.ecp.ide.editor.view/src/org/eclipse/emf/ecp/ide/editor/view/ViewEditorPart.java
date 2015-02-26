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
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecp.ide.editor.view.messages.Messages;
import org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrationException;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigrator;
import org.eclipse.emf.ecp.view.migrator.ViewModelMigratorUtil;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
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
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.internal.ErrorViewPart;
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
@SuppressWarnings("restriction")
public class ViewEditorPart extends EditorPart implements
	ViewModelEditorCallback {

	private Resource resource;
	private BasicCommandStack basicCommandStack;
	private Composite parent;
	private ECPSWTView render;

	private boolean ecoreOutOfSync;
	private IPartListener2 partListener;
	private final ViewEditorPart instance;

	/** Default constructor for {@link ViewEditorPart}. */
	public ViewEditorPart() {
		super();
		instance = this;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			resource.save(null);
			basicCommandStack.saveIsDone();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		super.setSite(site);
		super.setInput(input);
		super.setPartName(input.getName());

		basicCommandStack = new BasicCommandStack();
		basicCommandStack.addCommandStackListener
			(new CommandStackListener()
			{
				@Override
				public void commandStackChanged(final EventObject event)
				{
					parent.getDisplay().asyncExec
						(new Runnable()
						{
							@Override
							public void run()
							{
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

	private ResourceSet createResourceSet() {
		final ResourceSet resourceSet = new ResourceSetImpl();

		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
			basicCommandStack, resourceSet);
		resourceSet.eAdapters().add(
			new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		return resourceSet;
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
	 */
	private void loadView(boolean migrate) {
		final FileEditorInput fei = (FileEditorInput) getEditorInput();

		final ResourceSet resourceSet = createResourceSet();
		try {
			final URI resourceURI = URI.createURI(fei.getURI().toURL().toExternalForm());

			if (migrate) {
				checkMigration(resourceURI);
			}

			final Map<Object, Object> loadOptions = new HashMap<Object, Object>();
			loadOptions
				.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

			resource = resourceSet.createResource(resourceURI);

			resource.load(loadOptions);
			// resolve all proxies
			int rsSize = resourceSet.getResources().size();
			EcoreUtil.resolveAll(resourceSet);
			while (rsSize != resourceSet.getResources().size()) {
				EcoreUtil.resolveAll(resourceSet);
				rsSize = resourceSet.getResources().size();
			}

		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	private void checkMigration(final URI resourceURI) {
		final ViewModelMigrator migrator = ViewModelMigratorUtil.getViewModelMigrator();
		if (migrator == null) {
			return;
		}
		final boolean needsMigration = !migrator.checkMigration(resourceURI);
		if (needsMigration) {
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			final boolean migrate = MessageDialog.openQuestion(shell, Messages.ViewEditorPart_MigrationTitle,
				Messages.ViewEditorPart_MigrationQuestion);
			if (migrate) {
				final IRunnableWithProgress runnable = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
						try {
							migrator.performMigration(resourceURI);
						} catch (final ViewModelMigrationException ex) {
							throw new InvocationTargetException(ex);
						}
					}
				};
				try {
					new ProgressMonitorDialog(shell).run(false, false, runnable);
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
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		loadView(false);

		registerEcore();

		try {
			// reload view resource after EClass' package resource was loaded into the package registry
			loadView(true);
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

			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			displayError(e);
		}// END SUPRESS CATCH EXCEPTION
	}

	private void registerEcore() {
		final String ecorePath = getEcorePath();
		if (ecorePath == null) {
			return;
		}
		try {
			EcoreHelper.registerEcore(ecorePath);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));

		}
	}

	/**
	 * @param view
	 */
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
					Messages.ViewEditorPart_LoadedPartyTitle,
					Messages.ViewEditorPart_LoadedPartyDescription);
		}

		try {
			render = ECPSWTViewRenderer.INSTANCE.render(parent, view);
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
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (render != null) {
					render.dispose();
					render.getSWTControl().dispose();
				}

				final String ecorePath = getView().getEcorePath();
				if (ecorePath != null) {
					try {
						EcoreHelper.registerEcore(ecorePath);
					} catch (final IOException e) {
						Activator.getDefault().getLog()
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
					}
				}

				// reload view resource after EClass' package resource was loaded into the package registry
				loadView(true);
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
		});
	}

	@Override
	public void dispose() {
		final VView view = getView();
		Activator.getViewModelRegistry().unregisterViewModelEditor(view, this);
		getSite().getPage().removePartListener(partListener);
		super.dispose();
	}

	/**
	 * @return the VView object
	 */
	public VView getView() {
		final EObject eObject = resource.getContents().get(0);
		return (VView) eObject;
	}

	private String getEcorePath() {
		final EObject eObject = resource.getContents().get(0);
		if (VView.class.isInstance(eObject)) {
			return VView.class.cast(eObject).getEcorePath();
		}
		if (AnyType.class.isInstance(eObject)) {
			/* view model has older ns uri */
			final FeatureMap anyAttribute = AnyType.class.cast(eObject).getAnyAttribute();
			for (int i = 0; i < anyAttribute.size(); i++) {
				final EStructuralFeature feature = anyAttribute.getEStructuralFeature(i);
				if ("ecorePath".equals(feature.getName())) { //$NON-NLS-1$
					return (String) anyAttribute.getValue(i);
				}
			}
		}
		return null;
	}

	private void displayError(Exception e) {
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
			Messages.ViewEditorPart_ViewCannotBeDisplayed, e);
		final ErrorViewPart part = new ErrorViewPart(status);
		part.createPartControl(parent);
	}

	/**
	 *
	 * */
	private class ViewPartListener implements IPartListener2 {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (instance.equals(partRef.getPart(true))) {

				final VView view = getView();

				if ((view.getEcorePath() == null
					|| ResourcesPlugin.getWorkspace().getRoot().findMember(view.getEcorePath()) == null)
					&& view.getRootEClass().eIsProxy()) {

					final String selectedECorePath = selectEcoreFromWorkspace();
					if (selectedECorePath != null) {
						view.setEcorePath(selectedECorePath);
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
				public boolean visit(IResourceDelta delta)
				{
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
