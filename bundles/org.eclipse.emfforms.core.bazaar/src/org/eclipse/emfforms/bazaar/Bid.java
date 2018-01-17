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
package org.eclipse.emfforms.bazaar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method in a {@link Vendor} doing the {@link Bid} for creating a Ware on a {@link Bazaar}. The methode can
 * request arbitrary parameters from the {@link BazaarContext} or provided by {@link BazaarContextFunction}s.
 * The method will only be called if all specified parameters can be resolved. The method must to return a double
 * indicating the priority. The {@link Vendor} with the highest priority on a Bazaar will finally create the ware.
 * see also {@link Bazaar}
 *
 * @author jonas
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bid {

}
