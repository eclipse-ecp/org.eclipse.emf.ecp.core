/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.label;

import org.eclipse.emf.ecore.EClassifier;
import org.osgi.framework.Bundle;

/**
 * Class to resolve an EClassifier to a Bundle.
 *
 * @author Eugen Neufeld
 *
 */
public interface BundleResolver {

	/**
	 * Exception that is used if a bundle could not be found.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	class NoBundleFoundException extends Exception {
		private static final long serialVersionUID = 1L;

		/**
		 * Default Constructor.
		 *
		 * @param eClassifier The EClassifier to log for.
		 */
		public NoBundleFoundException(EClassifier eClassifier) {
			super(String.format("No Bundle could not be found for %1$s.", eClassifier.getName())); //$NON-NLS-1$
		}
	}

	/**
	 * Retrieve the edit bundle for the passed EClassifier.
	 *
	 * @param eClassifier The EClassifier to get the edit bundle for
	 * @return the Bundle , never null
	 * @throws NoBundleFoundException if no bundle could be found
	 */
	Bundle getEditBundle(EClassifier eClassifier) throws NoBundleFoundException;
}
