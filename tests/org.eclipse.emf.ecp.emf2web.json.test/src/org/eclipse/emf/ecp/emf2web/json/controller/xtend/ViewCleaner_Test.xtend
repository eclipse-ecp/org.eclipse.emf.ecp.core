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

import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.util.Diagnostician
import org.eclipse.emf.ecp.view.spi.model.VControl
import org.eclipse.emf.ecp.view.spi.model.VView
import org.eclipse.emf.ecp.view.spi.model.VViewFactory
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridFactory
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.*

class ViewCleaner_Test {

	static EClass personEClass
	static EAttribute ageAttribute

	@BeforeClass
	def static void init(){
		personEClass = EcoreFactory.eINSTANCE.createEClass
		personEClass.name = "Person"
		
		ageAttribute = EcoreFactory.eINSTANCE.createEAttribute
		ageAttribute.name = "age"
		ageAttribute.EType = EcorePackage.eINSTANCE.EInt
		
		personEClass.EStructuralFeatures.add(ageAttribute);
	}

	VView view
	VVerticalLayout vertical
	VControl ageControl

	@Before
	def void createDefaultView(){
		view = VViewFactory.eINSTANCE.createView
		view.rootEClass = personEClass
		
		vertical = VVerticalFactory.eINSTANCE.createVerticalLayout
		ageControl = VViewFactory.eINSTANCE.createControl
		view.children += vertical
		ageControl.domainModelReference = ageAttribute
		vertical.children += ageControl
	}

	@Test
	def void validViewModel(){				
		ViewCleaner.cleanView(view);
		
		assertTrue(view.children.contains(vertical))
		assertTrue(vertical.children.contains(ageControl))
	}

	@Test
	def void removeControlWithoutReference(){
		val control = VViewFactory.eINSTANCE.createControl
		vertical.children += control
		
		assertTrue(vertical.children.contains(control))
				
		ViewCleaner.cleanView(view);
		
		assertTrue(vertical.children.contains(ageControl))
		assertFalse(vertical.children.contains(control))
	}

	@Test
	def void removeControlWithInvalidReference(){
		val control = VViewFactory.eINSTANCE.createControl
		
		val attribute = EcoreFactory.eINSTANCE.createEAttribute
		attribute.name = "invalid"
		attribute.EType = EcorePackage.eINSTANCE.getEString()
		
		control.domainModelReference = attribute
		vertical.children += control
		
		assertTrue(vertical.children.contains(control))
				
		ViewCleaner.cleanView(view);
		
		assertTrue(vertical.children.contains(ageControl))
		assertFalse(vertical.children.contains(control))
	}

	@Test
	def void removeUnsupportedElement(){
		val control = VViewFactory.eINSTANCE.createControl
		control.domainModelReference = ageAttribute
		
		val grid = VControlgridFactory.eINSTANCE.createControlGrid
		val row = VControlgridFactory.eINSTANCE.createControlGridRow
		val cell = VControlgridFactory.eINSTANCE.createControlGridCell
		vertical.children += grid
		grid.rows += row
		row.cells += cell
		cell.control = control
		
		assertTrue(vertical.children.contains(grid))
		assertTrue(Diagnostician.INSTANCE.validate(grid).severity === Diagnostic.OK)
				
		ViewCleaner.cleanView(view);
		
		assertFalse(vertical.children.contains(grid))
	}

}