/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.core.swt;

import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RendererInitFailedReport;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.emfforms.spi.core.services.labelprovider.EMFFormsLabelProvider;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * Super class for all kinds of control renderer.
 *
 * @param <VCONTROL> the {@link VControl} of this renderer.
 * @author Eugen Neufeld
 *
 */
public abstract class AbstractControlSWTRenderer<VCONTROL extends VControl> extends AbstractSWTRenderer<VCONTROL> {

	private final EMFFormsDatabinding emfFormsDatabinding;
	private final EMFFormsLabelProvider emfFormsLabelProvider;
	private final VTViewTemplateProvider vtViewTemplateProvider;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsLabelProvider The {@link EMFFormsLabelProvider}
	 * @param reportService The {@link ReportService}
	 * @param vtViewTemplateProvider The {@link VTViewTemplateProvider}
	 */
	public AbstractControlSWTRenderer(VCONTROL vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsDatabinding emfFormsDatabinding, EMFFormsLabelProvider emfFormsLabelProvider,
		VTViewTemplateProvider vtViewTemplateProvider) {
		super(vElement, viewContext, reportService);
		this.emfFormsDatabinding = emfFormsDatabinding;
		this.emfFormsLabelProvider = emfFormsLabelProvider;
		this.vtViewTemplateProvider = vtViewTemplateProvider;
	}

	/**
	 * The {@link EMFFormsDatabinding} to use.
	 *
	 * @return The EMFFormsDatabinding
	 */
	protected EMFFormsDatabinding getEMFFormsDatabinding() {
		return emfFormsDatabinding;
	}

	/**
	 * The {@link EMFFormsLabelProvider} to use.
	 *
	 * @return The EMFFormsLabelProvider
	 */
	protected EMFFormsLabelProvider getEMFFormsLabelProvider() {
		return emfFormsLabelProvider;
	}

	/**
	 * The {@link VTViewTemplateProvider} to use.
	 *
	 * @return The VTViewTemplateProvider
	 */
	protected VTViewTemplateProvider getVTViewTemplateProvider() {
		return vtViewTemplateProvider;
	}

	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private ComposedAdapterFactory composedAdapterFactory;
	private DataBindingContext dataBindingContext;
	private IObservableValue modelValue;
	private IChangeListener modelValueChangeListener;

