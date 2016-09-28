/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Martin Fleck - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupPackage;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * This {@link ViewModelService} tracks the collapsed state of the groups used in {@link ViewModelContext#getViewModel()
 * view model} of the {@link org.eclipse.emfforms.internal.editor.genmodel.GenModelEditor GenModelEditor}. The group state is stored statically so it is the same across all input
 * models.
 *
 * @author Martin Fleck
 *
 */
public class GenModelGroupExpansionViewModelService implements ViewModelService {

	// known group names of the Ecore visible through the GenModel editor
	private static final String ECORE_STANDARD = "Standard";
	private static final String ECORE_ADVANCED = "Advanced";

	// known group names of the GenModel visible through the GenModel editor
	private static final String GENMODEL_ALL = "All";
	private static final String GENMODEL_EDIT = "Edit";
	private static final String GENMODEL_EDITOR = "Editor";
	private static final String GENMODEL_MODEL = "Model";
	private static final String GENMODEL_MODEL_CLASS_DEFAULTS = "Model Class Defaults";
	private static final String GENMODEL_MODEL_FEATURE_DEFAULTS = "Model Feature Defaults";
	private static final String GENMODEL_PACKAGE_SUFFIXES = "Package Suffixes";
	private static final String GENMODEL_TEMPLATES_MERGE = "Templates & Merge";
	private static final String GENMODEL_TESTS = "Tests";

	/**
	 * Map storing the collapsed group states by group name.
	 */
	private static final Map<String, Boolean> GROUP_COLLAPSED_STATES = new HashMap<String, Boolean>();
	static {
		// initialize default collapsed states
		GROUP_COLLAPSED_STATES.put(ECORE_STANDARD, Boolean.FALSE);
		GROUP_COLLAPSED_STATES.put(ECORE_ADVANCED, Boolean.FALSE);

		GROUP_COLLAPSED_STATES.put(GENMODEL_ALL, Boolean.FALSE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_EDIT, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_EDITOR, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_MODEL, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_MODEL_CLASS_DEFAULTS, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_MODEL_FEATURE_DEFAULTS, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_PACKAGE_SUFFIXES, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_TEMPLATES_MERGE, Boolean.TRUE);
		GROUP_COLLAPSED_STATES.put(GENMODEL_TESTS, Boolean.TRUE);
	}

	/**
	 * Context.
	 */
	private ViewModelContext viewModelContext;

	/**
	 * Listener updating the stored group states.
	 */
	private final ModelChangeListener collapsedStateListener = new ModelChangeListener() {
		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (notification.getNotifier() instanceof VGroup
				&& notification.getStructuralFeature().equals(VGroupPackage.Literals.GROUP__COLLAPSED)) {
				// update collapsed state
				final VGroup group = (VGroup) notification.getNotifier();
				GROUP_COLLAPSED_STATES.put(group.getName(), group.isCollapsed());
			}
		}
	};

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		final VElement viewModel = context.getViewModel();
		if (!(viewModel instanceof VView)) {
			return; // this service only works on views
		}

		final VView view = (VView) viewModel;
		viewModelContext = context;

		// Set the groups' collapsed states to the stored states (default or set by the user)
		for (final VContainedElement element : view.getChildren()) {
			if (element instanceof VGroup) {
				final VGroup group = (VGroup) element;
				final Boolean groupCollapsedState = GROUP_COLLAPSED_STATES.get(group.getName());
				if (groupCollapsedState != null) {
					group.setCollapsed(groupCollapsedState);
				}
			}
		}

		// register listener
		context.registerViewChangeListener(collapsedStateListener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		if (viewModelContext != null) {
			viewModelContext.unregisterViewChangeListener(collapsedStateListener);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}
}
