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
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.provider.ECPStringModifier;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;

/**
 * Tooltip modifier which provides tooltips for multi reference cells.
 *
 * @author Eugen Neufeld
 * @since 1.18
 *
 */
public class MultiReferenceTooltipModifier implements ECPStringModifier {

	@Override
	public String modifyString(String text, Setting setting) {
		if (setting == null) {
			return text;
		}
		if (setting.getEStructuralFeature() instanceof EAttribute) {
			return text;
		}
		if (!setting.getEStructuralFeature().isMany()) {
			return text;
		}
		final AdapterFactoryItemDelegator adapterFactoryItemDelegator = new AdapterFactoryItemDelegator(
			((AdapterFactoryEditingDomain) AdapterFactoryEditingDomain
				.getEditingDomainFor(setting.getEObject())).getAdapterFactory());
		final List<?> referencedValues = (List<?>) setting.get(true);

		return adapterFactoryItemDelegator.getText(referencedValues);

	}

	@Override
	public double getPriority() {
		return 20;
	}
}
