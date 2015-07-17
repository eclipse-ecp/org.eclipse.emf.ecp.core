// REUSED CLASS
package org.eclipse.emf.ecp.graphiti;

import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DefaultUpdateBehavior;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.editor.ElementDeleteListener;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.graphiti.ui.editor.IDiagramEditorInput;
import org.eclipse.graphiti.ui.internal.Messages;
import org.eclipse.graphiti.ui.internal.services.GraphitiUiInternal;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * SAP AG - initial API, implementation and documentation
 * mwenz - Bug 329523 - Add notification of DiagramTypeProvider after saving a diagram
 * mwenz - Bug 347152 - Do not log diagnostics errors as errors in the Eclipse error log
 * mwenz - Bug 359928 - DiagramEditorBehavior does not initialize adapterActive field
 * Bug 336488 - DiagramEditor API - Rename from DiagramEditorBehavior to DefaultUpdateBehavior
 *
 * </copyright>
 *
 *******************************************************************************/

/**
 * The default implementation for the {@link DiagramEditor} behavior extension
 * that controls update behavior of the editor and defines the EMF adapters that
 * watch over model object modifications. Clients may subclass to change the
 * behavior; use {@link org.eclipse.graphiti.ui.editor.DiagramEditor#createUpdateBehavior()} to return the
 * instance that shall be used.<br>
 * Note that there is always a 1:1 relation with a {@link org.eclipse.graphiti.ui.editor.DiagramEditor}.
 *
 * @since 0.9
 */
public class UpdateBehavior extends DefaultUpdateBehavior {

	/**
	 * @since 0.10
	 */
	protected final ECPDiagramBehavior diagramBehavior;

	/**
	 * The editing domain that is used throughout the {@link org.eclipse.graphiti.ui.editor.DiagramBehavior} is
	 * kept here and only here.
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * Closes editor if model object is deleted.
	 */
	private ElementDeleteListener elementDeleteListener;

	/**
	 * Is toggled by {@link DefaultUpdateBehavior#updateAdapter}.
	 */
	private boolean resourceDeleted;

	/**
	 * Is toggled by {@link DefaultUpdateBehavior#updateAdapter}.
	 */
	private boolean resourceChanged;

	/**
	 * Flag that indicates if the {@link #updateAdapter} shall be active or not.
	 * It may be deactivated when mass operations (e.g. saving the diagram
	 * editor with all its resources) take place. Use the methods {@link #isAdapterActive()} and
	 * {@link #setAdapterActive(boolean)} to
	 * access this field.
	 */
	private boolean adapterActive = true;

