/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import java.text.MessageFormat;
import java.util.List;

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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.view.internal.editor.handler.AdvancedCreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.internal.editor.handler.FeatureSegmentGenerator;
import org.eclipse.emf.ecp.view.internal.editor.handler.SegmentGenerator;
import org.eclipse.emf.ecp.view.internal.editor.handler.SimpleCreateDomainModelReferenceWizard;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.SegmentIdeDescriptor;
import org.eclipse.emf.ecp.view.spi.label.model.VLabel;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.util.SegmentResolvementUtil;
import org.eclipse.emf.ecp.view.spi.model.util.VViewResourceImpl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.Wizard;
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

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService The {@link ReportService}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	@Inject
	public DomainModelReferenceControlSWTRenderer(VControl vElement, ViewModelContext viewContext,
		ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsLabelProvider emfFormsLabelProvider, VTViewTemplateProvider vtViewTemplateProvider,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createBindings(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
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
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
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

	private Object getText(Object object) {
		final VDomainModelReference modelReference = (VDomainModelReference) object;
		if (modelReference == null) {
			return null;
		}
		final EList<VDomainModelReferenceSegment> segments = modelReference.getSegments();
		if (segments.isEmpty()) {
			return adapterFactoryItemDelegator.getText(object);
		}

		final List<EStructuralFeature> featurePath = SegmentResolvementUtil
			.resolveSegmentsToFeatureList(segments, Helper.getRootEClass(modelReference));
		if (segments.size() != featurePath.size()) {
			return adapterFactoryItemDelegator.getText(object);
		}

		final EStructuralFeature attributeFeature = featurePath.get(featurePath.size() - 1);
		final String className = Helper.getRootEClass(modelReference).getName();
		final String attributeName = " -> " + adapterFactoryItemDelegator.getText(segments.get(segments.size() - 1)) //$NON-NLS-1$
			+ " : " + attributeFeature.getEType().getName(); //$NON-NLS-1$
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
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							getDataBindingContext().updateTargets();
						}
					});
				}
			};
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer#createSWTControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		final IObservableValue observableValue = Activator.getDefault().getEMFFormsDatabinding()
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
			getDataBindingContext(), structuralFeature));

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTRenderer#getUnsetText()
	 */
	@Override
	protected String getUnsetText() {
		return LocalizationServiceHelper.getString(getClass(), "LinkControl_NoLinkSetClickToSetLink"); //$NON-NLS-1$
	}

	/**
	 * Returns the {@link SegmentGenerator} that is used by the {@link SimpleCreateDomainModelReferenceWizard} to
	 * generate segments from a selected {@link EStructuralFeatureSelectionValidator}.
	 * <p>
	 * Can be overwritten by subclasses to change which segments are generated.
	 *
	 * @return The {@link SegmentGenerator} to use
	 */
	protected SegmentGenerator getSegmentGenerator() {
		return new FeatureSegmentGenerator();
	}

	/**
	 * Returns the {@link EStructuralFeatureSelectionValidator} that is used by the
	 * {@link SimpleCreateDomainModelReferenceWizard} to validate whether a selected {@link EStructuralFeature} is a
	 * valid selection.
	 * <p>
	 * Can be overwritten by subclasses to change structural features are considered a valid selection.
	 *
	 * @return The {@link EStructuralFeatureSelectionValidator} to use
	 */
	protected EStructuralFeatureSelectionValidator getSelectionValidator() {
		return new EStructuralFeatureSelectionValidator() {

			@Override
			public String isValid(EStructuralFeature structuralFeature) {
				return null; // null means the selection is valid
			}
		};
	}

	/**
	 * Returns whether the display restrictions defined by {@link SegmentIdeDescriptor SegmentIdeDescriptors} are
	 * ignored by the dmr creation wizard.
	 * <p>
	 * Can be overwritten by subclasses to select the desired behavior.
	 *
	 * @return <code>true</code> if the restrictions are ignored, <code>false</code> otherwise
	 */
	protected boolean isIgnoreSegmentIdeRestriction() {
		return false;
	}

	/**
	 * This method can be used to restrict the type of the last segment in the advanced dmr creation mode of the dmr
	 * creation wizard. <br/>
	 * If this method returns a type, the wizard will not finish as along as the last segment's type
	 * does not match the returned type.</br>
	 * If this method returns <strong>null</strong>, the last segment's type is not restricted.
	 * <p>
	 * Can be overwritten by subclasses to set a mandatory type for the last segment.<br>
	 * <strong>Important: </strong> This does not influence the last segment's type in simple editing mode. This can be
	 * done by providing an appropriate {@link SegmentGenerator} by overwriting {@link #getSegmentGenerator()}.
	 *
	 * @return the last segment's type or <strong>null</strong> if there is no restriction.
	 */
	protected EClass getLastSegmentType() {
		return null;
	}

	/** SelectionAdapter for the set button. */
	private class SelectionAdapterExtension extends SelectionAdapter {

		private final EStructuralFeature eStructuralFeature;

		SelectionAdapterExtension(Label label, IObservableValue modelValue, ViewModelContext viewModelContext,
			DataBindingContext dataBindingContext, EStructuralFeature eStructuralFeature) {
			this.eStructuralFeature = eStructuralFeature;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			final EClass eclass = Helper.getRootEClass(getViewModelContext().getDomainModel());

			VDomainModelReference reference = null;
			if (VControl.class.isInstance(eObject)) {
				reference = VControl.class.cast(eObject).getDomainModelReference();
			} else if (VLabel.class.isInstance(eObject)) {
				reference = VLabel.class.cast(eObject).getDomainModelReference();
			}

			final Wizard wizard = new AdvancedCreateDomainModelReferenceWizard(eObject,
				eStructuralFeature, getEditingDomain(eObject), eclass,
				reference == null ? "New Domain Model Reference" : "Configure Domain Model Reference", //$NON-NLS-1$ //$NON-NLS-2$
				reference, getSelectionValidator(), getSegmentGenerator(), getLastSegmentType(),
				isIgnoreSegmentIdeRestriction());
			// new SimpleCreateDomainModelReferenceWizard(eObject,
			// eStructuralFeature, getEditingDomain(eObject), eclass,
			// reference == null ? "New Domain Model Reference" : "Configure Domain Model Reference", //$NON-NLS-1$
			// //$NON-NLS-2$
			// reference, getSelectionValidator(), getSegmentGenerator());

			final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
			wd.setHelpAvailable(false);
			wd.open();
		}
	}
}
