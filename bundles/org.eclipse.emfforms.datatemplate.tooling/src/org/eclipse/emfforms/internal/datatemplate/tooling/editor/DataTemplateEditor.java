/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.datatemplate.tooling.editor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emfforms.datatemplate.TemplateCollection;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;

/**
 * The DataTemplateEditor for editing {@link TemplateCollection}.
 *
 * @author Eugen Neufeld
 *
 */
public class DataTemplateEditor extends GenericEditor {

	@Override
	protected boolean enableValidation() {
		return true;
	}

	@Override
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		/* this access is save, otherwise we would have thrown a part init exception in init */
		final TemplateCollection collection = TemplateCollection.class
			.cast(resourceSet.getResources().get(0).getContents().get(0));
		return new RootObject(collection);
	}

}
