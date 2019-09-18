package org.eclipse.emf.ecp.view.model.refactoring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecp.view.model.presentation.ContributionUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ViewModelDeleteParticipant_PTest {

	@Parameters(name = "project:{0}")
	public static Object[] parameters() {
		return new Object[][] {
			{ "multipleEntries", "views/test.view" },
			{ "multipleEntriesPaths", "views/test.view" },
			{ "multipleEntriesPathsRoot", "test.view" },
			{ "lastEntryLastInProperties", "views/test.view" },
			{ "lastEntryMidInProperties", "views/test.view" },
			{ "lastEntryFirstInProperties", "views/test.view" },
		};
	}

	private ViewModelDeleteParticipant participant;
	private final String projectName;
	private final String viewModelToDelete;

	public ViewModelDeleteParticipant_PTest(String projectName, String viewModelToDelete) {
		this.projectName = projectName;
		this.viewModelToDelete = viewModelToDelete;
	}

	@Before
	public void setUp() throws Exception {
		participant = new ViewModelDeleteParticipant();
	}

	private static IProject createProject(String name, IProgressMonitor monitor) throws CoreException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject project = root.getProject(name);
		project.create(monitor);
		project.open(monitor);
		return project;
	}

	private static IResource importFileIntoProject(IContainer container, File file, IProgressMonitor monitor)
		throws CoreException, IOException {
		if (file.isDirectory()) {
			final IPath folderPath = container.getProjectRelativePath().append(file.getName());
			final IFolder folder = container.getFolder(folderPath);
			folder.create(true, true, monitor);
			for (final File child : file.listFiles()) {
				importFileIntoProject(folder, child, monitor);
			}
			return folder;
		}
		final IFile targetResource = container.getFile(new Path(file.getName()));
		final InputStream contentStream = new FileInputStream(file);
		targetResource.create(contentStream, false, monitor);
		contentStream.close();
		return targetResource;

	}

	private static IProject createAndPopulateProject(String projectName, IProgressMonitor monitor)
		throws CoreException, IOException {
		final IProject project = createProject(projectName, monitor);
		// copy content of the resources equivalent folder
		final String folderName = String.format("/resources/%s/", projectName);//$NON-NLS-1$
		final String resourceFolderPath = new File(".").getAbsolutePath() + folderName;//$NON-NLS-1$
		final File resourceFolder = new File(resourceFolderPath);
		for (final File file : resourceFolder.listFiles()) {
			importFileIntoProject(project, file, monitor);
		}
		return project;
	}

	@Test
	public void testCreateChangeIProgressMonitor() throws CoreException, IOException {
		final IProject project = createAndPopulateProject(projectName, new NullProgressMonitor());
		final IFile viewFile = project.getFile(viewModelToDelete);
		viewFile.delete(true, new NullProgressMonitor());
		participant.initialize(viewFile);
		final Change change = participant.createChange(new NullProgressMonitor());
		change.perform(new NullProgressMonitor());
		// assert project

		final Optional<String> buildProperties = ContributionUtil.parseIFile(project.getFile("build.properties"));
		final Optional<String> buildPropertiesFinal = ContributionUtil
			.parseIFile(project.getFile("build.properties-final"));
		final Optional<String> pluginXml = ContributionUtil.parseIFile(project.getFile("plugin.xml"));
		final Optional<String> pluginXmlFinal = ContributionUtil.parseIFile(project.getFile("plugin.xml-final"));

		assertTrue(buildProperties.isPresent() == buildPropertiesFinal.isPresent());
		assertTrue(pluginXml.isPresent() == pluginXmlFinal.isPresent());
		assertEquals(buildPropertiesFinal.get(), buildProperties.get());
		if (pluginXml.isPresent()) {
			assertEquals(pluginXmlFinal.get(), pluginXml.get());
		}
	}

}
