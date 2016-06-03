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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.DiagnosticCache;
import org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.ECPValidationServiceLabelDecorator;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * EcoreValidationServiceLabelDecorator validates always all containers of a changed element, too.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class EcoreValidationServiceLabelDecorator extends ECPValidationServiceLabelDecorator {

	/**
	 * Default constructor.
	 *
	 * @param viewer the {@link TreeViewer}
	 * @param input the input notifier
	 */
	public EcoreValidationServiceLabelDecorator(TreeViewer viewer, Notifier input) {
		super(viewer, input);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.ECPValidationServiceLabelDecorator#updateCache(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.DiagnosticCache)
	 */
	@Override
	protected void updateCache(EObject element, DiagnosticCache cache) {
		super.updateCache(element, cache);
		final EObject parent = element.eContainer();
		if (parent != null) {
			updateCache(parent, cache);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.ECPValidationServiceLabelDecorator#updateCacheWithoutRefresh(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emfforms.internal.swt.treemasterdetail.decorator.validation.ecp.DiagnosticCache)
	 */
	@Override
	protected void updateCacheWithoutRefresh(EObject element, DiagnosticCache cache) {
		super.updateCacheWithoutRefresh(element, cache);
		final EObject parent = element.eContainer();
		if (parent != null) {
			updateCacheWithoutRefresh(parent, cache);
		}
	}

}
