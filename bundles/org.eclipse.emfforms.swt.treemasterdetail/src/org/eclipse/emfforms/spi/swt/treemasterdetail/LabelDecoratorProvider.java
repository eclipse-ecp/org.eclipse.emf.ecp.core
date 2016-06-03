/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Interface to add an optional {@link ILabelDecorator} to the label provider.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public interface LabelDecoratorProvider extends Disposeable {

	/**
	 * Returns the {@link ILabelDecorator} to use.
	 * 
	 * @param viewer the {@link TreeViewer}
	 * @return the decorator or empty
	 */
	Optional<ILabelDecorator> getLabelDecorator(TreeViewer viewer);

}
