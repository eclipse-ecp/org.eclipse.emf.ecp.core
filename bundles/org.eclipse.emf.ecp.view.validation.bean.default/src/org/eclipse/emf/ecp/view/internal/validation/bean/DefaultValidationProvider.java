package org.eclipse.emf.ecp.view.internal.validation.bean;

import java.io.InputStream;

import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.validation.bean.BeanValidationProvider;

public class DefaultValidationProvider extends BeanValidationProvider {

	public DefaultValidationProvider() {
		super();
	}

	public DefaultValidationProvider(InputStream... inputStreams) {
		super(inputStreams);
	}

	@Override
	protected MessageInterpolator getMessageInterpolator(
			MessageInterpolator messageInterpolator) {
		return messageInterpolator;
	}

	@Override
	protected int getSeverity(ConstraintViolation<EObject> violation) {
		return Diagnostic.ERROR;
	}

}
