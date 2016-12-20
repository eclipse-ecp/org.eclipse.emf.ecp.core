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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

/**
 * Extension of the {@link PasteFromClipboardCommand} which enabled progress reporting.
 *
 * @author Johannes Faltermeier
 * @since 1.11
 *
 */
public class ProgressPasteFromClipboardCommand extends PasteFromClipboardCommand implements IProgressMonitorProvider {

	private IProgressMonitor monitor;

	/**
	 * Constructs a new {@link ProgressPasteFromClipboardCommand}.
	 *
	 * @param domain the {@link EditingDomain}
	 * @param owner the owner object
	 * @param feature the feature
	 * @param index the index
	 */
	public ProgressPasteFromClipboardCommand(EditingDomain domain, Object owner, Object feature, int index) {
		super(domain, owner, feature, index);
	}

	/**
	 * Constructs a new {@link ProgressPasteFromClipboardCommand}.
	 *
	 * @param domain the {@link EditingDomain}
	 * @param owner the owner object
	 * @param feature the feature
	 * @param index the index
	 * @param optimize whether to call the optimized can execute method
	 */
	public ProgressPasteFromClipboardCommand(EditingDomain domain, Object owner, Object feature, int index,
		boolean optimize) {
		super(domain, owner, feature, index, optimize);
	}

	@Override
	protected boolean prepare() {
		{
			// Create a strict compound command to do a copy and then add the result
			//
			command = new ProgressStrictCompoundCommand(this);

			// Create a command to copy the clipboard.
			//
			final Command copyCommand = ProgressCopyCommandFactory.create(domain, domain.getClipboard(), this);

			// REUSED CLASS
			command.append(copyCommand);

			// Create a proxy that will create an add command.
			//
			command.append(new CommandWrapper() {
				protected Collection<Object> original;
				protected Collection<Object> copy;

				@Override
				protected Command createCommand() {
					original = domain.getClipboard();
					copy = new ArrayList<Object>(copyCommand.getResult());

					// Use the original to do the add, but only if it's of the same type as the copy.
					// This ensures that if there is conversion being done as part of the copy,
					// as would be the case for a cross domain copy in the mapping framework,
					// that we do actually use the converted instance.
					//
					if (original.size() == copy.size()) {
						for (Iterator<Object> i = original.iterator(), j = copy.iterator(); i.hasNext();) {
							final Object originalObject = i.next();
							final Object copyObject = j.next();
							if (originalObject.getClass() != copyObject.getClass()) {
								original = null;
								break;
							}
						}
					}

					final Command addCommand = AddCommand.create(domain, owner, feature,
						original == null ? copy : original, index);
					if (IProgressMonitorConsumer.class.isInstance(addCommand)) {
						IProgressMonitorConsumer.class.cast(addCommand)
							.setIProgressMonitorAccessor(ProgressPasteFromClipboardCommand.this);
					}
					return addCommand;
				}

				@Override
				public void execute() {
					if (original != null) {
						domain.setClipboard(copy);
					}
					super.execute();
				}

				@Override
				public void undo() {
					super.undo();
					if (original != null) {
						domain.setClipboard(original);
					}
				}

				@Override
				public void redo() {
					if (original != null) {
						domain.setClipboard(copy);
					}
					super.redo();
				}
			});

			boolean result;
			if (optimize) {
				// This will determine canExecute as efficiently as possible.
				//
				result = optimizedCanExecute();
			} else {
				// This will actually execute the copy command in order to check if the add can execute.
				//
				result = command.canExecute();
			}

			return result;
		}
	}
	// END REUSED CLASS

	@Override
	public void doExecute() {
		doExecuteWithProgress("Pasting elements..."); //$NON-NLS-1$
	}

	/**
	 * Does the same as {@link #doExecute()} but also displays a progress dialog.
	 *
	 * @param task the name of the task
	 */
	protected void doExecuteWithProgress(final String task) {
		try {
			final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				Display.getDefault().getActiveShell());
			progressMonitorDialog.run(
				false, /* if we fork auto UI updates of EMF.edit.ui do not work, so stay on UI thread */
				false,
				new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						ProgressPasteFromClipboardCommand.this.monitor = monitor;
						monitor.beginTask(task, getTotalWorkExecute());
						ProgressPasteFromClipboardCommand.super.doExecute();
						ProgressPasteFromClipboardCommand.this.monitor = null;
					}
				});
		} catch (final InvocationTargetException ex) {
		} catch (final InterruptedException ex) {
		}
	}

	/**
	 * @return the total work required to execute the paste operation
	 */
	protected int getTotalWorkExecute() {
		int work = domain.getClipboard().size();
		final List<Command> commandList = command.getCommandList();
		work += commandList.size();
		for (final Command subCommand : commandList) {
			if (!ProgressCompoundCommand.class.isInstance(subCommand)) {
				continue;
			}
			work += ProgressCompoundCommand.class.cast(subCommand).getCommandList().size();
		}
		return work;
	}

	@Override
	public void doUndo() {
		doUndoWithProgress("Undoing paste operation"); //$NON-NLS-1$
	}

	/**
	 * Does the same as {@link #doUndo()} but also displays a progress dialog.
	 *
	 * @param task the name of the task
	 */
	protected void doUndoWithProgress(final String task) {
		try {
			final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				Display.getDefault().getActiveShell());
			progressMonitorDialog.run(
				false, /* if we fork auto UI updates of EMF.edit.ui do not work, so stay on UI thread */
				false,
				new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						ProgressPasteFromClipboardCommand.this.monitor = monitor;
						monitor.beginTask(task, 1);
						ProgressPasteFromClipboardCommand.super.doUndo();
						ProgressPasteFromClipboardCommand.this.monitor = null;
					}
				});
		} catch (final InvocationTargetException ex) {
		} catch (final InterruptedException ex) {
		}
	}

	@Override
	public void doRedo() {
		doRedoWithProgress("Redoing paste operation."); //$NON-NLS-1$
	}

	/**
	 * Does the same as {@link #doRedo()} but also displays a progress dialog.
	 *
	 * @param task the name of the task
	 */
	protected void doRedoWithProgress(final String task) {
		try {
			final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				Display.getDefault().getActiveShell());
			progressMonitorDialog.run(
				false, /* if we fork auto UI updates of EMF.edit.ui do not work, so stay on UI thread */
				false,
				new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						ProgressPasteFromClipboardCommand.this.monitor = monitor;
						monitor.beginTask(task, 1);
						ProgressPasteFromClipboardCommand.super.doRedo();
						ProgressPasteFromClipboardCommand.this.monitor = null;
					}
				});
		} catch (final InvocationTargetException ex) {
		} catch (final InterruptedException ex) {
		}
	}

	@Override
	public IProgressMonitor getProgressMonitor() {
		return monitor;
	}

}
