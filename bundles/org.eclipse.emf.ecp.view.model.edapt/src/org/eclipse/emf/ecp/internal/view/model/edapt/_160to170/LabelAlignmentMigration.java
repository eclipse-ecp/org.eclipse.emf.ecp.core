/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.model.edapt._160to170;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.edapt.migration.CustomMigration;
import org.eclipse.emf.edapt.migration.MigrationException;
import org.eclipse.emf.edapt.spi.migration.Instance;
import org.eclipse.emf.edapt.spi.migration.Metamodel;
import org.eclipse.emf.edapt.spi.migration.Model;

/**
 * This migration will make sure that after the default of the label alignment has been changed from left to default,
 * left is still used for the old models.
 *
 * @author Johannes Faltermeier
 *
 */
public class LabelAlignmentMigration extends CustomMigration {

	@Override
	public void migrateAfter(Model model, Metamodel metamodel) throws MigrationException {
		final EAttribute eAttribute = metamodel
			.getEAttribute("http://org/eclipse/emf/ecp/view/model/170.Control.labelAlignment"); //$NON-NLS-1$
		final EEnum eenum = EEnum.class.cast(eAttribute.getEAttributeType());
		final EEnumLiteral left = eenum.getEEnumLiteral("Left"); //$NON-NLS-1$
		final EEnumLiteral def = eenum.getEEnumLiteral("Default"); //$NON-NLS-1$

		final EList<Instance> allControlsIncludingSubclasses = model
			.getAllInstances("http://org/eclipse/emf/ecp/view/model/170.Control"); //$NON-NLS-1$
		for (final Instance control : allControlsIncludingSubclasses) {
			final Object object = control.get(eAttribute);
			if (def.getInstance() == object) {
				control.set(eAttribute, left.getInstance());
			}
		}
	}
}
