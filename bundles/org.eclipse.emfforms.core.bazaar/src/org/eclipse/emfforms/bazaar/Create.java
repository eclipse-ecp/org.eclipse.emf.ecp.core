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

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks a method in a {@link Vendor} creating a product on a {@link Bazaar}. The method can
 * request arbitrary parameters from the {@link BazaarContext} or provided by {@link BazaarContextFunction}s.
 * This method will be called only on the {@link Vendor} with the highest {@link Bid}.
 * Further, the method will only be called if all specified parameters can be resolved. The method returns the created
 * product.
 * see also {@link Bazaar}
 *
 * @author jonas
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Create {

}
