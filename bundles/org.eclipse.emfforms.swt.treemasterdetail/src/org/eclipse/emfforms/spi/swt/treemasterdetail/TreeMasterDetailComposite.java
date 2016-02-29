/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
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
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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

	private final TreeMasterDetailSWTCustomization customization;

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
	 */
	/* package */ TreeMasterDetailComposite(Composite parent, int style, Object input,
		TreeMasterDetailSWTCustomization customization) {
		super(parent, style);
		this.input = input;
		if (input instanceof Resource) {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(((Resource) input).getContents().get(0));
		} else if (input instanceof RootObject) {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(RootObject.class.cast(input).getRoot());
		} else {
			editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(input);
		}
		this.customization = customization;
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

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateDetailPanel();
			}
		});

		updateDetailPanel();

		return this;
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
	private void updateDetailPanel() {
		// Create a new detail panel in the scrollable composite. Disposes any old panels.
		createDetailPanel();

		// Get the selected object, if it is an EObject, render the details using EMF Forms
		final Object selectedObject = treeViewer.getSelection() != null ? ((StructuredSelection) treeViewer
			.getSelection()).getFirstElement() : null;
		if (selectedObject instanceof EObject) {
			final EObject eObject = EObject.class.cast(selectedObject);
			// Check, if the selected object would be rendered using a TreeMasterDetail. If so, render the provided
			// detail view.
			final VView view = ViewProviderHelper.getView((EObject) selectedObject, context);
			if (view.getChildren().size() > 0 && view.getChildren().get(0) instanceof VTreeMasterDetail) {
				// Yes, we need to render this node differently
				final VTreeMasterDetail vTreeMasterDetail = (VTreeMasterDetail) view.getChildren().get(0);
				try {
					ECPSWTViewRenderer.INSTANCE.render(detailPanel, (EObject) selectedObject,
						vTreeMasterDetail.getDetailView());
					detailPanel.layout(true, true);
				} catch (final ECPRendererException e) {
				}

			} else {
				// No, everything is fine
				try {
					final VView view2 = ViewProviderHelper.getView(eObject, context);
					final ViewModelContext modelContext = ViewModelContextFactory.INSTANCE.createViewModelContext(view2,
						eObject, customization.getViewModelServices(view2, eObject));
					ECPSWTViewRenderer.INSTANCE.render(detailPanel, modelContext);
					detailPanel.layout(true, true);
				} catch (final ECPRendererException e) {
				}
			}
			// After rendering the Forms, compute the size of the form. So the scroll container knows when to scroll
			if (ScrolledComposite.class.isInstance(detailComposite)) {
				ScrolledComposite.class.cast(detailComposite)
					.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		} else {
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

			if (ScrolledComposite.class.isInstance(detailComposite)) {
				ScrolledComposite.class.cast(detailComposite)
					.setMinSize(detailPanel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		}
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
		if (ScrolledComposite.class.isInstance(detailComposite)) {
			ScrolledComposite.class.cast(detailComposite).setContent(detailPanel);
		}

		detailComposite.layout(true, true);
		return detailPanel;
	}

	@Override
	public void dispose() {
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
	 */
	public void setSelection(StructuredSelection structuredSelection) {
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
}
