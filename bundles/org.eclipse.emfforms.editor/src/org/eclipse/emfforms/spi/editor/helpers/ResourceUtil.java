/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mborkowski - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.editor.helpers;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

/**
 * This is a utility class helping with working with {@link IResource} objects in the context of EMF.
 */
public final class ResourceUtil {
	private ResourceUtil() {
	}

	/**
	 * Resolved the given {@link URI} to an {@link IResource}.
	 *
	 * @param uri the {@link URI} to resolve
	 * @return the resulting {@link IResource}
	 */
	public static IResource getResourceFromURI(final URI uri) {
		final IResource targetFile;
		if (uri.isPlatform()) {
			final IPath platformString = new Path(uri.trimFragment().toPlatformString(true));
			targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(platformString);
		} else {
			targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(uri.trimFragment().toString()));
		}
		return targetFile;
	}
}
