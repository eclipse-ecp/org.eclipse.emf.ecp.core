/**
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
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.model.refactoring;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.model.presentation.ContributionUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;

/**
 * Delete Participant for View Models.
 * This class cleans up the plugin.xml.
 *
 * @author Eugen Neufeld
 **/

public class ViewModelDeleteParticipant extends DeleteParticipant {
	private static final Pattern EMPTY_PLUGIN_PATTERN = Pattern.compile("<plugin>\\s*</plugin>", //$NON-NLS-1$
		Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final Pattern EMPTY_XMI_EXTENSIONPOINT = Pattern.compile(
		"<extension.*point=\"org.eclipse.emf.ecp.view.model.provider.xmi.file\">\\s*</extension>", //$NON-NLS-1$
		Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private static final String ONE_LINE_FILE = "<file filePath=\"%s\"/>"; //$NON-NLS-1$
	private static final String MULTI_LINE_FILE = "<file\\s*filePath=\"%s\">\\s*</file>"; //$NON-NLS-1$
	private static final String REMOVE_PLUGIN_XML = "plugin.xml,?\\\\?\\s*"; //$NON-NLS-1$
	private static final String REMOVE_FOLDER = "%s/,?\\\\?\\s*"; //$NON-NLS-1$
	private static final String REMOVE_VIEW = "%s,?\\\\?\\s*"; //$NON-NLS-1$
	private static final String CLEAN_PLUGIN_XML = ",\\\\\\s*$"; //$NON-NLS-1$

	/**
	 * Custom Change that removes the registered view model from the plugin.xml .
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private final class ChangeExtension extends Change {
		@Override
		public Change perform(IProgressMonitor pm) throws CoreException {

			try {
				final String pluginString = ContributionUtil.parseIFile(pluginXml).get();
				final String viewPath = viewModel.getProjectRelativePath().toString();

				final String cleanExtensionString = pluginString
					.replaceAll(String.format(ONE_LINE_FILE, viewPath), EMPTY_STRING)
					.replaceAll(String.format(MULTI_LINE_FILE, viewPath), EMPTY_STRING);

				final String cleanPlugin = EMPTY_XMI_EXTENSIONPOINT.matcher(cleanExtensionString)
					.replaceAll(EMPTY_STRING);
				final Matcher matcher = EMPTY_PLUGIN_PATTERN.matcher(cleanPlugin);
				boolean removePluginXml = false;
				if (!matcher.find()) {
					final FileWriter out = new FileWriter(pluginXml.getRawLocation().makeAbsolute().toFile());
					out.write(String.valueOf(cleanPlugin));
					out.close();
				} else {
					pluginXml.delete(true, pm);
					removePluginXml = true;
				}
				boolean removeFolder = false;
				if (viewModel.getParent().members().length == 0) {
					viewModel.getParent().delete(true, pm);
					removeFolder = true;
				}
				final IFile buildProperties = viewModel.getProject().getFile("build.properties"); //$NON-NLS-1$
				final String buildPropertiesString = ContributionUtil.parseIFile(buildProperties).get();
				String cleanedProperties = null;
				if (removePluginXml || removeFolder) {
					if (removePluginXml) {
						cleanedProperties = buildPropertiesString
							.replaceAll(REMOVE_PLUGIN_XML, EMPTY_STRING);
					}
					if (removeFolder) {
						cleanedProperties = (cleanedProperties != null ? cleanedProperties : buildPropertiesString)
							.replaceAll(
								String.format(REMOVE_FOLDER,
									viewModel.getParent().getProjectRelativePath().toString()),
								EMPTY_STRING);
					}
				}
				{
					cleanedProperties = (cleanedProperties != null ? cleanedProperties : buildPropertiesString)
						.replaceAll(
							String.format(REMOVE_VIEW, viewModel.getProjectRelativePath().toString()),
							EMPTY_STRING);
				}

				cleanedProperties = cleanedProperties.replaceAll(CLEAN_PLUGIN_XML, EMPTY_STRING);
				final FileWriter out = new FileWriter(buildProperties.getRawLocation().makeAbsolute().toFile());
				out.write(String.valueOf(cleanedProperties));
				out.flush();
				out.close();

				viewModel.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);

			} catch (final IOException ex) {
				throw new CoreException(
					new Status(IStatus.ERROR, "org.eclipse.emf.ecp.view.model.editor", ex.getMessage(), ex)); //$NON-NLS-1$
			}

			return null;
		}

		@Override
		public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
			return RefactoringStatus.create(Status.OK_STATUS);
		}

		@Override
		public void initializeValidationData(IProgressMonitor pm) {
		}

		@Override
		public String getName() {
			return "Remove view model from plugin.xml"; //$NON-NLS-1$
		}

		@Override
		public Object getModifiedElement() {
			return null;
		}
	}

	private IFile viewModel;
	private IFile pluginXml;

	/**
	 * Default constructor.
	 */
	public ViewModelDeleteParticipant() {
	}

	@Override
	protected boolean initialize(Object element) {
		viewModel = Adapters.adapt(element, IFile.class);
		pluginXml = viewModel.getProject().getFile("plugin.xml"); //$NON-NLS-1$

		return viewModel != null && pluginXml.exists();
	}

	@Override
	public String getName() {
		return "Clean up after View Model Removal"; //$NON-NLS-1$
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
		throws OperationCanceledException {
		final ResourceChangeChecker resourceChecker = context.getChecker(ResourceChangeChecker.class);
		if (resourceChecker != null) {
			try {
				final RefactoringStatus result = resourceChecker.check(pm);
				final String projectInfo = "Remove view model from plugin.xml"; //$NON-NLS-1$
				result.addInfo(projectInfo);
				return result;
			} catch (final CoreException e) {
				return RefactoringStatus.create(e.getStatus());
			}
		}

		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new ChangeExtension();
	}

}