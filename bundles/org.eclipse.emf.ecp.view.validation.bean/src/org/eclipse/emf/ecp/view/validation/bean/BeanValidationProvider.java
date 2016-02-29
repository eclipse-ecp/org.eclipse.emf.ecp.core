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
package org.eclipse.emf.ecp.view.validation.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.internal.validation.bean.Activator;
import org.eclipse.emf.ecp.view.spi.validation.ValidationProvider;
import org.osgi.framework.Bundle;

public abstract class BeanValidationProvider implements ValidationProvider {

	private ValidatorContext validatorContext;

	public BeanValidationProvider() {
		this(new InputStream[0]);
	}

	public BeanValidationProvider(InputStream... inputStreams) {
		instantiateValidator(inputStreams);
	}

	private void instantiateValidator(InputStream[] inputStreams) {
		Configuration<?> configure = Validation.byDefaultProvider().configure();
		final List<InputStream> allInputStreams = new ArrayList<InputStream>(
			Arrays.asList(inputStreams));
		allInputStreams.addAll(readExtensionPoints());
		for (final InputStream is : allInputStreams) {
			configure = configure.addMapping(is);
		}
		final ValidatorFactory validatorFactory = configure.buildValidatorFactory();
		final MessageInterpolator interpolator = getMessageInterpolator(validatorFactory
			.getMessageInterpolator());
		validatorContext = validatorFactory.usingContext().messageInterpolator(
			interpolator);
		for (final InputStream is : allInputStreams) {
			try {
				is.close();
			} catch (final IOException e) {
				Activator.log(e);
			}
		}
	}

	private Collection<InputStream> readExtensionPoints() {
		final IExtensionRegistry extensionRegistry = Platform
			.getExtensionRegistry();
		if (extensionRegistry == null) {
			return Collections.emptyList();
		}
		final List<InputStream> inputStreams = new ArrayList<InputStream>();
		final IConfigurationElement[] controls = extensionRegistry
			.getConfigurationElementsFor("org.eclipse.emf.ecp.view.validation.bean.violations"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				final String xmlViolationPath = e.getAttribute("xml"); //$NON-NLS-1$
				final Bundle bundle = Platform.getBundle(e.getContributor()
					.getName());
				inputStreams.add(bundle.getResource(xmlViolationPath)
					.openStream());
			} catch (final IOException e1) {
				Activator.log(e1);
			}
		}
		return inputStreams;
	}

	@Override
	public List<Diagnostic> validate(EObject eObject) {
		final Validator validator = validatorContext.getValidator();
		final List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
		final Set<ConstraintViolation<EObject>> validationResult = validator
			.validate(eObject);
		for (final ConstraintViolation<EObject> violation : validationResult) {
			Node lastNode = null;
			final Iterator<Node> pathIterator = violation.getPropertyPath()
				.iterator();
			while (pathIterator.hasNext()) {
				lastNode = pathIterator.next();
			}
			final EObject leafBean = (EObject) violation.getLeafBean();
			final EStructuralFeature eStructuralFeature = leafBean.eClass()
				.getEStructuralFeature(lastNode.getName());
			final int severity = getSeverity(violation);
			final BasicDiagnostic diagnostic = new BasicDiagnostic(severity,
				"Bean Validation", 0, violation.getMessage(), new Object[] { //$NON-NLS-1$
					violation.getLeafBean(), eStructuralFeature });
			diagnostics.add(diagnostic);
		}
		return diagnostics;
	}

	protected abstract MessageInterpolator getMessageInterpolator(
		MessageInterpolator messageInterpolator);

	protected abstract int getSeverity(ConstraintViolation<EObject> violation);

}
