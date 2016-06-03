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
 * Stefan Dirix - Add ControlProcessorService
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.swt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.internal.custom.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPCustomControlChangeListener;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;

/**
 * Extend this class in order to provide an own implementation of an {@link ECPAbstractCustomControlSWT}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@SuppressWarnings("deprecation")
public abstract class ECPAbstractCustomControlSWT {
	/**
	 * Variant constant for indicating RAP controls.
	 *
	 * @since 1.3
	 */
	protected static final String CUSTOM_VARIANT = "org.eclipse.rap.rwt.customVariant"; //$NON-NLS-1$
	/**
	 * Constant for an validation error image.
	 */
	public static final int VALIDATION_ERROR_IMAGE = 0;
	/**
	 * Constant for an add image.
	 */
	public static final int ADD_IMAGE = 1;
	/**
	 * Constant for an delete image.
	 */
	public static final int DELETE_IMAGE = 2;
	/**
	 * Constant for a help image.
	 */
	public static final int HELP_IMAGE = 3;

	private final SWTCustomControlHelper swtHelper = new SWTCustomControlHelper();
	private ECPControlFactory controlFactory;
	private final Map<EStructuralFeature, ECPAbstractControl> controlMap = new LinkedHashMap<EStructuralFeature, ECPAbstractControl>();
	private final Map<VDomainModelReference, Adapter> adapterMap = new LinkedHashMap<VDomainModelReference, Adapter>();
	private ViewModelContext viewModelContext;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private VCustomControl customControl;
	private DataBindingContext dataBindingContext;
	private final EMFDataBindingContext viewModelDBC = new EMFDataBindingContext();

	/**
	 * Called by the framework to trigger an initialization.
	 *
	 * @param customControl the {@link VCustomControl} to use
	 * @param viewModelContext the {@link ViewModelContext} to use
	 * @since 1.3
	 */
	public final void init(VCustomControl customControl, ViewModelContext viewModelContext) {
		this.customControl = customControl;
		this.viewModelContext = viewModelContext;
		controlFactory = Activator.getDefault().getECPControlFactory();

		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);
		postInit();
	}

	/**
	 * This method is called after the initialization. Custom controls can overwrite this to execute specific
	 * initialization steps.
	 *
	 * @since 1.3
	 */
	protected void postInit() {
		if (!VCustomDomainModelReference.class.isInstance(customControl.getDomainModelReference())) {
			return;
		}
		final VCustomDomainModelReference customDomainModelReference = VCustomDomainModelReference.class
			.cast(customControl.getDomainModelReference());

		final ECPHardcodedReferences hardcodedReferences = loadObject(customDomainModelReference.getBundleName(),
			customDomainModelReference.getClassName());
		if (!customDomainModelReference.isControlChecked()) {
			// read stuff from control
			final Set<VDomainModelReference> controlReferences = new LinkedHashSet<VDomainModelReference>();
			controlReferences.addAll(hardcodedReferences.getNeededDomainModelReferences());
			controlReferences.addAll(customDomainModelReference.getDomainModelReferences());
			customDomainModelReference.getDomainModelReferences().clear();
			customDomainModelReference.getDomainModelReferences().addAll(controlReferences);
			customDomainModelReference.setControlChecked(true);
		}
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz) {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			new ClassNotFoundException(String.format(LocalizationServiceHelper.getString(
				VCustomDomainModelReferenceImpl.class, "BundleNotFound_ExceptionMessage"), clazz, bundleName)); //$NON-NLS-1$
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			if (!ECPHardcodedReferences.class.isAssignableFrom(loadClass)) {
				return null;
			}
			return ECPHardcodedReferences.class.cast(loadClass.newInstance());
		} catch (final ClassNotFoundException ex) {
			return null;
		} catch (final InstantiationException ex) {
			return null;
		} catch (final IllegalAccessException ex) {
			return null;
		}

	}

	/**
	 * Is called by the framework to trigger a dispose of the control.
	 */
	public final void dispose() {
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		if (dataBindingContext != null) {
			dataBindingContext.dispose();
		}
		viewModelDBC.dispose();
		customControl = null;
		if (adapterMap != null) {
			for (final VDomainModelReference domainModelReference : adapterMap.keySet()) {
				final Setting setting = getFirstSetting(domainModelReference);
				setting.getEObject().eAdapters().remove(adapterMap.get(domainModelReference));
			}

			adapterMap.clear();
		}
		if (controlMap != null) {
			for (final ECPAbstractControl control : controlMap.values()) {
				control.dispose();
			}
			controlMap.clear();
		}
		viewModelContext = null;

		Activator.getDefault().ungetECPControlFactory();
		controlFactory = null;

		disposeCustomControl();
	}

	/**
	 * This method is called during dispose and allows to dispose necessary objects.
	 *
	 * @since 1.3
	 */
	protected abstract void disposeCustomControl();

	/**
	 * Return the {@link ViewModelContext}.
	 *
	 * @return the {@link ViewModelContext} of this control
	 * @since 1.3
	 */
	protected final ViewModelContext getViewModelContext() {
		return viewModelContext;
	}

	/**
	 * Return the {@link VCustomControl}.
	 *
	 * @return the {@link VCustomControl} of this control
	 * @since 1.3
	 */
	protected final VCustomControl getCustomControl() {
		return customControl;
	}

	/**
	 * Returns a {@link DataBindingContext} for this control.
	 *
	 * @return the {@link DataBindingContext}
	 * @since 1.3
	 */
	protected final DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	private void handleCreatedControls(Diagnostic diagnostic) {
		if (diagnostic.getData() == null) {
			return;
		}
		if (diagnostic.getData().size() < 2) {
			return;
		}
		if (!(diagnostic.getData().get(1) instanceof EStructuralFeature)) {
			return;
		}
		final EStructuralFeature feature = (EStructuralFeature) diagnostic.getData().get(1);
		final ECPAbstractControl ecpControl = controlMap.get(feature);
		if (ecpControl == null) {
			return;
		}
		ecpControl.handleValidation(diagnostic);
	}

	/**
	 * This is called so that an error can be shown by the user.
	 *
	 * @since 1.3
	 */
	protected abstract void handleContentValidation();

	/**
	 * This is a helper method which provides an {@link SWTCustomControlHelper}. It allows to get an image based on the
	 * constants defined in {@link ECPAbstractCustomControlSWT}.
	 *
	 * @return the {@link SWTCustomControlHelper} to use to retrieve images.
	 * @since 1.3
	 */
	protected final SWTCustomControlHelper getHelper() {
		return swtHelper;
	}

	private Image getImage(int imageType) {
		switch (imageType) {
		case VALIDATION_ERROR_IMAGE:
			return Activator.getImage("icons/validation_error.png"); //$NON-NLS-1$
		case HELP_IMAGE:
			return Activator.getImage("icons/help.png"); //$NON-NLS-1$
		case ADD_IMAGE:
			return Activator.getImage("icons/add.png"); //$NON-NLS-1$
		case DELETE_IMAGE:
			return Activator.getImage("icons/unset_reference.png"); //$NON-NLS-1$
		default:
			return null;
		}
	}

	/**
	 * Whether the label for this control should be rendered on the left of the control. This is the case if the
	 * {@link VControl#getLabelAlignment()} is set to {@link LabelAlignment#LEFT} or {@link LabelAlignment#DEFAULT}.
	 *
	 * @return <code>true</code> if label should be on the left, <code>false</code> otherwise
	 * @since 1.7
	 */
	protected boolean hasLeftLabelAlignment() {
		return getCustomControl().getLabelAlignment() == LabelAlignment.LEFT
			|| getCustomControl().getLabelAlignment() == LabelAlignment.DEFAULT;
	}

	/**
	 * Create the {@link Control} displaying the label of the current {@link VControl}.
	 *
	 * @param parent the {@link Composite} to render onto
	 * @return the created {@link Control} or null
	 * @throws NoPropertyDescriptorFoundExeption thrown if the {@link org.eclipse.emf.ecore.EStructuralFeature
	 *             EStructuralFeature} of the {@link VControl} doesn't have a registered {@link IItemPropertyDescriptor}
	 * @since 1.3
	 */
	protected final Control createLabel(final Composite parent)
		throws NoPropertyDescriptorFoundExeption {
		Label label = null;
		labelRender: if (hasLeftLabelAlignment()) {
			IValueProperty valueProperty;
			try {
				valueProperty = Activator
					.getDefault()
					.getEMFFormsDatabinding()
					.getValueProperty(getCustomControl().getDomainModelReference(),
						getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
				break labelRender;
			}
			final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();

			label = new Label(parent, SWT.NONE);
			label.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_control_label"); //$NON-NLS-1$
			label.setBackground(parent.getBackground());

			try {
				viewModelDBC.bindValue(
					WidgetProperties.text().observe(label),
					Activator
						.getDefault()
						.getEMFFormsLabelProvider()
						.getDisplayName(getCustomControl().getDomainModelReference(),
							getViewModelContext().getDomainModel()),
					null, new UpdateValueStrategy() {

						/**
						 * {@inheritDoc}
						 *
						 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
						 */
						@Override
						public Object convert(Object value) {
							final String labelText = (String) super.convert(value);
							String extra = ""; //$NON-NLS-1$
							if (structuralFeature.getLowerBound() > 0) {
								extra = "*"; //$NON-NLS-1$
							}
							return labelText + extra;
						}

					});
				viewModelDBC.bindValue(
					WidgetProperties.tooltipText().observe(label),
					Activator
						.getDefault()
						.getEMFFormsLabelProvider()
						.getDescription(getCustomControl().getDomainModelReference(),
							getViewModelContext().getDomainModel()));
			} catch (final NoLabelFoundException e) {
				Activator.getDefault().getReportService().report(new RenderingFailedReport(e));
				label.setText(e.getMessage());
				label.setToolTipText(e.toString());
			}

		}
		return label;
	}

	/**
	 * Creates a Label which is used to display the validation icon.
	 *
	 * @param composite the {@link Composite} to render onto
	 * @return the created Label
	 * @since 1.3
	 */
	protected final Label createValidationIcon(Composite composite) {
		final Label validationLabel = new Label(composite, SWT.NONE);
		validationLabel.setBackground(composite.getBackground());
		return validationLabel;
	}

	/**
	 * Creates a binding for a {@link StructuredViewer} based on a {@link ECPCustomControlFeature} and the array of
	 * {@link IValueProperty IValueProperties} for labels.
	 *
	 * @param customControlFeature the {@link ECPCustomControlFeature} to use
	 * @param viewer the {@link StructuredViewer} to bind
	 * @param labelProperties the array if {@link IValueProperty IValueProperties} to use for labels
	 */
	protected final void createViewerBinding(VDomainModelReference customControlFeature, StructuredViewer viewer,
		IValueProperty[] labelProperties) {
		try {
			final IObservableList list = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableList(customControlFeature, getViewModelContext().getDomainModel());
			ViewerSupport.bind(viewer, list, labelProperties);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
		}
	}

	/**
	 * Return an {@link IObservableList} based on a {@link VDomainModelReference}.
	 *
	 * @param domainModelReference the {@link VDomainModelReference} to use
	 * @return the {@link IObservableList}
	 * @since 1.3
	 * @deprecated This method is deprecated and must not be used anymore. Use the
	 *             {@link org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding
	 *             databinding service} instead.
	 */
	@Deprecated
	protected final IObservableList getObservableList(VDomainModelReference domainModelReference) {
		throw new UnsupportedOperationException(
			"This method is deprecated and must not be used anymore. Use the databinding service instead."); //$NON-NLS-1$
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link Setting}.
	 *
	 * @param setting the provided {@link Setting}
	 * @return the {@link EditingDomain} of this {@link Setting}
	 * @since 1.3
	 */
	protected final EditingDomain getEditingDomain(Setting setting) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(setting.getEObject());
	}

	/**
	 * @param modelFeature the {@link VDomainModelReference} to get the Setting for
	 * @return the setting or throws an {@link IllegalStateException} if too many or too few elements are found.
	 */
	private Setting getFirstSetting(VDomainModelReference modelFeature) {
		IObservableValue observableValue;
		try {
			observableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(modelFeature, getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			throw new IllegalStateException("The databinding failed due to an incorrect VDomainModelReference: " //$NON-NLS-1$
				+ ex.getMessage());
		}
		final InternalEObject internalEObject = (InternalEObject) ((IObserving) observableValue).getObserved();
		final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
		observableValue.dispose();

		return internalEObject.eSetting(structuralFeature);
	}

	/**
	 * Use this method to get an {@link ECPControl} which can be used inside the {@link ECPCustomControl}.
	 *
	 * @param clazz the {@link Class} of the {@link ECPControl} to retrieve
	 * @param domainModelReference the {@link VDomainModelReference} to retrieve a control for
	 * @param <T> the type of the control to retrieve
	 * @return the {@link ECPControl} that is fitting the most for the {@link ECPCustomControlFeature}. Can also be
	 *         null.
	 * @since 1.3
	 */
	protected final <T extends ECPAbstractControl> T getControl(Class<T> clazz,
		VDomainModelReference domainModelReference) {
		final T createControl = controlFactory.createControl(clazz, getViewModelContext().getDomainModel(),
			domainModelReference);
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference modelReference = EcoreUtil.copy(domainModelReference);
		vControl.setDomainModelReference(modelReference);
		vControl.setDiagnostic(VViewFactory.eINSTANCE.createDiagnostic());
		createControl.init(getViewModelContext(), vControl);

		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding()
				.getValueProperty(domainModelReference, getViewModelContext().getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return null;
		}
		final EStructuralFeature structuralFeature = (EStructuralFeature) valueProperty.getValueType();
		controlMap.put(structuralFeature, createControl);
		return createControl;
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link Setting}.
	 *
	 * @param setting the {@link Setting} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 * @since 1.3
	 */
	protected final IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		return adapterFactoryItemDelegator.getPropertyDescriptor(setting.getEObject(),
			setting.getEStructuralFeature());
	}

	private String getHelp(VDomainModelReference domainModelReference) {
		if (!getResolvedDomainModelReferences().contains(domainModelReference)) {
			throw new IllegalArgumentException("The feature must have been registered before!"); //$NON-NLS-1$
		}
		return getItemPropertyDescriptor(getFirstSetting(domainModelReference)).getDescription(null);
	}

	private String getLabel(VDomainModelReference domainModelReference) {
		if (!getResolvedDomainModelReferences().contains(domainModelReference)) {
			throw new IllegalArgumentException("The feature must have been registered before!"); //$NON-NLS-1$
		}
		return getItemPropertyDescriptor(getFirstSetting(domainModelReference)).getDisplayName(null);
	}

	/**
	 * Returns a list of all {@link VDomainModelReference VDomainModelReferences} which were already resolved and thus
	 * can be used for binding etc.
	 *
	 * @return the List of {@link VDomainModelReference VDomainModelReferences}
	 * @since 1.3
	 */
	protected final List<VDomainModelReference> getResolvedDomainModelReferences() {
		// final VHardcodedDomainModelReference hardcodedDomainModelReference = VHardcodedDomainModelReference.class
		// .cast(getCustomControl().getDomainModelReference());
		// return hardcodedDomainModelReference.getDomainModelReferences();
		final VDomainModelReference domainModelReference = getCustomControl().getDomainModelReference();
		if (VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			return VCustomDomainModelReference.class.cast(domainModelReference).getDomainModelReferences();
		}
		return Collections.singletonList(domainModelReference);
	}

	/**
	 * Finds the {@link VDomainModelReference} which provides a specific {@link EStructuralFeature}.
	 *
	 * @param feature the {@link EStructuralFeature} to find the {@link VDomainModelReference} for
	 * @return the {@link VDomainModelReference} or null
	 * @since 1.3
	 */
	protected final VDomainModelReference getResolvedDomainModelReference(EStructuralFeature feature) {
		for (final VDomainModelReference domainModelReference : getResolvedDomainModelReferences()) {
			if (VFeaturePathDomainModelReference.class.isInstance(domainModelReference)) {
				final VFeaturePathDomainModelReference ref = VFeaturePathDomainModelReference.class
					.cast(domainModelReference);
				if (ref.getDomainModelEFeature() == feature) {
					return ref;
				}
			}
		}
		return null;
	}

	/**
	 * Method for enabling databinding on the reference/attribute of the referenced object.
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 *
	 *
	 * @param modelFeature the {@link VDomainModelReference} to bind
	 * @param targetValue the target observerable
	 * @param targetToModel update strategy target to model
	 * @param modelToTarget update strategy model to target
	 * @return the resulting binding
	 * @since 1.3
	 */
	protected final Binding bindTargetToModel(VDomainModelReference modelFeature, IObservableValue targetValue,
		UpdateValueStrategy targetToModel,
		UpdateValueStrategy modelToTarget) {
		final Setting setting = getFirstSetting(modelFeature);
		final IObservableValue modelValue = EMFEditObservables.observeValue(
			getEditingDomain(setting),
			setting.getEObject(), setting.getEStructuralFeature());
		return getDataBindingContext().bindValue(targetValue, modelValue, targetToModel,
			modelToTarget);
	}

	/**
	 * Provides the {@link EditingDomain} for this custom control.
	 *
	 * @return the {@link EditingDomain} for this control
	 * @since 1.3
	 */
	protected final EditingDomain getEditingDomain() {
		return getEditingDomain(getFirstSetting(getCustomControl().getDomainModelReference()));
	}

	/**
	 * Returns the current set value of the feature.
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 *
	 * @param modelReference the {@link VDomainModelReference} to get the value for
	 * @return the value
	 * @since 1.3
	 */
	protected final Object getValue(VDomainModelReference modelReference) {
		final Setting setting = getFirstSetting(modelReference);
		return setting.get(false);
	}

	/**
	 * Sets the value of the feature to the new value.
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 *
	 * @param modelReference the {@link VDomainModelReference} to get the value for
	 * @param newValue the value to be set
	 * @since 1.3
	 */
	protected final void setValue(VDomainModelReference modelReference, Object newValue) {
		// FIXME needed?
		// if (!isEditable) {
		// throw new UnsupportedOperationException(
		// "Set value is not supported on ECPCustomControlFeatures that are not editable.");
		// }
		final Setting setting = getFirstSetting(modelReference);
		setting.set(newValue);
	}

	/**
	 *
	 * Registers a change listener on the referenced object. {@link ECPCustomControlChangeListener#notifyChanged()} will
	 * be called when a change on the referenced object is noticed.
	 *
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 *
	 * @param modelReference the {@link VDomainModelReference} to register a listener for
	 * @param changeListener the change listener to register
	 * @since 1.3
	 */
	protected final void registerChangeListener(VDomainModelReference modelReference,
		final ECPCustomControlChangeListener changeListener) {
		if (adapterMap.containsKey(modelReference)) {
			return;
		}
		final Setting setting = getFirstSetting(modelReference);
		final Adapter newAdapter = new AdapterImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				if (msg.isTouch()) {
					return;
				}
				if (msg.getFeature().equals(setting.getEStructuralFeature())) {
					super.notifyChanged(msg);
					changeListener.notifyChanged();
				}
			}

		};
		setting.getEObject().eAdapters().add(newAdapter);
		adapterMap.put(modelReference, newAdapter);
	}

	/**
	 * The {@link SWTCustomControlHelper} allows the retrieval of SWT specific elements.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	public final class SWTCustomControlHelper {

		/**
		 * Allows to get an {@link Image} based on the constants defined in {@link ECPAbstractCustomControlSWT}.
		 *
		 * @param imageType the image type to retrieve
		 * @return the retrieved Image or null if an unknown imageType was provided
		 */
		public Image getImage(int imageType) {
			return ECPAbstractCustomControlSWT.this.getImage(imageType);
		}

		/**
		 * This return a text providing a long helpful description of the feature. Can be used for example in a ToolTip.
		 *
		 * @param domainModelReference the {@link VDomainModelReference} to retrieve the help text for
		 * @return the String containing the helpful description or null if no description is found
		 * @since 1.3
		 */
		public String getHelp(VDomainModelReference domainModelReference) {
			return ECPAbstractCustomControlSWT.this.getHelp(domainModelReference);
		}

		/**
		 * This return a text providing a short label of the feature. Can be used for example as a label in front of the
		 * edit field.
		 *
		 * @param domainModelReference the {@link VDomainModelReference} to retrieve the text for
		 * @return the String containing the label null if no label is found
		 * @since 1.3
		 */
		public String getLabel(VDomainModelReference domainModelReference) {
			return ECPAbstractCustomControlSWT.this.getLabel(domainModelReference);
		}
	}

	/**
	 * Returns the GridDescription for this Renderer.
	 *
	 * @return the GridDescription
	 * @since 1.6
	 */
	public abstract SWTGridDescription getGridDescription();

	/**
	 * Renders the control.
	 *
	 * @param cell the {@link SWTGridCell} of the control to render
	 * @param parent the {@link Composite} to render on
	 * @return the rendered {@link Control}
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 * @since 1.6
	 */
	public abstract Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption;

	/**
	 * Called by the framework to apply validation changes.
	 *
	 * @since 1.3
	 */
	public final void applyValidation() {
		if (getCustomControl() == null || getCustomControl().getDiagnostic() == null) {
			return;
		}
		for (final Object diagnostic : getCustomControl().getDiagnostic().getDiagnostics()) {
			handleCreatedControls((Diagnostic) diagnostic);
		}

		handleContentValidation();
	}

	/**
	 * Applies the current readOnlyState.
	 *
	 * @param controls the controls provided
	 * @since 1.3
	 */
	public final void applyReadOnly(Map<SWTGridCell, Control> controls) {
		if (setEditable(!getCustomControl().isReadonly())) {
			for (final SWTGridCell gridCell : controls.keySet()) {
				setControlEnabled(gridCell, controls.get(gridCell), !getCustomControl().isReadonly());
			}
		}
	}

	/**
	 * Override this to control which and how controls should be enabled/disabled.
	 *
	 * @param gridCell the {@link SWTGridCell} to enable/disable
	 * @param control the {@link Control} to enable/disable
	 * @param enabled true if the control should be enabled false otherwise
	 * @since 1.6
	 */
	protected void setControlEnabled(SWTGridCell gridCell, Control control, boolean enabled) {
		// ignore labels as they are readonly per definition
		if (Label.class.isInstance(control)) {
			return;
		}
		control.setEnabled(enabled);
	}

	/**
	 * Applies the current enable state.
	 *
	 * @param controls the controls
	 * @since 1.3
	 */
	public final void applyEnable(Map<SWTGridCell, Control> controls) {
		if (setEditable(getCustomControl().isEnabled())) {
			for (final SWTGridCell gridCell : controls.keySet()) {
				setControlEnabled(gridCell, controls.get(gridCell), getCustomControl().isEnabled());
			}
		}
	}

	/**
	 * Allows custom controls to call specific code for setting controls editable or not. The result indicates whether
	 * to call the default editable
	 *
	 * @param editable if the current state is editable
	 * @return true if default editable modification should be executed
	 * @since 1.3
	 */
	protected boolean setEditable(boolean editable) {
		return true;
	}

	/**
	 * Creates a simple grid.
	 *
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return the {@link GridDescription}
	 * @since 1.6
	 */
	protected final SWTGridDescription createSimpleGrid(int rows, int columns) {
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(rows, columns, null);
	}

	/**
	 * <p>
	 * Indicates if the given custom control takes the responsibility to call a possibly existing
	 * {@link org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService EMFFormsControlProcessorService} itself.
	 * </p>
	 * <p>
	 * The default implementation returns {@code false}.
	 * </p>
	 *
	 * @return
	 *         {@code true} if the custom control can handle the
	 *         {@link org.eclipse.emfforms.spi.swt.core.EMFFormsControlProcessorService EMFFormsControlProcessorService}
	 *         itself, {@code false} otherwise.
	 * @since 1.8
	 */
	protected boolean canHandleControlProcessor() {
		return false;
	}
}
