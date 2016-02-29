/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Philip Langer - bug fix 460968
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.reference;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.SWTImageHelper;
import org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.AddReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * This class defines a Control which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature
 * EStructuralFeature}s which have a reference.
 *
 * @author Eugen Neufeld
 *
 */
public class LinkControl extends SingleControl {

	private Composite linkComposite;

	private Link hyperlink;

	private Label imageHyperlink;

	/**
	 * The {@link ComposedAdapterFactory} used by the control.
	 */
	private ComposedAdapterFactory composedAdapterFactory;

	private ECPModelElementChangeListener modelElementChangeListener;

	private Label unsetLabel;

	private StackLayout stackLayout;

	private Composite mainComposite;

	private Button[] buttons;
	/**
	 * The {@link AdapterFactoryItemDelegator} used by this control.
	 */
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;

	@Override
	protected void fillControlComposite(Composite composite) {
		int numColumns = 1 + getNumButtons();
		if (isEmbedded()) {
			numColumns = 1;
		}
		if (!isEmbedded() && getFirstStructuralFeature().isUnsettable()) {
			numColumns++;
		}
		final Composite parent = new Composite(composite, SWT.NONE);
		parent.setBackground(composite.getBackground());
		GridLayoutFactory.fillDefaults().numColumns(numColumns).spacing(0, 0).equalWidth(false).applyTo(parent);
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(parent);
		mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setBackground(parent.getBackground());
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(mainComposite);

		stackLayout = new StackLayout();
		mainComposite.setLayout(stackLayout);

		unsetLabel = new Label(mainComposite, SWT.NONE);
		unsetLabel.setText(LocalizationServiceHelper.getString(getClass(), ReferenceMessageKeys.LinkControl_NotSet));
		unsetLabel.setBackground(mainComposite.getBackground());
		unsetLabel.setForeground(getSystemColor(SWT.COLOR_DARK_GRAY));
		unsetLabel.setAlignment(SWT.CENTER);

		linkComposite = new Composite(mainComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(linkComposite);
		linkComposite.setBackground(mainComposite.getBackground());

		createHyperlink();
		GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).applyTo(linkComposite);
		if (getFirstSetting().isSet()) {
			stackLayout.topControl = linkComposite;
		} else {
			stackLayout.topControl = unsetLabel;
		}
		if (!isEmbedded()) {
			buttons = createButtons(parent);
		}
	}

	/**
	 *
	 * @return number of buttons added by the link control.
	 */
	protected int getNumButtons() {
		return 3;
	}

