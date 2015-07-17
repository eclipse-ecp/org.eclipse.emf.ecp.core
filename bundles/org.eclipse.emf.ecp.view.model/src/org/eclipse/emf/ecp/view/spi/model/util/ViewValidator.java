/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.util;

import java.util.Iterator;
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
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 *
 * @since 1.5
 *        <!-- end-user-doc -->
 *
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
		case VViewPackage.LABEL_ALIGNMENT:
			return validateLabelAlignment((LabelAlignment) value, diagnostics, context);
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

		if (VDomainModelReference.class.isInstance(featurePathDomainModelReference.eContainer())
			&& featurePathDomainModelReference.eContainmentFeature().isMany()) {
			final VDomainModelReference parent = VDomainModelReference.class.cast(featurePathDomainModelReference
				.eContainer());
			final Iterator<EStructuralFeature> structuralFeatureIterator = parent.getEStructuralFeatureIterator();
			if (!structuralFeatureIterator.hasNext()) {
				return true;
			}
			final EStructuralFeature feature = structuralFeatureIterator.next();
			if (!EReference.class.isInstance(feature)) {
				return true;
			}
			context.put(ECLASS_KEY, EReference.class.cast(feature).getEReferenceType());
		}

		if (featurePathDomainModelReference.getDomainModelEFeature() == null) {
			if (featurePathDomainModelReference.eContainer() != null) {
				diagnostics
					.add(createDiagnostic(Diagnostic.ERROR, 0, "No EFeature set.", //$NON-NLS-1$
						featurePathDomainModelReference.eContainer(),
						featurePathDomainModelReference.eContainingFeature()));
			}
			return false;
		}

		// identify root eclass
		final VView parentView = getParentView(featurePathDomainModelReference);
		EClass rootEClass = null;
		if (context.containsKey(ECLASS_KEY)) {
			rootEClass = (EClass) context.get(ECLASS_KEY);
		} else if (parentView != null) {
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
		return validate_EveryDefaultConstraint(control, diagnostics, context);
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
