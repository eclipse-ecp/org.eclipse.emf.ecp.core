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
package org.eclipse.emfforms.internal.spreadsheet.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider;
import org.osgi.service.component.annotations.Component;

/**
 * Default implementation of the {@link EMFFormsSpreadsheetFormatDescriptionProvider}.
 * It may be replaced by a service instance with a higher than standard priority.
 */
@Component(name = "EMFFormsSpreadsheetFormatDescriptionProviderImpl")
public class EMFFormsSpreadsheetFormatDescriptionProviderImpl implements EMFFormsSpreadsheetFormatDescriptionProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetFormatDescriptionProvider#getFormatDescription(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public String getFormatDescription(final EStructuralFeature structuralFeature) {
		final StringBuilder sb = new StringBuilder();
		for (final EAnnotation eAnnotation : getEAnnotations(structuralFeature)) {
			final EMap<String, String> details = eAnnotation.getDetails();
			for (final String key : details.keySet()) {
				if (ExtendedMetaData.ANNOTATION_URI.equals(eAnnotation.getSource())) {
					if ("kind".equals(key)) { //$NON-NLS-1$
						continue;
					}
					if ("name".equals(key)) { //$NON-NLS-1$
						continue;
					}
					if ("baseType".equals(key)) { //$NON-NLS-1$
						continue;
					}
				}
				if ("appinfo".equals(key)) { //$NON-NLS-1$
					continue;
				}
				sb.append(key);
				sb.append("="); //$NON-NLS-1$
				sb.append(details.get(key));
				sb.append("\n"); //$NON-NLS-1$
			}
		}
		return sb.toString().trim();
	}

	private Set<EAnnotation> getEAnnotations(final EStructuralFeature structuralFeature) {
		final Set<EAnnotation> annotations = new LinkedHashSet<EAnnotation>();
		annotations.addAll(structuralFeature.getEAnnotations());
		annotations.addAll(structuralFeature.getEType().getEAnnotations());
		return annotations;
	}

}
