/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class PreSetValidationStrategy extends UpdateValueStrategy {

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#validateBeforeSet(java.lang.Object)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#convert(java.lang.Object)
	 */
	@Override
	public Object convert(Object value) {
		return strategy.convert(value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#getUpdatePolicy()
	 */
	@Override
	public int getUpdatePolicy() {
		return strategy.getUpdatePolicy();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#setAfterConvertValidator(org.eclipse.core.databinding.validation.IValidator)
	 */
	@Override
	public UpdateValueStrategy setAfterConvertValidator(IValidator validator) {
		return strategy.setAfterConvertValidator(validator);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#setBeforeSetValidator(org.eclipse.core.databinding.validation.IValidator)
	 */
	@Override
	public UpdateValueStrategy setBeforeSetValidator(IValidator validator) {
		return strategy.setBeforeSetValidator(validator);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#setAfterGetValidator(org.eclipse.core.databinding.validation.IValidator)
	 */
	@Override
	public UpdateValueStrategy setAfterGetValidator(IValidator validator) {
		return strategy.setAfterGetValidator(validator);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#setConverter(org.eclipse.core.databinding.conversion.IConverter)
	 */
	@Override
	public UpdateValueStrategy setConverter(IConverter converter) {
		return strategy.setConverter(converter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#validateAfterConvert(java.lang.Object)
	 */
	@Override
	public IStatus validateAfterConvert(Object value) {
		return strategy.validateAfterConvert(value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.UpdateValueStrategy#validateAfterGet(java.lang.Object)
	 */
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
