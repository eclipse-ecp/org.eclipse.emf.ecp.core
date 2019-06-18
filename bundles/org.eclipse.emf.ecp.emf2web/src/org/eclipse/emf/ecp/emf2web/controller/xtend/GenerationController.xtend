/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.controller.xtend

import java.util.List
import java.util.Collection
import org.eclipse.emf.ecp.view.spi.model.VView

/**
 * @author Stefan Dirix
 *
 */
interface GenerationController {
	def List<GenerationInfo> generate(Collection<? extends VView> views)
}