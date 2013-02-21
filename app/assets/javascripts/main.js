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
		'users': 'module/users',
		
		

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
"app",
"bootstrap"
], 
function (app) {
	"use strict";
	
	// Document is ready. Starting app here
	$(function() {

		
		// Array of all the bootstrappers in the modules.
		// See other modules, bootstrap should addInitializer to the "app" 
		// to initialize a Router object for the module (normally).
		// A module does not need a router, it can also be some sort of plugin funcitonality.
		var bootstrappers = [
		'homepage/bootstrap',
		'users/bootstrap'
		];
		
		
		// Require all bootstrappers before start.
		require(bootstrappers, function(){
			console.log("App bootstrapped.. Starting.");
			app.start();
		});
	});

});