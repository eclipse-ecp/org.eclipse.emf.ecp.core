/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.graphiti.feature;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

public class ECPDoubleClickFeature extends AbstractCustomFeature {

	public ECPDoubleClickFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	public void execute(ICustomContext context) {
		final EObject target = (EObject) getBusinessObjectForPictogramElement(context.getInnerPictogramElement());
		final Object o = getBusinessObjectForPictogramElement(getDiagram());
		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(o);
		ECPHandlerHelper.openModelElement(target, ecpProject);

	}

}
