/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation, implementation based
 * org.eclipse.emf.ecore.presentation.EcoreModelWizard and
 * org.eclipse.emf.importer.ui.EMFProjectWizard
 ******************************************************************************/
/**
 *
 * Copyright (c) 2005-2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *
 */
package org.eclipse.emf.ecp.ecore.editor.ui.operations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelEditPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecp.ecore.editor.IEcoreGenModelLinker;
import org.eclipse.emf.ecp.ecore.editor.factory.EcoreGenModelLinkerFactory;
import org.eclipse.emf.ecp.ecore.editor.util.EcoreGenException;
import org.eclipse.emf.ecp.ecore.editor.util.ProjectHelper;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Operation that modifies the workspace.
 */
public class CreateModelsWorkspaceModifyOperation extends
	WorkspaceModifyOperation {

	private final IFile modelFile;
	private final ProjectHelper projectHelper = new ProjectHelper("");
	private final String initialObjectName;
	private final String encoding;
	private final EcorePackage ecorePackage = EcorePackage.eINSTANCE;
	private final EcoreFactory ecoreFactory = ecorePackage.getEcoreFactory();

	/**
	 * Constructor.
	 *
	 * @param modelFile
	 *            the model file
	 * @param initialObjectName
	 *            initial object name
	 * @param encoding
	 *            the encoding
	 * @param projectFullName
	 *            name of the project
	 */
	public CreateModelsWorkspaceModifyOperation(IFile modelFile,
		String initialObjectName, String encoding, String projectFullName) {
		this.modelFile = modelFile;
		this.initialObjectName = initialObjectName;
		this.encoding = encoding;
		projectHelper.setProjectFullName(projectFullName);
	}

	private EObject createInitialModel() {
		final EClass eClass = (EClass) ecorePackage.getEClassifier(initialObjectName);
		final EObject rootObject = ecoreFactory.create(eClass);

		// We can't have a null name, in case we're using EMOF serialization.
		if (rootObject instanceof ENamedElement) {
			((ENamedElement) rootObject).setName("");
		}
		return rootObject;
	}

	@Override
	protected void execute(IProgressMonitor progressMonitor) {
		try {
			// Create a resource set
			//
			final ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getURIConverter().getURIMap()
				.putAll(EcorePlugin.computePlatformURIMap(true));

			// Get the URI of the model file.
			//
			final URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath()
				.toString(), true);

			// Create a resource for this file. Don't specify a
			// content type, as it could be Ecore or EMOF.
			//
			final Resource resource = resourceSet.createResource(fileURI);

			// Add the initial model object to the contents.
			//
			final EObject rootObject = createInitialModel();
			if (rootObject != null) {
				resource.getContents().add(rootObject);
			}

			// Save the contents of the resource to the file system.
			final Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_ENCODING, encoding);
			options.put(Resource.OPTION_LINE_DELIMITER,
				Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);
			EPackage ecorepack = EcorePackage.eINSTANCE;
			ecorepack = (EPackage) resource.getContents().get(0);
			ecorepack.setName(projectHelper.getProjectName());
			ecorepack.setNsPrefix(projectHelper.getNSPrefix());
			ecorepack.setNsURI(projectHelper.getNSURL());
			resource.save(options);
			final IEcoreGenModelLinker linker = EcoreGenModelLinkerFactory
				.getEcoreGenModelLinker();
			final String genModelPath = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(modelFile.getLocation()).getFullPath()
				.removeLastSegments(1).toOSString()
				+ "/model.genmodel";
			final String ecorePath = modelFile.getLocation().toFile().getAbsolutePath(); // ResourcesPlugin.getWorkspace().getRoot()
			// .getFile(modelFile.getLocation()).getFullPath()
			// .toOSString();
			final String projectPath = "/"
				+ modelFile.getLocation().segment(
					modelFile.getLocation().segmentCount() - 3);
			linker.generateGenModel(ecorePath, genModelPath, projectPath);
		} catch (final EcoreGenException ex) {
			GenModelEditPlugin.INSTANCE.log(ex);
		} catch (final IOException ex) {
			GenModelEditPlugin.INSTANCE.log(ex);
		} finally {
			progressMonitor.done();
		}
	}

}
