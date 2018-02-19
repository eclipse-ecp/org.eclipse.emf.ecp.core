package org.eclipse.emf.ecp.emf2web.generator.json

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalFactory
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference
import org.eclipse.emf.ecp.view.spi.model.VViewFactory
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*
import org.eclipse.emf.ecp.emf2web.json.generator.FormsJsonGenerator
import org.eclipse.emf.ecp.emf2web.util.ReferenceHelper
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationFactory

class FormsJsonExporter_Test {
	private FormsJsonGenerator exporter;
	val testName = "testName";
	val testReference = "testReference";
	val EStructuralFeature mockFeature = EcorePackage.eINSTANCE.getEClass_Abstract

	@Before
	def void init() {
		exporter = new FormsJsonGenerator(
			new ReferenceHelper(){
				override getLabel(VDomainModelReference reference) {
					testName
				}
				override getStringRepresentation(VDomainModelReference reference) {
					testReference
				}
				
			}
		)
	}

	@Test
	def testBuildEmptyViewModel() {
		val view = VViewFactory.eINSTANCE.createView
		val result = exporter.createJsonElement(view)
		assertEquals(emptyViewModel(), result)
	}

	@Test
	def testBuildViewWithAllContentsModel() {
		val view = VViewFactory.eINSTANCE.createView
		val horizontal = VHorizontalFactory.eINSTANCE.createHorizontalLayout
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		val control = VViewFactory.eINSTANCE.createControl

		//Use Ecore Ecore as a mock
		control.setDomainModelReference(mockFeature)
		
		view.children.add(horizontal)
		view.children.add(vertical)
		view.children.add(control)

		val result = exporter.createJsonElement(view)
		assertEquals(viewWithAllContentsModel(), result)
	}
	
	@Test
	def testCategorization() {
		val view = VViewFactory.eINSTANCE.createView
		val categorizationElement = VCategorizationFactory.eINSTANCE.createCategorizationElement
		val firstVerticalLayout = VVerticalFactory.eINSTANCE.createVerticalLayout
		val secondVerticalLayout = VVerticalFactory.eINSTANCE.createVerticalLayout
		val firstCategory = VCategorizationFactory.eINSTANCE.createCategory
		val secondCategory = VCategorizationFactory.eINSTANCE.createCategory
		
		firstCategory.label = "First"
		secondCategory.label = "Second"
		
		val firstControl = VViewFactory.eINSTANCE.createControl
		val secondControl = VViewFactory.eINSTANCE.createControl
		
		firstControl.setDomainModelReference(mockFeature)
		secondControl.setDomainModelReference(mockFeature)
		
		firstVerticalLayout.children.add(firstControl)
		secondVerticalLayout.children.add(secondControl)
		
		firstCategory.composite = firstVerticalLayout
		secondCategory.composite = secondVerticalLayout
		
		categorizationElement.categorizations.add(firstCategory)
		categorizationElement.categorizations.add(secondCategory)
		
		view.children.add(categorizationElement)
		
		val result = exporter.createJsonElement(view)
		assertEquals(viewWithTwoCategories, result)
	}
	
	def JsonElement viewWithTwoCategories() {
		'''
		{
		  "type": "Categorization",
		  "elements": [
		    {
		      "type": "Category",
		      "label": "First",
		      "elements": [{
		      	  "type": "Control",
		      	  "label": "«testName»",
		      	  "scope": "«testReference»"
			  	}
		      ]
		    },
		    {
		      "type": "Category",
		      "label": "Second",
		      "elements": [{
		      	  "type": "Control",
		      	  "label": "«testName»",
		      	  "scope": "«testReference»"
			  	}
		      ]
		    }		  
		  ]
		}
		'''
		.toJsonElement
	}
	
	def JsonElement viewWithAllContentsModel() {
		'''
		{
		  "type": "VerticalLayout",
		  "elements": [
		    {
		      "type": "HorizontalLayout",
		      "elements": [
		      ]
		    },
		    {
		      "type": "VerticalLayout",
		      "elements": [
		      ]
		    },
		    {
		      "type": "Control",
		      "scope": "«testReference»",
		      "label": "«testName»"
		    }
			]
		}
		'''
		.toJsonElement
	}

	@Test
	def testBuildControl() {
		val result = exporter.createJsonElement(VViewFactory.eINSTANCE.createControl)
		assertEquals(testControl, result)
	}

