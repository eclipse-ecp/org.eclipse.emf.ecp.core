/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Edgar Mueller - refactorings for the validation graph
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/**
 * Validation for view validation.
 * 
 * @author jfaltermeier
 * @author emueller
 */
public class ViewValidator extends ViewModelGraph<VDiagnostic> {

	private final Queue<EObject> validationQueue = new LinkedList<EObject>();

	private final ValidationRegistry validationRegistry;

	private boolean validationRunning;

	private final Set<ValidationProvider> validationProviders;

	/**
	 * Default constructor.
	 * 
	 * @param viewModel
	 *            the view model
	 * @param domainModel
	 *            the domain model
	 * @param validationRegistry
	 *            the validation registry for the view validation
	 * @param validationProviders the set of additional {@link ValidationProvider ValidationProviders}
	 */
	public ViewValidator(VElement viewModel, EObject domainModel, ValidationRegistry validationRegistry,
		Set<ValidationProvider> validationProviders) {
		super(viewModel, domainModel, new Comparator<VDiagnostic>() {
			public int compare(VDiagnostic vDiagnostic1, VDiagnostic vDiagnostic2) {
				if (vDiagnostic1.getHighestSeverity() > vDiagnostic2.getHighestSeverity()) {
					return 1;
				} else if (vDiagnostic1.getHighestSeverity() == vDiagnostic2.getHighestSeverity()) {
					return 0;
				}
				return -1;
			}
		});
		this.validationRegistry = validationRegistry;
		this.validationProviders = validationProviders;

		// readPropagators();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.common.cachetree.AbstractCachedTree#getDefaultValue()
	 */
	@Override
	public VDiagnostic getDefaultValue() {
		final VDiagnostic result = VViewFactory.eINSTANCE.createDiagnostic();
		result.getDiagnostics().add(Diagnostic.OK_INSTANCE);
		return result;
	}

	/**
	 * Validate the given eObject.
	 * 
	 * @param eObject the eObject to validate
	 */
	public void validate(EObject eObject) {
		if (validationRunning) {
			validationQueue.offer(eObject);
		} else {
			validationRunning = true;
			validateAndNotifyControls(eObject);

			EObject toValidate = validationQueue.poll();
			while (toValidate != null) {
				validateAndNotifyControls(toValidate);
				toValidate = validationQueue.poll();
			}

			validationRunning = false;
		}

	}

	/**
	 * @param eObject
	 */
	private void validateAndNotifyControls(EObject eObject) {

		final Diagnostic diagnostic = getDiagnosticForEObject(eObject);

		if (diagnostic.getSeverity() == Diagnostic.OK) {
			for (final VControl control : validationRegistry.getRenderablesForEObject(eObject)) {
				final VDomainModelReference modelReference = control.getDomainModelReference();
				final Iterator<Setting> settings = modelReference.getIterator();
				while (settings.hasNext()) {
					final Setting setting = settings.next();
					if (setting.getEStructuralFeature().getEContainingClass().isInstance(eObject)) {
						update(control, eObject, setting.getEStructuralFeature(), getDefaultValue());
					}
				}

			}
		} else {

			final Map<EStructuralFeature, Diagnostic> featureToValidationResult = new LinkedHashMap<EStructuralFeature,
				Diagnostic>();
			for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
				if (childDiagnostic.getData().size() != 2) {
					continue;
				}

				final EStructuralFeature feature = (EStructuralFeature) childDiagnostic.getData().get(1);
				featureToValidationResult.put(feature, childDiagnostic);
			}

			// TODO: if we ever get performance problems this is likely the place to start
			// validation registry should be queryable with a control and a feature
			// -> merge SettingsMapping and the registry
			for (final EStructuralFeature invalidFeature : featureToValidationResult.keySet()) {
				for (final VControl control : validationRegistry.getRenderablesForEObject(eObject)) {
					final VDomainModelReference modelReference = control.getDomainModelReference();
					final Iterator<Setting> settings = modelReference.getIterator();
					while (settings.hasNext()) {
						final Setting setting = settings.next();

						final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
						if (setting.getEStructuralFeature().equals(invalidFeature)) {
							vDiagnostic.getDiagnostics().add(featureToValidationResult.get(invalidFeature));
							update(control, eObject, setting.getEStructuralFeature(),
								vDiagnostic);
						} else {
							// check if feature is not contained in current diagnostics
							if (!featureToValidationResult.containsKey(setting.getEStructuralFeature())) {
								update(control, eObject, setting.getEStructuralFeature(),
									vDiagnostic);
							} else {
								// check if feature is not contained in current diagnostics
								if (!featureToValidationResult.containsKey(setting.getEStructuralFeature())
									&&
									eObject.eClass().getEAllStructuralFeatures()
										.contains(setting.getEStructuralFeature())) {
									update(control, eObject, setting.getEStructuralFeature(),
										getDefaultValue());
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Validates all given eObjects.
	 * 
	 * @param eObjects the eObjects to validate
	 */
	public void validate(Collection<EObject> eObjects) {

		for (final EObject eObject : eObjects) {
			validate(eObject);
		}

	}

	@Override
	protected void updateRenderable(VElement renderable) {

		final VDiagnostic val = getValue(renderable);

		if (!VDiagnosticHelper.isEqual(renderable.getDiagnostic(), val))
		{
			renderable.setDiagnostic(EcoreUtil.copy(val));
		}
	}

	/**
	 * Computes the {@link Diagnostic} for the given eObject.
	 * 
	 * @param object the eObject to validate
	 * @return the diagnostic
	 */
	public Diagnostic getDiagnosticForEObject(EObject object) {
		EValidator validator = EValidator.Registry.INSTANCE.getEValidator(object.eClass().getEPackage());
		final BasicDiagnostic diagnostics = Diagnostician.INSTANCE.createDefaultDiagnostic(object);

		if (validator == null) {
			validator = new EObjectValidator();
		}
		final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, Diagnostician.INSTANCE);
		context.put(EValidator.class, validator);

		validator.validate(object, diagnostics, context);

		final Map<EStructuralFeature, DiagnosticChain> diagnosticMap = new LinkedHashMap<EStructuralFeature, DiagnosticChain>();
		for (final Diagnostic child : diagnostics.getChildren()) {
			if (DiagnosticChain.class.isInstance(child)) {
				diagnosticMap.put((EStructuralFeature) child.getData().get(1), (DiagnosticChain) child);
			}
		}

		for (final ValidationProvider validationProvider : validationProviders) {
			final List<Diagnostic> additionValidation = validationProvider.validate(object);
			for (final Diagnostic additionDiagnostic : additionValidation) {
				if (diagnosticMap.containsKey(additionDiagnostic.getData().get(1))) {
					diagnosticMap.get(additionDiagnostic.getData().get(1)).add(additionDiagnostic);
				} else {
					diagnostics.add(additionDiagnostic);
				}

			}
		}
		return diagnostics;
	}

}
