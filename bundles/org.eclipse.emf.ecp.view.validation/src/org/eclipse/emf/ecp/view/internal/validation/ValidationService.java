/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Edgar Mueller - adoptions in order to avoid memory leaks
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * Validation service that, once instantiated, synchronizes the validation result of a model element with its
 * Renderable.
 * 
 * @author jfaltermeier
 * @author emueller
 */
public class ValidationService implements ViewModelService {

	private ViewModelContext context;
	private VElement renderable;

	private ModelChangeListener domainChangeListener;
	private ModelChangeListener viewChangeListener;

	private ViewValidator viewValidationGraph;
	private ValidationRegistry validationRegistry;

	private final List<ViewValidationListener> validationListener;

	private final Set<ValidationProvider> validationProviders = new LinkedHashSet<ValidationProvider>();

	/**
	 * Default constructor.
	 */
	public ValidationService() {
		validationListener = new ArrayList<ViewValidationListener>();
	}

	/**
	 * The {@link ModelChangeListener} for the view model.
	 * 
	 */
	class ViewModelChangeListener implements ModelChangeListener {

		private final EObject domainModel;

		/**
		 * Constructor.
		 * 
		 * @param domainModel
		 *            the domain model
		 */
		public ViewModelChangeListener(EObject domainModel) {
			this.domainModel = domainModel;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification)
		 */
		public void notifyChange(ModelChangeNotification notification) {
			// do nothing for now, not supported
			if (VElement.class.isInstance(notification.getRawNotification().getNotifier())) {
				if (notification.getRawNotification().getFeature() == VViewPackage.eINSTANCE
					.getElement_Diagnostic()) {

				}
				else if (EReference.class.isInstance(notification.getRawNotification().getFeature())
					&& VElement.class.isInstance(notification.getRawNotification().getNewValue())) {
				}
				else if (VViewPackage.eINSTANCE.getElement_Enabled() == notification.getRawNotification()
					.getFeature()
					|| VViewPackage.eINSTANCE.getElement_Visible() == notification.getRawNotification()
						.getFeature()) {
					if (VViewPackage.eINSTANCE.getControl() == notification.getNotifier().eClass()) {
						final VControl control = (VControl) notification.getNotifier();
						// final EObject controlDomainModel = validationRegistry.resolveDomainModel(domainModel,
						// control.getDomainModelReference().());
						// REFACTORING test
						final VDomainModelReference domainModelReference = control.getDomainModelReference();
						final Iterator<Setting> settings = domainModelReference.getIterator();
						while (settings.hasNext()) {
							viewValidationGraph.validate(settings.next().getEObject());
						}

					}
				}
			}
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyAdd(org.eclipse.emf.common.notify.Notifier)
		 */
		public void notifyAdd(Notifier notifier) {
			if (VElement.class.isInstance(notifier)) {
				final VElement renderable = (VElement) notifier;
				final EObject renderableParent = renderable.eContainer();
				if (VElement.class.isInstance(renderableParent)
					&& validationRegistry.containsRenderable((VElement) renderableParent)) {
					validationRegistry.register(domainModel, renderable);
					final Map<UniqueSetting, Set<VControl>> map = validationRegistry.getDomainToControlMapping(
						domainModel,
						renderable);
					viewValidationGraph.validateSettings(map.keySet());
				}
			}
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyRemove(org.eclipse.emf.common.notify.Notifier)
		 */
		public void notifyRemove(Notifier notifier) {
			if (VElement.class.isInstance(notifier)) {
				validationRegistry.removeRenderable((VElement) notifier);
				viewValidationGraph.removeRenderable((VElement) notifier);
			}
		}
	}

	/**
	 * The {@link ModelChangeListener} for the domain model.
	 * 
	 */
	class DomainModelChangeListener implements ModelChangeListener {

		private final EObject domainModel;

		/**
		 * Constructor.
		 * 
		 * @param domainModel
		 *            the domain model
		 */
		public DomainModelChangeListener(EObject domainModel) {
			this.domainModel = domainModel;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.context.ModelChangeNotification)
		 */
		public void notifyChange(ModelChangeNotification notification) {
			if (viewValidationGraph == null) {
				return; // ignore notifications during initialization
			}
			if (ValidationNotification.class.isInstance(notification.getRawNotification())) {
				viewValidationGraph.validate(getAllEObjects(notification.getNotifier()));
				return;
			}
			final Notification rawNotification = notification.getRawNotification();
			switch (rawNotification.getEventType()) {
			case Notification.ADD:
			case Notification.REMOVE:
				viewValidationGraph.validate(getAllEObjects(notification.getNotifier()));
				break;
			case Notification.ADD_MANY:
			case Notification.REMOVE_MANY:
				viewValidationGraph.validate(getAllEObjects(domainModel));

				break;
			default:
				viewValidationGraph.validate(getAllEObjects(notification.getNotifier()));
			}
			notifyListeners();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyAdd(org.eclipse.emf.common.notify.Notifier)
		 */
		public void notifyAdd(Notifier notifier) {
			// maybe null while init
			if (viewValidationGraph != null) {
				final EObject eObject = (EObject) notifier;
				validationRegistry.updateMappings(eObject);
				if (!validationRegistry.containsVElementForEObject(eObject)) {
					validationRegistry.register(eObject, renderable);
				}
				viewValidationGraph.validate(eObject);
			}
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext.ModelChangeListener#notifyRemove(org.eclipse.emf.common.notify.Notifier)
		 */
		public void notifyRemove(Notifier notifier) {
			final EObject eObject = (EObject) notifier;
			viewValidationGraph.removeDomainObject(eObject);
			final Set<VControl> removedControls = validationRegistry.removeDomainObject(eObject);
			for (final VControl vControl : removedControls) {
				viewValidationGraph.removeRenderable(vControl);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	public void instantiate(final ViewModelContext context) {
		this.context = context;

		renderable = context.getViewModel();

		if (renderable == null) {
			throw new IllegalStateException("View model must not be null"); //$NON-NLS-1$
		}

		final EObject domainModel = context.getDomainModel();

		if (domainModel == null) {
			throw new IllegalStateException("Domain model must not be null"); //$NON-NLS-1$
		}

		readValidationProvider();

		domainChangeListener = new DomainModelChangeListener(domainModel);
		viewChangeListener = new ViewModelChangeListener(domainModel);

		context.registerDomainChangeListener(domainChangeListener);
		context.registerViewChangeListener(viewChangeListener);

		init(renderable, domainModel);

		viewValidationGraph.validate(getAllEObjects(domainModel));

		notifyListeners();
	}

	/**
	 * 
	 */
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

	private void init(VElement view, EObject domainModel) {
		validationRegistry = new ValidationRegistry();
		validationRegistry.register(domainModel, view);
		viewValidationGraph = new ViewValidator(view, domainModel, validationRegistry, validationProviders);
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

	/**
	 * Registers a listener that will receive {@link Diagnostic}s with severity higher than {@link Diagnostic#OK}. After
	 * registration the listener's {@link ViewValidationListener#onNewValidation(Set)} will be called with current
	 * results.
	 * 
	 * @param listener the listener to register
	 */
	public void registerValidationListener(ViewValidationListener listener) {
		validationListener.add(listener);
		listener.onNewValidation(getDiagnosticResult());
	}

	/**
	 * Deregisters the given listener.
	 * 
	 * @param listener the listener to deregister
	 */
	public void deregisterValidationListener(ViewValidationListener listener) {
		validationListener.remove(listener);
	}

	private void notifyListeners() {
		if (validationListener.size() > 0) {
			final Set<Diagnostic> result = getDiagnosticResult();
			for (final ViewValidationListener l : validationListener) {
				l.onNewValidation(result);
			}

		}
	}

	private Set<Diagnostic> getDiagnosticResult() {
		final Set<Diagnostic> result = new LinkedHashSet<Diagnostic>();
		if (renderable.getDiagnostic().getHighestSeverity() > Diagnostic.OK) {
			for (final Object o : renderable.getDiagnostic().getDiagnostics()) {
				if (((Diagnostic) o).getSeverity() > Diagnostic.OK) {
					result.add((Diagnostic) o);
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	public void dispose() {
		context.unregisterDomainChangeListener(domainChangeListener);
		context.unregisterViewChangeListener(viewChangeListener);
	}

	/**
	 * Returns all values set for the provided domainObject.
	 * 
	 * @param domainObject the {@link EObject} to search the values for
	 * @return the set of all values currently associated with the provided {@link EObject}
	 */
	public Set<VDiagnostic> getAllDiagnostics(EObject domainObject) {
		return viewValidationGraph.getAllValues(domainObject);
	}

	/**
	 * Returns a Map containing all {@link EStructuralFeature EStructuralFeatures} and the corresponding value for the
	 * provided domainObject.
	 * 
	 * @param domainObject the {@link EObject} to search the map for
	 * @return a mapping between all {@link EStructuralFeature EStructuralFeatures} and its associated value, currently
	 *         associated with the provided {@link EObject}
	 */
	public Map<EStructuralFeature, VDiagnostic> getDiagnosticPerFeature(EObject domainObject) {
		return viewValidationGraph.getValuePerFeature(domainObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	public int getPriority() {
		return 3;
	}

	/**
	 * Adds a validation provider to the list of known validation providers.
	 * 
	 * @param validationProvider the {@link ValidationProvider} to add
	 */
	public void addValidationProvider(ValidationProvider validationProvider) {
		validationProviders.add(validationProvider);
		viewValidationGraph.validate(getAllEObjects(context.getDomainModel()));
	}

	/**
	 * Removes a validation provider from the list of known validation providers.
	 * 
	 * @param validationProvider the {@link ValidationProvider} to remove
	 */
	public void removeValidationProvider(ValidationProvider validationProvider) {
		validationProviders.remove(validationProvider);
		viewValidationGraph.validate(getAllEObjects(context.getDomainModel()));
	}
}
