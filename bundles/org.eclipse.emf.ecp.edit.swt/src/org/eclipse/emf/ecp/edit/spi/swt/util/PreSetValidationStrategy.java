/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.util;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A common {@link EMFUpdateValueStrategy} that implements {@link #validateBeforeSet(Object)}.
 *
 * @since 1.13
 */
public class PreSetValidationStrategy extends EMFUpdateValueStrategy {

	private final EStructuralFeature eStructuralFeature;
	private final VElement vElement;
	private final UpdateValueStrategy strategy;

	/**
	 * Constructor.
	 *
	 * @param vElement the {@link VElement}
	 * @param eStructuralFeature an {@link EStructuralFeature} that defines any validation constraints
	 * @param delegate the strategy to delegate to
	 */
	public PreSetValidationStrategy(VElement vElement, EStructuralFeature eStructuralFeature,
		UpdateValueStrategy delegate) {
		this.vElement = vElement;
		this.eStructuralFeature = eStructuralFeature;
		strategy = delegate;
	}

	@Override
	public IStatus validateBeforeSet(Object value) {
		final BundleContext bundleContext = FrameworkUtil
			.getBundle(getClass())
			.getBundleContext();
		final ServiceReference<PreSetValidationService> serviceReference = bundleContext
			.getServiceReference(PreSetValidationService.class);

		if (serviceReference == null) {
			return strategy.validateBeforeSet(value);
		}

		if (eStructuralFeature.isUnsettable() && SetCommand.UNSET_VALUE == value) {
			/* we are dealing with an unsettable feature and the unset value */
			/* we need to validate the default value instead of the unset value */
			value = eStructuralFeature.getDefaultValue();
		}

		try {

			final PreSetValidationService service = bundleContext.getService(serviceReference);

			if (service == null) {
				return strategy.validateBeforeSet(value);
			}

			final Diagnostic result = service.validate(eStructuralFeature, value);

			if (result.getSeverity() == Diagnostic.OK) {
				return Status.OK_STATUS;
			}

			// TODO: existing diagnostics?
			final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
			vDiagnostic.getDiagnostics().add(result);
			if (vElement != null) {
				vElement.setDiagnostic(vDiagnostic);
			}
			return BasicDiagnostic.toIStatus(result);
		} finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public Object convert(Object value) {
		return strategy.convert(value);
	}

	@Override
	public int getUpdatePolicy() {
		return strategy.getUpdatePolicy();
	}

	@Override
	public UpdateValueStrategy setAfterConvertValidator(IValidator validator) {
		super.setAfterConvertValidator(validator);
		return strategy.setAfterConvertValidator(validator);
	}

	@Override
	public UpdateValueStrategy setBeforeSetValidator(IValidator validator) {
		super.setBeforeSetValidator(validator);
		return strategy.setBeforeSetValidator(validator);
	}

	@Override
	public UpdateValueStrategy setAfterGetValidator(IValidator validator) {
		super.setAfterGetValidator(validator);
		return strategy.setAfterGetValidator(validator);
	}

	@Override
	public UpdateValueStrategy setConverter(IConverter converter) {
		super.setConverter(converter);
		return strategy.setConverter(converter);
	}

	@Override
	public IStatus validateAfterConvert(Object value) {
		return strategy.validateAfterConvert(value);
	}

	@Override
	public IStatus validateAfterGet(Object value) {
		return strategy.validateAfterGet(value);
	}

	/**
	 * Returns the {@link EStructuralFeature} that defines any validation constraints.
	 *
	 * @return the structural feature
	 */
	public EStructuralFeature getStructuralFeature() {
		return eStructuralFeature;
	}

	/**
	 * Returns the associated {@link VElement}.
	 *
	 * @return the {@link VElement}
	 */
	public VElement getVElement() {
		return vElement;
	}
}
