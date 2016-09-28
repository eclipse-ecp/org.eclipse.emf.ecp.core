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
package org.eclipse.emfforms.internal.editor.genmodel;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.editor.genmodel.service.GenModelGroupExpansionViewModelService;
import org.eclipse.emfforms.internal.editor.genmodel.service.GenModelReadonlyViewModelService;
import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeMasterDetailCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ViewModelServiceProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

/**
 * GenModel Editor-specific customization for the {@link org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite TreeMasterDetailComposite}.
 *
 * @author Martin Fleck
 */
@SuppressWarnings("restriction")
public class GenModelEditorTMDCustomization extends DefaultTreeMasterDetailCustomization {

	/**
	 * Constructs a new {@link GenModelEditorTMDCustomization}.
	 *
	 * @param createElementCallback the {@link CreateElementCallback}
	 * @param notifier The editor input that is being customized
	 */
	public GenModelEditorTMDCustomization(final CreateElementCallback createElementCallback, Notifier notifier) {
		setViewModelServices(new ViewModelServiceProvider() {
			@Override
			public ViewModelService[] getViewModelServices(VView view, EObject eObject) {
				return new ViewModelService[] { new GenModelGroupExpansionViewModelService(),
					new GenModelReadonlyViewModelService() };
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeViewerCustomization#getMenu(org.eclipse.jface.viewers.TreeViewer,
	 *      org.eclipse.emf.edit.domain.EditingDomain)
	 */
	@Override
	public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
		// menu not necessary: no cut/copy/paste-action, no EObject-specific actions
		return null;
	}
}
