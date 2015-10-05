/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecp.view.internal.model.common.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * Tester for Text Renderer.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 */
public class XMLDateControlRendererTester implements ECPRendererTester {

	private static final String XML_TYPE_DATE = "http://www.eclipse.org/emf/2003/XMLType#date"; //$NON-NLS-1$
	private static final String BASE_TYPE = "baseType"; //$NON-NLS-1$
	private static final String TYPE_DATE = "date"; //$NON-NLS-1$
	private static final String TYPE_NAME = "name"; //$NON-NLS-1$
	private static final String EXTENDED_META_DATA = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		if (control.getDomainModelReference() == null) {
			return NOT_APPLICABLE;
		}
		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding()
				.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (!isSingleAttribute(feature)) {
			return NOT_APPLICABLE;
		}

		final EDataType eAttributeType = EAttribute.class.cast(feature).getEAttributeType();
		if (eAttributeType == XMLTypePackage.eINSTANCE.getDate()) {
			return getPriority();
		}

		final Class<?> instanceClass = eAttributeType.getInstanceClass();
		if (instanceClass == null || !getSupportedClassType().isAssignableFrom(instanceClass)) {
			return NOT_APPLICABLE;
		}

		if (checkFeatureETypeAnnotations(feature.getEType().getEAnnotations())) {
			return getPriority();
		}

		return NOT_APPLICABLE;
	}

	private boolean isSingleAttribute(EStructuralFeature feature) {
		if (feature.isMany()) {
			return false;
		}

		if (EReference.class.isInstance(feature)) {
			return false;
		}
		return true;
	}

	private int getPriority() {
		return 3;
	}

	private Class<?> getSupportedClassType() {
		return XMLGregorianCalendar.class;
	}

	private boolean checkFeatureETypeAnnotations(EList<EAnnotation> eAnnotations) {
		for (final EAnnotation annotation : eAnnotations) {
			if (!annotation.getSource().equals(EXTENDED_META_DATA)) {
				continue;
			}
			if (annotation.getDetails().containsKey(TYPE_NAME)
				&& annotation.getDetails().get(TYPE_NAME).equals(TYPE_DATE)) {
				return true;
			}
			if (annotation.getDetails().containsKey(BASE_TYPE)
				&& annotation.getDetails().get(BASE_TYPE).equals(XML_TYPE_DATE)) {
				return true;
			}
		}
		return false;
	}
}
