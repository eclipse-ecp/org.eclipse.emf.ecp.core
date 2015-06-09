/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.util.Disposable;
import org.eclipse.emf.ecp.internal.core.util.PropertiesElement;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.ecp.spi.core.util.DisposeException;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable;
import org.eclipse.emf.ecp.spi.core.util.ECPDisposable.DisposeListener;
import org.eclipse.net4j.util.ReflectUtil.ExcludeFromDump;

/**
 * This Class describes a repository.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class ECPRepositoryImpl extends PropertiesElement implements InternalRepository, DisposeListener {
	@ExcludeFromDump
	private final Disposable disposable = new Disposable(this) {
		@Override
		protected void doDispose() {
			provider = null;
			providerSpecificData = null;
		}
	};

	private String label;

	private String description;

	private InternalProvider provider;

	private Object providerSpecificData;

	/**
	 * Constructor used to create an instance through user input.
	 *
	 * @param provider the {@link ECPProvider} for this repository
	 * @param name the name of this repository
	 * @param properties the {@link ECPProperties} of this repository
	 */
	public ECPRepositoryImpl(ECPProvider provider, String name, ECPProperties properties) {
		super(name, properties);
		label = name;
		description = "";

		if (provider == null) {
			throw new IllegalArgumentException("Provider is null");
		}

		this.provider = (InternalProvider) provider;
		this.provider.addDisposeListener(this);
	}

	/**
	 * Constructor used by the {@link org.eclipse.emf.ecp.core.ECPRepositoryManager ECPRepositoryManager} when loading
	 * existing repositories during startup.
	 *
	 * @param in the {@link ObjectInput} to parse
	 * @throws IOException when an error occurs
	 */
	public ECPRepositoryImpl(ObjectInput in) throws IOException {
		super(in);

		label = in.readUTF();
		description = in.readUTF();

		final String providerName = in.readUTF();
		provider = (InternalProvider) ECPUtil.getECPProviderRegistry().getProvider(providerName);
		if (provider == null) {
			throw new IllegalStateException("Provider not found: " + providerName);
		}

		provider.addDisposeListener(this);
	}

	/** {@inheritDoc} **/
	@Override
	public String getType() {
		return ECPRepository.TYPE;
	}

	/** {@inheritDoc} **/
	@Override
	public void disposed(ECPDisposable disposable) throws DisposeException {
		if (disposable == provider) {
			dispose();
		}
	}

	/** {@inheritDoc} **/
	@Override
	public boolean isStorable() {
		return true;
	}

	@Override
	public void write(ObjectOutput out) throws IOException {
		super.write(out);
		out.writeUTF(label);
		out.writeUTF(description);
		out.writeUTF(provider.getName());
	}

	/** {@inheritDoc} **/
	@Override
	public String getLabel() {
		return label;
	}

	/** {@inheritDoc} **/
	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	/** {@inheritDoc} **/
	@Override
	public String getDescription() {
		return description;
	}

	/** {@inheritDoc} **/
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/** {@inheritDoc} **/
	@Override
	public boolean isDisposed() {
		return disposable.isDisposed();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(Class adapterType) {
		final InternalProvider provider = getProvider();
		if (!provider.isDisposed()) {
			final Object result = provider.getAdapter(this, adapterType);
			if (result != null) {
				return result;
			}
		}

		return Platform.getAdapterManager().getAdapter(this, adapterType);
	}

	/** {@inheritDoc} **/
	@Override
	public void dispose() {
		disposable.dispose();
	}

	/** {@inheritDoc} **/
	@Override
	public void addDisposeListener(DisposeListener listener) {
		disposable.addDisposeListener(listener);
	}

	/** {@inheritDoc} **/
	@Override
	public void removeDisposeListener(DisposeListener listener) {
		disposable.removeDisposeListener(listener);
	}

	/** {@inheritDoc} **/
	@Override
	public InternalProvider getProvider() {
		return provider;
	}

	/** {@inheritDoc} **/
	@Override
	public Object getProviderSpecificData() {
		return providerSpecificData;
	}

	/** {@inheritDoc} **/
	@Override
	public void setProviderSpecificData(Object providerSpecificData) {
		this.providerSpecificData = providerSpecificData;
	}

	/** {@inheritDoc} **/
	@Override
	public boolean canDelete() {
		return isStorable();
	}

	/** {@inheritDoc} **/
	@Override
	public void delete() {
		if (!canDelete()) {
			throw new UnsupportedOperationException();
		}
		// FIXME https://bugs.eclipse.org/bugs/show_bug.cgi?id=462399
		cleanup();
		try {
			provider.handleLifecycle(this, LifecycleEvent.REMOVE);
		} catch (final Exception ex) {
			Activator.log(ex);
		}

		((ECPRepositoryManagerImpl) ECPUtil.getECPRepositoryManager()).changeElements(Collections.singleton(getName()),
			null);
	}

	/** {@inheritDoc} **/
	@Override
	public void notifyObjectsChanged(Collection<Object> objects) {
		if (objects != null && objects.size() != 0) {
			((ECPRepositoryManagerImpl) ECPUtil.getECPRepositoryManager()).notifyObjectsChanged(this, objects);
		}
	}

	/**
	 * Return all open projects of that are shared on this repository.
	 *
	 * @return array of currently open {@link ECPProject ECPProjects} that are shared on this repository
	 */
	public InternalProject[] getOpenProjects() {
		final List<InternalProject> result = new ArrayList<InternalProject>();
		for (final ECPProject project : ECPUtil.getECPProjectManager().getProjects()) {
			if (project.isOpen() && project.getRepository().equals(this)) {
				result.add((InternalProject) project);
			}
		}

		// TODO Consider to cache the result
		return result.toArray(new InternalProject[result.size()]);
	}

	@Override
	protected void propertiesChanged(Collection<Entry<String, String>> oldProperties,
		Collection<Entry<String, String>> newProperties) {
		((ECPRepositoryManagerImpl) ECPUtil.getECPRepositoryManager()).storeElement(this);
	}
}
