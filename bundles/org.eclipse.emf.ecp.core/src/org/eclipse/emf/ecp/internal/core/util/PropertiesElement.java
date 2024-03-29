/********************************************************************************
 * Copyright (c) 2011-2015 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPPropertiesAware;
import org.eclipse.emf.ecp.core.util.observer.ECPPropertiesObserver;
import org.eclipse.emf.ecp.internal.core.util.PropertiesStore.StorableElement;

/**
 * An element holding {@link ECPProperties}.
 *
 * @author Eike Stepper
 */
public abstract class PropertiesElement extends Element implements StorableElement, ECPPropertiesAware {
	private final ECPProperties properties;
	private ECPPropertiesObserver observer;

	/**
	 * Constructor.
	 *
	 * @param name the name of the elements
	 * @param properties the initial properties
	 */
	public PropertiesElement(String name, ECPProperties properties) {
		super(name);
		this.properties = properties;
		observeProperties();
	}

	/**
	 * Create a {@link PropertiesElement} from an {@link ObjectInput}.
	 *
	 * @param in the {@link ObjectInput}
	 * @throws IOException if there a problem while reading the input
	 */
	public PropertiesElement(ObjectInput in) throws IOException {
		super(in.readUTF());
		properties = new Properties(in);
		observeProperties();
	}

	/** {@inheritDoc} */
	@Override
	public void write(ObjectOutput out) throws IOException {
		out.writeUTF(getName());
		((Properties) properties).write(out);
	}

	/** {@inheritDoc} */
	@Override
	public final ECPProperties getProperties() {
		return properties;
	}

	/**
	 * Called if the properties of the element change. Can be implemented by subclasses
	 *
	 * @param oldProperties the old properties
	 * @param newProperties the new properties
	 */
	protected void propertiesChanged(Collection<Entry<String, String>> oldProperties,
		Collection<Entry<String, String>> newProperties) {
		// Do nothing
	}

	private void observeProperties() {
		if (observer != null) {
			cleanup();
		}
		observer = new ECPPropertiesObserver() {
			@Override
			public void propertiesChanged(ECPProperties properties, Collection<Entry<String, String>> oldProperties,
				Collection<Entry<String, String>> newProperties) {
				PropertiesElement.this.propertiesChanged(oldProperties, newProperties);
			}
		};
		properties.addObserver(observer);
	}

	/**
	 * Cleans up after the PropertiesElement. This call unregisters the {@link ECPPropertiesObserver} from the
	 * {@link ECPProperties}.
	 */
	protected void cleanup() {
		if (properties == null || observer == null) {
			return;
		}
		properties.removeObserver(observer);
	}
}
