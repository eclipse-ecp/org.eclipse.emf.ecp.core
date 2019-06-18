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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a strategy to create and configure a segment-based {@link VDomainModelReference}. This provider is
 * only active if the IDE tooling runs in segment mode.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "DmrNewReferenceElementStrategyProvider", property = "service.ranking:Integer=20")
public class DmrNewReferenceElementStrategyProvider
	extends ReferenceServiceCustomizationVendor<CreateNewModelElementStrategy> implements Provider {

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		if (!ToolingModeUtil.isSegmentToolingEnabled()) {
			return false;
		}
		return reference.getEReferenceType() == VViewPackage.Literals.DOMAIN_MODEL_REFERENCE;

	}

	/**
	 * Creates the {@link CreateNewModelElementStrategy}.
	 *
	 * @return The created {@link CreateNewModelElementStrategy}
	 */
	@Create
	public CreateNewModelElementStrategy createCreateNewModelElementStrategy() {
		return (owner, reference) -> {
			final EClass viewRoot = Helper.getRootEClass(owner);
			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(
				viewRoot, "New Domain Model Reference", null, //$NON-NLS-1$
				structuralFeature -> null, new FeatureSegmentGenerator(), null, false);
			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			return Optional.fromJavaOptional(dmrWizard.getDomainModelReference().map(EObject.class::cast));
		};
	}
}
