/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecp.view.spi.model.DateTimeDisplayType;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDateTimeDisplayAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>.
 *
 * @since 1.2 <!--
 *        end-user-doc -->
 * @generated
 */
public class VViewFactoryImpl extends EFactoryImpl implements VViewFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static VViewFactory init() {
		try {
			VViewFactory theViewFactory = (VViewFactory) EPackage.Registry.INSTANCE.getEFactory(VViewPackage.eNS_URI);
			if (theViewFactory != null) {
				return theViewFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VViewFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public VViewFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case VViewPackage.DIAGNOSTIC:
			return createDiagnostic();
		case VViewPackage.DOMAIN_MODEL_REFERENCE:
			return createDomainModelReference();
		case VViewPackage.VIEW:
			return createView();
		case VViewPackage.CONTROL:
			return createControl();
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES:
			return createViewModelLoadingProperties();
		case VViewPackage.STRING_TO_OBJECT_MAP_ENTRY:
			return (EObject) createStringToObjectMapEntry();
		case VViewPackage.DATE_TIME_DISPLAY_ATTACHMENT:
			return createDateTimeDisplayAttachment();
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT:
			return createFeatureDomainModelReferenceSegment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case VViewPackage.LABEL_ALIGNMENT:
			return createLabelAlignmentFromString(eDataType, initialValue);
		case VViewPackage.DATE_TIME_DISPLAY_TYPE:
			return createDateTimeDisplayTypeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case VViewPackage.LABEL_ALIGNMENT:
			return convertLabelAlignmentToString(eDataType, instanceValue);
		case VViewPackage.DATE_TIME_DISPLAY_TYPE:
			return convertDateTimeDisplayTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VDiagnostic createDiagnostic() {
		VDiagnosticImpl diagnostic = new VDiagnosticImpl();
		return diagnostic;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VDomainModelReference createDomainModelReference() {
		VDomainModelReferenceImpl domainModelReference = new VDomainModelReferenceImpl();
		return domainModelReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VView createView() {
		VViewImpl view = new VViewImpl();
		return view;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VControl createControl() {
		VControlImpl control = new VControlImpl();
		return control;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VViewModelLoadingProperties createViewModelLoadingProperties() {
		VViewModelLoadingPropertiesImpl viewModelLoadingProperties = new VViewModelLoadingPropertiesImpl();
		return viewModelLoadingProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.7
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Object> createStringToObjectMapEntry() {
		VStringToObjectMapEntryImpl stringToObjectMapEntry = new VStringToObjectMapEntryImpl();
		return stringToObjectMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VDateTimeDisplayAttachment createDateTimeDisplayAttachment() {
		VDateTimeDisplayAttachmentImpl dateTimeDisplayAttachment = new VDateTimeDisplayAttachmentImpl();
		return dateTimeDisplayAttachment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @since 2.0
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VFeatureDomainModelReferenceSegment createFeatureDomainModelReferenceSegment() {
		VFeatureDomainModelReferenceSegmentImpl featureDomainModelReferenceSegment = new VFeatureDomainModelReferenceSegmentImpl();
		return featureDomainModelReferenceSegment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LabelAlignment createLabelAlignmentFromString(EDataType eDataType, String initialValue) {
		LabelAlignment result = LabelAlignment.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertLabelAlignmentToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeDisplayType createDateTimeDisplayTypeFromString(EDataType eDataType, String initialValue) {
		DateTimeDisplayType result = DateTimeDisplayType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
				"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.8
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDateTimeDisplayTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VViewPackage getViewPackage() {
		return (VViewPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VViewPackage getPackage() {
		return VViewPackage.eINSTANCE;
	}

} // ViewFactoryImpl
