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
package org.eclipse.emf.ecp.emf2web.json.controller.xtend

import java.util.Collection
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecp.emf2web.controller.xtend.GenerationController
import org.eclipse.emf.ecp.emf2web.controller.xtend.GenerationInfo
import org.eclipse.emf.ecp.emf2web.json.generator.xtend.EcoreJsonGenerator
import org.eclipse.emf.ecp.emf2web.json.generator.xtend.FormsJsonGenerator
import org.eclipse.emf.ecp.emf2web.json.util.ReferenceHelperImpl
import org.eclipse.emf.ecp.view.spi.model.VElement
import org.eclipse.emf.ecp.view.spi.model.VView

/**
 * @author Stefan Dirix <sdirix@eclipsesource.com>
 *
 */
class JsonGenerationController implements GenerationController {
	
	override List<GenerationInfo> generate(Collection<? extends VView> views) {
		val result = new LinkedList<GenerationInfo>

		val modelGenerator = new EcoreJsonGenerator
		val helper=new ReferenceHelperImpl
		val formsGenerator = new FormsJsonGenerator(helper)

		for (rawView : views) {
			val view = EcoreUtil.copy(rawView)
			for (ecorePath : view.ecorePaths) {
				helper.addEcorePath(ecorePath);
			}
			val eClass = view.rootEClass
			val schemaIdentifier = eClass.name

			val schemaFile = modelGenerator.generate(eClass)
			val schemaInfo = new GenerationInfo(GenerationInfo.MODEL_TYPE, eClass, null,
				schemaIdentifier + "Model.json", null)
			schemaInfo.generatedString = schemaFile
			result.add(schemaInfo)

			ViewCleaner.cleanView(view)

			//internationalize view
			val allContents = view.eAllContents
			while (allContents.hasNext) {
				val next = allContents.next
				if (VElement.isInstance(next)) {
					VElement.cast(next).label = VElement.cast(next).name
				}
			}

			val controllerFile = formsGenerator.generate(view)
			val controllerInfo = new GenerationInfo(GenerationInfo.VIEW_TYPE, null, view,
				schemaIdentifier + "View.json", null)
			controllerInfo.generatedString = controllerFile
			result.add(controllerInfo)
		}

		result
	}
	
}