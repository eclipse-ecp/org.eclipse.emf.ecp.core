/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.ui;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.ECPAbstractControl;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.internal.custom.ui.Activator;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPCustomControlChangeListener;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VHardcodedDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * Abstract class extending {@link ECPAbstractControl} providing necessary common access methods.
 * 
 * @author emueller
 * @author eneufeld
 * 
 */
public abstract class ECPAbstractCustomControl extends ECPAbstractControl implements ECPHardcodedReferences {

	private final CustomControlHelper helper = new CustomControlHelper();

	private final ECPControlFactory controlFactory;
	/**
	 * This map manages the mapping between an {@link EStructuralFeature} and a corresponding {@link ECPControl}.
	 */
	private final Map<EStructuralFeature, ECPAbstractControl> controlMap = new LinkedHashMap<EStructuralFeature, ECPAbstractControl>();

	private final Map<VDomainModelReference, Adapter> adapterMap = new LinkedHashMap<VDomainModelReference, Adapter>();

	/**
	 * Constructor for instantiating the {@link ECPAbstractCustomControl}. The constructor maps the features and sets
	 * the ECPControlFactory.
	 */
	public ECPAbstractCustomControl() {
		super();
		controlFactory = Activator.getECPControlFactory();
	}

	/**
	 * Returns a list of all {@link VDomainModelReference VDomainModelReferences} which were already resolved and thus
	 * can be used for binding etc.
	 * 
	 * @return the List of {@link VDomainModelReference VDomainModelReferences}
	 */
	protected final List<VDomainModelReference> getResolvedDomainModelReferences() {
		final VHardcodedDomainModelReference hardcodedDomainModelReference = (VHardcodedDomainModelReference) getDomainModelReference();
		return hardcodedDomainModelReference.getDomainModelReferences();
	}

