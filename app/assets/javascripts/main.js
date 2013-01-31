require.config({
	baseUrl: "/assets/javascripts",
	deps: [
	       'start'
	       ],
	paths : {
		// Libraries
		jquery : [ 'vendor/jquery-1.9.0.min' ],
		bootstrap : [ 'vendor/bootstrap.min' ],
		backbone : 'vendor/backbone-min',
		underscore : 'vendor/underscore-min',
		marionette : 'vendor/backbone.marionette.min',
		handlebars: 'vendor/handlebars',
		json2: 'vendor/json2',
		
		
		// Modules
		homepage: 'module/homepage',
		
		// RequireJS Plugins
		text: 'vendor/text',
		
		// Paths.
		templates: '/assets/templates'
	},
	shim: {
		jquery : { 
			exports : 'jQuery'
		},
		underscore : {
			exports : '_'
		},
		backbone : {
			deps : [ 'jquery', 'underscore' ],
			exports : 'Backbone'
		},
		marionette : {
			deps : [ 'jquery', 'underscore', 'backbone' ],
			exports : 'Marionette'
		},
		handlebars : {
			deps : [],
			exports : 'Handlebars'
		},
		bootstrap : {
			deps : ['jquery'],
			exports : 'jquery'
		}
	}
});


