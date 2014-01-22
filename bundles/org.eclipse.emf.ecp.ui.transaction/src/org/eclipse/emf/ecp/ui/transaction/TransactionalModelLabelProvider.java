/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.transaction;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProjectOpenClosedObserver;
import org.eclipse.emf.ecp.internal.ui.model.ECPLabelProvider;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

/**
 * Label provider that wraps its {@code getText} calls in a transaction.
 * 
 * @author emueller
 */
public class TransactionalModelLabelProvider extends ECPLabelProvider implements ECPProjectOpenClosedObserver {

	/**
	 * Default constructor.
	 */
	public TransactionalModelLabelProvider() {
		super(null);
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	public void dispose() {
		ECPUtil.getECPObserverBus().unregister(this);
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof ECPProject) {
			final ECPProject project = (ECPProject) element;
			return project.getName();
		}

		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(element);
		if (!TransactionalEditingDomain.class.isInstance(editingDomain)) {
			return super.getText(element);
		}

		final TransactionalEditingDomain transactionalEditingDomain = TransactionalEditingDomain.class
			.cast(editingDomain);
		try {
			final String result = (String) transactionalEditingDomain
				.runExclusive(new RunnableWithResult.Impl<String>() {
					public void run() {
						setResult(TransactionalModelLabelProvider.super.getText(element));
					}
				});
			return result;
		} catch (final InterruptedException e) {
			return super.getText(element);
		}
	}

	/** {@inheritDoc} */
	public void projectChanged(final ECPProject project, boolean opened) {
		fireEvent(new LabelProviderChangedEvent(this, project));
	}
}
