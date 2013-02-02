require.config({
	baseUrl: "/assets/javascripts",
	locale: "en",
	pragmasOnSave: {
		//removes Handlebars.Parser code (used to compile template strings) set
		//it to `false` if you need to parse template strings even after build
		excludeHbsParser : true,
		// kills the entire plugin set once it's built.
		excludeHbs: true,
		// removes i18n precompiler, handlebars and json2
		excludeAfterBuild: true
	},
	hbs: {
		disableI18n: true,
		templateExtension: "html",
		i18nDirectory: '/lang/'
	},
	paths : {
		// Libraries
		jquery : [ 'vendor/jquery-1.9.0.min' ],
		bootstrap : [ 'vendor/bootstrap.min' ],
		backbone : 'vendor/backbone-min',
		underscore : 'vendor/underscore-min',
		marionette : 'vendor/backbone.marionette.min',
		'backbone.wreqr' : 'vendor/backbone.wreqr.min',
		'backbone.babysitter' : 'vendor/backbone.babysitter.min',
		handlebars: 'vendor/handlebars',
		json2: 'vendor/json2',
		cookie: 'vendor/jquery.cookie',

		// App Modules
		homepage: 'module/homepage',
		
		// Core shortcuts,
		Model: 'module/core/models/Model',
		userSession: 'module/core/models/userSession',
		vent: 'module/core/vent',
		
		// RequireJS Plugins
		hbs: 'vendor/hbs',
		
		//text: 'vendor/text',
		
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
		handlebars : {
			deps : [],
			exports : 'Handlebars'
		},
		bootstrap : {
			deps : ['jquery'],
			exports : 'jquery'
		},
		cookie : {
			deps : ['jquery'],
			exports : 'jquery'
		},
		marionette: {
			deps : ['jquery','underscore','backbone']
		}
	},
	deps: [
	       'start'
	       ]
});


