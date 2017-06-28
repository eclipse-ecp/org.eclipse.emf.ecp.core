/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.internal.validation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.spi.validation.ValidationResultListener;
import org.eclipse.emfforms.common.spi.validation.ValidationService;
import org.eclipse.emfforms.common.spi.validation.Validator;
import org.eclipse.emfforms.common.spi.validation.exception.ValidationCanceledException;
import org.eclipse.emfforms.common.spi.validation.filter.DiagnosticFilter;
import org.eclipse.emfforms.common.spi.validation.filter.ObjectFilter;
import org.eclipse.emfforms.common.spi.validation.filter.SubTreeFilter;
import org.eclipse.emfforms.common.spi.validation.filter.ValidationFilter;

/**
 * The implementation of {@link ValidationService}.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public class ValidationServiceImpl implements ValidationService {

	private final Set<Validator> validators = new LinkedHashSet<Validator>();

	private final Set<ObjectFilter> objectFilter = new LinkedHashSet<ObjectFilter>();
	private final Set<SubTreeFilter> subTreeFilters = new LinkedHashSet<SubTreeFilter>();
	private final Set<DiagnosticFilter> diagnosticFilters = new LinkedHashSet<DiagnosticFilter>();

	private final Set<ValidationResultListener> validationResultListeners = new LinkedHashSet<ValidationResultListener>();

	private SubstitutionLabelProvider substitutionLabelProvider;

	private boolean validationRunning;
	private boolean cancelationRequested;

	private Diagnostician diagnostician;

	/**
	 * Default constructor.
	 */
	public ValidationServiceImpl() {
		diagnostician = new Diagnostician(EValidator.Registry.INSTANCE) {
			@Override
			public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics,
				Map<Object, Object> context) {
				Object eValidator;
				EClass eType = eClass;
				while ((eValidator = eValidatorRegistry.get(eType.eContainer())) == null) {
					final List<EClass> eSuperTypes = eType.getESuperTypes();
					if (eSuperTypes.isEmpty()) {
						eValidator = eValidatorRegistry.get(null);
						break;
					}
					eType = eSuperTypes.get(0);
				}
				return doValidate((EValidator) eValidator, eClass, eObject, diagnostics, context);
			}
		};
	}

	private boolean isFiltered(EObject object) {
		if (objectFilter.isEmpty()) {
			return false;
		}
		for (final ObjectFilter filter : objectFilter) {
			if (filter.skipValidation(object)) {
				return true;
			}
		}
		return false;
	}

	private boolean isSkipSubtree(EObject object, Optional<Diagnostic> diagnostic) {
		if (subTreeFilters.isEmpty()) {
			return false;
		}
		for (final SubTreeFilter filter : subTreeFilters) {
			if (filter.skipSubtree(object, diagnostic)) {
				return true;
			}
		}
		return false;
	}

	private boolean isIgnored(EObject object, Diagnostic diagnostic) {
		if (diagnosticFilters.isEmpty()) {
			return false;
		}
		for (final DiagnosticFilter filter : diagnosticFilters) {
			if (filter.ignoreDiagnostic(object, diagnostic)) {
				return true;
			}
		}
		return false;
	}

	private Diagnostic doValidate(EObject eObject) {

		Diagnostic diagnostic = null;
		try {

			diagnostic = getDiagnosticForEObject(eObject);
			return diagnostic;

		} finally {
			invokeValidationResultListeners(eObject, diagnostic, false);
		}

	}

	@Override
	public Diagnostic validate(EObject eObject) {
		if (isFiltered(eObject)) {
			return null;
		}
		return doValidate(eObject);
	}

	@Override
	public Set<Diagnostic> validate(Iterator<EObject> eObjectsIterator) throws ValidationCanceledException {

		final Set<Diagnostic> diagnostics = new LinkedHashSet<Diagnostic>();
		if (validationRunning) {
			return diagnostics; // prevent re-entry
		}

		validationRunning = true;
		try {

			final boolean isTreeIterator = eObjectsIterator instanceof TreeIterator<?>;
			while (!cancelationRequested && eObjectsIterator.hasNext()) {

				final EObject eObject = eObjectsIterator.next();
				Diagnostic diagnostic = null;

				boolean isSubtreePruned = false;
				if (isTreeIterator && isSkipSubtree(eObject, Optional.ofNullable(diagnostic))) {
					((TreeIterator<?>) eObjectsIterator).prune();
					isSubtreePruned = true;
				}
				if (isFiltered(eObject)) {
					continue;
				}

				diagnostic = doValidate(eObject);

				if (isTreeIterator && !isSubtreePruned && isSkipSubtree(eObject, Optional.of(diagnostic))) {
					((TreeIterator<?>) eObjectsIterator).prune();
				}
				if (isIgnored(eObject, diagnostic)) {
					continue;
				}

				diagnostics.add(diagnostic);
				invokeValidationResultListeners(eObject, diagnostic, true);

			}
		} finally {
			validationRunning = false;
		}

		if (cancelationRequested) {
			throw new ValidationCanceledException();
		}
		return diagnostics;
	}

	private void invokeValidationResultListeners(EObject eObject, Diagnostic diagnostic, boolean passed) {
		if (validationResultListeners.isEmpty()) {
			return;
		}

		for (final ValidationResultListener listener : validationResultListeners) {
			if (passed) {
				listener.afterValidate(eObject, diagnostic);
			} else {
				listener.onValidate(eObject, diagnostic);
			}
		}
	}

	/**
	 * Returns a {@link EValidator} for the given {@link EObject}.
	 *
	 * @param eObject the {@link EObject} to retrieve a {@link EValidator} for
	 * @return the {@link EValidator}
	 */
	protected EValidator getEValidatorForEObject(EObject eObject) {
		if (EValidator.Registry.INSTANCE.getEValidator(eObject.eClass().getEPackage()) == null) {
			return new EObjectValidator();
		}
		return diagnostician;
	}

	/**
	 * Computes the {@link Diagnostic} for the given eObject.
	 *
	 * @param object the {@link EObject} to validate
	 * @return the {@link Diagnostic}
	 */
	protected Diagnostic getDiagnosticForEObject(EObject object) {

		final EValidator eValidator = getEValidatorForEObject(object);
		final BasicDiagnostic diagnostic = Diagnostician.INSTANCE.createDefaultDiagnostic(object);
		final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
		context.put(EValidator.class, eValidator);
		if (substitutionLabelProvider != null) {
			context.put(EValidator.SubstitutionLabelProvider.class, substitutionLabelProvider);
		}

		eValidator.validate(object, diagnostic, context);

		final Map<EStructuralFeature, DiagnosticChain> diagnosticMap = new LinkedHashMap<EStructuralFeature, DiagnosticChain>();
		for (final Diagnostic child : diagnostic.getChildren()) {
			if (DiagnosticChain.class.isInstance(child) && DiagnosticHelper.checkDiagnosticData(child)) {
				diagnosticMap.put(DiagnosticHelper.getEStructuralFeature(child.getData()),
					(DiagnosticChain) child);
			}
		}

		for (final Validator validator : validators) {
			final List<Diagnostic> additionValidation = validator.validate(object);
			if (additionValidation == null) {
				continue;
			}
			for (final Diagnostic additionDiagnostic : additionValidation) {
				if (diagnosticMap
					.containsKey(DiagnosticHelper.getEStructuralFeature(additionDiagnostic.getData()))) {
					diagnosticMap.get(DiagnosticHelper.getEStructuralFeature(additionDiagnostic.getData()))
						.add(additionDiagnostic);
				} else {
					// only add non-OK diagnostics
					if (additionDiagnostic.getSeverity() != Diagnostic.OK) {
						diagnostic.add(additionDiagnostic);
					}
				}

			}
		}
		return diagnostic;
	}

	@Override
	public void addValidator(Validator validator) {
		validators.add(validator);
	}

	@Override
	public void removeValidator(Validator validator) {
		validators.remove(validator);
	}

	@Override
	public void registerValidationFilter(ValidationFilter filter) {
		if (filter instanceof ObjectFilter) {
			objectFilter.add((ObjectFilter) filter);
		}
		if (filter instanceof SubTreeFilter) {
			subTreeFilters.add((SubTreeFilter) filter);
		}
		if (filter instanceof DiagnosticFilter) {
			diagnosticFilters.add((DiagnosticFilter) filter);
		}
	}

	@Override
	public void unregisterValidationFilter(ValidationFilter filter) {
		objectFilter.remove(filter);
		subTreeFilters.remove(filter);
		diagnosticFilters.remove(filter);
	}

	@Override
	public void registerValidationResultListener(ValidationResultListener listener) {
		validationResultListeners.add(listener);
	}

	@Override
	public void unregisterValidationResultListener(ValidationResultListener listener) {
		validationResultListeners.remove(listener);
	}

	@Override
	public void setSubstitutionLabelProvider(SubstitutionLabelProvider substitutionLabelProvider) {
		this.substitutionLabelProvider = substitutionLabelProvider;
	}

	@Override
	public boolean isBusy() {
		return validationRunning;
	}

	@Override
	public void cancel() {
		cancelationRequested = true;
	}

}
