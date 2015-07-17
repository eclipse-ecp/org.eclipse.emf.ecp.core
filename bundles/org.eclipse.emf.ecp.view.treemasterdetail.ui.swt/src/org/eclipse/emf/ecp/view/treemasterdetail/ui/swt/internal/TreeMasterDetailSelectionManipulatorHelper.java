/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.internal.treemasterdetail.ui.swt.Activator;

/**
 * Helper Class for manipulating a selection.
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public final class TreeMasterDetailSelectionManipulatorHelper {

	private static TreeMasterDetailSelectionManipulator manipulator;
	private static boolean readExtensions;

	private TreeMasterDetailSelectionManipulatorHelper() {
	}

	/**
	 * Manipulate the selection using the manipulateSelection ExtensionPoint.
	 *
	 * @param object The Object to manipulate
	 * @return the manipulated Object
	 */
	public static Object manipulateSelection(Object object) {
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry == null) {
			return object;
		}
		if (!readExtensions) {
			final IConfigurationElement[] controls = extensionRegistry
				.getConfigurationElementsFor("org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.selectionManipulator"); //$NON-NLS-1$
			for (final IConfigurationElement e : controls) {
				try {
					manipulator = (TreeMasterDetailSelectionManipulator) e.createExecutableExtension("class"); //$NON-NLS-1$
				} catch (final CoreException e1) {
					Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e1.getMessage(), e1));
				}
			}
			readExtensions = true;
		}
		if (manipulator != null) {
			return manipulator.manipulateSelection(object);
		}
		return object;
	}
}
