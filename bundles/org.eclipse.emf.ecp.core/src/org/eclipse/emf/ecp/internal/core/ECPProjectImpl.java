/*******************************************************************************
 * Copyright (c) 2011-2015 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.core;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPElement;
import org.eclipse.emf.ecp.core.util.ECPFilterProvider;
import org.eclipse.emf.ecp.core.util.ECPModelContextAdapter;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectPreDeleteObserver;
import org.eclipse.emf.ecp.internal.core.util.PropertiesElement;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable.DisposeListener;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPProjectImpl extends PropertiesElement implements InternalProject, DisposeListener {

	private static final String PACKAGEFILTERS_EXTENSIONPOINT = "org.eclipse.emf.ecp.core.filters"; //$NON-NLS-1$

	private InternalRepository repository;

	private InternalProvider provider;

	private Object providerSpecificData;

	private Set<EPackage> filteredEPackages = Collections.emptySet();

	private Set<EClass> filteredEClasses = Collections.emptySet();

	private EditingDomain editingDomain;

	private boolean open;

	private boolean initialized = false;

	/**
	 * Constructor used when an offline project is created.
	 *
	 * @param provider the {@link InternalProvider} of this project
	 * @param name the name of the project
	 * @param properties the properties of the project
	 */
	public ECPProjectImpl(InternalProvider provider, String name, ECPProperties properties) {
		super(name, properties);
		this.provider = provider;
		open = true;
		setupFilteredEPackages();
		notifyProvider(LifecycleEvent.INIT);
	}

	/**
	 * Constructor used when an online project is created.
	 *
	 * @param repository the {@link ECPRepository} of this project
	 * @param name the name of the project
	 * @param properties the properties of the project
	 */
	public ECPProjectImpl(ECPRepository repository, String name, ECPProperties properties) {
		super(name, properties);

		if (repository == null) {
			throw new IllegalArgumentException("Repository is null"); //$NON-NLS-1$
		}

		setRepository((InternalRepository) repository);
		provider = getRepository().getProvider();
		open = true;
		setupFilteredEPackages();
		notifyProvider(LifecycleEvent.INIT);
	}

	/**
	 * Constructor used to load persisted projects on startup.
	 *
	 * @param in the {@link ObjectInput} to parse
	 * @throws IOException is thrown when file can't be read.
	 */
	public ECPProjectImpl(ObjectInput in) throws IOException {
		super(in);

		final boolean shared = in.readBoolean();
		if (shared) {
			final String repositoryName = in.readUTF();
			InternalRepository repository = (InternalRepository) ECPUtil.getECPRepositoryManager().getRepository(
				repositoryName);
			if (repository == null) {
				repository = new Disposed(repositoryName);
			}

			setRepository(repository);
			provider = repository.getProvider();
		} else {
			final String providerName = in.readUTF();
			provider = (InternalProvider) ECPUtil.getECPProviderRegistry().getProvider(providerName);
			if (provider == null) {
				throw new IllegalStateException("Provider not found: " + providerName); //$NON-NLS-1$
			}
		}

		open = in.readBoolean();

		final int filteredPackageSize = in.readInt();
		filteredEPackages = new HashSet<EPackage>();
		for (int i = 0; i < filteredPackageSize; i++) {
			final EPackage ePackage = Registry.INSTANCE.getEPackage(in.readUTF());
			if (ePackage != null) {
				filteredEPackages.add(ePackage);
			}
		}
		final int filteredEClassSize = in.readInt();
		filteredEClasses = new HashSet<EClass>();
		for (int i = 0; i < filteredEClassSize; i++) {
			final EPackage ePackage = Registry.INSTANCE.getEPackage(in.readUTF());
			final EClassifier eClassifier = ePackage.getEClassifier(in.readUTF());
			if (eClassifier instanceof EClass) {
				filteredEClasses.add((EClass) eClassifier);
			}
		}

		// do not initialize on startup, will be initializes by view
		// notifyProvider(LifecycleEvent.INIT);
	}

	/**
	 * this method sets all known {@link EPackage}s as the filter.
	 */
	private void setupFilteredEPackages() {
		final List<ECPFilterProvider> filterProviders = new ArrayList<ECPFilterProvider>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
			PACKAGEFILTERS_EXTENSIONPOINT);
		for (final IExtension extension : extensionPoint.getExtensions()) {
			final IConfigurationElement configurationElement = extension.getConfigurationElements()[0];
			try {
				final ECPFilterProvider filterProvider = (ECPFilterProvider) configurationElement
					.createExecutableExtension("class"); //$NON-NLS-1$
				filterProviders.add(filterProvider);
			} catch (final CoreException ex) {
				Activator.log(ex);
			}
		}

		final Set<EPackage> ePackages = new HashSet<EPackage>();
		final Set<String> filteredNsUris = new HashSet<String>();
		for (final ECPFilterProvider filterProvider : filterProviders) {
			filteredNsUris.addAll(filterProvider.getHiddenPackages());
		}

		final Set<String> relevantURIs = new HashSet<String>(Registry.INSTANCE.keySet());
		relevantURIs.removeAll(filteredNsUris);

		for (final String nsUri : relevantURIs) {
			final EPackage ePackage = Registry.INSTANCE.getEPackage(nsUri);
			ePackages.add(ePackage);
		}

		setVisiblePackages(ePackages);
	}

	@Override
	public void write(ObjectOutput out) throws IOException {
		super.write(out);
		if (repository != null) {
			out.writeBoolean(true);
			out.writeUTF(repository.getName());
		} else if (provider != null) {
			out.writeBoolean(false);
			out.writeUTF(provider.getName());
		}

		out.writeBoolean(open);

		out.writeInt(filteredEPackages.size());
		for (final EPackage ePackage : filteredEPackages) {
			out.writeUTF(ePackage.getNsURI());
		}
		out.writeInt(filteredEClasses.size());
		for (final EClass eClass : filteredEClasses) {

			out.writeUTF(eClass.getEPackage().getNsURI());
			out.writeUTF(eClass.getName());
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return TYPE;
	}

	/** {@inheritDoc} */
	@Override
	public void disposed(ECPDisposable disposable) {
		if (disposable == repository) {
			dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isStorable() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public InternalProject getProject() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public InternalRepository getRepository() {
		return repository;
	}

	private void setRepository(InternalRepository repository) {
		if (this.repository != null) {
			this.repository.removeDisposeListener(this);
		}

		this.repository = repository;

		if (this.repository != null) {

			this.repository.addDisposeListener(this);
		}
	}

	/** {@inheritDoc} */
	@Override
	public InternalProvider getProvider() {
		return provider;
	}

	/** {@inheritDoc} */
	@Override
	public Object getProviderSpecificData() {
		return providerSpecificData;
	}

	/** {@inheritDoc} */
	@Override
	public void setProviderSpecificData(Object providerSpecificData) {
		this.providerSpecificData = providerSpecificData;
	}

	/** {@inheritDoc} */
	@Override
	public void notifyObjectsChanged(Collection<Object> objects, boolean structural) {
		if (objects != null && objects.size() != 0) {
			// if (getProvider().isDirty(this)) {
			// getProvider().doSave(this);
			// }
			((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).notifyObjectsChanged(this, objects, structural);
		}
	}

	/** {@inheritDoc} */
	@Override
	public synchronized EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			editingDomain = getProvider().createEditingDomain(this);
		}

		return editingDomain;
	}

	/**
	 * Returns an object which is an instance of the given class associated with this object. Returns <code>null</code>
	 * if
	 * no such object can be found.
	 * <p>
	 * This implementation of the method declared by <code>IAdaptable</code> passes the request along to the platform's
	 * adapter manager; roughly <code>Platform.getAdapterManager().getAdapter(this, adapter)</code>. Subclasses may
	 * override this method (however, if they do so, they should invoke the method on their superclass to ensure that
	 * the Platform's adapter manager is consulted).
	 * </p>
	 *
	 * @param adapterType
	 *            the class to adapt to
	 * @return the adapted object or <code>null</code>
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class) IAdaptable#getAdapter(Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(Class adapterType) {
		final InternalProvider provider = getProvider();
		if (provider != null && !provider.isDisposed()) {
			final Object result = provider.getAdapter(this, adapterType);
			if (result != null) {
				return result;
			}
		}

		return Platform.getAdapterManager().getAdapter(this, adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean canDelete() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void delete() {
		ECPUtil.getECPObserverBus().notify(ECPProjectPreDeleteObserver.class).projectDelete(this);
		// FIXME https://bugs.eclipse.org/bugs/show_bug.cgi?id=462399
		cleanup();
		getProvider().handleLifecycle(this, LifecycleEvent.REMOVE);
		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).changeElements(Collections.singleton(getName()), null);
	}

	/** {@inheritDoc} */
	@Override
	public synchronized boolean isOpen() {
		return !isRepositoryDisposed() && open;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void open() {
		if (!isRepositoryDisposed()) {
			setOpen(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void close() {
		if (!isRepositoryDisposed()) {
			setOpen(false);
		}
	}

	private boolean isRepositoryDisposed() {
		return repository != null && repository.isDisposed();
	}

	private void setOpen(boolean open) {
		boolean modified = false;
		synchronized (this) {
			if (open != this.open) {
				this.open = open;
				modified = true;

				notifyProvider(open ? LifecycleEvent.INIT : LifecycleEvent.DISPOSE);

				if (!open) {
					providerSpecificData = null;
					editingDomain = null;
				}
			}
		}

		if (modified) {
			((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).changeProject(this, open, true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void notifyProvider(LifecycleEvent event) {
		// guard to prevent multiple initializations
		if (event == LifecycleEvent.INIT && initialized) {
			return;
		}
		final InternalProvider provider = getProvider();

		provider.handleLifecycle(this, event);
		if (event == LifecycleEvent.INIT) {
			initialized = true;
			final Notifier root = provider.getRoot(this);
			if (root != null) {
				root.eAdapters().add(new ECPModelContextAdapter(this));
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void undispose(InternalRepository repository) {
		setRepository(repository);
		notifyProvider(LifecycleEvent.INIT);

		if (open) {
			((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).changeProject(this, true, true);
		}
	}

	private void dispose() {
		notifyProvider(LifecycleEvent.DISPOSE);
		if (repository != null) {
			setRepository(new Disposed(repository.getName()));
		}

		providerSpecificData = null;
		editingDomain = null;

		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).changeProject(this, false, false);
	}

	/**
	 * @author Eike Stepper
	 */
	private static final class Disposed implements InternalRepository {
		private final String name;

		public Disposed(String name) {
			this.name = name;
		}

		/** {@inheritDoc} */
		@Override
		public String getName() {
			return name;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isDisposed() {
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public String getLabel() {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String getDescription() {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public int compareTo(ECPElement o) {
			return 0;
		}

		/** {@inheritDoc} */
		@Override
		public ECPProperties getProperties() {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public boolean canDelete() {
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public void delete() {
		}

		/** {@inheritDoc} */
		@Override
		public boolean isStorable() {
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public void write(ObjectOutput out) throws IOException {
		}

		/** {@inheritDoc} */
		@Override
		public void setLabel(String label) {
		}

		/** {@inheritDoc} */
		@Override
		public void setDescription(String description) {
		}

		/** {@inheritDoc} */
		@Override
		public void dispose() {
		}

		/** {@inheritDoc} */
		@Override
		public void addDisposeListener(DisposeListener listener) {
		}

		/** {@inheritDoc} */
		@Override
		public void removeDisposeListener(DisposeListener listener) {
		}

		/** {@inheritDoc} */
		@Override
		public InternalProvider getProvider() {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public Object getProviderSpecificData() {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public void setProviderSpecificData(Object data) {
		}

		/** {@inheritDoc} */
		@Override
		public void notifyObjectsChanged(Collection<Object> objects) {
		}
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public EList<Object> getContents() {
		return (EList<Object>) getProvider().getElements(this);
	}

	/** {@inheritDoc} */
	@Override
	public Set<EPackage> getUnsupportedEPackages() {
		return getProvider().getUnsupportedEPackages(ECPUtil.getAllRegisteredEPackages(), getRepository());
	}

	/** {@inheritDoc} */
	@Override
	public void setVisiblePackages(Set<EPackage> filteredPackages) {
		filteredEPackages = filteredPackages;
		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).storeElement(this);
	}

	/** {@inheritDoc} */
	@Override
	public Set<EPackage> getVisiblePackages() {
		return filteredEPackages;
	}

	/** {@inheritDoc} */
	@Override
	public Set<EClass> getVisibleEClasses() {
		return filteredEClasses;
	}

	/** {@inheritDoc} */
	@Override
	public void setVisibleEClasses(Set<EClass> filteredEClasses) {
		this.filteredEClasses = filteredEClasses;
		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).storeElement(this);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<EObject> getReferenceCandidates(EObject modelElement, EReference eReference) {
		return getProvider().getLinkElements(this, modelElement, eReference);
	}

	/** {@inheritDoc} */
	@Override
	public void saveContents() {
		getProvider().doSave(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasDirtyContents() {
		return getProvider().isDirty(this);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteElements(Collection<Object> objects) {
		getProvider().delete(this, objects);
		notifyObjectsChanged(Collections.singleton((Object) this), true);
	}

	/** {@inheritDoc} */
	@Override
	public InternalProject clone(String name) {
		try {
			super.clone();
		} catch (final CloneNotSupportedException ex) {
			Activator.log(ex);
		}
		final InternalProject project = new ECPProjectImpl(getProvider(), name, ECPUtil.createProperties());
		project.setVisibleEClasses(getVisibleEClasses());
		project.setVisiblePackages(getVisiblePackages());
		getProvider().cloneProject(this, project);
		return project;
	}

	@Override
	protected void propertiesChanged(Collection<Entry<String, String>> oldProperties,
		Collection<Entry<String, String>> newProperties) {
		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).storeElement(this);
	}

	/**
	 * You must not call this anymore as properties are save automatically now.
	 */
	@Override
	@Deprecated
	public void saveProperties() {
		((ECPProjectManagerImpl) ECPUtil.getECPProjectManager()).storeElement(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isModelRoot(Object object) {
		return getProvider().getRoot(this).equals(object);
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(Object object) {
		return getProvider().contains(this, object);
	}
}
