package org.eclipse.emf.ecp.emf2web.json.generator.xtend

import com.google.gson.JsonParser
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*
import org.eclipse.emf.ecp.emf2web.json.generator.xtend.EcoreJsonGenerator
import org.eclipse.emf.ecore.EClassifier

class EcoreJsonExporter_Test {
	static val ECORE_PACKAGE = EcorePackage.eINSTANCE
	static val ECORE_FACTORY = EcoreFactory.eINSTANCE
	static val TEST_ECLASS_NAME = "TestEClass";
	static val TEST_EATTRIBUTE_NAME = "testAttribute";
	static val TEST_EREFERENCE_NAME = "testReference";

	val List<String> testEnumValues = new ArrayList<String>(Arrays.asList("1A", "2B"))

	EcoreJsonGenerator exporter;

	@Before
	def void init() {
		exporter = new EcoreJsonGenerator()
	}

	def buildEnum() {
		val eEnum = ECORE_FACTORY.createEEnum
		eEnum.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		for (String literal : testEnumValues) {
			val enumLiteral = ECORE_FACTORY.createEEnumLiteral
			enumLiteral.name = literal
			eEnum.getELiterals.add(enumLiteral)
		}
		eEnum
	}

	@Test
	def void createJsonSchemaElementFromEClassWithOptionalSingleReference() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		
		val refClass = ECORE_FACTORY.createEClass
		refClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME + "2"

		val eReference = eReference(-1, 1, refClass)
		eClass.getEStructuralFeatures.add(eReference)

		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithOptionalSingleReferencedEClassJsonElement, result)
	}
	
	@Test
	def void createJsonSchemaElementFromEClassWithMandatorySingleReference() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		
		val refClass = ECORE_FACTORY.createEClass
		refClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME + "2"

		val eReference = eReference(1, 1, refClass)
		eClass.getEStructuralFeatures.add(eReference)

		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithMandatorySingleReferencedEClassJsonElement, result)
	}
	
	@Test
	def void createJsonSchemaElementFromEClassWithOptionalMultiReference() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		
		val refClass = ECORE_FACTORY.createEClass
		refClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME + "2"

		val eReference = eReference(-1, 10, refClass)
		eClass.getEStructuralFeatures.add(eReference)

		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithOptionalMultiReferencedEClassJsonElement, result)
	}
	
	@Test
	def void createJsonSchemaElementFromEClassWithMandatoryMultiReference() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		
		val refClass = ECORE_FACTORY.createEClass
		refClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME + "2"

		val eReference = eReference(1, 10, refClass)
		eClass.getEStructuralFeatures.add(eReference)

		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithMandatoryMultiReferencedEClassJsonElement, result)
	}
	
	@Test
	def void createJsonSchemaElementFromEClassWithCircleReference() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME
		
		val refClass = ECORE_FACTORY.createEClass
		refClass.name = EcoreJsonExporter_Test.TEST_ECLASS_NAME + "2"

		val eReference = eReference(-1, 1, refClass)
		eClass.getEStructuralFeatures.add(eReference)
		
		val eReference2 = eReference(-1, 1, eClass)
		refClass.getEStructuralFeatures.add(eReference2)

		val result = exporter.createJsonElement(eClass)
		// eReference2 should not exist in output
		assertEquals(eClassWithOptionalSingleReferencedEClassJsonElement, result)
	}

	@Test
	def void createJsonSchemaElementFromEmptyEClass() {
		val eClass = emptyEClass()
		val result = exporter.createJsonElement(eClass)
		assertEquals(emptyEClassJsonElement(), result);
	}

	@Test
	def void createJsonSchemaElementFromEClassWithOptionalStringAttribute() {
		val eClass = emptyEClass()
		val optionalStringEAttribute = stringEAttribute(0, 1)
		eClass.getEStructuralFeatures.add(optionalStringEAttribute)
		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithOptionalStringEAttributeJsonElement(), result);
	}
	
	@Test
	def void createJsonSchemaElementFromEClassWithMandatoryStringAttribute() {
		val eClass = emptyEClass()
		val mandatoryStringEAttribute = stringEAttribute(1, 1)
		eClass.getEStructuralFeatures.add(mandatoryStringEAttribute)
		val result = exporter.createJsonElement(eClass)
		assertEquals(eClassWithMandatoryStringEAttributeJsonElement(), result);
	}

	private def emptyEClass() {
		val eClass = ECORE_FACTORY.createEClass
		eClass.name = TEST_ECLASS_NAME
		eClass
	}

	private def stringEAttribute(int lower, int upper) {
		val eAttribute = ECORE_FACTORY.createEAttribute
		eAttribute.name = TEST_EATTRIBUTE_NAME
		eAttribute.lowerBound = lower
		eAttribute.upperBound = upper
		eAttribute.EType = ECORE_PACKAGE.getEString
		eAttribute
	}
	
	private def eReference(int lower, int upper, EClassifier type) {
		val eReference = ECORE_FACTORY.createEReference
		eReference.name = TEST_EREFERENCE_NAME
		eReference.lowerBound = lower
		eReference.upperBound = upper
		eReference.EType = type
		eReference
	}

	private def emptyEClassJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  },
			  "additionalProperties": false
			}
		'''.toJsonElement
	}
	
	private def eClassWithOptionalStringEAttributeJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EATTRIBUTE_NAME»": {"type": "string"}
			  },
			  "additionalProperties": false
			}
		'''.toJsonElement
	}
	
	private def eClassWithMandatoryStringEAttributeJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EATTRIBUTE_NAME»": {"type": "string"}
			  },
			  "additionalProperties": false,
			  "required": [
			    "«TEST_EATTRIBUTE_NAME»"
			  ]
			}
		'''.toJsonElement
	}
	
	private def eClassWithOptionalSingleReferencedEClassJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EREFERENCE_NAME»": {
			  		"type": "object",
			  		"properties": {},
			  		"additionalProperties": false
			  	}
			  },
			  "additionalProperties": false
			}
		'''.toJsonElement
	}
	
	private def eClassWithMandatorySingleReferencedEClassJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EREFERENCE_NAME»": {
			  		"type": "object",
			  		"properties": {},
			  		"additionalProperties": false
			  	}
			  },
			  "additionalProperties": false,
			  "required": [
			    "«TEST_EREFERENCE_NAME»"
			  ]
			}
		'''.toJsonElement
	}
	
	private def eClassWithOptionalMultiReferencedEClassJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EREFERENCE_NAME»": {
			  		"type": "array",
			  		"items":{
			  			"type": "object",
				  		"properties": {},
				  		"additionalProperties": false
			  		}
			  	}
			  },
			  "additionalProperties": false
			}
		'''.toJsonElement
	}
	
	private def eClassWithMandatoryMultiReferencedEClassJsonElement() {
		'''
			{
			  "type": "object",
			  "properties": {
			  	"«TEST_EREFERENCE_NAME»": {
			  		"type": "array",
			  		"items":{
			  			"type": "object",
				  		"properties": {},
				  		"additionalProperties": false
			  		}
			  	}
			  },
			  "additionalProperties": false,
			  "required": [
			    "«TEST_EREFERENCE_NAME»"
			  ]
			}
		'''.toJsonElement
	}

	private def toJsonElement(CharSequence chars) {
		new JsonParser().parse(chars.toString)
	}
}
