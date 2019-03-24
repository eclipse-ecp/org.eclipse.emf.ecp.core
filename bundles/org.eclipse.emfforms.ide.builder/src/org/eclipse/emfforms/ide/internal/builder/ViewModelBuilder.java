/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emfforms.bazaar.Bazaar.Builder;
import org.eclipse.emfforms.ide.builder.ValidationDelegate;

/**
 * Incremental builder that triggers validation on view models in the workspace.
 */
public class ViewModelBuilder extends ValidationBuilder {

	/** identifier of the builder, similar to plugin.xml value. */
	public static final String BUILDER_ID = "org.eclipse.emfforms.ide.builder.viewModelBuilder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
		throws CoreException {

		// If the validation nature is installed, it covers me
		if (ValidationNature.PROTOTYPE.hasNature(getProject())) {
			return null;
		}

		return super.build(kind, args, monitor);
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// If the validation nature is installed, it covers me
		if (ValidationNature.PROTOTYPE.hasNature(getProject())) {
			return;
		}

		super.clean(monitor);
	}

	@Override
	protected Builder<ValidationDelegate> configureValidation(Builder<ValidationDelegate> bazaarBuilder) {
		// Only the view model validation
		bazaarBuilder.add(new ViewModelValidationDelegate.Provider());
		return bazaarBuilder;
	}

	@Override
	protected Builder<MarkerHelper> configureMarkers(Builder<MarkerHelper> bazaarBuilder) {
		// Only the view model markers
		bazaarBuilder.add(new ViewModelMarkerHelper.Provider());
		return bazaarBuilder;
	}

}
