/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt;

import static java.util.Collections.singleton;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**
 * <p>
 * The {@code DefaultReferenceService} is the standard implementation of the {@code ReferenceService}.
 * It is customizable by registration of {@linkplain org.eclipse.emfforms.bazaar.Vendor vendors}
 * of <em>customization strategies</em> as OSGi service components that participate in a
 * {@linkplain org.eclipse.emfforms.bazaar.Bazaar bazaar} to provide their customizations for the
 * {@link ReferenceService} operations to which they apply. In the
 * {@link org.eclipse.emfforms.bazaar.BazaarContext context} of that bazaar, the {@code DefaultReferenceService}
 * supplies supplies the following context variables for injection into <em>customization</em> vendors:
 * </p>
 * <ul>
 * <li>the {@link EObject} that owns the reference that is being edited</li>
 * <li>the {@link EReference} of the object that is being edited</li>
 * </ul>
 * <p>
 * As of the the 1.16 release, the <em>customization strategies</em> available to plug
 * application-specific behavior into the {@code DefaultReferenceService} are
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy AttachmentStrategy}</li>
 * <li>{@link org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy
 * CreateNewModelElementStrategy}</li>
 * <li>{@link org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy EObjectSelectionStrategy}</li>
 * <li>{@link org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy OpenInNewContextStrategy}</li>
 * <li>{@link org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy ReferenceStrategy}</li>
 * </ul>
 * <p>
 * For the convenience of distinguishing OSGi service components providing (as bazaar vendors) these
 * different <em>customization strategies</em>, each of these interfaces defines a {@code Provider}
 * nested interface that is the OSGi <em>Service Interface</em> binding the particular customization
 * type as the vendor <em>product</em> type parameter. See, for example, the
 * {@link org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy.Provider ReferenceStrategy.Provider} interface.
 * </p>
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 * @since 1.4
 *
 */
@SuppressWarnings("restriction")
public class DefaultReferenceService implements ReferenceService {

	private EObjectSelectionStrategy eobjectSelectionStrategy = EObjectSelectionStrategy.NULL;
	private CreateNewModelElementStrategy createNewModelElementStrategy = CreateNewModelElementStrategy.DEFAULT;
	private AttachmentStrategy attachmentStrategy = AttachmentStrategy.DEFAULT;
	private ReferenceStrategy referenceStrategy = ReferenceStrategy.DEFAULT;
	private OpenInNewContextStrategy openInNewContextStrategy = OpenInNewContextStrategy.DEFAULT;

	@Override
	public void instantiate(ViewModelContext context) {
		// Nothing to do
	}

	@Override
	public void dispose() {
		// Nothing to do
	}

	@Override
	public int getPriority() {
		return 2;
	}

	@Deprecated
	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		addNewModelElements(eObject, eReference, true);
	}

	@Override
	public Optional<EObject> addNewModelElements(EObject eObject, EReference eReference, boolean openInNewContext) {
		if (eReference.isContainer()) {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				"Operation not permitted for container references!");//$NON-NLS-1$
			return Optional.empty();
		}

		final Optional<EObject> newMEInstanceOptional = createNewModelElementStrategy.createNewModelElement(eObject,
			eReference);

		if (!newMEInstanceOptional.isPresent()) {
			return Optional.empty();
		}
		final EObject newMEInstance = newMEInstanceOptional.get();

		if (!eReference.isContainment()) {
			attachmentStrategy.addElementToModel(eObject, eReference, newMEInstance);
		}

		referenceStrategy.addElementsToReference(eObject, eReference, singleton(newMEInstance));

		if (openInNewContext) {
			openInNewContext(newMEInstance);
		}

		return newMEInstanceOptional;
	}

	@Override
	public void openInNewContext(final EObject eObject) {
		final EObject owner = eObject.eContainer();
		final EReference reference = eObject.eContainmentFeature();
		openInNewContextStrategy.openInNewContext(owner, reference, eObject);
	}

	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		final Iterator<EObject> allElements = ItemPropertyDescriptor
			.getReachableObjectsOfType(eObject, eReference.getEType())
			.iterator();

		Set<EObject> elements = new LinkedHashSet<EObject>();
		while (allElements.hasNext()) {
			elements.add(allElements.next());
		}

		elements = new LinkedHashSet<EObject>(
			eobjectSelectionStrategy.collectExistingObjects(eObject, eReference, elements));
		ECPControlHelper.removeExistingReferences(eObject, eReference, elements);

		final Set<EObject> addedElements = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(elements, eReference.isMany());

		// Don't invoke the Bazaar machinery to find a strategy just to add no elements
		if (!addedElements.isEmpty()) {
			referenceStrategy.addElementsToReference(eObject, eReference, addedElements);
		}
	}

	/**
	 * Add a strategy for selection of eligible existing {@link EObject}s to add
	 * to references.
	 *
	 * @param strategy a strategy to set
	 *
	 * @since 1.16
	 */
	void setEObjectSelectionStrategy(EObjectSelectionStrategy strategy) {
		if (strategy == null) {
			strategy = EObjectSelectionStrategy.NULL;
		}

		eobjectSelectionStrategy = strategy;
	}

	/**
	 * Add a strategy for the creation of a new model element.
	 *
	 * @param strategy The strategy to set
	 */
	void setCreateNewModelElementStrategy(CreateNewModelElementStrategy strategy) {
		if (strategy == null) {
			strategy = CreateNewModelElementStrategy.DEFAULT;
		}

		createNewModelElementStrategy = strategy;
	}

	/**
	 * Set a new attachment strategy ahead of previously added strategies.
	 *
	 * @param strategy an attachment strategy to set
	 *
	 * @since 1.16
	 */
	void setAttachmentStrategy(AttachmentStrategy strategy) {
		if (strategy == null) {
			strategy = AttachmentStrategy.DEFAULT;
		}

		attachmentStrategy = strategy;
	}

	/**
	 * Set a new reference strategy ahead of previously added strategies.
	 *
	 * @param strategy an reference strategy to set
	 *
	 * @since 1.16
	 */
	void setReferenceStrategy(ReferenceStrategy strategy) {
		if (strategy == null) {
			strategy = ReferenceStrategy.DEFAULT;
		}

		referenceStrategy = strategy;
	}

	/**
	 * Set a new open strategy ahead of previously added strategies.
	 *
	 * @param strategy an open strategy to set
	 *
	 * @since 1.16
	 */
	void setOpenStrategy(OpenInNewContextStrategy strategy) {
		if (strategy == null) {
			strategy = OpenInNewContextStrategy.DEFAULT;
		}

		openInNewContextStrategy = strategy;
	}

}
