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
package org.eclipse.emf.ecp.view.spi.table.ui.rcp;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * On creation this listener registers itself on a given {@link TableViewer} and register the according EMF
 * Cut/Copy/Paste-Actions. They are triggered using keybindings.
 *
 * @author Johannes Faltermeier
 *
 */
public class CutCopyPasteListener implements KeyListener, ISelectionChangedListener {

	private final CutAction cutAction;
	private final CopyAction copyAction;
	private final PasteAction pasteAction;

	private final EObject parent;

	/**
	 * Constructs this listener.
	 *
	 * @param tableViewer the {@link TableViewer}
	 * @param editingDomain the {@link EditingDomain} (contains the used clipboard)
	 * @param parent the parent EObject on which the paste will be performed
	 */
	public CutCopyPasteListener(TableViewer tableViewer, EditingDomain editingDomain, EObject parent) {
		this.parent = parent;
		cutAction = new CutAction(editingDomain);
		copyAction = new CopyAction(editingDomain);
		pasteAction = new PasteAction(editingDomain);
		tableViewer.getTable().addKeyListener(this);
		tableViewer.addSelectionChangedListener(this);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		final IStructuredSelection currentSelection = event.getSelection() instanceof IStructuredSelection
			? (IStructuredSelection) event.getSelection() : new StructuredSelection();
		cutAction.selectionChanged(currentSelection);
		copyAction.selectionChanged(currentSelection);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/* no op */
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isActivated(e, SWT.CTRL, 'x')) {
			execute(cutAction);
		} else if (isActivated(e, SWT.CTRL, 'c')) {
			execute(copyAction);
		} else if (isActivated(e, SWT.CTRL, 'v')) {
			/*
			 * set selection to parent to recheck enabled state. This works only, because keybindings can be used any
			 * time. If we would also want a menu, we need to handle this differently.
			 */
			pasteAction.selectionChanged(new StructuredSelection(parent));
			execute(pasteAction);
		}
	}

	private static void execute(BaseSelectionListenerAction delegatedAction) {
		if (!delegatedAction.isEnabled()) {
			return;
		}
		delegatedAction.run();
	}

	private static boolean isActivated(KeyEvent event, int swtMask, char c) {
		return (event.stateMask & swtMask) == swtMask && event.keyCode == c;
	}

}