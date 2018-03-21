/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class VendorCreatingProductParameter0 implements Vendor<MyProduct> {

	private final MyProduct myProductMock;

	/**
	 * @param myProductMock
	 */
	public VendorCreatingProductParameter0(MyProduct myProductMock) {
		this.myProductMock = myProductMock;
	}

	@Create
	public MyProduct create() {
		return myProductMock;
	}

}
