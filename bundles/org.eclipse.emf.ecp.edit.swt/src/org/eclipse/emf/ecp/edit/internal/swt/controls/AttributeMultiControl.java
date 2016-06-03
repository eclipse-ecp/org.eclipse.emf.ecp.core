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
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.internal.swt.actions.AddAttributeAction;
import org.eclipse.emf.ecp.edit.spi.swt.actions.ECPSWTAction;
import org.eclipse.emf.ecp.edit.spi.util.ECPStaticApplicableTester;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * This class defines a Control which is used for displaying {@link org.eclipse.emf.ecore.EStructuralFeature}s which
 * have a multi attribute.
 *
 * @deprecated use MultiAttributeSWTRenderer instead
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class AttributeMultiControl extends MultiControl {

	@Override
	protected ECPSWTAction[] instantiateActions() {
		final ECPSWTAction[] actions = new ECPSWTAction[1];
		final Setting firstSetting = getFirstSetting();
		actions[0] = new AddAttributeAction(getEditingDomain(firstSetting), firstSetting);
		return actions;
	}

	@Override
	protected int getTesterPriority(ECPStaticApplicableTester tester, Setting setting) {
		return AttributeMultiControlTester.getTesterPriority(tester,
			setting.getEStructuralFeature(), setting.getEObject());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetLabelText()
	 */
	@Override
	protected String getUnsetLabelText() {
		return LocalizationServiceHelper.getString(getClass(),
			DepricatedControlMessageKeys.AttributeMultiControl_NotSetClickToSet);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.SWTControl#getUnsetButtonTooltip()
	 */
	@Override
	protected String getUnsetButtonTooltip() {
		return LocalizationServiceHelper
			.getString(getClass(), DepricatedControlMessageKeys.AttributeMultiControl_Unset);
	}

}
