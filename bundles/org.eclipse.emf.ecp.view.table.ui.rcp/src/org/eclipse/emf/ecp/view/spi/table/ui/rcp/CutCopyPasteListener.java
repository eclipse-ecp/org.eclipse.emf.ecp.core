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

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.command.CutToClipboardCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
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

	private EObject parent;
	private EStructuralFeature feature;

	/**
	 * Constructs this listener.
	 *
	 * @param tableViewer the {@link TableViewer}
	 * @param editingDomain the {@link EditingDomain} (contains the used clipboard)
	 * @param setting the parent EObject on which the paste will be performed
	 */
	public CutCopyPasteListener(TableViewer tableViewer, EditingDomain editingDomain, Setting setting) {
		parent = setting.getEObject();
		feature = setting.getEStructuralFeature();
		cutAction = new CutAction(editingDomain) {
			@Override
			public Command createCommand(Collection<?> selection) {
				return CutToClipboardCommand.create(domain, parent, feature, selection);
			}
		};
		copyAction = new CopyAction(editingDomain);
		pasteAction = new PasteAction(editingDomain) {
			@Override
			public Command createCommand(Collection<?> selection) {
				if (selection.size() == 1) {
					return PasteFromClipboardCommand.create(domain, parent, feature);
				}
				return UnexecutableCommand.INSTANCE;
			}
		};
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

	/**
	 * Called to notify this listener that the domain model has changed.
	 *
	 * @param setting the new table setting
	 */
	public void rootDomainModelChanged(Setting setting) {
		parent = setting.getEObject();
		feature = setting.getEStructuralFeature();
	}

}