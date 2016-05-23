/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 ******************************************************************************/

package org.eclipse.emfforms.spi.editor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emfforms.internal.editor.Activator;
import org.eclipse.emfforms.internal.editor.toolbaractions.LoadEcoreAction;
import org.eclipse.emfforms.internal.editor.ui.EditorToolBar;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultDeleteActionBuilder;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailMenuListener;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.ActionCollector;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.EditorPart;

/**
 * The Class GenericEditor it is the generic part for editing any EObject.
 */
public class GenericEditor extends EditorPart implements IEditingDomainProvider {

	private static final String ITOOLBAR_ACTIONS_ID = "org.eclipse.emfforms.editor.toolbarActions";

	/** The Resource loaded from the provided EditorInput. */
	private ResourceSet resourceSet;

	/** The command stack. It is used to mark the editor as dirty as well as undo/redo operations */
	private final BasicCommandStack commandStack = new BasicCommandStack();

	/** The root view. It is the main Editor panel. */
	private TreeMasterDetailComposite rootView;

	/**
	 * True, if there were changes in the filesystem while the editor was in the background and the changes could not be
	 * applied to current view.
	 */
	private boolean filesChangedWithConflict;

	private final IPartListener partListener = new IPartListener() {
		@Override
		public void partOpened(IWorkbenchPart part) {
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partActivated(IWorkbenchPart part) {
			if (part == GenericEditor.this && isDirty() && filesChangedWithConflict && discardChanges()) {
				for (final Resource r : resourceSet.getResources()) {
					r.unload();
					try {
						r.load(null);
					} catch (final IOException e) {
					}
				}
			}
		}
	};

	private final IResourceChangeListener resourceChangeListener = new EcoreResourceChangeListener();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// Remove the Listener, so that we won't get a changed notification for our own save operation
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		if (ResourceSetHelpers.save(resourceSet)) {
			// Tell the CommandStack, that we have saved the file successfully
			// and inform the Workspace, that the Dirty property has changed.
			commandStack.saveIsDone();
			firePropertyChange(PROP_DIRTY);
			filesChangedWithConflict = false;
		}
		// Add the listener again, so that we get notifications for future changes
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	/**
	 * Handles filesystem changes.
	 *
	 * @param changedResources A List of changed Resources
	 * @param removedResources A List of removed Resources
	 */
	protected void handleResourceChange(Collection<Resource> changedResources, Collection<Resource> removedResources) {
		if (!isDirty()) {
			resourceSet.getResources().removeAll(removedResources);

			for (final Resource changed : changedResources) {
				changed.unload();
				try {
					changed.load(null);
				} catch (final IOException ex) {
				}
			}
		} else {
			filesChangedWithConflict = true;
		}
	}

	private boolean discardChanges() {
		return MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "File Changed",
			"The currently opened files were changed. Do you want to discard the changes and reload the file?");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		final SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
		final int result = saveAsDialog.open();
		if (result == Window.OK) {
			final IPath path = saveAsDialog.getResult();
			setPartName(path.lastSegment());
			resourceSet.getResources().get(0)
				.setURI(URI.createFileURI(path.toOSString()));
			doSave(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
		throws PartInitException {
		setSite(site);
		setInput(input);

		// Set the Title for this Editor to the Name of the Input (= Filename)
		setPartName(input.getName());

		// As soon as the resource changed, we inform the Workspace, that it is
		// now dirty
		commandStack.addCommandStackListener(new CommandStackListener() {
			@Override
			public void commandStackChanged(EventObject event) {
				GenericEditor.this.firePropertyChange(PROP_DIRTY);
			}
		});

		// Activate our context, so that our key-bindings are more important than
		// the default ones!
		site.getService(IContextService.class).activateContext(getContextId());

		site.getPage().addPartListener(partListener);

		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	/**
	 * Returns the context id set for this editor.
	 *
	 * @return the context id
	 */
	protected String getContextId() {
		return "org.eclipse.emfforms.editor.context";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return commandStack.isSaveNeeded();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		// Load the resource from the provided input and display the editor
		resourceSet = loadResource(getEditorInput());
		parent.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		parent.setBackgroundMode(SWT.INHERIT_FORCE);

		rootView = createRootView(parent, getEditorTitle(), modifyEditorInput(resourceSet), getToolbarActions(),
			getCreateElementCallback());

		// We need to set the selectionProvider for the editor, so that the EditingDomainActionBarContributor
		// knows the currently selected object to copy/paste
		getEditorSite().setSelectionProvider(rootView.getSelectionProvider());
	}

	private TreeMasterDetailComposite createRootView(Composite parent, String editorTitle, Object editorInput,
		List<Action> toolbarActions, CreateElementCallback createElementCallback) {
		final Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new FormLayout());

		final FormData toolbarLayoutData = new FormData();
		toolbarLayoutData.left = new FormAttachment(0);
		toolbarLayoutData.right = new FormAttachment(100);
		toolbarLayoutData.top = new FormAttachment(0);
		final EditorToolBar toolbar = new EditorToolBar(composite, SWT.NONE, editorTitle, toolbarActions);
		toolbar.setLayoutData(toolbarLayoutData);

		final FormData treeMasterDetailLayoutData = new FormData();
		treeMasterDetailLayoutData.top = new FormAttachment(toolbar, 5);
		treeMasterDetailLayoutData.left = new FormAttachment(0);
		treeMasterDetailLayoutData.right = new FormAttachment(100);
		treeMasterDetailLayoutData.bottom = new FormAttachment(100);
		final TreeMasterDetailComposite treeMasterDetail = createTreeMasterDetail(composite, editorInput,
			createElementCallback);
		treeMasterDetail.setLayoutData(treeMasterDetailLayoutData);
		return treeMasterDetail;
	}

	/**
	 * This method creates a tree master detail. Override this method if you want to customize the tree.
	 *
	 * @param composite the parent composite
	 * @param editorInput the editor input
	 * @param createElementCallback the create element callback to add
	 *
	 * @return the {@link TreeMasterDetailComposite}
	 */
	protected TreeMasterDetailComposite createTreeMasterDetail(final Composite composite,
		Object editorInput, final CreateElementCallback createElementCallback) {
		final TreeMasterDetailComposite treeMasterDetail = TreeMasterDetailSWTFactory
			.fillDefaults(composite, SWT.NONE, editorInput)
			.customizeCildCreation(createElementCallback)
			.customizeMenu(new MenuProvider() {
				@Override
				public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
					final MenuManager menuMgr = new MenuManager();
					menuMgr.setRemoveAllWhenShown(true);
					final List<MasterDetailAction> masterDetailActions = ActionCollector.newList()
						.addCutAction(editingDomain).addCopyAction(editingDomain).addPasteAction(editingDomain)
						.getList();
					menuMgr.addMenuListener(new TreeMasterDetailMenuListener(new ChildrenDescriptorCollector(), menuMgr,
						treeViewer, editingDomain, masterDetailActions, createElementCallback,
						new DefaultDeleteActionBuilder()));
					final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
					return menu;

				}
			})
			.create();
		return treeMasterDetail;
	}

	/**
	 * Allows to modify the input object for the editor.
	 *
	 * @param resourceSet the resourceSet which is the default editor input
	 * @return the object to set as the input
	 */
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		return resourceSet;
	}

	/**
	 * Creates a resource set and loads all required resources for the editor input.
	 *
	 * @param editorInput the editor input
	 * @return the resource set
	 */
	protected ResourceSet loadResource(IEditorInput editorInput) {
		final IURIEditorInput uei = (IURIEditorInput) editorInput;
		return ResourceSetHelpers.loadResourceSetWithProxies(URI.createURI(uei.getURI().toString(), false),
			commandStack);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// NOOP
	}

	/**
	 * Returns true, if the editor should have shortcuts.
	 *
	 * @return true, if the editor has shortcuts
	 */
	protected boolean hasShortcuts() {
		return false;
	}

	/**
	 * Returns the title for the currently displayed editor.
	 * Subclasses should override this function to change the Editor's title
	 *
	 * @return the title
	 */
	protected String getEditorTitle() {
		return "Model Editor";
	}

	/**
	 * Returns the createElementCallback for this editor. By default, there is none.
	 *
	 * @return the callback
	 */
	protected CreateElementCallback getCreateElementCallback() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditingDomain getEditingDomain() {
		if (rootView == null) {
			return null;
		}
		return rootView.getEditingDomain();
	}

	/**
	 * Returns the toolbar actions for this editor.
	 *
	 * @return A list of actions to show in the Editor's Toolbar
	 */
	private List<Action> getToolbarActions() {
		final List<Action> result = new LinkedList<Action>();

		result.add(new LoadEcoreAction(resourceSet));

		result.addAll(readToolbarActions());
		return result;
	}

	/**
	 * Read toolbar actions from all extensions.
	 *
	 * @return the Actions registered via extension point
	 */
	private List<Action> readToolbarActions() {
		final List<Action> result = new LinkedList<Action>();

		final ISelectionProvider selectionProvider = new ISelectionProvider() {

			@Override
			public void setSelection(ISelection selection) {
				if (rootView == null) {
					return;
				}
				rootView.getSelectionProvider().setSelection(selection);
			}

			@Override
			public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				throw new UnsupportedOperationException();
			}

			@Override
			public ISelection getSelection() {
				if (rootView == null) {
					return new StructuredSelection();
				}
				return rootView.getSelectionProvider().getSelection();
			}

			@Override
			public void addSelectionChangedListener(ISelectionChangedListener listener) {
				throw new UnsupportedOperationException();
			}
		};

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry == null) {
			return result;
		}

