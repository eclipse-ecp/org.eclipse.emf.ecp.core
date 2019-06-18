/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import java.text.MessageFormat;

import org.eclipse.emfforms.spi.common.report.AbstractReport;

/**
 * An error that indicates that two renderers with the same priority have been found.
 *
 * @author emueller
 * @since 1.5
 *
 */
public class AmbiguousRendererPriorityReport extends AbstractReport {

	/**
	 * Constructor.
	 *
	 * @param priority
	 *            the ambiguous priority
	 * @param rendererName
	 *            the name of the first renderer
	 * @param otherRendererName
	 *            the name of the second renderer
	 */
	public AmbiguousRendererPriorityReport(int priority, String rendererName, String otherRendererName) {
		super(MessageFormat.format("The {0} and the {1} renderers both have priority {2}.", //$NON-NLS-1$
			rendererName, otherRendererName, priority));
	}
}
