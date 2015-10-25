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
	 * @see org.eclipse.emfforms.spi.editor.IToolbarAction#getAction(java.lang.Object)
	 */
	@Override
	public Action getAction(Object currentObject) {
		return new CreateJavaCodeAction(currentObject);
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

		public static final String FILE_NAME_PLUGIN_XML = "plugin.xml";
		public static final String FILE_NAME_META_INF = "META-INF";
		public static final String FILE_NAME_MANIFEST_MF = "MANIFEST.MF";
		public static final String REQUIRE_BUNDLE_HEADER = "Require-Bundle";
		public static final String EDITOR_BUNDLE_NAME = "org.eclipse.emfforms.editor";
		public static final String EDITOR_BUNDLE_VERSION = "1.8.0";
		public static final String EMFFORMS_EDITOR_CLASS_NAME = "org.eclipse.emfforms.spi.editor.GenericEditor";
		public static final String EMFFORMS_EDITOR_NAME = "EMFForms Editor";
		public static final String EMFFORMS_EDITOR_ID = "emfformseditor";

		public CreateJavaCodeAction(String text, Object[] types, Object currentObject) {
			super(text);
			this.types = types;
			this.currentObject = currentObject;
		}

		public CreateJavaCodeAction(Object currentObject) {
			super("Generate All", SWT.DROP_DOWN);

			this.currentObject = currentObject;

			types = new Object[] {
				GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
				GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE
			};

			setMenuCreator(new GenmodelDropdownCreator());

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
			final IRunnableWithProgress generateCodeRunnable = new GenerateJavaCodeRunnable();

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
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Generating Code", IProgressMonitor.UNKNOWN);
				final ResourceSet resourceSet = (ResourceSet) currentObject;
				final GenModel genmodel = (GenModel) resourceSet.getResources().get(0).getContents().get(0);

				genmodel.reconcile();
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
					}
					else if (isManifestMf(uri)) {
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
				// TODO: icon is hardcoded
				PluginXmlUtil.addEditorExtensionPoint((IFile) resource, EMFFORMS_EDITOR_CLASS_NAME, false,
					getFileExtension(genmodel),
					"icons/full/obj16/MypackageModelFile.gif",
					EMFFORMS_EDITOR_ID, EMFFORMS_EDITOR_NAME);
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
				final Action generateModel = new CreateJavaCodeAction("Generate Model + Edit",
					new Object[] {
						GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
						GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE
					}, currentObject);

				final Action generateEditor = new CreateJavaCodeAction("Generate Editor",
					new Object[] {
						GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE
					}, currentObject);

				final Action generateTests = new CreateJavaCodeAction("Generate Tests",
					new Object[] {
						GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE
					}, currentObject);

				new ActionContributionItem(generateModel).fill(dropDown, 0);
				new ActionContributionItem(generateEditor).fill(dropDown, 1);
				new ActionContributionItem(generateTests).fill(dropDown, 2);

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
