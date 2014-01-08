/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryContentProvider;
import org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryLabelProvider;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
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
 * The Class SWTViewRenderer.
 */
public class SWTCategorizationElementRenderer extends AbstractSWTRenderer<VCategorizationElement> {

	/** The Constant INSTANCE. */
	public static final SWTCategorizationElementRenderer INSTANCE = new SWTCategorizationElementRenderer();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent,
		final VCategorizationElement vCategorizationElement,
		ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final TreeViewer treeViewer;

		// final RefreshTreeViewerAdapter adapter = new RefreshTreeViewerAdapter();
		// vCategorizationElement.eAdapters().add(adapter);
		// parent.addDisposeListener(new DisposeListener() {
		// private static final long serialVersionUID = 1L;
		//
		// public void widgetDisposed(DisposeEvent event) {
		// vCategorizationElement.eAdapters().remove(adapter);
		// }
		// });
		final EList<VAbstractCategorization> categorizations = vCategorizationElement.getCategorizations();

		if (categorizations.size() == 1 && categorizations.get(0) instanceof VCategory) {
			final List<RenderingResultRow<Control>> resultRows = SWTRendererFactory.INSTANCE.render(parent,
				vCategorizationElement.getCategorizations().get(0), viewContext);

			return resultRows;

		}
		final Composite composite = createComposite(parent);
		treeViewer = new TreeViewer(composite);
		final ScrolledComposite editorComposite = createdEditorPane(composite);
		setupTreeViewer(treeViewer, vCategorizationElement,
			editorComposite, viewContext);
		// adapter.setTreeViewer(treeViewer);

		initTreeViewer(treeViewer, vCategorizationElement);

		return createResult(composite);

	}

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

		final Composite childComposite = new Composite(scrolledComposite, SWT.NONE);
		childComposite.setBackground(parent.getBackground());

		return scrolledComposite;
	}

	/**
	 * Configures the passed tree viewer.
	 * 
	 * @param treeViewer the {@link TreeViewer} to configure
	 * @param vCategorizationElement the {@link VCategorizationElement}
	 * @param editorComposite the composite of the editor
	 * @param viewModelContext the {@link ViewModelContext} to use
	 */
	protected void setupTreeViewer(final TreeViewer treeViewer,
		final VCategorizationElement vCategorizationElement,
		final ScrolledComposite editorComposite, final ViewModelContext viewModelContext) {
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

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
			 */
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

		final TreeTableLabelProvider treeTableLabelProvider = new TreeTableLabelProvider(
			adapterFactory);
		treeViewer.getTree().addDisposeListener(new DisposeListener() {

			private static final long serialVersionUID = 1L;

			public void widgetDisposed(DisposeEvent event) {
				contentProvider.dispose();
				treeTableLabelProvider.dispose();
				adapterFactory.dispose();
			}
		});

		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(treeTableLabelProvider);

		treeViewer.addSelectionChangedListener(new TreeSelectionChangedListener(viewModelContext, editorComposite,
			vCategorizationElement,
			treeViewer, editors));

		addTreeEditor(treeViewer, vCategorizationElement, editors);

	}

	/**
	 * Inits the tree viewer.
	 * 
	 * @param treeViewer the tree viewer
	 * @param vCategorizationElement the {@link VCategorizationElement}
	 */
	protected void initTreeViewer(final TreeViewer treeViewer, VCategorizationElement vCategorizationElement) {

		treeViewer.setInput(vCategorizationElement);
		treeViewer.expandAll();
		treeViewer.setSelection(new StructuredSelection(vCategorizationElement.getCategorizations().get(0)));
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

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
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
	protected void addTreeEditor(final TreeViewer treeViewer, VCategorizationElement view,
		final List<TreeEditor> editors) {
		// The text column
		final Tree tree = treeViewer.getTree();
		final TreeColumn columnText = new TreeColumn(tree, SWT.NONE);
		columnText.setWidth(300);
		columnText.setAlignment(SWT.FILL);

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

			private static final long serialVersionUID = -576684848162963651L;

			public void treeExpanded(TreeEvent e) {
			}

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
	 * @author Jonas
	 * 
	 */
	private final class TreeSelectionChangedListener implements ISelectionChangedListener {
		private final ViewModelContext viewModelContext;
		private final ScrolledComposite editorComposite;
		private final VCategorizationElement vCategorizationElement;
		private final TreeViewer treeViewer;
		private final List<TreeEditor> editors;
		private Composite childComposite;

		/**
		 * @param viewModelContext
		 * @param editorComposite
		 * @param vCategorizationElement
		 * @param treeViewer
		 * @param editors
		 */
		private TreeSelectionChangedListener(ViewModelContext viewModelContext,
			ScrolledComposite editorComposite, VCategorizationElement vCategorizationElement, TreeViewer treeViewer,
			List<TreeEditor> editors) {
			this.viewModelContext = viewModelContext;
			this.editorComposite = editorComposite;
			this.vCategorizationElement = vCategorizationElement;
			this.treeViewer = treeViewer;
			this.editors = editors;
		}

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

			final VElement node = (VElement) selection;
			try {
				final List<RenderingResultRow<Control>> resultRows = SWTRendererFactory.INSTANCE.render(
					childComposite, node, viewModelContext);
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
					.applyTo(resultRows.get(0).getMainControl());
				vCategorizationElement.setCurrentSelection((VCategorizableElement) node);
			} catch (final NoRendererFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final NoPropertyDescriptorFoundExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			childComposite.layout();
			final Point point = childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			editorComposite.setMinSize(point);
			// }

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

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryLabelProvider#getColumnText(java.lang.Object,
		 *      int)
		 */
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
		 * @see org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryLabelProvider#getColumnImage(java.lang.Object,
		 *      int)
		 */
		@Override
		public Image getColumnImage(Object object, int columnIndex) {

			if (columnIndex != 0) {
				return null;
			}
			Image image = null;
			image = super.getColumnImage(object, columnIndex);
			if (VCategorizableElement.class.isInstance(object)) {
				image = super.getColumnImage(((VCategorizableElement) object).getLabelObject(), columnIndex);
			}

			final VElement categorization = (VElement) object;

			ImageDescriptor overlay = null;

			if (categorization.getDiagnostic() == null) {
				return image;
			}
			overlay = SWTValidationHelper.INSTANCE.getValidationOverlayDescriptor(categorization.getDiagnostic()
				.getHighestSeverity());

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
