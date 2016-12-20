/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mathias - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common.converter;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverter;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverter.Direction;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Mathias Schaefer <mschaefer@eclipsesource.com>
 *
 */
@Component(service = EStructuralFeatureValueConverterService.class)
public class EStructuralFeatureValueConverterServiceImpl implements EStructuralFeatureValueConverterService {

	private final Set<EStructuralFeatureValueConverter> converters = //
		new LinkedHashSet<EStructuralFeatureValueConverter>();

	@Override
	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	public void addValueConverter(EStructuralFeatureValueConverter converter) {
		converters.add(converter);
	}

	@Override
	public void removeValueConverter(EStructuralFeatureValueConverter converter) {
		converters.remove(converter);
	}

	private EStructuralFeatureValueConverter getHighestRankingConverter(EObject eObject, EStructuralFeature feature,
		Object value, Direction direction) {
		double priority = EStructuralFeatureValueConverter.NOT_APPLICABLE;
		EStructuralFeatureValueConverter highestRankingConverter = null;
		for (final EStructuralFeatureValueConverter converter : converters) {

			final double applicable = converter.isApplicable(eObject, feature, value, direction);
			if (applicable == EStructuralFeatureValueConverter.NOT_APPLICABLE || applicable <= priority) {
				continue;
			}

			highestRankingConverter = converter;
			priority = applicable;

		}
		return highestRankingConverter;
	}

	@Override
	public Object convertToModelValue(EObject eObject, EStructuralFeature feature, String literal) {
		final EStructuralFeatureValueConverter converter = getHighestRankingConverter(eObject, feature, literal,
			Direction.LITERAL_TO_MODEL);
		if (converter == null) {
			return null;
		}
		return converter.convertToModelValue(eObject, feature, literal);
	}

	@Override
	public Object convertToLiteral(EObject eObject, EStructuralFeature feature, Object instance) {
		final EStructuralFeatureValueConverter converter = getHighestRankingConverter(eObject, feature, instance,
			Direction.MODEL_TO_LITERAL);
		if (converter == null) {
			return null;
		}
		return converter.convertToLiteral(eObject, feature, instance);
	}

}
