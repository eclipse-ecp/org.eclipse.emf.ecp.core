/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Johannes Falterimeier - Refactoring
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
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
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
		TreeViewer treeViewer;
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
			return render;

		}

		final Object detailPane = getViewModelContext().getContextValue("detailPane"); //$NON-NLS-1$
		if (detailPane != null && Composite.class.isInstance(detailPane)) {
			treeViewer = new TreeViewer(parent);

			final ScrolledComposite editorComposite = createdEditorPane(Composite.class.cast(detailPane));

			setupTreeViewer(treeViewer, editorComposite);

			initTreeViewer(treeViewer);
			Composite.class.cast(detailPane).layout();
			return treeViewer.getControl();
		}

		final SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sashForm);

		treeViewer = new TreeViewer(sashForm);

		final ScrolledComposite editorComposite = createdEditorPane(sashForm);

		setupTreeViewer(treeViewer, editorComposite);

		initTreeViewer(treeViewer);
		sashForm.setWeights(new int[] { 1, 3 });
		return sashForm;
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

		final TreeTableLabelProvider treeTableLabelProvider = getTreeLabelProvider(adapterFactory);
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

		treeViewer.addSelectionChangedListener(new TreeSelectionChangedListener(getViewModelContext(), editorComposite,
			getCategorizationElement(),
			treeViewer, editors));

		addTreeEditor(treeViewer, getVElement(), editors);

	}

	/**
	 * The TreeTableLabel provider.
	 *
	 * @param adapterFactory the {@link AdapterFactory} to use
	 * @return the created {@link TreeTableLabelProvider}
	 */
	protected TreeTableLabelProvider getTreeLabelProvider(AdapterFactory adapterFactory) {
		return new TreeTableLabelProvider(adapterFactory);
	}

	/**
	 * Inits the tree viewer.
	 *
	 * @param treeViewer the tree viewer
	 */
	protected void initTreeViewer(final TreeViewer treeViewer) {

		treeViewer.setInput(getVElement());
		treeViewer.expandAll();
		if (getCategorizations().size() != 0) {
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
	 * The change listener for selections of the tree.
	 *
	 * @author Jonas Helming
	 *
	 */
	private final class TreeSelectionChangedListener implements ISelectionChangedListener {
		private final ViewModelContext viewModelContext;
		private final ScrolledComposite editorComposite;
		private final VCategorizationElement vCategorizationElement;
		private final TreeViewer treeViewer;
		private final List<TreeEditor> editors;
		private Composite childComposite;

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
			if (childComposite != null) {
				childComposite.dispose();
				childComposite = null;
			}
			childComposite = createComposite(editorComposite);

			childComposite.setBackground(editorComposite.getBackground());
			editorComposite.setContent(childComposite);

			final VElement child = (VElement) selection;
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
					renderer.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription()).getGrid()
						.get(0), childComposite);
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

		}
	}

	/**
	 * The Class TreeTableLabelProvider.
	 */
	protected class TreeTableLabelProvider extends AdapterFactoryLabelProvider implements ITableItemLabelProvider {

		/**
		 * Instantiates a new tree table label provider.
		 *
		 * @param adapterFactory the adapter factory
		 */
		public TreeTableLabelProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		@Override
		public String getColumnText(Object object, int columnIndex) {

			String text = super.getColumnText(object, columnIndex);
			if (columnIndex == 0 && VCategorizableElement.class.isInstance(object)) {
				text = super.getColumnText(((VCategorizableElement) object).getLabelObject(), columnIndex);
			}
			return text;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object object) {
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
