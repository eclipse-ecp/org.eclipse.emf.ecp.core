/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.ecp.editor.internal.e3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.edit.spi.ECPContextDisposedListener;
import org.eclipse.emf.ecp.edit.spi.util.ECPModelElementChangeListener;
import org.eclipse.emf.ecp.editor.e3.AbstractMEEditorPage;
import org.eclipse.emf.ecp.editor.e3.ECPEditorContext;
import org.eclipse.emf.ecp.editor.e3.MEEditorInput;
import org.eclipse.emf.ecp.editor.e3.StatusMessageProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;

/**
 * GUI view for editing MEs.
 *
 * @author helming
 * @author naughton
 */
public class MEEditor extends SharedHeaderFormEditor {

	private static final String STATUS_EXTENSIONPOINT_ID = "org.eclipse.emf.ecp.editor.statusmessage"; //$NON-NLS-1$

	private static final String DEFAULT_PAGE_ID = "Edit"; //$NON-NLS-1$

	private static final String REPLACE_ATTRIBUTE = "replace"; //$NON-NLS-1$

	private static final String EDITOR_PAGES_EXTENSIONPOINT_ID = "org.eclipse.emf.ecp.editor.pages"; //$NON-NLS-1$

	/**
	 * The Id for MEEditor. We need this to open a model element.
	 */
	public static final String ID = "org.eclipse.emf.ecp.editor.internal.e3"; //$NON-NLS-1$

	private MEEditorPage mePage;

	private StatusMessageProvider statusMessageProvider;

	private ECPModelElementChangeListener modelElementChangeListener;

	private ECPEditorContext modelElementContext;

	private ECPContextDisposedListener modelElementContextListener;

	private MEEditorInput meInput;

	private ShortLabelProvider shortLabelProvider;

	private ComposedAdapterFactory composedAdapterFactory;

	/**
	 * Default constructor.
	 */
	public MEEditor() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addPages() {
		final String editorID = DEFAULT_PAGE_ID;
		final String editorDesc = Messages.MEEditor_Standard_View_Name;
		final MEEditorInput editorInput = (MEEditorInput) getEditorInput();

		// add pages from the extension point
		final IConfigurationElement[] configTemp = Platform.getExtensionRegistry().getConfigurationElementsFor(
			EDITOR_PAGES_EXTENSIONPOINT_ID);
		IConfigurationElement[] configIn = null;

		boolean replaceMEEditor = false;
		int counter = 0;

		for (int i = 0; i < configTemp.length; i++) {
			if (configTemp[i].getAttribute(REPLACE_ATTRIBUTE) != null
				&& configTemp[i].getAttribute(REPLACE_ATTRIBUTE).equals(editorID)) {
				// if a replacement is found, create this page, so it becomes the first one
				replaceMEEditor = true;
				AbstractMEEditorPage newPage;

				try {
					newPage = (AbstractMEEditorPage) configTemp[i].createExecutableExtension("class"); //$NON-NLS-1$
					final FormPage createPage = newPage.createPage(this, modelElementContext);
					if (createPage != null) {
						addPage(createPage);
					}
				} catch (final CoreException e1) {
					Activator.logException(e1);
				}

				// put remaining pages into the original configIn array
				configIn = new IConfigurationElement[configTemp.length - 1];
				for (int j = 0, k = 0; j < configTemp.length - 1; j++, k++) {
					if (counter == j) {
						j--;
					} else {
						configIn[j] = configTemp[k];
					}
				}

				break;
			}
			counter++;
		}

		// create original MEEditor standard view if no replacement exists
		// and put remaining pages into the original configIn array
		if (!replaceMEEditor) {
			try {
				if (editorInput.getProblemFeature() != null) {
					mePage = new MEEditorPage(this, editorID, editorDesc, modelElementContext,
						modelElementContext.getDomainObject(), editorInput.getProblemFeature());
				} else {
					mePage = new MEEditorPage(this, editorID, editorDesc, modelElementContext,
						modelElementContext.getDomainObject());
				}

				addPage(mePage);
				configIn = configTemp;
			} catch (final PartInitException e) {
				// JH Auto-generated catch block
				Activator.logException(e);
			}
		}

		// Sort the pages by the "after" attribute and omit replaced pages
		final List<IConfigurationElement> config = PageCandidate.getPages(configIn);
		for (final IConfigurationElement e : config) {
			try {
				final AbstractMEEditorPage newPage = (AbstractMEEditorPage) e.createExecutableExtension("class"); //$NON-NLS-1$
				final FormPage createPage = newPage.createPage(this, modelElementContext);
				if (createPage != null) {
					addPage(createPage);
				}
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 * Save is not allowed as the editor can only modify model elements.
	 *
	 * @return false
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		if (input instanceof MEEditorInput) {
			setInput(input);
			meInput = (MEEditorInput) input;

			modelElementContext = meInput.getModelElementContext();
			composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
			shortLabelProvider = new ShortLabelProvider(composedAdapterFactory);
			setPartName(shortLabelProvider.getText(modelElementContext.getDomainObject()));
			setTitleImage(shortLabelProvider.getImage(modelElementContext.getDomainObject()));

			modelElementContextListener = new ECPContextDisposedListener() {

				@Override
				public void contextDisposed() {
					close(false);
				}

			};
			modelElementContext.addECPContextDisposeListener(modelElementContextListener);
			modelElementChangeListener = new ECPModelElementChangeListener(modelElementContext.getDomainObject()) {

				@Override
				public void onChange(Notification notification) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							updateIcon();
							setPartName(shortLabelProvider.getText(modelElementContext.getDomainObject()));
							if (mePage != null) {
								mePage.updateSectionTitle();
								// mePage.updateLiveValidation();
							}
							updateStatusMessage();
						}
					});

				}
			};

