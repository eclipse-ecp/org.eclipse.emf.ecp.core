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
 * Eugen - initial API and implementation
 * Christian W. Damus - bugs 533522, 543160, 545686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import static org.eclipse.emf.ecp.view.spi.validation.ValidationServiceConstants.PROPAGATION_LIMIT_KEY;
import static org.eclipse.emf.ecp.view.spi.validation.ValidationServiceConstants.PROPAGATION_UNLIMITED_VALUE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.eclipse.emf.ecp.view.spi.validation.ValidationService;
import org.eclipse.emf.ecp.view.spi.validation.ViewValidationListener;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.common.internal.validation.DiagnosticHelper;
import org.eclipse.emfforms.common.spi.validation.ValidationResultListener;
import org.eclipse.emfforms.common.spi.validation.filter.AbstractSimpleFilter;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.validation.DiagnosticFrequencyMap;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.controlmapper.SubControlMapper;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProviderManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.osgi.util.NLS;

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
					final VDomainModelReference domainModelReference = control.getDomainModelReference();
					if (domainModelReference == null) {
						return;
					}
					try {
						handleControlNotification(notification, control, domainModelReference);
					} catch (final DatabindingFailedException ex) {
						reportService.report(new DatabindingFailedReport(ex));
						return;
					}

				}
			}
			if (!VElement.class.isInstance(notification.getNotifier())) {
				return;
			}
			switch (notification.getRawNotification().getEventType()) {
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				final Map<VElement, VDiagnostic> map = Collections.emptyMap();
				reevaluateToTop((VElement) notification.getNotifier(), map);
				break;
			default:
				break;
			}
		}

		/**
		 * @param notification
		 * @param control
		 * @param domainModelReference
		 * @throws DatabindingFailedException
		 */
		private void handleControlNotification(ModelChangeNotification notification, VControl control,
			VDomainModelReference domainModelReference) throws DatabindingFailedException {
			if (VViewPackage.eINSTANCE.getElement_Enabled() == notification.getRawNotification().getFeature()) {
				control.setDiagnostic(null);
			}

			final Set<UniqueSetting> settingsForControl = controlMapper.getSettingsForControl(control);
			final Set<EObject> eObjectsToValidate = new LinkedHashSet<EObject>();
			for (final UniqueSetting setting : settingsForControl) {
				eObjectsToValidate.add(setting.getEObject());
			}
			validate(eObjectsToValidate);

		}

		@Override
		public void notifyAdd(Notifier notifier) {
			if (VDomainModelReference.class.isInstance(notifier)
				&& !VDomainModelReference.class.isInstance(EObject.class.cast(notifier).eContainer())
				&& !VDomainModelReferenceSegment.class.isInstance(EObject.class.cast(notifier).eContainer())) {
				final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(notifier);
				if (domainModelReference == null) {
					return;
				}

				final Set<EObject> eObjectsToValidate = new LinkedHashSet<EObject>();
				if (VControl.class.isInstance(domainModelReference.eContainer())) {
					final Set<UniqueSetting> settings = mappingProviderManager.getAllSettingsFor(domainModelReference,
						context.getDomainModel());
					for (final UniqueSetting setting : settings) {
						final EObject object = setting.getEObject();
						if (object != null) {
							eObjectsToValidate.add(object);
						}
					}
				} else {
					@SuppressWarnings("rawtypes")
					IObservableValue observableValue;
					try {
						observableValue = context.getService(EMFFormsDatabinding.class)
							.getObservableValue(domainModelReference, context.getDomainModel());
					} catch (final DatabindingFailedException ex) {
						reportService.report(new DatabindingFailedReport(ex));
						return;
					}
					final EObject observed = (EObject) ((IObserving) observableValue).getObserved();
					observableValue.dispose();
					if (observed != null) {
						eObjectsToValidate.add(observed);
					}

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
			if (notification.getRawNotification().isTouch()) {
				return true;
			}
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
				validate(getAllEObjectsToValidate(context));
			}
		}

		@Override
		public void notifyRemove(Notifier notifier) {
		}

	}

	private org.eclipse.emfforms.common.spi.validation.ValidationService validationService;
	private ValidationDomainModelChangeListener domainChangeListener;
	private ViewModelChangeListener viewChangeListener;
	private ViewModelContext context;
	private final Queue<EObject> validationQueue = new ConcurrentLinkedSetQueue<EObject>();
	private final Set<EObject> validated = Collections.newSetFromMap(new ConcurrentHashMap<EObject, Boolean>());
	private final AtomicBoolean validationRunning = new AtomicBoolean(false);

	// In a typical application, these lists will usually have zero or one element. In
	// any case, uniqueness is ensured by the validation algorithm and so needs not be
	// enforced by the collection, so just use simple lists
	private final Map<UniqueSetting, List<Diagnostic>> currentUpdates = new ConcurrentHashMap<UniqueSetting, List<Diagnostic>>();
	private final Function<Object, List<Diagnostic>> diagnosticListFactory = __ -> new ArrayList<>(3);

	private ComposedAdapterFactory adapterFactory;
	private ReportService reportService;
	private EMFFormsLocalizationService l10n;
	private ThresholdDiagnostic.Factory placeholderFactory;

	// Maximal number of problems to propagate up the view hierarchy, or negative for no limit
	private int propagationThreshold;

	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		reportService = context.getService(ReportService.class);
		l10n = context.getService(EMFFormsLocalizationService.class);
		placeholderFactory = new ThresholdDiagnostic.Factory(l10n);
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

		propagationThreshold = getPropagationThreshold();

		validationService = new org.eclipse.emfforms.common.internal.validation.ValidationServiceImpl();
		validationService.registerValidationFilter(new AbstractSimpleFilter() {
			@Override
			public boolean skipValidation(EObject eObject) {
				return validated.contains(eObject);
			}

			@Override
			public boolean ignoreDiagnostic(EObject eObject, Diagnostic diagnostic) {
				return !controlMapper.hasControlsFor(eObject);
			}
		});
		validationService.registerValidationResultListener(new ValidationResultListener() {
			@Override
			public void onValidate(EObject eObject, Diagnostic diagnostic) {
				validated.add(eObject);
			}

			@Override
			public void afterValidate(EObject eObject, Diagnostic diagnostic) {
				// nothing to do here
			}
		});

		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		final ViewSubstitutionLabelProviderFactory substitutionLabelProviderFactory = getSubstitutionLabelProviderFactory();

		SubstitutionLabelProvider substitutionLabelProvider;
		if (substitutionLabelProviderFactory != null) {
			substitutionLabelProvider = substitutionLabelProviderFactory
				.createSubstitutionLabelProvider(adapterFactory);
		} else {
			substitutionLabelProvider = new ECPSubstitutionLabelProvider(adapterFactory);
		}

		validationService.setSubstitutionLabelProvider(substitutionLabelProvider);

		registerValidationProviders();

		domainChangeListener = new ValidationDomainModelChangeListener();
		viewChangeListener = new ViewModelChangeListener();
		context.registerDomainChangeListener(domainChangeListener);
		context.registerViewChangeListener(viewChangeListener);
		context.registerEMFFormsContextListener(this);
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
				if (removedEObject.equals(DiagnosticHelper.getFirstInternalEObject(diagnostic.getData()))) {
					diagnosticsToRemove.add(diagnostic);
				}
			}
			vControl.getDiagnostic().getDiagnostics().removeAll(diagnosticsToRemove);
		}
	}

	private void registerValidationProviders() {
		for (final ValidationProvider provider : ValidationProviderHelper.fetchValidationProviders()) {
			validationService.addValidator(provider);
		}
	}

	@Override
	public void dispose() {
		context.unregisterEMFFormsContextListener(this);
		context.unregisterDomainChangeListener(domainChangeListener);
		context.unregisterViewChangeListener(viewChangeListener);
		adapterFactory.dispose();
	}

	@Override
	public int getPriority() {
		return 1;
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

	private Collection<EObject> getAllEObjectsToValidate(ViewModelContext context) {
		return getAllEObjectsToValidate(context, controlMapper);
	}

	private static Collection<EObject> getAllEObjectsToValidate(ViewModelContext context,
		EMFFormsSettingToControlMapper controlMapper) {

		if (context.getParentContext() == null || !(controlMapper instanceof SubControlMapper)) {
			// Easy: validate the whole model
			return controlMapper.getEObjectsWithSettings();
		}

		final SubControlMapper subMapper = (SubControlMapper) controlMapper;
		final Collection<EObject> result = subMapper.getEObjectsWithSettings(context.getViewModel());

		// And all container objects that have settings
		for (ViewModelContext parent = context.getParentContext(); parent != null; parent = parent
			.getParentContext()) {

			final EObject parentObject = parent.getDomainModel();
			if (controlMapper.hasControlsFor(parentObject)) {
				result.add(parentObject);
			}
		}

		return result;
	}

	@Override
	public void validate(Collection<EObject> eObjects) {
		validationQueue.addAll(eObjects);
		processValidationQueue();
	}

	/**
	 * Validate the given eObject.
	 *
	 * @param eObject the eObject to validate
	 */
	public void validate(EObject eObject) {
		/**
		 * We are using a queue here to allow validators to add additional eObjects
		 * to the current validation run. This is because we actually want a diagnostics aggregate,
		 * otherwise consecutive runs would replace already existing diagnostics on the UI.
		 * This is probably not the best way to solve this problem, but it will do for now.
		 */
		validationQueue.offer(eObject);
		processValidationQueue();
	}

	private void processValidationQueue() {
		if (!initialized) {
			return;
		}
		// prohibit re-entry in recursion
		if (!validationRunning.compareAndSet(false, true)) {
			return;
		}
		EObject toValidate;
		while ((toValidate = validationQueue.poll()) != null) {
			validateAndCollectSettings(toValidate);
		}
		update();
		notifyListeners();
		currentUpdates.clear();
		validated.clear();
		validationRunning.compareAndSet(true, false);
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
		// prepare Map
		final Map<VElement, Set<UniqueSetting>> vElementToSettingMap = prepareVElementToSettingMap();

		final Map<VElement, VDiagnostic> controlDiagnosticMap = new LinkedHashMap<VElement, VDiagnostic>();

		for (final VElement control : vElementToSettingMap.keySet()) {
			updateControlDiagnostics(control, vElementToSettingMap, controlDiagnosticMap);

			// add all diagnostics of control which are not in the currentUpdates
			if (control.getDiagnostic() == null) {
				continue;
			}

			for (final Object diagnosticObject : control.getDiagnostic().getDiagnostics()) {
				final Diagnostic diagnostic = Diagnostic.class.cast(diagnosticObject);
				if (diagnostic.getData().size() < 2) {
					continue;
				}
				final EObject diagnosticEobject = DiagnosticHelper.getFirstInternalEObject(diagnostic.getData());
				final EStructuralFeature eStructuralFeature = DiagnosticHelper
					.getEStructuralFeature(diagnostic.getData());
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

		updateAndPropagate(controlDiagnosticMap);
	}

	private void updateControlDiagnostics(final VElement control,
		final Map<VElement, Set<UniqueSetting>> vElementToSettingMap,
		final Map<VElement, VDiagnostic> controlDiagnosticMap) {

		if (!controlDiagnosticMap.containsKey(control)) {
			controlDiagnosticMap.put(control, VViewFactory.eINSTANCE.createDiagnostic());
		}

		for (final UniqueSetting uniqueSetting : vElementToSettingMap.get(control)) {
			final List<Diagnostic> diagnostics = currentUpdates.get(uniqueSetting);
			if (!diagnostics.isEmpty()) {
				controlDiagnosticMap.get(control).getDiagnostics().addAll(diagnostics);
			}
		}
	}

	private Map<VElement, Set<UniqueSetting>> prepareVElementToSettingMap() {
		final Map<VElement, Set<UniqueSetting>> result = new LinkedHashMap<VElement, Set<UniqueSetting>>();
		final Function<VElement, Set<UniqueSetting>> setFactory = __ -> new LinkedHashSet<>();

		for (final UniqueSetting uniqueSetting : currentUpdates.keySet()) {
			final Set<VElement> controls = controlMapper.getControlsFor(uniqueSetting);
			if (controls == null || controls.isEmpty()) {
				continue;
			}
			for (final VElement control : controls) {
				result.computeIfAbsent(control, setFactory).add(uniqueSetting);
			}
		}
		return result;
	}

	private boolean isObjectStillValid(EObject diagnosticEobject) {
		return controlMapper.hasControlsFor(diagnosticEobject);
	}

	/**
	 * Update the diagnostics attached to the given mapped view-model elements and propagate
	 * roll-ups of those diagnostics up the view model hierarchy.
	 *
	 * @param controlDiagnosticMap the mapping of source diagnostics by (leaf) control
	 */
	private void updateAndPropagate(Map<VElement, VDiagnostic> controlDiagnosticMap) {
		// First, set every control's diagnostics
		controlDiagnosticMap.forEach(VElement::setDiagnostic);

		// Breadth-first climb to the top of the view model tree, layer by layer. First,
		// partition the controls into layers by depth. This map is sorted by decreasing
		// depth (deepest first, to start there)
		final NavigableMap<Integer, Set<VElement>> depthMap = mapByDepth(controlDiagnosticMap.keySet());
		reevaluateToTop(depthMap, controlDiagnosticMap);
	}

	/**
	 * Compute a partitioning of view-model {@code elements} by their depth in the model.
	 * This is effectively the set of layers of the tree, collecting all elements at the same
	 * depth into subsets (these being the layers at each depth).
	 *
	 * @param elements the elements of the view model to partition
	 * @return the partition into layers, in decreasing order of depth; so, ordered from deepest,
	 *         the bottom layer, to shallowest, the root
	 */
	private NavigableMap<Integer, Set<VElement>> mapByDepth(Collection<? extends VElement> elements) {
		final Function<Integer, Set<VElement>> factory = __ -> new LinkedHashSet<>();
		final NavigableMap<Integer, Set<VElement>> result = new TreeMap<>(
			Comparator.<Integer> naturalOrder().reversed());

		for (final VElement next : elements) {
			result.computeIfAbsent(depth(next), factory).add(next);
		}

		return result;
	}

	/**
	 * Compute the depth of an {@code object} in its containing tree.
	 *
	 * @param object an object
	 * @return its depth (zero-based at the root)
	 */
	private int depth(EObject object) {
		int result = 0;
		for (EObject container = object.eContainer(); container != null; container = container.eContainer()) {
			result = result + 1;
		}
		return result;
	}

	/**
	 * Obtain the unique set of containers of a bunch of {@code elements} view model.
	 *
	 * @param elements elements in the view model
	 * @return the complete set of view-model elements covering the containers of the {@code elements}
	 */
	private Set<VElement> uniqueContainers(Collection<? extends VElement> elements) {
		return elements.stream().map(EObject::eContainer)
			.filter(VElement.class::isInstance).map(VElement.class::cast)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Perform a breadth-first climb to the top of the view model tree, layer by layer,
	 * recalculating roll-ups of diagnostics for every container element to aggregate
	 * all of the diagnostics reported in its sub-tree.
	 *
	 * @param depthMap a mapping of layers of elements in the view model by depth, in
	 *            order from bottom (deepest layer) to top (shallowest layer)
	 * @param controlDiagnosticMap the mapping of source diagnostics by (leaf) control
	 */
	private void reevaluateToTop(NavigableMap<Integer, Set<VElement>> depthMap,
		Map<VElement, VDiagnostic> controlDiagnosticMap) {
		Map.Entry<Integer, Set<VElement>> layer;

		for (layer = depthMap.pollFirstEntry(); layer != null; layer = depthMap.pollFirstEntry()) {
			final int depth = layer.getKey();

			final Set<VElement> containers = uniqueContainers(layer.getValue());
			containers.forEach(container -> reevaluate(container, controlDiagnosticMap));

			// These containers are elements at the next layer up, so we need to iterate over them
			final int depthUp = depth - 1;
			if (depthUp >= 0) {
				final Set<VElement> layerUp = depthMap.get(depthUp);
				if (layerUp != null) {
					layerUp.addAll(containers);
				} else {
					depthMap.put(depthUp, containers);
				}
			}
		}
	}

	/**
	 * Recalculate the roll-up of diagnostics fo a given element in the view model tree.
	 *
	 * @param vElement an element in the view model tree
	 * @param controlDiagnosticMap the mapping of source diagnostics by (leaf) control
	 */
	private void reevaluate(VElement vElement, Map<VElement, VDiagnostic> controlDiagnosticMap) {
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		if (controlDiagnosticMap.containsKey(vElement)) {
			vDiagnostic.getDiagnostics().addAll(controlDiagnosticMap.get(vElement).getDiagnostics());
		}

		// Propagate problems from children
		final DiagnosticFrequencyMap freq = getFrequencyMap(vElement);
		for (final Iterator<EObject> iter = vElement.eContents().iterator(); iter.hasNext();) {
			final EObject eObject = iter.next();
			if (!VElement.class.isInstance(eObject)) {
				continue;
			}

			final VElement childElement = (VElement) eObject;
			// check that the child is visible and enabled
			if (childElement.getDiagnostic() != null && childElement.isEffectivelyEnabled()
				&& childElement.isVisible()) {

				final List<?> childDiagnostics = childElement.getDiagnostic().getDiagnostics();

				// Note that we cannot fill up if we didn't add something
				if (freq.addAll(childDiagnostics) && freq.isFull()
					&& freq.getDiscardedSeverity() > Diagnostic.WARNING) {

					// No need to scan further children because it can't get more
					// full than this with a greater discarded severity
					break;
				}
			}
		}
		if (!freq.isEmpty()) {
			freq.appendTo(vDiagnostic.getDiagnostics());
			appendPlaceholder(freq, vDiagnostic.getDiagnostics());
		}
		vElement.setDiagnostic(vDiagnostic);
	}

	/**
	 * Perform a breadth-first climb to the top of the view model tree, layer by layer,
	 * recalculating roll-ups of diagnostics for every container element starting from the
	 * given {@code element}. It is assumed unnecessary to recompute diagnostics for
	 * layers deeper than the {@code element} because only the {@code element} has changed.
	 * But the changes to its diagnostics necessarily can affect roll-ups for all elements
	 * above it in the tree.
	 *
	 * @param element an element in the view model for which to calculate its roll-up of
	 *            diagnostics and, consequently, the roll-ups for all elements above it
	 * @param controlDiagnosticMap the mapping of source diagnostics by (leaf) control
	 */
	private void reevaluateToTop(VElement element, Map<VElement, VDiagnostic> controlDiagnosticMap) {
		// Breadth-first climb to the top of the view model tree, layer by layer. First,
		// partition the controls into layers by depth. This map is sorted by decreasing
		// depth (deepest first, to start there)
		final NavigableMap<Integer, Set<VElement>> depthMap = mapByDepth(controlDiagnosticMap.keySet());

		// The initial set of containers to process is just the 'element'
		final Set<VElement> containers = Collections.singleton(element);

		// Everything below this element doesn't need to be re-evaluated: their diagnostics
		// are assumed unchanged
		final int cutoff = depth(element);
		depthMap.navigableKeySet().headSet(cutoff, true).clear();
		depthMap.put(cutoff, containers); // Start here

		// Re-evaluate this element
		reevaluate(element, controlDiagnosticMap);

		// And then the rest
		reevaluateToTop(depthMap, controlDiagnosticMap);
	}

	/**
	 * Get a frequency map appropriate for collecting the most severe (and throttled)
	 * diagnostics for a view model element.
	 *
	 * @param vElement the view model element
	 * @return its diagnostic frequency map
	 */
	DiagnosticFrequencyMap getFrequencyMap(VElement vElement) {
		final DiagnosticFrequencyMap result = propagationThreshold < 0 ? DiagnosticFrequencyMap.unlimited()
			: DiagnosticFrequencyMap.limitedTo(propagationThreshold);

		result.addDiagnosticFilter(placeholderFactory.notThresholdDiagnostic());

		return result;
	}

	/**
	 * If the {@code freq}uency map is {@link DiagnosticFrequencyMap#isFull() full}, then
	 * append one more diagnostic covering all those that were discarded (if any).
	 *
	 * @param freq a frequency map
	 * @param diagnostics diagnostics being collected
	 */
	private void appendPlaceholder(DiagnosticFrequencyMap freq, List<? super Diagnostic> diagnostics) {
		if (freq.getDiscardedSeverity() > Diagnostic.OK) {
			diagnostics.add(placeholderFactory.get(freq.getDiscardedSeverity()));
		}
	}

	private void validateAndCollectSettings(EObject eObject) {
		final long start = System.nanoTime();

		try {
			final Diagnostic diagnostic = validationService.validate(eObject);
			if (diagnostic == null) { // happens if the eObject is being filtered
				return;
			}
			for (final EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
				final UniqueSetting uniqueSetting = UniqueSetting.createSetting(eObject, feature);
				currentUpdates.computeIfAbsent(uniqueSetting, diagnosticListFactory);
			}
			analyzeDiagnostic(diagnostic);
		} finally {
			if (System.nanoTime() - start > 1000L * 1000L * 1000L) {
				reportService.report(new AbstractReport(MessageFormat.format(
					"Validation took longer than expected for EObject {0}", eObject, //$NON-NLS-1$
					IStatus.INFO)));
			}
		}
	}

	private void analyzeDiagnostic(Diagnostic diagnostic) {
		if (diagnostic.getData().size() > 1) {

			final InternalEObject internalEObject = DiagnosticHelper.getFirstInternalEObject(diagnostic.getData());
			final EStructuralFeature eStructuralFeature = DiagnosticHelper.getEStructuralFeature(diagnostic.getData());
			if (internalEObject == null || eStructuralFeature == null) {
				return;
			}
			if (!internalEObject.eClass().getEAllStructuralFeatures().contains(eStructuralFeature)) {
				reportService.report(new AbstractReport(
					MessageFormat.format(
						"No Setting can be created for Diagnostic {0} since the EObject's EClass does not contain the Feature.", //$NON-NLS-1$
						diagnostic),
					IStatus.INFO));
				return;
			}
			final Setting setting = internalEObject.eSetting(eStructuralFeature);
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
			currentUpdates.computeIfAbsent(uniqueSetting, diagnosticListFactory).add(diagnostic);
		} else {
			for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
				analyzeDiagnostic(childDiagnostic);
			}
		}
	}

	@Override
	public void addValidationProvider(ValidationProvider validationProvider) {
		addValidationProvider(validationProvider, true);
	}

	@Override
	public void addValidationProvider(ValidationProvider validationProvider, boolean revalidate) {
		validationService.addValidator(validationProvider);
		if (revalidate && context != null) {
			validate(getAllEObjectsToValidate(context));
		}
	}

	@Override
	public void removeValidationProvider(ValidationProvider validationProvider) {
		removeValidationProvider(validationProvider, true);
	}

	@Override
	public void removeValidationProvider(ValidationProvider validationProvider, boolean revalidate) {
		validationService.removeValidator(validationProvider);
		if (revalidate && context != null) {
			validate(getAllEObjectsToValidate(context));
		}
	}

	private final Set<ViewValidationListener> validationListeners = new LinkedHashSet<ViewValidationListener>();
	private EMFFormsMappingProviderManager mappingProviderManager;
	private EMFFormsSettingToControlMapper controlMapper;
	private boolean initialized;

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

	@Override
	public void deregisterValidationListener(ViewValidationListener listener) {
		validationListeners.remove(listener);
	}

	@Override
	public void childViewModelContextAdded(ViewModelContext childContext) {
		// do nothing
	}

	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		// We are getting this from a parent content that is a view-model context, so the
		// child really ought to be one, also
		if (childContext instanceof ViewModelContext) {
			validate(getAllEObjectsToValidate((ViewModelContext) childContext));
		}

		childContext.registerViewChangeListener(viewChangeListener);
	}

	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		// do nothing
	}

	@Override
	public void contextInitialised() {
		initialized = true;
		validate(getAllEObjectsToValidate(context));
	}

	@Override
	public void contextDispose() {
		// do nothing
	}

	/**
	 * Returns a {@link ViewSubstitutionLabelProviderFactory}, if any is registered.
	 *
	 * @return an instance of a {@link ViewSubstitutionLabelProviderFactory}, if any is available,
	 *         {@code null} otherwise
	 */
	protected ViewSubstitutionLabelProviderFactory getSubstitutionLabelProviderFactory() {
		if (context.hasService(ViewSubstitutionLabelProviderFactory.class)) {
			return context.getService(ViewSubstitutionLabelProviderFactory.class);
		}
		return null;
	}

	/**
	 * Get the problems propagation limit from the annotation view model context.
	 *
	 * @return the propagation limit, or {@code -1} if unlimited
	 *
	 * @precondition the {@link ViewModelContext} is already set
	 * @precondition the {@link ReportService} is available
	 * @precondition the {@link EMFFormsLocalizationService} is available
	 */
	private int getPropagationThreshold() {
		int result = -1; // Internal code for unlimited

		final Object value = context.getContextValue(PROPAGATION_LIMIT_KEY);
		if (value instanceof Integer) {
			final int intValue = (Integer) value;
			if (intValue < 0) {
				warn("ValidationServiceImpl_limitNegative", value); //$NON-NLS-1$
			} else {
				result = intValue;
			}
		} else if (value == null || PROPAGATION_UNLIMITED_VALUE.equals(value)) {
			return result;
		} else {
			warn("ValidationServiceImpl_limitUnknown", value); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * Issue a warning composed of the indicated localized string with positional {@code arguments}.
	 *
	 * @param messageKey key to look up in the host bundle's localizations
	 * @param arguments zero-indexed arguments to substitute, if any, in the string
	 */
	protected void warn(String messageKey, Object... arguments) {
		final String report = NLS.bind(l10n.getString(ValidationServiceImpl.class, messageKey), arguments);
		reportService.report(new AbstractReport(report, IStatus.WARNING));
	}

}
