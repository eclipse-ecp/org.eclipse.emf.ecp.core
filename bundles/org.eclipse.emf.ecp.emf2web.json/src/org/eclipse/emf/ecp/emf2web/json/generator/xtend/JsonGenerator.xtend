/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.generator.xtend

import com.google.gson.JsonObject
import com.google.gson.JsonElement
import org.eclipse.emf.ecore.EObject
import java.util.Collection
import com.google.gson.JsonArray
import com.google.gson.GsonBuilder
import org.eclipse.emf.ecp.emf2web.generator.Generator
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl

/**
 * @author Stefan Dirix
 *
 */
abstract class JsonGenerator implements Generator{
	
	def abstract JsonElement createJsonElement(EObject object)
	
	override generate(EObject object){
		val json = createJsonElement(object)
		val gson = new GsonBuilder().setPrettyPrinting().create()
		gson.toJson(json)
	}
	
	protected def dispatch with(JsonObject jsonObject, String propertyName, JsonElement value) {
		jsonObject.add(propertyName, value)
		jsonObject
	}

	protected def dispatch with(JsonObject jsonObject, String propertyName, String value) {
		jsonObject.addProperty(propertyName, value)
		jsonObject
	}

	protected def dispatch with(JsonObject jsonObject, String propertyName, Number value) {
		jsonObject.addProperty(propertyName, value)
		jsonObject
	}

	protected def dispatch with(JsonObject jsonObject, String propertyName, Boolean value) {
		jsonObject.addProperty(propertyName, value)
		jsonObject
	}

	protected def dispatch with(JsonObject jsonObject, String propertyName, Character value) {
		jsonObject.addProperty(propertyName, value)
		jsonObject
	}
	
	protected def dispatch with(JsonObject jsonObject, String propertyName, Collection<? extends EObject> collection) {
		val filteredElements=collection.filter[item|!VCustomControl.isInstance(item)]
		val jsonElements = filteredElements.map [ item |
			createJsonElement(item)
		]
		val jsonArray = new JsonArray()
		for (jsonElement : jsonElements) {
			jsonArray.add(jsonElement)
		}
		jsonObject.add(propertyName, jsonArray)
		jsonObject
	}
	
	// fallback if value is null
	protected def dispatch with(JsonObject jsonObject, String propertyName, Void x) {
		jsonObject.addProperty(propertyName, "")
		jsonObject
	}
	
	// fallback for all values not handled above
	protected def dispatch with(JsonObject jsonObject, String propertyName, Object object) {
		jsonObject.addProperty(propertyName, "")
		jsonObject
	}
}