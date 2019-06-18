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
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.json.generator.xtend

import org.eclipse.emf.ecore.EObject
import com.google.gson.JsonObject
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*
import java.util.LinkedList
import org.eclipse.emf.ecore.EcorePackage

class JsonGenerator_Test {
	static class TestJsonGenerator extends JsonGenerator{
		override createJsonElement(EObject object) {
			new JsonObject
		}
	}
	
	JsonGenerator generator
	
	@Before
	def void init() {
		generator = new TestJsonGenerator()
	}
	
	@Test
	def void generateWithString() {
		val object = new JsonObject
		generator.with(object, "test", "test2");
		assertEquals("test2", object.getAsJsonPrimitive("test").asString)
	}
	
	@Test
	def void generateWithNumber() {
		val object = new JsonObject
		generator.with(object, "test", 10);
		assertEquals(10, object.getAsJsonPrimitive("test").asInt)
	}
	
	@Test
	def void generateWithBoolean() {
		val object = new JsonObject
		generator.with(object, "test", true);
		assertEquals(true, object.getAsJsonPrimitive("test").asBoolean)
	}
	
	@Test
	def void generateWithCharacter() {
		val object = new JsonObject
		generator.with(object, "test", 'c');
		assertEquals(new Character('c'), object.getAsJsonPrimitive("test").asCharacter)
	}
	
	@Test
	def void generateWithNull() {
		val object = new JsonObject
		generator.with(object, "test", null);
		assertEquals("", object.getAsJsonPrimitive("test").asString)
	}
	
	@Test
	def void generateWithEObjectList() {
		val object = new JsonObject
		val eObjects = new LinkedList
		eObjects.add(EcorePackage.eINSTANCE.EAttribute)
		generator.with(object, "test", eObjects);
		assertEquals(new JsonObject, object.getAsJsonArray("test").get(0))
	}
	
	@Test
	def void generateWithOther() {
		val object = new JsonObject
		generator.with(object, "test", new Object);
		assertEquals("", object.getAsJsonPrimitive("test").asString)
	}
	

}