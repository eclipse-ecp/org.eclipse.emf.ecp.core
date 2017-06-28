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
package org.eclipse.emf.ecp.edit.internal.swt.util;

import java.text.MessageFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.InvalidDatatypeValueException;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationService;
import org.eclipse.emfforms.spi.common.validation.PreSetValidationServiceRunnable;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Utility class for setting up a {@link VerifyListener}
 * that performs pre-set validation.
 *
 */
public final class PreSetValidationListeners {

	/**
	 * Singleton instance.
	 */
	private static PreSetValidationListeners validationListeners = new PreSetValidationListeners();
	private static PreSetValidationService preSetValidationService;

	private PreSetValidationListeners() {
		init();
	}

	/**
	 * Returns the validation listeners factory.
	 *
	 * @return the factory that can be used to create and attach listeners
	 */
	public static PreSetValidationListeners create() {
		return validationListeners;
	}

	private void init() {
		if (preSetValidationService == null) {
			final BundleContext bundleContext = FrameworkUtil
				.getBundle(getClass())
				.getBundleContext();

			final ServiceReference<PreSetValidationService> serviceReference = bundleContext
				.getServiceReference(PreSetValidationService.class);

			preSetValidationService = serviceReference != null
				? bundleContext.getService(serviceReference) : null;
		}
	}

	/**
	 * Attach a {@link VerifyListener} to the given {@link Text} widget.
	 * Performs pre-set validation for the given {@link EStructuralFeature}
	 *
	 * @param text the text widget the created verify listener should be attached to
	 * @param feature the feature to be validated
	 */
	public void verify(Text text, final EStructuralFeature feature) {
		verify(text, feature, null);
	}

	/**
	 * Attach a {@link VerifyListener} to the given {@link Combo} widget.
	 * Performs pre-set validation for the given {@link EStructuralFeature}
	 *
	 * @param combo the combo widget the created verify listener should be attached to
	 * @param feature the feature to be validated
	 */
	public void verify(Combo combo, final EStructuralFeature feature) {
		verify(combo, feature, null);
	}

	/**
	 * Attach a {@link VerifyListener} to the given {@link Text} widget.
	 * Performs pre-set validation for the given {@link EStructuralFeature} and reports any
	 * errors to the given {@link VElement}.
	 *
	 * @param text the text widget the created verify listener should be attached to
	 * @param feature the feature to be validated
	 * @param vElement the {@link VElement} an {@link Diagnostic} may be attached to
	 */
	public void verify(Text text, final EStructuralFeature feature, final VElement vElement) {

		if (!EAttribute.class.isInstance(feature)) {
			// this shouldn't happen as we expect only EDataTypes
			return;
		}

		final EAttribute attribute = (EAttribute) feature;

		if (preSetValidationService != null) {
			final VerifyListener verifyListener = new PreSetVerifyListener(vElement, attribute);
			text.addVerifyListener(verifyListener);
		}
	}

	/**
	 * Attach a {@link VerifyListener} to the given {@link Combo} widget.
	 * Performs pre-set validation for the given {@link EStructuralFeature} and reports any
	 * errors to the given {@link VElement}.
	 *
	 * @param combo the combo widget the created verify listener should be attached to
	 * @param feature the feature to be validated
	 * @param vElement the {@link VElement} an {@link Diagnostic} may be attached to
	 */
	public void verify(Combo combo, final EStructuralFeature feature, final VElement vElement) {

		if (!EAttribute.class.isInstance(feature)) {
			// this shouldn't happen as we expect only EDataTypes
			return;
		}

		final EAttribute attribute = (EAttribute) feature;

		if (preSetValidationService != null) {
			final VerifyListener verifyListener = new PreSetVerifyListener(vElement, attribute);
			combo.addVerifyListener(verifyListener);
		}
	}

	/**
	 * Validate a given feature value strictly based on the defined constraints.
	 *
	 * @param feature the feature to validate
	 * @param value the value to validate
	 * @return the resulting {@link VDiagnostic}
	 */
	protected VDiagnostic validateStrict(EStructuralFeature feature, Object value) {
		final Diagnostic strictDiag = preSetValidationService.validate(feature, value);
		final VDiagnostic vDiagnostic = VViewFactory.eINSTANCE.createDiagnostic();
		if (strictDiag.getSeverity() != Diagnostic.OK) {
			vDiagnostic.getDiagnostics().add(strictDiag);
			return vDiagnostic;
		}
		if (feature.isRequired()) {
			/* value must not be empty, which is not an EDataType-Constraint */
			if (value == null || isString(feature.getEType()) && "".equals(value)) { //$NON-NLS-1$
				final BasicDiagnostic multiplicityDiagnostic = new BasicDiagnostic(Diagnostic.ERROR, "", //$NON-NLS-1$
					EObjectValidator.EOBJECT__EVERY_MULTIPCITY_CONFORMS,
					MessageFormat.format("The required feature ''{0}'' must be set", feature.getName()), //$NON-NLS-1$
					new Object[0]);
				vDiagnostic.getDiagnostics().add(multiplicityDiagnostic);
				return vDiagnostic;
			}
		}
		return null;
	}

