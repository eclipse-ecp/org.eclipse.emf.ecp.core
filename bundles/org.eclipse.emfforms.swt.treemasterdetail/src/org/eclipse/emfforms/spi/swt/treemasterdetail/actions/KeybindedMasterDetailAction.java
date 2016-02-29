/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * Extended {@link MasterDetailAction} to help introduce keybindings.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public abstract class KeybindedMasterDetailAction extends MasterDetailAction
	implements KeyListener, ISelectionChangedListener {

	private TreeViewer registeredTreeViewer;
	private ISelection currentSelection;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction#setTreeViewer(org.eclipse.jface.viewers.TreeViewer)
	 */
	@Override
	public void setTreeViewer(TreeViewer treeviewer) {
		super.setTreeViewer(treeviewer);
		if (registeredTreeViewer != null) {
			registeredTreeViewer.getTree().removeKeyListener(this);
			registeredTreeViewer.removeSelectionChangedListener(this);
		}
		treeviewer.getTree().addKeyListener(this);
		treeviewer.addSelectionChangedListener(this);
		registeredTreeViewer = treeviewer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		currentSelection = event.getSelection();
	}

	/**
	 * Returns the current {@link ISelection}.
	 *
	 * @return
	 * 		The current {@link ISelection} if there is one, {@code null} otherwise.
	 */
	protected ISelection getCurrentSelection() {
		return currentSelection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (isExecuteOnKeyPressed(event)) {
			executeOnKeyPressed(currentSelection);
		}
	}

	/**
	 * Determines if the {@link #executeOnKeyPressed(ISelection)} method shall be executed.
	 *
	 * @param event
	 *            The {@link KeyEvent} which triggers this method.
	 * @return
	 *         {@code true} if {@link #executeOnKeyPressed(ISelection)} method shall be called, {@code false} otherwise.
	 */
	protected boolean isExecuteOnKeyPressed(KeyEvent event) {
		return false;
	}

	/**
	 * This method is triggered by {@link #isExecuteOnKeyPressed(KeyEvent)} when a {@link KeyEvent} is triggered by a
	 * key press.
	 *
	 * @param currentSelection
	 *            The current {@link ISelection}.
	 */
	protected void executeOnKeyPressed(ISelection currentSelection) {
		// no op
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		if (isExecuteOnKeyRelease(event)) {
			executeOnKeyRelease(currentSelection);
		}
	}

	/**
	 * Determines if the {@link #executeOnKeyRelease(ISelection)} method shall be executed.
	 *
	 * @param event
	 *            The {@link KeyEvent} which triggers this method.
	 * @return
	 *         {@code true} if {@link #executeOnKeyRelease(ISelection)} method shall be called, {@code false} otherwise.
	 */
	protected abstract boolean isExecuteOnKeyRelease(KeyEvent event);

	/**
	 * This method is triggered by {@link #isExecuteOnKeyRelease(KeyEvent)} when a {@link KeyEvent} is triggered by a
	 * key release.
	 *
	 * @param currentSelection
	 *            The current {@link ISelection}.
	 */
	protected abstract void executeOnKeyRelease(ISelection currentSelection);

	/**
	 * Determines if the keys indicated by the SWT {@code swtMask} and {@code c} are active.
	 *
	 * @param event
	 *            The {@link KeyEvent} to check.
	 * @param swtMask
	 *            SWT key event mask, e.g. {@link org.eclipse.swt.SWT.CTRL SWT.CTRL},
	 *            {@link org.eclipse.swt.SWT.ALT SWT.ALT} etc.
	 * @param c
	 *            The additional pressed char. Use {@link KeyEvent#keyCode} if you only want to check for
	 *            {@code swtMask}.
	 * @return
	 *         {@code true} if the keys indicated by {@code swtMask} and {@code c} are active, {@code false} otherwise.
	 */
	protected static boolean isActivated(KeyEvent event, int swtMask, char c) {
		return (event.stateMask & swtMask) == swtMask && event.keyCode == c;
	}
}
