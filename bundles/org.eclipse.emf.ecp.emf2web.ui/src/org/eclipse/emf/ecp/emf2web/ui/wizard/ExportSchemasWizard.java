/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.ui.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecp.emf2web.controller.GenerationInfo;
import org.eclipse.emf.ecp.emf2web.exporter.DialogToggleInteraction;
import org.eclipse.emf.ecp.emf2web.exporter.GenerationExporter;
import org.eclipse.emf.ecp.emf2web.ui.messages.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * Generic wizards for {@link GenerationExporter}s.
 *
 * @author Stefan Dirix
 *
 */
public class ExportSchemasWizard extends Wizard implements PropertyChangeListener {

	/**
	 * Collection of {@link GenerationInfo}s.
	 */
	private final Collection<? extends GenerationInfo> generationInfos;
	/**
	 * The {@link GenerationExporter} responsible for export.
	 */
	private final GenerationExporter exporter;

	/**
	 * Constructor.
	 *
	 * @param generationInfos Collection of information used by the exporter to export.
	 * @param exporter The exporter responsible to export the given generation information.
	 * @param locationProposal The locationProposal for the export. If {@code null} uses "user.home".
	 */
	public ExportSchemasWizard(Collection<? extends GenerationInfo> generationInfos, GenerationExporter exporter,
		URI locationProposal) {
		setWindowTitle(Messages.getString("ExportSchemasWizard.WindowTitle")); //$NON-NLS-1$
		this.generationInfos = generationInfos;
		this.exporter = exporter;

		if (locationProposal == null) {
			locationProposal = URI.createFileURI(System.getProperty("user.home", "")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		for (final GenerationInfo generationInfo : generationInfos) {
			// Initialize location
			final URI proposal = locationProposal.appendSegment(generationInfo.getNameProposal());
			generationInfo.setLocation(proposal);

			// Observe generationInfos to update default values
			generationInfo.addPropertyChangeListener(this);
		}
	}

	@Override
	public void addPages() {
		for (final GenerationInfo generationInfo : generationInfos) {
			addPage(new SelectLocationPage(generationInfo));
		}
	}

	@Override
	public boolean performFinish() {
		try {
			exporter.export(generationInfos, new DialogToggleInteraction());
			MessageDialog.openInformation(getShell(), Messages.getString("ExportSchemasWizard.DialogTitle_Success"), //$NON-NLS-1$
				Messages.getString("ExportSchemasWizard.DialogMessage_Success")); //$NON-NLS-1$
			return true;
		} catch (final IOException e) {
			MessageDialog.openError(getShell(), Messages.getString("ExportSchemasWizard.DialogTitle_Error"), //$NON-NLS-1$
				e.getMessage());
		}
		return false;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		for (final SelectLocationPage page : getNotVisitedLocationPages()) {
			final GenerationInfo generationInfo = page.getGenerationInfo();
			generationInfo.removePropertyChangeListener(this);
			if ("location".equals(event.getPropertyName())) { //$NON-NLS-1$
				final URI newLocation = getNewLocationProposal(URI.class.cast(event.getNewValue()),
					generationInfo.getNameProposal());
				generationInfo.setLocation(newLocation);
			}
			if ("wrap".equals(event.getPropertyName())) { //$NON-NLS-1$
				generationInfo.setWrap(Boolean.class.cast(event.getNewValue()));
			}
			generationInfo.addPropertyChangeListener(this);
			page.getBindingContext().updateTargets();
		}
	}

	private Set<SelectLocationPage> getNotVisitedLocationPages() {
		final Set<SelectLocationPage> locationPages = new HashSet<SelectLocationPage>();
		for (final IWizardPage page : getPages()) {
			addNotAlreadyVisibleLocationPage(page, locationPages);
		}
		return locationPages;
	}

	private void addNotAlreadyVisibleLocationPage(IWizardPage page, Set<SelectLocationPage> locationPages) {
		if (SelectLocationPage.class.isInstance(page)) {
			final SelectLocationPage locationPage = SelectLocationPage.class.cast(page);
			if (!locationPage.wasAlreadyVisible()) {
				locationPages.add(locationPage);
			}
		}
	}

	private URI getNewLocationProposal(URI location, String fileName) {
		URI result = location;
		result = result.trimSegments(1);
		result = result.appendSegment(fileName).trimFileExtension();
		if (location.fileExtension() != null) {
			result = result.appendFileExtension(location.fileExtension());
		}
		return result;
	}

}
