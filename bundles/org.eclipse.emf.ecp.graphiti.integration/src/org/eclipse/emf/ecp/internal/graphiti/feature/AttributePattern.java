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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.config.IPatternConfiguration;

public class AttributePattern extends AbstractPattern {

	public AttributePattern(IPatternConfiguration patternConfiguration) {
		super(patternConfiguration);
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof EAttribute;
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pictogramElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pictogramElement) {
		// TODO Auto-generated method stub
		return false;
	}

}
