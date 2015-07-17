/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.ecore.swt.internal;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This is a control for {@link org.eclipse.emf.ecore.EClassifier EClassifier}s which will offer the possibility to
 * select {@link org.eclipse.emf.ecore.EDataType EDataType}s. Will be used for editing the type of an
 * {@link org.eclipse.emf.ecore.EAttribute EAttribute}.
 *
 * @author jfaltermeier
 *
 */
public class EClassifierOnlyEDataTypeControl extends EClassifierControl {
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl#createButtons(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[1];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new AddEClassifierOnlyEDataTypeReferenceAction(
			getEditingDomain(getFirstSetting()), setting, getItemPropertyDescriptor(setting),
			getService(ReferenceService.class), getVisiblePackages()), composite);
		return buttons;
	}
}
