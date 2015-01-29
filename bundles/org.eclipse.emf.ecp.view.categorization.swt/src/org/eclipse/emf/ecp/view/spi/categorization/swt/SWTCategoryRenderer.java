/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;

/**
 * Renderer for {@link VCategory VCategories}.
 *
 * @author Eugen Neufeld
 *
 */
public class SWTCategoryRenderer extends ContainerSWTRenderer<VCategory> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SWTCategoryRenderer(VCategory vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		return "org_eclipse_emf_ecp_view_categorization_category"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */
	@Override
	protected Collection<VContainedElement> getChildren() {
		final VContainedElement element = getVElement().getComposite();
		if (element == null) {
			return Collections.emptySet();
		}
		return Collections.singleton(element);
	}

	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(int, org.eclipse.swt.widgets.Composite,
	// * org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	// */
	// @Override
	// protected Control renderControl(SWTGridCell gridCell, Composite parent)
	// throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
	// final Composite categoryComposite = new Composite(parent, SWT.NONE);
	// categoryComposite.setBackground(parent.getBackground());
	//
	// final AbstractSWTRenderer<VElement> renderer = getSWTRendererFactory().getRenderer(
	// getVElement().getComposite(),
	// getViewModelContext());
	// if (renderer == null) {
	// Activator
	// .getDefault()
	// .getLog()
	// .log(
	// new Status(IStatus.INFO, Activator.PLUGIN_ID, String.format(
	//						"No Renderer for %s found.", getVElement().getComposite().eClass().getName()))); //$NON-NLS-1$
	// return categoryComposite;
	// }
	// final SWTGridDescription gridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
	// .createEmptyGridDescription());
	// categoryComposite.setLayout(getLayoutHelper().getColumnLayout(
	// gridDescription.getColumns(), false));
	//
	// for (final SWTGridCell childGridCell : gridDescription.getGrid()) {
	// try {
	// final Control control = renderer.render(
	// childGridCell,
	// categoryComposite);
	// // TODO who should apply the layout
	// setLayoutDataForControl(childGridCell, gridDescription, gridDescription.getColumns(),
	// new LinkedHashSet<GridCellDescription>(),
	// new LinkedHashSet<GridCellDescription>(),
	// control);
	// // resultRows = SWTRendererFactory.INSTANCE.render(columnComposite, child, viewContext);
	// } catch (final NoPropertyDescriptorFoundExeption ex) {
	// Activator.getDefault().getLog()
	// .log(new Status(IStatus.INFO, Activator.PLUGIN_ID, ex.getMessage(), ex));
	// continue;
	// }
	// }
	// renderer.finalizeRendering(categoryComposite);
	//
	// return categoryComposite;
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#getGridDescription(SWTGridDescription)
	// */
	// @Override
	// public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
	// if (rendererGridDescription == null) {
	// rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
	// }
	// return rendererGridDescription;
	// }
}
