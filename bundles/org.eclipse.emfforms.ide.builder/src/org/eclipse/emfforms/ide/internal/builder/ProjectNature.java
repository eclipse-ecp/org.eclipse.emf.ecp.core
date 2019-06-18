/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.ide.internal.builder;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Common project nature implementation.
 */
public abstract class ProjectNature implements IProjectNature {

	private final String id;
	private final String builder;
	private IProject project;

	/**
	 * Initializes me with my unique identifier and builder ID.
	 *
	 * @param id my unique identifier
	 * @param builder my builder
	 */
	protected ProjectNature(String id, String builder) {
		super();

		this.id = id;
		this.builder = builder;
	}

	@Override
	public void configure() throws CoreException {
		if (builder == null) {
			// Nothing to configure;
			return;
		}

		final IProjectDescription desc = project.getDescription();
		final ICommand[] commands = desc.getBuildSpec();

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				return;
			}
		}

		final ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		final ICommand command = desc.newCommand();
		command.setBuilderName(builder);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
		project.setDescription(desc, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		if (builder == null) {
			// Nothing to deconfigure;
			return;
		}

		final IProjectDescription description = getProject().getDescription();
		final ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builder)) {
				final ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
					commands.length - i - 1);
				description.setBuildSpec(newCommands);
				project.setDescription(description, null);
				return;
			}
		}
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * Query whether a {@code project} has my nature.
	 *
	 * @param project a project
	 * @return whether it has my nature
	 */
	public boolean hasNature(IProject project) {
		try {
			return project.hasNature(id);
		} catch (final CoreException e) {
			// It doesn't have our nature, then
			return false;
		}
	}

	/**
	 * Toggles a nature on a project.
	 *
	 * @param project
	 *            to have sample nature added or removed
	 * @param natureID the nature ID to toggle
	 * @throws CoreException issue while toggling nature
	 */
	public static void toggleNature(IProject project, String natureID) throws CoreException {
		final IProjectDescription description = project.getDescription();
		final String[] natures = description.getNatureIds();

		for (int i = 0; i < natures.length; ++i) {
			if (natureID.equals(natures[i])) {
				// Remove the nature
				final String[] newNatures = new String[natures.length - 1];
				System.arraycopy(natures, 0, newNatures, 0, i);
				System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
				return;
			}
		}

		// Add the nature
		final String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = natureID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);
	}

}
