/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * david - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IChildCreationExtender;

/**
 * A helper class which collects all the available descriptors for an {@link EObject}.
 */
public class ChildrenDescriptorCollector {

	private EditingDomain domain;

	private EObject eObject;

	private Set<String> alreadyReadNameSpaces;

	private Collection<?> descriptors;

	/**
	 * Returns the available descriptors for an {@link EObject}.
	 * 
	 * @param eObject The {@link EObject} whose descriptors should be returned
	 * @return The descriptors of the requested {@link EObject}
	 */
	public Collection<?> getDescriptors(EObject eObject)
	{
		this.eObject = eObject;
		domain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		descriptors = domain.getNewChildDescriptors(eObject, null);
		alreadyReadNameSpaces = new LinkedHashSet<String>();
		alreadyReadNameSpaces.add(eObject.eClass().getEPackage().getNsURI());
		iterateThroughSuperTypes();
		return descriptors;
	}

	private void iterateThroughSuperTypes()
	{
		for (final EClass eClass : eObject.eClass().getEAllSuperTypes()) {
			final String namespace = eClass.getEPackage().getNsURI();
			if (alreadyReadNameSpaces.contains(namespace)) {
				continue;
			}
			iterateThroughExtenderDescriptors(namespace);
			alreadyReadNameSpaces.add(namespace);
		}
	}

	// EMF API
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void iterateThroughExtenderDescriptors(String namespace)
	{
		for (final IChildCreationExtender.Descriptor descriptor : EMFEditPlugin
			.getChildCreationExtenderDescriptorRegistry()
			.getDescriptors(namespace)) {
			final IChildCreationExtender createChildCreationExtender = descriptor.createChildCreationExtender();
			try {
				final Field declaredField = descriptor.getClass().getDeclaredField("contributor"); //$NON-NLS-1$
				AccessibleObject.setAccessible(new AccessibleObject[] { declaredField }, true);
				final String value = (String) declaredField.get(descriptor);
				if (value.startsWith(eObject.eClass().getEPackage().getNsPrefix())) {
					continue;
				}
			} catch (final NoSuchFieldException ex) {
				// TODO: log
				continue;
			} catch (final IllegalArgumentException ex) {
				// TODO: log
				continue;
			} catch (final IllegalAccessException ex) {
				// TODO: log
				continue;
			}
			final Collection newChildDescriptors = createChildCreationExtender
				.getNewChildDescriptors(eObject, domain);
			descriptors.addAll(newChildDescriptors);
		}
	}

}
