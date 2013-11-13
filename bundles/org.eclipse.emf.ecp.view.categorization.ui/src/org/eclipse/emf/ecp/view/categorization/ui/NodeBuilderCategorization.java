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
package org.eclipse.emf.ecp.view.categorization.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecp.internal.ui.view.builders.CustomNodeBuilder;
import org.eclipse.emf.ecp.internal.ui.view.builders.NodeBuilder;
import org.eclipse.emf.ecp.view.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.model.VElement;

/**
 * The Class NodeBuilderGroup.
 */
public class NodeBuilderCategorization implements CustomNodeBuilder {

	/**
	 * Instantiates a new node builder group.
	 */
	public NodeBuilderCategorization() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.internal.ui.view.builders.CustomNodeBuilder#getCustomNodeBuilders()
	 */
	public Map<Class<? extends VElement>, NodeBuilder<? extends VElement>> getCustomNodeBuilders() {

		final Map<Class<? extends VElement>, NodeBuilder<? extends VElement>> builders = new HashMap<Class<? extends VElement>, NodeBuilder<? extends VElement>>();
		builders.put(VCategorizationElement.class, new CategorizationElementNodeBuilder());
		builders.put(VCategorization.class, new CategorizationNodeBuilder());
		builders.put(VCategory.class, new CategoryNodeBuilder());
		return builders;
	}

}
