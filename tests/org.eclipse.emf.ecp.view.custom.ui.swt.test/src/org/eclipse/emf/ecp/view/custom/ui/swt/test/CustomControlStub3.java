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
package org.eclipse.emf.ecp.view.custom.ui.swt.test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPCustomControlChangeListener;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author jfaltermeier
 * 
 */
public class CustomControlStub3 extends ECPAbstractCustomControlSWT {
	// private VFeaturePathDomainModelReference playersReference;
	// private VFeaturePathDomainModelReference nameReference;
	// private VFeaturePathDomainModelReference dateReference;
	private final Set<VDomainModelReference> features = new LinkedHashSet<VDomainModelReference>();

	public static final String CHANGE_NOTICED = "CHANGE!";

	private Label label;

	public CustomControlStub3() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences#getNeededDomainModelReferences()
	 */
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		if (features.isEmpty()) {
			final VFeaturePathDomainModelReference playersReference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			playersReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getLeague_Players());

			final VFeaturePathDomainModelReference nameReference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			nameReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_Name());
			nameReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getLeague_Players());

			final VFeaturePathDomainModelReference dateReference = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			dateReference.setDomainModelEFeature(BowlingPackage.eINSTANCE.getPlayer_DateOfBirth());
			dateReference.getDomainModelEReferencePath().add(BowlingPackage.eINSTANCE.getLeague_Players());

			features.add(playersReference);
			features.add(nameReference);
			features.add(dateReference);
		}
		return features;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected List<RenderingResultRow<Control>> createControl(Composite composite) {
		final List<RenderingResultRow<Control>> result = new ArrayList<RenderingResultRow<Control>>();
		label = new Label(composite, SWT.NONE);
		label.setText("Players: ");

		final Composite tableParent = new Composite(composite, SWT.NONE);

		final Composite tableComposite = new Composite(tableParent, SWT.NONE);
		final TableViewer tableViewer = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION
			| SWT.V_SCROLL);
		tableViewer.getControl().setLayoutData(new GridData());
		tableComposite.setLayout(new TableColumnLayout());

		final IValueProperty[] valueProperties = EMFProperties.values(BowlingPackage.Literals.PLAYER__NAME,
			BowlingPackage.Literals.PLAYER__DATE_OF_BIRTH);

		createViewerBinding(getResolvedDomainModelReference(BowlingPackage.eINSTANCE.getLeague_Players()), tableViewer,
			valueProperties);

		registerChangeListener(getResolvedDomainModelReference(BowlingPackage.eINSTANCE.getLeague_Players()),
			new LeagueChangeListener());

		result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
			.createRenderingResultRow(label, tableParent));
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#handleContentValidation(int,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	protected void handleContentValidation(int severity, EStructuralFeature feature) {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#resetContentValidation()
	 */
	@Override
	protected void resetContentValidation() {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.ui.ECPAbstractCustomControl#disposeCustomControl()
	 */
	@Override
	protected void disposeCustomControl() {

	}

	public class LeagueChangeListener implements ECPCustomControlChangeListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecp.view.spi.custom.model.ECPCustomControlChangeListener#notifyChanged()
		 */
		public void notifyChanged() {
			label.setText(CHANGE_NOTICED);
		}

	}

}
