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
package org.eclipse.emf.ecp.view.internal.context;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.BasicCommandStack;
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
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.context.internal.reporting.ViewModelServiceNotAvailableReport;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * The Class ViewModelContextImpl.
 * 
 * @author Eugen Neufeld
 */
public class ViewModelContextImpl implements ViewModelContext {

	private static final String MODEL_CHANGE_LISTENER_MUST_NOT_BE_NULL = "ModelChangeAddRemoveListener must not be null."; //$NON-NLS-1$

	private static final String THE_VIEW_MODEL_CONTEXT_WAS_ALREADY_DISPOSED = "The ViewModelContext was already disposed."; //$NON-NLS-1$

	/** The view. */
	private final VElement view;

	/** The domain object. */
	private final EObject domainObject;

	/** The view model change listener. Needs to be thread safe. */
	private final List<ModelChangeListener> viewModelChangeListener = new CopyOnWriteArrayList<ModelChangeListener>();

	/** The domain model change listener. Needs to be thread safe. */
	private final List<ModelChangeListener> domainModelChangeListener = new CopyOnWriteArrayList<ModelChangeListener>();

	/** The domain model content adapter. */
	private EContentAdapter domainModelContentAdapter;

	/** The view model content adapter. */
	private EContentAdapter viewModelContentAdapter;

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

	/**
	 * A mapping between settings and controls.
	 */
	private final Map<UniqueSetting, Set<VControl>> settingToControlMap = new LinkedHashMap<UniqueSetting, Set<VControl>>();

	private final Map<VControl, DomainModelReferenceChangeListener> controlChangeListener = new LinkedHashMap<VControl, DomainModelReferenceChangeListener>();

	private Resource resource;

	private final Map<EObject, Set<ViewModelContext>> childContexts = new LinkedHashMap<EObject, Set<ViewModelContext>>();
	private final Map<ViewModelContext, VElement> childContextUsers = new LinkedHashMap<ViewModelContext, VElement>();

	private ViewModelContext parentContext;

	private final Set<ViewModelContextDisposeListener> disposeListeners = new LinkedHashSet<ViewModelContextDisposeListener>();

	/**
	 * Instantiates a new view model context impl.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject) {
		this.view = view;
		this.domainObject = domainObject;

		instantiate();
	}

	/**
	 * Instantiates a new view model context impl.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement) {
		this.view = view;
		this.domainObject = domainObject;
		parentContext = parent;
		instantiate();
	}

	/**
	 * Instantiates a new view model context impl.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelService... modelServices) {
		this.view = view;
		this.domainObject = domainObject;

		for (final ViewModelService vms : modelServices) {
			viewServices.add(vms);
		}
		instantiate();
	}

	/**
	 * Instantiates a new view model context impl.
	 * 
	 * @param view the view
	 * @param domainObject the domain object
	 * @param modelServices an array of services to use in the {@link ViewModelContext}
	 */
	public ViewModelContextImpl(VElement view, EObject domainObject, ViewModelContext parent,
		VElement parentVElement,
		ViewModelService... modelServices) {
		this.view = view;
		this.domainObject = domainObject;
		parentContext = parent;
		for (final ViewModelService vms : modelServices) {
			viewServices.add(vms);
		}
		instantiate();
	}

	/**
	 * Instantiate.
	 */
	private void instantiate() {

		addResourceIfNecessary();

		ViewModelUtil.resolveDomainReferences(getViewModel(), getDomainModel());

		viewModelContentAdapter = new ViewModelContentAdapter();

		view.eAdapters().add(viewModelContentAdapter);

		if (parentContext == null) {
			domainModelContentAdapter = new DomainModelContentAdapter();
			domainObject.eAdapters().add(domainModelContentAdapter);
		}

		createSettingToControlMapping();
		readAbstractViewServices();

		for (final ViewModelService viewService : viewServices) {
			if (!GlobalViewModelService.class.isInstance(viewService) || parentContext == null) {
				viewService.instantiate(this);
			}
		}
	}

	private void addResourceIfNecessary() {
		if (domainObject.eResource() != null) {
			return;
		}
		final ResourceSet rs = new ResourceSetImpl();
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		if (resource != null) {
			resource.getContents().add(domainObject);
		}
	}