	// TODO is this needed?
	@Override
	protected void postInit() {
		super.postInit();
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		modelValueChangeListener = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						applyEnable();
					}
				});
			}
		};

		try {
			getModelValue().addChangeListener(modelValueChangeListener);
		} catch (final DatabindingFailedException ex) {
			getReportService().report(new RendererInitFailedReport(ex));
		}
		applyEnable();
	}

	@Override
	protected void dispose() {
		if (modelValue != null) {
			modelValue.removeChangeListener(modelValueChangeListener);
		}

		modelValueChangeListener = null;

		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
			composedAdapterFactory = null;
		}
		if (dataBindingContext != null) {
			dataBindingContext.dispose();
			dataBindingContext = null;
		}
		if (modelValue != null) {
			modelValue.dispose();
			modelValue = null;
		}
		super.dispose();
	}

	/**
	 * Returns the validation icon matching the given severity.
	 *
	 * @param severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the icon to be displayed, or <code>null</code> when no icon is to be displayed
	 */
	protected final Image getValidationIcon(int severity) {
		return SWTValidationHelper.INSTANCE.getValidationIcon(severity, getVElement(), getViewModelContext());
	}

	/**
	 * Returns the background color for a control with the given validation severity.
	 *
	 * @param severity severity the severity of the {@link org.eclipse.emf.common.util.Diagnostic}
	 * @return the color to be used as a background color
	 */
	protected final Color getValidationBackgroundColor(int severity) {
		// TODO add real dispose check
		if (modelValue == null) {
			return null;
		}
		return SWTValidationHelper.INSTANCE
			.getValidationBackgroundColor(severity, getVElement(), getViewModelContext());
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link Setting}.
	 *
	 * @param setting the {@link Setting} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 */
	@Deprecated
	protected final IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		return getItemPropertyDescriptor(setting.getEObject(), setting.getEStructuralFeature());
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link EObject} and {@link EStructuralFeature}.
	 *
	 * @param eObject The {@link EObject} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @param structuralFeature The {@link EStructuralFeature} to use for identifying the
	 *            {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 */
	@Deprecated
	protected final IItemPropertyDescriptor getItemPropertyDescriptor(EObject eObject,
		EStructuralFeature structuralFeature) {
		if (eObject == null || structuralFeature == null) {
			return null;
		}
		return adapterFactoryItemDelegator.getPropertyDescriptor(eObject, structuralFeature);
	}

	/**
	 * Creates a new {@link DataBindingContext}.
	 *
	 * @return a new {@link DataBindingContext} each time this method is called
	 */
	protected final DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	/**
	 * Returns an {@link IObservableValue} based on the control's domain model reference and domain model.
	 *
	 * @return the {@link IObservableValue}
	 * @throws DatabindingFailedException if the databinding of the domain model object fails.
	 */
	protected final IObservableValue getModelValue() throws DatabindingFailedException {
		final VDomainModelReference ref = getVElement().getDomainModelReference();
		final EObject eObject = getViewModelContext().getDomainModel();

		if (modelValue == null) {
			final EMFFormsDatabinding databindingService = getEMFFormsDatabinding();
			modelValue = databindingService.getObservableValue(ref, eObject);
		}
		return modelValue;
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link Setting}.
	 *
	 * @param setting the provided {@link Setting}
	 * @return the {@link EditingDomain} of this {@link Setting}
	 */
	protected final EditingDomain getEditingDomain(Setting setting) {
		return getEditingDomain(setting.getEObject());
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link EObject domain model}.
	 *
	 * @param domainModel The provided {@link EObject domain model}
	 * @return The {@link EditingDomain} of this {@link EObject domain model}
	 */
	protected final EditingDomain getEditingDomain(EObject domainModel) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(domainModel);
	}

	/**
	 * Create the {@link Control} displaying the label of the current {@link VControl}.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the created {@link Control} or null
	 */
	protected final Control createLabel(final Composite parent) {
		Label label = null;
		labelRender: if (getVElement().getLabelAlignment() == LabelAlignment.LEFT) {
			final VDomainModelReference domainModelReference = getVElement().getDomainModelReference();
			final EClass rootEClass = getViewModelContext().getDomainModel().eClass();
			final IValueProperty valueProperty;
			try {
				valueProperty = getEMFFormsDatabinding().getValueProperty(domainModelReference, rootEClass);
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				break labelRender;
			}

			final EMFFormsLabelProvider labelProvider = getEMFFormsLabelProvider();
			label = new Label(parent, SWT.NONE);
			label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
			label.setBackground(parent.getBackground());
			String extra = ""; //$NON-NLS-1$
			final VTMandatoryStyleProperty mandatoryStyle = getMandatoryStyle();
			final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
			if (mandatoryStyle.isHighliteMandatoryFields() && structuralFeature.getLowerBound() > 0) {
				extra = mandatoryStyle.getMandatoryMarker();
			}
			final EObject rootObject = getViewModelContext().getDomainModel();
			final String labelText = labelProvider.getDisplayName(domainModelReference, rootObject);
			if (labelText != null && labelText.trim().length() != 0) {
				label.setText(labelText + extra);
				label.setToolTipText(labelProvider.getDescription(domainModelReference, rootObject));
			}

		}
		return label;
	}

	/**
	 * Provides a localized string for a key.
	 *
	 * @param key The Key to get the value for
	 * @return The localized String
	 */
	protected String getLocalizedString(String key) {
		return LocalizationServiceHelper.getString(getClass(), key);
	}

	private VTMandatoryStyleProperty getMandatoryStyle() {
		if (vtViewTemplateProvider == null) {
			return getDefaultStyle();
		}
		final Set<VTStyleProperty> styleProperties = vtViewTemplateProvider
			.getStyleProperties(getVElement(), getViewModelContext());
		for (final VTStyleProperty styleProperty : styleProperties) {
			if (VTMandatoryStyleProperty.class.isInstance(styleProperty)) {
				return (VTMandatoryStyleProperty) styleProperty;
			}
		}
		return getDefaultStyle();
	}

	private VTMandatoryStyleProperty getDefaultStyle() {
		return VTMandatoryFactory.eINSTANCE.createMandatoryStyleProperty();
	}

	/**
	 * Creates a validation icon.
	 *
	 * @param composite the {@link Composite} to create onto
	 * @return the created Label
	 */
	protected final Label createValidationIcon(Composite composite) {
		final Label validationLabel = new Label(composite, SWT.NONE);
		validationLabel.setBackground(composite.getBackground());
		return validationLabel;
	}

}
