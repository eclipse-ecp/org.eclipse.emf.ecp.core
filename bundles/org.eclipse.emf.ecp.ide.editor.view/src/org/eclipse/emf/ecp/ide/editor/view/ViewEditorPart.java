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
import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ide.view.service.ViewModelEditorCallback;
import org.eclipse.emf.ecp.internal.ide.util.EcoreHelper;
import org.eclipse.emf.ecp.spi.ui.ECPReferenceServiceImpl;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ErrorViewPart;
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

	private ViewModelContext viewContext;
	private ModelChangeAddRemoveListener modelChangeListener;

	private boolean ecoreOutOfSync;
	private IPartListener2 partListener;
	private final ViewEditorPart instance;
	private ViewModelService referenceService;

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

		referenceService = new ECPReferenceServiceImpl();

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
	}

	private ResourceSet createResourceSet() {
		final ResourceSet resourceSet = new ResourceSetImpl();

		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
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

	private void loadView() {
		final FileEditorInput fei = (FileEditorInput) getEditorInput();

		final ResourceSet resourceSet = createResourceSet();
		try {
			resource = resourceSet.getResource(URI.createURI(fei.getURI().toURL().toExternalForm()), true);
			resource.load(null);
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

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;

		// before we can load the view we need to check if it needs to be migrated.

		// parse view with sax and check ns-uris specified in view element.
		// check if these models are old
		// perform migration if needed

		loadView();
		VView view = getView();

		final String ecorePath = Activator.getViewModelRegistry().getEcorePath(view);
		try {
			EcoreHelper.registerEcore(ecorePath);
		} catch (final IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

		try {
			// reload view resource after EClass' package resource was loaded into the package registry
			loadView();
			view = getView();

			Activator.getViewModelRegistry().registerViewModel(view, resource.getURI().toString());
			try {
				Activator.getViewModelRegistry().registerViewModelEditor(view, this);
			} catch (final IOException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			}
			if (view.getRootEClass() != null) {
				Activator.getViewModelRegistry().register(view.getRootEClass().eResource().getURI().toString(), view);
			}

			showView();

			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			displayError(e);
		}// END SUPRESS CATCH EXCEPTION
	}

	private void showView() {
		final VView view = getView();

		viewContext = ViewModelContextFactory.INSTANCE.createViewModelContext(
			view, view.getRootEClass(), referenceService);

		try {
			render = ECPSWTViewRenderer.INSTANCE.render(parent, view);
		} catch (final ECPRendererException ex) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
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
				loadView();
				showView();
			}
		});
	}

	@Override
	public void dispose() {
		final VView view = getView();
		Activator.getViewModelRegistry().unregisterViewModelEditor(view, this);
		if (viewContext != null && modelChangeListener != null) {
			viewContext.unregisterViewChangeListener(modelChangeListener);
		}
		getSite().getPage().removePartListener(partListener);
		super.dispose();
	}

	/**
	 * @return the VView object
	 */
	public VView getView() {
		return (VView) resource.getContents().get(0);
	}

	private void displayError(Exception e) {
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Current view cannot be displayed", e); //$NON-NLS-1$
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
				if (ecoreOutOfSync) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							final Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getShell();
							final MessageDialog dialog = new MessageDialog(
								activeShell,
								"Warning", //$NON-NLS-1$
								null,
								"The ECore or Genmodel of your ViewModel just changed. This change is not reflected in this View Model Editor.", //$NON-NLS-1$
								MessageDialog.WARNING,
								new String[] { "Ok" }, //$NON-NLS-1$
								0);
							dialog.open();
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
}
