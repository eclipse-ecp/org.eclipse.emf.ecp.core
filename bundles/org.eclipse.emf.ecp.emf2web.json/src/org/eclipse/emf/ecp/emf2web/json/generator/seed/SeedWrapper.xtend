package org.eclipse.emf.ecp.emf2web.json.generator.seed

import org.eclipse.emf.ecp.emf2web.controller.GenerationInfo
import org.eclipse.emf.ecp.emf2web.exporter.SchemaWrapper

class SeedWrapper implements SchemaWrapper {

	override getName() {
		return "JSONForms Seed project";
	}

	override getFileExtension() {
		"js"
	}

	override wrap(String toWrap, String type) {
		switch (type) {
			case GenerationInfo.MODEL_TYPE:
				wrapModel(toWrap).toString
			case GenerationInfo.VIEW_TYPE:
				wrapView(toWrap).toString
			default:
				throw new IllegalArgumentException("Could not wrap: " + type)
		}

	}

	def wrapModel(String model) {
		'''
			'use strict';
			
			var app = angular.module('jsonforms-seed');
			app.value('Schema', «model» );
		'''
	}

	def wrapView(String view) {
		'''
			'use strict';
			
			var app = angular.module('jsonforms-seed');
			app.value('UISchema', «view» );
		'''
	}

}