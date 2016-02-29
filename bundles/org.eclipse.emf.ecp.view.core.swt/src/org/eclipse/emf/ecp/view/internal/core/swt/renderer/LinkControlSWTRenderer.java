/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.OverlayImageDescriptor;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.view.internal.core.swt.MessageKeys;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
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
	private final EMFFormsEditSupport emfFormsEditSuppport;

	/**
	 * @param vElement the element to render
	 * @param viewContext the view model context
	 * @param reportService the report service
	 * @param emfFormsDatabinding the data binding service
	 * @param emfFormsLabelProvider the label provider
	 * @param vtViewTemplateProvider the view template provider
	 * @param localizationService the localization service
	 * @param imageRegistryService the image registry service
	 * @param emfFormsEditSuppport the edit support
	 */
	@Inject
	// CHECKSTYLE.OFF: ParameterNumber
	public LinkControlSWTRenderer(VControl vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider, EMFFormsLocalizationService localizationService,
		ImageRegistryService imageRegistryService, EMFFormsEditSupport emfFormsEditSuppport) {
		// CHECKSTYLE.ON: ParameterNumber
		super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider, vtViewTemplateProvider);
		this.localizationService = localizationService;
		this.imageRegistryService = imageRegistryService;
		this.emfFormsLabelProvider = emfFormsLabelProvider;
		this.emfFormsEditSuppport = emfFormsEditSuppport;
	}

	@Override
	protected Binding[] createBindings(Control control) throws DatabindingFailedException {

		final IObservableValue value = WidgetProperties.text().observe(hyperlink);
		final Binding binding = getDataBindingContext().bindValue(value, getModelValue(),
			createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					updateChangeListener((EObject) value);
					return "<a>" + getText(value) + "</a>"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			});

		final IObservableValue tooltipValue = WidgetProperties.tooltipText().observe(hyperlink);
		final Binding tooltipBinding = getDataBindingContext().bindValue(tooltipValue, getModelValue(),
			createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getText(value);
				}
			});

		final IObservableValue imageValue = WidgetProperties.image().observe(imageHyperlink);
		final Binding imageBinding = getDataBindingContext().bindValue(imageValue, getModelValue(),
			new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getImage(value);
				}
			});

		final IObservableValue deleteButtonEnablement = WidgetProperties.enabled().observe(deleteReferenceButton);
		final Binding deleteBinding = getDataBindingContext().bindValue(deleteButtonEnablement, getModelValue(),
			createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
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

	private void createButtons(Composite parent) {

		String elementDisplayName = null;
		try {
			elementDisplayName = (String) emfFormsLabelProvider.getDisplayName(getVElement().getDomainModelReference())
				.getValue();
		} catch (final NoLabelFoundException ex) {
			getReportService().report(new AbstractReport(ex));
		}

		addReferenceBtn = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(addReferenceBtn);
		addReferenceBtn
			.setImage(getAddReferenceButtonImage());
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

		newReferenceBtn = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(newReferenceBtn);
		newReferenceBtn
			.setImage(getNewReferenceButtonImage());
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
					getReferenceService().addNewModelElements(eObject, eReference);
				} catch (final DatabindingFailedException ex) {
					getReportService().report(new DatabindingFailedReport(ex));
				}
			}
		});

		deleteReferenceButton = new Button(parent, SWT.PUSH);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.CENTER, SWT.CENTER).applyTo(deleteReferenceButton);
		deleteReferenceButton
			.setImage(imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), "icons/unset_reference.png")); //$NON-NLS-1$
		deleteReferenceButton.setToolTipText(getLocalizedString(MessageKeys.LinkControl_DeleteReference));
		deleteReferenceButton.addSelectionListener(new DeleteSelectionAdapter());

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
		final String linkName = emfFormsEditSuppport.getText(getVElement().getDomainModelReference(),
			getViewModelContext().getDomainModel(), value);
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
		final Object imageDescription = emfFormsEditSuppport.getImage(getVElement().getDomainModelReference(),
			getViewModelContext().getDomainModel(), value);
		if (imageDescription == null) {
			return null;
		}
		@SuppressWarnings("restriction")
		final Image image = org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper.getImage(imageDescription);
		return image;
	}

	private Image getNewReferenceButtonImage() {
		final Image image = getImage(getViewModelContext().getDomainModel());
		if (image != null) {
			final Image addOverlay = imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()),
				"icons/add_overlay.png"); //$NON-NLS-1$

			final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image,
				ImageDescriptor.createFromImage(addOverlay),
				OverlayImageDescriptor.LOWER_RIGHT);
			return imageDescriptor.createImage();
		}
		// fallback
		return imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), "icons/set_reference.png"); //$NON-NLS-1$
	}

	private Image getAddReferenceButtonImage() {
		final Image image = getImage(getViewModelContext().getDomainModel());
		if (image != null) {
			final Image overlay = imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()),
				"icons/link_overlay.png"); //$NON-NLS-1$

			final OverlayImageDescriptor imageDescriptor = new OverlayImageDescriptor(image,
				ImageDescriptor.createFromImage(overlay),
				OverlayImageDescriptor.LOWER_RIGHT);
			return imageDescriptor.createImage();
		}
		// fallback
		return imageRegistryService.getImage(FrameworkUtil.getBundle(getClass()), "icons/reference.png"); //$NON-NLS-1$
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

	private ReferenceService getReferenceService() {
		if (referenceService == null) {
			referenceService = getViewModelContext().getService(ReferenceService.class);
		}
		return referenceService;
	}

	private String getLocalizedString(String key) {
		return localizationService.getString(getClass(), key);
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
