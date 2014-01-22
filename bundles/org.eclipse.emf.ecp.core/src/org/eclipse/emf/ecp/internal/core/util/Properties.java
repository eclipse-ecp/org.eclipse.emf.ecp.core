/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.core.util;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPPropertiesObserver;

/**
 * @author Eike Stepper
 */
public class Properties extends Registry<Map.Entry<String, String>, ECPPropertiesObserver> implements ECPProperties {
	/**
	 * Default constructor.
	 */
	public Properties() {
		activate();
	}

	public Properties(ObjectInput in) throws IOException {
		activate();
		final int count = in.readInt();
		for (int i = 0; i < count; i++) {
			final String key = in.readUTF();
			final String value = in.readUTF();
			addProperty(key, value);
		}

	}

	public void write(ObjectOutput out) throws IOException {
		final Collection<Entry<String, String>> entries = getElementsToWrite();
		out.writeInt(entries.size());
		for (final Entry<String, String> entry : entries) {
			out.writeUTF(entry.getKey());
			out.writeUTF(entry.getValue());
		}
	}

	/** {@inheritDoc} */
	public void addProperty(String key, String value) {
		final Map.Entry<String, String> property = new Property(key, value);
		doChangeElements(null, Collections.singleton(property));
	}

	/** {@inheritDoc} */
	public void removeProperty(String key) {
		doChangeElements(Collections.singleton(key), null);
	}

	/** {@inheritDoc} */
	public String getValue(String name) {
		final Entry<String, String> element = getElement(name);
		return element == null ? null : element.getValue();
	}

	/** {@inheritDoc} */
	public Set<String> getKeys() {
		return getElementNames();
	}

	/** {@inheritDoc} */
	public Collection<Map.Entry<String, String>> getProperties() {
		return getElements();
	}

	/** {@inheritDoc} */
	public boolean hasProperties() {
		return hasElements();
	}

	/** {@inheritDoc} */
	public ECPProperties copy() {
		final ECPProperties copy = ECPUtil.createProperties();
		for (final Entry<String, String> property : getElements()) {
			copy.addProperty(property.getKey(), property.getValue());
		}

		return copy;
	}

	@Override
	protected String getElementName(Map.Entry<String, String> element) {
		return element.getKey();
	}

	protected Collection<Entry<String, String>> getElementsToWrite() {
		return getElements();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.ecp.internal.core.util.Registry#notifyObservers(org.eclipse.emf.ecp.core.util.observer.ECPObserver
	 * , ELEMENT[], ELEMENT[])
	 */
	@Override
	protected void notifyObservers(Collection<Map.Entry<String, String>> oldProperties,
		Collection<Map.Entry<String, String>> newProperties) throws Exception {
		ECPUtil.getECPObserverBus().notify(ECPPropertiesObserver.class)
			.propertiesChanged(this, oldProperties, newProperties);
	}

	// @Override
	// @SuppressWarnings("unchecked")
	// protected Map.Entry<String, String>[] createElementArray(int size) {
	// return new Map.Entry[size];
	// }

	/**
	 * @author Eike Stepper
	 */
	public static final class Property implements Map.Entry<String, String> {
		private final String key;

		private final String value;

		public Property(String key, String value) {
			this.key = key;
			this.value = value;
		}

		/** {@inheritDoc} */
		public String getKey() {
			return key;
		}

		/** {@inheritDoc} */
		public String getValue() {
			return value;
		}

		/** {@inheritDoc} */
		public String setValue(String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String toString() {
			return key + " --> " + value;
		}
	}

}
