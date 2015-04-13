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
package org.eclipse.emf.ecp.spi.ui;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * ECP specific implementation of the {@link org.eclipse.emf.ecp.edit.spi.DeleteService DeleteService}.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public class ECPDeleteServiceImpl extends EMFDeleteServiceImpl {

	private ECPProject ecpProject;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		super.instantiate(context);
		ecpProject = ECPUtil.getECPProjectManager().getProject(context.getDomainModel());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		ecpProject = null;
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return super.getPriority() - 1;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl#deleteElements(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.util.Collection)
	 */
	@Override
	public void deleteElements(EditingDomain editingDomain, Collection<Object> toDelete) {
		getECPProject().deleteElements(toDelete);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl#deleteElement(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.lang.Object)
	 */
	@Override
	public void deleteElement(EditingDomain editingDomain, Object toDelete) {
		getECPProject().deleteElements(Collections.singleton(toDelete));
	}

	/**
	 * Returns the ECPProject.
	 *
	 * @return the project
	 */
	ECPProject getECPProject() {
		return ecpProject;
	}

}
