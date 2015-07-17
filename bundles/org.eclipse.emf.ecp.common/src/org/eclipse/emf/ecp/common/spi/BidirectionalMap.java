/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Convenience class for mapping keys to values and vice versa.
 * Both, keys and values, have to be unique.
 *
 * @author emuller
 *
 * @param <K>
 *            the type of the key
 * @param <V>
 *            the type of the value
 * @since 1.5
 */
public class BidirectionalMap<K, V> {

	private final Map<K, V> keyToValues;
	private final Map<V, K> valuesToKeys;

	/**
	 * Default constructor.
	 */
	public BidirectionalMap() {
		keyToValues = new LinkedHashMap<K, V>();
		valuesToKeys = new LinkedHashMap<V, K>();
	}

	/**
	 * Associates the specified value with the specified key in this map
	 * and vice versa.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(K key, V value) {
		keyToValues.put(key, value);
		valuesToKeys.put(value, key);
	}

	/**
	 * Removes the bidirectional mapping for the given key.
	 *
	 * @param key
	 *            the key to be removed
	 * @return the value that was associated with the key
	 */
	public synchronized V removeByKey(K key) {
		final V v = keyToValues.get(key);
		keyToValues.remove(key);
		valuesToKeys.remove(v);
		return v;
	}

	/**
	 * Removes the bidirectional mapping for the given value.
	 *
	 * @param value
	 *            the value to be removed
	 * @return the key that was associated with the value
	 */
	public synchronized K removeByValue(V value) {
		final K k = valuesToKeys.get(value);
		valuesToKeys.remove(value);
		keyToValues.remove(k);
		return k;
	}

	/**
	 * Returns the value belonging to the given key.
	 *
	 * @param key
	 *            the key whose value should be looked up
	 * @return the value belonging to the given key
	 */
	public V getValue(K key) {
		return keyToValues.get(key);
	}

	/**
	 * Returns the key belonging to the given value.
	 *
	 * @param value
	 *            the key whose value should be looked up
	 * @return the key belonging to the given value
	 */
	public K getByValue(V value) {
		return valuesToKeys.get(value);
	}

	/**
	 * Returns all keys.
	 *
	 * @return a set of all keys
	 */
	public Set<K> keys() {
		final LinkedHashSet<K> linkedHashSet = new LinkedHashSet<K>();
		linkedHashSet.addAll(keyToValues.keySet());
		return linkedHashSet;
	}

	/**
	 * Returns all values.
	 *
	 * @return a set of all values
	 */
	public Set<V> values() {
		final LinkedHashSet<V> linkedHashSet = new LinkedHashSet<V>();
		linkedHashSet.addAll(valuesToKeys.keySet());
		return linkedHashSet;
	}

}