	/**
	 * The default update that cares about refreshing the diagram editor in case
	 * of resource changes. May be disabled by overriding {@link #isAdapterActive()} and returning false.
	 */
	private final Adapter updateAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification msg) {
			if (!isAdapterActive()) {
				return;
			}
			if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED) {
				if (msg.getNewBooleanValue() == Boolean.FALSE) {
					final Resource resource = (Resource) msg.getNotifier();
					final URI uri = resource.getURI();
					final IDiagramContainerUI diagramContainer = diagramBehavior.getDiagramContainer();
					if (editingDomain.getResourceSet().getURIConverter().exists(uri, null)) {
						// file content has changes
						setResourceChanged(true);
						final IWorkbenchPart activePart = diagramContainer.getWorkbenchPart().getSite().getPage()
							.getActivePart();
						if (activePart == diagramContainer) {
							getShell().getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									handleActivate();
								}
							});
						}
					} else {
						// file has been deleted
						if (!diagramContainer.isDirty()) {
							final IDiagramEditorInput editorInput = diagramContainer.getDiagramEditorInput();
							if (editorInput != null) {
								final IDiagramEditorInput input = editorInput;
								final URI inputUri = input.getUri();
								final URI diagUri = GraphitiUiInternal.getEmfService().mapDiagramFileUriToDiagramUri(
									uri);
								if (diagUri.equals(inputUri)) {
									startCloseEditorJob();
								}
							}
						} else {
							setResourceDeleted(true);
							final IWorkbenchPart activePart = diagramContainer.getWorkbenchPart().getSite().getPage()
								.getActivePart();
							if (activePart == diagramContainer) {
								getShell().getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										handleActivate();
									}
								});
							}
						}
					}
				}
			}
			super.notifyChanged(msg);
		}

		private void startCloseEditorJob() {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					closeContainer();
				}
			});
		}
	};

	/**
	 * Creates a new {@link DefaultUpdateBehavior} instance associated with the
	 * given {@link DiagramBehavior}.
	 *
	 * @param diagramBehavior
	 *            the diagram behavior
	 * @since 0.10
	 */
	public UpdateBehavior(ECPDiagramBehavior diagramBehavior) {
		super(diagramBehavior);
		this.diagramBehavior = diagramBehavior;
	}

	/**
	 * Returns the flag that indicates if the underlying resource of the {@link Diagram} has been deleted. Note that
	 * this flag will only be
	 * updated in case the {@link #updateAdapter} is enabled, see {@link #adapterActive}, {@link #isAdapterActive()} and
	 * {@link #setAdapterActive(boolean)}. If this flag is set the editor will
	 * close on receiving the next event.
	 *
	 * @return <code>true</code> in case the resource has been deleted, <code>false</code> otherwise
	 */
	@Override
	protected boolean isResourceDeleted() {
		return resourceDeleted;
	}

	/**
	 * Sets the flag that indicates if the underlying resource of the {@link Diagram} has been deleted. Note that this
	 * flag should only be
	 * updated by the {@link #updateAdapter}, see {@link #adapterActive}, {@link #isAdapterActive()} and
	 * {@link #setAdapterActive(boolean)}.
	 * <p>
	 * Should not be called by external clients.
	 *
	 * @param resourceDeleted
	 *            the value to set the flag to, <code>true</code> indicates that
	 *            the resource has been deleted.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Override
	public void setResourceDeleted(boolean resourceDeleted) {
		this.resourceDeleted = resourceDeleted;
	}

	/**
	 * Returns the flag that indicates if the underlying resource of the {@link Diagram} has been changed. Note that
	 * this flag will only be
	 * updated in case the {@link #updateAdapter} is enabled, see {@link #adapterActive}, {@link #isAdapterActive()} and
	 * {@link #setAdapterActive(boolean)}.
	 *
	 * @return <code>true</code> in case the resource has been changed, <code>false</code> otherwise
	 */
	@Override
	protected boolean isResourceChanged() {
		return resourceChanged;
	}

	/**
	 * Sets the flag that indicates if the underlying resource of the {@link Diagram} has been changed. Note that this
	 * flag should only be
	 * updated by the {@link #updateAdapter}, see {@link #adapterActive}, {@link #isAdapterActive()} and
	 * {@link #setAdapterActive(boolean)}.
	 * <p>
	 * Should not be called by external clients.
	 *
	 * @param resourceChanged
	 *            the value to set the flag to, <code>true</code> indicates that
	 *            the resource has been changed.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Override
	public void setResourceChanged(boolean resourceChanged) {
		this.resourceChanged = resourceChanged;
	}

	/**
	 * Handles activation of the editor. In case of the underlying diagram
	 * resource being deleted ({@link #resourceDeleted} is <code>true</code>)
	 * the editor will be closed after a call to {@link #handleDirtyConflict()} that returns <code>true</code>. Also it
	 * will call {@link #handleChangedResources()} in case the underlying diagram resource
	 * has changed ({@link #resourceChanged} is <code>true</code>).
	 */
	@Override
	public void handleActivate() {
		if (isResourceDeleted()) {
			if (handleDirtyConflict()) {
				closeContainer();
			} else {
				setResourceDeleted(false);
				setResourceChanged(false);
			}
		} else if (isResourceChanged()) {
			handleChangedResources();
			setResourceChanged(false);
		}
	}

	/**
	 * Returns the flag that indicates if the {@link #updateAdapter} shall be
	 * active of not ({@link #adapterActive}). In case this method returns <code>false</code>, the
	 * {@link #updateAdapter} will do nothing on being
	 * called.
	 *
	 * @return <code>true</code> in case the adapter shall run, <code>false</code> otherwise.
	 */
	@Override
	protected boolean isAdapterActive() {
		return adapterActive;
	}

	/**
	 * Sets the flag that indicates if the {@link #updateAdapter} shall be
	 * active of not ({@link #adapterActive}).
	 *
	 * @param active
	 *            the new value for the flag
	 */
	@Override
	public void setAdapterActive(boolean active) {
		adapterActive = active;
	}

	/**
	 * Handles what to do with changed resources on editor activation.
	 */
	@Override
	protected void handleChangedResources() {
		if (!diagramBehavior.getDiagramContainer().isDirty() || handleDirtyConflict()) {

			// Disable adapters temporarily.
			diagramBehavior.disableAdapters();

			try {
				// We unload our resources such that refreshEditorContent does a
				// complete diagram refresh.
				final EList<Resource> resources = getEditingDomain().getResourceSet().getResources();
				for (final Resource resource : resources) {
					resource.unload();
				}
				diagramBehavior.refreshContent();
			} finally {
				// Re-enable adapters again
				diagramBehavior.enableAdapters();
			}
		}
	}

	/**
	 * Shows a dialog that asks if conflicting changes should be discarded or
	 * not. See {@link #handleActivate()}.
	 *
	 * @return <code>true</code> in case the editor shall be closed, <code>false</code> otherwise
	 */
	@Override
	protected boolean handleDirtyConflict() {
		return MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			Messages.DiscardChangesDialog_0_xmsg, Messages.DiscardChangesDialog_1_xmsg);
	}

	/**
	 * This returns the editing domain as required by the {@link org.eclipse.emf.edit.domain.IEditingDomainProvider}
	 * interface.
	 *
	 * @return The {@link TransactionalEditingDomain} that is used within this
	 *         editor
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * Initializes listeners and adapters.
	 */
	@Override
	public void init() {
		for (final Resource r : getEditingDomain().getResourceSet().getResources()) {
			r.eAdapters().add(updateAdapter);
		}

		// Retrieve the object from the editor input
		final EObject object = (EObject) diagramBehavior.getAdapter(Diagram.class);
		// Register for object deletion
		if (object != null) {
			elementDeleteListener = new ElementDeleteListener(diagramBehavior);
			object.eAdapters().add(elementDeleteListener);
		}

	}

	// /**
	// * Created the {@link TransactionalEditingDomain} that shall be used within
	// * the diagram editor and initializes it by delegating to
	// * {@link #initializeEditingDomain(TransactionalEditingDomain)}.
	// */
	// @Override
	// protected void createEditingDomain() {
	// final TransactionalEditingDomain editingDomain = (TransactionalEditingDomain) AdapterFactoryEditingDomain
	// .getEditingDomainFor(diagramBehavior.getDiagram());
	// initializeEditingDomain(editingDomain);
	// }

	/**
	 * This sets up the editing domain for this model editor.
	 *
	 * @param domain
	 *            The {@link TransactionalEditingDomain} that is used within
	 *            this model editor
	 */
	@Override
	protected void initializeEditingDomain(TransactionalEditingDomain domain) {
		editingDomain = domain;
		final ResourceSet resourceSet = domain.getResourceSet();

		// Problem analysis
		diagramBehavior.editingDomainInitialized();
	}

	/**
	 * Disposes this {@link DefaultUpdateBehavior} and free all resources it
	 * holds. In case you only want to omit or influence the disposal of the {@link TransactionalEditingDomain}, you can
	 * also override {@link #disposeEditingDomain()}.
	 */
	@Override
	public void dispose() {

		for (final Resource r : editingDomain.getResourceSet().getResources()) {
			r.eAdapters().remove(updateAdapter);
		}

		final EObject object = (EObject) diagramBehavior.getAdapter(Diagram.class);
		if (object != null) {
			object.eAdapters().remove(elementDeleteListener);
		}

		// Remove reference
		disposeEditingDomain();
		editingDomain = null;
	}

	/**
	 * Cares about disposing the {@link TransactionalEditingDomain} held in this
	 * instance. Is called during the {@link #dispose()} method.
	 */
	@Override
	protected void disposeEditingDomain() {
		return;
	}

	/**
	 * Is called by the operation history of the {@link TransactionalEditingDomain} in case the history changes. Reacts
	 * on
	 * undo and redo events and updates the dirty state of the editor.
	 *
	 * @param event
	 *            the {@link OperationHistoryEvent} to react upon
	 */
	@Override
	public void historyNotification(OperationHistoryEvent event) {
		switch (event.getEventType()) {
		case OperationHistoryEvent.REDONE:
		case OperationHistoryEvent.UNDONE:
			diagramBehavior.getDiagramContainer().updateDirtyState();
			break;
		}
	}

	private Shell getShell() {
		return diagramBehavior.getDiagramContainer().getSite().getShell();
	}

	/**
	 * @since 0.10
	 */
	@Override
	public void setEditingDomain(TransactionalEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
		initializeEditingDomain(editingDomain);
	}
}
