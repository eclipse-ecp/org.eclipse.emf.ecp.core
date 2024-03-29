/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.util;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VHasTooltip;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 *
 * @since 1.5
 *        <!-- end-user-doc -->
 * @see org.eclipse.emf.ecp.view.spi.model.VViewPackage
 * @generated
 */
public class ViewValidator extends EObjectValidator {
	public static final String ECLASS_KEY = "dmr_resolvement_eclass"; //$NON-NLS-1$

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final ViewValidator INSTANCE = new ViewValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic
	 * {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.ecp.view.spi.model"; //$NON-NLS-1$

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a
	 * derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ViewValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
		return VViewPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		switch (classifierID) {
		case VViewPackage.DIAGNOSTIC:
			return validateDiagnostic((VDiagnostic) value, diagnostics, context);
		case VViewPackage.ATTACHMENT:
			return validateAttachment((VAttachment) value, diagnostics, context);
		case VViewPackage.DOMAIN_MODEL_REFERENCE:
			return validateDomainModelReference((VDomainModelReference) value, diagnostics, context);
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE:
			return validateFeaturePathDomainModelReference((VFeaturePathDomainModelReference) value, diagnostics,
				context);
		case VViewPackage.ELEMENT:
			return validateElement((VElement) value, diagnostics, context);
		case VViewPackage.VIEW:
			return validateView((VView) value, diagnostics, context);
		case VViewPackage.CONTAINED_ELEMENT:
			return validateContainedElement((VContainedElement) value, diagnostics, context);
		case VViewPackage.CONTAINER:
			return validateContainer((VContainer) value, diagnostics, context);
		case VViewPackage.CONTAINED_CONTAINER:
			return validateContainedContainer((VContainedContainer) value, diagnostics, context);
		case VViewPackage.CONTROL:
			return validateControl((VControl) value, diagnostics, context);
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES:
			return validateViewModelLoadingProperties((VViewModelLoadingProperties) value, diagnostics, context);
		case VViewPackage.STRING_TO_OBJECT_MAP_ENTRY:
			return validateStringToObjectMapEntry((Map.Entry<?, ?>) value, diagnostics, context);
		case VViewPackage.VIEW_MODEL_PROPERTIES:
			return validateViewModelProperties((VViewModelProperties) value, diagnostics, context);
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT:
			return validateDateTimeDisplayAttachment((VDateTimeDisplayAttachment) value, diagnostics, context);
		case VViewPackage.HAS_TOOLTIP:
			return validateHasTooltip((VHasTooltip) value, diagnostics, context);
		case VViewPackage.DOMAIN_MODEL_REFERENCE_SEGMENT:
			return validateDomainModelReferenceSegment((VDomainModelReferenceSegment) value, diagnostics, context);
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return validateFeatureDomainModelReferenceSegment((VFeatureDomainModelReferenceSegment) value, diagnostics,
				context);
		case VViewPackage.LABEL_ALIGNMENT:
			return validateLabelAlignment((LabelAlignment) value, diagnostics, context);
		case VViewPackage.DATE_TIME_DISPLAY_TYPE:
			return validateDateTimeDisplayType((DateTimeDisplayType) value, diagnostics, context);
		case VViewPackage.DOMAIN_MODEL_REFERENCE_CHANGE_LISTENER:
			return validateDomainModelReferenceChangeListener((DomainModelReferenceChangeListener) value, diagnostics,
				context);
		default:
			return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDiagnostic(VDiagnostic diagnostic, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(diagnostic, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateAttachment(VAttachment attachment, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attachment, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDomainModelReference(VDomainModelReference domainModelReference,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(domainModelReference, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateFeaturePathDomainModelReference(
		VFeaturePathDomainModelReference featurePathDomainModelReference, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		if (!validate_NoCircularContainment(featurePathDomainModelReference, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(featurePathDomainModelReference, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(featurePathDomainModelReference, diagnostics,
				context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(featurePathDomainModelReference, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateFeaturePathDomainModelReference_resolveable(featurePathDomainModelReference, diagnostics,
				context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Feature Path Domain Model Reference</em>'.
	 * <!-- begin-user-doc -->
	 *
	 * @param featurePathDomainModelReference the dmr to check
	 * @param diagnostics the chain
	 * @param context the validation context
	 * @return the result
	 *         <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	// BEGIN COMPLEX CODE
	public boolean validateFeaturePathDomainModelReference_resolveable(
		VFeaturePathDomainModelReference featurePathDomainModelReference, DiagnosticChain diagnostics,
		Map<Object, Object> context) {

		if (featurePathDomainModelReference.getDomainModelEFeature() == null) {
			if (featurePathDomainModelReference.eContainer() != null) {
				diagnostics
					.add(createDiagnostic(Diagnostic.ERROR, 0, "No EFeature set.", //$NON-NLS-1$
						featurePathDomainModelReference.eContainer(),
						featurePathDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		// Do not validate feature dmrs witch are contained in another dmr or segment,
		// as normally a specific logic is applied for them
		if ((VDomainModelReference.class.isInstance(featurePathDomainModelReference.eContainer())
			|| VDomainModelReferenceSegment.class.isInstance(featurePathDomainModelReference.eContainer()))
			&& !context.containsKey(ECLASS_KEY)) {
			return true;
		}

		// identify root eclass
		final VView parentView = getParentView(featurePathDomainModelReference);
		EClass rootEClass = null;
		if (context.containsKey(ECLASS_KEY)) {
			rootEClass = (EClass) context.get(ECLASS_KEY);
		}
		if (rootEClass == null && parentView != null) {
			rootEClass = parentView.getRootEClass();
			if (rootEClass == null) {
				if (diagnostics != null) {
					diagnostics.add(createDiagnostic(Diagnostic.WARNING, 0,
						"Parent view has no root EClass set. The reference may be unresolveable.", //$NON-NLS-1$
						featurePathDomainModelReference.eContainer(),
						featurePathDomainModelReference.eContainingFeature()));
				}
			}
		}
		if (rootEClass == null) {
			if (isProxy(featurePathDomainModelReference)) {
				return false;
			}
			if (featurePathDomainModelReference.getDomainModelEReferencePath().isEmpty()) {
				rootEClass = (EClass) featurePathDomainModelReference.getDomainModelEFeature().eContainer();
			} else {
				rootEClass = (EClass) featurePathDomainModelReference.getDomainModelEReferencePath().get(0)
					.eContainer();
			}
		}

		// test if path resolveable
		EClass current = rootEClass;
		for (final EReference reference : featurePathDomainModelReference.getDomainModelEReferencePath()) {
			if (!current.getEAllReferences().contains(reference)) {
				if (diagnostics != null) {
					final String message = "Domain model reference is unresolveable. Failed on reference: " //$NON-NLS-1$
						+ reference.getName();
					if (featurePathDomainModelReference.eContainer() != null) {
						diagnostics.add(
							createDiagnostic(Diagnostic.ERROR, 0, message, featurePathDomainModelReference.eContainer(),
								featurePathDomainModelReference.eContainingFeature()));
					}
					diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, featurePathDomainModelReference,
						VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEReferencePath()));
				}
				return false;
			}
			current = reference.getEReferenceType();
			if (current == null) {
				/* Ecore is misconfigured */
				final String message = MessageFormat.format(
					"Domain model reference is unresolveable. EReference {0} has no type. Please check the ecore", //$NON-NLS-1$
					reference.getName());
				if (featurePathDomainModelReference.eContainer() != null) {
					diagnostics.add(
						createDiagnostic(Diagnostic.ERROR, 0, message, featurePathDomainModelReference.eContainer(),
							featurePathDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, featurePathDomainModelReference,
					VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEReferencePath()));
				return false;
			}
		}

		// test if efeature resolveable
		if (!current.getEAllStructuralFeatures().contains(
			featurePathDomainModelReference.getDomainModelEFeature())) {
			if (diagnostics != null) {
				final String message = "Domain model reference is unresolveable. Failed on domain model feature: " //$NON-NLS-1$
					+ featurePathDomainModelReference.getDomainModelEFeature().getName();
				if (featurePathDomainModelReference.eContainer() != null) {
					diagnostics
						.add(createDiagnostic(Diagnostic.ERROR, 0, message,
							featurePathDomainModelReference.eContainer(),
							featurePathDomainModelReference.eContainingFeature()));
				}
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, 0, message, featurePathDomainModelReference,
					VViewPackage.eINSTANCE.getFeaturePathDomainModelReference_DomainModelEFeature()));
			}
			return false;
		}

		return true;
	}

	// END COMPLEX CODE

	private boolean isProxy(VFeaturePathDomainModelReference featurePathDomainModelReference) {
		if (featurePathDomainModelReference.getDomainModelEFeature().eIsProxy()) {
			return true;
		}
		for (final EReference eReference : featurePathDomainModelReference.getDomainModelEReferencePath()) {
			if (eReference.eIsProxy()) {
				return true;
			}
		}
		return false;
	}

	private Diagnostic createDiagnostic(int severity, int code, String message, EObject object,
		EStructuralFeature feature) {
		return new BasicDiagnostic(
			severity,
			DIAGNOSTIC_SOURCE,
			code,
			message,
			new Object[] { object, feature });
	}

	private VView getParentView(VDomainModelReference reference) {
		EObject parent = reference.eContainer();
		while (parent != null && !VView.class.isInstance(parent)) {
			parent = parent.eContainer();
		}
		return VView.class.cast(parent);
	}

	/**
	 * <!-- begin-user-doc -->.
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateElement(VElement element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(element, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateView(VView view, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(view, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateContainedElement(VContainedElement containedElement, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(containedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateContainer(VContainer container, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(container, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateContainedContainer(VContainedContainer containedContainer, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(containedContainer, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateControl(VControl control, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(control, diagnostics, context)) {
			return false;
		}
		boolean result = validate_EveryMultiplicityConforms(control, diagnostics, context);
		if (result || diagnostics != null) {
			result &= validate_EveryDataValueConforms(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryReferenceIsContained(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryBidirectionalReferenceIsPaired(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryProxyResolves(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_UniqueID(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryKeyUnique(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validate_EveryMapEntryUnique(control, diagnostics, context);
		}
		if (result || diagnostics != null) {
			result &= validateControl_resolveable(control, diagnostics, context);
		}
		return result;
	}

	/**
	 * Validates the resolveable constraint of '<em>Control</em>'.
	 * <!-- begin-user-doc -->
	 *
	 * @param control the {@link VControl} to check
	 * @param diagnostics the diagnostics
	 * @param context the validation context
	 *            <!-- end-user-doc -->
	 * @return the validation result
	 *
	 * @generated NOT
	 * @since 1.10
	 */
	// CHECKSTYLE.OFF: MethodName
	public boolean validateControl_resolveable(VControl control, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		// CHECKSTYLE.ON: MethodName
		final VDomainModelReference domainModelReference = control.getDomainModelReference();
		if (domainModelReference == null) {
			diagnostics
				.add(createDiagnostic(Diagnostic.ERROR, 0, "No Domain Model Reference set.", //$NON-NLS-1$
					control, VViewPackage.eINSTANCE.getControl_DomainModelReference()));
			return false;
		}
		if (domainModelReference instanceof VFeaturePathDomainModelReference) {
			return validateFeaturePathDomainModelReference_resolveable(
				(VFeaturePathDomainModelReference) domainModelReference, diagnostics, context);
		}
		return validateDomainModelReference(domainModelReference, diagnostics, context);
	}
	// end of custom code

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateViewModelLoadingProperties(VViewModelLoadingProperties viewModelLoadingProperties,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(viewModelLoadingProperties, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringToObjectMapEntry(Map.Entry<?, ?> stringToObjectMapEntry, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject) stringToObjectMapEntry, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateViewModelProperties(VViewModelProperties viewModelProperties, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(viewModelProperties, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeDisplayAttachment(VDateTimeDisplayAttachment dateTimeDisplayAttachment,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dateTimeDisplayAttachment, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.13
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHasTooltip(VHasTooltip hasTooltip, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(hasTooltip, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.19
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDomainModelReferenceSegment(VDomainModelReferenceSegment domainModelReferenceSegment,
		DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(domainModelReferenceSegment, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.19
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFeatureDomainModelReferenceSegment(
		VFeatureDomainModelReferenceSegment featureDomainModelReferenceSegment, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(featureDomainModelReferenceSegment, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateLabelAlignment(LabelAlignment labelAlignment, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeDisplayType(DateTimeDisplayType dateTimeDisplayType, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean validateDomainModelReferenceChangeListener(
		DomainModelReferenceChangeListener domainModelReferenceChangeListener, DiagnosticChain diagnostics,
		Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} // ViewValidator
