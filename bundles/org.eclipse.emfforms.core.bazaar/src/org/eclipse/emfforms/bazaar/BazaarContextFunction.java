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

import java.security.Policy.Parameters;

/**
 * Can exchange {@link Parameters} in a {@link BazaarContext} into a new parameter which is requested by
 * a {@link Vendor}. Is registered at a {@link Bazaar#addContextFunction(String, BazaarContextFunction)} under a
 * key. The key matches the parameter, this {@link BazaarContextFunction} can exchange to. A
 * {@link BazaarContextFunction} holds a method annotated with {@link Exchange}. This method can request arbitrary
 * parameters present in the {@link BazaarContext} and return the exchanged parameter.
 *
 * @author jonas
 *
 */
public interface BazaarContextFunction {

}
