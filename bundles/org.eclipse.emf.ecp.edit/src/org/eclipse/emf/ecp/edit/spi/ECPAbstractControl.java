/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi;

import java.util.Locale;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.internal.edit.Activator;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * The {@link ECPAbstractControl} is the abstract class describing a control.
 * This class provides the necessary common access methods.
 *
 * @author Eugen Neufeld
 *
 */
@Deprecated
public abstract class ECPAbstractControl {

	/**
	 * @author Jonas
	 *
	 */
	private final class ViewModelChangeListener implements ModelChangeAddRemoveListener {
		private final VControl control;

		/**
		 * @param control
		 */
		private ViewModelChangeListener(VControl control) {
			this.control = control;
		}

		@Override
		public void notifyRemove(Notifier notifier) {
			// TODO Auto-generated method stub

		}

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (notification.getNotifier() != ECPAbstractControl.this.control) {
				return;
			}
			if (notification.getStructuralFeature() == VViewPackage.eINSTANCE
				.getElement_Diagnostic()) {
				applyValidation(control.getDiagnostic());

				// TODO remove asap
				backwardCompatibleHandleValidation();
			}
			if (notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Enabled()) {
				enabledmentChanged(control.isEnabled());
			}
		}

		@Override
		public void notifyAdd(Notifier notifier) {
			// TODO Auto-generated method stub

		}
	}

	private boolean embedded;
	private EMFDataBindingContext dataBindingContext;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private ViewModelContext viewModelContext;
	private VControl control;
	private Setting firstSetting;
	private EStructuralFeature firstFeature;
	private ModelChangeAddRemoveListener viewChangeListener;

	/**
	 * This method is called by the framework to instantiate the {@link ECPAbstractControl}.
	 *
	 * @param viewModelContext the {@link ViewModelContext} to use by this {@link ECPAbstractControl}.
	 * @param control the {@link VControl} of this control
	 * @since 1.2
	 */
	public final void init(ViewModelContext viewModelContext, final VControl control) {
		this.viewModelContext = viewModelContext;
		this.control = control;
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		viewChangeListener = new ViewModelChangeListener(control);

		viewModelContext.registerViewChangeListener(viewChangeListener);

		postInit();
	}

	/**
	 * Overwrite this method to implement control specific operations which must be executed after the init but before
	 * the rendering.
	 *
	 * @since 1.2
	 */
	protected void postInit() {
		// do nothing
	}

	/**
	 * Notifies a control, that its enablement state has changed.
	 *
	 * @param enabled the new enablement value
	 * @since 1.2
	 */
	protected void enabledmentChanged(boolean enabled) {
		setEditable(enabled);
	}

	/**
	 * Override this method in order to handle validation.
	 *
	 * @param diagnostic the current {@link VDiagnostic}
	 * @since 1.2
	 */
	protected void applyValidation(VDiagnostic diagnostic) {
		// default case do nothing
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link Setting}.
	 *
	 * @param setting the {@link Setting} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 * @since 1.2
	 */
	public IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		return adapterFactoryItemDelegator.getPropertyDescriptor(setting.getEObject(),
			setting.getEStructuralFeature());
	}

	/**
	 * Return the {@link VControl}.
	 *
	 * @return the {@link VControl} of this control
	 * @since 1.2
	 */
	protected final VControl getControl() {
		return control;
	}

	/**
	 * Return the {@link ViewModelContext}.
	 *
	 * @return the {@link ViewModelContext} of this control
	 * @since 1.2
	 */
	protected final ViewModelContext getViewModelContext() {
		return viewModelContext;
	}

	/**
	 * Returns the first setting for this control.
	 *
	 * @return the first Setting or throws an {@link IllegalArgumentException} if no settings can be found
	 * @since 1.2
	 */
	public final Setting getFirstSetting() {
		if (firstSetting == null) {
			@SuppressWarnings("rawtypes")
			IObservableValue observableValue;
			try {
				observableValue = Activator.getDefault().getEMFFormsDatabinding()
					.getObservableValue(control.getDomainModelReference(), getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
				throw new IllegalStateException("The databinding failed due to an incorrect VDomainModelReference: " //$NON-NLS-1$
					+ ex.getMessage());
			}
			final InternalEObject internalEObject = (InternalEObject) ((IObserving) observableValue).getObserved();
			final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
			observableValue.dispose();

			firstSetting = internalEObject.eSetting(structuralFeature);
			return firstSetting;
		}
		return firstSetting;
	}

	/**
	 * Return the {@link EStructuralFeature} of this control.
	 *
	 * @return the {@link EStructuralFeature}
	 * @since 1.2
	 */
	public final EStructuralFeature getFirstStructuralFeature() {
		if (firstFeature == null) {
			@SuppressWarnings("rawtypes")
			IValueProperty valueProperty;
			try {
				valueProperty = Activator.getDefault().getEMFFormsDatabinding()
					.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel());
			} catch (final DatabindingFailedException ex) {
				throw new IllegalArgumentException(
					"The passed VDomainModelReference resolves to no EStructuralFeature."); //$NON-NLS-1$
			}
			firstFeature = (EStructuralFeature) valueProperty.getValueType();
		}
		return firstFeature;
	}

	/**
	 * Returns a {@link DataBindingContext} for this control.
	 *
	 * @return the {@link DataBindingContext}
	 * @since 1.1
	 */
	public final DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	/**
	 * Disposes the control.
	 * A control which needs specific dispose handling must still call super.dispose.
	 *
	 * @since 1.1
	 */
	public void dispose() {
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		composedAdapterFactory = null;
		adapterFactoryItemDelegator = null;
		if (dataBindingContext != null) {
			dataBindingContext.dispose();
		}
		dataBindingContext = null;
		if (viewModelContext != null) {
			viewModelContext.unregisterViewChangeListener(viewChangeListener);
		}
		viewModelContext = null;

		viewChangeListener = null;

		control = null;
		firstFeature = null;
		firstSetting = null;
	}

	/**
	 * Whether a control is embedded. An embedded control can be rendered in an other fashion then an not embedded
	 * version.
	 *
	 * @return true if the control is embedded in another control
	 */
	protected final boolean isEmbedded() {
		return embedded;
	}

	/**
	 * Sets whether this control is used as an embedded control.
	 *
	 * @param embedded whether the control is used as an embedded control
	 */
	public final void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link Setting}.
	 *
	 * @param setting the provided {@link Setting}
	 * @return the {@link EditingDomain} of this {@link Setting}
	 * @since 1.2
	 */
	protected final EditingDomain getEditingDomain(Setting setting) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(setting.getEObject());
	}

	/**
	 * This method allows to get a service from the view model context.
	 *
	 * @param serviceClass the type of the service to get
	 * @return the service instance
	 * @param <T> the type of the service
	 * @since 1.2
	 */
	protected final <T> T getService(Class<T> serviceClass) {
		return viewModelContext.getService(serviceClass);
	}

	// TODO need view model service
	/**
	 * Returns the current Locale.
	 *
	 * @return the current {@link Locale}
	 * @since 1.2
	 */
	protected final Locale getLocale() {
		final ViewLocaleService service = viewModelContext.getService(ViewLocaleService.class);
		if (service != null) {
			return service.getLocale();
		}
		return Locale.getDefault();
	}

	/**
	 * Returns the {@link VDomainModelReference} set for this control.
	 *
	 * @return the domainModelReference the {@link VDomainModelReference} of this control
	 * @since 1.2
	 */
	protected final VDomainModelReference getDomainModelReference() {
		return control.getDomainModelReference();
	}

	/**
	 * Returns the {@link EditingDomain} for the set {@link VDomainModelReference}.
	 *
	 * @return the {@link EditingDomain} for this control
	 * @since 1.2
	 * @deprecated
	 */
	@Deprecated
	protected final EditingDomain getEditingDomain() {
		return getEditingDomain(getFirstSetting());
	}

	/**
	 * Helper method to keep the old validation. Does nothing.
	 *
	 * @since 1.2
	 */
	protected void backwardCompatibleHandleValidation() {
	}

	/**
	 * Handle live validation.
	 *
	 * @param diagnostic of type Diagnostic
	 * @deprecated
	 * @since 1.2
	 **/
	@Deprecated
	public void handleValidation(Diagnostic diagnostic) {
		// do nothing
	}

	/**
	 * Reset the validation status 'ok'.
	 *
	 * @deprecated
	 * @since 1.2
	 */
	@Deprecated
	public void resetValidation() {
		// do nothing
	}

	/**
	 * Whether a label should be shown for this control.
	 *
	 * @return true if a label should be created, false otherwise
	 * @deprecated use the labelAlignment of the control model element
	 * @since 1.2
	 */
	@Deprecated
	public boolean showLabel() {
		return true;
	}

	/**
	 * Sets the state of the widget to be either editable or not.
	 *
	 * @param isEditable whether to set the widget editable
	 * @since 1.2
	 * @deprecated
	 */
	@Deprecated
	public void setEditable(boolean isEditable) {
		// do nothing
	}

}
