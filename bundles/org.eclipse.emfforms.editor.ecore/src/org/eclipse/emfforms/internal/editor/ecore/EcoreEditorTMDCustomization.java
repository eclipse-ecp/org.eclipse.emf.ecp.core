/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

import java.util.Collections;

import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emfforms.internal.editor.ecore.helpers.EcoreHelpers;
import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeMasterDetailCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
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
	 */
	public EcoreEditorTMDCustomization(final CreateElementCallback createElementCallback) {
		setMenu(new MenuProvider() {
			@Override
			public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
				final MenuManager menuMgr = new MenuManager();
				menuMgr.setRemoveAllWhenShown(true);
				menuMgr.addMenuListener(new EcoreEditorMenuListener(new ChildrenDescriptorCollector(), menuMgr,
					treeViewer, editingDomain, Collections.<MasterDetailAction> emptyList(), createElementCallback));
				final Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
				return menu;
			}
		});
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
