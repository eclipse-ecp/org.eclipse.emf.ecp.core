/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.model.fx.ECPFXView;
import org.eclipse.emf.ecp.view.model.fx.ECPFXViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

/**
 * @author Jonas
 *
 */
@SuppressWarnings("restriction")
// TODO no api
public class ECPFXViewRendererImpl implements ECPFXViewRenderer {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.ui.view.fx.ECPFXViewRenderer#render(org.eclipse.emf.ecp.view.model.VView,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ECPFXView render(VView view, EObject domainObject, ViewModelService... services) { // throws
		// ECPRendererException

		final ViewModelContext viewModelContext = new ViewModelContextImpl(view,
			domainObject, services);

		final RendererFX<VElement> viewRenderer = RendererFactory.INSTANCE.getRenderer(view, viewModelContext);
		if (viewRenderer != null) {
			Node result;
			try {
				// TODO: checkGrid
				result = viewRenderer.render(viewRenderer.getGridDescription().getGrid().get(0));
			} catch (final NoRendererFoundException e) {
				return new ECPFXViewImpl(new Label("Rendering went wrong: " + e.getMessage()));
			} catch (final NoPropertyDescriptorFoundExeption e) {
				return new ECPFXViewImpl(new Label("Rendering went wrong: " + e.getMessage()));
			}
			if (result != null) {
				return new ECPFXViewImpl(result);
			} else {
				return new ECPFXViewImpl(new Label("Rendering went wrong!"));
			}
		} else {
			return new ECPFXViewImpl(new Label("Rendering went wrong: Couldn't find a renderer for VView."));
		}
	}

	@Override
	public ECPFXView render(EObject domainObject, ViewModelService... services) {
		final VView view = ViewProviderHelper.getView(domainObject, null);
		return render(view, domainObject, services);
	}

	// private VView generate(EClass eClass) {
	// final VView view = VViewFactory.eINSTANCE.createView();
	// for (final EStructuralFeature feature : eClass
	// .getEAllStructuralFeatures()) {
	//
	// final VControl control = VViewFactory.eINSTANCE.createControl();
	// final VFeaturePathDomainModelReference modelReference =
	// VViewFactory.eINSTANCE
	// .createFeaturePathDomainModelReference();
	// modelReference.setDomainModelEFeature(feature);
	// control.setDomainModelReference(modelReference);
	//
	// view.getChildren().add(control);
	//
	// }
	//
	// return view;
	// }

	// private void init(VView view, EObject domainObject){
	// TreeIterator<EObject> eAllContents = view.eAllContents();
	// while (eAllContents.hasNext()) {
	// EObject next = eAllContents.next();
	// if (VDomainModelReference.class.isInstance(next)) {
	// ((VDomainModelReference) next).resolve(domainObject);
	// }
	// }
	// }
}
