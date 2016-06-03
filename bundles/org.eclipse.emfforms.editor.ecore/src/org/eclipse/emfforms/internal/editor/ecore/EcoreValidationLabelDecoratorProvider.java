/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.swt.treemasterdetail.LabelDecoratorProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * An Ecore LabelDecoratorProvider.
 *
 * @author Eugen Neufeld
 *
 */
public class EcoreValidationLabelDecoratorProvider implements LabelDecoratorProvider {

	private final Notifier input;
	private EcoreValidationServiceLabelDecorator decorator;

	/**
	 * Default constructor.
	 *
	 * @param input the notifier which we will display the diagnostics
	 */
	public EcoreValidationLabelDecoratorProvider(Notifier input) {
		this.input = input;
	}

	@Override
	public Optional<ILabelDecorator> getLabelDecorator(TreeViewer viewer) {
		decorator = new EcoreValidationServiceLabelDecorator(viewer, input);
		return Optional.<ILabelDecorator> of(decorator);
	}

	@SuppressWarnings("restriction")
	@Override
	public void dispose() {
		decorator.dispose();
	}

}
