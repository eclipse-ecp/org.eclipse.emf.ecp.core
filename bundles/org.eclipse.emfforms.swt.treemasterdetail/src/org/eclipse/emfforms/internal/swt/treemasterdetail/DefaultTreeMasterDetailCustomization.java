/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Clemens Elflein - initial implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultDetailCompositeBuilder;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultTreeWidthProvider;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultViewModelServiceProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DetailCompositeBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeWidthProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ViewModelServiceProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * Default implementation of the {@link TreeMasterDetailSWTCustomization}.
 *
 * @author Johannes Faltermeier
 *
 */
public class DefaultTreeMasterDetailCustomization extends DefaultTreeViewerCustomization
	implements TreeMasterDetailSWTCustomization {

	private DetailCompositeBuilder detailComposite;
	private TreeWidthProvider width;
	private ViewModelServiceProvider viewServiceProvider;

	/**
	 * Default constructor.
	 */
	public DefaultTreeMasterDetailCustomization() {
		super();
		width = new DefaultTreeWidthProvider();
		detailComposite = new DefaultDetailCompositeBuilder();
		viewServiceProvider = new DefaultViewModelServiceProvider();
	}

	@Override
	public int getInitialTreeWidth() {
		return width.getInitialTreeWidth();
	}

	@Override
	public Composite createDetailComposite(Composite parent) {
		return detailComposite.createDetailComposite(parent);
	}

	@Override
	public ViewModelService[] getViewModelServices(VView view, EObject eObject) {
		return viewServiceProvider.getViewModelServices(view, eObject);
	}

	/**
	 * Sets the detail composite builder.
	 *
	 * @param detailComposite the builder
	 */
	public void setDetailComposite(DetailCompositeBuilder detailComposite) {
		this.detailComposite = detailComposite;
	}

	/**
	 * Sets the tree width provider.
	 *
	 * @param width the provider
	 */
	public void setInitialTreeWidth(TreeWidthProvider width) {
		this.width = width;
	}

	/**
	 * Sets the view model services provider.
	 *
	 * @param viewServiceProvider the provider
	 */
	public void setViewModelServices(ViewModelServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

}
