/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeMasterDetailCustomization;
import org.eclipse.swt.widgets.Composite;

/**
 * This factory allows to create {@link TreeMasterDetailComposite TreeMasterDetailComposites}.
 *
 * @author Johannes Faltermeier
 *
 */
public final class TreeMasterDetailSWTFactory {

	private TreeMasterDetailSWTFactory() {
		// factory
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link TreeMasterDetailComposite}. This will return
	 * a {@link TreeMasterDetailSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits which will be passed to the {@link TreeMasterDetailComposite}
	 * @param input the input object
	 * @return the builder
	 */
	public static TreeMasterDetailSWTBuilder fillDefaults(Composite composite, int swtStyleBits, Object input) {
		return new TreeMasterDetailSWTBuilder(composite, swtStyleBits, input);
	}

	/**
	 * Creates a {@link TreeMasterDetailComposite} with the default behavior.
	 *
	 * @param parent the parent composite
	 * @param style the style bits which will be passed to the {@link TreeMasterDetailComposite}
	 * @param input the input object
	 * @return the tree master detail
	 */
	public static TreeMasterDetailComposite createTreeMasterDetail(Composite parent, int style, Object input) {
		return createTreeMasterDetail(parent, style, input, new DefaultTreeMasterDetailCustomization());
	}

	/**
	 * Creates a {@link TreeMasterDetailComposite} with a customized behavior. Please note that there is also the
	 * {@link #fillDefaults(Composite, int, Object)} method which allows to customize single aspects of the default
	 * behavior without having to provider a full implementaion of {@link TreeMasterDetailSWTCustomization}.
	 *
	 * @param parent the parent composite
	 * @param style the style bits which will be passed to the {@link TreeMasterDetailComposite}
	 * @param input the input object
	 * @param buildBehaviour the custom behavior
	 * @return the tree master detail
	 */
	public static TreeMasterDetailComposite createTreeMasterDetail(Composite parent, int style, Object input,
		TreeMasterDetailSWTCustomization buildBehaviour) {
		return createTreeMasterDetail(parent, style, input, 100, buildBehaviour);
	}

	/**
	 * Creates a {@link TreeMasterDetailComposite} with a customized behavior. Please note that there is also the
	 * {@link #fillDefaults(Composite, int, Object)} method which allows to customize single aspects of the default
	 * behavior without having to provider a full implementaion of {@link TreeMasterDetailSWTCustomization}.
	 *
	 * @param parent the parent composite
	 * @param style the style bits which will be passed to the {@link TreeMasterDetailComposite}
	 * @param input the input object
	 * @param buildBehaviour the custom behavior
	 * @param updateDelay the time between a detected selection change and updating the detail panel in ms
	 * @return the tree master detail
	 * @since 1.11
	 */
	public static TreeMasterDetailComposite createTreeMasterDetail(Composite parent, int style, Object input,
		int updateDelay, TreeMasterDetailSWTCustomization buildBehaviour) {
		return new TreeMasterDetailComposite(parent, style, input, buildBehaviour, updateDelay);
	}

}