	private void createSettingToControlMapping() {
		checkAndUpdateSettingToControlMapping(view);

		final TreeIterator<EObject> eAllContents = view.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			checkAndUpdateSettingToControlMapping(eObject);
		}
	}

	private void checkAndUpdateSettingToControlMapping(EObject eObject) {
		if (VControl.class.isInstance(eObject)) {
			final VControl vControl = (VControl) eObject;
			if (vControl.getDomainModelReference() == null) {
				return;
			}

			updateControlMapping(vControl);

			final DomainModelReferenceChangeListener changeListener = new DomainModelReferenceChangeListener() {

				@Override
				public void notifyChange() {
					updateControlMapping(vControl);
				}
			};
			controlChangeListener.put(vControl, changeListener);

			vControl.getDomainModelReference().getChangeListener().add(changeListener);
			registerDomainChangeListener(vControl.getDomainModelReference());
		}
	}

	private void updateControlMapping(VControl vControl) {
		// delete old mapping
		for (final UniqueSetting setting : settingToControlMap.keySet()) {
			settingToControlMap.get(setting).remove(vControl);
		}
		// vControl.getDomainModelReference().init(getDomainModel());
		final Iterator<Setting> iterator = vControl.getDomainModelReference().getIterator();
		while (iterator.hasNext()) {
			final Setting setting = iterator.next();
			if (setting == null) {
				continue;
			}
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
			if (!settingToControlMap.containsKey(uniqueSetting)) {
				settingToControlMap.put(uniqueSetting, new LinkedHashSet<VControl>());
			}
			settingToControlMap.get(uniqueSetting).add(vControl);
		}
	}

	private void vControlRemoved(VControl vControl) {
		if (vControl.getDomainModelReference() == null) {
			return;
		}
		final Iterator<Setting> iterator = vControl.getDomainModelReference().getIterator();
		while (iterator.hasNext()) {
			final Setting next = iterator.next();
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(next);
			if (settingToControlMap.containsKey(uniqueSetting)) {
				settingToControlMap.get(uniqueSetting).remove(vControl);
				if (settingToControlMap.get(uniqueSetting).size() == 0) {
					settingToControlMap.remove(uniqueSetting);
				}
			}
		}

		vControl.getDomainModelReference().getChangeListener().remove(controlChangeListener.get(vControl));
		controlChangeListener.remove(vControl);
		unregisterDomainChangeListener(vControl.getDomainModelReference());
	}

	private void vControlAdded(VControl vControl) {
		if (vControl.getDomainModelReference() == null) {
			return;
		}
		final Iterator<Setting> iterator = vControl.getDomainModelReference().getIterator();
		while (iterator.hasNext()) {
			final Setting next = iterator.next();
			if (next == null) {
				continue;
			}
			final UniqueSetting uniqueSetting = UniqueSetting.createSetting(next);
			if (!settingToControlMap.containsKey(uniqueSetting)) {
				settingToControlMap.put(uniqueSetting, new LinkedHashSet<VControl>());
			}
			settingToControlMap.get(uniqueSetting).add(vControl);
		}

	}

	// private void eObjectRemoved(EObject eObject) {
	// for (final EStructuralFeature eStructuralFeature : eObject.eClass().getEAllStructuralFeatures()) {
	// final Setting setting = InternalEObject.class.cast(eObject).eSetting(eStructuralFeature);
	// final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
	// if (settingToControlMap.containsKey(uniqueSetting)) {
	// settingToControlMap.remove(uniqueSetting);
	// }
	// }
	// }
	//
	// private void eObjectAdded(EObject eObject) {
	// final InternalEObject internalParent = InternalEObject.class.cast(eObject.eContainer());
	// if (internalParent == null) {
	// return;
	// }
	// // FIXME how to add??
	// // TODO hack:
	// for (final EReference eReference : internalParent.eClass().getEAllContainments()) {
	// if (eReference.getEReferenceType().isInstance(eObject)) {
	// final Setting setting = internalParent.eSetting(eReference);
	// final UniqueSetting uniqueSetting = UniqueSetting.createSetting(setting);
	// final Set<VControl> controls = settingToControlMap.get(uniqueSetting);
	// if (controls == null) {
	// continue;
	// }
	// final Set<VControl> iterateControls = new LinkedHashSet<VControl>(controls);
	// for (final VControl control : iterateControls) {
	// vControlAdded(control);
	// }
	//
	// }
	// }
	//
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 */
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		return settingToControlMap.get(UniqueSetting.createSetting(setting));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecp.common.UniqueSetting)
	 */
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		final Set<VElement> elements = new LinkedHashSet<VElement>();
		final Set<VControl> currentControls = settingToControlMap.get(setting);
		if (currentControls != null) {
			for (final VElement control : currentControls) {
				if (!control.isEnabled() || !control.isVisible() || control.isReadonly()) {
					continue;
				}
				elements.add(control);
			}
		}
		for (final ViewModelContext childContext : childContextUsers.keySet()) {
			final Set<VElement> childControls = childContext.getControlsFor(setting);
			boolean validControls = false;
			if (childControls == null) {
				continue;
			}
			for (final VElement element : childControls) {
				validControls |= element.isVisible() && !element.isReadonly() && element.isEnabled();
			}
			if (validControls) {
				elements.add(childContextUsers.get(childContext));
				elements.addAll(childControls);
			}
		}
		// TODO change expactation
		if (elements.isEmpty()) {
			return null;
		}
		return elements;
	}

	/**
	 * Read abstract view services.
	 */
	private void readAbstractViewServices() {

		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return;
		}
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.context.viewServices"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {

				final ViewModelService viewService = (ViewModelService) e.createExecutableExtension("class"); //$NON-NLS-1$
				if (!GlobalViewModelService.class.isInstance(viewService) || parentContext == null) {
					viewServices.add(viewService);
				}

			} catch (final CoreException e1) {
				Activator.log(e1);
			}
		}
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
		if (resource != null) {
			resource.getContents().remove(domainObject);
		}

		view.eAdapters().remove(viewModelContentAdapter);
		domainObject.eAdapters().remove(domainModelContentAdapter);

		viewModelChangeListener.clear();
		domainModelChangeListener.clear();

		for (final ViewModelService viewService : viewServices) {
			viewService.dispose();
		}
		viewServices.clear();
		settingToControlMap.clear();

		for (final VControl vControl : controlChangeListener.keySet()) {
			if (vControl.getDomainModelReference() != null) {
				vControl.getDomainModelReference().getChangeListener().remove(controlChangeListener.get(vControl));
			}
			unregisterDomainChangeListener(vControl.getDomainModelReference());
		}
		controlChangeListener.clear();

		isDisposing = false;
		isDisposed = true;

		for (final ViewModelContextDisposeListener listener : disposeListeners) {
			listener.contextDisposed(this);
		}
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
			domainModelChangeListener.add(modelChangeListener);
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
		for (final ViewModelService service : viewServices) {
			if (serviceType.isInstance(service)) {
				return true;
			}
		}
		if (parentContext != null) {
			return parentContext.hasService(serviceType);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getService(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceType) {
		for (final ViewModelService service : viewServices) {
			if (serviceType.isInstance(service)) {
				return (T) service;
			}
		}
		if (parentContext != null) {
			return parentContext.getService(serviceType);
		}

		Activator.getInstance()
			.getReportService()
			.report(new ViewModelServiceNotAvailableReport(serviceType));

		return null;
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
			// // delegate to parent context
			// if (parentContext != null) {
			// parentContext.viewModelContentAdapter.notifyChanged(notification);
			// return;
			// }
			if (notification.isTouch()) {
				return;
			}
			// FIXME possible side effects?
			if (notification.getEventType() == Notification.ADD) {
				if (VControl.class.isInstance(notification.getNewValue())) {
					checkAndUpdateSettingToControlMapping(VControl.class.cast(notification.getNewValue()));
				}
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
			if (VElement.class.isInstance(notifier)) {
				ViewModelUtil.resolveDomainReferences(
					(VElement) notifier,
					getDomainModel());
			}
			if (VControl.class.isInstance(notifier)) {
				vControlAdded((VControl) notifier);
			}
			if (VDomainModelReference.class.isInstance(notifier)) {
				final VControl control = findControl(VDomainModelReference.class.cast(notifier));
				if (control != null) {
					updateControlMapping(control);
				}

			}
			for (final ModelChangeListener modelChangeListener : viewModelChangeListener) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyAdd(notifier);
				}
			}
		}

		/**
		 * @param cast
		 * @return
		 */
		private VControl findControl(VDomainModelReference dmr) {
			EObject parent = dmr.eContainer();
			while (!VControl.class.isInstance(parent) && parent != null) {
				parent = parent.eContainer();
			}
			return (VControl) parent;
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
			if (VControl.class.isInstance(notifier)) {
				vControlRemoved((VControl) notifier);
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
			// if (EObject.class.isInstance(notifier)) {
			// eObjectAdded((EObject) notifier);
			// }
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
			// if (EObject.class.isInstance(notifier)) {
			// eObjectRemoved((EObject) notifier);
			// }
			for (final ModelChangeListener modelChangeListener : domainModelChangeListener) {
				if (ModelChangeAddRemoveListener.class.isInstance(modelChangeListener)) {
					ModelChangeAddRemoveListener.class.cast(modelChangeListener).notifyRemove(notifier);
				}
			}
		}

	}

	private final Set<Object> users = new LinkedHashSet<Object>();

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

		// childContext.addContextUser(this);

		if (!childContexts.containsKey(eObject)) {
			childContexts.put(eObject, new LinkedHashSet<ViewModelContext>());
		}
		childContexts.get(eObject).add(childContext);
		childContextUsers.put(childContext, vElement);

		// notify all global View model services
		for (final ViewModelService viewModelService : viewServices) {
			if (GlobalViewModelService.class.isInstance(viewModelService)) {
				GlobalViewModelService.class.cast(viewModelService).childViewModelContextAdded(childContext);
			}
		}
	}

	private void removeChildContext(EObject eObject) {
		final Set<ViewModelContext> removedContexts = childContexts.remove(eObject);
		if (removedContexts != null) {
			for (final ViewModelContext removedContext : removedContexts) {
				childContextUsers.remove(removedContext);
			}
		}
	}

	@Override
	public ViewModelContext getChildContext(final EObject eObject, VElement parent, VView vView,
		ViewModelService... viewModelServices) {
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
		final ViewModelContext childContext = new ViewModelContextImpl(vView, eObject, this, parent, viewModelServices);
		childContext.registerDisposeListener(new ViewModelContextDisposeListener() {

			@Override
			public void contextDisposed(ViewModelContext viewModelContext) {
				removeChildContext(eObject);
			}
		});
		addChildContext(parent, eObject, childContext);
		return childContext;
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
}
