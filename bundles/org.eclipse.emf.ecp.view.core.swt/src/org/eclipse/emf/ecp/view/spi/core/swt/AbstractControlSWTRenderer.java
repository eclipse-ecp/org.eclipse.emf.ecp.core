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

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.swt.util.SWTValidationHelper;
import org.eclipse.emf.ecp.view.internal.core.swt.Activator;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
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

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public AbstractControlSWTRenderer(VCONTROL vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private ComposedAdapterFactory composedAdapterFactory;
	private DataBindingContext dataBindingContext;
	private IObservableValue modelValue;
	private final WritableValue value = new WritableValue();
	private DomainModelReferenceChangeListener domainModelReferenceChangeListener;

	@Override
	protected void postInit() {
		super.postInit();
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			composedAdapterFactory);
		domainModelReferenceChangeListener = new DomainModelReferenceChangeListener() {

			@Override
			public void notifyChange() {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						if (!value.isDisposed()) {
							updateControl();
						}
					}
				});
			}
		};
		getVElement().getDomainModelReference().getChangeListener().add(domainModelReferenceChangeListener);
		updateControl();
	}

	@Override
	protected void dispose() {
		if (getVElement().getDomainModelReference() != null) {
			getVElement().getDomainModelReference().getChangeListener().remove(domainModelReferenceChangeListener);
		}

		domainModelReferenceChangeListener = null;
		if (value != null) {
			value.dispose();
		}

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
		return SWTValidationHelper.INSTANCE
			.getValidationBackgroundColor(severity, getVElement(), getViewModelContext());
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link Setting}.
	 *
	 * @param setting the {@link Setting} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 */
	protected final IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		if (setting == null) {
			return null;
		}
		final IItemPropertyDescriptor descriptor = adapterFactoryItemDelegator.getPropertyDescriptor(
			setting.getEObject(),
			setting.getEStructuralFeature());
		return descriptor;
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
	 * Returns an {@link IObservableValue} based on the provided {@link Setting}.
	 *
	 * @param setting the {@link Setting} to get the {@link IObservableValue} for
	 * @return the {@link IObservableValue}
	 */
	protected final IObservableValue getModelValue(final Setting setting) {
		if (modelValue == null) {

			modelValue = EMFEditProperties.value(getEditingDomain(setting), setting.getEStructuralFeature())
				.observeDetail(
					value);
			// modelValue = EMFEditObservables.observeValue(getEditingDomain(setting),
			// setting.getEObject(), setting.getEStructuralFeature());
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
		return AdapterFactoryEditingDomain.getEditingDomainFor(setting.getEObject());
	}

	/**
	 * Create the {@link Control} displaying the label of the current {@link VControl}.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the created {@link Control} or null
	 * @throws NoPropertyDescriptorFoundExeption thrown if the {@link org.eclipse.emf.ecore.EStructuralFeature
	 *             EStructuralFeature} of the {@link VControl} doesn't have a registered {@link IItemPropertyDescriptor}
	 */
	protected final Control createLabel(final Composite parent)
		throws NoPropertyDescriptorFoundExeption {
		Label label = null;
		labelRender: if (getVElement().getLabelAlignment() == LabelAlignment.LEFT) {
			if (!getVElement().getDomainModelReference().getIterator().hasNext()) {
				break labelRender;
			}
			final Setting setting = getVElement().getDomainModelReference().getIterator().next();
			if (setting == null) {
				break labelRender;
			}
			final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(setting);

			if (itemPropertyDescriptor == null) {
				throw new NoPropertyDescriptorFoundExeption(setting.getEObject(), setting.getEStructuralFeature());
			}

			label = new Label(parent, SWT.NONE);
			label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
			label.setBackground(parent.getBackground());
			String extra = ""; //$NON-NLS-1$
			final VTMandatoryStyleProperty mandatoryStyle = getMandatoryStyle();
			if (mandatoryStyle.isHighliteMandatoryFields() && setting.getEStructuralFeature().getLowerBound() > 0) {
				extra = mandatoryStyle.getMandatoryMarker();
			}
			final String labelText = itemPropertyDescriptor.getDisplayName(setting.getEObject());
			if (labelText != null && labelText.trim().length() != 0) {
				label.setText(labelText + extra);
				label.setToolTipText(itemPropertyDescriptor.getDescription(setting.getEObject()));
			}

		}
		return label;
	}

	private VTMandatoryStyleProperty getMandatoryStyle() {
		final VTViewTemplateProvider vtViewTemplateProvider = Activator.getDefault().getVTViewTemplateProvider();
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

	private void updateControl() {
		final Iterator<Setting> settings = getVElement().getDomainModelReference().getIterator();
		if (settings.hasNext()) {
			value.setValue(settings.next().getEObject());
			applyEnable();
		} else {
			value.setValue(null);
			for (final Entry<SWTGridCell, Control> entry : getControls().entrySet()) {
				setControlEnabled(entry.getKey(), entry.getValue(), false);

			}
		}
	}
}
