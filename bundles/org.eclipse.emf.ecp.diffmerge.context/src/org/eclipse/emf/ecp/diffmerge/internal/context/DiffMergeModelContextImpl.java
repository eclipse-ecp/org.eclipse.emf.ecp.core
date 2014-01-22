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
package org.eclipse.emf.ecp.diffmerge.internal.context;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.diffmerge.spi.context.ControlPair;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;

/**
 * Implementation of the DiffMergeModelContext based on the {@link ViewModelContextImpl}.
 * As the {@link ViewModelContextImpl} is internal we suppress the restriction.
 * 
 * @author Eugen Neufeld
 * 
 */
@SuppressWarnings("restriction")
public class DiffMergeModelContextImpl extends ViewModelContextImpl implements
	DiffMergeModelContext {

	private final EObject left;
	private final EObject right;
	private Map<VControl, ControlPair> controlDiffMap;

	/**
	 * Constructor for the {@link DiffMergeModelContextImpl}.
	 * 
	 * @param view the {@link VElement}
	 * @param domainObject the {@link EObject} which is editable
	 * @param left the first object
	 * @param right the second object
	 * @see ViewModelContextImpl#ViewModelContextImpl(VElement, EObject)
	 */
	public DiffMergeModelContextImpl(VElement view, EObject domainObject,
		EObject left, EObject right) {
		super(view, domainObject);
		this.left = left;
		this.right = right;

		initComparison();
	}

	private void initComparison() {

		final VElement viewModelLeft = EcoreUtil.copy(getViewModel());
		final VElement viewModelRight = EcoreUtil.copy(getViewModel());

		ViewModelUtil.resolveDomainReferences(viewModelLeft, getLeftModel());
		ViewModelUtil.resolveDomainReferences(viewModelRight, getRightModel());

		final TreeIterator<EObject> mainViewModel = getViewModel().eAllContents();
		final TreeIterator<EObject> leftViewModel = viewModelLeft.eAllContents();
		final TreeIterator<EObject> rightViewModel = viewModelRight.eAllContents();

		controlDiffMap = new LinkedHashMap<VControl, ControlPair>();

		while (mainViewModel.hasNext()) {
			final EObject mainEObject = mainViewModel.next();
			final EObject leftEObject = leftViewModel.next();
			final EObject rightEObject = rightViewModel.next();
			if (VControl.class.isInstance(mainEObject)) {
				if (hasDiff((VControl) leftEObject, (VControl) rightEObject)) {
					controlDiffMap.put((VControl) mainEObject, new ControlPair((VControl) leftEObject,
						(VControl) rightEObject));
				}
			}
		}
	}

	private boolean hasDiff(VControl leftEObject, VControl rightEObject) {
		return !CompareControls.areEqual(leftEObject, rightEObject);
	}

	/**
	 * Constructor for the {@link DiffMergeModelContextImpl}.
	 * 
	 * @param view the {@link VElement}
	 * @param domainObject the {@link EObject} which is editable
	 * @param origin1 the first object
	 * @param origin2 the second object
	 * @param modelServices the {@link ViewModelService ViewModelServices} to register
	 * @see ViewModelContextImpl#ViewModelContextImpl(VElement, EObject, ViewModelService...)
	 */
	public DiffMergeModelContextImpl(VElement view, EObject domainObject,
		EObject origin1, EObject origin2, ViewModelService[] modelServices) {
		super(view, domainObject, modelServices);
		left = origin1;
		right = origin2;

		initComparison();
	}

	/** {@inheritDoc} **/
	public EObject getLeftModel() {
		return left;
	}

	/** {@inheritDoc} **/
	public EObject getRightModel() {
		return right;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext#hasDiff(org.eclipse.emf.ecp.view.spi.model.VControl)
	 */
	public boolean hasDiff(VControl control) {
		return controlDiffMap.containsKey(control);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext#getPairWithDiff(org.eclipse.emf.ecp.view.spi.model.VControl)
	 */
	public ControlPair getPairWithDiff(VControl control) {
		return controlDiffMap.get(control);
	}
}
