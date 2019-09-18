/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.ide.preferences;

import org.eclipse.emfforms.spi.ide.preferences.EmfFormsPreferences;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Main preference page for EMF Forms.
 *
 * @author Lucas Koehler
 *
 */
public class EmfFormsMainPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button legacyButton;
	private Button segmentsButton;

	/**
	 * Create me with an empty title and no image.
	 */
	public EmfFormsMainPreferencePage() {
		// Nothing to do here
	}

	/**
	 * Creates me with a title.
	 *
	 * @param title Title of this preference page
	 */
	public EmfFormsMainPreferencePage(String title) {
		super(title);
	}

	/**
	 * Creates me with a title and an image.
	 *
	 * @param title Title of this preference page
	 * @param image Image of this preference page
	 */
	public EmfFormsMainPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {
		// Nothing to do here
	}

	@Override
	protected Control createContents(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);

		final Group modeGroup = new Group(composite, SWT.TITLE);
		modeGroup.setText(Messages.EmfFormsMainPreferencePage_toolingModeTitle);
		final Composite padding = new Composite(modeGroup, SWT.NONE);
		final Label modeDescription = new Label(padding, SWT.WRAP);
		modeDescription.setText(
			Messages.EmfFormsMainPreferencePage_toolingModeDescription);
		final Composite modeSelection = new Composite(padding, SWT.NONE);
		modeSelection.setLayout(new RowLayout(SWT.VERTICAL));
		legacyButton = new Button(modeSelection, SWT.RADIO);
		legacyButton.setText(Messages.EmfFormsMainPreferencePage_legacyMode);
		segmentsButton = new Button(modeSelection, SWT.RADIO);
		segmentsButton.setText(Messages.EmfFormsMainPreferencePage_segmentMode);

		GridLayoutFactory.fillDefaults().generateLayout(modeSelection);
		GridLayoutFactory.fillDefaults().generateLayout(padding);
		GridLayoutFactory.fillDefaults().margins(5, 5).generateLayout(modeGroup);
		GridLayoutFactory.fillDefaults().generateLayout(composite);

		updateToolingModeButtons(EmfFormsPreferences.isSegmentTooling());
		return composite;
	}

	@Override
	protected void performDefaults() {
		updateToolingModeButtons(EmfFormsPreferences.SEGMENT_TOOLING_DEFAULT);
		super.performDefaults();
	}

	private void updateToolingModeButtons(boolean segmentTooling) {
		legacyButton.setSelection(!segmentTooling);
		segmentsButton.setSelection(segmentTooling);
	}

	@Override
	public boolean performOk() {
		EmfFormsPreferences.setSegmentTooling(segmentsButton.getSelection());
		return super.performOk();
	}
}
