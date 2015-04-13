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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.editor.e3.ECPEditorContext;
import org.eclipse.emf.ecp.spi.ui.ECPDeleteServiceImpl;
import org.eclipse.emf.ecp.spi.ui.ECPReferenceServiceImpl;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.menus.IMenuService;

/**
 * The editor page for the {@link MEEditor}.
 *
 * @author helming
 * @author shterev
 * @author naughton
 */
public class MEEditorPage extends FormPage {

	private static final String TOOLBAR_ORG_ECLIPSE_EMF_ECP_EDITOR_INTERNAL_E3_ME_EDITOR_PAGE = "toolbar:org.eclipse.emf.ecp.editor.internal.e3.MEEditorPage"; //$NON-NLS-1$
	//
	private ScrolledForm form;
	//
	private final ECPEditorContext modelElementContext;

	private ShortLabelProvider shortLabelProvider;

	private ComposedAdapterFactory composedAdapterFactory;
	private ECPSWTView ecpView;

	/**
	 * Default constructor.
	 *
	 * @param editor
	 *            the {@link MEEditor}
	 * @param id
	 *            the {@link FormPage#id}
	 * @param title
	 *            the title
	 * @param ecpEditorContext
	 *            the {@link ECPEditorContext}
	 * @param modelElement
	 *            the modelElement
	 */
	public MEEditorPage(MEEditor editor, String id, String title, ECPEditorContext ecpEditorContext,
		EObject modelElement) {
		super(editor, id, title);
		modelElementContext = ecpEditorContext;

	}

	/**
	 * Default constructor.
	 *
	 * @param editor
	 *            the {@link MEEditor}
	 * @param id
	 *            the {@link FormPage#id}
	 * @param title
	 *            the title
	 * @param modelElementContext
	 *            the {@link ECPEditorContext}
	 * @param modelElement
	 *            the modelElement
	 * @param problemFeature
	 *            the problemFeature
	 */
	public MEEditorPage(MEEditor editor, String id, String title, ECPEditorContext modelElementContext,
		EObject modelElement, EStructuralFeature problemFeature) {
		this(editor, id, title, modelElementContext, modelElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		shortLabelProvider = new ShortLabelProvider(composedAdapterFactory);
		final FormToolkit toolkit = getEditor().getToolkit();
		form = managedForm.getForm();
		form.setShowFocusedControl(true);
		toolkit.decorateFormHeading(form.getForm());
		final Composite body = form.getBody();
		body.setLayout(new GridLayout());
		body.setBackground(body.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		body.setBackgroundMode(SWT.INHERIT_FORCE);

		final EObject domainObject = modelElementContext.getDomainObject();
		final VView view = ViewProviderHelper.getView(domainObject, null);
		final ViewModelContext vmc = ViewModelContextFactory.INSTANCE.createViewModelContext(view, domainObject,
			new ECPReferenceServiceImpl(), new ECPDeleteServiceImpl());
		try {
			ecpView = ECPSWTViewRenderer.INSTANCE.render(body, vmc);
		} catch (final ECPRendererException ex) {
			Activator.getDefault().getReportService().report(new RenderingFailedReport(ex));
		}

		form.setImage(shortLabelProvider.getImage(modelElementContext.getDomainObject()));
		createToolbar();
		form.pack();
		updateSectionTitle();

	}

	/**
	 * Updates the name of the section.
	 */
	public void updateSectionTitle() {
		// Layout form

		String name = shortLabelProvider.getText(modelElementContext.getDomainObject());

		name += " [" + modelElementContext.getDomainObject().eClass().getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		try {
			form.setText(name);
		} catch (final SWTException e) {
			// Catch in case editor is closed directly after change
		}
	}

	private void createToolbar() {
		final IMenuService menuService = (IMenuService) PlatformUI.getWorkbench().getService(IMenuService.class);
		// sourceProvider = new AbstractSourceProvider() {
		// public void dispose() {
		// }
		//
		// @SuppressWarnings("rawtypes")
		// public Map getCurrentState() {
		// HashMap<Object, Object> map = new HashMap<Object, Object>();
		// map.put(activeModelelement, modelElementContext.getModelElement());
		// return map;
		// }
		//
		// public String[] getProvidedSourceNames() {
		// String[] namens = new String[1];
		// namens[0] = activeModelelement;
		// return namens;
		// }
		//
		// };
		//
		// service = (IEvaluationService) PlatformUI.getWorkbench()
		// .getService(IEvaluationService.class);
		// service.addSourceProvider(sourceProvider);

		form.getToolBarManager().add(new Action("", Activator.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE)) { //$NON-NLS-1$

			@Override
			public void run() {
				final EditingDomain editingDomain = AdapterFactoryEditingDomain
					.getEditingDomainFor(modelElementContext
						.getDomainObject());
				new ECPCommand(modelElementContext.getDomainObject(), editingDomain) {

					@Override
					protected void doRun() {
						EcoreUtil.delete(modelElementContext.getDomainObject(), true);
					}

				}.run(true);

				MEEditorPage.this.getEditor().close(true);
			}
		});
		menuService.populateContributionManager((ContributionManager) form.getToolBarManager(),
			TOOLBAR_ORG_ECLIPSE_EMF_ECP_EDITOR_INTERNAL_E3_ME_EDITOR_PAGE);
		form.getToolBarManager().update(true);
	}

	/**
	 * {@inheritDoc} This method is added to solve the focus bug of navigator. Every time that a ME is opened in editor,
	 * navigator has to lose focus so that its action contributions are set correctly for next time.
	 */
	@Override
	public void setFocus() {
		// TODO
		// editorPageContent.focus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		if (ecpView != null) {
			ecpView.dispose();
		}
		if (composedAdapterFactory != null) {
			composedAdapterFactory.dispose();
		}
		if (shortLabelProvider != null) {
			shortLabelProvider.dispose();
		}
		if (form != null) {
			form.dispose();
		}
		super.dispose();
	}
}
