/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.internal.validation.ValidationServiceImpl;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.SegmentResolvementUtil;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * An advanced DMR creation wizard that allows the user to either create a simple reference path consisting only of
 * feature segments or switching to an advanced mode. The advanced mode allows to create the reference path one
 * segment at a time. Thereby, all available segment types can be created.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class AdvancedCreateDomainModelReferenceWizard extends Wizard {

	private final EditingDomain editingDomain;
	private final EStructuralFeature structuralFeature;
	private final EObject eObject;
	private final EClass rootEClass;
	private final VDomainModelReference existingDMR;
	private final EStructuralFeatureSelectionValidator selectionValidator;
	private final SegmentGenerator segmentGenerator;
	private final String lastSegmentTypeInfo;
	/**
	 * Contains all types of segments that can be created in the advanced mode. The types are mapped to their
	 * corresponding {@link SegmentIdeDescriptor}.
	 */
	private final Map<EClass, SegmentIdeDescriptor> segmentToIdeDescriptorMap = new LinkedHashMap<EClass, SegmentIdeDescriptor>();
	private final List<ServiceReference<SegmentIdeDescriptor>> serviceReferences = new LinkedList<ServiceReference<SegmentIdeDescriptor>>();

	private AdvancedDMRWizardFirstPage firstPage;
	private VDomainModelReference advancedDmr;
	private final EClass lastSegmentType;

	/**
	 * A wizard used for creating and configuring a DomainModelReference.
	 *
	 * @param eObject The {@link EObject} containing a domain model reference
	 * @param structuralFeature The corresponding {@link EStructuralFeature}
	 * @param editingDomain The setting's editing domain
	 * @param rootEClass The root {@link EClass} of the VView the eObject belongs to
	 * @param windowTitle The title for the wizard window
	 * @param existingDMR The domain model reference to configure. May be null, then a new DMR is created
	 * @param selectionValidator Validates whether a selected structural feature is a valid selection (e.g. the
	 *            selection could be required to be a multi reference)
	 * @param segmentGenerator The {@link SegmentGenerator} used in the simple dmr creation mode
	 * @param lastSegmentType The type that the last segment in advanced creation mode must have, or
	 *            <strong>null</strong> if there is no restriction
	 * @param ignoreSegmentIdeRestriction If <code>true</code>, all types of segments are available independently of the
	 *            configuration in their {@link SegmentIdeDescriptor}.
	 */
	// CHECKSTYLE.OFF: ParameterNumber
	public AdvancedCreateDomainModelReferenceWizard(final EObject eObject, final EStructuralFeature structuralFeature,
		final EditingDomain editingDomain, final EClass rootEClass, final String windowTitle,
		VDomainModelReference existingDMR, EStructuralFeatureSelectionValidator selectionValidator,
		SegmentGenerator segmentGenerator, EClass lastSegmentType, boolean ignoreSegmentIdeRestriction) {
		// CHECKSTYLE.ON: ParameterNumber
		setWindowTitle(windowTitle);
		this.eObject = eObject;
		this.structuralFeature = structuralFeature;
		this.editingDomain = editingDomain;
		this.rootEClass = rootEClass;
		this.existingDMR = existingDMR;
		this.selectionValidator = selectionValidator;
		this.segmentGenerator = segmentGenerator;
		this.lastSegmentType = lastSegmentType;

		setForcePreviousAndNextButtons(true);
		advancedDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		if (lastSegmentType != null) {
			lastSegmentTypeInfo = "\nNote: Cannot finish because the last segment must be a " //$NON-NLS-1$
				+ lastSegmentType.getName();
		} else {
			lastSegmentTypeInfo = ""; //$NON-NLS-1$
		}

		try {
			final BundleContext bundleContext = FrameworkUtil.getBundle(AdvancedCreateDomainModelReferenceWizard.class)
				.getBundleContext();
			final Collection<ServiceReference<SegmentIdeDescriptor>> references = bundleContext
				.getServiceReferences(SegmentIdeDescriptor.class, null);
			for (final ServiceReference<SegmentIdeDescriptor> serviceRef : references) {
				final SegmentIdeDescriptor service = bundleContext.getService(serviceRef);
				if (ignoreSegmentIdeRestriction || service.isAvailableInIde()) {
					segmentToIdeDescriptorMap.put(service.getSegmentType(), service);
					serviceReferences.add(serviceRef);
				}
			}
		} catch (final InvalidSyntaxException ex) {
			// Should never happen because no filter is used
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#dispose()
	 */
	@Override
	public void dispose() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(AdvancedCreateDomainModelReferenceWizard.class)
			.getBundleContext();
		for (final ServiceReference<SegmentIdeDescriptor> serviceRef : serviceReferences) {
			bundleContext.ungetService(serviceRef);
		}
		segmentToIdeDescriptorMap.clear();
		serviceReferences.clear();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		firstPage = new AdvancedDMRWizardFirstPage("New Domain Model Reference", //$NON-NLS-1$
			"Select an EStructuralFeature", //$NON-NLS-1$
			"Select a structural feature for the domain model reference or switch to the advanced creation mode\n to create the reference path one feature at a time.", //$NON-NLS-1$
			rootEClass, getInitialSelection());
		addPage(firstPage);
	}

	/**
	 * @return The initial selection for the {@link SelectFeaturePathWizardPage SelectFeaturePathWizardPage's} tree
	 *         viewer. Returns an empty selection if there is no existing dmr or it does not contain any segments.
	 */
	private ISelection getInitialSelection() {
		if (existingDMR == null || existingDMR.getSegments().isEmpty()) {
			return TreeSelection.EMPTY;
		}

		final List<EStructuralFeature> pathList = SegmentResolvementUtil
			.resolveSegmentsToFeatureList(existingDMR.getSegments(), rootEClass);
		if (pathList.size() == existingDMR.getSegments().size()) {
			return new TreeSelection(new TreePath(pathList.toArray()));
		}
		return TreeSelection.EMPTY;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (!canFinish()) {
			return false;
		}

		Command command = null;
		if (firstPage.equals(getContainer().getCurrentPage())) {
			if (structuralFeature.isMany()) {
				command = AddCommand.create(editingDomain, eObject,
					structuralFeature, firstPage.getDomainModelReference());
			} else {
				command = SetCommand.create(editingDomain, eObject,
					structuralFeature, firstPage.getDomainModelReference());
			}
		} else {
			final SegmentCreationPage finalSegmentCreationPage = (SegmentCreationPage) getContainer().getCurrentPage();
			final int lastSegmentIndex = finalSegmentCreationPage.index;
			/*
			 * If there are segments in the dmr with a higher index than the final segment creation page,
			 * they origin from a page with a higher index where the "back" button has been clicked by the user.
			 * => They need to be removed.
			 */
			for (int i = advancedDmr.getSegments().size() - 1; i > lastSegmentIndex; i--) {
				advancedDmr.getSegments().remove(i);
			}

			// remove validation adapters - they are only needed during the dmr creation
			for (final VDomainModelReferenceSegment segment : advancedDmr.getSegments()) {
				for (final Adapter adapter : segment.eAdapters()) {
					if (SegmentCreationPage.SegmentValidationAdapter.class.isInstance(adapter)) {
						segment.eAdapters().remove(adapter);
						// only one is registered, avoid concurrent modification exception
						break;
					}
				}
			}
			
			if (structuralFeature.isMany()) {
				command = AddCommand.create(editingDomain, eObject,
					structuralFeature, advancedDmr);
			} else {
				command = SetCommand.create(editingDomain, eObject,
					structuralFeature, advancedDmr);
			}
		}

		editingDomain.getCommandStack().execute(command);
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		// "simple" mode
		if (firstPage.equals(getContainer().getCurrentPage())) {
			return firstPage.isPageComplete();
		}
		// "advanced" mode
		if (advancedDmr.getSegments().isEmpty() || !getContainer().getCurrentPage().isPageComplete()) {
			return false;
		}
		if (lastSegmentType == null) {
			return true;
		}
		final EList<VDomainModelReferenceSegment> segments = advancedDmr.getSegments();
		return lastSegmentType.isInstance(segments.get(segments.size() - 1));
	}

	/**
	 * The wizard's first page. It allows to either create a simple reference path consisting only of feature segments
	 * or switching to an advanced mode. The advanced mode allows to create the reference path one reference at a time.
	 * Thereby, all available segment types can be created.
	 *
	 * @author Lucas Koehler
	 *
	 */
	private class AdvancedDMRWizardFirstPage extends SelectFeaturePathWizardPage {

		protected AdvancedDMRWizardFirstPage(String pageName, String pageTitle, String pageDescription,
			EClass rootEClass, ISelection firstSelection) {
			super(pageName, pageTitle, pageDescription, rootEClass, firstSelection, segmentGenerator,
				selectionValidator, false);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);

			final Composite controlComposite = (Composite) getControl();

			final Button advancedModeButton = new Button(controlComposite, SWT.NONE);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.END).grab(true, false).applyTo(advancedModeButton);
			advancedModeButton.setText("Switch To Advanced DMR Creation"); //$NON-NLS-1$
			if (!segmentToIdeDescriptorMap.isEmpty()) {
				advancedModeButton.setToolTipText(
					"Using the advanced Domain Model Reference creation allows to use all available kinds of segments, i.e. index segments."); //$NON-NLS-1$
				advancedModeButton.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						final IWizardPage advancedPage = new SegmentCreationPage("Select Segment Type", rootEClass, 0); //$NON-NLS-1$
						addPage(advancedPage);
						advancedDmr = VViewFactory.eINSTANCE.createDomainModelReference();
						getContainer().showPage(advancedPage);

					}
				});
			} else {
				// There are no registered segment types that can be used => Advanced mode is useless
				advancedModeButton.setEnabled(false);
				advancedModeButton.setToolTipText(
					"There are no segment types available for IDE usage. Therefore, the advanced creation mode is deactivated."); //$NON-NLS-1$
			}
		}

		/**
		 * Override method and never allow to flip to next page: Advanced mode should only be accessed by using the
		 * designated button.<br/>
		 * The next button would allow to go back to the advanced mode after it has been activated and the user pressed
		 * back on the first advanced page. This could lead to unexpected behavior. Therefore, it is not allowed.
		 * <hr>
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
		 */
		@Override
		public boolean canFlipToNextPage() {
			return false;
		}

	}

	/**
	 * WizardPage to create one segment. Allows to create all types of registered segments.
	 *
	 * @author Lucas Koehler
	 *
	 */
	private class SegmentCreationPage extends WizardPage {
		private static final String PLEASE_SELECT_A_SEGMENT_TYPE_ERROR_MSG = "Please select a segment type."; //$NON-NLS-1$
		private final int index;

		private ComposedAdapterFactory composedAdapterFactory;
		private AdapterFactoryLabelProvider labelProvider;
		private EClass rootEClass;

		private EClass segmentType;
		private VFeatureDomainModelReferenceSegment createdSegment;
		private Composite renderComposite;
		private ECPSWTView segmentECPSWTView;
		private SegmentCreationPage nextPage;
		private TableViewer tableViewer;

		/**
		 * Set to true, if the page is complete except for the successful validation of the current segment. Is still
		 * true when the validation is successful, too.
		 */
		private boolean pageCompleteWithoutValidation;

		/**
		 * @param pageName
		 * @param rootEClass The root {@link EClass} to select the feature from for the path segment created by this
		 *            page
		 * @param index The index of the created segment in the DMRs segment list. This is necessary to set the segment
		 *            at the correct location in the DMR.
		 */
		protected SegmentCreationPage(String pageName, EClass rootEClass, int index) {
			super(pageName);
			setTitle("Create a Segment"); //$NON-NLS-1$
			setDescription("Create a segment by selecting its type and an appropriate feature."); //$NON-NLS-1$
			this.rootEClass = rootEClass;
			this.index = index;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
		 */
		@Override
		public boolean canFlipToNextPage() {
			if (createdSegment == null) {
				return false;
			}
			final SegmentIdeDescriptor ideDescriptor = segmentToIdeDescriptorMap.get(createdSegment.eClass());
			if (ideDescriptor.isLastElementInPath()) {
				return false;
			}
			final String domainModelFeature = createdSegment.getDomainModelFeature();
			if (domainModelFeature != null && !domainModelFeature.isEmpty()) {
				final EStructuralFeature feature = rootEClass
					.getEStructuralFeature(createdSegment.getDomainModelFeature());
				if (EReference.class.isInstance(feature)) {
					// Feature segments can only contain a multi reference at the end of the reference path, never in
					// the beginning or the middle
					if (VViewPackage.eINSTANCE.getFeatureDomainModelReferenceSegment()
						.equals(createdSegment.eClass()) && EReference.class.cast(feature).isMany()) {
						return false;
					}

					if (nextPage.rootEClass != null) {
						return super.canFlipToNextPage();
					}
				}
			}
			return false;
		}

		/**
		 * Sets the root {@link EClass} of this page and updates the input of the table viewer.
		 *
		 * @param rootEClass The new root {@link EClass}
		 */
		public void setRootEClass(EClass rootEClass) {
			this.rootEClass = rootEClass;
			if (tableViewer != null) {
				tableViewer.setInput(rootEClass);
			}
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(final Composite parent) {
			nextPage = new SegmentCreationPage(getName(), null, index + 1);
			addPage(nextPage);

			final Composite composite = new Composite(parent, SWT.FILL);
			GridLayoutFactory.fillDefaults().applyTo(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);

			composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			labelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
			final EStructuralFeatureContentProvider contentProvider = new EStructuralFeatureContentProvider(
				false);

			// get subclasses
			final Collection<EClass> segmentClasses = segmentToIdeDescriptorMap.keySet();
			final Map<String, EClass> segmentClassesMap = new LinkedHashMap<String, EClass>();
			for (final EClass segmentClass : segmentClasses) {
				segmentClassesMap.put(segmentClass.getName(), segmentClass);
			}

			// configure combo box
			final Combo segmentTypeSelector = new Combo(composite, SWT.READ_ONLY | SWT.DROP_DOWN);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false)
				.applyTo(segmentTypeSelector);
			final String[] items = segmentClassesMap.keySet().toArray(new String[segmentClassesMap.size()]);
			Arrays.sort(items);
			segmentTypeSelector.setItems(items);

			// table viewer
			tableViewer = new TableViewer(composite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(tableViewer.getControl());
			tableViewer.setContentProvider(contentProvider);
			tableViewer.setLabelProvider(labelProvider);
			tableViewer.setInput(rootEClass);
			tableViewer.addSelectionChangedListener(new FeatureSelectionChangedListerner());
			segmentTypeSelector.addListener(SWT.Selection,
				new SegmentTypeSelectionListener(composite, segmentClassesMap, tableViewer, segmentTypeSelector));

			// If available, select the feature segment by default
			if (segmentClassesMap.values().contains(VViewPackage.eINSTANCE.getFeatureDomainModelReferenceSegment())) {
				final int index = Arrays.binarySearch(items,
					VViewPackage.eINSTANCE.getFeatureDomainModelReferenceSegment().getName());
				if (index >= 0) {
					segmentTypeSelector.select(index);
					segmentTypeSelector.notifyListeners(SWT.Selection, new Event());
				}
			}

			setControl(composite);
		}

		/**
		 * Creates the {@link Composite} used to render the advanced properties of the current segment.
		 *
		 * @param parent The created {@link Composite Composite's} parent.
		 * @return The {@link Composite}
		 */
		private Composite createRenderComposite(final Composite parent) {
			final Composite renderComposite = new Composite(parent, SWT.FILL);
			GridLayoutFactory.fillDefaults().applyTo(renderComposite);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(renderComposite);
			return renderComposite;
		}

		/**
		 * Listener to handle a segment type selection.
		 *
		 * @author Lucas Koehler
		 *
		 */
		private final class SegmentTypeSelectionListener implements Listener {
			private final Composite composite;
			private final Map<String, EClass> segmentClassesMap;
			private final TableViewer tableViewer;
			private final Combo segmentTypeSelector;

			private SegmentTypeSelectionListener(Composite composite, Map<String, EClass> segmentClassesMap,
				TableViewer tableViewer, Combo segmentTypeSelector) {
				this.composite = composite;
				this.segmentClassesMap = segmentClassesMap;
				this.tableViewer = tableViewer;
				this.segmentTypeSelector = segmentTypeSelector;
			}

			@Override
			public void handleEvent(Event event) {
				if (segmentTypeSelector.getText() == null || segmentTypeSelector.getText().isEmpty()) {
					setErrorMessage(PLEASE_SELECT_A_SEGMENT_TYPE_ERROR_MSG);
					createdSegment = null;
				}
				if (PLEASE_SELECT_A_SEGMENT_TYPE_ERROR_MSG.equals(getErrorMessage())) {
					setErrorMessage(null);
				}

				segmentType = segmentClassesMap.get(segmentTypeSelector.getText());
				// (re)set last segment type info text
				if (lastSegmentType != null && !lastSegmentType.isSuperTypeOf(segmentType)
					&& !getDescription().endsWith(lastSegmentTypeInfo)) {
					setDescription(getDescription() + lastSegmentTypeInfo);
				} else if (lastSegmentType != null && lastSegmentType.isSuperTypeOf(segmentType)
					&& getDescription().endsWith(lastSegmentTypeInfo)) {
					final String desc = getDescription();
					setDescription(desc.substring(0, desc.length() - lastSegmentTypeInfo.length()));
				}
				// create segment and add to dmr
				createdSegment = (VFeatureDomainModelReferenceSegment) EcoreUtil.create(segmentType);
				createdSegment.eAdapters().add(new SegmentValidationAdapter());
				if (advancedDmr.getSegments().size() > index) {
					advancedDmr.getSegments().set(index, createdSegment);
				} else {
					advancedDmr.getSegments().add(createdSegment);
				}

				// render segment properties
				try {
					if (renderComposite != null) {
						renderComposite.dispose();
					}
					renderComposite = createRenderComposite(composite);
					segmentECPSWTView = ECPSWTViewRenderer.INSTANCE.render(renderComposite, createdSegment);
					composite.layout();
				} catch (final ECPRendererException ex) {
					MessageDialog.openError(getShell(), "Rendering Error", //$NON-NLS-1$
						"The current segment could not be rendered: " + ex.getMessage()); //$NON-NLS-1$
				}
				// trigger selection changed event to apply current selection to the selected segment type
				tableViewer.setSelection(tableViewer.getSelection(), true);
			}
		}

		/**
		 * An adapter that is registered at the createdSegment to re-validate it on change.
		 *
		 * @author Lucas Koehler
		 *
		 */
		private class SegmentValidationAdapter extends AdapterImpl {
			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification notification) {
				if (notification.isTouch()) {
					return;
				}
				if (validateSegment()) {
					if (pageCompleteWithoutValidation) {
						setErrorMessage(null);
						setPageComplete(true);
					}
				}
			}
		}

		/**
		 * Selection changed listener for the table containing the structural features to select for the current
		 * segment.
		 *
		 * @author Lucas Koehler
		 *
		 */
		private class FeatureSelectionChangedListerner implements ISelectionChangedListener {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				/*
				 * If there are segments in the dmr with a higher index than the current page,
				 * they origin from a page with a higher index where the "back" button has been clicked by the user.
				 * => They need to be removed, because they are no longer valid when the selection changes
				 */
				for (int i = advancedDmr.getSegments().size() - 1; i > index; i--) {
					advancedDmr.getSegments().remove(i);
				}

				// Page is not complete until the opposite is proven
				setPageComplete(false);
				pageCompleteWithoutValidation = false;

				// Get the selected element
				final IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
				if (structuredSelection.isEmpty()) {
					return;
				}

				if (createdSegment == null) {
					setErrorMessage(PLEASE_SELECT_A_SEGMENT_TYPE_ERROR_MSG);
					return;
				}

				// Validate that a valid structural feature was selected
				final EStructuralFeature structuralFeature = (EStructuralFeature) structuredSelection
					.getFirstElement();
				final SegmentIdeDescriptor ideDescriptor = segmentToIdeDescriptorMap.get(createdSegment.eClass());
				final String errorMessage = ideDescriptor.getEStructuralFeatureSelectionValidator()
					.isValid(structuralFeature);
				setErrorMessage(errorMessage);
				if (errorMessage != null) {
					return;
				}

				createdSegment.setDomainModelFeature(structuralFeature.getName());
				if (EReference.class.isInstance(structuralFeature)) {
					final EReference reference = (EReference) structuralFeature;
					nextPage.setRootEClass(ideDescriptor.getReferenceTypeResolver().resolveNextEClass(reference));
					if (nextPage.rootEClass == null) {
						return;
					}
				}

				pageCompleteWithoutValidation = true;

				if (validateSegment()) {
					setPageComplete(true);
				}
			}

		}

		/**
		 * Validates this page's segment and sets the appropriate (error) message if necessary.
		 *
		 * @return <code>true</code> if the segment is valid, <code>false</code> otherwise
		 */
		private boolean validateSegment() {
			// FIXME get new validation service over OSGI after rebase
			final ValidationServiceImpl validationService = (ValidationServiceImpl) segmentECPSWTView
				.getViewModelContext().getService(ValidationService.class);
			final Diagnostic diagnostic = validationService.getDiagnosticForEObject(createdSegment);
			if (diagnostic.getSeverity() == Diagnostic.INFO) {
				setMessage("Validation Information: " + diagnostic.getMessage()); //$NON-NLS-1$
			} else if (diagnostic.getSeverity() == Diagnostic.WARNING) {
				setMessage("Validation Warning: " + diagnostic.getMessage()); //$NON-NLS-1$
			} else if (diagnostic.getSeverity() == Diagnostic.ERROR) {
				setErrorMessage(diagnostic.getMessage());
				return false;
			}
			return true;
		}
	}
}
