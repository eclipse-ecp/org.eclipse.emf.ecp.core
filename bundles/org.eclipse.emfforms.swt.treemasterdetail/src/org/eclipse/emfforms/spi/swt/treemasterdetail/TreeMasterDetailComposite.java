/*******************************************************************************
 * Copyright (c) 2011-2020 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bugs 533568, 545460, 527686, 548592, 559116
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import static org.eclipse.emfforms.spi.localization.LocalizationServiceHelper.getString;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.observable.DelayedObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.common.callback.ViewModelPropertiesUpdateCallback;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewCache;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewManager;
import org.eclipse.emf.ecp.view.spi.swt.selection.IMasterDetailSelectionProvider;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailFocusAdapter;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailSelectionProvider;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.DetailPanelRenderingFinishedCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

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

	/** The selection provider. */
	private IMasterDetailSelectionProvider selectionProvider;

	/** The vertical sash. */
	private Sash verticalSash;

	/** The detail scrollable composite. */
	private Composite detailComposite;

	/** Manager of the currently rendered ECPSWTView with caching. */
	private DetailViewManager detailManager;

	private final String selectNodeMessage = getString(getClass(), "selectNodeMessage"); //$NON-NLS-1$
	private final String loadingMessage = getString(getClass(), "loadingMessage"); //$NON-NLS-1$

	private Object lastRenderedObject;

	private final TreeMasterDetailSWTCustomization customization;

	/** the delay between a selection change and the start of the rendering. */
	private final int renderDelay;

	private ViewModelPropertiesUpdateCallback viewModelPropertiesUpdateCallback;
	private final Set<DetailPanelRenderingFinishedCallback> detailPanelRenderingFinishedCallbacks = new LinkedHashSet<DetailPanelRenderingFinishedCallback>();

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

		renderControl(customization);

		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				TreeMasterDetailComposite.this.dispose();
			}
		});
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
		selectionProvider = new MasterDetailSelectionProvider(treeViewer);
		treeViewer.getControl().addFocusListener(
			new MasterDetailFocusAdapter(selectionProvider, () -> detailManager.getDetailContainer()));

		// Create detail composite
		detailComposite = buildBehaviour.createDetailComposite(this);
		addDetailCompositeLayoutData(detailComposite, verticalSash);
		Composite detailParent = detailComposite;
		if (detailParent instanceof ScrolledComposite) {
			final Composite detailPanel = new Composite(detailParent, SWT.BORDER);
			((ScrolledComposite) detailParent).setContent(detailPanel);
			detailParent = detailPanel;
		}
		detailManager = new DetailViewManager(detailParent);
		detailManager.setNoDetailMessage(selectNodeMessage);
		detailManager.layoutDetailParent(detailParent);

		/* enable optional delayed update mechanism */
		IObservableValue<?> treeViewerSelectionObservable = ViewerProperties.singleSelection().observe(treeViewer);
		if (renderDelay > 0) {
			treeViewerSelectionObservable = new DelayedObservableValue<>(renderDelay,
				treeViewerSelectionObservable);
		}
		treeViewerSelectionObservable.addChangeListener(__ -> doUpdateDetailPanel(false));

		final IObservableValue<?> observableToDispose = treeViewerSelectionObservable;
		treeComposite.addDisposeListener(__ -> observableToDispose.dispose());

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
		detailManager.setFocus();
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
		final IStructuredSelection selection = (StructuredSelection) treeViewer.getSelection();
		final Object selectedObject = getSelectedObject(selection);
		detailManager.cacheCurrentDetail();

		boolean asyncRendering = false;
		ViewModelContext context = null;
		if (selectedObject instanceof EObject) {
			lastRenderedObject = selectedObject;
			final EObject eObject = EObject.class.cast(selectedObject);

			if (detailManager.isCached(eObject)) {
				// It's ready to present (no async needed)
				context = detailManager.activate(eObject).getViewModelContext();

				updateScrolledComposite();
			} else {
				if (viewModelPropertiesUpdateCallback != null) {
					viewModelPropertiesUpdateCallback.updateViewModelProperties(detailManager.getDetailProperties());
				}
				// Check, if the selected object would be rendered using a TreeMasterDetail. If so, render the provided
				// detail view.
				final VView view = detailManager.getDetailView(eObject);
				if (view.getChildren().size() > 0 && view.getChildren().get(0) instanceof VTreeMasterDetail) {
					// Yes, we need to render this node differently
					final VView treeDetailView = VTreeMasterDetail.class.cast(view.getChildren().get(0))
						.getDetailView();
					// Even if the TMD composite is not configured as read-only honor the effective read-only
					// configuration of the loaded detail view
					treeDetailView.setReadonly(treeDetailView.isEffectivelyReadonly() || customization.isReadOnly());
					context = ViewModelContextFactory.INSTANCE.createViewModelContext(treeDetailView, eObject);
					detailManager.render(context, ECPSWTViewRenderer.INSTANCE::render);
				} else {
					// No, everything is fine
					detailManager.setNoDetailMessage(loadingMessage);
					asyncRendering = true;
					Display.getDefault().asyncExec(new UpdateDetailRunnable(setFocusToDetail, eObject));
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
				callback.renderingFinished(context, selectedObject);
			}
		}
	}

	private Object getSelectedObject(IStructuredSelection selection) {
		// Get the selected object, if it is an EObject, render the details using EMF Forms
		Object selectedObject = selection != null ? selection.getFirstElement() : null;
		if (customization.enableVerticalCopy() && selectedObject instanceof EObject && selection.size() > 1) {
			boolean allOfSameType = true;
			final EObject dummy = EcoreUtil.create(((EObject) selectedObject).eClass());

			final Iterator<?> iterator = selection.iterator();
			final Set<EObject> selectedEObjects = new LinkedHashSet<EObject>();
			while (iterator.hasNext()) {
				final EObject eObject = (EObject) iterator.next();
				allOfSameType &= eObject.eClass() == dummy.eClass();
				if (allOfSameType) {
					for (final EAttribute attribute : dummy.eClass().getEAllAttributes()) {
						if (eObject == selectedObject) {
							dummy.eSet(attribute, eObject.eGet(attribute));
						} else if (dummy.eGet(attribute) != null
							&& !dummy.eGet(attribute).equals(eObject.eGet(attribute))) {
							dummy.eUnset(attribute);
						}
					}
					selectedEObjects.add(eObject);
				} else {
					break;
				}
			}
			if (allOfSameType) {
				selectedObject = dummy;
				dummy.eAdapters().add(new MultiEditAdapter(selectedEObjects, dummy));
			}
		}
		return selectedObject;
	}

	private void updateScrolledComposite() {
		if (ScrolledComposite.class.isInstance(detailComposite)) {
			ScrolledComposite.class.cast(detailComposite)
				.setMinSize(detailManager.getDetailContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
	}

	private void renderEmptyDetailPanel() {
		lastRenderedObject = null;
		detailManager.cacheCurrentDetail();

		updateScrolledComposite();
	}

	@Override
	public void dispose() {
		detailManager.dispose();
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
	 * Gets the tree viewer.
	 *
	 * @return the tree viewer (which is a selection provider)
	 *
	 * @deprecated Use the {@link #getMasterDetailSelectionProvider() master-detail selection provider}, instead},
	 *             or {@link #refresh()} to force a refresh of the tree, or {@link #selectAndReveal(Object)}
	 *             to select and reveal some object in my tree
	 * @see #getMasterDetailSelectionProvider()
	 */
	@Deprecated
	public TreeViewer getSelectionProvider() {
		return treeViewer;
	}

	/**
	 * Get the master/detail-aware selection provider.
	 *
	 * @return a selection provider that is aware of the user's focus on either the
	 *         master tree or the detail view
	 * @since 1.21
	 */
	public ISelectionProvider getMasterDetailSelectionProvider() {
		return selectionProvider;
	}

	/**
	 * Request a refresh of my tree.
	 *
	 * @since 1.22
	 */
	public void refresh() {
		if (treeViewer != null) {
			treeViewer.refresh();
		}
	}

	/**
	 * Select and reveal a {@code selection} in my tree. If the argument is an {@link UniqueSetting},
	 * then the {@linkplain UniqueSetting#getEObject() owner} of the setting will be revealed and the
	 * control that edits the {@linkplain UniqueSetting#getEStructuralFeature() setting} will be
	 * revealed and focused (if possible) in the object's detail view.
	 *
	 * @param selection the objet to select and reveal
	 * @return {@code true} if the {@code selection} was revealed; {@code false}, otherwise, including
	 *         the case where the nearest parent object up the tree was revealed instead
	 * @since 1.22
	 */
	public boolean selectAndReveal(final Object selection) {
		boolean result = false;

		Object toReveal = selection;
		final EStructuralFeature feature;

		if (selection instanceof UniqueSetting) {
			final UniqueSetting setting = (UniqueSetting) selection;
			toReveal = setting.getEObject();
			feature = setting.getEStructuralFeature();
		} else if (selection instanceof EStructuralFeature.Setting) {
			final EStructuralFeature.Setting setting = (EStructuralFeature.Setting) selection;
			toReveal = setting.getEObject();
			feature = setting.getEStructuralFeature();
		} else {
			feature = null;
		}

		if (feature != null) {
			final CompletableFuture<ECPSWTView> renderedDetail = new CompletableFuture<>();
			final DetailPanelRenderingFinishedCallback detailReady = __ -> renderedDetail
				.complete(detailManager.getCurrentDetail());
			registerDetailPanelRenderingFinishedCallback(detailReady);

			final EObject owner = (EObject) toReveal;
			result = selectAndRevealInTree(owner);
			if (result) {
				renderedDetail.whenComplete((view, e) -> {
					unregisterDetailPanelRenderingFinishedCallback(detailReady);
					revealInDetail(view, owner, feature);
				});
			} else {
				// Won't need the call-back so remove it now
				renderedDetail.cancel(false);
				unregisterDetailPanelRenderingFinishedCallback(detailReady);
			}

			// Do we already have the detail?
			final ECPSWTView currentDetail = detailManager.getCurrentDetail();
			if (currentDetail != null && currentDetail.getViewModelContext().getDomainModel() == owner) {
				// There won't be an asynchronous rendering to wait for
				renderedDetail.complete(currentDetail);
			}
		} else {
			result = selectAndRevealInTree(toReveal);
		}

		return result;
	}

	private boolean selectAndRevealInTree(final Object selection) {
		if (treeViewer == null) {
			return false;
		}

		boolean result = false;

		// Try to reveal the 'selection' in the tree. If it isn't in the
		// tree, then search up the content provider to find the nearest
		// object that can be revealed and select that, or give up
		for (Object objectToReveal = selection; objectToReveal != null;) {
			treeViewer.reveal(objectToReveal);
			if (treeViewer.testFindItem(objectToReveal) != null) {
				// Select it and we're done
				treeViewer.setSelection(new StructuredSelection(objectToReveal));
				result = objectToReveal == selection;
				break;
			}

			// Look up the content tree for an object to reveal
			objectToReveal = ((ITreeContentProvider) treeViewer.getContentProvider()).getParent(objectToReveal);
		}

		return result;
	}

	private void revealInDetail(ECPSWTView detail, EObject object, EStructuralFeature feature) {
		final ViewModelContext context = detail.getViewModelContext();
		if (!context.hasService(EMFFormsRevealService.class)) {
			// Nothing to do
			return;
		}

		final EMFFormsRevealService reveal = context.getService(EMFFormsRevealService.class);
		reveal.reveal(object, feature);
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
	 *
	 * @deprecated As of 1.22, use the {@link #setCache(DetailViewCache)} API, instead
	 */
	@Deprecated
	public void setCache(TreeMasterDetailCache cache) {
		setCache((DetailViewCache) cache);
	}

	/**
	 * Override the default cache implementation.
	 *
	 * @param cache the {@link DetailViewCache} to use, or {@code null} to use no cache
	 * @since 1.22
	 */
	public void setCache(DetailViewCache cache) {
		detailManager.setCache(cache);
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
	 * Returns whether I am read-only.
	 *
	 * @return <code>true</code> if read-only
	 * @since 1.22
	 * @see TreeMasterDetailSWTBuilder#customizeReadOnly(boolean)
	 */
	public boolean isReadOnly() {
		return customization.isReadOnly();
	}

	/**
	 * Adapter which listens to changes and delegates the notification to other EObjects.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private final class MultiEditAdapter extends AdapterImpl {
		private final Set<EObject> selectedEObjects;
		private final EObject dummy;

		private MultiEditAdapter(Set<EObject> selectedEObjects, EObject dummy) {
			this.selectedEObjects = selectedEObjects;
			this.dummy = dummy;
		}

		@Override
		public void notifyChanged(Notification notification) {
			if (dummy.eClass().getEAllAttributes().contains(notification.getFeature())) {
				final CompoundCommand cc = new CompoundCommand();
				for (final EObject selected : selectedEObjects) {
					Command command = null;
					switch (notification.getEventType()) {
					case Notification.SET:
						command = SetCommand.create(editingDomain, selected,
							notification.getFeature(), notification.getNewValue());
						break;
					case Notification.UNSET:
						command = SetCommand.create(editingDomain, selected,
							notification.getFeature(), SetCommand.UNSET_VALUE);
						break;
					case Notification.ADD:
					case Notification.ADD_MANY:
						command = AddCommand.create(editingDomain, selected,
							notification.getFeature(), notification.getNewValue());
						break;
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						command = DeleteCommand.create(editingDomain, notification.getOldValue());
						break;
					default:
						continue;
					}
					cc.append(command);
				}
				editingDomain.getCommandStack().execute(cc);
			}
		}
	}

	/**
	 * Runnable which updates the detail panel.
	 */
	private final class UpdateDetailRunnable implements Runnable {
		private final boolean setFocusToDetail;
		private final EObject eObject;

		UpdateDetailRunnable(boolean setFocusToDetail, EObject eObject) {
			super();

			this.setFocusToDetail = setFocusToDetail;
			this.eObject = eObject;
		}

		@Override
		public void run() {
			if (detailManager.isDisposed()) {
				// We've been disposed. Nothing to do
				return;
			}

			if (viewModelPropertiesUpdateCallback != null) {
				viewModelPropertiesUpdateCallback.updateViewModelProperties(detailManager.getDetailProperties());
			}
			final VView view = detailManager.getDetailView(eObject);
			// Even if the TMD is not configured as read-only honor the effective read-only
			// configuration of the loaded view
			view.setReadonly(view.isEffectivelyReadonly() || customization.isReadOnly());
			final ViewModelContext modelContext = ViewModelContextFactory.INSTANCE
				.createViewModelContext(
					view, eObject, customization.getViewModelServices(view, eObject));

			detailManager.setNoDetailMessage(selectNodeMessage);
			if (detailManager.isDisposed()) {
				return;
			}
			detailManager.render(modelContext, ECPSWTViewRenderer.INSTANCE::render);
			updateScrolledComposite();
			if (setFocusToDetail) {
				setFocusToDetail();
			}
			// notify callbacks that the rendering was finished
			for (final DetailPanelRenderingFinishedCallback callback : detailPanelRenderingFinishedCallbacks) {
				callback.renderingFinished(modelContext, eObject);
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
