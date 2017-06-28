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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.internal.databinding.observable.DelayedObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.common.callback.ViewModelPropertiesUpdateCallback;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.DetailPanelRenderingFinishedCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class MasterDetailRenderer.
 * It is the base renderer for the editor.
 *
 * It takes any object as input and renders a tree on the left-hand side.
 * When selecting an item in the tree (that is an EObject) EMF-Forms is used to render the detail pane on the right-hand
 * side
 *
 * MasterDetailRenderer implements IEditingDomainProvider to allow Undo/Redo/Copy/Cut/Paste actions to be performed
 * externally.
 *
 * MasterDetailRenderer provides an ISelectionProvider to get the currently selected items in the tree
 *
 */
@SuppressWarnings("restriction")
public class TreeMasterDetailComposite extends Composite implements IEditingDomainProvider {

	/** The input. */
	private final Object input;

	/** The editing domain. */
	private final EditingDomain editingDomain;

	/** The tree viewer. */
	private TreeViewer treeViewer;

	/** The vertical sash. */
	private Sash verticalSash;

	/** The detail scrollable composite. */
	private Composite detailComposite;

	/** The detail panel. */
	private Composite detailPanel;

	/** The currently rendered ECPSWTView. */
	private ECPSWTView renderedView;
	private final Shell limbo;

	private Object lastRenderedObject;

	private final TreeMasterDetailSWTCustomization customization;
	private TreeMasterDetailCache cache = new TreeMasterDetailCache() {

		@Override
		public boolean isChached(EObject selection) {
			return false;
		}

		@Override
		public ECPSWTView getCachedView(EObject selection) {
			return null;
		}

		@Override
		public void cache(ECPSWTView ecpView) {
			ecpView.dispose();
		}
	};

	/** the delay between a selection change and the start of the rendering. */
	private final int renderDelay;

	private ViewModelPropertiesUpdateCallback viewModelPropertiesUpdateCallback;
	private final Set<DetailPanelRenderingFinishedCallback> detailPanelRenderingFinishedCallbacks = new LinkedHashSet<DetailPanelRenderingFinishedCallback>();

	/** The CreateElementCallback to allow modifications to the newly created element. */

	/**
	 * The context. It is used in the same way as in TreeMasterDetail.
	 * It allows custom viewmodels for the detail panel
	 */
	private static VViewModelProperties context = VViewFactory.eINSTANCE.createViewModelLoadingProperties();

	static {
		context.addNonInheritableProperty("detail", true);
	}

	/**
	 * Default constructor.
	 *
	 * @param parent the parent composite
	 * @param style the style bits
	 * @param input the input for the tree
	 * @param customization the customization
	 * @param renderDelay the delay between a selection change and updating the detail
	 */
	/* package */ TreeMasterDetailComposite(Composite parent, int style, Object input,
		TreeMasterDetailSWTCustomization customization, int renderDelay) {
		super(parent, style);
		this.input = input;
		if (input instanceof Resource) {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(((Resource) input).getContents().get(0));
		} else if (input instanceof RootObject) {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(RootObject.class.cast(input).getRoot());
		} else {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(input);
		}
		this.renderDelay = renderDelay;
		this.customization = customization;
		limbo = new Shell(Display.getCurrent(), SWT.NONE);
		// Place the limbo shell 'off screen'
		limbo.setLocation(0, 10000);
		limbo.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		limbo.setBackgroundMode(SWT.INHERIT_FORCE);
		renderControl(customization);
	}

	private Control renderControl(TreeMasterDetailSWTCustomization buildBehaviour) {
		// Create the Form with two panels and a header
		setLayout(new FormLayout());

		// Create the Separator
		verticalSash = createSash(this, buildBehaviour);

		// Create the Tree
		final Composite treeComposite = new Composite(this, SWT.NONE);
		addTreeViewerLayoutData(treeComposite, verticalSash);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(treeComposite);
		treeViewer = TreeViewerSWTFactory.createTreeViewer(treeComposite, input, customization);

		// Create detail composite
		detailComposite = buildBehaviour.createDetailComposite(this);
		addDetailCompositeLayoutData(detailComposite, verticalSash);

		/* enable delayed update mechanism */
		final IViewerObservableValue treeViewerSelectionObservable = ViewersObservables
			.observeSingleSelection(treeViewer);
		final DelayedObservableValue delayedObservableValue = new DelayedObservableValue(renderDelay,
			treeViewerSelectionObservable);
		delayedObservableValue.addChangeListener(new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {
				doUpdateDetailPanel(false);
			}
		});

