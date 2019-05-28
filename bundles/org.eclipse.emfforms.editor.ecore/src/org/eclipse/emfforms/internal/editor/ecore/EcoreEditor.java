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
 * cleme_000 - initial API and implementation
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.BasicDetailViewCache;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.editor.InitializeChildCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailComposite;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeMasterDetailSWTFactory;
import org.eclipse.emfforms.spi.swt.treemasterdetail.diagnostic.DiagnosticCache;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * This class extends the GenericEditor to provide customized features for Ecore files.
 */
public class EcoreEditor extends GenericEditor {

	private static final String ECORE_EDITOR_CONTEXT = "org.eclipse.emfforms.editor.ecore.context";

	@Override
	protected CreateElementCallback getCreateElementCallback() {
		return new InitializeChildCallback();
	}

	@Override
	protected String getEditorTitle() {
		return "Ecore Model Editor";
	}

	@Override
	protected TreeMasterDetailComposite createTreeMasterDetail(Composite composite, Object editorInput,
		CreateElementCallback createElementCallback) {
		final EcoreEditorTMDCustomization buildBehaviour = new EcoreEditorTMDCustomization(createElementCallback,
			(Notifier) editorInput, (EcoreDiagnosticCache) getDiagnosticCache());
		buildBehaviour.setTree(createTreeViewerBuilder());

		final TreeMasterDetailComposite result = TreeMasterDetailSWTFactory.createTreeMasterDetail(composite, SWT.NONE,
			editorInput,
			buildBehaviour);
		result.setCache(new BasicDetailViewCache(EcorePackage.eINSTANCE.getEClassifiers().size()));
		return result;
	}

	@Override
	protected DiagnosticCache createDiangosticCache(Notifier input) {
		return new EcoreDiagnosticCache(input);
	}

	@Override
	protected String getContextId() {
		return ECORE_EDITOR_CONTEXT;
	}

	@Override
	protected boolean enableValidation() {
		return true;
	}
}
