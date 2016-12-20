/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mathias Schaefer - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverter;
import org.osgi.service.component.annotations.Component;

/**
 * Default value converter for generic EAttribute handling.
 * Does currently not handle EReferences.
 *
 * @author Mathias Schaefer <mschaefer@eclipsesource.com>
 *
 */
@Component
public class DefaultEAttributeValueConverterImpl implements EStructuralFeatureValueConverter {

	/**
	 * Default list separator token.
	 */
	public static final String DEFAULT_LIST_SEPARATOR = ","; //$NON-NLS-1$
	/**
	 * The default priority for this converter implementation.
	 * Make sure your converter has a higher priority.
	 */
	public static final double DEFAULT_PRIORITY = 2d;

	private final String listSeparator;
	private final double priority;

	/**
	 * Default no value constructor (will use the
	 * DEFAULT_LIST_SEPARATOR and DEFAULT_PRIORITY for this converter).
	 */
	public DefaultEAttributeValueConverterImpl() {
		this(DEFAULT_LIST_SEPARATOR, DEFAULT_PRIORITY);
	}

	/**
	 * Constructor which allows to override the list separator.
	 *
	 * @param listSeparator the separator token to split lists (i.e. "|")
	 * @param priority the priority for this converter
	 */
	protected DefaultEAttributeValueConverterImpl(String listSeparator, double priority) {
		this.listSeparator = listSeparator;
		this.priority = priority;
	}

	@Override
	public double isApplicable(EObject eObject, EStructuralFeature feature, Object value, Direction direction) {
		if (Direction.MODEL_TO_LITERAL.equals(direction) || feature instanceof EReference) {
			return NOT_APPLICABLE;
		}
		return priority; // otherwise always applicable
	}

	@Override
	public Object convertToModelValue(EObject eObject, EStructuralFeature feature, String literal) {

		try {
			if (feature.isMany()) {
				final List<Object> objects = new ArrayList<Object>();
				final StringTokenizer tokenizer = new StringTokenizer(literal, listSeparator, false);
				while (tokenizer.hasMoreTokens()) {

					final String item = tokenizer.nextToken();
					objects.add(fromString(feature, item.trim()));

				}
				return objects;
			}
			return fromString(feature, literal);
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		catch (final RuntimeException ex) {// END SUPRESS CATCH EXCEPTION
			// silently ignore this (conversion can fail for various reasons)
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertToLiteral(EObject eObject, EStructuralFeature feature, Object instance) {
		try {
			if (instance instanceof Collection) {
				final StringBuilder stringBuilder = new StringBuilder();
				for (final Object object : (Collection<Object>) instance) {
					if (stringBuilder.length() > 0) {
						stringBuilder.append(listSeparator);
					}
					stringBuilder.append(String.valueOf(toString(feature, object)));
				}
				return stringBuilder.toString();
			}
			return toString(feature, instance);
		}
		// BEGIN SUPRESS CATCH EXCEPTION
		catch (final RuntimeException ex) {// END SUPRESS CATCH EXCEPTION
			// silently ignore this (conversion can fail for various reasons)
			return null;
		}
	}

	/**
	 * Convert literal to Object.
	 *
	 * @param feature target feature
	 * @param literal to convert
	 * @return converted object
	 */
	protected Object fromString(EStructuralFeature feature, String literal) {
		return EcoreUtil.createFromString(((EAttribute) feature).getEAttributeType(), literal);
	}

	/**
	 * Basic conversion from model to string literal.
	 *
	 * @param feature the feauter
	 * @param instance the model value
	 * @return the string value
	 */
	protected Object toString(EStructuralFeature feature, Object instance) {
		return String.valueOf(instance);

	}

}
