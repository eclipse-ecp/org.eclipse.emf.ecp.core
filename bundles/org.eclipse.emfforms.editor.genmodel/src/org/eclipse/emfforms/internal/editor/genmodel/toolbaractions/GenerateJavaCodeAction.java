/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.genmodel.toolbaractions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emfforms.internal.editor.genmodel.Activator;
import org.eclipse.emfforms.internal.editor.genmodel.util.PluginXmlUtil;
import org.eclipse.emfforms.spi.editor.IToolbarAction;
import org.eclipse.emfforms.spi.editor.helpers.ResourceUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * The ToolbarAction allowing the User to generate Java code for the currently visible Genmodel.
 *
 * @author Clemens Elflein
 */
public class GenerateJavaCodeAction implements IToolbarAction {

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.editor.IToolbarAction#getAction(java.lang.Object,
	 *      org.eclipse.jface.viewers.ISelectionProvider)
	 */
	@Override
	public Action getAction(Object currentObject, ISelectionProvider selectionProvider) {
		return new CreateJavaCodeAction(currentObject, selectionProvider);
	}

	@Override
	public boolean canExecute(Object object) {
		// We can't execute our Action on Objects other than ResourceSet
		if (!(object instanceof ResourceSet)) {
			return false;
		}
		// Check, if the ResourceSet contains a Genmodel. If so, we also can't execute our Action
		final ResourceSet resourceSet = (ResourceSet) object;
		for (final Resource r : resourceSet.getResources()) {
			if (r.getContents().size() > 0 && r.getContents().get(0) instanceof GenModel) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ToolbarAction to generate Java Code. It also provides the DropDown menu to create
	 * each type separately (Model, Edit, Editor, Tests).
	 */
	private static class CreateJavaCodeAction extends Action {
		private final Object[] types;
		private final Object currentObject;
		private final ISelectionProvider selectionProvider;

		public static final String FILE_NAME_PLUGIN_XML = "plugin.xml";
		public static final String FILE_NAME_META_INF = "META-INF";
		public static final String FILE_NAME_MANIFEST_MF = "MANIFEST.MF";
		public static final String REQUIRE_BUNDLE_HEADER = "Require-Bundle";
		public static final String EDITOR_BUNDLE_NAME = "org.eclipse.emfforms.editor";
		public static final String EDITOR_BUNDLE_VERSION = "1.8.0";
		public static final String EMFFORMS_EDITOR_CLASS_NAME = "org.eclipse.emfforms.spi.editor.GenericEditor";
		public static final String EMFFORMS_EDITOR_NAME = "EMFForms Editor";
		public static final String EMFFORMS_EDITOR_ID = "emfformseditor";

		CreateJavaCodeAction(String text, Object[] types, Object currentObject, ISelectionProvider selectionProvider) {
			super(text);
			this.types = types;
			this.currentObject = currentObject;
			this.selectionProvider = selectionProvider;
		}

		CreateJavaCodeAction(Object currentObject, ISelectionProvider selectionProvider) {
			super("Generate All", SWT.DROP_DOWN);
			this.selectionProvider = selectionProvider;
			this.currentObject = currentObject;

			types = new Object[] {
				GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
				GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE
			};

			setMenuCreator(new GenmodelDropdownCreator(selectionProvider));

			setImageDescriptor(ImageDescriptor.createFromURL(FrameworkUtil.getBundle(
				this.getClass()).getResource("icons/page_white_cup.png")));
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.jface.action.Action#run()
		 */
		@Override
		public void run() {
			final ResourceSet resourceSet = (ResourceSet) currentObject;
			final GenModel genmodel = (GenModel) resourceSet.getResources().get(0).getContents().get(0);
			final ISelection oldSelection = selectionProvider.getSelection();
			selectionProvider.setSelection(new StructuredSelection(resourceSet));
			genmodel.reconcile();
			selectionProvider.setSelection(oldSelection);
			final IRunnableWithProgress generateCodeRunnable = new GenerateJavaCodeRunnable(genmodel);

			try {
				new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(true, false, generateCodeRunnable);
			} catch (final InvocationTargetException ex) {
				Activator.getDefault().getReportService().report(
					new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			} catch (final InterruptedException ex) {
				Activator.getDefault().getReportService().report(
					new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
			}
		}

		/**
		 * {@link IRunnableWithProgress} to execute code generation.
		 */
		public class GenerateJavaCodeRunnable implements IRunnableWithProgress {

			private final GenModel genmodel;

			GenerateJavaCodeRunnable(GenModel genmodel) {
				this.genmodel = genmodel;
			}

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Generating Code", IProgressMonitor.UNKNOWN);

				genmodel.setCanGenerate(true);

				final Generator generator = GenModelUtil
					.createGenerator(genmodel);

				final Set<URI> knownUris = new HashSet<URI>();

				for (final Object type : types) {
					generate(monitor, genmodel, generator, knownUris, type);
				}
			}

			private void generate(IProgressMonitor monitor, final GenModel genmodel, final Generator generator,
				final Set<URI> knownUris, final Object type) {
				generator.generate(genmodel, type,
					new BasicMonitor.EclipseSubProgress(monitor, 1));

				final Set<URI> uris = new HashSet<URI>(generator.getGeneratedOutputs());
				uris.removeAll(knownUris);
				knownUris.addAll(uris);

				if (GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE.equals(type)) {
					handleGeneratedEditorProject(uris, genmodel);
				}
			}

			/**
			 * Handles the case that an editor project was generated. Takes a set of generated URIs to find the
			 * plugin.xml file to edit.
			 *
			 * @param uris a set of generated URIs
			 * @param genmodel
			 */
			private void handleGeneratedEditorProject(Set<URI> uris, GenModel genmodel) {
				URI pluginXml = null;
				URI manifestMf = null;
				for (final URI uri : uris) {
					if (isPluginXml(uri)) {
						pluginXml = uri;
					} else if (isManifestMf(uri)) {
						manifestMf = uri;
					}
				}

				if (pluginXml != null && manifestMf != null) {
					injectPluginXml(pluginXml, genmodel);
					injectManifestMf(manifestMf);
				}
			}

			private boolean isPluginXml(URI uri) {
				if (uri.segmentCount() != 2) {
					return false;
				}

				final String lastSegment = uri.lastSegment();
				return FILE_NAME_PLUGIN_XML.equals(lastSegment);
			}

			private boolean isManifestMf(URI uri) {
				if (uri.segmentCount() != 3) {
					return false;
				}

				final String secondSegment = uri.segment(1);
				final String lastSegment = uri.lastSegment();
				return FILE_NAME_META_INF.equals(secondSegment) && FILE_NAME_MANIFEST_MF.equals(lastSegment);
			}

			private void injectManifestMf(URI manifestMf) {
				final IResource resource = ResourceUtil.getResourceFromURI(manifestMf);
				final IFile file = (IFile) resource;

				InputStream original;
				try {
					original = file.getContents();
					final Manifest originalManifest = new Manifest(original);
					final Attributes attributes = originalManifest.getMainAttributes();
					String newValue = attributes.getValue(REQUIRE_BUNDLE_HEADER);
					if (newValue == null) {
						newValue = "";
					} else {
						newValue += ",";
					}
					newValue += EDITOR_BUNDLE_NAME;
					if (EDITOR_BUNDLE_VERSION != null) {
						newValue += ";bundle-version=\"" + EDITOR_BUNDLE_VERSION + "\"";
					}
					attributes.putValue(REQUIRE_BUNDLE_HEADER, newValue);
					final ByteArrayOutputStream bos = new ByteArrayOutputStream();
					originalManifest.write(bos);
					file.setContents(new ByteArrayInputStream(bos.toByteArray()), false, true,
						new NullProgressMonitor());
				} catch (final CoreException ex) {
					Activator.getDefault().getReportService().report(
						new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
				} catch (final IOException ex) {
					Activator.getDefault().getReportService().report(
						new StatusReport(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex)));
				}
			}

			private void injectPluginXml(URI pluginXml, GenModel genmodel) {
				final IResource resource = ResourceUtil.getResourceFromURI(pluginXml);
				final GenPackage genPackage = genmodel.getGenPackages().get(0);

				PluginXmlUtil.addEditorExtensionPoint(
					(IFile) resource,
					EMFFORMS_EDITOR_CLASS_NAME,
					false, /* default */
					getFileExtension(genmodel),
					/**
					 * Code from org.eclipse.emf.codegen.ecore.templates.editor.PluginXML
					 * stringBuffer.append("icon=\"icons/full/obj16/");
					 * stringBuffer.append(genPackage.getPrefix());
					 * stringBuffer.append("ModelFile.gif\"");
					 */
					MessageFormat.format("icons/full/obj16/{0}ModelFile.gif", genPackage.getPrefix()),
					/**
					 * Default id is the fully qualified generated editor class appended by ID.
					 * e.g.: org.eclipse.emf.ecp.makeithappen.model.task.presentation.TaskEditorID
					 *
					 * we will use the generated editor plugin id appended by the package prefix appended by
					 * emfformseditor.
					 * e.g.: org.eclipse.emf.ecp.makeithappen.model.editor.Task.emfformseditor
					 */
					MessageFormat.format("{0}.{1}.{2}", genmodel.getEditorPluginID(), genPackage.getPrefix(),
						EMFFORMS_EDITOR_ID),
					EMFFORMS_EDITOR_NAME);
			}

			private String getFileExtension(GenModel genmodel) {
				return genmodel.getGenPackages().get(0).getFileExtensions();
			}
		}

		/**
		 * IMenuCreator to create each type separately.
		 */
		private class GenmodelDropdownCreator implements IMenuCreator {
			private Menu dropDown;
			private final ISelectionProvider selectionProvider;

			GenmodelDropdownCreator(ISelectionProvider selectionProvider) {
				this.selectionProvider = selectionProvider;
			}

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.jface.action.IMenuCreator#dispose()
			 */
			@Override
			public void dispose() {
				if (dropDown != null) {
					dropDown.dispose();
				}
			}

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
			 */
			@Override
			public Menu getMenu(Control parent) {
				dispose();

				dropDown = new Menu(parent);
				final Action generateModelAndEdit = new CreateJavaCodeAction("Generate Model + Edit",
					new Object[] {
						GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
						GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE
					}, currentObject, selectionProvider);

				final Action generateModel = new CreateJavaCodeAction("Generate Model",
					new Object[] {
						GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE
					}, currentObject, selectionProvider);

				final Action generateEdit = new CreateJavaCodeAction("Generate Edit",
					new Object[] {
						GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE
					}, currentObject, selectionProvider);

				final Action generateEditor = new CreateJavaCodeAction("Generate Editor",
					new Object[] {
						GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE
					}, currentObject, selectionProvider);

				final Action generateTests = new CreateJavaCodeAction("Generate Tests",
					new Object[] {
						GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE
					}, currentObject, selectionProvider);

				new ActionContributionItem(generateModelAndEdit).fill(dropDown, 0);
				new ActionContributionItem(generateModel).fill(dropDown, 1);
				new ActionContributionItem(generateEdit).fill(dropDown, 2);
				new ActionContributionItem(generateEditor).fill(dropDown, 3);
				new ActionContributionItem(generateTests).fill(dropDown, 4);

				return dropDown;
			}

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
			 */
			@Override
			public Menu getMenu(Menu parent) {
				return null;
			}

		}
	}
}
