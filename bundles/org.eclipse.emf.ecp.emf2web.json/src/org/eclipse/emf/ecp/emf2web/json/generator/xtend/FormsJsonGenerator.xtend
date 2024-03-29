/*******************************************************************************
 * Copyright (c) 2014-2017 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Stefan Dirix - initial API and implementation
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.generator.xtend

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecp.emf2web.util.ReferenceHelper
import org.eclipse.emf.ecp.view.spi.model.VContainer
import org.eclipse.emf.ecp.view.spi.model.VControl
import org.eclipse.emf.ecp.view.spi.model.VElement
import org.eclipse.emf.ecp.view.spi.model.VView
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory
import org.eclipse.emf.ecp.view.spi.label.model.VLabel

/** 
 * The class which handles the conversion from ecore files to qbForm files.
 * 
 * */
class FormsJsonGenerator extends JsonGenerator {
	
	static val TYPE = "type"
	static val ELEMENTS = "elements"
	static val CONTROL = "Control"
	// reactive again
	// static val CATEGORIZATION_ELEMENT = "CategorizationElement"
	static val CATEGORIZATION = "Categorization"
	static val CATEGORY = "Category"
	static val SCOPE = "scope"
	static val LABEL = "label"
	
	ReferenceHelper refHelper
	
	new(ReferenceHelper refHelper) {
		this.refHelper = refHelper
	}

	override createJsonElement(EObject object) {
		createJsonFormsElement(object)
	}
	
	private def dispatch JsonElement createJsonFormsElement(VLabel label) {
		val jsonObj = new JsonObject()
		jsonObj.withType("Label")
		jsonObj.with("text", label.name)
	}
	
	private def dispatch JsonElement createJsonFormsElement(VCategorizationElement categorizationElement) {
		new JsonObject()
//			.withType(CATEGORIZATION_ELEMENT)
			.withType(CATEGORIZATION)
    		.withElements(categorizationElement.categorizations)
	}
	
	private def dispatch JsonElement createJsonFormsElement(VCategorization categorization) {
		new JsonObject()
			.withType(CATEGORIZATION)
			.withLabel(categorization.label)
			.withElements(categorization.categorizations)
	}
	
	private def dispatch JsonElement createJsonFormsElement(VCategory category) {
		val jsonObj = new JsonObject
		jsonObj.withType(CATEGORY)
		jsonObj.withLabel(category.label)
			
	    // FIXME: shortcut the elements of any container into the category
		val contained = category.composite
		switch contained {
			VContainer : jsonObj.withElements(contained.children)
			default: throw new UnsupportedOperationException("Category must contain a VContainer element")
		}			
	}
	
	private def dispatch JsonElement createJsonFormsElement(VView view){
		switch(view.children.size){
			case 0 : new JsonObject
			case 1 : createJsonFormsElement(view.children.get(0))
			default : createJsonFormsElement(view.children)
		}
	}
	private def dispatch JsonElement createJsonFormsElement(VControl control){
		val jsonObject = new JsonObject
		jsonObject.withType(CONTROL)
		jsonObject.withLabel(control.displayLabel)
		jsonObject.withScope(control.ref)
	}
	
	private def dispatch JsonElement createJsonFormsElement(VContainer container){
		val jsonObject = new JsonObject
		jsonObject.withType(container.type)
		if(container.name!==null) {
			jsonObject.withLabel(container.label.displayLabel)
		}
		jsonObject.withElements(container.children)
	}
	
	private def dispatch JsonElement createJsonFormsElement(Collection<? extends VElement> elements){
		val jsonObject = new JsonObject
		jsonObject.withVerticalLayout(elements)
	}
	
	// fallback to avoid generator-breaking exceptions - should not be hit but avoided by only processing valid view models
	private def dispatch JsonElement createJsonFormsElement(EObject object){
		new JsonObject
	}
	
	// fallback to avoid generator-breaking exceptions - should not be hit but avoided by only processing valid view models
	private def dispatch JsonElement createJsonFormsElement(Void x){
		new JsonObject
	}
	
	private def withType(JsonObject jsonObject, String type) {
		jsonObject.with(TYPE, type)
	}
	
	private def withScope(JsonObject jsonObject, String ref) {
		jsonObject.with(SCOPE, ref)
	}
	
	private def withLabel(JsonObject jsonObject, String label) {
		jsonObject.with(LABEL, label)
	}
	
	private def withElements(JsonObject jsonObject, Collection<? extends VElement> elements){
		jsonObject.with(ELEMENTS, elements);
	}
	
	private def withVerticalLayout(JsonObject jsonObject, Collection<? extends VElement> elements){
		jsonObject.withType("VerticalLayout")
		jsonObject.withElements(elements)
	}
	
	private def String getType(VElement vElement) {
		vElement.eClass.name
	}
	
	private def String getRef(VControl control){		
		refHelper.getStringRepresentation(control.domainModelReference)
	}
	
	private def String getDisplayLabel(VControl control){
		refHelper.getLabel(control.domainModelReference)
	}
	
	private def String getDisplayLabel(String containerLabel){
		if(containerLabel.trim.startsWith("%")){
			var label = containerLabel.trim.substring(1);
			val split = label.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"); //$NON-NLS-1$
			if(split.get(0).length > 0){
				val charArray = split.get(0).toCharArray();
				charArray.set(0, Character.toUpperCase(charArray.get(0)))
				split.set(0, new String(charArray))
			}
			val sb = new StringBuilder();
			for (str : split) {
				sb.append(str);
				sb.append(" "); //$NON-NLS-1$
			}
			return sb.toString.trim;
		}
		containerLabel
	}
	
}
