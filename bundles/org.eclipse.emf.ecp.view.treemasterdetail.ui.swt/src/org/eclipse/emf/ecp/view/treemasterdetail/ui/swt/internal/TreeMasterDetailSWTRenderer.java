/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Anas Chakfeh - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryContentProvider;
import org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryLabelProvider;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.action.ecp.CreateChildAction;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * SWT Renderer for a {@link VTreeMasterDetail} element.
 * 
 * @author Anas Chakfeh
 * 
 */
@SuppressWarnings("restriction")
public class TreeMasterDetailSWTRenderer extends AbstractSWTRenderer<VTreeMasterDetail> {

	/**
	 * Static string.
	 * 
	 */
	public static final String GLOBAL_ADDITIONS = "global_additions"; //$NON-NLS-1$

	/**
	 * @author Anas Chakfeh
	 * 
	 */
	private class RootObject {

		private final EObject modelElement;

		/**
		 * @param modelElement
		 */
		public RootObject(EObject modelElement) {
			this.modelElement = modelElement;
		}

		/**
		 * @return the root object
		 */
		public Object getRoot() {
			return modelElement;
		}

	}

	/**
	 * {@inheritDoc}.
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.swt.internal.AbstractSWTRenderer#renderModel(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecp.view.model.VElement, org.eclipse.emf.ecp.view.context.ViewModelContext)
	 */
	@Override
	protected List<RenderingResultRow<Control>> renderModel(Composite parent, VTreeMasterDetail vElement,
		ViewModelContext viewContext) throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final SashForm sash = new SashForm(parent, SWT.HORIZONTAL);

		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(sash);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sash);
		sash.setSashWidth(5);

		final Composite leftPanel = new Composite(sash, SWT.FILL);
		leftPanel.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.FILL).grab(false, true).applyTo(leftPanel);
		leftPanel.setBackground(parent.getBackground());

		final ScrolledComposite rightPanel = new ScrolledComposite(sash, SWT.V_SCROLL | SWT.H_SCROLL
			| SWT.BORDER);
		rightPanel.setShowFocusedControl(true);
		rightPanel.setExpandVertical(true);
		rightPanel.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(rightPanel);
		rightPanel.setLayout(GridLayoutFactory.fillDefaults().create());
		rightPanel.setBackground(parent.getBackground());

		sash.setWeights(new int[] { 1, 3 });

		final EObject modelElement = viewContext.getDomainModel();
		final VView detailView = vElement.getDetailView();

		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(modelElement);

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		final AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
			adapterFactory) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecp.internal.ui.view.emf.AdapterFactoryContentProvider#getElements(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object object) {

				return new Object[] { ((RootObject) object).getRoot() };
			}

		};
		final AdapterFactoryLabelProvider adapterFactoryLabelProvider = new
			AdapterFactoryLabelProvider(adapterFactory);

		final TreeViewer treeViewer = new TreeViewer(leftPanel);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(100, SWT.DEFAULT)
			.applyTo(treeViewer.getTree());

		treeViewer.setContentProvider(adapterFactoryContentProvider);
		treeViewer.setLabelProvider(adapterFactoryLabelProvider);
		treeViewer.setInput(new RootObject(modelElement));

		// Drag and Drop
		addDragAndDropSupport(modelElement, treeViewer, editingDomain);

		// Selection Listener
		treeViewer.addSelectionChangedListener(new TreeMasterViewSelectionListener(rightPanel, detailView));

		fillContextMenu(treeViewer, editingDomain);

		final RenderingResultRow<Control> createRenderingResultRow = SWTRenderingHelper.INSTANCE.getResultRowFactory()
			.createRenderingResultRow(parent);

		final List<RenderingResultRow<Control>> renderingList = new ArrayList<RenderingResultRow<Control>>();
		renderingList.add(createRenderingResultRow);

		treeViewer.getTree().addDisposeListener(new DisposeListener() {

			private static final long serialVersionUID = 1L;

			public void widgetDisposed(DisposeEvent event) {
				adapterFactoryContentProvider.dispose();
				adapterFactoryLabelProvider.dispose();
				adapterFactory.dispose();
			}
		});

		return renderingList;

	}

	/**
	 * @param modelElement
	 * @param treeViewer
	 * @param editingDomain
	 */
	private void addDragAndDropSupport(final EObject modelElement, final TreeViewer treeViewer,
		EditingDomain editingDomain) {

		final int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		treeViewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(treeViewer));
		final EditingDomainViewerDropAdapter editingDomainViewerDropAdapter = new EditingDomainViewerDropAdapter(
			editingDomain,
			treeViewer);
		treeViewer.addDropSupport(dndOperations, transfers, editingDomainViewerDropAdapter);
	}

	/**
	 * @param treeViewer
	 * @param editingDomain
	 */
	private void fillContextMenu(final TreeViewer treeViewer, final EditingDomain editingDomain) {
		final ChildrenDescriptorCollector childrenDescriptorCollector = new ChildrenDescriptorCollector();

		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				if (treeViewer.getSelection().isEmpty()) {
					return;
				}

				final Object root = ((RootObject) treeViewer.getInput()).getRoot();

				if (treeViewer.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

					if (selection.size() == 1) {
						final EObject eObject = (EObject) selection.getFirstElement();
						final Collection<?> descriptors = childrenDescriptorCollector.getDescriptors(eObject);
						fillContextMenu(manager, descriptors, editingDomain, eObject);
					}
					if (!selection.toList().contains(root)) {
						manager.add(new Separator(GLOBAL_ADDITIONS));
						addDeleteActionToContextMenu(editingDomain, manager, selection);
					}

				}
			}

		});
		final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
	}

	/**
	 * @param manager The menu manager responsible for the context menu
	 * @param descriptors The menu items to be added
	 * @param domain The editing domain of the current EObject
	 * @param eObject The model element
	 */
	private void fillContextMenu(IMenuManager manager, Collection<?> descriptors, final EditingDomain domain,
		final EObject eObject) {
		for (final Object descriptor : descriptors) {

			final CommandParameter cp = (CommandParameter) descriptor;
			if (!CommandParameter.class.isInstance(descriptor)) {
				continue;
			}
			if (cp.getEReference() == null) {
				continue;
			}
			if (!cp.getEReference().isMany() && eObject.eIsSet(cp.getEStructuralFeature())) {
				continue;
			} else if (cp.getEReference().isMany() && cp.getEReference().getUpperBound() != -1
				&& cp.getEReference().getUpperBound() <= ((List<?>) eObject.eGet(cp.getEReference())).size()) {
				continue;
			}

			manager.add(new CreateChildAction(domain, new StructuredSelection(eObject), descriptor) {
				@Override
				public void run() {
					super.run();

					final EReference reference = ((CommandParameter) descriptor).getEReference();
					// if (!reference.isContainment()) {
					// domain.getCommandStack().execute(
					// AddCommand.create(domain, eObject.eContainer(), null, cp.getEValue()));
					// }

					domain.getCommandStack().execute(
						AddCommand.create(domain, eObject, reference, cp.getEValue()));
				}
			});
		}

	}

	/**
	 * @param editingDomain
	 * @param manager
	 * @param selection
	 */
	private void addDeleteActionToContextMenu(final EditingDomain editingDomain, final IMenuManager manager,
		final IStructuredSelection selection) {

		final Action deleteAction = new Action() {
			@Override
			public void run() {
				super.run();
				for (final Object obj : selection.toList())
				{
					editingDomain.getCommandStack().execute(
						RemoveCommand.create(editingDomain, obj));
				}

			}
		};
		final String deleteImagePath = "icons/delete.png";//$NON-NLS-1$
		deleteAction.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(deleteImagePath)));

		manager.add(deleteAction);
	}

	/**
	 * 
	 * @author Anas Chakfeh
	 *         This class is responsible for handling selection changed events which happen on the tree
	 * 
	 */
	private class TreeMasterViewSelectionListener implements ISelectionChangedListener {

		private final ScrolledComposite parent;
		private Composite childComposite;
		private final VView vView;

		/**
		 * @param rightPanel
		 * @param vView
		 */
		public TreeMasterViewSelectionListener(ScrolledComposite rightPanel, VView vView) {
			parent = rightPanel;
			this.vView = vView;
		}

		public void selectionChanged(SelectionChangedEvent event) {

			final Object selected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if (selected instanceof EObject) {
				try {
					if (childComposite != null) {
						childComposite.dispose();
					}
					childComposite = createComposite(parent);
					parent.setContent(childComposite);

					final Object root = ((RootObject) ((TreeViewer) event.getSource()).getInput()).getRoot();

					if (selected.equals(root)) {
						ECPSWTViewRenderer.INSTANCE.render(childComposite, (EObject) selected, vView);
					} else {
						ECPSWTViewRenderer.INSTANCE
							.render(childComposite, (EObject) selected);
					}

					parent.layout();
					childComposite.layout();
					final Point point = childComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					parent.setMinSize(point);
				} catch (final ECPRendererException ex) {
					ex.printStackTrace();
				}
			}

		}

		private Composite createComposite(Composite parent) {
			final Composite composite = new Composite(parent, SWT.NONE);
			composite.setBackground(parent.getBackground());

			GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
			GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
			return composite;
		}
	}

}
