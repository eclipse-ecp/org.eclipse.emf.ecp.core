/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecp.view.internal.validation.ECPSubstitutionLabelProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.internal.validation.ValidationServiceImpl;
import org.eclipse.emfforms.common.spi.validation.ValidationService;
import org.eclipse.emfforms.common.spi.validation.exception.ValidationCanceledException;
import org.eclipse.emfforms.ide.internal.builder.Activator;

/**
 * Implementation of a validation delegate that uses the {@link ValidationService}
 * to validate a resource. This class may be used as is or it may be extended by
 * clients to customize any of
 * <ul>
 * <li>{@link #loadModel(IFile) loading the model file}</li>
 * <li>{@link #getModel(ResourceSet) finding the model object} to validate</li>
 * <li>{@link #configure(ValidationService, ResourceSet, EObject) configuring the validation service}
 * for the model's peculiar needs</li>
 * <li>{@link #unload(ResourceSet) unloading the model} to clean up any additional resources</li>
 * </ul>
 */
@SuppressWarnings("restriction")
public class ValidationServiceDelegate implements ValidationDelegate {

	/**
	 * Initializes me.
	 */
	public ValidationServiceDelegate() {
		super();
	}

	@Override
	public Optional<Diagnostic> validate(IFile file, IProgressMonitor monitor) {
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		ResourceSet rset = null;

		try {
			// load file thanks to ECP helpers to avoid missing Properties
			rset = loadModel(file);
			final EObject model = getModel(rset);
			if (model != null) {
				// trigger validation
				final ValidationService service = new ValidationServiceImpl();
				final SubstitutionLabelProvider labelProvider = new ECPSubstitutionLabelProvider(adapterFactory);
				service.setSubstitutionLabelProvider(labelProvider);
				configure(service, rset, model);

				final Diagnostic diagnostic = service.validate(model);
				final Set<Diagnostic> subDiagnostics = service.validate(model.eAllContents());

				// create the final result, mainly extract the most relevant diagnostic among all of them
				final Diagnostician diagnostician = new Diagnostician();
				final BasicDiagnostic diagnostics = diagnostician.createDefaultDiagnostic(model);
				if (diagnostic.getSeverity() >= Diagnostic.WARNING) {
					diagnostics.add(diagnostic);
				}
				for (final Diagnostic sub : subDiagnostics) {
					if (sub.getSeverity() >= Diagnostic.WARNING) {
						diagnostics.add(sub);
					}
				}

				return Optional.<Diagnostic> of(diagnostics);
			}
		} catch (final ClassCastException e) {
			// can occur during validation
			return Optional.of(toDiagnostic(e));
		} catch (final IOException ex) {
			return Optional.of(toDiagnostic(ex));
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			// suppressing checkstyle warning to catch the runtime exception thrown when failing to load a resource
			// END SUPRESS CATCH EXCEPTION
			return Optional.of(toDiagnostic(e));
		} catch (final ValidationCanceledException ex) {
			// no diagnostic to return in this case
		} finally {
			adapterFactory.dispose();
			if (rset != null) {
				unload(rset);
			}
		}

		return Optional.empty();
	}

	/**
	 * Load a model into a resource set.
	 *
	 * @param file the model file to load
	 * @return the resource set into which it is loaded
	 * @throws IOException on failure to load the {@code file}
	 */
	protected ResourceSet loadModel(IFile file) throws IOException {
		final ResourceSet result = new ResourceSetImpl();

		result.getResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true), true);

		return result;
	}

	/**
	 * Obtain the model object to be validated from the loaded resource set.
	 *
	 * @param resourceSet the loaded resource set
	 * @return the model object to validate, or {@code null} if there is none
	 *
	 * @see #loadModel(IFile)
	 */
	protected EObject getModel(ResourceSet resourceSet) {
		EObject result = null;

		final Resource resource = resourceSet.getResources().isEmpty() ? null : resourceSet.getResources().get(0);
		if (resource != null && !resource.getContents().isEmpty()) {
			result = resource.getContents().get(0);
		}

		return result;
	}

	/**
	 * Configure the validation service with constraint providers, filters, or whatever else
	 * is necessary for complete and correct validation of the {@code model}.
	 *
	 * @param validationService the validation service to configure
	 * @param resourceSet the resource set in which it will validate the {@code model}
	 * @param model the model object to be validated
	 */
	protected void configure(ValidationService validationService, ResourceSet resourceSet, EObject model) {
		// Nothing to do
	}

	/**
	 * Unload the model after validation is complete.
	 *
	 * @param resourceSet the model to unload
	 *
	 * @see #loadModel(IFile)
	 */
	protected void unload(ResourceSet resourceSet) {
		for (final Resource next : resourceSet.getResources()) {
			next.unload();
			next.eAdapters().clear();
		}

		resourceSet.getResources().clear();
		resourceSet.eAdapters().clear();
	}

	private static Diagnostic toDiagnostic(Throwable throwable) {
		String message = throwable.getClass().getName();
		final int index = message.lastIndexOf('.');
		if (index >= 0) {
			message = message.substring(index + 1);
		}
		if (throwable.getLocalizedMessage() != null) {
			message = message + ": " + throwable.getLocalizedMessage();//$NON-NLS-1$
		}

		final BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
			Activator.PLUGIN_ID,
			0,
			message,
			new Object[] { throwable });

		if (throwable.getCause() != null && throwable.getCause() != throwable) {
			throwable = throwable.getCause();
			basicDiagnostic.add(toDiagnostic(throwable));
		}

		return basicDiagnostic;
	}

}
