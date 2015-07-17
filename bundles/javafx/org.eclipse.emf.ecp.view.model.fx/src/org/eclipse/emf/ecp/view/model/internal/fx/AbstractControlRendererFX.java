package org.eclipse.emf.ecp.view.model.internal.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.fx.core.databinding.JFXBeanProperties;

public abstract class AbstractControlRendererFX extends RendererFX<VControl> {

	private EMFDataBindingContext dataBindingContext;
	private ComposedAdapterFactory composedAdapterFactory;
	private AdapterFactoryItemDelegator adapterFactoryItemDelegator;
	private ModelChangeListener viewChangeListener;
	private IObservableValue modelValue;

	@Override
	public void init(final VControl control, ViewModelContext viewModelContext) {
		super.init(control, viewModelContext);
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(composedAdapterFactory);

		if (getViewModelContext() != null) {
			viewChangeListener = new ModelChangeListener() {

				@Override
				public void notifyChange(ModelChangeNotification notification) {
					if (notification.getNotifier() != getVElement()) {
						return;
					}
					if (notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Visible()) {
						applyVisible();
					}
					if (notification.getStructuralFeature() == VViewPackage.eINSTANCE.getElement_Enabled()
						&& !getVElement().isReadonly()) {
						applyEnable();
					}
					if (notification.getStructuralFeature() == VViewPackage.eINSTANCE
						.getElement_Diagnostic()) {
						applyValidation();
					}
				}
			};
			getViewModelContext().registerViewChangeListener(viewChangeListener);
		}
	}

	protected void applyValidation() {
		// TODO Auto-generated method stub

	}

	protected void applyEnable() {
		// TODO Auto-generated method stub

	}

	protected void applyVisible() {
		// TODO Auto-generated method stub

	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link Setting}.
	 *
	 * @param setting the {@link Setting} to use for identifying the {@link IItemPropertyDescriptor}.
	 * @return the {@link IItemPropertyDescriptor}
	 */
	protected IItemPropertyDescriptor getItemPropertyDescriptor(Setting setting) {
		return adapterFactoryItemDelegator.getPropertyDescriptor(setting.getEObject(),
			setting.getEStructuralFeature());
	}

	protected final DataBindingContext getDataBindingContext() {
		if (dataBindingContext == null) {
			dataBindingContext = new EMFDataBindingContext();
		}
		return dataBindingContext;
	}

	protected IObservableValue getTargetObservable(Object source,
		String property) {
		return JFXBeanProperties.value(property).observe(source);
	}

	protected IObservableValue getModelObservable(Setting setting) {
		return new EObjectObservableValue(setting.getEObject(),
			setting.getEStructuralFeature()) {

			@Override
			public Object getValueType() {
				// TODO Auto-generated method stub
				return ((EStructuralFeature) super.getValueType()).getEType()
					.getInstanceClass();
			}

		};
	}

	protected Binding bindModelToTarget(IObservableValue target,
		IObservableValue model, UpdateValueStrategy targetToModelStrategy,
		UpdateValueStrategy modelToTargetStrategy) {
		final Binding binding = getDataBindingContext().bindValue(target, model,
			targetToModelStrategy, modelToTargetStrategy);
		binding.getValidationStatus().addValueChangeListener(
			new IValueChangeListener() {

				@Override
				public void handleValueChange(ValueChangeEvent event) {
					final IStatus statusNew = (IStatus) event.diff.getNewValue();
					if (IStatus.ERROR == statusNew.getSeverity()) {
						binding.updateModelToTarget();
					}
				}
			});

		return binding;
	}

	protected void applyValidation(VControl control, Node node) {
		if (control.getDiagnostic() == null) {
			node.setId(null);
			return;
		}
		switch (control.getDiagnostic().getHighestSeverity()) {
		case Diagnostic.ERROR:
			node.setId("error"); //$NON-NLS-1$
			break;
		case Diagnostic.OK:
			node.setId(null);
			break;
		}
	}

	/**
	 * Returns an {@link IObservableValue} based on the provided {@link Setting}.
	 *
	 * @param setting the {@link Setting} to get the {@link IObservableValue} for
	 * @return the {@link IObservableValue}
	 */
	protected final IObservableValue getModelValue(final Setting setting) {
		if (modelValue == null) {
			modelValue = EMFEditObservables.observeValue(getEditingDomain(setting),
				setting.getEObject(), setting.getEStructuralFeature());
		}
		return modelValue;
	}

	/**
	 * Returns the {@link EditingDomain} for the provided {@link Setting}.
	 *
	 * @param setting the provided {@link Setting}
	 * @return the {@link EditingDomain} of this {@link Setting}s
	 */
	protected final EditingDomain getEditingDomain(Setting setting) {
		return AdapterFactoryEditingDomain.getEditingDomainFor(setting.getEObject());
	}

	/**
	 * Create label of the current {@link VControl}.
	 *
	 * @return the created {@link Label} or null
	 * @throws NoPropertyDescriptorFoundExeption thrown if the {@link org.eclipse.emf.ecore.EStructuralFeature
	 *             EStructuralFeature} of the {@link VControl} doesn't have a registered {@link IItemPropertyDescriptor}
	 */
	protected Label createLabel() throws NoPropertyDescriptorFoundExeption {
		Label label = null;

		final Setting setting = getVElement().getDomainModelReference().getIterator().next();
		if (setting == null) {
			return null;
		}
		final IItemPropertyDescriptor itemPropertyDescriptor = getItemPropertyDescriptor(setting);

		if (itemPropertyDescriptor == null) {
			throw new NoPropertyDescriptorFoundExeption(setting.getEObject(), setting.getEStructuralFeature());
		}

		if (getVElement().getLabelAlignment() == LabelAlignment.LEFT) {
			label = new Label();
			label.setText(itemPropertyDescriptor.getDisplayName(setting
				.getEObject()));

		}

		return label;
	}

	protected Node createValidationIcon() {
		final Label label = new Label();
		return label;
	}

	/**
	 * @return the adapterFactoryItemDelegator
	 */
	public AdapterFactoryItemDelegator getAdapterFactoryItemDelegator() {
		return adapterFactoryItemDelegator;
	}
}
