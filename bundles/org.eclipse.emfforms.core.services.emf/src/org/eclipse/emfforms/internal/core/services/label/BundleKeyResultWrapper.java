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

/**
 * A Wrapper Object to wrap a bundle, a key and the result object.
 *
 * @author Eugen Neufeld
 *
 */
class BundleKeyResultWrapper {
	private final BundleKeyWrapper bundleKeyWrapper;
	private final String result;

	/**
	 * Default constructor.
	 *
	 * @param bundleKeyWrapper The BundleKeyWrapper
	 * @param result The result
	 */
	BundleKeyResultWrapper(BundleKeyWrapper bundleKeyWrapper, String result) {
		super();
		this.bundleKeyWrapper = bundleKeyWrapper;
		this.result = result;
	}

	/**
	 * The BundleKeyWrapper.
	 *
	 * @return the bundleKeyWrapper
	 */
	public BundleKeyWrapper getBundleKeyWrapper() {
		return bundleKeyWrapper;
	}

	/**
	 * The wrapped Result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
}
