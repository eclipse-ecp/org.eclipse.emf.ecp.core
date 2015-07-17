/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecp.common.spi.cachetree.AbstractCachedTree;
import org.eclipse.emf.ecp.common.spi.cachetree.CachedTreeNode;
import org.eclipse.emf.ecp.common.spi.cachetree.IExcludedObjectsCallback;
import org.eclipse.emf.ecp.validation.api.IValidationService;

/**
 * Implementation of a validation service.
 *
 * @author emueller
 * @author Tobias Verhoeven
 *
 */
public final class ValidationService extends AbstractCachedTree<Diagnostic> implements IValidationService {

	/**
	 * Constructor for the ECP ValidationService.
	 *
	 * @param callback to use
	 */
	public ValidationService(IExcludedObjectsCallback callback) {
		super(callback);
	}

	/**
	 * Tree node that caches the severity of its children.
	 */
	public class CachedSeverityTreeNode extends CachedTreeNode<Diagnostic> {

		/**
		 * Constructor.
		 *
		 * @param diagnostic
		 *            the initial diagnostic containing the severity and validation message
		 */
		public CachedSeverityTreeNode(Diagnostic diagnostic) {
			super(diagnostic);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void update() {
			final Collection<Diagnostic> severities = values();

			if (severities.size() > 0) {
				Diagnostic mostSevereDiagnostic = values().iterator().next();
				for (final Diagnostic diagnostic : severities) {
					if (diagnostic.getSeverity() > mostSevereDiagnostic.getSeverity()) {
						mostSevereDiagnostic = diagnostic;
					}
				}
				setChildValue(mostSevereDiagnostic);
				return;
			}
			setChildValue(getDefaultValue());
		}

		@Override
		public Diagnostic getDisplayValue() {
			if (getChildValue() == null) {
				return getOwnValue();
			}
			return getOwnValue().getSeverity() > getChildValue().getSeverity() ? getOwnValue() : getChildValue();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<EObject> validate(Collection<EObject> eObjects) {
		final Set<EObject> allAffected = new HashSet<EObject>();
		for (final EObject eObject : eObjects) {
			final Set<EObject> affected = validate(eObject);
			allAffected.addAll(affected);
		}
		return allAffected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<EObject> validate(EObject eObject) {
		return update(eObject, getSeverity(eObject));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Diagnostic getDiagnostic(Object eObject) {
		return getCachedValue(eObject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Diagnostic getRootDiagnostic() {
		return getRootValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Diagnostic getDefaultValue() {
		return Diagnostic.OK_INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CachedTreeNode<Diagnostic> createdCachedTreeNode(Diagnostic diagnostic) {
		return new CachedSeverityTreeNode(diagnostic);
	}

	private Diagnostic getSeverity(EObject object) {
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(object.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(object);

		if (validator == null) {
			validator = new EObjectValidator();
		}
		final Map<Object, Object> context = new HashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, Diagnostician.INSTANCE);
		context.put(EValidator.class, validator);

		validator.validate(object, diagnostics, context);
		return diagnostics;

	}

}
