/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.label;

import org.osgi.framework.Bundle;

/**
 * Key for the map of displayname observables.
 *
 * @author Eugen Neufeld
 */
class BundleKeyWrapper {
	private final String key;
	private final Bundle bundle;

	/**
	 * The default constructor.
	 *
	 * @param key The Key
	 * @param bundle The Bundle
	 */
	BundleKeyWrapper(String key, Bundle bundle) {
		super();
		this.key = key;
		this.bundle = bundle;
	}

	/**
	 * The Key.
	 *
	 * @return The key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * The Bundle.
	 *
	 * @return The bundle
	 */
	public Bundle getBundle() {
		return bundle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bundle == null ? 0 : bundle.hashCode());
		result = prime * result + (key == null ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BundleKeyWrapper)) {
			return false;
		}
		final BundleKeyWrapper other = (BundleKeyWrapper) obj;
		if (bundle == null) {
			if (other.bundle != null) {
				return false;
			}
		} else if (!bundle.equals(other.bundle)) {
			return false;
		}
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}
}