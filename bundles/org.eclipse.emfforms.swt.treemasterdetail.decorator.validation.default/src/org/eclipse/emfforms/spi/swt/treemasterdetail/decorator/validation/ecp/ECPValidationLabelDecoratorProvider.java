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
package org.eclipse.emfforms.spi.swt.treemasterdetail.decorator.validation.ecp;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.ECPValidationServiceLabelDecorator;
import org.eclipse.emfforms.spi.swt.treemasterdetail.LabelDecoratorProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Johannes Faltermeier
 *
 */
public class ECPValidationLabelDecoratorProvider implements LabelDecoratorProvider {

	private final Notifier input;
	private ECPValidationServiceLabelDecorator decorator;
	private final DiagnosticCache cache;

	/**
	 * Default constructor.
	 *
	 * @param input the notifier which we will display the diagnostics
	 */
	public ECPValidationLabelDecoratorProvider(Notifier input) {
		this(input, new DiagnosticCache(input));
	}

	/**
	 * Default constructor.
	 *
	 * @param input the notifier which we will display the diagnostics
	 * @param cache the cache to use.
	 */
	public ECPValidationLabelDecoratorProvider(Notifier input, DiagnosticCache cache) {
		this.input = input;
		this.cache = cache;
	}

	@Override
	public Optional<ILabelDecorator> getLabelDecorator(TreeViewer viewer) {
		decorator = new ECPValidationServiceLabelDecorator(viewer, input, cache);
		return Optional.<ILabelDecorator> of(decorator);
	}

	@Override
	public void dispose() {
		decorator.dispose();
	}

}
