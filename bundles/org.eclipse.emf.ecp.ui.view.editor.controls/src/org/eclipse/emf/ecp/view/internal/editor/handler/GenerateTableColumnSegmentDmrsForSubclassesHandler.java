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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;

/**
 * Tree master detail action that generates segment based table column DMRs based on a subclass of the table elements.
 * This subclass is selected by the user. This action is only used in the segment
 * mode of the tooling.
 *
 * @author Lucas Koehler
 *
 */
public class GenerateTableColumnSegmentDmrsForSubclassesHandler extends GenerateTableColumnSegmentDmrsHandler {

	@Override
	protected Optional<EClass> getColumnDmrRootEClass(EClass baseEClass) {
		return GenerateTableColumnsUtil.selectSubClass(baseEClass);
	}
}
