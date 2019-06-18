/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * @author jonas
 *
 */
public class VendorCreatingProductParameter1 implements Vendor<MyProduct> {

	private final MyProduct myProduct;

	/**
	 * @param myProduct
	 */
	public VendorCreatingProductParameter1(MyProduct myProduct) {
		this.myProduct = myProduct;
	}

	@Create
	public MyProduct create(String string) {
		return myProduct;
	}

}
