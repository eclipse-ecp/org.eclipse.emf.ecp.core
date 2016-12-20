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
package org.eclipse.emf.ecp.edit.spi.swt.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.CopyCommand.Helper;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Factory to create copy commands enabling progress reposrting.
 *
 * @since 1.11
 */
public final class ProgressCopyCommandFactory {

	private ProgressCopyCommandFactory() {
	}

	/**
	 * This creates a command that copies the given collection of objects. If the collection contains more than one
	 * object,
	 * then a compound command will be created containing individual copy commands for each object.
	 *
	 * @param domain {@link EditingDomain}
	 * @param collection objects to copy
	 * @param monitor the monitor.
	 * @return the copy command.
	 */
	public static Command create(final EditingDomain domain, final Collection<?> collection,
		IProgressMonitorProvider monitor) {
		if (collection == null || collection.isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}

		final Helper copyHelper = new Helper();
		final CompoundCommand copyCommand = new ProgressCompoundCommand(CompoundCommand.MERGE_COMMAND_ALL, monitor);
		for (final Object object : collection) {
			copyCommand.append(domain.createCommand(CopyCommand.class, new CommandParameter(object, null, copyHelper)));
		}

		return copyCommand.unwrap();
	}
}
