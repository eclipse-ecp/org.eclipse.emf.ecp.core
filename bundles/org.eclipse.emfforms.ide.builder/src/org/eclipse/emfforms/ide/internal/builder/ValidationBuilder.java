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
 * Christian W. Damus - bugs 544499, 545418
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.ide.builder.BuilderConstants;
import org.eclipse.emfforms.ide.builder.MarkerHelperProvider;
import org.eclipse.emfforms.ide.builder.ValidationDelegate;
import org.eclipse.emfforms.ide.builder.ValidationDelegateProvider;

/**
 * Incremental builder that triggers validation on view models in the workspace.
 */
public class ValidationBuilder extends IncrementalProjectBuilder {

	/** identifier of the builder, similar to plugin.xml value. */
	public static final String BUILDER_ID = "org.eclipse.emfforms.ide.builder.validationBuilder"; //$NON-NLS-1$

	/** identifier of the marker, similar to plugin.xml value. */
	public static final String MARKER_ID = "org.eclipse.emfforms.ide.builder.ValidationProblem"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public ValidationBuilder() {
		super();
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
		throws CoreException {

		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			final IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	/**
	 * Create the core bazaar builder for a bazaar that has a default vendor.
	 * The default vendor should lose all auctions in which it is not the only bidder.
	 *
	 * @param <T> the product type of the bazaar
	 * @param defaultVendor the default vendor of the bazaar's product type
	 * @return the bazaar builder
	 */
	protected <T> Bazaar.Builder<T> createBazaarBuilder(Vendor<? extends T> defaultVendor) {
		return Bazaar.Builder.<T> empty().add(defaultVendor);
	}

	/**
	 * Configure the validation delegate bazaar builder. Subclasses may extend or override
	 * to add vendors or context functions.
	 *
	 * @param bazaarBuilder the validation delegate bazaar builder
	 * @return the same {@code bazaarBuilder}
	 */
	protected Bazaar.Builder<ValidationDelegate> configureValidation(Bazaar.Builder<ValidationDelegate> bazaarBuilder) {
		bazaarBuilder.addAll(Activator.getDefault().getValidationDelegateProviders());
		return bazaarBuilder;
	}

	/**
	 * Configure the marker helper bazaar builder. Subclasses may extend or override
	 * to add vendors or context functions.
	 *
	 * @param bazaarBuilder the marker helper bazaar builder
	 * @return the same {@code bazaarBuilder}
	 */
	protected Bazaar.Builder<MarkerHelper> configureMarkers(Bazaar.Builder<MarkerHelper> bazaarBuilder) {
		bazaarBuilder.addAll(Activator.getDefault().getMarkerHelperProviders());
		return bazaarBuilder;
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// delete markers set and files created
		getProject().deleteMarkers(MARKER_ID, true, IResource.DEPTH_INFINITE);
	}

	/**
	 * Runs a full build on the project.
	 *
	 * @param monitor the progress monitor to track the build
	 * @throws CoreException exception in case of issues
	 */
	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		try {
			getProject().accept(new ResourceValidationVisitor(monitor));
		} catch (final CoreException e) {
			Activator.log("Error running full build", e);//$NON-NLS-1$
		}
	}

	/**
	 * Runs an incremental build on the project.
	 *
	 * @param delta the delta on the resource that triggers this incremental build
	 * @param monitor the progress monitor to track the build
	 * @throws CoreException exception in case of issues
	 */
	protected void incrementalBuild(IResourceDelta delta,
		IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new DeltaValidationVisitor(monitor));
	}

	/**
	 * Create the bazaar vendor injection context for a {@code file}.
	 *
	 * @param file a file to be validated
	 * @return the injection context
	 */
	protected BazaarContext createContext(IFile file) {
		final BazaarContext.Builder result = BazaarContext.Builder.empty();
		result.put(IFile.class, file);

		// We have to compute this a priori because if we try to do it on demand
		// via a context function, then for any file that does not have a content
		// type, we will end up injecting nulls instead of failing to resolve the
		// injection, which is what we would prefer. Returning IInjector.NOT_A_VALUE
		// from a context function always ultimately results in a null anyways
		final IContentType contentType = getContentType(file);
		if (contentType != null) {
			result.put(IContentType.class, contentType);
			result.put(BuilderConstants.CONTENT_TYPE, contentType.getId());
		}

		return result.build();
	}

