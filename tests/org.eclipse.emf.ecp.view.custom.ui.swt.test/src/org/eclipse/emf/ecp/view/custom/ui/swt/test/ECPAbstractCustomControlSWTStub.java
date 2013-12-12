/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.edit.internal.swt.util.SWTRenderingHelper;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPCustomControlChangeListener;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author Jonas
 * 
 */
public class ECPAbstractCustomControlSWTStub extends ECPAbstractCustomControlSWT {

	private static final String TEST_MESSAGE = "TestMessage";
	private static final String TEST_TITEL = "TestTitel";
	private boolean rendered;
	private int lastValidationSeverity;
	private EStructuralFeature lastValidationFeature;
	private boolean disposed;
	private Label label;
	private Composite textControl;
	private final boolean withControl;
	private boolean validationReseted;

	public ECPAbstractCustomControlSWTStub() {
		this(false);
	}

	public ECPAbstractCustomControlSWTStub(boolean withControl) {
		super();
		this.withControl = withControl;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#createControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public List<RenderingResultRow<Control>> createControl(Composite composite) {
		final List<RenderingResultRow<Control>> result = new ArrayList<RenderingResultRow<Control>>();

		label = new Label(composite, SWT.NONE);
		label.setText(ECPAbstractCustomControlSWTTest.LABELTEXT);
		setRendered(true);

		if (!withControl) {
			result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
				.createRenderingResultRow(label));
			return result;
		}
		final VFeaturePathDomainModelReference controlFeature = (VFeaturePathDomainModelReference) getResolvedDomainModelReference(VCustomPackage.eINSTANCE
			.getHardcodedDomainModelReference_ControlId());

		setTextControl(createControl(controlFeature, composite));
		result.add(SWTRenderingHelper.INSTANCE.getResultRowFactory()
			.createRenderingResultRow(label, getTextControl()));

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
		setLastValidationSeverity(severity);
		setLastValidationFeature(feature);

	}

	@Override
	public void setValue(VDomainModelReference domainModelReference, Object value) {
		super.setValue(domainModelReference, value);
	}

	@Override
	public Object getValue(VDomainModelReference domainModelReference) {
		return super.getValue(domainModelReference);
	}

	@Override
	public void registerChangeListener(VDomainModelReference modelReference,
		final ECPCustomControlChangeListener changeListener) {
		super.registerChangeListener(modelReference, changeListener);
	}

	@Override
	public Binding bindTargetToModel(VDomainModelReference modelFeature, IObservableValue targetValue,
		UpdateValueStrategy targetToModel,
		UpdateValueStrategy modelToTarget) {
		return super.bindTargetToModel(modelFeature, targetValue, targetToModel, modelToTarget);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.swt.ECPAbstractCustomControlSWT#resetContentValidation()
	 */
	@Override
	protected void resetContentValidation() {
		setValidationReseted(true);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.ui.ECPAbstractCustomControl#disposeCustomControl()
	 */
	@Override
	protected void disposeCustomControl() {
		setDisposed(true);

	}

	/**
	 * @param composite
	 */
	public void createValidationLabelInStub(Composite composite) {
		super.createValidationLabel(composite);

	}

	public void stubShowError() {
		super.showError(TEST_TITEL, TEST_MESSAGE);
	}

	public void stubShowInfo() {
		super.showInfo(TEST_TITEL, TEST_MESSAGE);
	}

	public SWTCustomControlHelper getStubSWTHelper() {
		return super.getSWTHelper();

	}

	public CustomControlHelper getStubHelper() {
		return super.getHelper();

	}

	/**
	 * 
	 */
	public void stubInitValidation(Composite parent) {
		super.createValidationLabel(parent);

	}

	/**
	 * @return the rendered
	 */
	public boolean isRendered() {
		return rendered;
	}

	/**
	 * @param rendered the rendered to set
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	/**
	 * @return the lastValidationSeverity
	 */
	public int getLastValidationSeverity() {
		return lastValidationSeverity;
	}

	/**
	 * @param lastValidationSeverity the lastValidationSeverity to set
	 */
	public void setLastValidationSeverity(int lastValidationSeverity) {
		this.lastValidationSeverity = lastValidationSeverity;
	}

	/**
	 * @return the lastValidationFeature
	 */
	public EStructuralFeature getLastValidationFeature() {
		return lastValidationFeature;
	}

	/**
	 * @param lastValidationFeature the lastValidationFeature to set
	 */
	public void setLastValidationFeature(EStructuralFeature lastValidationFeature) {
		this.lastValidationFeature = lastValidationFeature;
	}

	/**
	 * @return the disposed
	 */
	public boolean isDisposed() {
		return disposed;
	}

	/**
	 * @param disposed the disposed to set
	 */
	public void setDisposed(boolean disposed) {
		this.disposed = disposed;
	}

	/**
	 * @return the textControl
	 */
	public Composite getTextControl() {
		return textControl;
	}

	public Label getLabel() {
		return label;
	}

	/**
	 * @param textControl the textControl to set
	 */
	public void setTextControl(Composite textControl) {
		this.textControl = textControl;
	}

	/**
	 * @return the validationReseted
	 */
	public boolean isValidationReseted() {
		return validationReseted;
	}

	/**
	 * @param validationReseted the validationReseted to set
	 */
	public void setValidationReseted(boolean validationReseted) {
		this.validationReseted = validationReseted;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences#getNeededDomainModelReferences()
	 */
	public Set<VDomainModelReference> getNeededDomainModelReferences() {
		return Collections.emptySet();
	}

	public List<VDomainModelReference> getResolvedReferences() {
		return getResolvedDomainModelReferences();
	}

}