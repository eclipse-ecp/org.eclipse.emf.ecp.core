/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core.converter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * Registry for {@link EMFFormsSpreadsheetValueConverter converters}.
 *
 * @author Johannes Faltermeier
 *
 */
public interface EMFFormsSpreadsheetValueConverterRegistry {

	/**
	 * Returns a suitable {@link EMFFormsSpreadsheetValueConverter converter} for the given arguments.
	 *
	 * @param domainObject the domain object
	 * @param dmr the domain model reference
	 * @return the converter
	 * @throws EMFFormsConverterException in case no suitable converter is registered
	 */
	EMFFormsSpreadsheetValueConverter getConverter(EObject domainObject, VDomainModelReference dmr)
		throws EMFFormsConverterException;

}
