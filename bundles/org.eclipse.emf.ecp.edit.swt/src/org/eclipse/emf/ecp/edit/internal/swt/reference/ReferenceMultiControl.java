/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.reference;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.internal.swt.controls.MultiControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.AddReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * This class defines a Control which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature}s which
 * have a multi reference.
 *
 * @deprecated Use the new MultiReferenceSWTRenderer instead
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class ReferenceMultiControl extends MultiControl {

	@Override
	protected ECPSWTAction[] instantiateActions() {
		final ECPSWTAction[] actions = new ECPSWTAction[2];
		final Setting firstSetting = getFirstSetting();
		actions[0] = new AddReferenceAction(getEditingDomain(firstSetting), firstSetting,
			getItemPropertyDescriptor(firstSetting), getService(ReferenceService.class));
		actions[1] = new NewReferenceAction(getEditingDomain(firstSetting), firstSetting,
			getViewModelContext().getService(EMFFormsEditSupport.class),
			getViewModelContext().getService(EMFFormsLabelProvider.class),
			getService(ReferenceService.class), getViewModelContext().getService(ReportService.class),
			getDomainModelReference(),
			getViewModelContext().getDomainModel());
		return actions;
	}

	@Override
	protected int getTesterPriority(ECPStaticApplicableTester tester, Setting setting) {
		return ReferenceMultiControlTester.getTesterPriority(tester,
			setting.getEStructuralFeature(), setting.getEObject());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			ReferenceMessageKeys.ReferenceMultiControl_NotSetClickToSet);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper.getString(getClass(), ReferenceMessageKeys.ReferenceMultiControl_Unset);
	}
}
