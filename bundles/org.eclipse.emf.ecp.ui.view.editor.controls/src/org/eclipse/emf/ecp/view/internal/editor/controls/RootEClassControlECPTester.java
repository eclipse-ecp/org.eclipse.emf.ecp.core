/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * @author Eugen Neufeld
 *
 */
public class RootEClassControlECPTester extends ControlRootEClassControl2SWTRendererTester {

	@Override
	protected int isApplicable(EStructuralFeature feature) {
		if (VViewPackage.eINSTANCE.getView_RootEClass() != feature) {
			return NOT_APPLICABLE;
		}
		return 3;
	}

}
