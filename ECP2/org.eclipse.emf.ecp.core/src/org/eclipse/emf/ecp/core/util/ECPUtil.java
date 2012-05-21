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
package org.eclipse.emf.ecp.core.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecp.internal.core.util.ElementDescriptor;
import org.eclipse.emf.ecp.internal.core.util.Properties;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Eike Stepper
 */
public final class ECPUtil
{
  public static final int NOT_FOUND = -1;

  private ECPUtil()
  {
  }

  public static ECPModelContext getModelContext(ECPModelContextProvider contextProvider, Object[] elements)
  {
    ECPModelContext commonContext = null;
    for (Object element : elements)
    {
      ECPModelContext elementContext = contextProvider.getModelContext(element);
      if (elementContext == null)
      {
        return null;
      }

      if (elementContext != commonContext)
      {
        if (commonContext == null)
        {
          commonContext = elementContext;
        }
        else
        {
          return null;
        }
      }
    }

    return commonContext;
  }

  public static ECPProperties createProperties()
  {
    return new Properties();
  }

  public static boolean isDisposed(Object object)
  {
    if (object instanceof ECPDisposable)
    {
      ECPDisposable disposable = (ECPDisposable)object;
      return disposable.isDisposed();
    }

    return false;
  }

  public static boolean isClosed(Object object)
  {
    if (object instanceof ECPCloseable)
    {
      ECPCloseable closeable = (ECPCloseable)object;
      return !closeable.isOpen();
    }

    return false;
  }

  public static ECPElement getResolvedElement(ECPElement elementOrDescriptor)
  {
    if (elementOrDescriptor instanceof ElementDescriptor)
    {
      ElementDescriptor<?> descriptor = (ElementDescriptor<?>)elementOrDescriptor;
      return descriptor.getResolvedElement();
    }

    return elementOrDescriptor;
  }

  public static <E> boolean containsElement(E[] elements, E element)
  {
    return getElementIndex(elements, element) != NOT_FOUND;
  }

  public static <E> int getElementIndex(E[] elements, E element)
  {
    for (int i = 0; i < elements.length; i++)
    {
      if (elements[i] == element)
      {
        return i;
      }
    }

    return NOT_FOUND;
  }

  public static Set<String> getElementNames(Set<? extends ECPElement> elements)
  {
    Set<String> names = new HashSet<String>();
    for (ECPElement element : elements)
    {
      names.add(element.getName());
    }

    return names;
  }

  public static <E> Set<E> getAddedElements(E[] oldElements, E[] newElements)
  {
    return getRemovedElements(newElements, oldElements);
  }

  public static <E> Set<E> getRemovedElements(E[] oldElements, E[] newElements)
  {
    Set<E> removed = new HashSet<E>();
    for (int i = 0; i < oldElements.length; i++)
    {
      E element = oldElements[i];
      if (!containsElement(newElements, element))
      {
        removed.add(element);
      }
    }

    return removed;
  }

  // TODO move to another helper
  /**
   * This method looks through all known {@link EPackage}s to find all subclasses for the provided super class.
   * 
   * @param superClass
   *          - the class for which to get the subclasses
   * @return a {@link Collection} of {@link EClass}es
   */
  public static Collection<EClass> getSubClasses(EClass superClass)
  {
    Collection<EClass> classes = new HashSet<EClass>();
    for (Object objectEPackage : Registry.INSTANCE.values())
    {
      EPackage ePackage = (EPackage)objectEPackage;
      for (EClassifier eClassifier : ePackage.getEClassifiers())
      {
        if (eClassifier instanceof EClass)
        {
          EClass eClass = (EClass)eClassifier;
          if (superClass.isSuperTypeOf(eClass) && !eClass.isAbstract() && !eClass.isInterface())
          {
            classes.add(eClass);
          }
        }
      }
    }
    return classes;
  }

  // TODO move to another helper
  public static Set<EPackage> getAllRegisteredEPackages()
  {
    Set<EPackage> ePackages = new HashSet<EPackage>();
    for (Object object : Registry.INSTANCE.values())
    {
      if (object instanceof EPackage)
      {
        EPackage ePackage = (EPackage)object;
        ePackages.add(ePackage);
      }
    }
    return ePackages;
  }
}
