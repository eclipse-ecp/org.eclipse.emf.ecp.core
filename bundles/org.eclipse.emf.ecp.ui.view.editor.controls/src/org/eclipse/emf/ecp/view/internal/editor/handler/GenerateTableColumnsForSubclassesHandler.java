/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.internal.editor.controls.Activator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author jfaltermeier
 *
 */
public class GenerateTableColumnsForSubclassesHandler extends MasterDetailAction {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Object selection = ((IStructuredSelection) HandlerUtil.getActiveMenuSelection(event)).getFirstElement();
		if (selection == null || !(selection instanceof EObject)) {
			return null;
		}
		execute((EObject) selection);
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#shouldShow(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean shouldShow(EObject eObject) {
		if (VTableControl.class.isInstance(eObject)) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.MasterDetailAction#execute(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void execute(EObject object) {
		final VTableControl tableControl = VTableControl.class.cast(object);
		final VDomainModelReference domainModelReference = tableControl.getDomainModelReference();
		if (domainModelReference == null || !VTableDomainModelReference.class.isInstance(domainModelReference)) {
			return;
		}

		final VTableDomainModelReference tableDMR = (VTableDomainModelReference) domainModelReference;
		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding().getValueProperty(tableDMR, null);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return;
		}
		final Object eStructuralFeature = valueProperty.getValueType();
		if (!EReference.class.isInstance(eStructuralFeature)) {
			return;
		}
		final EReference eReference = (EReference) eStructuralFeature;

		final Set<EStructuralFeature> existingFeatures = new LinkedHashSet<EStructuralFeature>();
		for (final VDomainModelReference ref : tableDMR.getColumnDomainModelReferences()) {
			final VFeaturePathDomainModelReference featureDMR = (VFeaturePathDomainModelReference) ref;
			existingFeatures.add(featureDMR.getDomainModelEFeature());
		}
		final Set<VDomainModelReference> referencesToAdd = new LinkedHashSet<VDomainModelReference>();
		final EClass subclass = getSubclass(Display.getDefault().getActiveShell(), eReference);
		if (subclass == null) {
			return;
		}
		for (final EAttribute attribute : subclass.getEAllAttributes()) {
			if (existingFeatures.contains(attribute)) {
				continue;
			}
			final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			dmr.setDomainModelEFeature(attribute);
			referencesToAdd.add(dmr);

		}
		final EditingDomain editingDomainFor = AdapterFactoryEditingDomain.getEditingDomainFor(object);
		final Command command = AddCommand.create(editingDomainFor, tableDMR,
			VTablePackage.eINSTANCE.getTableDomainModelReference_ColumnDomainModelReferences(), referencesToAdd);
		editingDomainFor.getCommandStack().execute(command);

	}

	private EClass getSubclass(Shell shell, EReference reference) {
		EClass eclass = reference.getEReferenceType();
		final Collection<EClass> subClasses = EMFUtils.getSubClasses(eclass);

		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(
			adapterFactory);

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setTitle("Select EClass"); //$NON-NLS-1$
		dialog.setMessage("Select a subclass to generate columns for all attributes from this EClass."); //$NON-NLS-1$
		dialog.setMultipleSelection(false);
		dialog.setElements(subClasses.toArray(new EClass[subClasses.size()]));

		if (dialog.open() == Window.OK) {
			final Object firstResult = dialog.getFirstResult();
			if (firstResult != null) {
				eclass = (EClass) firstResult;
			}
		} else {
			eclass = null;
		}

		labelProvider.dispose();
		adapterFactory.dispose();

		return eclass;
	}
}
