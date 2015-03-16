/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.group.swt;

import java.util.Collection;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.internal.group.swt.GroupTextProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Renders a SWT group.
 *
 * @since 1.5
 *
 */
public class GroupSWTRenderer extends ContainerSWTRenderer<VGroup> {
	private static final String CONTROL_GROUP = "org_eclipse_emf_ecp_ui_control_group"; //$NON-NLS-1$
	private final EMFDataBindingContext dbc;

	/**
	 * Default constructor.
	 */
	public GroupSWTRenderer() {
		super();
		dbc = new EMFDataBindingContext();
	}

	/**
	 * Test constructor.
	 *
	 * @param factory the {@link SWTRendererFactory} to use.
	 */
	protected GroupSWTRenderer(SWTRendererFactory factory) {
		super(factory);
		dbc = new EMFDataBindingContext();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		return CONTROL_GROUP;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */

	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite getComposite(Composite parent) {
		final Group group = new Group(parent, SWT.TITLE);
		final IObservableValue modelValue = EMFEditObservables.observeValue(
			AdapterFactoryEditingDomain.getEditingDomainFor(getVElement()), getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		final IObservableValue targetValue = new GroupTextProperty().observe(group);
		// final IObservableValue targetValue = SWTObservables.observeText(group);

		dbc.bindValue(targetValue, modelValue);

		return group;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		dbc.dispose();
		super.dispose();
	}

}
