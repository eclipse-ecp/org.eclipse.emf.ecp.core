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
package org.eclipse.emfforms.internal.spreadsheet.core.converter;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Implementation of the {@link EMFFormsSpreadsheetValueConverterRegistry}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "EMFFormsSpreadsheetValueConverterRegistryImpl")
public class EMFFormsSpreadsheetValueConverterRegistryImpl implements EMFFormsSpreadsheetValueConverterRegistry {

	private final Set<EMFFormsSpreadsheetValueConverter> allConverter = new CopyOnWriteArraySet<EMFFormsSpreadsheetValueConverter>();

	/**
	 * Adds a {@link EMFFormsSpreadsheetValueConverter converter} to the registry.
	 *
	 * @param converter the converter
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addConverter(EMFFormsSpreadsheetValueConverter converter) {
		allConverter.add(converter);
	}

	/**
	 * Removes a {@link EMFFormsSpreadsheetValueConverter converter} from the registry.
	 *
	 * @param converter the converter
	 */
	public void removeConverter(EMFFormsSpreadsheetValueConverter converter) {
		allConverter.remove(converter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistry#getConverter(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public EMFFormsSpreadsheetValueConverter getConverter(EObject domainObject, VDomainModelReference dmr)
		throws EMFFormsConverterException {
		double highestPrio = -Double.MIN_VALUE;

		final Set<EMFFormsSpreadsheetValueConverter> applicableConverters = new LinkedHashSet<EMFFormsSpreadsheetValueConverter>();
		for (final EMFFormsSpreadsheetValueConverter current : allConverter) {
			final double currentPrio = current.isApplicable(domainObject, dmr);
			if (Double.isNaN(currentPrio)) {
				continue;
			}
			if (highestPrio > currentPrio) {
				continue;
			}
			if (highestPrio < currentPrio) {
				applicableConverters.clear();
			}
			highestPrio = currentPrio;
			applicableConverters.add(current);
		}
		if (applicableConverters.isEmpty()) {
			throw new EMFFormsConverterException(
				MessageFormat.format("No converter found for domain model reference: {0}.", dmr)); //$NON-NLS-1$
		}
		if (applicableConverters.size() > 1) {
			throw new EMFFormsConverterException(
				MessageFormat
					.format("Multiple converters with same priority registered for domain model reference: {0}.", dmr)); //$NON-NLS-1$
		}
		return applicableConverters.iterator().next();
	}

}
