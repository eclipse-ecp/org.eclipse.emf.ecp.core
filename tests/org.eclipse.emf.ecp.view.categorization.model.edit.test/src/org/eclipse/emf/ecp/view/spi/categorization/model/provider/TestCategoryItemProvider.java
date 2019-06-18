/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.model.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecp.common.spi.ChildrenDescriptorCollector;

/**
 * Test implementation allowing to provide a custom/mocked {@link ChildrenDescriptorCollector}.
 *
 * @author Lucas Koehler
 *
 */
public class TestCategoryItemProvider extends CategoryItemProvider {

	private ChildrenDescriptorCollector collector;

	public TestCategoryItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	protected ChildrenDescriptorCollector getChildrenDescriptorCollector() {
		return collector;
	}

	protected void setChildrenDescriptorCollector(ChildrenDescriptorCollector collector) {
		this.collector = collector;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.provider.CategoryItemProvider#collectNewChildDescriptors(java.util.Collection,
	 *      java.lang.Object)
	 */
	@Override
	public void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.provider.CategoryItemProvider#collectContainerChildDecriptors(java.util.Collection,
	 *      java.lang.Object)
	 */
	@Override
	public void collectContainerChildDecriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectContainerChildDecriptors(newChildDescriptors, object);
	}

}