			initStatusProvider();
			updateStatusMessage();

			// labelProviderListener = new ILabelProviderListener() {
			// public void labelProviderChanged(LabelProviderChangedEvent event) {
			// if(!titleImage.isDisposed())
			// titleImage.dispose();
			// titleImage=meInput.getImageDescriptor().createImage();
			// updateIcon();
			// }
			// };
			// meInput.getLabelProvider().addListener(labelProviderListener);

		} else {
			throw new PartInitException("MEEditor is only appliable for MEEditorInputs"); //$NON-NLS-1$
		}
	}

	private void initStatusProvider() {
		final IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(
				STATUS_EXTENSIONPOINT_ID);
		final ArrayList<IConfigurationElement> provider = new ArrayList<IConfigurationElement>();
		provider.addAll(Arrays.asList(configurationElements));
		int priority = 0;
		for (final IConfigurationElement e : provider) {
			try {
				final StatusMessageProvider statusMessageProvider = (StatusMessageProvider) e
					.createExecutableExtension("class"); //$NON-NLS-1$
				final int newpriority = statusMessageProvider.canRender(modelElementContext.getDomainObject());
				if (newpriority > priority) {
					priority = newpriority;
					this.statusMessageProvider = statusMessageProvider;
				}
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
	}

	private void updateStatusMessage() {
		if (statusMessageProvider != null) {
			getEditorSite().getActionBars().getStatusLineManager()
				.setMessage(statusMessageProvider.getMessage(modelElementContext.getDomainObject()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {

		super.setFocus();
		if (mePage != null) {
			mePage.setFocus();
		}
		updateStatusMessage();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		modelElementChangeListener.remove();
		modelElementContext.dispose();
		meInput.dispose();
		// ((MEEditorInput) getEditorInput()).getLabelProvider().removeListener(labelProviderListener);
		composedAdapterFactory.dispose();
		shortLabelProvider.dispose();
		// meInput.dispose();
		getSite().setSelectionProvider(null);
		super.dispose();

	}

	private void updateIcon() {

		setTitleImage(shortLabelProvider.getImage(modelElementContext.getDomainObject()));
		// TODO AS: Debug why sometimes the page is null - not disposed Adapter?
		if (mePage != null) {
			try {
				mePage.getManagedForm().getForm()
					.setImage(shortLabelProvider.getImage(modelElementContext.getDomainObject()));
			} catch (final SWTException e) {
				// Catch in case Editor is directly closed after change.
			}
		}
	}
}
