/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPRepositoryAware;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPPropertiesObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPRepositoriesChangedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPRepositoryContentChangedObserver;
import org.eclipse.emf.ecp.internal.core.util.ExtensionParser;
import org.eclipse.emf.ecp.internal.core.util.ExtensionParser.ExtensionDescriptor;
import org.eclipse.emf.ecp.internal.core.util.InternalUtil;
import org.eclipse.emf.ecp.internal.core.util.Properties;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.net4j.util.AdapterUtil;
import org.eclipse.net4j.util.io.IOUtil;

/**
 * This class manages the repositories.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPRepositoryManagerImpl extends PropertiesStore<InternalRepository, ECPObserver> implements
	ECPRepositoryManager, ECPProvidersChangedObserver {

	/**
	 * The file extension that is used for dynamic properties of statically declared repositories.
	 */
	private static final String DYNAMIC_PROPERTIES_EXTENSION = ".dynamic_properties";

	private final RepositoryParser extensionParser = new RepositoryParser();

	/**
	 * Should not be called directly, use service instead.
	 */
	public ECPRepositoryManagerImpl() {

		final File stateLocation = Activator.getInstance().getStateLocation().toFile();
		setFolder(new File(stateLocation, "repositories"));
	}

	/** {@inheritDoc} **/
	@Override
	public InternalRepository getRepository(Object adaptable) {
		if (adaptable instanceof ECPRepositoryAware) {
			final ECPRepositoryAware repositoryAware = (ECPRepositoryAware) adaptable;
			return (InternalRepository) repositoryAware.getRepository();
		}

		return AdapterUtil.adapt(adaptable, InternalRepository.class);
	}

	/** {@inheritDoc} **/
	@Override
	public InternalRepository getRepository(String name) {
		return getElement(name);
	}

	/** {@inheritDoc} **/
	@Override
	public Collection<ECPRepository> getRepositories() {
		return (Collection) getElements();
	}

	/**
	 * Checks whether any repositories are available.
	 *
	 * @return true if any repository is available, false otherwise
	 */
	public boolean hasRepositories() {
		return hasElements();
	}

	/** {@inheritDoc} **/
	@Override
	public ECPRepository addRepository(ECPProvider provider, String name, String label, String description,
		ECPProperties properties) {
		if (!provider.hasCreateRepositorySupport()) {
			throw new UnsupportedOperationException("The provider " + provider.getLabel()
				+ " doesn't support the addition of new repositories.");
		}
		final InternalRepository repository = new ECPRepositoryImpl(provider, name, properties);
		repository.setLabel(label);
		repository.setDescription(description);

		((InternalProvider) provider).handleLifecycle(repository, LifecycleEvent.CREATE);
		changeElements(null, Collections.singleton(repository));
		return repository;
	}

	/**
	 * This is called by the {@link ECPRepository} to notificate observers about chnages it its objects.
	 *
	 * @param repository the repository where the changes occured
	 * @param objects the changed objects
	 */
	public void notifyObjectsChanged(ECPRepository repository, Collection<Object> objects) {

		try {
			ECPUtil.getECPObserverBus().notify(ECPRepositoryContentChangedObserver.class)
				.contentChanged(repository, objects);
		} catch (final Exception ex) {
			Activator.log(ex);
		}
	}

	/** {@inheritDoc} **/
	@Override
	public void providersChanged(Collection<ECPProvider> oldProviders, Collection<ECPProvider> newProviders) {
		final Set<ECPProvider> addedProviders = InternalUtil.getAddedElements(oldProviders, newProviders);
		if (!addedProviders.isEmpty()) {
			load();
		}
	}

	@Override
	protected File getFile(InternalRepository element) {
		if (element instanceof RepositoryDescriptor) {
			return new File(getFolder(), element.getName() + DYNAMIC_PROPERTIES_EXTENSION);
		}
		return super.getFile(element);
	}

	@Override
	protected boolean isLoadableElement(File file) {
		return super.isLoadableElement(file) && !file.getName().endsWith(DYNAMIC_PROPERTIES_EXTENSION);
	}

	@Override
	protected InternalRepository loadElement(ObjectInput in) throws IOException {
		return new ECPRepositoryImpl(in);
	}

	// @Override
	// protected InternalRepository[] createElementArray(int size) {
	// return new InternalRepository[size];
	// }

	@Override
	protected void notifyObservers(Collection<InternalRepository> oldRepositories,
		Collection<InternalRepository> newRepositories) throws Exception {
		ECPUtil.getECPObserverBus().notify(ECPRepositoriesChangedObserver.class)
			.repositoriesChanged((Collection) oldRepositories, (Collection) newRepositories);
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate(); // 1. Load dynamic repositories
		extensionParser.activate(); // 2. Register static repositories
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	protected void doDeactivate() throws Exception {
		ECPUtil.getECPObserverBus().unregister(this);
		extensionParser.deactivate();
		super.doDeactivate();
	}

	/**
	 * @author Eike Stepper
	 */
	private final class RepositoryParser extends ExtensionParser<InternalRepository> {
		private static final String EXTENSION_POINT_NAME = "repositories";

		RepositoryParser() {
			super(ECPRepositoryManagerImpl.this, Activator.PLUGIN_ID, EXTENSION_POINT_NAME);
		}

		@Override
		protected InternalRepository createElement(String name, IConfigurationElement configurationElement) {
			final RepositoryDescriptor descriptor = new RepositoryDescriptor(name, configurationElement);
			descriptor.setLabel(configurationElement.getDeclaringExtension().getLabel());
			descriptor.setDescription(configurationElement.getAttribute("description"));
			return descriptor;
		}
	}

	/**
	 * @author Eike Stepper
	 */
	private final class RepositoryDescriptor extends ExtensionDescriptor<InternalRepository> implements
		InternalRepository {
		private final Set<String> declaredPropertyKeys;
		private final ECPProperties properties = new Properties() {
			@Override
			public void addProperty(String key, String value) {
				excludeDeclaredProperties(key);
				super.addProperty(key, value);
			}

			@Override
			public void removeProperty(String key) {
				excludeDeclaredProperties(key);
				super.removeProperty(key);
			}

			private void excludeDeclaredProperties(String key) {
				if (declaredPropertyKeys != null && declaredPropertyKeys.contains(key)) {
					throw new IllegalArgumentException("Statically declared property can not be changed: " + key);
				}
			}

			@Override
			protected Collection<Map.Entry<String, String>> getElementsToWrite() {
				final List<Map.Entry<String, String>> elementsToWrite = new ArrayList<Map.Entry<String, String>>();
				for (final Map.Entry<String, String> entry : getElements()) {
					if (!declaredPropertyKeys.contains(entry.getKey())) {
						elementsToWrite.add(entry);
					}
				}
				return elementsToWrite;
			}
		};

		RepositoryDescriptor(String name, IConfigurationElement configurationElement) {
			super(ECPRepositoryManagerImpl.this, name, TYPE, configurationElement);
			for (final IConfigurationElement property : configurationElement.getChildren("property")) {
				final String key = property.getAttribute("key");
				final String value = property.getAttribute("value");
				properties.addProperty(key, value);
			}

			declaredPropertyKeys = new HashSet<String>(properties.getKeys());

			InputStream stream = null;

			try {
				final File file = getFile(this);
				if (file.exists()) {
					stream = new FileInputStream(file);
					final ObjectInputStream in = new ObjectInputStream(stream);

					final Properties dynamicProperties = new Properties(in);
					for (final Entry<String, String> property : dynamicProperties.getProperties()) {
						properties.addProperty(property.getKey(), property.getValue());
					}
				}
			} catch (final IOException ex) {
				Activator.log(ex);
			} finally {
				IOUtil.close(stream);
			}

			properties.addObserver(new ECPPropertiesObserver() {
				@Override
				public void propertiesChanged(ECPProperties properties,
					Collection<Entry<String, String>> oldProperties, Collection<Entry<String, String>> newProperties) {
					((ECPRepositoryManagerImpl) ECPUtil.getECPRepositoryManager())
						.storeElement(RepositoryDescriptor.this);
				}
			});
		}

		/** {@inheritDoc} */
		@Override
		public boolean isStorable() {
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public void write(ObjectOutput out) throws IOException {
			((Properties) properties).write(out);
		}

		/** {@inheritDoc} */
		@Override
		public InternalProvider getProvider() {
			final String providerName = getConfigurationElement().getAttribute("provider");
			return (InternalProvider) ECPUtil.getECPProviderRegistry().getProvider(providerName);
		}

		/** {@inheritDoc} */
		@Override
		public ECPProperties getProperties() {
			return properties;
		}

		/** {@inheritDoc} */
		@Override
		public Object getProviderSpecificData() {
			return getResolvedElement().getProviderSpecificData();
		}

		/** {@inheritDoc} */
		@Override
		public void setProviderSpecificData(Object data) {
			getResolvedElement().setProviderSpecificData(data);
		}

		/** {@inheritDoc} */
		@Override
		public boolean canDelete() {
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public void delete() {
			throw new UnsupportedOperationException();
		}

		/** {@inheritDoc} */
		@Override
		public void notifyObjectsChanged(Collection<Object> objects) {
			getResolvedElement().notifyObjectsChanged(objects);
		}

		/** {@inheritDoc} */
		@Override
		protected InternalRepository resolve() throws Exception {
			return new ECPRepositoryImpl(getProvider(), getName(), getProperties());
		}
	}
}
