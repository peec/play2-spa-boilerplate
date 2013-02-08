require.config({
	
	locale: "en",
	
    pragmasOnSave: {
    	// Remove tpl library after compile. Not needed after that.
        excludeTpl: true
    },
	
    paths : {
		/***
		 ***  LIBRARIES
		 ***/
		
		'jquery' : [ 'vendor/jquery-1.9.0.min' ],
		// Twitter bootstrap
		'bootstrap' : [ 'vendor/bootstrap.min' ],
		// MVC
		'backbone' : 'vendor/backbone-min',
		// Utility belt and templating system (micro templates)
		'underscore' : 'vendor/underscore-min',
		// For precompiling html files into js (performance in prod.)
		'tpl' : 'vendor/tpl',
		// Built on backbone, provides better architecture
		'marionette' : 'vendor/backbone.marionette.min',
		// Event architecture (vent)
		'backbone.wreqr' : 'vendor/backbone.wreqr.min',
		'backbone.babysitter' : 'vendor/backbone.babysitter.min',
		// Compability 
		'json2': 'vendor/json2',
		// Cookie manipulation.
		'cookie': 'vendor/jquery.cookie',
		
		
		/***
		 ***  App Modules
		 ***/
		'homepage': 'module/homepage',
		
		

		/***
		 ***  Core shortcuts
		 ***/
		
		'Model': 'module/core/models/Model',
		'userSession': 'module/core/models/userSession',
		'vent': 'module/core/vent',
		'commands': 'module/core/commands'
	},
	shim: {
		'jquery' : { 
			exports : 'jQuery'
		},
		'underscore' : {
			exports : '_'
		},
		'backbone' : {
			deps : [ 'jquery', 'underscore' ],
			exports : 'Backbone'
		},
		'bootstrap' : {
			deps : ['jquery'],
			exports : 'jquery'
		},
		'cookie' : {
			deps : ['jquery'],
			exports : 'jquery'
		},
		'marionette': {
			deps : ['jquery','underscore','backbone']
		}
	}
});

require([
"app"
], 
function (app) {
	"use strict";
	
	// Document is ready. Starting app here
	$(function() {

		
		// Array of all the routers in the modules.
		// Routers should be self executing and hooked to the apps initialize.
		var routers = [
		'homepage/Router'
		];
		
		
		// Require all routers before start.
		require(routers, function(){
			console.log("App bootstrapped.. Starting.");
			app.start();
		});
	});

});