	/**
	 * Get the content-type of a {@code file}.
	 *
	 * @param file a file
	 * @return the content type, or {@code null} if it cannot be determined for some reason
	 */
	protected IContentType getContentType(IFile file) {
		IContentType result;
		try {
			final IContentDescription contentDescription = file.getContentDescription();
			result = contentDescription == null ? null : contentDescription.getContentType();
		} catch (final CoreException e) {
			// Unavailable or out of sync? Not interesting to validate it
			result = null;
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * Resource visitor for full-build validation.
	 */
	abstract class ValidationVisitor {

		private final IProgressMonitor monitor;
		private final Bazaar<ValidationDelegate> delegateBazaar;
		private final Bazaar<MarkerHelper> markerHelperBazaar;

		/**
		 * Constructor.
		 *
		 * @param monitor the progress monitor
		 */
		ValidationVisitor(IProgressMonitor monitor) {
			this.monitor = monitor;

			delegateBazaar = configureValidation(createBazaarBuilder(ValidationDelegateProvider.NULL))
				.build();
			markerHelperBazaar = configureMarkers(createBazaarBuilder(MarkerHelperProvider.DEFAULT))
				.build();
		}

		/**
		 * Validate a {@code file}.
		 *
		 * @param file the file validate
		 * @param context the bazaar context for injection to find a validation delegate
		 * @param monitor the progress monitor
		 */
		void validate(IFile file, BazaarContext context, IProgressMonitor monitor) {
			// We will always at least get the null instance
			final ValidationDelegate delegate = delegateBazaar.createProduct(context);

			final SubMonitor sub = SubMonitor.convert(monitor, 1);
			try {
				final Optional<Diagnostic> diagnostics = delegate.validate(file, sub.newChild(1));

				// If there isn't an OK diagnostic, then nothing was validated, so don't
				// clear existing markers
				if (diagnostics.isPresent()) {
					final MarkerHelper markerHelper = markerHelperBazaar.createProduct(context);
					final Diagnostic diagnostic = diagnostics.get();
					markerHelper.deleteMarkers(file);

					// create markers only if severity >= Warning
					if (diagnostic.getSeverity() >= IMarker.SEVERITY_WARNING) {
						markerHelper.createMarkers(diagnostic);
					}
				}
			} catch (final CoreException ex) {
				Activator.log("Errors while creating markers on file " + file, ex);//$NON-NLS-1$
			} finally {
				sub.done();
			}
		}

		/**
		 * Get the progress monitor.
		 *
		 * @return the progress monitor
		 */
		final IProgressMonitor getMonitor() {
			return monitor;
		}

	}

	/**
	 * Resource delta visitor for incremental-build validation.
	 */
	class DeltaValidationVisitor extends ValidationVisitor implements IResourceDeltaVisitor {

		/**
		 * Constructor.
		 *
		 * @param monitor the progress monitor
		 */
		DeltaValidationVisitor(IProgressMonitor monitor) {
			super(monitor);
		}

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.CHANGED:
				// handle added or changed resource
				final IResource resource = delta.getResource();
				switch (resource.getType()) {
				case IResource.FILE:
					final IFile file = (IFile) resource;
					final BazaarContext context = createContext(file);
					validate(file, context, getMonitor());
					break;
				default:
					// Nothing to do for other kinds of resource
				}
				break;
			case IResourceDelta.REMOVED:
				// handle removed resource
				break;
			default:
				break;
			}
			// return true to continue visiting children.
			return true;
		}
	}

	/**
	 * Resource visitor for full-build validation.
	 */
	class ResourceValidationVisitor extends ValidationVisitor implements IResourceVisitor {

		/**
		 * Constructor.
		 *
		 * @param monitor the progress monitor
		 */
		ResourceValidationVisitor(IProgressMonitor monitor) {
			super(monitor);
		}

		@Override
		public boolean visit(IResource resource) {
			boolean result;

			switch (resource.getType()) {
			case IResource.FILE:
				final IFile file = (IFile) resource;
				final BazaarContext context = createContext(file);
				validate(file, context, getMonitor());
				result = false; // No children, anyways
				break;
			default:
				// return true to continue visiting children.
				result = true;
				break;
			}

			return result;
		}
	}

}