	/**
	 * Finds the {@link VDomainModelReference} which provides a specific {@link EStructuralFeature}.
	 * 
	 * @param feature the {@link EStructuralFeature} to find the {@link VDomainModelReference} for
	 * @return the {@link VDomainModelReference} or null
	 */
	protected final VDomainModelReference getResolvedDomainModelReference(EStructuralFeature feature) {
		final VHardcodedDomainModelReference hardcodedDomainModelReference = (VHardcodedDomainModelReference) getDomainModelReference();
		for (final VDomainModelReference domainModelReference : hardcodedDomainModelReference
			.getDomainModelReferences()) {
			if (VFeaturePathDomainModelReference.class.isInstance(domainModelReference)) {
				final VFeaturePathDomainModelReference ref = (VFeaturePathDomainModelReference) domainModelReference;
				if (ref.getDomainModelEFeature() == feature) {
					return ref;
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves the control used for an {@link EStructuralFeature}.
	 * 
	 * @param eStructuralFeature the {@link EStructuralFeature} to get the control for
	 * @return the {@link ECPControl} or null if none is used inside this {@link ECPCustomControl}
	 */
	protected final ECPAbstractControl getRetrievedControl(EStructuralFeature eStructuralFeature) {
		return controlMap.get(eStructuralFeature);
	}

	/**
	 * Reset all validation errors on all {@link ECPControl ECPControls} used in this {@link ECPCustomControl}.
	 */
	protected final void resetControlValidation() {
		final Set<EStructuralFeature> keySet = controlMap.keySet();

		for (final EStructuralFeature eStructuralFeature : keySet) {
			final ECPAbstractControl ecpControl = controlMap.get(eStructuralFeature);
			ecpControl.resetValidation();
		}
	}

	// private void initFeatures() {
	// for (final ECPCustomControlFeature feature : features) {
	// feature.init(modelElementContext.getModelElement(), modelElementContext.getDataBindingContext(),
	// modelElementContext.getEditingDomain());
	// }
	// }

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
	 */
	protected Binding bindTargetToModel(VDomainModelReference modelFeature, IObservableValue targetValue,
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
	 * Return an {@link IObservableList} based on a {@link VDomainModelReference}.
	 * 
	 * @param domainModelReference the {@link VDomainModelReference} to use
	 * @return the {@link IObservableList}
	 */
	protected IObservableList getObservableList(VDomainModelReference domainModelReference) {
		final Setting setting = getFirstSetting(domainModelReference);
		return EMFEditObservables.observeList(
			getEditingDomain(setting),
			setting.getEObject(), setting.getEStructuralFeature());
	}

	/**
	 * Sets the value of the feature to the new value.
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 * 
	 * @param modelReference the {@link VDomainModelReference} to get the value for
	 * @param newValue the value to be set
	 */
	protected void setValue(VDomainModelReference modelReference, Object newValue) {
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
	 */
	protected void registerChangeListener(VDomainModelReference modelReference,
		final ECPCustomControlChangeListener changeListener) {
		if (adapterMap.containsKey(modelReference)) {
			// FIXME handling if an listener already exists
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
	 * Returns the current set value of the feature.
	 * Throws an {@link IllegalStateException} if the {@link VDomainModelReference} doesn't resolve to exactly one
	 * {@link Setting}.
	 * 
	 * @param modelReference the {@link VDomainModelReference} to get the value for
	 * @return the value
	 */
	protected Object getValue(VDomainModelReference modelReference) {
		final Setting setting = getFirstSetting(modelReference);
		return setting.get(false);
	}

	/**
	 * @param modelFeature the {@link VDomainModelReference} to get the Setting for
	 * @return the setting or throws an {@link IllegalStateException} if too many or too few elements are found.
	 */
	private Setting getFirstSetting(VDomainModelReference modelFeature) {
		final Iterator<Setting> iterator = modelFeature.getIterator();
		int numElments = 0;
		Setting setting = null;
		while (iterator.hasNext()) {
			setting = iterator.next();
			numElments++;
		}
		if (numElments == 0) {
			throw new IllegalStateException("The VDomainModelReference was not initialised."); //$NON-NLS-1$
		}
		else if (numElments > 1) {
			throw new IllegalStateException(
				"The VDomainModelReference is ambigous, please use VDomainModelReference which resolve to exactly one setting."); //$NON-NLS-1$
		}
		// if (!isEditable()) {
		// throw new IllegalArgumentException("Feature is not registered as editable");
		// }
		return setting;
	}

	/**
	 * Return the {@link IItemPropertyDescriptor} describing this {@link EStructuralFeature}.
	 * 
	 * @param domainModelReference the {@link VDomainModelReference} to use for identifying the correct
	 *            {@link EStructuralFeature}.
	 * @return the {@link IItemPropertyDescriptor}
	 * @since 1.1
	 */
	protected final IItemPropertyDescriptor getItemPropertyDescriptor(VDomainModelReference domainModelReference) {
		return getItemPropertyDescriptor(getFirstSetting(domainModelReference));
	}

	private String getHelp(VDomainModelReference domainModelReference) {
		if (!getResolvedDomainModelReferences().contains(domainModelReference)) {
			throw new IllegalArgumentException("The feature must have been registered before!"); //$NON-NLS-1$
		}
		return getItemPropertyDescriptor(domainModelReference).getDescription(null);
	}

	private String getLabel(VDomainModelReference domainModelReference) {
		if (!getResolvedDomainModelReferences().contains(domainModelReference)) {
			throw new IllegalArgumentException("The feature must have been registered before!"); //$NON-NLS-1$
		}
		return getItemPropertyDescriptor(domainModelReference).getDisplayName(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated
	 */
	@Override
	@Deprecated
	public final boolean showLabel() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		Activator.ungetECPControlFactory();

		for (final VDomainModelReference domainModelReference : adapterMap.keySet()) {
			final Setting setting = getFirstSetting(domainModelReference);
			setting.getEObject().eAdapters().remove(adapterMap.get(domainModelReference));
		}
		disposeCustomControl();
	}

	/**
	 * This method is called during dispose and allows to dispose necessary objects.
	 */
	protected abstract void disposeCustomControl();

	/**
	 * This is a helper method which provides an {@link CustomControlHelper}. It allows to get the help description as
	 * well as the label of an {@link ECPCustomControlFeature}.
	 * 
	 * @return the {@link CustomControlHelper} to use to retrieve addition descriptions of an
	 *         {@link ECPCustomControlFeature}.
	 */
	protected final CustomControlHelper getHelper() {
		return helper;
	}

	/**
	 * Use this method to get an {@link ECPControl} which can be used inside the {@link ECPCustomControl}.
	 * 
	 * @param clazz the {@link Class} of the {@link ECPControl} to retrieve
	 * @param domainModelReference the {@link VDomainModelReference} to retrieve a control for
	 * @param <T> the type of the control to retrieve
	 * @return the {@link ECPControl} that is fitting the most for the {@link ECPCustomControlFeature}. Can also be
	 *         null.
	 */
	protected final <T extends ECPAbstractControl> T getControl(Class<T> clazz,
		VDomainModelReference domainModelReference) {
		final T createControl = controlFactory.createControl(clazz, domainModelReference);
		final VControl vControl = VViewFactory.eINSTANCE.createControl();
		vControl.setDomainModelReference(domainModelReference);
		vControl.setDiagnostic(VViewFactory.eINSTANCE.createDiagnostic());
		createControl.init(getViewModelContext(), vControl);
		final Iterator<Setting> iterator = domainModelReference.getIterator();
		final boolean hasNext = iterator.hasNext();
		if (hasNext) {
			controlMap.put(iterator.next().getEStructuralFeature(), createControl);
			return createControl;
		}
		return null;
	}

	/**
	 * The {@link CustomControlHelper} allows the retrieval of information based on an {@link VDomainModelReference}.
	 * 
	 * @author Eugen Neufeld
	 * 
	 */
	public final class CustomControlHelper {

		/**
		 * This return a text providing a long helpful description of the feature. Can be used for example in a ToolTip.
		 * 
		 * @param domainModelReference the {@link VDomainModelReference} to retrieve the help text for
		 * @return the String containing the helpful description or null if no description is found
		 */
		public String getHelp(VDomainModelReference domainModelReference) {
			return ECPAbstractCustomControl.this.getHelp(domainModelReference);
		}

		/**
		 * This return a text providing a short label of the feature. Can be used for example as a label in front of the
		 * edit field.
		 * 
		 * @param domainModelReference the {@link VDomainModelReference} to retrieve the text for
		 * @return the String containing the label null if no label is found
		 */
		public String getLabel(VDomainModelReference domainModelReference) {
			return ECPAbstractCustomControl.this.getLabel(domainModelReference);
		}
	}
}