	private boolean isString(EClassifier classifier) {
		return classifier.getInstanceTypeName().equals(String.class.getCanonicalName());
	}

	/**
	 * Attach a {@link FocusListener} to the given {@link Text} widget.
	 * Performs pre-set validation for the given {@link EStructuralFeature} and
	 * executes the {@link Runnable} in case the content of the text widget is
	 * invalid.
	 *
	 * @param text the text widget the created verify listener should be attached to
	 * @param feature the feature to be validated
	 * @param focusLost code to be executed in case the text is invalid and focus has been lost
	 * @param focusGained code to be executed in case the focus has been gained
	 */
	public void focus(final Text text, final EStructuralFeature feature,
		final PreSetValidationServiceRunnable focusLost,
		final Runnable focusGained) {
		if (preSetValidationService != null) {
			text.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					focusLost.run(preSetValidationService);
				}

				@Override
				public void focusGained(FocusEvent e) {
					focusGained.run();
				}
			});
		}
	}

	/**
	 * Default VerifyListener implementation.
	 *
	 */
	public static class PreSetVerifyListener implements VerifyListener {

		private final EAttribute attribute;
		private final VElement vElement;

		/**
		 * Constructor.
		 *
		 * @param vElement the {@link VElement} any {@link VDiagnostic} will be attached to
		 * @param attribute the {@link EAttribute} to be validated
		 */
		public PreSetVerifyListener(VElement vElement, EAttribute attribute) {
			this.vElement = vElement;
			this.attribute = attribute;
		}

		@Override
		/**
		 *
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.VerifyEvent)
		 */
		public void verifyText(VerifyEvent e) {
			final String changedText = obtainText(e);

			Object changedValue;
			try {
				changedValue = EcoreUtil.createFromString(attribute.getEAttributeType(), changedText);
			} catch (final IllegalArgumentException | InvalidDatatypeValueException formatException) {
				if (isInteger(attribute.getEType()) && changedText.isEmpty()
					|| XMLGregorianCalendar.class.isAssignableFrom(attribute.getEType().getInstanceClass())
					|| double.class.isAssignableFrom(attribute.getEType().getInstanceClass())
					|| Double.class.isAssignableFrom(attribute.getEType().getInstanceClass())) {

					// TODO: corner case, let change propagate in case of integer
					return;
				}

				e.doit = false;
				return;
			}

			final VDiagnostic prevDiagnostic = vElement == null ? null : vElement.getDiagnostic();
			if (vElement != null) {
				vElement.setDiagnostic(validationListeners.validateStrict(attribute, changedValue));
			}

			final Diagnostic looseDiag = preSetValidationService.validateLoose(attribute,
				changedValue);
			if (looseDiag.getSeverity() == Diagnostic.OK) {
				// loose validation successfully, but keep nevertheless keep validation diagnostic
				return;
			}

			// loose validation not successfully, revert and restore previous diagnostic, if any
			// TODO: revert only for strings because of un-intuitive behavior for integers
			if (validationListeners.isString(attribute.getEType())) {
				// remove diagnostic once again, since we revert the change
				e.doit = false;
				if (vElement != null) {
					vElement.setDiagnostic(prevDiagnostic);
				}

			}
		}

		/**
		 * Obtain the text value of the widget.
		 *
		 * @param event the event
		 * @return the current text value
		 */
		protected String obtainText(VerifyEvent event) {
			String currentText = ""; //$NON-NLS-1$
			if (event.widget instanceof Text) {
				currentText = Text.class.cast(event.widget).getText();
			} else if (event.widget instanceof Combo) {
				currentText = Combo.class.cast(event.widget).getText();
			}
			final String changedText = currentText.substring(0, event.start) + event.text
				+ currentText.substring(event.end);
			return changedText;
		}

		private boolean isInteger(EClassifier classifier) {
			return classifier.getInstanceTypeName().equals(Integer.class.getCanonicalName());
		}

	}
}
