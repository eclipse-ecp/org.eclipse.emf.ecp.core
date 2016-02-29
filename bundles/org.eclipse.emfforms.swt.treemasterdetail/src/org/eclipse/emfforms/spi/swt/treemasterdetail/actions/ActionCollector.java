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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating.CopyMasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating.CutMasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating.PasteMasterDetailAction;

/**
 * Helper class to dot-link the creation of {@link MasterDetailAction} collections.
 *
 * @author Stefan Dirix
 * @since 1.8
 *
 */
public class ActionCollector {

	private final List<MasterDetailAction> list;

	/**
	 * Constructor.
	 */
	public ActionCollector() {
		list = new LinkedList<MasterDetailAction>();
	}

	/**
	 * Adds the given {@link MasterDetailAction} to the collection.
	 *
	 * @param action
	 *            The {@link MasterDetailAction} to add.
	 * @return
	 * 		self.
	 */
	public ActionCollector add(MasterDetailAction action) {
		list.add(action);
		return this;
	}

	/**
	 * Adds the given collection of {@link MasterDetailAction}s to the collection.
	 *
	 * @param collection
	 *            The collection of {@link MasterDetailAction}s to add.
	 * @return
	 * 		self.
	 */
	public ActionCollector addAll(Collection<? extends MasterDetailAction> collection) {
		list.addAll(collection);
		return this;
	}

	/**
	 * Adds the {@link CopyMasterDetailAction} to the collection.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} used to create the {@link CopyMasterDetailAction}.
	 * @return
	 * 		self.
	 */
	public ActionCollector addCopyAction(EditingDomain editingDomain) {
		list.add(new CopyMasterDetailAction(editingDomain));
		return this;
	}

	/**
	 * Adds the {@link PasteMasterDetailAction} to the collection.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} used to create the {@link PasteMasterDetailAction}.
	 * @return
	 * 		self.
	 */
	public ActionCollector addPasteAction(EditingDomain editingDomain) {
		list.add(new PasteMasterDetailAction(editingDomain));
		return this;
	}

	/**
	 * Adds the {@link CutMasterDetailAction} to the collection.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} used to create the {@link CutMasterDetailAction}.
	 * @return
	 * 		self.
	 */
	public ActionCollector addCutAction(EditingDomain editingDomain) {
		list.add(new CutMasterDetailAction(editingDomain));
		return this;
	}

	/**
	 * Returns the collected list of {@link MasterDetailAction}s.
	 *
	 * @return
	 * 		The collection of {@link MasterDetailAction}s.
	 */
	public List<MasterDetailAction> getList() {
		return list;
	}

	/**
	 * Start a new collection.
	 *
	 * @return
	 * 		A new {@link ActionCollector}.
	 */
	public static ActionCollector newList() {
		return new ActionCollector();
	}
}
