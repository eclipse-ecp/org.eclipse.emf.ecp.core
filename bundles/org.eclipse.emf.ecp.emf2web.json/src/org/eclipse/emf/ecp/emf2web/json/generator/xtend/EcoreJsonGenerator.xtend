/*******************************************************************************
 * Copyright (c) 2014-2015 EclipseSource Muenchen GmbH and others.
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
 * Philip Langer - re-implementation based on Gson 
 * Florian Zoubek - bug fixing
 *******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.generator.xtend

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.Collection
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature

import static extension org.eclipse.emf.ecp.emf2web.util.xtend.TypeMapper.isBooleanType
import static extension org.eclipse.emf.ecp.emf2web.util.xtend.TypeMapper.isDateType
import static extension org.eclipse.emf.ecp.emf2web.util.xtend.TypeMapper.isEnumType
import static extension org.eclipse.emf.ecp.emf2web.util.xtend.TypeMapper.isIntegerType
import static extension org.eclipse.emf.ecp.emf2web.util.xtend.TypeMapper.isNumberType
import java.util.HashSet

/** 
 * The class which handles the conversion from ecore files to qbForm files.
 * 
 */
class EcoreJsonGenerator extends JsonGenerator {

	static val TYPE = "type"
	static val OBJECT = "object"
	static val REQUIRED = "required"
	static val PROPERTIES = "properties"
	static val ADDITIONAL_PROPERTIES = "additionalProperties"	
	
	val visitedClasses = new HashSet<EClass>();

	override createJsonElement(EObject object) {
		synchronized(visitedClasses){
			visitedClasses.clear()
			createJsonSchemaElement(object)
		}
	}

	private def dispatch JsonElement createJsonSchemaElement(EClass eClass) {
		visitedClasses.add(eClass)
		val jsonObject = new JsonObject().withObjectType
		jsonObject.withProperties(eClass.getEAllStructuralFeatures)
		jsonObject.with(ADDITIONAL_PROPERTIES, false)
		jsonObject.withRequiredProperties(eClass.getEAllStructuralFeatures.filter[required])
	}

	private def dispatch JsonElement createJsonSchemaElement(EAttribute attribute) {
		new JsonObject().withTypeProperties(attribute.getEType, attribute.upperBound)
	}

	private def JsonObject withTypeProperties(JsonObject jsonObject, EClassifier eClassifier, int upper) {
		if (upper > 1 || upper==-1) {
			jsonObject.withType("array")
			jsonObject.with("items", new JsonObject().withTypeProperties(eClassifier))
		} else {
			jsonObject.withTypeProperties(eClassifier)
		}
		jsonObject
	}

	private def JsonObject withTypeProperties(JsonObject jsonObject, EClassifier eClassifier) {
		jsonObject.withType(jsonType(eClassifier))
		if (eClassifier.isDateType) {
			jsonObject.with("format", "date-time")
		} else if (eClassifier.isEnumType) {
			val eEnum = eClassifier as EEnum
			val literalArray = new JsonArray
			for (name : eEnum.getELiterals.map[name]) {
				literalArray.add(new JsonPrimitive(name))
			}
			jsonObject.with("enum", literalArray)
		}
		jsonObject
	}

	private def jsonType(EClassifier eClassifier) {
		switch (eClassifier) {
			case eClassifier.isBooleanType: "boolean"
			case eClassifier.isIntegerType: "integer"
			case eClassifier.isNumberType: "number"
			default: "string"
		}
	}

	private def dispatch JsonElement createJsonSchemaElement(EReference reference) {
		val jsonObject = new JsonObject
		if (reference.upperBound > 1 || reference.upperBound==-1) {
			jsonObject.withType("array")
			jsonObject.with("items", createJsonSchemaElement(reference.EReferenceType))	
		} else {
			createJsonSchemaElement(reference.EReferenceType)
		}  	
	}

	private def dispatch JsonElement createJsonSchemaElement(EObject eObject) {
		throw new UnsupportedOperationException(
			"Cannot create a Json Schema element for EObjects that are not " +
				"EClasses, EEnums, EAttributes, or EReferences.")
	}


	private def withObjectType(JsonObject object) {
		object.withType(OBJECT)
	}

	private def withType(JsonObject jsonObject, String type) {
		jsonObject.with(TYPE, type)
	}

	private def withRequiredProperties(JsonObject jsonObject, Iterable<EStructuralFeature> requiredProperties) {
		val requiredPropertiesArray = new JsonArray
		for (name : requiredProperties.map[name]) {
			requiredPropertiesArray.add(new JsonPrimitive(name))
		}
		if (!requiredPropertiesArray.empty) {
			jsonObject.with(REQUIRED, requiredPropertiesArray)
		}
		jsonObject
	}

	private def withProperties(JsonObject jsonObject, Collection<? extends EStructuralFeature> features) {
		val propertyObject = new JsonObject
		for (feature : features) {
			if (feature.isNotCircle) {
				val jsonElement = createJsonSchemaElement(feature)
				propertyObject.add(feature.name, jsonElement)
			}
		}
		jsonObject.with(PROPERTIES, propertyObject)
	}
	
	private def boolean isNotCircle(EStructuralFeature feature){
		if(feature instanceof EReference){
			return !visitedClasses.contains(feature.EType)
		}
		true
	}
	
	
}
