package org.eclipse.emf.ecp.view.model.internal.fx;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

public interface RendererFactory {

	RendererFactory INSTANCE = RendererFactoryImpl.getInstance();

	RendererFX<VElement> getRenderer(VElement vElement, ViewModelContext viewContext);
}
