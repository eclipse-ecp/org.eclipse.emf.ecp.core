/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.quickfix.ui.e4.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.quickfix.ModelQuickFix;
import org.eclipse.emf.ecp.quickfix.ModelQuickFixRegistry;

/** Context Menu entries for Model Quick Fixes. */
public class ModelQuickFixContextMenuContribution {
	@Inject
	private ModelQuickFixRegistry registry;

	/**
	 * @see org.eclipse.e4.ui.di.AboutToShow
	 * @param items - the list of dynamically shown menu elements
	 * @param diagnostic - the selected diagnostic
	 * @param modelService - the e4 EModelService
	 */
	@AboutToShow
	public void aboutToShow(List<MMenuElement> items,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Diagnostic diagnostic, EModelService modelService) {

		final List<ModelQuickFix> fixes = getModelQuickFixes(diagnostic);
		if (fixes == null) {
			return;
		}
		for (final ModelQuickFix fix : fixes) {
			final List<?> data = diagnostic.getData();
			if (data != null && data.size() > 0) {
				final Object object = data.get(0);
				if (!EObject.class.isInstance(object)) {
					return;
				}
				final MDirectMenuItem menuItem = modelService.createModelElement(MDirectMenuItem.class);
				menuItem.setLabel(fix.getLabel(diagnostic));
				menuItem.setObject(new ModelQuickFixContextMenuItemHandler(fix, (EObject) object));
				items.add(menuItem);
			}
		}
	}

	private List<ModelQuickFix> getModelQuickFixes(Diagnostic diagnostic) {
		if (registry == null) {
			return null;
		}
		return registry.getApplicableModelQuickFixes(diagnostic);
	}
}