/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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

/**
 * Encapsulates user interaction.
 *
 * @author Stefan Dirix
 *
 */
public interface UserInteraction {

	/**
	 * Indicates a 'Yes' answer.
	 */
	int YES = 0;
	/**
	 * Indicates a 'No' answer.
	 */
	int NO = 1;
	/**
	 * Indicates a 'Cancel' answer.
	 */
	int CANCEL = 2;

	/**
	 * Asks the user a question.
	 *
	 * @param title The title of the question.
	 * @param question The question itself.
	 * @param toggle The message for an eventual toggle box.
	 * @return
	 * 		The answer, typically {@code UserInteraction.YES}, {@code UserInteraction.NO} or
	 *         {@code UserInteraction.CANCEL}
	 */
	int askQuestion(String title, String question, String toggle);

}
