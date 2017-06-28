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
import org.eclipse.emfforms.internal.editor.genmodel.Messages;
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
 * The ToolbarAction allowing the User to generate Java code for the currently visible Genmodel.
 *
 * @author Clemens Elflein
 */
public class GenerateJavaCodeAction implements IToolbarAction {

	@Override
	public Action getAction(Object currentObject, ISelectionProvider selectionProvider) {
		final ResourceSet resourceSet = (ResourceSet) currentObject;
		final GenModel genModel = getGenModel(resourceSet);
		return new CreateJavaCodeAction(genModel, selectionProvider);
	}

	/**
	 * Returns the first {@link GenModel} object found in the first of the given {@link ResourceSet}.
	 *
	 * @param resourceSet the {@link ResourceSet} to check
	 * @return the {@link GenModel} or <code>null</code> if none was found
	 */
	protected GenModel getGenModel(ResourceSet resourceSet) {
		if (resourceSet.getResources().isEmpty()) {
			return null;
		}
		final Resource topResource = resourceSet.getResources().get(0);
		if (!topResource.getContents().isEmpty() && GenModel.class.isInstance(topResource.getContents().get(0))) {
			return (GenModel) topResource.getContents().get(0);
		}
		return null;
	}

	@Override
	public boolean canExecute(Object object) {
		// We can't execute our Action on Objects other than ResourceSet
		if (!(object instanceof ResourceSet)) {
			return false;
		}
		// We can execute our Action only if the ResourceSet contains a GenModel
		final ResourceSet resourceSet = (ResourceSet) object;
		final GenModel genModel = getGenModel(resourceSet);
		return genModel != null;
	}

	/**
	 * ToolbarAction to generate Java Code. It also provides the DropDown menu to create
	 * each type separately (Model, Edit, Editor, Tests).
	 */
	class CreateJavaCodeAction extends Action {
		private final Object[] types;
		private final ISelectionProvider selectionProvider;
		private final GenModel genModel;
		private static final String FILE_NAME_PLUGIN_XML = "plugin.xml"; //$NON-NLS-1$
		private static final String FILE_NAME_META_INF = "META-INF"; //$NON-NLS-1$
		private static final String FILE_NAME_MANIFEST_MF = "MANIFEST.MF"; //$NON-NLS-1$
		private static final String REQUIRE_BUNDLE_HEADER = "Require-Bundle"; //$NON-NLS-1$
		private static final String EDITOR_BUNDLE_NAME = "org.eclipse.emfforms.editor";//$NON-NLS-1$
		private static final String EDITOR_BUNDLE_VERSION = "1.8.0";//$NON-NLS-1$
		private static final String EMFFORMS_EDITOR_CLASS_NAME = "org.eclipse.emfforms.spi.editor.GenericEditor";//$NON-NLS-1$
		private static final String EMFFORMS_EDITOR_NAME = "EMFForms Editor";//$NON-NLS-1$
		private static final String EMFFORMS_EDITOR_ID = "emfformseditor";//$NON-NLS-1$

		/**
		 * Constructor.
		 *
		 * @param text the string used as the text for the action, or null if there is no text
		 * @param types the project types
		 * @param genModel the {@link GenModel}
		 * @param selectionProvider the {@link ISelectionProvider}
		 */
		CreateJavaCodeAction(String text, Object[] types, GenModel genModel, ISelectionProvider selectionProvider) {
			super(text);
			this.types = types;
			this.genModel = genModel;
			this.selectionProvider = selectionProvider;
		}

		/**
		 * Constructor.
		 *
		 * @param genModel the {@link GenModel}
		 * @param selectionProvider the {@link ISelectionProvider}
		 */
		CreateJavaCodeAction(GenModel genModel, ISelectionProvider selectionProvider) {
			super(Messages.GenerateJavaCodeAction_generateAll, SWT.DROP_DOWN);
			this.selectionProvider = selectionProvider;
			this.genModel = genModel;

			types = new Object[] {
				GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
				GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
				GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE
			};

			setMenuCreator(new GenmodelDropdownCreator(selectionProvider));

			setImageDescriptor(ImageDescriptor.createFromURL(FrameworkUtil.getBundle(
				this.getClass()).getResource("icons/page_white_cup.png"))); //$NON-NLS-1$
		}

