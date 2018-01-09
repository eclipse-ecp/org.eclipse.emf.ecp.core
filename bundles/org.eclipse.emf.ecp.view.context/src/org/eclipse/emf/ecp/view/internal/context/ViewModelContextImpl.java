/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 527740
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.context;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

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

	/** The view model change listener. Needs to be thread safe. */
	private final List<ModelChangeListener> viewModelChangeListener = new CopyOnWriteArrayList<ModelChangeListener>();

	/** The domain model change listener. Needs to be thread safe. */
	private final List<ModelChangeListener> domainModelChangeListener = new CopyOnWriteArrayList<ModelChangeListener>();

	/** The root domain model change listeners. Needs to be thread safe. */
	private final List<RootDomainModelChangeListener> rootDomainModelChangeListeners = new CopyOnWriteArrayList<RootDomainModelChangeListener>();

	/** The domain model content adapter. */
	private EContentAdapter domainModelContentAdapter;

	/** The view model content adapter. */
	private EContentAdapter viewModelContentAdapter;

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
		this.view = view;
		this.domainObject = domainObject;
		parentContext = parent;
		if (modelServiceProvider == null) {
			viewServiceProvider = ViewModelServiceProvider.NULL;
		} else {
			viewServiceProvider = modelServiceProvider;
		}
		viewServices.addAll(viewServiceProvider.getViewModelServices(view, domainObject));
		this.parentVElement = parentVElement;
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
			&& !VDomainModelReference.class.isInstance(eObject.eContainer())) {
			final VDomainModelReference domainModelReference = VDomainModelReference.class.cast(eObject);
			try {
				domainExpander.prepareDomainObject(domainModelReference, domainModelRoot);
			} catch (final EMFFormsExpandingFailedException ex) {
				Activator.getInstance().getReportService().report(new AbstractReport(ex));
			}
		}
	}

	/**
	 * Instantiate.
	 */
	private void instantiate() {

		addResourceIfNecessary();

		resolveDomainReferences(getViewModel(), getDomainModel());
		if (parentContext == null) {
			domainModelContentAdapter = new DomainModelContentAdapter();
			domainObject.eAdapters().add(domainModelContentAdapter);
		}
		loadImmediateServices();

		viewModelContentAdapter = new ViewModelContentAdapter();

		view.eAdapters().add(viewModelContentAdapter);

		for (final ViewModelService viewService : viewServices) {
			viewService.instantiate(this);
		}

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.contextInitialised();
		}
	}

	private void loadImmediateServices() {
		final Bundle bundle = FrameworkUtil.getBundle(getClass());

		if (bundle != null) {
			final BundleContext bundleContext = bundle.getBundleContext();
			final ServiceReference<EMFFormsLegacyServicesManager> serviceReferenceLegacy = bundleContext
				.getServiceReference(EMFFormsLegacyServicesManager.class);
			if (serviceReferenceLegacy != null) {
				final EMFFormsLegacyServicesManager legacyServicesFactory = bundleContext
					.getService(serviceReferenceLegacy);
				legacyServicesFactory.instantiate();
				bundleContext.ungetService(serviceReferenceLegacy);
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
		viewModelChangeListener.clear();
		rootDomainModelChangeListeners.clear();

		isDisposing = false;
		isDisposed = true;

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
		viewModelChangeListener.add(modelChangeListener);
	}

	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		// if (isDisposed) {
		// throw new IllegalStateException("The ViewModelContext was already disposed.");
		// }
		viewModelChangeListener.remove(modelChangeListener);
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
			// TODO performance
			// needed to make sure, all data operations are done before any validation etc provided by services happens
			if (VDomainModelReference.class.isInstance(modelChangeListener)) {
				domainModelChangeListener.add(0, modelChangeListener);
			} else {
				// TODO hack for https://bugs.eclipse.org/bugs/show_bug.cgi?id=460158
				int positionToInsert = -1;
				for (int i = 0; i < domainModelChangeListener.size(); i++) {
					final ModelChangeListener listener = domainModelChangeListener.get(i);
					if (modelChangeListener.getClass().isInstance(listener)) {
						positionToInsert = i;
						break;
					}
				}

				if (positionToInsert == -1) {
					domainModelChangeListener.add(modelChangeListener);
				} else {
					domainModelChangeListener.add(positionToInsert, modelChangeListener);
				}
			}
		} else {
			parentContext.registerDomainChangeListener(modelChangeListener);
		}
	}

	@Override
	public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
		// if (isDisposed) {
		// throw new IllegalStateException("The ViewModelContext was already disposed.");
		// }
		if (parentContext == null) {
			domainModelChangeListener.remove(modelChangeListener);
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
		if (servicesManager != null && parentContext == null) {
			final Optional<T> lazyService = servicesManager.createGlobalLazyService(serviceType, this);
			if (lazyService.isPresent()) {
				final T t = lazyService.get();
				serviceMap.put(serviceType, t);
				return t;
			}
		}
		if (parentContext != null) {
			return parentContext.getService(serviceType);
		}

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

	private void report(AbstractReport report) {
		final Activator activator = Activator.getInstance();
		if (activator != null) {
			final ReportService reportService = activator.getReportService();
			if (reportService != null) {
				reportService.report(report);
			}
		}
	}

	/**
	 * The content adapter for the view model.
	 */
	private class ViewModelContentAdapter extends EContentAdapter {

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
			for (final ModelChangeListener modelChangeListener : viewModelChangeListener) {
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
			for (final ModelChangeListener modelChangeListener : viewModelChangeListener) {
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
			for (final ModelChangeListener modelChangeListener : viewModelChangeListener) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyRemove(notifier);
				}
			}
		}

	}

	/**
	 * The content adapter for the domain model.
	 */
	private class DomainModelContentAdapter extends EContentAdapter {

		@Override
		public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);

			// do not notify while being disposed
			if (isDisposing) {
				return;
			}

			final ModelChangeNotification modelChangeNotification = new ModelChangeNotification(notification);
			for (final ModelChangeListener modelChangeListener : domainModelChangeListener) {
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
			for (final ModelChangeListener modelChangeListener : domainModelChangeListener) {
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
			for (final ModelChangeListener modelChangeListener : domainModelChangeListener) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyRemove(notifier);
				}
			}
		}

	}

	private final Set<Object> users = new LinkedHashSet<Object>();

	private EMFFormsViewServiceManager servicesManager;

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

	private void addChildContext(VElement vElement, EObject eObject, ViewModelContext childContext) {

		if (!childContexts.containsKey(eObject)) {
			childContexts.put(eObject, new LinkedHashSet<ViewModelContext>());
		}
		childContexts.get(eObject).add(childContext);
		childContextUsers.put(childContext, vElement);

		for (final EMFFormsContextListener contextListener : contextListeners) {
			contextListener.childContextAdded(vElement, childContext);
		}
		// notify all global View model services
		for (final ViewModelService viewModelService : viewServices) {
			if (GlobalViewModelService.class.isInstance(viewModelService)) {
				GlobalViewModelService.class.cast(viewModelService).childViewModelContextAdded(childContext);
			}
		}

	}

	private void removeChildContext(EObject eObject, ViewModelContext context) {
		final boolean removed = childContexts.get(eObject).remove(context);
		if (removed) {
			childContextUsers.remove(context);
		}
		if (childContexts.get(eObject).size() == 0) {
			childContexts.remove(eObject);
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
				removeChildContext(eObject, viewModelContext);
				for (final EMFFormsContextListener contextListener : contextListeners) {
					contextListener.childContextDisposed(viewModelContext);
				}
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext#changeDomainModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void changeDomainModel(EObject newDomainModel) {
		if (isDisposed) {
			throw new IllegalStateException(THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED);
		}

		// =============
		// DISPOSE CODE
		innerDispose();

		// =======================

		domainObject = newDomainModel;

		// re-instantiate
		instantiate();

		for (final RootDomainModelChangeListener listener : rootDomainModelChangeListeners) {
			listener.notifyChange();
		}
	}

	private void innerDispose() {
		if (resource != null) {
			resource.getContents().remove(domainObject);
		}

		view.eAdapters().remove(viewModelContentAdapter);
		domainObject.eAdapters().remove(domainModelContentAdapter);

		domainModelChangeListener.clear();

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
}
