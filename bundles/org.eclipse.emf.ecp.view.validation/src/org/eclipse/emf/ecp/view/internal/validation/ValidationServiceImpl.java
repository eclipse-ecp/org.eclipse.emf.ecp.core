/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.ecp.view.spi.validation.ViewValidationListener;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * Validation service that, once instantiated, synchronizes the validation result of a model element with its
 * Renderable.
 *
 * @author Eugen Neufeld
 *
 */
public class ValidationServiceImpl implements ValidationService, EMFFormsContextListener {

	/**
	 * The {@link ValidationDomainModelChangeListener} for the view model.
	 *
	 */
	private class ViewModelChangeListener implements ModelChangeAddRemoveListener {

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (VViewPackage.eINSTANCE.getElement_Enabled() == notification.getRawNotification()
				.getFeature()
				|| VViewPackage.eINSTANCE.getElement_Visible() == notification.getRawNotification()
					.getFeature()) {
				if (VViewPackage.eINSTANCE.getControl().isInstance(notification.getNotifier())) {
					final VControl control = (VControl) notification.getNotifier();

					if (VViewPackage.eINSTANCE.getElement_Enabled() == notification.getRawNotification().getFeature()) {
						control.setDiagnostic(null);
					}

					final VDomainModelReference domainModelReference = control.getDomainModelReference();
					if (domainModelReference == null) {
						return;
					}
					IObservableValue observableValue;
					try {
						observableValue = Activator.getDefault().getEMFFormsDatabinding()
							.getObservableValue(domainModelReference, context.getDomainModel());
					} catch (final DatabindingFailedException ex) {
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
						return;
					}
					final EObject observed = (EObject) ((IObserving) observableValue).getObserved();
					// validate(observed);
					// TODO: add test case fo this
					final Set<EObject> eObjectsToValidate = new LinkedHashSet<EObject>();
					eObjectsToValidate.add(observed);
					final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
					final Object value = observableValue.getValue();
					if (EReference.class.isInstance(structuralFeature) && value != null) {
						/*
						 * the value may be null! this is possible e.g. when there is a longer feature path dmr on
						 * which an element on the path gets deleted/replaced during runtime.
						 * Adding null to the set is no advised as we will get exception immediately or in the future.
						 */
						if (structuralFeature.isMany()) {
							@SuppressWarnings("unchecked")
							final List<EObject> list = (List<EObject>) value;
							eObjectsToValidate.addAll(list);
						} else {
							eObjectsToValidate.add((EObject) value);
						}
					}
					validate(eObjectsToValidate);
					observableValue.dispose();
				}
			}
			if (!VElement.class.isInstance(notification.getNotifier())) {
				return;
			}
			switch (notification.getRawNotification().getEventType()) {
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				final Map<VElement, VDiagnostic> map = Collections.emptyMap();
				reevaluateToTop(notification.getNotifier(), map);
				break;
			default:
				break;
			}
		}

		@Override
		public void notifyAdd(Notifier notifier) {
			if (VDomainModelReference.class.isInstance(notifier)
				&& !VDomainModelReference.class.isInstance(EObject.class.cast(notifier).eContainer())) {
				final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(notifier);
				if (domainModelReference == null) {
					return;
				}

				final Set<EObject> eObjectsToValidate = new LinkedHashSet<EObject>();
				if (VControl.class.isInstance(domainModelReference.eContainer())) {
					final Set<UniqueSetting> settings = mappingProviderManager.getAllSettingsFor(domainModelReference,
						context.getDomainModel());
					for (final UniqueSetting setting : settings) {
						eObjectsToValidate.add(setting.getEObject());
					}
				} else {
					IObservableValue observableValue;
					try {
						observableValue = Activator.getDefault().getEMFFormsDatabinding()
							.getObservableValue(domainModelReference, context.getDomainModel());
					} catch (final DatabindingFailedException ex) {
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
						return;
					}
					final EObject observed = (EObject) ((IObserving) observableValue).getObserved();
					observableValue.dispose();
					eObjectsToValidate.add(observed);
				}
				validate(eObjectsToValidate);

			}
		}

