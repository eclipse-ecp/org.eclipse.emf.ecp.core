/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.section.swt;

import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;

/**
 * {@link ECPRendererTester} for {@link org.eclipse.emf.ecp.view.spi.section.swt.SectionLeafSWTRenderer
 * SectionLeafSWTRenderer}.
 *
 * @author jfaltermeier
 *
 */
public class LeafRendererTester implements ECPRendererTester {

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VSection.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		if (VSection.class.cast(vElement).getChildItems().size() == 0) {
			return 6;
		}
		return NOT_APPLICABLE;
	}

}
