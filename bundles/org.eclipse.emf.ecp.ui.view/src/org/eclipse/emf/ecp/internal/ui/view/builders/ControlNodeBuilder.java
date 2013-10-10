/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.internal.ui.view.builders;

import java.util.Iterator;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.ECPControl;
import org.eclipse.emf.ecp.edit.ECPControlContext;
import org.eclipse.emf.ecp.edit.ECPControlFactory;
import org.eclipse.emf.ecp.internal.ui.view.Activator;
import org.eclipse.emf.ecp.internal.ui.view.renderer.Leaf;
import org.eclipse.emf.ecp.internal.ui.view.renderer.Node;
import org.eclipse.emf.ecp.ui.view.custom.ECPCustomControl;
import org.eclipse.emf.ecp.view.model.Control;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;

/**
 * A NodeBuilder for Controls.
 * 
 * @author Eugen Neufeld
 * 
 * @param <C> the Control of this NodeBuilder
 */
public class ControlNodeBuilder<C extends Control> implements NodeBuilder<C> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.internal.ui.view.builders.NodeBuilder#build(org.eclipse.emf.ecp.view.model.Renderable,
	 *      org.eclipse.emf.ecp.edit.ECPControlContext, org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator)
	 */
	public Node<C> build(C renderable, ECPControlContext controlContext,
		AdapterFactoryItemDelegator adapterFactoryItemDelegator) {
		if (renderable.getDomainModelReferences().isEmpty() && renderable.getControlId() != null
			&& renderable.getControlId().length() != 0) {
			final ECPControlFactory controlFactory = Activator.getDefault().getECPControlFactory();
			final ECPControl createControl = controlFactory.createControl(null, controlContext,
				renderable.getControlId());
			if (ECPCustomControl.class.isInstance(createControl)) {
				final ECPCustomControl customControl = (ECPCustomControl) createControl;
				renderable.getDomainModelReferences().addAll(customControl.getNeededDomainModelReferences());
			}
		}

		ECPControlContext relevantContext = controlContext;

		if (renderable.getDomainModelReferences().size() == 1) {
			final Iterator<Setting> iterator = renderable.getDomainModelReferences().get(0).getIterator();
			int count = 0;
			Setting lastSetting = null;
			while (iterator.hasNext()) {
				count++;
				lastSetting = iterator.next();
			}
			if (count == 1 && lastSetting != null) {
				relevantContext = controlContext.createSubContext(lastSetting.getEObject());
			}
		}
		return new Leaf<C>(renderable, relevantContext);
	}
}