		@Override
		public void notifyRemove(Notifier notifier) {
			// do nothing
		}

	}

	/**
	 * The {@link ValidationDomainModelChangeListener} for the domain model.
	 *
	 */
	private class ValidationDomainModelChangeListener implements ModelChangeAddRemoveListener {

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (ValidationNotification.class.isInstance(notification.getRawNotification())) {
				validate(notification.getNotifier());
				return;
			}

			if (isIgnore(notification)) {
				return;
			}

			final Notification rawNotification = notification.getRawNotification();
			final int eventType = rawNotification.getEventType();

			// Special cases
			if (eventType == Notification.ADD || eventType == Notification.ADD_MANY) {
				handleAdd(notification);
				return;
			} else if (eventType == Notification.REMOVE || eventType == Notification.REMOVE_MANY) {
				handleRemove(notification);
				return;
			}

			// Default case
			validate(notification.getNotifier());
			if (EReference.class.isInstance(notification.getStructuralFeature())) {
				if (notification.getRawNotification().getNewValue() != null) {
					final Object newValue = notification.getRawNotification().getNewValue();
					/*
					 * unset on a list has a boolean as a new value. therefore we need to check if new value is an
					 * EObject
					 */
					if (EObject.class.isInstance(newValue)) {
						validate((EObject) newValue);
					}
				}
			}
		}

		/**
		 * Indicates whether the given {@link ModelChangeNotification} should be ignored.
		 *
		 * @param notification the {@link ModelChangeNotification} to check.
		 * @return {@code true} if the given notification should be ignored, {@code false} otherwise.
		 */
		private boolean isIgnore(ModelChangeNotification notification) {
			final int eventType = notification.getRawNotification().getEventType();
			if (eventType == Notification.REMOVING_ADAPTER) {
				return true;
			}
			if (eventType == Notification.SET) {
				if (EReference.class.isInstance(notification.getStructuralFeature())) {
					final EReference eReference = EReference.class.cast(notification.getStructuralFeature());
					if (eReference.isContainer() && notification.getRawNotification().getNewValue() == null) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Handles the case when the given {@link ModelChangeNotification} originates from an ADD.
		 *
		 * @param notification
		 *            the {@link ModelChangeNotification} to handle.
		 */
		@SuppressWarnings("unchecked")
		private void handleAdd(ModelChangeNotification notification) {
			final Set<EObject> toValidate = new LinkedHashSet<EObject>();
			toValidate.add(notification.getNotifier());

			// in case of not containment references
			if (EReference.class.isInstance(notification.getStructuralFeature())) {
				if (Notification.ADD == notification.getRawNotification().getEventType()) {
					toValidate.addAll(getAllEObjects((EObject) notification.getRawNotification().getNewValue()));
				} else {
					toValidate.addAll((Collection<EObject>) notification.getRawNotification().getNewValue());
				}

			}
			validate(toValidate);
		}

		/**
		 * Handles the case when the given {@link ModelChangeNotification} originates from a REMOVE.
		 *
		 * @param notification
		 *            the {@link ModelChangeNotification} to handle.
		 */
		private void handleRemove(ModelChangeNotification notification) {
			final Notification rawNotification = notification.getRawNotification();
			if (rawNotification.getEventType() == Notification.REMOVE
				&& EReference.class.isInstance(rawNotification.getFeature())) {
				cleanControlDiagnostics(EObject.class.cast(notification.getNotifier()),
					EReference.class.cast(rawNotification.getFeature()),
					EObject.class.cast(rawNotification.getOldValue()));
			}
			// TODO JF since we now have an indexed dmr, this should clean diagnostics, too, doesn't it?
			validate(notification.getNotifier());
		}

		@Override
		public void notifyAdd(Notifier notifier) {
			if (notifier == context.getDomainModel()) {
				validate(getAllEObjectsToValidate());
			}
		}

		@Override
		public void notifyRemove(Notifier notifier) {
		}

	}

	private final Set<ValidationProvider> validationProviders = new LinkedHashSet<ValidationProvider>();
	private ValidationDomainModelChangeListener domainChangeListener;
	private ViewModelChangeListener viewChangeListener;
	private ViewModelContext context;
	private final Queue<EObject> validationQueue = new LinkedList<EObject>();
	private final Set<EObject> validated = new LinkedHashSet<EObject>();
	private boolean validationRunning;
	private final Map<UniqueSetting, VDiagnostic> currentUpdates = new LinkedHashMap<UniqueSetting, VDiagnostic>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		mappingProviderManager = context.getService(EMFFormsMappingProviderManager.class);
		controlMapper = context.getService(EMFFormsSettingToControlMapper.class);
		final VElement renderable = context.getViewModel();

		if (renderable == null) {
			throw new IllegalStateException("View model must not be null"); //$NON-NLS-1$
		}

		final EObject domainModel = context.getDomainModel();

		if (domainModel == null) {
			throw new IllegalStateException("Domain model must not be null"); //$NON-NLS-1$
		}

		readValidationProvider();

		domainChangeListener = new ValidationDomainModelChangeListener();
		viewChangeListener = new ViewModelChangeListener();
		context.registerDomainChangeListener(domainChangeListener);
		context.registerViewChangeListener(viewChangeListener);
		context.registerEMFFormsContextListener(this);
		// validate(getAllEObjects(domainModel));
	}

	private void cleanControlDiagnostics(EObject parent, EReference parentReference, EObject removedEObject) {
		final Set<VElement> controls = controlMapper
			.getControlsFor(UniqueSetting.createSetting(parent, parentReference));
		if (controls == null) {
			return;
		}
		for (final VElement vControl : controls) {
			if (vControl == null) {
				continue;
			}
			if (vControl.getDiagnostic() == null) {
				continue;
			}
			final Set<Object> diagnosticsToRemove = new LinkedHashSet<Object>();
			for (final Object diagnosticObject : vControl.getDiagnostic().getDiagnostics()) {
				final Diagnostic diagnostic = Diagnostic.class.cast(diagnosticObject);
				if (diagnostic.getData().size() < 1) {
					continue;
				}
				if (removedEObject.equals(getFirstInternalEObject(diagnostic.getData()))) {
					diagnosticsToRemove.add(diagnostic);
				}
			}
			vControl.getDiagnostic().getDiagnostics().removeAll(diagnosticsToRemove);
		}
	}

	private void readValidationProvider() {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.validation.validationProvider"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final ValidationProvider validationProvider = (ValidationProvider) e.createExecutableExtension("class"); //$NON-NLS-1$
				validationProviders.add(validationProvider);
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		context.unregisterEMFFormsContextListener(this);
		context.unregisterDomainChangeListener(domainChangeListener);
		context.unregisterViewChangeListener(viewChangeListener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 3;
	}

	/**
	 * Returns a collection of all direct and indirect child-EObjects including the parent.
	 *
	 * @param eObject the parent
	 * @return all eobjects
	 */
	private Collection<EObject> getAllEObjects(EObject eObject) {
		final List<EObject> result = new ArrayList<EObject>();
		result.add(eObject);
		final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(eObject, false);
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	private Collection<EObject> getAllEObjectsToValidate() {
		return getAllEObjectsToValidate(controlMapper);
	}

	private static Collection<EObject> getAllEObjectsToValidate(EMFFormsSettingToControlMapper controlMapper) {
		return controlMapper.getEObjectsWithSettings();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#validate(java.util.Collection)
	 */
	@Override
	public void validate(Collection<EObject> eObjects) {

		for (final EObject eObject : eObjects) {
			if (validated.contains(eObject)) {
				continue;
			}
			validated.add(eObject);
			validationQueue.offer(eObject);
		}
		processValidationQueue();

	}

	/**
	 * Validate the given eObject.
	 *
	 * @param eObject the eObject to validate
	 */
	public void validate(EObject eObject) {
		if (!validated.contains(eObject)) {
			validated.add(eObject);
			validationQueue.offer(eObject);
			processValidationQueue();
		}
	}

	private void processValidationQueue() {
		if (!initialized) {
			return;
		}
		// prohibit reentry in recursion
		if (validationRunning) {
			return;
		}
		validationRunning = true;
		EObject toValidate;
		while ((toValidate = validationQueue.poll()) != null) {
			validateAndCollectSettings(toValidate);
		}
		update();
		notifyListeners();
		currentUpdates.clear();
		validated.clear();
		validationRunning = false;
	}

	/**
	 * Notifies all listeners.
	 */
	public void notifyListeners() {
		if (validationListeners.size() > 0) {
			final Set<Diagnostic> result = getDiagnosticResult();
			for (final ViewValidationListener l : validationListeners) {
				l.onNewValidation(result);
			}
		}
	}

	private void update() {
		final Map<VElement, VDiagnostic> controlDiagnosticMap = new LinkedHashMap<VElement, VDiagnostic>();
		for (final UniqueSetting uniqueSetting : currentUpdates.keySet()) {
			final Set<VElement> controls = controlMapper.getControlsFor(uniqueSetting);
			if (controls == null) {
				continue;
			}

			for (final VElement control : controls) {
				if (!controlDiagnosticMap.containsKey(control)) {
					controlDiagnosticMap.put(control, VViewFactory.eINSTANCE.createDiagnostic());
				}
				// TODO Performance
				controlDiagnosticMap.get(control).getDiagnostics()
					.removeAll(currentUpdates.get(uniqueSetting).getDiagnostics());
				controlDiagnosticMap.get(control).getDiagnostics()
					.addAll(currentUpdates.get(uniqueSetting).getDiagnostics());

				// add all diagnostics of control which are not in the currentUpdates
				if (control.getDiagnostic() == null) {
					continue;
				}

				for (final Object diagnosticObject : control.getDiagnostic().getDiagnostics()) {
					final Diagnostic diagnostic = Diagnostic.class.cast(diagnosticObject);
					if (diagnostic.getData().size() < 2) {
						continue;
					}
					final EObject diagnosticEobject = getFirstInternalEObject(diagnostic.getData());
					final EStructuralFeature eStructuralFeature = getFirstEStructuralFeature(diagnostic.getData());
					if (diagnosticEobject == null || eStructuralFeature == null) {
						continue;
					}
					// TODO performance
					if (!isObjectStillValid(diagnosticEobject)) {
						continue;
					}
					final UniqueSetting uniqueSetting2 = UniqueSetting.createSetting(
						diagnosticEobject, eStructuralFeature);
					if (!currentUpdates.containsKey(uniqueSetting2)) {
						controlDiagnosticMap.get(control).getDiagnostics().add(diagnosticObject);
					}

				}

			}
		}

		updateAndPropagate(controlDiagnosticMap);
	}

	private boolean isObjectStillValid(EObject diagnosticEobject) {
		return controlMapper.hasControlsFor(diagnosticEobject);
	}

	private void updateAndPropagate(Map<VElement, VDiagnostic> controlDiagnosticMap) {
		for (final VElement control : controlDiagnosticMap.keySet()) {

			control.setDiagnostic(controlDiagnosticMap.get(control));

			reevaluateToTop(control.eContainer(), controlDiagnosticMap);
		}
	}

	private void reevaluateToTop(EObject parent, Map<VElement, VDiagnostic> controlDiagnosticMap) {

		while (parent != null) {
			final EObject newParent = parent.eContainer();
			if (!VElement.class.isInstance(parent)) {
				parent = newParent;
				continue;
			}
			final VElement vElement = (VElement) parent;

			final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
			if (controlDiagnosticMap.containsKey(vElement)) {
				vDiagnostic.getDiagnostics().addAll(controlDiagnosticMap.get(vElement).getDiagnostics());
			}

			for (final EObject eObject : vElement.eContents()) {
				if (!VElement.class.isInstance(eObject)) {
					continue;
				}
				final VElement childElement = (VElement) eObject;
				// check that the child is visible and enabled
				if (childElement.getDiagnostic() != null && childElement.isEnabled() && childElement.isVisible()) {
					vDiagnostic.getDiagnostics().addAll(childElement.getDiagnostic().getDiagnostics());
				}
			}
			vElement.setDiagnostic(vDiagnostic);
			parent = newParent;
		}
	}

	private void validateAndCollectSettings(EObject eObject) {
		final Diagnostic diagnostic = getDiagnosticForEObject(eObject);
		for (final EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(eObject, feature);
			if (!currentUpdates.containsKey(uniqueSetting)) {
				currentUpdates.put(uniqueSetting, VViewFactory.eINSTANCE.createDiagnostic());
			}
		}
		analyzeDiagnostic(diagnostic);

	}

	private void analyzeDiagnostic(Diagnostic diagnostic) {
		if (diagnostic.getData().size() > 1) {

			final InternalEObject internalEObject = getFirstInternalEObject(diagnostic.getData());
			final EStructuralFeature eStructuralFeature = getFirstEStructuralFeature(diagnostic.getData());
			if (internalEObject == null || eStructuralFeature == null) {
				return;
			}
			if (!internalEObject.eClass().getEAllStructuralFeatures().contains(eStructuralFeature)) {
				Activator.getDefault().getReportService()
					.report(new AbstractReport(
						MessageFormat.format(
							"No Setting can be created for Diagnostic {0} since the EObject's EClass does not contain the Feature.", //$NON-NLS-1$
							diagnostic),
						IStatus.INFO));
				return;
			}
			final Setting setting = internalEObject.eSetting(eStructuralFeature);
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
			if (!currentUpdates.containsKey(uniqueSetting)) {
				currentUpdates.put(uniqueSetting, VViewFactory.eINSTANCE.createDiagnostic());
			}
			currentUpdates.get(uniqueSetting).getDiagnostics().add(diagnostic);

		} else {
			for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
				analyzeDiagnostic(childDiagnostic);
			}
		}
	}

	private EStructuralFeature getFirstEStructuralFeature(List<?> data) {
		// Exclude first object for cases when we validate an EStructuralFeature.
		for (final Object object : data.subList(1, data.size())) {
			if (EStructuralFeature.class.isInstance(object)) {
				return EStructuralFeature.class.cast(object);
			}
		}
		return null;
	}

	private InternalEObject getFirstInternalEObject(List<?> data) {
		for (final Object object : data) {
			if (InternalEObject.class.isInstance(object)) {
				return InternalEObject.class.cast(object);
			}
		}
		return null;
	}

	/**
	 * Computes the {@link Diagnostic} for the given eObject.
	 *
	 * @param object the eObject to validate
	 * @return the diagnostic
	 */
	public Diagnostic getDiagnosticForEObject(EObject object) {
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(object.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(object);

		if (validator == null) {
			validator = new EObjectValidator();
		}
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, new ECPSubstitutionLabelProvider(adapterFactory));
		context.put(EValidator.class, validator);

		validator.validate(object, diagnostics, context);

		adapterFactory.dispose();

		final Map<EStructuralFeature, DiagnosticChain> diagnosticMap = new LinkedHashMap<EStructuralFeature, DiagnosticChain>();
		for (final Diagnostic child : diagnostics.getChildren()) {
			if (DiagnosticChain.class.isInstance(child) && checkDiagnosticData(child)) {
				diagnosticMap.put(getFirstEStructuralFeature(child.getData()), (DiagnosticChain) child);
			}
		}

		for (final ValidationProvider validationProvider : validationProviders) {
			final List<Diagnostic> additionValidation = validationProvider.validate(object);
			for (final Diagnostic additionDiagnostic : additionValidation) {
				if (diagnosticMap.containsKey(getFirstEStructuralFeature(additionDiagnostic.getData()))) {
					diagnosticMap.get(getFirstEStructuralFeature(additionDiagnostic.getData())).add(additionDiagnostic);
				} else {
					diagnostics.add(additionDiagnostic);
				}

			}
		}
		return diagnostics;
	}

	private boolean checkDiagnosticData(Diagnostic diagnostic) {
		final List<?> data = diagnostic.getData();
		if (data.size() < 2) {
			return false;
		}
		if (getFirstInternalEObject(data) == null) {
			return false;
		}
		if (getFirstEStructuralFeature(data) == null) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#addValidationProvider(org.eclipse.emf.ecp.view.spi.validation.ValidationProvider)
	 */
	@Override
	public void addValidationProvider(ValidationProvider validationProvider) {
		addValidationProvider(validationProvider, true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#addValidationProvider(org.eclipse.emf.ecp.view.spi.validation.ValidationProvider,
	 *      boolean)
	 */
	@Override
	public void addValidationProvider(ValidationProvider validationProvider, boolean revalidate) {
		validationProviders.add(validationProvider);
		if (revalidate) {
			validate(getAllEObjectsToValidate());
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#removeValidationProvider(org.eclipse.emf.ecp.view.spi.validation.ValidationProvider)
	 */
	@Override
	public void removeValidationProvider(ValidationProvider validationProvider) {
		removeValidationProvider(validationProvider, true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#removeValidationProvider(org.eclipse.emf.ecp.view.spi.validation.ValidationProvider,
	 *      boolean)
	 */
	@Override
	public void removeValidationProvider(ValidationProvider validationProvider, boolean revalidate) {
		validationProviders.remove(validationProvider);
		if (revalidate) {
			validate(getAllEObjectsToValidate());
		}
	}

	private final Set<ViewValidationListener> validationListeners = new LinkedHashSet<ViewValidationListener>();
	private EMFFormsMappingProviderManager mappingProviderManager;
	private EMFFormsSettingToControlMapper controlMapper;
	private boolean initialized;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#registerValidationListener(org.eclipse.emf.ecp.view.spi.validation.ViewValidationListener)
	 */
	@Override
	public void registerValidationListener(ViewValidationListener listener) {
		validationListeners.add(listener);

		listener.onNewValidation(getDiagnosticResult());
	}

	private Set<Diagnostic> getDiagnosticResult() {
		final Set<Diagnostic> result = new LinkedHashSet<Diagnostic>();
		final VDiagnostic diagnostic = context.getViewModel().getDiagnostic();
		if (diagnostic != null) {
			for (final Object diagObject : diagnostic.getDiagnostics()) {
				result.add((Diagnostic) diagObject);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.validation.ValidationService#deregisterValidationListener(org.eclipse.emf.ecp.view.spi.validation.ViewValidationListener)
	 */
	@Override
	public void deregisterValidationListener(ViewValidationListener listener) {
		validationListeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService#childViewModelContextAdded(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void childViewModelContextAdded(ViewModelContext childContext) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextAdded(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		validate(getAllEObjectsToValidate());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextInitialised()
	 */
	@Override
	public void contextInitialised() {
		initialized = true;
		validate(getAllEObjectsToValidate());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextDispose()
	 */
	@Override
	public void contextDispose() {
		// do nothing
	}

}
