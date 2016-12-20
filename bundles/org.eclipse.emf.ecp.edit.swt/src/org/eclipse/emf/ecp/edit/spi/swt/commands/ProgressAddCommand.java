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
package org.eclipse.emf.ecp.edit.spi.swt.commands;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

/**
 * {@link AddCommand} which is able to report progress.
 *
 * @author Johannes Faltermeier
 * @since 1.11
 *
 */
public class ProgressAddCommand extends AddCommand implements IProgressMonitorConsumer {

	private IProgressMonitorProvider monitor;

	/**
	 * Constructs a {@link ProgressAddCommand}.
	 *
	 * @param domain the {@link EditingDomain}
	 * @param owner the parent
	 * @param feature the feature
	 * @param collection the children to add
	 * @param index the start index
	 */
	public ProgressAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection<?> collection,
		int index) {
		super(domain, owner, feature, collection, index);
	}

	@Override
	public void setIProgressMonitorAccessor(IProgressMonitorProvider monitor) {
		this.monitor = monitor;
	}

	@Override
	public void doExecute() {
		// Simply add the collection to the list.
		//
		if (index == CommandParameter.NO_INDEX) {
			for (final Object object : collection) {
				ownerList.add(object);
				worked();
			}
		} else {
			int i = index;
			for (final Object object : collection) {
				ownerList.add(i++, object);
				worked();
			}
		}

		// Update the containing map, if necessary.
		//
		updateEMap(owner, feature);

		// We'd like the collection of things added to be selected after this command completes.
		//
		affectedObjects = collection;
	}

	private void worked() {
		final IProgressMonitor progressMonitor = monitor.getProgressMonitor();
		if (progressMonitor == null) {
			return;
		}
		progressMonitor.worked(1);
		while (Display.getDefault().readAndDispatch()) {
		}
	}

}
