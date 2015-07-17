/**
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.spi.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextAdapter;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.internal.core.util.Disposable;
import org.eclipse.emf.ecp.internal.core.util.Element;
import org.eclipse.emf.ecp.spi.core.util.AdapterProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

/**
 * @author Eike Stepper
 * @since 1.1
 */
public abstract class DefaultProvider extends Element implements InternalProvider {
	private final Disposable disposable = new Disposable(this) {
		@Override
		protected void doDispose() {
			uiProvider = null;

			for (final ECPRepository repository : ECPUtil.getECPRepositoryManager().getRepositories()) {
				if (repository.getProvider().getName().equals(getName())) {
					handleLifecycle(repository, LifecycleEvent.DISPOSE);
				}
			}

			for (final ECPProject project : ECPUtil.getECPProjectManager().getProjects()) {
				if (project.getProvider().getName().equals(getName())) {
					handleLifecycle(project, LifecycleEvent.DISPOSE);
				}
			}

			DefaultProvider.this.doDispose();
		}
	};

	private String label;

	private String description;

	private AdapterProvider uiProvider;

	/**
	 * Convenient constructor for an {@link org.eclipse.emf.ecp.core.ECPProvider ECPProvider}.
	 *
	 * @param name the name of the implementing provider
	 */
	protected DefaultProvider(String name) {
		super(name);
		label = name;
		description = ""; //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	@Override
	public final String getType() {
		return TYPE;
	}

	/** {@inheritDoc} */
	@Override
	public final InternalProvider getProvider() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final String getLabel() {
		return label;
	}

	/** {@inheritDoc} */
	@Override
	public final void setLabel(String label) {
		this.label = label;
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public final void setDescription(String description) {
		this.description = description;
	}

	/** {@inheritDoc} */
	@Override
	public final AdapterProvider getUIProvider() {
		return uiProvider;
	}

	/** {@inheritDoc} */
	@Override
	public final void setUIProvider(AdapterProvider uiProvider) {
		this.uiProvider = uiProvider;
	}

	/** {@inheritDoc} */
	@Override
	public final Set<InternalProject> getOpenProjects() {
		final Set<InternalProject> result = new LinkedHashSet<InternalProject>();
		for (final ECPProject project : ECPUtil.getECPProjectManager().getProjects()) {
			if (project.isOpen()) {
				if (project.getProvider().equals(this)) {
					result.add((InternalProject) project);
				}

			}
		}

		// TODO Consider to cache the result
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isDisposed() {
		return disposable.isDisposed();
	}

	/** {@inheritDoc} */
	@Override
	public final void dispose() {
		disposable.dispose();
	}

	/** {@inheritDoc} */
	@Override
	public final void addDisposeListener(DisposeListener listener) {
		disposable.addDisposeListener(listener);
	}

	/** {@inheritDoc} */
	@Override
	public final void removeDisposeListener(DisposeListener listener) {
		disposable.removeDisposeListener(listener);
	}

	/**
	 * This method is called when a provider is disposed. Subclasses which need to dispose should overwrite this.
	 */
	protected void doDispose() {
	}

	/** {@inheritDoc} */
	@Override
	public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
		if (uiProvider != null) {
			return uiProvider.getAdapter(adaptable, adapterType);
		}

		return null;
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
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterType) {
		return Platform.getAdapterManager().getAdapter(this, adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public EditingDomain createEditingDomain(InternalProject project) {
		final CommandStack commandStack = createCommandStack(project);
		final EditingDomain editingDomain = new AdapterFactoryEditingDomain(InternalProvider.EMF_ADAPTER_FACTORY,
			commandStack);
		editingDomain.getResourceSet().eAdapters().add(new ECPModelContextAdapter(project));
		return editingDomain;
	}

	/**
	 * This is used during the creation of the {@link EditingDomain}. This implementation creates an
	 * {@link BasicCommandStack}.
	 *
	 * @param project the project to create the {@link CommandStack} for
	 * @return the created {@link CommandStack}
	 */
	protected CommandStack createCommandStack(InternalProject project) {
		return new BasicCommandStack();
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasCreateRepositorySupport() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSlow(Object parent) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public ECPContainer getModelContext(Object element) {
		if (element instanceof ECPContainer) {
			return (ECPContainer) element;
		}

		if (element instanceof ECPModelContextProvider) {
			return ((ECPModelContextProvider) element).getModelContext(element);
		}

		if (element instanceof EObject) {
			final EObject eObject = (EObject) element;
			final ECPContainer context = getModelContextFromAdapter(eObject);
			if (context != null) {
				return context;
			}

			element = eObject.eResource();
		}

		if (element instanceof Resource) {
			final Resource resource = (Resource) element;
			final ECPContainer context = getModelContextFromAdapter(resource);
			if (context != null) {
				return context;
			}

			element = resource.getResourceSet();
		}

		if (element instanceof ResourceSet) {
			final ResourceSet resourceSet = (ResourceSet) element;
			final ECPContainer context = getModelContextFromAdapter(resourceSet);
			if (context != null) {
				return context;
			}
		}

		return null;
	}

	/**
	 * This allows to get the {@link ECPContainer} from a {@link Notifier} using the EcoreUtil.
	 * This first gets the {@link ECPModelContextAdapter} and from it it gets the {@link ECPContainer}.
	 *
	 * @param notifier the {@link Notifier} to get the {@link ECPContainer} from
	 * @return the {@link ECPContainer} registered as an Adapter on this {@link Notifier} or null
	 */
	protected final ECPContainer getModelContextFromAdapter(Notifier notifier) {
		final ECPModelContextAdapter adapter = (ECPModelContextAdapter) EcoreUtil.getAdapter(notifier.eAdapters(),
			ECPModelContextAdapter.class);
		if (adapter != null) {
			return adapter.getContext();
		}

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void fillChildren(ECPContainer context, Object parent, InternalChildrenList childrenList) {
		if (parent == ECPUtil.getECPProjectManager()) {
			childrenList.addChildren(ECPUtil.getECPProjectManager().getProjects());
		} else if (parent == ECPUtil.getECPRepositoryManager()) {
			childrenList.addChildren(ECPUtil.getECPRepositoryManager().getRepositories());
		} else {
			// Get the adapter from the factory.
			final ITreeItemContentProvider treeItemContentProvider = (ITreeItemContentProvider) EMF_ADAPTER_FACTORY
				.adapt(
					parent, ITreeItemContentProvider.class);

			// Either delegate the call or return nothing.
			if (treeItemContentProvider != null) {
				final Collection<?> children = treeItemContentProvider.getChildren(parent);
				childrenList.addChildren(children);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void handleLifecycle(ECPContainer context, LifecycleEvent event) {
		final String providerClass = getClass().getSimpleName();
		final String contextClass = context.getClass().getSimpleName();
		Activator.log(providerClass + " received " + event + " for " + contextClass + " " + context); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Convenient implementation of the {@link #getUnsupportedEPackages(Collection,InternalRepository)} method to return
	 * an empty list. The
	 * provider has to {@link Override} this method if not all {@link EPackage}s are supported.
	 *
	 * @param packages available packages
	 * @param repository the repository to check
	 *
	 * @return the {@link Collection} of {@link EPackage EPackages} unsupported by this provider for the specified
	 *         repository
	 */
	@Override
	public Set<EPackage> getUnsupportedEPackages(Collection<EPackage> packages, InternalRepository repository) {
		return Collections.emptySet();
	}

	/**
	 * Convenient implementation of the {@link #getLinkElements(InternalProject, EObject, EReference)} method to use the
	 * {@link ItemPropertyDescriptor} to get all object of an object.
	 *
	 * @param project
	 *            the project the call is from
	 * @param modelElement
	 *            {@link EObject} to add the {@link EReference} to
	 * @param eReference
	 *            the {@link EReference} to add
	 * @return {@link Iterator} of {@link EObject} that can be linked
	 */
	@Override
	public Iterator<EObject> getLinkElements(InternalProject project, EObject modelElement, EReference eReference) {
		final EClass elementClass = modelElement.eClass();
		EClassifier type = EcoreUtil.getReifiedType(elementClass, eReference.getEGenericType()).getEClassifier();
		if (type == null) {
			type = eReference.getEType();
		}
		return ItemPropertyDescriptor.getReachableObjectsOfType(modelElement, type).iterator();
	}

	/**
	 * Convenient implementation where nothing happens.
	 *
	 * @param project
	 *            the project to save
	 */
	@Override
	public void doSave(InternalProject project) {
		// do nothing
	}

	/**
	 * Convenient implementation where the provider saves changes of the project automatically, so a project never gets
	 * dirty. Thus this returns false.
	 *
	 * @param project
	 *            the project to check
	 * @return false
	 */
	@Override
	public boolean isDirty(InternalProject project) {
		return false;
	}

	/**
	 * Convenient implementation that return true during this check.
	 *
	 * @param project the project to check
	 * @return true
	 */
	@Override
	public boolean modelExists(InternalProject project) {
		return true;
	}

	/**
	 * Convenient implementation that return false.
	 *
	 * @return false
	 */
	@Override
	public boolean hasCreateProjectWithoutRepositorySupport() {
		return false;
	}

	/** {@inheritDoc} */
	// FIXME implement only in provider
	@Override
	public boolean contains(InternalProject project, Object object) {
		return false;
	}
}
