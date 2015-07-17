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

import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.edit.spi.swt.reference.DeleteReferenceAction;
import org.eclipse.emf.ecp.internal.core.ECPProjectImpl;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This is a control for {@link org.eclipse.emf.ecore.EClassifier EClassifier}s.
 *
 * @author jfaltermeier
 *
 */
public class EClassifierControl extends LinkControl {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.reference.LinkControl#createButtons(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Button[] createButtons(Composite composite) {
		final Button[] buttons = new Button[2];
		final Setting setting = getFirstSetting();
		buttons[0] = createButtonForAction(new DeleteReferenceAction(getEditingDomain(getFirstSetting()), setting,
			getService(ReferenceService.class)), composite);
		buttons[1] = createButtonForAction(new AddEClassifierReferenceAction(getEditingDomain(getFirstSetting()),
			setting, getItemPropertyDescriptor(setting), getService(ReferenceService.class), getVisiblePackages()),
			composite);
		return buttons;
	}

	/**
	 * Returns the {@link EPackage}s to extract the classifiers from.
	 *
	 * @return the packages
	 */
	protected Set<EPackage> getVisiblePackages() {
		final ECPProjectImpl project = (ECPProjectImpl) ECPUtil.getECPProjectManager()
			.getProject(getViewModelContext().getDomainModel());
		return project.getVisiblePackages();
	}
}