		final IConfigurationElement[] config = registry.getConfigurationElementsFor(ITOOLBAR_ACTIONS_ID);
		try {
			for (final IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("toolbarAction");
				if (o instanceof IToolbarAction) {
					final IToolbarAction action = (IToolbarAction) o;
					if (!action.canExecute(resourceSet)) {
						continue;
					}

					result.add(action.getAction(resourceSet, selectionProvider));
				}
			}
		} catch (final CoreException ex) {
			Activator.getDefault().getReportService().report(
				new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
		}
		return result;
	}

	/**
	 * The EcoreResourceChangeListener listens for changes in currently opened Ecore files and reports
	 * them to the EcoreEditor.
	 */
	private final class EcoreResourceChangeListener implements IResourceChangeListener {
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			final Collection<Resource> changedResources = new ArrayList<Resource>();
			final Collection<Resource> removedResources = new ArrayList<Resource>();
			final IResourceDelta delta = event.getDelta();

			if (delta == null) {
				return;
			}

			try {
				delta.accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(final IResourceDelta delta) {
						if (delta.getResource().getType() == IResource.FILE
							&& (delta.getKind() == IResourceDelta.REMOVED ||
								delta.getKind() == IResourceDelta.CHANGED)) {
							final URI uri = URI.createPlatformResourceURI(delta.getFullPath().toString(), true);
							Resource resource = null;
							try {
								final URL fileURL = FileLocator.resolve(new URL(uri.toString()));
								resource = resourceSet.getResource(URI.createFileURI(fileURL.getPath()), false);
							} catch (final MalformedURLException ex) {
								// TODO log properly
								return false;
							} catch (final IOException ex) {
								// TODO log properly
								return false;
							}
							if (resource != null) {
								if (delta.getKind() == IResourceDelta.REMOVED) {
									removedResources.add(resource);
								} else {
									changedResources.add(resource);
								}
							}
							return false;
						}
						return true;
					}
				});
			} catch (final CoreException ex) {
				Activator.getDefault().getReportService().report(
					new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			}

			handleResourceChange(changedResources, removedResources);
		}
	}

	/**
	 * Returns the ResouceSet of this Editor.
	 *
	 * @return The resource set
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}
}
