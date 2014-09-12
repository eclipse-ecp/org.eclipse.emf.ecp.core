/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.reporting;

import java.text.MessageFormat;

import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;

/**
 * An error that indicates that two renderers with the same priority have been found.
 * 
 * @author emueller
 * 
 */
public class AmbiguousRendererPriorityError implements ReportEntity {

	private final int priority;
	private final String rendererName;
	private final String otherRendererName;

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
	public AmbiguousRendererPriorityError(int priority, String rendererName, String otherRendererName) {
		this.priority = priority;
		this.rendererName = rendererName;
		this.otherRendererName = otherRendererName;
	}

	/**
	 * Returns the name of the first renderer.
	 * 
	 * @return the name of the first renderer
	 */
	public String getRendererName() {
		return rendererName;
	}

	/**
	 * Returns the name of the second renderer.
	 * 
	 * @return the name of the second renderer
	 */
	public String getOtherRendererName() {
		return otherRendererName;
	}

	/**
	 * Returns the priority.
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getMessage()
	 */
	@Override
	public String getMessage() {
		return MessageFormat.format("The {0} and the {1} renderers both have priority {2}.", //$NON-NLS-1$
			rendererName, otherRendererName, priority);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#hasException()
	 */
	@Override
	public boolean hasException() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity#getException()
	 */
	@Override
	public <T extends Exception> T getException() {
		return null;
	}
}
