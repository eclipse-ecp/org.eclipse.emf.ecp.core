/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ide.spi.util.EcoreHelper;
import org.eclipse.emf.ecp.ide.spi.util.ViewModelHelper;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.model.util.ViewValidator;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.spi.validation.ValidationService;
import org.eclipse.emfforms.common.spi.validation.filter.AbstractComplexFilter;
import org.eclipse.emfforms.ide.builder.ValidationDelegate;
import org.eclipse.emfforms.ide.builder.ValidationDelegateProvider;
import org.eclipse.emfforms.ide.builder.ValidationServiceDelegate;
import org.osgi.service.component.annotations.Component;

/**
 * The validation delegate for view models.
 */
public class ViewModelValidationDelegate extends ValidationServiceDelegate {

	/**
	 * Initializes me.
	 */
	public ViewModelValidationDelegate() {
		super();
	}

	@Override
	protected ResourceSet loadModel(IFile file) throws IOException {
		ResourceSet result = null;

		final LinkedHashSet<String> ecores = new LinkedHashSet<String>();

		// load file thanks to ECP helpers to avoid missing Properties
		final VView view = ViewModelHelper.loadView(file, ecores);
		final ViewAdapter adapter = new ViewAdapter(view, ecores);

		if (view == null) {
			adapter.dispose();
		} else {
			view.eAdapters().add(adapter);

			result = view.eResource().getResourceSet();
			result.eAdapters().add(adapter);
		}

		return result;
	}

	@Override
	protected EObject getModel(ResourceSet resourceSet) {
		return ViewAdapter.getView(resourceSet);
	}

	@Override
	protected void configure(ValidationService validationService, ResourceSet resourceSet, EObject model) {
		super.configure(validationService, resourceSet, model);

		final VView view = (VView) model;

		final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(view);
		view.setLoadingProperties(properties);

		validationService.registerValidationFilter(new ViewValidatorDuplicateFilter());
	}

	/**
	 * Filter to avoid duplications in validation from {@link ViewValidator}.
	 */
	private class ViewValidatorDuplicateFilter extends AbstractComplexFilter {
		@Override
		public boolean skipSubtree(EObject eObject, Optional<Diagnostic> diagnostic) {
			return false;
		}

		@Override
		public boolean skipValidation(EObject eObject) {
			return VDomainModelReference.class.isInstance(eObject);
		}

	}

	//
	// Nested types
	//

	/**
	 * Implementation of the validation delegate provider for view model validation.
	 */
	@Component
	public static class Provider implements ValidationDelegateProvider {

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
		 * Create the view model validation delegate.
		 *
		 * @return the view model validation delegate
		 */
		@Create
		public ValidationDelegate createValidationDelegate() {
			return new ViewModelValidationDelegate();
		}

		private static boolean isViewModelResource(IFile resource) {
			return VIEW.equals(resource.getFileExtension());
		}

	}

	/**
	 * An adapter that just attaches the list of registered Ecore paths
	 * to the model to find after validation for deregistration.
	 */
	private static final class ViewAdapter extends AdapterImpl {
		private final Set<String> ecores;
		private final VView view;

		ViewAdapter(VView view, Set<String> ecores) {
			super();

			this.view = view;
			this.ecores = ecores;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == VView.class || type == ViewAdapter.class;
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			super.unsetTarget(oldTarget);

			dispose();
		}

		VView getView() {
			return view;
		}

		void dispose() {
			for (final String registeredEcore : ecores) {
				EcoreHelper.unregisterEcore(registeredEcore);
			}
			ecores.clear();
		}

		static VView getView(ResourceSet resourceSet) {
			final ViewAdapter adapter = (ViewAdapter) EcoreUtil.getExistingAdapter(resourceSet, ViewAdapter.class);
			return adapter == null ? null : adapter.getView();
		}

	}

}
