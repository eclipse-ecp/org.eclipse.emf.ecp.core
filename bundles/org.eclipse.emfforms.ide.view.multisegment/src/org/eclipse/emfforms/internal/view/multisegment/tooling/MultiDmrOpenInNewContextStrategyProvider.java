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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.internal.editor.handler.CreateSegmentDmrWizard;
import org.eclipse.emf.ecp.view.spi.editor.controls.Helper;
import org.eclipse.emf.ecp.view.spi.editor.controls.ToolingModeUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a strategy to create and configure a segment-based {@link VDomainModelReference} which has multi segment as
 * its last segment. This provider is only active if the IDE tooling runs in segment mode.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "MultiDmrOpenInNewContextStrategyProvider", property = "service.ranking:Integer=30")
public class MultiDmrOpenInNewContextStrategyProvider
	extends ReferenceServiceCustomizationVendor<OpenInNewContextStrategy> implements Provider {

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		if (!isSegmentToolingEnabled()) {
			return false;
		}
		return reference == VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE
			&& owner.eClass() == VTablePackage.Literals.TABLE_CONTROL;

	}

	/**
	 * @return true if segment based tooling is enabled
	 */
	boolean isSegmentToolingEnabled() {
		return ToolingModeUtil.isSegmentToolingEnabled();
	}

	/**
	 * Creates the {@link CreateNewModelElementStrategy}.
	 *
	 * @return The created {@link CreateNewModelElementStrategy}
	 */
	@Create
	public OpenInNewContextStrategy createOpenInNewContextStrategy() {
		return (owner, reference, object) -> {
			final EClass viewRoot = Helper.getRootEClass(owner);
			final CreateSegmentDmrWizard dmrWizard = new CreateSegmentDmrWizard(
				viewRoot, "Edit Multi Domain Model Reference", null, //$NON-NLS-1$
				new MultiSelectionValidator(), new MultiSegmentGenerator(),
				VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT, true);
			final WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), dmrWizard);
			wizardDialog.setBlockOnOpen(true);
			wizardDialog.open();

			// Replace the old dmr with the new one
			final Optional<VDomainModelReference> newDmr = dmrWizard.getDomainModelReference();
			if (newDmr.isPresent()) {
				final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);
				// The DMR is always set in a single reference
				final Command command = SetCommand.create(domain, owner, reference, newDmr.get());
				domain.getCommandStack().execute(command);
			}

			return true;
		};
	}
}
