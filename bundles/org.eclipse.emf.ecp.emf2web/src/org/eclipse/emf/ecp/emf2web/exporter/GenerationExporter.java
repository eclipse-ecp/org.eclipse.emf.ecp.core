/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.exporter;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.emf.ecp.emf2web.controller.xtend.GenerationInfo;

/**
 * Exporter responsible to export generated content.
 *
 * @author Stefan Dirix
 *
 */
public interface GenerationExporter {

	/**
	 * Export the given {@link GenerationInfo}s' content.
	 *
	 * @param generationInfos
	 *            A collection of {@link GenerationInfo}s which shall be exported.
	 * @param userInteraction
	 *            Handles the communication with the user.
	 * @throws IOException
	 *             If something went wrong during export.
	 */
	void export(Collection<? extends GenerationInfo> generationInfos, UserInteraction userInteraction)
		throws IOException;
}
