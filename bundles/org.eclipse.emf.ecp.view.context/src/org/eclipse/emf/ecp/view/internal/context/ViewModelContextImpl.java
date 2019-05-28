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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bugs 527740, 533522, 545686, 527686
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.context;

import static java.util.Collections.singleton;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.EMFFormsLegacyServicesManager;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelServiceNotAvailableReport;
import org.eclipse.emf.ecp.view.spi.context.ViewModelServiceProvider;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.controlmapper.EMFFormsSettingToControlMapper;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

/**
 * The Class ViewModelContextImpl.
 *
 * @author Eugen Neufeld
 */
public class ViewModelContextImpl implements ViewModelContext {

	static {
		final Bundle bundle = FrameworkUtil.getBundle(ViewModelContextImpl.class);
		if (bundle != null) {
			bundleContext = bundle.getBundleContext();
		}
	}
	private static BundleContext bundleContext;
	private static final String MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL = "ModelChangeAddRemoveListener must not be null."; //$NON-NLS-1$

	private static final String ROOT_DOMAIN_MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL = "RootDomainModelChangeListener must not be null."; //$NON-NLS-1$

	private static final String THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED = "The ViewModelContext was already disposed."; //$NON-NLS-1$

	/** The view. */
	private final VElement view;

	/** The domain object. */
	private EObject domainObject;

	/** The root domain model change listeners. Needs to be thread safe. */
	private final List<RootDomainModelChangeListener> rootDomainModelChangeListeners = new CopyOnWriteArrayList<RootDomainModelChangeListener>();

	/** The domain model content adapter. */
	private final DomainModelContentAdapter domainModelContentAdapter;

	/** The view model content adapter. */
	private final ViewModelContentAdapter viewModelContentAdapter;

	private final Set<EMFFormsContextListener> contextListeners = new CopyOnWriteArraySet<EMFFormsContextListener>();

	/** The view services. */
	private final SortedSet<ViewModelService> viewServices = new TreeSet<ViewModelService>(
		new Comparator<ViewModelService>() {

			@Override
			public int compare(ViewModelService arg0, ViewModelService arg1) {
				if (arg0.getPriority() == arg1.getPriority()) {
					/* compare would return 0, meaning the services are identical -> 1 service would get lost */
					return arg0.getClass().getName().compareTo(arg1.getClass().getName());
				}
				return arg0.getPriority() - arg1.getPriority();
			}
		});

	/** Provider of view service overrides. */
	private final ViewModelServiceProvider viewServiceProvider;

	/**
	 * The disposed state.
	 */
	private boolean isDisposed;

	/**
	 * The context map.
	 */
	private final Map<String, Object> keyObjectMap = new LinkedHashMap<String, Object>();

	/**
	 * Whether the context is being disposed.
	 */
	private boolean isDisposing;

	private Resource resource;

	private final Map<EObject, Set<ViewModelContext>> childContexts = new LinkedHashMap<EObject, Set<ViewModelContext>>();
	private final Map<ViewModelContext, VElement> childContextUsers = new LinkedHashMap<ViewModelContext, VElement>();

	private final ViewModelContext parentContext;

	private final Set<ViewModelContextDisposeListener> disposeListeners = new LinkedHashSet<ViewModelContextDisposeListener>();

	// TODO replace with eclipse context?!
	private final Map<Class<?>, Object> serviceMap = new LinkedHashMap<Class<?>, Object>();

	private final Map<ServiceReference<?>, Class<?>> usedOSGiServices = new LinkedHashMap<ServiceReference<?>, Class<?>>();

	private ServiceListener serviceListener;

	private final VElement parentVElement;

