/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider;
import org.osgi.service.component.annotations.Component;

/**
 * Simple implementation of the EMFFormsIdProvider which returns a generated UUID per EObject.
 * 
 * @author Eugen Neufeld
 *
 */
@Component
public class EMFFormsIdProviderImpl implements EMFFormsIdProvider {

	private final Map<EObject, String> cache = new LinkedHashMap<EObject, String>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsIdProvider#getId(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String getId(EObject eObject) {
		if (!cache.containsKey(eObject)) {
			final String uuid = EcoreUtil.generateUUID();
			cache.put(eObject, uuid);
		}
		return cache.get(eObject);
	}

}
