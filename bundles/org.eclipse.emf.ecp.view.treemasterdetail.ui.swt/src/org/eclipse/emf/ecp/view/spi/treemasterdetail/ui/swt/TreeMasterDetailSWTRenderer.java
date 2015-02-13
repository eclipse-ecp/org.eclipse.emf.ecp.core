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
 * Alexandra Buzila - Refactoring
 * Johannes Faltermeier - integration with validation service
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.internal.swt.ContextMenuViewModelService;
import org.eclipse.emf.ecp.view.internal.treemasterdetail.ui.swt.Activator;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.RootObject;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSelectionManipulatorHelper;
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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.osgi.framework.FrameworkUtil;

/**
 * SWT Renderer for a {@link VTreeMasterDetail} element.
 *
 * @author Anas Chakfeh
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public class TreeMasterDetailSWTRenderer extends AbstractSWTRenderer<VTreeMasterDetail> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public TreeMasterDetailSWTRenderer(VTreeMasterDetail vElement, ViewModelContext viewContext,
		SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * The detail key passed to the view model context.
	 */
	public static final String DETAIL_KEY = "detail"; //$NON-NLS-1$

	/**
	 * Context key for the root.
	 */
	public static final String ROOT_KEY = "root"; //$NON-NLS-1$
	private SWTGridDescription rendererGridDescription;

	private Font detailsFont;
	private Color titleColor;
	private Font titleFont;
	private Color headerBgColor;
	private TreeViewer treeViewer;
	/**
	 * Static string.
	 *
	 */
	public static final String GLOBAL_ADDITIONS = "global_additions"; //$NON-NLS-1$

	private ScrolledComposite rightPanel;

	private Composite container;

	private Composite rightPanelContainerComposite;

	/**
	 * @author jfaltermeier
	 *
	 */
	private final class MasterTreeContextMenuListener implements IMenuListener {
		private final EditingDomain editingDomain;
		private final TreeViewer treeViewer;
		private final ChildrenDescriptorCollector childrenDescriptorCollector;
		private final List<MasterDetailAction> menuActions;

		/**
		 * @param editingDomain
		 * @param treeViewer
		 * @param childrenDescriptorCollector
		 * @param menuActions
		 */
		private MasterTreeContextMenuListener(EditingDomain editingDomain, TreeViewer treeViewer,
			ChildrenDescriptorCollector childrenDescriptorCollector, List<MasterDetailAction> menuActions) {
			this.editingDomain = editingDomain;
			this.treeViewer = treeViewer;
			this.childrenDescriptorCollector = childrenDescriptorCollector;
			this.menuActions = menuActions;
		}

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			if (treeViewer.getSelection().isEmpty()) {
				return;
			}
			final EObject root = ((RootObject) treeViewer.getInput()).getRoot();

			if (treeViewer.getSelection() instanceof IStructuredSelection) {
				final IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

				if (selection.size() == 1) {
					final EObject eObject = (EObject) selection.getFirstElement();
					final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
					if (domain == null) {
						return;
					}
					final Collection<?> descriptors = childrenDescriptorCollector.getDescriptors(eObject);
					fillContextMenu(manager, descriptors, editingDomain, eObject);
				}
				if (!selection.toList().contains(root)) {
					manager.add(new Separator(GLOBAL_ADDITIONS));
					addDeleteActionToContextMenu(editingDomain, manager, selection);
				}
				manager.add(new Separator());

				if (selection.getFirstElement() != null && EObject.class.isInstance(selection.getFirstElement())) {
					final EObject selectedObject = (EObject) selection.getFirstElement();

					for (final MasterDetailAction menuAction : menuActions) {
						if (menuAction.shouldShow(selectedObject)) {
							final Action newAction = new Action() {
								@Override
								public void run() {
									super.run();
									menuAction.execute(selectedObject);
								}
							};

							newAction.setImageDescriptor(ImageDescriptor.createFromURL(FrameworkUtil.getBundle(
								menuAction.getClass())
								.getResource(menuAction.getImagePath())));
							newAction.setText(menuAction.getLabel());

							manager.add(newAction);
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		rendererGridDescription = null;
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	 */
	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return rendererGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {

		/* The tree's composites */
		final Composite form = createMasterDetailForm(parent);

		createHeader(form);

		final SashForm sash = createSash(form);

		final Composite masterPanel = createMasterPanel(sash);

		createRightPanelContent(sash);

		sash.setWeights(new int[] { 1, 3 });

		createMasterTree(masterPanel);

		if (hasContextMenu()) {
			registerControlAsContextMenuReceiver();
		}
		form.layout(true);
		return form;
	}

	private void registerControlAsContextMenuReceiver() {
		if (!getViewModelContext().hasService(ContextMenuViewModelService.class)) {
			return;
		}
		final ContextMenuViewModelService service = getViewModelContext().getService(
			ContextMenuViewModelService.class);

		if (service != null) {
			service.setParentControl(treeViewer.getTree());
			service.registerContextMenu();
		}
	}

	/**
	 * Creates the sashform for the master detail colums.
	 *
	 * @param parent the parent
	 * @return the sash
	 */
	protected SashForm createSash(Composite parent) {
		/* THe contents of the composite */
		final Composite sashComposite = new Composite(parent, SWT.FILL);
		final GridLayout sashLayout = GridLayoutFactory.fillDefaults().create();
		sashLayout.marginWidth = 5;
		sashComposite.setLayout(sashLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(sashComposite);

		final SashForm sash = new SashForm(sashComposite, SWT.HORIZONTAL);

		sash.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(sash);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(sash);
		sash.setSashWidth(5);
		return sash;
	}

	/**
	 * Create the parent of the master detail form.
	 *
	 * @param parent the parent
	 * @return the composite
	 */
	protected Composite createMasterDetailForm(Composite parent) {
		final Composite form = new Composite(parent, SWT.BORDER);
		final GridLayout layout = GridLayoutFactory.fillDefaults().create();

		form.setLayout(layout);
		form.setBackgroundMode(SWT.INHERIT_FORCE);
		// form.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		return form;
	}

	/**
	 * Creates the tree viewer for the master.
	 *
	 * @param masterPanel the parent
	 * @return the tree viewer
	 */
	protected TreeViewer createMasterTree(final Composite masterPanel) {
		final EObject modelElement = getViewModelContext().getDomainModel();
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(modelElement);

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		final AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
			adapterFactory) {

			@Override
			public Object[] getElements(Object object) {
				return new Object[] { ((RootObject) object).getRoot() };
			}
		};
		final AdapterFactoryLabelProvider labelProvider = new TreeMasterDetailLabelProvider(adapterFactory);

		treeViewer = new TreeViewer(masterPanel);

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).hint(100, SWT.DEFAULT)
			.applyTo(treeViewer.getTree());

		treeViewer.setContentProvider(adapterFactoryContentProvider);
		treeViewer.setLabelProvider(getLabelProvider(labelProvider));
		treeViewer.setAutoExpandLevel(2); // top level element is expanded, but not the children
		treeViewer.setInput(new RootObject(modelElement));

		// Drag and Drop
		if (hasDnDSupport()) {
			addDragAndDropSupport(modelElement, treeViewer, editingDomain);
		}

		// Selection Listener
		treeViewer.addSelectionChangedListener(new TreeMasterViewSelectionListener());
		treeViewer.setSelection(new StructuredSelection(modelElement));
		if (hasContextMenu()) {
			fillContextMenu(treeViewer, editingDomain);
		}

		treeViewer.getTree().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent event) {
				adapterFactoryContentProvider.dispose();
				labelProvider.dispose();
				adapterFactory.dispose();
				if (titleFont != null) {
					titleFont.dispose();
				}
				if (detailsFont != null) {
					detailsFont.dispose();
				}
				if (titleColor != null) {
					titleColor.dispose();
				}
				if (headerBgColor != null) {
					headerBgColor.dispose();
				}
			}
		});
		return treeViewer;
	}

	/**
	 * Return true if a context menu should be shown in the tree.
	 *
	 * @return true if a context menu should be shown, false otherwise
	 */
	protected boolean hasContextMenu() {
		return true;
	}

	/**
	 * Return true if the tree should support DnD.
	 *
	 * @return true if DnD should be supported , false otherwise
	 */
	protected boolean hasDnDSupport() {
		return true;
	}

	/**
	 * Returns the label provider.
	 *
	 * @param adapterFactoryLabelProvider the adaper factory label provider
	 * @return the label provider to use for the tree
	 */
	protected ILabelProvider getLabelProvider(final AdapterFactoryLabelProvider adapterFactoryLabelProvider) {
		return adapterFactoryLabelProvider;
	}

	/**
	 * Creates the composite for the master panel.
	 *
	 * @param sash the parent
	 * @return the composite
	 */
	protected Composite createMasterPanel(final SashForm sash) {
		final Composite leftPanel = new Composite(sash, SWT.NONE);
		leftPanel.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(leftPanel);
		// leftPanel.setBackground(sash.getBackground());
		leftPanel.setBackgroundMode(SWT.INHERIT_FORCE);
		return leftPanel;
	}

	/**
	 * Adds the header to a parent composite.
	 *
	 * @param parent the parent
	 */
	protected void createHeader(Composite parent) {
		final Composite headerComposite = new Composite(parent, SWT.NONE);
		final GridLayout headerLayout = GridLayoutFactory.fillDefaults().create();
		headerComposite.setLayout(headerLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(headerComposite);
		headerBgColor = new Color(parent.getDisplay(), new RGB(220, 240, 247));
		headerComposite.setBackground(headerBgColor);

		final EObject modelElement = getViewModelContext().getDomainModel();
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(modelElement);

		/* The header of the composite */
		if (modelElement.eContainer() == null && !DynamicEObjectImpl.class.isInstance(modelElement)) {

			final Composite header = getPageHeader(headerComposite);
			final List<Action> actions = readToolbarActions(modelElement, editingDomain);

			final ToolBar toolBar = new ToolBar(header, SWT.FLAT | SWT.RIGHT);
			final FormData formData = new FormData();
			formData.right = new FormAttachment(100, 0);
			toolBar.setLayoutData(formData);
			toolBar.layout();
			final ToolBarManager toolBarManager = new ToolBarManager(toolBar);

			/* Add actions to header */
			for (final Action action : actions) {
				toolBarManager.add(action);
			}
			toolBarManager.update(true);
			header.layout();

		}
	}

	/**
	 * @param form2
	 */
	private Composite getPageHeader(Composite parent) {
		final Composite header = new Composite(parent, SWT.FILL);
		final FormLayout layout = new FormLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		header.setLayout(layout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(header);

		header.setBackground(parent.getBackground());

		final Label titleImage = new Label(header, SWT.FILL);
		final ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource("icons/view.png")); //$NON-NLS-1$
		titleImage.setImage(new Image(parent.getDisplay(), imageDescriptor.getImageData()));
		final FormData titleImageData = new FormData();
		final int imageOffset = -titleImage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y / 2;
		titleImageData.top = new FormAttachment(50, imageOffset);
		titleImageData.left = new FormAttachment(0, 10);
		titleImage.setLayoutData(titleImageData);

		final Label title = new Label(header, SWT.WRAP);
		title.setText("View Editor"); //$NON-NLS-1$
		titleFont = new Font(title.getDisplay(), getDefaultFontName(title), 12, SWT.BOLD);
		title.setFont(titleFont);
		title.setForeground(getTitleColor(parent));
		final FormData titleData = new FormData();
		title.setLayoutData(titleData);
		titleData.left = new FormAttachment(titleImage, 5, SWT.DEFAULT);

		return header;

	}

	/**
	 * @return
	 */
	private Color getTitleColor(Composite parent) {
		if (titleColor == null) {
			titleColor = new Color(parent.getDisplay(), new RGB(25, 76, 127));
		}
		return titleColor;
	}

	/**
	 * Creates the composite holding the details.
	 *
	 * @param parent the parent
	 * @return the right panel/detail composite
	 */
	protected ScrolledComposite createRightPanelContent(Composite parent) {
		rightPanel = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		rightPanel.setShowFocusedControl(true);
		rightPanel.setExpandVertical(true);
		rightPanel.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(rightPanel);
		rightPanel.setLayout(GridLayoutFactory.fillDefaults().create());
		rightPanel.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		container = new Composite(rightPanel, SWT.FILL);
		container.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(container);
		container.setBackground(rightPanel.getBackground());

		/* The header */
		final Composite header = new Composite(container, SWT.FILL);
		final GridLayout headerLayout = GridLayoutFactory.fillDefaults().create();
		headerLayout.marginWidth = 5;
		header.setLayout(headerLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(header);
		header.setBackground(rightPanel.getBackground());

		final Label label = new Label(header, SWT.WRAP);
		label.setText("Details"); //$NON-NLS-1$
		detailsFont = new Font(label.getDisplay(), getDefaultFontName(label), 10, SWT.BOLD);
		label.setFont(detailsFont);
		label.setForeground(getTitleColor(parent));
		label.setBackground(header.getBackground());

		rightPanelContainerComposite = new Composite(container, SWT.FILL);
		rightPanelContainerComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true)
			.applyTo(rightPanelContainerComposite);
		rightPanelContainerComposite.setBackground(rightPanel.getBackground());

		rightPanel.setContent(container);

		rightPanel.layout();
		container.layout();

		final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		rightPanel.setMinSize(point);

		return rightPanel;
	}

	@Override
	protected String getDefaultFontName(Control control) {
		return control.getDisplay().getSystemFont().getFontData()[0].getName();
	}

	/**
	 *
	 */
	private List<Action> readToolbarActions(EObject modelElement, final EditingDomain editingDomain) {
		final List<Action> actions = new ArrayList<Action>();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return actions;
		}
		if (!VView.class.isInstance(modelElement)) {
			return actions;
		}
		final VView view = (VView) modelElement;

		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.masterDetailActions"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final String location = e.getAttribute("location"); //$NON-NLS-1$
				if (!location.equals("toolbar")) { //$NON-NLS-1$
					continue;
				}

				final String label = e.getAttribute("label"); //$NON-NLS-1$
				final String imagePath = e.getAttribute("imagePath"); //$NON-NLS-1$
				final MasterDetailAction command = (MasterDetailAction) e.createExecutableExtension("command"); //$NON-NLS-1$
				final Action newAction = new Action() {
					@Override
					public void run() {
						super.run();
						command.execute(view);
					}
				};

				newAction.setImageDescriptor(ImageDescriptor.createFromURL(FrameworkUtil.getBundle(command.getClass())
					.getResource(imagePath)));
				newAction.setText(label);
				actions.add(newAction);
			} catch (final CoreException e1) {
				e1.printStackTrace();
			}
		}
		return actions;
	}

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
		final List<MasterDetailAction> menuActions = readMasterDetailActions();
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new MasterTreeContextMenuListener(editingDomain, treeViewer,
			childrenDescriptorCollector, menuActions));
		final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
	}

	/**
	 * Returns a list of all {@link MasterDetailAction MasterDetailActions} which shall be displayed in the context menu
	 * of the master treeviewer.
	 *
	 * @return the actions
	 */
	protected List<MasterDetailAction> readMasterDetailActions() {
		final List<MasterDetailAction> commands = new ArrayList<MasterDetailAction>();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return commands;
		}

		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.masterDetailActions"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final String location = e.getAttribute("location"); //$NON-NLS-1$
				if (!location.equals("menu")) { //$NON-NLS-1$
					continue;
				}
				final String label = e.getAttribute("label"); //$NON-NLS-1$
				final String imagePath = e.getAttribute("imagePath"); //$NON-NLS-1$
				final MasterDetailAction command = (MasterDetailAction) e.createExecutableExtension("command"); //$NON-NLS-1$
				command.setLabel(label);
				command.setImagePath(imagePath);

				commands.add(command);

			} catch (final CoreException ex) {
				Activator.getDefault().getLog().log(
					new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(),
						ex.getMessage(), ex));
			}
		}

		return commands;

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
				treeViewer.setSelection(new StructuredSelection(getViewModelContext().getDomainModel()));
			}
		};
		final String deleteImagePath = "icons/delete.png";//$NON-NLS-1$
		deleteAction.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(deleteImagePath)));
		deleteAction.setText("Delete"); //$NON-NLS-1$
		manager.add(deleteAction);
	}

	/**
	 * Allows to manipulate the view context for the selected element that is about to be rendered.
	 *
	 * @param viewContext the view context.
	 */
	protected void manipulateViewContext(ViewModelContext viewContext) {
		// do nothing
	}

	/**
	 *
	 * @author Anas Chakfeh
	 *         This class is responsible for handling selection changed events which happen on the tree
	 *
	 */
	private class TreeMasterViewSelectionListener implements ISelectionChangedListener {

		private Composite childComposite;
		private ReferenceService referenceService;

		public TreeMasterViewSelectionListener() {
			// TODO refactor
			if (getViewModelContext().hasService(ReferenceService.class)) {
				referenceService = getViewModelContext().getService(ReferenceService.class);
			}
			else {
				referenceService = new DefaultReferenceService();
			}
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {

			final Object treeSelected = ((IStructuredSelection) event.getSelection()).getFirstElement();
			final Object selected = treeSelected == null ? treeSelected : manipulateSelection(treeSelected);
			if (selected instanceof EObject) {
				try {
					if (childComposite != null) {
						childComposite.dispose();
						cleanCustomOnSelectionChange();
					}
					childComposite = createComposite();

					final Object root = manipulateSelection(((RootObject) ((TreeViewer)
						event.getSource()).getInput())
							.getRoot());
					final Map<String, Object> context = new LinkedHashMap<String, Object>();
					context.put(DETAIL_KEY, true);

					/* root selected */
					if (selected.equals(root)) {
						context.put(ROOT_KEY, true);
						VView vView = getVElement().getDetailView();
						if (vView.getChildren().isEmpty()) {
							vView = ViewProviderHelper.getView((EObject) selected, context);
						}

						final ViewModelContext childContext = getViewModelContext()
							.getChildContext((EObject) selected, getVElement(), vView);

						manipulateViewContext(childContext);

						ECPSWTViewRenderer.INSTANCE.render(childComposite, childContext);

					}
					/* child selected */
					else {
						final VView view = ViewProviderHelper.getView((EObject) selected, context);
						final ViewModelContext childContext = getViewModelContext().getChildContext((EObject) selected,
							getVElement(), view);

						manipulateViewContext(childContext);
						ECPSWTViewRenderer.INSTANCE.render(childComposite, childContext);
					}

					relayoutDetail();
				} catch (final ECPRendererException e) {
					Activator
						.getDefault()
						.getReportService()
						.report(new StatusReport(
							new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e
								.getMessage(), e)));
				}
			}

		}

		private Composite createComposite() {
			final Composite parent = getDetailContainer();
			final Composite composite = new Composite(parent, SWT.NONE);
			composite.setBackground(parent.getBackground());

			final GridLayout gridLayout = GridLayoutFactory.fillDefaults().create();
			// gridLayout.marginTop = 7;
			// gridLayout.marginLeft = 5;
			composite.setLayout(gridLayout);
			GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
			// .indent(10, 10)
			composite.layout(true, true);
			return composite;
		}
	}

	/**
	 * Returns the composite for the detail.
	 *
	 * @return the composite
	 */
	protected Composite getDetailContainer() {
		return rightPanelContainerComposite;
	}

	/**
	 * Allows to manipulate the selection by returning a specific child.
	 *
	 * @param treeSelected the selected element in the tree
	 * @return the object that should be used as a selection
	 */
	protected Object manipulateSelection(Object treeSelected) {
		return TreeMasterDetailSelectionManipulatorHelper.manipulateSelection(treeSelected);
	}

	/**
	 * Gets called after a detail composite was disposed. Allows for further cleanup.
	 */
	protected void cleanCustomOnSelectionChange() {
		// do nothing
	}

	/**
	 * Relayouts the detail composite.
	 */
	protected void relayoutDetail() {
		rightPanelContainerComposite.layout();
		final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		rightPanel.setMinSize(point);
	}

	/**
	 * The label provider used for the detail tree.
	 *
	 * @author jfaltermeier
	 *
	 */
	private class TreeMasterDetailLabelProvider extends AdapterFactoryLabelProvider {

		public TreeMasterDetailLabelProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		@Override
		public Image getImage(Object object) {
			final Image image = super.getImage(object);
			return getValidationOverlay(image, (EObject) object);
		}

		protected Image getValidationOverlay(Image image, final EObject object) {
			// final Integer severity = validationResultCacheTree.getCachedValue(object);
			final VDiagnostic vDiagnostic = getVElement().getDiagnostic();
			int highestSeverity = Diagnostic.OK;
			if (vDiagnostic != null) {
				for (final Diagnostic diagnostic : vDiagnostic.getDiagnostics(object)) {
					if (diagnostic.getSeverity() > highestSeverity) {
						highestSeverity = diagnostic.getSeverity();
					}
				}
			}
			final ImageDescriptor overlay = SWTValidationHelper.INSTANCE
				.getValidationOverlayDescriptor(highestSeverity);
			if (overlay == null) {
				return image;
			}
			final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image, overlay,
				OverlayImageDescriptor.LOWER_RIGHT);
			final Image resultImage = imageDescriptor.createImage();
			return resultImage;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#applyValidation()
	 */
	@Override
	protected void applyValidation() {
		super.applyValidation();

		if (treeViewer == null) {
			return;
		}
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (treeViewer.getTree().isDisposed()) {
					return;
				}
				treeViewer.refresh();
			}
		});

	}

}
