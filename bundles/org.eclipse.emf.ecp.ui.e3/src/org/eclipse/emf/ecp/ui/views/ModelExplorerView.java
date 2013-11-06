/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.views;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.model.ModelContentProvider;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.spi.ui.UIProvider;
import org.eclipse.emf.ecp.spi.ui.UIProviderRegistry;
import org.eclipse.emf.ecp.ui.common.TreeViewerFactory;
import org.eclipse.emf.ecp.ui.linkedView.ILinkedWithEditorView;
import org.eclipse.emf.ecp.ui.linkedView.LinkedWithEditorPartListener;
import org.eclipse.emf.ecp.ui.platform.Activator;
import org.eclipse.emf.ecp.ui.tester.SaveButtonEnablementObserver;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.PartInitException;

/**
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public class ModelExplorerView extends TreeView implements ILinkedWithEditorView {
	/**
	 * @author Jonas
	 * @author Eugen Neufeld
	 */
	public class DoubleClickListener implements IDoubleClickListener {

		/**
		 * Opens an EObject using the ActionHelper or opens a closed ECPProject. {@inheritDoc}
		 */
		public void doubleClick(DoubleClickEvent event) {
			if (event.getSelection() instanceof IStructuredSelection) {
				final IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
				final Object firstElement = structuredSelection.getFirstElement();

				if (firstElement instanceof ECPProject) {
					final ECPProject project = (ECPProject) firstElement;
					if (!project.isOpen()) {
						project.open();
					}
				}
				else if (firstElement instanceof Object) {
					final ECPContainer context = ECPUtil
						.getModelContext(contentProvider, structuredSelection.toArray());
					ECPHandlerHelper.openModelElement(firstElement, (ECPProject) context);
				}
			}
		}
	}

	/**
	 * The id of this view.
	 */
	public static final String ID = "org.eclipse.emf.ecp.ui.ModelExplorerView";

	private final IPartListener2 linkWithEditorPartListener = new LinkedWithEditorPartListener(this);

	private boolean linkingActive = true;

	private ModelContentProvider contentProvider;

	private TreeViewer viewer;

	private Action linkWithEditorAction;

	/**
	 * Default Constructor.
	 */
	public ModelExplorerView() {
		super(ID);
	}

	@Override
	protected TreeViewer createViewer(final Composite parent) {
		viewer = TreeViewerFactory.createModelExplorerViewer(parent, true, createLabelDecorator());
		contentProvider = (ModelContentProvider) viewer.getContentProvider();
		viewer.addDoubleClickListener(new DoubleClickListener());
		viewer.addSelectionChangedListener(new ModelExplorerViewSelectionListener());
		viewer.getControl().addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
			}

			public void focusLost(FocusEvent event) {
				ECPUtil.getECPObserverBus().notify(SaveButtonEnablementObserver.class)
					.notifyChangeButtonState(null, false);
			}

		});

		final IConfigurationElement[] modelExplorerSettings = Platform.getExtensionRegistry()
			.getConfigurationElementsFor("org.eclipse.emf.ecp.ui.modelExplorerSettings"); //$NON-NLS-1$
		if (modelExplorerSettings.length == 1) {
			if (modelExplorerSettings[0].getAttribute("viewSorter") != null) {//$NON-NLS-1$
				try {
					final ViewerSorter sorter = (ViewerSorter) modelExplorerSettings[0]
						.createExecutableExtension("viewSorter");
					viewer.setSorter(sorter);
				} catch (final CoreException e) {
					Activator.log(e);
				}

			}
		}
		return viewer;
	}

	@Override
	protected void fillLocalToolBar(IToolBarManager manager) {
		if (getDialogSettings().getBoolean("LinkWithEditorSet")) {
			linkingActive = getDialogSettings().getBoolean("LinkWithEditor");
		}
		if (linkingActive) {
			getSite().getPage().addPartListener(linkWithEditorPartListener);
		}

		linkWithEditorAction = new Action("Link with editor", SWT.TOGGLE) {

			@Override
			public void run() {
				if (linkingActive) {
					linkingActive = false;
					getSite().getPage().removePartListener(linkWithEditorPartListener);
				} else {
					linkingActive = true;
					getSite().getPage().addPartListener(linkWithEditorPartListener);
					final IEditorPart editor = getSite().getPage().getActiveEditor();
					if (editor != null) {
						editorActivated(editor);
					}
				}

				getDialogSettings().put("LinkWithEditor", isChecked());
				getDialogSettings().put("LinkWithEditorSet", true);
			}

		};

		linkWithEditorAction.setImageDescriptor(Activator.getImageDescriptor("icons/link_with_editor.gif"));
		linkWithEditorAction.setToolTipText("Link with editor");
		linkWithEditorAction.setChecked(getDialogSettings().getBoolean("LinkWithEditorSet") ? getDialogSettings()
			.getBoolean("LinkWithEditor") : true);
		manager.add(linkWithEditorAction);
	}

	private IDialogSettings getDialogSettings() {
		return Activator.getDefault().getDialogSettings();
	}

	@Override
	protected void fillContextMenu(IMenuManager manager) {
		final Object[] elements = getSelection().toArray();

		final ECPContainer context = ECPUtil.getModelContext(contentProvider, elements);
		if (context != null) {
			final UIProvider provider = UIProviderRegistry.INSTANCE.getUIProvider(context);
			if (provider != null) {
				provider.fillContextMenu(manager, context, elements);
			}
		}

		super.fillContextMenu(manager);
	}

	/** {@inheritDoc} */
	public void editorActivated(IEditorPart activatedEditor) {
		if (!linkingActive || !getViewSite().getPage().isPartVisible(this)) {
			return;
		}
		final Object input = activatedEditor.getEditorInput().getAdapter(EObject.class);
		if (input != null) {
			viewer.setSelection(new StructuredSelection(input), true);
		}
	}

	/**
	 * 
	 * @author jfaltermeier
	 * 
	 */
	private class ModelExplorerViewSelectionListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			notifyAboutSaveButtonState(event);
			if (linkingActive) {
				final Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (selected instanceof EObject) {
					for (final IEditorReference editorRef : getSite().getPage().getEditorReferences()) {
						Object editorInput = null;
						try {

							editorInput = editorRef.getEditorInput().getAdapter(EObject.class);
						} catch (final PartInitException e) {
							e.printStackTrace();
						}
						if (selected.equals(editorInput)) {
							getSite().getPage().bringToTop(editorRef.getPart(true));
							return;
						}
					}
				}
			}
		}

		private void notifyAboutSaveButtonState(SelectionChangedEvent event) {
			final boolean selectedProjectIsDirty;

			final Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			final ECPProject project = ECPUtil.getECPProjectManager().getProject(selected);

			if (project == null) {
				selectedProjectIsDirty = false;
			} else {
				selectedProjectIsDirty = project.hasDirtyContents();
			}

			ECPUtil.getECPObserverBus().notify(SaveButtonEnablementObserver.class)
				.notifyChangeButtonState(project, selectedProjectIsDirty);
		}
	}
}
