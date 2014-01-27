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
import java.util.LinkedHashSet;
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * Validation for view validation.
 * 
 * @author jfaltermeier
 * @author emueller
 */
public class ViewValidator extends ViewModelGraph {

	private final Queue<EObject> validationQueue = new LinkedList<EObject>();

	private final ValidationRegistry validationRegistry;

	private boolean validationRunning;

	private final Set<ValidationProvider> validationProviders;

	private final Map<VControl, Map<UniqueSetting, VDiagnostic>> currentUpdates = new LinkedHashMap<VControl, Map<UniqueSetting, VDiagnostic>>();
	private final Set<EObject> validated = new LinkedHashSet<EObject>();

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
		return VViewFactory.eINSTANCE.createDiagnostic();
	}

	/**
	 * Validates all given eObjects.
	 * 
	 * @param eObjects the eObjects to validate
	 */
	public void validate(Collection<EObject> eObjects) {

		EObject firstObject = null;
		for (final EObject eObject : eObjects) {
			// validate(eObject);
			if (firstObject == null) {
				firstObject = eObject;
			} else {
				validationQueue.offer(eObject);
			}
		}
		if (firstObject != null) {
			validate(firstObject);
		}

	}

	/**
	 * Validates all given eObjects.
	 * 
	 * @param eObjects the eObjects to validate
	 */
	// FIXME remove
	@Deprecated
	public void validateSettings(Collection<UniqueSetting> eObjects) {
		EObject firstObject = null;
		for (final UniqueSetting eObject : eObjects) {
			// validate(eObject.getEObject());
			if (firstObject == null) {
				firstObject = eObject.getEObject();
			} else {
				validationQueue.offer(eObject.getEObject());
			}
		}
		if (firstObject != null) {
			validate(firstObject);
		}
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
			validated.add(eObject);
			validateAndNotifyControls(eObject);

			EObject toValidate = validationQueue.poll();
			while (toValidate != null) {
				if (!validated.contains(toValidate)) {
					validated.add(toValidate);
					validateAndNotifyControls(toValidate);
				}
				toValidate = validationQueue.poll();
			}
			update();
			validated.clear();
			currentUpdates.clear();
			validationRunning = false;
		}

	}

	private void update() {
		for (final VControl control : currentUpdates.keySet()) {
			for (final Map.Entry<UniqueSetting, VDiagnostic> pair : currentUpdates.get(control).entrySet()) {
				update(control, pair.getKey().getEObject(), pair.getKey().getEStructuralFeature(),
					VDiagnosticHelper.clean(pair.getValue()));
			}
		}
		for (final VControl control : currentUpdates.keySet()) {
			updateRenderable(control);
			updateParents(control);
			// EObject parent = control.eContainer();
			// while (parent != null && VElement.class.isInstance(parent)) {
			// updateRenderable((VElement) parent);
			// parent = parent.eContainer();
			// }
		}
	}

	/**
	 * @param eObject
	 */
	private void validateAndNotifyControls(EObject eObject) {

		final Diagnostic diagnostic = getDiagnosticForEObject(eObject);

		if (diagnostic.getSeverity() == Diagnostic.OK) {
			handleOKDiagnostic(eObject, diagnostic);
		}
		else {
			final Map<Setting, Set<Diagnostic>> featureToValidationResult = collectChangedSettings(eObject, diagnostic);

			// TODO: if we ever get performance problems this is likely the place to start
			// validation registry should be queryable with a control and a feature
			// -> merge SettingsMapping and the registry
			// final Set<UpdateTriple> updateTriplets = new LinkedHashSet<ViewValidator.UpdateTriple>();
			for (final Setting invalidSetting : featureToValidationResult.keySet()) {
				for (final VControl control : validationRegistry.getRenderablesForEObject(invalidSetting)) {
					final VDomainModelReference modelReference = control.getDomainModelReference();
					final Iterator<Setting> settings = modelReference.getIterator();
					final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
					while (settings.hasNext()) {
						final Setting setting = settings.next();

						if (setting.getEObject().equals(invalidSetting.getEObject())
							&& setting.getEStructuralFeature().equals(invalidSetting.getEStructuralFeature())) {
							vDiagnostic.getDiagnostics().addAll(featureToValidationResult.get(invalidSetting));

							addTriple(control,
								UniqueSetting.createSetting(setting), vDiagnostic);
						}

					}
				}
			}
		}
	}

	private void addTriple(VControl control, UniqueSetting uniqueSetting, VDiagnostic vDiagnostic) {
		Map<UniqueSetting, VDiagnostic> settingDiagnostic = null;
		if (currentUpdates.containsKey(control)) {
			settingDiagnostic = currentUpdates.get(control);
		} else {
			settingDiagnostic = new LinkedHashMap<UniqueSetting, VDiagnostic>();
			currentUpdates.put(control, settingDiagnostic);
		}
		if (!settingDiagnostic.containsKey(uniqueSetting)) {
			settingDiagnostic.put(uniqueSetting, vDiagnostic);
		} else {
			settingDiagnostic.get(uniqueSetting).getDiagnostics().addAll(vDiagnostic.getDiagnostics());
		}
	}

	private Map<Setting, Set<Diagnostic>> collectChangedSettings(EObject eObject, Diagnostic diagnostic) {
		final Map<Setting, Set<Diagnostic>> featureToValidationResult = new LinkedHashMap<Setting,
			Set<Diagnostic>>();
		final Set<EStructuralFeature> validatedFeatures = new LinkedHashSet<EStructuralFeature>();
		for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
			if (childDiagnostic.getData().size() != 2) {
				continue;
			}

			final EStructuralFeature feature = (EStructuralFeature) childDiagnostic.getData().get(1);
			validatedFeatures.add(feature);
			final InternalEObject eObject2 = (InternalEObject) childDiagnostic.getData().get(0);
			final Setting setting = eObject2.eSetting(feature);
			if (!featureToValidationResult.containsKey(setting)) {
				featureToValidationResult.put(setting, new LinkedHashSet<Diagnostic>());
			}
			featureToValidationResult.get(setting).add(childDiagnostic);
		}
		for (final EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			if (validatedFeatures.contains(feature)) {
				continue;
			}
			final InternalEObject eObject2 = (InternalEObject) eObject;
			final Setting setting = eObject2.eSetting(feature);
			if (!featureToValidationResult.containsKey(setting)) {
				featureToValidationResult.put(setting, new LinkedHashSet<Diagnostic>());
			}
			featureToValidationResult.get(setting).add(Diagnostic.OK_INSTANCE);
		}
		return featureToValidationResult;
	}

	private void handleOKDiagnostic(EObject eObject, Diagnostic diagnostic) {
		// TODO hack or ok?
		final Set<EObject> okEObjects = new LinkedHashSet<EObject>();
		okEObjects.add(eObject);
		if (diagnostic.getChildren() != null) {
			for (final Diagnostic childDiagnostic : diagnostic.getChildren()) {
				okEObjects.add((EObject) childDiagnostic.getData().get(0));
			}
		}
		for (final EObject okEObject : okEObjects) {
			for (final EStructuralFeature esf : okEObject.eClass().getEAllStructuralFeatures()) {
				final Setting diagSetting = ((InternalEObject) okEObject).eSetting(esf);
				for (final VControl control : validationRegistry.getRenderablesForEObject(diagSetting)) {
					if (VDiagnosticHelper.isEqual(control.getDiagnostic(), getDefaultValue())) {
						continue;
					}
					final VDomainModelReference modelReference = control.getDomainModelReference();
					final Iterator<Setting> settings = modelReference.getIterator();
					while (settings.hasNext()) {
						final Setting setting = settings.next();
						if (setting.getEStructuralFeature().getEContainingClass().isInstance(okEObject)) {

							// update(control, okEObject, setting.getEStructuralFeature(), getDefaultValue());
							addTriple(control,
								UniqueSetting.createSetting(setting), getDefaultValue());
						}
					}

				}
			}
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
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		final Map<Object, Object> context = new LinkedHashMap<Object, Object>();
		context.put(EValidator.SubstitutionLabelProvider.class, new ECPSubstitutionLabelProvider(adapterFactory));
		context.put(EValidator.class, validator);

		validator.validate(object, diagnostics, context);

		adapterFactory.dispose();

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
