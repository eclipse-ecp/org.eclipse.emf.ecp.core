/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 * Eugen Neufeld - refactor
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.editor.internal.e3;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

// TODO: possibily remove this class
/**
 * Command capable of recording changes on a model element.
 *
 * @author emueller
 */
public abstract class ECPCommand extends ChangeCommand {

	private final EditingDomain domain;

	private RuntimeException runtimeException;

	/**
	 * Constructor.
	 *
	 * @param eObject
	 *            the model element whose changes one is interested in
	 * @param domain the {@link EditingDomain} to use
	 */
	public ECPCommand(EObject eObject, EditingDomain domain) {
		super(eObject);
		this.domain = domain;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.edit.ChangeCommand#doExecute()
	 */
	@Override
	protected final void doExecute() {
		try {
			doRun();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (final RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			runtimeException = e;
			throw e;
		}
	}

	/**
	 * The actual action that is being executed.
	 */
	protected abstract void doRun();

	/**
	 * Executes the command.
	 *
	 * @param ignoreExceptions
	 *            true if any thrown exception in the execution of the command should be ignored.
	 */
	public void run(boolean ignoreExceptions) {
		runtimeException = null;

		// EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		if (domain == null) {
			return;
		}
		domain.getCommandStack().execute(this);

		if (!ignoreExceptions && runtimeException != null) {
			throw runtimeException;
		}
	}

}
