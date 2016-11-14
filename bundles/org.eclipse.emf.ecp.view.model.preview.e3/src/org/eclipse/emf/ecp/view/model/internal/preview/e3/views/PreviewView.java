/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila- initial API and implementation
 * Clemens Elflein - changed for new ViewModelEditor
 ******************************************************************************/

package org.eclipse.emf.ecp.view.model.internal.preview.e3.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecp.ide.editor.view.ViewEditorPart;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.model.internal.preview.Activator;
import org.eclipse.emf.ecp.view.model.internal.preview.ManageAdditionalViewsDialog;
import org.eclipse.emf.ecp.view.model.internal.preview.Messages;
import org.eclipse.emf.ecp.view.model.preview.common.Preview;
import org.eclipse.emf.ecp.view.model.preview.common.PreviewWorkspaceViewProvider;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.internal.editor.viewmodel.ViewModelEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/** The {@link ViewPart} containing a rendered version a {@link VView}. */
public class PreviewView extends ViewPart implements ISelectionListener {

	/**
	 * @author Jonas
	 *
	 */
	private final class PreviewPaintListener implements PaintListener {
		@Override
		public void paintControl(PaintEvent e) {
			// super.paintControl(e);
			final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			scrolledComposite.setMinSize(point);
			container.layout(true);
			scrolledComposite.layout(true);

		}
	}

	/**
	 * @author Clemens Elflein
	 *
	 */
	private final class PreviewPartListener implements IPartListener2 {
		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (ViewEditorPart.class.isInstance(partRef.getPart(true))) {
				final ViewEditorPart part = (ViewEditorPart) partRef.getPart(true);
				if (part.getView() != view) {
					setView(part.getView());
					sampleData = null;
					render(view);
				}
				if (updateAutomatic) {
					preView.registerForViewModelChanges();
				}
			} else if (NEW_EDITOR_CONSTANT
				.equals(partRef.getPart(true) != null ? partRef.getPart(true).getClass().getName() : "")) { //$NON-NLS-1$
				final ViewModelEditor part = (ViewModelEditor) partRef.getPart(true);
				if (part.getView() != view) {
					setView(part.getView());
					sampleData = null;
					render(view);
				}
				if (updateAutomatic) {
					preView.registerForViewModelChanges();
				}
			}
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			if (PreviewView.class.isInstance(partRef.getPart(true))) {

				getSite().getPage().removePartListener(this);
			}

