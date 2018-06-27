/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.wizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * The WizardNewFileCreationPage to create a new DataTemplate.
 *
 * @author Eugen Neufeld
 *
 */
public class WizardNewDataTemplateCreationPage extends WizardNewFileCreationPage {

	/**
	 * Default Constructor.
	 *
	 * @param pageName The name of the page
	 * @param selection The initial selection
	 */
	public WizardNewDataTemplateCreationPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setFileExtension("datatemplate"); //$NON-NLS-1$
	}

	@Override
	protected InputStream getInitialContents() {
		try {
			final TemplateCollection collection = DataTemplateFactory.eINSTANCE.createTemplateCollection();
			final ResourceSet rs = new ResourceSetImpl();
			final Resource r = rs.createResource(URI.createURI("VIRTUAL")); //$NON-NLS-1$
			r.getContents().add(collection);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			r.save(bos, null);
			final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			return bis;
		} catch (final IOException ex) {
			// should not happen
			final Bundle bundle = FrameworkUtil.getBundle(WizardNewDataTemplateCreationPage.class);
			Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(), ex.getMessage(), ex));
		}
		return null;
	}

}
