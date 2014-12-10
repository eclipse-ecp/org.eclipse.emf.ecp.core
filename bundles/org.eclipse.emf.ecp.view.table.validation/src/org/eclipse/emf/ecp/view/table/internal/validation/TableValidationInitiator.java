/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.internal.validation;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.table.model.DetailEditing;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;

/**
 * The TableValidationInitiator searches for Tables with an Editing Panel, and creates the necessary ViewModelContexts.
 * 
 * @author Eugen Neufeld
 * 
 */
@SuppressWarnings("restriction")
public class TableValidationInitiator implements GlobalViewModelService {

	/**
	 * A Mapping Class for tables.
	 * 
	 * @author Eugen Neufeld
	 * 
	 */
	private static class TableContextMapping {
		/**
		 * Default Constructor.
		 * 
		 * @param control The {@link VTableControl}
		 * @param context The {@link ViewModelContext}
		 */
		public TableContextMapping(VTableControl control, ViewModelContext context) {
			this.control = control;
			this.context = context;
		}

		private final VTableControl control;
		private final ViewModelContext context;
	}

	private final Map<UniqueSetting, TableContextMapping> mapping = new LinkedHashMap<UniqueSetting, TableContextMapping>();

	@Override
	public void instantiate(ViewModelContext context) {
		context.registerDomainChangeListener(new ModelChangeListener() {

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				if (notification.getRawNotification().isTouch() || mapping.isEmpty()) {
					return;
				}
				final TableContextMapping tableContextMapping = mapping.get(UniqueSetting.createSetting(
					notification.getNotifier(), notification.getStructuralFeature()));
				if (tableContextMapping == null) {
					return;
				}
				tableContextMapping.context.getChildContext((EObject) notification.getRawNotification().getNewValue(),
					tableContextMapping.control, getView(tableContextMapping.control));
			}
		});
		checkForTables(context);
	}

	@SuppressWarnings("unchecked")
	private void checkForTables(ViewModelContext context) {
		final EObject viewRoot = context.getViewModel();
		final TreeIterator<EObject> eAllContents = viewRoot.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (VTableControl.class.isInstance(eObject)) {
				final VTableControl tableControl = VTableControl.class.cast(eObject);

				if (tableControl.getDetailEditing() == DetailEditing.WITH_PANEL) {
					final Iterator<Setting> iterator = tableControl
						.getDomainModelReference().getIterator();
					final Setting tableSetting = iterator.next();
					mapping.put(UniqueSetting.createSetting(tableSetting), new TableContextMapping(tableControl,
						context));
					final EList<EObject> tableContents = (EList<EObject>) tableSetting.get(true);
					for (final EObject tableEObject : tableContents) {
						final VView detailView = getView(tableControl);
						final ViewModelContext childContext = context.getChildContext(tableEObject, tableControl,
							detailView);
						childContext.addContextUser(this);
					}

				}
			}
		}
	}

	private VView getView(VTableControl tableControl) {
		VView detailView = tableControl.getDetailView();
		if (detailView == null) {
			final Setting setting = tableControl.getDomainModelReference()
				.getIterator().next();
			final EReference reference = (EReference) setting
				.getEStructuralFeature();
			detailView = ViewProviderHelper.getView(
				EcoreUtil.create(reference.getEReferenceType()),
				Collections.<String, Object> emptyMap());
		}

		return EcoreUtil.copy(detailView);
	}

	@Override
	public void dispose() {
	}

	@Override
	public int getPriority() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService#childViewModelContextAdded(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void childViewModelContextAdded(ViewModelContext childContext) {
		checkForTables(childContext);
	}

}