	/**
	 * Creates the buttons to delete a reference, add one to an existing and add a new element to be referenced.
	 *
	 * @param composite the {@link Composite} to place the buttons on
	 * @return An array of buttons
	 */
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[3];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new DeleteReferenceAction(getEditingDomain(getFirstSetting()), setting,
			getService(ReferenceService.class)), composite);
		buttons[1] = createButtonForAction(new AddReferenceAction(getEditingDomain(getFirstSetting()), setting,
			getItemPropertyDescriptor(setting), getService(ReferenceService.class)), composite);
		buttons[2] = createButtonForAction(new NewReferenceAction(getEditingDomain(getFirstSetting()), setting,
			Activator.getDefault().getEMFFormsEditSupport(), Activator.getDefault().getEMFFormsLabelProvider(),
			getService(ReferenceService.class), Activator.getDefault().getReportService(), getDomainModelReference(),
			getViewModelContext().getDomainModel()), composite);
		return buttons;
	}

	private void createHyperlink() {
		setComposedAdapterFactory(new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) }));
		setAdapterFactoryItemDelegator(new AdapterFactoryItemDelegator(getComposedAdapterFactory()));
		// adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(composedAdapterFactory);
		// shortLabelProvider = new ShortLabelProvider(composedAdapterFactory);

		imageHyperlink = new Label(linkComposite, SWT.NONE);
		imageHyperlink.setBackground(linkComposite.getBackground());

		hyperlink = new Link(linkComposite, SWT.NONE);
		hyperlink.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_reference"); //$NON-NLS-1$
		hyperlink.setBackground(linkComposite.getBackground());
		hyperlink.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				super.widgetDefaultSelected(e);
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				linkClicked((EObject) getModelValue().getValue());
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
		getService(ReferenceService.class).openInNewContext(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEditable(boolean isEditable) {
		if (!isEmbedded()) {
			for (final Button button : buttons) {
				button.setVisible(isEditable);
			}
		}
		mainComposite.getParent().layout();
	}

	@Override
	public Binding bindValue() {

		final IObservableValue value = WidgetProperties.text().observe(hyperlink);
		getDataBindingContext().bindValue(value, getModelValue(), createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					updateChangeListener((EObject) value);
					return "<a>" + getLinkText(value) + "</a>"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			});

		final IObservableValue tooltipValue = WidgetProperties.tooltipText().observe(hyperlink);
		getDataBindingContext().bindValue(tooltipValue, getModelValue(),
			createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getLinkText(value);
				}
			});

		final IObservableValue imageValue = WidgetProperties.image().observe(imageHyperlink);
		getDataBindingContext().bindValue(imageValue, getModelValue(),
			new UpdateValueStrategy(UpdateValueStrategy.POLICY_NEVER),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return getImage(value);
				}
			});

		final IObservableValue deleteButtonEnablement = WidgetProperties.enabled().observe(getDeleteButton());
		getDataBindingContext().bindValue(deleteButtonEnablement, getModelValue(),
			createValueExtractingUpdateStrategy(),
			new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return value != null;
				}
			});

		return null;
	}

	private UpdateValueStrategy createValueExtractingUpdateStrategy() {
		return new UpdateValueStrategy() {
			@Override
			public Object convert(Object value) {
				return getModelValue().getValue();
			}
		};
	}

	/**
	 * Returns the image to be used for the given linked {@code value}.
	 *
	 * @param value the value
	 * @return The image.
	 */
	protected Object getImage(Object value) {
		final Object image = getAdapterFactoryItemDelegator().getImage(value);
		return SWTImageHelper.getImage(image);
	}

	/**
	 * Returns the link text to be used for the given linked {@code value}.
	 *
	 * @param value the value
	 * @return The link text.
	 */
	protected Object getLinkText(Object value) {
		final String linkName = getAdapterFactoryItemDelegator().getText(value);
		return linkName == null ? "" : linkName; //$NON-NLS-1$
	}

	/**
	 * Returns the delete button of this control.
	 *
	 * @return The delete button of this control.
	 */
	protected Button getDeleteButton() {
		return buttons[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#updateValidationColor(org.eclipse.swt.graphics.Color)
	 */
	@Override
	protected void updateValidationColor(Color color) {
		if (hyperlink != null) {
			hyperlink.setBackground(color);
		}
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
	public void dispose() {
		// adapterFactoryItemDelegator.dispose();
		getComposedAdapterFactory().dispose();
		// shortLabelProvider.dispose();
		if (modelElementChangeListener != null) {
			modelElementChangeListener.remove();
		}
		hyperlink.dispose();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper
			.getString(getClass(), ReferenceMessageKeys.LinkControl_NoLinkSetClickToSetLink);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.controls.SingleControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), ReferenceMessageKeys.LinkControl_UnsetLink);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getControlForTooltip()
	 */
	@Override
	protected Control[] getControlsForTooltip() {
		// return new Control[] { hyperlink, imageHyperlink };
		return new Control[0];
	}

	/**
	 * @return the adapterFactoryItemDelegator
	 */
	public AdapterFactoryItemDelegator getAdapterFactoryItemDelegator() {
		return adapterFactoryItemDelegator;
	}

	/**
	 * @param adapterFactoryItemDelegator the adapterFactoryItemDelegator to set
	 */
	public void setAdapterFactoryItemDelegator(AdapterFactoryItemDelegator adapterFactoryItemDelegator) {
		this.adapterFactoryItemDelegator = adapterFactoryItemDelegator;
	}

	/**
	 * @return the composedAdapterFactory
	 */
	public ComposedAdapterFactory getComposedAdapterFactory() {
		return composedAdapterFactory;
	}

	/**
	 * @param composedAdapterFactory the composedAdapterFactory to set
	 */
	public void setComposedAdapterFactory(ComposedAdapterFactory composedAdapterFactory) {
		this.composedAdapterFactory = composedAdapterFactory;
	}
}
