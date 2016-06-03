/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * This ViewModelService listens on the {@link ViewModelContext} and statically saves the collapsed statuses for certain
 * groups used in the view models of the ecore editor.
 *
 * @author Lucas Koehler
 *
 */
public class GroupExpansionViewModelService implements ViewModelService {

	private static final String STANDARD = "Standard";
	private static final String ADVANCED = "Advanced";
	private static final String ADVANCED_ANNOTATION = "Advanced (Use With Caution)";

	private static boolean isStandardGroupCollapsed;
	private static boolean isAdvancedGroupCollapsed = true;
	private static boolean isAdvancedAnnotationGroupCollapsed = true;

	private ModelChangeListener modelChangeListener;
	private ViewModelContext viewModelContext;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		viewModelContext = context;

		final VElement viewModel = context.getViewModel();
		VView view = null;
		if (viewModel instanceof VView) {
			view = (VView) viewModel;
		} else {
			// If the view model is no VView this service does nothing
			return;
		}

		// Set the groups' collapsed states to the statically saved values
		for (final VContainedElement element : view.getChildren()) {
			if (element instanceof VGroup) {
				final VGroup group = (VGroup) element;
				if (STANDARD.equals(group.getName())) {
					group.setCollapsed(isStandardGroupCollapsed);
				} else if (ADVANCED.equals(group.getName())) {
					group.setCollapsed(isAdvancedGroupCollapsed);
				} else if (ADVANCED_ANNOTATION.equals(group.getName())) {
					group.setCollapsed(isAdvancedAnnotationGroupCollapsed);
				}
			}
		}

		// Define a model change listener that updates the statically saved collapsed statuses of the groups.
		modelChangeListener = new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getNotifier() instanceof VGroup) {
					final VGroup group = (VGroup) notification.getNotifier();
					if (STANDARD.equals(group.getName())) {
						isStandardGroupCollapsed = group.isCollapsed();
					} else if (ADVANCED.equals(group.getName())) {
						isAdvancedGroupCollapsed = group.isCollapsed();
					} else if (ADVANCED_ANNOTATION.equals(group.getName())) {
						isAdvancedAnnotationGroupCollapsed = group.isCollapsed();
					}
				}
			}
		};

		context.registerViewChangeListener(modelChangeListener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		viewModelContext.unregisterViewChangeListener(modelChangeListener);

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
