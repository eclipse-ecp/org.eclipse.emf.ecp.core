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
 * Alexandra Buzila - initial API and implementation
 * Lucas Koehler - Also support DMR segments (Bug 542669)
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.spi.common.ui.CompositeFactory;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceImpl;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.ide.view.segments.ToolingModeUtil;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Renderer for DomainModelReferences.
 *
 * @author Alexandra Buzila
 *
 */
public class DomainModelReferenceControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private final EMFFormsEditSupport emfFormsEditSupport;
	private final EMFFormsDatabindingEMF emfFormsDatabindingEMF;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabindingEMF The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public DomainModelReferenceControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabindingEMF emfFormsDatabindingEMF,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabindingEMF, emfFormsLabelProvider,
			vtViewTemplateProvider);
		this.emfFormsDatabindingEMF = emfFormsDatabindingEMF;
		this.emfFormsEditSupport = emfFormsEditSupport;
	}

	private Composite mainComposite;
	private StackLayout stackLayout;
	private Label unsetLabel;
	private EObject eObject;
	private EStructuralFeature structuralFeature;
	// private Setting setting;
	private ECPModelElementChangeListener modelElementChangeListener;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private Composite parentComposite;
	private Label setLabel;
	private Label imageLabel;
	private Composite contentSetComposite;

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {

		final Binding[] bindings = new Binding[3];
		final IObservableValue value = WidgetProperties.text().observe(setLabel);

		bindings[0] = getDataBindingContext().bindValue(value, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy() {

				@Override
				public Object convert(Object value) { // target to model
					try {
						return getModelValue().getValue();
					} catch (final DatabindingFailedException ex) {
						getReportService().report(new DatabindingFailedReport(ex));
						return null;
					}
				}
			}), new UpdateValueStrategy() {// model to target
				@Override
				public Object convert(Object value) {
					updateChangeListener((EObject) value);
					return getText(value);
				}
			});

		final IObservableValue imageValue = WidgetProperties.image().observe(imageLabel);
		bindings[1] = getDataBindingContext().bindValue(imageValue, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getImage(value);
				}
			});

		final ISWTObservableValue setLabelTooltip = WidgetProperties.tooltipText().observe(setLabel);
		bindings[2] = getDataBindingContext().bindValue(
			setLabelTooltip,
			getModelValue(),
			withPreSetValidation(new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					if (!EObject.class.isInstance(value)) {
						return ""; //$NON-NLS-1$
					}
					final EObject eObject = EObject.class.cast(value);
					final Resource resource = eObject.eResource();
					if (!VViewResourceImpl.class.isInstance(resource)) {
						return ""; //$NON-NLS-1$
					}
					final String id = VViewResourceImpl.class.cast(resource).getID(eObject);
					return MessageFormat.format("UUID: {0}", id); //$NON-NLS-1$
				}
			});
		return bindings;
	}

	private Object getImage(Object value) {
		final Object image = adapterFactoryItemDelegator.getImage(value);
		return SWTImageHelper.getImage(image);
	}

	// TODO this whole method is ugly as it has to many dependencies, the generating of the text should be delegated to
	// some service
	// BEGIN COMPLEX CODE
	private Object getText(Object object) {
		if (ToolingModeUtil.isSegmentToolingEnabled()) {
			return getTextForSegments((VDomainModelReference) object);
		}

		VFeaturePathDomainModelReference modelReference = (VFeaturePathDomainModelReference) object;
		if (VTableDomainModelReference.class.isInstance(modelReference)) {
			VTableDomainModelReference tableRef = VTableDomainModelReference.class.cast(modelReference);
			while (tableRef.getDomainModelReference() != null
				&& VTableDomainModelReference.class.isInstance(tableRef.getDomainModelReference())) {
				tableRef = VTableDomainModelReference.class.cast(tableRef.getDomainModelReference());
			}
			modelReference = (VFeaturePathDomainModelReference) tableRef.getDomainModelReference();
		}
		if (modelReference == null) {
			return null;
		}
		final EStructuralFeature value = modelReference.getDomainModelEFeature();

		String className = ""; //$NON-NLS-1$
		final String attributeName = " -> " + adapterFactoryItemDelegator.getText(value); //$NON-NLS-1$
		String referencePath = ""; //$NON-NLS-1$

		for (final EReference ref : modelReference.getDomainModelEReferencePath()) {
			if (className.isEmpty()) {
				className = ref.getEContainingClass().getName();
			}
			referencePath = referencePath + " -> " + adapterFactoryItemDelegator.getText(ref); //$NON-NLS-1$
		}
		if (className.isEmpty() && modelReference.getDomainModelEFeature() != null
			&& modelReference.getDomainModelEFeature().getEContainingClass() != null) {
			className = modelReference.getDomainModelEFeature().getEContainingClass().getName();
		}

		final String linkText = className + referencePath + attributeName;
		if (linkText.equals(" -> ")) { //$NON-NLS-1$
			return null;
		}
		return linkText;
	}
	// END COMPLEX CODE

	/**
	 * Provides a label text for a segment based DMR.
	 */
	private String getTextForSegments(VDomainModelReference dmr) {
		final EList<VDomainModelReferenceSegment> segments = dmr.getSegments();
		if (segments.isEmpty()) {
			return adapterFactoryItemDelegator.getText(dmr);
		}

		String attributeType = null;
		final EClass rootEClass = getDmrRootEClass();
		try {
			final IEMFValueProperty valueProperty = emfFormsDatabindingEMF.getValueProperty(
				dmr, rootEClass);
			attributeType = valueProperty.getStructuralFeature().getEType().getName();
		} catch (final DatabindingFailedException ex) {
			// TODO handle?
		}

		final String className = rootEClass.getName();
		String attributeName = " -> " + adapterFactoryItemDelegator.getText(segments.get(segments.size() - 1)); //$NON-NLS-1$
		if (attributeType != null && !attributeType.isEmpty()) {
			attributeName += " : " + attributeType; //$NON-NLS-1$
		}
		String referencePath = ""; //$NON-NLS-1$

		for (int i = 0; i < segments.size() - 1; i++) {
			referencePath = referencePath + " -> " //$NON-NLS-1$
				+ adapterFactoryItemDelegator.getText(segments.get(i));
		}

		final String linkText = className + referencePath + attributeName;
		if (linkText.equals(" -> ")) { //$NON-NLS-1$
			return null;
		}
		return linkText;
	}

	private void updateChangeListener(final EObject value) {
		if (modelElementChangeListener != null) {
			if (modelElementChangeListener.getTarget().equals(value)) {
				return;
			}
			modelElementChangeListener.remove();
			modelElementChangeListener = null;
		}

		if (value == null) {
			if (stackLayout.topControl != unsetLabel) {
				stackLayout.topControl = unsetLabel;
				mainComposite.layout();
			}

		} else {
			if (stackLayout.topControl != contentSetComposite) {
				stackLayout.topControl = contentSetComposite;
				mainComposite.layout();
			}

			modelElementChangeListener = new ECPModelElementChangeListener(value) {

				@Override
				public void onChange(Notification notification) {
					Display.getDefault().syncExec(() -> getDataBindingContext().updateTargets());
				}
			};
		}
	}

	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		final IObservableValue<?> observableValue = getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		eObject = (EObject) ((IObserving) observableValue).getObserved();
		structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		final Composite containerComposite = new Composite(parent, SWT.NONE);
		containerComposite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().applyTo(containerComposite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(containerComposite);

		parentComposite = new Composite(containerComposite, SWT.NONE);
		parentComposite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(4).spacing(0, 0).equalWidth(false)
			.applyTo(parentComposite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(parentComposite);

		stackLayout = new StackLayout();

		mainComposite = new Composite(parentComposite, SWT.NONE);
		mainComposite.setBackground(parentComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(mainComposite);
		mainComposite.setLayout(stackLayout);

		unsetLabel = new Label(mainComposite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(unsetLabel);
		unsetLabel.setText(
			LocalizationServiceHelper.getString(DomainModelReferenceControlSWTRenderer.class, "LinkControl_NotSet")); //$NON-NLS-1$
		unsetLabel.setBackground(mainComposite.getBackground());
		unsetLabel.setForeground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		unsetLabel.setAlignment(SWT.CENTER);

		contentSetComposite = new Composite(mainComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(contentSetComposite);
		contentSetComposite.setBackground(mainComposite.getBackground());
		imageLabel = new Label(contentSetComposite, SWT.NONE);
		imageLabel.setBackground(contentSetComposite.getBackground());
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).hint(17, SWT.DEFAULT)
			.applyTo(imageLabel);
		setLabel = new Label(contentSetComposite, SWT.NONE);
		setLabel.setBackground(contentSetComposite.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(setLabel);

		if (eObject.eIsSet(structuralFeature)) {
			stackLayout.topControl = contentSetComposite;
		} else {
			stackLayout.topControl = unsetLabel;
		}

		createButtons(parentComposite);

		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		return containerComposite;
	}

	/**
	 * @param parentComposite
	 * @throws DatabindingFailedException
	 */
	private void createButtons(Composite composite) throws DatabindingFailedException {
		final Button unsetBtn = createButtonForAction(new DeleteReferenceAction(getEditingDomain(eObject), eObject,
			structuralFeature, null), composite);
		unsetBtn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final Command setCommand = SetCommand.create(getEditingDomain(eObject), eObject, structuralFeature,
					SetCommand.UNSET_VALUE);
				getEditingDomain(eObject).getCommandStack().execute(setCommand);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});

		final Button setBtn = createButtonForAction(new NewReferenceAction(getEditingDomain(eObject), eObject,
			structuralFeature, emfFormsEditSupport, getEMFFormsLabelProvider(), null, getReportService(), getVElement()
				.getDomainModelReference(),
			getViewModelContext().getDomainModel()), composite); // getViewModelContext().getService(ReferenceService.class)
		setBtn.addSelectionListener(new SelectionAdapterExtension(setLabel, getModelValue(), getViewModelContext(),
			getDataBindingContext(), (EReference) structuralFeature));

	}

	/**
	 * A helper method which creates a button for an action on a composite.
	 *
	 * @param action the action to create a button for
	 * @param composite the composite to create the button onto
	 * @return the created button
	 */
	protected Button createButtonForAction(final Action action, final Composite composite) {
		final Button selectButton = new Button(composite, SWT.PUSH);
		selectButton.setImage(Activator.getImage(action));
		selectButton.setEnabled(true);
		selectButton.setToolTipText(action.getToolTipText());
		return selectButton;
	}

	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper.getString(getClass(), "LinkControl_NoLinkSetClickToSetLink"); //$NON-NLS-1$
	}

	/**
	 * Create a new segment based domain model reference and set it in the <code>reference</code> of the given
	 * <code>container</code> object.
	 *
	 * @param container The EObject which will contain the new domain model reference
	 * @param reference The EReference which the new domain model reference will be set in
	 */
	protected void addNewSegmentDmr(EObject container, EReference reference) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.addNewModelElements(eObject, (EReference) structuralFeature, false);
	}

	/**
	 * Edits the existing DMR set in the <code>reference</code> of the given
	 * <code>container</code> object.
	 *
	 * @param container The EObject which will contain the new domain model reference
	 * @param reference The EReference which contains the current new domain model reference
	 * @param dmr The domain model reference to edit
	 */
	protected void editSegmentDmr(EObject container, EReference reference, VDomainModelReference dmr) {
		final ReferenceService referenceService = getViewModelContext().getService(ReferenceService.class);
		referenceService.openInNewContext(dmr);
	}

	/**
	 * Returns the root EClass of the domain model reference.
	 *
	 * @return the DMR's root EClass.
	 */
	protected EClass getDmrRootEClass() {
		return Helper.getRootEClass(getViewModelContext().getDomainModel());
	}

	@Override
	protected void rootDomainModelChanged() throws DatabindingFailedException {
		final IObservableValue<?> observableValue = getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		try {
			eObject = (EObject) ((IObserving) observableValue).getObserved();
		} finally {
			observableValue.dispose();
		}

		super.rootDomainModelChanged();
	}

	/** SelectionAdapter for the set button. */
	private class SelectionAdapterExtension extends SelectionAdapter {

		private final EReference eReference;

		SelectionAdapterExtension(Label label, IObservableValue<?> modelValue, ViewModelContext viewModelContext,
			DataBindingContext dataBindingContext, EReference eReference) {
			this.eReference = eReference;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final EClass eclass = getDmrRootEClass();

			VDomainModelReference reference = null;
			if (VControl.class.isInstance(eObject)) {
				reference = VControl.class.cast(eObject).getDomainModelReference();
			} else if (VLabel.class.isInstance(eObject)) {
				reference = VLabel.class.cast(eObject).getDomainModelReference();
			} else if (eObject instanceof LeafCondition) {
				reference = LeafCondition.class.cast(eObject).getDomainModelReference();
			}

			if (ToolingModeUtil.isSegmentToolingEnabled()) {
				if (reference == null) {
					addNewSegmentDmr(eObject, eReference);
				} else {
					editSegmentDmr(eObject, eReference, reference);
				}
			} else {
				final Collection<EClass> classes = EMFUtils.getSubClasses(eReference.getEReferenceType());
				// Don't allow to create a plain DMR legacy mode
				classes.remove(VViewPackage.Literals.DOMAIN_MODEL_REFERENCE);

				final CreateDomainModelReferenceWizard wizard = new CreateDomainModelReferenceWizard(
					eObject, structuralFeature, getEditingDomain(eObject), eclass,
					reference == null ? "New Reference Element" : "Configure " + reference.eClass().getName(), //$NON-NLS-1$ //$NON-NLS-2$
					LocalizationServiceHelper.getString(DomainModelReferenceControlSWTRenderer.class,
						"NewModelElementWizard_WizardTitle_AddModelElement"), //$NON-NLS-1$
					LocalizationServiceHelper.getString(DomainModelReferenceControlSWTRenderer.class,
						"NewModelElementWizard_PageTitle_AddModelElement"), //$NON-NLS-1$
					LocalizationServiceHelper.getString(DomainModelReferenceControlSWTRenderer.class,
						"NewModelElementWizard_PageDescription_AddModelElement"), //$NON-NLS-1$
					reference);

				final SelectionComposite<TreeViewer> helper = CompositeFactory.getSelectModelClassComposite(
					new HashSet<EPackage>(),
					new HashSet<EPackage>(), classes);
				wizard.setCompositeProvider(helper);
				new WizardDialog(Display.getDefault().getActiveShell(), wizard).open();
			}

		}
	}
}
