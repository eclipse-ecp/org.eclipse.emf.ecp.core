/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.fx.internal.projects;

import javax.annotation.PostConstruct;

import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class RemoteProjectsView {

	@PostConstruct
	public void postConstruct(BorderPane parent) {
		final TreeView<Object> remoteProjectsView = new TreeView<>();
		remoteProjectsView.setShowRoot(false);
		final EmfStoreRemoteProjectTreeItem remoteProjectTreeItem = new EmfStoreRemoteProjectTreeItem(
			ESWorkspaceProvider.INSTANCE.getWorkspace());
		remoteProjectsView.setRoot(remoteProjectTreeItem);
		remoteProjectsView
			.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
				@Override
				public TreeCell<Object> call(TreeView<Object> param) {
					return new ESRemoteProjectTreeCell();
				}
			});

		parent.setCenter(remoteProjectsView);
	}
}
