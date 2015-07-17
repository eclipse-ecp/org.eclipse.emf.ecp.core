/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common.edit.provider;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

/**
 * @author Alexandra Buzila
 *
 */
/**
 * This is the factory that is used to provide the interfaces needed to support Viewers reflectively.
 */
public class CustomReflectiveItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory
{

	/**
	 * This constructs an instance.
	 */
	public CustomReflectiveItemProviderAdapterFactory()
	{
		reflectiveItemProviderAdapter = new CustomReflectiveItemProvider(this);

		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemPropertySource.class);
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(ITableItemLabelProvider.class);
	}

	@Override
	public Adapter createAdapter(Notifier target)
	{
		return reflectiveItemProviderAdapter;
	}

	@Override
	public void dispose()
	{
		if (reflectiveItemProviderAdapter != null)
		{
			reflectiveItemProviderAdapter.dispose();
		}
	}
}