		/**
		 * Returns the Java code generation action.
		 *
		 * @param text the text of the Action
		 * @param types the project types
		 * @return a new Action
		 */
		protected Action getJavaCodeAction(String text, Object[] types) {
			return new CreateJavaCodeAction(text, types, getGenModel(), selectionProvider);
		}

		@Override
		public void run() {
			final ISelection oldSelection = selectionProvider.getSelection();
			selectionProvider.setSelection(new StructuredSelection(getGenModel()));
			getGenModel().reconcile();
			selectionProvider.setSelection(oldSelection);
			final IRunnableWithProgress generateCodeRunnable = new GenerateJavaCodeRunnable(getGenModel());

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
		 * @return the genModel
		 */
		public GenModel getGenModel() {
			return genModel;
		}

		/**
		 * @return the selectionProvider
		 */
		public ISelectionProvider getSelectionProvider() {
			return selectionProvider;
		}

		/**
		 * {@link IRunnableWithProgress} to execute code generation.
		 */
		public class GenerateJavaCodeRunnable implements IRunnableWithProgress {

			private final GenModel genmodel;

			/**
			 * Constructor.
			 *
			 * @param genmodel the {@link GenModel}
			 */
			GenerateJavaCodeRunnable(GenModel genmodel) {
				this.genmodel = genmodel;
			}

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(Messages.GenerateJavaCodeAction_generatingCodeTask, IProgressMonitor.UNKNOWN);

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
						newValue = ""; //$NON-NLS-1$
					} else {
						newValue += ","; //$NON-NLS-1$
					}
					newValue += EDITOR_BUNDLE_NAME;
					if (EDITOR_BUNDLE_VERSION != null) {
						newValue += ";bundle-version=\"" + EDITOR_BUNDLE_VERSION + "\""; //$NON-NLS-1$ //$NON-NLS-2$
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
					MessageFormat.format("icons/full/obj16/{0}ModelFile.gif", genPackage.getPrefix()), //$NON-NLS-1$
					/**
					 * Default id is the fully qualified generated editor class appended by ID.
					 * e.g.: org.eclipse.emf.ecp.makeithappen.model.task.presentation.TaskEditorID
					 *
					 * we will use the generated editor plugin id appended by the package prefix appended by
					 * emfformseditor.
					 * e.g.: org.eclipse.emf.ecp.makeithappen.model.editor.Task.emfformseditor
					 */
					MessageFormat.format("{0}.{1}.{2}", genmodel.getEditorPluginID(), genPackage.getPrefix(), //$NON-NLS-1$
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

			@Override
			public void dispose() {
				if (dropDown != null) {
					dropDown.dispose();
				}
			}

			@Override
			public Menu getMenu(Control parent) {
				dispose();

				dropDown = new Menu(parent);
				final Action generateModelAndEdit = getJavaCodeAction(
					Messages.GenerateJavaCodeAction_generateModelEdit, new Object[] {
						GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE });

				final Action generateModel = getJavaCodeAction(Messages.GenerateJavaCodeAction_generateModel,
					new Object[] { GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE });

				final Action generateEdit = getJavaCodeAction(Messages.GenerateJavaCodeAction_generateEdit,
					new Object[] { GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE });

				final Action generateEditor = getJavaCodeAction(Messages.GenerateJavaCodeAction_generateEditor,
					new Object[] { GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE });

				final Action generateTests = getJavaCodeAction(Messages.GenerateJavaCodeAction_generateTests,
					new Object[] { GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE });

				new ActionContributionItem(generateModelAndEdit).fill(dropDown, 0);
				new ActionContributionItem(generateModel).fill(dropDown, 1);
				new ActionContributionItem(generateEdit).fill(dropDown, 2);
				new ActionContributionItem(generateEditor).fill(dropDown, 3);
				new ActionContributionItem(generateTests).fill(dropDown, 4);

				return dropDown;
			}

			@Override
			public Menu getMenu(Menu parent) {
				return null;
			}

		}
	}
}
