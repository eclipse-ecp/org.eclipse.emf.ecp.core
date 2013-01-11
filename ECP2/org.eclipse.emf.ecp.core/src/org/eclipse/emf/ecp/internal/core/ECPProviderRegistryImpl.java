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

import org.eclipse.net4j.util.AdapterUtil;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPModelContext;
import org.eclipse.emf.ecp.core.util.ECPProviderAware;
import org.eclipse.emf.ecp.core.util.observer.IECPProvidersChangedObserver;
import org.eclipse.emf.ecp.internal.core.util.ElementRegistry;
import org.eclipse.emf.ecp.internal.core.util.ExtensionParser;
import org.eclipse.emf.ecp.internal.core.util.ExtensionParser.ExtensionDescriptor;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.ecp.spi.core.util.AdapterProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.core.runtime.IConfigurationElement;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class manages {@link ECPProvider}.
 * 
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPProviderRegistryImpl extends ElementRegistry<InternalProvider, IECPProvidersChangedObserver>
	implements ECPProviderRegistry {
	/**
	 * The Singleton used in {@link ECPProviderRegistry#INSTANCE}.
	 */
	public static final ECPProviderRegistryImpl INSTANCE = new ECPProviderRegistryImpl();

	private final ProviderParser extensionParser = new ProviderParser();

	private ECPProviderRegistryImpl() {
	}

	/** {@inheritDoc} **/
	public InternalProvider getProvider(Object adaptable) {
		if (adaptable instanceof ECPProviderAware) {
			ECPProviderAware providerAware = (ECPProviderAware) adaptable;
			return (InternalProvider) providerAware.getProvider();
		}
		return AdapterUtil.adapt(adaptable, InternalProvider.class);
	}

	/** {@inheritDoc} **/
	public InternalProvider getProvider(String name) {
		return getElement(name);
	}

	/** {@inheritDoc} **/
	public InternalProvider[] getProviders() {
		return getElements();
	}

	/** {@inheritDoc} **/
	public boolean hasProviders() {
		return hasElements();
	}

	/** {@inheritDoc} **/
	public void addProvider(ECPProvider provider) {
		changeElements(null, Collections.singleton((InternalProvider) provider));
	}

	/** {@inheritDoc} **/
	public void removeProvider(String name) {
		changeElements(Collections.singleton(name), null);
	}

	@Override
	protected InternalProvider[] createElementArray(int size) {
		return new InternalProvider[size];
	}

	/** {@inheritDoc} **/
	@Override
	protected void notifyObservers(IECPProvidersChangedObserver observer, InternalProvider[] oldProviders,
		InternalProvider[] newProviders) throws Exception {
		observer.providersChanged(oldProviders, newProviders);
	}

	@Override
	protected void elementsChanged(InternalProvider[] oldElements, InternalProvider[] newElements) {
		super.elementsChanged(oldElements, newElements);
	}

	@Override
	protected void doActivate() throws Exception {
		super.doActivate();
		extensionParser.activate();
	}

	@Override
	protected void doDeactivate() throws Exception {
		extensionParser.deactivate();
		super.doDeactivate();
	}

	/**
	 * @author Eike Stepper
	 */
	private final class ProviderParser extends ExtensionParser<InternalProvider> {
		private static final String EXTENSION_POINT_NAME = "providers";

		public ProviderParser() {
			super(ECPProviderRegistryImpl.this, Activator.PLUGIN_ID, EXTENSION_POINT_NAME);
		}

		@Override
		protected InternalProvider createElement(String name, IConfigurationElement configurationElement) {
			ProviderDescriptor descriptor = new ProviderDescriptor(name, configurationElement);
			descriptor.setLabel(configurationElement.getDeclaringExtension().getLabel());
			descriptor.setDescription(configurationElement.getAttribute("description"));
			return descriptor;
		}
	}

	/**
	 * @author Eike Stepper
	 */
	private final class ProviderDescriptor extends ExtensionDescriptor<InternalProvider> implements InternalProvider {
		private AdapterProvider uiProvider;

		public ProviderDescriptor(String name, IConfigurationElement configurationElement) {
			super(ECPProviderRegistryImpl.this, name, TYPE, configurationElement);
		}

		public ECPProvider getProvider() {
			return this;
		}

		public AdapterProvider getUIProvider() {
			return uiProvider;
		}

		public void setUIProvider(AdapterProvider uiProvider) {
			this.uiProvider = uiProvider;
			if (isResolved()) {
				getResolvedElement().setUIProvider(uiProvider);
			}
		}

		public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
			return getResolvedElement().getAdapter(adaptable, adapterType);
		}

		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterType) {
			return getResolvedElement().getAdapter(adapterType);
		}

		public ECPRepository[] getRepositories() {
			return getResolvedElement().getRepositories();
		}

		public ECPProject[] getOpenProjects() {
			return getResolvedElement().getOpenProjects();
		}

		public EditingDomain createEditingDomain(InternalProject project) {
			return getResolvedElement().createEditingDomain(project);
		}

		public boolean canAddRepositories() {
			return getResolvedElement().canAddRepositories();
		}

		public boolean isSlow(Object parent) {
			return getResolvedElement().isSlow(parent);
		}

		public ECPModelContext getModelContext(Object element) {
			return getResolvedElement().getModelContext(element);
		}

		public void fillChildren(ECPModelContext context, Object parent, InternalChildrenList childrenList) {
			getResolvedElement().fillChildren(context, parent, childrenList);
		}

		public void handleLifecycle(ECPModelContext context, LifecycleEvent event) {
			getResolvedElement().handleLifecycle(context, event);
		}

		@Override
		protected void resolvedElement(InternalProvider provider) {
			super.resolvedElement(provider);
			provider.setLabel(getLabel());
			provider.setDescription(getDescription());
			provider.setUIProvider(uiProvider);
		}

		@Override
		protected void doDispose() {
			uiProvider = null;
			super.doDispose();
		}

		public boolean hasUnsharedProjectSupport() {
			return getResolvedElement().hasUnsharedProjectSupport();
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.emf.ecp.spi.core.InternalProvider#getElements(org.eclipse.emf.ecp.internal.core.ECPProjectImpl)
		 */
		public EList<? extends Object> getElements(InternalProject project) {
			return getResolvedElement().getElements(project);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#getLinkElements(org.eclipse.emf.ecp.core.ECPProject,
		 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference)
		 */
		public Iterator<EObject> getLinkElements(InternalProject project, EObject modelElement, EReference eReference) {
			return getResolvedElement().getLinkElements(project, modelElement, eReference);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#getUnsupportedEPackages(java.util.Collection,
		 * org.eclipse.emf.ecp.spi.core.InternalRepository)
		 */
		public Collection<EPackage> getUnsupportedEPackages(Collection<EPackage> ePackages,
			InternalRepository repository) {
			return getResolvedElement().getUnsupportedEPackages(ePackages, repository);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#doSave(org.eclipse.emf.ecp.spi.core.InternalProject)
		 */
		public void doSave(InternalProject project) {
			getResolvedElement().doSave(project);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#isDirty(org.eclipse.emf.ecp.spi.core.InternalProject)
		 */
		public boolean isDirty(InternalProject project) {
			return getResolvedElement().isDirty(project);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#delete(org.eclipse.emf.ecp.spi.core.InternalProject,
		 * java.util.Collection)
		 */
		public void delete(InternalProject project, Collection<EObject> eObjects) {
			getResolvedElement().delete(project, eObjects);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.emf.ecp.spi.core.InternalProvider#cloneProject(org.eclipse.emf.ecp.internal.core.ECPProjectImpl,
		 * org.eclipse.emf.ecp.spi.core.InternalProject)
		 */
		public void cloneProject(InternalProject projectToClone, InternalProject targetProject) {
			getResolvedElement().cloneProject(projectToClone, targetProject);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * org.eclipse.emf.ecp.spi.core.InternalProvider#projectExists(org.eclipse.emf.ecp.spi.core.InternalProject)
		 */
		public boolean modelExists(InternalProject project) {
			return getResolvedElement().modelExists(project);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecp.spi.core.InternalProvider#getRoot(org.eclipse.emf.ecp.spi.core.InternalProject)
		 */
		public Notifier getRoot(InternalProject project) {
			return getResolvedElement().getRoot(project);
		}
	}
}
