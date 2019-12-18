/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a strategy to the DefaultReferenceService
 * that does not allow to create objects in non containment references inside data templates.
 *
 * @author Eugen Neufeld
 * @since 1.23
 */
@Component(property = "service.ranking:Integer=20")
public class DataTemplateCreateNewModelElementStrategyProvider
	extends ReferenceServiceCustomizationVendor<CreateNewModelElementStrategy> implements Provider {

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return !(owner instanceof Template) && !reference.isContainment()
			&& EcoreUtil.getRootContainer(owner) instanceof TemplateCollection;
	}

	/**
	 * Creates the {@link CreateNewModelElementStrategy}.
	 *
	 * @return The created {@link CreateNewModelElementStrategy}
	 */
	@Create
	public CreateNewModelElementStrategy createCreateNewModelElementStrategy() {
		return new Strategy();
	}

	/**
	 * The actual {@link CreateNewModelElementStrategy strategy} that informes the user, that a new object cannot be
	 * created in a non containment references in a data template.
	 *
	 * @author Eugen Neufeld
	 */
	class Strategy implements CreateNewModelElementStrategy {

		@Override
		public Optional<EObject> createNewModelElement(EObject owner, EReference reference) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(),
				Messages.DataTemplateCreateNewModelElement_0,
				Messages.DataTemplateCreateNewModelElement_1);
			return Optional.empty();
		}

	}
}
