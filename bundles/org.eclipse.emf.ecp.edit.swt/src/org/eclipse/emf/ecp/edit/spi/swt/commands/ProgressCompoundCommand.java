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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.swt.widgets.Display;

/**
 * Extension of the {@link CompoundCommand} which reports progress.
 *
 * @author Johannes Faltermeier
 * @since 1.11
 *
 */
public class ProgressCompoundCommand extends CompoundCommand {

	private final IProgressMonitorProvider monitor;

	/**
	 * Creates an empty instance with the given result index.
	 *
	 * @param resultIndex the {@link #resultIndex}.
	 * @param monitor the progress monitor
	 */
	public ProgressCompoundCommand(int resultIndex, IProgressMonitorProvider monitor) {
		super(resultIndex);
		this.monitor = monitor;
	}

	// REUSED CLASS

	/**
	 * Returns whether there are commands in the list.
	 *
	 * @return whether there are commands in the list.
	 */
	@Override
	public boolean isEmpty() {
		return commandList.isEmpty();
	}

	/**
	 * Returns an unmodifiable view of the commands in the list.
	 *
	 * @return an unmodifiable view of the commands in the list.
	 */
	@Override
	public List<Command> getCommandList() {
		return Collections.unmodifiableList(commandList);
	}

	/**
	 * Returns the index of the command whose result and affected objects are forwarded.
	 * Negative values have special meaning, as defined by the static constants.
	 *
	 * @return the index of the command whose result and affected objects are forwarded.
	 * @see #LAST_COMMAND_ALL
	 * @see #MERGE_COMMAND_ALL
	 */
	@Override
	public int getResultIndex() {
		return resultIndex;
	}

