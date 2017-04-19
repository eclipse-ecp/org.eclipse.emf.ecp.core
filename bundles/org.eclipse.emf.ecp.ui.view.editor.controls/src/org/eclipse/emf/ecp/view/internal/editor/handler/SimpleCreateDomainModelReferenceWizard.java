/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.util.SegmentResolvementUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.Wizard;

/**
 * Wizard to create a new {@link VDomainModelReference} and select its {@link VDomainModelReferenceSegment
 * VDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
public class SimpleCreateDomainModelReferenceWizard extends Wizard {
	private SelectFeaturePathWizardPage selectFeaturePathPage;
	private final EditingDomain editingDomain;
	private final EStructuralFeature structuralFeature;
	private final EObject eObject;
	private final EClass rootEClass;
	private final VDomainModelReference existingDMR;
	private final EStructuralFeatureSelectionValidator selectionValidator;
	private final SegmentGenerator segmentGenerator;

	/**
	 * A wizard used for creating and configuring a DomainModelReference.
	 *
	 * @param eObject The {@link EObject} containing a domain model reference
	 * @param structuralFeature The corresponding {@link EStructuralFeature}
	 * @param editingDomain The setting's editing domain
	 * @param rootEClass The root {@link EClass} of the VView the eObject belongs to
	 * @param windowTitle The title for the wizard window
	 * @param existingDMR The domain model reference to configure. May be null, then a new DMR is created
	 * @param selectionValidator Validates whether a selected structural feature is a valid selection (e.g. the
	 *            selection could be required to be a multi reference)
	 */
	public SimpleCreateDomainModelReferenceWizard(final EObject eObject, final EStructuralFeature structuralFeature,
		final EditingDomain editingDomain, final EClass rootEClass, final String windowTitle,
		VDomainModelReference existingDMR, EStructuralFeatureSelectionValidator selectionValidator,
		SegmentGenerator segmentGenerator) {
		setWindowTitle(windowTitle);
		this.eObject = eObject;
		this.structuralFeature = structuralFeature;
		this.editingDomain = editingDomain;
		this.rootEClass = rootEClass;
		this.existingDMR = existingDMR;
		this.selectionValidator = selectionValidator;
		this.segmentGenerator = segmentGenerator;
	}

	/**
	 * @return The initial selection for the {@link SelectFeaturePathWizardPage SelectFeaturePathWizardPage's} tree
	 *         viewer. Returns an empty selection if there is no existing dmr or it does not contain any segments.
	 */
	private ISelection getInitialSelection() {
		if (existingDMR == null || existingDMR.getSegments().isEmpty()) {
			return TreeSelection.EMPTY;
		}

		final List<EStructuralFeature> pathList = SegmentResolvementUtil
			.resolveSegmentsToFeatureList(existingDMR.getSegments(), rootEClass);
		if (pathList.size() == existingDMR.getSegments().size()) {
			return new TreeSelection(new TreePath(pathList.toArray()));
		}
		return TreeSelection.EMPTY;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (!canFinish()) {
			return false;
		}
		Command command = null;
		if (structuralFeature.isMany()) {
			command = AddCommand.create(editingDomain, eObject,
				structuralFeature, selectFeaturePathPage.getDomainModelReference());
		} else {
			command = SetCommand.create(editingDomain, eObject,
				structuralFeature, selectFeaturePathPage.getDomainModelReference());
		}
		editingDomain.getCommandStack().execute(command);
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return super.canFinish();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		selectFeaturePathPage = new SelectFeaturePathWizardPage("New Domain Model Reference", //$NON-NLS-1$
			"Select an EStructuralFeature", "Select a domain model EStructuralFeature for the domain model reference.", //$NON-NLS-1$//$NON-NLS-2$
			rootEClass, getInitialSelection(), segmentGenerator, selectionValidator, isAllowMultiReferences());
		addPage(selectFeaturePathPage);
	}

	/**
	 * @return Whether the selection of multi references is allowed in the {@link SelectFeaturePathWizardPage}
	 */
	protected boolean isAllowMultiReferences() {
		// TODO configurable?
		return false;
	}
}
