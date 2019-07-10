/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.reveal;

/**
 * Enumeration of kinds of {@link RevealStep}.
 *
 * @since 1.22
 * @see RevealStep#getType()
 */
public enum RevealStepKind {
	/** A terminal step: it reveals the object. */
	TERMINAL,
	/** An intermediate step: there is more to reveal within a nested scope. */
	INTERMEDIATE,
	/** A failed step: the object is not there. */
	FAILED;
}
