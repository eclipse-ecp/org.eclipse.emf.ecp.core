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
package org.eclipse.emfforms.internal.editor.ecore;

import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emfforms.internal.editor.ecore.actions.CreateNewInstanceAction;
import org.eclipse.emfforms.internal.editor.ecore.helpers.EcoreHelpers;
import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeMasterDetailCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ViewModelServiceProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.ActionCollector;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.decorator.validation.ecp.ECPValidationLabelDecoratorProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

/**
 * Ecore Editor specific behaviour for the TMD.
 *
 * @author Johannes Faltermeier
 *
 */
public class EcoreEditorTMDCustomization extends DefaultTreeMasterDetailCustomization {

	private AdapterFactoryContentProvider adapterFactoryContentProvider;

	/**
	 * Constructs a new {@link EcoreEditorTMDCustomization}.
	 *
	 * @param createElementCallback the {@link CreateElementCallback}
	 * @param notifier The Notifier to create the customization for
	 * @param diagnosticCache the {@link org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache
	 *            DiagnosticCache}
	 */
	public EcoreEditorTMDCustomization(final CreateElementCallback createElementCallback, Notifier notifier,
		EcoreDiagnosticCache diagnosticCache) {
		setMenu(new MenuProvider() {
			@Override
			public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
				final MenuManager menuMgr = new MenuManager();
				final List<MasterDetailAction> masterDetailActions = ActionCollector.newList()
					.addCutAction(editingDomain).addCopyAction(editingDomain).addPasteAction(editingDomain)
					.add(new CreateNewInstanceAction()).getList();

				menuMgr.setRemoveAllWhenShown(true);
				menuMgr.addMenuListener(new EcoreEditorMenuListener(new ChildrenDescriptorCollector(), menuMgr,
					treeViewer, editingDomain, masterDetailActions, createElementCallback));
				final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
				return menu;
			}
		});

		setViewModelServices(new ViewModelServiceProvider() {

			@Override
			public ViewModelService[] getViewModelServices(VView view, EObject eObject) {
				return new ViewModelService[] { new GroupExpansionViewModelService() };
			}
		});

		setLabelDecorator(new ECPValidationLabelDecoratorProvider(notifier, diagnosticCache));
	}

	@Override
	protected AdapterFactoryContentProvider getAdapterFactoryContentProvider() {
		if (adapterFactoryContentProvider == null) {
			final ComposedAdapterFactory adapterFactory = getComposedAdapterFactory();
			adapterFactoryContentProvider = new AdapterFactoryContentProvider(
				adapterFactory) {

				@Override
				public Object[] getElements(Object object) {
					if (RootObject.class.isInstance(object)) {
						return new Object[] { RootObject.class.cast(object).getRoot() };
					}
					return super.getElements(object);
				}

				@Override
				public boolean hasChildren(Object object) {
					return getChildren(object).length > 0;
				}

				@Override
				public Object[] getChildren(Object object) {
					return EcoreHelpers.filterGenericElements(super.getChildren(object));
				}
			};
		}
		return adapterFactoryContentProvider;
	}

	@Override
	public void dispose() {
		if (adapterFactoryContentProvider != null) {
			adapterFactoryContentProvider.dispose();
		}
		super.dispose();
	}

}
