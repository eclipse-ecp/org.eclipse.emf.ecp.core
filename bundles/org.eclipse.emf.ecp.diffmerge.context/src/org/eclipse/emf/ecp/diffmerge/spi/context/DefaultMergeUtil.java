/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.spi.context;

import java.util.Collection;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.diffmerge.internal.context.Activator;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * This util class provides a method, which allows to copy values from one {@link VControl} to another generically.
 *
 * @author Eugen Neufeld
 *
 */
public final class DefaultMergeUtil {

	private DefaultMergeUtil() {

	}

	/**
	 * Copies the values from one {@link VControl} to another.
	 *
	 * @param from the {@link VControl} holding the values
	 * @param fromDomainModel The domain model belonging to the 'from control'.
	 * @param to the {@link VControl} which values should be updated
	 * @param toDomainModel The domain model belonging to the 'to control'.
	 * @since 1.6
	 */
	@SuppressWarnings("unchecked")
	public static void copyValues(VControl from, EObject fromDomainModel, VControl to, EObject toDomainModel) {
		final IObservableValue fromObservableValue;
		final IObservableValue toObservableValue;
		try {
			fromObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(from.getDomainModelReference(), fromDomainModel);
			toObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(to.getDomainModelReference(), toDomainModel);
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return;
		}
		final EObject fromEObject = (EObject) ((IObserving) fromObservableValue).getObserved();
		final EStructuralFeature fromStructuralFeature = (EStructuralFeature) fromObservableValue.getValueType();
		final EObject toEObject = (EObject) ((IObserving) toObservableValue).getObserved();
		final EStructuralFeature toStructuralFeature = (EStructuralFeature) toObservableValue.getValueType();

		fromObservableValue.dispose();
		toObservableValue.dispose();

		if (!toStructuralFeature.isChangeable()) {
			return;
		}

		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(toEObject);

		if (toStructuralFeature.isMany()) {
			editingDomain.getCommandStack().execute(
				RemoveCommand.create(editingDomain, toEObject, toStructuralFeature,
					(Collection<?>) toEObject.eGet(toStructuralFeature, true)));
		}
		if (EAttribute.class.isInstance(toStructuralFeature)) {

			if (toStructuralFeature.isMany()) {
				editingDomain.getCommandStack().execute(
					AddCommand.create(editingDomain, toEObject, toStructuralFeature,
						(Collection<?>) fromEObject.eGet(fromStructuralFeature, true)));
			}
			else {
				editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain, toEObject, toStructuralFeature,
						fromEObject.eGet(fromStructuralFeature, true)));
			}
		}
		if (EReference.class.isInstance(toStructuralFeature)) {
			if (toStructuralFeature.isMany()) {
				for (final EObject eObject : (Collection<EObject>) fromEObject.eGet(fromStructuralFeature, true)) {
					editingDomain.getCommandStack().execute(
						AddCommand.create(editingDomain, toEObject, toStructuralFeature, EcoreUtil.copy(eObject)));
				}
				return;
			}
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, toEObject, toStructuralFeature,
					EcoreUtil.copy((EObject) fromEObject.eGet(fromStructuralFeature, true))));

		}
	}
}