	/**
	 * Instantiates a new view model context impl.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject) {
		this(view, domainObject, ViewModelServiceProvider.NULL);
	}

	/**
	 * Instantiates a new view model context impl.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param parent The parent {@link ViewModelContext}
	 * @param parentVElement the parent {@link VElement}
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement) {
		this(view, domainObject, parent, parentVElement, ViewModelServiceProvider.NULL);
	}

	/**
	 * Instantiates a new view model context impl.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 *
	 * @deprecated As of 1.16, use the {@link #ViewModelContextImpl(VElement, EObject, ViewModelServiceProvider)} API
	 */
	@Deprecated
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelService... modelServices) {
		this(view, domainObject, new ArrayOnceViewModelServiceProvider(modelServices));
	}

	/**
	 * Instantiates a new view model context impl.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServiceProvider a provider of services to use in the {@link ViewModelContext}. May be {@code null} if
	 *            local service overrides are not needed
	 *
	 * @since 1.16
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelServiceProvider modelServiceProvider) {
		this(view, domainObject, null, null, modelServiceProvider);
	}

	/**
	 * Instantiates a new view model context impl with a parent context.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param parent The parent {@link ViewModelContext}
	 * @param parentVElement The parent {@link VElement}
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 *
	 * @deprecated As of 1.16, use the
	 *             {@link #ViewModelContextImpl(VElement, EObject, ViewModelContext, VElement, ViewModelServiceProvider)}
	 *             API
	 */
	@Deprecated
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement, ViewModelService... modelServices) {

		this(view, domainObject, parent, parentVElement, new ArrayOnceViewModelServiceProvider(modelServices));
	}

	/**
	 * Instantiates a new view model context impl with a parent context.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param parent The parent {@link ViewModelContext}
	 * @param parentVElement The parent {@link VElement}
	 * @param modelServiceProvider a provider of services to use in the {@link ViewModelContext}. May be {@code null} if
	 *            local service overrides are not needed
	 *
	 * @since 1.16
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement, ViewModelServiceProvider modelServiceProvider) {

		this(view, domainObject, parent, parentVElement, modelServiceProvider, null);
	}

	/**
	 * Instantiates a new view model context with initial {@linkplain #getContextValue(String) context values}.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param contextValues initial context values to set
	 *
	 * @since 1.21
	 * @see #getContextValue(String)
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, Map<String, ?> contextValues) {
		this(view, domainObject, ViewModelServiceProvider.NULL, contextValues);
	}

	/**
	 * Instantiates a new view model context with initial {@linkplain #getContextValue(String) context values}.
	 *
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServiceProvider a provider of services to use in the {@link ViewModelContext}. May be {@code null} if
	 *            local service overrides are not needed
	 * @param contextValues initial context values to set
	 *
	 * @since 1.21
	 * @see #getContextValue(String)
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelServiceProvider modelServiceProvider,
		Map<String, ?> contextValues) {

		this(view, domainObject, null, null, modelServiceProvider, contextValues);
	}

	/**
	 * Internal constructor to which all others ultimately delegate.
	 */
	private ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement, ViewModelServiceProvider modelServiceProvider, Map<String, ?> contextValues) {

		this.view = view;
		this.domainObject = domainObject;
		parentContext = parent;
		if (modelServiceProvider == null) {
			viewServiceProvider = ViewModelServiceProvider.NULL;
		} else {
			viewServiceProvider = modelServiceProvider;
		}
		this.parentVElement = parentVElement;

		// This must be done before instantiating services that may use the context values
		if (contextValues != null) {
			if (parentContext != null) {
				// Values go up to the parent
				contextValues.forEach(parentContext::putContextValue);
			} else {
				// I am the root
				keyObjectMap.putAll(contextValues);
			}
		}

		viewModelContentAdapter = new ViewModelContentAdapter();

		if (parentContext == null) {
			domainModelContentAdapter = new DomainModelContentAdapter();
		} else {
			domainModelContentAdapter = null;
		}

		instantiate();
	}

	/**
	 * Resolve all domain model references for a given resolvable and a given domain model root.
	 *
	 * @param resolvable The EObject to resolve all {@link VDomainModelReference domain model references} of.
	 * @param domainModelRoot the domain model used for the resolving.
	 * @throws EMFFormsExpandingFailedException If the domain expansion fails.
	 */
	private void resolveDomainReferences(EObject resolvable, EObject domainModelRoot) {
		// Get domain expander service
		final EMFFormsDomainExpander domainExpander = getService(EMFFormsDomainExpander.class);
		if (domainExpander == null) {
			return;
		}
		expandAndInitDMR(domainModelRoot, domainExpander, resolvable);
		// Iterate over all domain model references of the given EObject.
		final TreeIterator<EObject> eAllContents = resolvable.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			expandAndInitDMR(domainModelRoot, domainExpander, eObject);
		}
	}

	private void expandAndInitDMR(EObject domainModelRoot, final EMFFormsDomainExpander domainExpander,
		final EObject eObject) {
		if (VDomainModelReference.class.isInstance(eObject)
			&& !VDomainModelReference.class.isInstance(eObject.eContainer())
			&& !VDomainModelReferenceSegment.class.isInstance(eObject.eContainer())) {
			final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(eObject);
			try {
				domainExpander.prepareDomainObject(domainModelReference, domainModelRoot);
			} catch (final EMFFormsExpandingFailedException ex) {
				getServiceWithoutLog(ReportService.class).report(new AbstractReport(ex));
			}
		}
	}

	/**
	 * Instantiate.
	 */
	private void instantiate() {
		addResourceIfNecessary();

		resolveDomainReferences(getViewModel(), getDomainModel());

		// Only the root context has this adapter to attach it to the model. Child contents
		// all push listeners up to the root and expect their own domain object to be in
		// the content tree of the root context's domain object
		if (domainModelContentAdapter != null) {
			domainObject.eAdapters().add(domainModelContentAdapter);
		}

		loadImmediateServices();

		view.eAdapters().add(viewModelContentAdapter);

		// Generate local view services from our provider
		viewServices.addAll(viewServiceProvider.getViewModelServices(view, domainObject));

		for (final ViewModelService viewService : viewServices) {
			viewService.instantiate(this);
		}

		// If I have a parent, then I should be added to it if I wasn't already.
		// e.g., if this is the re-instantiate after dispose on changing the domain
		// model, then I need to be added back as a child because I was removed
		if (parentContext instanceof ViewModelContextImpl) {
			((ViewModelContextImpl) parentContext).addChildContext(getParentVElement(), getDomainModel(), this);
		}

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.contextInitialised();
		}
	}

	private void loadImmediateServices() {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());

		if (bundle != null) {
			final BundleContext bundleContext = bundle.getBundleContext();
			serviceReferenceLegacy = bundleContext
				.getServiceReference(EMFFormsLegacyServicesManager.class);
			if (serviceReferenceLegacy != null) {
				final EMFFormsLegacyServicesManager legacyServicesFactory = bundleContext
					.getService(serviceReferenceLegacy);
				legacyServicesFactory.instantiate();
			}

			servicesManager = getService(EMFFormsViewServiceManager.class);

			for (final Class<?> localImmediateService : servicesManager.getAllLocalImmediateServiceTypes()) {
				final Optional<?> service = servicesManager.createLocalImmediateService(localImmediateService, this);
				if (!service.isPresent()) {
					// error case?
					continue;
				}
				serviceMap.put(localImmediateService, service.get());

			}

			if (parentContext == null) {
				for (final Class<?> globalImmediateService : servicesManager.getAllGlobalImmediateServiceTypes()) {
					final Optional<?> service = servicesManager.createGlobalImmediateService(globalImmediateService,
						this);
					if (!service.isPresent()) {
						// error case?
						continue;
					}
					final Object serviceObject = service.get();
					serviceMap.put(globalImmediateService, serviceObject);

				}
			}

			serviceListener = new ServiceListener() {

				@Override
				public void serviceChanged(ServiceEvent event) {
					if (event.getType() == ServiceEvent.UNREGISTERING &&
						usedOSGiServices.containsKey(event.getServiceReference())) {
						final Class<?> remove = usedOSGiServices.remove(event.getServiceReference());
						serviceMap.remove(remove);
					}
				}
			};
			bundleContext.addServiceListener(serviceListener);

		}
	}

	private void addResourceIfNecessary() {
		if (domainObject.eResource() != null) {
			return;
		}
		final EObject rootObject = EcoreUtil.getRootContainer(domainObject);
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		if (resource != null) {
			resource.getContents().add(rootObject);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 * @deprecated
	 */
	@Deprecated
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		final Set<VElement> elements = getService(EMFFormsSettingToControlMapper.class)
			.getControlsFor(UniqueSetting.createSetting(setting));
		final Set<VControl> controls = new LinkedHashSet<VControl>();
		for (final VElement element : elements) {
			if (VControl.class.isInstance(element)) {
				controls.add((VControl) element);
			}
		}
		return controls;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecp.common.spi.UniqueSetting)
	 * @deprecated
	 */
	@Deprecated
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		return getService(EMFFormsSettingToControlMapper.class).getControlsFor(setting);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getViewModel()
	 */
	@Override
	public VElement getViewModel() {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}
		return view;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getDomainModel()
	 */
	@Override
	public EObject getDomainModel() {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}
		return domainObject;
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		if (isDisposed) {
			return;
		}
		isDisposing = true;
		for (final ViewModelContextDisposeListener listener : disposeListeners) {
			listener.contextDisposed(this);
		}
		innerDispose();
		viewModelContentAdapter.dispose();
		if (domainModelContentAdapter != null) {
			domainModelContentAdapter.dispose();
		}
		rootDomainModelChangeListeners.clear();

		isDisposing = false;
		isDisposed = true;
		if (serviceReferenceLegacy != null) {
			bundleContext.ungetService(serviceReferenceLegacy);
		}

	}

	private void releaseOSGiServices() {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle != null) {
			final BundleContext bundleContext = bundle.getBundleContext();
			for (final ServiceReference<?> serviceReference : usedOSGiServices.keySet()) {
				bundleContext.ungetService(serviceReference);
			}
		}
		usedOSGiServices.clear();
	}

	@Override
	public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}
		if (modelChangeListener == null) {
			throw new IllegalArgumentException(MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL);
		}
		viewModelContentAdapter.add(modelChangeListener);
	}

	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		// if (isDisposed) {
		// throw new IllegalStateException("The ViewModelContext was already disposed.");
		// }
		viewModelContentAdapter.remove(modelChangeListener);
	}

	@Override
	public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}
		if (modelChangeListener == null) {
			throw new IllegalArgumentException(MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL);
		}
		if (parentContext == null) {
			domainModelContentAdapter.add(modelChangeListener);
		} else {
			parentContext.registerDomainChangeListener(modelChangeListener);
		}
	}

	@Override
	public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
		// if (isDisposed) {
		// throw new IllegalStateException("The ViewModelContext was already disposed.");
		// }
		if (modelChangeListener == null) {
			// ConcurrentSkipListSet doesn't allow nulls but balks on attempts to remove them, too
			return;
		}
		if (parentContext == null) {
			domainModelContentAdapter.remove(modelChangeListener);
		} else {
			parentContext.unregisterDomainChangeListener(modelChangeListener);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#hasService(java.lang.Class)
	 */
	@Override
	public <T> boolean hasService(Class<T> serviceType) {
		return getServiceWithoutLog(serviceType) != null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getService(java.lang.Class)
	 */
	@Override
	public <T> T getService(Class<T> serviceType) {
		final T service = getServiceWithoutLog(serviceType);
		if (service == null) {
			report(new ViewModelServiceNotAvailableReport(serviceType));
		}
		return service;
	}

	/**
	 * Retrieve an {@link ViewModelService} of type {@code serviceType} without reporting an error if none is found.
	 *
	 * @param <T>
	 *            the type of the desired service
	 *
	 * @param serviceType
	 *            the type of the service to be retrieved
	 * @return the service
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getServiceWithoutLog(Class<T> serviceType) {
		// legacy stuff
		for (final ViewModelService service : viewServices) {
			if (serviceType.isInstance(service)) {
				return (T) service;
			}
		}
		// First check local services
		final T localService = getLocalService(serviceType);
		if (localService != null) {
			return localService;
		}
		// If context is the root, check global services to be instanciated
		if (servicesManager != null && parentContext == null) {
			final Optional<T> lazyService = servicesManager.createGlobalLazyService(serviceType, this);
			if (lazyService.isPresent()) {
				final T t = lazyService.get();
				serviceMap.put(serviceType, t);
				return t;
			}
		}
		// Check the parent context
		if (parentContext != null && parentContext.hasService(serviceType)) {
			return parentContext.getService(serviceType);
		}
		// Check OSGi services
		if (bundleContext != null) {
			final ServiceReference<T> serviceReference = bundleContext.getServiceReference(serviceType);
			if (serviceReference != null) {
				usedOSGiServices.put(serviceReference, serviceType);
				final T service = bundleContext.getService(serviceReference);
				serviceMap.put(serviceType, service);
				return service;
			}
		}
		return null;
	}

	/**
	 * @return a instance of a local service or a local service service, which can be created or null if neither exists.
	 */
	@SuppressWarnings("unchecked")
	private <T> T getLocalService(Class<T> serviceType) {
		if (serviceMap.containsKey(serviceType)) {
			return (T) serviceMap.get(serviceType);
		} else if (servicesManager != null) {
			final Optional<T> lazyService = servicesManager.createLocalLazyService(serviceType, this);
			if (lazyService.isPresent()) {
				final T t = lazyService.get();
				serviceMap.put(serviceType, t);
				return t;
			}
		}
		return null;
	}

	private void report(AbstractReport report) {
		final ReportService reportService = getServiceWithoutLog(ReportService.class);
		if (reportService != null) {
			reportService.report(report);
		}
	}

	/**
	 * The content adapter for the view model.
	 */
	private class ViewModelContentAdapter extends EContentAdapter {

		/** The view model change listener. Needs to be thread safe. */
		private final List<ModelChangeListener> viewModelChangeListeners = new CopyOnWriteArrayList<ModelChangeListener>();

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			// do not notify while being disposed
			if (isDisposing) {
				return;
			}
			if (isDisposed) {
				return;
			}
			if (notification.isTouch()) {
				return;
			}
			final ModelChangeNotification modelChangeNotification = new ModelChangeNotification(notification);
			for (final ModelChangeListener modelChangeListener : viewModelChangeListeners) {
				modelChangeListener.notifyChange(modelChangeNotification);
			}
		}

		@Override
		protected void addAdapter(Notifier notifier) {
			super.addAdapter(notifier);
			// do not notify while being disposed
			if (isDisposing || isDisposed) {
				return;
			}
			for (final ModelChangeListener modelChangeListener : viewModelChangeListeners) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyAdd(notifier);
				}
			}
		}

		@Override
		protected void removeAdapter(Notifier notifier) {
			super.removeAdapter(notifier);
			// do not notify while being disposed
			if (isDisposing) {
				return;
			}
			if (VElement.class.isInstance(notifier)) {
				VElement.class.cast(notifier).setDiagnostic(null);
			}
			for (final ModelChangeListener modelChangeListener : viewModelChangeListeners) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyRemove(notifier);
				}
			}
		}

		void add(ModelChangeListener viewModelChangeListener) {
			viewModelChangeListeners.add(viewModelChangeListener);

			// do not notify while being disposed
			if (isDisposing || isDisposed || !view.eAdapters().contains(this)
				|| !(viewModelChangeListener instanceof ModelChangeAddRemoveListener)) {
				return;
			}

			// This listener needs to discover all of the view model as new
			final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) viewModelChangeListener;
			for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(view), resolve()); iter.hasNext();) {
				addRemove.notifyAdd(iter.next());
			}
		}

		void remove(ModelChangeListener viewModelChangeListener) {
			viewModelChangeListeners.remove(viewModelChangeListener);

			// do not notify while being disposed
			if (isDisposing || !view.eAdapters().contains(this)
				|| !(viewModelChangeListener instanceof ModelChangeAddRemoveListener)) {
				return;
			}

			// This listener needs to un-discover all of the view model
			final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) viewModelChangeListener;
			for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(view), resolve()); iter.hasNext();) {
				addRemove.notifyRemove(iter.next());
			}
		}

		void dispose() {
			viewModelChangeListeners.clear();
		}
	}

	/**
	 * The content adapter for the domain model.
	 */
	private class DomainModelContentAdapter extends EContentAdapter {

		/** The domain model change listeners. Needs to be thread safe. */
		private final GroupedListenerList<ModelChangeListener> domainModelChangeListeners =
			// needed to make sure that all data operations are done before any validation etc provided by services
			// happens
			new GroupedListenerList<ModelChangeListener>(VDomainModelReference.class);

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			// do not notify while being disposed
			if (isDisposing) {
				return;
			}

			final ModelChangeNotification modelChangeNotification = new ModelChangeNotification(notification);
			for (final ModelChangeListener modelChangeListener : domainModelChangeListeners) {
				modelChangeListener.notifyChange(modelChangeNotification);
			}
		}

		@Override
		protected void addAdapter(Notifier notifier) {
			super.addAdapter(notifier);
			// do not notify while being disposed
			if (isDisposing) {
				return;
			}
			for (final ModelChangeListener modelChangeListener : domainModelChangeListeners) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyAdd(notifier);
				}
			}
		}

		@Override
		protected void removeAdapter(Notifier notifier) {
			super.removeAdapter(notifier);
			// do not notify while being disposed
			if (isDisposing) {
				return;
			}
			for (final ModelChangeListener modelChangeListener : domainModelChangeListeners) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyRemove(notifier);
				}
			}
		}

		void dispose() {
			domainModelChangeListeners.clear();
		}

		void add(ModelChangeListener domainModelChangeListener) {
			domainModelChangeListeners.add(domainModelChangeListener);

			// do not notify while being disposed
			if (isDisposing || !domainObject.eAdapters().contains(this)
				|| !(domainModelChangeListener instanceof ModelChangeAddRemoveListener)) {
				return;
			}

			// This listener needs to discover all of the domain model as new
			final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) domainModelChangeListener;
			for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(domainObject), resolve()); iter
				.hasNext();) {
				addRemove.notifyAdd(iter.next());
			}
		}

		void remove(ModelChangeListener domainModelChangeListener) {
			domainModelChangeListeners.remove(domainModelChangeListener);

			// do not notify while being disposed
			if (isDisposing || !domainObject.eAdapters().contains(this)
				|| !(domainModelChangeListener instanceof ModelChangeAddRemoveListener)) {
				return;
			}

			// This listener needs to un-discover all of the domain model
			final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) domainModelChangeListener;
			for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(domainObject), resolve()); iter
				.hasNext();) {
				addRemove.notifyRemove(iter.next());
			}
		}

	}

	private final Set<Object> users = new LinkedHashSet<Object>();

	private EMFFormsViewServiceManager servicesManager;
	private ServiceReference<EMFFormsLegacyServicesManager> serviceReferenceLegacy;

	/**
	 * Inner method for registering context users (not {@link ViewModelService}).
	 *
	 * @param user the user of the context
	 */
	@Override
	public void addContextUser(Object user) {
		users.add(user);
	}

	/**
	 * Inner method for unregistering the context user.
	 *
	 * @param user the user of the context
	 */
	@Override
	public void removeContextUser(Object user) {
		users.remove(user);
		// Every renderer is registered here, as it needs to know when the view model changes (rules, validations, etc).
		// If no listener is left, we can dispose the context
		// if (users.isEmpty() || users.size() == 1 && parentContext != null && users.contains(parentContext)) {
		if (users.isEmpty()) {
			dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getContextValue(java.lang.String)
	 */
	@Override
	public Object getContextValue(String key) {
		if (parentContext != null) {
			return parentContext.getContextValue(key);
		}
		return keyObjectMap.get(key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#putContextValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void putContextValue(String key, Object value) {
		if (parentContext != null) {
			parentContext.putContextValue(key, value);
			return;
		}
		keyObjectMap.put(key, value);
	}

	private void addChildContext(VElement parentElement, EObject eObject, ViewModelContext childContext) {
		if (hasChildContext(parentElement, eObject, childContext)) {
			return;
		}

		if (!childContexts.containsKey(eObject)) {
			childContexts.put(eObject, new LinkedHashSet<ViewModelContext>());
		}
		childContexts.get(eObject).add(childContext);
		childContextUsers.put(childContext, parentElement);

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.childContextAdded(parentElement, childContext);
		}
		// notify all global View model services
		for (final ViewModelService viewModelService : viewServices) {
			if (GlobalViewModelService.class.isInstance(viewModelService)) {
				GlobalViewModelService.class.cast(viewModelService).childViewModelContextAdded(childContext);
			}
		}

	}

	/**
	 * Query whether I have a valid child context for the given domain {@code object} under
	 * the given parent view-model element.
	 *
	 * @param parentElement the parent view-model element
	 * @param object a domain model element
	 * @param childContext a child context expected possibly for that element
	 * @return {@code true} if the given context is currently registered as a child
	 *         context for the {@code object}; {@code false}, otherwise
	 */
	private boolean hasChildContext(VElement parentElement, EObject object, ViewModelContext childContext) {
		boolean result = childContexts.getOrDefault(object, Collections.emptySet()).contains(childContext);
		result = result && childContextUsers.get(childContext) == parentElement;
		return result;
	}

	private void removeChildContext(EObject eObject, ViewModelContext context) {
		final Set<ViewModelContext> children = childContexts.get(eObject);
		final boolean removed = children != null && children.remove(context);

		if (!removed) {
			return;
		}

		childContextUsers.remove(context);
		if (children.size() == 0) {
			childContexts.remove(eObject);
		}

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.childContextDisposed(context);
		}
	}

	/**
	 * Obtains the provider of local view-model service overrides.
	 *
	 * @return the local view-model service provider
	 *
	 * @since 1.16
	 */
	protected ViewModelServiceProvider getViewModelServiceProvider() {
		return viewServiceProvider;
	}

	@Deprecated
	@Override
	public ViewModelContext getChildContext(final EObject eObject, VElement parent, VView vView,
		ViewModelService... viewModelServices) {

		return getChildContext(eObject, parent, vView, new ArrayOnceViewModelServiceProvider(viewModelServices));
	}

	@Override
	public ViewModelContext getChildContext(final EObject eObject, VElement parent, VView vView,
		ViewModelServiceProvider viewModelServiceProvider) {

		final Set<ViewModelContext> contexts = childContexts.get(eObject);
		if (contexts != null) {
			for (final ViewModelContext context : contexts) {
				// TODO change to use bidirectional map
				if (childContextUsers.get(context).equals(parent)) {
					return context;
				}
			}
		}

		// no context found -> create a new one

		ViewModelServiceProvider serviceProvider = getViewModelServiceProvider();
		if (viewModelServiceProvider != null) {
			// Compose the client's provided service first as they must override ours
			serviceProvider = new ViewModelServiceProvider.Composed(
				viewModelServiceProvider, serviceProvider);
		}

		final ViewModelContext childContext = new ViewModelContextImpl(vView, eObject, this, parent, serviceProvider);
		childContext.registerDisposeListener(new ViewModelContextDisposeListener() {

			@Override
			public void contextDisposed(ViewModelContext viewModelContext) {
				// If the child context had had its domain object changed along the way,
				// then re-mapped it, so be sure to use it here for accuracy (it is still
				// accessible while disposal is in progress)
				removeChildContext(viewModelContext.getDomainModel(), viewModelContext);
			}
		});
		addChildContext(parent, eObject, childContext);
		return childContext;
	}

	@Override
	public ViewModelContext getParentContext() {
		return parentContext;
	}

	@Override
	public VElement getParentVElement() {
		return parentVElement;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerDisposeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener)
	 */
	@Override
	public void registerDisposeListener(ViewModelContextDisposeListener listener) {
		disposeListeners.add(listener);
	}

	@Override
	public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {
		contextListeners.add(contextListener);
	}

	@Override
	public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {
		contextListeners.remove(contextListener);
	}

	@Override
	public void changeDomainModel(EObject newDomainModel) {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}

		if (newDomainModel == domainObject) {
			// Nothing to do
			return;
		}

		innerDispose();

		final EObject oldObject = domainObject;
		domainObject = newDomainModel;

		// if I have a parent, it tracks me now under the wrong key
		if (parentContext instanceof ViewModelContextImpl) {
			((ViewModelContextImpl) parentContext).updateChildContext(oldObject, domainObject, this);
		}

		instantiate();

		for (final RootDomainModelChangeListener listener : rootDomainModelChangeListeners) {
			listener.notifyChange();
		}
	}

	/**
	 * Re-key a child context under its new domain object.
	 *
	 * @param oldDomainObject the domain object under which I currently track the child context
	 * @param newDomainObject the new domain object of the child context
	 * @param childContext the child context to re-key
	 */
	private void updateChildContext(EObject oldDomainObject, EObject newDomainObject, ViewModelContext childContext) {
		final Set<ViewModelContext> oldSibs = childContexts.get(oldDomainObject);
		if (oldSibs != null) {
			oldSibs.remove(childContext);
		}
		final Set<ViewModelContext> newSibs = childContexts.computeIfAbsent(newDomainObject,
			__ -> new LinkedHashSet<ViewModelContext>());
		newSibs.add(childContext);
	}

	private void innerDispose() {
		if (resource != null) {
			resource.getContents().remove(domainObject);
		}

		view.eAdapters().remove(viewModelContentAdapter);
		if (domainModelContentAdapter != null) {
			domainObject.eAdapters().remove(domainModelContentAdapter);
		}

		for (final ViewModelService viewService : viewServices) {
			viewService.dispose();
		}
		viewServices.clear();

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.contextDispose();
		}

		// TODO Child context disposing necessary?
		final Set<ViewModelContext> toDispose = new LinkedHashSet<ViewModelContext>(childContextUsers.keySet());
		for (final ViewModelContext vmc : toDispose) {
			vmc.dispose();
		}
		childContextUsers.clear();
		childContexts.clear();

		// Remove me from my parent context, if any
		if (parentContext instanceof ViewModelContextImpl) {
			((ViewModelContextImpl) parentContext).removeChildContext(getDomainModel(), this);
		}

		releaseOSGiServices();
		serviceMap.clear();

		final Bundle bundle = FrameworkUtil.getBundle(getClass());
		if (bundle != null) {
			final BundleContext bundleContext = bundle.getBundleContext();
			bundleContext.removeServiceListener(serviceListener);
			serviceListener = null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext#registerRootDomainModelChangeListener(org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener)
	 */
	@Override
	public void registerRootDomainModelChangeListener(RootDomainModelChangeListener rootDomainModelChangeListener) {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}
		if (rootDomainModelChangeListener == null) {
			throw new IllegalArgumentException(ROOT_DOMAIN_MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL);
		}
		rootDomainModelChangeListeners.add(rootDomainModelChangeListener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext#unregisterRootDomainModelChangeListener(org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener)
	 */
	@Override
	public void unregisterRootDomainModelChangeListener(RootDomainModelChangeListener rootDomainModelChangeListener) {
		rootDomainModelChangeListeners.remove(rootDomainModelChangeListener);
	}

	/**
	 * A collection of listeners grouped by class. Work-around for the absence of priority
	 * support (<a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=460158">bug 460158</a>).
	 *
	 * @author Christian W. Damus
	 *
	 * @param <T> the type of listener stored in the list
	 * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=460158">bug 460158</a>
	 */
	private static final class GroupedListenerList<T> implements Iterable<T> {
		private final Set<T> listeners = new ConcurrentSkipListSet<T>(comparator());

		private final AtomicInteger nextGroup = new AtomicInteger();
		private final ConcurrentMap<Class<?>, Integer> groups = new ConcurrentHashMap<Class<?>, Integer>();

		private final Class<?> importantGroup;

		/**
		 * Initializes with an important group that must be first in the iteration order.
		 */
		GroupedListenerList(Class<?> importantGroup) {
			super();

			this.importantGroup = importantGroup;
			seedImportantGroup();
		}

		/**
		 * Sort elements by group first and then arbitrarily within each group.
		 *
		 * @return a grouping comparator
		 */
		private Comparator<T> comparator() {
			return new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					if (o1 == o2) {
						return 0;
					}

					final int group1 = getGroup(o1);
					final int group2 = getGroup(o2);
					int result = group1 - group2;

					if (result == 0) {
						// Same group. Arbitrary ordering by address
						result = System.identityHashCode(o1) - System.identityHashCode(o2);
					}

					return result;
				}
			};
		}

		private void seedImportantGroup() {
			if (importantGroup != null) {
				// Seed the map with a lowest-order group
				groups.put(importantGroup, nextGroup.getAndIncrement());
			}
		}

		private int getGroup(T listener) {
			final Class<?> groupKey = importantGroup != null && importantGroup.isInstance(listener)
				? importantGroup
				: listener.getClass();

			Integer result = groups.get(groupKey);
			if (result == null) {
				result = nextGroup.getAndIncrement();
				final Integer collision = groups.putIfAbsent(groupKey, result);
				if (collision != null) {
					result = collision;
				}
			}

			return result;
		}

		void add(T listener) {
			listeners.add(listener);
			// need to add group as soon as listener added to correctly calculate order
			getGroup(listener);
		}

		void remove(T listener) {
			listeners.remove(listener);
		}

		void clear() {
			listeners.clear();
			groups.clear();
			seedImportantGroup();
		}

		@Override
		public Iterator<T> iterator() {
			return listeners.iterator();
		}
	}
}
