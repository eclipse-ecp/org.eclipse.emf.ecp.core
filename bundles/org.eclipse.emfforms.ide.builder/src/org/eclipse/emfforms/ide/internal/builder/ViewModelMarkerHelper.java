/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.ide.builder.MarkerHelperProvider;
import org.osgi.service.component.annotations.Component;

/**
 * Specific Marker Helper that directly knows the file.
 */
public class ViewModelMarkerHelper extends MarkerHelper {
	private final IFile file;

	/** identifier of the marker, similar to plugin.xml value. */
	public static final String MARKER_ID = "org.eclipse.emfforms.ide.builder.ViewModelProblem"; //$NON-NLS-1$

	@Override
	protected String getMarkerID() {
		return MARKER_ID;
	}

	/**
	 * Constructor.
	 *
	 * @param file file being validated
	 */
	ViewModelMarkerHelper(IFile file) {
		this.file = file;
	}

	@Override
	public void createMarkers(final Diagnostic diagnostic) throws CoreException {
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				final int severity = diagnostic.getSeverity();
				if (severity < Diagnostic.WARNING) {
					// neither info nor ok markers
					return;
				}
				// no child and severity ok, directly create marker.
				if (diagnostic.getChildren().isEmpty()) {
					createMarkers(getFile(diagnostic), diagnostic, null);
				} else {
					for (final Diagnostic child : diagnostic.getChildren()) {
						// retrieve the most relevant child to get the right message: most relevant is a child
						// diagnostic with the ECP diagnostic specific source and the right severity
						final Diagnostic relevant = extractRelevantDiagnostic(child);
						createMarkers(getFile(diagnostic), relevant, diagnostic);
					}
				}

			}
		},
			null);
	}

	private Diagnostic extractRelevantDiagnostic(Diagnostic parentDiagnostic) {
		if (parentDiagnostic.getSeverity() == 0 || parentDiagnostic.getChildren().isEmpty()) {
			return parentDiagnostic;
		}

		// try to get the most relevant => the one with highest severity, and the ECP validation source among the
		// children with highest severity
		Diagnostic result = parentDiagnostic.getChildren().get(0);
		int currentHighest = result.getSeverity();
		for (final Diagnostic child : parentDiagnostic.getChildren()) {
			if (child.getSeverity() > currentHighest) {
				currentHighest = child.getSeverity();
				result = child;
			} else if (child.getSeverity() == currentHighest
				&& !ViewValidator.DIAGNOSTIC_SOURCE.equals(result.getSource())
				&& ViewValidator.DIAGNOSTIC_SOURCE.equals(child.getSource())) {
				currentHighest = child.getSeverity();
				result = child;
			}
		}
		return result;
	}

	@Override
	protected IFile getFile(Object datum) {
		if (file != null) {
			return file;
		}
		return super.getFile(datum);
	}

	//
	// Nested types
	//

	/**
	 * Implementation of the marker helper provider for view model validation.
	 */
	@Component
	public static class Provider implements MarkerHelperProvider {

		/** Standard bid (for view model files). */
		private static final Double BID = 10.0;

		/** View model file extension. */
		private static final String VIEW = "view"; //$NON-NLS-1$

		/**
		 * Bid on view model files.
		 *
		 * @param file a file
		 * @return a bid, if it is a view model file
		 */
		@Bid
		public Double bid(IFile file) {
			return isViewModelResource(file) ? BID : null;
		}

		/**
		 * Create the view model marker helper.
		 * 
		 * @param file a view model file
		 * @return its marker helper
		 */
		@Create
		public MarkerHelper createMarkerHelper(IFile file) {
			return new ViewModelMarkerHelper(file);
		}

		private static boolean isViewModelResource(IFile resource) {
			return VIEW.equals(resource.getFileExtension());
		}

	}

}
