/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.test;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescription;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Jonas
 * 
 */
public class CustomControlStub extends ECPAbstractCustomControlSWT implements ECPHardcodedReferences {

	public static final String LABEL_TEXT = "labelText";
	private static Label label;
	private static Composite parent;

	public CustomControlStub() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.ui.ECPAbstractCustomControl#disposeCustomControl()
	 */
	@Override
	protected void disposeCustomControl() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the label
	 */
	public static Label getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public static void setLabel(Label label) {
		CustomControlStub.label = label;
	}

	/**
	 * @return the parent
	 */
	public static Composite getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public static void setParent(Composite parent) {
		CustomControlStub.parent = parent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences#getNeededDomainModelReferences()
	 */
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#handleContentValidation()
	 */
	@Override
	protected void handleContentValidation() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#getGridDescription()
	 */
	@Override
	public GridDescription getGridDescription() {
		// TODO Auto-generated method stub
		return GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.GridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Control renderControl(GridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		setParent(parent);
		setLabel(new Label(parent, SWT.NONE));
		return label;
	}

}