	/**
	 * Returns whether all the commands can execute so that {@link #isExecutable} can be cached.
	 * An empty command list causes <code>false</code> to be returned.
	 *
	 * @return whether all the commands can execute.
	 */
	@Override
	protected boolean prepare() {
		if (commandList.isEmpty()) {
			return false;
		}
		for (final Command command : commandList) {
			if (!command.canExecute()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Calls {@link Command#execute} for each command in the list.
	 */
	@Override
	public void execute() {
		for (final ListIterator<Command> commands = commandList.listIterator(); commands.hasNext();) {
			try {
				final Command command = commands.next();
				command.execute();
				worked();
			} catch (final RuntimeException exception) {
				// Skip over the command that threw the exception.
				//
				commands.previous();

				try {
					// Iterate back over the executed commands to undo them.
					//
					while (commands.hasPrevious()) {
						final Command command = commands.previous();
						if (command.canUndo()) {
							command.undo();
							worked();
						} else {
							break;
						}
					}
				} catch (final RuntimeException nestedException) {
					CommonPlugin.INSTANCE
						.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), //$NON-NLS-1$
							nestedException).fillInStackTrace());
				}

				throw exception;
			}
		}
	}

	/**
	 * Returns <code>false</code> if any of the commands return <code>false</code> for {@link Command#canUndo}.
	 *
	 * @return <code>false</code> if any of the commands return <code>false</code> for <code>canUndo</code>.
	 */
	@Override
	public boolean canUndo() {
		for (final Command command : commandList) {
			if (!command.canUndo()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Calls {@link Command#undo} for each command in the list, in reverse order.
	 */
	@Override
	public void undo() {
		for (final ListIterator<Command> commands = commandList.listIterator(commandList.size()); commands
			.hasPrevious();) {
			try {
				final Command command = commands.previous();
				command.undo();
				worked();
			} catch (final RuntimeException exception) {
				// Skip over the command that threw the exception.
				//
				commands.next();

				try {
					// Iterate forward over the undone commands to redo them.
					//
					while (commands.hasNext()) {
						final Command command = commands.next();
						command.redo();
						worked();
					}
				} catch (final RuntimeException nestedException) {
					CommonPlugin.INSTANCE
						.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), //$NON-NLS-1$
							nestedException).fillInStackTrace());
				}

				throw exception;
			}
		}
	}

	/**
	 * Calls {@link Command#redo} for each command in the list.
	 */
	@Override
	public void redo() {
		for (final ListIterator<Command> commands = commandList.listIterator(); commands.hasNext();) {
			try {
				final Command command = commands.next();
				command.redo();
				worked();
			} catch (final RuntimeException exception) {
				// Skip over the command that threw the exception.
				//
				commands.previous();

				try {
					// Iterate back over the executed commands to undo them.
					//
					while (commands.hasPrevious()) {
						final Command command = commands.previous();
						command.undo();
						worked();
					}
				} catch (final RuntimeException nestedException) {
					CommonPlugin.INSTANCE
						.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), //$NON-NLS-1$
							nestedException).fillInStackTrace());
				}

				throw exception;
			}
		}
	}

	/**
	 * Determines the result by composing the results of the commands in the list;
	 * this is affected by the setting of {@link #resultIndex}.
	 *
	 * @return the result.
	 */
	@Override
	public Collection<?> getResult() {
		if (commandList.isEmpty()) {
			return Collections.EMPTY_LIST;
		} else if (resultIndex == LAST_COMMAND_ALL) {
			return commandList.get(commandList.size() - 1).getResult();
		} else if (resultIndex == MERGE_COMMAND_ALL) {
			return getMergedResultCollection();
		} else if (resultIndex < commandList.size()) {
			return commandList.get(resultIndex).getResult();
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * Returns the merged collection of all command results.
	 *
	 * @return the merged collection of all command results.
	 */
	@Override
	protected Collection<?> getMergedResultCollection() {
		final Collection<Object> result = new ArrayList<Object>();

		for (final Command command : commandList) {
			result.addAll(command.getResult());
		}

		return result;
	}

	/**
	 * Determines the affected objects by composing the affected objects of the commands in the list;
	 * this is affected by the setting of {@link #resultIndex}.
	 *
	 * @return the affected objects.
	 */
	@Override
	public Collection<?> getAffectedObjects() {
		if (commandList.isEmpty()) {
			return Collections.EMPTY_LIST;
		} else if (resultIndex == LAST_COMMAND_ALL) {
			return commandList.get(commandList.size() - 1).getAffectedObjects();
		} else if (resultIndex == MERGE_COMMAND_ALL) {
			return getMergedAffectedObjectsCollection();
		} else if (resultIndex < commandList.size()) {
			return commandList.get(resultIndex).getAffectedObjects();
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * Returns the merged collection of all command affected objects.
	 *
	 * @return the merged collection of all command affected objects.
	 */
	@Override
	protected Collection<?> getMergedAffectedObjectsCollection() {
		final Collection<Object> result = new ArrayList<Object>();

		for (final Command command : commandList) {
			result.addAll(command.getAffectedObjects());
		}

		return result;
	}

	/**
	 * Determines the label by composing the labels of the commands in the list;
	 * this is affected by the setting of {@link #resultIndex}.
	 *
	 * @return the label.
	 */
	@Override
	public String getLabel() {
		if (label != null) {
			return label;
		} else if (commandList.isEmpty()) {
			return CommonPlugin.INSTANCE.getString("_UI_CompoundCommand_label"); //$NON-NLS-1$
		} else if (resultIndex == LAST_COMMAND_ALL || resultIndex == MERGE_COMMAND_ALL) {
			return commandList.get(commandList.size() - 1).getLabel();
		} else if (resultIndex < commandList.size()) {
			return commandList.get(resultIndex).getLabel();
		} else {
			return CommonPlugin.INSTANCE.getString("_UI_CompoundCommand_label"); //$NON-NLS-1$
		}
	}

	/**
	 * Determines the description by composing the descriptions of the commands in the list;
	 * this is affected by the setting of {@link #resultIndex}.
	 *
	 * @return the description.
	 */
	@Override
	public String getDescription() {
		if (description != null) {
			return description;
		} else if (commandList.isEmpty()) {
			return CommonPlugin.INSTANCE.getString("_UI_CompoundCommand_description"); //$NON-NLS-1$
		} else if (resultIndex == LAST_COMMAND_ALL || resultIndex == MERGE_COMMAND_ALL) {
			return commandList.get(commandList.size() - 1).getDescription();
		} else if (resultIndex < commandList.size()) {
			return commandList.get(resultIndex).getDescription();
		} else {
			return CommonPlugin.INSTANCE.getString("_UI_CompoundCommand_description"); //$NON-NLS-1$
		}
	}

	/**
	 * Adds a command to this compound command's list of commands.
	 *
	 * @param command the command to append.
	 */
	@Override
	public void append(Command command) {
		if (isPrepared) {
			throw new IllegalStateException("The command is already prepared"); //$NON-NLS-1$
		}

		if (command != null) {
			commandList.add(command);
		}
	}

	/**
	 * Checks if the command can execute;
	 * if so, it is executed, appended to the list, and true is returned,
	 * if not, it is just disposed and false is returned.
	 * A typical use for this is to execute commands created during the execution of another command, e.g.,
	 *
	 * <pre>
	 *   class MyCommand extends CommandBase
	 *   {
	 *     protected Command subcommand;
	 *
	 *     //...
	 *
	 *     public void execute()
	 *     {
	 *       // ...
	 *       Compound subcommands = new CompoundCommand();
	 *       subcommands.appendAndExecute(new AddCommand(...));
	 *       if (condition) subcommands.appendAndExecute(new AddCommand(...));
	 *       subcommand = subcommands.unwrap();
	 *     }
	 *
	 *     public void undo()
	 *     {
	 *       // ...
	 *       subcommand.undo();
	 *     }
	 *
	 *     public void redo()
	 *     {
	 *       // ...
	 *       subcommand.redo();
	 *     }
	 *
	 *     public void dispose()
	 *     {
	 *       // ...
	 *       if (subcommand != null)
	 *      {
	 *         subcommand.dispose();
	 *       }
	 *     }
	 *   }
	 * </pre>
	 *
	 * Another use is in an execute override of compound command itself:
	 *
	 * <pre>
	 *   class MyCommand extends CompoundCommand
	 *   {
	 *     public void execute()
	 *     {
	 *       // ...
	 *       appendAndExecute(new AddCommand(...));
	 *       if (condition) appendAndExecute(new AddCommand(...));
	 *     }
	 *   }
	 * </pre>
	 *
	 * Note that appending commands will modify what getResult and getAffectedObjects return,
	 * so you may want to set the resultIndex flag.
	 *
	 * @param command the command.
	 * @return whether the command was successfully executed and appended.
	 */
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

	/**
	 * Adds a command to this compound command's the list of commands and returns <code>true</code>,
	 * if <code>command.{@link org.eclipse.emf.common.command.Command#canExecute() canExecute()}</code> returns true;
	 * otherwise, it simply calls
	 * <code>command.{@link org.eclipse.emf.common.command.Command#dispose() dispose()}</code>
	 * and returns <code>false</code>.
	 *
	 * @param command the command.
	 * @return whether the command was executed and appended.
	 */
	@Override
	public boolean appendIfCanExecute(Command command) {
		if (command == null) {
			return false;
		} else if (command.canExecute()) {
			commandList.add(command);
			return true;
		} else {
			command.dispose();
			return false;
		}
	}

	/**
	 * Calls {@link Command#dispose} for each command in the list.
	 */
	@Override
	public void dispose() {
		for (final Command command : commandList) {
			command.dispose();
		}
	}

	/**
	 * Returns one of three things:
	 * {@link org.eclipse.emf.common.command.UnexecutableCommand#INSTANCE}, if there are no commands,
	 * the one command, if there is exactly one command,
	 * or <code>this</code>, if there are multiple commands;
	 * this command is {@link #dispose}d in the first two cases.
	 * You should only unwrap a compound command if you created it for that purpose, e.g.,
	 *
	 * <pre>
	 * CompoundCommand subcommands = new CompoundCommand();
	 * subcommands.append(x);
	 * if (condition)
	 * 	subcommands.append(y);
	 * Command result = subcommands.unwrap();
	 * </pre>
	 *
	 * is a good way to create an efficient accumulated result.
	 *
	 * @return the unwrapped command.
	 */
	@Override
	public Command unwrap() {
		switch (commandList.size()) {
		case 0: {
			dispose();
			return UnexecutableCommand.INSTANCE;
		}
		case 1: {
			final Command result = commandList.remove(0);
			dispose();
			return result;
		}
		default: {
			return this;
		}
		}
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (commandList: #" + commandList.size() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		result.append(" (resultIndex: " + resultIndex + ")"); //$NON-NLS-1$ //$NON-NLS-2$

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
