/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.datatemplate.DataTemplatePackage;
import org.osgi.service.component.annotations.Component;

/**
 * An {@link org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy.Provider
 * EClassSelectionStrategy.Provider} for
 * DataTemplate EClass Selection.
 *
 * @author Eugen Neufeld
 *
 */
@Component
public class DataTemplateEClassSelectionStrategyProvider implements EClassSelectionStrategy.Provider {

	/**
	 * This is the {@link Bid} for the EClassSelectionStrategy.
	 *
	 * @param eReference The {@link EReference} to check
	 * @return 10 if the reference is the Template_Instance, null otherwise
	 * @see Bid
	 */
	@Bid
	public Double bid(EReference eReference) {
		if (DataTemplatePackage.eINSTANCE.getTemplate_Instance() == eReference) {
			return 10d;
		}
		return null;
	}

	/**
	 * This actually creates the EClassSelectionStrategy.
	 *
	 * @return The {@link EClassSelectionStrategy}
	 */
	@Create
	public EClassSelectionStrategy create() {
		return new EClassSelectionStrategy() {

			@Override
			public Collection<EClass> collectEClasses(EObject owner, EReference reference,
				Collection<EClass> eclasses) {
				return EMFUtils.getSubClasses(reference.getEReferenceType());
			}
		};
	}
}
