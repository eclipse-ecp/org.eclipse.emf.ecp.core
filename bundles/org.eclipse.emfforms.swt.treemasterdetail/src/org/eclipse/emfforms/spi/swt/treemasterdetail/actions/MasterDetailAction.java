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
 * Johannes Faltermeier - Bug 500895
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * A MasterDetailAction may be added to the context menu of a
 * {@link org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite TreeMasterDetailComposite}.
 *
 * @author Alexandra Buzila
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public abstract class MasterDetailAction extends AbstractHandler {
	private String label, imagePath;
	private TreeViewer treeviewer;

	/**
	 * @param objects the list of objects on which to test if the action can be executed
	 * @return <b>true</b> if the action can be executed on the parameter
	 * @since 1.10
	 */
	public abstract boolean shouldShow(List<Object> objects);

	/**
	 * @param objects The list of objects on which the action is executed
	 * @since 1.10
	 **/
	public abstract void execute(List<Object> objects);

	/**
	 * @return the label of the action
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label of the action
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the path to the image
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Sets the treeviewer of the tree master detail composite.
	 *
	 * @param treeviewer the tree
	 */
	public void setTreeViewer(TreeViewer treeviewer) {
		this.treeviewer = treeviewer;
	}

	/**
	 * Returns the treeviewer renderered by the tree master detail composite.
	 *
	 * @return the tree
	 */
	protected TreeViewer getTreeViewer() {
		return treeviewer;
	}
}
