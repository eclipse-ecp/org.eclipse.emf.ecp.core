/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.controller.xtend

import org.eclipse.emf.ecp.view.spi.model.VView
import org.eclipse.emf.ecore.util.Diagnostician
import org.eclipse.emf.ecp.view.spi.label.model.VLabel
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory
import org.eclipse.emf.ecp.view.spi.model.VControl
import org.eclipse.emf.ecp.view.spi.model.VContainer
import java.util.LinkedList
import org.eclipse.emf.ecp.view.spi.model.VElement
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.Diagnostic
import java.util.List

/**
 * Processes {@link VView}s before ui schema generation
 * 
 * @author Stefan Dirix <sdirix@eclipsesource.com>
 */
class ViewCleaner {
	
	/**
	 * Remove invalid and not supported {@link VElement}s.
	 */
	def static cleanView(VView view) {
		removeInvalidElements(view)

		val whiteList = #[VLabel, VCategorizationElement, VCategorization, VCategory, VView, VControl, VContainer]
		removeUnsupportedElements(view, whiteList)
	}

	private static def void removeInvalidElements(VView view) {
		val validation = Diagnostician.INSTANCE.validate(view);
		if (validation.severity == Diagnostic.ERROR) {
			for (diagnostic : validation.children) {
				removeInvalidElements(diagnostic)
			}
		}
	}

	private static def void removeInvalidElements(Diagnostic diagnostic) {
		if (diagnostic.severity == Diagnostic.ERROR && !diagnostic.data.isEmpty &&
			EObject.isInstance(diagnostic.data.get(0))) {
			EcoreUtil.delete(diagnostic.data.get(0) as EObject)
		}
		for (childDiagnostic : diagnostic.children) {
			removeInvalidElements(childDiagnostic)
		}
	}
	
	private static def void removeUnsupportedElements(VView view, List<? extends Class<?>> whiteList) {
		val toDelete = new LinkedList
		val allContents = view.eAllContents
		while (allContents.hasNext) {
			val next = allContents.next
			if (VElement.isInstance(next)) {
				var supported = false
				for (element : whiteList) {
					if (element.isInstance(next)) {
						supported = true
					}
				}
				if (!supported) {
					toDelete.add(next)
				}
			}
		}
		EcoreUtil.deleteAll(toDelete, false)
	}
}