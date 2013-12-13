/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.model.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.internal.wizards.SelectEStructuralFeaturesWizard;
import org.eclipse.emf.ecp.ui.common.CheckedEStructuralFeatureComposite;
import org.eclipse.emf.ecp.ui.common.CompositeFactory;
import org.eclipse.emf.ecp.view.internal.table.generator.TableColumnGenerator;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * Handler for generation the {@link org.eclipse.emf.ecp.view.spi.table.model.VTableColumn VTableColumn}s of a
 * {@link VTableControl}.
 * 
 * @author jfaltermeier
 * 
 */
public class GenerateColumnsHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof VTableControl)) {
			return null;
		}
		final VTableControl tableControl = (VTableControl) selection;
		final VFeaturePathDomainModelReferenceImpl domainModelReference = (VFeaturePathDomainModelReferenceImpl) tableControl
			.getDomainModelReference();
		if (domainModelReference == null) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Cannot generate Columns",
				"Please set the domain model reference before generating columns.");
			return null;
		}
		final EStructuralFeature feature = domainModelReference.getDomainModelEFeature();
		if (feature == null) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Cannot generate Columns",
				"Please set a feature in the domain model reference before generating columns.");
			return null;
		}

		final EClassifier eType = feature.getEType();
		if (eType instanceof EClass) {
			final EClass clazz = (EClass) eType;
			final Object[] attributesForColumns = getAttributesForColumns(clazz.getEAttributes().toArray(
				new EAttribute[clazz.getEAttributes().size()]));
			for (final Object o : attributesForColumns) {
				if (!(o instanceof EAttribute)) {
					continue;
				}
				final EAttribute a = (EAttribute) o;
				TableColumnGenerator.addColumn(a, tableControl);
			}
		}

		return null;
	}

	private Object[] getAttributesForColumns(EAttribute[] attributes) {
		final SelectEStructuralFeaturesWizard wizard = new SelectEStructuralFeaturesWizard();
		final CheckedEStructuralFeatureComposite compositeProvider = CompositeFactory
			.getCheckedTableSelectionComposite(attributes);
		wizard.setCompositeProvider(compositeProvider);

		final WizardDialog wd = new WizardDialog(Display.getDefault().getActiveShell(), wizard);
		Object[] toReturn = new Object[0];
		final int result = wd.open();
		if (result == Window.OK) {
			final Object[] selection = compositeProvider.getSelection();
			if (selection == null || selection.length == 0) {
				return toReturn;
			}
			toReturn = selection;
		}
		return toReturn;
	}

}
