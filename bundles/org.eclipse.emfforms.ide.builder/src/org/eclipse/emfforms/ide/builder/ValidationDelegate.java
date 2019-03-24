/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emfforms.common.Optional;

/**
 * Protocol for delegates that the validation builder invokes to validate workspace resources.
 */
public interface ValidationDelegate {
	/**
	 * A validation delegate that does nothing.
	 */
	ValidationDelegate NULL = new ValidationDelegate() {

		@Override
		public Optional<Diagnostic> validate(IFile file, IProgressMonitor monitor) {
			if (monitor != null) {
				monitor.done();
			}

			return Optional.empty();
		}
	};

	/**
	 * Validate a {@code file} in the workspace.
	 *
	 * @param file the file to validate
	 * @param monitor for reporting validation progress
	 *
	 * @return the problems found, if any
	 */
	Optional<Diagnostic> validate(IFile file, IProgressMonitor monitor);

}
