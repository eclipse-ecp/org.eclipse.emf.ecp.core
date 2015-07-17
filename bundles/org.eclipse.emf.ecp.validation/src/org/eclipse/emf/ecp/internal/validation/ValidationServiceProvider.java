/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.cachetree.IExcludedObjectsCallback;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.ecp.validation.api.IValidationService;
import org.eclipse.emf.ecp.validation.api.IValidationServiceProvider;

/**
 * Validation service provider.
 *
 * @author emueller
 * @author Tobias Verhoeven
 */
public class ValidationServiceProvider implements IValidationServiceProvider {

	private final Map<Object, IValidationService> mapping;

	/**
	 * Constructor.
	 */
	public ValidationServiceProvider() {
		mapping = new HashMap<Object, IValidationService>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IValidationService getValidationService(final Object validationServiceObject) {
		if (!mapping.containsKey(validationServiceObject)) {
			final IValidationService validationService = new ValidationService(new IExcludedObjectsCallback() {

				@Override
				public boolean isExcluded(Object object) {
					if (InternalProject.class.isInstance(validationServiceObject)) {
						return ((InternalProject) validationServiceObject).isModelRoot(object);
					}
					return false;
				}
			});
			mapping.put(validationServiceObject, validationService);
			if (validationServiceObject instanceof ECPProject) {
				final ECPProject project = (ECPProject) validationServiceObject;
				validationService.validate(getAllChildEObjects(project));
			}
			return validationService;
		}

		return mapping.get(validationServiceObject);
	}

	private Collection<EObject> getAllChildEObjects(ECPProject project) {
		final List<EObject> result = new ArrayList<EObject>();

		for (final Object object : project.getContents()) {
			if (EObject.class.isInstance(object)) {
				final EObject eObject = (EObject) object;
				result.add(eObject);
				final TreeIterator<EObject> iterator = EcoreUtil.getAllContents(eObject, false);

				while (iterator.hasNext()) {
					result.add(iterator.next());
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteValidationService(Object key) {
		mapping.remove(key);
	}
}
