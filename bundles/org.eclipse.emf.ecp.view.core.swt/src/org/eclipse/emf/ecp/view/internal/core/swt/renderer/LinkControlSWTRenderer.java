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
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * SWT Renderer for link controls.
 *
 * @author Alexandra Buzila
 *
 */
public class LinkControlSWTRenderer extends SimpleControlSWTControlSWTRenderer {

	private Composite mainComposite;
	private StackLayout stackLayout;
	private Label imageHyperlink;
	private Composite linkComposite;
	private Link hyperlink;
	private final EMFFormsLocalizationService localizationService;
	private Button newReferenceBtn;
	private Button addReferenceBtn;
	private Button deleteReferenceButton;
	private Label unsetLabel;
	private ReferenceService referenceService;
	private final ImageRegistryService imageRegistryService;
	private final EMFFormsLabelProvider emfFormsLabelProvider;
	private ECPModelElementChangeListener modelElementChangeListener;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;

	/**
	 * @param vElement the element to render
	 * @param viewContext the view model context
	 * @param reportService the report service
	 * @param emfFormsDatabinding the data binding service
	 * @param emfFormsLabelProvider the label provider
	 * @param vtViewTemplateProvider the view template provider
	 * @param localizationService the localization service
	 * @param imageRegistryService the image registry service
	 * @param emfFormsEditSupport the EMFFormsEditSupport
	 *
	 * @deprecated with 1.22
	 */
	@Deprecated
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public LinkControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService, EMFFormsEditSupport emfFormsEditSupport) {
		// CHECKSTYLE.ON: ParameterNumber
		this(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider,
			localizationService, imageRegistryService);
	}

	/**
	 * @param vElement the element to render
	 * @param viewContext the view model context
	 * @param reportService the report service
	 * @param emfFormsDatabinding the data binding service
	 * @param emfFormsLabelProvider the label provider
	 * @param vtViewTemplateProvider the view template provider
	 * @param localizationService the localization service
	 * @param imageRegistryService the image registry service
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public LinkControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.localizationService = localizationService;
		this.imageRegistryService = imageRegistryService;
		this.emfFormsLabelProvider = emfFormsLabelProvider;
	}

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {

		final IObservableValue<String> value = WidgetProperties.text().observe(hyperlink);
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			withPreSetValidation(createValueExtractingUpdateStrategy()),
			new UpdateValueStrategy<Object, String>() {
				@Override
				public String convert(Object value) {
					updateChangeListener((EObject) value);
					return "<a>" + getText(value) + "</a>"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			});

		final IObservableValue<String> tooltipValue = WidgetProperties.tooltipText().observe(hyperlink);
		final Binding tooltipBinding = getDataBindingContext().bindValue(tooltipValue, getModelValue(),
			withPreSetValidation(createValueExtractingUpdateStrategy()),
			new UpdateValueStrategy<Object, String>() {
				@Override
				public String convert(Object value) {
					return getText(value);
				}
			});

		final IObservableValue<Image> imageValue = WidgetProperties.image().observe(imageHyperlink);
		final Binding imageBinding = getDataBindingContext().bindValue(imageValue, getModelValue(),
			withPreSetValidation(new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER)),
			new UpdateValueStrategy<Object, Image>() {
				@Override
				public Image convert(Object value) {
					return getImage(value);
				}
			});

		final IObservableValue<Boolean> deleteButtonEnablement = WidgetProperties.enabled()
			.observe(deleteReferenceButton);
		final Binding deleteBinding = getDataBindingContext().bindValue(deleteButtonEnablement, getModelValue(),
			withPreSetValidation(createValueExtractingUpdateStrategy()),
			new UpdateValueStrategy<Object, Boolean>() {
				@Override
				public Boolean convert(Object value) {
					return value != null;
				}
			});

		return new Binding[] { binding, tooltipBinding, imageBinding, deleteBinding };
	}

	private UpdateValueStrategy createValueExtractingUpdateStrategy() {
		return new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				try {
					return getModelValue().getValue();
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
				}
				return value;
			}
		};
	}

	@Override
	protected Control createSWTControl(Composite parent) throws DatabindingFailedException {
		final IObservableValue observableValue = getEMFFormsDatabinding()
			.getObservableValue(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel());
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
		observableValue.dispose();

		final int numColumns = 1 + getNumButtons();

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(parent.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(numColumns).spacing(0, 0).equalWidth(false).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(composite);

		mainComposite = new Composite(composite, SWT.NONE);
		mainComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(mainComposite);

		stackLayout = new StackLayout();
		mainComposite.setLayout(stackLayout);

		unsetLabel = new Label(mainComposite, SWT.CENTER);
		unsetLabel.setText(getLocalizedString(MessageKeys.LinkControl_NotSet));

		unsetLabel.setBackground(mainComposite.getBackground());
		unsetLabel.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		unsetLabel.setAlignment(SWT.CENTER);

		linkComposite = new Composite(mainComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(linkComposite);
		linkComposite.setBackground(mainComposite.getBackground());

		createHyperlink();
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(linkComposite);

		if (eObject.eIsSet(structuralFeature)) {
			stackLayout.topControl = linkComposite;
		} else {
			stackLayout.topControl = unsetLabel;
		}
		createButtons(composite);
		return composite;
	}

	/**
	 * Called by {@link #createSWTControl(Composite)} in order to create the buttons.
	 *
	 * @param parent the parent composite
	 */
	protected void createButtons(Composite parent) {
		String elementDisplayName = null;
		try {
			elementDisplayName = emfFormsLabelProvider
				.getDisplayName(getVElement().getDomainModelReference(), getViewModelContext().getDomainModel())
				.getValue();
		} catch (final NoLabelFoundException ex) {
			getReportService().report(new AbstractReport(ex));
		}

		VTReferenceStyleProperty referenceStyle = RendererUtil.getStyleProperty(
			getVTViewTemplateProvider(), getVElement(), getViewModelContext(), VTReferenceStyleProperty.class);
		if (referenceStyle == null) {
			referenceStyle = getDefaultReferenceStyle();
		}

		EReference eReference = null;
		try {
			eReference = (EReference) getModelValue().getValueType();
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new AbstractReport(ex));
		}

		if (eReference != null) {
			if (eReference.isContainment()) {
				// Only allow to link existing elements in a containment reference if it was allowed in a reference
				// style property.
				if (referenceStyle.isShowLinkButtonForContainmentReferences()) {
					createAddReferenceButton(parent, elementDisplayName);
				}
				createNewReferenceButton(parent, elementDisplayName);
			} else {
				createAddReferenceButton(parent, elementDisplayName);
				// Only allow to create new elements in a cross reference if it was allowed in a reference style
				// property.
				if (referenceStyle.isShowCreateAndLinkButtonForCrossReferences()) {
					createNewReferenceButton(parent, elementDisplayName);
				}
			}
		}
		createDeleteReferenceButton(parent, elementDisplayName);
	}

	/**
	 * Creates and returns a default version of a {@link VTReferenceStyleProperty}.
	 *
	 * @return The default {@link VTReferenceStyleProperty}
	 */
	protected VTReferenceStyleProperty getDefaultReferenceStyle() {
		return VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
	}

	/**
	 * Called by {@link #createButtons(Composite)} to create the add existing reference button.
	 *
	 * @param parent the parent composite
	 * @param elementDisplayName the display name of the reference
	 */
	protected void createAddReferenceButton(Composite parent, String elementDisplayName) {
		addReferenceBtn = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(addReferenceBtn);
		addReferenceBtn.setImage(getAddReferenceButtonImage());
		addReferenceBtn.setToolTipText(getLocalizedString(MessageKeys.LinkControl_AddReference) + elementDisplayName);
		addReferenceBtn.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
					final EReference eReference = (EReference) getModelValue().getValueType();
					getReferenceService().addExistingModelElements(eObject, eReference);
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
				}
			}
		});
	}

	/**
	 * Returns the add reference image for button.
	 *
	 * @return the add reference image for button
	 */
	protected Image getAddReferenceButtonImage() {
		return getImage(getIconBundle(), "icons/reference.png"); //$NON-NLS-1$
	}

	/**
	 * Called by {@link #createButtons(Composite)} to create the create new reference button.
	 *
	 * @param parent the parent composite
	 * @param elementDisplayName the display name of the reference
	 */
	protected void createNewReferenceButton(Composite parent, String elementDisplayName) {
		newReferenceBtn = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(newReferenceBtn);
		newReferenceBtn.setImage(getNewReferenceButtonImage());
		newReferenceBtn
			.setToolTipText(getLocalizedString(MessageKeys.LinkControl_NewReference) + elementDisplayName);
		newReferenceBtn.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					final EObject eObject = (EObject) ((IObserving) getModelValue()).getObserved();
					final EReference eReference = (EReference) getModelValue().getValueType();
					getReferenceService().addNewModelElements(eObject, eReference, openNewReferenceInContext());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
				}
			}
		});
	}

	/**
	 * Returns the new reference image for button.
	 *
	 * @return the new reference image for button
	 */
	protected Image getNewReferenceButtonImage() {
		return getImage(getIconBundle(), "icons/set_reference.png"); // //$NON-NLS-1$
	}

	/**
	 * Whether a new reference should be opened in a new context. True to open in new context, false otherwise.
	 *
	 * @return true to open in new context, false otherwise
	 */
	protected boolean openNewReferenceInContext() {
		return true;
	}

	/**
	 * Called by {@link #createButtons(Composite)} to create the delete reference button.
	 *
	 * @param parent the parent composite
	 * @param elementDisplayName the display name of the reference
	 */
	protected void createDeleteReferenceButton(Composite parent, String elementDisplayName) {
		deleteReferenceButton = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(deleteReferenceButton);
		deleteReferenceButton.setImage(getDeleteReferenceButtonImage());
		deleteReferenceButton.setToolTipText(getLocalizedString(MessageKeys.LinkControl_DeleteReference));
		deleteReferenceButton.addSelectionListener(new DeleteSelectionAdapter());
	}

	/**
	 * Returns the image for delete reference button.
	 *
	 * @return the image for delete reference button
	 */
	protected Image getDeleteReferenceButtonImage() {
		return getImage(getIconBundle(), "icons/unset_reference.png");//$NON-NLS-1$
	}

	/**
	 * Returns an image to be displayed given the bundle and path in the bundle where icon file can be found.
	 * <p>
	 * The image found is not meant to be disposed by user.
	 * </p>
	 *
	 * @param bundle the bundle where the image file is located
	 * @param iconPath the path of the icon file in the bundle
	 * @return the image to be displayed.
	 */
	protected Image getImage(Bundle bundle, String iconPath) {
		return imageRegistryService != null ? imageRegistryService.getImage(bundle, iconPath) : null;
	}

	/**
	 * Returns the bundle where the icon file is located.
	 *
	 * @return the bundle where the icon file is located
	 */
	protected Bundle getIconBundle() {
		return FrameworkUtil.getBundle(LinkControlSWTRenderer.class);
	}

	/**
	 * Returns the link text to be used for the given linked {@code value}.
	 *
	 * @param value the value
	 * @return The link text.
	 * @throws DatabindingFailedException
	 * @throws NoLabelFoundException
	 */
	protected String getText(Object value) {
		final String linkName = adapterFactoryItemDelegator.getText(value);
		return linkName == null ? "" : linkName; //$NON-NLS-1$
	}

	/**
	 * Returns the image to be used for the given linked {@code value}.
	 *
	 * @param value the object for which the image is retrieved
	 * @return the image
	 */
	protected Image getImage(Object value) {
		if (value == null) {
			return null;
		}
		final Object imageDescription = adapterFactoryItemDelegator.getImage(value);
		if (imageDescription == null) {
			return null;
		}
		final Image image = org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper.getImage(imageDescription);
		return image;
	}

	private void createHyperlink() throws DatabindingFailedException {
		imageHyperlink = new Label(linkComposite, SWT.NONE);
		imageHyperlink.setBackground(linkComposite.getBackground());

		hyperlink = new Link(linkComposite, SWT.NONE);
		hyperlink.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_reference"); //$NON-NLS-1$
		hyperlink.setBackground(linkComposite.getBackground());
		hyperlink.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				super.widgetDefaultSelected(e);
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				try {
					linkClicked((EObject) getModelValue().getValue());
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
				}
			}

		});
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(hyperlink);
	}

	/**
	 * This code is called whenever the link of the link widget is clicked. You can overwrite this to change the
	 * behavior.
	 *
	 * @param value the EObject that is linked
	 */
	protected void linkClicked(EObject value) {
		final ReferenceService referenceService = getReferenceService();
		referenceService.openInNewContext(value);
	}

	/**
	 * @return the {@link ReferenceService}
	 */
	protected ReferenceService getReferenceService() {
		if (referenceService == null) {
			referenceService = getViewModelContext().getService(ReferenceService.class);
		}
		return referenceService;
	}

	private String getLocalizedString(String key) {
		return localizationService.getString(LinkControlSWTRenderer.class, key);
	}

	/**
	 * @return number of buttons added by the link control.
	 */
	protected int getNumButtons() {
		return 3;
	}

	@Override
	protected String getUnsetText() {
		return getLocalizedString(MessageKeys.LinkControl_NoLinkSetClickToSetLink);
	}

	@Override
	protected void postInit() {
		super.postInit();
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);
	}

	@Override
	protected void dispose() {
		if (modelElementChangeListener != null) {
			modelElementChangeListener.remove();
		}
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		super.dispose();
	}

	private void updateChangeListener(EObject value) {
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
			if (stackLayout.topControl != linkComposite) {
				stackLayout.topControl = linkComposite;
				mainComposite.layout();
			}

			modelElementChangeListener = new ECPModelElementChangeListener(value) {

				@Override
				public void onChange(Notification notification) {
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							getDataBindingContext().updateTargets();
							linkComposite.layout();
						}
					});
				}
			};
		}
	}

	@Override
	public void scrollToReveal() {
		if (canReveal(addReferenceBtn) && addReferenceBtn.isEnabled()) {
			scrollToReveal(addReferenceBtn);
		} else if (canReveal(newReferenceBtn) && newReferenceBtn.isEnabled()) {
			scrollToReveal(newReferenceBtn);
		} else {
			super.scrollToReveal();
		}
	}

	@Override
	protected void applyReadOnly() {
		super.applyReadOnly();
		updateButtonVisibility();
		if (isRenderingFinished()) {
			mainComposite.getParent().layout();
		}
	}

	/**
	 * Updates the visibility of 'add reference', 'new reference', and 'delete reference' buttons according to the bound
	 * input.
	 */
	protected void updateButtonVisibility() {
		final boolean isVisible = !getVElement().isEffectivelyReadonly();

		// Check for null because not all buttons might have been created
		if (addReferenceBtn != null) {
			addReferenceBtn.setVisible(isVisible);
			GridData.class.cast(addReferenceBtn.getLayoutData()).exclude = !isVisible;
		}
		if (newReferenceBtn != null) {
			newReferenceBtn.setVisible(isVisible);
			GridData.class.cast(newReferenceBtn.getLayoutData()).exclude = !isVisible;
		}
		if (deleteReferenceButton != null) {
			deleteReferenceButton.setVisible(isVisible);
			GridData.class.cast(deleteReferenceButton.getLayoutData()).exclude = !isVisible;
		}
	}

	/** Selection listener for the delete reference button. */
	class DeleteSelectionAdapter extends SelectionAdapter {
		@SuppressWarnings("unused")
		private static final long serialVersionUID = 1L;

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				final EReference reference = (EReference) getModelValue().getValueType();
				final EObject object = getViewModelContext().getDomainModel();
				if (reference.isContainment()) {
					if (askConfirmation(object)) {
						delete(object, reference);
					}
				} else {
					delete(object, reference);
				}
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new DatabindingFailedReport(ex));
			}
		}

		private void delete(EObject eObject, EReference reference) {
			final EditingDomain editingDomain = getEditingDomain(eObject);
			final Command removeCommand = SetCommand.create(editingDomain, eObject,
				reference, null);
			if (removeCommand.canExecute()) {
				editingDomain.getCommandStack().execute(removeCommand);
			}
		}

		private boolean askConfirmation(EObject eObject) {
			final String modelElementName = getText(eObject);
			final String question = getLocalizedString(MessageKeys.LinkControl_DeleteModelQuestion)
				+ modelElementName
				+ getLocalizedString(MessageKeys.LinkControl_QuestionMark);
			final MessageDialog dialog = new MessageDialog(
				null,
				getLocalizedString(MessageKeys.LinkControl_DeleteReferenceConfirmation),
				null,
				question,
				MessageDialog.QUESTION,
				new String[] {
					getLocalizedString(MessageKeys.LinkControl_DeleteReferenceYes),
					getLocalizedString(MessageKeys.LinkControl_DeleteReferenceNo) },
				0);

			boolean confirm = false;
			if (dialog.open() == Window.OK) {
				confirm = true;
			}
			return confirm;
		}
	}
}