		/* add key listener to switch focus on enter */
		treeViewer.getTree().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.LF) {
					doUpdateDetailPanel(true);
				}
			}

		});

		/* add double click listener to switch focus on enter */
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				doUpdateDetailPanel(true);
			}
		});

		updateDetailPanel(false);

		return this;
	}

	private void setFocusToDetail() {
		if (renderedView == null || renderedView.getSWTControl().isDisposed()) {
			return;
		}
		renderedView.getSWTControl().setFocus();
	}

	private void addDetailCompositeLayoutData(Composite detailComposite, Sash verticalSash) {
		final FormData detailFormData = new FormData();
		detailFormData.left = new FormAttachment(verticalSash, 2);
		detailFormData.top = new FormAttachment(0, 5);
		detailFormData.bottom = new FormAttachment(100, -5);
		detailFormData.right = new FormAttachment(100, -5);
		detailComposite.setLayoutData(detailFormData);
	}

	private void addTreeViewerLayoutData(Composite treeComposite, Sash verticalSash) {
		final FormData treeFormData = new FormData();
		treeFormData.bottom = new FormAttachment(100, -5);
		treeFormData.left = new FormAttachment(0, 5);
		treeFormData.right = new FormAttachment(verticalSash, -2);
		treeFormData.top = new FormAttachment(0, 5);
		treeComposite.setLayoutData(treeFormData);
	}

	private Sash createSash(final Composite parent, TreeWidthProvider buildBehaviour) {
		final Sash sash = new Sash(parent, SWT.VERTICAL);

		// Make the left panel 300px wide and put it below the header
		final FormData sashFormData = new FormData();
		sashFormData.bottom = new FormAttachment(100, -5);
		sashFormData.left = new FormAttachment(0, buildBehaviour.getInitialTreeWidth());
		sashFormData.top = new FormAttachment(0, 5);
		sash.setLayoutData(sashFormData);

		// As soon as the sash is moved, layout the parent to reflect the changes
		sash.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				sash.setLocation(e.x, e.y);

				final FormData sashFormData = new FormData();
				sashFormData.bottom = new FormAttachment(100, -5);
				sashFormData.left = new FormAttachment(0, e.x);
				sashFormData.top = new FormAttachment(0, 5);
				sash.setLayoutData(sashFormData);
				parent.layout(true);
			}
		});
		return sash;
	}

	// TODO JF this needs to be refactored, when used as the replacement for the treemasterdetail renderer.
	// selection modification required as well as adjusting the loading properties
	/**
	 * Updates the detail panel of the tree master detail.
	 *
	 * @param setFocusToDetail <code>true</code> if the focus should be moved to the detail panel
	 *
	 * @since 1.11
	 */
	public void updateDetailPanel(final boolean setFocusToDetail) {
		// Create a new detail panel in the scrollable composite. Disposes any old panels.
		// createDetailPanel();
		// TODO create detail panel at the right location

		// Get the selected object, if it is an EObject, render the details using EMF Forms
		final Object selectedObject = treeViewer.getSelection() != null ? ((StructuredSelection) treeViewer
			.getSelection()).getFirstElement() : null;

		boolean asyncRendering = false;
		if (selectedObject instanceof EObject) {
			lastRenderedObject = selectedObject;
			final EObject eObject = EObject.class.cast(selectedObject);
			if (renderedView != null && !renderedView.getSWTControl().isDisposed()) {
				renderedView.getSWTControl().setParent(limbo);
				cache.cache(renderedView);
			}
			createDetailPanel();
			if (cache.isChached(eObject)) {
				renderedView = cache.getCachedView(eObject);
				renderedView.getSWTControl().setParent(detailPanel);
				/*
				 * layout detail, since the size of the window might have changed (e.g. made smaller). layout is
				 * required to make sure that scrollbars, etc. are updated
				 */
				detailPanel.layout();
				renderedView.getViewModelContext().changeDomainModel(eObject);
				updateScrolledComposite();
			} else {
				if (viewModelPropertiesUpdateCallback != null) {
					viewModelPropertiesUpdateCallback.updateViewModelProperties(context);
				}
				// Check, if the selected object would be rendered using a TreeMasterDetail. If so, render the provided
				// detail view.
				final VView view = ViewProviderHelper.getView((EObject) selectedObject, context);
				if (view.getChildren().size() > 0 && view.getChildren().get(0) instanceof VTreeMasterDetail) {
					// Yes, we need to render this node differently
					final VTreeMasterDetail vTreeMasterDetail = (VTreeMasterDetail) view.getChildren().get(0);
					try {
						renderedView = ECPSWTViewRenderer.INSTANCE.render(detailPanel, (EObject) selectedObject,
							vTreeMasterDetail.getDetailView());
						detailPanel.layout(true, true);
					} catch (final ECPRendererException e) {
					}

				} else {
					// No, everything is fine
					final Label label = new Label(detailPanel, SWT.NONE);
					label.setText("loading...");
					GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, true).applyTo(label);
					detailPanel.layout(true, true);
					asyncRendering = true;
					Display.getDefault().asyncExec(new UpdateDetailRunnable(setFocusToDetail, eObject, label));
				}
				// After rendering the Forms, compute the size of the form. So the scroll container knows when to scroll
				updateScrolledComposite();
			}
		} else {
			renderEmptyDetailPanel();
		}

		/*
		 * Notify the callbacks that the rendering has been finished.
		 * In case of async rendering, the async process needs to notify the callbacks.
		 */
		if (!asyncRendering) {
			for (final DetailPanelRenderingFinishedCallback callback : detailPanelRenderingFinishedCallbacks) {
				callback.renderingFinished(selectedObject);
			}
		}
	}

	private void updateScrolledComposite() {
		if (ScrolledComposite.class.isInstance(detailComposite)) {
			ScrolledComposite.class.cast(detailComposite)
				.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
	}

	private void renderEmptyDetailPanel() {
		lastRenderedObject = null;
		if (renderedView != null && !renderedView.getSWTControl().isDisposed()) {
			renderedView.getSWTControl().setParent(limbo);
			cache.cache(renderedView);
			/* set renderedView to null so that it is not offered to the cache further times */
			renderedView = null;
		}
		createDetailPanel();
		final Label hint = new Label(detailPanel, SWT.CENTER);
		final FontDescriptor boldDescriptor = FontDescriptor.createFrom(hint.getFont()).setHeight(18)
			.setStyle(SWT.BOLD);
		final Font boldFont = boldDescriptor.createFont(hint.getDisplay());
		hint.setFont(boldFont);
		hint.setForeground(new Color(hint.getDisplay(), 190, 190, 190));
		hint.setText("Select a node in the tree to edit it");
		final GridData hintLayoutData = new GridData();
		hintLayoutData.grabExcessVerticalSpace = true;
		hintLayoutData.grabExcessHorizontalSpace = true;
		hintLayoutData.horizontalAlignment = SWT.CENTER;
		hintLayoutData.verticalAlignment = SWT.CENTER;
		hint.setLayoutData(hintLayoutData);

		detailPanel.pack();
		detailPanel.layout(true, true);

		updateScrolledComposite();
	}

	/**
	 * Creates the detail panel.
	 *
	 * @return the control
	 */
	private Control createDetailPanel() {
		// Dispose old panels to avoid memory leaks
		if (detailPanel != null) {
			detailPanel.dispose();
		}

		detailPanel = new Composite(detailComposite, SWT.BORDER);
		detailPanel.setLayout(new GridLayout());
		detailPanel.setBackground(new Color(Display.getDefault(), new RGB(255, 255, 255)));
		detailPanel.setBackgroundMode(SWT.INHERIT_FORCE);
		if (ScrolledComposite.class.isInstance(detailComposite)) {
			ScrolledComposite.class.cast(detailComposite).setContent(detailPanel);
		}

		detailComposite.layout(true, true);
		return detailPanel;
	}

	@Override
	public void dispose() {
		limbo.dispose();
		customization.dispose();
		super.dispose();
	}

	/**
	 * Gets the current selection.
	 *
	 * @return the current selection
	 */
	public Object getCurrentSelection() {
		if (!(treeViewer.getSelection() instanceof StructuredSelection)) {
			return null;
		}
		return ((StructuredSelection) treeViewer.getSelection()).getFirstElement();
	}

	/**
	 * Sets the selection.
	 *
	 * @param structuredSelection the new selection
	 * @since 1.9
	 */
	public void setSelection(ISelection structuredSelection) {
		treeViewer.setSelection(structuredSelection);
	}

	/**
	 * Gets the selection provider.
	 *
	 * @return the selection provider
	 */
	public TreeViewer getSelectionProvider() {
		return treeViewer;
	}

	/**
	 * Gets the editing domain.
	 *
	 * @return the editing domain
	 */
	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * Allows to set a different input for the treeviewer.
	 *
	 * @param input the new input
	 */
	public void setInput(Object input) {
		treeViewer.setInput(input);
	}

	/**
	 * Allows to override the default cache implementation by the provided one.
	 *
	 * @param cache The {@link TreeMasterDetailCache} to use.
	 * @since 1.9
	 */
	public void setCache(TreeMasterDetailCache cache) {
		if (cache != null) {
			this.cache = cache;
		}
	}

	private void doUpdateDetailPanel(boolean setFocusToDetail) {
		if (lastRenderedObject == getCurrentSelection()) {
			if (setFocusToDetail) {
				setFocusToDetail();
			}
			/*
			 * possible when e.g. a double click or enter has forced an instant rendering and the delay update kicks in.
			 */
			return;
		}
		updateDetailPanel(setFocusToDetail);
	}

	/**
	 * Runnable which updates the detail panel.
	 */
	private final class UpdateDetailRunnable implements Runnable {
		private final boolean setFocusToDetail;
		private final EObject eObject;
		private final Label label;

		UpdateDetailRunnable(boolean setFocusToDetail, EObject eObject, Label label) {
			this.setFocusToDetail = setFocusToDetail;
			this.eObject = eObject;
			this.label = label;
		}

		@Override
		public void run() {
			try {
				if (viewModelPropertiesUpdateCallback != null) {
					viewModelPropertiesUpdateCallback.updateViewModelProperties(context);
				}
				final VView view = ViewProviderHelper.getView(eObject, context);
				final ViewModelContext modelContext = ViewModelContextFactory.INSTANCE
					.createViewModelContext(
						view, eObject, customization.getViewModelServices(view, eObject));
				renderedView = ECPSWTViewRenderer.INSTANCE.render(limbo, modelContext);
				label.dispose();
				if (detailPanel.isDisposed()) {
					return;
				}
				renderedView.getSWTControl().setParent(detailPanel);
				detailPanel.layout(true, true);
				if (ScrolledComposite.class.isInstance(detailComposite)) {
					ScrolledComposite.class.cast(detailComposite)
						.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				}
				if (setFocusToDetail) {
					setFocusToDetail();
				}
				// notify callbacks that the rendering was finished
				for (final DetailPanelRenderingFinishedCallback callback : detailPanelRenderingFinishedCallbacks) {
					callback.renderingFinished(eObject);
				}
			} catch (final ECPRendererException e) {
			}
		}
	}

	/**
	 * Adds a {@link ViewModelPropertiesUpdateCallback}.
	 *
	 * @param viewModelPropertiesUpdateCallback the callback
	 * @since 1.11
	 */
	public void addViewModelPropertiesUpdateCallback(
		ViewModelPropertiesUpdateCallback viewModelPropertiesUpdateCallback) {
		this.viewModelPropertiesUpdateCallback = viewModelPropertiesUpdateCallback;
	}

	/**
	 * Register a callback that is notified whenever the rendering of a detail panel is finished.
	 *
	 * @param detailPanelRenderingFinishedCallback the callback
	 * @return <code>true</code> if the callback has been added, <code>false</code> if it was already registered
	 * @since 1.13
	 */
	public boolean registerDetailPanelRenderingFinishedCallback(
		DetailPanelRenderingFinishedCallback detailPanelRenderingFinishedCallback) {
		return detailPanelRenderingFinishedCallbacks.add(detailPanelRenderingFinishedCallback);
	}

	/**
	 * Register a callback that is notified whenever the rendering of a detail panel is finished.
	 *
	 * @param detailPanelRenderingFinishedCallback the callback
	 * @return <code>true</code> if the callback has been removed, <code>false</code> if it was not registered
	 * @since 1.13
	 */
	public boolean unregisterDetailPanelRenderingFinishedCallback(
		DetailPanelRenderingFinishedCallback detailPanelRenderingFinishedCallback) {
		return detailPanelRenderingFinishedCallbacks.remove(detailPanelRenderingFinishedCallback);
	}
}
