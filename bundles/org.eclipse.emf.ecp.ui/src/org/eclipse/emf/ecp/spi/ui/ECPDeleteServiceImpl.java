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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.ui;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.edit.spi.DeleteService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;

/**
 * ECP specific implementation of the {@link org.eclipse.emf.ecp.edit.spi.DeleteService DeleteService}.
 *
 * @author jfaltermeier
 * @since 1.6
 *
 */
public class ECPDeleteServiceImpl implements DeleteService {

	private ECPProject ecpProject;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
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
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl#deleteElements(java.util.Collection)
	 */
	@Override
	public void deleteElements(Collection<Object> toDelete) {
		getECPProject().deleteElements(toDelete);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.EMFDeleteServiceImpl#deleteElement(java.lang.Object)
	 */
	@Override
	public void deleteElement(Object toDelete) {
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
