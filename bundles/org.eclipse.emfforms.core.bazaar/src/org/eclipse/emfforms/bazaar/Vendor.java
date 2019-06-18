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
package org.eclipse.emfforms.bazaar;

/**
 * A {@link Vendor} provides a product of type T on a {@link Bazaar}. To find the best {@link Vendor}, every
 * {@link Vendor} can do a {@link Bid} in a method annotated with {@link Bid}. The best {@link Vendor} creates
 * the product in a method annotaed with {@link Create}.
 *
 * @author jonas
 *
 * @param <T> the type of product create by this {@link Vendor}
 */
public interface Vendor<T> {

}
