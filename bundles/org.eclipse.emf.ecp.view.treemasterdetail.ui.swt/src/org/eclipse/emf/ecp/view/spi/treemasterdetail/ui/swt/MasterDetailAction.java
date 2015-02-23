/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * @author Alexandra Buzila
 * @since 1.5
 *
 */
public abstract class MasterDetailAction extends AbstractHandler {
	private String label, imagePath;
	private TreeViewer treeviewer;

	/**
	 * @param eObject the {@link EObject} on which to test if the action can be executed
	 * @return <b>true</b> if the action can be executed on the parameter {@code eObject}
	 */
	public abstract boolean shouldShow(EObject eObject);

	/**
	 * @param object The {@link EObject} on which the action is executed
	 **/
	public abstract void execute(EObject object);

	/**
	 * @return the label of the action
	 */
	String getLabel() {
		return label;
	}

	/**
	 * @param label the label of the action
	 */
	void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the imagePath
	 */
	String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the path to the image
	 */
	void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Sets the treeviewer of the tree master detail renderer.
	 * 
	 * @param treeviewer the tree
	 */
	void setTreeViewer(TreeViewer treeviewer) {
		this.treeviewer = treeviewer;
	}

	/**
	 * Returns the treeviewer renderered by the tree master detail renderer.
	 * 
	 * @return the tree
	 */
	protected TreeViewer getTreeViewer() {
		return treeviewer;
	}
}