	@Test
	def testBuildHorizontalLayoutl() {
		val horizontal = VHorizontalFactory.eINSTANCE.createHorizontalLayout
		val result = exporter.createJsonElement(horizontal)
		assertEquals(testHorizontal, result)
	}

	@Test
	def testBuildVerticalLayout() {
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		val result = exporter.createJsonElement(vertical)
		assertEquals(testVertical, result)
	}

	@Test
	def testBuildVerticalInHorizontalLayout() {
		val horizontal = VHorizontalFactory.eINSTANCE.createHorizontalLayout
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		horizontal.children.add(vertical)
		val result = exporter.createJsonElement(horizontal)
		assertEquals(testVerticalInHorizontal, result)
	}

	@Test
	def testBuildHorizontalInVerticalLayout() {
		val horizontal = VHorizontalFactory.eINSTANCE.createHorizontalLayout
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		vertical.children.add(horizontal)
		val result = exporter.createJsonElement(vertical)
		assertEquals(testHorizontalInVertical, result)
	}

	@Test
	def testBuild2HorizontalsInVerticalLayout() {
		val horizontal = VHorizontalFactory.eINSTANCE.createHorizontalLayout
		val horizontal2 = VHorizontalFactory.eINSTANCE.createHorizontalLayout

		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		vertical.children.add(horizontal)
		vertical.children.add(horizontal2)
		val result = exporter.createJsonElement(vertical)
		assertEquals(test2HorizontalInVertical, result)
	}

	@Test
	def testBuildControlInVerticalLayout() {
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		val control = VViewFactory.eINSTANCE.createControl

		//Use Ecore Ecore as a mock
		control.setDomainModelReference(mockFeature)
		vertical.children.add(control);
		val result = exporter.createJsonElement(vertical)
		assertEquals(testControlInVertical, result)
	}

	@Test
	def testBuild2ControlsInVerticalLayout() {
		val vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		val control = VViewFactory.eINSTANCE.createControl
		val control2 = VViewFactory.eINSTANCE.createControl

		//Use Ecore Ecore as a mock
		control.setDomainModelReference(mockFeature)
		control2.setDomainModelReference(mockFeature)
		vertical.children.add(control);
		vertical.children.add(control2);
		val result = exporter.createJsonElement(vertical)
		assertEquals(test2ControlsInVertical, result)
	}

	def JsonElement test2HorizontalInVertical() {
		'''
			{
			  "type": "VerticalLayout",
			  "elements": [
			    {
			      "type": "HorizontalLayout",
			      "elements": [
			      ]
			    },
			    {
			      "type": "HorizontalLayout",
			      "elements": [
			      ]
			    }
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement test2ControlsInVertical() {
		'''
			{
			  "type": "VerticalLayout",
			  "elements": [
			    {
			      "type": "Control",
			      "label": "«testName»",
			      "scope": "«testReference»"
			    },
			    {
			      "type": "Control",
			      "label": "«testName»",
			      "scope": "«testReference»"
			    }
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement testControlInVertical() {
		'''
			{
			  "type": "VerticalLayout",
			  "elements": [
			    {
			      "type": "Control",
			      "label": "«testName»",
			      "scope": "«testReference»"
			    }
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement testVerticalInHorizontal() {
		'''
			{
			  "type": "HorizontalLayout",
			  "elements": [
			    {
			      "type": "VerticalLayout",
			      "elements": [
			      ]
			    }
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement testHorizontalInVertical() {
		'''
			{
			  "type": "VerticalLayout",
			  "elements": [
			    {
			      "type": "HorizontalLayout",
			      "elements": [
			      ]
			    }
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement testHorizontal() {
		'''
			{
			  "type": "HorizontalLayout",
			  "elements": [
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement testVertical() {
		'''
			{
			  "type": "VerticalLayout",
			  "elements": [
			  ]
			}
		'''
		.toJsonElement
	}

	def JsonElement emptyViewModel() {
		'''
			{}
		'''
		.toJsonElement
	}

	def JsonElement testControl() {
		'''
		    {
		      "type": "Control",
		      "label": "«testName»",
		      "scope": "«testReference»"
		    }
		'''
		.toJsonElement
	}
	
	private def toJsonElement(CharSequence chars) {
		new JsonParser().parse(chars.toString)
	}

}
