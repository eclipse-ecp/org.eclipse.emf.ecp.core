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
package org.eclipse.emf.ecp.emf2web.util

import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EcorePackage
import java.math.BigDecimal
import java.math.BigInteger
import javax.xml.datatype.XMLGregorianCalendar
import java.util.Date

/**
 * @author Stefan Dirix
 * 
 */
class TypeMapper {
	def static isBooleanType(EClassifier eType) {
		switch (eType.instanceClass) {
			case boolean:true
			case Boolean:true
			default: false
		}
	}

	def static isStringType(EClassifier eType) {
		switch (eType.instanceClass) {
			case String:true
			default: false
		}
	}

	def static isNumberType(EClassifier eType) {
		switch (eType.instanceClass) {
			case BigDecimal:true
			case double:true
			case Double:true
			case float:true
			case Float:true
			default: false
		}
	}

	def static isIntegerType(EClassifier eType) {
		
		switch (eType.instanceClass) {
			case BigInteger:true
			case Byte:true
			case byte:true
			case char:true
			case Character:true
			case int:true
			case Integer: true
			case Long:true
			case long: true
			case Short:true
			case short:true
			default: false
		}
	}

	def static isDateType(EClassifier eType) {
		switch(eType.instanceClass){
			case XMLGregorianCalendar:true
			case Date:true
			default: false
		}
	}
	
	def static isEnumType(EClassifier eType) {
		switch (eType) {
			case EcorePackage.eINSTANCE.EEnum.isInstance(eType): true
			default: false
		}
	}

	def static isUnsupportedType(EClassifier eType) {
		switch (eType) {
			case EcorePackage.eINSTANCE.EByteArray: true
			case EcorePackage.eINSTANCE.EDiagnosticChain: true
			case EcorePackage.eINSTANCE.EEList: true
			case EcorePackage.eINSTANCE.EEnumerator: true
			case EcorePackage.eINSTANCE.EFeatureMap: true
			case EcorePackage.eINSTANCE.EFeatureMapEntry: true
			case EcorePackage.eINSTANCE.EInvocationTargetException: true
			case EcorePackage.eINSTANCE.EJavaClass: true
			case EcorePackage.eINSTANCE.EJavaObject: true
			case EcorePackage.eINSTANCE.EMap: true
			case EcorePackage.eINSTANCE.EResource: true
			case EcorePackage.eINSTANCE.EResourceSet: true
			case EcorePackage.eINSTANCE.ETreeIterator: true
			default: false
		}
	}

	def static isAllowed(EClassifier eType) {
		eType.booleanType || eType.stringType || eType.numberType || eType.integerType || eType.dateType || eType.enumType
	}
}



