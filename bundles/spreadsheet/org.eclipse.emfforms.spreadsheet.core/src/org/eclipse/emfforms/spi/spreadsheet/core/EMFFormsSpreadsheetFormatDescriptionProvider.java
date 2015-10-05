/*******************************************************************************
 * Copyright (c) 2015 Innoopract Informationssysteme GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Innoopract Informationssysteme GmbH - initial API and implementation
 * EclipseSource - ongoing implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A service that translates the format and the constraint information for a given structural feature into a human
 * readable string that can be used to provide guidance when filling out a spreadsheet. A default implementation is
 * provided by EMFFormsSpreadsheetFormatDescriptionProviderImpl; it can be overridden by providing a OSGi service
 * instance with a higher priority.
 */
public interface EMFFormsSpreadsheetFormatDescriptionProvider {

	/**
	 * Based on the given structural feature a human readable format string is being computed; this string is meant to
	 * help a user to fill out forms in a spreadsheet.
	 *
	 * @param structuralFeature used for the format description.
	 * @return A string that describes the format and the constraints of the structural feature.
	 */
	String getFormatDescription(EStructuralFeature structuralFeature);

}