			if (ViewEditorPart.class.isInstance(partRef.getPart(true))) {
				if (updateAutomatic) {
					preView.clear();
				}
				preView.removeView();
				view = null;
			} else if (NEW_EDITOR_CONSTANT
				.equals(partRef.getPart(true) != null ? partRef.getPart(true).getClass().getName() : "")) { //$NON-NLS-1$
				if (updateAutomatic) {
					preView.clear();
				}
				preView.removeView();
				view = null;
			}

		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			final IWorkbenchPart part = partRef.getPart(true);
			if (ViewEditorPart.class.isInstance(part) || PreviewView.class.isInstance(part)) {
				removeAdapters();
			} else if (NEW_EDITOR_CONSTANT
				.equals(partRef.getPart(true) != null ? partRef.getPart(true).getClass().getName() : "") //$NON-NLS-1$
				|| PreviewView.class.isInstance(part)) {
				removeAdapters();
			}
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}
	}

	/**
	 * The qualified name of the new editor. Currently needed to keep an optional dependency on the new editor.
	 * TODO remove again
	 */
	private static final String NEW_EDITOR_CONSTANT = "org.eclipse.emfforms.internal.editor.viewmodel.ViewModelEditor";//$NON-NLS-1$

	private Preview preView;
	private IPartListener2 partListener;
	private Composite form;
	private EContentAdapter adapter;
	private VView view;
	private boolean updateAutomatic;
	private Composite parent;
	private Action automaticToggleButton, manualRefreshButton;
	private Color headerBgColor;
	private Font titleFont;
	private Color titleColor;
	private ScrolledComposite scrolledComposite;
	private Composite container;
	private Action loadDataButton;
	private EObject sampleData;
	private Action cleanDataButton;
	private Action exportDataButton;

	private final Set<IPath> knownViews = new LinkedHashSet<IPath>();
	private BundleContext bundleContext;
	private ServiceReference<PreviewWorkspaceViewProvider> previewViewProviderServiceRef;
	private PreviewWorkspaceViewProvider workspaceViewProvider;

	/** The constructor. */
	public PreviewView() {
		super();
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
		// .addSelectionListener("org.eclipse.emf.ecp.ui.ModelExplorerView", this); //$NON-NLS-1$
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		super.dispose();
		getSite().getPage().removePartListener(partListener);
		preView.clear();
		preView.removeAdapter();
		titleColor.dispose();
		titleFont.dispose();
		headerBgColor.dispose();
		for (final IPath path : knownViews) {
			workspaceViewProvider.removeViewModel(path);
		}
		knownViews.clear();
		workspaceViewProvider = null;
		bundleContext.ungetService(previewViewProviderServiceRef);
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		previewViewProviderServiceRef = bundleContext.getServiceReference(PreviewWorkspaceViewProvider.class);
		workspaceViewProvider = bundleContext.getService(previewViewProviderServiceRef);

		/* The container composite */
		form = new Composite(parent, SWT.BORDER);
		form.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		form.setLayout(GridLayoutFactory.fillDefaults().create());

		/* The header */
		createHeader(parent);

		/* The body */
		createBody(parent);

		/* The container */
		container = new Composite(scrolledComposite, SWT.FILL);
		final GridLayout containerLayout = GridLayoutFactory.fillDefaults().create();
		containerLayout.marginLeft = 10;
		containerLayout.marginRight = 10;
		container.setLayout(containerLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(container);
		container.setBackground(scrolledComposite.getBackground());
		scrolledComposite.setContent(container);
		container.addPaintListener(new PreviewPaintListener());
		container.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
				container.layout(true);
				scrolledComposite.layout(true);

			}

			@Override
			public void controlMoved(ControlEvent e) {
				final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
				container.layout(true);
				scrolledComposite.layout(true);
			}
		});

		preView = new Preview(container);

		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		final IWorkbenchPage page = win.getActivePage();

		for (final IEditorReference reference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.getEditorReferences()) {
			final IEditorPart part = reference.getEditor(false);
			if (page.isPartVisible(part)) {
				if (ViewEditorPart.class.isInstance(part)) {
					final ViewEditorPart viewPart = (ViewEditorPart) part;
					setView(viewPart.getView());
					render(view);
				} else if (NEW_EDITOR_CONSTANT.equals(part != null ? part.getClass().getName() : "")) { //$NON-NLS-1$
					final ViewModelEditor viewPart = (ViewModelEditor) part;
					setView(viewPart.getView());
					render(view);
				}
			}
		}

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		partListener = new PreviewPartListener();
		getSite().getPage().addPartListener(partListener);
	}

	private void createBody(Composite parent) {
		scrolledComposite = new ScrolledComposite(form, SWT.V_SCROLL | SWT.H_SCROLL);
		scrolledComposite.setShowFocusedControl(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(scrolledComposite);
		scrolledComposite.setLayout(GridLayoutFactory.fillDefaults().create());
		scrolledComposite.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
	}

	private void createHeader(Composite parent) {
		final Composite headerComposite = new Composite(form, SWT.FILL);
		final GridLayout headerLayout = GridLayoutFactory.fillDefaults().create();
		headerComposite.setLayout(headerLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(headerComposite);
		headerBgColor = new Color(parent.getDisplay(), new RGB(220, 240, 247));
		headerComposite.setBackground(headerBgColor);

		final Composite header = getPageHeader(headerComposite);
		final ToolBar toolBar = new ToolBar(header, SWT.FLAT | SWT.RIGHT);
		final FormData formData = new FormData();
		formData.right = new FormAttachment(100, 0);
		toolBar.setLayoutData(formData);
		toolBar.layout();
		final ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		addButtonsToFormToolbar(toolBarManager);
		header.layout();
	}

	/**
	 * @param parent
	 * @return
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
			.getResource("icons/preview.png")); //$NON-NLS-1$
		titleImage.setImage(new Image(parent.getDisplay(), imageDescriptor.getImageData()));
		final FormData titleImageData = new FormData();
		final int imageOffset = -titleImage.computeSize(SWT.DEFAULT, SWT.DEFAULT).y / 2;
		titleImageData.top = new FormAttachment(50, imageOffset);
		titleImageData.left = new FormAttachment(0, 10);
		titleImage.setLayoutData(titleImageData);

		final Label title = new Label(header, SWT.WRAP);
		title.setText(Messages.PreviewView_EditorTitle);
		titleFont = new Font(title.getDisplay(), getDefaultFontName(title), 12, SWT.BOLD);
		title.setFont(titleFont);
		title.setForeground(getTitleColor());
		final FormData titleData = new FormData();
		title.setLayoutData(titleData);
		titleData.left = new FormAttachment(titleImage, 5, SWT.DEFAULT);

		return header;
	}

	/**
	 * @param control
	 * @return
	 */
	private String getDefaultFontName(Label control) {
		return control.getDisplay().getSystemFont().getFontData()[0].getName();
	}

	private Color getTitleColor() {
		if (titleColor == null) {
			titleColor = new Color(form.getDisplay(), new RGB(25, 76, 127));
		}
		return titleColor;
	}

	/**
	 * @param toolBarManager toolBarManager the toolbar manager to which the buttons should be added.
	 */
	private void addButtonsToFormToolbar(IToolBarManager toolBarManager) {

		addSampleDataButtons(toolBarManager);
		toolBarManager.add(new Separator());
		addRefreshButtons(toolBarManager);
		toolBarManager.add(new Separator());
		additionalViewsButtons(toolBarManager);
		toolBarManager.update(true);
	}

	private void additionalViewsButtons(IToolBarManager toolBarManager) {
		final Action manageAdditionalViews = new Action() {
			@Override
			public void run() {
				super.run();
				openAdditionalViewsDialog();
			}
		};
		manageAdditionalViews.setText(Messages.PreviewView_AdditionalViews);
		final String exportDataImagePath = "icons/manageViews.png";//$NON-NLS-1$
		manageAdditionalViews.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle().getResource(exportDataImagePath)));
		toolBarManager.add(manageAdditionalViews);
	}

	private void openAdditionalViewsDialog() {
		final Set<IPath> input = new LinkedHashSet<IPath>(knownViews);
		final ManageAdditionalViewsDialog d = new ManageAdditionalViewsDialog(getSite().getShell(), input);
		final int result = d.open();
		if (result == Window.OK) {
			// add views
			final Set<IPath> viewsToAdd = new LinkedHashSet<IPath>(input);
			viewsToAdd.removeAll(knownViews);
			for (final IPath path : viewsToAdd) {
				knownViews.add(path);
				workspaceViewProvider.addViewModel(path);
			}
			// remove views
			final Set<IPath> viewsToRemove = new LinkedHashSet<IPath>(knownViews);
			viewsToRemove.removeAll(input);
			for (final IPath path : viewsToRemove) {
				knownViews.remove(path);
				workspaceViewProvider.removeViewModel(path);
			}

			render();
		}

	}

	/**
	 * Adds the load, export and clear sample data buttons to the toolbar.
	 *
	 * @param toolBarManager the toolbar manager to which the buttons should be added.
	 */
	private void addSampleDataButtons(IToolBarManager toolBarManager) {
		// load sample data
		loadDataButton = new Action() {
			@Override
			public void run() {
				super.run();
				loadSampleData();
			}
		};
		final String loadDataImagePath = "icons/loadData.png";//$NON-NLS-1$
		loadDataButton.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(loadDataImagePath)));

		loadDataButton.setText(Messages.PreviewView_ImportSampleDataButton);
		loadDataButton.setEnabled(true);

		// export sample data
		exportDataButton = new Action() {
			@Override
			public void run() {
				super.run();
				exportSampleData();
			}
		};
		final String exportDataImagePath = "icons/exportData.png";//$NON-NLS-1$
		exportDataButton.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(exportDataImagePath)));

		exportDataButton.setText(Messages.PreviewView_ExportSampleDataButton);
		exportDataButton.setEnabled(true);

		// clean sample data
		cleanDataButton = new Action() {
			@Override
			public void run() {
				super.run();
				sampleData = null;
				preView.cleanSampleData();
				render();
			}
		};
		final String cleanDataImagePath = "icons/cleanData.png";//$NON-NLS-1$
		cleanDataButton.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(cleanDataImagePath)));
		cleanDataButton.setText(Messages.PreviewView_ClearSampleDataButton);
		cleanDataButton.setEnabled(true);

		toolBarManager.add(cleanDataButton);
		toolBarManager.add(loadDataButton);
		toolBarManager.add(exportDataButton);

	}

	/**
	 * Adds the automatic and manual refresh buttons to the toolbar.
	 *
	 * @param toolBarManager the toolbar manager to which the buttons should be added.
	 *
	 */

	private void addRefreshButtons(IToolBarManager toolBarManager) {
		// automatic refresh
		automaticToggleButton = new Action("", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				super.run();
				setUpdateAutomatic(isChecked());
				manualRefreshButton.setEnabled(!isChecked());
			}
		};

		final String autoRefreshImagePath = "icons/arrow_rotate_anticlockwise.png";//$NON-NLS-1$
		automaticToggleButton.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(autoRefreshImagePath)));

		automaticToggleButton.setText(Messages.PreviewView_AutomaticRefresh);
		automaticToggleButton.setEnabled(true);
		automaticToggleButton.setChecked(false);

		// manual refresh
		manualRefreshButton = new Action() {
			@Override
			public void run() {
				super.run();
				render();
			}
		};
		final String manualRefreshImagePath = "icons/arrow_refresh.png";//$NON-NLS-1$
		manualRefreshButton.setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault()
			.getBundle()
			.getResource(manualRefreshImagePath)));

		manualRefreshButton.setText(Messages.PreviewView_ManualRefresh);
		manualRefreshButton.setEnabled(true);

		toolBarManager.add(manualRefreshButton);
		toolBarManager.add(automaticToggleButton);

	}

	/**
	 * Exports the data from the preview as an xmi file.
	 */
	protected void exportSampleData() {
		if (preView == null) {
			return;
		}

		final EObject sampleData = preView.getSampleData();
		if (sampleData == null) {
			return;
		}

		final FileDialog dialog = new FileDialog(parent.getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] { "*.xmi" }); //$NON-NLS-1$
		final String result = dialog.open();
		if (result == null) {
			return;
		}
		final ResourceSet rs = new ResourceSetImpl();
		final Resource resource = rs.createResource(URI.createFileURI(result));
		resource.getContents().add(sampleData);
		try {
			resource.save(null);
		} catch (final IOException e) {

			final StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			final String stackTrace = sw.toString();

			/*
			 * splitting stack trace into children statuses, such that each line in the message will show up on a new
			 * line in the details section of the error dialog
			 */
			final List<Status> childStatuses = new ArrayList<Status>();
			for (final String line : stackTrace.split(System.getProperty("line.separator"))) { //$NON-NLS-1$
				childStatuses.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, line));
			}
			final MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR,
				childStatuses.toArray(new Status[] {}),
				e.getLocalizedMessage(), null);
			ErrorDialog.openError(parent.getShell(), Messages.PreviewView_SaveErrorDialogTitle,
				Messages.PreviewView_SaveErrorDescription, status);

		}

	}

	/**
	 * Loads an xmi into the preview.
	 */
	private void loadSampleData() {

		if (view == null || view.getRootEClass() == null) {
			return;
		}
		final FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.xmi" }); //$NON-NLS-1$
		final String result = dialog.open();
		if (result == null) {
			return;
		}
		final ResourceSet rs = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(
			new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createFileURI(result));

		try {
			resource.load(null);
		} catch (final IOException ex) {
			// do nothing - exceptions treated below
		}
		if (!resource.getContents().isEmpty()) {
			sampleData = resource.getContents().get(0);
			if (sampleData != null && sampleData.eClass() == view.getRootEClass()) {
				render();
			} else {
				new MessageDialog(
					parent.getShell(),
					Messages.PreviewView_WrongInputTypeError,
					null,
					String.format(Messages.PreviewView_WrongInputTypeErrorDetails,
						sampleData.eClass().getName(),
						view.getRootEClass().getName()),
					MessageDialog.ERROR, new String[] { Messages.PreviewView_OK }, 0).open();
				sampleData = null;
			}
		} else {
			sampleData = null;
			new MessageDialog(
				parent.getShell(), Messages.PreviewView_WrongInputFileContentError,
				null, Messages.PreviewView_WrongInputFileContentErrorDetails,
				MessageDialog.ERROR, new String[] { Messages.PreviewView_OK }, 0).open();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		final IStructuredSelection ss = (IStructuredSelection) selection;
		final Object firstElement = ss.getFirstElement();
		if (!VView.class.isInstance(firstElement)) {
			return;
		}
		setView((VView) firstElement);
		render(view);
	}

	private void render(VView view) {
		if (adapter != null) {
			// remove adapter
			removeAdapters();

		}
		adapter = new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification notification) {
				if (form.isDisposed()) {
					return;
				}
				super.notifyChanged(notification);
				if (notification.isTouch()) {
					return;
				}

				// TODO needed?
				if (EStructuralFeature.class.cast(notification.getFeature()).isTransient()) {
					return;
				}

				final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
				scrolledComposite.layout(true);
			}
		};

		view.eAdapters().add(adapter);
		preView.registerForViewModelChanges();
		preView.render(view, sampleData);
		final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scrolledComposite.setMinSize(point);
		scrolledComposite.layout(true);
	}

	private void setView(VView view) {
		if (this.view != view) {
			removeAdapters();
			this.view = view;
		}
	}

	private void removeAdapters() {
		if (view != null && adapter != null) {
			for (final Adapter a : view.eAdapters()) {
				if (a.equals(adapter)) {
					view.eAdapters().remove(adapter);
					adapter = null;
					break;
				}
			}
		}
		preView.removeAdapter();
	}

	/**
	 * @return the updateAutomatic
	 */
	public boolean isUpdateAutomatic() {
		return updateAutomatic;
	}

	/**
	 * @param updateAutomatic the updateAutomatic to set
	 */
	private void setUpdateAutomatic(boolean updateAutomatic) {
		this.updateAutomatic = updateAutomatic;
		if (preView != null) {
			preView.setUpdateAutomatic(updateAutomatic);
			final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			scrolledComposite.setMinSize(point);
			scrolledComposite.layout(true);
		}
	}

	private void render() {
		if (preView != null) {
			if (view != null) {
				render(view);
				final Point point = container.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledComposite.setMinSize(point);
				scrolledComposite.layout(true);
				parent.layout();
			} else {
				preView.clear();
			}

		}
	}
}