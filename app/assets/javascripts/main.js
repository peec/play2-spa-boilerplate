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
		handlebars: 'vendor/handlebars',
		json2: 'vendor/json2',
		
		
		// App Modules
		homepage: 'module/homepage',
		
		
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
	},
	deps: [
	       'start'
	       ]
});


