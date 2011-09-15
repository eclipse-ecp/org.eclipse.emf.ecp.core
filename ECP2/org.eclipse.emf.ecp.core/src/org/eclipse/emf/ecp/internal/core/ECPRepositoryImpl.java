/*
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.internal.core;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPDisposable;
import org.eclipse.emf.ecp.core.util.ECPDisposable.DisposeListener;
import org.eclipse.emf.ecp.core.util.ECPModelContext;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.internal.core.util.Disposable;
import org.eclipse.emf.ecp.internal.core.util.PropertiesElement;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.InternalProvider.LifecycleEvent;
import org.eclipse.emf.ecp.spi.core.InternalRepository;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eike Stepper
 */
public final class ECPRepositoryImpl extends PropertiesElement implements InternalRepository, DisposeListener
{
  private final Disposable disposable = new Disposable(this)
  {
    @Override
    protected void doDispose()
    {
      provider = null;
      providerSpecificData = null;
    }
  };

  private String label;

  private String description;

  private InternalProvider provider;

  private Object providerSpecificData;

  public ECPRepositoryImpl(ECPProvider provider, String name, ECPProperties properties)
  {
    super(name, properties);
    label = name;
    description = "";

    if (provider == null)
    {
      throw new IllegalArgumentException("Provider is null");
    }

    this.provider = (InternalProvider)provider;
    this.provider.addDisposeListener(this);
  }

  public ECPRepositoryImpl(ObjectInput in) throws IOException
  {
    super(in);

    label = in.readUTF();
    description = in.readUTF();

    String providerName = in.readUTF();
    provider = (InternalProvider)ECPProviderRegistry.INSTANCE.getProvider(providerName);
    if (provider == null)
    {
      throw new IllegalStateException("Provider not found: " + providerName);
    }

    provider.addDisposeListener(this);
  }

  public String getType()
  {
    return ECPRepository.TYPE;
  }

  public void disposed(ECPDisposable disposable) throws Exception
  {
    if (disposable == provider)
    {
      dispose();
    }
  }

  public boolean isStorable()
  {
    return true;
  }

  @Override
  public void write(ObjectOutput out) throws IOException
  {
    super.write(out);
    out.writeUTF(label);
    out.writeUTF(description);
    out.writeUTF(provider.getName());
  }

  public final String getLabel()
  {
    return label;
  }

  public final void setLabel(String label)
  {
    this.label = label;
  }

  public final String getDescription()
  {
    return description;
  }

  public final void setDescription(String description)
  {
    this.description = description;
  }

  public final boolean isDisposed()
  {
    return disposable.isDisposed();
  }

  /**
   * Returns an object which is an instance of the given class associated with this object. Returns <code>null</code> if
   * no such object can be found.
   * <p>
   * This implementation of the method declared by <code>IAdaptable</code> passes the request along to the platform's
   * adapter manager; roughly <code>Platform.getAdapterManager().getAdapter(this, adapter)</code>. Subclasses may
   * override this method (however, if they do so, they should invoke the method on their superclass to ensure that the
   * Platform's adapter manager is consulted).
   * </p>
   * 
   * @param adapterType
   *          the class to adapt to
   * @return the adapted object or <code>null</code>
   * @see IAdaptable#getAdapter(Class)
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Object getAdapter(Class adapterType)
  {
    InternalProvider provider = getProvider();
    if (!provider.isDisposed())
    {
      Object result = provider.getAdapter(this, adapterType);
      if (result != null)
      {
        return result;
      }
    }

    return Platform.getAdapterManager().getAdapter(this, adapterType);
  }

  public final void dispose()
  {
    disposable.dispose();
  }

  public final void addDisposeListener(DisposeListener listener)
  {
    disposable.addDisposeListener(listener);
  }

  public final void removeDisposeListener(DisposeListener listener)
  {
    disposable.removeDisposeListener(listener);
  }

  public InternalRepository getRepository()
  {
    return this;
  }

  public InternalProvider getProvider()
  {
    return provider;
  }

  public Object getProviderSpecificData()
  {
    return providerSpecificData;
  }

  public void setProviderSpecificData(Object providerSpecificData)
  {
    this.providerSpecificData = providerSpecificData;
  }

  public ECPModelContext getContext()
  {
    return this;
  }

  public boolean canDelete()
  {
    return isStorable();
  }

  public void delete()
  {
    if (!canDelete())
    {
      throw new UnsupportedOperationException();
    }

    try
    {
      provider.handleLifecycle(this, LifecycleEvent.REMOVE);
    }
    catch (Exception ex)
    {
      Activator.log(ex);
    }

    ECPRepositoryManagerImpl.INSTANCE.changeElements(Collections.singleton(getName()), null);
  }

  public void notifyObjectsChanged(Object[] objects)
  {
    if (objects != null && objects.length != 0)
    {
      ECPRepositoryManagerImpl.INSTANCE.notifyObjectsChanged(this, objects);
    }
  }

  public InternalProject[] getOpenProjects()
  {
    List<InternalProject> result = new ArrayList<InternalProject>();
    for (ECPProject project : ECPProjectManager.INSTANCE.getProjects())
    {
      if (project.isOpen() && project.getRepository().equals(this))
      {
        result.add((InternalProject)project);
      }
    }

    // TODO Consider to cache the result
    return result.toArray(new InternalProject[result.size()]);
  }

  public String getDefaultCheckoutName()
  {
    return getName();
  }

  public ECPProject checkout(String projectName, ECPProperties projectProperties)
  {
    InternalProject project = new ECPProjectImpl(this, projectName, projectProperties);
    provider.handleLifecycle(project, LifecycleEvent.CREATE);
    ECPProjectManagerImpl.INSTANCE.changeElements(null, Collections.singleton(project));
    return project;
  }
}
