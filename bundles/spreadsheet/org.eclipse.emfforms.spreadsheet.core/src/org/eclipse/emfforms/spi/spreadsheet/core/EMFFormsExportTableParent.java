/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core;

import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;

/**
 * @author Eugen
 *
 */
public class EMFFormsExportTableParent {

	/**
	 * Key to use in the ViewModelContext to set this object into the context.
	 */
	public static final String EXPORT_TABLE_PARENT = "excelExportParent"; //$NON-NLS-1$

	private final VIndexDomainModelReference indexDMRToExtend;
	private final String labelPrefix;

	private final VIndexDomainModelReference indexDMRToResolve;

	/**
	 * Default Constructor.
	 *
	 * @param indexDMRToExtend The VIndexDomainModelReference to extend
	 * @param indexDMRToResolve The VIndexDomainModelReference to use for resolvement
	 * @param labelPrefix The prefix for labels
	 */
	public EMFFormsExportTableParent(VIndexDomainModelReference indexDMRToExtend,
		VIndexDomainModelReference indexDMRToResolve, String labelPrefix) {
		super();
		this.indexDMRToExtend = indexDMRToExtend;
		this.indexDMRToResolve = indexDMRToResolve;
		this.labelPrefix = labelPrefix;
	}

	/**
	 * The VIndexDomainModelReference to use extend.
	 *
	 * @return the indexDMR
	 */
	public VIndexDomainModelReference getIndexDMRToExtend() {
		return indexDMRToExtend;
	}

	/**
	 * The Prefix to use for labels.
	 *
	 * @return the labelPrefix
	 */
	public String getLabelPrefix() {
		return labelPrefix;
	}

	/**
	 * The VIndexDomainModelReference to use for resolving.
	 *
	 * @return the indexDMRToResolve
	 */
	public VIndexDomainModelReference getIndexDMRToResolve() {
		return indexDMRToResolve;
	}

}
