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

import java.util.ListIterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.swt.widgets.Display;

/**
 * Extension of the {@link StrictCompoundCommand} which reports the progress.
 *
 * @author Johannes Faltermeier
 * @since 1.11
 *
 */
public class ProgressStrictCompoundCommand extends StrictCompoundCommand {

	private final IProgressMonitorProvider monitor;

	/**
	 * Creates an empty instance.
	 *
	 * @param monitor the monitor
	 */
	public ProgressStrictCompoundCommand(IProgressMonitorProvider monitor) {
		super();
		this.monitor = monitor;
	}

	// REUSED CLASS

	@Override
	protected boolean prepare() {
		// Go through the commands of the list.
		//
		final ListIterator<Command> commands = commandList.listIterator();

		// If there are some...
		//
		if (commands.hasNext()) {
			boolean result = true;

			// The termination guard is in the body.
			//
			for (;;) {
				final Command command = commands.next();
				if (command.canExecute()) {
					if (commands.hasNext()) {
						if (command.canUndo()) {
							try {
								if (commands.previousIndex() <= rightMostExecutedCommandIndex) {
									command.redo();
									worked();
								} else {
									++rightMostExecutedCommandIndex;
									command.execute();
									worked();
								}
							} catch (final RuntimeException exception) {
								CommonPlugin.INSTANCE.log(new WrappedException(
									CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), exception) //$NON-NLS-1$
										.fillInStackTrace());

								result = false;
								break;
							}
						} else {
							// We can't undo it, so we'd better give up.
							//
							result = false;
							break;
						}
					} else {
						// Now is the best time to record isUndoable because later we would have to do all the executes
						// again!
						// This makes canUndo very simple!
						//
						isUndoable = command.canUndo();
						break;
					}
				} else {
					// If we can't execute this one, we just can't do it at all.
					//
					result = false;
					break;
				}
			}

			// If we are pessimistic, then we need to undo all the commands that we have executed so far.
			//
			if (isPessimistic) {
				// The most recently processed command will never have been executed.
				//
				commands.previous();

				// We want to unroll all the effects of the previous commands.
				//
				while (commands.hasPrevious()) {
					final Command command = commands.previous();
					command.undo();
					worked();
				}
			}

			return result;
		}
		isUndoable = false;
		return false;
	}

	@Override
	public void execute() {
		if (isPessimistic) {
			for (final ListIterator<Command> commands = commandList.listIterator(); commands.hasNext();) {
				try {
					// Either execute or redo the command, as appropriate.
					//
					final Command command = commands.next();
					if (commands.previousIndex() <= rightMostExecutedCommandIndex) {
						command.redo();
						worked();
					} else {
						command.execute();
						worked();
					}
				} catch (final RuntimeException exception) {
					// Skip over the command that threw the exception.
					//
					commands.previous();

					// Iterate back over the executed commands to undo them.
					//
					while (commands.hasPrevious()) {
						commands.previous();
						final Command command = commands.previous();
						if (command.canUndo()) {
							command.undo();
							worked();
						} else {
							break;
						}
					}

					throw exception;
				}
			}
		} else if (!commandList.isEmpty()) {
			final Command command = commandList.get(commandList.size() - 1);
			command.execute();
			worked();
		}
	}

	@Override
	public void undo() {
		if (isPessimistic) {
			super.undo();
		} else if (!commandList.isEmpty()) {
			final Command command = commandList.get(commandList.size() - 1);
			command.undo();
			worked();
		}
	}

	@Override
	public void redo() {
		if (isPessimistic) {
			super.redo();
		} else if (!commandList.isEmpty()) {
			final Command command = commandList.get(commandList.size() - 1);
			command.redo();
			worked();
		}
	}

	@Override
	public boolean appendAndExecute(Command command) {
		if (command != null) {
			if (!isPrepared) {
				if (commandList.isEmpty()) {
					isPrepared = true;
					isExecutable = true;
				} else {
					isExecutable = prepare();
					isPrepared = true;
					isPessimistic = true;
					if (isExecutable) {
						execute();
					}
				}
			}

			if (command.canExecute()) {
				try {
					command.execute();
					worked();
					commandList.add(command);
					++rightMostExecutedCommandIndex;
					isUndoable = command.canUndo();
					return true;
				} catch (final RuntimeException exception) {
					CommonPlugin.INSTANCE
						.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), //$NON-NLS-1$
							exception).fillInStackTrace());
				}
			}

			command.dispose();
		}

		return false;
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isUndoable: " + isUndoable + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		result.append(" (isPessimistic: " + isPessimistic + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		result.append(" (rightMostExecutedCommandIndex: " + rightMostExecutedCommandIndex + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		return result.toString();
	}

	// END REUSED CLASS

	private void worked() {
		final IProgressMonitor progressMonitor = monitor.getProgressMonitor();
		if (progressMonitor == null) {
			return;
		}
		progressMonitor.worked(1);
		while (Display.getDefault().readAndDispatch()) {
		}
	}
}
