/*******************************************************************************
 * Copyright (c) 2011-2020 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 559116
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.util;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * This interface allows to be notified when the
 * {@link org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite TreeMasterDetailComposite} finished
 * rendering a detail pane.
 *
 * @author Lucas Koehler
 * @since 1.13
 *
 */
public interface DetailPanelRenderingFinishedCallback {

	/**
	 * This method is called after the renderedObject has been rendered.
	 * If access to the {@link ViewModelContext} of the rendering is required, then
	 * override the {@link #renderingFinished(ViewModelContext, Object)} method also.
	 *
	 * @param renderedObject The rendered Object
	 *
	 * @see #renderingFinished(ViewModelContext, Object)
	 */
	void renderingFinished(Object renderedObject);

	/**
	 * Notify me that an object's detail view has been rendered. The default implementation
	 * just delegates to the {@link #renderingFinished(Object)} call-back.
	 *
	 * @param context the view-model context of the rendering
	 * @param renderedObject the object that was rendered
	 *
	 * @since 1.24
	 * @see #renderingFinished(Object)
	 */
	default void renderingFinished(ViewModelContext context, Object renderedObject) {
		renderingFinished(renderedObject);
	}

	/**
	 * Adapt the two-argument version of the callback alone. A functional alternative that
	 * does not require implementing the single-argument call-back.
	 *
	 * @param renderingFinished the rendering-finished call-back to adapt
	 * @return the call-back adapter
	 *
	 * @since 1.24
	 * @see #renderingFinished(ViewModelContext, Object)
	 */
	static DetailPanelRenderingFinishedCallback adapt(
		BiConsumer<? super ViewModelContext, ? super Object> renderingFinished) {

		return new DetailPanelRenderingFinishedCallback() {

			@Override
			public void renderingFinished(ViewModelContext context, Object renderedObject) {
				renderingFinished.accept(context, renderedObject);
			}

			@Override
			public void renderingFinished(Object renderedObject) {
				// Not needed
			}
		};
	}

}
