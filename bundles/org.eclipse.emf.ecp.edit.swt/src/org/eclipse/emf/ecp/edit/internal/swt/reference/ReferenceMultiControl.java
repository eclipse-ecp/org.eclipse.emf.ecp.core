/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.edit.internal.swt.reference;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.controls.MultiControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.AddReferenceAction;
import org.eclipse.emf.ecp.edit.spi.swt.reference.NewReferenceAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emf.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * This class defines a Control which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature}s which
 * have a multi reference.
 *
 * @author Eugen Neufeld
 *
 */
public class ReferenceMultiControl extends MultiControl {

	@Override
	protected ECPSWTAction[] instantiateActions() {
		final ECPSWTAction[] actions = new ECPSWTAction[2];
		final Setting firstSetting = getFirstSetting();
		actions[0] = new AddReferenceAction(getEditingDomain(firstSetting), firstSetting,
			getItemPropertyDescriptor(firstSetting), getService(ReferenceService.class));
		actions[1] = new NewReferenceAction(getEditingDomain(firstSetting), firstSetting,
			getItemPropertyDescriptor(firstSetting), getService(ReferenceService.class));
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
