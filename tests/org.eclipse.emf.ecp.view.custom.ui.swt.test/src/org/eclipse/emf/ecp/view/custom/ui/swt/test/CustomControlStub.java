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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.internal.ui.view.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.ui.view.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.model.VDomainModelReference;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Jonas
 * 
 */
public class CustomControlStub extends ECPAbstractCustomControlSWT {

	public static final String LABEL_TEXT = "labelText";
	private static Label label;
	private static Composite parent;

	public CustomControlStub() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.custom.swt.ECPAbstractCustomControlSWT#createControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public List<RenderingResultRow<Control>> createControl(Composite composite) {
		final List<RenderingResultRow<Control>> result = new ArrayList<RenderingResultRow<Control>>();

		setParent(composite);
		setLabel(new Label(composite, SWT.NONE));

		result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
			.createRenderingResultRow(label));

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.custom.swt.ECPAbstractCustomControlSWT#handleContentValidation(int,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	protected void handleContentValidation(int severity, EStructuralFeature feature) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.custom.ECPAbstractCustomControl#disposeCustomControl()
	 */
	@Override
	protected void disposeCustomControl() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.ui.view.custom.swt.ECPAbstractCustomControlSWT#resetContentValidation()
	 */
	@Override
	protected void resetContentValidation() {
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
	 * @see org.eclipse.emf.ecp.view.custom.model.ECPHardcodedReferences#getNeededDomainModelReferences()
	 */
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

}
