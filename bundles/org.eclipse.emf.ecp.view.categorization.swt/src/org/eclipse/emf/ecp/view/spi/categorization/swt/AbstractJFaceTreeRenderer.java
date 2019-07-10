/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Johannes Faltermeier - Refactoring
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.internal.categorization.swt.Activator;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage.Literals;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * Abstract class for a tree renderer.
 *
 * @author Eugen Neufeld
 *
 * @param <VELEMENT> the {@link VElement}
 */
public abstract class AbstractJFaceTreeRenderer<VELEMENT extends VElement> extends AbstractSWTRenderer<VELEMENT> {

	private final EMFFormsRendererFactory emfFormsRendererFactory;

	private EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return emfFormsRendererFactory;
	}

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param emfFormsRendererFactory The {@link EMFFormsRendererFactory}
	 * @since 1.6
	 */
	public AbstractJFaceTreeRenderer(VELEMENT vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory emfFormsRendererFactory) {
		super(vElement, viewContext, reportService);
		this.emfFormsRendererFactory = emfFormsRendererFactory;
	}

	private SWTGridDescription gridDescription;
	private TreeViewer treeViewer;
	private AbstractJFaceTreeRenderer<VELEMENT>.TreeSelectionChangedListener treeSelectionChangedListener;

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (this.gridDescription == null) {
			this.gridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return this.gridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		gridDescription = null;
		getViewModelContext().unregisterViewChangeListener(treeSelectionChangedListener);
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final EList<VAbstractCategorization> categorizations = getCategorizations();

		if (categorizations.size() == 1 && categorizations.get(0) instanceof VCategory) {
			final VElement child = getCategorizations().get(0);
			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = getEMFFormsRendererFactory().getRendererInstance(child,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(
					new StatusReport(
						new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
							"No Renderer for %s found.", child.eClass().getName(), ex)))); //$NON-NLS-1$
				return null;
			}
			final Control render = renderer.render(cell, parent);
			renderer.finalizeRendering(parent);
			SWTDataElementIdHelper.setElementIdDataWithSubId(render, getVElement(), "vcategory", getViewModelContext()); //$NON-NLS-1$
			return render;

		}

		final Object detailPane = getViewModelContext().getContextValue("detailPane"); //$NON-NLS-1$
		if (detailPane != null && Composite.class.isInstance(detailPane)) {
			treeViewer = createTreeViewer(parent);

			final ScrolledComposite editorComposite = createdEditorPane(Composite.class.cast(detailPane));

			setupTreeViewer(treeViewer, editorComposite);

			initTreeViewer(treeViewer);
			Composite.class.cast(detailPane).layout();
			SWTDataElementIdHelper.setElementIdDataWithSubId(treeViewer.getControl(), getVElement(), "tree", //$NON-NLS-1$
				getViewModelContext());
			return treeViewer.getControl();
		}

		final SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sashForm);

		treeViewer = createTreeViewer(sashForm);

		final ScrolledComposite editorComposite = createdEditorPane(sashForm);

		setupTreeViewer(treeViewer, editorComposite);

		initTreeViewer(treeViewer);
		sashForm.setWeights(new int[] { 1, 3 });
		SWTDataElementIdHelper.setElementIdDataWithSubId(treeViewer.getControl(), getVElement(), "tree", //$NON-NLS-1$
			getViewModelContext());
		SWTDataElementIdHelper.setElementIdDataWithSubId(sashForm, getVElement(), "sash", getViewModelContext()); //$NON-NLS-1$
		return sashForm;
	}

	/**
	 * Creates a {@link TreeViewer}. Sub classes can override to influence the {@link TreeViewer}.
	 *
	 * @param parent the parent {@link Composite}
	 * @return a {@link TreeViewer}
	 * @since 1.14
	 */
	protected TreeViewer createTreeViewer(Composite parent) {
		return new TreeViewer(parent, SWT.SINGLE);
	}

	/**
	 * The list of categorizations to display in the tree.
	 *
	 * @return the list of {@link VAbstractCategorization}
	 */
	protected abstract EList<VAbstractCategorization> getCategorizations();

	/**
	 * The VCategorizationElement to set the current selection onto.
	 *
	 * @return the VCategorizationElement
	 */
	protected abstract VCategorizationElement getCategorizationElement();

	/**
	 * Created editor pane.
	 *
	 * @param composite the composite
	 * @return the created editor composite
	 */
	protected ScrolledComposite createdEditorPane(Composite composite) {
		final ScrolledComposite editorComposite = createScrolledComposite(composite);
		editorComposite.setExpandHorizontal(true);
		editorComposite.setExpandVertical(true);
		editorComposite.setShowFocusedControl(true);

		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(editorComposite);

		return editorComposite;
	}

	/**
	 * Creates the scrolled composite.
	 *
	 * @param parent the parent
	 * @return the scrolled composite
	 */
	private ScrolledComposite createScrolledComposite(Composite parent) {
		final ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL
			| SWT.BORDER);
		scrolledComposite.setShowFocusedControl(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setBackground(parent.getBackground());

		return scrolledComposite;
	}

	/**
	 * Configures the passed tree viewer.
	 *
	 * @param treeViewer the {@link TreeViewer} to configure
	 * @param editorComposite the composite of the editor
	 */
	protected void setupTreeViewer(final TreeViewer treeViewer,

		final ScrolledComposite editorComposite) {
		treeViewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return VCategorizableElement.class.isInstance(element) && ((VCategorizableElement) element).isVisible();
			}
		});
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, true).hint(400, SWT.DEFAULT)
			.applyTo(treeViewer.getTree());

		final List<TreeEditor> editors = new ArrayList<TreeEditor>();

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		final AdapterFactoryContentProvider contentProvider = new AdapterFactoryContentProvider(
			adapterFactory) {

			@Override
			public boolean hasChildren(Object object) {

				final boolean hasChildren = super.hasChildren(object);
				if (hasChildren) {
					for (final Object o : getChildren(object)) {
						for (final ViewerFilter viewerFilter : treeViewer.getFilters()) {
							if (viewerFilter.select(treeViewer, object, o)) {
								return true;
							}
						}
					}
				}
				return false;
			}

		};

		final TreeTableLabelProvider treeTableLabelProvider = getTreeLabelProvider(treeViewer, adapterFactory);
		treeViewer.getTree().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent event) {
				contentProvider.dispose();
				treeTableLabelProvider.dispose();
				adapterFactory.dispose();
			}
		});

		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(treeTableLabelProvider);

		treeSelectionChangedListener = new TreeSelectionChangedListener(
			getViewModelContext(), editorComposite,
			getCategorizationElement(),
			treeViewer, editors);
		treeViewer.addSelectionChangedListener(treeSelectionChangedListener);
		getViewModelContext().registerViewChangeListener(treeSelectionChangedListener);

		addTreeEditor(treeViewer, getVElement(), editors);

	}

	/**
	 * The TreeTableLabel provider.
	 *
	 * @param treeViewer the {@link TreeViewer}
	 * @param adapterFactory the {@link AdapterFactory} to use
	 * @return the created {@link TreeTableLabelProvider}
	 * @since 1.9
	 */
	protected TreeTableLabelProvider getTreeLabelProvider(TreeViewer treeViewer, AdapterFactory adapterFactory) {
		return new TreeTableLabelProvider(adapterFactory, treeViewer);
	}

	/**
	 * Inits the tree viewer.
	 *
	 * @param treeViewer the tree viewer
	 */
	protected void initTreeViewer(final TreeViewer treeViewer) {

		treeViewer.setInput(getVElement());
		treeViewer.expandAll();
		if (getCategorizationElement().getCurrentSelection() != null) {
			treeViewer.setSelection(new StructuredSelection(getCategorizationElement().getCurrentSelection()));
		} else if (getCategorizations().size() != 0) {
			treeViewer.setSelection(new StructuredSelection(getCategorizations().get(0)));
		}
	}

	/**
	 * Creates the composite.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	private Composite createComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).margins(7, 7).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
		return composite;
	}

	/**
	 * Adds the tree editor.
	 *
	 * @param treeViewer the tree viewer
	 * @param view the view
	 * @param editors the list of tree editors
	 */
	protected void addTreeEditor(final TreeViewer treeViewer, EObject view,
		final List<TreeEditor> editors) {
		// The text column
		final Tree tree = treeViewer.getTree();
		int maxActions = 0;
		final Iterator<EObject> viewContents = view.eAllContents();
		while (viewContents.hasNext()) {
			final EObject object = viewContents.next();
			if (VAbstractCategorization.class.isInstance(object)) {
				final VAbstractCategorization abstractCategorization = (VAbstractCategorization) object;
				if (maxActions < abstractCategorization.getActions().size()) {
					maxActions = abstractCategorization.getActions().size();
				}

				// I render this view-model element
				register(abstractCategorization);
			}
		}
		if (maxActions == 0) {
			return;
		}
		final TreeColumn columnText = new TreeColumn(tree, SWT.NONE);
		columnText.setWidth(300);
		columnText.setAlignment(SWT.FILL);

		for (int i = 0; i < maxActions; i++) {
			// The column
			final TreeColumn column = new TreeColumn(tree, SWT.NONE);
			column.setWidth(50);

			final TreeEditor editor = new TreeEditor(tree);
			// The editor must have the same size as the cell and must
			// not be any smaller than 50 pixels.
			editor.horizontalAlignment = SWT.CENTER;
			editor.grabHorizontal = true;
			editor.minimumWidth = 50;
			editor.setColumn(i + 1);

			editors.add(editor);
		}

		tree.addTreeListener(new TreeListener() {

			@Override
			public void treeExpanded(TreeEvent e) {
			}

			@Override
			public void treeCollapsed(TreeEvent e) {
				cleanUpTreeEditors(editors);
			}
		});

	}

	// Clean up any previous editor control
	/**
	 * Clean up tree editors.
	 */
	private void cleanUpTreeEditors(List<TreeEditor> editors) {
		for (final TreeEditor editor : editors) {
			final Control oldEditor = editor.getEditor();
			if (oldEditor != null) {
				oldEditor.dispose();
			}
		}
	}

	/**
	 * Adds the buttons.
	 *
	 * @param treeViewer the tree viewer
	 * @param treeSelection the tree selection
	 * @param editors the list of tree editors
	 */
	protected void addButtons(final TreeViewer treeViewer, TreeSelection treeSelection,
		List<TreeEditor> editors) {

		cleanUpTreeEditors(editors);

		if (treeSelection.getPaths().length == 0) {
			return;
		}

		// Identify the selected row
		final TreeItem item = treeViewer.getTree().getSelection()[0];
		if (item == null) {
			return;
		}

		final VCategorizableElement object = (VCategorizableElement) treeSelection.getFirstElement();
		if (object.getECPActions() == null) {
			return;
		}
		for (int i = 0; i < object.getECPActions().size(); i++) {
			final ECPTreeViewAction action = (ECPTreeViewAction) object.getECPActions().get(i);
			final TreeEditor editor = editors.get(i);
			action.init(treeViewer, treeSelection, editor);
			action.execute();
		}
	}

	/**
	 * Reveal the control that renders the given {@code categorization}.
	 *
	 * @param categorization a categorization to reveal
	 * @return whether the {@code categorization} was successfully revealed
	 *
	 * @since 1.22
	 */
	public boolean showCategorization(VAbstractCategorization categorization) {
		// If there's only one categorization, we won't have a tree viewer. But then
		// the categorization is always revealed, so we would have nothing to do
		if (treeViewer != null) {
			treeViewer.setSelection(new StructuredSelection(categorization));
		}
		return true;
	}

	/**
	 * The change listener for selections of the tree.
	 *
	 * @author Jonas Helming
	 *
	 */
	private final class TreeSelectionChangedListener implements ISelectionChangedListener, ModelChangeListener {
		private final ViewModelContext viewModelContext;
		private final ScrolledComposite editorComposite;
		private final VCategorizationElement vCategorizationElement;
		private final TreeViewer treeViewer;
		private final List<TreeEditor> editors;
		private Composite childComposite;

		private boolean busy;

		private TreeSelectionChangedListener(ViewModelContext viewModelContext,
			ScrolledComposite editorComposite, VCategorizationElement vCategorizationElement, TreeViewer treeViewer,
			List<TreeEditor> editors) {
			this.viewModelContext = viewModelContext;
			this.editorComposite = editorComposite;
			this.vCategorizationElement = vCategorizationElement;
			this.treeViewer = treeViewer;
			this.editors = editors;
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {

			final TreeSelection treeSelection = (TreeSelection) event.getSelection();
			final Object selection = treeSelection.getFirstElement();
			addButtons(treeViewer, treeSelection, editors);

			if (selection == null) {
				return;
			}
			onSelectionChanged(VElement.class.cast(selection));
		}

		public void onSelectionChanged(VElement child) {

			if (busy) {
				return;
			}

			busy = true;
			try {
				if (childComposite != null) {
					childComposite.dispose();
					childComposite = null;
				}
				childComposite = createComposite(editorComposite);

				childComposite.setBackground(editorComposite.getBackground());
				editorComposite.setContent(childComposite);

				try {

					AbstractSWTRenderer<VElement> renderer;
					try {
						renderer = getEMFFormsRendererFactory().getRendererInstance(child,
							viewModelContext);
					} catch (final EMFFormsNoRendererException ex) {
						getReportService().report(
							new StatusReport(
								new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
									"No Renderer for %s found.", child.eClass().getName(), ex)))); //$NON-NLS-1$
						return;
					}
					// we have a VCategory-> thus only one element in the grid
					final Control render = renderer.render(
						renderer.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription())
							.getGrid()
							.get(0),
						childComposite);
					renderer.finalizeRendering(childComposite);
					GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
						.minSize(SWT.DEFAULT, 200)
						.applyTo(render);
					vCategorizationElement.setCurrentSelection((VCategorizableElement) child);
				} catch (final NoRendererFoundException e) {
					getReportService().report(new RenderingFailedReport(e));
				} catch (final NoPropertyDescriptorFoundExeption e) {
					getReportService().report(new RenderingFailedReport(e));
				}

				childComposite.layout();
				final Point point = childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				editorComposite.setMinSize(point);

			} finally {
				busy = false;
			}

		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
		 */
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (notification.getNotifier() instanceof VCategorizationElement
				&& notification.getStructuralFeature() == Literals.CATEGORIZATION_ELEMENT__CURRENT_SELECTION) {
				onSelectionChanged((VElement) notification.getNotifier());
			}

		}

	}

	/**
	 * The Class TreeTableLabelProvider.
	 */
	protected class TreeTableLabelProvider extends AdapterFactoryLabelProvider implements ITableItemLabelProvider {

		private final TreeViewer treeViewer;

		/**
		 * Instantiates a new tree table label provider.
		 *
		 * @param adapterFactory the adapter factory
		 * @param treeViewer the tree viewer
		 * @since 1.9
		 */
		public TreeTableLabelProvider(AdapterFactory adapterFactory, TreeViewer treeViewer) {
			super(adapterFactory);
			this.treeViewer = treeViewer;
		}

		@Override
		public String getColumnText(Object object, int columnIndex) {
			adjustItemData(object);
			String text = super.getColumnText(object, columnIndex);
			if (columnIndex == 0 && VCategorizableElement.class.isInstance(object)) {
				text = super.getColumnText(((VCategorizableElement) object).getLabelObject(), columnIndex);
			}
			return text;
		}

		// XXX is there a better way to get informed when there are new TreeItems and to access the TreeItems?
		// the problem is double-edged:
		// 1. the content-provider is responsible for updating the tree, but we cannot listen to it
		// 2. TreeItems might be removed/recreated without being able to listen for this
		// the workaround is to plug in to the label provider. when a treeitem is created/changed it needs a display
		// text, so we can access the treeitem and should be called on all relevant changes
		private void adjustItemData(Object object) {
			if (!VElement.class.isInstance(object)) {
				return;
			}
			final Widget widget = treeViewer.testFindItem(object);
			if (widget == null) {
				return;
			}
			if (widget.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY) != null) {
				/* already set */
				return;
			}
			final VElement element = VElement.class.cast(object);
			SWTDataElementIdHelper.setElementIdDataWithSubId(widget, element, "treeItem", getViewModelContext()); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object object) {
			adjustItemData(object);
			String text;
			if (VCategorizableElement.class.isInstance(object)) {
				text = super.getText(((VCategorizableElement) object).getLabelObject());
			} else {
				text = super.getText(object);
			}
			return text;
		}

		@Override
		public Image getColumnImage(Object object, int columnIndex) {

			if (columnIndex != 0) {
				return null;
			}
			Image image = super.getColumnImage(object, columnIndex);
			if (VCategorizableElement.class.isInstance(object)) {
				image = super.getColumnImage(((VCategorizableElement) object).getLabelObject(), columnIndex);
			}

			return getValidationOverlay(image, (VElement) object);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object object) {
			Image image;
			if (VCategorizableElement.class.isInstance(object)) {
				image = super.getImage(((VCategorizableElement) object).getLabelObject());
			} else {
				image = super.getImage(object);
			}

			return getValidationOverlay(image, (VElement) object);
		}

		/**
		 * This method generated an image with a validation overlay if necessary.
		 *
		 * @param image the image to overlay
		 * @param categorization the {@link VElement} to get the validation for
		 * @return the Image
		 */
		protected Image getValidationOverlay(Image image, final VElement categorization) {
			ImageDescriptor overlay = null;

			if (categorization.getDiagnostic() == null) {
				return image;
			}
			overlay = SWTValidationHelper.INSTANCE.getValidationOverlayDescriptor(categorization.getDiagnostic()
				.getHighestSeverity(), getVElement(), getViewModelContext());

			if (overlay == null) {
				return image;
			}
			final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image, overlay,
				OverlayImageDescriptor.LOWER_RIGHT);
			final Image resultImage = imageDescriptor.createImage();

			return resultImage;
		}

	}

}
